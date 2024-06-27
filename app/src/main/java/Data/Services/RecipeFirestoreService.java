package Data.Services;

import android.os.Build;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import Dominio.Models.Recipe;
import Dominio.Repository.RecipeRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RecipeFirestoreService implements RecipeRepository {

    private FirebaseFirestore db;

    public RecipeFirestoreService() {
        this.db = FirebaseFirestore.getInstance();
    }

    @Override
    public CompletableFuture<List<Recipe>> getAllRecipes() {
        CompletableFuture<List<Recipe>> future = new CompletableFuture<>();
        db.collection("recetas").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Recipe> recipes = new ArrayList<>();
                        QuerySnapshot result = task.getResult();
                        if (result != null) {
                            for (QueryDocumentSnapshot document : result) {
                                //UserFirestoreService us = new UserFirestoreService();
                                // us.addRecipeToFavorites("lmEiRD4PzT9X7U2u2Tm2", document.getReference());
                                Recipe recipe = Recipe.fromMap(document.getData());
                                recipe.setRecipeId(document.getId());
                                recipes.add(recipe);
                            }
                        }
                        future.complete(recipes);
                    } else {
                        future.completeExceptionally(new Exception("Error getting documents: " + task.getException()));

                    }
                });
        return future;
    }


    @Override
    public CompletableFuture<List<Recipe>> getAllRecipesFavoritesUser(String userId) {
        CompletableFuture<List<Recipe>> future = new CompletableFuture<>();

        db.collection("user").document(userId).get()
                .addOnCompleteListener(userTask -> {
                    if (userTask.isSuccessful()) {
                        DocumentSnapshot userDoc = userTask.getResult();
                        if (userDoc.exists()) {
                            List<DocumentReference> favoritesRefs = (List<DocumentReference>) userDoc.get("favorites");
                            if (favoritesRefs != null && !favoritesRefs.isEmpty()) {
                                List<CompletableFuture<Recipe>> recipeFutures = new ArrayList<>();

                                for (DocumentReference favoriteRef : favoritesRefs) {
                                    CompletableFuture<Recipe> recipeFuture = new CompletableFuture<>();
                                    // Se necesita guardar el favorite ref
                                    favoriteRef.get().addOnCompleteListener(favoriteTask -> {
                                        if (favoriteTask.isSuccessful()) {
                                            DocumentSnapshot favoriteDoc = favoriteTask.getResult();
                                            if (favoriteDoc.exists()) {
                                                Recipe recipe = Recipe.fromMap(favoriteDoc.getData());
                                                recipe.setRecipeId(favoriteDoc.getId());
                                                recipeFuture.complete(recipe);
                                            } else {
                                                recipeFuture.completeExceptionally(new Exception("Favorite document does not exist."));
                                            }
                                        } else {
                                            recipeFuture.completeExceptionally(favoriteTask.getException());
                                        }
                                    });
                                    recipeFutures.add(recipeFuture);
                                }

                                CompletableFuture<Void> allRecipesFuture = CompletableFuture.allOf(recipeFutures.toArray(new CompletableFuture[0]));
                                allRecipesFuture.thenApply(v -> {
                                    List<Recipe> recipes = new ArrayList<>();
                                    for (CompletableFuture<Recipe> recipeFuture : recipeFutures) {
                                        try {
                                            recipes.add(recipeFuture.get());
                                        } catch (Exception e) {
                                            future.completeExceptionally(e);
                                        }
                                    }
                                    future.complete(recipes);
                                    return null;
                                }).exceptionally(e -> {
                                    future.completeExceptionally(e);
                                    return null;
                                });
                            } else {
                                future.complete(new ArrayList<>()); // No hay favoritos, devolver lista vacía
                            }
                        } else {
                            future.completeExceptionally(new Exception("User document not found."));
                        }
                    } else {
                        future.completeExceptionally(userTask.getException());
                    }
                })
                .addOnFailureListener(e -> {
                    future.completeExceptionally(e);
                });

        return future;
    }


    public interface RecipeCallback {
        void onCallback(Recipe recipe);
    }

    public interface RecipesCallback {
        void onCallback(Recipe recipe);
    }
}

