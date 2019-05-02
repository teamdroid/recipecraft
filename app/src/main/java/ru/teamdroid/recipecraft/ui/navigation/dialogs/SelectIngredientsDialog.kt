package ru.teamdroid.recipecraft.ui.navigation.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import org.jetbrains.anko.bundleOf

import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.ui.base.listeners.OnSubmitClickListener

class SelectIngredientsDialog : DialogFragment() {

    private var listIngredients: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            listIngredients = it?.getStringArrayList(LIST_INGREDIENTS) as ArrayList<String>
        }
        isCancelable = true
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val selectedItems = ArrayList<String>()
        val builder = AlertDialog.Builder(targetFragment?.context)
        builder.setTitle(getString(R.string.list_ingredients_text))
                .setMultiChoiceItems(listIngredients.toTypedArray(), null) { _, which, isChecked ->
                    if (isChecked) {
                        selectedItems.add(listIngredients[which])
                    } else if (selectedItems.contains(listIngredients[which])) {
                        selectedItems.remove(listIngredients[which])
                    }
                }
                .setPositiveButton(R.string.ok) { dialog, _ ->
                    (targetFragment as OnSubmitClickListener).onSubmitClicked(selectedItems)
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
        return builder.create()
    }

    companion object {
        const val LIST_INGREDIENTS = "listIngredients"
        fun newInstance(listIngredients: ArrayList<String>) = SelectIngredientsDialog().apply {
            arguments = bundleOf(LIST_INGREDIENTS to listIngredients)
        }
    }
}