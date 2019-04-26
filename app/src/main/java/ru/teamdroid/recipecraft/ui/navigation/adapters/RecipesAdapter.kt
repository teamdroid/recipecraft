package ru.teamdroid.recipecraft.ui.navigation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_list_recipes_item.view.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.ui.base.SortRecipes

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
            titleTextView.text = recipes[position].title.toLowerCase()
            ingredientsTextView.text = recipes[position].ingredients.size.toString()
            timeTextView.text = recipes[position].time.toString()
            portionTextView.text = recipes[position].portion.toString()

//            with(favoritesButton) {
//                text = if (!recipes[position].isBookmarked) context.getString(R.string.save) else context.getString(R.string.Delete)
//                setOnClickListener {
//                    onFavoriteClickListener.invoke(recipes[position])
//                }
//            }
        }
    }

    fun sort(sortType: String) {
        when (sortType) {
            SortRecipes.ByNewer -> recipes.sortByDescending { it.idRecipe }
            SortRecipes.ByIngredients -> recipes.sortBy { it.ingredients.size }
            SortRecipes.ByTime -> recipes.sortBy { it.time }
            SortRecipes.ByPortion -> recipes.sortByDescending { it.portion }

        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}