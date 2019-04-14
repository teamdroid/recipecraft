package ru.teamdroid.recipecraft.ui.navigation.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_craft.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.data.model.Recipe
import ru.teamdroid.recipecraft.ui.base.BaseFragment
import ru.teamdroid.recipecraft.ui.base.Constants
import ru.teamdroid.recipecraft.ui.base.CustomGridLayoutManager
import ru.teamdroid.recipecraft.ui.navigation.CraftRecipeContract
import ru.teamdroid.recipecraft.ui.navigation.OnSubmitClickListener
import ru.teamdroid.recipecraft.ui.navigation.adapters.RecipesAdapter
import ru.teamdroid.recipecraft.ui.navigation.adapters.SimpleListAdapter
import ru.teamdroid.recipecraft.ui.navigation.components.DaggerCraftComponent
import ru.teamdroid.recipecraft.ui.navigation.dialogs.SelectIngredientsDialog
import ru.teamdroid.recipecraft.ui.navigation.modules.CraftPresenterModule
import ru.teamdroid.recipecraft.ui.navigation.presenters.CraftPresenter
import javax.inject.Inject

class CraftFragment : BaseFragment(), CraftRecipeContract.View, OnSubmitClickListener {

    @Inject
    internal lateinit var presenter: CraftPresenter

    override val contentResId = R.layout.fragment_craft

    private val lifecycleRegistry = LifecycleRegistry(this)

    private val ingredientsAdapter = SimpleListAdapter()

    private val recipesAdapter by lazy {
        RecipesAdapter(
                onItemClickListener = {
                    onClick(it)
                },
                onFavoriteClickListener = {
                    onFavoriteClick(it)
                }
        )
    }

    private var listIngredientsTitle: ArrayList<String> = arrayListOf()

    override fun getLifecycle(): LifecycleRegistry = lifecycleRegistry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializePresenter()
        presenter.loadIngredientsTitle()
    }

    private fun initializePresenter() {
        DaggerCraftComponent.builder()
                .craftPresenterModule(CraftPresenterModule(this))
                .recipeRepositoryComponent(baseActivity.recipeRepositoryComponent)
                .build()
                .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(toolbar, false, getString(R.string.fragment_craft_title))

        with(ingredientsRecyclerView) {
            adapter = ingredientsAdapter
            layoutManager = CustomGridLayoutManager(context)
        }

        with(recipesRecyclerView) {
            adapter = recipesAdapter
            layoutManager = LinearLayoutManager(context)
        }

        button.setOnClickListener {
            val selectIngredientsDialog = SelectIngredientsDialog.newInstance(listIngredientsTitle)
            selectIngredientsDialog.setTargetFragment(this, Constants.REQUEST_CODE)
            selectIngredientsDialog.show(requireFragmentManager(), TAG)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_navigation, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_report -> {
                baseActivity.replaceFragment(ReportFragment.newInstance(), NavigationFragment.TAG)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onClick(position: Int) {
        baseActivity.replaceFragment(DetailRecipeFragment.newInstance(recipesAdapter.recipes[position]), NavigationFragment.TAG)
    }

    private fun onFavoriteClick(recipe: Recipe) {
        recipe.isBookmarked = !recipe.isBookmarked
        presenter.bookmarkRecipe(recipe)
    }

    override fun setIngredientsTitle(listIngredientsTitle: List<String>) {
        this.listIngredientsTitle.addAll(listIngredientsTitle)
    }

    override fun showRecipe(listRecipe: MutableList<Recipe>) {
        recipesAdapter.recipes = listRecipe
    }

    override fun showBookmarked(isBookmarked: Boolean) {
        val snackBar = Snackbar.make(constraintLayout, if (isBookmarked) getString(R.string.bookmarked) else getString(R.string.unbookmark_text), 500)
        snackBar.setAction(getString(R.string.close_text)) { snackBar.dismiss() }.setActionTextColor(resources.getColor(R.color.textWhite)).show()
    }

    override fun onSubmitClicked(list: ArrayList<String>) {
        ingredientsAdapter.items = list
        presenter.findRecipeByIngredients(list, list.size)
    }

    override fun onDestroyView() {
        button.setOnClickListener(null)
        ingredientsRecyclerView.adapter = null
        recipesRecyclerView.adapter = null
        super.onDestroyView()
    }

    companion object {
        const val TAG = "CraftFragment"
        fun newInstance() = CraftFragment()
    }
}