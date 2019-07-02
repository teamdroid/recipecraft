package ru.teamdroid.recipecraft.ui.navigation.adapters

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_favorites.view.*
import kotlinx.android.synthetic.main.layout_list_ingredients_item_with_buttons.view.*
import org.jetbrains.anko.image
import org.jetbrains.anko.textColor
import ru.teamdroid.recipecraft.R
import kotlinx.android.synthetic.main.fragment_favorites.view.placeholderTextView as placeholderTextView1

class RecipesFilterAdapter(var onDeleteClickListener: (ingredient: String) -> Unit) : RecyclerView.Adapter<RecipesFilterAdapter.ViewHolder>() {

    private var listIngredients: ArrayList<String> = arrayListOf()

    var listSelectedIngredients: ArrayList<String> = arrayListOf()

    var items: MutableList<String> = arrayListOf("", "", "")
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_ingredients_item_with_buttons, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            ingredientRelativeLayout.isEnabled = false

            ingredientsNumberTextView.isEnabled = false
            ingredientsNumberTextView.text = Editable.Factory.getInstance().newEditable((position + 1).toString().plus("."))

            setStateView(holder, position)

            titleIngredientTextView.setDropDownBackgroundResource(R.drawable.ic_clear_grey)
            with(titleIngredientTextView) {
                text = Editable.Factory.getInstance().newEditable(items[position])
                setAdapter(ArrayAdapter<String>(context, R.layout.simple_dropdown_item, listIngredients))
                setOnItemClickListener { _, _, _, _ ->
                    selectedIngredient(holder, position)
                }
                setOnFocusChangeListener { v, hasFocus ->
                    setColorViewStateWithBorder(holder, position, hasFocus)
                    setNotSelectedIngredients(holder, position, hasFocus)
                }
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


    private fun setStateView(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            if (items[position] != "") {
                titleIngredientTextView.isEnabled = false
                deleteButton.visibility = View.VISIBLE
                setColorViewState(holder, false)
            } else {
                titleIngredientTextView.isEnabled = true
                deleteButton.visibility = if (position > 2) View.VISIBLE else View.INVISIBLE
                setColorViewState(holder, true)
            }
        }
    }

    private fun setColorViewState(holder: ViewHolder, isEnabled: Boolean) {
        with(holder.itemView) {
            when (isEnabled) {
                true -> {
                    ingredientsNumberTextView.textColor = resources.getColor(R.color.textGray)
                    titleIngredientTextView.textColor = resources.getColor(R.color.textGray)
                    ingredientRelativeLayout.background = resources.getDrawable(R.color.dark_orange_color_12_opacity)
                    deleteButton.image = resources.getDrawable(R.drawable.cancel_gray)
                }
                false -> {
                    ingredientsNumberTextView.textColor = resources.getColor(R.color.textWhite)
                    titleIngredientTextView.textColor = resources.getColor(R.color.textWhite)
                    ingredientRelativeLayout.background = resources.getDrawable(R.color.dark_orange_color)
                    deleteButton.image = resources.getDrawable(R.drawable.ic_clear_grey)
                }
            }
        }
    }

    private fun setNotSelectedIngredients(holder: ViewHolder, position: Int, hasFocus: Boolean) {
        with(holder.itemView) {
            if (!hasFocus) {
                items[position] = titleIngredientTextView.text.toString()
                setSelectedIngredientList(items)
            }
        }
    }

    /*TODO: if(position in 0..items.size - 1) - это костыль против слушателя setOnFocusChangeListener titleIngredientTextView
    * TODO: слушатель и этот метод вместе с ним срабатывает всякий раз когда мы удаляем элемент. Фокус теряется и работа
    * TODO: в методе идет с несуществующим элементом если этот элемент последний. По идее if сам по себе надежен но
    * TODO: возможно есть решение по лучше.*/
    private fun setColorViewStateWithBorder(holder: ViewHolder, position: Int, viewHasFocus: Boolean) {
        if (position in 0..items.size - 1) {
            with(holder.itemView) {
                when (viewHasFocus) {
                    true -> {
                        ingredientsNumberTextView.textColor = resources.getColor(R.color.dark_orange_color)
                        titleIngredientTextView.textColor = resources.getColor(R.color.dark_orange_color)
                        titleIngredientTextView.hint = ""
                        ingredientRelativeLayout.background = resources.getDrawable(R.drawable.change_choice_ingredients_view)
                    }
                    false -> {
                        titleIngredientTextView.hint = resources.getString(R.string.select_ingredient_text)
                        setStateView(holder, position)
                    }
                }
            }
        }
    }

    private fun selectedIngredient(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            items[position] = titleIngredientTextView.text.toString()
            deleteButton.visibility = View.VISIBLE
            titleIngredientTextView.isEnabled = false
            setSelectedIngredientList(items)
            setStateView(holder, position)
        }
    }

    private fun deleteIngredient(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            if (items.size < 4) {
                items[position] = ""
                setColorViewState(holder, true)
            }
            titleIngredientTextView.isEnabled = true
            titleIngredientTextView.text = null
            deleteButton.visibility = View.INVISIBLE
            onDeleteClickListener.invoke(items[position])
        }
    }


    public fun setSelectedIngredientList(selectedIngredientsList: List<String>): ArrayList<String> {
        val finalList: ArrayList<String> = arrayListOf()
        for (ingredient: String in selectedIngredientsList) {
            if (ingredient.isNotBlank()) finalList.add(ingredient)
        }
        listSelectedIngredients = finalList
        return listSelectedIngredients
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