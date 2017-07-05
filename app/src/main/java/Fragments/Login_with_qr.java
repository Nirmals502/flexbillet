package Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Service_handler.Constant;
//import Service_handler.ServiceHandler;
import camera_permission_uttility.Utility;
import flexbillet.flexbillet.Login_screen;
import flexbillet.flexbillet.MainActivity;
import flexbillet.flexbillet.R;
import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.core.ViewFinderView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class Login_with_qr extends Fragment implements ZXingScannerView.ResultHandler {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ImageView img_scan;
    private ProgressDialog pDialog;
    private ZXingScannerView mScannerView;

    final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String Str_id = "no_response";
    String Username, Password;
    private OnFragmentInteractionListener mListener;
    RelativeLayout preview;
    String organizerkey, passphrase;
    Set<String> set_username_password = new HashSet<String>();


    public Login_with_qr() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        img_scan = (ImageView) view.findViewById(R.id.imageView6);
        preview = (RelativeLayout) view.findViewById(R.id.camera_preview);
        SharedPreferences shared = getActivity().getSharedPreferences("Flexbillet", getActivity().MODE_PRIVATE);
        organizerkey = (shared.getString("organizerkey", "empty"));
        passphrase = (shared.getString("passphrase", "empty"));
        set_username_password = (shared.getStringSet("list", null));
        // if (organizerkey.contentEquals("empty")) {


        //preview.addView(mCameraPreview);
        mScannerView = new ZXingScannerView(getActivity());

        // Programmatically initialize the scanner view
        // setContentView(mScannerView);
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


        // setContentView(mScannerView);

        preview.addView(mScannerView);

        mScannerView.setResultHandler(this);
        mScannerView.setAutoFocus(true);
        // mScannerView.setPadding(60,20,0,50);
        // Register ourselves as a handler for scan results.
        // boolean result = Utility.checkPermission(getActivity());

        // if (result) {
        mScannerView.startCamera();
        //  mScannerView.
        //  }

        //galleryIntent();


        //  }


        img_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                img_scan.startAnimation(buttonClick);
//                Intent i1 = new Intent(getActivity(), MainActivity.class);
//                getActivity().startActivity(i1);
            }
        });
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
    public static Login_with_qr newInstance(String param1, String param2) {
        Login_with_qr fragment = new Login_with_qr();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_with_qr, container, false);
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


        if (result.getText().contains("organizerkey")) {
            HashMap<String, String> map = new HashMap<String, String>();
            String[] resDetails = result.getText().split("/");
            for (String pair : resDetails) {
                String[] entry = pair.split(":");
                map.put(entry[0].trim(), entry[1].trim());
            }
            Username = map.get("organizerkey");
            Password = map.get("passphrase");
            try {
                set_username_password.add(Username + ":,//" + Password);
            } catch (java.lang.NullPointerException e) {
                e.printStackTrace();
                set_username_password = new HashSet<String>();
                set_username_password.add(Username + ":,//" + Password);
            }

            //set_username_password.
            //  String textPassToB = A_input.getText().toString();

//            String TabOfFragmentB = ((Login_screen)getActivity()).Onloginwithcrential();
//
//            MyFragmentB fragmentB = (MyFragmentB)getActivity()
//                    .getSupportFragmentManager()
//                    .findFragmentByTag(TabOfFragmentB);
//
//            fragmentB.b_updateText(textPassToB);
//            mScannerView.stopCamera();
//            Intent i1 = new Intent(getActivity(),Login_screen.class);
//            i1.putExtra("organizerkey",Username);
//            i1.putExtra("passphrase",Password);
//            getActivity().startActivity(i1);
            // new lOGIN().execute();
            SharedPreferences sharedpreferences = getActivity().getSharedPreferences("Flexbillet", getActivity().MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString("organizerkey", Username);
            editor.putString("passphrase", Password);
            editor.putStringSet("list", set_username_password);
            editor.commit();
            Intent i1 = new Intent(getActivity(), Login_screen.class);

            getActivity().startActivity(i1);

        } else {
            Toast.makeText(getActivity(), "Invalid Ticket", Toast.LENGTH_LONG).show();
            mScannerView.stopCamera();
            mScannerView = new ZXingScannerView(getActivity());
            // Programmatically initialize the scanner view
            // setContentView(mScannerView);
            preview.removeAllViews();
            preview.addView(mScannerView);

            mScannerView.setResultHandler(this);
            // Register ourselves as a handler for scan results.
            // boolean result = Utility.checkPermission(getActivity());

            // if (result) {
            mScannerView.startCamera();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event


    public interface OnFragmentInteractionListener {
        public void Onloginwithcrential(String string);
    }

    public static Login_with_qr newInstance(String text) {

        Login_with_qr f = new Login_with_qr();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
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

//                    if (userChoosenTask.equals("Choose from Library"))
//                        galleryIntent();
//                } else {
//                    //code for deny
//                }
                break;
        }
    }

    private class lOGIN extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {


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

            Calendar c = Calendar.getInstance();
            System.out.println("Current time => " + c.getTime());


//            ServiceHandler sh = new ServiceHandler();
//            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
//            //nameValuePairs.add(new BasicNameValuePair("email", email_fb));
//            nameValuePairs.add(new BasicNameValuePair("organizerKey", "flexcheckin"));
//            nameValuePairs.add(new BasicNameValuePair("passphrase", "281416573000497bc712044b3a6a8f318742a898a06a4eb"));
//            JSONObject object = new JSONObject();
//                try {
//
//                    object.put("organizerKey", Username);
//                    object.put("passphrase", Password);
//
//
//                } catch (Exception ex) {
//
//                }
//
//            // password - character
//
//
//            String jsonStr = sh.makeServiceCall_withHeader(Constant.LOGIN,
//                    ServiceHandler.POST, nameValuePairs,object);
//
//            Log.d("Response: ", "> " + jsonStr);
//
//            if (jsonStr != null) {
//                JSONObject jsonObj = null;
//                try {
//                    jsonObj = new JSONObject(jsonStr);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    Str_id = jsonObj.getString("id");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                // Getting JSON Array node
//                // JSONArray array1 = null;
//
//
//            } else {
//                Log.e("ServiceHandler", "Couldn't get any data from the url");
//            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            pDialog.dismiss();
            if (Str_id.contentEquals("no_response")) {
                Toast.makeText(getActivity(), "Unable to authenticate; please try again", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getActivity(), Password, Toast.LENGTH_LONG).show();
            }


        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }
    }

}
