//11/5/2018 code Adapted from: https://github.com/tonikami/
package fall2018.csc2017.LaunchCentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import fall2018.csc2017.slidingtiles.R;

/**
 * Login Activity
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Adds user login functionality to R.layout.login xml file.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final EditText etUsername = findViewById(R.id.etUsername);
        final EditText etPassword= findViewById(R.id.etPassword);
        final ImageButton bLogin = findViewById(R.id.bLogin);
        final TextView registerLink = findViewById(R.id.tvRegister);


        registerLink.setOnClickListener(new View.OnClickListener() {
            /**
             * Changes activity to the Register screen
             */
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            /**
             * Creates Login requests and response listeners
             */
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responder = new Response.Listener<String>() {
                    /**
                     * Converts response into JSONObject
                     * Checks if response is successful
                     */
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                String name = jsonObject.getString("name");
                                Toast.makeText(LoginActivity.this, "Welcome " + name + "!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, GameLaunchActivity.class);
                                intent.putExtra("username", username);
                                LoginActivity.this.startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password, responder);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}
