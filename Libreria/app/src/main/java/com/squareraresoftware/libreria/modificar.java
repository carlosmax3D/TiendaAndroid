package com.squareraresoftware.libreria;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class modificar extends Activity implements View.OnClickListener {
		private TiendaDatabase database;
		private ArrayList<String> Proveedores = new ArrayList<String>();
		private ArrayList<String> selectedProveedores = new ArrayList<String>();
		private ArrayList<Integer> seleccionados = new ArrayList<Integer>();
        private ArrayList<Integer> preseleccionados = new ArrayList<Integer>();
        private int id = 0;

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
			((Button) findViewById(R.id.modificar_Proveedores)).setText(v);
		}

		@Override
		public void onClick(View v) {
			String Nombre;
			String Modelo;
			String UbicacionAlmacen;
			int FamiliaId;
			int MarcaId;
			int PiezasMax;
			int PiezasMin;
			int Cantidad = 0;
			float costo;
			int idT = 0;
			int activo;
			switch (v.getId()){
				case R.id.modificar_Proveedores:
					Proveedores.clear();
					database.consultaProveedores();
					while (database.cargarProveedor()){
						ContentValues val = database.getProveedor();
						if (((int)val.get(TiendaDatabase.ColumnProveedores.ACTIVO)) == 1)
							Proveedores.add(String.valueOf(val.get(TiendaDatabase.ColumnProveedores.ID_PROVEEDOR))+"-"+(String)val.get(TiendaDatabase.ColumnProveedores.NOMBRE));
					}
					showSelectProveedoresDialog();
					break;
                case R.id.boton_consultar:
                    try {
                        id = Integer.parseInt(((TextView) findViewById(R.id.modificar_Id)).getText().toString());
                    }catch(Exception ex){
                        Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.IDIngresaInvalido),Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (id < 1){
                        Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.IDIngresaInvalido),Toast.LENGTH_SHORT).show();
                        break;
                    }
                    database.consultaArticulo(id);
                    if (!database.cargarArticulo()){
                        Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.IDIngresadoNoExiste),Toast.LENGTH_SHORT).show();
                        break;
                    }
                    Button boton = (Button)findViewById(R.id.boton_modificar);
                    boton.setEnabled(true);
                    boton.setVisibility(View.VISIBLE);
                    ContentValues valores = database.getArticulo();
                    try{
                        String tmp = valores.get(TiendaDatabase.ColumnArticulos.NOMBRE).toString().trim().toUpperCase();
                        ((TextView) findViewById(R.id.modificar_nombre)).setText(tmp);
                        tmp = valores.get(TiendaDatabase.ColumnArticulos.MODELO).toString().trim().toUpperCase();
                        ((TextView) findViewById(R.id.modificar_Modelo)).setText(tmp);
                        tmp = valores.get(TiendaDatabase.ColumnArticulos.COSTO).toString().trim().toUpperCase();
                        ((TextView) findViewById(R.id.modificar_Costo)).setText(tmp);
                        tmp = valores.get(TiendaDatabase.ColumnArticulos.UBICACION).toString().trim().toUpperCase();
                        ((TextView) findViewById(R.id.modificar_UbicacionAlmacen)).setText(tmp);
                        tmp = valores.get(TiendaDatabase.ColumnArticulos.MAXIMO).toString().trim().toUpperCase();
                        ((TextView) findViewById(R.id.modificar_PiezasMax)).setText(tmp);
                        tmp = valores.get(TiendaDatabase.ColumnArticulos.MINIMO).toString().trim().toUpperCase();
                        ((TextView) findViewById(R.id.modificar_PiezasMin)).setText(tmp);
                        tmp = valores.get(TiendaDatabase.ColumnArticulos.CANTIDAD).toString().trim().toUpperCase();
                        ((TextView) findViewById(R.id.modificar_Cantidad)).setText(tmp);
                        tmp = valores.get(TiendaDatabase.ColumnArticulos.ID_ARTICULO).toString().trim().toUpperCase();
                        ((TextView)findViewById(R.id.modificar_Id)).setText(tmp);
                        if (valores.get(TiendaDatabase.ColumnArticulos.ACTIVO).toString().trim().equals("1"))
                            ((CheckBox)findViewById(R.id.modificarActivo)).setChecked(true);
                        else
                            ((CheckBox)findViewById(R.id.modificarActivo)).setChecked(false);
                        selectSpinnerItemByValue(((Spinner)findViewById(R.id.modificar_Familia)),
                                                Integer.parseInt(valores.get(TiendaDatabase.ColumnArticulos.ID_FAMILIA).toString()),
                                                TiendaDatabase.ColumnFamilia.ID_FAMILIA);
                        selectSpinnerItemByValue(((Spinner) findViewById(R.id.modificar_Marca)),
                                Integer.parseInt(valores.get(TiendaDatabase.ColumnArticulos.ID_MARCA).toString()),
                                TiendaDatabase.ColumnMarca.ID_MARCA);
                        Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.ArticuloCargaExitosa), Toast.LENGTH_SHORT).show();
                    }catch(Exception ex){
                        Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.ArticuloCargaErronea), Toast.LENGTH_SHORT).show();
                        break;
                    }
                    preseleccionados.clear();
                    selectedProveedores.clear();
                    seleccionados.clear();
                    database.consultaArticuloProveedor(id);
                    ((Button) findViewById(R.id.modificar_Proveedores)).setText(getResources().getString(R.string.ProveedoresSel));
                    if (!database.cargarArticuloProveedor()){
                        break;
                    }
                    valores = database.getArticulo();
					preseleccionados.add((int) (valores.get(TiendaDatabase.ColumnProveedores.ID_PROVEEDOR)));
					seleccionados.add((int)(valores.get(TiendaDatabase.ColumnProveedores.ID_PROVEEDOR)));
                    selectedProveedores.add(String.valueOf((int) valores.get(TiendaDatabase.ColumnProveedores.ID_PROVEEDOR)) + "-" + (String) valores.get(TiendaDatabase.ColumnProveedores.NOMBRE));
					String selProv=String.valueOf((int)valores.get(TiendaDatabase.ColumnProveedores.ID_PROVEEDOR)) + "-" + (String)valores.get(TiendaDatabase.ColumnProveedores.NOMBRE)+",";
                    while (database.cargarArticuloProveedor()){
                        valores = database.getArticulo();
                        selectedProveedores.add(String.valueOf((int) valores.get(TiendaDatabase.ColumnProveedores.ID_PROVEEDOR)) + "-" + (String)valores.get(TiendaDatabase.ColumnProveedores.NOMBRE));
                        selProv += String.valueOf((int) valores.get(TiendaDatabase.ColumnProveedores.ID_PROVEEDOR)) + "-" + (String) valores.get(TiendaDatabase.ColumnProveedores.NOMBRE)+",";
                        seleccionados.add((int)(valores.get(TiendaDatabase.ColumnProveedores.ID_PROVEEDOR)));
                        preseleccionados.add((int)(valores.get(TiendaDatabase.ColumnProveedores.ID_PROVEEDOR)));
                    }
                    if (selProv.endsWith(","))
                        selProv= selProv.substring(0,selProv.length()-1);
                    ((Button) findViewById(R.id.modificar_Proveedores)).setText(selProv);
                    break;
                case R.id.boton_modificar:
					try {
						Nombre = ((TextView) findViewById(R.id.modificar_nombre)).getText().toString().trim().toUpperCase();
						Modelo = ((TextView) findViewById(R.id.modificar_Modelo)).getText().toString().trim().toUpperCase();
						UbicacionAlmacen = ((TextView) findViewById(R.id.modificar_UbicacionAlmacen)).getText().toString().trim().toUpperCase();
						FamiliaId = (int) (((Spinner) findViewById(R.id.modificar_Familia)).getSelectedItemId());
						MarcaId = (int) (((Spinner) findViewById(R.id.modificar_Marca)).getSelectedItemId());
						PiezasMax = Integer.parseInt(((TextView) findViewById(R.id.modificar_PiezasMax)).getText().toString());
						PiezasMin = Integer.parseInt(((TextView) findViewById(R.id.modificar_PiezasMin)).getText().toString());
						costo = Float.parseFloat(((TextView) findViewById(R.id.modificar_Costo)).getText().toString());
						idT = Integer.parseInt(((TextView) findViewById(R.id.modificar_Id)).getText().toString());
                        Cantidad = Integer.parseInt(((TextView) findViewById(R.id.modificar_Cantidad)).getText().toString());
                        if ((((CheckBox)findViewById(R.id.modificarActivo)).isChecked()))
                            activo=1;
                        else
                            activo=0;
					}catch(Exception ex){
						Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.ValoresInvalidos),Toast.LENGTH_SHORT).show();
						break;
					}
                    if (id < 1){
                        Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.IDIngresaInvalido),Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (idT != id){
                        Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.IDDiferente),Toast.LENGTH_SHORT).show();
                        break;
                    }
					if (Nombre.equals("")){
						Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.IngresaNombre),Toast.LENGTH_SHORT).show();
						break;
					}
					if (database.verificaModelo(Modelo,id) || Modelo.equals("")){
						Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.ModeloErroneo),Toast.LENGTH_SHORT).show();
						break;
					}
					if (PiezasMax < 1 || PiezasMin < 0 || PiezasMin > PiezasMax){
						Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.PiezasError),Toast.LENGTH_LONG).show();
						break;
					}
/*					if (seleccionados.size() < 1){
						Toast.makeText(this.getApplicationContext(), "Debes seleccionar al menos un proveedor del articulo",Toast.LENGTH_LONG).show();
						break;
					}*/
                    if (Cantidad < 0 || Cantidad > PiezasMax){
                        Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.CantidadError),Toast.LENGTH_LONG).show();
                        break;
                    }
					if (costo <= 0.00){
						Toast.makeText(this.getApplicationContext(), getResources().getString(R.string.CostoInvalido),Toast.LENGTH_SHORT).show();
						break;
					}
					database.updateArticulo(id,Nombre,
							FamiliaId,
							MarcaId,
							Modelo,
							costo,
							UbicacionAlmacen,
							PiezasMax,
							PiezasMin,
							Cantidad,
                            activo);
					ContentValues values = database.getArticulo();
//					id = Integer.parseInt(values.get(TiendaDatabase.ColumnArticulos.ID_ARTICULO).toString());
                    if (!preseleccionados.equals(seleccionados)){
                        for (int i = 0; i < preseleccionados.size(); i++) {
                            if (!seleccionados.contains(preseleccionados.get(i)))
                                database.deleteArticulo_Proveedor(preseleccionados.get(i),id);
                        }
                        for (int i = 0; i < seleccionados.size(); i++) {
                            if (!preseleccionados.contains(seleccionados.get(i)))
                                database.saveArticulo_Proveedor(seleccionados.get(i), id);
                        }
						preseleccionados = new ArrayList<Integer>(seleccionados);
                    }
					if (values != null){
						((TextView)findViewById(R.id.modificar_Id)).setText(String.valueOf(id));
						Toast.makeText(this.getApplicationContext(), (getResources().getString(R.string.ModificarExito)+", ID = ")+id,Toast.LENGTH_SHORT).show();
                        ((Button)findViewById(R.id.boton_modificar)).setText(getResources().getString(R.string.boton_modificar));
					}else{
                        ((TextView)findViewById(R.id.modificar_Id)).setText(String.valueOf(id));
						Toast.makeText(this.getApplicationContext(), (getResources().getString(R.string.ModificarFallo)),Toast.LENGTH_SHORT).show();
						((Button)findViewById(R.id.boton_modificar)).setText(getResources().getString(R.string.boton_modificar));
					}
					break;
			}
		}

        public void selectSpinnerItemByValue(Spinner spnr, int id, String row){
            SimpleCursorAdapter adapter = (SimpleCursorAdapter)spnr.getAdapter();
            Cursor cur;
            for (int i = 0; i < adapter.getCount(); i++){
                cur = ((Cursor)spnr.getItemAtPosition(i));
                if (cur.getInt((cur.getColumnIndexOrThrow(row)))==id){
                    spnr.setSelection(i);
                    return;
                }
            }
        }

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_modificar);
			database = (TiendaDatabase)getIntent().getExtras().getSerializable("database");

			Button boton = (Button)findViewById(R.id.boton_modificar);
			boton.setOnClickListener(this);
            boton.setEnabled(false);
            boton.setVisibility(View.INVISIBLE);
            boton = (Button)findViewById(R.id.boton_consultar);
            boton.setOnClickListener(this);
			((Button)findViewById(R.id.modificar_Proveedores)).setOnClickListener(this);
			SimpleCursorAdapter FamiliaSpinnerAdapter = new SimpleCursorAdapter(this,
					R.layout.spinnerlayout,//simple_spinner_dropdown_item
					database.spinnerFamilia(),
					new String[]{TiendaDatabase.ColumnFamilia.NOMBRE},
					new int[]{R.id.spinnerTarget},
					SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
			((Spinner)findViewById(R.id.modificar_Familia)).setAdapter(FamiliaSpinnerAdapter);
			SimpleCursorAdapter MarcaSpinnerAdapter = new SimpleCursorAdapter(this,
					R.layout.spinnerlayout,//simple_spinner_dropdown_item
					database.spinnerMarcas(),
					new String[]{TiendaDatabase.ColumnMarca.NOMBRE},
					new int[]{R.id.spinnerTarget},
					SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
			((Spinner) findViewById(R.id.modificar_Marca)).setAdapter(MarcaSpinnerAdapter);
            Proveedores.clear();
            database.consultaProveedores();
            while (database.cargarProveedor()){
                ContentValues val = database.getProveedor();
                if (((int)val.get(TiendaDatabase.ColumnProveedores.ACTIVO)) == 1)
                    Proveedores.add(String.valueOf(val.get(TiendaDatabase.ColumnProveedores.ID_PROVEEDOR))+"-"+(String)val.get(TiendaDatabase.ColumnProveedores.NOMBRE));
            }
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.acti1, menu);
			return true;
		}

	}
