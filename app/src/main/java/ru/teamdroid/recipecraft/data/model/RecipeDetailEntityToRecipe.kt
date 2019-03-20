package ru.teamdroid.recipecraft.data.model

import java.util.*

class RecipeDetailEntityToRecipe : Mapper<Recipe, RecipeEntity>() {

    override fun map(value: Recipe): RecipeEntity = RecipeEntity(value.idRecipe, value.title, value.time, value.portion)
    override fun reverseMap(value: RecipeEntity) = Recipe(value.idRecipe, value.title, value.time, value.portion)

    fun mapDetailRecipe(value: List<RecipeEntity>, ingredientEntities: List<IngredientEntity>): MutableList<Recipe> {

        if (value.isEmpty()) return arrayListOf()

        val listRecipes: MutableList<Recipe> = arrayListOf()

        val ingredients = ArrayList<Ingredient>(ingredientEntities.size)

        for (ingredientEntity in ingredientEntities) {
            ingredients.add(Ingredient(ingredientEntity.idIngredient, ingredientEntity.title))
        }
        value.forEach { recipe ->
            val recipe1 = Recipe(recipe.idRecipe, recipe.title, recipe.time, recipe.portion)
            ingredientEntities.forEach {
                ingredients.add(Ingredient(it.idIngredient, it.title))
            }
            listRecipes.add(recipe1)
        }

        return listRecipes
    }

    fun mapIngredients(listIngredients: MutableList<Ingredient>): MutableList<IngredientEntity> {
        val ingredientsEntities: MutableList<IngredientEntity> = arrayListOf()
        for (ingredient in listIngredients) {
            val actorEntity = IngredientEntity(ingredient.idIngredient, ingredient.title)
            ingredientsEntities.add(actorEntity)
        }
        return ingredientsEntities
    }

    fun mapRecipeIngredients(listRecipeIngredients: MutableList<RecipeIngredients>): MutableList<RecipeIngredientsEntity> {
        val recipeIngredientsEntity: MutableList<RecipeIngredientsEntity> = arrayListOf()
        for (recipeIngredient in listRecipeIngredients) {
            val recipeIngredientEntity = RecipeIngredientsEntity(recipeIngredient.id, recipeIngredient.idRecipe, recipeIngredient.idIngredient)
            recipeIngredientsEntity.add(recipeIngredientEntity)
        }
        return recipeIngredientsEntity
    }

    fun mapRecipe(recipes: MutableList<Recipe>): MutableList<RecipeEntity> {
        val recipesEtinity: MutableList<RecipeEntity> = arrayListOf()
        for (recipe in recipes) {
            val recipeEntity = RecipeEntity(recipe.idRecipe, recipe.title)
            recipesEtinity.add(recipeEntity)
        }
        return recipesEtinity
    }


}