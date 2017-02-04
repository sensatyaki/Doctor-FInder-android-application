package rohan.loldesign;


import java.io.Serializable;

public class ReviewData implements Serializable{
    String email,docphone;
    int rating;
    String content;
    ReviewData(String a,String x,String c,int b)
    {
        email=a;
        docphone=x;
        content=c;
        rating=b;


    }
}
