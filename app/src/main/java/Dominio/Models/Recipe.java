package Dominio.Models;


import java.util.HashMap;
import java.util.Map;

public class Recipe {
    private String recipeId;
    private String category;
    private String ingredients;
    private String introduction;

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeId='" + recipeId + '\'' +
                ", category='" + category + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", introduction='" + introduction + '\'' +
                ", preparation='" + preparation + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", isFavorite=" + isFavorite +
                '}';
    }

    private String preparation;
    private String title;
    private String url;
    private boolean isFavorite;

    public Recipe(String category, String ingredients, String introduction, String preparation, String title, String url) {
        this.category = category;
        this.ingredients = ingredients;
        this.introduction = introduction;
        this.preparation = preparation;
        this.title = title;
        this.url = url;
        this.isFavorite = false;
    }

    public Recipe() {
    }

    public static Recipe fromMap(Map<String, Object> map) {
        Recipe recipe = new Recipe();
        recipe.setCategory((String) map.get("category"));
        recipe.setIngredients((String) map.get("ingredients"));
        recipe.setIntroduction((String) map.get("introduction"));
        recipe.setPreparation((String) map.get("preparation"));
        recipe.setTitle((String) map.get("title"));
        recipe.setUrl((String) map.get("url"));
        return recipe;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> recipeMap = new HashMap<>();
        recipeMap.put("category", category);
        recipeMap.put("ingredients", ingredients);
        recipeMap.put("introduction", introduction);
        recipeMap.put("preparation", preparation);
        recipeMap.put("title", title);
        recipeMap.put("url", url);
        return recipeMap;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

}
