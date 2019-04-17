package ru.teamdroid.recipecraft.ui.navigation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_list_ingredients_item_with_buttons.view.*
import ru.teamdroid.recipecraft.R

class SimpleListAdapter(var onDeleteClickListener: (ingredient: String) -> Unit) : RecyclerView.Adapter<SimpleListAdapter.ViewHolder>() {

    var items: MutableList<String> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_ingredients_item_with_buttons, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            titleIngredientTextView.text = items[position]
            deleteButton.setOnClickListener {
                onDeleteClickListener.invoke(items[position])
            }
        }
    }

    fun isEmpty(): Boolean = items.isEmpty()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}