package ru.teamdroid.recipecraft.ui.navigation.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.data.repository.RecipeRepository
import ru.teamdroid.recipecraft.ui.navigation.fragments.RecipesFragment
import ru.teamdroid.recipecraft.ui.navigation.views.RecipeView
import ru.teamdroid.recipecraft.util.schedulers.RunOn
import ru.teamdroid.recipecraft.util.schedulers.SchedulerType.IO
import ru.teamdroid.recipecraft.util.schedulers.SchedulerType.UI
import javax.inject.Inject

@InjectViewState
class RecipesPresenter @Inject constructor(private var repository: RecipeRepository,
                                           @RunOn(IO) private var ioScheduler: Scheduler,
                                           @RunOn(UI) private var uiScheduler: Scheduler) : MvpPresenter<RecipeView>() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var recipeDisposable: Disposable? = null

    fun loadRecipes(onlineRequired: Boolean, sortType: String, offset: Int) {
        //if (recipeDisposable != null) recipeDisposable?.dispose()
        recipeDisposable = repository.loadRecipes(sortType, offset)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ handleSuccess(it, onlineRequired, sortType) }, { handleError(it) }, { })
        recipeDisposable?.let { compositeDisposable.add(it) }

    }

    fun bookmarkRecipe(recipe: Recipe) {
        compositeDisposable.add(repository.bookmark(recipe)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ viewState.showBookmarked(recipe.isBookmarked) }, { }))
    }

    private fun handleSuccess(list: MutableList<Recipe>, onlineRequired: Boolean, sortType: String) {
        if (list.isNotEmpty() || onlineRequired) viewState.showRecipes(list) else loadRecipes(true, sortType, 0)
    }


    private fun countHandleSuccess(count: Int, offset: Int, sortType: String) {
        if (offset < count)
            loadRecipes(false, sortType, offset)
    }

    fun loadCount(offset: Int, sortType: String) {
        recipeDisposable = repository.getRecipesCount().subscribeOn(ioScheduler).observeOn(uiScheduler)
                .subscribe({ countHandleSuccess(it, offset, sortType) }, { handleError(it) })
    }

    private fun handleError(error: Throwable) {
        Log.d(RecipesFragment.TAG, error.message)
        viewState.showRecipes(arrayListOf())
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

}