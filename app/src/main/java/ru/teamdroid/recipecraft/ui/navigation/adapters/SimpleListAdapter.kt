package ru.teamdroid.recipecraft.ui.navigation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_list_ingredients_item.view.*
import ru.teamdroid.recipecraft.R

class SimpleListAdapter : RecyclerView.Adapter<SimpleListAdapter.ViewHolder>() {

    var items: List<String> = arrayListOf()
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
            titleTexView.text = items[position]
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}