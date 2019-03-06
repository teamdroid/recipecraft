package ru.teamdroid.recipecraft.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.teamdroid.recipecraft.fragments.NavigationFragment
import ru.teamdroid.recipecraft.room.entity.Recipes
import ru.teamdroid.recipecraft.room.models.RecipesViewModel
import ru.teamdroid.recipecraft.views.FavoritesView

@InjectViewState
class FavoritesPresenter : MvpPresenter<FavoritesView>() {

    private var compositeDisposable = CompositeDisposable()

    fun getAllBookmarkedRecipes(viewModelRecipes: RecipesViewModel) {
        compositeDisposable.add(viewModelRecipes.getAllBookmarkedRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                   viewState.onSuccessLoad(it)
                }, { }))
    }

    fun bookmarkRecipe(recipes: Recipes, viewModelRecipes: RecipesViewModel) {
        compositeDisposable.add(viewModelRecipes.bookmarkRecipe(recipes)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ Log.d(NavigationFragment.TAG, "Success") }, { }))
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}