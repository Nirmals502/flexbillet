package Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import adapter.history_base_adapter;
import data_base_history.History_list;
import flexbillet.flexbillet.R;
import io.swagger.client.ApiException;
import io.swagger.client.api.CheckinApi;
import io.swagger.client.model.Status;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Status_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Status_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Status_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ProgressDialog pDialog;
    TextView Txt_expected_guest, Txt_Vw_Scaned_tickets, Status;
    String Str_expected_guest, Str_Scanned_tickect, String_status, Session_id;
    ImageView Img_internet_status;
    private OnFragmentInteractionListener mListener;

    public Status_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Status_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Status_Fragment newInstance(String param1, String param2) {
        Status_Fragment fragment = new Status_Fragment();
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Txt_expected_guest = (TextView) view.findViewById(R.id.textView19);
        Txt_Vw_Scaned_tickets = (TextView) view.findViewById(R.id.textView199);
        Status = (TextView) view.findViewById(R.id.textView1999);
        Img_internet_status = (ImageView) view.findViewById(R.id.imageView1000);
        SharedPreferences shared = getActivity().getSharedPreferences("Flexbillet", getActivity().MODE_PRIVATE);
        Session_id = (shared.getString("Session_id", "empty"));

        new GET_STATUS().execute();
        //  Txt_expected_guest =(TextView)view.findViewById(R.id.textView19);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status_, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    //    new GET_STATUS().execute();
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public static Status_Fragment newInstance(String text) {

        Status_Fragment f = new Status_Fragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);
        System.out.println("sattus screen");

       // Dialog();
      //  new GET_STATUS().execute();
        return f;

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
        // TODO: Update argument type and name

        void onFragmentInteraction(Uri uri);

    }
    private void Dialog() {

    }
    private class GET_STATUS extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {


        JSONObject jsonnode, json_User;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // mProgressHUD = ProgressHUD.show(Login_Screen.this, "Connecting", true, true, this);
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            //  pDialog.show();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            // publishProgress("Please wait...");
            CheckinApi apiInstance = new CheckinApi();
            /// String checkinSessionId = "checkinSessionId_example"; // String | checkinSessionId
            try {
                io.swagger.client.model.Status result = apiInstance.getStatus(Session_id);
                Str_expected_guest = result.getScannedTicketCount().toString();
                Str_Scanned_tickect = result.getTicketCount().toString();
                String_status = result.getBackendAlive().toString();
                System.out.println(result);
            } catch (ApiException e) {
                System.err.println("Exception when calling CheckinApi#getStatus");
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
            //  pDialog.dismiss();
            Txt_expected_guest.setText(Str_Scanned_tickect);
            Txt_Vw_Scaned_tickets.setText(Str_expected_guest);
            if (String_status.contentEquals("true")) {
                Status.setText("Online");
                Img_internet_status.setImageResource(R.drawable.internet_connectivity_online_ico);
            } else if (String_status.contentEquals("false")) {
                Status.setText("Offline");
                Img_internet_status.setImageResource(R.drawable.internet_connectivity_offline_ico);
            }


        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (isVisibleToUser) {
                new GET_STATUS().execute();
                Log.d("MyFragment", "Not visible anymore.  Stopping audio.");
                // TODO stop audio playback
            }
        }
    }
}
