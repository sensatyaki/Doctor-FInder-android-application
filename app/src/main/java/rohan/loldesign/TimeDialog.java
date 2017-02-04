package rohan.loldesign;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;



public class TimeDialog extends DialogFragment implements View.OnClickListener{
   RadioGroup r1,r2;
    RadioButton b1,b2;
    Button butt;
    SharedPreferences pref;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.timedialog, container, false);
       getDialog().setTitle("Choose date and time");

        r1=(RadioGroup)v.findViewById(R.id.day);
        r2=(RadioGroup)v.findViewById(R.id.time);
        butt=(Button)v.findViewById(R.id.appsubmit);
        butt.setOnClickListener(this);
        return v;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onClick(View v) {

        String d="",t="";
        switch(r1.getCheckedRadioButtonId())
        {
            case R.id.radioButton : d="Today";break;
            case R.id.radioButton2 : d="Tommorow";break;
            case R.id.radioButton3 : d="Day after";break;
        }
        switch(r2.getCheckedRadioButtonId())
        {
            case R.id.radioButton4 : t="Morning";break;
            case R.id.radioButton5 : t="Afternoon";break;
            case R.id.radioButton6 : t="Evening";break;
        }
        final ProgressDialog pd = ProgressDialog.show(getActivity(), "Booking", "Saving Appointment");
        Calendar c=Calendar.getInstance();
        SimpleDateFormat sd=new SimpleDateFormat("dd-MM-yyyy");
        String date=sd.format(c.getTime());
        pref = getActivity().getSharedPreferences("LolPref", 0);
        ParseSearchData p=(ParseSearchData)getActivity().getIntent().getSerializableExtra("rohan.loldesign.ParseSearchData.SearchClick");
        ParseObject po=new ParseObject("Appointments");
        po.put("Useremail",pref.getString("UserEMAIL","X"));
        po.put("Date",d);
        po.put("Time",t);
        po.put("Docphone",p.docphone);
        po.put("Docname",p.name);
        po.put("Clinic",p.clinic) ;
        po.put("Address",p.address);
        po.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                pd.dismiss();
                DoctorProfile.dia();
            }
        });
    }

}
