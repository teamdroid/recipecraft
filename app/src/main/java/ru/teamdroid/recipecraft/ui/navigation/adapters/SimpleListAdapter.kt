package ru.teamdroid.recipecraft.ui.navigation.adapters

import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.opengl.Visibility
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_list_ingredients_item_with_buttons.view.*
import ru.teamdroid.recipecraft.ui.navigation.fragments.CraftFragment


class SimpleListAdapter(var onDeleteClickListener: (ingredient: String) -> Unit) : RecyclerView.Adapter<SimpleListAdapter.ViewHolder>() {

    var craftFragment: CraftFragment? = null

    private var listIngredients: ArrayList<String> = arrayListOf()

    var ingredientIsEnabled: Boolean = false

    var items: MutableList<String> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addItems(item: String) {
        items.add(item)
        notifyDataSetChanged()
    }

    fun setFragment(_craftFragment: CraftFragment) {
        craftFragment = _craftFragment
    }

    fun setIngredientList(_listIngredients: ArrayList<String>) {
        listIngredients = _listIngredients
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(ru.teamdroid.recipecraft.R.layout.layout_list_ingredients_item_with_buttons, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            ingredientConstraintLayout.isEnabled = false

            ingredientsNumberTextView.isEnabled = false
            ingredientsNumberTextView.text = Editable.Factory.getInstance().newEditable((position + 1).toString().plus("."))

            titleIngredientTextView.text = Editable.Factory.getInstance().newEditable(items[position])
            titleIngredientTextView.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, arrayListOf("chesnok", "yaponka", "che")))//listIngredients arrayListOf("chesnok", "yaponka", "che")
            titleIngredientTextView.setOnItemClickListener { parent, view, positionIngredient, id ->
                selectedIngredient(holder, position)
            }
            deleteButton.setOnClickListener {
                deleteIngredient(holder, position)
            }
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.unbindViewHolder(holder.itemView)

    }

    private fun selectedIngredient(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            items[position] = titleIngredientTextView.text.toString()
            deleteButton.visibility = View.VISIBLE
            titleIngredientTextView.isEnabled = false
        }
    }

    private fun deleteIngredient(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            if (items.size > 3) {
                onDeleteClickListener.invoke(items[position])
            } else {
                items[position] = ""
                titleIngredientTextView.isEnabled = true
                titleIngredientTextView.text = null
                deleteButton.visibility = View.INVISIBLE
            }
        }
    }

    fun isEmpty(): Boolean = items.isEmpty()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun unbindViewHolder(view: View) {
            with(view) {
                if (titleIngredientTextView.isEnabled) {
                    titleIngredientTextView.text = null
                    deleteButton.visibility = View.INVISIBLE
                }
            }
        }
    }
}