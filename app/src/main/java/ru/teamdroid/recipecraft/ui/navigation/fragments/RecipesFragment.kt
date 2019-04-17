package ru.teamdroid.recipecraft.ui.navigation.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_recipes.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.ui.base.BaseFragment
import ru.teamdroid.recipecraft.ui.base.SortRecipes
import ru.teamdroid.recipecraft.ui.navigation.adapters.RecipesAdapter
import ru.teamdroid.recipecraft.ui.navigation.components.DaggerRecipesComponent
import ru.teamdroid.recipecraft.ui.navigation.contracts.RecipesContract
import ru.teamdroid.recipecraft.ui.navigation.modules.RecipesPresenterModule
import ru.teamdroid.recipecraft.ui.navigation.presenters.RecipesPresenter
import javax.inject.Inject

class RecipesFragment : BaseFragment(), RecipesContract.View {

    @Inject
    internal lateinit var presenter: RecipesPresenter

    private var currentSort = SortRecipes.ByNewer

    override val contentResId = R.layout.fragment_recipes

    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry = lifecycleRegistry

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

    private lateinit var sortAdapter: Adapter

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
        setupToolbar(toolbar, false, "")

        with(recyclerView) {
            adapter = recipesAdapter
            layoutManager = LinearLayoutManager(context)
        }

        if (recipesAdapter.recipes.isEmpty()) refresh(false)

        sortAdapter = ArrayAdapter.createFromResource(
                context,
                R.array.sort_recipes,
                R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            spinner_nav.adapter = adapter
        }

        spinner_nav.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(adapter: AdapterView<*>, v: View?, i: Int, lng: Long) {
                with(recipesAdapter) {
                    when (i) {
                        0 -> currentSort = SortRecipes.ByNewer
                        1 -> currentSort = SortRecipes.ByPortion
                        2 -> currentSort = SortRecipes.ByIngredients
                        3 -> currentSort = SortRecipes.ByTime
                    }
                    sort(currentSort)
                    notifyDataSetChanged()
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }

        swipeRefreshLayout.setOnRefreshListener {
            refresh(isOnline())
        }
    }

    private fun onClick(position: Int) {
        baseActivity.replaceFragment(DetailRecipeFragment.newInstance(recipesAdapter.recipes[position]), NavigationFragment.TAG)
    }

    private fun onFavoriteClick(recipe: Recipe) {
        recipe.isBookmarked = !recipe.isBookmarked
        presenter.bookmarkRecipe(recipe)
    }

    private fun refresh(onlineRequired: Boolean) {
        progressBar.visibility = View.VISIBLE
        swipeRefreshLayout.isRefreshing = false
        recipesAdapter.recipes = arrayListOf()
        presenter.loadRecipes(onlineRequired)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_navigation, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun showRecipes(recipes: MutableList<Recipe>) {
        recipesAdapter.apply {
            this.recipes = recipes
            sort(currentSort)
        }
        setInvisibleRefreshing()
    }

    private fun setInvisibleRefreshing() {
        if (isResumed) {
            progressBar.visibility = View.GONE
            swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun showBookmarked(isBookmarked: Boolean) {
        val snackBar = Snackbar.make(constraintLayout, if (isBookmarked) getString(R.string.bookmarked) else getString(R.string.unbookmark_text), 500)
        snackBar.setAction(getString(R.string.close_text)) { snackBar.dismiss() }.setActionTextColor(resources.getColor(R.color.textWhite)).show()
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

    override fun onDestroyView() {
        recyclerView.adapter = null
        spinner_nav.adapter = null
        super.onDestroyView()
    }

    companion object {
        const val TAG = "RecipesFragment"
        fun newInstance() = RecipesFragment()
    }
}
