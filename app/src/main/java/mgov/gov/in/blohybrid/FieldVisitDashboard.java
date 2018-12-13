package mgov.gov.in.blohybrid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import mgov.gov.in.blohybrid.Constants;

public class FieldVisitDashboard extends Activity {
    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs" ;
    String urlAuth,count,urlpostoverseas,urlpostGPSlocation,urlpostASDStatus,urlpostProbablePS,urlpostExistingPS,urlpostFutureVoters,urlPostPOAddress,
            urlHC,urlpoliceStation,urlAuxiliaryps,SharedKey,urlunenrolled,IP_HEADER;
    TableLayout tbll;
    int coutgpsentries, count_asd_entries,count_probable_PS_entries,count_existing_PS_entries,count_PO_Address_entries,count_future_voters_entries,
         count_overseas_voter_entries,testcount=1,count_HC,count_auxilaryPS,countPoliceStation,
        count_asd_false,countUnenrolled;
    RequestQueue queue;
    JSONArray jsonArrayUnenrolled,jsonArray,jsonArrayASD,jsonArrayProbablePS,jsonArrayExistingPS,jsonArrayPOAddress,jsonArrayFutureVoter,jsonArrayOverseas,jsonArrayHC,jsonArrayPoliceStation,jsonArrayAuxiliaryPS;
    DatabaseHelper myDb;
    String falsee = "'false'";
    Dialog dialg;
    JSONObject jsonBody = new JSONObject();
    JSONObject jsonBodyASD = new JSONObject();
    JSONObject jsonBodyProbablePS = new JSONObject();
    JSONObject jsonBodyExistingPS = new JSONObject();
    JSONObject jsonBodyPOAddress = new JSONObject();
    JSONObject jsonBodyFutureVoter = new JSONObject();
    JSONObject jsonBodyHC = new JSONObject();
    JSONObject jsonBodyPoliceStation = new JSONObject();
    JSONObject jsonBodyAuxiliaryPS = new JSONObject();
    JSONObject jsonBodyOverseas = new JSONObject();
    JSONObject jsonBodyUnenrolled = new JSONObject();

    Map<String, JSONArray> keyVal = new LinkedHashMap<String, JSONArray>();
    Map<String, JSONArray> keyValASD = new LinkedHashMap<String, JSONArray>();
    Map<String, JSONArray> keyValProbablePS = new LinkedHashMap<String, JSONArray>();
    Map<String, JSONArray> keyValExisitngPS = new LinkedHashMap<String, JSONArray>();
    Map<String, JSONArray> keyValPOAddress = new LinkedHashMap<String, JSONArray>();
    Map<String, JSONArray> keyValFutureVoter = new LinkedHashMap<String, JSONArray>();
    Map<String, JSONArray> keyValOverseas = new LinkedHashMap<String, JSONArray>();
    Map<String, JSONArray> keyValHC = new LinkedHashMap<String, JSONArray>();
    Map<String, JSONArray> keyValAuxiliaryPS = new LinkedHashMap<String, JSONArray>();
    Map<String, JSONArray> keyValPoliceStation = new LinkedHashMap<String, JSONArray>();
    Map<String, JSONArray> keyValUnenrolled = new LinkedHashMap<String, JSONArray>();
    JSONObject jsonBodyAuth = new JSONObject();
    Map<String, String> keyValAuth = new LinkedHashMap<String, String>();

    /*boolean flagasd = false; boolean flaggps = false;boolean flagprobable = false; boolean flagexisting= false;
    boolean flagpo= false; boolean flagfuture =false; boolean flagoverseas = false; boolean flagpolice = false;
    boolean flagauxilary = false; boolean flaghealth = false;*/
    String flagasd , flaggps,flagprobable,flagexisting,flagpo,flagfuture,flagoverseas,flagpolice,flagauxilary,flaghealth,StringWhichloop ;
    String mobnumber,stcode,acno,partno;
    ProgressDialog pd;
    android.widget.ImageView imgview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_visit_dashboard);
        TextView tvfldvst,tvcount,tvpush,tvhome,tvpending;
        tvfldvst = (TextView) findViewById(R.id.tvfldvst);
        tbll = (TableLayout) findViewById(R.id.tableLayout);
        tvpush = (TextView) findViewById(R.id.tvpush);
        tvcount = (TextView) findViewById(R.id.tvcount);
        tvhome = (TextView) findViewById(R.id.tvhome);
        tvpending = (TextView) findViewById(R.id.tvpending);
        imgview=(ImageView)findViewById(R.id.imageView_refresh);

        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FieldVisitDashboard.this, "", Toast.LENGTH_SHORT).show();
                getDashBoardData();
            }
        });

        myDb = new DatabaseHelper(this);

        prefs = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Please Wait ...");
        pd.setCancelable(false);
        queue = Volley.newRequestQueue(this);
        prefs = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mobnumber  = prefs.getString("mob","");
        stcode = prefs.getString("st_code","");
        acno = prefs.getString("AcNo","");
        partno = prefs.getString("PartNo","");
        count  = prefs.getString("count","");
        SharedKey = prefs.getString("Key","");
        Log.d("SharedKey",SharedKey);
        Log.d("count",count);
        tvcount.setText(count);
        IP_HEADER = Constants.IP_HEADER;
        urlpostoverseas = IP_HEADER+"Post_OverseasVotersDetails?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno+"&mobile_no="+mobnumber;
        urlpostGPSlocation = IP_HEADER+"POSTElectoralSaveFamilyTree?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno+"&mobile_no="+mobnumber;//"http://117.239.180.198/BLONet_API_Dev/api/BLONet/Post_GPSLocations?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno+"&mobile_no="+mobnumber;
                              //http://117.239.180.198/BLONet_API_Dev/api/BLONet/Post_GPSLocations?st_code=S02&ac_no=1&part_no=20&mobile_no=9823763919
        //urlpostASDStatus = "http://117.239.180.198/BLONet_API_Dev/api/BLONet/Post_ASDs_Status?st_code=S02&ac_no=1&part_no=20&mobile_no=9823763919";
        urlpostASDStatus = IP_HEADER+"Post_ASDs_Status?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno+"&mobile_no="+mobnumber;
        //urlpostASDStatus = "http://117.239.180.198/BLONet_API_Test/api/BLONet/Post_ASDs_Status?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno+"&mobile_no="+mobnumber;
        urlpostProbablePS = IP_HEADER+"Post_Probable_PollingStationsDetails?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno+"&mobile_no="+mobnumber;
        urlpostExistingPS = IP_HEADER+"Post_Existing_PollingStationsDetails?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno+"&mobile_no="+mobnumber;
        urlPostPOAddress = IP_HEADER+"Post_PostOfficeAddressLocations?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno+"&mobile_no="+mobnumber;
        urlpostFutureVoters = IP_HEADER+"Post_FutureVotersList?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno+"&mobile_no="+mobnumber;
        urlpostoverseas = IP_HEADER+"Post_OverseasVotersDetails?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno+"&mobile_no="+mobnumber;

        urlHC= IP_HEADER+"Post_HealthcareCentresDetails?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno;
        urlAuxiliaryps= IP_HEADER+"Post_AuxiliaryPollingStationsDetails?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno;
        urlpoliceStation = IP_HEADER+"Post_PoliceStationsDetails?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno;
        urlAuth = IP_HEADER+"IsAccessTokenExpired?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno;
        urlunenrolled = IP_HEADER+"Post_UnEnrollMembersDetails?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno+"&mobile_no="+mobnumber;
        Log.d("url",urlAuth);
        dialg= new Dialog(FieldVisitDashboard.this);
        dialg.setContentView(R.layout.custom_blo_report_done_message);

        tvfldvst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),FieldVisitSearch.class);
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        tvpending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(FieldVisitDashboard.this);
                dialog.setContentView(R.layout.custom_pending_work_popup_menu);
                TextView tvpendingwork = (TextView) dialog.findViewById(R.id.tvpendingwork);
                TextView tvpendingSaveddata = (TextView) dialog.findViewById(R.id.tvpendingSaveddata);
                dialog.setCanceledOnTouchOutside(false);

                tvpendingwork.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        showProgressDialog();
                        Cursor cursor = myDb.getVoterListPendingdata("false");
                        if(cursor.getCount() == 0 ){
                            Toast.makeText(getApplicationContext(),"Nothing to show",Toast.LENGTH_SHORT).show();
                            hideProgressDialog();
                            return;
                        }
                        StringBuffer stringBuffer = new StringBuffer();
                        while (cursor.moveToNext()){
                            stringBuffer.append("SerialNo : "+ cursor.getString(0)+"\n");
                            stringBuffer.append("Name : "+ cursor.getString(2)+"\n");
                            stringBuffer.append("Gender : "+ cursor.getString(4)+"\n");
                            stringBuffer.append("Age : "+ cursor.getString(5)+"\n");
                            stringBuffer.append("Address : "+ cursor.getString(6)+"\n\n");

                        }
                        hideProgressDialog();
                        showmessage("Data",stringBuffer.toString());
                        //Toast.makeText(getApplicationContext(),"pending work",Toast.LENGTH_SHORT).show();
                    }
                });

                tvpendingSaveddata.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        showProgressDialog();
                        Cursor cursor = myDb.getASDtobePusheddata("false");
                        if(cursor.getCount() == 0 ){
                            Toast.makeText(getApplicationContext(),"Nothing to show",Toast.LENGTH_SHORT).show();
                            hideProgressDialog();
                            return;
                        }
                        StringBuffer stringBuffer = new StringBuffer();
                        while (cursor.moveToNext()){
                            stringBuffer.append("SerialNo : "+ cursor.getString(0)+"\n");
                            stringBuffer.append("UserMobileNo : "+ cursor.getString(2)+"\n");
                            stringBuffer.append("MobileType : "+ cursor.getString(4)+"\n");
                            stringBuffer.append("ASDstatus : "+ cursor.getString(5)+"\n");
                            stringBuffer.append("Date : "+ cursor.getString(6)+"\n\n");

                        }
                        hideProgressDialog();
                        showmessage("Data",stringBuffer.toString());


                    }
                });
             dialog.show();
            }
        });

        tvpush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyValAuth.put("ST_CODE",stcode);
                keyValAuth.put("AC_NO",acno);
                keyValAuth.put("PART_NO",partno);
                try {
                    jsonBodyAuth.put("ST_CODE",stcode);
                    jsonBodyAuth.put("AC_NO",acno);
                    jsonBodyAuth.put("PART_NO",partno);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("jsonBodyAuth formed ",jsonBodyAuth.toString());
                vollyRequestForPostAuth(urlAuth, keyValAuth);


            }
        });

        tvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FieldVisitDashboard.this,WelcomeBLONew.class);
                startActivity(intent);
                finish();
            }
        });

        getDashBoardData();

    }

    private void showProgressDialog() {
        if (!pd.isShowing())
            pd.show();
    }

    private void hideProgressDialog() {
        if (pd.isShowing())
            pd.dismiss();
    }

    class SendJsonDataToServer extends AsyncTask<String,String,String>{
    String StringinPost;
        @Override
        protected void onPreExecute(){
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... params) {
            String JsonResponse = null;
            Log.e("PARAMS0",params[0]);
            Log.e("PARAMS1",params[1]);
            Log.e("PARAMS2",params[2]);
            String JsonDATA = params[1];
            StringinPost = params[2];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                // is output buffer writter
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("access_token" , SharedKey);

//set headers and method
                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                writer.write(JsonDATA);
// json data
                writer.close();
                InputStream inputStream = urlConnection.getInputStream();
//input stream
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String inputLine;
                while ((inputLine = reader.readLine()) != null)
                    buffer.append(inputLine + "\n");
                if (buffer.length() == 0) {
                    // Stream was empty. No point in parsing.
                    return null;
                }
                JsonResponse = buffer.toString();
//response data
                Log.i("JSON RESPONSE",JsonResponse);
                //send to post execute
                return JsonResponse;



            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();

                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("ERROR", "Error closing stream", e);
                    }
                }
            }
            return null;

        }


        @Override
        protected void onPostExecute(String response) {
            Log.d("On Response : ", response.toString());

            try {
                JSONObject jsonObject=new JSONObject(response.toString());
                String IsSuccess = jsonObject.getString("IsSuccess");
                String IsPartialSuccess = jsonObject.getString("IsPartialSuccess");
                Log.d("IsSuccess",IsSuccess);
                Log.d("IsPartialSuccess",IsPartialSuccess);

                if((IsSuccess.trim().equals("true")|| IsPartialSuccess.trim().equals("true")) &&(StringinPost=="loopasd" || StringinPost.equals("loopasd")) ) {
                    Log.d("StringWhichloop in post",StringinPost);
                    //DatabaseHelper db=new DatabaseHelper(FieldVisitDashboard.this);
                    myDb.update_Asd_pushed_timestamp(count_asd_false);
                    boolean isInserted = myDb.updateASD("PushedFlag","true");
                    if(isInserted == true){
                        Log.d("asd data","äsd data changed");
                    }  else {Log.d("asd data","äsd data not changed");}
                    getDashBoardData();


                } else  if((IsSuccess.trim().equals("true")|| IsPartialSuccess.trim().equals("true")) &&(StringinPost=="loopgps" || StringinPost.equals("loopgps")) ) {
                    Log.d("StringWhichloop in post",StringinPost);
                    Integer deleterows = myDb.deleteGPSdata("false");
                    if(deleterows>0){
                        Log.d("deletedGPS",deleterows.toString());
                    } else Log.d("not deletedGps","not deleted");

                } else  if((IsSuccess.trim().equals("true")|| IsPartialSuccess.trim().equals("true")) &&(StringinPost=="loopprobable" || StringinPost.equals("loopprobable")) ) {
                    Log.d("StringWhichloop in post",StringinPost);
                    Integer deleterows = myDb.deletePPSdata("false");
                    if(deleterows>0){
                        Log.d("deletedProbable",deleterows.toString());
                    } else Log.d("not deletedProbable","not deleted");

                }else  if((IsSuccess.trim().equals("true")|| IsPartialSuccess.trim().equals("true")) &&(StringinPost=="loopexisting" || StringinPost.equals("loopexisting")) ) {
                    Log.d("StringWhichloop in post",StringinPost);
                    Integer deleterows = myDb.deleteEXISTINGdata("false");
                    if(deleterows>0){
                        Log.d("deletedExisting",deleterows.toString());
                    } else Log.d("not deletedExisting","not deleted");

                }else  if((IsSuccess.trim().equals("true")|| IsPartialSuccess.trim().equals("true")) &&(StringinPost=="loopPO" || StringinPost.equals("loopPO")) ) {
                    Log.d("StringWhichloop in post",StringinPost);
                    Integer deleterows = myDb.deletePOdata("false");
                    if(deleterows>0){
                        Log.d("deletedPO",deleterows.toString());
                    } else Log.d("not deletedPO","not deleted");

                }else  if((IsSuccess.trim().equals("true")|| IsPartialSuccess.trim().equals("true")) &&(StringinPost=="loopfuturevoter" || StringinPost.equals("loopfuturevoter")) ) {
                    Log.d("StringWhichloop in post",StringinPost);
                    Integer deleterows = myDb.deleteFUTUREVOTERdata("false");
                    if(deleterows>0){
                        Log.d("deletedFV",deleterows.toString());
                    } else Log.d("not deletedFV","not deleted");

                }else  if((IsSuccess.trim().equals("true")|| IsPartialSuccess.trim().equals("true")) &&(StringinPost=="loopoverseas" || StringinPost.equals("loopoverseas")) ) {
                    Log.d("StringWhichloop in post",StringinPost);
                    Integer deleterows = myDb.deleteOVERSEASdata("false");
                    if(deleterows>0){
                        Log.d("deletedOVERSEAS",deleterows.toString());
                    } else Log.d("not deletedOVERSEAS","not deleted");

                } else  if((IsSuccess.trim().equals("true")|| IsPartialSuccess.trim().equals("true")) &&(StringinPost=="loopHC" || StringinPost.equals("loopoHC")) ) {
                    Log.d("StringWhichloop in post",StringinPost);
                    Integer deleterows = myDb.deleteHCdata("false");
                    if(deleterows>0){
                        Log.d("deletedHC",deleterows.toString());
                    } else Log.d("not deletedHC","not deleted");

                } else  if((IsSuccess.trim().equals("true")|| IsPartialSuccess.trim().equals("true")) &&(StringinPost=="loopPOLICE" || StringinPost.equals("loopoPOLICE")) ) {
                    Log.d("StringWhichloop in post",StringinPost);
                    Integer deleterows = myDb.deletePOLICEdata("false");
                    if(deleterows>0){
                        Log.d("deletedPOLICE",deleterows.toString());
                    } else Log.d("not deletedPOLICE","not deleted");

                } else  if((IsSuccess.trim().equals("true")|| IsPartialSuccess.trim().equals("true")) &&(StringinPost=="loopAPS" || StringinPost.equals("loopoAPS")) ) {
                    Log.d("StringWhichloop in post",StringinPost);
                    Integer deleterows = myDb.deleteAUXILIARYdata("false");
                    if(deleterows>0){
                        Log.d("deletedAPS",deleterows.toString());
                    } else Log.d("not deletedAPS","not deleted");

                }else  if((IsSuccess.trim().equals("true")|| IsPartialSuccess.trim().equals("true")) &&(StringinPost=="loopUN" || StringinPost.equals("loopUN")) ) {
                    Log.d("StringWhichloop in post",StringinPost);
                    Integer deleterows = myDb.deleteUnenrolleddata("false");
                    if(deleterows>0){
                        Log.d("deletedUN",deleterows.toString());
                    } else Log.d("not deletedUN","not deleted");

                }

                if(IsSuccess.trim().equals("true")|| IsPartialSuccess.trim().equals("true")){

                    if(dialg.isShowing()){
                        dialg.dismiss();
                    }
                    Button btnok1 = (Button) dialg.findViewById(R.id.btnok);
                    dialg.setCanceledOnTouchOutside(false);
                    btnok1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialg.dismiss();

                        }
                    });

                    dialg.show();

                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Something_Went_Wrong),Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                hideProgressDialog();
            }

        }

    }


    public void vollyRequestForPostGPSLocation(String URL, final JSONArray keyVal) {
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
                            String IsSuccess = response.getString("IsSuccess");
                            final String Message = response.getString("Message");
                            String IsPartialSuccess = response.getString("IsPartialSuccess");
                            Log.d("IsSuccess",IsSuccess);
                            Log.d("Message",Message);

                            Log.v("Response : ", "IsSuccess : " + IsSuccess);
                            Log.d("Test Count", String.valueOf(testcount));
                            testcount++;
                            Log.d("flag", String.valueOf(flagasd)+" "+String.valueOf(flaggps)+ " "+String.valueOf(flagprobable)+ " "+String.valueOf(flagexisting)
                                    +" "+ String.valueOf(flagpo)+" "+String.valueOf(flagfuture)+ " "+String.valueOf(flagoverseas));

                            if(IsSuccess.trim().equals("true") || IsPartialSuccess.trim().equals("true")) {
                                //do db work
                                final Dialog dialog = new Dialog(FieldVisitDashboard.this);
                                dialog.setContentView(R.layout.custom_blo_report_done_message);
                                Button btnok = (Button) dialog.findViewById(R.id.btnok);
                                dialog.setCanceledOnTouchOutside(false);


                                btnok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        /*Intent intent=new Intent(FieldVisitDashboard.this,WelcomeBLONew.class);
                                        startActivity(intent);
                                        finish();*/
                                        Toast.makeText(getApplicationContext(),Message.toString(),Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();

                                    }
                                });
                                dialog.show();



                            } else
                                Toast.makeText(getApplicationContext(), "No Response from Server.", Toast.LENGTH_LONG).show();



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
                               // Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT);
                                Toast.makeText(getApplicationContext(),"Error in uploading data",Toast.LENGTH_SHORT);
                                hideProgressDialog();

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
                    return keyVal.toString() == null ? null : keyVal.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", keyVal.toString(), "utf-8");
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

        queue.add(jsObjRequest);
    }


    /*class RequestTask2 extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost post = new HttpPost(uri[0]);
            try {
                post.setEntity(new UrlEncodedFormEntity(jsonBodyASD, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            HttpResponse response;
            String responseString = null;
            try {
                response = httpclient.execute(new HttpGet(uri[0]));
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
        protected void onPostExecute(String response) {
            Log.d("On Response : ", response.toString());
            try {
                JSONObject jsonObject=new JSONObject(response.toString());
                String IsSuccess = jsonObject.getString("IsSuccess");
                String Message = jsonObject.getString("Message");
                Log.d("IsSuccess",IsSuccess);
                Log.d("Message",Message);

                Log.v("Response : ", "IsSuccess : " + IsSuccess);

                if(IsSuccess.trim().equals("true") || IsSuccess.trim().equals("true")) {
                    final Dialog dialog = new Dialog(FieldVisitDashboard.this);
                    dialog.setContentView(R.layout.custom_blo_report_done_message);
                    Button btnok = (Button) dialog.findViewById(R.id.btnok);
                    dialog.setCanceledOnTouchOutside(false);
                    btnok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            *//*Intent intent=new Intent(VoterListFamily.this,WelcomeBLONew.class);
                            startActivity(intent);
                            finish();*//*



                            dialog.dismiss();

                        }
                    });
                    dialog.show();


                } else
                    Toast.makeText(getApplicationContext(), "No Response from Server.", Toast.LENGTH_LONG).show();



            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                hideProgressDialog();
            }

        }
    }*/

    private void showmessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private void getDashBoardData(){
        DatabaseHelper db=new DatabaseHelper(FieldVisitDashboard.this);
        ArrayList<DashboardClass> dbc=db.update_show_Dashboard();

        tbll.removeAllViews();

        {
            TableRow row = new TableRow(this);
            TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(0,1,0,0);
            row.setLayoutParams(layoutParams);

            TextView tv_date,tv_workdone,tv_pending,tv_pushed_records,tv_pushedtimestamp;

            tv_date = (TextView)getLayoutInflater().inflate(R.layout.textview1, null);

            tv_workdone =(TextView)getLayoutInflater().inflate(R.layout.textview2, null);

            tv_pending = (TextView)getLayoutInflater().inflate(R.layout.textview3, null);

            tv_pushed_records = (TextView)getLayoutInflater().inflate(R.layout.textview4, null);

            tv_pushedtimestamp = (TextView)getLayoutInflater().inflate(R.layout.textview5, null);


            row.addView(tv_date);
            row.addView(tv_workdone);
            row.addView(tv_pending);
            row.addView(tv_pushed_records);
            row.addView(tv_pushedtimestamp);
            tbll.addView(row, 0);

        }

            Log.e("data",""+dbc.size());

        for (int i = 0; i <dbc.size(); i++) {

            TableRow row = new TableRow(this);
            TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(0,1,0,0);
            row.setLayoutParams(layoutParams);
            /*TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);*/
            TextView tv_date,tv_workdone,tv_pending,tv_pushed_records,tv_pushedtimestamp;

            tv_date = (TextView)getLayoutInflater().inflate(R.layout.textview1, null);
            //tv_date.setTextAppearance(this, R.style.TableTextView);
            //tv_date.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f));

            tv_workdone =(TextView)getLayoutInflater().inflate(R.layout.textview2, null);
            //tv_workdone.setTextAppearance(this, R.style.TableTextView);
            //tv_workdone.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f));

            tv_pending = (TextView)getLayoutInflater().inflate(R.layout.textview3, null);
            //tv_pending.setTextAppearance(this, R.style.TableTextView);
            //tv_pending.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f));

            tv_pushed_records = (TextView)getLayoutInflater().inflate(R.layout.textview4, null);
            //tv_pushed_records.setTextAppearance(this, R.style.TableTextView);
            //tv_pushed_records.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f));

            tv_pushedtimestamp = (TextView)getLayoutInflater().inflate(R.layout.textview5, null);
            //tv_pushedtimestamp.setTextAppearance(this, R.style.TableTextView);
            //tv_pushedtimestamp.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f));

            tv_date.setText(dbc.get(i).getDate());
            tv_workdone.setText(""+dbc.get(i).getWorkdone());
            tv_pending.setText(""+dbc.get(i).getPending_work());
            tv_pushed_records.setText(""+dbc.get(i).getPushed_records());
            tv_pushedtimestamp.setText(dbc.get(i).getPused_timestamp());
            Log.e("pushed data",dbc.get(i).getPused_timestamp());

            row.addView(tv_date);
            row.addView(tv_workdone);
            row.addView(tv_pending);
            row.addView(tv_pushed_records);
            row.addView(tv_pushedtimestamp);

            tbll.addView(row, i+1);
        }
    }

    public void vollyRequestForPostAuth(String URL, Map<String, String> keyValAuth) {
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

                                final Dialog dialog = new Dialog(FieldVisitDashboard.this);
                                dialog.setContentView(R.layout.custom_push_warning);
                                Button btnok = (Button) dialog.findViewById(R.id.bok);
                                Button btncncl = (Button) dialog.findViewById(R.id.bcancel);
                                dialog.setCanceledOnTouchOutside(false);

                                btncncl.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });

                                btnok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        final Dialog dialogg = new Dialog(FieldVisitDashboard.this);
                                        dialogg.setContentView(R.layout.custom_push_warning2);
                                        Button btnok = (Button) dialogg.findViewById(R.id.bok);
                                        Button btncncl = (Button) dialogg.findViewById(R.id.bcancel);
                                        dialogg.setCanceledOnTouchOutside(false);

                                        btncncl.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialogg.dismiss();
                                            }
                                        });

                                        btnok.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialogg.dismiss();
                                                coutgpsentries = myDb.count("FAMILY_GPS_LOCATION");
                                                Log.d("coutgpsentries", String.valueOf(coutgpsentries));


                                                //count to be consider for time stamp
                                                count_asd_entries = myDb.count("ASD_Saved_Data");
                                                Log.d("count_asd_entries", String.valueOf(count_asd_entries));

                                                count_probable_PS_entries = myDb.count("PROBABLE_POLLING_STATION");
                                                Log.d("count_probable_PS_entries", String.valueOf(count_probable_PS_entries));

                                                count_existing_PS_entries = myDb.count("EXISTING_POLLING_STATION");
                                                Log.d("count_existing_PS_entries", String.valueOf(count_existing_PS_entries));

                                                count_PO_Address_entries = myDb.count("PostOfficeAddressLocations");
                                                Log.d("count_PO_Address_entries", String.valueOf(count_PO_Address_entries));

                                                count_future_voters_entries = myDb.count("FutureVotersList");
                                                Log.d("count_future_voters_entries", String.valueOf(count_future_voters_entries));

                                                count_overseas_voter_entries = myDb.count("OVERSEAS_VOTER");
                                                Log.d("count_overseas_voter_entries", String.valueOf(count_overseas_voter_entries));

                                                count_HC = myDb.count("HealthCareCentre");
                                                Log.d("count_HC", String.valueOf(count_HC));

                                                count_auxilaryPS = myDb.count("AUXILIARY_POLLING_STATION");
                                                Log.d("count_auxilaryPS", String.valueOf(count_auxilaryPS));

                                                countPoliceStation = myDb.count("PoliceStationLocations");
                                                Log.d("countPoliceStation", String.valueOf(countPoliceStation));

                                                countUnenrolled = myDb.count("Unenrolled");
                                                Log.d("countPoliceStation", String.valueOf(countPoliceStation));

                                                if(count_asd_entries >0){
                                                    jsonArrayASD = myDb.SelectASDData(falsee);

                                                    count_asd_false=jsonArrayASD.length();
                                                    Log.d("count_asd_false","" +count_asd_false);
                                                    Log.d("jsonArrayASD",jsonArrayASD.toString());
                                                    if(count_asd_false > 0)
                                                    {
                                                        keyValASD.put("reqObject",jsonArrayASD);
                                                        try {
                                                            jsonBodyASD.put("reqObject",jsonArrayASD);
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                        Log.e("json formedASD", String.valueOf(jsonBodyASD));
                                                        flagasd = "true";
                                                        //new RequestTask2().execute(urlpostASDStatus);
                                                        //vollyRequestForPostGPSLocation(urlpostASDStatus, keyValASD);
                                                        //vollyRequestForPostGPSLocation(urlpostASDStatus, jsonArrayASD);
                                                        StringWhichloop = "loopasd";
                                                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                                                .permitAll()
                                                                .build();
                                                        StrictMode.setThreadPolicy(policy);

                                                        new SendJsonDataToServer().execute(urlpostASDStatus,String.valueOf(jsonBodyASD), String.valueOf(StringWhichloop));

                                                        //volley(urlpostASDStatus,jsonArrayASD);
                                                        flagasd = "false";
                                                    }

                                                } /*else {
                                    Toast.makeText(getApplicationContext(), "ASD STATUS : NO DATA TO PUSH ", Toast.LENGTH_LONG).show();
                                }*/


                                                if(coutgpsentries >0){
                                                    jsonArray = myDb.SelectFamilyGPSData(falsee);
                                                    Log.d("jsonArray",jsonArray.toString());
                                                    keyVal.put("reqObject",jsonArray);
                                                    try {
                                                        jsonBody.put("reqObject",jsonArray);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    Log.e("json formed", String.valueOf(jsonBody));
                                                    flaggps = "true";
                                                    StringWhichloop = "loopgps";
                                                    //vollyRequestForPostGPSLocation(urlpostGPSlocation, keyVal);
                                                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                                            .permitAll()
                                                            .build();
                                                    StrictMode.setThreadPolicy(policy);

                                                    new SendJsonDataToServer().execute(urlpostGPSlocation,String.valueOf(jsonBody),String.valueOf(StringWhichloop));

                                                    flaggps = "false";

                                                } /*else {
                                    Toast.makeText(getApplicationContext(), "Family GPS Location: NO DATA TO PUSH ", Toast.LENGTH_LONG).show();
                                }*/


                                                if(count_probable_PS_entries >0){
                                                    jsonArrayProbablePS = myDb.SelectPrabableData(falsee);
                                                    Log.d("jsonArrayProbablePS",jsonArrayProbablePS.toString());
                                                    keyValProbablePS.put("reqObject",jsonArrayProbablePS);
                                                    try {
                                                        jsonBodyProbablePS.put("reqObject",jsonArrayProbablePS);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    Log.e("json formedPPS", String.valueOf(jsonBodyProbablePS));
                                                    flagprobable = "true";
                                                    //vollyRequestForPostGPSLocation(urlpostProbablePS, keyValProbablePS);
                                                    StringWhichloop = "loopprobable";

                                                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                                            .permitAll()
                                                            .build();
                                                    StrictMode.setThreadPolicy(policy);

                                                    new SendJsonDataToServer().execute(urlpostProbablePS,String.valueOf(jsonBodyProbablePS),String.valueOf(StringWhichloop));

                                                    flagprobable = "false";
                                                }/* else {
                                    Toast.makeText(getApplicationContext(), "Probable Polling Station: NO DATA TO PUSH ", Toast.LENGTH_LONG).show();
                                }*/

                                                if(count_existing_PS_entries >0){
                                                    jsonArrayExistingPS = myDb.SelectExistingData(falsee);
                                                    Log.d("jsonArrayExistingPS",jsonArrayExistingPS.toString());
                                                    keyValExisitngPS.put("reqObject",jsonArrayExistingPS);
                                                    try {
                                                        jsonBodyExistingPS.put("reqObject",jsonArrayExistingPS);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    Log.e("json formedEPS", String.valueOf(jsonBodyExistingPS));
                                                    flagexisting = "true";
                                                    //vollyRequestForPostGPSLocation(urlpostExistingPS, keyValExisitngPS);
                                                    StringWhichloop = "loopexisting";
                                                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                                            .permitAll()
                                                            .build();
                                                    StrictMode.setThreadPolicy(policy);

                                                    new SendJsonDataToServer().execute(urlpostExistingPS,String.valueOf(jsonBodyExistingPS),String.valueOf(StringWhichloop));

                                                    flagexisting = "false";
                                                } /*else {
                                    Toast.makeText(getApplicationContext(), "Existing Polling Station: NO DATA TO PUSH ", Toast.LENGTH_LONG).show();
                                }*/

                                                if(count_PO_Address_entries >0){
                                                    jsonArrayPOAddress = myDb.SelectPOData(falsee);
                                                    Log.d("jsonArrayPO",jsonArrayPOAddress.toString());
                                                    keyValPOAddress.put("reqObject",jsonArrayPOAddress);
                                                    try {
                                                        jsonBodyPOAddress.put("reqObject",jsonArrayPOAddress);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    Log.e("json formedPO", String.valueOf(jsonBodyPOAddress));
                                                    flagpo = "true";
                                                    //vollyRequestForPostGPSLocation(urlPostPOAddress, keyValPOAddress);
                                                    StringWhichloop = "loopPO";

                                                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                                            .permitAll()
                                                            .build();
                                                    StrictMode.setThreadPolicy(policy);

                                                    new SendJsonDataToServer().execute(urlPostPOAddress,String.valueOf(jsonBodyPOAddress),String.valueOf(StringWhichloop));

                                                    flagpo = "false";
                                                } /*else {
                                    Toast.makeText(getApplicationContext(), "Post Office : NO DATA TO PUSH ", Toast.LENGTH_LONG).show();
                                }*/

                                                if(count_future_voters_entries >0){
                                                    jsonArrayFutureVoter = myDb.SelectFutureVoterData(falsee);
                                                    Log.d("jsonArrayFuturVoter",jsonArrayFutureVoter.toString());
                                                    keyValFutureVoter.put("reqObject",jsonArrayFutureVoter);
                                                    try {
                                                        jsonBodyFutureVoter.put("reqObject",jsonArrayFutureVoter);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    Log.e("json formedFutureVoter", String.valueOf(jsonBodyFutureVoter));
                                                    flagfuture = "true";
                                                    //vollyRequestForPostGPSLocation(urlpostFutureVoters, keyValFutureVoter);
                                                    StringWhichloop = "loopfuturevoter";
                                                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                                            .permitAll()
                                                            .build();
                                                    StrictMode.setThreadPolicy(policy);

                                                    new SendJsonDataToServer().execute(urlpostFutureVoters,String.valueOf(jsonBodyFutureVoter),String.valueOf(StringWhichloop));

                                                    flagfuture = "false";
                                                } /*else {
                                    Toast.makeText(getApplicationContext(), "Future Voters : NO DATA TO PUSH ", Toast.LENGTH_LONG).show();
                                }*/

                                                if(count_overseas_voter_entries >0){
                                                    jsonArrayOverseas = myDb.SelectOverseasData(falsee);
                                                    Log.d("jsonArrayOverseas",jsonArrayOverseas.toString());
                                                    keyValOverseas.put("reqObject",jsonArrayOverseas);
                                                    try {
                                                        jsonBodyOverseas.put("reqObject",jsonArrayOverseas);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    Log.e("json formedOverseas", String.valueOf(jsonBodyOverseas));
                                                    flagoverseas = "true";
                                                    //vollyRequestForPostGPSLocation(urlpostoverseas, keyValOverseas);
                                                    StringWhichloop = "loopoverseas";
                                                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                                            .permitAll()
                                                            .build();
                                                    StrictMode.setThreadPolicy(policy);

                                                    new SendJsonDataToServer().execute(urlpostoverseas,String.valueOf(jsonBodyOverseas),String.valueOf(StringWhichloop));

                                                    flagoverseas = "false";
                                                }

                                                if(count_HC >0){
                                                    jsonArrayHC = myDb.SelectHealthCareData(falsee);
                                                    Log.d("jsonArrayHC",jsonArrayHC.toString());
                                                    keyValHC.put("reqObject",jsonArrayHC);
                                                    try {
                                                        jsonBodyHC.put("reqObject",jsonArrayHC);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    Log.e("json formedHC", String.valueOf(jsonBodyHC));

                                                    //vollyRequestForPostGPSLocation(urlpostoverseas, keyValOverseas);
                                                    StringWhichloop = "loopHC";
                                                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                                            .permitAll()
                                                            .build();
                                                    StrictMode.setThreadPolicy(policy);

                                                    new SendJsonDataToServer().execute(urlHC,String.valueOf(jsonBodyHC),String.valueOf(StringWhichloop));


                                                }

                                                if(countPoliceStation >0){
                                                    jsonArrayPoliceStation = myDb.SelectPOLICEData(falsee);
                                                    Log.d("jsonArrayPoliceStation",jsonArrayPoliceStation.toString());
                                                    keyValPoliceStation.put("reqObject",jsonArrayPoliceStation);
                                                    try {
                                                        jsonBodyPoliceStation.put("reqObject",jsonArrayPoliceStation);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    Log.e("json formed Police Station", String.valueOf(jsonBodyPoliceStation));

                                                    //vollyRequestForPostGPSLocation(urlpostoverseas, keyValOverseas);
                                                    StringWhichloop = "loopPOLICE";
                                                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                                            .permitAll()
                                                            .build();
                                                    StrictMode.setThreadPolicy(policy);

                                                    new SendJsonDataToServer().execute(urlpoliceStation,String.valueOf(jsonBodyPoliceStation),String.valueOf(StringWhichloop));


                                                }

                                                if(count_auxilaryPS >0){
                                                    jsonArrayAuxiliaryPS = myDb.SelectAuxiliaryData(falsee);
                                                    Log.d("jsonArrayAuxiliaryPS",jsonArrayAuxiliaryPS.toString());
                                                    keyValAuxiliaryPS.put("reqObject",jsonArrayAuxiliaryPS);
                                                    try {
                                                        jsonBodyAuxiliaryPS.put("reqObject",jsonArrayAuxiliaryPS);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    Log.e("json formedAPS", String.valueOf(jsonBodyAuxiliaryPS));

                                                    //vollyRequestForPostGPSLocation(urlpostoverseas, keyValOverseas);
                                                    StringWhichloop = "loopAPS";
                                                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                                            .permitAll()
                                                            .build();
                                                    StrictMode.setThreadPolicy(policy);

                                                    new SendJsonDataToServer().execute(urlAuxiliaryps,String.valueOf(jsonBodyAuxiliaryPS),String.valueOf(StringWhichloop));
                                                }

                                                if(countUnenrolled >0){
                                                    jsonArrayUnenrolled = myDb.SelectUnenrolledData(falsee);
                                                    Log.d("jsonArrayUnenrolled",jsonArrayUnenrolled.toString());
                                                    keyValUnenrolled.put("reqObject",jsonArrayUnenrolled);
                                                    try {
                                                        jsonBodyUnenrolled.put("reqObject",jsonArrayUnenrolled);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    Log.e("json formedUN", String.valueOf(jsonBodyUnenrolled));



                                                    //vollyRequestForPostGPSLocation(urlpostoverseas, keyValOverseas);
                                                    StringWhichloop = "loopUN";
                                                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                                            .permitAll()
                                                            .build();
                                                    StrictMode.setThreadPolicy(policy);

                                                    new SendJsonDataToServer().execute(urlunenrolled,String.valueOf(jsonBodyUnenrolled),String.valueOf(StringWhichloop));
                                                }

                                            }
                                        });
                                        dialogg.show();

                                    }
                                });
                                dialog.show();




                            } else {
                                Toast.makeText(getApplicationContext(), "Authentication Failed,Please Re-login", Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = prefs.edit();
                                //ditor.putString("BLO_ID", BLO_ID);
                                editor.putString("IS_AUTH","false");
                                editor.commit();
                                Intent intent = new Intent(FieldVisitDashboard.this, LoginActivity.class);
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
