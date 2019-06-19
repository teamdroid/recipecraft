package ru.teamdroid.recipecraft.ui.navigation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_list_recipes_item.view.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.data.model.Recipe
import android.graphics.BitmapFactory

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
            cardView.setOnClickListener { onItemClickListener.invoke(position) }
            titleTextView.text = recipes[position].title.toLowerCase()
            ingredientsTextView.text = recipes[position].ingredients.size.toString()
            timeTextView.text = recipes[position].time.toString()
            portionTextView.text = recipes[position].portion.toString()
            imageView.setImageBitmap(BitmapFactory.decodeFile(recipes[position].image))
            with(favoriteImageView) {
                if (recipes[position].isBookmarked) setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_active)) else setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_inactive))
                setOnClickListener {
                    onFavoriteClickListener.invoke(recipes[position])
                }
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}