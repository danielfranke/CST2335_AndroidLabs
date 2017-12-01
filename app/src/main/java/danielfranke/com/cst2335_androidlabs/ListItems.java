package danielfranke.com.cst2335_androidlabs;

import android.app.Activity;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.Switch;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;



public class ListItems extends Activity {

    // CLASS VARIABLES
    protected final String ACTIVITY_NAME = "ListItems";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageButton buttonCamera;
    private Switch switchStatus; // on or off
    private CheckBox checkBoxStatus; // checked or unchecked

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "in onCreate");
        setContentView(R.layout.activity_list_items);

        // Camera picture
        buttonCamera = findViewById(R.id.buttonCamera);
        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        // Switch
        final String switchOn = getString(R.string.switchOn);
        final String switchOff = getString(R.string.switchOff);

        switchStatus = findViewById(R.id.switchListItems);
        switchStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CharSequence text = switchOn;

                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(ListItems.this, text, duration);
                    toast.show();
                } else {
                    CharSequence text = switchOff;

                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(ListItems.this, text, duration);
                    toast.show();
                }
            }

        });

        // Checkbox
        checkBoxStatus = findViewById(R.id.checkBoxListItems);
        checkBoxStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ListItems.this);
                builder.setMessage(R.string.alertSelectOptionsMessage) // Add dialog message to strings.xml

                        .setTitle(R.string.alertSetTitle)
                        .setPositiveButton((R.string.alertPositiveResponse), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                                Intent resultIntent = new Intent(  );

                                String response = getString(R.string.response);

                                resultIntent.putExtra("Response", response); // *** FIX - use xml for message
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();
                            }
                        })
                        // User cancelled the dialog
                        .setNegativeButton((R.string.alertNegativeResponse), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
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

    // take picture using camera
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    // request thumbnail
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.  get("data");
            buttonCamera.setImageBitmap(imageBitmap);
        }
    }

}
