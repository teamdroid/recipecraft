package ru.teamdroid.recipecraft.fragments

import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.base.BaseMoxyFragment

class FavoritesFragment : BaseMoxyFragment() {

    override val contentResId = R.layout.fragment_favorites

    companion object {
        fun newInstance() = FavoritesFragment()
    }
}