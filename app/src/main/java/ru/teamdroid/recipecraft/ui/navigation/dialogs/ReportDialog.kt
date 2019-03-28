package ru.teamdroid.recipecraft.ui.navigation.dialogs

import android.app.Dialog
import android.app.ProgressDialog

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.teamdroid.recipecraft.R

class ReportDialog : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = ProgressDialog(activity, theme)
        with(dialog) {
            setTitle(getString(R.string.send_report_message_text))
            setMessage(getString(R.string.please_wait_text))
            setProgressStyle(ProgressDialog.STYLE_SPINNER)
            isIndeterminate = true
        }
        return dialog
    }
}