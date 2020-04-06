package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText name, price, id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       name = (EditText)findViewById(R.id.name);
       price = (EditText) findViewById(R.id.price);
       id = (EditText)findViewById(R.id.id);
    }

    //ALTA - ADD
    public void add (View view){
        adminSQL db = new adminSQL(this, "admin" , null , 1);
        SQLiteDatabase base = db.getWritableDatabase();

        String codigo = id.getText().toString();
        String nombre = name.getText().toString();
        String precio = price.getText().toString();

        if (!codigo.isEmpty() && !nombre.isEmpty() && !precio.isEmpty()){
            ContentValues cv = new ContentValues();
            cv.put("codigo" , codigo);
            cv.put("description", nombre);
            cv.put("price" , precio);

            base.insert("articulos" , null , cv);
            base.close();

            id.setText("");
            name.setText("");
            price.setText("");

            Toast.makeText(this, "Save Successful", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void search (View view){
        adminSQL db = new adminSQL(this, "admin" , null , 1);
        SQLiteDatabase base = db.getWritableDatabase();

        String codigo = id.getText().toString();
        if (!codigo.isEmpty()){
            Cursor fila = base.rawQuery("select description, price from articulos where codigo = " + codigo , null);
            if (fila.moveToFirst()){
                name.setText(fila.getString(0));
                price.setText(fila.getString(1));

            }else{
                Toast.makeText(this, "Object not found" , Toast.LENGTH_SHORT).show();
            }
            base.close();
        }
        else{
            Toast.makeText(this, "Please fill the id field" , Toast.LENGTH_LONG).show();
        }
    }

    public void delete(View view){
        adminSQL db = new adminSQL(this, "admin" , null , 1);
        SQLiteDatabase base = db.getWritableDatabase();
        String codigo = id.getText().toString();
        if (!codigo.isEmpty()) {
           int cantidad = base.delete("articulos" , "codigo =" + codigo , null);
           base.close();
            id.setText("");
            name.setText("");
            price.setText("");

            if (cantidad > 0){
                Toast.makeText(this, "Delete successful" , Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Item not found" , Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Please fill the id field to delete" , Toast.LENGTH_SHORT).show();
        }

    }

    public void modify (View view){
        adminSQL db = new adminSQL(this, "admin" , null , 1);
        SQLiteDatabase base = db.getWritableDatabase();

        String codigo = id.getText().toString();
        String nombre = name.getText().toString();
        String precio = price.getText().toString();
        if (!codigo.isEmpty() && !nombre.isEmpty() && !precio.isEmpty()){
            ContentValues cv = new ContentValues();
            cv.put("codigo" , codigo);
            cv.put("description", nombre);
            cv.put("price" , precio);
            int cantidad = base.update("articulos" , cv , "codigo =" + codigo, null);
            base.close();
            if (cantidad > 0){
                Toast.makeText(this, "Edit Successful" , Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Not Found" , Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Please fill all fields to modify" , Toast.LENGTH_SHORT).show();
        }
    }
}
