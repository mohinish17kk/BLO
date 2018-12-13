package mgov.gov.in.blohybrid;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import mgov.gov.in.blohybrid.Constants;

public class RevisionActivity extends AppCompatActivity {

    TextView tvhome,tv_check_list_verify,tv_Logical_error,tv_dse,tv_register_death,tv_migration_verify,
             tv_replacement_of_poor_quality_photo, tv_verify_reported_death,tv_verify_multiple_entries,tv_verify_permanently_shifted;

    String bundledata,mob,stcode,acno,partno,url;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences prefs;
    RequestQueue queue;
    ProgressDialog pd;
    String SharedKey,IsAuthenticated,buttonClicked,IP_HEADER;

    JSONObject jsonBody = new JSONObject();
    Map<String, String> keyVal = new LinkedHashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revision);
        tvhome = (TextView) findViewById(R.id.tvhome);

        tv_check_list_verify = (TextView) findViewById(R.id.option1);
        tv_Logical_error = (TextView) findViewById(R.id.option2);
        tv_dse = (TextView) findViewById(R.id.option3);
        tv_register_death = (TextView) findViewById(R.id.option4);
        tv_migration_verify = (TextView) findViewById(R.id.option5);
        tv_replacement_of_poor_quality_photo = (TextView) findViewById(R.id.option6);
        tv_verify_reported_death = (TextView) findViewById(R.id.option7);
        tv_verify_multiple_entries = (TextView) findViewById(R.id.option9);
        tv_verify_permanently_shifted = (TextView) findViewById(R.id.option8);

        prefs = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedKey = prefs.getString("Key","");
        Log.d("SharedKey",SharedKey);
        mob  = prefs.getString("mob","");
        stcode = prefs.getString("st_code","");
        acno = prefs.getString("AcNo","");
        partno = prefs.getString("PartNo","");
        Log.d("mobedf",mob);
        Log.d("st_code",stcode);
        Log.d("AcNo",acno);
        Log.d("PartNo",partno);

        IP_HEADER = Constants.IP_HEADER;

        url = IP_HEADER+"IsAccessTokenExpired?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno;
        Log.d("url",url);

        queue = Volley.newRequestQueue(this);
        pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getApplicationContext().getResources().getString(R.string.Please_Wait));
        pd.setCancelable(false);

        keyVal.put("ST_CODE",stcode);
        keyVal.put("AC_NO",acno);
        keyVal.put("PART_NO",partno);
        try {
            jsonBody.put("ST_CODE",stcode);
            jsonBody.put("AC_NO",acno);
            jsonBody.put("PART_NO",partno);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("json formed",jsonBody.toString());
        //vollyRequestForPost(url, keyVal);


        tv_check_list_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ConnectionStatus.isNetworkConnected(RevisionActivity.this)) {
                    buttonClicked = "tv_check_list_verify";
                    vollyRequestForPost(url, keyVal);
                } else {
                    Intent intent = new Intent(getApplicationContext(), NoNetwork.class);
                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }


            }
        });

        tv_Logical_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ConnectionStatus.isNetworkConnected(RevisionActivity.this)) {
                    buttonClicked = "tv_Logical_error";
                    vollyRequestForPost(url, keyVal);
                } else {
                    Intent intent = new Intent(getApplicationContext(), NoNetwork.class);
                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }


            }
        });

        tv_dse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(ConnectionStatus.isNetworkConnected(RevisionActivity.this)) {
                    buttonClicked = "tv_dse";
                    vollyRequestForPost(url, keyVal);
                } else {
                    Intent intent = new Intent(getApplicationContext(), NoNetwork.class);
                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

            }
        });

        tv_register_death.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ConnectionStatus.isNetworkConnected(RevisionActivity.this)) {
                    buttonClicked = "tv_register_death";
                    vollyRequestForPost(url, keyVal);
                } else {
                    Intent intent = new Intent(getApplicationContext(), NoNetwork.class);
                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

            }
        });

        tv_migration_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ConnectionStatus.isNetworkConnected(RevisionActivity.this)) {
                    buttonClicked = "tv_migration_verify";
                    vollyRequestForPost(url, keyVal);
                } else {
                    Intent intent = new Intent(getApplicationContext(), NoNetwork.class);
                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

            }
        });

        tv_replacement_of_poor_quality_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent=new Intent(RevisionActivity.this,Veri.class);
                startActivity(intent);*/
                Toast.makeText(getApplicationContext(),"Under Development",Toast.LENGTH_SHORT).show();
            }
        });

        tv_verify_multiple_entries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ConnectionStatus.isNetworkConnected(RevisionActivity.this)) {
                    buttonClicked = "tv_verify_multiple_entries";
                    vollyRequestForPost(url, keyVal);
                } else {
                    Intent intent = new Intent(getApplicationContext(), NoNetwork.class);
                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

            }
        });
        tv_verify_permanently_shifted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(ConnectionStatus.isNetworkConnected(RevisionActivity.this)) {
                    buttonClicked = "tv_verify_permanently_shifted";
                    vollyRequestForPost(url, keyVal);
                } else {
                    Intent intent = new Intent(getApplicationContext(), NoNetwork.class);
                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

            }
        });
        tv_verify_reported_death.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ConnectionStatus.isNetworkConnected(RevisionActivity.this)) {
                    buttonClicked = "tv_verify_reported_death";
                    vollyRequestForPost(url, keyVal);
                } else {
                    Intent intent = new Intent(getApplicationContext(), NoNetwork.class);
                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

            }
        });



        tvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RevisionActivity.this, WelcomeBLONew.class);
                startActivity(intent);
                finish();
            }
        });
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
                            Log.d("IsAuthenticated",IsAuthenticated);
                            if(IsAuthenticated=="true" || IsAuthenticated.equals("true")){
                                Log.d("IsAuthenticated",IsAuthenticated);
                                if(buttonClicked == "tv_check_list_verify"){
                                    Intent intent=new Intent(RevisionActivity.this,CheckListVerificationActivity.class);
                                    startActivity(intent);
                                }
                                if(buttonClicked == "tv_Logical_error"){
                                    Intent intent=new Intent(RevisionActivity.this,LogicalErrorActivity.class);
                                    startActivity(intent);
                                }
                                if(buttonClicked == "tv_dse"){
                                    Intent intent=new Intent(RevisionActivity.this,DSEActivity.class);
                                    startActivity(intent);
                                }
                                if(buttonClicked == "tv_register_death"){
                                    Intent intent=new Intent(RevisionActivity.this,RegisteredDeathActivity.class);
                                    startActivity(intent);
                                }
                                if(buttonClicked == "tv_migration_verify"){
                                    Intent intent=new Intent(RevisionActivity.this,MigrationVerifyActivity.class);
                                    startActivity(intent);
                                }
                                if(buttonClicked == "tv_verify_multiple_entries"){
                                    Intent intent=new Intent(RevisionActivity.this,Verification_RD_RME_PS.class);
                                    Bundle mBundle = new Bundle();
                                    bundledata = "me";
                                    mBundle.putString("bundledata", bundledata);
                                    Log.d("bundledata",bundledata);
                                    intent.putExtras(mBundle);
                                    startActivity(intent);
                                }

                                if(buttonClicked == "tv_verify_permanently_shifted"){
                                    Intent intent=new Intent(RevisionActivity.this,Verification_RD_RME_PS.class);
                                    Bundle mBundle = new Bundle();
                                    bundledata = "ps";
                                    mBundle.putString("bundledata", bundledata);
                                    Log.d("bundledata",bundledata);
                                    intent.putExtras(mBundle);
                                    startActivity(intent);
                                }

                                if(buttonClicked == "tv_verify_reported_death"){
                                    Intent intent=new Intent(RevisionActivity.this,Verification_RD_RME_PS.class);
                                    Bundle mBundle = new Bundle();
                                    bundledata = "rd";
                                    mBundle.putString("bundledata", bundledata);
                                    Log.d("bundledata",bundledata);
                                    intent.putExtras(mBundle);
                                    startActivity(intent);
                                }


                            } else {
                                Toast.makeText(getApplicationContext(), "Authentication Failed,Please Re-login", Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = prefs.edit();
                                //ditor.putString("BLO_ID", BLO_ID);
                                editor.putString("IS_AUTH","false");
                                editor.commit();
                                Intent intent = new Intent(RevisionActivity.this, LoginActivity.class);
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

    private void showProgressDialog() {
        if (!pd.isShowing())
            pd.show();
    }

    private void hideProgressDialog() {
        if (pd.isShowing())
            pd.dismiss();
    }

}
