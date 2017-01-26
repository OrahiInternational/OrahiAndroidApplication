package com.example.malmike21.orahiapp.fragments.carouselLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.malmike21.orahiapp.R;
import com.example.malmike21.orahiapp.activities.ServiceActivity;
import com.example.malmike21.orahiapp.sessionManager.SharedInformation;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by malmike21 on 19/01/2017.
 */

public class ItemFragment extends Fragment{

    private static final String POSITION = "position";
    private static final String SCALE = "scale";
    private static final String DRAWABLE_RESOURCE = "resource";
    private static Context context2;
    private OnItemFragmentInteractionListener mListener;

    private int screenWidth;
    private int screenHeight;

    private int[] imageArray = new int[]{R.drawable.image1, R.drawable.image2,
            R.drawable.image3, R.drawable.image4, R.drawable.image5,
            R.drawable.image6, R.drawable.image7, R.drawable.image8,
            R.drawable.image9, R.drawable.image10};


    /*private List<String> imageStringArray = Arrays.asList(
            "https://orahirestapi.herokuapp.com/api/readImage?image=/app/images/serviceProviders/2017-01-12_53940_image1.jpg",
            "https://orahirestapi.herokuapp.com/api/readImage?image=/app/images/serviceProviders/2017-01-12_53940_image2.jpg",
            "https://orahirestapi.herokuapp.com/api/readImage?image=/app/images/serviceProviders/2017-01-12_53940_image3.jpg",
            "https://orahirestapi.herokuapp.com/api/readImage?image=/app/images/serviceProviders/2017-01-12_53940_image4.jpg",
            "https://orahirestapi.herokuapp.com/api/readImage?image=/app/images/serviceProviders/2017-01-12_53940_image5.jpg"
    );*/

    private List<String> imageURIs = SharedInformation.getInstance().getImageURIs();

    public static Fragment newInstance(ServiceActivity context, int pos, float scale) {
        Bundle b = new Bundle();
        b.putInt(POSITION, pos);
        b.putFloat(SCALE, scale);
        context2 = context;

        return Fragment.instantiate(context, ItemFragment.class.getName(), b);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWidthAndHeight();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        final int position = this.getArguments().getInt(POSITION);
        float scale = this.getArguments().getFloat(SCALE);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenWidth / 2, screenHeight / 2);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_image, container, false);

        TextView textView = (TextView) linearLayout.findViewById(R.id.text);
        CarouselLinearLayout root = (CarouselLinearLayout) linearLayout.findViewById(R.id.root_container);
        ImageView imageView = (ImageView) linearLayout.findViewById(R.id.pagerImg);

        textView.setText("Carousel item: " + position);
        imageView.setLayoutParams(layoutParams);
        //imageView.setImageResource(imageArray[postion]);

        /*Picasso.with(context2)
                .load(imageStringArray.get(position))
                .into(imageView);*/

        Picasso.with(context2)
                .load(imageURIs.get(position))
                .into(imageView);

        //handling click event
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resource = DRAWABLE_RESOURCE;
                String image = imageURIs.get(position);
                mListener.onItemFragmentInteractionClicked(resource, image);
            }
        });

        root.setScaleBoth(scale);

        return linearLayout;
    }

    /**
     * Get device screen width and height
     */
    private void getWidthAndHeight() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;
    }

    public interface OnItemFragmentInteractionListener {
        // TODO: Update argument type and name
        void onItemFragmentInteractionClicked(String resources, String imageStringArray);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemFragmentInteractionListener) {
            mListener = (OnItemFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnItemFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
