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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ScrollView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.content.Intent;
import android.content.ClipData;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.OnProgressListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Continuation;
import android.net.Uri;
import java.io.File;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.graphics.Typeface;
import com.bumptech.glide.Glide;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;


public class SubirActivity extends  AppCompatActivity  { 
	
	public final int REQ_CD_FP = 101;
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private HashMap<String, Object> lrecetas = new HashMap<>();
	private String categoria = "";
	private String string = "";
	private String name = "";
	private double nspinner = 0;
	
	private ArrayList<String> lcategorias = new ArrayList<>();
	
	private LinearLayout linear_barrq;
	private LinearLayout linear2;
	private ImageView imageview1;
	private TextView textview2;
	private ScrollView vscroll1;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private TextView url;
	private LinearLayout linear5;
	private ImageView imageview2;
	private Button btn_imagen;
	private TextView textview5;
	private EditText et_titulo;
	private TextView textview4;
	private EditText et_descripcion;
	private TextView textview6;
	private EditText et_ingredientes;
	private TextView textview7;
	private EditText et_preparacion;
	private TextView textview3;
	private Spinner sp_categoria;
	private Button btn_subir;
	
	private DatabaseReference recetas = _firebase.getReference("recetas");
	private ChildEventListener _recetas_child_listener;
	private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
	private StorageReference fotos = _firebase_storage.getReference("recetas");
	private OnCompleteListener<Uri> _fotos_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _fotos_download_success_listener;
	private OnSuccessListener _fotos_delete_success_listener;
	private OnProgressListener _fotos_upload_progress_listener;
	private OnProgressListener _fotos_download_progress_listener;
	private OnFailureListener _fotos_failure_listener;
	private Intent editar = new Intent();
	private AlertDialog.Builder actualizar;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.subir);
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
		
		linear_barrq = (LinearLayout) findViewById(R.id.linear_barrq);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		textview2 = (TextView) findViewById(R.id.textview2);
		vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		url = (TextView) findViewById(R.id.url);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		btn_imagen = (Button) findViewById(R.id.btn_imagen);
		textview5 = (TextView) findViewById(R.id.textview5);
		et_titulo = (EditText) findViewById(R.id.et_titulo);
		textview4 = (TextView) findViewById(R.id.textview4);
		et_descripcion = (EditText) findViewById(R.id.et_descripcion);
		textview6 = (TextView) findViewById(R.id.textview6);
		et_ingredientes = (EditText) findViewById(R.id.et_ingredientes);
		textview7 = (TextView) findViewById(R.id.textview7);
		et_preparacion = (EditText) findViewById(R.id.et_preparacion);
		textview3 = (TextView) findViewById(R.id.textview3);
		sp_categoria = (Spinner) findViewById(R.id.sp_categoria);
		btn_subir = (Button) findViewById(R.id.btn_subir);
		fp.setType("image/*");
		fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		actualizar = new AlertDialog.Builder(this);
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		btn_imagen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivityForResult(fp, REQ_CD_FP);
			}
		});
		
		btn_subir.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (getIntent().getStringExtra("editar").equals("true")) {
					actualizar.setMessage("¿Deseas actualizar los datos de la receta?");
					actualizar.setPositiveButton("si", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							if (et_titulo.getText().toString().equals("") && (et_descripcion.getText().toString().equals("") && (et_ingredientes.getText().toString().equals("") && et_preparacion.getText().toString().equals("")))) {
								SketchwareUtil.showMessage(getApplicationContext(), "Debes rellenar todos los campos solicitados");
							}
							else {
								lrecetas = new HashMap<>();
								if (url.getText().toString().equals("")) {
									
								}
								else {
									lrecetas.put("url", url.getText().toString());
								}
								lrecetas.put("title", et_titulo.getText().toString());
								lrecetas.put("introduction", et_descripcion.getText().toString());
								if (sp_categoria.getSelectedItemPosition() == 0) {
									categoria = "almuerzos";
								}
								else {
									if (sp_categoria.getSelectedItemPosition() == 1) {
										categoria = "bebidas";
									}
									else {
										if (sp_categoria.getSelectedItemPosition() == 2) {
											categoria = "entradas";
										}
										else {
											categoria = "postres";
										}
									}
								}
								lrecetas.put("category", categoria);
								lrecetas.put("preparation", et_preparacion.getText().toString());
								lrecetas.put("ingredients", et_ingredientes.getText().toString());
								recetas.child(getIntent().getStringExtra("key")).updateChildren(lrecetas);
								SketchwareUtil.showMessage(getApplicationContext(), "se actualizo correctamente");
								finish();
							}
						}
					});
					actualizar.setNegativeButton("no", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					actualizar.create().show();
				}
				else {
					if (et_titulo.getText().toString().equals("") && (et_descripcion.getText().toString().equals("") && (et_ingredientes.getText().toString().equals("") && (et_preparacion.getText().toString().equals("") && url.getText().toString().equals(""))))) {
						SketchwareUtil.showMessage(getApplicationContext(), "Debes rellenar todos los campos solicitados");
					}
					else {
						lrecetas = new HashMap<>();
						lrecetas.put("url", url.getText().toString());
						lrecetas.put("title", et_titulo.getText().toString());
						lrecetas.put("introduction", et_descripcion.getText().toString());
						if (sp_categoria.getSelectedItemPosition() == 0) {
							categoria = "almuerzos";
						}
						else {
							if (sp_categoria.getSelectedItemPosition() == 1) {
								categoria = "bebidas";
							}
							else {
								if (sp_categoria.getSelectedItemPosition() == 2) {
									categoria = "entradas";
								}
								else {
									categoria = "postres";
								}
							}
						}
						lrecetas.put("category", categoria);
						lrecetas.put("preparation", et_preparacion.getText().toString());
						lrecetas.put("ingredients", et_ingredientes.getText().toString());
						recetas.child(recetas.push().getKey()).updateChildren(lrecetas);
						lrecetas.clear();
						et_titulo.setText("");
						et_descripcion.setText("");
						et_preparacion.setText("");
						et_ingredientes.setText("");
						imageview2.setImageResource(R.drawable.logo);
						url.setText("");
						SketchwareUtil.showMessage(getApplicationContext(), "Receta publicada correctamente!");
						finish();
					}
				}
			}
		});
		
		_recetas_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (getIntent().getStringExtra("editar").equals("true")) {
					if (getIntent().getStringExtra("key").equals(_childKey)) {
						et_titulo.setText(_childValue.get("title").toString());
						et_descripcion.setText(_childValue.get("introduction").toString());
						et_ingredientes.setText(_childValue.get("ingredients").toString());
						et_preparacion.setText(_childValue.get("preparation").toString());
						Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("url").toString())).into(imageview2);
						if (_childValue.get("category").toString().equals("almuerzos")) {
							nspinner = 0;
						}
						else {
							if (_childValue.get("category").toString().equals("bebidas")) {
								nspinner = 1;
							}
							else {
								if (_childValue.get("category").toString().equals("entradas")) {
									nspinner = 2;
								}
								else {
									if (_childValue.get("category").toString().equals("postres")) {
										nspinner = 3;
									}
									else {
										
									}
								}
							}
						}
						sp_categoria.setSelection((int)(nspinner));
					}
					else {
						
					}
				}
				else {
					
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
		
		_fotos_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				imageview2.setImageResource(R.drawable.ic_rotate_left_grey);
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
				url.setText(_downloadUrl);
				Glide.with(getApplicationContext()).load(Uri.parse(_downloadUrl)).into(imageview2);
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
	}
	
	private void initializeLogic() {
		_elevation(linear_barrq, 5);
		lcategorias.add("almuerzos");
		lcategorias.add("bebidas");
		lcategorias.add("entradas");
		lcategorias.add("postres");
		textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/medio.ttf"), 1);
		textview3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/medio.ttf"), 1);
		textview4.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/medio.ttf"), 0);
		textview5.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/medio.ttf"), 0);
		textview6.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/medio.ttf"), 0);
		textview7.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/medio.ttf"), 0);
		sp_categoria.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, lcategorias));
		_radius(btn_imagen, 10, 5, "#263238");
		_radius(btn_subir, 10, 5, "#FF9800");
		_rounds(et_titulo, 10, "FAFAFA", 5);
		_rounds(et_descripcion, 10, "FAFAFA", 5);
		_rounds(et_ingredientes, 10, "FAFAFA", 5);
		_rounds(et_preparacion, 10, "FAFAFA", 5);
		_ocultarScroll(vscroll1);
		url.setVisibility(View.GONE);
		if (getIntent().getStringExtra("editar").equals("true")) {
			btn_subir.setText("ACTUALIZAR RECETA");
			btn_imagen.setText("Actualizar imagen de portada");
			textview2.setText("EDITAR RECETA");
			recetas.addChildEventListener(_recetas_child_listener);
		}
		else {
			textview2.setText("PUBLICAR UNA RECETA");
		}
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_FP:
			if (_resultCode == Activity.RESULT_OK) {
				ArrayList<String> _filePath = new ArrayList<>();
				if (_data != null) {
					if (_data.getClipData() != null) {
						for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
							ClipData.Item _item = _data.getClipData().getItemAt(_index);
							_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
						}
					}
					else {
						_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
					}
				}
				string = _filePath.get((int)(0));
				name = Uri.parse(_filePath.get((int)(0))).getLastPathSegment();
				fotos.child(name).putFile(Uri.fromFile(new File(string))).addOnFailureListener(_fotos_failure_listener).addOnProgressListener(_fotos_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
					@Override
					public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
						return fotos.child(name).getDownloadUrl();
					}}).addOnCompleteListener(_fotos_upload_success_listener);
			}
			else {
				
			}
			break;
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
	
	
	public void _ocultarScroll (final View _view) {
		_view.setVerticalScrollBarEnabled(false);
		_view.setHorizontalScrollBarEnabled(false);
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