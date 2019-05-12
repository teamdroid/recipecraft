package ru.teamdroid.recipecraft.ui.navigation.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.fragment_favorites.toolbar
import ru.teamdroid.recipecraft.BuildConfig
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.ui.base.BaseFragment

class AboutFragment : BaseFragment() {

    override val contentResId = R.layout.fragment_about

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(toolbar, true, getString(R.string.fragment_about_title))
        showNotificationsTextView.text = resources.getString(R.string.about_version_app, BuildConfig.VERSION_NAME)
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
        fun newInstance() = AboutFragment()
    }
}