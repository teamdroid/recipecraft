package ru.teamdroid.recipecraft.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.teamdroid.recipecraft.api.RecipesApi
import ru.teamdroid.recipecraft.api.RequestInterface
import ru.teamdroid.recipecraft.interactors.RecipeInteractor
import ru.teamdroid.recipecraft.room.entity.Recipe
import ru.teamdroid.recipecraft.views.RecipesView

@InjectViewState
class RecipesPresenter : MvpPresenter<RecipesView>() {

    private val recipeInteractor = RecipeInteractor()

    private var compositeDisposable = CompositeDisposable()

    fun loadRemote(lang: String) {
        compositeDisposable.add(RequestInterface.getRetrofitService(RecipesApi::class.java)
                .getAllRecipes(lang)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(({ onSuccess(it) }), ({ onFailure(it) })
                ))
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    private fun onSuccess(list: MutableList<Recipe>) {
        viewState.onSuccessLoad(list)
    }

    private fun onFailure(error: Throwable) {
        viewState.onErrorLoad(error)
    }

}