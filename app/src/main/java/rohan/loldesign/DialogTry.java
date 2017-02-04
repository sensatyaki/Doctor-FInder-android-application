package rohan.loldesign;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class DialogTry extends DialogFragment {
  RadioGroup r1,r2;String d,t;
    SharedPreferences spob;
    SharedPreferences.Editor editor;
    public interface Searchdiag
  {
      public void sear(String a,String b);
  }

    Searchdiag sd;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.sd=(Searchdiag)activity;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getDialog().setTitle("Apply filter");

        View v = inflater.inflate(R.layout.settinglayout, container, false);
        spob= PreferenceManager.getDefaultSharedPreferences(getActivity());
      editor = spob.edit();

        Button b=(Button)v.findViewById(R.id.filter);
        Button b1=(Button)v.findViewById(R.id.cancelfilter);
        r1=(RadioGroup)v.findViewById(R.id.gender);
        r2=(RadioGroup)v.findViewById(R.id.sort);
      int a=spob.getInt("Filtergen",0);
        int c=spob.getInt("Filtersort",0);
      if(a>0) {
          RadioButton rb1 = (RadioButton) v.findViewById(a);
          Log.i("Radio1",a+" ");
          rb1.setChecked(true);
      }
        if(c>0) {
            RadioButton rb2 = (RadioButton) v.findViewById(c);
            Log.i("Radio2",c+" ");
            rb2.setChecked(true);
        }
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(r1.getCheckedRadioButtonId())
                {
                    case R.id.male : d="Male";break;
                    case R.id.female : d="Female";break;
                    default: d="X";

                }
                switch(r2.getCheckedRadioButtonId())
                {
                    case R.id.filradioButton4 : t="Fee";break;
                    case R.id.filradioButton5 : t="Experience";break;
                    case R.id.filradioButton6 : t="Recommendation";break;
                    default: t="X";
                }
                editor.putInt("Filtergen",r1.getCheckedRadioButtonId());
                editor.putInt("Filtersort",r2.getCheckedRadioButtonId());
                editor.commit();
                Log.i("Gen",d+" ");
                Log.i("Gen",t+" ");
                sd.sear(d, t);

            }
        });
         b1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 editor.remove("Filtergen");
                 editor.remove("Filtersort");
                 editor.commit();
                 sd.sear("X","X");

             }
         });

        return v;
    }



}
