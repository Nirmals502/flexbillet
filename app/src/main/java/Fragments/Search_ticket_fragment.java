package Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


import adapter.scan_history_adapter;
import data_base_history.DatabaseHandler;
import data_base_history.History_list;
import flexbillet.flexbillet.R;
import flexbillet.flexbillet.Select_Events_Ticket_Types_Screen;
import io.swagger.client.ApiException;
import io.swagger.client.api.CheckinApi;
import io.swagger.client.model.CreateValidation;
import io.swagger.client.model.Purchase;
import io.swagger.client.model.ScannableBarcode;
import io.swagger.client.model.ScannableBarcodesGroup;
import io.swagger.client.model.TicketType;
import io.swagger.client.model.Validation;

import static android.R.id.list;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Search_ticket_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Search_ticket_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Search_ticket_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ProgressDialog pDialog;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String Str_email;
    //  List<Purchase> result_list;
    ExpandableListView Lv;
    EditText Edt_txt_search;
    Button Btn_Search;
    String Search_value, Session_id;
    private OnFragmentInteractionListener mListener;
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();
    scan_history_adapter mAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    String Str_checksound = "default";
    ImageView Img_Scan_result;
    SharedPreferences shared;
    DatabaseHandler db;
    String Scan_result = "no_status";

    public Search_ticket_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Search_ticket_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Search_ticket_fragment newInstance(String param1, String param2) {
        Search_ticket_fragment fragment = new Search_ticket_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Lv = (ExpandableListView) view.findViewById(R.id.list_vw);
        Edt_txt_search = (EditText) view.findViewById(R.id.editText4);
        Btn_Search = (Button) view.findViewById(R.id.button2);
        Img_Scan_result = (ImageView) view.findViewById(R.id.scan_image);
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        //mAdapter = new scan_history_adapter(getActivity());
        shared = getActivity().getSharedPreferences("Flexbillet", getActivity().MODE_PRIVATE);
        db = new DatabaseHandler(getActivity());
        Session_id = (shared.getString("Session_id", "empty"));
        Btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
                Btn_Search.startAnimation(buttonClick);
                if (Edt_txt_search.getText().toString().contentEquals("")) {
                    Animation anm = Shake_Animation();
                    Edt_txt_search.startAnimation(anm);
                } else {
                    Search_value = Edt_txt_search.getText().toString();
                    Edt_txt_search.setText("");
                    listDataHeader.clear();
                    listDataChild.clear();
                    new Search().execute();
                }
            }
        });
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
        return inflater.inflate(R.layout.fragment_search_ticket_fragment, container, false);
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public static Search_ticket_fragment newInstance(String text) {

        Search_ticket_fragment f = new Search_ticket_fragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    public Animation Shake_Animation() {
        Animation shake = new TranslateAnimation(0, 5, 0, 0);
        shake.setInterpolator(new CycleInterpolator(5));
        shake.setDuration(300);


        return shake;
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

    private class Search extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {


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

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            publishProgress("Please wait...");

            CheckinApi apiInstance = new CheckinApi();
            String Str_all_child_data = "";

            // String checkinSessionId = "438067"; // String | checkinSessionId
            String searchString = "20211777"; // String | searchString
            try {
                List<Purchase> result_list = apiInstance.searchPurchases(Session_id, Search_value);
                for (int i = 0; i < result_list.size(); i++) {

                    Purchase purchase = new Purchase();
                    purchase = result_list.get(i);
                    String ordernumber_nd_name = purchase.getReference() + " (" + purchase.getRegistrantName() + ")";
                    // purchase.
                    // listDataHeader.add(ordernumber_nd_name);

                    List<ScannableBarcodesGroup> scannableBarcodesGroups = purchase.getScannableBarcodesGroups();
                    for (int isncode = 0; isncode < scannableBarcodesGroups.size(); isncode++) {
                        List<String> child_list = new ArrayList<String>();
                        ScannableBarcodesGroup SCBG = scannableBarcodesGroups.get(isncode);

                        Boolean Ticket_type = SCBG.getValidTicketType();
                        String Str_boloean_tostring = String.valueOf(Ticket_type);
                        TicketType tickettype = SCBG.getTicketType();
                        String Str_Ticket_types_id = tickettype.getId();
                        String Str_Ticket_name = tickettype.getName();
                        listDataHeader.add(ordernumber_nd_name + System.lineSeparator() + Str_Ticket_name);
                        List<ScannableBarcode> scannableBarcode = SCBG.getBarcodes();


                        for (int scbarcode = 0; scbarcode < scannableBarcode.size(); scbarcode++) {


                            ScannableBarcode sbc = scannableBarcode.get(scbarcode);
                            Boolean Str_scanable_stats = sbc.getScannable();
                            // sbc.
                            String boolean_To_string = String.valueOf(Str_scanable_stats);
                            String Str_barcode = sbc.getBarcode();
                            Str_all_child_data = Str_boloean_tostring + ":,//" + Str_Ticket_types_id + ":,//" + Str_Ticket_name + ":,//" + boolean_To_string + ":,//" + Str_barcode;
                            child_list.add(Str_all_child_data);
                        }
                        try {
                            listDataChild.put(listDataHeader.get(isncode), child_list);
                        } catch (java.lang.IndexOutOfBoundsException e) {

                        }

                    }


                }


                System.out.println(result_list);
            } catch (ApiException e) {
                System.err.println("Exception when calling CheckinApi#searchPurchases");
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

            scan_history_adapterr adapter = new scan_history_adapterr(getActivity(), listDataHeader, listDataChild);

            // setting list adapter
            Lv.setAdapter(adapter);
            try {
                Lv.expandGroup(0);
//                Lv.expandGroup(1);
//                Lv.expandGroup(2);
//                Lv.expandGroup(3);
            } catch (java.lang.IndexOutOfBoundsException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public class scan_history_adapterr extends BaseExpandableListAdapter {

        private Context _context;
        private List<String> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<String, List<String>> _listDataChild;
        private ProgressDialog pDialog;
        String Str_id;
        String Barcode;


        public scan_history_adapterr(Context context, List<String> listDataHeader,
                                     HashMap<String, List<String>> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
            Collections.sort(_listDataHeader);
            // Collections.sort(_listDataChild);
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            try {
                return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                        .get(childPosititon);
            } catch (java.lang.IndexOutOfBoundsException e) {
                e.printStackTrace();
                return 0;
            }

        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            try {


                final String childText = (String) getChild(groupPosition, childPosition);
                String[] separated = childText.split(":,//");
                final String Barcode_value = separated[4];
                Barcode = Barcode_value;
                final String Scananble = separated[3];
                SharedPreferences shared = _context.getSharedPreferences("Flexbillet", _context.MODE_PRIVATE);
                Str_id = (shared.getString("Session_id", "empty"));


                if (convertView == null) {
                    LayoutInflater infalInflater = (LayoutInflater) this._context
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = infalInflater.inflate(R.layout.adater_layout_for_scan_history, null);
                }

                TextView txtListChild = (TextView) convertView
                        .findViewById(R.id.Txt_Header);
                ImageView Img_Status = (ImageView) convertView
                        .findViewById(R.id.img_Status);
                ImageView Img_scan = (ImageView) convertView
                        .findViewById(R.id.imageView12);
                if (Scananble.contentEquals("true")) {
                    Img_Status.setImageResource(R.drawable.ok_ico);
                    Img_scan.setVisibility(View.VISIBLE);
                } else if (Scananble.contentEquals("false")) {
                    Img_Status.setImageResource(R.drawable.warning_ico);
                    Img_scan.setVisibility(View.GONE);
                }

                Img_scan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String childText = (String) getChild(groupPosition, childPosition);
                        String[] separated = childText.split(":,//");
                        final String Barcode_value = separated[4];
                        Barcode = Barcode_value;

                        new Create_validation().execute();
                        //Toast.makeText(_context,Barcode,Toast.LENGTH_LONG).show();


                    }
                });

                txtListChild.setText("[" + Barcode_value + "]");
            } catch (java.lang.ClassCastException e) {
                e.printStackTrace();
            }
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {

            try {
                return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                        .size();
            } catch (java.lang.NullPointerException e) {
                e.printStackTrace();
                return 0;
            }

        }

        @Override
        public Object getGroup(int groupPosition) {
            try {
                return this._listDataHeader.get(groupPosition);
            } catch (java.lang.IndexOutOfBoundsException e) {
                return null;
            }

        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_header, null);
            }

            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.text);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle);

            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle);
//        if (isExpanded) {
//            img_checkbox.setImageResource(R.drawable.checkbox_check);
//        } else {
//            img_checkbox.setImageResource(R.drawable.checkbox_uncheck);
//        }


            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        private class Create_validation extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                // mProgressHUD = ProgressHUD.show(Login_Screen.this, "Connecting", true, true, this);
                // Showing progress dialog
                pDialog = new ProgressDialog(_context);
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
                createValidation.setCheckinSessionId(Str_id);
                createValidation.setBarcode(Barcode + "0");
                // CreateValidation | createValidation
                try {
                    Validation result = apiInstance.createValidation(createValidation);
                    Scan_result = result.getValidationResult().toString();
                    System.out.println(result);
                } catch (ApiException e) {
                    System.err.println("Exception when calling CheckinApi#createValidation");
                    e.printStackTrace();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (ExecutionException e1) {
                    e1.printStackTrace();
                } catch (TimeoutException e1) {
                    e1.printStackTrace();
                }


                // System.out.println(result_list);


                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                // Dismiss the progress dialog
                pDialog.dismiss();
                Str_checksound = (shared.getString("sound", "default"));
                Calendar now = Calendar.getInstance();
                int hour = now.get(Calendar.HOUR_OF_DAY);
                int minute = now.get(Calendar.MINUTE);
                String str_hour = String.valueOf(hour);
                String str_minute = String.valueOf(minute);

                db.add_history_items(new History_list(str_hour + ":" + minute + " [" + Barcode + "]" + ":,//" + Scan_result));
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

                        }

                    }.start();
                }
                //scan_history_adapterr adapter = new scan_history_adapterr(getActivity(), listDataHeader, listDataChild);
//                listDataHeader.clear();
//                listDataChild.clear();
                listDataHeader.clear();
                listDataChild.clear();
                new Search().execute();
            }

            @Override
            public void onCancel(DialogInterface dialog) {

            }
        }
    }

    private void Notification_sound(String Sound_name) {
        if (Sound_name.contentEquals("Scan_succes")) {
            MediaPlayer mp = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.scan_success);
            mp.start();
        } else if (Sound_name.contentEquals("Sound_failure")) {
            MediaPlayer mp = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.scan_failure);
            mp.start();
        } else if (Sound_name.contentEquals("warning")) {
            MediaPlayer mp = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.scan_warning);
            mp.start();
        } else if (Sound_name.contentEquals("WRONG_TICKET_TYPE")) {
            MediaPlayer mp = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.system_error);
            mp.start();
        }


    }
}
