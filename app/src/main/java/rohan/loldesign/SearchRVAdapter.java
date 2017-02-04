package rohan.loldesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import java.util.List;



public class SearchRVAdapter extends RecyclerView.Adapter<SearchRVAdapter.DataViewHolder>{
    List<ParseSearchData> lr;

    SearchRVAdapter(List<ParseSearchData> dat){
        lr=dat;
    }
    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.searchdataitem,parent,false);
        DataViewHolder dv=new DataViewHolder(v);
        return dv;
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, final int position) {
        holder.personName.setText(lr.get(position).name);
        holder.personClinic.setText(lr.get(position).clinic);
        holder.personAddress.setText(lr.get(position).address);
        holder.personCTime.setText("Rs."+lr.get(position).fee);

        holder.personRecomm.setText(String.valueOf("♥ "+lr.get(position).recommendation));

        setAnimation(holder.cv);
       // holder.rb.setRating(4.0f);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), DoctorProfile.class);
                ParseSearchData p = lr.get(position);
                i.putExtra("rohan.loldesign.ParseSearchData.SearchClick", p);
                Log.i("TestInt","1");
                v.getContext().startActivity(i);
            }
        });


    }
    private void setAnimation(View viewToAnimate)
    {


            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);


    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    @Override
    public int getItemCount() {
        return lr.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personClinic;
        TextView personAddress;
        TextView personCTime;
        TextView personRecomm;



        DataViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.searchcard_view);
            personName = (TextView)itemView.findViewById(R.id.searchinfo_text2);
            personClinic = (TextView)itemView.findViewById(R.id.searchinfo_text3);
            personAddress = (TextView)itemView.findViewById(R.id.searchinfo_text4);
           personCTime=(TextView)itemView.findViewById(R.id.searchinfo_text5);
            personRecomm=(TextView)itemView.findViewById(R.id.searchinfo_text6);

        }
    }

}