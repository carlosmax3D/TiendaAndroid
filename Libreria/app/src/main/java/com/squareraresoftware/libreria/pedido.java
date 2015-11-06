package com.squareraresoftware.libreria;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class pedido extends Activity implements View.OnClickListener{

    private Timer tmr = new Timer();
    private TiendaDatabase database;
    private int id = 0;
    private int piezasMin = 0;
    private int piezasMax = 0;
    private int cantidad = 0;
    private float costoArt=0;
    private ArrayList<String> proveedores = new ArrayList<String>();
    private TextWatcher txtwatch =new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            cargaInformacion(s.toString());
        }
    };
    private TextWatcher costwatch =new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            actualizaCosto(s.toString());
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.boton_pedido:
                try{
                    if (id < 1 || id != Integer.parseInt(((TextView)findViewById(R.id.pedido_id)).getText().toString())){
                        Toast.makeText(this,getResources().getString(R.string.IDDiferente),Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (proveedores.size() < 1){
                        Toast.makeText(this,getResources().getString(R.string.ArticuloSinProveedores),Toast.LENGTH_SHORT).show();
                        break;
                    }
                    database.consultaArticulo(this.id);
                    if (database.cargarArticulo()) {
                        ContentValues tmp = database.getArticulo();
                        piezasMax = Integer.parseInt((String)tmp.get(TiendaDatabase.ColumnArticulos.MAXIMO));
                        piezasMin = Integer.parseInt((String)tmp.get(TiendaDatabase.ColumnArticulos.MINIMO));
                        cantidad = Integer.parseInt(((TextView)findViewById(R.id.pedido_cantidadPedir)).getText().toString());
                        ((TextView)findViewById(R.id.pedido_PiezasMax)).setText(String.valueOf(piezasMax));
                        ((TextView)findViewById(R.id.pedido_PiezasMin)).setText(String.valueOf(piezasMin));
                        ((TextView)findViewById(R.id.pedido_cantidadActual)).setText((String) tmp.get(TiendaDatabase.ColumnArticulos.CANTIDAD));
                        if (cantidad+Integer.parseInt((String)tmp.get(TiendaDatabase.ColumnArticulos.CANTIDAD)) > piezasMax ||
                                cantidad+Integer.parseInt((String)tmp.get(TiendaDatabase.ColumnArticulos.CANTIDAD)) < piezasMin){
                            Toast.makeText(this,getResources().getString(R.string.CantidadPiezasError),Toast.LENGTH_SHORT).show();
                            break;
                        }
                        if (cantidad < 1){
                            Toast.makeText(this,getResources().getString(R.string.CantidadError),Toast.LENGTH_SHORT).show();
                            break;
                        }
                        database.updateArticulo(id,
                                (String)tmp.get(TiendaDatabase.ColumnArticulos.NOMBRE),
                                Integer.parseInt((String)tmp.get(TiendaDatabase.ColumnArticulos.ID_FAMILIA)),
                                Integer.parseInt((String)tmp.get(TiendaDatabase.ColumnArticulos.ID_MARCA)),
                                (String)tmp.get(TiendaDatabase.ColumnArticulos.MODELO),
                                Float.parseFloat((String)tmp.get(TiendaDatabase.ColumnArticulos.COSTO)),
                                (String)tmp.get(TiendaDatabase.ColumnArticulos.UBICACION),
                                Integer.parseInt((String)tmp.get(TiendaDatabase.ColumnArticulos.MAXIMO)),
                                Integer.parseInt((String)tmp.get(TiendaDatabase.ColumnArticulos.MINIMO)),
                                cantidad+Integer.parseInt((String)tmp.get(TiendaDatabase.ColumnArticulos.CANTIDAD)),
                                (int)tmp.get(TiendaDatabase.ColumnArticulos.ACTIVO)
                        );
                        Toast.makeText(this, getResources().getString(R.string.RegistroExitoso), Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                    Toast.makeText(this,getResources().getString(R.string.ErrorActualizar),Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void actualizaCosto(String costo){
        try{
            ((TextView)findViewById(R.id.pedido_costo)).setText(String.valueOf(costoArt*Integer.parseInt(((TextView)findViewById(R.id.pedido_cantidadPedir)).getText().toString())));
        }catch(Exception ex){
            System.out.println("ERR "+ex.toString());
        }
    }

    public void cargaInformacion(String id){
        try{
            this.id = Integer.valueOf(id);
            proveedores.clear();
            database.consultaArticulo(this.id);
            costoArt =0;
            if (database.cargarArticulo()){
                ContentValues tmp = database.getArticulo();
                if (((int)tmp.get(TiendaDatabase.ColumnArticulos.ACTIVO)) == 0){
                    Toast.makeText(this,getResources().getString(R.string.IDIngresadoNoExiste),Toast.LENGTH_SHORT).show();
                    throw  null;
                }
                piezasMax = Integer.parseInt((String)tmp.get(TiendaDatabase.ColumnArticulos.MAXIMO));
                piezasMin = Integer.parseInt((String)tmp.get(TiendaDatabase.ColumnArticulos.MINIMO));
                ((TextView)findViewById(R.id.pedido_PiezasMax)).setText(String.valueOf(piezasMax));
                ((TextView)findViewById(R.id.pedido_PiezasMin)).setText(String.valueOf(piezasMin));
                ((TextView)findViewById(R.id.pedido_cantidadActual)).setText((String) tmp.get(TiendaDatabase.ColumnArticulos.CANTIDAD));
                costoArt = Float.parseFloat((String) tmp.get(TiendaDatabase.ColumnArticulos.COSTO));
                ((TextView)findViewById(R.id.pedido_costo)).setText(String.valueOf(costoArt));
                database.consultaArticuloProveedor(this.id);
                if (database.cargarArticuloProveedor()){
                    tmp = database.getArticulo();
                    String linea = "";
                    if (((int)tmp.get(TiendaDatabase.ColumnProveedores.ACTIVO)) == 1) {
                        linea = String.valueOf(tmp.get(TiendaDatabase.ColumnProveedores.ID_PROVEEDOR)) + "-" + tmp.get(TiendaDatabase.ColumnProveedores.NOMBRE);
                        proveedores.add(linea);
                    }
                    while (database.cargarArticuloProveedor()){
                        tmp = database.getArticulo();
                        if (((int)tmp.get(TiendaDatabase.ColumnProveedores.ACTIVO)) == 1) {
                            linea = String.valueOf(tmp.get(TiendaDatabase.ColumnProveedores.ID_PROVEEDOR)) + "-" + tmp.get(TiendaDatabase.ColumnProveedores.NOMBRE);
                            proveedores.add(linea);
                        }
                    }
                }
                ArrayAdapter<String> datSpinner = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,proveedores.toArray(new String[proveedores.size()]));
                datSpinner.setDropDownViewResource(R.layout.spinnerlayout);
                ((Spinner)findViewById(R.id.pedido_proveedor)).setAdapter(datSpinner);
                datSpinner.notifyDataSetChanged();
            }
        }catch(Exception ex){
            System.out.println("ERR "+ex.toString());
        }
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        ((EditText)findViewById(R.id.pedido_id)).addTextChangedListener(txtwatch);
        ((EditText)findViewById(R.id.pedido_cantidadPedir)).addTextChangedListener(costwatch);
        database = (TiendaDatabase)getIntent().getExtras().getSerializable("database");
        ((Button)findViewById(R.id.boton_pedido)).setOnClickListener(this);
        tmr.schedule(new RefreshView(), 1000, 2000);
    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.acti1, menu);
		return true;
	}

    public void ActualizaTextEdit(){
        String vartmp = database.consultaMinimos(getResources().getString(R.string.ArticuloCritico));
        ((EditText)findViewById(R.id.alerta1)).setText(vartmp);
        if (vartmp.trim().length() < 2){
//            ((EditText)findViewById(R.id.alerta1)).setBackgroundColor(0);
            ((EditText)findViewById(R.id.alerta1)).getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }else{
//            ((EditText)findViewById(R.id.alerta1)).setBackgroundColor(16711680);
            ((EditText)findViewById(R.id.alerta1)).getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        }
        ((EditText)findViewById(R.id.alerta1)).invalidate();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        tmr.cancel();
        tmr.purge();
        database=null;
    }

    private class RefreshView extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ActualizaTextEdit();
                }
            });
        }
    }
}
