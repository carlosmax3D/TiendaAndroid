package com.squareraresoftware.libreria;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by carlos on 22/10/15.
 */
public class ConectSqlite extends SQLiteOpenHelper implements Serializable {

    public static final String DATABASE_NAME = "Tienda.db";
    public static final int DATABASE_VERSION = 1;

    public ConectSqlite(Context context){
            super(context,
                    DATABASE_NAME,//String name
                    null,//factory
                    DATABASE_VERSION//int version
            );
        }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fa = sdf.format(c.getTime());
        //Crear las tablas
        db.execSQL(TiendaDatabase.CREATE_PROVEEDORES_SCRIPT);
        db.execSQL(TiendaDatabase.CREATE_MARCA_SCRIPT);
        db.execSQL(TiendaDatabase.CREATE_FAMILIA_SCRIPT);
        db.execSQL(TiendaDatabase.CREATE_ARTICULOS_SCRIPT);
        db.execSQL(TiendaDatabase.CREATE_ARTICULO_PROVVEDOR_SCRIPT);
        db.execSQL(String.format(TiendaDatabase.INSERT_MARCAS_SCRIPT,fa,fa,fa,fa,fa));
        db.execSQL(String.format(TiendaDatabase.INSERT_FAMILIAS_SCRIPT,fa,fa,fa,fa,fa));
//        db.execSQL(String.format(TiendaDatabase.INSERT_PROVEEDORES_SCRIPT));
//        db.execSQL(String.format(TiendaDatabase.INSERT_PROVEEDORES_SCRIPT));
//        db.execSQL(String.format(TiendaDatabase.INSERT_PROVEEDORES_SCRIPT));
//        db.execSQL(String.format(TiendaDatabase.INSERT_PROVEEDORES_SCRIPT));

        //Insertar registros iniciales
//        db.execSQL(TiendaDatabase.INSERT_ARTICULOS_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Añade los cambios que se realizarán en el esquema
    }
}