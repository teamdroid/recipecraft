package ru.teamdroid.recipecraft.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_list_recipes_item.view.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.room.entity.Recipe

class RecipesAdapter(
        var onItemClickListener: (position: Int) -> Unit,
        var onFavoriteClickListener: (recipe: Recipe) -> Unit)
    : RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {

    var recipes: MutableList<Recipe> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = recipes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_recipes_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            setOnClickListener { onItemClickListener.invoke(position) }
            titleTextView.text = recipes[position].title
            infoTextView.text = resources.getString(R.string.info_recipes, recipes[position].ingredients.size.toString())
            with(favoritesButton) {
                text = recipes[position].isBookmarked.toString()
                setOnClickListener {
                    onFavoriteClickListener.invoke(recipes[position])
                }
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}