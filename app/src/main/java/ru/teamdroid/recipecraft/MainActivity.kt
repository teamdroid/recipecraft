package ru.teamdroid.recipecraft

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpAppCompatActivity
import dagger.android.AndroidInjection
import ru.teamdroid.recipecraft.base.Constants
import ru.teamdroid.recipecraft.base.extensions.getProperty
import ru.teamdroid.recipecraft.base.extensions.setProperty
import ru.teamdroid.recipecraft.fragments.NavigationFragment

class MainActivity : MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidInjection.inject(this)

        baseContext.setProperty(Constants.STARTUP, baseContext.getProperty(Constants.STARTUP, 1) + 1)

        checkFirstStartup()
        replaceFragment(NavigationFragment.newInstance())
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainContainer, fragment).addToBackStack(null)
        }.commit()
    }

    private fun checkFirstStartup() {
        if (baseContext.getProperty(Constants.STARTUP, 1) == 1) {
            AlertDialog.Builder(this)
                    .setTitle(R.string.first_startup_title)
                    .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
                    .setMessage(R.string.first_startup_message)
                    .show()
        }
    }
}