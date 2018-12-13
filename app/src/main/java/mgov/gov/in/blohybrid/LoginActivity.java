package mgov.gov.in.blohybrid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import mgov.gov.in.blohybrid.Constants;


public class LoginActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    int countBLOcredentials;
    ArrayList<String> serialArray ;
    private TextView tvchangePassword,tverror,tverrorid,tvverion;
    private EditText etuserid,etpassword,etacno,etpartno;
    private Button btnlogin;
    ProgressDialog pd;
    RequestQueue queue;
    SharedPreferences prefs;
    String url,mob,pass,versionName,datamob,datapass,dataIsDisabled,curserdata,IS_AUTH,IP_HEADER;
    int versionCode;
    SharedPreferences sharedPref;
    public static final String MyPREFERENCES = "MyPrefs" ;
    String[] st_code = {"Select State Code","S01","S02","S03","S04","S05","S06","S07","S08","S09","S10","S11","S12","S13","S14","S15","S16","S17","S18","S19","S20","S21","S22","S23","S24","S25","S26","S27","S28","S29","U01","U02","U03","U04","U05","U06","U07"};
    String st_codespinner;
    JSONObject jsonBody = new JSONObject();
    Map<String, String> keyVal = new LinkedHashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        tvverion = (TextView) findViewById(R.id.tvverion);
        prefs = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        IS_AUTH  = prefs.getString("IS_AUTH","");
        Log.d("IS_AUTH",IS_AUTH);
        IP_HEADER= Constants.IP_HEADER;
        myDb = new DatabaseHelper(this);

        final String packageName = getApplicationContext().getPackageName();
        try {
            versionName = getPackageManager().getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        try {
            versionCode = getPackageManager().getPackageInfo(packageName, 0).versionCode;
            Log.d("versionCode", String.valueOf(versionCode));
            tvverion.setText(String.valueOf(versionCode));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if(this.hasConnection()) {
            Log.e("Response", "inside func");
        // CheckUpdate().execute(new
           // String[] {"https://apps.mgov.gov.in/appupdate/appversionupdate"});
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://apps.mgov.gov.in/appupdate/appversionupdate";
            //line 104 to 162 uncomment for updation
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String result) {
// response
                            Log.e("Response", result);
                            if(result != null) {
                                if(result.contains("id=")) {
                                    System.out.println("Update available");

                                    PendingIntent pi = PendingIntent.getActivity(LoginActivity.this, 0,new
                                            Intent(Intent.ACTION_VIEW, Uri.parse("https://apps.mgov.gov.in/descp.do?app" +
                                            result)), 0);
                                    Resources r = getResources();
                                    Notification notification = new NotificationCompat.Builder(LoginActivity.this)
                                            .setTicker("New Version Available")
                                            .setSmallIcon(R.mipmap.ic_launcher)
                                            .setColor(Color.parseColor("#34495e"))
                                            .setContentTitle("New Version Available")
                                            .setContentText("Download our new version of HYBRID BLO app")
                                            .setContentIntent(pi)
                                            .setAutoCancel(true)
                                            .build();

                                    NotificationManager notificationManager = (NotificationManager)
                                            getSystemService(NOTIFICATION_SERVICE);
                                    notificationManager.notify(0, notification);
                                } else {
                                    System.out.println("No updates found");
                                }
                            } else {
                                System.out.println("No data");
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("packagename", packageName);
                    params.put("appversion", versionName);
                    params.put("versionCode", ""+versionCode);

                    return params;
                }
            };
            queue.add(postRequest);
        } else {
            Toast.makeText(getApplicationContext(),"Internet Not Available",Toast.LENGTH_SHORT).show();
        }




/*
        String packageName = getApplicationContext().getPackageName();
        String versionName = null;
        try {
            versionName = getPackageManager().getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        int versionCode = 0;
        try {
            versionCode = getPackageManager().getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(LoginActivity.this, CheckUpdateService.class);
        intent.putExtra("packageName", packageName);
        intent.putExtra("versionName", versionName);
        intent.putExtra("versionCode", versionCode);
        intent.putExtra("notificationIcon", R.mipmap.ic_launcher);
        startService(intent);*/



        pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getApplicationContext().getResources().getString(R.string.Please_Wait));
        pd.setCancelable(false);

        final Spinner spinSC = (Spinner) findViewById(R.id.spinnerSC);
        tvchangePassword=(TextView)findViewById(R.id.tvchangepassword);
        tverror= (TextView) findViewById(R.id.tverrorpassword);
        tverrorid= (TextView) findViewById(R.id.tverrorid);

        etuserid= (EditText) findViewById(R.id.etuserid);
        etpassword= (EditText) findViewById(R.id.etpassword);
        etpartno = (EditText) findViewById(R.id.etpartno);
        etacno = (EditText) findViewById(R.id.etacno);
        btnlogin= (Button) findViewById(R.id.login_btn);

        if(!tvverion.getText().toString().isEmpty()){
            getterSetter.gs.setVERSION_CODE(tvverion.getText().toString());
        }



        ArrayAdapter<String> sourceofSCAdapter = new ArrayAdapter<>(LoginActivity.this, android.R.layout.simple_list_item_checked, st_code);
        spinSC.setAdapter(sourceofSCAdapter);

        spinSC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                st_codespinner = (String) spinSC.getItemAtPosition(position);
                Log.d("date", st_codespinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        queue = Volley.newRequestQueue(this);




        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countBLOcredentials = myDb.count("BLO_CREDENTIALS");
                Log.d("countBLOcredentials", String.valueOf(countBLOcredentials));
                if(countBLOcredentials==0){
                    if(ConnectionStatus.isNetworkConnected(LoginActivity.this)) {
                        tverror.setText("");
                        login();
                    } else {
                        Intent intent=new Intent(getApplicationContext(),NoNetwork.class);
                        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                } else if(IS_AUTH=="false" || IS_AUTH.equals("false")){
                    login();
                }
                 else {
                    if (!validuserID()) {
                        return;
                    }
                    if (!validpassword()) {
                        return;
                    }
                    if(!validState()){
                        return;
                    }
                    if(!validac()){
                        return;
                    }
                    if(!validpart()){
                        return;
                    }
                    JSONArray jsonArray = myDb.selectBLOCredentials(etuserid.getText().toString().trim());
                    Log.d("jsonArray",jsonArray.toString());
                    try {
                        //JSONArray jArray = new JSONArray(jsonArray);
                        Log.v("response",jsonArray.toString());
                        Log.v("jArray.length()", String.valueOf(jsonArray.length()));
                        serialArray = new ArrayList<String>();

                            JSONObject jsreceipt = jsonArray.getJSONObject(0);


                            String AcNo = jsreceipt.getString("AcNo");
                            String PartNo = jsreceipt.getString("PartNo");
                            String BLOMobileNo = jsreceipt.getString("BLOMobileNo");
                            String StateCode = jsreceipt.getString("StateCode");
                            String Password = jsreceipt.getString("Password");
                            String IsDisabledFlag = jsreceipt.getString("IsDisabledFlag");
                            String BLOName = jsreceipt.getString("BLOName");
                            String key = jsreceipt.getString("Key");
                        if(IsDisabledFlag.equals("true") || IsDisabledFlag=="true"){
                            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.User_is_deactivated),Toast.LENGTH_LONG).show();
                            etuserid.setText("");
                            etpassword.setText("");
                        }else if((etuserid.getText().toString().equals(BLOMobileNo) || etuserid.getText().toString() == BLOMobileNo) &&
                                (etpassword.getText().toString().equals(Password) || etpassword.getText().toString() == Password) &&
                                (IsDisabledFlag.equals("false") || IsDisabledFlag=="false") && (st_codespinner.equals(StateCode) || st_codespinner==StateCode))
                        {
                            SharedPreferences.Editor editor = sharedPref.edit();
                            //ditor.putString("BLO_ID", BLO_ID);
                            editor.putString("AcNo",AcNo);
                            editor.putString("PartNo",PartNo);
                            editor.putString("BLO_NAME",BLOName);
                            editor.putString("mob",BLOMobileNo);
                            editor.putString("st_code",StateCode);
                            editor.putString("Key",key);
                            editor.commit();

                            Intent intent=new Intent(LoginActivity.this,WelcomeBLONew.class);
                            startActivity(intent);
                            finish();
                        } else if((etuserid.getText().toString() != BLOMobileNo) ||
                                (etpassword.getText().toString() != Password) &&
                                        (IsDisabledFlag.equals("false") || IsDisabledFlag=="false"))
                        {
                            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.User_ID_or_Password_is_Incorrect),Toast.LENGTH_LONG).show();
                            etuserid.setText("");
                            etpassword.setText("");
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("printStackTrace",e.getMessage());
                        Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Something_Went_Wrong),Toast.LENGTH_LONG).show();
                        /*Intent intent=new Intent(LoginActivity.this,WelcomeBLO.class);
                        startActivity(intent);
                        finish();*/
                    }
                    finally {
                        hideProgressDialog();
                    }


                    }
               /* if(ConnectionStatus.isNetworkConnected(LoginActivity.this))
                {
                    tverror.setText("");
                    login();
                    *//*countBLOcredentials = myDb.count("BLO_CREDENTIALS");
                    Log.d("countBLOcredentials", String.valueOf(countBLOcredentials));
                    if(countBLOcredentials == 0){
                        login();
                    }
                    if(countBLOcredentials !=0){
                        Cursor cursor = myDb.selectBLOCredentials(mob);
                        int totalColumn = cursor.getColumnCount();
                        int cursorcount = cursor.getCount();
                        Log.d("cursorcount", String.valueOf(cursorcount));
                        Log.d("totalColumn", String.valueOf(totalColumn));

                        for( int i=0 ;  i< totalColumn ; i++ )
                        {
                            if( cursor.getColumnName(i) != null )
                            {
                                Log.d("cursor.getColumnName1(i)",cursor.getColumnName(i));
                                curserdata = cursor.getColumnName(i);
                                Log.d("curserdata",curserdata);

                                try
                                {
                                    switch (curserdata){
                                        case "Password":{
                                            if( cursor.getString(i) != null )
                                            {
                                                datapass = cursor.getString(i);
                                                Log.d("datapass", datapass );

                                            }

                                        }
                                        case "BLOMobileNo" : {
                                            if( cursor.getString(i) != null )
                                            {
                                                datamob = cursor.getString(i);
                                                Log.d("datamob", datamob );

                                            }

                                        }
                                        case "IsDisabledFlag" : {
                                            if( cursor.getString(i) != null )
                                            {
                                                dataIsDisabled = cursor.getString(i);
                                                Log.d("dataIsDisabled",dataIsDisabled );

                                            }

                                        }
                                    }

                                }
                                catch( Exception e )
                                {
                                    Log.d("TAG_NAME", e.getMessage()  );
                                }
                            }
                        }
                        cursor.close();
                        if(dataIsDisabled.equals("true") || dataIsDisabled=="true"){
                            Toast.makeText(getApplicationContext(),"User is deactivated",Toast.LENGTH_LONG).show();
                            etuserid.setText("");
                            etpassword.setText("");
                        }else if((etuserid.getText().toString().equals(datamob) || etuserid.getText().toString() == datamob) &&
                                (etpassword.getText().toString().equals(datapass) || etpassword.getText().toString() == datapass) &&
                                (dataIsDisabled.equals("false") || dataIsDisabled=="false"))
                        {
                            Intent intent=new Intent(LoginActivity.this,WelcomeBLONew.class);
                            startActivity(intent);
                            finish();
                        } else if((etuserid.getText().toString() != datamob) ||
                                (etpassword.getText().toString() != datapass) &&
                                        (dataIsDisabled.equals("false") || dataIsDisabled=="false"))
                        {
                            Toast.makeText(getApplicationContext(),"User ID or Password is Incorrect",Toast.LENGTH_LONG).show();
                            etuserid.setText("");
                            etpassword.setText("");
                        }
                    }*//*

                }
                else
                {
                    *//*countBLOcredentials = myDb.count("BLO_CREDENTIALS");
                    Log.d("countBLOcredentials", String.valueOf(countBLOcredentials));
                    if(countBLOcredentials !=0){
                        Cursor cursor = myDb.selectBLOCredentials(mob);
                        int totalColumn = cursor.getColumnCount();
                        Log.d("totalColumnoff", String.valueOf(totalColumn));
                        for( int i=0 ;  i< totalColumn ; i++ )
                        {
                            if( cursor.getColumnName(i) != null )
                            {
                                Log.d("cursoroff.getColumnName(i)", cursor.getColumnName(i));
                                try
                                {
                                    switch (cursor.getString(i)){
                                        case "Password":{
                                            if( cursor.getString(i) != null )
                                            {
                                                datapass = cursor.getString(i);
                                                Log.d("datapassoff", datapass );

                                            }

                                        }
                                        case "BLOMobileNo" : {
                                            if( cursor.getString(i) != null )
                                            {
                                                datamob = cursor.getString(i);
                                                Log.d("datamoboff", datamob );

                                            }

                                        }
                                        case "IsDisabledFlag" : {
                                            if( cursor.getString(i) != null )
                                            {
                                                dataIsDisabled = cursor.getString(i);
                                                Log.d("dataIsDisabledoff", dataIsDisabled );

                                            }

                                        }
                                    }

                                }
                                catch( Exception e )
                                {
                                    Log.d("TAG_NAME", e.getMessage()  );
                                }
                            }
                        }
                        cursor.close();
                        if(dataIsDisabled.equals("true") || dataIsDisabled=="true"){
                            Toast.makeText(getApplicationContext(),"User is deactivated",Toast.LENGTH_LONG).show();
                            etuserid.setText("");
                            etpassword.setText("");
                        }else if((etuserid.getText().toString().equals(datamob) || etuserid.getText().toString() == datamob) &&
                                (etpassword.getText().toString().equals(datapass) || etpassword.getText().toString() == datapass) &&
                                (dataIsDisabled.equals("false") || dataIsDisabled=="false"))
                        {
                            Intent intent=new Intent(LoginActivity.this,WelcomeBLONew.class);
                            startActivity(intent);
                            finish();
                        } else if((etuserid.getText().toString() != datamob) ||
                                (etpassword.getText().toString() != datapass) &&
                                (dataIsDisabled.equals("false") || dataIsDisabled=="false"))
                        {
                            Toast.makeText(getApplicationContext(),"User ID or Password is Incorrect",Toast.LENGTH_LONG).show();
                            etuserid.setText("");
                            etpassword.setText("");
                        }
                    }  else*//*  {
                        Intent intent=new Intent(getApplicationContext(),NoNetwork.class);
                        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                }
*/

            }
        });

        tvchangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ChangePassword.class);
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        backButtonHandler();
    }
    private void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                LoginActivity.this);
        // Setting Dialog Title
        alertDialog.setTitle(getApplicationContext().getResources().getString(R.string.BLO_Register));
        // Setting Dialog Message
        alertDialog.setMessage(getApplicationContext().getResources().getString(R.string.Do_you_want_to_EXIT_from_this_App));
        // Setting Icon to Dialog
        alertDialog.setIcon(R.mipmap.ic_launcher);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton(getApplicationContext().getResources().getString(R.string.YES),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);


                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton(getApplicationContext().getResources().getString(R.string.NO),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }

     private void login() {

         if (!validuserID()) {
             return;
         }
         if (!validpassword()) {
             return;
         }
         if(!validState()){
             return;
         }
         if(!validac()){
             return;
         }
         if(!validpart()){
             return;
         }

         //showProgressDialog();
         mob=etuserid.getText().toString().trim();
         pass=etpassword.getText().toString();
         //url ="http://eronet.ecinet.in/services/api/blonet/Login?st_code=S13&UserId=8169706174&password=12345&ac_no=1&part_no=1";
         //main//url ="http://eronet.ecinet.in/services/api/blonet/Login?st_code="+st_codespinner+"&UserId="+mob+"&password="+pass;
         url= IP_HEADER+"Login?st_code="+st_codespinner+"&ac_no="+etacno.getText().toString()+"&part_no="+etpartno.getText().toString();
         keyVal.put("USER_ID",mob);
         keyVal.put("PASSWORD",pass);
         try {
             jsonBody.put("USER_ID",mob);
             jsonBody.put("PASSWORD",pass);

         } catch (JSONException e) {
             e.printStackTrace();
         }

         Log.e("json formed",jsonBody.toString());
         Log.d("loginurl",url);
         SharedPreferences.Editor editor = sharedPref.edit();
         editor.putString("mob", mob);
         editor.putString("st_code",st_codespinner);
         editor.putString("AcNo",etacno.getText().toString());
         editor.putString("PartNo",etpartno.getText().toString());
         editor.commit();

         vollyRequestForPost(url, keyVal);

        /*moh showProgressDialog();
         JSONObject obj = new JSONObject();
         Helper.getmHelper().v("OBJ",obj.toString());
         JsonObjectRequest jsObjRequest = new JsonObjectRequest
                 (Request.Method.GET, url, obj, new Response.Listener<JSONObject>() {

                     @Override
                     public void onResponse(JSONObject response) {
                         Log.d("On Response : ", response.toString());
                         try {
                             String IsSuccess = response.getString("IsSuccess");
                             String Message = response.getString("Message");
                             String IsDisable = response.getString("IsDisable");
                             Log.e("IsSuccess",IsSuccess);
                             Log.e("Message",Message);
                             Log.e("IsDisable",IsDisable);

                             Log.e("Response : ", "IsSuccess : " + IsSuccess);

                             if(IsSuccess.trim().equals("true") && IsDisable.trim().equals("false")) {
                                 Intent intent=new Intent(LoginActivity.this,WelcomeBLONew.class);
                                 Bundle mBundle = new Bundle();
                                 mBundle.putString("Key", Message);
                                 intent.putExtras(mBundle);
                                 startActivity(intent);
                                 finish();


                             } else if(IsDisable.trim().equals("true")) {
                                 Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.User_is_deactivated),Toast.LENGTH_LONG).show();
                                 etuserid.setText("");
                                 etpassword.setText("");
                             }
                             else
                                 Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.No_Response_from_Server_Enter_correct_User_ID_and_Password), Toast.LENGTH_LONG).show();



                         } catch (JSONException e) {
                             e.printStackTrace();
                             Log.d("printStackTrace",e.getMessage());
                         }
                         hideProgressDialog();

                     }
                 }, new Response.ErrorListener() {

                     @Override
                     public void onErrorResponse(VolleyError error) {
                         // TODO Auto-generated method stub

                     }
                 });

// Access the RequestQueue through your singleton class.
         queue.add(jsObjRequest);
moh*/
         /*Intent intent=new Intent(LoginActivity.this,WelcomeBLO.class);
         startActivity(intent);
         finish();*/

     }

    private boolean validState() {
        if(st_codespinner.toString().equals("Select State Code")){
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Select_state_Code), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean validac() {
        if(etacno.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.enterAcNo), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean validpart() {
        if(etpartno.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.enterPArtNO), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean validpassword() {
        tverror.setText("");
        tverrorid.setText("");
        tverror.setVisibility(View.GONE);
        if(etpassword.getText().toString().trim().isEmpty())
        {
            tverror.setText(getApplicationContext().getResources().getString(R.string.Please_Enter_Password));
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.ERROR_Please_Enter_Password),Toast.LENGTH_SHORT).show();
            tverror.setVisibility(View.VISIBLE);
            return false;
        }else  if((etpassword.getText().toString().length()<3)||(etpassword.getText().toString().length()>50))
        {
            tverror.setText(getApplicationContext().getResources().getString(R.string.Password_must_be_Min6_and_Max500_Characters));
            Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.ERROR_Password_must_be_Min6and_Max500_Characters),Toast.LENGTH_SHORT).show();
            tverror.setVisibility(View.VISIBLE);
            return false;
        }
        tverror.setText("");
        tverror.setVisibility(View.GONE);
        return true;
    }

    private boolean validuserID() {
        tverrorid.setText("");
        tverror.setText("");
        tverrorid.setVisibility(View.GONE);
        {
            if(etuserid.getText().toString().trim().isEmpty())
            {
                tverrorid.setText(getApplicationContext().getResources().getString(R.string.Enter_User_ID));
                Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Enter_User_ID),Toast.LENGTH_SHORT).show();
                tverrorid.setVisibility(View.VISIBLE);
                return false;
            }else if (!(etuserid.getText().toString().length()==10)) {
                tverrorid.setText(getApplicationContext().getResources().getString(R.string.User_ID_must_be_of_10_Digits));
                Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.ERROR_User_ID_must_be_of_10_Digits),Toast.LENGTH_SHORT).show();
                tverrorid.setVisibility(View.VISIBLE);
                return false;
            }
            tverrorid.setText("");
            tverrorid.setVisibility(View.GONE);
            return true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showProgressDialog() {
        if (!pd.isShowing())
            pd.show();
    }

    private void hideProgressDialog() {
        if (pd.isShowing())
            pd.dismiss();
    }

    public boolean hasConnection() {
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = cm.getNetworkInfo(1);
        if(wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        } else {
            NetworkInfo mobileNetwork = cm.getNetworkInfo(0);
            if(mobileNetwork != null && mobileNetwork.isConnected()) {
                return true;
            } else {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                return activeNetwork != null && activeNetwork.isConnected();
            }
        }
    }
    public void vollyRequestForPost(String URL, Map<String, String> keyVal) {
        Log.e("URL",URL);
        showProgressDialog();
        JSONObject obj = new JSONObject();
        Helper.getmHelper().v("OBJ",obj.toString());
        //JSONObject obj = new JSONObject(keyVal);
        showProgressDialog();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, URL, obj, new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("On Response : ", response.toString());
                        try {
                            String IsAuthenticated = response.getString("IsAuthenticated");
                            String Message = response.getString("Message");
                            String IsDisable = response.getString("IsDisable");
                            String ACCESS_TOKEN = response.getString("ACCESS_TOKEN");
                            Log.e("IsAuthenticated",IsAuthenticated);
                            Log.e("Message",Message);
                            Log.e("IsDisable",IsDisable);
                            Log.e("ACCESS_TOKEN",ACCESS_TOKEN);

                            Log.e("Response : ", "IsAuthenticated : " + IsAuthenticated);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("Key", ACCESS_TOKEN);
                            editor.commit();

                            if(IsAuthenticated.trim().equals("true") && IsDisable.trim().equals("false")) {
                                Intent intent=new Intent(LoginActivity.this,WelcomeBLONew.class);
                                startActivity(intent);
                                finish();


                            } else if(IsDisable.trim().equals("true")) {
                                Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.User_is_deactivated),Toast.LENGTH_LONG).show();
                                etuserid.setText("");
                                etpassword.setText("");
                            }
                            else
                                Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.No_Response_from_Server_Enter_correct_User_ID_and_Password), Toast.LENGTH_LONG).show();



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("printStackTrace",e.getMessage());
                        }
                        hideProgressDialog();

                    }
                },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Log.d("Error.Response", error.toString());

                            }
                        }
                ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }


            @Override
            public byte[] getBody(){
                try {
                    return jsonBody.toString() == null ? null : jsonBody.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", jsonBody.toString(), "utf-8");
                    return null;
                }
            }



            /*@Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                //params.put("RoleId", ROLE_ID);
                params.put("TotalVoters_Population_in_BLOArea",etoption1.getText().toString());
                params.put("TotalVerificationDone",etoption5.getText().toString());
                params.put("NewFormCollectedOrSubmited",etoption6.getText().toString());
                params.put("FamilyDetailsCollected",etoption7.getText().toString());
                params.put("DeadOrShiftedElector",etoption8.getText().toString());
                params.put("FutureVoter",etoption9.getText().toString());
                params.put("MaleVoters",etoption2.getText().toString());
                params.put("FemaleVoters",etoption3.getText().toString());
                params.put("ThirdGender",etoption4.getText().toString());

                Log.d("param option156789234",etoption1.getText().toString()+" "+etoption5.getText().toString()+" "+etoption6.getText().toString()
                        +" "+etoption7.getText().toString()+" "+etoption8.getText().toString()+" "+etoption9.getText().toString()
                        +" "+etoption2.getText().toString()+" "+etoption3.getText().toString()+" "+etoption4.getText().toString());

                return params;
            }*/
        };
        jsObjRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        queue.add(jsObjRequest);
    }

}
