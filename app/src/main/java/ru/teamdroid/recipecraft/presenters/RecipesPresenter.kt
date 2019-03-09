package ru.teamdroid.recipecraft.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.teamdroid.recipecraft.room.entity.Recipes
import ru.teamdroid.recipecraft.views.RecipesView
import android.util.Log
import ru.teamdroid.recipecraft.room.models.RecipesViewModel

@InjectViewState
class RecipesPresenter : MvpPresenter<RecipesView>() {

    private lateinit var viewModelRecipes: RecipesViewModel

    fun onCreate(viewModelRecipes: RecipesViewModel) {
        this.viewModelRecipes = viewModelRecipes
    }

    fun loadRemote(lang: String) {
        viewModelRecipes.getRemoteData(lang)
        viewModelRecipes.remoteLoadListener = { checkedState ->
            if (checkedState) {
                getAllRecipe()
            } else if (!checkedState) {
                Log.d("Error", "Error")
            }
        }
    }

    fun getAllRecipe() {
        with(viewModelRecipes) {
            getAllRecipes()
            localLoadListener = { listRecipes, checkedState ->
                if (checkedState) {
                    viewState.onSuccessLoad(listRecipes)
                } else if (!checkedState) {

                }
            }
        }
    }

    fun bookmarkRecipe(recipes: Recipes) {
        viewModelRecipes.bookmarkRecipe(recipes)
    }
}