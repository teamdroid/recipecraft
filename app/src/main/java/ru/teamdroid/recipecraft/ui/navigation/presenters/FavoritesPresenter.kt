package ru.teamdroid.recipecraft.ui.navigation.presenters

import android.util.Log
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.data.repository.RecipeRepository
import ru.teamdroid.recipecraft.ui.navigation.contracts.FavoritesContract
import ru.teamdroid.recipecraft.util.schedulers.RunOn
import ru.teamdroid.recipecraft.util.schedulers.SchedulerType
import javax.inject.Inject

class FavoritesPresenter @Inject constructor(private var repository: RecipeRepository,
                                             private var view: FavoritesContract.View,
                                             @RunOn(SchedulerType.IO) private var ioScheduler: Scheduler,
                                             @RunOn(SchedulerType.UI) private var uiScheduler: Scheduler) : FavoritesContract.Presenter {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun loadRecipes() {
        compositeDisposable.add(repository.loadBookmarkedRecipes()
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
        Log.d("Error", error.message)
    }

    override fun onAttachView() { }

    override fun onDetachView() { }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

}