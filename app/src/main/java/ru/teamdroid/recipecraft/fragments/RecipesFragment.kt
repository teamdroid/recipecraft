package ru.teamdroid.recipecraft.fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_recipes.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.adapters.RecipesAdapter
import ru.teamdroid.recipecraft.base.BaseMoxyFragment
import ru.teamdroid.recipecraft.room.models.RecipesViewModel
import ru.teamdroid.recipecraft.presenters.RecipesPresenter
import ru.teamdroid.recipecraft.room.Injection
import ru.teamdroid.recipecraft.room.entity.Recipe
import ru.teamdroid.recipecraft.views.RecipesView

class RecipesFragment : BaseMoxyFragment(), RecipesView {

    @InjectPresenter
    lateinit var presenter: RecipesPresenter

    private var compositeDisposable: CompositeDisposable? = null

    private lateinit var viewModelRecipes: RecipesViewModel

    override val contentResId = R.layout.fragment_recipes

    private val recipesAdapter by lazy {
        RecipesAdapter(
                onItemClickListener = {
                    onClick(it)
                },
                onFavoriteClickListener = {
                    onFavoriteClick(it)
                }
        )
    }

    private fun onClick(position: Int) {
        baseActivity.replaceFragment(DetailRecipeFragment.newInstance(recipesAdapter.recipes[position]), NavigationFragment.TAG)
    }

    private fun onFavoriteClick(recipe: Recipe) {
        compositeDisposable = CompositeDisposable()
        compositeDisposable?.add(viewModelRecipes.bookmarkRecipe(recipe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { recipesAdapter.notifyDataSetChanged() })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(toolbar, false, getString(R.string.fragment_recipes_title))

        viewModelRecipes = ViewModelProviders.of(this, Injection.provideRecipesViewModelFactory(context)).get(RecipesViewModel::class.java)

        with(recyclerView) {
            adapter = recipesAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        if (recipesAdapter.recipes.isEmpty()) loadRemote()

        swipeRefreshLayout.setOnRefreshListener {
            progressBar.visibility = View.VISIBLE
            swipeRefreshLayout.isRefreshing = false
            progressBar.visibility = View.VISIBLE
            loadRemote()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadRemote()
    }

    private fun loadRemote() {
        recipesAdapter.recipes = ArrayList()
        presenter.loadRemote(getString(R.string.language))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_navigation, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onSuccessLoad(list: MutableList<Recipe>) {

        recipesAdapter.recipes = list

        compositeDisposable?.add(viewModelRecipes.insertRecipes(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ Log.d(TAG, "Recipes is saved") }, { Log.d("Resulting", "Not saved") }))

        setInvisibleRefreshing()
    }

    override fun onErrorLoad(error: Throwable) {
        Toast.makeText(context, error.message, Int.MAX_VALUE).show()
        setInvisibleRefreshing()
    }

    override fun setInvisibleRefreshing() {
        progressBar.visibility = View.GONE
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_report -> {
                baseActivity.replaceFragment(ReportFragment.newInstance(), NavigationFragment.TAG)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.dispose()
    }

    companion object {
        const val TAG = "RecipesFragment"
        fun newInstance() = RecipesFragment()
    }
}