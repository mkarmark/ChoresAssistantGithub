package com.example.mital.choresassistant;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class CreateAndPostChore extends AppCompatActivity {

    private ChoreDBHelper dbHelper = new ChoreDBHelper(CreateAndPostChore.this);
    private ChoreTypeDBHelper dbTypeHelper = new ChoreTypeDBHelper(CreateAndPostChore.this);
    private UserDBHelper userHelper = new UserDBHelper(CreateAndPostChore.this);
    String email;
    String birthday;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_and_post_chore);
        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");
        birthday = bundle.getString("birthday");
        name = bundle.getString("name");

        System.out.println("In CreateAndPostChore, email is \"" + email + "\"");

        EditText editEmail = (EditText) findViewById(R.id.editText4);
        editEmail.setText(email);

        Spinner spinner = (Spinner) findViewById(R.id.chores_options_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.chores_options_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        dbHelper.onUpgrade(db, 0, 1);
//        SQLiteDatabase dbType = dbTypeHelper.getWritableDatabase();
//        dbTypeHelper.onUpgrade(dbType, 0, 1);
//        dbTypeHelper.insertChoreType("Grocery");
//        dbTypeHelper.insertChoreType("Shopping");
//        dbTypeHelper.insertChoreType("Laundry");
//        dbTypeHelper.insertChoreType("Gardening");
//        dbTypeHelper.insertChoreType("Autoshop");

        final Button saveChoreButton = (Button) findViewById(R.id.btnsavechore);
        saveChoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner spinner = (Spinner) findViewById(R.id.chores_options_spinner);
                String strChore = spinner.getSelectedItem().toString();
                Cursor choreTypeIDCursor = dbTypeHelper.getChoreTypeIDByChoreTypeName(strChore);
                choreTypeIDCursor.moveToFirst();

                EditText editSpecifications = (EditText) findViewById(R.id.editText);
                String strSpecifications = editSpecifications.getText().toString();

                EditText editDate = (EditText) findViewById(R.id.editText2);
                String strDate = editDate.getText().toString();

                EditText editTime = (EditText) findViewById(R.id.editText3);
                String strTime = editTime.getText().toString();

                EditText editEmail = (EditText) findViewById(R.id.editText4);
                String strEmail = editEmail.getText().toString();
                Cursor userIDCursor = userHelper.getUserIDByUserEmail(strEmail);
                userIDCursor.moveToFirst();

                EditText editPhoneNumber = (EditText) findViewById(R.id.editText5);
                String strPhoneNumber = editPhoneNumber.getText().toString();

                EditText editLocation = (EditText) findViewById(R.id.editText6);
                String strLocation = editLocation.getText().toString();

//                System.out.println("strChore: " + strChore);
//                System.out.println("strSpecifications: " + strSpecifications);
//                System.out.println("strDate: " + strDate);
//                System.out.println("strTime: " + strTime);
//                System.out.println("strEmail: " + strEmail);
//                System.out.println("strPhoneNumber: " + strPhoneNumber);
//                System.out.println("strLocation: " + strLocation);
                dbHelper.insertChore(choreTypeIDCursor.getInt(choreTypeIDCursor.getColumnIndex(ChoreTypeDBHelper.CHORE_TYPE_COLUMN_ID)), "test specifications", userIDCursor.getInt(userIDCursor.getColumnIndex(UserDBHelper.USER_COLUMN_ID)));

                Intent i = new Intent(CreateAndPostChore.this, Options.class);
                Bundle b = new Bundle();
                b.putString("email", email);
                b.putString("birthday", birthday);
                b.putString("name", name);
                i.putExtras(b);
                startActivity(i);

            }
        });
    }
}
