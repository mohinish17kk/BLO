package mgov.gov.in.blohybrid;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;
import mgov.gov.in.blohybrid.Constants;

public class PoliceGPSLocation extends AppCompatActivity {
    EditText etpollingaddress,etpollingaddress2,etpollingaddres3,etpollingaddress4;
    Button btn_submit;
    TextView tvpollinggps, tvfetched,tvfetched1, tvhome,tvshow,etPolicemob;
    CheckBox cbamf;
    DatabaseHelper myDb;
    ProgressDialog pd;
    RequestQueue queue;
    JSONObject jsonBody = new JSONObject();
    String slatlong,AddressPin,sHCmob;
    String url;//="http://117.239.180.198/BLONet_API_Test/api/blo/Post_PostOfficeAddressLocation?st_code=S24&ac_no=1&part_no=1";
    Map<String, String> keyVal = new LinkedHashMap<String, String>();
    String completeaddress,IP_HEADER;

    String mob,stcode,acno,partno;
    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_gpslocation);

        tvpollinggps = (TextView) findViewById(R.id.pollinggps);
        btn_submit = (Button) findViewById(R.id.btn_submitform6);
        etpollingaddress = (EditText) findViewById(R.id.etpostaddress);
        etpollingaddress2 = (EditText) findViewById(R.id.etpollingaddress2);
        etpollingaddres3 = (EditText) findViewById(R.id.etpollingaddres3);
        etpollingaddress4 = (EditText) findViewById(R.id.etpollingaddress4);
        etPolicemob = (EditText) findViewById(R.id.etPolicemob);
        tvfetched = (TextView) findViewById(R.id.tvfetchedgpslocation);
        tvfetched1 = (TextView) findViewById(R.id.tvfetchedgpslocation1);
        tvhome = (TextView) findViewById(R.id.tvhome);
        tvshow = (TextView) findViewById(R.id.tvshow);

        myDb = new DatabaseHelper(this);

        prefs = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mob  = prefs.getString("mob","");
        stcode = prefs.getString("st_code","");
        acno = prefs.getString("AcNo","");
        partno = prefs.getString("PartNo","");

        Log.d("mobedf",mob);
        Log.d("st_code",stcode);
        Log.d("AcNo",acno);
        Log.d("PartNo",partno);
        IP_HEADER = Constants.IP_HEADER;
        url = IP_HEADER+"Post_PostOfficeAddressLocation?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno;

        tvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PoliceGPSLocation.this,WelcomeBLONew.class);
                startActivity(intent);
                finish();
            }
        });

        tvshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog();
                Cursor cursor = myDb.getAllDatafromTable12();
                if(cursor.getCount() == 0 ){
                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Nothing_to_show),Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while (cursor.moveToNext()){
                    stringBuffer.append("AddressLine1 : "+ cursor.getString(0)+"\n");
                    stringBuffer.append("AddressLine2 : "+ cursor.getString(1)+"\n");
                    stringBuffer.append("AddressLine3 : "+ cursor.getString(2)+"\n");
                    stringBuffer.append("PO_BULDING_LAT_LONG : "+ cursor.getString(3)+"\n");
                    stringBuffer.append("Mobile_Number : "+ cursor.getString(4)+"\n\n");
                }
                hideProgressDialog();
                showmessage("Data",stringBuffer.toString());

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();

            }
        });

        pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getApplicationContext().getResources().getString(R.string.Please_Wait));
        pd.setCancelable(false);
        queue = Volley.newRequestQueue(this);


        ActivityCompat.requestPermissions(PoliceGPSLocation.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);
        tvpollinggps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("1","1");
                GPSTracker g = new GPSTracker(getApplicationContext());
                if (g.isGPSEnabled){
                    Log.d("1","2");
                    Location l = g.getLocation();
                    Log.d("1","3");
                    if(l!=null)
                    {
                        Log.d("1","4");
                        double lat = l.getLatitude();
                        double lon = l.getLongitude();
                        //Toast.makeText(getApplicationContext(),"Lat  "+lat+"  Lon  "+lon,Toast.LENGTH_LONG).show();
                        tvfetched.setText("Lat  "+lat+"  Lon  "+lon);
                        Log.d("lat long","Lat  "+lat+"  Lon  "+lon);
                        Log.d("1","5");
                        slatlong = String.valueOf(lat)+","+String.valueOf(lon);
                        Log.d("slatlong",slatlong);
                    }
                } else {
                    Toast.makeText(PoliceGPSLocation.this, getApplicationContext().getResources().getString(R.string.Please_Enable_GPS), Toast.LENGTH_SHORT).show();
                }
                /*if(l.equals(null)){
                Toast.makeText(getApplicationContext(),"null",Toast.LENGTH_SHORT).show();
            } Log.d("l", l.toString());*/
            }
        });
    }

    private void submit(){
        if(!address1()){
            return;
        }
        if(!address2()){
            return;
        }
        if(!address3()){
            return;
        }
        if(!pincode()){
            return;

        }
        /*if(!areapincode())
        {
            return;
        }*/
        if(!GPS()){
            return;
        }
        if(!mobile()){
            return;
        }
        if(etPolicemob.getText().toString().isEmpty()){
            sHCmob = "";
        }else sHCmob = etPolicemob.getText().toString();
        AddressPin = etpollingaddres3.getText().toString()+","+etpollingaddress4.getText().toString();
        Log.d("AddressPin",AddressPin);

        keyVal.put("AddressLine1",etpollingaddress.getText().toString());
        keyVal.put("AddressLine2",etpollingaddress2.getText().toString());
        keyVal.put("AddressLine3",AddressPin.toString());
        keyVal.put("PO_BULDING_LAT_LONG",slatlong.toString());

        try {
            jsonBody.put("AddressLine1",etpollingaddress.getText().toString());
            jsonBody.put("AddressLine2",etpollingaddress2.getText().toString());
            jsonBody.put("AddressLine3",AddressPin.toString());
            jsonBody.put("PO_BULDING_LAT_LONG",slatlong.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("json formed",jsonBody.toString());


        Log.d("keyVal ",etpollingaddress.getText().toString()+" "+etpollingaddress2.getText().toString()+" "+AddressPin
                +" "+slatlong.toString());

        boolean isInserted = myDb.insertPOLICELocationData(etpollingaddress.getText().toString(),etpollingaddress2.getText().toString(),AddressPin.toString(),
                slatlong.toString(),sHCmob,"false");
        if(isInserted = true){
            final Dialog dialog = new Dialog(PoliceGPSLocation.this);
            dialog.setContentView(R.layout.custom_blo_report_done_message);
            Button btnok = (Button) dialog.findViewById(R.id.btnok);
            dialog.setCanceledOnTouchOutside(false);

            btnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(PoliceGPSLocation.this,WelcomeBLONew.class);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();

                }
            });
            dialog.show();
            //Toast.makeText(getApplicationContext(),"Data locally saved successfully",Toast.LENGTH_SHORT).show();

        } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Data_not_saved_TRY_AGAIN),Toast.LENGTH_SHORT).show();

        //vollyRequestForPost(url, keyVal);
    }

    private boolean address1() {
        if(etpollingaddress.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Empty_Flat_HouseNo_Floor_Building_is_not_allowed), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private boolean address2() {
        if(etpollingaddress2.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Empty_Colony_Street_Locality_is_not_allowed), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean address3() {
        if(etpollingaddres3.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Empty_City_State_is_not_allowed), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean pincode() {
        if(etpollingaddress4.getText().toString().isEmpty() || etpollingaddress4.length()!=6)
        {
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Invalid_Pincode), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


    private boolean GPS() {
        if(tvfetched.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Capture_GPS_location),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private boolean mobile() {
        if(!(etPolicemob.getText().toString().isEmpty()) && etPolicemob.length()<10)
        {
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Enter_Valid_Contact_Number),Toast.LENGTH_LONG).show();
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

    private void showmessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
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
                            String IsSuccess = response.getString("IsSuccess");
                            String Message = response.getString("Message");
                            Log.d("IsSuccess",IsSuccess);
                            Log.d("Message",Message);

                            Log.v("Response : ", "IsSuccess : " + IsSuccess);

                            if(IsSuccess.trim().equals("true") || IsSuccess.trim().equals("true")) {
                                final Dialog dialog = new Dialog(PoliceGPSLocation.this);
                                dialog.setContentView(R.layout.custom_blo_report_done_message);
                                Button btnok = (Button) dialog.findViewById(R.id.btnok);
                                dialog.setCanceledOnTouchOutside(false);




                                btnok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent=new Intent(PoliceGPSLocation.this,WelcomeBLONew.class);
                                        startActivity(intent);
                                        finish();
                                        dialog.dismiss();

                                    }
                                });
                                dialog.show();



                            } else
                                Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.No_Response_from_Server), Toast.LENGTH_LONG).show();



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
        queue.add(jsObjRequest);
    }


}
