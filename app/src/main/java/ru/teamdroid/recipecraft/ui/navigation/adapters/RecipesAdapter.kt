package ru.teamdroid.recipecraft.ui.navigation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_list_recipes_item.view.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.ui.base.RecipeDiffCallback
import java.io.File

class RecipesAdapter(
        var onItemClickListener: (position: Int) -> Unit,
        var onFavoriteClickListener: (recipes: Recipe) -> Unit)
    : RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {

    var listRecipes: MutableList<Recipe> = ArrayList()

    private lateinit var currentRecipe: Recipe

    override fun getItemCount() = listRecipes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_recipes_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            currentRecipe = listRecipes[position]
            cardView.setOnClickListener { onItemClickListener.invoke(position) }
            titleTextView.text = listRecipes[position].title.toLowerCase()
            ingredientsTextView.text = listRecipes[position].ingredients.size.toString()
            timeTextView.text = listRecipes[position].time.toString()
            portionTextView.text = listRecipes[position].portion.toString()

            Picasso.with(context)
                    .load(File(listRecipes[position].image))
                    .placeholder(R.drawable.ic_placeholder_recipes)
                    .into(imageView)

            with(favoriteImageView) {
                if (listRecipes[position].isBookmarked) setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_active)) else setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_inactive))
                setOnClickListener {
                    onFavoriteClickListener.invoke(listRecipes[position].copy())
                }
            }
        }
    }

    fun updateRecipes(recipes: MutableList<Recipe>) {

        recipes.forEach { newRecipe ->
            if (!listRecipes.any { oldRecipe -> oldRecipe.idRecipe == newRecipe.idRecipe })
                listRecipes.add(newRecipe)
            else {
                listRecipes.forEachIndexed { index, oldRecipe ->
                    if (oldRecipe.idRecipe == newRecipe.idRecipe &&
                            oldRecipe.isBookmarked != newRecipe.isBookmarked)
                        listRecipes[index] = newRecipe
                }
            }
        }

        notifyDataSetChanged()
    }

    fun clear() {
        listRecipes.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}
