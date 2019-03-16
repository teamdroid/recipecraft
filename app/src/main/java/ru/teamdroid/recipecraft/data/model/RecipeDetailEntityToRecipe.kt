package ru.teamdroid.recipecraft.data.model

import java.util.*

class RecipeDetailEntityToRecipe : Mapper<Recipe, RecipeEntity>() {

    override fun map(value: Recipe): RecipeEntity = RecipeEntity(value.idRecipe, value.title, value.time, value.portion)
    override fun reverseMap(value: RecipeEntity) = Recipe(value.idRecipe, value.title, value.time, value.portion)

    fun mapDetailRecipe(value: List<RecipeEntity>, ingredientEntities: List<IngredientEntity>): MutableList<Recipe> {

        val f: MutableList<Recipe> = arrayListOf()

        val ingredients = ArrayList<Ingredient>(ingredientEntities.size)

        for (ingredientEntity in ingredientEntities) {
            ingredients.add(Ingredient(ingredientEntity.idIngredient, ingredientEntity.title))
        }
        value.forEach { recipe ->
            val recipe1 = Recipe(recipe.idRecipe, recipe.title, recipe.time, recipe.portion)
            ingredientEntities.forEach {
                ingredients.add(Ingredient(it.idIngredient, it.title))
            }
            f.add(recipe1)
        }


        return f
    }

    fun mapIngredients(listIngredients: MutableList<Ingredient>): MutableList<IngredientEntity> {
        val ingredientsEntities: MutableList<IngredientEntity> = arrayListOf()
        for (actor in listIngredients) {
            val actorEntity = IngredientEntity(actor.idIngredient, actor.title)
            ingredientsEntities.add(actorEntity)
        }
        return ingredientsEntities
    }

}