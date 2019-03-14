package ru.teamdroid.recipecraft.data.repository

import android.support.annotation.VisibleForTesting
import io.reactivex.Flowable
import ru.teamdroid.recipecraft.data.model.Recipes
import javax.inject.Inject

class RecipeRepository @Inject constructor(@Local private val localDataSource: RecipesDataSource,
                                           @Remote private val remoteDataSource: RecipesDataSource) : RecipesDataSource {

    @VisibleForTesting
    var caches: MutableList<Recipes> = arrayListOf()

    override fun loadRecipe(forceRemote: Boolean): Flowable<List<Recipes>> {
        return if (forceRemote) {
            refreshData()
        } else {
            if (caches.size > 0) {
                Flowable.just<List<Recipes>>(caches)
            } else {
                localDataSource.loadRecipe(false)
                        .take(1)
                        .flatMap { numberList -> Flowable.fromIterable(numberList) }
                        .toList().toFlowable()
                        .filter { !it.isEmpty() }.switchIfEmpty(refreshData())
            }
        }
    }


    /**
     * Fetches data from remote source.
     * Save it into both local database and cache.
     *
     * @return the Flowable of newly fetched data.
     */
    private fun refreshData(): Flowable<List<Recipes>> {
        return remoteDataSource.loadRecipe(true).doOnNext{
            caches.clear()
            localDataSource.clearData()
        }.flatMap { Flowable.fromIterable(it) }.doOnNext {
            caches.add(it)
            localDataSource.addRecipe(it)
        }.toList().toFlowable()
    }


    /**
     * Loads a question by its question id.
     *
     * @param questionId question's id.
     * @return a corresponding question from cache.
     */
//    fun getQuestion(questionId: Long): Flowable<Question> {
//        return Flowable.fromIterable<Question>(caches).filter { question -> question.getId() === questionId }
//    }
//

    override fun addRecipe(recipes: Recipes) {
        throw UnsupportedOperationException("Unsupported operation")
    }

    override fun clearData() {
        caches.clear()
        localDataSource.clearData()
    }
}