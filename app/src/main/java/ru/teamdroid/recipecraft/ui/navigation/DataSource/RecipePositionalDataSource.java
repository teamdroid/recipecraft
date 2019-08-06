package ru.teamdroid.recipecraft.ui.navigation.DataSource;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import java.util.List;

import ru.teamdroid.recipecraft.data.model.Recipe;
import ru.teamdroid.recipecraft.ui.base.SortRecipes;
import ru.teamdroid.recipecraft.ui.navigation.presenters.RecipesPresenter;

public class RecipePositionalDataSource extends PositionalDataSource<Recipe> {


    private RecipesPresenter recipesPresenter;

    public RecipePositionalDataSource(RecipesPresenter recipesPresenter) {
        this.recipesPresenter = recipesPresenter;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Recipe> callback) {
        recipesPresenter.loadRecipes(false, SortRecipes.ByNewer, params.requestedStartPosition);
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Recipe> callback) {
        recipesPresenter.loadRecipes(false, SortRecipes.ByNewer, params.startPosition);
    }
}
