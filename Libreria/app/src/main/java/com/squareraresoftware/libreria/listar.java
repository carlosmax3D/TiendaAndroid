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

public class listar extends Activity implements View.OnClickListener{

    private TiendaDatabase database;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.boton_listar_consultar:
//                Toast.makeText(this, database.consultaMinimos("El articulo %s esta en su limite minimo"), Toast.LENGTH_LONG).show();
                ListView listView = (ListView)findViewById(R.id.listar_listView);
//                String[] values = new String[]{"lol","lol","lol","lol","lol","lol","lol","lol","lol","lol","lol","lol","lol","lol"};
                ArrayAdapter<String> data = new ArrayAdapter<String>(this,R.layout.list_black_text,R.id.text1,crearContenidoAdaptador());
                listView.setAdapter(data);
                break;
        }
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listar);
        this.database = (TiendaDatabase)getIntent().getExtras().getSerializable("database");
        Button boton = (Button)findViewById(R.id.boton_listar_consultar);
        boton.setOnClickListener(this);

/*		OnItemClickListener listener = new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position,
									long id) {
				setTitle(parent.getItemAtPosition(position).toString());
			}
		};
		listView.setOnItemClickListener(listener);*/
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
        if (((RadioButton)findViewById(R.id.filtro1)).isChecked())
            this.database.consultaArticulos(TiendaDatabase.ColumnArticulos.ID_ARTICULO);
        else
            this.database.consultaArticulos(TiendaDatabase.ColumnArticulos.ID_MARCA);
        if (database.cargarArticulo()){
            tmp = database.getArticulo();
            linea = String.valueOf((int)tmp.get(TiendaDatabase.ColumnArticulos.ID_ARTICULO))+"-"+(String)tmp.get(TiendaDatabase.ColumnArticulos.NOMBRE)+"-"+
                    (String)tmp.get(TiendaDatabase.ColumnArticulos.MODELO)+"-"+(String)tmp.get(TiendaDatabase.ColumnArticulos.CANTIDAD)+"-"+(String)tmp.get("mtNombre")+
                    "-"+(String)tmp.get("ftNombre");
            var1.add(linea);
            while (database.cargarArticulo()){
                tmp = database.getArticulo();
                linea = String.valueOf((int)tmp.get(TiendaDatabase.ColumnArticulos.ID_ARTICULO))+"-"+(String)tmp.get(TiendaDatabase.ColumnArticulos.NOMBRE)+"-"+
                        (String)tmp.get(TiendaDatabase.ColumnArticulos.MODELO)+"-"+(String)tmp.get(TiendaDatabase.ColumnArticulos.CANTIDAD)+"-"+(String)tmp.get("mtNombre")+
                        "-"+(String)tmp.get("ftNombre");
                var1.add(linea);
            }
        }else{
            Toast.makeText(this,getResources().getText(R.string.NoArticulos), Toast.LENGTH_SHORT).show();
        }
        return var1.toArray(new String[var1.size()]);
    }
}
