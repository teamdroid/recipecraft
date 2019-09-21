package ru.teamdroid.recipecraft

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import ru.teamdroid.recipecraft.data.components.DaggerRecipeRepositoryComponent
import ru.teamdroid.recipecraft.data.components.RecipeRepositoryComponent
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.soloader.SoLoader
import com.facebook.flipper.plugins.leakcanary.LeakCanaryFlipperPlugin
import com.facebook.flipper.plugins.leakcanary.RecordLeakService

class AndroidApplication : Application() {

    private lateinit var recipeRepositoryComponent: RecipeRepositoryComponent

    override fun onCreate() {
        super.onCreate()
        initializeDependencies()
        SoLoader.init(this, false)
        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            val client = AndroidFlipperClient.getInstance(this)
            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
            client.addPlugin(DatabasesFlipperPlugin(this))
            client.addPlugin(LeakCanaryFlipperPlugin())

            LeakCanary.refWatcher(this)
                    .listenerServiceClass(RecordLeakService::class.java)
                    .buildAndInstall()

            client.start()
        }
    }

    private fun initializeDependencies() {
        recipeRepositoryComponent = DaggerRecipeRepositoryComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    fun getRecipeRepositoryComponent(): RecipeRepositoryComponent = recipeRepositoryComponent

}
