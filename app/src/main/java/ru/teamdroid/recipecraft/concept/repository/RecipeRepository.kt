package ru.teamdroid.recipecraft.concept.repository

import android.support.annotation.VisibleForTesting
import io.reactivex.Flowable
import ru.teamdroid.recipecraft.concept.data.model.Recipes
import javax.inject.Inject

class RecipeRepository @Inject constructor(@param:Local private val localDataSource: RecipesDataSource,
                                           @param:Remote private val remoteDataSource: RecipesDataSource) : RecipesDataSource {

    override fun loadRecipe(forceRemote: Boolean): Flowable<MutableList<Recipes>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @VisibleForTesting
    var caches: MutableList<Recipes> = arrayListOf()


//    fun loadQuestions(forceRemote: Boolean): Flowable<List<Question>> {
//        if (forceRemote) {
//            return refreshData()
//        } else {
//            if (caches.size > 0) {
//                // if cache is available, return it immediately
//                return Flowable.just<List<Question>>(caches)
//            } else {
//                // else return data from local storage
//                return localDataSource.loadQuestions(false)
//                        .take(1)
//                        .flatMap(???({ Flowable.fromIterable(it) }))
//                .doOnNext { question -> caches.add(question) }
//                        .toList()
//                        .toFlowable()
//                        .filter({ list -> !list.isEmpty() })
//                        .switchIfEmpty(
//                                refreshData()) // If local data is empty, fetch from remote source instead.
//            }
//        }
//    }

    /**
     * Fetches data from remote source.
     * Save it into both local database and cache.
     *
     * @return the Flowable of newly fetched data.
     */
//    internal fun refreshData(): Flowable<List<Question>> {
//        return remoteDataSource.loadQuestions(true).doOnNext({ list ->
//            // Clear cache
//            caches.clear()
//            // Clear data in local storage
//            localDataSource.clearData()
//        }).flatMap(???({ Flowable.fromIterable(it) })).doOnNext({ question ->
//            caches.add(question)
//            localDataSource.addQuestion(question)
//        }).toList().toFlowable()
//    }

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
//    fun addQuestion(question: Question) {
//        //Currently, we do not need this.
//        throw UnsupportedOperationException("Unsupported operation")
//    }

    override fun clearData() {
        caches.clear()
        localDataSource.clearData()
    }
}