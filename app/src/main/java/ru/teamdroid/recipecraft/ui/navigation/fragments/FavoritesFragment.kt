package ru.teamdroid.recipecraft.ui.navigation.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_favorites.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.ui.base.BaseFragment
import ru.teamdroid.recipecraft.ui.navigation.adapters.RecipesAdapter

class FavoritesFragment : BaseFragment() {


    override val contentResId = R.layout.fragment_favorites

    private var compositeDisposable = CompositeDisposable()


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(toolbar, false, getString(R.string.fragment_favorites_title))

        with(recyclerView) {
            adapter = bookmarkRecipesAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        if (bookmarkRecipesAdapter.recipes.isEmpty()) {
            refresh()
        }

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
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_navigation, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun onClick(position: Int) {
        val currentRecipe = bookmarkRecipesAdapter.recipes[position]
        //baseActivity.replaceFragment(DetailRecipeFragment.newInstance(RecipeEntity(currentRecipe.idRecipe, currentRecipe.title, currentRecipe.isBookmarked, currentRecipe.ingredients)), NavigationFragment.TAG)
    }

    private fun onFavoriteClick(recipes: Recipe) {

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

    fun onSuccessLoad(list: MutableList<Recipe>) {
        bookmarkRecipesAdapter.recipes = list
        setInvisibleRefreshing()
    }

    fun setInvisibleRefreshing() {
        progressBar.visibility = View.GONE
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }
}