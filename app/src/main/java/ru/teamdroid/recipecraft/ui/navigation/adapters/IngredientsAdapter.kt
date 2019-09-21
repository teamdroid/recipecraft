package ru.teamdroid.recipecraft.ui.navigation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_list_ingredients_item.view.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.data.model.Ingredient
import ru.teamdroid.recipecraft.ui.base.ViewType
import java.text.DecimalFormat

class IngredientsAdapter(var onItemClickListener: (position: Int) -> Unit) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    var items: MutableList<Ingredient> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ViewType.HEADER -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_list_ingredients_item_header, parent, false))
            else -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_list_ingredients_item, parent, false))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            setOnClickListener { onItemClickListener.invoke(position) }
            titleTexView.text = items[position].title.toLowerCase()
            measureTextView.text = resources.getString(
                    R.string.measure_text,
                    DecimalFormat("#.#").format(items[position].amount).toLowerCase().takeUnless { it == "0" } ?: "",
                    items[position].measureTitle
            ).trimStart()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            ViewType.HEADER -> ViewType.HEADER
            else -> ViewType.NORMAL
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}