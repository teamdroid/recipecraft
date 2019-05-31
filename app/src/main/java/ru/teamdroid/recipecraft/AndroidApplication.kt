package ru.teamdroid.recipecraft

import android.app.Application
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import ru.teamdroid.recipecraft.data.components.DaggerRecipeRepositoryComponent
import ru.teamdroid.recipecraft.data.components.RecipeRepositoryComponent

class AndroidApplication : Application() {

    private lateinit var recipeRepositoryComponent: RecipeRepositoryComponent

    override fun onCreate() {
        super.onCreate()
        initializeDependencies()
        Stetho.initializeWithDefaults(this)
        //LeakCanary.install(this)
    }

    private fun initializeDependencies() {
        recipeRepositoryComponent = DaggerRecipeRepositoryComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    fun getRecipeRepositoryComponent(): RecipeRepositoryComponent = recipeRepositoryComponent

}
