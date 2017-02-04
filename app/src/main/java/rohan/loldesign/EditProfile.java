package rohan.loldesign;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class EditProfile extends AppCompatActivity {
   TextView t1,t2,t3,t4,t5;Button b1,b2;ImageView iv;
    DrawerLayout searchdl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        t1=(TextView)findViewById(R.id.editprof1);
        t2=(TextView)findViewById(R.id.editprof2);
        t3=(TextView)findViewById(R.id.editprof3);
        t4=(TextView)findViewById(R.id.editprof4);
        t5=(TextView)findViewById(R.id.editprof5);
        iv=(ImageView)findViewById(R.id.editcoverpic);

        SharedPreferences pref1=getSharedPreferences("LolPref", 0);
        if(!pref1.getString("UserPROFILE","X").equals("X")) {
            Picasso.with(EditProfile.this).load(pref1.getString("UserPROFILE", "X")).placeholder(R.drawable.place).into(iv);
        }
        b1=(Button)findViewById(R.id.editprof);

        SharedPreferences pref = EditProfile.this.getSharedPreferences("LolPref", 0);
        String uname = pref.getString("UserNAME","Log in");
        String uemail=pref.getString("UserEMAIL","");
        String uage=pref.getString("UserAGE","");
        String ublood=pref.getString("UserBLOOD","");
        String uphone=pref.getString("UserPHONE","");
        t1.setText(uname);
        t2.setText(uphone);
        t3.setText(uemail);
        t4.setText(ublood);
        t5.setText(uage);
        Toolbar seatool=(Toolbar)findViewById(R.id.editproftool);

        seatool.setTitle("User profile");
        seatool.setTitleTextColor(Color.WHITE);
        setSupportActionBar(seatool);
      searchdl=(DrawerLayout)findViewById(R.id.editpdrawer);
        ActionBarDrawerToggle abt=new ActionBarDrawerToggle(this,searchdl,seatool,R.string.open,R.string.close);
        searchdl.setDrawerListener(abt);
        abt.syncState();
        abt.setDrawerIndicatorEnabled(true);

    }

   public void editprof(View v)
   {
      startActivity(new Intent(EditProfile.this,EditUser.class));


   }

    @Override
    public void onBackPressed() {

        searchdl.openDrawer(Gravity.LEFT);
    }
}
