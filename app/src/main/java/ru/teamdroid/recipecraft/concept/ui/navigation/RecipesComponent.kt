package ru.teamdroid.recipecraft.concept.ui.navigation

import dagger.Component
import ru.teamdroid.recipecraft.concept.data.RecipeRepositoryComponent
import ru.teamdroid.recipecraft.concept.ui.base.ActivityScope
import ru.teamdroid.recipecraft.concept.ui.navigation.fragments.RecipesFragment
import ru.teamdroid.recipecraft.concept.ui.navigation.modules.RecipesPresenterModule
import ru.teamdroid.recipecraft.concept.util.schedulers.SchedulerModule

@ActivityScope
@Component(modules = [RecipesPresenterModule::class, SchedulerModule::class], dependencies = [RecipeRepositoryComponent::class])
interface RecipesComponent {
    fun inject(recipesFragment: RecipesFragment)
}