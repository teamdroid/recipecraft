package ru.teamdroid.recipecraft

import android.app.Application
import com.facebook.stetho.Stetho
import ru.teamdroid.recipecraft.data.DaggerRecipeRepositoryComponent
import ru.teamdroid.recipecraft.data.RecipeRepositoryComponent

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