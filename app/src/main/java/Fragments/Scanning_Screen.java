package Fragments;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.zxing.Result;


import org.json.JSONObject;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

//import Service_handler.ServiceHandler;
import camera_permission_uttility.Utility;
import data_base_history.DatabaseHandler;
import data_base_history.History_list;
import flexbillet.flexbillet.MainActivity;
import flexbillet.flexbillet.R;
import flexbillet.flexbillet.Ticket_Scanning_screen;
import io.swagger.client.ApiException;
import io.swagger.client.api.CheckinApi;
import io.swagger.client.model.CreateValidation;
import io.swagger.client.model.Validation;
import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.core.ViewFinderView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Login_with_qr.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Login_with_qr#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Scanning_Screen extends Fragment implements ZXingScannerView.ResultHandler {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ImageView img_scan;
    private ProgressDialog pDialog;
    private ZXingScannerView mScannerView;
    NotificationCompat.Builder notification;
    private static final int uniqueid = 123;

    final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String Str_id = "no_response";
    String Barcode_value = "no_barcode_value";

    ImageView Img_Scan_result, Img_start_scan;
    FrameLayout preview;
    EditText Edt_txt_scan_result;
    String Session_id, Str_qr_result;
    String Scan_result = "no_status";
    String Str_checksound = "default";
    String Str_Check_flash = "off";
    SharedPreferences shared;
    DatabaseHandler db;
    public Scanning_Screen() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        img_scan = (ImageView) view.findViewById(R.id.imageView6);
        preview = (FrameLayout) view.findViewById(R.id.camera_preview);
        Img_Scan_result = (ImageView) view.findViewById(R.id.scan_image);
        Edt_txt_scan_result = (EditText) view.findViewById(R.id.editText3);
         db = new DatabaseHandler(getActivity());
      //  getActivity().registerReceiver(new PhoneUnlockedReceiver(), new IntentFilter("android.intent.action.USER_PRESENT"));

        shared = getActivity().getSharedPreferences("Flexbillet", getActivity().MODE_PRIVATE);
        Session_id = (shared.getString("Session_id", "empty"));
        Str_checksound = (shared.getString("sound", "default"));
        Str_Check_flash = (shared.getString("Flash", "off"));

        Barcode_value = (shared.getString("barcode_value", "no_barcode_value"));

        //preview.addView(mCameraPreview);
        mScannerView = new ZXingScannerView(getActivity());

        mScannerView = new ZXingScannerView(getActivity()) {
            @Override
            protected IViewFinder createViewFinderView(Context context) {
                ViewFinderView finderView = new ViewFinderView(context);
                finderView.setLaserColor(Color.RED);
                finderView.setBorderColor(Color.TRANSPARENT);

                finderView.setMaskColor(Color.TRANSPARENT);
                return finderView;
            }
        };


        // Programmatically initialize the scanner view
        // setContentView(mScannerView);
        preview.addView(mScannerView);
      //  mScannerView.set

        mScannerView.setResultHandler(this);

        //CameraManager cm =
        // Register ourselves as a handler for scan results.
        // boolean result = Utility.checkPermission(getActivity());

        // if (result) {
        mScannerView.startCamera();
       // mScannerView.
        if (Str_Check_flash.contentEquals("on")){
            mScannerView.setFlash(true);
        }else if (Str_Check_flash.contentEquals("off")){
            mScannerView.setFlash(false);
        }else if (Str_Check_flash.contentEquals("Automatic")){
            mScannerView.setFlash(false);
        }


        img_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String manullay_bar_code = Edt_txt_scan_result.getText().toString();
                if(manullay_bar_code.contentEquals("")) {
                    Img_Scan_result.setVisibility(View.GONE);
                    Edt_txt_scan_result.setText("");
                    reset_qr();
                }else{
                    Str_qr_result = manullay_bar_code+"0";

                    new Qr_response().execute();
                }

            }
        });
    }

    public void reset_qr() {
        mScannerView.stopCamera();
        mScannerView = new ZXingScannerView(getActivity());
        // Programmatically initialize the scanner view
        // setContentView(mScannerView);
        preview.removeAllViews();
        mScannerView = new ZXingScannerView(getActivity()) {
            @Override
            protected IViewFinder createViewFinderView(Context context) {
                ViewFinderView finderView = new ViewFinderView(context);
                finderView.setLaserColor(Color.RED);
                finderView.setBorderColor(Color.TRANSPARENT);
                finderView.setKeepScreenOn(true);
               // finderView.setMinimumHeight(90);
                finderView.setMaskColor(Color.TRANSPARENT);
                return finderView;
            }
        };
        preview.addView(mScannerView);
        Edt_txt_scan_result.setText("");

        mScannerView.setResultHandler(this);
        // Register ourselves as a handler for scan results.
        // boolean result = Utility.checkPermission(getActivity());

        // if (result) {
        mScannerView.startCamera();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login_with_qr.
     */
    // TODO: Rename and change types and number of parameters
    public static Scanning_Screen newInstance(String param1, String param2) {
        Scanning_Screen fragment = new Scanning_Screen();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mScannerView.stopCamera();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scanning__screen, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result result) {
        Log.e("handler", result.getText()); // Prints scan results
        Log.e("handler", result.getBarcodeFormat().toString()); // Prints the scan format (qrcode)


        // Toast.makeText(getActivity(), result.getText().toString(), Toast.LENGTH_LONG).show();
        Str_qr_result = result.getText().toString();
        Edt_txt_scan_result.setText(Str_qr_result);
        new Qr_response().execute();
    }

    // TODO: Rename method, update argument and hook method into UI event


    public static Scanning_Screen newInstance(String text) {

        Scanning_Screen f = new Scanning_Screen();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    public interface OnFragmentInteractionListener {
        public void Scanning_Screen(String string);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
            case Utility.MY_PERMISSIONS_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }

                break;
        }
    }

    private class Qr_response extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {


        JSONObject jsonnode, json_User;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // mProgressHUD = ProgressHUD.show(Login_Screen.this, "Connecting", true, true, this);
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            publishProgress("Please wait...");

            CheckinApi apiInstance = new CheckinApi();
            CreateValidation createValidation = new CreateValidation();
            // CreateValidation | createValidation
            createValidation.setBarcode(Str_qr_result);
            createValidation.setCheckinSessionId(Session_id);
            try {
                Validation result = apiInstance.createValidation(createValidation);
                Scan_result = result.getValidationResult().toString();
                System.out.println(result);
            } catch (ApiException e) {
                System.err.println("Exception when calling CheckinApi#createValidation");
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            pDialog.dismiss();
            Str_checksound = (shared.getString("sound", "default"));
            Calendar now    = Calendar.getInstance();
            int hour        = now.get(Calendar.HOUR_OF_DAY);
            int minute      = now.get(Calendar.MINUTE);
            String str_hour = String.valueOf(hour);
            String str_minute = String.valueOf(minute);

            db.add_history_items(new History_list(str_hour+":"+minute+" ["+Str_qr_result+"]"+":,//"+Scan_result));
            if (Scan_result.contentEquals("TICKET_OK")) {
                Img_Scan_result.setVisibility(View.VISIBLE);
                Img_Scan_result.setImageResource(R.drawable.ok);
                if (Str_checksound.contentEquals("default")) {
                    Notification_sound("Scan_succes");
                } else if (Str_checksound.contentEquals("on")) {
                    Notification_sound("Scan_succes");
                }

                new CountDownTimer(2000, 1000) {

                    public void onTick(long millisUntilFinished) {

                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        Img_Scan_result.setVisibility(View.GONE);
                        reset_qr();
                    }

                }.start();

            } else if (Scan_result.contentEquals("TICKET_VOIDED")) {
                Img_Scan_result.setVisibility(View.VISIBLE);
                Img_Scan_result.setImageResource(R.drawable.failed);
                if (Str_checksound.contentEquals("default")) {
                    Notification_sound("Sound_failure");
                } else if (Str_checksound.contentEquals("on")) {
                    Notification_sound("Sound_failure");
                }

                new CountDownTimer(2000, 1000) {

                    public void onTick(long millisUntilFinished) {

                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        Img_Scan_result.setVisibility(View.GONE);
                        reset_qr();
                    }

                }.start();

            } else if (Scan_result.contentEquals("NOT_RECOGNIZED")) {
                Img_Scan_result.setVisibility(View.VISIBLE);
                Img_Scan_result.setImageResource(R.drawable.failed);
                if (Str_checksound.contentEquals("default")) {
                    Notification_sound("warning");
                } else if (Str_checksound.contentEquals("on")) {
                    Notification_sound("warning");
                }

                new CountDownTimer(2000, 1000) {

                    public void onTick(long millisUntilFinished) {

                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        Img_Scan_result.setVisibility(View.GONE);
                        reset_qr();
                    }

                }.start();


            } else if (Scan_result.contentEquals("SCAN_COUNT_EXCEEDED")) {
                Img_Scan_result.setVisibility(View.VISIBLE);
                Img_Scan_result.setImageResource(R.drawable.failed);
                if (Str_checksound.contentEquals("default")) {
                    Notification_sound("warning");
                } else if (Str_checksound.contentEquals("on")) {
                    Notification_sound("warning");
                }

                new CountDownTimer(2000, 1000) {

                    public void onTick(long millisUntilFinished) {

                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        Img_Scan_result.setVisibility(View.GONE);
                        reset_qr();
                    }

                }.start();

            } else if (Scan_result.contentEquals("WRONG_TICKET_TYPE")) {
                Img_Scan_result.setVisibility(View.VISIBLE);
                Img_Scan_result.setImageResource(R.drawable.warning);
                if (Str_checksound.contentEquals("default")) {
                    Notification_sound("system_error");
                } else if (Str_checksound.contentEquals("on")) {
                    Notification_sound("system_error");
                }

                new CountDownTimer(2000, 1000) {

                    public void onTick(long millisUntilFinished) {

                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        Img_Scan_result.setVisibility(View.GONE);
                        reset_qr();
                    }

                }.start();
            }


        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }
    }

//    public class PhoneUnlockedReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
//                //Log.d(TAG, "Phone unlocked");
//                reset_qr();
//            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
//                // Log.d(TAG, "Phone locked");
//            }
//        }
//    }

    private void Notification_sound(String Sound_name) {
        if (Sound_name.contentEquals("Scan_succes")) {

            MediaPlayer mp = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.scan_success);

            mp.setVolume(0.2f, 0.2f);
            mp.start();
        } else if (Sound_name.contentEquals("Sound_failure")) {
            MediaPlayer mp = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.scan_failure);
            mp.setVolume(0.2f, 0.2f);
            mp.start();
        } else if (Sound_name.contentEquals("warning")) {
            MediaPlayer mp = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.scan_warning);
            mp.setVolume(0.2f, 0.2f);
            mp.start();
        } else if (Sound_name.contentEquals("WRONG_TICKET_TYPE")) {
            MediaPlayer mp = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.system_error);
            mp.setVolume(0.2f, 0.2f);
            mp.start();
        }


    }
}
