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

    private ChoreDBHelper choreDBHelper = new ChoreDBHelper(CompleteChore.this);
    private ChoreTypeDBHelper choreTypeDBHelper = new ChoreTypeDBHelper(CompleteChore.this);
    private UserDBHelper userDBHelper = new UserDBHelper(CompleteChore.this);
    private ListView listView;
    private ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_chore);
        Cursor choreResult = choreDBHelper.getAllChores();
        choreResult.moveToFirst();
        list= new ArrayList<String>();

         do {
             int choreTypeID = choreResult.getInt(choreResult.getColumnIndex(ChoreDBHelper.CHORE_COLUMN_TYPE_ID));
             Cursor choreTypeResult = choreTypeDBHelper.getChoreTypeNameByChoreTypeID(choreTypeID);
             String choreType = choreTypeResult.getString(choreTypeResult.getColumnIndex(ChoreTypeDBHelper.CHORE_TYPE_COLUMN_NAME));
             String choreSpecifications = choreResult.getString(choreResult.getColumnIndex(ChoreDBHelper.CHORE_COLUMN_SPECIFICATIONS));
             int userID = choreResult.getInt(choreResult.getColumnIndex(ChoreDBHelper.CHORE_COLUMN_USER_ID));
             Cursor userResult = userDBHelper.getUserEmailPhoneAddressByUserID(userID);
             String choreEmail = userResult.getString(userResult.getColumnIndex(UserDBHelper.USER_COLUMN_EMAIL));
             String chorePhoneNumber = userResult.getString(userResult.getColumnIndex(UserDBHelper.USER_COLUMN_PHONE));
             String choreLocation = userResult.getString(userResult.getColumnIndex(UserDBHelper.USER_COLUMN_ADDRESS));
             String listStr = "Type: " + choreType + "\nSpecifications: " + choreSpecifications + "\nContact: " + choreEmail + ", " + chorePhoneNumber + "\nLocation: " + choreLocation;
             list.add(listStr);
        } while (choreResult.moveToNext());
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
