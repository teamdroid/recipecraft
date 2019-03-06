package ru.teamdroid.recipecraft.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.teamdroid.recipecraft.api.RecipesApi
import ru.teamdroid.recipecraft.api.RequestInterface
import ru.teamdroid.recipecraft.room.entity.Recipes
import ru.teamdroid.recipecraft.views.RecipesView
import android.util.Log
import ru.teamdroid.recipecraft.fragments.NavigationFragment
import ru.teamdroid.recipecraft.room.entity.Ingredients
import ru.teamdroid.recipecraft.room.entity.RecipeIngredients
import ru.teamdroid.recipecraft.room.models.IngredientsViewModel
import ru.teamdroid.recipecraft.room.models.RecipesViewModel

@InjectViewState
class RecipesPresenter : MvpPresenter<RecipesView>() {

    private var compositeDisposable = CompositeDisposable()
    private lateinit var viewModelRecipes: RecipesViewModel
    private lateinit var viewModelIngredients: IngredientsViewModel

    private var listIngredients: MutableList<Ingredients> = arrayListOf()
    private var listRecipeIngredients: MutableList<RecipeIngredients> = arrayListOf()

    private var listRecipes: MutableList<Recipes> = arrayListOf()

    fun onCreate(viewModelRecipes: RecipesViewModel, viewModelIngredients: IngredientsViewModel) {
        this.viewModelRecipes = viewModelRecipes
        this.viewModelIngredients = viewModelIngredients
    }

    fun loadRemote(lang: String) {
        compositeDisposable.add(RequestInterface.getRetrofitService(RecipesApi::class.java).getAllRecipes(lang)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(({ insertRecipes(it) }), ({ onFailure(it) })))
    }

    fun getAllRecipe() {
        compositeDisposable.add(viewModelRecipes.getAllRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listRecipes = it
                }, {
                    onFailure(it)
                }))

        if (listRecipeIngredients.isEmpty()) getRecipeIngredients()
    }

    fun bookmarkRecipe(recipes: Recipes) {
        compositeDisposable.add(viewModelRecipes.bookmarkRecipe(recipes)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ Log.d(NavigationFragment.TAG, "Success") }, { }))
    }

    private fun insertRecipes(listRecipes: MutableList<Recipes>) {
        listRecipes.forEach { recipe ->
            recipe.ingredients.forEach {
                listIngredients.add(Ingredients(it.idIngredient, it.title, it.amount))
                listRecipeIngredients.add(RecipeIngredients(id = it.id, idRecipe = recipe.idRecipe, idIngredient = it.idIngredient, amount = it.amount))
            }
        }

        compositeDisposable.add(viewModelRecipes.insertRecipes(listRecipes)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ insertIngredients(listIngredients) }, { }))
    }

    private fun insertRecipeIngredients(listRecipeIngredients: MutableList<RecipeIngredients>) {
        compositeDisposable.add(viewModelRecipes.insertRecipeIngredients(listRecipeIngredients)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ getAllRecipe() }, { Log.d("Error", it.message) }))
    }

    private fun insertIngredients(listIngredients: MutableList<Ingredients>) {
        compositeDisposable.add(viewModelRecipes.insertIngredients(listIngredients)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ insertRecipeIngredients(listRecipeIngredients) }, { Log.d("Error", it.message) }))
    }

    private fun getRecipeIngredients() {
        compositeDisposable.add(viewModelIngredients.getAllRecipeIngredients()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listRecipes.forEach { recipe ->
                        it.forEach {
                            if (recipe.idRecipe == it.idRecipe) {
                                recipe.ingredients.add(Ingredients(idIngredient = it.idIngredient, title = it.title))
                            }
                        }
                    }
                    onSuccess()
                }, { Log.d("Error", it.stackTrace.toString()) }))
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    private fun onSuccess() {
        viewState.onSuccessLoad(listRecipes)
    }

    private fun onFailure(error: Throwable) {
        viewState.onErrorLoad(error)
    }
}