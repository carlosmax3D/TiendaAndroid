package com.squareraresoftware.libreria;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by carlos on 22/10/15.
 */
public class TiendaDatabase implements Serializable {

        //Metainformación de la base de datos
        public static final String ARTICULOS_TABLE_NAME = "Articulos";
        public static final String MARCA_TABLE_NAME = "Marca";
        public static final String PROVEEDOR_TABLE_NAME = "Proveedor";
        public static final String ARTICULO_PROVEEDOR_TABLE_NAME = "ArticulosProveedor";
        public static final String FAMILIA_TABLE_NAME = "Familia";
        public static final String STRING_TYPE = "varchar(200)";
        public static final String INT_TYPE = "integer";
        public static final String BOOL_TYPE = "integer";

        //Campos de la tabla Articulos
        public static class ColumnArticulos{
            public static final String ID_ARTICULO    = BaseColumns._ID ;
            public static final String NOMBRE         = "nombre";
            public static final String MODELO         = "modelo";
            public static final String COSTO          = "costo";
            public static final String UBICACION      = "ubicacion";
            public static final String MINIMO         = "minimo";
            public static final String MAXIMO         = "maximo";
            public static final String CANTIDAD       = "cantidad";
            public static final String FECHA_AGREGADO = "fechaAgregado";
            public static final String ID_MARCA       = "marca";
            public static final String ID_FAMILIA     = "idFamilia";
            public static final String ACTIVO         = "activo";
        }

        //Campos de la tabla Proveedores
        public static class ColumnProveedores{
            public static final String ID_PROVEEDOR   = BaseColumns._ID ;
            public static final String NOMBRE         = "nombre";
            public static final String CIUDAD         = "ciudad";
            public static final String ESTADO         = "estado";
            public static final String PAIS           = "pais";
            public static final String DIRECCION      = "direccion";
            public static final String TELEFONO       = "telefono";
            public static final String FECHA_AGREGADO = "fechaAgregado";
            public static final String ACTIVO         = "activo";
        }

        //Campos de la tabla Marca
        public static class ColumnMarca{
            public static final String ID_MARCA       = BaseColumns._ID ;
            public static final String NOMBRE         = "nombre";
            public static final String FECHA_AGREGADO = "fechaAgregado";
        }

        //Campos de la tabla Familia
        public static class ColumnFamilia{
            public static final String ID_FAMILIA     = BaseColumns._ID ;
            public static final String NOMBRE         = "nombre";
            public static final String FECHA_AGREGADO = "fechaAgregado";
        }

        //Campos de la tabla Articulo_Proveedor
        public static class ColumnArticuloProveedor{
            public static final String ID_ARTICULO_PROVEEDOR = BaseColumns._ID ;
            public static final String ID_ARTICULO           = "articuloId";
            public static final String ID_PROVEEDOR          = "proveedorId";
            public static final String FECHA_AGREGADO = "fechaAgregado";
        }

        //Script de Creación de la tabla ARTICULOS
        public static final String CREATE_ARTICULOS_SCRIPT =
                "create table "+ARTICULOS_TABLE_NAME+"(" +
                        ColumnArticulos.ID_ARTICULO+" "+INT_TYPE+" primary key autoincrement," +
                        ColumnArticulos.NOMBRE+" "+STRING_TYPE+" not null," +
                        ColumnArticulos.MODELO+" "+STRING_TYPE+" not null," +
                        ColumnArticulos.COSTO+" "+STRING_TYPE+" not null," + //COSTO COMO STRING PORQUE SQLITE NO TIENE FLOAT O MONEY
                        ColumnArticulos.UBICACION+" "+STRING_TYPE+" not null," +
                        ColumnArticulos.MINIMO+" "+INT_TYPE+" not null," +
                        ColumnArticulos.MAXIMO+" "+INT_TYPE+" not null," +
                        ColumnArticulos.CANTIDAD+" "+INT_TYPE+" not null," +
                        ColumnArticulos.FECHA_AGREGADO+" "+STRING_TYPE+" not null," +
                        ColumnArticulos.ID_MARCA + " " + INT_TYPE+ " not null," +
                        ColumnArticulos.ID_FAMILIA + " " + INT_TYPE+ " not null," +
                        ColumnArticulos.ACTIVO+" "+BOOL_TYPE+" not null," +
                        "FOREIGN KEY ("+ColumnArticulos.ID_MARCA+") REFERENCES "+MARCA_TABLE_NAME+"("+ColumnMarca.ID_MARCA+")," +
                        "FOREIGN KEY ("+ColumnArticulos.ID_FAMILIA+") REFERENCES "+FAMILIA_TABLE_NAME+"("+ColumnFamilia.ID_FAMILIA+") )";

        //Script de Creación de la tabla PROVEEDORES
        public static final String CREATE_PROVEEDORES_SCRIPT =
                "create table "+PROVEEDOR_TABLE_NAME+"(" +
                        ColumnProveedores.ID_PROVEEDOR+" "+INT_TYPE+" primary key autoincrement," +
                        ColumnProveedores.NOMBRE+" "+STRING_TYPE+" not null," +
                        ColumnProveedores.CIUDAD+" "+STRING_TYPE+" not null," +
                        ColumnProveedores.ESTADO+" "+STRING_TYPE+" not null," + //COSTO COMO STRING PORQUE SQLITE NO TIENE FLOAT O MONEY
                        ColumnProveedores.PAIS+" "+STRING_TYPE+" not null," +
                        ColumnProveedores.DIRECCION+" "+STRING_TYPE+" not null," +
                        ColumnProveedores.TELEFONO+" "+STRING_TYPE+" not null," +
                        ColumnProveedores.FECHA_AGREGADO+" "+STRING_TYPE+" not null," +
                        ColumnProveedores.ACTIVO+" "+BOOL_TYPE+" not null)";

        //Script de Creación de la tabla Marca
        public static final String CREATE_MARCA_SCRIPT =
                "create table "+MARCA_TABLE_NAME+"(" +
                        ColumnMarca.ID_MARCA+" "+INT_TYPE+" primary key autoincrement," +
                        ColumnMarca.NOMBRE+" "+STRING_TYPE+" not null," +
                        ColumnMarca.FECHA_AGREGADO+" "+STRING_TYPE+" not null)";

        //Script de Creación de la tabla Familia
        public static final String CREATE_FAMILIA_SCRIPT =
                "create table "+FAMILIA_TABLE_NAME+"(" +
                        ColumnFamilia.ID_FAMILIA+" "+INT_TYPE+" primary key autoincrement," +
                        ColumnFamilia.NOMBRE+" "+STRING_TYPE+" not null," +
                        ColumnFamilia.FECHA_AGREGADO+" "+STRING_TYPE+" not null)";

        //Script de Creación de la tabla ArticuloProveedor
        public static final String CREATE_ARTICULO_PROVVEDOR_SCRIPT =
                "create table "+ARTICULO_PROVEEDOR_TABLE_NAME+"(" +
                        ColumnArticuloProveedor.ID_ARTICULO_PROVEEDOR+" "+INT_TYPE+" primary key autoincrement," +
                        ColumnArticuloProveedor.ID_ARTICULO + " " + INT_TYPE+ " not null," +
                        ColumnArticuloProveedor.ID_PROVEEDOR + " " + INT_TYPE+ " not null," +
                        ColumnArticuloProveedor.FECHA_AGREGADO+" "+STRING_TYPE+" not null," +
                        "FOREIGN KEY ("+ColumnArticuloProveedor.ID_ARTICULO+") REFERENCES "+ARTICULOS_TABLE_NAME+"("+ColumnArticulos.ID_ARTICULO+")," +
                        "FOREIGN KEY ("+ColumnArticuloProveedor.ID_PROVEEDOR+") REFERENCES "+PROVEEDOR_TABLE_NAME+"("+ColumnProveedores.ID_PROVEEDOR+") )";

        //Scripts de inserción por defecto
        public static final String INSERT_MARCAS_SCRIPT =
                "insert into "+MARCA_TABLE_NAME+" values(null," +
                        "\"Toshiba\"," +
                        "\"%s\")," +
                        "(null,\"HP\"," +
                        "\"%s\")," +
                        "(null,\"Samsung\"," +
                        "\"%s\")," +
                        "(null,\"Lenovo\"," +
                        "\"%s\")," +
                        "(null,\"Acer\"," +
                        "\"%s\")";

        //Scripts de inserción por defecto
        public static final String INSERT_PROVEEDORES_SCRIPT =
                "insert into "+PROVEEDOR_TABLE_NAME+" values(null," +
                        "\"PRUEBA\",\"LOL\",\"NUEVO LEON\",\"MEXICO\",\"\",\"8186600501\",\"01/01/1900\",1)";

        public static final String INSERT_FAMILIAS_SCRIPT =
                "insert into "+FAMILIA_TABLE_NAME+" values(null," +
                        "\"Electrodomesticos\"," +
                        "\"%s\")," +
                        "(null,\"Computadoras\"," +
                        "\"%s\")," +
                        "(null,\"Tabletas\"," +
                        "\"%s\")," +
                        "(null,\"Celulares\"," +
                        "\"%s\")," +
                        "(null,\"Aires Acondicionados\"," +
                        "\"%s\")";

        private ConectSqlite openHelper;
        private SQLiteDatabase database;
        private Cursor cursor;
        private ContentValues values;

        public TiendaDatabase(Context context) {
            //Creando una instancia hacia la base de datos
            openHelper = new ConectSqlite(context);
            database = openHelper.getWritableDatabase();
        }

        public void saveArticulo(String Nombre, int Familia, int Marca, String Modelo, float Costo, String UbicacionAlmacen, int PiezasMax, int PiezasMin, int Cantidad){
            values = new ContentValues();
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fechaAgregado = sdf.format(c.getTime());
            //Seteando datos de Articulo
            values.put(ColumnArticulos.NOMBRE,Nombre);
            values.put(ColumnArticulos.MODELO,Modelo);
            values.put(ColumnArticulos.COSTO,Costo);
            values.put(ColumnArticulos.UBICACION,UbicacionAlmacen);
            values.put(ColumnArticulos.MINIMO,PiezasMin);
            values.put(ColumnArticulos.MAXIMO,PiezasMax);
            values.put(ColumnArticulos.CANTIDAD,Cantidad);
            values.put(ColumnArticulos.FECHA_AGREGADO,fechaAgregado);
            values.put(ColumnArticulos.ID_MARCA,Marca);
            values.put(ColumnArticulos.ID_FAMILIA,Familia);
            values.put(ColumnArticulos.ACTIVO,1);

            //Insertando en la base de datos
            values.put(ColumnArticulos.ID_ARTICULO,(database.insert(ARTICULOS_TABLE_NAME, null, values)));
        }

        public void updateArticulo(int id, String Nombre, int Familia, int Marca, String Modelo, float Costo, String UbicacionAlmacen, int PiezasMax, int PiezasMin,int Cantidad,int activo){

            //Insertando en la base de datos
            Cursor c = database.rawQuery("update "+ARTICULOS_TABLE_NAME+" set "+
                              ColumnArticulos.NOMBRE+"='"+Nombre+"',"+
                              ColumnArticulos.MODELO+"='"+Modelo+"',"+
                              ColumnArticulos.COSTO+"="+Costo+","+
                              ColumnArticulos.UBICACION+"='"+UbicacionAlmacen+"',"+
                              ColumnArticulos.MINIMO+"="+PiezasMin+","+
                              ColumnArticulos.MAXIMO+"="+PiezasMax+","+
                              ColumnArticulos.CANTIDAD+"="+Cantidad+","+
//                              ColumnArticulos.FECHA_AGREGADO+","+
                              ColumnArticulos.ID_MARCA+"="+Marca+","+
                              ColumnArticulos.ID_FAMILIA+"="+Familia+","+
                              ColumnArticulos.ACTIVO+"="+activo+
                              " where "+ColumnArticulos.ID_ARTICULO+"="+id,null);
            c.moveToNext();
            c.close();
            consultaArticulo(id);
            cargarArticulo();
        }

        public void saveProveedor(String Nombre, String Ciudad, String Estado, String Pais, String Direccion, String Telefono){
            values = new ContentValues();
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fechaAgregado = sdf.format(c.getTime());

            //Seteando datos de proveedor
            values.put(ColumnProveedores.NOMBRE,Nombre);
            values.put(ColumnProveedores.CIUDAD,Ciudad);
            values.put(ColumnProveedores.ESTADO,Estado);
            values.put(ColumnProveedores.PAIS,Pais);
            values.put(ColumnProveedores.DIRECCION,Direccion);
            values.put(ColumnProveedores.TELEFONO, Telefono);
            values.put(ColumnProveedores.FECHA_AGREGADO, fechaAgregado);
            values.put(ColumnProveedores.ACTIVO, 1);

            //Insertando en la base de datos
            values.put(ColumnProveedores.ID_PROVEEDOR, database.insert(PROVEEDOR_TABLE_NAME, null, values));
        }

        public void updateProveedor(int id, String Nombre, String Ciudad, String Estado, String Pais, String Direccion, String Telefono, int activo){

            //Insertando en la base de datos
            Cursor c = database.rawQuery("update " + PROVEEDOR_TABLE_NAME + " set " +
                    ColumnProveedores.NOMBRE + "='" + Nombre + "'," +
                    ColumnProveedores.CIUDAD + "='" + Ciudad + "'," +
                    ColumnProveedores.ESTADO + "='" + Estado + "'," +
                    ColumnProveedores.PAIS + "='" + Pais + "'," +
                    ColumnProveedores.DIRECCION + "='" + Direccion + "'," +
                    ColumnProveedores.TELEFONO + "='" + Telefono + "'," +
//                    ColumnProveedores.FECHA_AGREGADO+","+
                    ColumnProveedores.ACTIVO + "=" + activo
                    + " where " + ColumnProveedores.ID_PROVEEDOR + "=" + id, null);
            c.moveToNext();
            c.close();
            consultaProveedor(id);
            cargarProveedor();
        }

        public void saveMarca(String Nombre){
            values = new ContentValues();
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fechaAgregado = sdf.format(c.getTime());

            //Seteando datos de proveedor
            values.put(ColumnMarca.NOMBRE, Nombre);
            values.put(ColumnMarca.FECHA_AGREGADO, fechaAgregado);

            //Insertando en la base de datos
            values.put(ColumnMarca.ID_MARCA, database.insert(MARCA_TABLE_NAME, null, values));
        }

        public void updateMarca(int id, String Nombre){

            //Insertando en la base de datos
            Cursor c = database.rawQuery("update " + MARCA_TABLE_NAME + " set " +
                    ColumnMarca.NOMBRE + "='" + Nombre +
                    " where " + ColumnMarca.ID_MARCA + "=" + id, null);
            c.moveToNext();
            c.close();
            consultaMarca(id);
            cargarMarca();
        }

        public void saveFamilia(String Nombre){
            values = new ContentValues();
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fechaAgregado = sdf.format(c.getTime());

            //Seteando datos de proveedor
            values.put(ColumnFamilia.NOMBRE, Nombre);
            values.put(ColumnFamilia.FECHA_AGREGADO, fechaAgregado);

            //Insertando en la base de datos
            values.put(ColumnFamilia.ID_FAMILIA, database.insert(FAMILIA_TABLE_NAME, null, values));
        }

        public void updateFamilia(int id, String Nombre){

            //Insertando en la base de datos
            Cursor c = database.rawQuery("update " + FAMILIA_TABLE_NAME + " set " +
                    ColumnFamilia.NOMBRE + "='" + Nombre +
                    " where " + ColumnFamilia.ID_FAMILIA + "=" + id, null);
            c.moveToNext();
            c.close();
            consultaFamilia(id);
            cargarFamilia();
        }

        public void saveArticulo_Proveedor(int idProveedor,int idArticulo){
            values = new ContentValues();
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fechaAgregado = sdf.format(c.getTime());

            //Seteando datos de proveedor
            values.put(ColumnArticuloProveedor.ID_ARTICULO, idArticulo);
            values.put(ColumnArticuloProveedor.ID_PROVEEDOR, idProveedor);
            values.put(ColumnArticuloProveedor.FECHA_AGREGADO, fechaAgregado);

            //Insertando en la base de datos
            database.insert(ARTICULO_PROVEEDOR_TABLE_NAME, null, values);
        }

        public void deleteArticulo_Proveedor(int idProveedor,int idArticulo){
            Cursor c = database.rawQuery("delete from " + ARTICULO_PROVEEDOR_TABLE_NAME +
                    " where "+ColumnArticuloProveedor.ID_PROVEEDOR+"="+idProveedor+
                    " and "+ColumnArticuloProveedor.ID_ARTICULO+"="+idArticulo, null);
            c.moveToNext();
            c.close();
        }
/*        public void updateArticulo_Proveedor(int id, String Nombre){

            //Insertando en la base de datos
            database.rawQuery("update " + FAMILIA_TABLE_NAME + " set (" +
                    ColumnFamilia.NOMBRE+"="+Nombre+")"+
                    " where " + ColumnFamilia.ID_FAMILIA + "=" + id, null);
            consultaFamilia(id);
            cargarFamilia();
        }
*/

        public void consultaArticulos(){
            cursor = database.rawQuery("select AT.*,FT."+ColumnFamilia.NOMBRE+" as ftNombre,MT."+ColumnMarca.NOMBRE+" as mtNombre"+
                    " from "+ARTICULOS_TABLE_NAME+" as AT inner join "+FAMILIA_TABLE_NAME+" as FT on FT."+
                    ColumnFamilia.ID_FAMILIA+"=AT."+ColumnArticulos.ID_FAMILIA+" inner join "+MARCA_TABLE_NAME+" as MT on MT."+
                    ColumnMarca.ID_MARCA+"=AT."+ColumnArticulos.ID_MARCA, null); //nul por parametros string
        }
        public void consultaArticulos(String orden){
            cursor = database.rawQuery("select AT.*,FT."+ColumnFamilia.NOMBRE+" as ftNombre,MT."+ColumnMarca.NOMBRE+" as mtNombre"+
                                       " from "+ARTICULOS_TABLE_NAME+" as AT inner join "+FAMILIA_TABLE_NAME+" as FT on FT."+
                                       ColumnFamilia.ID_FAMILIA+"=AT."+ColumnArticulos.ID_FAMILIA+" inner join "+MARCA_TABLE_NAME+" as MT on MT."+
                                       ColumnMarca.ID_MARCA+"=AT."+ColumnArticulos.ID_MARCA+" order by AT."+orden, null); //nul por parametros string
        }
        public void consultaArticulo(int id){
            cursor = database.rawQuery("select AT.*,FT."+ColumnFamilia.NOMBRE+" as ftNombre,MT."+ColumnMarca.NOMBRE+" as mtNombre"+
                    " from "+ARTICULOS_TABLE_NAME+" as AT inner join "+FAMILIA_TABLE_NAME+" as FT on FT."+
                    ColumnFamilia.ID_FAMILIA+"=AT."+ColumnArticulos.ID_FAMILIA+" inner join "+MARCA_TABLE_NAME+" as MT on MT."+
                    ColumnMarca.ID_MARCA+"=AT."+ColumnArticulos.ID_MARCA+" where AT."+ColumnArticulos.ID_ARTICULO +"="+id, null); //nul por parametros string
        }
        public boolean verificaModelo(String modelo){
            consultaArticulos();
            cursor.moveToFirst();
            while (cargarArticulo()){
                if (((String)values.get(ColumnArticulos.MODELO)).trim().toUpperCase().equals(modelo.trim().toUpperCase())) {
                    cursor.close();
                    return true;
                }
            }
            return false;
        }
        public boolean verificaModelo(String modelo, int id){
            consultaArticulos();
            cursor.moveToFirst();
            while (cargarArticulo()){
                if (((String)values.get(ColumnArticulos.MODELO)).trim().toUpperCase().equals(modelo.trim().toUpperCase())) {
                    if (!(((int)values.get(ColumnArticulos.ID_ARTICULO)) == id)) {
                        cursor.close();
                        return true;
                    }
                }
            }
            return false;
        }

        public void consultaArticulosProveedor(){
            cursor = database.rawQuery("select AP."+ColumnArticuloProveedor.ID_ARTICULO_PROVEEDOR+
                    ",AP."+ColumnArticuloProveedor.ID_ARTICULO+
                    ",AP."+ColumnArticuloProveedor.FECHA_AGREGADO+
                    " as APFechaAgregado,P.* from "+ARTICULO_PROVEEDOR_TABLE_NAME+
                    " as AP inner join "+PROVEEDOR_TABLE_NAME+" as P on P."+
                    ColumnProveedores.ID_PROVEEDOR+"=AP."+ColumnArticuloProveedor.ID_PROVEEDOR+
                    " order by P."+ColumnProveedores.ID_PROVEEDOR, null); //nul por parametros string
        }
        public void consultaArticuloProveedor(int id){
            cursor = database.rawQuery("select AP."+ColumnArticuloProveedor.ID_ARTICULO_PROVEEDOR+
                                       ",AP."+ColumnArticuloProveedor.ID_ARTICULO+
                                       ",AP."+ColumnArticuloProveedor.FECHA_AGREGADO+
                                       " as APFechaAgregado,P.* from "+ARTICULO_PROVEEDOR_TABLE_NAME+
                                       " as AP inner join "+PROVEEDOR_TABLE_NAME+" as P on P."+
                                       ColumnProveedores.ID_PROVEEDOR+"=AP."+ColumnArticuloProveedor.ID_PROVEEDOR+
                                       " where AP."+ColumnArticuloProveedor.ID_ARTICULO +"="+id, null); //nul por parametros string
        }
        public boolean verificaProveedor(String proveedor){
            consultaProveedores();
            cursor.moveToFirst();
            while (cargarProveedor()){
                if (((String)values.get(ColumnProveedores.NOMBRE)).trim().toUpperCase().equals(proveedor.trim().toUpperCase())) {
                    cursor.close();
                    return true;
                }
            }
            return false;
        }
        public boolean verificaProveedor(String proveedor, int id){
            consultaProveedores();
            cursor.moveToFirst();
            while (cargarProveedor()){
                if (((String)values.get(ColumnProveedores.NOMBRE)).trim().toUpperCase().equals(proveedor.trim().toUpperCase())) {
                    if (!(((int)values.get(ColumnProveedores.ID_PROVEEDOR)) == id)) {
                        cursor.close();
                        return true;
                    }
                }
            }
            return false;
        }

        public void consultaMarcas(){
            cursor = database.rawQuery("select * from " + MARCA_TABLE_NAME, null); //nul por parametros string
        }
        public void consultaMarca(int id){
            cursor = database.rawQuery("select * from "+MARCA_TABLE_NAME+" where "+ColumnMarca.ID_MARCA+"="+id, null); //nul por parametros string
        }

        public void consultaFamilias(){
            cursor = database.rawQuery("select * from " + FAMILIA_TABLE_NAME, null); //nul por parametros string
        }
        public void consultaFamilia(int id){
            cursor = database.rawQuery("select * from "+FAMILIA_TABLE_NAME+" where "+ColumnFamilia.ID_FAMILIA+"="+id, null); //nul por parametros string
        }

        public void consultaProveedores(){
            cursor = database.rawQuery("select * from "+PROVEEDOR_TABLE_NAME, null); //nul por parametros string
        }
        public void consultaProveedores(String orden){
            cursor = database.rawQuery("select * from "+PROVEEDOR_TABLE_NAME+" order by "+orden, null); //nul por parametros string
        }
        public void consultaProveedor(int id){
            cursor = database.rawQuery("select * from "+PROVEEDOR_TABLE_NAME+" where "+ColumnProveedores.ID_PROVEEDOR+"="+id, null); //nul por parametros string
        }

        public boolean cargarProveedor(){
            values = new ContentValues();
            try{
                if (cursor.moveToNext()) {
                    values.put(ColumnProveedores.ID_PROVEEDOR, cursor.getInt(0));
                    values.put(ColumnProveedores.NOMBRE, cursor.getString(1));
                    values.put(ColumnProveedores.CIUDAD, cursor.getString(2));
                    values.put(ColumnProveedores.ESTADO, cursor.getString(3));
                    values.put(ColumnProveedores.PAIS, cursor.getString(4));
                    values.put(ColumnProveedores.DIRECCION, cursor.getString(5));
                    values.put(ColumnProveedores.TELEFONO, cursor.getString(6));
                    values.put(ColumnProveedores.FECHA_AGREGADO, cursor.getString(7));
                    values.put(ColumnProveedores.ACTIVO, cursor.getInt(8));
                    return true;
                }else{
                    values.put(ColumnProveedores.ID_PROVEEDOR, 0);
                    values.put(ColumnProveedores.NOMBRE, "");
                    values.put(ColumnProveedores.CIUDAD, "");
                    values.put(ColumnProveedores.ESTADO, "");
                    values.put(ColumnProveedores.PAIS, "");
                    values.put(ColumnProveedores.DIRECCION, "");
                    values.put(ColumnProveedores.TELEFONO, "");
                    values.put(ColumnProveedores.FECHA_AGREGADO, "01/01/1900");
                    values.put(ColumnProveedores.ACTIVO, 0);
                    cursor.close();
                    return false;
                }
            }catch(Exception ex){
                System.out.println(ex.getMessage());
                return false;
            }
        }

        public boolean cargarArticulo(){
            values = new ContentValues();
            try {
                if (cursor.moveToNext()) {
                    values.put(ColumnArticulos.ID_ARTICULO, cursor.getInt(0));
                    values.put(ColumnArticulos.NOMBRE, cursor.getString(1));
                    values.put(ColumnArticulos.MODELO, cursor.getString(2));
                    values.put(ColumnArticulos.COSTO, cursor.getString(3));
                    values.put(ColumnArticulos.UBICACION, cursor.getString(4));
                    values.put(ColumnArticulos.MINIMO, cursor.getString(5));
                    values.put(ColumnArticulos.MAXIMO, cursor.getString(6));
                    values.put(ColumnArticulos.CANTIDAD, cursor.getString(7));
                    values.put(ColumnArticulos.FECHA_AGREGADO, cursor.getString(8));
                    values.put(ColumnArticulos.ID_MARCA, cursor.getString(9));
                    values.put(ColumnArticulos.ID_FAMILIA, cursor.getString(10));
                    values.put(ColumnArticulos.ACTIVO, cursor.getInt(11));
                    values.put("ftNombre", cursor.getString(12));
                    values.put("mtNombre", cursor.getString(13));
                    return true;
                } else {
                    values.put(ColumnArticulos.ID_ARTICULO, 0);
                    values.put(ColumnArticulos.NOMBRE, "");
                    values.put(ColumnArticulos.MODELO, "");
                    values.put(ColumnArticulos.COSTO, 0.00);
                    values.put(ColumnArticulos.UBICACION, "");
                    values.put(ColumnArticulos.MINIMO, 0);
                    values.put(ColumnArticulos.MAXIMO, 0);
                    values.put(ColumnArticulos.CANTIDAD, 0);
                    values.put(ColumnArticulos.FECHA_AGREGADO, "01/01/1900");
                    values.put(ColumnArticulos.ID_MARCA, 0);
                    values.put(ColumnArticulos.ID_FAMILIA, 0);
                    values.put(ColumnArticulos.ACTIVO, 0);
                    values.put("ftNombre", "");
                    values.put("mtNombre", "");
                    cursor.close();
                    return false;
                }
            }catch (Exception ex){
                System.out.println(ex.getMessage());
                return false;
            }
        }

        public boolean cargarArticuloProveedor(){
            values = new ContentValues();
            try {
                if (cursor.moveToNext()){
                    values.put(ColumnArticuloProveedor.ID_ARTICULO_PROVEEDOR, cursor.getInt(0));
                    values.put(ColumnArticuloProveedor.ID_ARTICULO, cursor.getString(1));
                    values.put("AP."+ColumnArticuloProveedor.FECHA_AGREGADO, cursor.getString(2));
                    values.put(ColumnProveedores.ID_PROVEEDOR, cursor.getInt(3));
                    values.put(ColumnProveedores.NOMBRE, cursor.getString(4));
                    values.put(ColumnProveedores.CIUDAD, cursor.getString(5));
                    values.put(ColumnProveedores.ESTADO, cursor.getString(6));
                    values.put(ColumnProveedores.PAIS, cursor.getString(7));
                    values.put(ColumnProveedores.DIRECCION, cursor.getString(8));
                    values.put(ColumnProveedores.TELEFONO, cursor.getString(9));
                    values.put(ColumnProveedores.FECHA_AGREGADO, cursor.getString(10));
                    values.put(ColumnProveedores.ACTIVO, cursor.getInt(11));
                    return true;
                } else {
                    values.put(ColumnArticuloProveedor.ID_ARTICULO_PROVEEDOR, 0);
                    values.put(ColumnArticuloProveedor.ID_ARTICULO, 0);
                    values.put("AP."+ColumnArticuloProveedor.FECHA_AGREGADO, "");
                    values.put(ColumnProveedores.ID_PROVEEDOR, 0);
                    values.put(ColumnProveedores.NOMBRE, "");
                    values.put(ColumnProveedores.CIUDAD, "");
                    values.put(ColumnProveedores.ESTADO, "");
                    values.put(ColumnProveedores.PAIS, "");
                    values.put(ColumnProveedores.DIRECCION, "");
                    values.put(ColumnProveedores.TELEFONO, "");
                    values.put(ColumnProveedores.FECHA_AGREGADO, "01/01/1900");
                    values.put(ColumnProveedores.ACTIVO, 0);
                    cursor.close();
                    return false;
                }
            }catch (Exception ex){
                System.out.println(ex.getMessage());
                return false;
            }
        }

        public boolean cargarFamilia(){
            values = new ContentValues();
            try{
                if (cursor.moveToNext()) {
                    values.put(ColumnFamilia.ID_FAMILIA, cursor.getInt(0));
                    values.put(ColumnFamilia.NOMBRE, cursor.getString(1));
                    values.put(ColumnFamilia.FECHA_AGREGADO, cursor.getString(2));
                    return true;
                }else{
                    values.put(ColumnFamilia.ID_FAMILIA, 0);
                    values.put(ColumnFamilia.NOMBRE,"");
                    values.put(ColumnFamilia.FECHA_AGREGADO,"01/01/1900");
                    cursor.close();
                    return false;
                }
            }catch(Exception ex){
                System.out.println(ex.getMessage());
                return false;
            }
        }

        public boolean cargarMarca(){
            values = new ContentValues();
            try{
                if (cursor.moveToNext()) {
                    values.put(ColumnMarca.ID_MARCA, cursor.getInt(0));
                    values.put(ColumnMarca.NOMBRE, cursor.getString(1));
                    values.put(ColumnMarca.FECHA_AGREGADO, cursor.getString(2));
                    return true;
                }else{
                    values.put(ColumnMarca.ID_MARCA, 0);
                    values.put(ColumnMarca.NOMBRE,"");
                    values.put(ColumnMarca.FECHA_AGREGADO,"01/01/1900");
                    cursor.close();
                    return false;
                }
            }catch(Exception ex){
                System.out.println(ex.getMessage());
                return false;
            }
        }

        public ContentValues getProveedor(){
        return values;
    }

        public ContentValues getArticulo(){
        return values;
    }

        public Cursor getCursor(){
            return cursor;
        }

        public Cursor spinnerFamilia(){
            consultaFamilias();
            return cursor;
        }

        public Cursor spinnerMarcas(){
            consultaMarcas();
            return cursor;
        }

        public String consultaMinimos(String mensaje){
            String cadena = "";
            Cursor cur = database.rawQuery("select * from " + ARTICULOS_TABLE_NAME+
                                           " where ("+ColumnArticulos.CANTIDAD+"<"+ColumnArticulos.MINIMO+" or "+
                                           ColumnArticulos.CANTIDAD+"="+ColumnArticulos.MINIMO+") and "+ColumnArticulos.ACTIVO+"=1" , null);
            while (cur.moveToNext()){
                try {
                    cadena += String.format(mensaje+"\n",String.valueOf(cur.getInt(0)));
                }catch (Exception ex){
                    System.out.println(ex.getMessage());
                    break;
                }
            }
            try {
                cur.close();
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
            return cadena;
        }

    }
