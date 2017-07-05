package flexbillet.flexbillet;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

import Fragments.Login_with_credential;
import Fragments.Login_with_qr;
import Fragments.Scan_history;
import Fragments.Scanning_Screen;
import Fragments.Search_ticket_fragment;
import Fragments.Status_Fragment;

public class Ticket_Scanning_screen extends FragmentActivity implements Scanning_Screen.OnFragmentInteractionListener, Status_Fragment.OnFragmentInteractionListener, Scan_history.OnFragmentInteractionListener, Search_ticket_fragment.OnFragmentInteractionListener {
    private ViewPager mPager;
    private static final int NUM_PAGES = 4;
    private PagerAdapter mPagerAdapter;
    ImageView Img_indicator1, Img_indicator2, Img_indicator3, Img_indicator4, Img_menu;
    String organizerkey, passphrase;
    String Str_Screen_check = "no_value";
    SharedPreferences shared;
    ArrayList<String> child_list_id = new ArrayList<String>();
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ticket__scanning_screen);


        mPager = (ViewPager) findViewById(R.id.pager);
        Img_indicator1 = (ImageView) findViewById(R.id.imageView5);
        Img_indicator2 = (ImageView) findViewById(R.id.imageView4);
        Img_indicator3 = (ImageView) findViewById(R.id.imageView7);
        Img_indicator4 = (ImageView) findViewById(R.id.imageView9);
        Img_menu = (ImageView) findViewById(R.id.imageView3);

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        Bundle extras = getIntent().getExtras();
        shared = getSharedPreferences("Flexbillet", MODE_PRIVATE);


        if (extras != null) {
            try {


                Str_Screen_check = extras.getString("dialog");
                if (Str_Screen_check.contentEquals("open")) {
                    Dialog_Setting();
                }
            } catch (java.lang.NullPointerException e) {
                e.printStackTrace();
                child_list_id = (ArrayList<String>) getIntent().getSerializableExtra("List");
            }
//            if(Str_Screen_check.contentEquals("qr_screen")){
//
//            }
            // and get whatever type user account id is
        }


        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    Img_indicator1.setImageResource(R.drawable.swipe_dots_orenge);
                    Img_indicator2.setImageResource(R.drawable.swipe_dots_grey);
                    Img_indicator3.setImageResource(R.drawable.swipe_dots_grey);
                    Img_indicator4.setImageResource(R.drawable.swipe_dots_grey);
                } else if (position == 1) {
                    Img_indicator1.setImageResource(R.drawable.swipe_dots_grey);
                    Img_indicator2.setImageResource(R.drawable.swipe_dots_orenge);
                    Img_indicator3.setImageResource(R.drawable.swipe_dots_grey);
                    Img_indicator4.setImageResource(R.drawable.swipe_dots_grey);
                } else if (position == 2) {
                    Img_indicator1.setImageResource(R.drawable.swipe_dots_grey);
                    Img_indicator2.setImageResource(R.drawable.swipe_dots_grey);
                    Img_indicator3.setImageResource(R.drawable.swipe_dots_orenge);
                    Img_indicator4.setImageResource(R.drawable.swipe_dots_grey);
                } else if (position == 3) {
                    Img_indicator1.setImageResource(R.drawable.swipe_dots_grey);
                    Img_indicator2.setImageResource(R.drawable.swipe_dots_grey);
                    Img_indicator3.setImageResource(R.drawable.swipe_dots_grey);
                    Img_indicator4.setImageResource(R.drawable.swipe_dots_orenge);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog();
            }
        });
    }


    @Override
    public void Scanning_Screen(String string) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void Scanning_history(String string) {

    }


    private void Dialog() {
        // reviewicon=(ImageView) view.findViewById(R.id.reviewicon);
        final Dialog dialog = new Dialog(Ticket_Scanning_screen.this, R.style.DialoueBox);

        dialog.setContentView(R.layout.dialog_for_setting);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        RelativeLayout RLV_popup = (RelativeLayout) dialog.findViewById(R.id.Rlv_popup);
        YoYo.with(Techniques.BounceIn)
                .duration(700).playOn(RLV_popup);
        Button Btn_gotit = (Button) dialog.findViewById(R.id.btn_confirm);
        Button Btn_setting = (Button) dialog.findViewById(R.id.button7);
        Button Btn_Selectevent_tickets_types = (Button) dialog.findViewById(R.id.button4);
        Button Btn_Logout = (Button) dialog.findViewById(R.id.button5);
        Btn_Selectevent_tickets_types.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dialog_Menu();
                Intent i1 = new Intent(Ticket_Scanning_screen.this, Select_Events_Ticket_Types_Screen.class);
              //  i1.putStringArrayListExtra("List",child_list_id);
                startActivity(i1);
                finish();
                Ticket_Scanning_screen.this.overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_left);
            }
        });
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
                Intent i1 = new Intent(Ticket_Scanning_screen.this, Login_screen.class);
                startActivity(i1);
                finish();
            }
        });
    }

    private void Dialog_Setting() {
        // reviewicon=(ImageView) view.findViewById(R.id.reviewicon);
        final Dialog dialog = new Dialog(Ticket_Scanning_screen.this, R.style.DialoueBox);

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
                    Intent i1 = new Intent(Ticket_Scanning_screen.this, Ticket_Scanning_screen.class);
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
                    Intent i1 = new Intent(Ticket_Scanning_screen.this, Ticket_Scanning_screen.class);
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
                    Intent i1 = new Intent(Ticket_Scanning_screen.this, Ticket_Scanning_screen.class);
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

    private void Dialog_Menu() {
        // reviewicon=(ImageView) view.findViewById(R.id.reviewicon);
        final Dialog dialog = new Dialog(Ticket_Scanning_screen.this, R.style.DialoueBox);

        dialog.setContentView(R.layout.menu_dialog);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);


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

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {

                case 0:
                    return Scanning_Screen.newInstance("FirstFragment, Instance 1");
                case 1:

                    return Status_Fragment.newInstance("SecondFragment, Instance 1");
                case 2:
                    return Scan_history.newInstance("ThirdFragment, Instance 1");
                case 3:
                    return Search_ticket_fragment.newInstance("fourthfragment, Instance 1");

                default:
                    return Scanning_Screen.newInstance("FirstFragment, Default");

            }

        }

        @Override
        public int getCount() {
            return NUM_PAGES;
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
