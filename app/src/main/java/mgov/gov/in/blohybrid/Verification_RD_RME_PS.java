package mgov.gov.in.blohybrid;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
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
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import mgov.gov.in.blohybrid.Constants;

public class Verification_RD_RME_PS extends AppCompatActivity {

    ListView listView;
    RequestQueue queue;
    ProgressDialog pd;
    TextView tvhome,textView;
    ArrayList<HashMap<String, String>> dataAL;
    SharedPreferences prefs;
    String mob,stcode,acno,partno,url,url1,isVerified,BLO_NAME;
    String sauto,bloremark,verifiedBy,SharedKey,IP_HEADER;
    public static final String MyPREFERENCES = "MyPrefs";
    RadioButton rbverified,rbnotverified;
    EditText remark;
    JSONObject jsonBody = new JSONObject();
    Map<String, String> keyVal = new LinkedHashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification__rd__rme__ps);
        final Dialog dialogg = new Dialog(Verification_RD_RME_PS.this);
        textView = (TextView) findViewById(R.id.textView);

        String bundledata = getIntent().getExtras().getString("bundledata");
        String asdtype="";
        Log.d("bundledata1",bundledata);
        if(bundledata.toString().equals("me") || bundledata.toString()=="me"){
            textView.setText("Reported Multiple Entries");
            asdtype = "4";
        } else if(bundledata.toString().equals("ps") || bundledata.toString()=="ps"){
            textView.setText("Permanently Shifted");
            asdtype = "2";
        } else if(bundledata.toString().equals("rd") || bundledata.toString()=="rd"){
            textView.setText("Reported Death");
            asdtype = "3";
        }

        tvhome = (TextView) findViewById(R.id.tvhome);
        prefs = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mob  = prefs.getString("mob","");
        stcode = prefs.getString("st_code","");
        acno = prefs.getString("AcNo","");
        partno = prefs.getString("PartNo","");
        BLO_NAME = prefs.getString("BLO_NAME","");
        verifiedBy = BLO_NAME+" | "+mob;
        Log.d("verifiedBy",verifiedBy);
        SharedKey = prefs.getString("Key","");
        Log.d("SharedKey",SharedKey);

        Log.d("mobedf",mob);
        Log.d("st_code",stcode);
        Log.d("AcNo",acno);
        Log.d("PartNo",partno);
        Log.d("BLO_NAME",BLO_NAME);
        IP_HEADER = Constants.IP_HEADER;

        //url = "http://eronet.ecinet.in/services/api/BLONet/Get_DSERecords?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno+"&mobile_no="+mob;
        url = IP_HEADER+"Get_ASD_Details?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno+"&asd_type="+asdtype;
        Log.d("url",url);

        queue = Volley.newRequestQueue(this);
        pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getApplicationContext().getResources().getString(R.string.Please_Wait));
        pd.setCancelable(false);


        listView = (ListView) findViewById(R.id.RD_RME_PS_ListView);
        dataAL = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sauto = ((TextView) view.findViewById(R.id.tvAUTO_ID)).getText().toString();
                Log.d("sauto",sauto);

                dialogg.setContentView(R.layout.custom_vrified_not_verified);
                rbverified = (RadioButton) dialogg.findViewById(R.id.rbverified);
                rbnotverified = (RadioButton) dialogg.findViewById(R.id.rbnotverified);
                remark= (EditText) dialogg.findViewById(R.id.remark);
                remark.setVisibility(View.VISIBLE);
                Button btnsubmit = (Button) dialogg.findViewById(R.id.btnsubmit);
                dialogg.setCanceledOnTouchOutside(false);

                rbverified.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(getApplicationContext(),"male cheked",Toast.LENGTH_LONG).show();
                        isVerified = "true";
                        rbnotverified.setChecked(false);


                    }
                });

                rbnotverified.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(getApplicationContext(),"male cheked",Toast.LENGTH_LONG).show();
                        isVerified = "false";
                        rbverified.setChecked(false);

                    }
                });

                btnsubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!(rbverified.isChecked() || rbnotverified.isChecked())) {
                            Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.Select_Verified_OR_Not_Verified), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        dialogg.dismiss();
                        bloremark = remark.getText().toString();
                        url1 = IP_HEADER+"Post_ASD_IsVerified?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno;
                        Log.d("url1", url1);
                        keyVal.put("AUTO_ID",sauto);
                        keyVal.put("IsVerified",isVerified);
                        keyVal.put("VERIFICATION_REMARK",bloremark);
                        keyVal.put("VERIFIED_BY",verifiedBy);

                        try {
                            jsonBody.put("AUTO_ID",sauto);
                            jsonBody.put("IsVerified",isVerified);
                            jsonBody.put("VERIFICATION_REMARK",bloremark);
                            jsonBody.put("VERIFIED_BY",verifiedBy);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("json formed",jsonBody.toString());
                        vollyRequestForPost(url1, keyVal);
                    }
                });

                dialogg.show();


                    }
        });

        tvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Verification_RD_RME_PS.this, WelcomeBLONew.class);
                startActivity(intent);
                finish();
            }
        });

        showProgressDialog();
        if(ConnectionStatus.isNetworkConnected(Verification_RD_RME_PS.this)) {
            new RequestTask1().execute(url);
        } else {
            Intent intent=new Intent(getApplicationContext(),NoNetwork.class);
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }



    }

    class RequestTask1 extends AsyncTask<String, String, String> {

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
                JSONArray jArray = new JSONArray(result);
                Log.v("response",result.toString());
                Log.v("jArray.length()", String.valueOf(jArray.length()));
                if(String.valueOf(jArray.length()).equals("0") || String.valueOf(jArray.length())=="0"){
                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_data_to_display),Toast.LENGTH_SHORT).show();
                    finish();
                }
                for(int i=0; i < jArray.length(); i++) {

                    JSONObject jsreceipt = jArray.getJSONObject(i);


                    String FMNAME_EN = jsreceipt.getString("UserName");
                    //String LASTNAME_EN = jsreceipt.getString("LASTNAME_EN");
                    String EPIC_NO = jsreceipt.getString("EPIC_NO");
                    String GENDER = jsreceipt.getString("GENDER");
                    String SerialNo = jsreceipt.getString("SerialNo");
                    String RLN_NAME = jsreceipt.getString("RLN_NAME");
                    String RLN_TYPE = jsreceipt.getString("RLN_TYPE");
                    String AUTO_ID = jsreceipt.getString("AUTO_ID");

                    HashMap<String, String> data = new HashMap<>();
                    if(!FMNAME_EN.equals("null") || FMNAME_EN!="null"){
                        data.put("NAME", FMNAME_EN);
                    } else data.put("NAME", "--");
                    //data.put("NAME", FMNAME_EN);
                    if(!EPIC_NO.equals("null") || EPIC_NO!="null"){
                        data.put("EPIC_NO", EPIC_NO);
                    } else data.put("EPIC_NO", "--");
                    //data.put("EPIC_NO", EPIC_NO);
                    if(!GENDER.equals("null") || GENDER!="null"){
                        data.put("GENDER", GENDER);
                    } else data.put("GENDER", "--");
                    //data.put("GENDER", GENDER);
                    if(!RLN_NAME.equals("null") || RLN_NAME!="null"){
                        data.put("RLN_NAME", RLN_NAME);
                    } else data.put("RLN_NAME", "--");
                    //data.put("RLN_NAME", RLN_NAME);
                    if(!SerialNo.equals("null") || SerialNo!="null"){
                        data.put("SerialNo", SerialNo);
                    } else data.put("SerialNo", "--");
                    //data.put("SerialNo", SerialNo);
                    if(!RLN_TYPE.equals("null") || RLN_TYPE!="null"){
                        data.put("RLN_TYPE", RLN_TYPE);
                    } else data.put("RLN_TYPE", "--");
                    //data.put("RLN_TYPE", RLN_TYPE);
                    if(!AUTO_ID.equals("null") || AUTO_ID!="null"){
                        data.put("AUTO_ID", AUTO_ID);
                    } else data.put("AUTO_ID", "--");
                    //data.put("AUTO_ID",AUTO_ID);

                    //data.put("TAG_U", urgent);
                    dataAL.add(data);

                    ListAdapter la = new SimpleAdapter(getApplicationContext(), dataAL, R.layout.custom_rd_rme_ps,
                            new String[]{"NAME", "EPIC_NO", "GENDER", "RLN_NAME","SerialNo","RLN_TYPE","AUTO_ID"},
                            new int[]{R.id.tvname, R.id.tvepicno, R.id.tvgender, R.id.tvrlnname, R.id.tvserialno, R.id.tvrlntype, R.id.tvAUTO_ID});
                    listView.setAdapter(la);
                }


            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("printStackTrace",e.getMessage());
                Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Something_Went_Wrong),Toast.LENGTH_LONG).show();
                Intent intent=new Intent(Verification_RD_RME_PS.this,WelcomeBLO.class);
                startActivity(intent);
                finish();
            }
            finally {
                hideProgressDialog();
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
                            String IsSuccess = response.getString("IsSuccess");
                            String Message = response.getString("Message");
                            Log.d("IsSuccess",IsSuccess);
                            Log.d("Message",Message);

                            Log.v("Response : ", "IsSuccess : " + IsSuccess);

                            if(IsSuccess.trim().equals("true") || IsSuccess.trim().equals("true")) {
                                final Dialog dialog = new Dialog(Verification_RD_RME_PS.this);
                                dialog.setContentView(R.layout.custom_blo_report_done_message);
                                Button btnok = (Button) dialog.findViewById(R.id.btnok);
                                dialog.setCanceledOnTouchOutside(false);

                                btnok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        //finish();


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
