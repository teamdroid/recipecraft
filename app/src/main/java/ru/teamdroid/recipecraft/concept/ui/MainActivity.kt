package ru.teamdroid.recipecraft.concept.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpAppCompatActivity
import com.facebook.stetho.Stetho
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.base.Constants
import ru.teamdroid.recipecraft.base.extensions.getProperty
import ru.teamdroid.recipecraft.base.extensions.setProperty
import ru.teamdroid.recipecraft.concept.AndroidApplication
import ru.teamdroid.recipecraft.concept.data.RecipeRepositoryComponent
import ru.teamdroid.recipecraft.concept.ui.base.BaseActivity
import ru.teamdroid.recipecraft.concept.ui.navigation.fragments.NavigationFragment

class MainActivity : BaseActivity() {

    private var isFirstStartup: Boolean = false

    val recipeRepositoryComponent: RecipeRepositoryComponent
        get() = (application as AndroidApplication).getRecipeRepositoryComponent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Stetho.initializeWithDefaults(this)

        with(baseContext) {
            if (getProperty(Constants.STARTUP, 1) == 1) isFirstStartup = true
            setProperty(Constants.STARTUP, getProperty(Constants.STARTUP, 1) + 1)
        }

        replaceFragment(NavigationFragment.newInstance(isFirstStartup), NavigationFragment.TAG)
    }


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1)
            supportFragmentManager.popBackStack()
        else
            finish()
    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainContainer, fragment).addToBackStack(tag)
        }.commit()
    }
}