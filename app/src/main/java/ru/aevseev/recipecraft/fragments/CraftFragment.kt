package ru.aevseev.recipecraft.fragments

import ru.aevseev.recipecraft.R
import ru.aevseev.recipecraft.base.BaseMoxyFragment

class CraftFragment : BaseMoxyFragment() {

    override val contentResId = R.layout.fragment_craft

    companion object {
        fun newInstance() = CraftFragment()
    }
}