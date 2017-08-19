package com.example.mital.choresassistant;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CompleteChore extends AppCompatActivity {

    private ChoreDBHelper dbHelper = new ChoreDBHelper(CompleteChore.this);
    private ListView listView;
    private ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_chore);
        Cursor result = dbHelper.getAllChores();
        result.moveToFirst();
        list= new ArrayList<String>();
        String choreType = result.getString(result.getColumnIndex(ChoreDBHelper.CHORE_COLUMN_TYPE));
        String choreSpecifications = result.getString(result.getColumnIndex(ChoreDBHelper.CHORE_COLUMN_SPECIFICATIONS));
        String choreDate = result.getString(result.getColumnIndex(ChoreDBHelper.CHORE_COLUMN_DATE));
        String choreTime = result.getString(result.getColumnIndex(ChoreDBHelper.CHORE_COLUMN_TIME));
        String choreEmail = result.getString(result.getColumnIndex(ChoreDBHelper.CHORE_COLUMN_EMAIL));
        String chorePhoneNumber = result.getString(result.getColumnIndex(ChoreDBHelper.CHORE_COLUMN_PHONENUMBER));
        String choreLocation = result.getString(result.getColumnIndex(ChoreDBHelper.CHORE_COLUMN_LOCATION));
        String listStr = "Type: " + choreType + "\nSpecifications: " + choreSpecifications + "\nDeadline: " + choreDate + " " + choreTime + "\nContact: " + choreEmail + ", " + chorePhoneNumber + "\nLocation: " + choreLocation;
        list.add(listStr);

        while (result.moveToNext()) {
            choreType = result.getString(result.getColumnIndex(ChoreDBHelper.CHORE_COLUMN_TYPE));
            choreSpecifications = result.getString(result.getColumnIndex(ChoreDBHelper.CHORE_COLUMN_SPECIFICATIONS));
            choreDate = result.getString(result.getColumnIndex(ChoreDBHelper.CHORE_COLUMN_DATE));
            choreTime = result.getString(result.getColumnIndex(ChoreDBHelper.CHORE_COLUMN_TIME));
            choreEmail = result.getString(result.getColumnIndex(ChoreDBHelper.CHORE_COLUMN_EMAIL));
            chorePhoneNumber = result.getString(result.getColumnIndex(ChoreDBHelper.CHORE_COLUMN_PHONENUMBER));
            choreLocation = result.getString(result.getColumnIndex(ChoreDBHelper.CHORE_COLUMN_LOCATION));
            listStr = "Type: " + choreType + "\nSpecifications: " + choreSpecifications + "\nDeadline: " + choreDate + " " + choreTime + "\nContact: " + choreEmail + ", " + chorePhoneNumber + "\nLocation: " + choreLocation;
            list.add(listStr);
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView = (ListView)findViewById(R.id.listView1);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(CompleteChore.this, "Completing Chore: " + CompleteChore.this.list.get(i),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
