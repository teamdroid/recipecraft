package ru.teamdroid.recipecraft.ui.navigation.presenters

import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.data.repository.RecipeRepository
import ru.teamdroid.recipecraft.ui.navigation.contracts.DetailRecipeContract
import ru.teamdroid.recipecraft.util.schedulers.RunOn
import ru.teamdroid.recipecraft.util.schedulers.SchedulerType
import javax.inject.Inject

class DetailRecipePresenter @Inject constructor(private var repository: RecipeRepository,
                                                private var view: DetailRecipeContract.View,
                                                @RunOn(SchedulerType.IO) private var ioScheduler: Scheduler,
                                                @RunOn(SchedulerType.UI) private var uiScheduler: Scheduler) : DetailRecipeContract.Presenter {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun bookmarkRecipe(recipe: Recipe) {
        recipe.isBookmarked = !recipe.isBookmarked
        compositeDisposable.add(repository.bookmark(recipe)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ view.showBookmarked(recipe.isBookmarked) }, { }))
    }

    override fun onDetachView() { }

    override fun onAttachView() { }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}