package ru.teamdroid.recipecraft.ui.navigation.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.data.repository.RecipeRepository
import ru.teamdroid.recipecraft.ui.navigation.views.DetailRecipeView
import ru.teamdroid.recipecraft.util.schedulers.RunOn
import ru.teamdroid.recipecraft.util.schedulers.SchedulerType
import javax.inject.Inject

@InjectViewState
class DetailRecipePresenter @Inject constructor(private var repository: RecipeRepository,
                                                @RunOn(SchedulerType.IO) private var ioScheduler: Scheduler,
                                                @RunOn(SchedulerType.UI) private var uiScheduler: Scheduler) : MvpPresenter<DetailRecipeView>() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun bookmarkRecipe(recipe: Recipe) {
        recipe.isBookmarked = !recipe.isBookmarked
        compositeDisposable.add(repository.bookmark(recipe)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ viewState.showBookmarked(recipe.isBookmarked) }, { }))
    }

    override fun destroyView(view: DetailRecipeView?) {
        super.destroyView(view)
        compositeDisposable.dispose()
    }
}