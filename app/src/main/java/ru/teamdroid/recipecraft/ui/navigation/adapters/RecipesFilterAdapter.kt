package ru.teamdroid.recipecraft.ui.navigation.adapters

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_list_ingredients_item_with_buttons.view.*
import org.jetbrains.anko.image
import org.jetbrains.anko.textColor
import android.text.TextWatcher


class RecipesFilterAdapter(var onDeleteClickListener: (ingredientPosition: Int) -> Unit) : RecyclerView.Adapter<RecipesFilterAdapter.ViewHolder>() {

    private var listIngredients: ArrayList<String> = arrayListOf()

    var listSelectedIngredients: ArrayList<String> = arrayListOf()

    var ingredientTextWatcher: TextWatcher? = null

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
        val view = LayoutInflater.from(parent.context).inflate(ru.teamdroid.recipecraft.R.layout.layout_list_ingredients_item_with_buttons, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            ingredientRelativeLayout.isEnabled = false

            ingredientsNumberTextView.isEnabled = false
            ingredientsNumberTextView.text = Editable.Factory.getInstance().newEditable((position + 1).toString().plus("."))

            setStateView(holder, position)

            titleIngredientTextView.setDropDownBackgroundResource(ru.teamdroid.recipecraft.R.drawable.ic_clear_grey)
            with(titleIngredientTextView) {
                text = Editable.Factory.getInstance().newEditable(items[position])
                setAdapter(ArrayAdapter<String>(context, ru.teamdroid.recipecraft.R.layout.simple_dropdown_item, listIngredients))
                setOnItemClickListener { _, _, _, _ ->
                    selectedIngredient(holder, position)
                }
                setOnFocusChangeListener { v, hasFocus ->
                    setColorViewStateWithBorder(holder, position, hasFocus)
                }

                //можно использовать объект который выше, ingredientTextWatcher чтобы очищать слушатель (см. unbindViewHolder метод в самом низу)
                //но в таком случае, вроде как идут какие то траблы с позицией, можешь в дебаге посмотреть
                addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        setChangedText(holder, position)
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    }
                })
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
                    ingredientsNumberTextView.textColor = resources.getColor(ru.teamdroid.recipecraft.R.color.textGray)
                    titleIngredientTextView.textColor = resources.getColor(ru.teamdroid.recipecraft.R.color.textGray)
                    ingredientRelativeLayout.background = resources.getDrawable(ru.teamdroid.recipecraft.R.color.dark_orange_color_12_opacity)
                    deleteButton.image = resources.getDrawable(ru.teamdroid.recipecraft.R.drawable.cancel_gray)
                }
                false -> {
                    ingredientsNumberTextView.textColor = resources.getColor(ru.teamdroid.recipecraft.R.color.textWhite)
                    titleIngredientTextView.textColor = resources.getColor(ru.teamdroid.recipecraft.R.color.textWhite)
                    ingredientRelativeLayout.background = resources.getDrawable(ru.teamdroid.recipecraft.R.color.dark_orange_color)
                    deleteButton.image = resources.getDrawable(ru.teamdroid.recipecraft.R.drawable.ic_clear_grey)
                }
            }
        }
    }

    private fun setChangedText(holder: ViewHolder, position: Int) {
        if (position in 0..items.size - 1) {
            with(holder.itemView) {
                items[position] = titleIngredientTextView.text.toString()
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
                        ingredientsNumberTextView.textColor = resources.getColor(ru.teamdroid.recipecraft.R.color.dark_orange_color)
                        titleIngredientTextView.textColor = resources.getColor(ru.teamdroid.recipecraft.R.color.dark_orange_color)
                        titleIngredientTextView.hint = ""
                        ingredientRelativeLayout.background = resources.getDrawable(ru.teamdroid.recipecraft.R.drawable.change_choice_ingredients_view)
                    }
                    false -> {
                        titleIngredientTextView.hint = resources.getString(ru.teamdroid.recipecraft.R.string.select_ingredient_text)
                        setStateView(holder, position)
                    }
                }
            }
        }
    }

    private fun selectedIngredient(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            deleteButton.visibility = View.VISIBLE
            titleIngredientTextView.isEnabled = false
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
            if (items[position] != "")
                titleIngredientTextView.text = null
            deleteButton.visibility = View.INVISIBLE
            onDeleteClickListener.invoke(position)
        }
    }


    public fun setSelectedIngredientList() {
        val finalList: ArrayList<String> = arrayListOf()
        for (ingredient: String in items) {
            if (ingredient.isNotBlank()) finalList.add(ingredient)
        }
        listSelectedIngredients = finalList
    }

    fun isEmpty(): Boolean = items.isEmpty()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun unbindViewHolder(view: View) { //ingredientTextWatcher: TextWatcher
            with(view) {
                if (titleIngredientTextView.isEnabled) {
                    deleteButton.visibility = View.INVISIBLE
                }
                deleteButton.setOnClickListener(null)
                //titleIngredientTextView.removeTextChangedListener(ingredientTextWatcher)
                titleIngredientTextView.onItemClickListener = null
                titleIngredientTextView.setAdapter(null)
            }
        }
    }
}