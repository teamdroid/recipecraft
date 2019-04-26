package ru.teamdroid.recipecraft.ui.navigation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_list_ingredients_item.view.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.data.model.Ingredient
import java.text.DecimalFormat

class IngredientsAdapter(var onItemClickListener: (position: Int) -> Unit) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    var items: MutableList<Ingredient> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_ingredients_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            setOnClickListener { onItemClickListener.invoke(position) }

            titleTexView.text = resources.getString(
                    R.string.ingredients_list_text,
                    items[position].title,
                    DecimalFormat("#.#").format(items[position].amount).takeUnless { it == "0" } ?: "",
                    items[position].measureTitle
            )
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}