package ru.teamdroid.recipecraft.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.teamdroid.recipecraft.AndroidApplication
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.data.components.RecipeRepositoryComponent
import ru.teamdroid.recipecraft.ui.base.BaseMoxyActivity
import ru.teamdroid.recipecraft.ui.base.Constants
import ru.teamdroid.recipecraft.ui.base.extensions.getProperty
import ru.teamdroid.recipecraft.ui.base.extensions.setProperty
import ru.teamdroid.recipecraft.ui.navigation.fragments.NavigationFragment

class MainActivity : BaseMoxyActivity() {

    val recipeRepositoryComponent: RecipeRepositoryComponent
        get() = (application as AndroidApplication).getRecipeRepositoryComponent()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

    companion object {
        var isFirstStartup: Boolean = false
    }
}