package ru.teamdroid.recipecraft.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_list_recipes_item.view.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.room.entity.Recipes

class RecipesAdapter(
        var onItemClickListener: (position: Int) -> Unit,
        var onFavoriteClickListener: (recipes: Recipes) -> Unit)
    : RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {

    var recipes: MutableList<Recipes> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private lateinit var currentRecipe: Recipes

    override fun getItemCount() = recipes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_recipes_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            currentRecipe = recipes[position]
            setOnClickListener { onItemClickListener.invoke(position) }
            titleTextView.text = recipes[position].title
            infoTextView.text = resources.getString(
                    R.string.info_recipes,
                    currentRecipe.ingredients.size.toString(),
                    currentRecipe.portion.toString(),
                    currentRecipe.time.toString()
            )

            with(favoritesButton) {
                text = if (!recipes[position].isBookmarked) context.getString(R.string.save) else context.getString(R.string.Delete)
                setOnClickListener {
                    onFavoriteClickListener.invoke(recipes[position])
                }
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}