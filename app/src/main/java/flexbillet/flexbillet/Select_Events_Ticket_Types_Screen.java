package flexbillet.flexbillet;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import Service_handler.Constant;


import io.swagger.client.ApiException;
import io.swagger.client.api.CheckinApi;
import io.swagger.client.model.CheckinSession;
import io.swagger.client.model.Event;
import io.swagger.client.model.IdAndHref;
import io.swagger.client.model.Status;
import io.swagger.client.model.TicketType;


import static android.R.attr.type;

public class Select_Events_Ticket_Types_Screen extends Activity {
    expandable_list_adapterr listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private ProgressDialog pDialog;
    String Str_id;
    ImageView img_checkbox, Img_menu;
    ArrayList<String> list_for_checkbox = new ArrayList<String>();
    ArrayList<String> list_for_checkbox_remove_items = new ArrayList<String>();
    ArrayList<String> child_list_id = new ArrayList<String>();
    Button Btn_Start_scanning;
    CheckinApi checkinApi = new CheckinApi();
    CheckinSession checkinSession = new CheckinSession();
    String Str_response = "200";
    TicketType Tickettype = new TicketType();
    Event event = new Event();
    int checkboxid = 1;
    String Device_name;
    SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_select__events__ticket__types__screen);
        Bundle extras = getIntent().getExtras();
        String userName;
        Device_name = getDeviceName();
       // try {
        if (extras != null) {
            Str_id = extras.getString("Session_id");
            // and get whatever type user account id is
        } else {
            SharedPreferences shared = getSharedPreferences("Flexbillet", MODE_PRIVATE);
            Str_id = (shared.getString("Session_id", "empty"));

        }
//        } catch (java.lang.NullPointerException e) {
//            e.printStackTrace();
//            child_list_id = (ArrayList<String>) getIntent().getSerializableExtra("List");
//        }
        expListView = (ExpandableListView) findViewById(R.id.expandable_lv_);
        Btn_Start_scanning = (Button) findViewById(R.id.button5);
        Img_menu = (ImageView) findViewById(R.id.imageView3);
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        shared = getSharedPreferences("Flexbillet", MODE_PRIVATE);
        Img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog();
            }
        });
//        expListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                ImageView img_check = (ImageView) parent.findViewById(R.id.imageView8);
//               // img_checkbox.setImageResource(R.drawable.checkbox_check);
//                Toast.makeText(Select_Events_Ticket_Types_Screen.this,"Clicked",Toast.LENGTH_LONG).show();
//            }
//        });
        Btn_Start_scanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new Update().execute();
//                Intent i1 = new Intent(Select_Events_Ticket_Types_Screen.this, Ticket_Scanning_screen.class);
//                startActivity(i1);
                if (child_list_id.size() != 0) {
                    new Update().execute();
                } else {
                    Toast.makeText(Select_Events_Ticket_Types_Screen.this, R.string.Select_Tickect_error, Toast.LENGTH_LONG).show();
                }

            }
        });
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Expanded",
//                        Toast.LENGTH_SHORT).show();
            }
        });
//        expListView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                img_checkbox.setImageResource(R.drawable.checkbox_check);
//            }
//        });
        new Event_ticket().execute();
    }

    private class Event_ticket extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // mProgressHUD = ProgressHUD.show(Login_Screen.this, "Connecting", true, true, this);
            // Showing progress dialog
            pDialog = new ProgressDialog(Select_Events_Ticket_Types_Screen.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            publishProgress("Please wait...");


            try {
                List<Event> result = checkinApi.getEvents(Str_id);
                for (int count = 0; count < result.size(); count++) {
                    event = result.get(count);
                    String name = event.getName();
                    Long event_Start_date = event.getEventStart();
                    String str_event_start = String.valueOf(event_Start_date);
                    //event.
                    listDataHeader.add(name + ":,//" + str_event_start);
                    //String session_id = event.
                    List<TicketType> resulrt = event.getTicketTypes();


                    List<String> child_list = new ArrayList<String>();
                    for (int count_ticket = 0; count_ticket < resulrt.size(); count_ticket++) {
                        Tickettype = resulrt.get(count_ticket);
                        String str_Ticket_name = Tickettype.getName();

                        //  String str_Ticket_name= Tickettype.getName();
                        String session_id = Tickettype.getId();
                        child_list.add(str_Ticket_name + ":,//" + session_id);
                    }
                    listDataChild.put(listDataHeader.get(count), child_list);
//                                child_list.add(name + ":,//" + session_id);
                }


                //Event ec = new Event();

                System.out.println(result);
            } catch (ApiException e) {
                System.err.println("Exception when calling CheckinApi#getEvents");
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
            listAdapter = new expandable_list_adapterr(Select_Events_Ticket_Types_Screen.this, listDataHeader, listDataChild);

            // setting list adapter
            expListView.setAdapter(listAdapter);


        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }
    }

    public class expandable_list_adapterr extends BaseExpandableListAdapter {

        private Context _context;
        private List<String> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<String, List<String>> _listDataChild;
        String CLICK = "FALSE";
        ArrayList<ArrayList<Boolean>> selectedChildCheckBoxStates = new ArrayList<>();
        ArrayList<Boolean> selectedParentCheckBoxesState = new ArrayList<>();

        public expandable_list_adapterr(Context context, List<String> listDataHeader,
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
        public View getChildView(final int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final String childText = (String) getChild(groupPosition, childPosition);
            String[] separated = childText.split(":,//");
            final String Name = separated[0]; // this will contain "Fruit"
            final String id = separated[1]; // this will contain " they taste good"

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_item, null);
            }

            final CheckBox txtListChild = (CheckBox) convertView
                    .findViewById(R.id.checkBox);
            try {
                if (child_list_id.contains(id)) {
                    txtListChild.setChecked(true);
                } else {

                    txtListChild.setChecked(false);
                }
            } catch (java.lang.IndexOutOfBoundsException e) {
                e.printStackTrace();
            }

            txtListChild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        if (child_list_id.contains(id)) {
                            child_list_id.remove(id);
                            txtListChild.setChecked(false);
                            try {
                                String Str_position = String.valueOf(groupPosition);
                                list_for_checkbox.remove(Str_position);
                            } catch (java.lang.IndexOutOfBoundsException e) {
                                e.printStackTrace();
                            }
                            notifyDataSetChanged();
                        } else {
                            child_list_id.add(id);

                            txtListChild.setChecked(true);
                            try {
                                String Str_position = String.valueOf(groupPosition);
                                list_for_checkbox_remove_items.remove(Str_position);
                            } catch (java.lang.IndexOutOfBoundsException e) {
                                e.printStackTrace();
                            }
                            notifyDataSetChanged();
                        }
                    } catch (java.lang.IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            });


            // list_for_checkbox.add(groupPosition,"added");
//            if(CLICK.contentEquals("TRUE")) {
            String Str_position = String.valueOf(groupPosition);
            //  if (list_for_checkbox.contains(Str_position)) {
            if (list_for_checkbox.contains(Str_position)) {

                if (!child_list_id.contains(id)) {
                    child_list_id.add(id);
                    txtListChild.setChecked(true);
                }
//                    txtListChild.setChecked(true);
//                    child_list_id.add(id);

                //child_list_id.add(id);
                // child_list_id.add(id);
            } else if (list_for_checkbox_remove_items.contains(Str_position)) {
                //txtListChild.setChecked(false);

                try {
                    if (child_list_id.contains(id)) {
                        child_list_id.remove(id);
                        txtListChild.setChecked(false);
                    }

                } catch (java.lang.IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }

            // }
//            }
//            }
            System.out.println("Array size  " + child_list_id.size());
            txtListChild.setText(Name);
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .size();
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
        public View getGroupView(final int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            String[] separated = headerTitle.split(":,//");
            final String Tittle = separated[0]; // this will contain "Fruit"
            final String timestamp = separated[1];
            Long long_timestamp = null;
            long_timestamp = long_timestamp.valueOf(timestamp);
            Timestamp stamp = new Timestamp(long_timestamp);
            Date date = new Date(stamp.getTime());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String date_str = format.format(Date.parse(date.toString()));
            // date.
            //Log.d("Current date Time is   "  +date.toString());
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_group, null);
            }

            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.textView11);
            TextView txt_vw_time_date = (TextView) convertView
                    .findViewById(R.id.textView12);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle);
            txt_vw_time_date.setText(date_str);
            final ImageView img_checkbox = (ImageView) convertView
                    .findViewById(R.id.imageView8);
            final ImageView img_indicator= (ImageView) convertView
                    .findViewById(R.id.indicator);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(Tittle);

            String Str_position = String.valueOf(groupPosition);
            if (list_for_checkbox.contains(Str_position)) {
                img_checkbox.setImageResource(R.drawable.checkbox_check);
               // img_indicator.setImageResource(R.drawable.expanded);

                //child_list_id.add(id);
                // child_list_id.add(id);

            } else {
                //txtListChild.setChecked(false);
                img_checkbox.setImageResource(R.drawable.checkbox_uncheck);
               // img_indicator.setImageResource(R.drawable.unexpanded);
                try {

                } catch (java.lang.IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
            img_checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String Str_position = String.valueOf(groupPosition);


                    if (list_for_checkbox.contains(Str_position)) {
                        CLICK = "FALSE";
                        //txtListChild.setChecked(true);
                        list_for_checkbox.remove(Str_position);
                        list_for_checkbox_remove_items.add(Str_position);
                        img_checkbox.setImageResource(R.drawable.checkbox_uncheck);
                        notifyDataSetChanged();
                    } else {
                        CLICK = "TRUE";
                        list_for_checkbox.add(Str_position);
                        list_for_checkbox_remove_items.remove(Str_position);
                        // child_list_id.clear();
                        img_checkbox.setImageResource(R.drawable.checkbox_check);
                        expListView.expandGroup(groupPosition);
                        notifyDataSetChanged();
                    }

                }
            });

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
    }

    private class Update extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {


        //JSONObject jsonnode, json_User;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // mProgressHUD = ProgressHUD.show(Login_Screen.this, "Connecting", true, true, this);
            // Showing progress dialog
            pDialog = new ProgressDialog(Select_Events_Ticket_Types_Screen.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            publishProgress("Please wait...");


            checkinSession.setId(Str_id);
            checkinSession.setTeamAccessValidated(false);
            checkinSession.setScannerStation(Device_name);
            checkinSession.setMemberShipValidated(false);
            List<IdAndHref> ticketIds = new ArrayList<>();
            for (int Ticket_id = 0; Ticket_id < child_list_id.size(); Ticket_id++) {
                IdAndHref ticketId = new IdAndHref();
                ticketId.setId(child_list_id.get(Ticket_id));
                ticketIds.add(ticketId);
            }
            // for (TicketType type : // the selected tickettypes to scan) {

            checkinSession.setTicketTypes(ticketIds);

            try {

                checkinApi.updateCheckinSession(checkinSession);
                //int re = checkinApi.

                //checkinApi.
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ApiException e) {
                int error_code = e.getCode();
                Str_response = String.valueOf(error_code);
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            pDialog.dismiss();
            if (Str_response.contentEquals("200")) {
               // Toast.makeText(Select_Events_Ticket_Types_Screen.this, "Updated succesfully", Toast.LENGTH_LONG).show();
                Intent i1 = new Intent(Select_Events_Ticket_Types_Screen.this, Ticket_Scanning_screen.class);
                //i1.putStringArrayListExtra("List",child_list_id);
               // startActivity(intent);
                startActivity(i1);
                finish();
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_left);

            } else {
                Toast.makeText(Select_Events_Ticket_Types_Screen.this, Str_response, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }
    }

    public void b_updateText(String t) {
        //b_received.setText(t);
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        String phrase = "";
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c);
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase += c;
        }
        return phrase;
    }

    private void Dialog_Setting() {
        // reviewicon=(ImageView) view.findViewById(R.id.reviewicon);
        final Dialog dialog = new Dialog(Select_Events_Ticket_Types_Screen.this, R.style.DialoueBox);

        dialog.setContentView(R.layout.setting_layout);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        final ToggleButton Togle_Sound1 = (ToggleButton) dialog.findViewById(R.id.toggleButton);
        final ToggleButton Togle_Sound2 = (ToggleButton) dialog.findViewById(R.id.toggleButton2);
        final ToggleButton Togle_flash1 = (ToggleButton) dialog.findViewById(R.id.toggleButtonn);
        final ToggleButton Togle_flash2 = (ToggleButton) dialog.findViewById(R.id.toggleButton3);
        final ToggleButton Togle_flash3 = (ToggleButton) dialog.findViewById(R.id.toggleButton22);
        Button Btn_clear_organizer_list = (Button) dialog.findViewById(R.id.textView2111);
        Btn_clear_organizer_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_confirmation();
            }
        });
        String sound_status = (shared.getString("sound", "default"));
        String flash_status = (shared.getString("Flash", "Automatic"));
        if (sound_status.contentEquals("default")) {
            Togle_Sound1.setChecked(false);
            Togle_Sound2.setChecked(true);
        } else if (sound_status.contentEquals("on")) {
            Togle_Sound1.setChecked(false);
            Togle_Sound2.setChecked(true);
        } else if (sound_status.contentEquals("off")) {
            Togle_Sound1.setChecked(true);
            Togle_Sound2.setChecked(false);
        }


        if (flash_status.contentEquals("Automatic")) {
            Togle_flash2.setChecked(false);
            Togle_flash3.setChecked(false);
            Togle_flash1.setChecked(true);
        } else if (flash_status.contentEquals("on")) {
            Togle_flash2.setChecked(true);
            Togle_flash3.setChecked(false);
            Togle_flash1.setChecked(false);
        } else if (flash_status.contentEquals("off")) {
            Togle_flash2.setChecked(false);
            Togle_flash3.setChecked(true);
            Togle_flash1.setChecked(false);
        }

        // Togle_flash1.setChecked(true);
        Togle_Sound1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    Togle_Sound2.setChecked(false);
                    SharedPreferences.Editor editor = shared.edit();

                    editor.putString("sound", "off");

                    editor.commit();
                } else if (isChecked == false) {
                    Togle_Sound2.setChecked(true);
                }
            }
        });
        Togle_Sound2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    SharedPreferences.Editor editor = shared.edit();

                    editor.putString("sound", "on");

                    editor.commit();
                    Togle_Sound1.setChecked(false);
                } else if (isChecked == false) {
                    Togle_Sound1.setChecked(true);
                }
            }
        });
        Togle_flash1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    Togle_flash2.setChecked(false);
                    Togle_flash3.setChecked(false);
                    SharedPreferences.Editor editor = shared.edit();

                    editor.putString("Flash", "Automatic");
                    //  editor.
                    // mPager.getAdapter.notifyDataSetChanged();


                    editor.commit();
                    Intent i1 = new Intent(Select_Events_Ticket_Types_Screen.this, Ticket_Scanning_screen.class);
                    i1.putExtra("dialog", "open");
                    startActivity(i1);
                    finish();
                    //mPagerAdapter.getItemPosition(3);
                    //Status_Fragment.newInstance("SecondFragment, Instance 1");
                    //mPagerAdapter.
                    // mPagerAdapter.notifyDataSetChanged();
                } else {
                    Togle_flash2.setChecked(false);
                    Togle_flash3.setChecked(false);
                    Togle_flash1.setChecked(true);
//                    SharedPreferences.Editor editor = shared.edit();
//
//                    editor.putString("Flash", "Automatic");
//                    // mPager.getAdapter.notifyDataSetChanged();
//
//
//                    editor.commit();
//                    Intent i1 = new Intent(Ticket_Scanning_screen.this, Ticket_Scanning_screen.class);
//                    i1.putExtra("dialog","open");
//                    startActivity(i1);
//                    finish();
                }

            }
        });
        Togle_flash2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    Togle_flash1.setChecked(false);
                    Togle_flash3.setChecked(false);
                    SharedPreferences.Editor editor = shared.edit();

                    editor.putString("Flash", "on");

                    editor.commit();
                    Intent i1 = new Intent(Select_Events_Ticket_Types_Screen.this, Ticket_Scanning_screen.class);
                    i1.putExtra("dialog", "open");
                    startActivity(i1);
                    finish();
                } else {
                    Togle_flash2.setChecked(true);
                    Togle_flash3.setChecked(false);
                    Togle_flash1.setChecked(false);
//
                }

            }
        });
        Togle_flash3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    Togle_flash1.setChecked(false);
                    Togle_flash2.setChecked(false);
                    SharedPreferences.Editor editor = shared.edit();

                    editor.putString("Flash", "off");

                    editor.commit();
                    Intent i1 = new Intent(Select_Events_Ticket_Types_Screen.this, Ticket_Scanning_screen.class);
                    i1.putExtra("dialog", "open");
                    startActivity(i1);
                    finish();
                } else {
                    Togle_flash2.setChecked(false);
                    Togle_flash3.setChecked(true);
                    Togle_flash1.setChecked(false);
//
                }

            }
        });
//        RelativeLayout RLV_popup = (RelativeLayout) dialog.findViewById(R.id.Rlv_popup);
//        YoYo.with(Techniques.BounceIn)
//                .duration(700).playOn(RLV_popup);
        Button Btn_gotit = (Button) dialog.findViewById(R.id.button6);
        Btn_gotit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    private void Dialog() {
        // reviewicon=(ImageView) view.findViewById(R.id.reviewicon);
        final Dialog dialog = new Dialog(Select_Events_Ticket_Types_Screen.this, R.style.DialoueBox);

        dialog.setContentView(R.layout.dialog_for_setting);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        RelativeLayout RLV_popup = (RelativeLayout) dialog.findViewById(R.id.Rlv_popup);
        YoYo.with(Techniques.BounceIn)
                .duration(700).playOn(RLV_popup);
        Button Btn_gotit = (Button) dialog.findViewById(R.id.btn_confirm);
        Button Btn_setting = (Button) dialog.findViewById(R.id.button7);
        Button Btn_Selectevent_tickets_types = (Button) dialog.findViewById(R.id.button4);
        Btn_Selectevent_tickets_types.setVisibility(View.GONE);
        Button Btn_Logout = (Button) dialog.findViewById(R.id.button5);

        Btn_gotit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_Setting();
                dialog.dismiss();
            }
        });
        Btn_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(Select_Events_Ticket_Types_Screen.this, Login_screen.class);
                startActivity(i1);
                finish();
            }
        });
    }

    public void dialog_confirmation() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, You wanted to Clear Organizer List");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        SharedPreferences settings = getSharedPreferences("Flexbillet", MODE_PRIVATE);
                        settings.edit().clear().commit();
                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
