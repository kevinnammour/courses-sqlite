package com.kevedvoc.courses_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list_view = (ListView) findViewById(R.id.listview);
        ArrayList<String> course_names = new ArrayList<>(), course_links = new ArrayList<>();

        try {
            SQLiteDatabase sql_lite_db = this.openOrCreateDatabase("coursesdb", MODE_PRIVATE, null);
            // sql_lite_db.execSQL("CREATE Table if not exists courses (name VARCHAR, link VARCHAR)");
            // sql_lite_db.execSQL("INSERT INTO courses(name, link) VALUES ('Mobile Computing', 'https://ionicframework.com/'), ('Web programming', 'https://reactjs.org/'), ('DBMS', 'https://www.mongodb.com/')");
            Cursor cursor = sql_lite_db.rawQuery("SELECT * FROM courses", null);
            int name_index = cursor.getColumnIndex("name");
            int link_index = cursor.getColumnIndex("link");
            cursor.moveToFirst();
            int i = 0;
            while (i < cursor.getCount()) {
                course_names.add(cursor.getString(name_index));
                course_links.add(cursor.getString(link_index));
                cursor.moveToNext();
                i++;
            }
            ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, course_names);
            list_view.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("link", course_links.get(i));
                startActivity(intent);
            }
        });
    }
}