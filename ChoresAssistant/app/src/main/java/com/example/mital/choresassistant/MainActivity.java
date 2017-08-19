package com.example.mital.choresassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    LoginButton loginButton;
    CallbackManager callbackManager;
    String email;
    String birthday; // 01/31/1980 format
    String name;
    boolean isLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext(), new FacebookSdk.InitializeCallback() {
            @Override
            public void onInitialized() {
                if(AccessToken.getCurrentAccessToken() != null) {
                    isLoggedIn = true;
                }
            }
        });
        if (isLoggedIn) {
            Intent i = new Intent(MainActivity.this, Options_page.class);
            Bundle b = new Bundle();
            b.putString("email", email);
            b.putString("birthday", birthday);
            b.putString("name", name);
            i.putExtras(b);
            startActivity(i);
            return;
        }
        setContentView(R.layout.activity_main);
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(MainActivity.this, "Login Success!", Toast.LENGTH_LONG).show();
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                // Application code
                                try {
                                    MainActivity.this.email = object.getString("email");
                                    MainActivity.this.birthday = object.getString("birthday"); // 01/31/1980 format
                                    MainActivity.this.name = object.getString("name");
                                } catch (JSONException jsone) {
                                    System.out.println("json exception");
                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
                Intent i = new Intent(MainActivity.this, Options_page.class);
                i.putExtra("email", email);
                i.putExtra("birthday", birthday);
                i.putExtra("name", name);
                startActivity(i);
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "Login Canceled!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(MainActivity.this, "Login Errored!", Toast.LENGTH_LONG).show();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("LoginActivity", response.toString());

                        // Application code
                        try {
                            if (object == null) return;
                            MainActivity.this.email = object.getString("email");
                            MainActivity.this.birthday = object.getString("birthday"); // 01/31/1980 format
                            MainActivity.this.name = object.getString("name");
                            if (isLoggedIn) {
                                Intent i = new Intent(MainActivity.this, Options_page.class);
                                Bundle b = new Bundle();
                                b.putString("email", email);
                                b.putString("birthday", birthday);
                                b.putString("name", name);
                                i.putExtras(b);
                                startActivity(i);
                                return;
                            }

                        } catch (JSONException jsone) {
                            System.out.println("json exception");
                        }

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");
        request.setParameters(parameters);
        request.executeAsync();

    }
}