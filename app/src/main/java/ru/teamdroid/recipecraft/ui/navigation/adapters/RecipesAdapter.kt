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
                    .into(imageView)

            with(favoriteImageView) {
                if (listRecipes[position].isBookmarked) setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_active)) else setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_inactive))
                setOnClickListener {
                    onFavoriteClickListener.invoke(listRecipes[position].copy())
                }
            }
        }
    }

    fun updateListRecipes(listRecipe: MutableList<Recipe>) {
        if (listRecipes.isNotEmpty()) {
            val diffCallback = RecipeDiffCallback(this.listRecipes, listRecipe)
            val diffResult = DiffUtil.calculateDiff(diffCallback)

            this.listRecipes.clear()
            this.listRecipes.addAll(listRecipe)

            diffResult.dispatchUpdatesTo(this)
        } else {
            listRecipes = listRecipe
        }
    }

    fun clear() {
        listRecipes.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}
