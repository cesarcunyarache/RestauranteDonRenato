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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.EditText;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.content.Intent;
import android.net.Uri;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.OnProgressListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Continuation;
import java.io.File;
import android.view.View;
import android.widget.AdapterView;
import android.text.Editable;
import android.text.TextWatcher;
import android.graphics.Typeface;
import com.bumptech.glide.Glide;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;


public class MenuActivity extends  AppCompatActivity  { 
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private String tipo = "";
	private double length = 0;
	private double r = 0;
	private String value = "";
	private HashMap<String, Object> map = new HashMap<>();
	private String type_user = "";
	
	private ArrayList<HashMap<String, Object>> lrecetas = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> lfav = new ArrayList<>();
	private ArrayList<String> keys = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear_menu;
	private LinearLayout linear_titulo;
	private LinearLayout linear_panel;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private TextView txt_titulo;
	private TextView txt_titulo2;
	private ImageView imageview1;
	private LinearLayout linear_home;
	private LinearLayout linear_favoritos;
	private LinearLayout linear_settings;
	private LinearLayout linear_scroll;
	private LinearLayout linear17;
	private ListView lista_recetas;
	private HorizontalScrollView hscroll1;
	private LinearLayout linear11;
	private LinearLayout categoria_todos;
	private LinearLayout categoria_almuerzos;
	private LinearLayout categoria_bebidas;
	private LinearLayout categoria_entradas;
	private LinearLayout categoria_postres;
	private TextView txt_todos;
	private TextView txt_almuerzos;
	private TextView txt_bebidas;
	private TextView txt_entradas;
	private TextView txt_postres;
	private EditText edittext1;
	private TextView titulo_favoritos;
	private ListView listview1;
	private TextView titulo_perfil;
	private LinearLayout linear19;
	private LinearLayout linear12;
	private LinearLayout linear14;
	private LinearLayout linear15;
	private LinearLayout linear16;
	private TextView name;
	private TextView txt_name;
	private TextView perfil_correo;
	private TextView txt_correo;
	private TextView txt_cerrar_sesion;
	private LinearLayout menu_home;
	private LinearLayout menu_favoritos;
	private LinearLayout menu_settings;
	private ImageView img_menu_home;
	private ImageView img_menu_favoritos;
	private ImageView img_menu_settings;
	
	private DatabaseReference recetas = _firebase.getReference("recetas");
	private ChildEventListener _recetas_child_listener;
	private Intent subir = new Intent();
	private Intent receta = new Intent();
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
	private Intent login = new Intent();
	private Intent menu = new Intent();
	private AlertDialog.Builder dialog;
	private AlertDialog.Builder favoritos;
	private AlertDialog.Builder settings;
	private DatabaseReference user = _firebase.getReference("user");
	private ChildEventListener _user_child_listener;
	private AlertDialog.Builder cuenta;
	private AlertDialog.Builder delete;
	private StorageReference fotos = _firebase_storage.getReference("recetas");
	private OnCompleteListener<Uri> _fotos_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _fotos_download_success_listener;
	private OnSuccessListener _fotos_delete_success_listener;
	private OnProgressListener _fotos_upload_progress_listener;
	private OnProgressListener _fotos_download_progress_listener;
	private OnFailureListener _fotos_failure_listener;
	private Intent editar = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.menu);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		}
		else {
			initializeLogic();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear_menu = (LinearLayout) findViewById(R.id.linear_menu);
		linear_titulo = (LinearLayout) findViewById(R.id.linear_titulo);
		linear_panel = (LinearLayout) findViewById(R.id.linear_panel);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		txt_titulo = (TextView) findViewById(R.id.txt_titulo);
		txt_titulo2 = (TextView) findViewById(R.id.txt_titulo2);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		linear_home = (LinearLayout) findViewById(R.id.linear_home);
		linear_favoritos = (LinearLayout) findViewById(R.id.linear_favoritos);
		linear_settings = (LinearLayout) findViewById(R.id.linear_settings);
		linear_scroll = (LinearLayout) findViewById(R.id.linear_scroll);
		linear17 = (LinearLayout) findViewById(R.id.linear17);
		lista_recetas = (ListView) findViewById(R.id.lista_recetas);
		hscroll1 = (HorizontalScrollView) findViewById(R.id.hscroll1);
		linear11 = (LinearLayout) findViewById(R.id.linear11);
		categoria_todos = (LinearLayout) findViewById(R.id.categoria_todos);
		categoria_almuerzos = (LinearLayout) findViewById(R.id.categoria_almuerzos);
		categoria_bebidas = (LinearLayout) findViewById(R.id.categoria_bebidas);
		categoria_entradas = (LinearLayout) findViewById(R.id.categoria_entradas);
		categoria_postres = (LinearLayout) findViewById(R.id.categoria_postres);
		txt_todos = (TextView) findViewById(R.id.txt_todos);
		txt_almuerzos = (TextView) findViewById(R.id.txt_almuerzos);
		txt_bebidas = (TextView) findViewById(R.id.txt_bebidas);
		txt_entradas = (TextView) findViewById(R.id.txt_entradas);
		txt_postres = (TextView) findViewById(R.id.txt_postres);
		edittext1 = (EditText) findViewById(R.id.edittext1);
		titulo_favoritos = (TextView) findViewById(R.id.titulo_favoritos);
		listview1 = (ListView) findViewById(R.id.listview1);
		titulo_perfil = (TextView) findViewById(R.id.titulo_perfil);
		linear19 = (LinearLayout) findViewById(R.id.linear19);
		linear12 = (LinearLayout) findViewById(R.id.linear12);
		linear14 = (LinearLayout) findViewById(R.id.linear14);
		linear15 = (LinearLayout) findViewById(R.id.linear15);
		linear16 = (LinearLayout) findViewById(R.id.linear16);
		name = (TextView) findViewById(R.id.name);
		txt_name = (TextView) findViewById(R.id.txt_name);
		perfil_correo = (TextView) findViewById(R.id.perfil_correo);
		txt_correo = (TextView) findViewById(R.id.txt_correo);
		txt_cerrar_sesion = (TextView) findViewById(R.id.txt_cerrar_sesion);
		menu_home = (LinearLayout) findViewById(R.id.menu_home);
		menu_favoritos = (LinearLayout) findViewById(R.id.menu_favoritos);
		menu_settings = (LinearLayout) findViewById(R.id.menu_settings);
		img_menu_home = (ImageView) findViewById(R.id.img_menu_home);
		img_menu_favoritos = (ImageView) findViewById(R.id.img_menu_favoritos);
		img_menu_settings = (ImageView) findViewById(R.id.img_menu_settings);
		users = FirebaseAuth.getInstance();
		dialog = new AlertDialog.Builder(this);
		favoritos = new AlertDialog.Builder(this);
		settings = new AlertDialog.Builder(this);
		cuenta = new AlertDialog.Builder(this);
		delete = new AlertDialog.Builder(this);
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				user.addChildEventListener(_user_child_listener);
				if (type_user.equals("admin")) {
					cuenta.setTitle("Aviso Importante!");
					cuenta.setMessage("¿deseas agregar una receta?");
					cuenta.setPositiveButton("si", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							editar.setAction(Intent.ACTION_VIEW);
							editar.setClass(getApplicationContext(), SubirActivity.class);
							editar.putExtra("editar", "false");
							startActivity(editar);
						}
					});
					cuenta.setNegativeButton("no", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					cuenta.create().show();
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "Restaurante Don Renato");
				}
			}
		});
		
		lista_recetas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				receta.setAction(Intent.ACTION_VIEW);
				receta.setClass(getApplicationContext(), RecetaActivity.class);
				receta.putExtra("key", keys.get((int)(_position)));
				receta.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(receta);
			}
		});
		
		lista_recetas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				
				return true;
			}
		});
		
		categoria_todos.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				txt_todos.setTextColor(0xFFF5F5F5);
				txt_almuerzos.setTextColor(0xFF212121);
				txt_bebidas.setTextColor(0xFF212121);
				txt_entradas.setTextColor(0xFF212121);
				txt_postres.setTextColor(0xFF212121);
				_radius(categoria_todos, 25, 5, "#212121");
				_radius(categoria_almuerzos, 25, 5, "#EEEEEE");
				_radius(categoria_bebidas, 25, 5, "#EEEEEE");
				_radius(categoria_entradas, 25, 5, "#EEEEEE");
				_radius(categoria_postres, 25, 5, "#EEEEEE");
				tipo = "todos";
				recetas.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						lrecetas = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								lrecetas.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						lista_recetas.setAdapter(new Lista_recetasAdapter(lrecetas));
						((BaseAdapter)lista_recetas.getAdapter()).notifyDataSetChanged();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
		});
		
		categoria_almuerzos.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				txt_todos.setTextColor(0xFF212121);
				txt_almuerzos.setTextColor(0xFFF5F5F5);
				txt_bebidas.setTextColor(0xFF212121);
				txt_entradas.setTextColor(0xFF212121);
				txt_postres.setTextColor(0xFF212121);
				_radius(categoria_almuerzos, 25, 5, "#212121");
				_radius(categoria_todos, 25, 5, "#EEEEEE");
				_radius(categoria_bebidas, 25, 5, "#EEEEEE");
				_radius(categoria_entradas, 25, 5, "#EEEEEE");
				_radius(categoria_postres, 25, 5, "#EEEEEE");
				tipo = "almuerzos";
				recetas.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						lrecetas = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								lrecetas.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						lista_recetas.setAdapter(new Lista_recetasAdapter(lrecetas));
						((BaseAdapter)lista_recetas.getAdapter()).notifyDataSetChanged();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
		});
		
		categoria_bebidas.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				txt_bebidas.setTextColor(0xFFF5F5F5);
				txt_almuerzos.setTextColor(0xFF212121);
				txt_todos.setTextColor(0xFF212121);
				txt_entradas.setTextColor(0xFF212121);
				txt_postres.setTextColor(0xFF212121);
				_radius(categoria_bebidas, 25, 5, "#212121");
				_radius(categoria_almuerzos, 25, 5, "#EEEEEE");
				_radius(categoria_todos, 25, 5, "#EEEEEE");
				_radius(categoria_entradas, 25, 5, "#EEEEEE");
				_radius(categoria_postres, 25, 5, "#EEEEEE");
				tipo = "bebidas";
				recetas.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						lrecetas = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								lrecetas.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						lista_recetas.setAdapter(new Lista_recetasAdapter(lrecetas));
						((BaseAdapter)lista_recetas.getAdapter()).notifyDataSetChanged();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
		});
		
		categoria_entradas.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				txt_entradas.setTextColor(0xFFF5F5F5);
				txt_almuerzos.setTextColor(0xFF212121);
				txt_bebidas.setTextColor(0xFF212121);
				txt_todos.setTextColor(0xFF212121);
				txt_postres.setTextColor(0xFF212121);
				_radius(categoria_entradas, 25, 5, "#212121");
				_radius(categoria_almuerzos, 25, 5, "#EEEEEE");
				_radius(categoria_bebidas, 25, 5, "#EEEEEE");
				_radius(categoria_todos, 25, 5, "#EEEEEE");
				_radius(categoria_postres, 25, 5, "#EEEEEE");
				tipo = "entradas";
				recetas.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						lrecetas = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								lrecetas.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						lista_recetas.setAdapter(new Lista_recetasAdapter(lrecetas));
						((BaseAdapter)lista_recetas.getAdapter()).notifyDataSetChanged();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
		});
		
		categoria_postres.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				txt_postres.setTextColor(0xFFF5F5F5);
				txt_almuerzos.setTextColor(0xFF212121);
				txt_bebidas.setTextColor(0xFF212121);
				txt_entradas.setTextColor(0xFF212121);
				txt_todos.setTextColor(0xFF212121);
				_radius(categoria_postres, 25, 5, "#212121");
				_radius(categoria_almuerzos, 25, 5, "#EEEEEE");
				_radius(categoria_bebidas, 25, 5, "#EEEEEE");
				_radius(categoria_entradas, 25, 5, "#EEEEEE");
				_radius(categoria_todos, 25, 5, "#EEEEEE");
				tipo = "postres";
				recetas.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						lrecetas = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								lrecetas.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						lista_recetas.setAdapter(new Lista_recetasAdapter(lrecetas));
						((BaseAdapter)lista_recetas.getAdapter()).notifyDataSetChanged();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
		});
		
		edittext1.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				recetas.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						lrecetas = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								lrecetas.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						length = lrecetas.size();
						r = length - 1;
						for(int _repeat17 = 0; _repeat17 < (int)(length); _repeat17++) {
							value = lrecetas.get((int)r).get("title").toString();
							if (!(edittext1.getText().toString().length() > value.length()) && value.toLowerCase().contains(edittext1.getText().toString())) {
								
							}
							else {
								lrecetas.remove((int)(r));
								lista_recetas.setAdapter(new Lista_recetasAdapter(lrecetas));
								((BaseAdapter)lista_recetas.getAdapter()).notifyDataSetChanged();
							}
							r--;
						}
						lista_recetas.setAdapter(new Lista_recetasAdapter(lrecetas));
						((BaseAdapter)lista_recetas.getAdapter()).notifyDataSetChanged();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		txt_cerrar_sesion.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				dialog.setTitle("¡Advertencia!");
				dialog.setMessage("¿quieres cerrar sesion de tu cuenta?");
				dialog.setPositiveButton("si", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						FirebaseAuth.getInstance().signOut();
						menu.setAction(Intent.ACTION_VIEW);
						menu.setClass(getApplicationContext(), MenuActivity.class);
						menu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(menu);
						finish();
					}
				});
				dialog.setNegativeButton("no", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				dialog.create().show();
			}
		});
		
		menu_home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				linear_home.setVisibility(View.VISIBLE);
				linear_settings.setVisibility(View.GONE);
				linear_favoritos.setVisibility(View.GONE);
				menu_home.setBackgroundColor(0xFFF5F5F5);
				menu_favoritos.setBackgroundColor(0xFFFAFAFA);
				menu_settings.setBackgroundColor(0xFFFAFAFA);
			}
		});
		
		menu_favoritos.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
					linear_favoritos.setVisibility(View.VISIBLE);
					linear_home.setVisibility(View.GONE);
					linear_settings.setVisibility(View.GONE);
					menu_favoritos.setBackgroundColor(0xFFF5F5F5);
					menu_home.setBackgroundColor(0xFFFAFAFA);
					menu_settings.setBackgroundColor(0xFFFAFAFA);
				}
				else {
					favoritos.setMessage("Para acceder a esta seccion debes iniciar sesion!");
					favoritos.setPositiveButton("ok", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							login.setAction(Intent.ACTION_VIEW);
							login.setClass(getApplicationContext(), LoginActivity.class);
							startActivity(login);
						}
					});
					favoritos.setNegativeButton("despues", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					favoritos.create().show();
				}
			}
		});
		
		menu_settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
					linear_home.setVisibility(View.GONE);
					linear_favoritos.setVisibility(View.GONE);
					menu_settings.setBackgroundColor(0xFFF5F5F5);
					menu_favoritos.setBackgroundColor(0xFFFAFAFA);
					menu_home.setBackgroundColor(0xFFFAFAFA);
					linear_settings.setVisibility(View.VISIBLE);
					txt_correo.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
					user.addChildEventListener(_user_child_listener);
				}
				else {
					settings.setMessage("para acceder a esta seccion debes iniciar sesion, quieres hacerlo?");
					settings.setPositiveButton("si", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							login.setAction(Intent.ACTION_VIEW);
							login.setClass(getApplicationContext(), LoginActivity.class);
							startActivity(login);
						}
					});
					settings.setNegativeButton("no", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					settings.create().show();
				}
			}
		});
		
		_recetas_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				keys.add(_childKey);
				recetas.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						lrecetas = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								lrecetas.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						lista_recetas.setAdapter(new Lista_recetasAdapter(lrecetas));
						((BaseAdapter)lista_recetas.getAdapter()).notifyDataSetChanged();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
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
				SketchwareUtil.showMessage(getApplicationContext(), "Receta eliminada correctamente.");
				recetas.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						lrecetas = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								lrecetas.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						lista_recetas.setAdapter(new Lista_recetasAdapter(lrecetas));
						((BaseAdapter)lista_recetas.getAdapter()).notifyDataSetChanged();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		recetas.addChildEventListener(_recetas_child_listener);
		
		_user_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
					if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
						txt_name.setText(_childValue.get("username").toString());
						type_user = _childValue.get("type").toString();
					}
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
		user.addChildEventListener(_user_child_listener);
		
		_fotos_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_fotos_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_fotos_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				
			}
		};
		
		_fotos_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_fotos_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_fotos_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				
			}
		};
		
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
				
			}
		};
		
		_users_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_users_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	
	private void initializeLogic() {
		txt_todos.setTextColor(0xFFF5F5F5);
		_radius(categoria_todos, 25, 5, "#212121");
		_radius(categoria_almuerzos, 25, 5, "#EEEEEE");
		_radius(categoria_bebidas, 25, 5, "#EEEEEE");
		_radius(categoria_entradas, 25, 5, "#EEEEEE");
		_radius(categoria_postres, 25, 5, "#EEEEEE");
		img_menu_home.setColorFilter(0xFF263238, PorterDuff.Mode.MULTIPLY);
		txt_titulo.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/semibold.ttf"), 1);
		txt_titulo2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/medio.ttf"), 0);
		txt_todos.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/medio.ttf"), 0);
		txt_almuerzos.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/medio.ttf"), 0);
		txt_bebidas.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/medio.ttf"), 0);
		txt_entradas.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/medio.ttf"), 0);
		txt_postres.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/medio.ttf"), 0);
		_elevation(linear_titulo, 5);
		tipo = "todos";
		recetas.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				lrecetas = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						System.out.println(_map);
						lrecetas.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				lista_recetas.setAdapter(new Lista_recetasAdapter(lrecetas));
				((BaseAdapter)lista_recetas.getAdapter()).notifyDataSetChanged();
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
		_ocultarScroll(hscroll1);
		_ocultarScroll(lista_recetas);
		_elevation(linear_menu, 5);
		_elevation(linear_menu, 5);
		_elevation(edittext1, 2);
		_rounds(txt_cerrar_sesion, 10, "FF9800", 3);
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
	
	
	public void _ocultarScroll (final View _view) {
		_view.setVerticalScrollBarEnabled(false);
		_view.setHorizontalScrollBarEnabled(false);
	}
	
	
	public void _elevation (final View _view, final double _shadow) {
		android.graphics.drawable.GradientDrawable ab = new android.graphics.drawable.GradientDrawable();
		
		_view.setElevation((float) _shadow);
		
	}
	
	
	public void _rounds (final View _view, final double _value, final String _color, final double _shadow) {
		android.graphics.drawable.GradientDrawable round_corner = new android.graphics.drawable.GradientDrawable(); round_corner.setColor(Color.parseColor("#"+_color)); round_corner.setCornerRadius(new Float(_value)); _view.setBackground(round_corner);
		_view.setElevation((float) _shadow);
	}
	
	
	public class Lista_recetasAdapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Lista_recetasAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.recetas, null);
			}
			
			final LinearLayout linear8 = (LinearLayout) _view.findViewById(R.id.linear8);
			final LinearLayout linear6 = (LinearLayout) _view.findViewById(R.id.linear6);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final LinearLayout linear7 = (LinearLayout) _view.findViewById(R.id.linear7);
			final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final LinearLayout linear4 = (LinearLayout) _view.findViewById(R.id.linear4);
			final TextView titulo_recetas = (TextView) _view.findViewById(R.id.titulo_recetas);
			final TextView categoria_recetas = (TextView) _view.findViewById(R.id.categoria_recetas);
			final LinearLayout linear5 = (LinearLayout) _view.findViewById(R.id.linear5);
			final ImageView img_delete = (ImageView) _view.findViewById(R.id.img_delete);
			final ImageView img_edit = (ImageView) _view.findViewById(R.id.img_edit);
			final ImageView favorito_recetas = (ImageView) _view.findViewById(R.id.favorito_recetas);
			
			user.addChildEventListener(_user_child_listener);
			if (type_user.equals("admin")) {
				img_delete.setVisibility(View.VISIBLE);
				img_edit.setVisibility(View.VISIBLE);
			}
			else {
				img_delete.setVisibility(View.GONE);
				img_edit.setVisibility(View.GONE);
			}
			if (tipo.equals("todos")) {
				linear8.setVisibility(View.VISIBLE);
				titulo_recetas.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/medio.ttf"), 1);
				categoria_recetas.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/thin.ttf"), 1);
				Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("url").toString())).into(imageview1);
				titulo_recetas.setText(_data.get((int)_position).get("title").toString());
				categoria_recetas.setText(_data.get((int)_position).get("category").toString());
				_radius(linear6, 10, 5, "#F5F5F5");
			}
			else {
				if (_data.get((int)_position).get("category").toString().equals(tipo)) {
					linear8.setVisibility(View.VISIBLE);
					titulo_recetas.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/medio.ttf"), 1);
					categoria_recetas.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/thin.ttf"), 1);
					Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("url").toString())).into(imageview1);
					titulo_recetas.setText(_data.get((int)_position).get("title").toString());
					categoria_recetas.setText(tipo);
					_radius(linear6, 10, 5, "#F5F5F5");
				}
				else {
					linear8.setVisibility(View.GONE);
				}
			}
			img_delete.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					delete.setTitle("Advertencia!");
					delete.setMessage("¿Quieres eliminar esta receta?");
					delete.setPositiveButton("si", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							recetas.child(keys.get((int)(_position))).removeValue();
							keys.remove((int)(_position));
						}
					});
					delete.setNegativeButton("no", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					delete.create().show();
				}
			});
			img_edit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					editar.setAction(Intent.ACTION_VIEW);
					editar.setClass(getApplicationContext(), SubirActivity.class);
					editar.putExtra("editar", "true");
					editar.putExtra("key", keys.get((int)(_position)));
					startActivity(editar);
				}
			});
			
			return _view;
		}
	}
	
	public class Listview1Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.recetas, null);
			}
			
			final LinearLayout linear8 = (LinearLayout) _view.findViewById(R.id.linear8);
			final LinearLayout linear6 = (LinearLayout) _view.findViewById(R.id.linear6);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final LinearLayout linear7 = (LinearLayout) _view.findViewById(R.id.linear7);
			final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final LinearLayout linear4 = (LinearLayout) _view.findViewById(R.id.linear4);
			final TextView titulo_recetas = (TextView) _view.findViewById(R.id.titulo_recetas);
			final TextView categoria_recetas = (TextView) _view.findViewById(R.id.categoria_recetas);
			final LinearLayout linear5 = (LinearLayout) _view.findViewById(R.id.linear5);
			final ImageView img_delete = (ImageView) _view.findViewById(R.id.img_delete);
			final ImageView img_edit = (ImageView) _view.findViewById(R.id.img_edit);
			final ImageView favorito_recetas = (ImageView) _view.findViewById(R.id.favorito_recetas);
			
			
			
			return _view;
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
