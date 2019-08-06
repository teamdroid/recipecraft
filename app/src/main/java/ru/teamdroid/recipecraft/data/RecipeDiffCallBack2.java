package ru.teamdroid.recipecraft.data;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import ru.teamdroid.recipecraft.data.model.Recipe;

public class RecipeDiffCallBack2 extends DiffUtil.ItemCallback<Recipe> {

    public RecipeDiffCallBack2() {
    }

    @Override
    public boolean areItemsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
        return oldItem.getIdRecipe() == newItem.getIdRecipe();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
        return oldItem.getTitle().equals(newItem.getTitle());
    }
}
