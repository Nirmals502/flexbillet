package flexbillet.flexbillet;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import Fragments.Login_with_credential;
import Fragments.Login_with_qr;


public class Login_screen extends FragmentActivity implements Login_with_credential.OnFragmentInteractionListener, Login_with_qr.OnFragmentInteractionListener{
    private ViewPager mPager;
    private static final int NUM_PAGES = 2;
    private PagerAdapter mPagerAdapter;
    ImageView Img_indicator1,Img_indicator2;
    String organizerkey,passphrase;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_screen);
        SharedPreferences shared = getSharedPreferences("Flexbillet", MODE_PRIVATE);
        organizerkey = (shared.getString("organizerkey", "empty"));
        passphrase = (shared.getString("passphrase", "empty"));




        mPager = (ViewPager) findViewById(R.id.pager);
        Img_indicator1 = (ImageView)findViewById(R.id.imageView4);
        Img_indicator2 = (ImageView)findViewById(R.id.imageView5);


        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        if(organizerkey.contentEquals("empty")){
            mPager.setCurrentItem(0);
            Img_indicator1.setImageResource(R.drawable.swipe_dots_orenge);
            Img_indicator2.setImageResource(R.drawable.swipe_dots_grey);
            Dialog();
        }else{
            mPager.setCurrentItem(1);
            Img_indicator1.setImageResource(R.drawable.swipe_dots_grey);
            Img_indicator2.setImageResource(R.drawable.swipe_dots_orenge);
        }

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position ==0){
                    Img_indicator1.setImageResource(R.drawable.swipe_dots_orenge);
                    Img_indicator2.setImageResource(R.drawable.swipe_dots_grey);
                }else if(position ==1){
                    Img_indicator1.setImageResource(R.drawable.swipe_dots_grey);
                    Img_indicator2.setImageResource(R.drawable.swipe_dots_orenge);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public void Onloginwithqr(String string) {

    }

    @Override
    public void Onloginwithcrential(String string) {

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

          switch(position) {

            case 0: return Login_with_qr.newInstance("FirstFragment, Instance 1");
            case 1: return Login_with_credential.newInstance("SecondFragment, Instance 1");

            default: return Login_with_qr.newInstance("ThirdFragment, Default");

        }

        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

    }

    private void Dialog() {
        // reviewicon=(ImageView) view.findViewById(R.id.reviewicon);
        final Dialog dialog = new Dialog(Login_screen.this, R.style.DialoueBox);

        dialog.setContentView(R.layout.order_confirmation_dialog);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        Button Btn_gotit = (Button) dialog.findViewById(R.id.btn_confirm);
        Btn_gotit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



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
