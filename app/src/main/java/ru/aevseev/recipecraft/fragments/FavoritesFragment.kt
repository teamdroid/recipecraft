package ru.aevseev.recipecraft.fragments

import ru.aevseev.recipecraft.R
import ru.aevseev.recipecraft.base.BaseMoxyFragment

class FavoritesFragment : BaseMoxyFragment() {

    override val contentResId = R.layout.fragment_favorites

    companion object {
        fun newInstance() = FavoritesFragment()
    }
}