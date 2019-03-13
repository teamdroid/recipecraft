package ru.teamdroid.recipecraft.ui.navigation.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_list_ingredients_item.view.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.data.model.Ingredients

class IngredientsAdapter(
        var onItemClickListener: (position: Int) -> Unit)
    : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    var items: MutableList<Ingredients> = arrayListOf()
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
            titleTexView.text = items[position].title
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}