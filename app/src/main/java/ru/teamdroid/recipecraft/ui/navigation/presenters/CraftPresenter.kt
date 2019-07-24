package ru.teamdroid.recipecraft.ui.navigation.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.data.repository.RecipeRepository
import ru.teamdroid.recipecraft.ui.navigation.views.CraftRecipeView
import ru.teamdroid.recipecraft.util.schedulers.RunOn
import ru.teamdroid.recipecraft.util.schedulers.SchedulerType
import javax.inject.Inject

@InjectViewState
class CraftPresenter @Inject constructor(private var repository: RecipeRepository,
                                         @RunOn(SchedulerType.IO) private var ioScheduler: Scheduler,
                                         @RunOn(SchedulerType.UI) private var uiScheduler: Scheduler) : MvpPresenter<CraftRecipeView>() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var findRecipeDisposable: Disposable? = null

    fun loadIngredientsTitle() {
        compositeDisposable.add(repository.loadIngredientsTitle()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ viewState.setIngredientsTitle(it) }, { }))
    }

    fun findRecipeByIngredients(listIngredients: List<String>, count: Int) {
        if (findRecipeDisposable != null) findRecipeDisposable?.dispose()
        findRecipeDisposable = repository.findRecipeByIngredients(listIngredients, count)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ viewState.showRecipes(it) }, { })

        findRecipeDisposable?.let {
            compositeDisposable.add(it)
        }
    }

    fun bookmarkRecipe(recipe: Recipe) {
        compositeDisposable.add(repository.bookmark(recipe)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ viewState.showBookmarked(recipe.isBookmarked) }, { }))
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

}