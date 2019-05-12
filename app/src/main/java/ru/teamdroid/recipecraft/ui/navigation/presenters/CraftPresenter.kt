package ru.teamdroid.recipecraft.ui.navigation.presenters

import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.data.repository.RecipeRepository
import ru.teamdroid.recipecraft.ui.navigation.contracts.CraftRecipeContract
import ru.teamdroid.recipecraft.util.schedulers.RunOn
import ru.teamdroid.recipecraft.util.schedulers.SchedulerType
import javax.inject.Inject

class CraftPresenter @Inject constructor(private var repository: RecipeRepository,
                                         private var view: CraftRecipeContract.View,
                                         @RunOn(SchedulerType.IO) private var ioScheduler: Scheduler,
                                         @RunOn(SchedulerType.UI) private var uiScheduler: Scheduler) : CraftRecipeContract.Presenter {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var findRecipeDisposable: Disposable? = null

    override fun loadIngredientsTitle() {
        compositeDisposable.add(repository.loadIngredientsTitle()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ view.setIngredientsTitle(it) }, { }))
    }

    override fun findRecipeByIngredients(listIngredients: List<String>, count: Int) {
        if (findRecipeDisposable != null) findRecipeDisposable?.dispose()
        findRecipeDisposable = repository.findRecipeByIngredients(listIngredients, count)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ view.showRecipes(it) }, { })

        findRecipeDisposable?.let {
            compositeDisposable.add(it)
        }
    }

    fun bookmarkRecipe(recipe: Recipe) {
        compositeDisposable.add(repository.bookmark(recipe)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ view.showBookmarked(recipe.isBookmarked) }, { }))
    }


    override fun onAttachView() { }

    override fun onDetachView() { }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

}