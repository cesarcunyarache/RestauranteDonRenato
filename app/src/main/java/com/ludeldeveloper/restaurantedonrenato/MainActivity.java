package com.ludeldeveloper.restaurantedonrenato;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;
import java.util.Timer;
import java.util.TimerTask;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import Data.Services.RecipeFirestoreService;
import Data.Services.UserFirestoreService;
import Dominio.Models.Recipe;


public class MainActivity extends  AppCompatActivity  {

	private Timer _timer = new Timer();

	private LinearLayout linear2;
	private LinearLayout linear3;
	private ImageView imageview1;
	private TextView textview1;

	private Intent intent = new Intent();
	private TimerTask timer;

	ArrayList<Recipe> recetas =  new ArrayList<>();
	ArrayList<Recipe> getRecipes =  new ArrayList<>();
	ArrayList<Recipe> recipesFavorites =  new ArrayList<>();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
		FirebaseFirestore.setLoggingEnabled(true);
		FirebaseFirestore db = FirebaseFirestore.getInstance();

		RecipeFirestoreService rc = new RecipeFirestoreService();


		// us.addRecipeToFavorites("lmEiRD4PzT9X7U2u2Tm2", "/recetas/OtTlFmfLAILUCxR56r8P");

		// Obtener todas las Recetas Firestore
		CompletableFuture<List<Recipe>> futureRecipes = rc.getAllRecipes();
		futureRecipes.thenAccept(recipes -> {
			for (Recipe recipe : recipes) {
				getRecipes.add(recipe);
			}
		}).exceptionally(e -> {
			System.err.println("Error: " + e.getMessage());
			return null;
		});


	   // Obtener recetas favoritas del usuario
		CompletableFuture<List<Recipe>> futureRecipesFavoritesUser = rc.getAllRecipesFavoritesUser("lmEiRD4PzT9X7U2u2Tm2");
		futureRecipesFavoritesUser.thenAccept(recipes -> {
			for (Recipe recipe : recipes) {
				recipesFavorites.add(recipe);
			}

			generateRecipes ();
		}).exceptionally(e -> {
			System.err.println("Error: " + e.getMessage());
			return null;
		});

	}


	private void generateRecipes () {
		for (Recipe recipe : getRecipes) {
			for (Recipe favoriteRecipe : recipesFavorites) {
				if (recipe.getRecipeId().equals(favoriteRecipe.getRecipeId())) {
					recipe.setFavorite(true);
					break;
				}
			}
		}
		System.out.println(getRecipes);
	}

	private void initialize(Bundle _savedInstanceState) {

		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		textview1 = (TextView) findViewById(R.id.textview1);
	}

	private void initializeLogic() {
		timer = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						intent.setAction(Intent.ACTION_VIEW);
						intent.setClass(getApplicationContext(), MenuActivity.class);
						startActivity(intent);
						finish();
					}
				});
			}
		};
		_timer.schedule(timer, (int)(3000));
	}

	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {

		super.onActivityResult(_requestCode, _resultCode, _data);

		switch (_requestCode) {

			default:
				break;
		}
	}

	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}

	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}

	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}

	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}

	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
				_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}

	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}

	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}

	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}

}
