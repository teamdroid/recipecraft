package ru.teamdroid.recipecraft.ui.navigation.adapters

import android.graphics.Typeface.BOLD
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_list_ingredients_item.view.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.data.model.Instruction
import ru.teamdroid.recipecraft.ui.base.ViewType

class InstructionAdapter(var onItemClickListener: (position: Int) -> Unit) : RecyclerView.Adapter<InstructionAdapter.ViewHolder>() {

    var items: MutableList<Instruction> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ViewType.HEADER -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_list_instructions_item_header, parent, false))
            else -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_list_instructions_item, parent, false))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            setOnClickListener { onItemClickListener.invoke(position) }

            var positionInstruction = position
            val spannable = SpannableString((++positionInstruction).toString() + " " + items[position].title)

            with(spannable) {
                setSpan(
                        RelativeSizeSpan(1.27f),
                        0, 2,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(
                        StyleSpan(BOLD),
                        0, 2,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            titleTexView.text = spannable
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