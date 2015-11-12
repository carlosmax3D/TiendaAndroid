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

public class agregar extends Activity implements View.OnClickListener {
    private TiendaDatabase database;
    private ArrayList<String> Proveedores = new ArrayList<String>();
    private ArrayList<String> selectedProveedores = new ArrayList<String>();
    private ArrayList<Integer> seleccionados = new ArrayList<Integer>();

	protected void showSelectProveedoresDialog() {
		boolean[] checkedProveedores = new boolean[Proveedores.size()];
		int count = Proveedores.size();
		for(int i = 0; i < count; i++)
			checkedProveedores[i] = selectedProveedores.contains(Proveedores.get(i));
		DialogInterface.OnMultiChoiceClickListener proveedoresDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if(isChecked)
					selectedProveedores.add(Proveedores.get(which));
				else
					selectedProveedores.remove(Proveedores.get(which));
				onChangeSelectedProveedor();
			}
		};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.msgProveedores);
		builder.setMultiChoiceItems(Proveedores.toArray(new String[Proveedores.size()]), checkedProveedores, proveedoresDialogListener);
		AlertDialog dialog = builder.create();
		dialog.show();
	}

    protected void onChangeSelectedProveedor() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        seleccionados.clear();
        for(String selproveedor : selectedProveedores) {
            stringBuilder.append(selproveedor + ",");
            seleccionados.add(Integer.parseInt(selproveedor.substring(0, selproveedor.indexOf("-"))));
        }
//        Toast.makeText(this.getApplicationContext(),stringBuilder.toString(),Toast.LENGTH_LONG).show();
        String v = stringBuilder.toString();
        if (v.equals(""))
            v=getResources().getString(R.string.ProveedoresSel);
        else
            if (v.toString().endsWith(","))
                v=v.substring(0,v.length()-1);
        ((Button) findViewById(R.id.agregar_Proveedores)).setText(v);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()){
            case R.id.agregar_Proveedores:
                Proveedores.clear();
                database.consultaProveedores();
                while (database.cargarProveedor()){
                    ContentValues val = database.getProveedor();
                    if (((int)val.get(TiendaDatabase.ColumnProveedores.ACTIVO)) == 1)
                        Proveedores.add(String.valueOf(val.get(TiendaDatabase.ColumnProveedores.ID_PROVEEDOR))+"-"+(String)val.get(TiendaDatabase.ColumnProveedores.NOMBRE));
                }
                showSelectProveedoresDialog();
                break;
			case R.id.boton_agregar:
//                database.saveProveedor("Prueba","LOL","NUEVO LEON","MEXICO","","8186600501");
//                database.consultaProveedor(1);
//                database.cargarProveedor();
    /*				database.updateProveedor((int)values.get(TiendaDatabase.ColumnProveedores.ID_PROVEEDOR),
                                             "",//TiendaDatabase.ColumnProveedores.NOMBRE
                                             (String)values.get(TiendaDatabase.ColumnProveedores.CIUDAD),
                                             (String)values.get(TiendaDatabase.ColumnProveedores.ESTADO),
                                             (String)values.get(TiendaDatabase.ColumnProveedores.PAIS),
                                             (String)values.get(TiendaDatabase.ColumnProveedores.DIRECCION),
                                             (String)values.get(TiendaDatabase.ColumnProveedores.TELEFONO),
                                             (int)values.get(TiendaDatabase.ColumnProveedores.ACTIVO));*/
                if (((Button)findViewById(R.id.boton_agregar)).getText().equals(getResources().getString(R.string.boton_nuevo))){
                    ((TextView) findViewById(R.id.agregar_nombre)).setText("");
                    ((TextView) findViewById(R.id.agregar_Modelo)).setText("");
                    ((TextView) findViewById(R.id.agregar_Costo)).setText("0.00");
                    ((TextView) findViewById(R.id.agregar_UbicacionAlmacen)).setText("");
                    ((TextView) findViewById(R.id.agregar_PiezasMax)).setText("0");
                    ((TextView) findViewById(R.id.agregar_PiezasMin)).setText("0");
                    ((TextView)findViewById(R.id.agregar_Id)).setText(String.valueOf("0"));
                    Toast.makeText(this.getApplicationContext(), (getResources().getString(R.string.CamposListos)),Toast.LENGTH_SHORT).show();
                    ((Button)findViewById(R.id.boton_agregar)).setText(R.string.boton_agregar);
                }else{
                    String Nombre;
                    String Modelo;
                    String UbicacionAlmacen;
                    int FamiliaId;
                    int MarcaId;
                    int PiezasMax;
                    int PiezasMin;
                    float costo;
                    int Cantidad = 0;
                    try {
                        Nombre = ((TextView) findViewById(R.id.agregar_nombre)).getText().toString().trim().toUpperCase();
                        Modelo = ((TextView) findViewById(R.id.agregar_Modelo)).getText().toString().trim().toUpperCase();
                        UbicacionAlmacen = ((TextView) findViewById(R.id.agregar_UbicacionAlmacen)).getText().toString().trim().toUpperCase();
                        FamiliaId = (int) (((Spinner) findViewById(R.id.agregar_Familia)).getSelectedItemId());
                        MarcaId = (int) (((Spinner) findViewById(R.id.agregar_Marca)).getSelectedItemId());
                        PiezasMax = Integer.parseInt(((TextView) findViewById(R.id.agregar_PiezasMax)).getText().toString());
                        PiezasMin = Integer.parseInt(((TextView) findViewById(R.id.agregar_PiezasMin)).getText().toString());
                        Cantidad = Integer.parseInt(((TextView) findViewById(R.id.agregar_Cantidad)).getText().toString());
                        costo = Float.parseFloat(((TextView) findViewById(R.id.agregar_Costo)).getText().toString());
                    }catch(Exception ex){
                        Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.ValoresInvalidos),Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (Nombre.equals("")){
                        Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.IngresaNombre),Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (database.verificaModelo(Modelo) || Modelo.equals("")){
                        Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.ModeloErroneo),Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (PiezasMax < 1 || PiezasMin < 0 || PiezasMin > PiezasMax){
                        Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.PiezasError),Toast.LENGTH_LONG).show();
                        break;
                    }
                    if (Cantidad < 0 || Cantidad > PiezasMax){
                        Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.CantidadError),Toast.LENGTH_LONG).show();
                        break;
                    }
/*                    if (seleccionados.size() < 1){
                        Toast.makeText(this.getApplicationContext(), "Debes seleccionar al menos un proveedor del articulo",Toast.LENGTH_LONG).show();
                        break;
                    }*/
                    if (costo <= 0.00){
                        Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.CostoInvalido),Toast.LENGTH_SHORT).show();
                        break;
                    }
                    database.saveArticulo(Nombre,
                                          FamiliaId,
                                          MarcaId,
                                          Modelo,
                                          costo,
                                          UbicacionAlmacen,
                                          PiezasMax,
                                          PiezasMin,
                                          Cantidad);
                    ContentValues values = database.getArticulo();
                    int id = Integer.parseInt(values.get(TiendaDatabase.ColumnArticulos.ID_ARTICULO).toString());
                    for (int i = 0; i < seleccionados.size(); i++)
                        database.saveArticulo_Proveedor(seleccionados.get(i),id);
                    if (values != null){
                        ((TextView)findViewById(R.id.agregar_Id)).setText(String.valueOf(id));
                        Toast.makeText(this.getApplicationContext(), (getResources().getString(R.string.RegistroExitoso)+", ID = "+id),Toast.LENGTH_SHORT).show();
                        ((Button)findViewById(R.id.boton_agregar)).setText(getResources().getString(R.string.boton_nuevo));
                    }else{
                        ((TextView)findViewById(R.id.agregar_Id)).setText(String.valueOf("0"));
                        Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.RegistroFallo),Toast.LENGTH_SHORT).show();
                        ((Button)findViewById(R.id.boton_agregar)).setText(getResources().getString(R.string.boton_agregar));
                    }
                }
				break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agregar);
        database = (TiendaDatabase)getIntent().getExtras().getSerializable("database");

		EditText id = (EditText)findViewById(R.id.agregar_Id);
		Button boton = (Button)findViewById(R.id.boton_agregar);
		boton.setOnClickListener(this);
        ((Button)findViewById(R.id.agregar_Proveedores)).setOnClickListener(this);
		SimpleCursorAdapter FamiliaSpinnerAdapter = new SimpleCursorAdapter(this,
				R.layout.spinnerlayout,//simple_spinner_dropdown_item
				database.spinnerFamilia(),
				new String[]{TiendaDatabase.ColumnFamilia.NOMBRE},
				new int[]{R.id.spinnerTarget},
				SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		((Spinner)findViewById(R.id.agregar_Familia)).setAdapter(FamiliaSpinnerAdapter);
		SimpleCursorAdapter MarcaSpinnerAdapter = new SimpleCursorAdapter(this,
				R.layout.spinnerlayout,//simple_spinner_dropdown_item
				database.spinnerMarcas(),
				new String[]{TiendaDatabase.ColumnMarca.NOMBRE},
				new int[]{R.id.spinnerTarget},
				SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		((Spinner) findViewById(R.id.agregar_Marca)).setAdapter(MarcaSpinnerAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.acti1, menu);
		return true;
	}

}
