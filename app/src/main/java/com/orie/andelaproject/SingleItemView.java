package com.orie.andelaproject;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static android.R.attr.country;

public class SingleItemView extends AppCompatActivity {
    //declare variables
    String username;
    String images;
    String userpage;
    Button button;
    TextView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_view);

        Intent i = getIntent();
        // Get the result of username
        username = i.getStringExtra("username");
        // Get the result of images
        images = i.getStringExtra("images");
        // Get the result of userpage
        userpage = i.getStringExtra("userpage");



        // Locate the TextViews in singleitemview.xml
        TextView userName = (TextView) findViewById(R.id.txtusername);
         profile = (TextView) findViewById(R.id.textprofile);

        // Locate the ImageView in singleitemview.xml
        ImageView img = (ImageView) findViewById(R.id.imageView);

        // Set results to the TextViews
        userName.setText(username);
        profile.setText(userpage);

        //loading the image
        Picasso.with(this).load(images).resize(250, 200).into(img);

        //respond to profileUrl click event
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.setTextColor(Color.BLUE);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(userpage));
                startActivity(Intent.createChooser(intent, "Select any of the options "));
            }
        });

        //respond to button share event
        button = (Button) findViewById(R.id.buttonShare);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "Check out this awesome developer @ <github " + username + ">, <github "+ userpage + ">.";
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(intent, text ));
            }
        });
    }
}
