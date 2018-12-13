package mgov.gov.in.blohybrid;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import mgov.gov.in.blohybrid.Constants;

public class WelcomeBLONew extends AppCompatActivity {
    DatabaseHelper myDb;
    ImageView ivnotification;
    TextView option11adjacent,tvssr2018,tvlogout,tvelectorlist,tvstateid,tvacid,tvpartid,tvbloname,tvspecialdrive,tvverion,tvonlineformfilling,tvbloregister,tvchangepassword;
    ProgressDialog pd;
    int count2;
    RequestQueue queue;
    String ac,part,IS_AUTH,buttonClicked,IP_HEADER;
    String url,url1,url2,url3,mob,st_code,sac,spart,sbloname,sversion,dataac,datapart,dataname,acno,partno,bloname,StringAC,StringPart,SharedKey;
    int count = 0;
    int countBLOcredentials;
    SharedPreferences prefs;
    Map<String, String> params = new LinkedHashMap<String, String>();
    //String urlToDownload = "http://117.239.180.198/BLONet_API_Test/Static/ERollMatrix.pdf";
    public static final String MyPREFERENCES = "MyPrefs" ;
    JSONObject jsonBody = new JSONObject();
    Map<String, String> keyVal = new LinkedHashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_blonew);

        tvspecialdrive = (TextView) findViewById(R.id.option3);
        tvssr2018= (TextView) findViewById(R.id.option2);
        tvlogout = (TextView) findViewById(R.id.logout);
        tvelectorlist = (TextView) findViewById(R.id.option1);
        tvstateid = (TextView) findViewById(R.id.tvstateid);
        tvacid = (TextView) findViewById(R.id.tvacid);
        tvpartid = (TextView) findViewById(R.id.tvpartid);
        tvbloname = (TextView) findViewById(R.id.textView);
        tvverion = (TextView) findViewById(R.id.tvverion);
        tvonlineformfilling = (TextView) findViewById(R.id.option4);
        tvbloregister = (TextView) findViewById(R.id.option5);
        tvchangepassword = (TextView) findViewById(R.id.tvchangepassword);
        option11adjacent = (TextView) findViewById(R.id.option11);
        ivnotification = (ImageView) findViewById(R.id.ivnotification);

        myDb = new DatabaseHelper(this);

        sversion = getterSetter.gs.getVERSION_CODE();
        //Log.d("sversion",sversion);
        tvverion.setText("Version: "+sversion);

        prefs = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        IS_AUTH  = prefs.getString("IS_AUTH","");
        Log.d("IS_AUTH",IS_AUTH);
        mob  = prefs.getString("mob","");
        st_code =prefs.getString("st_code","");
        ac =prefs.getString("AcNo","");
        part =prefs.getString("PartNo","");
        SharedKey = prefs.getString("Key","");
        Log.d("SharedKey",SharedKey);
        Log.d("mobedf",mob);
        Log.d("st_code",st_code);
        IP_HEADER = Constants.IP_HEADER;

        url3 = IP_HEADER+"IsAccessTokenExpired?st_code="+st_code+"&ac_no="+ac+"&part_no="+part;

        pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getApplicationContext().getResources().getString(R.string.Please_Wait));
        pd.setCancelable(false);
        queue = Volley.newRequestQueue(this);

        tvssr2018.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int count1=0;
                count1 = myDb.count("Voter_List_Table");
                if(count1>0){
                    Intent intent=new Intent(WelcomeBLONew.this,RevisionActivity.class);
                    startActivity(intent);
                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Local_Server_is_not_created_Click_on_download_elector_list_first),Toast.LENGTH_LONG).show();


            }
        });

        ivnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count1=0;
                count1 = myDb.count("Voter_List_Table");
                if(count1>0){
                    if(ConnectionStatus.isNetworkConnected(WelcomeBLONew.this)){
                        Intent intent=new Intent(WelcomeBLONew.this,NotificationActivity.class);
                        startActivity(intent);
                    }else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_Internet_connection),Toast.LENGTH_SHORT).show();

                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Local_Server_is_not_created_Click_on_download_elector_list_first),Toast.LENGTH_LONG).show();
            }
        });

        option11adjacent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyVal.put("ST_CODE",st_code);
                keyVal.put("AC_NO",acno);
                keyVal.put("PART_NO",partno);
                try {
                    jsonBody.put("ST_CODE",st_code);
                    jsonBody.put("AC_NO",acno);
                    jsonBody.put("PART_NO",partno);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("json formed",jsonBody.toString());
                vollyRequestForPostAuth(url3, keyVal);
                buttonClicked = "option11adjacent";


            }
        });

        tvchangepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WelcomeBLONew.this,ChangePassword.class);
                startActivity(intent);
            }
        });

        tvbloregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                keyVal.put("ST_CODE",st_code);
                keyVal.put("AC_NO",acno);
                keyVal.put("PART_NO",partno);
                try {
                    jsonBody.put("ST_CODE",st_code);
                    jsonBody.put("AC_NO",acno);
                    jsonBody.put("PART_NO",partno);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("json formed",jsonBody.toString());
                vollyRequestForPostAuth(url3, keyVal);
                buttonClicked = "tvbloregister";



            }
        });

        countBLOcredentials = myDb.count("BLO_CREDENTIALS");
        Log.d("countBLOcredentials", String.valueOf(countBLOcredentials));
        if(countBLOcredentials==0 || IS_AUTH=="false" || IS_AUTH.equals("false")){
            if(ConnectionStatus.isNetworkConnected(WelcomeBLONew.this)){
                Log.d("mob123",mob);
                url = IP_HEADER+"GetBloDetails?st_code="+st_code+"&mobile_no="+mob+"&ac_no="+ac+"&part_no="+part;
                //main//url ="http://eronet.ecinet.in/services/api/blonet/GetBloDetails?st_code="+st_code+"&mobile_no="+mob;
               // url = "http://117.239.180.198/BLONet_API_Test/api/blonet/GetBloDetails?st_code="+st_code+"&mobile_no="+mob+"&ac_no="+ac+"&part_no="+part;
                //url1="http://eronet.ecinet.in/services/api/blonet/SearchElectoralBulk?st_code=S02&ac_no=1&part_no=20";
                Log.e("url",url);

                //showProgressDialog();
                new RequestTask3().execute(url);
                /*JSONObject obj = new JSONObject();
                Helper.getmHelper().v("OBJ",obj.toString());


                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.GET, url, obj, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("On Response : ", response.toString());
                                try {
                                    String IsSuccess = response.getString("IsSuccess");
                                    Log.d("IsSuccess",IsSuccess);
                                    String StateCode = response.getString("StateCode");
                                    //Log.v("Response : ", "IsSuccess : " + IsSuccess);

                                    if(IsSuccess.trim().equals("true") || IsSuccess.trim().equals("true")) {
                                        //String StateCode = response.getString("StateCode");
                                        Log.d("loop","loop");
                                        String AcNo = response.getString("AcNo");
                                        *//*StringAC = response.getString("AcNo");
                                        StringPart= response.getString("PartNo");*//*
                                        String PartNo = response.getString("PartNo");
                                        String BLOName = response.getString("BLOName");
                                        String BLO_ID = response.getString("BLO_ID");
                                        String Password = response.getString("Password");
                                        //Log.e("StringAC StringPart",StringAC + " " + StringPart);
                                        //String Password = response.getString("StateCode");
                                        boolean isInserted = myDb.insertBLOCredentials(Integer.parseInt(AcNo.toString()),Integer.parseInt(PartNo.toString()),BLOName.toString(),
                                                mob.toString(),StateCode.toString(),Password,"false",SharedKey);

                                        tvstateid.setText("State "+StateCode);
                                        tvacid.setText("Ac "+AcNo);
                                        tvpartid.setText("Part "+PartNo);
                                        tvbloname.setText("Welcome "+BLOName);
                                        sac = AcNo.toString();
                                        spart = PartNo.toString();
                                        Log.e("sac",sac);
                                        Log.e("spart",spart);

                                        SharedPreferences.Editor editor = prefs.edit();
                                        editor.putString("BLO_ID", BLO_ID);
                                        editor.putString("AcNo",AcNo);
                                        editor.putString("PartNo",PartNo);
                                        editor.putString("BLO_NAME",BLOName);
                                        editor.putString("Key",SharedKey);
                                        //editor.putString("State",StateCode);
                                        editor.commit();




                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                hideProgressDialog();

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub

                            }
                        })
                {
                    *//** Passing some request headers* *//*
                    @Override
                    public Map getHeaders() throws AuthFailureError {
                        HashMap headers = new HashMap();
                        headers.put("Content-Type", "application/json");
                        headers.put("access_token", SharedKey);
                        return headers;
                    }
                };



// Access the RequestQueue through your singleton class.
                queue.add(jsObjRequest);

*/

            } else {
                Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.No_Internet_connection), Toast.LENGTH_LONG).show();
            }

        }
        else if(countBLOcredentials >0)
        {
            Log.d("countBLOcredentials > 0", String.valueOf(countBLOcredentials));
                acno = prefs.getString("AcNo","");
                partno = prefs.getString("PartNo","");
                bloname = prefs.getString("BLO_NAME","");

                tvstateid.setText("State "+st_code);
                tvacid.setText("Ac "+acno);
                tvpartid.setText("Part "+partno);
                tvbloname.setText("Welcome "+bloname);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("IS_AUTH","true");
            editor.commit();

        }
       /* if(ConnectionStatus.isNetworkConnected(WelcomeBLONew.this))
        {*/



            /*countBLOcredentials = myDb.count("BLO_CREDENTIALS");
            Log.d("countBLOcredentials", String.valueOf(countBLOcredentials));

            if(countBLOcredentials >0)
            {

                Cursor cursor = myDb.selectBLOCredentials(mob);
                int totalColumn = cursor.getColumnCount();
                Log.d("totalColumn", String.valueOf(totalColumn));
                for( int i=0 ;  i< totalColumn ; i++ )
                {
                    if( cursor.getColumnName(i) != null )
                    Log.d("cursor.getColumnName(i)",cursor.getColumnName(i));
                    if(cursor.getColumnName(i)=="BLOMobileNo"){

                    }
                    {
                        try
                        {
                            switch (cursor.getColumnName(i)){
                                case "AcNo":{
                                    if( cursor.getString(i) != null )
                                    {
                                        dataac = cursor.getString(i);
                                        Log.d("AcNo", cursor.getString(i) );
                                        tvacid.setText("Ac "+dataac);
                                    }

                                }
                                case "PartNo" : {
                                    if( cursor.getString(i) != null )
                                    {
                                        datapart = cursor.getString(i);
                                        Log.d("PartNo", cursor.getString(i) );
                                        tvpartid.setText("Part "+datapart);
                                    }

                                }
                                case "BLOName" : {
                                    if( cursor.getString(i) != null )
                                    {
                                        dataname = cursor.getString(i);
                                        Log.d("BLOName", cursor.getString(i) );
                                        tvbloname.setText("Welcome "+dataname);
                                    }

                                }
                                case "StateCode" : {
                                    if( cursor.getString(i) != null )
                                    {
                                        Log.d("StateCode", cursor.getString(i) );
                                        tvstateid.setText("State "+cursor.getString(i));
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
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("AcNo",dataac);
                editor.putString("PartNo",datapart);
                editor.putString("BLO_NAME",dataname);
                editor.commit();


            } else if(countBLOcredentials==0){
                Toast.makeText(getApplicationContext(), "No Internet Connection ", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
*/
            /*Intent intent=new Intent(getApplicationContext(),NoNetwork.class);
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);*/
      //  }

        count2 = myDb.count("Voter_List_Table");
        tvelectorlist.setVisibility(View.VISIBLE);
        if(count2 >0){
            //Toast.makeText(getApplicationContext(),"Locale database all ready created",Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("count", String.valueOf(count2));
            editor.commit();
            Log.d("shared count2", String.valueOf(count2));
            //tvelectorlist.setClickable(false);
            tvelectorlist.setBackgroundResource(R.drawable.btn_background_disable);
            //tvelectorlist.setVisibility(View.GONE);
        }

        ActivityCompat.requestPermissions(WelcomeBLONew.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},123);
        tvelectorlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Under Development",Toast.LENGTH_LONG).show();
                if(count2 >0){
                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Locale_database_all_ready_created),Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("count", String.valueOf(count2));
                    editor.commit();
                    Log.d("shared count2", String.valueOf(count2));
                    //tvelectorlist.setClickable(false);
                    //tvelectorlist.setVisibility(View.GONE);
                } else if(ConnectionStatus.isNetworkConnected(getApplicationContext())) {
                    //PrintApplicationFormMethod(); //original to download pdf file
                    //CreateLocalDataBaseMethod();
                    StringAC  = prefs.getString("AcNo","");
                    StringPart =prefs.getString("PartNo","");
                    Log.d("StringAC",StringAC);
                    Log.d("StringPart",StringPart);
                    showProgressDialog();
                    //main//url1="http://eronet.ecinet.in/services/api/blonet/SearchElectoralBulk?st_code="+st_code+"&ac_no="+StringAC+"&part_no="+StringPart;
                   url1 = IP_HEADER+"SearchElectoralBulk?st_code="+st_code+"&ac_no="+StringAC+"&part_no="+StringPart;
                    Log.e("url1",url1);
                    new RequestTask1().execute(url1);
                    //Toast.makeText(getApplicationContext(),"local database created",Toast.LENGTH_SHORT).show();
                    /*String url = "http://117.239.180.198/BLONet_API_Dev/api/blo/GetElectoralList?st_code="+st_code+"&ac_no="+sac+"&part_no="+spart;
                    Log.d("pdf",url);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);*/



                    //downloadPdfContent(urlToDownload);
                }
                else
                {
                    Intent intent=new Intent(getApplicationContext(),NoNetwork.class);
                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });

        tvlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WelcomeBLONew.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvonlineformfilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count1=0;
                count1 = myDb.count("Voter_List_Table");
                if(count1>0){
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("com.ecip.msdp.ecitest1",
                            "com.ecip.msdp.ecitest1.Home"));
                    PackageManager manager = getApplicationContext().getPackageManager();
                    List<ResolveInfo> infos = manager.queryIntentActivities(intent, 0);
                    if (infos.size() > 0) {
                        startActivity(intent);
                        //Then there is an Application(s) can handle your intent
                    } else {
                        //Toast.makeText(getApplicationContext(),"Please install voter list application",Toast.LENGTH_SHORT).show(); //No Application can handle your intent
                        final Dialog dialog = new Dialog(WelcomeBLONew.this);
                        dialog.setContentView(R.layout.custom_voter_service_app_link);
                        Button btnok = (Button) dialog.findViewById(R.id.btnok);
                        Button bcancel = (Button) dialog.findViewById(R.id.bcancel);
                        dialog.setCanceledOnTouchOutside(false);




                        btnok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String url = "https://apps.mgov.gov.in/descp.do?param=0&appid=1457&fb=true";
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                                dialog.dismiss();

                            }
                        });
                        bcancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();

                    }

                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Local_Server_is_not_created_Click_on_download_elector_list_first),Toast.LENGTH_LONG).show();
            }
        });

        tvspecialdrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count1=0;
                count1 = myDb.count("Voter_List_Table");
                if(count1>0){
                    Intent intent=new Intent(WelcomeBLONew.this,WelcomeBLO.class);
                    startActivity(intent);
                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Local_Server_is_not_created_Click_on_download_elector_list_first),Toast.LENGTH_LONG).show();

            }
        });


    }

    class RequestTask1 extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            try {
                HttpGet httpGet = new HttpGet(url1);

                httpGet.addHeader("Content-Type" , "application/json");
                httpGet.addHeader("access_token" , SharedKey);
                Log.e("access_token" , SharedKey);

                response = httpclient.execute(httpGet);
                //response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    responseString = out.toString();
                    out.close();
                } else {
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {

            Log.d("On Response1 : ", result.toString());
            try {
                JSONArray jArray = new JSONArray(result);
                Log.v("response",result.toString());
                Log.v("jArray.length()", String.valueOf(jArray.length()));

                int count1=0;
                count1 = myDb.count("Voter_List_Table");
                Log.d("count using method is ", String.valueOf(count1));

                for(int i=0; i < jArray.length(); i++) {

                    JSONObject jsreceipt = jArray.getJSONObject(i);

                    String RLN_TYPE = jsreceipt.getString("RLN_TYPE");
                    String HOUSE_NO = jsreceipt.getString("HOUSE_NO");
                    String SLNOINPART = jsreceipt.getString("SLNOINPART");
                    String EPIC_NO = jsreceipt.getString("EPIC_NO");
                    String Name = jsreceipt.getString("Name");
                    String ADDRESS = jsreceipt.getString("ADDRESS");
                    String RLN_Name = jsreceipt.getString("RLN_Name");
                    String GENDER = jsreceipt.getString("GENDER");
                    int AGE = jsreceipt.getInt("AGE");

                    if(count1==0){
                        boolean isInserted = myDb.insertData(SLNOINPART.toString(),EPIC_NO.toString(),Name.toString(),
                                RLN_Name.toString(),GENDER.toString(),AGE,ADDRESS.toString(),HOUSE_NO.toString(),RLN_TYPE.toString(),"false");
                        if(isInserted = true){
                            count++;

                        }
                    }

                }
                Log.d("count", String.valueOf(count));

                if(count1!=0){
                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Locale_database_all_ready_created),Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("count", String.valueOf(count1));
                    editor.commit();
                    Log.d("shared count1", String.valueOf(count1));
                } else {
                Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Local_Database_Created),Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Total)+" "+ count + " "+getApplicationContext().getResources().getString(R.string.values_inserted),Toast.LENGTH_SHORT).show();
                    tvelectorlist.setBackgroundResource(R.drawable.btn_background_disable);
                    tvelectorlist.setClickable(false);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("count", String.valueOf(count));
                    editor.commit();
                    Log.d("shared count", String.valueOf(count));
                }




            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("printStackTrace",e.getMessage());
                finish();
            }
            finally {
                hideProgressDialog();
            }

        }
    }

    class RequestTask2 extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            try {
                HttpGet httpGet = new HttpGet(url2);

                httpGet.addHeader("Content-Type" , "application/json");
                httpGet.addHeader("access_token" , SharedKey);
                Log.e("access_token" , SharedKey);

                 response = httpclient.execute(httpGet);
                //response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    responseString = out.toString();
                    out.close();
                } else {
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {

            Log.d("On Response : ", result.toString());
            try {
                JSONObject jsonObject = new JSONObject(result);
                Log.v("response jsonObject",jsonObject.toString());
                String IsSuccess = jsonObject.getString("IsSuccess");
                Log.d("IsSuccess",IsSuccess);
                if(IsSuccess.trim().equals("true")) {
                    final Dialog dialog = new Dialog(WelcomeBLONew.this);
                    dialog.setContentView(R.layout.custom_blo_report_done_message);
                    Button btnok = (Button) dialog.findViewById(R.id.btnok);
                    dialog.setCanceledOnTouchOutside(false);

                    btnok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.dismiss();

                        }
                    });
                    dialog.show();
                }
                else
                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.No_Response_from_Server_Enter_correct_User_ID_and_Password), Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("printStackTrace",e.getMessage());
                Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_Response_from_Server),Toast.LENGTH_LONG).show();
            }
            finally {
                hideProgressDialog();
            }

        }
    }


    class RequestTask3 extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            try {
                HttpGet httpGet = new HttpGet(url);

                httpGet.addHeader("Content-Type" , "application/json");
                httpGet.addHeader("access_token" , SharedKey);
                Log.e("access_token" , SharedKey);

                response = httpclient.execute(httpGet);
                //response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    responseString = out.toString();
                    out.close();
                } else {
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {

            Log.d("On Response : ", result.toString());
            try {
                JSONObject jsonObject = new JSONObject(result);
                Log.v("response jsonObject",jsonObject.toString());
                String IsSuccess = jsonObject.getString("IsSuccess");
                Log.d("IsSuccess",IsSuccess);

                String StateCode = jsonObject.getString("StateCode");
                //Log.v("Response : ", "IsSuccess : " + IsSuccess);

                if(IsSuccess.trim().equals("true") || IsSuccess.trim().equals("true")) {
                    //String StateCode = response.getString("StateCode");
                    Log.d("loop","loop");
                    String AcNo = jsonObject.getString("AcNo");
                                        /*StringAC = response.getString("AcNo");
                                        StringPart= response.getString("PartNo");*/
                    String PartNo = jsonObject.getString("PartNo");
                    String BLOName = jsonObject.getString("BLOName");
                    String BLO_ID = jsonObject.getString("BLO_ID");
                    String Password = jsonObject.getString("Password");
                    //Log.e("StringAC StringPart",StringAC + " " + StringPart);
                    //String Password = response.getString("StateCode");
                    boolean isInserted = myDb.insertBLOCredentials(Integer.parseInt(AcNo.toString()),Integer.parseInt(PartNo.toString()),BLOName.toString(),
                            mob.toString(),StateCode.toString(),Password,"false",SharedKey);

                    tvstateid.setText("State "+StateCode);
                    tvacid.setText("Ac "+AcNo);
                    tvpartid.setText("Part "+PartNo);
                    tvbloname.setText("Welcome "+BLOName);
                    sac = AcNo.toString();
                    spart = PartNo.toString();
                    Log.e("sac",sac);
                    Log.e("spart",spart);

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("BLO_ID", BLO_ID);
                    editor.putString("AcNo",AcNo);
                    editor.putString("PartNo",PartNo);
                    editor.putString("BLO_NAME",BLOName);
                    editor.putString("Key",SharedKey);
                    editor.putString("IS_AUTH","true");
                    //editor.putString("State",StateCode);
                    editor.commit();




                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                hideProgressDialog();
            }

        }
    }

    /*private void CreateLocalDataBaseMethod() {
        url ="http://117.239.180.198/BLONet_API_Dev/api/blonet/SearchElectoralBulk?st_code=U07&ac_no=21&part_no=2";
        showProgressDialog();
        JSONObject obj = new JSONObject();
        Helper.getmHelper().v("OBJ",obj.toString());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, obj, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("On Response : ", response.toString());
                        try {
                            JSONArray jArray = new JSONArray(response);
                            Log.v("response",response.toString());
                            Log.v("jArray.length()", String.valueOf(jArray.length()));
                            int count = 0;
                            for(int i=0; i < jArray.length(); i++) {

                                JSONObject jsreceipt = jArray.getJSONObject(i);


                                String RLN_TYPE = jsreceipt.getString("RLN_TYPE");
                                String HOUSE_NO = jsreceipt.getString("HOUSE_NO");
                                String SLNOINPART = jsreceipt.getString("SLNOINPART");
                                String EPIC_NO = jsreceipt.getString("EPIC_NO");
                                String Name = jsreceipt.getString("Name");
                                String ADDRESS = jsreceipt.getString("ADDRESS");
                                String RLN_Name = jsreceipt.getString("RLN_Name");
                                String GENDER = jsreceipt.getString("GENDER");
                                int AGE = jsreceipt.getInt("AGE");

                                boolean isInserted = myDb.insertData(SLNOINPART.toString(),EPIC_NO.toString(),Name.toString(),
                                        RLN_Name.toString(),GENDER.toString(),AGE,ADDRESS.toString(),HOUSE_NO.toString(),RLN_TYPE.toString());
                                if(isInserted = true){
                                    count++;
                                    Log.d("count", String.valueOf(count));
                                    Log.d("name",Name);
                                }
                                Toast.makeText(getApplicationContext(),"Local Database Created",Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(),"Total"+ count + "values inserted",Toast.LENGTH_SHORT).show();


                                *//*HashMap<String, String> data = new HashMap<>();
                                data.put("ST_CODE", ST_CODE);
                                data.put("AC_NO", AC_NO);
                                data.put("PART_NO", PART_NO);
                                data.put("SLNOINPART", SLNOINPART);
                                data.put("EPIC_NO", EPIC_NO);
                                data.put("Name", Name);
                                data.put("ADDRESS",ADDRESS);
                                data.put("RLN_Name", RLN_Name);
                                data.put("GENDER", GENDER);
                                data.put("AGE", AGE);*//*


                            }




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

         *//*Intent intent=new Intent(LoginActivity.this,WelcomeBLO.class);
         startActivity(intent);
         finish();*//*



    }*/

    @Override
    public void onBackPressed() {
        backButtonHandler();
    }
    private void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                WelcomeBLONew.this);
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

    private void showProgressDialog() {
        if (!pd.isShowing())
            pd.show();
    }

    private void hideProgressDialog() {
        if (pd.isShowing())
            pd.dismiss();
    }

    private void PrintApplicationFormMethod() {
        //String url="http://117.239.180.198/BLONet_API_Dev/Static/ERollMatrix.pdf";
        String url = IP_HEADER+"GetElectoralList?st_code="+st_code+"&ac_no="+sac+"&part_no="+spart;
        PrintApplicationFormMethodtask printApplicationFormMethodtask=new PrintApplicationFormMethodtask();
        printApplicationFormMethodtask.execute(url);
    }
    private class PrintApplicationFormMethodtask extends AsyncTask<String,String,String> {
        ProgressDialog pd=new ProgressDialog(WelcomeBLONew.this);
        String ap;
        File file;
        int totalSize;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd.setTitle(getApplicationContext().getResources().getString(R.string.Downloading));
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.show();
            pd.setCanceledOnTouchOutside(false);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                //set the download URL, a url that points to a file on the internet
                //this is the file to be downloaded
                String fileName="ERollMatrix";
                String fileExtension=".pdf";
                //URL url = new URL("http://117.239.180.198/BLONet_API_Dev/Static/ERollMatrix.pdf");
                URL url = new URL(IP_HEADER+"GetElectoralList?st_code="+st_code+"&ac_no="+sac+"&part_no="+spart);
                //http://kmmc.in/wp-content/uploads/2014/01/lesson2.pdf
                Log.e("url",url.toString());

                //create the new connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //set up some things on the connection
                /*urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);*/

                //and connect!
                urlConnection.connect();
                Log.d("connect","connect");

                //set the path where we want to save the file
                //in this case, going to save it on the root directory of the
                //sd card.
                //File SDCardRoot = Environment.getExternalStorageDirectory();
                //create a new file, specifying the path, and the filename
                //which we want to save the file as.
                file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), fileName+fileExtension);
                Log.d("createNewFile1",file.getAbsolutePath());
                file.createNewFile();
                Log.d("createNewFile",file.getAbsolutePath());
                //this will be used to write the downloaded data into the file we created
                FileOutputStream fileOutput = new FileOutputStream(file);
                Log.d("fileOutput","fileOutput");
                //this will be used in reading the data from the internet
                InputStream inputStream = urlConnection.getInputStream();
                 Log.d("inputStream", String.valueOf(inputStream));
                //this is the total size of the file
                totalSize = urlConnection.getContentLength();
                Log.d("totalSize", String.valueOf(totalSize));
                //variable to store total downloaded bytes
                int downloadedSize = 0;

                //create a buffer...
                byte[] buffer = new byte[1024];
                int bufferLength = 0; //used to store a temporary size of the buffer

                //now, read through the input buffer and write the contents to the file
                Log.e("bufferLength","bufferLength");
                while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                    //add the data in the buffer to the file in the file output stream (the file on the sd card
                    fileOutput.write(buffer, 0, bufferLength);
                    //add up the size so we know how much is downloaded
                    downloadedSize += bufferLength;
                    //this is where you would do something to report the prgress, like this maybe
                    //updateProgress(downloadedSize, totalSize);

                }
                //close the output stream when done
                Log.e("dsf",file.getAbsolutePath());
                ap=file.getAbsolutePath();
                fileOutput.close();

//catch some possible errors...
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("e.printStackTrace()","a");
                return "";
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("e.printStackTrace()","b");
                return "";
            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(totalSize <= 10) {

                Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Server_is_overloaded_Please_try_after_some_time),Toast.LENGTH_LONG).show();
                pd.dismiss();
            }else {
                Log.d("totalSize", String.valueOf(totalSize));
                final Dialog dialog=new Dialog(WelcomeBLONew.this);
                dialog.setContentView(R.layout.customviewapplicationform);
                Button bOK=(Button)dialog.findViewById(R.id.bok);
                Button bCANCEL=(Button)dialog.findViewById(R.id.bcancel);
                dialog.setCanceledOnTouchOutside(false);


                bOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    /*intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);*/
                        startActivity(intent);
                        //Intent intent = new Intent(Intent.ACTION_VIEW);
                        //intent.setDataAndType(Uri.fromFile(new File(ap), "application*//*");

                        //startActivity(browserIntent);
                        Log.e("ap", "" + ap);
                        dialog.dismiss();


                    }
                });
                bCANCEL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });
                dialog.show();
                pd.dismiss();
            }


        }
    }

    /*public void downloadPdfContent(String urlToDownload){

        URLConnection urlConnection = null;

        try{

            URL url = new URL(urlToDownload);

            //Opening connection of currrent url

            urlConnection = url.openConnection();
            urlConnection.connect();
            Log.d("urlConnection","urlConnection");

            //int lenghtOfFile = urlConnection.getContentLength();


            String PATH = Environment.getExternalStorageDirectory() + "/1/";

            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, "test.pdf");
            FileOutputStream fos = new FileOutputStream(outputFile);

            InputStream is = url.openStream();

            Log.d("InputStream","InputStream");


            byte[] buffer = new byte[1024];

            int len1 = 0;
            Log.d("before while","before while");

            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }

            fos.close();
            is.close();
            Log.d("is.close",urlToDownload);

            System.out.println("--pdf downloaded--ok--"+urlToDownload);

        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Log.d("Exception","Exception");
        }

    }*/
    public void vollyRequestForPostAuth(String URL, Map<String, String> keyVal) {
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
                            Log.d("IsAuthenticated",IsAuthenticated);
                            if(IsAuthenticated=="true" || IsAuthenticated.equals("true")){
                                Log.d("IsAuthenticated",IsAuthenticated);
                                if(buttonClicked == "option11adjacent"){
                                    int count1=0;
                                    count1 = myDb.count("Voter_List_Table");
                                    if(count1>0){
                                        final Dialog dialog = new Dialog(WelcomeBLONew.this);
                                        dialog.setContentView(R.layout.custom_adjacent_part_number);
                                        Button btnsubmit = (Button) dialog.findViewById(R.id.btnsubmit);
                                        final EditText etpartnumber = (EditText) dialog.findViewById(R.id.etpartnumber);
                                        dialog.setCanceledOnTouchOutside(false);

                                        btnsubmit.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if(etpartnumber.getText().toString().isEmpty()){
                                                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Enter_Part_Number),Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                                if(ConnectionStatus.isNetworkConnected(WelcomeBLONew.this)){
                                                    String partseries = etpartnumber.getText().toString().trim();
                                                    dialog.dismiss();

                                                    //main// url2 = "http://eronet.ecinet.in/services/api/BLONet/Post_AdjacentPartDetails?st_code="+st_code+"&ac_no="+acno+"&part_no="+partno+"&adj_partlist="+partseries;
                                                    url2 = IP_HEADER+"Post_AdjacentPartDetails?st_code="+st_code+"&ac_no="+acno+"&part_no="+partno+"&adj_partlist="+partseries;
                                                    Log.d("url2",url2);
                                                    showProgressDialog();

                                                    new RequestTask2().execute(url2);
                                                } else {
                                                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_Internet_connection),Toast.LENGTH_SHORT).show();
                                                }

                                            }


                                        });
                                        dialog.show();
                                    } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Local_Server_is_not_created_Click_on_download_elector_list_first),Toast.LENGTH_LONG).show();

                                }
                                if(buttonClicked == "tvbloregister"){
                                    int count1=0;
                                    count1 = myDb.count("Voter_List_Table");
                                    if(count1>0){
                                        Intent intent=new Intent(WelcomeBLONew.this,BloRegisterActivity.class);
                                        startActivity(intent);
                                    } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Local_Server_is_not_created_Click_on_download_elector_list_first),Toast.LENGTH_LONG).show();
                                }


                            } else {
                                Toast.makeText(getApplicationContext(), "Authentication Failed,Please Re-login", Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = prefs.edit();
                                //ditor.putString("BLO_ID", BLO_ID);
                                editor.putString("IS_AUTH","false");
                                editor.commit();
                                Intent intent = new Intent(WelcomeBLONew.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
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
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                //headers.put("Content-Type", "application/json");
                headers.put("access_token", SharedKey);
                return headers;
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


                params.put("AddressLine1",etpollingaddress.getText().toString());
                params.put("AddressLine2",etpollingaddress2.getText().toString());
                params.put("AddressLine3",AddressPin.toString());
                params.put("PO_BULDING_LAT_LONG",slatlong.toString());
                params.put("PO_AREA_PINCODE",etareapincodecovered.getText().toString());

                Log.d("params ",etpollingaddress.getText().toString()+" "+etpollingaddress2.getText().toString()+" "+AddressPin
                        +" "+slatlong.toString()+" "+etareapincodecovered.getText().toString());

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
