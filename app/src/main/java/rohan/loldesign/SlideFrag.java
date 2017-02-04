package rohan.loldesign;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;

public class SlideFrag extends Fragment{


    ListView lv;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences pref = getActivity().getSharedPreferences("LolPref", 0);
        String uname = pref.getString("UserNAME","Log in");


        View v;
        v=inflater.inflate(R.layout.slidefrag,container,false);
        TextView tv=(TextView)v.findViewById(R.id.drawertext);

        ImageView iv=(ImageView)v.findViewById(R.id.coverpic);
        Log.i("Profile",pref.getString("UserPROFILE","X") );
        if(!pref.getString("UserPROFILE","X").equals("X")) {
           Picasso.with(v.getContext()).load(pref.getString("UserPROFILE", "X")).into(iv);
        }
        if(!uname.equals("Log in")) {
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                    startActivity(new Intent(v.getContext(), EditProfile.class));
                }
            });
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                    startActivity(new Intent(v.getContext(), EditProfile.class));
                }
            });
        }
        if(uname.equals("Log in")) {
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                    startActivity(new Intent(v.getContext(), CloudEntry.class));
                }
            });
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                    startActivity(new Intent(v.getContext(),CloudEntry.class));
                }
            });
        }

        tv.setText("Hello "+uname+"!");

        return v;

    }
}
