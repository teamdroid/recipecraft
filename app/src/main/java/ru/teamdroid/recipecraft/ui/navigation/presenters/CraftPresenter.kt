package ru.teamdroid.recipecraft.ui.navigation.presenters

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.data.repository.RecipeRepository
import ru.teamdroid.recipecraft.ui.navigation.CraftRecipeContract
import ru.teamdroid.recipecraft.util.schedulers.RunOn
import ru.teamdroid.recipecraft.util.schedulers.SchedulerType
import javax.inject.Inject

class CraftPresenter @Inject constructor(private var repository: RecipeRepository,
                                         private var view: CraftRecipeContract.View,
                                         @RunOn(SchedulerType.IO) private var ioScheduler: Scheduler,
                                         @RunOn(SchedulerType.UI) private var uiScheduler: Scheduler) : CraftRecipeContract.Presenter, LifecycleObserver {
    private var compositeDisposable: CompositeDisposable

    init {
        if (view is LifecycleOwner) {
            (view as LifecycleOwner).lifecycle.addObserver(this)
        }
        compositeDisposable = CompositeDisposable()
    }

    override fun loadIngredientsTitle() {
        compositeDisposable.add(repository.loadIngredientsTitle()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ view.setIngredientsTitle(it) }, { }))
    }

    override fun findRecipeByIngredients(listIngredients: List<String>, count: Int) {
        compositeDisposable.add(repository.findRecipeByIngredients(listIngredients, count)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ view.showRecipe(it) }, { }))
    }

    fun bookmarkRecipe(recipe: Recipe) {
        compositeDisposable.add(repository.bookmark(recipe)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ view.showBookmarked(recipe.isBookmarked) }, { }))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onAttach() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {
        compositeDisposable.clear()
    }

}