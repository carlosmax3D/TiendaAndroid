package com.squareraresoftware.libreria;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class agregarProveedores extends Activity implements View.OnClickListener {

		TiendaDatabase database;

		@Override
		public void onClick(View v) {
			switch (v.getId()){
				case R.id.boton_agregarProveedor:
					if (((Button)findViewById(R.id.boton_agregarProveedor)).getText().equals(getResources().getString(R.string.boton_nuevo))){
						((TextView) findViewById(R.id.agregar_nombreProveedor)).setText("");
						((TextView) findViewById(R.id.agregar_ciudadProveedor)).setText("");
						((TextView) findViewById(R.id.agregar_estadoProveedor)).setText("");
						((TextView) findViewById(R.id.agregar_paisProveedor)).setText("");
						((TextView) findViewById(R.id.agregar_direccionProveedor)).setText("");
						((TextView) findViewById(R.id.agregar_telefonoProveedor)).setText("");
						((TextView)findViewById(R.id.agregar_IdProveedor)).setText(String.valueOf(""));
						Toast.makeText(this.getApplicationContext(), (getResources().getString(R.string.CamposListos)), Toast.LENGTH_SHORT).show();
						((Button)findViewById(R.id.boton_agregarProveedor)).setText(R.string.boton_agregar);
					}else{
						String Nombre;
						String Ciudad;
						String Estado;
						String Pais;
						String Direccion;
						String Telefono;
						try {
							Nombre = ((TextView) findViewById(R.id.agregar_nombreProveedor)).getText().toString().trim().toUpperCase();
							Ciudad = ((TextView) findViewById(R.id.agregar_ciudadProveedor)).getText().toString().trim().toUpperCase();
							Estado = ((TextView) findViewById(R.id.agregar_estadoProveedor)).getText().toString().trim().toUpperCase();
							Pais   = ((TextView) findViewById(R.id.agregar_paisProveedor)).getText().toString().trim().toUpperCase();
							Direccion = ((TextView) findViewById(R.id.agregar_direccionProveedor)).getText().toString().trim().toUpperCase();;
							Telefono = ((TextView) findViewById(R.id.agregar_telefonoProveedor)).getText().toString().trim().toUpperCase();
						}catch(Exception ex){
							Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.ValoresInvalidos),Toast.LENGTH_SHORT).show();
							break;
						}
						if (database.verificaProveedor(Nombre) || Nombre.equals("")){
							Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.NombreErroneo),Toast.LENGTH_SHORT).show();
							break;
						}
						database.saveProveedor(Nombre,
                                               Ciudad,
                                               Estado,
                                               Pais,
                                               Direccion,
                                               Telefono);
						ContentValues values = database.getProveedor();
						int id = Integer.parseInt(values.get(TiendaDatabase.ColumnProveedores.ID_PROVEEDOR).toString());
						if (values != null){
							((TextView)findViewById(R.id.agregar_IdProveedor)).setText(String.valueOf(id));
							Toast.makeText(this.getApplicationContext(), (getResources().getString(R.string.RegistroExitoso)+", ID = "+id),Toast.LENGTH_SHORT).show();
							((Button)findViewById(R.id.boton_agregarProveedor)).setText(getResources().getString(R.string.boton_nuevo));
						}else{
							((TextView)findViewById(R.id.agregar_IdProveedor)).setText(String.valueOf("0"));
							Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.RegistroFallo),Toast.LENGTH_SHORT).show();
							((Button)findViewById(R.id.boton_agregarProveedor)).setText(getResources().getString(R.string.boton_agregar));
						}
					}
					break;
			}
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_agregar_proveedores);
			database = (TiendaDatabase)getIntent().getExtras().getSerializable("database");

			Button boton = (Button)findViewById(R.id.boton_agregarProveedor);
			boton.setOnClickListener(this);
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.acti1, menu);
			return true;
		}
}
