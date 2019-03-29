package ru.teamdroid.recipecraft.ui.navigation

import ru.teamdroid.recipecraft.data.model.Instruction
import ru.teamdroid.recipecraft.ui.base.BasePresenter

interface DetailRecipeContract {
    interface View {
        fun updateInstructions(listInstruction: MutableList<Instruction>)
    }

    interface Presenter : BasePresenter<View> {
        fun getInstructionsById(idRecipe: Int)
    }
}