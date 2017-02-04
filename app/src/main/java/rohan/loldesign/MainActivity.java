package rohan.loldesign;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Home","Appointments"};
    int Numboftabs =2;



   static CoordinatorLayout cl=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        Parse.initialize(this,getString(R.string.appid),getString(R.string.clikey));


        Log.i("Check", "Test1");
        setContentView(R.layout.activity_main);


        cl=(CoordinatorLayout)findViewById(R.id.cood);
        Log.i("Check", "Test2");
        android.support.v7.widget.Toolbar t=(android.support.v7.widget.Toolbar)findViewById(R.id.tool1);
        t.setTitle("Doctor+");

        t.setTitleTextColor(Color.WHITE);



        setSupportActionBar(t);
        DrawerLayout dl=(DrawerLayout)findViewById(R.id.root);
        ActionBarDrawerToggle abt=new ActionBarDrawerToggle(this,dl,t,R.string.open,R.string.close);/*{
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };*/
        dl.setDrawerListener(abt);
        abt.syncState();
        abt.setDrawerIndicatorEnabled(true);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                //return getResources().getColor(R.color.material_deep_teal_500);
                 return Color.WHITE;
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        //View v=findViewById(R.id.root);
        //snackBar(v);
    }


    public void snackBar(View view) {

        Snackbar.make(cl, "This is a SnackBar!", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();

    }


    public void snackBarX(View view) {

        Snackbar.make(cl, "This is a SnackBar with options!", Snackbar.LENGTH_LONG)
                .setAction("Options", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Snackbar with Toasts!", Toast.LENGTH_SHORT).show();
                    }
                })
               .setActionTextColor(Color.YELLOW)
               .show();
    }





}
