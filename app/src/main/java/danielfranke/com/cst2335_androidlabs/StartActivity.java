package danielfranke.com.cst2335_androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class StartActivity extends Activity {

    private static final String ACTIVITY_NAME = "StartActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME, "In onCreate()");


        Button b1 = findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View view) {
                                      Intent intent = new Intent(StartActivity.this, ListItems.class);
                                      startActivityForResult(intent, 10);
                                  }
                              }
        );


        Button b2 = findViewById(R.id.buttonLogin);
        b2.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View view) {
                                      Intent intent = new Intent(StartActivity.this, Login.class);
                                      startActivityForResult(intent, 10);
                                  }
                              }
        );

        Button b3 = findViewById(R.id.buttonChat);
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked Start Chat");
                Intent intent = new Intent(StartActivity.this, ChatWindow.class);
                startActivity(intent);
            }
        });

        Button b4 = findViewById(R.id.buttonWeather);

        b4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Log.i(ACTIVITY_NAME,"User clicked Weather Forecast Button");
                Intent intent = new Intent(StartActivity.this,WeatherForecast.class);
                startActivity(intent);
            }
        });


//        Button toolbar = (Button)findViewById(R.id.toolbarbutton);
//        toolbar.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view){
//                Log.i(ACTIVITY_NAME,"User clicked toolbar Button");
//                Intent intent = new Intent(StartActivity.this,TestToolbar.class);
//                startActivity(intent);
//            }
//        });
    }



    protected void onActivityResult(int requestCode, int responseCode, Intent data)  {
        if (requestCode == 10)  {
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        }

        if(responseCode==Activity.RESULT_OK){
            String result = data.getStringExtra("Response");
            Toast toast = Toast.makeText(this , result, Toast.LENGTH_LONG);
            toast.show();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
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