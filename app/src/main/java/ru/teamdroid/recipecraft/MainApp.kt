package ru.teamdroid.recipecraft

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import ru.teamdroid.recipecraft.di.components.AppComponent
import ru.teamdroid.recipecraft.di.components.DaggerAppComponent
import ru.teamdroid.recipecraft.di.modules.AppModule
import javax.inject.Inject

class MainApp : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        // TODO: DEPRECATED
        appComponent = DaggerAppComponent.builder().appModule(AppModule(applicationContext)).build()
        appComponent.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}
