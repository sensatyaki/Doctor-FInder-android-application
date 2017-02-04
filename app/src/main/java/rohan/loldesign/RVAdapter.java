package rohan.loldesign;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.w3c.dom.Text;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.DataViewHolder>{
    List<RecData> lr;
   ProgressDialog pd;
    RVAdapter(List<RecData> dat){
        lr=dat;
    }
    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.dataitem,parent,false);
        DataViewHolder dv=new DataViewHolder(v);
        return dv;
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, final int position) {
        holder.clinic.setText(lr.get(position).clinic);
        holder.doctor.setText(lr.get(position).name);
        holder.address.setText(lr.get(position).address);
        holder.date.setText(lr.get(position).date);
        holder.time.setText(lr.get(position).time);
        holder.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View viv=v;
                AlertDialog.Builder al=new AlertDialog.Builder(v.getContext());
                al.setTitle("Cancel appointment");
                al.setMessage("Are you sure?");
                al.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pd = ProgressDialog.show(viv.getContext(), "Deleting", "Cancelling appointment");
                        ParseQuery<ParseObject> p=ParseQuery.getQuery("Appointments");
                        p.whereEqualTo("objectId",lr.get(position).objid);
                        Log.i("Parse",lr.get(position).objid+" ");
                       p.getFirstInBackground(new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject parseObject, ParseException e) {
                                if(e==null) {
                                    parseObject.deleteInBackground();
                                    pd.dismiss();
                                    lr.remove(position);

                                    notifyDataSetChanged();
                                    Snackbar.make(MainActivity.cl,"Appointment cancelled!",Snackbar.LENGTH_SHORT)
                                            .setAction("DISMISS", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                }
                                            })
                                            .show();
                                    Log.i("Size",lr.size()+" ");

                                    if(lr.size()==0) {
                                        Tab2.tv.setText("You have not booked any appointments.");
                                        Tab2.tv.setVisibility(View.VISIBLE);
                                    }
                                }
                                else
                                    Toast.makeText(viv.getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                al.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                al.create();
                al.show();


            }
        });
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
        TextView clinic;
        TextView doctor;
        TextView date;
        TextView time;
        TextView address;
        Button b;


        DataViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.appcard_view);
            clinic = (TextView)itemView.findViewById(R.id.appinfo_text2);
            doctor = (TextView)itemView.findViewById(R.id.appinfo_text3);
            address = (TextView)itemView.findViewById(R.id.appinfo_text4);
            date= (TextView)itemView.findViewById(R.id.appinfo_text5);
            time = (TextView)itemView.findViewById(R.id.appinfo_text6);
            b=(Button)itemView.findViewById(R.id.cancelapp);

        }
    }

}