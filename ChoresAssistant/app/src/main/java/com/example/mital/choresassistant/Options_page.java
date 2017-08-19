package com.example.mital.choresassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

public class Options_page extends AppCompatActivity {
    LoginButton loginButton;
    boolean isLogOutButton = false;
    String email;
    String birthday;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext(), new FacebookSdk.InitializeCallback() {
            @Override
            public void onInitialized() {
                if(AccessToken.getCurrentAccessToken() == null){
                    Intent i = new Intent(Options_page.this, MainActivity.class);
                    startActivity(i);
                } else {
                    isLogOutButton = true;
                }
            }
        });
        Intent i = getIntent();
        Bundle b = i.getExtras();
        email = b.getString("email");
        birthday = getIntent().getStringExtra("birthday");
        name = getIntent().getStringExtra("name");
        setContentView(R.layout.activity_options_page);
        Bundle bundle = getIntent().getExtras();
        loginButton = (LoginButton)findViewById(R.id.login_button);
        if (isLogOutButton) {
            loginButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                            .Callback() {
                        @Override
                        public void onCompleted(GraphResponse graphResponse) {

                            LoginManager.getInstance().logOut();

                        }
                    }).executeAsync();
                    try{
                        Intent i = new Intent(Options_page.this, MainActivity.class);
                        startActivity(i);
                    }
                    catch(Exception ex)
                    {
                        Log.e("main",ex.toString());
                    }
                }
            });
        }

        final Button postChoreButton = (Button)findViewById(R.id.btnpostchore);
        postChoreButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                setContentView(R.layout.activity_post_chore);
                try{
                    Intent i = new Intent(Options_page.this, CreateAndPostChore.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("email", email);
                    bundle.putString("birthday", birthday);
                    bundle.putString("name", name);
                    i.putExtras(bundle);
                    startActivity(i);
                }
                catch(Exception ex)
                {
                    Log.e("main",ex.toString());
                }
            }
        });

        final Button completeChoreButton = (Button)findViewById(R.id.btncompletechore);
        completeChoreButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try{
                    Intent i = new Intent(Options_page.this, CompleteChore.class);
                    startActivity(i);
                }
                catch(Exception ex)
                {
                    Log.e("main",ex.toString());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent i = getIntent();
        Bundle b = i.getExtras();
        email = getIntent().getStringExtra("email");
        birthday = getIntent().getStringExtra("birthday");
        name = getIntent().getStringExtra("name");
        // ... do what you wanna do with the intent
    }
}
