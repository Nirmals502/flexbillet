package adapter;

/**
 * Created by Mr singh on 2/21/2017.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import flexbillet.flexbillet.R;
import flexbillet.flexbillet.Ticket_Scanning_screen;
import io.swagger.client.ApiException;
import io.swagger.client.api.CheckinApi;
import io.swagger.client.model.CreateValidation;
import io.swagger.client.model.Purchase;
import io.swagger.client.model.ScannableBarcode;
import io.swagger.client.model.ScannableBarcodesGroup;
import io.swagger.client.model.TicketType;
import io.swagger.client.model.Validation;

public class scan_history_adapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    private ProgressDialog pDialog;
    String Str_id;
    String Barcode;


    public scan_history_adapter(Context context, List<String> listDataHeader,
                                HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

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

//                SharedPreferences shared;
//                shared = _context.getSharedPreferences("Flexbillet", _context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = shared.edit();
//
//                editor.putString("barcode_value", Barcode_value);
//
//                editor.commit();
//                Intent i1 = new Intent(_context, Ticket_Scanning_screen.class);
//                i1.putExtra("qr_screen", "qr_screen");
//
//                _context.startActivity(i1);
                new Create_validation().execute();


            }
        });

        txtListChild.setText("[" + Barcode_value + "]");
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
        return this._listDataHeader.get(groupPosition);
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
            createValidation.setBarcode(Barcode+"0");
            // CreateValidation | createValidation
            try {
                Validation result = apiInstance.createValidation(createValidation);
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
            //Toast.makeText(getActivity(), Str_email, Toast.LENGTH_LONG).show();
//            scan_history_adapter adapter = new scan_history_adapter(getActivity(),
//                    result_list
//            );
            // Lv.setAdapter(mAdapter);


            // setting list adapter


        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }
    }
}