package ru.teamdroid.recipecraft.room.models

import android.arch.lifecycle.ViewModel
import android.util.Log

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.teamdroid.recipecraft.api.RecipesApi
import ru.teamdroid.recipecraft.api.RequestInterface
import ru.teamdroid.recipecraft.room.dao.RecipesDao
import ru.teamdroid.recipecraft.room.entity.Ingredients
import ru.teamdroid.recipecraft.room.entity.Recipes
import ru.teamdroid.recipecraft.room.entity.RecipeIngredients

class RecipesViewModel(private val dataSource: RecipesDao) : ViewModel() {

    private var compositeDisposable = CompositeDisposable()
    private var listRecipes: MutableList<Recipes> = arrayListOf()
    private var listBookmarkRecipes: MutableList<Recipes> = arrayListOf()

    var remoteLoadListener: ((checkedState: Boolean) -> Unit)? = null
    var localLoadListener: ((listRecipe: MutableList<Recipes>, checkedState: Boolean) -> Unit)? = null
    var loadBookmark: ((listRecipe: MutableList<Recipes>, checkedState: Boolean) -> Unit)? = null

    fun getRemoteData(lang: String) {
        compositeDisposable.add(RequestInterface.getRetrofitService(RecipesApi::class.java).getAllRecipes(lang)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(({ insertRecipes(it) }), ({ Log.d("error", "error") })))
    }

    fun getAllBookmarkedRecipes() {
        compositeDisposable.add(dataSource.getAllBookmarkedRecipes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(({
                    listRecipes.forEach { listBookmarkRecipes.add(it) }
                    loadBookmark?.invoke(listBookmarkRecipes, true)
                }), ({ Log.d("error", "error") })))
    }

    private fun insertRecipes(listRecipes: MutableList<Recipes>) {
        compositeDisposable.add(Completable.fromAction { dataSource.insertRecipes(listRecipes) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(({ insertIngredients(listRecipes) }), ({ Log.d("error", "error") })))
    }

    private fun insertIngredients(listRecipes: MutableList<Recipes>) {
        val listIngredients: MutableList<Ingredients> = arrayListOf()

        listRecipes.forEach { recipe ->
            recipe.ingredients.forEach { listIngredients.add(Ingredients(it.idIngredient, it.title, it.amount)) }
        }

        compositeDisposable.add(Completable.fromAction { dataSource.insertIngredients(listIngredients) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(({ insertRecipeIngredients(listRecipes) }), ({ Log.d("error", "error") })))
    }

    private fun insertRecipeIngredients(listRecipes: MutableList<Recipes>) {

        val listRecipeIngredients: MutableList<RecipeIngredients> = arrayListOf()

        listRecipes.forEach { recipe ->
            recipe.ingredients.forEach {
                listRecipeIngredients.add(RecipeIngredients(id = it.id, idRecipe = recipe.idRecipe, idIngredient = it.idIngredient, amount = it.amount))
            }
        }

        compositeDisposable.add(Completable.fromAction { dataSource.insertRecipeIngredients(listRecipeIngredients) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(({ remoteLoadListener?.invoke(true) }), ({ Log.d("error", "error") })))
    }

    fun bookmarkRecipe(recipes: Recipes) {
        recipes.isBookmarked = !recipes.isBookmarked
        compositeDisposable.add(Completable.fromAction { dataSource.updateRecipe(recipes) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(({ remoteLoadListener?.invoke(true) }), ({ Log.d("error", "error") })))
    }

    fun getAllRecipes() {
        compositeDisposable.add(dataSource.getAllRecipes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(({
                    listRecipes = it
                    listRecipes.forEach { recipe ->
                        if (recipe.isBookmarked) listBookmarkRecipes.add(recipe) else listBookmarkRecipes.remove(recipe)
                    }
                    getRecipeIngredients()
                }), ({ localLoadListener?.invoke(arrayListOf(), false) })))
    }

    private fun getRecipeIngredients() {
        compositeDisposable.add(dataSource.getAllIngredientsById()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listRecipes.forEach { recipe ->
                        recipe.ingredients.clear()
                        it.forEach {
                            if (recipe.idRecipe == it.idRecipe) {
                                recipe.ingredients.add(Ingredients(idIngredient = it.idIngredient, title = it.title))
                            }
                        }
                    }
                    localLoadListener?.invoke(listRecipes, true)
                }, {
                    localLoadListener?.invoke(arrayListOf(), false)
                }))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}