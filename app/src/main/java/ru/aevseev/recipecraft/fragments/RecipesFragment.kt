package ru.aevseev.recipecraft.fragments

import ru.aevseev.recipecraft.R
import ru.aevseev.recipecraft.base.BaseMoxyFragment

class RecipesFragment : BaseMoxyFragment() {

    override val contentResId = R.layout.fragment_recipes

    companion object {
        fun newInstance() = RecipesFragment()
    }
}