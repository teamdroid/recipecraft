package ru.teamdroid.recipecraft.fragments

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_report.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.base.BaseMoxyFragment

class ReportFragment : BaseMoxyFragment() {

    override val contentResId = R.layout.fragment_report

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(toolbar, true, getString(R.string.fragment_report_title))

        submitFeedbackButton.setOnClickListener {
            if (nameEditText.text.isNotBlank() && emailEditText.text.isNotBlank() && feedbackEditText.text.isNotBlank()) {
                Toast.makeText(context,R.string.thanks_feedback, Toast.LENGTH_LONG).show() // todo : created presenter and interactor
            } else {
                val toast = Toast.makeText(context, R.string.error_input_text, Toast.LENGTH_SHORT)
                toast.view.background.setColorFilter(ContextCompat.getColor(context, R.color.textRed), PorterDuff.Mode.SRC_IN)
                toast.show()
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                fragmentManager.popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        fun newInstance() = ReportFragment()
    }
}