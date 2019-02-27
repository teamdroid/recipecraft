package ru.teamdroid.recipecraft.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_favorites.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.adapters.RecipesAdapter
import ru.teamdroid.recipecraft.base.BaseMoxyFragment
import ru.teamdroid.recipecraft.room.models.RecipesViewModel
import ru.teamdroid.recipecraft.room.Injection
import ru.teamdroid.recipecraft.room.ViewModelFactory
import ru.teamdroid.recipecraft.room.entity.Recipe

class FavoritesFragment : BaseMoxyFragment() {

    override val contentResId = R.layout.fragment_favorites

    private var compositeDisposable: CompositeDisposable? = null

    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var recipesViewModel: RecipesViewModel

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(toolbar, false, getString(R.string.fragment_favorites_title))

        with(recyclerView) {
            adapter = recipesAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        viewModelFactory = Injection.provideRecipesViewModelFactory(context)
        recipesViewModel = ViewModelProviders.of(this, viewModelFactory).get(RecipesViewModel::class.java)

        refresh()
    }

    private fun refresh() {
        progressBar.visibility = View.VISIBLE
        swipeRefreshLayout.isRefreshing = false
        recipesAdapter.recipes = ArrayList()

        compositeDisposable = CompositeDisposable()

        compositeDisposable?.add(recipesViewModel.getAllRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    recipesAdapter.recipes = it
                    setInvisibleRefreshing()
                }, { }))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_navigation, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun onClick(position: Int) {
        baseActivity.replaceFragment(DetailRecipeFragment.newInstance(recipesAdapter.recipes[position]), NavigationFragment.TAG)
    }

    private fun onFavoriteClick(recipe: Recipe) {
        compositeDisposable?.add(recipesViewModel.update(recipe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ Log.d("dsfsdfsd", recipe.isBookmarked.toString())}, { }))
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

    private fun setInvisibleRefreshing() {
        progressBar.visibility = View.GONE
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.dispose()
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }
}