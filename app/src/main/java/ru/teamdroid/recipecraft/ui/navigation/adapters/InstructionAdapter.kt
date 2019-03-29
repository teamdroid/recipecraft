package ru.teamdroid.recipecraft.ui.navigation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_list_ingredients_item.view.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.data.model.Instruction

class InstructionAdapter(var onItemClickListener: (position: Int) -> Unit) : RecyclerView.Adapter<InstructionAdapter.ViewHolder>() {

    var items: MutableList<Instruction> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_instructions_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            setOnClickListener { onItemClickListener.invoke(position) }

            titleTexView.text = resources.getString(
                    R.string.instructions_text,
                    position + 1,
                    items[position].title
            )
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}