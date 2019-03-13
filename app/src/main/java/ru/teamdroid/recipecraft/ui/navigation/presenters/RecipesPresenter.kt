package ru.teamdroid.recipecraft.ui.navigation.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.Scheduler
import ru.teamdroid.recipecraft.data.model.Recipes
import ru.teamdroid.recipecraft.repository.RecipeRepository
import ru.teamdroid.recipecraft.ui.navigation.RecipesContract
import ru.teamdroid.recipecraft.util.schedulers.RunOn

import javax.inject.Inject

import ru.teamdroid.recipecraft.util.schedulers.SchedulerType.IO
import ru.teamdroid.recipecraft.util.schedulers.SchedulerType.UI

class RecipesPresenter @Inject constructor(private var repository: RecipeRepository,
                                           private var view: RecipesContract.View,
                                           @RunOn(IO) private var ioScheduler: Scheduler,
                                           @RunOn(UI) private var uiScheduler: Scheduler) : RecipesContract.View, LifecycleObserver {

    init {
        if (view is LifecycleOwner) {
            (view as LifecycleOwner).lifecycle.addObserver(this)
        }
    }

    fun loadRemote(lang: String) {
//        viewModelRecipes.getRemoteData(lang)
//        viewModelRecipes.remoteLoadListener = { checkedState ->
//            if (checkedState) {
//                getAllRecipe()
//            } else if (!checkedState) {
//                Log.d("Error", "Error")
//            }
//        }
    }

    fun getAllRecipe() {
//        with(viewModelRecipes) {
//            getAllRecipes()
//            localLoadListener = { listRecipes, checkedState ->
//                if (checkedState) {
//                  //  viewState.onSuccessLoad(listRecipes)
//                } else if (!checkedState) {
//
//                }
//            }
//        }
    }

    fun bookmarkRecipe(recipes: Recipes) {
        //viewModelRecipes.bookmarkRecipe(recipes)
    }

    override fun showRecipes(recipes: MutableList<Recipes>) {}

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onAttach() {
        //loadQuestions(false)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onDetach() {
        // Clean up any no-longer-use resources here
        //disposeBag.clear()
    }

}