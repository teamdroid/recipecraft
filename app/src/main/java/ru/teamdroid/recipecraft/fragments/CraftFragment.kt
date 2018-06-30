package ru.teamdroid.recipecraft.fragments

import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.base.BaseMoxyFragment

class CraftFragment : BaseMoxyFragment() {

    override val contentResId = R.layout.fragment_craft

    companion object {
        fun newInstance() = CraftFragment()
    }
}