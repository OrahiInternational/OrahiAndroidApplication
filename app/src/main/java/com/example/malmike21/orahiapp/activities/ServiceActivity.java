package com.example.malmike21.orahiapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.malmike21.orahiapp.R;
import com.example.malmike21.orahiapp.fragments.carouselLayout.CarouselPagerAdapter;
import com.example.malmike21.orahiapp.fragments.carouselLayout.ItemFragment;
import com.example.malmike21.orahiapp.sessionManager.SharedInformation;

import java.util.Calendar;

public class ServiceActivity extends AppCompatActivity implements ItemFragment.OnItemFragmentInteractionListener{

        public final static int LOOPS = 1000;
        public CarouselPagerAdapter adapter;
        public ViewPager pager;
        public RelativeLayout imageLayout;
        public static int count = SharedInformation.getInstance().getImageNumber(); //ViewPager items size
        private SharedInformation information = SharedInformation.getInstance();
        private TextView serviceDescription;
        private TextView unitPrice;
        private Switch setWekume;
        private Button setPayment;
        private CheckBox setBooking;
        private TextView bookingDate;
        private TextView bookingTime;
        public Calendar mCalendar;
        private static final String DATE_FORMAT = "yyyy-MM-dd";
        private static final String TIME_FORMAT = "kk:mm";
        DialogFragment dateFragment;
        DialogFragment timeFragment;

        /**
         * You shouldn't define first page = 0.
         * Let define firstpage = 'number viewpager size' to make endless carousel
         */
        public static int FIRST_PAGE = 5;


        @Override
        public void onItemFragmentInteractionClicked(String resources, String imageStringArray) {

                Intent intent = new Intent(this, ImageDetailsActivity.class);
                intent.putExtra(resources, imageStringArray);
                startActivity(intent);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_service);
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                pager = (ViewPager) findViewById(R.id.myviewpager);
                imageLayout = (RelativeLayout) findViewById(R.id.imageLayout);

                if(information.getImageNumber() > 0) {
                        //set page margin between pages for viewpager
                        DisplayMetrics metrics = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(metrics);
                        int pageMargin = ((metrics.widthPixels / 4) * 2);
                        pager.setPageMargin(-pageMargin);

                        adapter = new CarouselPagerAdapter(this, getSupportFragmentManager());
                        pager.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        pager.addOnPageChangeListener(adapter);

                        // Set current item to the middle page so we can fling to both
                        // directions left and right
                        pager.setCurrentItem(information.getImageNumber());
                        pager.setOffscreenPageLimit(3);
                }else{

                        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(0, 0, 0);
                        pager.setVisibility(View.INVISIBLE);
                        imageLayout.setVisibility(View.INVISIBLE);
                        imageLayout.setLayoutParams(param);
                }

                serviceDescription = (TextView) findViewById(R.id.service_description);
                serviceDescription.setText(information.getService().getDiscription());

                unitPrice = (TextView) findViewById(R.id.unit_price);
                unitPrice.setText(information.getService().getRate());

                setBooking = (CheckBox) findViewById(R.id.set_booking);

                setWekume = (Switch) findViewById(R.id.wekume_switch);

                setPayment = (Button) findViewById(R.id.set_payment);

                bookingDate = (TextView) findViewById(R.id.booking_date);
                bookingTime = (TextView) findViewById(R.id.booking_time);
                mCalendar = Calendar.getInstance();


                setBooking.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                                if(setBooking.isChecked()){
                                       bookingDate.setVisibility(View.VISIBLE);
                                }else{
                                        bookingDate.setVisibility(View.GONE);
                                }
                        }
                });

                bookingDate.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {

                        }
                });

                setPayment.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                                setBookingDate();
                        }
                });

                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                        }
                });

                Toast.makeText(this, SharedInformation.getInstance().getService().getServiceName()+"reached", Toast.LENGTH_LONG).show();

        }

        private void setBookingDate(){
                //showDatePickerDialog(v);
        }

}
