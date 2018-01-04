package danielfranke.com.cst2335_androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {

    // CLASS VARIABLES
    protected final String ACTIVITY_NAME = "Login";
    private EditText email;
    private EditText password;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "In onCreate");
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editTextEmailAddress);
        password = findViewById(R.id.editTextPassword);

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref =  getSharedPreferences("email", Context.MODE_PRIVATE ); // email = key
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("email", email.getText().toString());
                editor.putString("password", password.getText().toString());
                editor.apply();

                // Activity to start when button clicked and validation successful
                Intent intent = new Intent(Login.this, StartActivity.class);
                startActivity(intent);
            }
        }));

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");

        SharedPreferences sharedPref = getSharedPreferences("email", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        email.setText(sharedPref.getString("email", "email@domain.com" ));
        password.setText(sharedPref.getString("password", "123456"));
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

}