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

import java.util.ArrayList;
import java.util.List;



public class ReviewRVAdapter extends RecyclerView.Adapter<ReviewRVAdapter.DataViewHolder>{
   ArrayList<ReviewData> lr;

    ReviewRVAdapter(ArrayList<ReviewData> dat){
        lr=dat;
    }
    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.reviewdataitem,parent,false);
        DataViewHolder dv=new DataViewHolder(v);
        return dv;
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, final int position) {
        int r=lr.get(position).rating;
        String s="";
        for(int i=0;i<r;i++)
        {
            s=s+"★";
        }
        holder.email.setText(lr.get(position).email);
        holder.body.setText(lr.get(position).content);
        holder.stars.setText(s);
        setAnimation(holder.cv);



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
        TextView email;
        TextView body;
        TextView stars;



        DataViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.reviewcard_view);
           email = (TextView)itemView.findViewById(R.id.reviewinfo_text1);
           body = (TextView)itemView.findViewById(R.id.reviewinfo_text3);
            stars=(TextView)itemView.findViewById(R.id.reviewinfo_text2);


        }
    }

}