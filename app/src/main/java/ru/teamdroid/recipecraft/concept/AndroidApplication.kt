package ru.teamdroid.recipecraft.concept

import android.app.Application
import com.facebook.stetho.Stetho
import ru.teamdroid.recipecraft.concept.data.DaggerRecipeRepositoryComponent
import ru.teamdroid.recipecraft.concept.data.RecipeRepositoryComponent

class AndroidApplication : Application() {

    private lateinit var repositoryComponent: RecipeRepositoryComponent

    override fun onCreate() {
        super.onCreate()
        initializeDependencies()
        Stetho.initializeWithDefaults(this)
    }

    private fun initializeDependencies() {
        repositoryComponent = DaggerRecipeRepositoryComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    fun getRecipeRepositoryComponent(): RecipeRepositoryComponent {
        return repositoryComponent
    }

}