package ru.teamdroid.recipecraft.ui.navigation.adapters

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_list_ingredients_item_with_buttons.view.*

class RecipesFilterAdapter(var onDeleteClickListener: (ingredient: String) -> Unit) : RecyclerView.Adapter<RecipesFilterAdapter.ViewHolder>() {

    private var listIngredients: ArrayList<String> = arrayListOf()

    var listSelectedIngredients: ArrayList<String> = arrayListOf()

    var items: MutableList<String> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addItems(item: String) {
        items.add(item)
        notifyDataSetChanged()
    }


    fun setIngredientList(listIngredients: ArrayList<String>) {
        this.listIngredients = listIngredients
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

            setStateView(holder, position)

            with(titleIngredientTextView) {
                text = Editable.Factory.getInstance().newEditable(items[position])
                setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, listIngredients))
                setOnItemClickListener { _, _, _, _ ->
                    selectedIngredient(holder, position)
                }
            }

            titleIngredientTextView.dropDownAnchor = ingredientCardView.id

            deleteButton.setOnClickListener {
                deleteIngredient(holder, position)
            }
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.unbindViewHolder(holder.itemView)
    }

    private fun setStateView(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            if (items[position].isNotBlank()) {
                titleIngredientTextView.isEnabled = false
                deleteButton.visibility = View.VISIBLE
            } else {
                titleIngredientTextView.isEnabled = true
                deleteButton.visibility = View.INVISIBLE
            }
        }
    }

    private fun selectedIngredient(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            items[position] = titleIngredientTextView.text.toString()
            deleteButton.visibility = View.VISIBLE
            titleIngredientTextView.isEnabled = false
            setSelectedIngredientList(items)
        }
    }

    private fun deleteIngredient(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            if (items.size > 3) {
                onDeleteClickListener.invoke(items[position])
                titleIngredientTextView.isEnabled = true
                titleIngredientTextView.text = null
                deleteButton.visibility = View.INVISIBLE
            } else {
                items[position] = ""
                titleIngredientTextView.isEnabled = true
                titleIngredientTextView.text = null
                deleteButton.visibility = View.INVISIBLE
            }
            setSelectedIngredientList(items)
        }
    }

    private fun setSelectedIngredientList(selectedIngredientsList: List<String>) {
        val finalList: ArrayList<String> = arrayListOf()
        for (ingredient: String in selectedIngredientsList) {
            if (ingredient.isNotBlank()) finalList.add(ingredient)
        }
        listSelectedIngredients = finalList
    }

    fun isEmpty(): Boolean = items.isEmpty()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun unbindViewHolder(view: View) {
            with(view) {
                if (titleIngredientTextView.isEnabled) {
                    deleteButton.visibility = View.INVISIBLE
                }
                deleteButton.setOnClickListener(null)
                titleIngredientTextView.onItemClickListener = null
                titleIngredientTextView.setAdapter(null)
            }
        }
    }
}