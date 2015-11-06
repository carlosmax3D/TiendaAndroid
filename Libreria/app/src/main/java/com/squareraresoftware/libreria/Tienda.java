package com.squareraresoftware.libreria;

import com.squareraresoftware.libreria.util.SystemUiHider;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import java.util.Timer;
import java.util.TimerTask;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class Tienda extends Activity implements OnClickListener {

    private TiendaDatabase database;
//    private Timer timer;

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_libreria);
        LocalActivityManager mLocalActivityManager = new LocalActivityManager(this, false);
        database = new TiendaDatabase(this);
        Bundle parametros = new Bundle();
        parametros.putSerializable("database", database);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        tabHost.setup(mLocalActivityManager);

        TabSpec tab = tabHost.newTabSpec("Articulos");
        tab.setIndicator("", getResources().getDrawable(R.drawable.libros));
        tab.setContent(new Intent(this, listar.class).putExtras(parametros));
        tabHost.addTab(tab);

        tab = tabHost.newTabSpec("Altas");
        tab.setIndicator("", getResources().getDrawable(R.drawable.agregar));
        tab.setContent(new Intent(this, agregar.class).putExtras(parametros));
        tabHost.addTab(tab);

        tab = tabHost.newTabSpec("Modificacion");
        tab.setIndicator("", getResources().getDrawable(R.drawable.editar));
        tab.setContent(new Intent(this, modificar.class).putExtras(parametros));
        tabHost.addTab(tab);

        tab = tabHost.newTabSpec("Proveedores");
        tab.setIndicator("", getResources().getDrawable(R.drawable.proveedor));
        tab.setContent(new Intent(this, listarProveedores.class).putExtras(parametros));
        tabHost.addTab(tab);

        tab = tabHost.newTabSpec("AgregarProveedor");
        tab.setIndicator("", getResources().getDrawable(R.drawable.agregar_proveedor));
        tab.setContent(new Intent(this, agregarProveedores.class).putExtras(parametros));
        tabHost.addTab(tab);

        tab = tabHost.newTabSpec("EditarProveedor");
        tab.setIndicator("", getResources().getDrawable(R.drawable.editar_proveedor));
        tab.setContent(new Intent(this, modificarProveedores.class).putExtras(parametros));
        tabHost.addTab(tab);

        tab = tabHost.newTabSpec("Pedido");
        tab.setIndicator("", getResources().getDrawable(R.drawable.buscar));
        tab.setContent(new Intent(this, pedido.class).putExtras(parametros));
        tabHost.addTab(tab);

        tab = tabHost.newTabSpec("Buscar");
        tab.setIndicator("", getResources().getDrawable(R.drawable.pedido));
        tab.setContent(new Intent(this, buscar.class).putExtras(parametros));
        tabHost.addTab(tab);
/*		timer = new Timer();
		chequeoPiezas ch = new chequeoPiezas();
		timer.scheduleAtFixedRate(ch, 0, 60000);
*/   }

    @Override
    public void onResume(){
        super.onResume();
        LocalActivityManager mLocalActivityManager = new LocalActivityManager(this, false);
        mLocalActivityManager.dispatchResume();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
//        timer.cancel();
//        timer.purge();
        database=null;
    }

    @Override
    public void onPause(){
        super.onPause();
        LocalActivityManager mLocalActivityManager = new LocalActivityManager(this, false);
        mLocalActivityManager.dispatchPause(isFinishing());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

/*    class chequeoPiezas extends TimerTask {

        @Override
        public void run() {
//            setContentView(R.layout.activity_pedido);
            ((EditText) findViewById(R.id.alerta1)).setText("Prueba\nPrueba");
//            setContentView(R.layout.activity_libreria);
        }
    }
*/
}
