package rohan.loldesign;

import com.parse.ParseGeoPoint;

import java.io.Serializable;


public class ParseSearchData implements Serializable{
    double latitude,longitude;
    String name,clinic,type,degree,address,docphone,gender;int fee,experience;
    int recommendation;
    ParseSearchData(double x,double y,String a,String b,String c,String d,int e,String f,String g,String h,int i,int p)
    {
       latitude=x;
        longitude=y;
        name=a;
        clinic=b;
        type=c;
        degree=d;
        experience=e;
        address=f;
        docphone=g;
        gender=h;
        fee=i;
        recommendation=p;


    }
}
