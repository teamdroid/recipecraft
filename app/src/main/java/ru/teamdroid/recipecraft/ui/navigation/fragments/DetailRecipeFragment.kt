package ru.teamdroid.recipecraft.ui.navigation.fragments

import android.graphics.BitmapFactory
import android.graphics.Rect
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_detail_recipe.*
import org.jetbrains.anko.bundleOf
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.ui.base.BaseMoxyFragment
import ru.teamdroid.recipecraft.ui.base.customs.CustomGridLayoutManager
import ru.teamdroid.recipecraft.ui.navigation.adapters.IngredientsAdapter
import ru.teamdroid.recipecraft.ui.navigation.adapters.InstructionAdapter
import ru.teamdroid.recipecraft.ui.navigation.components.DaggerDetailRecipeComponent
import ru.teamdroid.recipecraft.ui.navigation.presenters.DetailRecipePresenter
import ru.teamdroid.recipecraft.ui.navigation.views.DetailRecipeView
import javax.inject.Inject

class DetailRecipeFragment : BaseMoxyFragment(), DetailRecipeView {

    @Inject
    @InjectPresenter
    lateinit var presenter: DetailRecipePresenter

    @ProvidePresenter
    fun providePresenter(): DetailRecipePresenter {
        DaggerDetailRecipeComponent.builder()
                .recipeRepositoryComponent(baseActivity.recipeRepositoryComponent)
                .build()
                .inject(this)
        return presenter
    }

    override val contentResId = R.layout.fragment_detail_recipe

    private var recipe: Recipe? = null

    private val ingredientsAdapter by lazy {
        IngredientsAdapter(
                onItemClickListener = {}
        )
    }

    private val instructionsAdapter by lazy {
        InstructionAdapter(
                onItemClickListener = {}
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            recipe = it?.getParcelable(RECIPE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(toolbar, true, "")

        with(favoriteRecipesRecyclerView) {
            adapter = ingredientsAdapter
            layoutManager = CustomGridLayoutManager(context)
        }

        with(instructionsRecyclerView) {
            adapter = instructionsAdapter
            layoutManager = CustomGridLayoutManager(context)
        }

        ingredientsAdapter.items = recipe?.ingredients ?: arrayListOf()
        instructionsAdapter.items = recipe?.instructions ?: arrayListOf()

        recipe?.isBookmarked?.let { setShowBookmark(it) }

        nestedScrollView.setOnScrollChangeListener { v: NestedScrollView?, _: Int, _: Int, _: Int, _: Int ->
            val react = Rect()
            v?.getHitRect(react)
            if (titleTextView.getLocalVisibleRect(react)) {
                collapsing_toolbar.title = ""
            } else {
                collapsing_toolbar.title = recipe?.title ?: ""
            }
        }

        titleTextView.text = recipe?.title
        ingredient_info_text.text = resources.getString(R.string.ingredients_info_text, recipe?.ingredients?.size.toString())
        time_info_text.text = resources.getString(R.string.time_info_text, recipe?.time.toString())
        portion_info_text.text = resources.getString(R.string.portion_info_text, recipe?.portion.toString())
        toolbarImage.setImageBitmap(BitmapFactory.decodeFile(recipe?.image))
        favoriteImageView.setOnClickListener {
            recipe?.let { presenter.bookmarkRecipe(it) }
        }

    }

    override fun showBookmarked(isBookmark: Boolean) {
        recipe?.let {
            it.isBookmarked = isBookmark
            setShowBookmark(it.isBookmarked)
            val snackBar = Snackbar.make(coordinatorLayout, if (it.isBookmarked) getString(R.string.bookmarked) else getString(R.string.unbookmark_text), 500)
            snackBar.setAction(getString(R.string.close_text)) { snackBar.dismiss() }.setActionTextColor(resources.getColor(R.color.textWhite)).show()
        }
    }

    private fun setShowBookmark(isBookmark: Boolean) {
        if (isBookmark) favoriteImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_active))
        else favoriteImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_inactive))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        favoriteRecipesRecyclerView.adapter = null
        instructionsRecyclerView.adapter = null
        favoriteImageView.setOnClickListener(null)
        nestedScrollView.setOnClickListener(null)
        super.onDestroyView()
    }

    companion object {
        private const val RECIPE = "recipes"

        fun newInstance(recipe: Recipe?) = DetailRecipeFragment().apply {
            arguments = bundleOf(RECIPE to recipe)
        }
    }
}