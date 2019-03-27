package ru.teamdroid.recipecraft.ui.navigation.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_favorites.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.ui.base.BaseFragment
import ru.teamdroid.recipecraft.ui.navigation.FavoritesContract
import ru.teamdroid.recipecraft.ui.navigation.adapters.RecipesAdapter
import ru.teamdroid.recipecraft.ui.navigation.components.DaggerFavoritesComponent
import ru.teamdroid.recipecraft.ui.navigation.modules.FavoritesPresenterModule
import ru.teamdroid.recipecraft.ui.navigation.presenters.FavoritesPresenter
import javax.inject.Inject

class FavoritesFragment : BaseFragment(), FavoritesContract.View {

    @Inject
    internal lateinit var presenter: FavoritesPresenter

    override val contentResId = R.layout.fragment_favorites

    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry = lifecycleRegistry

    private val bookmarkRecipesAdapter by lazy {
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
        initializePresenter()
    }

    private fun initializePresenter() {
        DaggerFavoritesComponent.builder()
                .favoritesPresenterModule(FavoritesPresenterModule(this))
                .recipeRepositoryComponent(baseActivity.recipeRepositoryComponent)
                .build()
                .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(toolbar, false, getString(R.string.fragment_favorites_title))

        with(recyclerView) {
            adapter = bookmarkRecipesAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        if (bookmarkRecipesAdapter.recipes.isEmpty()) refresh()

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            progressBar.visibility = View.VISIBLE
            refresh()
        }
    }

    private fun refresh() {
        progressBar.visibility = View.VISIBLE
        swipeRefreshLayout.isRefreshing = false
        bookmarkRecipesAdapter.recipes = ArrayList()
        presenter.loadRecipes()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_navigation, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun onClick(position: Int) {
        val currentRecipe = bookmarkRecipesAdapter.recipes[position]
        //baseActivity.replaceFragment(DetailRecipeFragment.newInstance(RecipeEntity(currentRecipe.idRecipe, currentRecipe.title, currentRecipe.isBookmarked, currentRecipe.ingredients)), NavigationFragment.TAG)
    }

    private fun onFavoriteClick(recipe: Recipe) {
        recipe.isBookmarked = !recipe.isBookmarked
        presenter.bookmarkRecipe(recipe)
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

//    fun onSuccessLoad(list: MutableList<Recipe>) {
//        bookmarkRecipesAdapter.recipes = list
//        setInvisibleRefreshing()
//    }
//
//    fun setInvisibleRefreshing() {
//        progressBar.visibility = View.GONE
//        swipeRefreshLayout.isRefreshing = false
//    }

    override fun showRecipes(recipes: MutableList<Recipe>) {
        bookmarkRecipesAdapter.recipes = recipes
    }

    private fun setInvisibleRefreshing() {
        progressBar.visibility = View.GONE
        swipeRefreshLayout.isRefreshing = false
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }
}