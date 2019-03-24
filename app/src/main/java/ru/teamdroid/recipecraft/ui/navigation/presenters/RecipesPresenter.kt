package ru.teamdroid.recipecraft.ui.navigation.presenters

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.data.repository.RecipeRepository
import ru.teamdroid.recipecraft.ui.navigation.RecipesContract
import ru.teamdroid.recipecraft.util.schedulers.RunOn
import ru.teamdroid.recipecraft.util.schedulers.SchedulerType.IO
import ru.teamdroid.recipecraft.util.schedulers.SchedulerType.UI
import javax.inject.Inject

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

    fun bookmarkRecipe(recipe: Recipe) {
        repository.bookmark(recipe).subscribeOn(ioScheduler).observeOn(uiScheduler).subscribe()
    }

    private fun handleReturnedData(list: MutableList<Recipe>) {
        if (!list.isEmpty()) {
          view.showRecipes(list)
        } else {
        }
    }

    private fun handleError(error: Throwable) {
        Log.d("Error", error.message)
    }

    override fun showRecipes(recipes: MutableList<Recipe>) {

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