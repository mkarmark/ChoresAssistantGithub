package com.example.mital.choresassistant;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class PostChore extends AppCompatActivity {

    public final static String KEY_EXTRA_CONTACT_ID = "KEY_EXTRA_CONTACT_ID";
    private static int versionControl = 2;

    private ListView listView;
    FeedReaderDBHelper dbHelper = new FeedReaderDBHelper(PostChore.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_chore);
        final Button saveChoreButton = (Button)findViewById(R.id.btnsavechore);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        System.out.println(db);



        //dbHelper.onUpgrade(db, versionControl, versionControl +1);
        saveChoreButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(PostChore.this, "Saving Chore!",
                        Toast.LENGTH_LONG).show();
                System.out.println("a");
                // Gets the data repository in write mode

                System.out.println("b");

                versionControl++;
                System.out.println("version control:" + versionControl);
                Cursor result = dbHelper.getAllPersons();
                System.out.println("0Num entries: " + result.getCount());
                dbHelper.insertPerson("vc:"+versionControl, "F", 20);
                result = dbHelper.getAllPersons();
                System.out.println("1Num entries: " + result.getCount());
                result.moveToFirst();
                String name = result.getString(result.getColumnIndex(FeedReaderDBHelper.PERSON_COLUMN_NAME));
                System.out.println("name: " + name);

                while (result.moveToNext()) {
                    name = result.getString(result.getColumnIndex(FeedReaderDBHelper.PERSON_COLUMN_NAME));
                    System.out.println("name: " + name);
                }
                System.out.println("no more names");

            }
        });

        Button button = (Button) findViewById(R.id.addNew);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostChore.this, CreateOrEditActivity.class);
                intent.putExtra(KEY_EXTRA_CONTACT_ID, 0);
                startActivity(intent);
            }
        });

        dbHelper = new FeedReaderDBHelper(this);

        final Cursor cursor = dbHelper.getAllPersons();
        String [] columns = new String[] {
                FeedReaderDBHelper.PERSON_COLUMN_ID,
                FeedReaderDBHelper.PERSON_COLUMN_NAME
        };
        int [] widgets = new int[] {
                R.id.personID,
                R.id.personName
        };

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.person_info,
                cursor, columns, widgets, 0);
        listView = (ListView)findViewById(R.id.listView1);
        listView.setAdapter(cursorAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                Cursor itemCursor = (Cursor) PostChore.this.listView.getItemAtPosition(position);
                int personID = itemCursor.getInt(itemCursor.getColumnIndex(FeedReaderDBHelper.PERSON_COLUMN_ID));
                Intent intent = new Intent(getApplicationContext(), CreateOrEditActivity.class);
                intent.putExtra(KEY_EXTRA_CONTACT_ID, personID);
                startActivity(intent);
            }
        });
    }
}
