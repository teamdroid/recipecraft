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

class FavoritesAdapter(
        var onItemClickListener: (position: Int) -> Unit,
        var onFavoriteClickListener: (recipes: Recipe) -> Unit)
    : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {


    var listRecipes: MutableList<Recipe> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private lateinit var currentRecipe: Recipe

    override fun getItemCount() = listRecipes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesAdapter.ViewHolder {
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


    fun updateRecipes(recipe: MutableList<Recipe>) {
        if (listRecipes.isNotEmpty()) {
            val diffCallback = RecipeDiffCallback(this.listRecipes, recipe)
            val diffResult = DiffUtil.calculateDiff(diffCallback)

            this.listRecipes.clear()
            this.listRecipes.addAll(recipe)

            diffResult.dispatchUpdatesTo(this)
        } else {
            listRecipes = recipe
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}