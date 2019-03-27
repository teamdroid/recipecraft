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
import ru.teamdroid.recipecraft.ui.navigation.FavoritesContract
import ru.teamdroid.recipecraft.util.schedulers.RunOn
import ru.teamdroid.recipecraft.util.schedulers.SchedulerType
import javax.inject.Inject

class FavoritesPresenter @Inject constructor(private var repository: RecipeRepository,
                                             private var view: FavoritesContract.View,
                                             @RunOn(SchedulerType.IO) private var ioScheduler: Scheduler,
                                             @RunOn(SchedulerType.UI) private var uiScheduler: Scheduler) : FavoritesContract.Presenter, LifecycleObserver {

    private var compositeDisposable: CompositeDisposable

    init {
        if (view is LifecycleOwner) {
            (view as LifecycleOwner).lifecycle.addObserver(this)
        }
        compositeDisposable = CompositeDisposable()
    }

    override fun loadRecipes() {
        compositeDisposable.add(repository.loadRecipe(false)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ handleReturnedData(it) }, { handleError(it) }, { }))
    }

    private fun handleReturnedData(list: MutableList<Recipe>) {
        view.showRecipes(list)
    }

    fun bookmarkRecipe(recipe: Recipe) {
        compositeDisposable.add(repository.bookmark(recipe)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ }, { }))
    }

    private fun handleError(error: Throwable) {
        Log.d("Error4", error.message)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onAttach() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {
        compositeDisposable.clear()
    }


}