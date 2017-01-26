package com.example.malmike21.orahiapp.activities;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.malmike21.orahiapp.R;
import com.squareup.picasso.Picasso;

public class ImageDetailsActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button button;
    private static final String DRAWABLE_RESOURCE = "resource";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_details);

        imageView = (ImageView)findViewById(R.id.img);
        button = (Button)findViewById(R.id.btnClose);

        //int drawableResource = getIntent().getIntExtra(DRAWABLE_RESOURCE, 0);
        //imageView.setImageResource(drawableResource);

        String drawableResource = getIntent().getExtras().getString(DRAWABLE_RESOURCE);
        Picasso.with(this)
                .load(drawableResource)
                .into(imageView);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

