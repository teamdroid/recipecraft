package ru.teamdroid.recipecraft.ui.navigation.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.fragment_settings.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.ui.base.BaseMoxyFragment
import ru.teamdroid.recipecraft.ui.base.Constants
import ru.teamdroid.recipecraft.ui.base.extensions.getProperty
import ru.teamdroid.recipecraft.ui.base.extensions.setProperty

class SettingsFragment : BaseMoxyFragment() {

    override val contentResId = R.layout.fragment_settings

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       
        setupToolbar(toolbar, true, getString(R.string.fragment_settings_application_title))

        with(notificationSwitch) {
            isChecked = context.getProperty(Constants.NOTIFICATION, 1) == 1
            jumpDrawablesToCurrentState()
        }

        constraintLayout.setOnClickListener {
            with(notificationSwitch) {
                isChecked = if (isChecked) {
                    context.setProperty(Constants.NOTIFICATION, 0)
                    false
                } else {
                    context.setProperty(Constants.NOTIFICATION, 1)
                    true
                }
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}
