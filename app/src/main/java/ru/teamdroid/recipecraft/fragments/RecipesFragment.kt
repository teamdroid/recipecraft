package ru.teamdroid.recipecraft.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_recipes.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.adapters.RecipesAdapter
import ru.teamdroid.recipecraft.base.BaseMoxyFragment
import ru.teamdroid.recipecraft.room.models.RecipesViewModel
import ru.teamdroid.recipecraft.presenters.RecipesPresenter
import ru.teamdroid.recipecraft.room.Injection
import ru.teamdroid.recipecraft.room.entity.Recipes
import ru.teamdroid.recipecraft.views.RecipesView

class RecipesFragment : BaseMoxyFragment(), RecipesView {

    @InjectPresenter
    lateinit var presenter: RecipesPresenter

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelRecipes = ViewModelProviders.of(this, Injection.provideRecipesViewModelFactory(context)).get(RecipesViewModel::class.java)
        presenter.onCreate(viewModelRecipes)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(toolbar, false, getString(R.string.fragment_recipes_title))

        with(recyclerView) {
            adapter = recipesAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        if (recipesAdapter.recipes.isEmpty()) loadLocal()

        swipeRefreshLayout.setOnRefreshListener {
            progressBar.visibility = View.VISIBLE
            swipeRefreshLayout.isRefreshing = false
            progressBar.visibility = View.VISIBLE
            refresh()
        }
    }

    private fun onClick(position: Int) {
        baseActivity.replaceFragment(DetailRecipeFragment.newInstance(recipesAdapter.recipes[position]), NavigationFragment.TAG)
    }

    private fun onFavoriteClick(recipes: Recipes) {
        presenter.bookmarkRecipe(recipes)
    }

    private fun refresh() {
        progressBar.visibility = View.VISIBLE
        swipeRefreshLayout.isRefreshing = false
        recipesAdapter.recipes = ArrayList()
        loadRemote()
    }

    private fun loadLocal() {
        presenter.getAllRecipe()
        if (recipesAdapter.recipes.isEmpty()) loadRemote()
    }

    private fun loadRemote() {
        recipesAdapter.recipes = ArrayList()
        presenter.loadRemote(getString(R.string.language))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_navigation, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onSuccessLoad(list: MutableList<Recipes>) {
        recipesAdapter.recipes = list
        setInvisibleRefreshing()
    }

    override fun onErrorLoad(error: Throwable) {
        Toast.makeText(context, getString(R.string.error_remote_load), Int.MAX_VALUE).show()
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

    companion object {
        const val TAG = "RecipesFragment"
        fun newInstance() = RecipesFragment()
    }
}