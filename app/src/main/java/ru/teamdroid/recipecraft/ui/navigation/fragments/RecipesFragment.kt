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
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_recipes.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.ui.base.customs.CustomLinearLayoutManager
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.ui.base.BaseMoxyFragment
import ru.teamdroid.recipecraft.ui.base.SortRecipes
import ru.teamdroid.recipecraft.ui.navigation.adapters.RecipesAdapter
import ru.teamdroid.recipecraft.ui.navigation.components.DaggerRecipesComponent
import ru.teamdroid.recipecraft.ui.navigation.presenters.RecipesPresenter
import ru.teamdroid.recipecraft.ui.navigation.views.RecipeView
import javax.inject.Inject

class RecipesFragment : BaseMoxyFragment(), RecipeView {

    @Inject
    @InjectPresenter
    lateinit var presenter: RecipesPresenter

    @ProvidePresenter
    fun providePresenter(): RecipesPresenter {
        DaggerRecipesComponent.builder()
                .recipeRepositoryComponent(baseActivity.recipeRepositoryComponent)
                .build()
                .inject(this)
        return presenter
    }

    override val contentResId = R.layout.fragment_recipes

    private var currentSort = ""

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(toolbar, false, "")

        setUpRecyclerView()
        with(recipesRecyclerView) {
            adapter = recipesAdapter
        }

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
                when (i) {
                    0 -> refresh(false, SortRecipes.ByNewer)
                    1 -> refresh(false, SortRecipes.ByPortion)
                    2 -> refresh(false, SortRecipes.ByIngredients)
                    3 -> refresh(false, SortRecipes.ByTime)
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }

        swipeRefreshLayout.setOnRefreshListener {
            refresh(isNetworkAvailable())
        }
    }

    private fun onClick(position: Int) {
        baseActivity.replaceFragment(DetailRecipeFragment.newInstance(recipesAdapter.listRecipes[position]), NavigationFragment.TAG)
    }

    private fun onFavoriteClick(recipe: Recipe) {
        presenter.bookmarkRecipe(recipe)
    }

    private fun refresh(onlineRequired: Boolean, sort : String = currentSort) {
        if (onlineRequired || sort != currentSort) {
            currentSort = sort
            progressBar.visibility = View.VISIBLE
            swipeRefreshLayout.isRefreshing = false
            recipesAdapter.clear()
            recipesAdapter.notifyDataSetChanged()
            presenter.loadRecipes(onlineRequired, currentSort, 0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_navigation, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setUpRecyclerView() {

        val customLinearLayoutManager = CustomLinearLayoutManager(context)

        with(recipesRecyclerView) {
            layoutManager = customLinearLayoutManager
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (customLinearLayoutManager.isOnNextPagePosition()) {
                        presenter.loadCount(recipesAdapter.itemCount, currentSort)
                    }
                }
            })
        }
        (recipesRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    override fun showRecipes(listRecipes: MutableList<Recipe>) {
        recipesAdapter.updateRecipes(listRecipes)
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
        snackBar.setAction(getString(R.string.close_text)) { snackBar.dismiss() }.setActionTextColor(ContextCompat.getColor(context, R.color.textWhite)).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_report -> {
                baseActivity.replaceFragment(FeedbackFragment.newInstance(), NavigationFragment.TAG)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        recipesRecyclerView.adapter = null
        spinner_nav.adapter = null
        super.onDestroyView()
    }

    companion object {
        const val TAG = "RecipesFragment"
        fun newInstance() = RecipesFragment()
    }
}
