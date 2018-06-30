package ru.teamdroid.recipecraft.fragments

import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.base.BaseMoxyFragment

class RecipesFragment : BaseMoxyFragment() {

    override val contentResId = R.layout.fragment_recipes

    companion object {
        fun newInstance() = RecipesFragment()
    }
}