package ru.teamdroid.recipecraft.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.teamdroid.recipecraft.api.RecipesApi
import ru.teamdroid.recipecraft.api.RequestInterface
import ru.teamdroid.recipecraft.room.entity.Recipe
import ru.teamdroid.recipecraft.views.RecipesView
import android.util.Log
import ru.teamdroid.recipecraft.fragments.NavigationFragment
import ru.teamdroid.recipecraft.room.entity.Ingredients
import ru.teamdroid.recipecraft.room.entity.Instruction
import ru.teamdroid.recipecraft.room.models.RecipesViewModel

@InjectViewState
class RecipesPresenter : MvpPresenter<RecipesView>() {

    private var compositeDisposable = CompositeDisposable()
    private lateinit var viewModelRecipes : RecipesViewModel

    fun onCreate(viewModelRecipes : RecipesViewModel) {
        this.viewModelRecipes = viewModelRecipes
    }

    fun loadRemote(lang: String) {
        compositeDisposable.add(RequestInterface.getRetrofitService(RecipesApi::class.java).getAllRecipes(lang)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(({ insertRecipes(it) }), ({ onFailure(it) })
                ))
    }

    fun getAllRecipe(viewModelRecipes: RecipesViewModel) {
        compositeDisposable.add(viewModelRecipes.getAllRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onSuccess(it) }, { onFailure(it) }))
    }

    fun bookmarkRecipe(recipe: Recipe) {
        compositeDisposable.add(viewModelRecipes.bookmarkRecipe(recipe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ Log.d(NavigationFragment.TAG, "Success") }, { }))
    }

    private fun insertRecipes(listRecipe : MutableList<Recipe>) {

        val listIngredients : MutableList<Ingredients> = arrayListOf()

        listRecipe.forEach { recipe ->
            recipe.ingredients.forEach {
                listIngredients.add(it)
            }
        }

        val disposableInsertRecipes = viewModelRecipes.insertRecipes(listRecipe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ Log.d(NavigationFragment.TAG, "Success") }, { })

        val disposableInsertIngredients = viewModelRecipes.insertIngredients(listIngredients)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ Log.d(NavigationFragment.TAG, "Success") }, { })

        compositeDisposable.addAll(disposableInsertIngredients, disposableInsertRecipes)
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