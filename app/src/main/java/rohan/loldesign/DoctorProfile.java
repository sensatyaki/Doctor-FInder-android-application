package rohan.loldesign;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class DoctorProfile extends AppCompatActivity implements OnMapReadyCallback {
   TextView tv,tv2,tv3,tv4,tv5,tv6,tv7;ParseSearchData docrecv;
    ParseFile pf; double lat,lng;
    Button shareb;
    String body;
    ParseImageView piv;
    static TimeDialog af;
    static CoordinatorLayout cd;
    ArrayList<ReviewData> reviewlist=new ArrayList<ReviewData>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent doc=getIntent();
         docrecv=(ParseSearchData)doc.getSerializableExtra("rohan.loldesign.ParseSearchData.SearchClick");
        setContentView(R.layout.activity_doctor_profile);
        shareb=(Button)findViewById(R.id.sh);
        piv=(ParseImageView)findViewById(R.id.docpiv);
        piv.setPlaceholder(getResources().getDrawable(R.drawable.place));
        ParseQuery<ParseObject> q1=ParseQuery.getQuery("Doc");
        q1.whereEqualTo("Docphone",docrecv.docphone);
        q1.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                pf=parseObject.getParseFile("Docprofile");
                piv.setParseFile(pf);
                piv.loadInBackground();
            }
        });
        cd=(CoordinatorLayout)findViewById(R.id.docood);
        Toolbar t= (Toolbar)findViewById(R.id.docproftoolbar);
        CollapsingToolbarLayout dt=(CollapsingToolbarLayout)findViewById(R.id.docprofcollapse);
        dt.setTitle("Doctor's profile");
        dt.setExpandedTitleColor(Color.WHITE);
        dt.setCollapsedTitleTextColor(Color.WHITE);
        setSupportActionBar(t);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



                       tv = (TextView) findViewById(R.id.docprofinfo_text2);
        tv2 = (TextView) findViewById(R.id.docprofinfo_text3);
        tv3 = (TextView) findViewById(R.id.docprofinfo_text4);
        tv4 = (TextView) findViewById(R.id.docprofinfo_text5);
        tv5 =(TextView)findViewById(R.id.docprofinfo_text6);
        tv6 =(TextView)findViewById(R.id.docprofinfo_text7);
        tv7 =(TextView)findViewById(R.id.docprofinfo_text8);
                       tv.setText(docrecv.name);

        tv2.setText(docrecv.degree);
        tv3.setText(docrecv.experience+" years");
        tv4.setText("Rs."+docrecv.fee);
        tv5.setText(docrecv.docphone);
        tv6.setText(docrecv.clinic);
        body="I found "+docrecv.name+" on Doctor+! Download this awesome app now! www.doctorplus.com";
      //  tv7.setText(docrecv.recommendation);
        lat=docrecv.latitude;
        lng=docrecv.longitude;
                       ParseQuery<ParseObject> q = ParseQuery.getQuery("Review");
                       q.whereEqualTo("Docphone", docrecv.docphone);
                       q.findInBackground(new FindCallback<ParseObject>() {
                           @Override
                           public void done(List<ParseObject> list, ParseException e) {
                               ReviewData rd;
                               for (ParseObject p : list) {
                                   rd = new ReviewData(p.getString("Useremail"), p.getString("Docphone"), p.getString("Content"), p.getInt("Rating"));
                                   reviewlist.add(rd);
                               }
                           }
                       });


    }

                   public void showreview(View v) {
                       Intent i = new Intent(DoctorProfile.this, Review.class);
                       i.putExtra("rohan.loldesign.DoctorProfile.reviewdata", reviewlist);
                       i.putExtra("Phone", docrecv.docphone);
                       startActivity(i);
                   }

                   @Override
                   public boolean onOptionsItemSelected(MenuItem item) {
                       if (item.getItemId() == android.R.id.home) {
                           NavUtils.navigateUpFromSameTask(this);
                           return true;
                       }
                       return super.onOptionsItemSelected(item);


                   }

                   public void docbook(View v) {

                       af = new TimeDialog();
                       af.show(getFragmentManager(), "Select date and time");
                   }

                   /* af.onDismiss(new DialogInterface() {
                        @Override
                        public void cancel() {

                        }

                        @Override
                        public void dismiss() {
                            Snackbar.make(viv, "This is a SnackBar with options!", Snackbar.LENGTH_LONG)
                                    .setAction("Options", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    })
                                    .setActionTextColor(Color.RED)
                                    .show();
                        }
                    });*/
                   public static void dia() {
                       af.dismiss();
                       Snackbar.make(cd, "Appointment Confirmed!", Snackbar.LENGTH_LONG)
                               .setAction("Dismiss", new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                   }
                               })
                               .setActionTextColor(Color.RED)
                               .show();
                   }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng loc = new LatLng(lat, lng);
        map.addMarker(new MarkerOptions().position(loc).title(docrecv.clinic));
        map.moveCamera(CameraUpdateFactory.newLatLng(loc));
        map.animateCamera(CameraUpdateFactory.zoomTo(17.0f ) );
    }
    public void shareinfo(View v)
    {
        Intent sharing=new Intent(android.content.Intent.ACTION_SEND);
        sharing.setType("text/plain");

        sharing.putExtra(android.content.Intent.EXTRA_SUBJECT, "Doctor+");
        sharing.putExtra(android.content.Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(sharing, "Share via"));
    }
}

