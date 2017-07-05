package flexbillet.flexbillet;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.Toast;

import data_base_history.DatabaseHandler;

public class Welcome_screen extends Activity {
    RelativeLayout Rlv_get_started;
    SharedPreferences shared;
    String Session_key = "empty";
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome_screen);
        Rlv_get_started = (RelativeLayout) findViewById(R.id.Rlv_get_started);
        shared = getSharedPreferences("Flexbillet", MODE_PRIVATE);
        Session_key = (shared.getString("Session_id", "empty"));
        final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
        db = new DatabaseHandler(Welcome_screen.this);
        db.delete();


        Rlv_get_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rlv_get_started.startAnimation(buttonClick);
                if (iscameraAllowed()) {
                    //If permission is already having then showing the toast
                    // Toast.makeText(We.this,"You already have the permission",Toast.LENGTH_LONG).show();
                    //Existing the method with return
                    // return;
                    if (!Session_key.contentEquals("empty")) {
                        Intent i1 = new Intent(Welcome_screen.this, Login_screen.class);
                        startActivity(i1);
                        finish();
                        Welcome_screen.this.overridePendingTransition(R.anim.slide_in_left,
                                R.anim.slide_out_left);
                    } else {
                        Intent i1 = new Intent(Welcome_screen.this, Login_screen.class);
                        startActivity(i1);
                        finish();
                        Welcome_screen.this.overridePendingTransition(R.anim.slide_in_left,
                                R.anim.slide_out_left);
                    }
                    // Rlv_get_started.setEnabled(true);
                } else {
                    // Rlv_get_started.setEnabled(false);
                    requestcameraPermission();

                    //If the app has not the permission then asking for the permission

                }


            }
        });
    }

    private boolean iscameraAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    //Requesting permission
    private void requestcameraPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == 1) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (!Session_key.contentEquals("empty")) {
                    Intent i1 = new Intent(Welcome_screen.this, Login_screen.class);
                    startActivity(i1);
                    finish();
                    Welcome_screen.this.overridePendingTransition(R.anim.slide_in_left,
                            R.anim.slide_out_left);
                } else {
                    Intent i1 = new Intent(Welcome_screen.this, Login_screen.class);
                    startActivity(i1);
                    finish();
                    Welcome_screen.this.overridePendingTransition(R.anim.slide_in_left,
                            R.anim.slide_out_left);
                }
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // code here to show dialog
        //super.onBackPressed();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure,You wanted to Close");

        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //alertDialog.dismiss();
                arg0.dismiss();
            }
        });
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }// optional depending on your needs


}
