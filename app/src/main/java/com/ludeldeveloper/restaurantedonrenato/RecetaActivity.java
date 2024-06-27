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
import java.util.regex.*;
import java.text.*;
import org.json.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.graphics.Typeface;
import com.bumptech.glide.Glide;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class RecetaActivity extends  AppCompatActivity  { 
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> lre = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> lrecetas = new ArrayList<>();
	
	private LinearLayout linear_barra;
	private ScrollView vscroll1;
	private ImageView imageview1;
	private LinearLayout linear7;
	private ImageView imageview4;
	private LinearLayout linear2;
	private ImageView img_portada;
	private LinearLayout linear3;
	private LinearLayout linear5;
	private TextView titulo_receta;
	private LinearLayout linear4;
	private TextView textview1;
	private ImageView imageview3;
	private TextView txt_introduccion;
	private TextView textview3;
	private TextView txt_ingredientes;
	private TextView textview5;
	private TextView txt_preparacion;
	
	private DatabaseReference recetas = _firebase.getReference("recetas");
	private ChildEventListener _recetas_child_listener;
	private Intent receta = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.receta);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear_barra = (LinearLayout) findViewById(R.id.linear_barra);
		vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		img_portada = (ImageView) findViewById(R.id.img_portada);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		titulo_receta = (TextView) findViewById(R.id.titulo_receta);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		textview1 = (TextView) findViewById(R.id.textview1);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		txt_introduccion = (TextView) findViewById(R.id.txt_introduccion);
		textview3 = (TextView) findViewById(R.id.textview3);
		txt_ingredientes = (TextView) findViewById(R.id.txt_ingredientes);
		textview5 = (TextView) findViewById(R.id.textview5);
		txt_preparacion = (TextView) findViewById(R.id.txt_preparacion);
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		_recetas_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (getIntent().getStringExtra("key").equals(_childKey)) {
					titulo_receta.setText(_childValue.get("title").toString());
					txt_introduccion.setText(_childValue.get("introduction").toString());
					txt_ingredientes.setText(_childValue.get("ingredients").toString());
					txt_preparacion.setText(_childValue.get("preparation").toString());
					Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("url").toString())).into(img_portada);
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		recetas.addChildEventListener(_recetas_child_listener);
	}
	
	private void initializeLogic() {
		titulo_receta.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/bold.ttf"), 1);
		imageview3.setColorFilter(0xFFFFEB3B, PorterDuff.Mode.MULTIPLY);
		textview3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/semibold.ttf"), 0);
		textview5.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/semibold.ttf"), 0);
		_elevation(linear_barra, 5);
		recetas.addChildEventListener(_recetas_child_listener);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _elevation (final View _view, final double _shadow) {
		android.graphics.drawable.GradientDrawable ab = new android.graphics.drawable.GradientDrawable();
		
		_view.setElevation((float) _shadow);
		
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