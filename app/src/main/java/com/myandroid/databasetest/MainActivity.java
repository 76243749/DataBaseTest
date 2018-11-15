package com.myandroid.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button createBtn;
    private Button addBtn;
    private Button editBtn;
    private Button delBtn;
    private Button selectBtn;
    private MyDateBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDateBaseHelper(this,"BookStory.db",null,2);

        createBtn = (Button) findViewById(R.id.create_btn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getReadableDatabase();
            }
        });

        addBtn = (Button) findViewById(R.id.add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                ContentValues values = new ContentValues();
                values.put("name","android入门");
                values.put("author","大学");
                values.put("pages",450);
                values.put("price",20.45);
                db.insert("book",null,values);
                values.clear();
                values.put("name","android入门");
                values.put("author","大学");
                values.put("pages",450);
                values.put("price",20.45);
                db.insert("book",null,values);
                Toast.makeText(MainActivity.this,"插入成功",Toast.LENGTH_SHORT).show();
            }
        });

        editBtn = (Button) findViewById(R.id.edit_btn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price",55.44);
                db.update("book",values,"name = ?",new String[]{"android入门"});
                Toast.makeText(MainActivity.this,"更新成功",Toast.LENGTH_SHORT).show();
            }
        });

        delBtn = (Button) findViewById(R.id.del_btn);
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("book","pages > ?",new String[]{"400"});
                Toast.makeText(MainActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
            }
        });

        selectBtn = (Button) findViewById(R.id.select_btn);
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("book",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity:","name:"+name);
                        Log.d("MainActivity:","author:"+author);
                        Log.d("MainActivity:","pages:"+pages);
                        Log.d("MainActivity:","price:"+price);
                    }while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }
}
