package ru.teamdroid.recipecraft.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_favorites.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.adapters.RecipesAdapter
import ru.teamdroid.recipecraft.base.BaseMoxyFragment
import ru.teamdroid.recipecraft.presenters.FavoritesPresenter
import ru.teamdroid.recipecraft.room.Injection
import ru.teamdroid.recipecraft.room.entity.Recipes
import ru.teamdroid.recipecraft.room.models.RecipesViewModel
import ru.teamdroid.recipecraft.views.FavoritesView

class FavoritesFragment : BaseMoxyFragment(), FavoritesView {

    @InjectPresenter
    lateinit var presenter: FavoritesPresenter

    override val contentResId = R.layout.fragment_favorites

    private var compositeDisposable = CompositeDisposable()

    private lateinit var viewModelRecipes: RecipesViewModel

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
            viewModelRecipes = ViewModelProviders.of(this, Injection.provideRecipesViewModelFactory(context)).get(RecipesViewModel::class.java)
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
        presenter.getAllBookmarkedRecipes(viewModelRecipes)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_navigation, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun onClick(position: Int) {
        val currentRecipe = bookmarkRecipesAdapter.recipes[position]
        //baseActivity.replaceFragment(DetailRecipeFragment.newInstance(Recipes(currentRecipe.idRecipe, currentRecipe.title, currentRecipe.isBookmarked, currentRecipe.ingredients)), NavigationFragment.TAG)
    }

    private fun onFavoriteClick(recipes: Recipes) {
        presenter.bookmarkRecipe(recipes, viewModelRecipes)
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

    override fun onSuccessLoad(list: MutableList<Recipes>) {
        bookmarkRecipesAdapter.recipes = list
        setInvisibleRefreshing()
    }

    private fun setInvisibleRefreshing() {
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