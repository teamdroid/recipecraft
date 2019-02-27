package ru.teamdroid.recipecraft.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_report.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.data.ReportMessage
import ru.teamdroid.recipecraft.api.ReportApi
import ru.teamdroid.recipecraft.base.BaseMoxyFragment
import ru.teamdroid.recipecraft.api.RequestInterface

class ReportFragment : BaseMoxyFragment() {

    override val contentResId = R.layout.fragment_report
    private var compositeDisposable: CompositeDisposable? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(toolbar, true, getString(R.string.fragment_report_title))

        compositeDisposable = CompositeDisposable()

        submitFeedbackButton.setOnClickListener {
            if (nameEditText.text.isNotBlank() && emailEditText.text.isNotBlank() && feedbackEditText.text.isNotBlank()) {
                sendReportMessage(nameEditText.text.toString(), emailEditText.text.toString(), feedbackEditText.text.toString())
            } else {
                Toast.makeText(context, R.string.error_input_text, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendReportMessage(name: String, email: String, message: String) {

        submitFeedbackButton.isEnabled = false

        compositeDisposable?.add(RequestInterface.getRetrofitService(ReportApi::class.java).sendReportMessage(ReportMessage(name, email, message))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(({ onSuccess() }), ({ onFailure() })
                ))
    }

    private fun onSuccess() {
        Toast.makeText(context, R.string.thanks_feedback, Toast.LENGTH_SHORT).show()
        onBack()
    }

    private fun onFailure() {
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

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.dispose()
    }

    companion object {
        fun newInstance() = ReportFragment()
    }
}