package Dominio.Repository;

import com.google.firebase.firestore.DocumentReference;

public interface UserRepository {

    void addRecipeToFavorites(String userId, DocumentReference newRecipeReference);
    void removeRecipeFromFavorites(String userId, DocumentReference recipeReferenceToRemove);

}
