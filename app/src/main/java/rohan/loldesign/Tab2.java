package rohan.loldesign;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.parse.FindCallback;
import com.parse.GetCallback;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class Tab2 extends Fragment{
    RecyclerView rv;
   static TextView tv,tv1;
    List<RecData> lr=new ArrayList<>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v;
        Log.i("Test","4");
        v=inflater.inflate(R.layout.tab2,container,false);
        tv=(TextView)v.findViewById(R.id.appempty);
        tv1=(TextView)v.findViewById(R.id.appload);
        tv.setVisibility(View.GONE);
        Log.i("Test","5");
        rv=(RecyclerView)v.findViewById(R.id.recview);
        Log.i("Test","6");
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.i("Test","7");

        Log.i("Test","9");
        SharedPreferences pref = getActivity().getSharedPreferences("LolPref", 0);

        Log.i("Test",pref.getString("UserEMAIL","Hello"));

        ParseQuery<ParseObject> q = ParseQuery.getQuery("Appointments");
        q.whereEqualTo("Useremail",pref.getString("UserEMAIL",""));
        q.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(list.size()==0) {
                    Log.i("Test", "XX");
                   tv.setVisibility(View.VISIBLE);
                    tv1.setVisibility(View.GONE);
                }
                if(e==null) {
                    Log.i("Test","1");
                    for (ParseObject p : list) {
                        Log.i("Test","2");
                        RecData r = new RecData(p.getString("Clinic"), p.getString("Docphone"), p.getString("Address"), p.getString("Date")
                                , p.getString("Time"), p.getString("Docname"),p.getObjectId()
                        );
                        lr.add(r);
                    }
                    RVAdapter adapter = new RVAdapter(lr);
                    Log.i("Test","8");

                    tv1.setVisibility(View.GONE);
                    rv.setAdapter(adapter);
                }
                else
                { Log.i("Test","3");
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Log.i("Test","9");

        Log.i("Test","11");


        return v;
   }
}
