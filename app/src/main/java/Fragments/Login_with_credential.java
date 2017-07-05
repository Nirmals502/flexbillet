package Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import Service_handler.Constant;
//import Service_handler.ServiceHandler;


import flexbillet.flexbillet.R;
//import flexbillet.flexbillet.Select_Events_Ticket_Types_Screen;
import flexbillet.flexbillet.Select_Events_Ticket_Types_Screen;
import flexbillet.flexbillet.Ticket_Scanning_screen;
import flexbillet.flexbillet.Welcome_screen;
import io.swagger.client.ApiException;
import io.swagger.client.api.CheckinApi;
import io.swagger.client.model.CheckinSession;
import io.swagger.client.model.OrganizerAuth;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Login_with_credential.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Login_with_credential#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login_with_credential extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button Btn_login;
    EditText  Edttxt_password;
    AutoCompleteTextView Edttxt_username;

    private ProgressDialog pDialog;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String Username, Password;
    String Str_id = "no_response";
    private OnFragmentInteractionListener mListener;
    String organizerkey, passphrase;
    SharedPreferences shared;
   // String[] username_password;
    ArrayList<String> username_password = new ArrayList<String>();
    ArrayList<String> array_password = new ArrayList<String>();
    Set<String> set_username = new HashSet<String>();
    // CheckinApi apiInstance = new CheckinApi();
    // CheckinApi checkinApi =
    //CheckinApi checkinApi = new CheckinApiBuilder().protocol("https").host("checkin.flexbillet.dk").build();

    CheckinApi checkinApi = new CheckinApi();

    //    CheckinApiBuilder api_builder;
    public Login_with_credential() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login_with_credential.
     */
    // TODO: Rename and change types and number of parameters
    public static Login_with_credential newInstance(String param1, String param2) {
        Login_with_credential fragment = new Login_with_credential();
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
        return inflater.inflate(R.layout.fragment_login_with_credential, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Edttxt_username = (AutoCompleteTextView) view.findViewById(R.id.editText);
//        String[] ProgLanguages = { "Java", "C", "C++", ".Net", "PHP", "Perl",
//                "Objective-c", "Small-Talk", "C#", "Ruby", "ASP", "ASP .NET" };
       // String[] ProgLanguages = new String;
        Edttxt_password = (EditText) view.findViewById(R.id.editText2);
        Btn_login = (Button) view.findViewById(R.id.button);
        shared = getActivity().getSharedPreferences("Flexbillet", getActivity().MODE_PRIVATE);
        organizerkey = (shared.getString("organizerkey", "empty"));
        passphrase = (shared.getString("passphrase", "empty"));
        set_username = (shared.getStringSet("list",null));
        if(set_username!=null) {


            for (String temp : set_username) {
                String[] separated = temp.split(":,//");
                final String username = separated[0]; // this will contain "Fruit"
                final String password = separated[1];
                username_password.add(username);
                array_password.add(password);
                System.out.println(temp);
            }
        }
        if (!organizerkey.contentEquals("empty")) {
            Edttxt_username.setText(organizerkey);
            Edttxt_password.setText(passphrase);
//            username_password.add(organizerkey);
//            array_password.add(passphrase);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.select_dialog_item, username_password);
        //Used to specify minimum number of
        //characters the user has to type in order to display the drop down hint.
        Edttxt_username.setThreshold(1);
        //Setting adapter
        Edttxt_username.setAdapter(arrayAdapter);
        Btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
                Btn_login.startAnimation(buttonClick);
                if (Edttxt_username.getText().toString().contentEquals("")) {
                    Animation anm = Shake_Animation();
                    Edttxt_username.startAnimation(anm);
                } else if (Edttxt_password.getText().toString().contentEquals("")) {
                    Animation anm = Shake_Animation();
                    Edttxt_password.startAnimation(anm);
                } else {
                    Username = Edttxt_username.getText().toString();
                    Password = Edttxt_password.getText().toString();
                    new lOGIN().execute();
                }

            }
        });
        Edttxt_username.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String password = array_password.get(position);
                Edttxt_password.setText(password);
            }
        });

    }

    public Animation Shake_Animation() {
        Animation shake = new TranslateAnimation(0, 5, 0, 0);
        shake.setInterpolator(new CycleInterpolator(5));
        shake.setDuration(300);


        return shake;
    }

    public static Login_with_credential newInstance(String text) {

        Login_with_credential f = new Login_with_credential();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void Onloginwithqr(String string);
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
            //   checkinApi = new CheckinApiBuilder().protocol("https").host("checkin.flexbillet.dk").build();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            publishProgress("Please wait...");


            OrganizerAuth organizerAuth = new OrganizerAuth();
            organizerAuth.setOrganizerKey(Username);
            organizerAuth.setPassphrase(Password);

            //CheckinSession checkinSession = checkinApi.createCheckinSession(organizerAuth);
            try {
                CheckinSession checkinSession = checkinApi.createCheckinSession(organizerAuth);
                Str_id = checkinSession.getId();
                System.out.println("result is..." + Str_id);
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ApiException e) {
                e.printStackTrace();
            }


            System.out.println("String result......" + Str_id);


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            pDialog.dismiss();
            if (Str_id.contentEquals("no_response")) {
                Toast.makeText(getActivity(), "Unable to authenticate; please try again", Toast.LENGTH_LONG).show();
//                Intent i1 = new Intent(getActivity(), Ticket_Scanning_screen.class);
//                //i1.putExtra("Session_id", Str_id);
//
//                getActivity().startActivity(i1);
//                getActivity().finish();
//                getActivity().overridePendingTransition(R.anim.slide_in_left,
//                        R.anim.slide_out_left);
            } else {
                // Toast.makeText(getActivity(), Str_id, Toast.LENGTH_LONG).show();
                //    SharedPreferences sharedpreferences = getActivity().getSharedPreferences("Flexbillet", getActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();

                editor.putString("Session_id", Str_id);

                editor.commit();
                Intent i1 = new Intent(getActivity(), Select_Events_Ticket_Types_Screen.class);
                i1.putExtra("Session_id", Str_id);

                getActivity().startActivity(i1);
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_left);
            }


        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }
    }

    public void b_updateText(String t) {
        //b_received.setText(t);
    }

}
