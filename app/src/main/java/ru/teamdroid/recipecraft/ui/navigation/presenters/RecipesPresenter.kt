package ru.teamdroid.recipecraft.ui.navigation.presenters

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import ru.teamdroid.recipecraft.data.model.Recipes
import ru.teamdroid.recipecraft.data.repository.RecipeRepository
import ru.teamdroid.recipecraft.ui.navigation.RecipesContract
import ru.teamdroid.recipecraft.util.schedulers.RunOn

import javax.inject.Inject

import ru.teamdroid.recipecraft.util.schedulers.SchedulerType.IO
import ru.teamdroid.recipecraft.util.schedulers.SchedulerType.UI

class RecipesPresenter @Inject constructor(private var repository: RecipeRepository,
                                           private var view: RecipesContract.View,
                                           @RunOn(IO) private var ioScheduler: Scheduler,
                                           @RunOn(UI) private var uiScheduler: Scheduler) : RecipesContract.Presenter, LifecycleObserver {

    private var compositeDisposable: CompositeDisposable

    init {
        if (view is LifecycleOwner) {
            (view as LifecycleOwner).lifecycle.addObserver(this)
        }
        compositeDisposable = CompositeDisposable()
    }


    override fun loadRecipes(onlineRequired: Boolean) {
        val disposable = repository.loadRecipe(false)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ handleReturnedData(it) }, { handleError(it) }, {  })
        compositeDisposable.add(disposable)
    }

    fun bookmarkRecipe(recipes: Recipes) {
        //viewModelRecipes.bookmarkRecipe(recipes)
    }


    private fun handleReturnedData(list: List<Recipes>) {
        if (!list.isEmpty()) {
          view.showRecipes(list)
        } else {

        }
    }

    private fun handleError(error: Throwable) {
        Log.d("Error", error.message)
    }

    override fun showRecipes(recipes: List<Recipes>) {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onAttach() {
        //loadRecipes("ru")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onDetach() {
        compositeDisposable.clear()
    }

}