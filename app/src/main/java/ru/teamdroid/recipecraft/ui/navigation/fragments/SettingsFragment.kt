package ru.teamdroid.recipecraft.ui.navigation.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.fragment_favorites.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.ui.base.BaseFragment

class SettingsFragment : BaseFragment() {

    override val contentResId = R.layout.fragment_settings

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(toolbar, true, getString(R.string.fragment_settings_application_title))
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