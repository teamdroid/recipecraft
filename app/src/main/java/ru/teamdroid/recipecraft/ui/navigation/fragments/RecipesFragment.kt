package ru.teamdroid.recipecraft.ui.navigation.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_recipes.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.ui.base.BaseFragment
import ru.teamdroid.recipecraft.ui.navigation.RecipesContract
import ru.teamdroid.recipecraft.ui.navigation.adapters.RecipesAdapter
import ru.teamdroid.recipecraft.ui.navigation.components.DaggerRecipesComponent
import ru.teamdroid.recipecraft.ui.navigation.modules.RecipesPresenterModule
import ru.teamdroid.recipecraft.ui.navigation.presenters.RecipesPresenter
import javax.inject.Inject

class RecipesFragment : BaseFragment(), RecipesContract.View {

    @Inject
    internal lateinit var presenter: RecipesPresenter

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
        initializePresenter()
    }

    private fun initializePresenter() {
        DaggerRecipesComponent.builder()
                .recipesPresenterModule(RecipesPresenterModule(this))
                .recipeRepositoryComponent(baseActivity.recipeRepositoryComponent)
                .build()
                .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(toolbar, false, getString(R.string.fragment_recipes_title))

        with(recyclerView) {
            adapter = recipesAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        if (recipesAdapter.recipes.isEmpty()) refresh()

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

    private fun onFavoriteClick(recipes: Recipe) {
        presenter.bookmarkRecipe(recipes)
    }

    private fun refresh() {
        progressBar.visibility = View.VISIBLE
        swipeRefreshLayout.isRefreshing = false
        recipesAdapter.recipes = ArrayList()
        presenter.loadRecipes(false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_navigation, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    fun onSuccessLoad(list: MutableList<Recipe>) {
      //  recipesAdapter.recipes = list
        setInvisibleRefreshing()
    }

    fun onErrorLoad(error: Throwable) {
        Toast.makeText(context, getString(R.string.error_remote_load), Int.MAX_VALUE).show()
        setInvisibleRefreshing()
    }

    override fun showRecipes(recipes: MutableList<Recipe>) {
        recipesAdapter.recipes = recipes
        setInvisibleRefreshing()
    }

    private fun setInvisibleRefreshing() {
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
