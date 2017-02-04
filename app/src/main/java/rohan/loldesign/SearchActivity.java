package rohan.loldesign;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.widget.DrawerLayout;

import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LocationCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements DialogTry.Searchdiag{
  Intent searchreceiver;
    ProgressDialog pd;
    SearchRVAdapter sea;
    ParseGeoPoint parsegeo;
    Location location;
    String qu,so="X",gen="X";

    static DialogTry dt;
   TextView tv;
    RecyclerView rv;
    Criteria c=new Criteria();
    List<ParseSearchData> parseres=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        tv=(TextView)findViewById(R.id.searchempty);
        tv.setVisibility(View.GONE);
        Toolbar seatool=(Toolbar)findViewById(R.id.searchtool);

        seatool.setTitle("Search results");
        seatool.setTitleTextColor(Color.WHITE);
        setSupportActionBar(seatool);
        DrawerLayout searchdl=(DrawerLayout)findViewById(R.id.searchdrawer);
        ActionBarDrawerToggle abt=new ActionBarDrawerToggle(this,searchdl,seatool,R.string.open,R.string.close);
        searchdl.setDrawerListener(abt);
        abt.syncState();
        abt.setDrawerIndicatorEnabled(true);
        rv=(RecyclerView)findViewById(R.id.searchrecview);
        rv.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
       sea=new SearchRVAdapter(parseres);
        searchreceiver=getIntent();LocationManager mgr = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String best = mgr.getBestProvider(criteria, true);

       location = mgr.getLastKnownLocation(best);
        Toast.makeText(SearchActivity.this, "Lat:" + location.getLatitude() +
                "Lon:" +location.getLongitude(), Toast.LENGTH_SHORT).show();
        qu=searchreceiver.getStringExtra("Query");

        if(searchreceiver.getStringExtra("Gender")!=null)
        gen=searchreceiver.getStringExtra("Gender");
        if(searchreceiver.getStringExtra("Sort")!=null)
        so=searchreceiver.getStringExtra("Sort");
        Log.i("Filter1",qu);
        Log.i("Filter2",gen);
        Log.i("Filter3",so);
        c.setAccuracy(Criteria.ACCURACY_LOW);
        c.setAltitudeRequired(false);
        c.setBearingRequired(false);
        c.setCostAllowed(true);
        c.setPowerRequirement(Criteria.POWER_LOW);
        pd =ProgressDialog.show(SearchActivity.this,"Searching","Fetching result");

        ParseGeoPoint.getCurrentLocationInBackground(6000, c,new LocationCallback() {


            @Override
            public void done(ParseGeoPoint parseGeoPoint, ParseException e) {
                if(e!=null) {
                    Log.i("location", e.getMessage() + " ");
                    if (e.getMessage().equals("Location fetch timed out.")) {
                        e = null;
                        parseGeoPoint = new ParseGeoPoint(location.getLatitude(), location.getLongitude());
                    }
                }
                if (e == null) {
                    Log.i("TestGeo","2");
                    parsegeo = parseGeoPoint;
                    Toast.makeText(SearchActivity.this, "Parse_Lat:" + parseGeoPoint.getLatitude() +
                            " Parse_Lon:" + parseGeoPoint.getLongitude(), Toast.LENGTH_SHORT).show();
                    ParseQuery<ParseObject> q = ParseQuery.getQuery("Doc");
                    //q.whereNear("Location", parsegeo);
                   // q.whereWithinKilometers("Location",parsegeo,2.0);
                    q.whereEqualTo("Type",qu);

                    if(!gen.equals("X"))
                        q.whereEqualTo("Gender",gen);
                    if(!so.equals("X"))
                        q.orderByAscending(so);
                    q.findInBackground(new FindCallback<ParseObject>() {


                        public void done(List<ParseObject> list, ParseException e) {
                            if (list.size() == 0) {
                                pd.dismiss();
                                Toast.makeText(SearchActivity.this, "No results found!", Toast.LENGTH_SHORT).show();
                                tv.setVisibility(View.VISIBLE);
                            }
                            if (e == null) {
                                Log.i("TestGeo", "4");
                                for (ParseObject j : list) {
                                    Log.i("TestGeo", "5");

                                  //  Toast.makeText(SearchActivity.this, j.getString("Name"), Toast.LENGTH_SHORT).show();
                                    ParseGeoPoint parserecv = j.getParseGeoPoint("Location");
                                    ParseSearchData ps = new ParseSearchData(parserecv.getLatitude(),
                                            parserecv.getLongitude(), j.getString("Name"),
                                            j.getString("Clinic"), j.getString("Type"),
                                            j.getString("Degree"), j.getInt("Experience"), j.getString("Address"),
                                            j.getString("Docphone"), j.getString("Gender"), j.getInt("Fee"), j.getInt("Recommendation")
                                    );
                                    parseres.add(ps);
                                    pd.dismiss();
                                    rv.setAdapter(sea);

                                }
                            } else {

                                pd.dismiss();
                                Toast.makeText(SearchActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                            }

                        }

                    });
                }
                else {
                    pd.dismiss();
                    Toast.makeText(SearchActivity.this, e.getMessage() + " Check your location settings.Make sure they are set " +
                            "to High Accuracy" , Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SearchActivity.this, MainActivity.class));
                }

            }

        });

    }
    public void filterbtn(View v)
    {
        dt=new DialogTry();
        dt.show(getFragmentManager(),"dia");
    }
    public static void closefil()
    {
        dt.dismiss();
    }



    public void sear(String a, String b) {
        dt.dismiss();
        Log.i("Gen",a+" ");
        Log.i("Sort",b+" ");
        finish();
         startActivity(new Intent(SearchActivity.this,SearchActivity.class)
                 .putExtra("Query",qu)
                 .putExtra("Gender",a)
                 .putExtra("Sort",b).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

         );
    }
}
