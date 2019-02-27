package ru.teamdroid.recipecraft.fragments

import android.arch.lifecycle.ViewModelProviders
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
import ru.teamdroid.recipecraft.room.ViewModelFactory
import ru.teamdroid.recipecraft.room.entity.Recipe
import ru.teamdroid.recipecraft.views.RecipesView

class RecipesFragment : BaseMoxyFragment(), RecipesView {

    @InjectPresenter
    lateinit var presenter: RecipesPresenter

    private var compositeDisposable: CompositeDisposable? = null

    private lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: RecipesViewModel

    override val contentResId = R.layout.fragment_recipes

    private var isCreate = false

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
        compositeDisposable?.add(viewModel.update(recipe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ Log.d(TAG, "Resulting is "+recipesAdapter.recipes[recipe.id].isBookmarked.toString()) }, { Log.d("resulting", "error") }))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(toolbar, false, getString(R.string.fragment_recipes_title))

        viewModelFactory = Injection.provideRecipesViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RecipesViewModel::class.java)

        with(recyclerView) {
            adapter = recipesAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            progressBar.visibility = View.VISIBLE
            loadRemote()
        }

        // fix it
        if (!isCreate) loadRemote()
        isCreate = true
    }

    private fun loadRemote() {
        progressBar.visibility = View.VISIBLE
        swipeRefreshLayout.isRefreshing = false
        recipesAdapter.recipes = ArrayList()
        presenter.loadRemote(getString(R.string.language))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_navigation, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onSuccessLoad(list: MutableList<Recipe>) {
        recipesAdapter.recipes = list


        compositeDisposable?.add(viewModel.insertRecipes(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ Log.d(TAG, "cashed") }, { Log.d("resulting", "error") }))

        setInvisibleRefreshing()

        Log.d(TAG, list.toString())

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