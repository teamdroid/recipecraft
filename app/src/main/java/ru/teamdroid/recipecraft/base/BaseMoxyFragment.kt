package ru.teamdroid.recipecraft.base

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import ru.teamdroid.recipecraft.MainActivity
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.base.interfaces.OnBackPressedListener

abstract class BaseMoxyFragment : MvpAppCompatFragment(), OnBackPressedListener {

    protected val baseActivity: MainActivity
        get() = activity as MainActivity

    protected abstract val contentResId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(contentResId, container, false)

    protected fun setupToolbar(toolbar: Toolbar, needsUpButton: Boolean, title: String) {
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar)
        val actionBar = activity.supportActionBar
        if (actionBar != null) {
            setHasOptionsMenu(true)
            actionBar.setDisplayShowTitleEnabled(false)
            if (needsUpButton) {
                actionBar.setDisplayHomeAsUpEnabled(true)
                actionBar.setDisplayShowHomeEnabled(true)
                actionBar.setHomeButtonEnabled(true)
            }
        }

        toolbar.title = title
    }

    open fun showStartup() {
        AlertDialog.Builder(this.context)
                .setTitle(R.string.first_startup_title)
                .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
                .setMessage(R.string.first_startup_message)
                .show()
    }

    fun onBack() {
        fragmentManager.popBackStack()
    }

    override fun getContext(): Context {
        return super.getContext() as Context
    }

    override fun onBackPressed() = true
}