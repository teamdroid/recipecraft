package ru.teamdroid.recipecraft.ui.navigation.presenters

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.data.repository.RecipeRepository
import ru.teamdroid.recipecraft.ui.navigation.contracts.RecipesContract
import ru.teamdroid.recipecraft.ui.navigation.fragments.RecipesFragment
import ru.teamdroid.recipecraft.util.schedulers.RunOn
import ru.teamdroid.recipecraft.util.schedulers.SchedulerType.IO
import ru.teamdroid.recipecraft.util.schedulers.SchedulerType.UI
import javax.inject.Inject

class RecipesPresenter @Inject constructor(private var repository: RecipeRepository,
                                           private var view: RecipesContract.View,
                                           @RunOn(IO) private var ioScheduler: Scheduler,
                                           @RunOn(UI) private var uiScheduler: Scheduler) : RecipesContract.Presenter, LifecycleObserver {

    private var compositeDisposable: CompositeDisposable
    private var recipeDisposable: Disposable? = null

    init {
        if (view is LifecycleOwner) {
            (view as LifecycleOwner).lifecycle.addObserver(this)
        }
        compositeDisposable = CompositeDisposable()
    }

    override fun loadRecipes(onlineRequired: Boolean, sortType: String) {


        Log.d("wow", "heck $sortType")

        if (recipeDisposable != null) recipeDisposable?.dispose()
        recipeDisposable = repository.loadRecipes(onlineRequired, sortType)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ handleReturnedData(it, onlineRequired, sortType) }, { handleError(it) }, { })
        recipeDisposable?.let { compositeDisposable.add(it) }
    }

    fun bookmarkRecipe(recipe: Recipe) {
        compositeDisposable.add(repository.bookmark(recipe)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ view.showBookmarked(recipe.isBookmarked) }, { }))
    }

    private fun handleReturnedData(list: MutableList<Recipe>, onlineRequired: Boolean, sortType: String) {
        if (list.isNotEmpty() || onlineRequired) view.showRecipes(list) else loadRecipes(true, sortType)
    }

    private fun handleError(error: Throwable) {
        Log.d(RecipesFragment.TAG, error.message)
        view.showRecipes(arrayListOf())
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onAttach() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {
        compositeDisposable.dispose()
    }

}