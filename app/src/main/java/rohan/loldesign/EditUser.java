package rohan.loldesign;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

public class EditUser extends AppCompatActivity {
    EditText t1,t2,t3,t4,t5;Button b1;
    String uname;
    String uphone;
    String uemail;
    String ublood;
    String uage;
    ParseObject po;
    SharedPreferences pref;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        pref = EditUser.this.getSharedPreferences("LolPref", 0);
        t1=(EditText)findViewById(R.id.edituserprof1);
        t2=(EditText)findViewById(R.id.edituserprof2);
        t3=(EditText)findViewById(R.id.edituserprof3);
        t4=(EditText)findViewById(R.id.edituserprof4);
        t5=(EditText)findViewById(R.id.edituserprof5);
        b1=(Button)findViewById(R.id.saveprof);

        iv=(ImageView)findViewById(R.id.usercoverpic);
        SharedPreferences pref1=getSharedPreferences("LolPref", 0);
        if(!pref1.getString("UserPROFILE","X").equals("X")) {
            Picasso.with(EditUser.this).load(pref1.getString("UserPROFILE", "X")).into(iv);
        }
        Toolbar tool=(Toolbar)findViewById(R.id.editusertool);
        t1.setText(pref1.getString("UserNAME"," "));
        t2.setText(pref1.getString("UserPHONE"," "));
        t3.setText(pref1.getString("UserEMAIL"," "));
        t4.setText(pref1.getString("UserBLOOD"," "));
        t5.setText(pref1.getString("UserAGE"," "));
        setSupportActionBar(tool);
        tool.setTitle("User profile");
        tool.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void saveprof(View v)
    {
      uname=t1.getText().toString();
        uphone=t2.getText().toString();
      uemail=t3.getText().toString();
        ublood=t4.getText().toString();
        uage=t5.getText().toString();
        final ProgressDialog p=ProgressDialog.show(this,"Saving","Uploading data");
        ParseQuery<ParseObject> q = ParseQuery.getQuery("Users");
        q.getInBackground(pref.getString("UserID", ""), new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                parseObject.put("Name",uname);
                parseObject.put("Email",uemail);
                parseObject.put("Age",uage);
                parseObject.put("Blood",ublood);
                parseObject.put("Phone",uphone);
                po=parseObject;
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null) {
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("UserID",po.getObjectId());
                            editor.putString("UserNAME",po.getString("Name"));
                            editor.putString("UserEMAIL",po.getString("Email"));
                            editor.putString("UserAGE",po.getString("Age"));
                            editor.putString("UserBLOOD",po.getString("Blood"));
                            editor.putString("UserPHONE",po.getString("Phone"));
                            //editor.putString("UserPROFILE",po.getString("Name"));
                            editor.commit();
                            p.dismiss();

                           startActivity(new Intent(EditUser.this,EditProfile.class));

                    }
                    }
                });

            }
        });

    }

}
