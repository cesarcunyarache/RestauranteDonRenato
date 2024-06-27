package Data.Services;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import Dominio.Repository.UserRepository;

public class UserFirestoreService implements UserRepository {
    private FirebaseFirestore db;

    public UserFirestoreService() {
        this.db = FirebaseFirestore.getInstance();
    }

    @Override
    public void addRecipeToFavorites(String userId, DocumentReference newRecipeReference) {
        DocumentReference userRef = db.collection("user").document(userId);
        userRef.update("favorites", FieldValue.arrayUnion(newRecipeReference))
                .addOnSuccessListener(aVoid -> {

                })
                .addOnFailureListener(e -> {

                });
    }

    @Override
    public void removeRecipeFromFavorites(String userId, DocumentReference recipeReferenceToRemove) {
        DocumentReference userRef = db.collection("user").document(userId);
        userRef.update("favorites", FieldValue.arrayRemove(recipeReferenceToRemove))
                .addOnSuccessListener(aVoid -> {
                })
                .addOnFailureListener(e -> {
                });
    }


}
