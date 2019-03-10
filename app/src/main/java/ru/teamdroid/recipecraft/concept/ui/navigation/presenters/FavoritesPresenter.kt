package ru.teamdroid.recipecraft.concept.ui.navigation.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.CompositeDisposable
import ru.teamdroid.recipecraft.room.entity.Recipes
import ru.teamdroid.recipecraft.room.models.RecipesViewModel
import ru.teamdroid.recipecraft.views.FavoritesView

@InjectViewState
class FavoritesPresenter : MvpPresenter<FavoritesView>() {

    private var compositeDisposable = CompositeDisposable()

    fun getAllBookmarkedRecipes(viewModelRecipes: RecipesViewModel) {
        viewModelRecipes.getAllBookmarkedRecipes()
        viewModelRecipes.loadBookmark = { list, checkedState ->
            if (checkedState) {
                viewState.onSuccessLoad(list)
            } else if (!checkedState) {
                Log.d("Error", "error")
            }
        }
    }

    fun bookmarkRecipe(recipes: Recipes, viewModelRecipes: RecipesViewModel) {
        viewModelRecipes.bookmarkRecipe(recipes)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}