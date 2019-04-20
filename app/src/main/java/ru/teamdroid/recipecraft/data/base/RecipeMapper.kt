package ru.teamdroid.recipecraft.data.base

import ru.teamdroid.recipecraft.data.database.entities.*
import ru.teamdroid.recipecraft.data.model.*

class RecipeMapper : Mapper<Recipe, RecipeEntity> {

    override fun map(value: Recipe): RecipeEntity = RecipeEntity(value.idRecipe, value.title, value.time, value.portion, value.type, value.isBookmarked)
    override fun reverseMap(value: RecipeEntity) = Recipe(value.idRecipe, value.title, value.time, value.portion)

    fun mapDetailRecipe(value: RecipeEntity, ingredientEntities: List<IngredientEntity>, listRecipeInstructions: MutableList<InstructionEntity>): Recipe {

        val recipe = Recipe(value.idRecipe, value.title, value.time, value.portion, value.type, value.isBookmarked)

        ingredientEntities.forEach { ingredientEntity ->
            recipe.ingredients.add(Ingredient(ingredientEntity.idIngredient, ingredientEntity.title, 0, ingredientEntity.amount, ingredientEntity.idUnitMeasure, ingredientEntity.title_unit_measure))
        }

        listRecipeInstructions.forEach { instruction ->
            recipe.instructions.add(Instruction(instruction.idInstruction, instruction.idRecipe, instruction.title))
        }

        return recipe
    }

    fun mapIngredients(listIngredients: MutableList<Ingredient>): MutableList<IngredientEntity> {
        val ingredientsEntities: MutableList<IngredientEntity> = arrayListOf()
        for (ingredient in listIngredients) {

            val ingredientEntity = IngredientEntity(ingredient.idIngredient, ingredient.title, ingredient.amount, ingredient.idUnitMeasure, ingredient.title_unit_measure)
            ingredientsEntities.add(ingredientEntity)
        }
        return ingredientsEntities
    }

    fun mapRecipeIngredients(listRecipeIngredients: MutableList<RecipeIngredients>): MutableList<RecipeIngredientsEntity> {
        val recipeIngredientsEntity: MutableList<RecipeIngredientsEntity> = arrayListOf()
        for (recipeIngredient in listRecipeIngredients) {
            val recipeIngredientEntity = RecipeIngredientsEntity(recipeIngredient.id, recipeIngredient.idRecipe, recipeIngredient.idIngredient, recipeIngredient.amount, recipeIngredient.idUnitMeasure)
            recipeIngredientsEntity.add(recipeIngredientEntity)
        }

        return recipeIngredientsEntity
    }

    fun mapRecipe(recipes: MutableList<Recipe>): MutableList<RecipeEntity> {
        val recipesEntities: MutableList<RecipeEntity> = arrayListOf()
        for (recipe in recipes) {
            val recipeEntity = RecipeEntity(recipe.idRecipe, recipe.title, recipe.time, recipe.portion, recipe.type, recipe.isBookmarked)
            recipesEntities.add(recipeEntity)
        }
        return recipesEntities
    }

    fun mapRecipeInstructions(listRecipeInstructions: MutableList<Instruction>): MutableList<InstructionEntity> {
        val recipeInstructionsEntity: MutableList<InstructionEntity> = arrayListOf()
        for (instruction in listRecipeInstructions) {
            val recipeIngredientEntity = InstructionEntity(instruction.idInstruction, instruction.idRecipe, instruction.title)
            recipeInstructionsEntity.add(recipeIngredientEntity)
        }

        return recipeInstructionsEntity
    }

    fun reverseInstructions(listInstructionsEntity: MutableList<InstructionEntity>): MutableList<Instruction> {
        val recipeInstructions: MutableList<Instruction> = arrayListOf()

        for (instruction in listInstructionsEntity) {
            val recipeIngredient = Instruction(instruction.idInstruction, instruction.idRecipe, instruction.title)
            recipeInstructions.add(recipeIngredient)
        }

        return recipeInstructions
    }

    fun mapUnitMeasure(listUnitMeasure: MutableList<UnitMeasure>): MutableList<UnitMeasureEntity> {
        val listUnitMeasureEntities: MutableList<UnitMeasureEntity> = arrayListOf()
        for (unitMeasure in listUnitMeasure) {
            val unitMeasureEntity = UnitMeasureEntity(unitMeasure.idUnitMeasure, unitMeasure.title)
            listUnitMeasureEntities.add(unitMeasureEntity)
        }
        return listUnitMeasureEntities
    }
}