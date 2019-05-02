package ru.teamdroid.recipecraft.ui.navigation.fragments

import android.graphics.Rect
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.LifecycleRegistry
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_detail_recipe.*
import org.jetbrains.anko.bundleOf
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.ui.base.BaseFragment
import ru.teamdroid.recipecraft.ui.base.customs.CustomGridLayoutManager
import ru.teamdroid.recipecraft.ui.navigation.adapters.IngredientsAdapter
import ru.teamdroid.recipecraft.ui.navigation.adapters.InstructionAdapter
import ru.teamdroid.recipecraft.ui.navigation.components.DaggerDetailRecipeComponent
import ru.teamdroid.recipecraft.ui.navigation.contracts.DetailRecipeContract
import ru.teamdroid.recipecraft.ui.navigation.modules.DetailRecipePresenterModule
import ru.teamdroid.recipecraft.ui.navigation.presenters.DetailRecipePresenter
import javax.inject.Inject

class DetailRecipeFragment : BaseFragment(), DetailRecipeContract.View {

    @Inject
    internal lateinit var presenter: DetailRecipePresenter

    override val contentResId = R.layout.fragment_detail_recipe

    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry = lifecycleRegistry

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

        initializePresenter()
    }

    private fun initializePresenter() {
        DaggerDetailRecipeComponent.builder()
                .detailRecipePresenterModule(DetailRecipePresenterModule(this))
                .recipeRepositoryComponent(baseActivity.recipeRepositoryComponent)
                .build()
                .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(toolbar, true, "")

        with(ingredientsRecyclerView) {
            adapter = ingredientsAdapter
            layoutManager = CustomGridLayoutManager(context)
        }

        with(instructionsRecyclerView) {
            adapter = instructionsAdapter
            layoutManager = CustomGridLayoutManager(context)
        }

        ingredientsAdapter.items = recipe?.ingredients ?: arrayListOf()
        instructionsAdapter.items = recipe?.instructions ?: arrayListOf()

        recipe?.isBookmarked?.let { isBookmarked(it) }

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

        favoriteImageView.setOnClickListener {
            recipe?.let { presenter.bookmarkRecipe(it) }
        }

    }

    override fun showBookmarked(isBookmarked: Boolean) {
        recipe?.let { it.isBookmarked = !it.isBookmarked }
        isBookmarked(isBookmarked)
        val snackBar = Snackbar.make(coordinatorLayout, if (isBookmarked) getString(R.string.bookmarked) else getString(R.string.unbookmark_text), 500)
        snackBar.setAction(getString(R.string.close_text)) { snackBar.dismiss() }.setActionTextColor(resources.getColor(R.color.textWhite)).show()
    }

    private fun isBookmarked(isBookmarked: Boolean) {
        if (isBookmarked) favoriteImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_active)) else favoriteImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_inactive))
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
        ingredientsRecyclerView.adapter = null
        instructionsRecyclerView.adapter = null
        favoriteImageView.setOnClickListener(null)
        nestedScrollView.setOnClickListener(null)
        super.onDestroyView()
    }

    companion object {
        private const val RECIPE = "recipes"

        fun newInstance(recipe: Recipe) = DetailRecipeFragment().apply {
            arguments = bundleOf(RECIPE to recipe)
        }
    }
}