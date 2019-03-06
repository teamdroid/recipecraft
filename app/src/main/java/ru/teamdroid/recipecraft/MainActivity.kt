package ru.teamdroid.recipecraft

import android.os.Bundle
import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpAppCompatActivity
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjection
import ru.teamdroid.recipecraft.base.Constants
import ru.teamdroid.recipecraft.base.extensions.getProperty
import ru.teamdroid.recipecraft.base.extensions.setProperty
import ru.teamdroid.recipecraft.fragments.NavigationFragment

class MainActivity : MvpAppCompatActivity() {

    private var isFirstStartup: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidInjection.inject(this)
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