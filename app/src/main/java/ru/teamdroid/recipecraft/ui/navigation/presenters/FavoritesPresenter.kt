package ru.teamdroid.recipecraft.ui.navigation.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.data.repository.RecipeRepository
import ru.teamdroid.recipecraft.ui.navigation.views.FavoritesView
import ru.teamdroid.recipecraft.util.schedulers.RunOn
import ru.teamdroid.recipecraft.util.schedulers.SchedulerType
import javax.inject.Inject

@InjectViewState
class FavoritesPresenter @Inject constructor(private var repository: RecipeRepository,
                                             @RunOn(SchedulerType.IO) private var ioScheduler: Scheduler,
                                             @RunOn(SchedulerType.UI) private var uiScheduler: Scheduler) : MvpPresenter<FavoritesView>() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadRecipes() {
        compositeDisposable.add(repository.loadBookmarkedRecipes()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ handleSuccess(it) }, { handleError(it) }, { }))
    }

    fun bookmarkRecipe(recipe: Recipe) {
        compositeDisposable.add(repository.bookmark(recipe)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ }, { handleError(it) }))
    }

    private fun handleSuccess(list: MutableList<Recipe>) {
        viewState.showRecipes(list)
    }

    private fun handleError(error: Throwable) {
        Log.d("Error", error.message)
    }

    override fun destroyView(view: FavoritesView?) {
        compositeDisposable.dispose()
        super.destroyView(view)
    }

}