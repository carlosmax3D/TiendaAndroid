package com.squareraresoftware.libreria;

import android.content.ContentValues;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class listarProveedores extends Activity implements View.OnClickListener{

	private TiendaDatabase database;

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.boton_listarProveedores_consultar:
//                Toast.makeText(this, database.consultaMinimos("El articulo %s esta en su limite minimo"), Toast.LENGTH_LONG).show();
				ListView listView = (ListView)findViewById(R.id.listarProveedores_listView);
//                String[] values = new String[]{"lol","lol","lol","lol","lol","lol","lol","lol","lol","lol","lol","lol","lol","lol"};
				ArrayAdapter<String> data = new ArrayAdapter<String>(this,R.layout.list_black_text,R.id.text1,crearContenidoAdaptador());
				listView.setAdapter(data);
				break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listar_proveedores);
		this.database = (TiendaDatabase)getIntent().getExtras().getSerializable("database");
		Button boton = (Button)findViewById(R.id.boton_listarProveedores_consultar);
		boton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.acti1, menu);
		return true;
	}

	public String[] crearContenidoAdaptador() {
		ArrayList<String> var1 = new ArrayList<String>();
		ContentValues tmp;
		String linea;
		if (((RadioButton)findViewById(R.id.filtro1Proveedores)).isChecked())
			this.database.consultaProveedores(TiendaDatabase.ColumnProveedores.ID_PROVEEDOR);
		else
			this.database.consultaProveedores(TiendaDatabase.ColumnProveedores.NOMBRE);
		if (database.cargarProveedor()){
			tmp = database.getProveedor();
			linea = String.valueOf((int)tmp.get(TiendaDatabase.ColumnProveedores.ID_PROVEEDOR))+"-"+(String)tmp.get(TiendaDatabase.ColumnProveedores.NOMBRE);
			var1.add(linea);
			while (database.cargarProveedor()){
				tmp = database.getProveedor();
				linea = String.valueOf((int)tmp.get(TiendaDatabase.ColumnProveedores.ID_PROVEEDOR))+"-"+(String)tmp.get(TiendaDatabase.ColumnProveedores.NOMBRE);
				var1.add(linea);
			}
		}else{
			Toast.makeText(this, getResources().getText(R.string.NoProveedores), Toast.LENGTH_SHORT).show();
		}
		return var1.toArray(new String[var1.size()]);
	}
}
