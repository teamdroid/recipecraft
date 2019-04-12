package ru.teamdroid.recipecraft.ui.navigation.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import org.jetbrains.anko.bundleOf

import ru.teamdroid.recipecraft.R

class SelectIngredientsDialog : DialogFragment() {

    private var listIngredients: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            listIngredients = it?.getStringArrayList(LIST_INGREDIENTS) as ArrayList<String>
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            val selectedItems = ArrayList<String>()
            val builder = AlertDialog.Builder(it)

            builder.setTitle(getString(R.string.list_ingredients_text))
                    .setMultiChoiceItems(listIngredients.toTypedArray(), null) { _, which, isChecked ->
                        if (isChecked) {
                            selectedItems.add(listIngredients[which])
                        } else if (selectedItems.contains(listIngredients[which])) {
                            selectedItems.remove(listIngredients[which])
                        }
                    }
                    .setPositiveButton(R.string.ok) { _, _ ->
                        val intent = Intent().putExtra(LIST_INGREDIENTS, selectedItems)
                        targetFragment?.onActivityResult(targetRequestCode, REQUEST_CODE, intent)
                    }
                    .setNegativeButton(getString(R.string.cancel)) { _, _ ->

                    }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        const val LIST_INGREDIENTS = "listIngredients"
        const val REQUEST_CODE = 134
        fun newInstance(listIngredients: ArrayList<String>) = SelectIngredientsDialog().apply {
            arguments = bundleOf(LIST_INGREDIENTS to listIngredients)
        }
    }
}