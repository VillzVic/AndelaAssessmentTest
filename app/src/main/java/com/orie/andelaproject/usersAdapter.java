package com.orie.andelaproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Orie Victor on 3/7/2017.
 */

public class usersAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;

    HashMap<String, String> resultp = new HashMap<String, String>();

    public usersAdapter(Context context, ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // Declare Variables
        TextView username;
        TextView userpage;
        ImageView image;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.list_item, parent, false);
        // Get the position
        resultp = data.get(position);

        // Locate the TextViews in listview_item.xml
        username = (TextView) itemView.findViewById(R.id.textView);
//        userpage = (TextView) itemView.findViewById(R.id.country);

        // Locate the ImageView in listview_item.xml
        image = (ImageView) itemView.findViewById(R.id.profile_pic);

        // Capture position and set results to the TextViews
        username.setText(resultp.get(MainActivity.USERNAME));
//        userpage.setText(resultp.get(MainActivity.USERPAGE));


        Picasso.with(context).load(resultp.get(MainActivity.IMAGES)).into(image);

        // Capture ListView item click
        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get the position
                resultp = data.get(position);
                Intent intent = new Intent(context, SingleItemView.class);
                // Pass all data username
                intent.putExtra("username", resultp.get(MainActivity.USERNAME));
                // Pass all data images
                intent.putExtra("images", resultp.get(MainActivity.IMAGES));
                // Pass all data userpage
                intent.putExtra("userpage", resultp.get(MainActivity.USERPAGE));

                // Start SingleItemView Class
                context.startActivity(intent);

            }
        });
        return itemView;


    }
}
