package com.squareraresoftware.libreria;

import android.content.ContentValues;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class modificarProveedores extends Activity implements View.OnClickListener{

	TiendaDatabase database;
    int id;

	@Override
	public void onClick(View v) {
		switch (v.getId()){
            case R.id.boton_consultarProveedor:
                try {
                    id = Integer.parseInt(((TextView) findViewById(R.id.modificar_IdProveedor)).getText().toString());
                }catch(Exception ex){
                    Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.IDIngresaInvalido),Toast.LENGTH_SHORT).show();
                    break;
                }
                if (id < 1){
                    Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.IDIngresaInvalido),Toast.LENGTH_SHORT).show();
                    break;
                }
                database.consultaProveedor(id);
                if (!database.cargarProveedor()){
                    Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.IDIngresadoNoExiste),Toast.LENGTH_SHORT).show();
                    break;
                }
                Button boton = (Button)findViewById(R.id.boton_modificarProveedor);
                boton.setEnabled(true);
                boton.setVisibility(View.VISIBLE);
                ContentValues valores = database.getProveedor();
                try{
                    String tmp = valores.get(TiendaDatabase.ColumnProveedores.NOMBRE).toString().trim().toUpperCase();
                    ((TextView) findViewById(R.id.modificar_nombreProveedor)).setText(tmp);
                    tmp = valores.get(TiendaDatabase.ColumnProveedores.CIUDAD).toString().trim().toUpperCase();
                    ((TextView) findViewById(R.id.modificar_ciudadProveedor)).setText(tmp);
                    tmp = valores.get(TiendaDatabase.ColumnProveedores.ESTADO).toString().trim().toUpperCase();
                    ((TextView) findViewById(R.id.modificar_estadoProveedor)).setText(tmp);
                    tmp = valores.get(TiendaDatabase.ColumnProveedores.PAIS).toString().trim().toUpperCase();
                    ((TextView) findViewById(R.id.modificar_paisProveedor)).setText(tmp);
                    tmp = valores.get(TiendaDatabase.ColumnProveedores.DIRECCION).toString().trim().toUpperCase();
                    ((TextView) findViewById(R.id.modificar_direccionProveedor)).setText(tmp);
                    tmp = valores.get(TiendaDatabase.ColumnProveedores.TELEFONO).toString().trim().toUpperCase();
                    ((TextView) findViewById(R.id.modificar_telefonoProveedor)).setText(tmp);
                    tmp = valores.get(TiendaDatabase.ColumnProveedores.ID_PROVEEDOR).toString().trim().toUpperCase();
                    ((TextView)findViewById(R.id.modificar_IdProveedor)).setText(tmp);
                    if (valores.get(TiendaDatabase.ColumnProveedores.ACTIVO).toString().trim().equals("1"))
                        ((CheckBox)findViewById(R.id.modificarActivoProveedor)).setChecked(true);
                    else
                        ((CheckBox)findViewById(R.id.modificarActivoProveedor)).setChecked(false);
                    Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.ProveedorCargaExitosa), Toast.LENGTH_SHORT).show();
                }catch(Exception ex){
                    Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.ProveedorCargaErronea), Toast.LENGTH_SHORT).show();
                    break;
                }
                break;
			case R.id.boton_modificarProveedor:
				if (((Button)findViewById(R.id.boton_modificarProveedor)).getText().equals(getResources().getString(R.string.boton_nuevo))){
					((TextView) findViewById(R.id.modificar_nombreProveedor)).setText("");
					((TextView) findViewById(R.id.modificar_ciudadProveedor)).setText("");
					((TextView) findViewById(R.id.modificar_estadoProveedor)).setText("");
					((TextView) findViewById(R.id.modificar_paisProveedor)).setText("");
					((TextView) findViewById(R.id.modificar_direccionProveedor)).setText("");
					((TextView) findViewById(R.id.modificar_telefonoProveedor)).setText("");
					((TextView)findViewById(R.id.modificar_IdProveedor)).setText("");
					Toast.makeText(this.getApplicationContext(), (getResources().getString(R.string.CamposListos)), Toast.LENGTH_SHORT).show();
				}else{
					String Nombre;
					String Ciudad;
					String Estado;
					String Pais;
					String Direccion;
					String Telefono;
                    int activo = 0;
					try {
						Nombre = ((TextView) findViewById(R.id.modificar_nombreProveedor)).getText().toString().trim().toUpperCase();
						Ciudad = ((TextView) findViewById(R.id.modificar_ciudadProveedor)).getText().toString().trim().toUpperCase();
						Estado = ((TextView) findViewById(R.id.modificar_estadoProveedor)).getText().toString().trim().toUpperCase();
						Pais   = ((TextView) findViewById(R.id.modificar_paisProveedor)).getText().toString().trim().toUpperCase();
						Direccion = ((TextView) findViewById(R.id.modificar_direccionProveedor)).getText().toString().trim().toUpperCase();;
						Telefono = ((TextView) findViewById(R.id.modificar_telefonoProveedor)).getText().toString().trim().toUpperCase();
                        if (((CheckBox)findViewById(R.id.modificarActivoProveedor)).isChecked())
                            activo = 1;
                        else
                            activo = 0;
					}catch(Exception ex){
						Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.ValoresInvalidos),Toast.LENGTH_SHORT).show();
						break;
					}
					if (Nombre.equals("")){
						Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.IngresaNombre),Toast.LENGTH_SHORT).show();
						break;
					}
					if (database.verificaProveedor(Nombre,id) || Nombre.equals("")){
						Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.ProveedorExistente),Toast.LENGTH_SHORT).show();
						break;
					}
					database.updateProveedor(id,
                                             Nombre,
                                             Ciudad,
                                             Estado,
                                             Pais,
                                             Direccion,
                                             Telefono,
                                             activo);
					ContentValues values = database.getProveedor();
                    if (values != null){
                        ((TextView)findViewById(R.id.modificar_IdProveedor)).setText(String.valueOf(id));
                        Toast.makeText(this.getApplicationContext(), (getResources().getString(R.string.ModificarExito)+", ID = ")+id,Toast.LENGTH_SHORT).show();
                        ((Button)findViewById(R.id.boton_modificarProveedor)).setText(getResources().getString(R.string.boton_modificar));
                    }else{
                        ((TextView)findViewById(R.id.modificar_IdProveedor)).setText(String.valueOf(id));
                        Toast.makeText(this.getApplicationContext(), (getResources().getString(R.string.ModificarFallo)),Toast.LENGTH_SHORT).show();
                        ((Button)findViewById(R.id.boton_modificarProveedor)).setText(getResources().getString(R.string.boton_modificar));
                    }
				}
				break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modificar_proveedores);
		database = (TiendaDatabase)getIntent().getExtras().getSerializable("database");

        Button boton = (Button)findViewById(R.id.boton_modificarProveedor);
        boton.setOnClickListener(this);
        boton.setEnabled(false);
        boton.setVisibility(View.INVISIBLE);
        ((Button)findViewById(R.id.boton_consultarProveedor)).setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.acti1, menu);
		return true;
	}

}
