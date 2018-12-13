package mgov.gov.in.blohybrid;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static mgov.gov.in.blohybrid.Constants.buttonclr;

public class VoterListFamily extends Activity {
    DatabaseHelper myDb;

    ListView listView;
    StringBuffer sb = new StringBuffer();
    StringBuffer sb1 = new StringBuffer();
    RequestQueue queue;
    ProgressDialog pd;
    Button btngps,btnsubmit,btnskip;
    String HOF_GENDER,HOF_SLNO,HOF_RLN_SLNO,HOF_RLN_GENDER,S_SLNO,DU_SLNO,DM_SLNO, s, CaptureOrSkip="",totalStringBuffer,sbcom;
    String HOF_NEW_GENDER,HOF_NEW_SLNO,HOF_NEW_RLN_SLNO,HOF_NEW_RLN_GENDER,S_NEW_SLNO,DU_NEW_SLNO,DM_NEW_SLNO;
    TextView tvgps,tvhome,tvfetchedgpslocationmanually,tvRationalization;
    double lat,lon;
    //String url = "http://117.239.180.198/BLONet_API_Test/api/blo/SearchElectoral?st_code=S02&ac_no=1&part_no=20";
      String url,IP_HEADER,urlgps,langlong=""; //= "http://117.239.180.198/BLONet_API_Test/api/blo/SearchElectoral?st_code=S02&ac_no=1&part_no=20";
    Map<String, String> keyVal = new LinkedHashMap<String, String>();

    ArrayList<HashMap<String, String>> dataAL;
    ArrayList<String> serialArray ;
    String mob,stcode,acno,partno,AddressPin;
    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_list_family);

        btngps = (Button) findViewById(R.id.btn_submitform6);
        btnsubmit = (Button) findViewById(R.id.btn_submit);
        btnskip = (Button) findViewById(R.id.btn_skip);
        tvgps = (TextView) findViewById(R.id.tvfetchedgpslocation);
        tvhome = (TextView) findViewById(R.id.tvhome);
        tvfetchedgpslocationmanually = (TextView) findViewById(R.id.tvfetchedgpslocationmanually);
        tvRationalization = (TextView) findViewById(R.id.tvRationalization);
        myDb = new DatabaseHelper(this);

        //getterSetter obj = new getterSetter();
        HOF_GENDER = getterSetter.gs.getHOF_GENDER();
        HOF_SLNO = getterSetter.gs.getHOF_SLNO();
        HOF_RLN_GENDER = getterSetter.gs.getHOF_RLN_GENDER();
        HOF_RLN_SLNO = getterSetter.gs.getHOF_RLN_SLNO();
        S_SLNO = getterSetter.gs.getS_SLNO();
        DM_SLNO = getterSetter.gs.getDM_SLNO();
        DU_SLNO = getterSetter.gs.getDU_SLNO();
        Log.d("HOF_GENDER",HOF_GENDER);
        Log.d("HOF_SLNO",HOF_SLNO);
        Log.d("HOF_RLN_GENDER",HOF_RLN_GENDER);
        Log.d("HOF_RLN_SLNO",HOF_RLN_SLNO);
        Log.d("DM_SLNO",DM_SLNO);
        Log.d("DU_SLNO",DU_SLNO);
        IP_HEADER = Constants.IP_HEADER;

        totalStringBuffer = getterSetter.gs.getSTRINGBUFFER();
        sbcom = getterSetter.gs.getSTRINGBUFFERCOM();
       // Log.d("sbcomvlf",sbcom);
        Log.d("getterSetterVLF",HOF_GENDER+" "+HOF_SLNO+" "+HOF_RLN_GENDER+" "+HOF_RLN_SLNO+" "+S_SLNO+" "+DM_SLNO+" "+DU_SLNO+" "+totalStringBuffer);

        prefs = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mob  = prefs.getString("mob","");
        stcode = prefs.getString("st_code","");
        acno = prefs.getString("AcNo","");
        partno = prefs.getString("PartNo","");
        //url = "http://117.239.180.198/BLONet_API_Dev/api/blo/Post_Existing_PollingStationDetails?st_code="+stcode+"&mobile_no="+mob+"&ac_no="+acno+"&part_no="+partno;

        Log.d("mobedf",mob);
        Log.d("st_code",stcode);
        Log.d("AcNo",acno);
        Log.d("PartNo",partno);
        //http://117.239.180.198/BLONet_API_Dev/api/blo/SearchElectoral?st_code=S02&ac_no=1&part_no=20&HOF_GENDER=M&HOF_SLNO=285&HOF_RLN_GENDER=F&HOF_RLN_SLNO=319&S_SLNO=286,288,287&DM_SLNO=292,293&DU_SLNO=295,294,297
        //url = "http://eronet.ecinet.in/services/api/blonet/SearchElectoral?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno+"&HOF_GENDER="+HOF_GENDER+"&HOF_SLNO="+HOF_SLNO+"&HOF_RLN_GENDER="+HOF_RLN_GENDER+"&HOF_RLN_SLNO="+HOF_RLN_SLNO+"&S_SLNO="+S_SLNO+"&DM_SLNO="+DM_SLNO+"&DU_SLNO="+DU_SLNO;
       // Log.d("getURL",url);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            HOF_NEW_GENDER = extras.getString("HOF_GENDER");
            HOF_NEW_SLNO = extras.getString("HOF_SLNO");
            HOF_NEW_RLN_GENDER = extras.getString("HOF_RLN_GENDER");
            HOF_NEW_RLN_SLNO = extras.getString("HOF_RLN_SLNO");
            S_NEW_SLNO = extras.getString("S_SLNO");
            DM_NEW_SLNO = extras.getString("DM_SLNO");
            DU_NEW_SLNO = extras.getString("DU_SLNO");
            Log.e("HOF_NEW_GENDER",HOF_NEW_GENDER);
            Log.e("HOF_NEW_SLNO",HOF_NEW_SLNO);
            Log.e("HOF_NEW_RLN_GENDER",HOF_NEW_RLN_GENDER);
            Log.e("HOF_NEW_RLN_SLNO",HOF_NEW_RLN_SLNO);
            Log.e("S_NEW_SLNO",S_NEW_SLNO);
            Log.e("DM_NEW_SLNO",DM_NEW_SLNO);
            Log.e("DU_NEW_SLNO",DU_NEW_SLNO);
        }

        if(buttonclr.equals("disable") || buttonclr=="disable"){
            btngps.setClickable(false);
            btnsubmit.setClickable(false);
            btnskip.setClickable(false);
            btngps.setBackgroundResource(R.drawable.btn_background_disable);
            btnsubmit.setBackgroundResource(R.drawable.btn_background_disable);
            btnskip.setBackgroundResource(R.drawable.btn_background_disable);
        }

        queue = Volley.newRequestQueue(this);
        pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getApplicationContext().getResources().getString(R.string.Please_Wait));
        pd.setCancelable(false);
        showProgressDialog();

        dataAL = new ArrayList<>();
        listView = (ListView) findViewById(R.id.voterListView);

        tvRationalization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Rationalization.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("sb1", sb1.toString());
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });


        tvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VoterListFamily.this, WelcomeBLONew.class);
                startActivity(intent);
                finish();
            }
        });

        btnskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buttonclr.equals("enable") || buttonclr == "enable"){
                    tvgps.setText("");
                    CaptureOrSkip = "SKIP";
                    Log.d("SKIP",CaptureOrSkip);
                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Family_serial_number_is_collected_without_GPS_location),Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Now_Click_on_SUBMIT_button_to_upload_the_information),Toast.LENGTH_LONG).show();
                }else {Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Family_serial_number_already_submitted),Toast.LENGTH_SHORT).show();}


            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   // if((tvgps.getText().toString().isEmpty()) && CaptureOrSkip.isEmpty())
                    if(CaptureOrSkip.isEmpty() && tvfetchedgpslocationmanually.getText().toString().isEmpty())
                    {
                        Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.CAPTURE_or_SKIP_family_gps_Location_first_Else_enter_gps_location_manually),Toast.LENGTH_LONG).show();

                    } else if(buttonclr.equals("enable") || buttonclr=="enable")
                     {
                    submit();
                      }
                    else { Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Detail_already_submitted),Toast.LENGTH_SHORT).show();}


            }
        });

        ActivityCompat.requestPermissions(VoterListFamily.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        btngps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (buttonclr.equals("enable") || buttonclr == "enable") {

                    final Dialog dialog = new Dialog(VoterListFamily.this);
                    dialog.setContentView(R.layout.gpswarning);
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
                            Log.d("1", "1");
                            GPSTracker g = new GPSTracker(getApplicationContext());
                            if (g.isGPSEnabled) {
                                Log.d("1", "2");
                                Location l = g.getLocation();
                                Log.d("1", "3");
                                if (l != null) {
                                    Log.d("1", "4");
                                    lat = g.getLatitude();
                                    lon = g.getLongitude();
                                    tvgps.setText("Lat  " + lat + "  Lon  " + lon);
                                    Log.d("lat long", "Lat  " + lat + "  Lon  " + lon);
                                    Log.d("1", "5");
                                    CaptureOrSkip = "CAPTURE";
                                    Log.d("CAPTURE",CaptureOrSkip);
                                    langlong = lat+","+lon;
                                }
                            } else {
                                Toast.makeText(VoterListFamily.this, getApplicationContext().getResources().getString(R.string.Please_Enable_GPS), Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();

                        }
                    });
                    dialog.show();


                } else {Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.GPS_location_already_submitted),Toast.LENGTH_SHORT).show();}
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String sname = ((TextView) view.findViewById(R.id.tvname)).getText().toString();
                String srlnname = ((TextView) view.findViewById(R.id.tvrlnname)).getText().toString();
                String sepic = ((TextView) view.findViewById(R.id.tvepicno)).getText().toString();
                String sgender = ((TextView) view.findViewById(R.id.tvgender)).getText().toString();
                String sserial = ((TextView) view.findViewById(R.id.tvserialno)).getText().toString();
                String sage = ((TextView) view.findViewById(R.id.tvage)).getText().toString();
                String saddress = ((TextView) view.findViewById(R.id.tvaddress)).getText().toString();
                Log.d("voterlistfamily","sname- "+sname+" sepic- "+sepic+" sgender- "+sgender+" sserial- "+sserial+" sage- "+sage+" saddress- "+saddress + "srlnname-"+srlnname);
                Intent intent = new Intent(getApplicationContext(), VoterFullDetail.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("sname", sname);
                mBundle.putString("sepic", sepic);
                mBundle.putString("sgender", sgender);
                mBundle.putString("sserial", sserial);
                mBundle.putString("sage", sage);
                mBundle.putString("address",saddress);
                mBundle.putString("srlnname",srlnname);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });

        //new RequestTask1().execute(url);
        //String a = HOF_SLNO + HOF_RLN_SLNO + S_SLNO  + DU_SLNO  + DM_SLNO;

//        Log.d("search condition",totalStringBuffer);

     JSONArray jsonArray = myDb.SelectSearchedData(totalStringBuffer);
        Log.d("jsonArray",jsonArray.toString());

        //if(String.valueOf(jsonArray.length()).equals("0") || String.valueOf(jsonArray.length()) == "0"){
        if(jsonArray.isNull(0)){
            Log.d("null","null");
            Toast.makeText(getApplicationContext(),"Searched Data Does Not Exist..!!!",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(VoterListFamily.this,WelcomeBLONew.class);
            startActivity(intent);
            finish();

        }
        else {
            try {
                //JSONArray jArray = new JSONArray(jsonArray);
                Log.v("response",jsonArray.toString());
                Log.v("jArray.length()", String.valueOf(jsonArray.length()));


                serialArray = new ArrayList<String>();
                for(int i=0; i < jsonArray.length(); i++) {

                    JSONObject jsreceipt = jsonArray.getJSONObject(i);


                    String SLNOINPART = jsreceipt.getString("SLNOINPART");
                    String EPIC_NO = jsreceipt.getString("EPIC_NO");
                    String Name = jsreceipt.getString("Name");
                    String ADDRESS = jsreceipt.getString("ADDRESS");
                    String GENDER = jsreceipt.getString("GENDER");
                    String AGE = jsreceipt.getString("AGE");
                    String RLN_Name = jsreceipt.getString("RLN_Name");
                    sb1.append(SLNOINPART+",");


                    HashMap<String, String> data = new HashMap<>();
                    data.put("SLNOINPART", SLNOINPART);
                    data.put("EPIC_NO", EPIC_NO);
                    data.put("Name", Name);
                    data.put("ADDRESS",ADDRESS);
                    data.put("GENDER", GENDER);
                    data.put("AGE", AGE);
                    data.put("RLN_Name",RLN_Name);

                    //data.put("TAG_U", urgent);
                    dataAL.add(data);
                    serialArray.add(SLNOINPART);

                    ListAdapter la = new SimpleAdapter(getApplicationContext(), dataAL, R.layout.custom_voter_listview_item,
                            new String[]{"Name", "EPIC_NO", "GENDER", "SLNOINPART", "AGE", "ADDRESS","RLN_Name"},
                            new int[]{R.id.tvname, R.id.tvepicno, R.id.tvgender, R.id.tvserialno, R.id.tvage, R.id.tvaddress, R.id.tvrlnname});
                    listView.setAdapter(la);
                }
                sb1.setLength(sb1.length() - 1);
                Log.d("sb1",sb1.toString());


                btnsubmit.setVisibility(View.VISIBLE);
                btngps.setVisibility(View.VISIBLE);
                btnskip.setVisibility(View.VISIBLE);
                for (int i = 0; i < serialArray.size(); i++) {
                    if (!(serialArray.get(i).isEmpty()))
                        sb.append(serialArray.get(i) + ",");
                }
                if (sb.length() != 0) {
                    sb.setLength(sb.length() - 1);
                    Log.d("StringBuffer", sb.toString());
                }
                s = String.valueOf(sb);
                Log.d("totalStringBuffer",sb.toString());
                Log.d("s",s);


            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("printStackTrace",e.getMessage());
                Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Something_Went_Wrong),Toast.LENGTH_LONG).show();
                Intent intent=new Intent(VoterListFamily.this,WelcomeBLO.class);
                startActivity(intent);
                finish();
            }
            finally {
                hideProgressDialog();
            }

        }




    }
    private void submit() {

        /*if (!validgpslocation()) {

            return;
        }*/
        if (!validserial()) {
            return;
        }
        //urlgps = "http://117.239.180.198/BLONet_API_Dev/api/blo/Post_LocationDetails?st_code=S13&ac_no=1&part_no=1&serial_no="+s+"&lat="+lat+"&lon="+lon;
        if(CaptureOrSkip.equals("SKIP") || CaptureOrSkip=="SKIP" && tvfetchedgpslocationmanually.getText().toString().isEmpty()){
            urlgps= IP_HEADER+"Post_GPSLocation?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno+"&mobile_no="+mob+"&HOF_SLNO="+HOF_SLNO+"&HOF_RLN_SLNO="+HOF_RLN_SLNO+"&S_SLNO="+S_SLNO+"&DM_SLNO="+DM_SLNO+"&DU_SLNO="+DU_SLNO+"&LagLong=";
        } else if(CaptureOrSkip.equals("CAPTURE") || CaptureOrSkip=="CAPTURE" && tvfetchedgpslocationmanually.getText().toString().isEmpty()){
            urlgps= IP_HEADER+"Post_GPSLocation?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno+"&mobile_no="+mob+"&HOF_SLNO="+HOF_SLNO+"&HOF_RLN_SLNO="+HOF_RLN_SLNO+"&S_SLNO="+S_SLNO+"&DM_SLNO="+DM_SLNO+"&DU_SLNO="+DU_SLNO+"&LagLong="+lat+","+lon;
        } else if(!(tvfetchedgpslocationmanually.getText().toString().isEmpty())){
            if(tvfetchedgpslocationmanually.length()>10){
                urlgps= IP_HEADER+"Post_GPSLocation?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno+"&mobile_no="+mob+"&HOF_SLNO="+HOF_SLNO+"&HOF_RLN_SLNO="+HOF_RLN_SLNO+"&S_SLNO="+S_SLNO+"&DM_SLNO="+DM_SLNO+"&DU_SLNO="+DU_SLNO+"&LagLong="+tvfetchedgpslocationmanually.getText().toString();
            } else {
                Toast.makeText(getApplicationContext(),"Invalid GPS location",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Log.d("urlgps",urlgps);
        showProgressDialog();

        boolean isInserted = myDb.insertFamilyGPSLocationData(HOF_NEW_SLNO,HOF_NEW_GENDER,HOF_NEW_RLN_SLNO,HOF_NEW_RLN_GENDER,S_NEW_SLNO,DM_NEW_SLNO,DU_NEW_SLNO,langlong,"false");
        if(isInserted = true){
            final Dialog dialog = new Dialog(VoterListFamily.this);
            dialog.setContentView(R.layout.custom_blo_report_done_message);
            Button btnok = (Button) dialog.findViewById(R.id.btnok);
            dialog.setCanceledOnTouchOutside(false);

            btnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*Intent intent=new Intent(VoterListFamily.this,VoterListFamily.class);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();*/
                    btngps.setClickable(false);
                    btnsubmit.setClickable(false);
                    btnskip.setClickable(false);
                    btngps.setBackgroundResource(R.drawable.btn_background_disable);
                    btnsubmit.setBackgroundResource(R.drawable.btn_background_disable);
                    btnskip.setBackgroundResource(R.drawable.btn_background_disable);
                    tvgps.setText("");
                    buttonclr="disable";
                    dialog.dismiss();
                    hideProgressDialog();

                }
            });
            dialog.show();
            //Toast.makeText(getApplicationContext(),"Data locally saved successfully",Toast.LENGTH_SHORT).show();

        } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Data_not_saved_TRY_AGAIN),Toast.LENGTH_SHORT).show();

        //new RequestTask2().execute(urlgps); // submitting gps location to server

    }

    private boolean validserial() {
        if((s.isEmpty())){
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_List_to_display),Toast.LENGTH_LONG).show();
            return false ;
        }
        return true;
    }

    private boolean validgpslocation() {
        if((tvgps.getText().toString().isEmpty())){
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Capture_GPS_location),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void showProgressDialog() {
        if (!pd.isShowing())
            pd.show();
    }

    private void hideProgressDialog() {
        if (pd.isShowing())
            pd.dismiss();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),WelcomeBLO.class);
        startActivity(intent);
    }

    /*class RequestTask1 extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
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
        protected void onPostExecute(String result) {

            Log.d("On Response : ", result.toString());
            try {
                JSONArray jArray = new JSONArray(result);
                Log.v("response",result.toString());
                Log.v("jArray.length()", String.valueOf(jArray.length()));
                serialArray = new ArrayList<String>();
                for(int i=0; i < jArray.length(); i++) {

                    JSONObject jsreceipt = jArray.getJSONObject(i);


                    String ST_CODE = jsreceipt.getString("ST_CODE");
                    String AC_NO = jsreceipt.getString("AC_NO");
                    String PART_NO = jsreceipt.getString("PART_NO");
                    String SLNOINPART = jsreceipt.getString("SLNOINPART");
                    String EPIC_NO = jsreceipt.getString("EPIC_NO");
                    String Name = jsreceipt.getString("Name");
                    String ADDRESS = jsreceipt.getString("ADDRESS");
                    String RLN_Name = jsreceipt.getString("RLN_Name");
                    String GENDER = jsreceipt.getString("GENDER");
                    String AGE = jsreceipt.getString("AGE");


                    HashMap<String, String> data = new HashMap<>();
                    data.put("ST_CODE", ST_CODE);
                    data.put("AC_NO", AC_NO);
                    data.put("PART_NO", PART_NO);
                    data.put("SLNOINPART", SLNOINPART);
                    data.put("EPIC_NO", EPIC_NO);
                    data.put("Name", Name);
                    data.put("ADDRESS",ADDRESS);
                    data.put("RLN_Name", RLN_Name);
                    data.put("GENDER", GENDER);
                    data.put("AGE", AGE);

                    //data.put("TAG_U", urgent);
                    dataAL.add(data);
                    serialArray.add(SLNOINPART);

                    ListAdapter la = new SimpleAdapter(getApplicationContext(), dataAL, R.layout.custom_voter_listview_item,
                            new String[]{"Name", "EPIC_NO", "GENDER", "SLNOINPART", "AGE", "ADDRESS"},
                            new int[]{R.id.tvname, R.id.tvepicno, R.id.tvgender, R.id.tvserialno, R.id.tvage, R.id.tvaddress});
                    listView.setAdapter(la);
                }


                btnsubmit.setVisibility(View.VISIBLE);
                btngps.setVisibility(View.VISIBLE);
                btnskip.setVisibility(View.VISIBLE);
                for (int i = 0; i < serialArray.size(); i++) {
                    if (!(serialArray.get(i).isEmpty()))
                        sb.append(serialArray.get(i) + ",");
                }
                if (sb.length() != 0) {
                    sb.setLength(sb.length() - 1);
                    Log.d("StringBuffer", sb.toString());
                }
                s = String.valueOf(sb);
                Log.d("totalStringBuffer",sb.toString());
                Log.d("s",s);


            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("printStackTrace",e.getMessage());
                Toast.makeText(getApplicationContext(),"Something Went Wrong",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(VoterListFamily.this,WelcomeBLO.class);
                startActivity(intent);
                finish();
            }
            finally {
                hideProgressDialog();
            }

        }
    }*/

    class RequestTask2 extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
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
                    final Dialog dialog = new Dialog(VoterListFamily.this);
                    dialog.setContentView(R.layout.custom_blo_report_done_message);
                    Button btnok = (Button) dialog.findViewById(R.id.btnok);
                    dialog.setCanceledOnTouchOutside(false);
                    btnok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            /*Intent intent=new Intent(VoterListFamily.this,WelcomeBLONew.class);
                            startActivity(intent);
                            finish();*/
                                btngps.setClickable(false);
                                btnsubmit.setClickable(false);
                                btnskip.setClickable(false);
                                btngps.setBackgroundResource(R.drawable.btn_background_disable);
                                btnsubmit.setBackgroundResource(R.drawable.btn_background_disable);
                                btnskip.setBackgroundResource(R.drawable.btn_background_disable);
                                tvgps.setText("");
                            buttonclr="disable";


                            dialog.dismiss();

                        }
                    });
                    dialog.show();


                } else
                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.No_Response_from_Server), Toast.LENGTH_LONG).show();



            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                hideProgressDialog();
            }

        }
    }

}
