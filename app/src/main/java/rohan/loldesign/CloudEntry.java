package rohan.loldesign;

import android.app.AlertDialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;



public class CloudEntry extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,View.OnClickListener{
    SharedPreferences spob1;
    SharedPreferences.Editor editor1;
    ParseObject googu;
   EditText et1,et2,et3;
    Button b;
    com.google.android.gms.common.SignInButton googb;
    ProgressDialog beedoo;
    /* Is there a ConnectionResult resolution in progress? */
    private boolean mIsResolving = false;

    /* Should we automatically resolve ConnectionResults when possible? */
    private boolean mShouldResolve = false;
    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;

    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    ParseObject po;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloudentry);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .build();
         Log.i("Google+X","777");
        et1=(EditText)findViewById(R.id.editText);
        et2=(EditText)findViewById(R.id.editText2);
        et3=(EditText)findViewById(R.id.editText3);
        b=(Button)findViewById(R.id.login);
        com.google.android.gms.common.SignInButton googb=(com.google.android.gms.common.SignInButton)findViewById(R.id.sign_in_button);
        googb.setOnClickListener(this);
        et3.setVisibility(View.GONE);
        b=(Button)findViewById(R.id.login);
    }
    public void cloudsend(View v)
    {
        po=new ParseObject("Users");
        String a,b;
        a=et1.getText().toString();
        b=et2.getText().toString();
        po.put("Email", a);
        po.put("Password", b);
       po.saveInBackground(new SaveCallback() {
         ProgressDialog pd=ProgressDialog.show(CloudEntry.this,"Logging in","Signing into account");
           @Override
           public void done(com.parse.ParseException e) {
               pd.dismiss();
               if (e == null)
               {Toast.makeText(CloudEntry.this, "Logged in!", Toast.LENGTH_LONG).show();

                   SharedPreferences spob=getSharedPreferences("LolPref", 0);
                   SharedPreferences.Editor editor = spob.edit();
                   editor.putString("UserID",po.getObjectId());
                   int i=po.getString("Email").lastIndexOf('@');

                   editor.putString("UserNAME", po.getString("Email").substring(0,i));
                   editor.putString("UserEMAIL",po.getString("Email"));
                   editor.putString("UserAGE",po.getString("Age"));
                   editor.putString("UserBLOOD",po.getString("Blood"));
                   editor.putString("UserPHONE",po.getString("Phone"));
                   //editor.putString("UserPROFILE",po.getString("Name"));
                   editor.commit();
                   startActivity(new Intent(CloudEntry.this,MainActivity.class));
               }
               else {
                   Toast.makeText(CloudEntry.this, "Could not log in! Reason: " + e.getMessage(), Toast.LENGTH_LONG).show();
                   Log.i("Exception", e.getMessage());

               }
           }
       });
        et1.setText(" ");
        et2.setText(" ");
    }
    public void signup(View v)

    {
        et1.setError(null);
        et2.setError(null);
        et3.setError(null);
        if(et1.getText().toString().length()==0 ||et2.getText().toString().length()==0||et3.getText().toString().length()==0)

    {
        et3.setVisibility(View.VISIBLE);

        b.setEnabled(false);
        if(et1.getText().toString().length()==0)
            et1.setError("Field cannot be empty");
        if(et2.getText().toString().length()==0)
            et2.setError("Field cannot be empty");
        if(et3.getText().toString().length()==0)
            et3.setError("Field cannot be empty");

    }
        else
    {
        et1.setError(null);
        et2.setError(null);
        et3.setError(null);
        if(et2.getText().toString().equals(et3.getText().toString()) &&
            android.util.Patterns.EMAIL_ADDRESS.matcher(et1.getText().toString().trim()).matches()==true

                )
        {
            cloudsend(v);
        }
        else
        {
            if(   android.util.Patterns.EMAIL_ADDRESS.matcher(et1.getText().toString().trim()).matches()==false)
                et1.setError("Invalid Email Address");
            if(et2.getText().toString().equals(et3.getText().toString())==false)
            {
            et2.setError("Passwords do not match");
            et3.setError("Passwords do not match");
           }
        }

    }

    }
    @Override
    public void onClick(View v) {
        Log.i("Google+Click","1");
        mShouldResolve = true;
        Log.i("Google+Click","2");
        mGoogleApiClient.connect();
        Log.i("Google+Click","3");
        beedoo=ProgressDialog.show(CloudEntry.this,"Logging in","Signing into account");
        Log.i("Google+Click","4");
    }


    @Override
    public void onConnected(Bundle bundle) {

        // onConnected indicates that an account was selected on the device, that the selected
        // account has granted any requested permissions to our app and that we were able to
        // establish a service connection to Google Play services.
        Log.d("Google+3", "onConnected:" + bundle);
        mShouldResolve = false;

        Toast.makeText(CloudEntry.this, "Signed In Google+", Toast.LENGTH_SHORT).show();
        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            String personName = currentPerson.getDisplayName();
            String personPhoto = currentPerson.getImage().getUrl();
            String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
            String personGooglePlusProfile = currentPerson.getUrl();
            Log.i("Google+XX",personName);
            Log.i("Google+XX",personPhoto);
            Log.i("Google+XX",email);
            Log.i("Google+XX",personGooglePlusProfile);

    spob1=getSharedPreferences("LolPref", 0);
            editor1 = spob1.edit();

            int i=personName.lastIndexOf(' ');
            int j=personPhoto.lastIndexOf('?');
            editor1.putString("UserNAME", personName.substring(0,i));
            editor1.putString("UserEMAIL",email);
            editor1.putString("UserPROFILE",personPhoto.substring(0,j));
          googu=new ParseObject("Users");
            googu.put("Email",email);
            googu.put("Name",personName);
            googu.put("Profile",personPhoto.substring(0,j));
            googu.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                     if(e==null) {
                         beedoo.dismiss();
                         editor1.putString("UserID", googu.getObjectId());
                         editor1.commit();
                         finish();
                         startActivity(new Intent(CloudEntry.this,MainActivity.class));
                     }
                    else
                     {
                         beedoo.dismiss();
                         Toast.makeText(CloudEntry.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                     }
                }
            });


        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Could not connect to Google Play Services.  The user needs to select an account,
        // grant permissions or resolve an error in order to sign in. Refer to the javadoc for
        // ConnectionResult to see possible error codes.
        Log.d("Google+", "onConnectionFailed:" + connectionResult);

        if (!mIsResolving && mShouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                    mIsResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    Log.e("Google+10", "Could not resolve ConnectionResult.", e);
                    mIsResolving = false;
                    mGoogleApiClient.connect();
                }
            } else {
                // Could not resolve the connection result, show the user an
                // error dialog.
                AlertDialog.Builder alert=new AlertDialog.Builder(CloudEntry.this);
                alert.setTitle("No network");
                alert.setMessage("Please check your internet connection");
                alert.setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.create();
                alert.show();
            }
        } else {
            AlertDialog.Builder alert=new AlertDialog.Builder(CloudEntry.this);
            alert.setTitle("Signing out");
            alert.setMessage("Logging out of account");
            alert.setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert.create();
            alert.show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Google+1", "onActivityResult:" + requestCode + ":" + resultCode + ":" + data);

        if (requestCode == RC_SIGN_IN) {
            // If the error resolution was not successful we should not resolve further.
            if (resultCode != RESULT_OK) {
                mShouldResolve = false;
            }

            mIsResolving = false;
            mGoogleApiClient.connect();
        }
    }


}
