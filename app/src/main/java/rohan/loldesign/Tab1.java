package rohan.loldesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;


public class Tab1 extends Fragment{
    AutoCompleteTextView et;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;
        v=inflater.inflate(R.layout.tab1,container,false);
        et=(AutoCompleteTextView)v.findViewById(R.id.searchedit);

    String speciality[]={"General Physician","Dentist","Dermatologist","Neurologist","Cardiologist"
                          ,"ENT","Psychiatrist" };
        ArrayAdapter<String> adap=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,speciality);
        et.setAdapter(adap);


        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId== EditorInfo.IME_ACTION_SEARCH)
                {
                    startActivity(new Intent(getActivity(),SearchActivity.class).putExtra("Query",v.getText().toString()));
                }
                return true;
            }
        });
        return v;
    }

}
