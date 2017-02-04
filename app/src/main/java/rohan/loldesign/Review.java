package rohan.loldesign;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;

public class Review extends AppCompatActivity {
    SharedPreferences pref;
    ArrayList<ReviewData> ard;
    ArrayList<ReviewData> refard;
    RatingBar r;
    EditText et;
     TextView tv,tv1;
    RecyclerView rv;
    ReviewRVAdapter rva;
    CoordinatorLayout cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        tv=(TextView)findViewById(R.id.revempty);

        tv.setVisibility(View.GONE);

        et=(EditText)findViewById(R.id.reviewedit);
        pref = Review.this.getSharedPreferences("LolPref", 0);
        Toolbar t=(Toolbar)findViewById(R.id.reviewtoolbar);
        r=(RatingBar)findViewById(R.id.ratingBar);
        t.setTitle("Reviews");
        t.setTitleTextColor(Color.WHITE);
        setSupportActionBar(t);
        cd=(CoordinatorLayout)findViewById(R.id.revcood);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       rv=(RecyclerView)findViewById(R.id.revrecview);
        rv.setLayoutManager(new LinearLayoutManager(Review.this));
        ard=new ArrayList<>();
         ard=(ArrayList<ReviewData>)getIntent().getSerializableExtra("rohan.loldesign.DoctorProfile.reviewdata");

        if(ard.size()==0) {

            tv.setVisibility(View.VISIBLE);
        }
       // ReviewData sample=new ReviewData("Email","Docphone","Content",4);
      //  Log.i("Hello", String.valueOf(ard.size()));
      //  ard.add(sample);
       // Log.i("Hello", String.valueOf(ard.size()));
         rva=new ReviewRVAdapter(ard);
        rv.setAdapter(rva);

    }

  public void reviewsend(View v)

  {   SharedPreferences pref = Review.this.getSharedPreferences("LolPref", 0);
      if(pref.getString("UserNAME","X").equals("X"))
      {
          AlertDialog.Builder alert = new AlertDialog.Builder(
                  Review.this);
          alert.setTitle("Cannot submit review");
          alert.setMessage("You need to sign in before submitting a review.");
          alert.setNeutralButton("Okay", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                  et.setText(" ");
                  r.setRating(0.0f);
                  startActivity(new Intent(Review.this,MainActivity.class));
              }
          });
          alert.create();
          alert.show();

      }
      else {
          InputMethodManager imm = (InputMethodManager) getSystemService(
                  Context.INPUT_METHOD_SERVICE);
          imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
          final String s = getIntent().getStringExtra("Phone");
          ParseObject po = new ParseObject("Review");
          po.put("Useremail", pref.getString("UserEMAIL", ""));
          po.put("Docphone", s);
          po.put("Content", et.getText().toString());
          po.put("Rating", Math.floor(r.getRating()));
          ReviewData rd = new ReviewData(pref.getString("UserEMAIL", ""), s, et.getText().toString(), (int) Math.floor(r.getRating()));
          ard.add(rd);
          final ProgressDialog pd = ProgressDialog.show(Review.this, "Adding", "Saving Review");
          po.saveInBackground(new SaveCallback() {
              @Override
              public void done(ParseException e) {
                  pd.dismiss();
                  Snackbar.make(cd, "Review submitted!", Snackbar.LENGTH_LONG)
                          .setAction("Dismiss", new View.OnClickListener() {
                              @Override
                              public void onClick(View v) {
                              }
                          })
                          .setActionTextColor(Color.RED)
                          .show();
                  rva = new ReviewRVAdapter(ard);
                  rv.setAdapter(rva);
                  et.setText("");
                  r.setRating(0.0f);
          /*   startActivity(new Intent(Review.this,Review.class)
             .putExtra("rohan.loldesign.DoctorProfile.reviewdata",refard)
              .putExtra("Docphone",s)
                     .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
             );*/

              }
          });
      }

  }
}
