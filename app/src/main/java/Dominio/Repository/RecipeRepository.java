package Dominio.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import Dominio.Models.Recipe;

public interface RecipeRepository {

    CompletableFuture<List<Recipe>> getAllRecipes();
    CompletableFuture<List<Recipe>> getAllRecipesFavoritesUser(String userId);

}
