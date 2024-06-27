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
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.graphics.Typeface;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class LoginActivity extends  AppCompatActivity  { 
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String username = "";
	private HashMap<String, Object> map = new HashMap<>();
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private ImageView imageview1;
	private TextView txt_titulo;
	private LinearLayout linear8;
	private LinearLayout linear3;
	private LinearLayout panel_login;
	private EditText et_nombre;
	private EditText et_correo;
	private EditText et_contra;
	private Button btn_iniciar;
	private Button btn_registrar;
	private Button btn_recuperar;
	private TextView txt_recuperarcontra;
	private TextView txt_registrar;
	private TextView txt_iniciarsesion;
	
	private FirebaseAuth users;
	private OnCompleteListener<Void> users_updateEmailListener;
	private OnCompleteListener<Void> users_updatePasswordListener;
	private OnCompleteListener<Void> users_emailVerificationSentListener;
	private OnCompleteListener<Void> users_deleteUserListener;
	private OnCompleteListener<Void> users_updateProfileListener;
	private OnCompleteListener<AuthResult> users_phoneAuthListener;
	private OnCompleteListener<AuthResult> users_googleSignInListener;
	private OnCompleteListener<AuthResult> _users_create_user_listener;
	private OnCompleteListener<AuthResult> _users_sign_in_listener;
	private OnCompleteListener<Void> _users_reset_password_listener;
	private DatabaseReference user = _firebase.getReference("user");
	private ChildEventListener _user_child_listener;
	private AlertDialog.Builder dialog;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.login);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		txt_titulo = (TextView) findViewById(R.id.txt_titulo);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		panel_login = (LinearLayout) findViewById(R.id.panel_login);
		et_nombre = (EditText) findViewById(R.id.et_nombre);
		et_correo = (EditText) findViewById(R.id.et_correo);
		et_contra = (EditText) findViewById(R.id.et_contra);
		btn_iniciar = (Button) findViewById(R.id.btn_iniciar);
		btn_registrar = (Button) findViewById(R.id.btn_registrar);
		btn_recuperar = (Button) findViewById(R.id.btn_recuperar);
		txt_recuperarcontra = (TextView) findViewById(R.id.txt_recuperarcontra);
		txt_registrar = (TextView) findViewById(R.id.txt_registrar);
		txt_iniciarsesion = (TextView) findViewById(R.id.txt_iniciarsesion);
		users = FirebaseAuth.getInstance();
		dialog = new AlertDialog.Builder(this);
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		btn_iniciar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if ((et_correo.getText().toString().length() > 0) && (et_contra.getText().toString().length() > 0)) {
					users.signInWithEmailAndPassword(et_correo.getText().toString(), et_contra.getText().toString()).addOnCompleteListener(LoginActivity.this, _users_sign_in_listener);
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "Rellena los campos solicitados.");
				}
			}
		});
		
		btn_registrar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (et_nombre.getText().toString().length() > 3) {
					if ((et_correo.getText().toString().length() > 0) && (et_contra.getText().toString().length() > 0)) {
						username = et_nombre.getText().toString();
						users.createUserWithEmailAndPassword(et_correo.getText().toString(), et_contra.getText().toString()).addOnCompleteListener(LoginActivity.this, _users_create_user_listener);
					}
					else {
						SketchwareUtil.showMessage(getApplicationContext(), "Rellena los campos solicitados.");
					}
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "Rellena los campos solicitados.");
				}
			}
		});
		
		btn_recuperar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (et_correo.getText().toString().length() > 0) {
					users.sendPasswordResetEmail(et_correo.getText().toString()).addOnCompleteListener(_users_reset_password_listener);
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "Por favor ingresa un correo electronico.");
				}
			}
		});
		
		txt_recuperarcontra.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				et_nombre.setVisibility(View.GONE);
				et_contra.setVisibility(View.GONE);
				btn_iniciar.setVisibility(View.GONE);
				btn_recuperar.setVisibility(View.VISIBLE);
			}
		});
		
		txt_registrar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				et_nombre.setVisibility(View.VISIBLE);
				btn_registrar.setVisibility(View.VISIBLE);
				btn_iniciar.setVisibility(View.GONE);
				txt_recuperarcontra.setVisibility(View.GONE);
				txt_registrar.setVisibility(View.GONE);
			}
		});
		
		txt_iniciarsesion.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				btn_recuperar.setVisibility(View.GONE);
				btn_registrar.setVisibility(View.GONE);
				btn_iniciar.setVisibility(View.VISIBLE);
				et_nombre.setVisibility(View.GONE);
				txt_iniciarsesion.setVisibility(View.GONE);
			}
		});
		
		_user_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
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
		user.addChildEventListener(_user_child_listener);
		
		users_updateEmailListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		users_updatePasswordListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		users_emailVerificationSentListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		users_deleteUserListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		users_phoneAuthListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task){
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		users_updateProfileListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		users_googleSignInListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task){
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		_users_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				if (_success) {
					map = new HashMap<>();
					map.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
					map.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
					map.put("username", username);
					map.put("type", "user");
					user.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map);
					SketchwareUtil.showMessage(getApplicationContext(), "Usuario registrado correctamente!");
					finish();
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), _errorMessage);
				}
			}
		};
		
		_users_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				if (_success) {
					SketchwareUtil.showMessage(getApplicationContext(), "Iniciaste sesion correctamente.");
					finish();
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), _errorMessage);
				}
			}
		};
		
		_users_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				if (_success) {
					dialog.setMessage("correo de recuperacion enviado correctamente.");
				}
				else {
					dialog.setMessage("no se pudo enviar el correo de recuperacion.");
				}
				dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						et_contra.setVisibility(View.VISIBLE);
						btn_iniciar.setVisibility(View.VISIBLE);
						btn_recuperar.setVisibility(View.GONE);
					}
				});
				dialog.create().show();
			}
		};
	}
	
	private void initializeLogic() {
		et_nombre.setVisibility(View.GONE);
		btn_registrar.setVisibility(View.GONE);
		btn_recuperar.setVisibility(View.GONE);
		txt_iniciarsesion.setVisibility(View.GONE);
		txt_registrar.setVisibility(View.VISIBLE);
		txt_recuperarcontra.setVisibility(View.VISIBLE);
		_radius(btn_iniciar, 10, 5, "#FF9800");
		_radius(btn_registrar, 10, 5, "#FF9800");
		_radius(btn_recuperar, 10, 5, "#FF9800");
		txt_titulo.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/bold.ttf"), 1);
		txt_iniciarsesion.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/thin.ttf"), 0);
		txt_registrar.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/thin.ttf"), 0);
		txt_recuperarcontra.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/thin.ttf"), 0);
		_rounds(et_nombre, 15, "FAFAFA", 5);
		_rounds(et_correo, 15, "FAFAFA", 5);
		_rounds(et_contra, 15, "FAFAFA", 5);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _radius (final View _view, final double _radius, final double _shadow, final String _color) {
		android.graphics.drawable.GradientDrawable ab = new android.graphics.drawable.GradientDrawable();
		
		
		ab.setColor(Color.parseColor(_color));
		
		ab.setCornerRadius((float) _radius);
		
		_view.setElevation((float) _shadow);
		
		_view.setBackground(ab);
		
		
		
	}
	
	
	public void _rounds (final View _view, final double _value, final String _color, final double _shadow) {
		android.graphics.drawable.GradientDrawable round_corner = new android.graphics.drawable.GradientDrawable(); round_corner.setColor(Color.parseColor("#"+_color)); round_corner.setCornerRadius(new Float(_value)); _view.setBackground(round_corner);
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