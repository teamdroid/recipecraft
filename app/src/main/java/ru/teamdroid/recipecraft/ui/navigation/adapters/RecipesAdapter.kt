package ru.teamdroid.recipecraft.ui.navigation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_list_recipes_item.view.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.data.model.Recipe

class RecipesAdapter(
        var onItemClickListener: (position: Int) -> Unit,
        var onFavoriteClickListener: (recipes: Recipe) -> Unit)
    : RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {

    var recipes: MutableList<Recipe> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private lateinit var currentRecipe: Recipe

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
            categoryTextView.text = recipes[position].type
            with(favoritesButton) {
                text = if (!recipes[position].isBookmarked) context.getString(R.string.save) else context.getString(R.string.Delete)
                setOnClickListener {
                    onFavoriteClickListener.invoke(recipes[position])
                }
            }
        }
    }

    fun sortByTime() {
        recipes.sortBy { it.time }
    }

    fun sortByPortion() {
        recipes.sortByDescending { it.portion }
    }

    fun sortByIngredients() {
        recipes.sortBy { it.ingredients.size }
    }

    fun sortByAll() {
        recipes.sortBy { it.idRecipe }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}