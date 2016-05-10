package com.example.valentina.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


public class MainActivity extends AppCompatActivity {

    private EditText ePeluchito, ePrecio, eCantidad, eIden,eItem;
    private TextView eScreem;
    private Cursor c;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaseDeDatos peluche = new BaseDeDatos(this, "basededatos", null, 1);
        SQLiteDatabase bd = peluche.getWritableDatabase();


        ContentValues registro = new ContentValues();//Es para guardar los datos ingresados
        registro.put("id", 7);//tag debe aparecer igual que en la clase BaseDeDatos
        registro.put("nombre", "ganancias");
        registro.put("cantidad", 0);
        registro.put("precio", 0);

        bd.insert("database", null, registro);
        bd.close();// cerrar para que guarde

        ePeluchito = (EditText) findViewById(R.id.ePeluchito);
        ePrecio = (EditText) findViewById(R.id.ePrecio);
        eIden = (EditText) findViewById(R.id.eIden);
        eCantidad = (EditText) findViewById(R.id.eCantidad);
        eScreem = (TextView) findViewById(R.id.eScreem);
        eItem = (EditText) findViewById(R.id.eItem);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    //funciones


    public void agregar(View v) {
        BaseDeDatos peluche = new BaseDeDatos(this, "basededatos", null, 1);
        SQLiteDatabase bd = peluche.getWritableDatabase();

        String nombre = ePeluchito.getText().toString();
        String id = eIden.getText().toString();
        String cantidad = eCantidad.getText().toString();
        String precio = ePrecio.getText().toString();

        ContentValues registro = new ContentValues();//Es para guardar los datos ingresados
        registro.put("id", id);//tag debe aparecer igual que en la clase BaseDeDatos
        registro.put("nombre", nombre);
        registro.put("cantidad", cantidad);
        registro.put("precio", precio);

        bd.insert("database", null, registro);
        bd.close();// cerrar para que guarde

        ePeluchito.setText("");
        eIden.setText("");
        ePrecio.setText("");
        eCantidad.setText("");
        Toast.makeText(this, "Se guardaron los datos correctamente",
                Toast.LENGTH_SHORT).show();

    }


    public void buscar(View v) {
        BaseDeDatos peluche = new BaseDeDatos(this, "basededatos", null, 1);
        SQLiteDatabase bd = peluche.getWritableDatabase();
        if (TextUtils.isEmpty(eIden.getText())) {
            Toast.makeText(getBaseContext(), "Coloque el id!", Toast.LENGTH_SHORT).show();
        }else {
            String id = eIden.getText().toString();

            c = bd.rawQuery("select nombre, cantidad,precio from database where id=" + id, null);
            if (c.moveToFirst()) {

                ePeluchito.setText(c.getString(0));
                eCantidad.setText(c.getString(1));
                ePrecio.setText(c.getString(2));


            } else {
                Toast.makeText(this, "No existe peluche con dicha id",
                        Toast.LENGTH_SHORT).show();
            }
            bd.close();
        }
    }

    public void actualizar(View v) {
        BaseDeDatos peluche = new BaseDeDatos(this, "basededatos", null, 1);
        SQLiteDatabase bd = peluche.getWritableDatabase();

        if (TextUtils.isEmpty(eIden.getText())) {
            Toast.makeText(getBaseContext(), "Coloque el id!", Toast.LENGTH_SHORT).show();
        }else {
            String id = eIden.getText().toString();
            String cantidad = eCantidad.getText().toString();

            ContentValues registro = new ContentValues();//Es para guardar los datos ingresados
            registro.put("id", id);//tag debe aparecer igual que en la clase BaseDeDatos
            registro.put("cantidad", cantidad);


            int cant = bd.update("database", registro, "id=" + id, null);
            bd.close();
            if (cant == 1)
                Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT)
                        .show();
            else
                Toast.makeText(this, "no existe peluche con dicho id",
                        Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminar(View v) {
        BaseDeDatos peluche = new BaseDeDatos(this, "basededatos", null, 1);
        SQLiteDatabase bd = peluche.getWritableDatabase();

        if (TextUtils.isEmpty(eIden.getText())) {
            Toast.makeText(getBaseContext(), "Coloque el id!", Toast.LENGTH_SHORT).show();
        }else {
            String id = eIden.getText().toString();
            int cant = bd.delete("database", "id=" + id, null);
            bd.close();
            eIden.setText("");
            ePeluchito.setText("");
            ePrecio.setText("");
            eCantidad.setText("");

            if (cant == 1)
                Toast.makeText(this, "Se borró el peluche con dicho id",
                        Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "No existe peluche con ese id",
                        Toast.LENGTH_SHORT).show();
        }
    }

    public void inventario(View v) {
        BaseDeDatos peluche = new BaseDeDatos(this, "basededatos", null, 1);
        SQLiteDatabase bd = peluche.getWritableDatabase();
        double i;
        String id ;
        String cadena = "PELUCHITO  CANTIDAD  PRECIO \n";
        for(i =1 ; i<7 ;i++){
            id = Double.toString(i);

            c = bd.rawQuery("select nombre, cantidad,precio from database where id=" + id, null);
            if (c.moveToFirst()) {

                cadena = cadena + (c.getString(0)) + " " +(c.getString(1)) + " " +(c.getString(2))+"\n";
            }

            }
        eScreem.setText(cadena);
        bd.close();

    }

    public void sale(View v){
        BaseDeDatos peluche = new BaseDeDatos(this, "basededatos", null, 1);
        SQLiteDatabase bd = peluche.getWritableDatabase();
        double cant = 0;
        if (TextUtils.isEmpty(eIden.getText())) {
            Toast.makeText(getBaseContext(), "Coloque el id!", Toast.LENGTH_SHORT).show();
        }else {
            String ite = eItem.getText().toString();
            String id = eIden.getText().toString();
            double item = Double.parseDouble(ite);
            double precio = 0, ganancia = 0;

            c = bd.rawQuery("select nombre, cantidad,precio from database where id=" + id, null);
            if (c.moveToFirst()) {

                ePeluchito.setText(c.getString(0));
                String cantidad = (c.getString(1));
                String prec = (c.getString(2));
                ePrecio.setText(prec);
                precio = Integer.parseInt(prec);
                cant = Integer.parseInt(cantidad);
                double total = cant - item;
                String res = Double.toString(total);

                ContentValues registro = new ContentValues();//Es para guardar los datos ingresados
                registro.put("id", id);//tag debe aparecer igual que en la clase BaseDeDatos
                registro.put("cantidad", res);


                int canti = bd.update("database", registro, "id=" + id, null);
                // bd.close();
                if (canti == 1)
                    Toast.makeText(this, "se realizo la venta", Toast.LENGTH_SHORT)
                            .show();
                else
                    Toast.makeText(this, "venta no realizada",
                            Toast.LENGTH_SHORT).show();

            }
            if (cant <= 5) {
                Toast.makeText(this, "se estan acabando los peluches", Toast.LENGTH_SHORT)
                        .show();
            }

            c = bd.rawQuery("select precio from database where id=" + 7, null);
            if (c.moveToFirst()) {


                String gan = (c.getString(0));
                ganancia = Integer.parseInt(gan);

                double total = ganancia + (item * precio);
                String res = Double.toString(total);

                ContentValues registro2 = new ContentValues();//Es para guardar los datos ingresados
                registro2.put("id", 7);//tag debe aparecer igual que en la clase BaseDeDatos
                registro2.put("precio", res);


                int canti = bd.update("database", registro2, "id=" + 7, null);
                bd.close();
                if (canti == 1)
                    Toast.makeText(this, "se guardo ganancia", Toast.LENGTH_SHORT)
                            .show();
                else
                    Toast.makeText(this, "venta no se guardo ganancia",
                            Toast.LENGTH_SHORT).show();

            }
        }

        }

    public void ganancias(View v) {
        BaseDeDatos peluche = new BaseDeDatos(this, "basededatos", null, 1);
        SQLiteDatabase bd = peluche.getWritableDatabase();

        String id = "7";



        c = bd.rawQuery("select nombre, precio from database where id=" + id, null);
        if (c.moveToFirst()) {

            String a =(c.getString(0));
            String b = (c.getString(1));

            eScreem.setText(a+" "+b);



        } else {
            Toast.makeText(this, "No hay ganancias",
                    Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }



}
