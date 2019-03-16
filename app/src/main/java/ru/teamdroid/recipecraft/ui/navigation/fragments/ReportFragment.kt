package ru.teamdroid.recipecraft.ui.navigation.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_report.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.ui.base.BaseFragment

class ReportFragment : BaseFragment() {

    override val contentResId = R.layout.fragment_report

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(toolbar, true, getString(R.string.fragment_report_title))
        submitFeedbackButton.setOnClickListener {
            if (nameEditText.text.isNotBlank() && emailEditText.text.isNotBlank() && feedbackEditText.text.isNotBlank()) {
                progressBar.visibility = View.VISIBLE
                //presenter.sendReportMessage(nameEditText.text.toString(), emailEditText.text.toString(), feedbackEditText.text.toString())
            } else {
                Toast.makeText(context, R.string.error_input_text, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onSuccess() {
        progressBar.visibility = View.GONE
        Toast.makeText(context, R.string.thanks_feedback, Toast.LENGTH_SHORT).show()
        onBack()
    }

    fun onFailure() {
        progressBar.visibility = View.GONE
        Toast.makeText(context, R.string.error_feedback, Toast.LENGTH_SHORT).show()
        onBack()
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
        fun newInstance() = ReportFragment()
    }
}