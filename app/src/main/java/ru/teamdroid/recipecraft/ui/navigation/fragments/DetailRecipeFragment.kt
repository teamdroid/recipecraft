package ru.teamdroid.recipecraft.ui.navigation.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.LifecycleRegistry
import kotlinx.android.synthetic.main.fragment_detail_recipe.*
import org.jetbrains.anko.bundleOf
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.ui.base.BaseFragment
import ru.teamdroid.recipecraft.ui.base.CustomGridLayoutManager
import ru.teamdroid.recipecraft.ui.navigation.DetailRecipeContract
import ru.teamdroid.recipecraft.ui.navigation.adapters.IngredientsAdapter
import ru.teamdroid.recipecraft.ui.navigation.adapters.InstructionAdapter
import ru.teamdroid.recipecraft.ui.navigation.components.DaggerDetailRecipeComponent
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
        setupToolbar(toolbar, true, recipe?.title ?: "")

        with(recyclerView) {
            adapter = ingredientsAdapter
            layoutManager = CustomGridLayoutManager(context)
        }

        with(recyclerViewInstruction) {
            adapter = instructionsAdapter
            layoutManager = CustomGridLayoutManager(context)
        }

        ingredientsAdapter.items = recipe?.ingredients ?: arrayListOf()
        instructionsAdapter.items = recipe?.insctructions ?: arrayListOf()

        information_text.text = resources.getString(
                R.string.information_detail_recipe_text,
                recipe?.time,
                recipe?.portion,
                recipe?.ingredients?.size.toString(),
                recipe?.insctructions?.size.toString()
        )
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

    companion object {
        private const val RECIPE = "recipes"

        fun newInstance(recipe: Recipe) = DetailRecipeFragment().apply {
            arguments = bundleOf(RECIPE to recipe)
        }
    }
}