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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
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
import mgov.gov.in.blohybrid.Constants;

public class DSESubActivity extends AppCompatActivity {
    ListView listView;
    RequestQueue queue;
    ProgressDialog pd;
    TextView tvhome;
    ArrayList<HashMap<String, String>> dataAL;
    SharedPreferences prefs;
    String sauto,dseSubAutoID;
    String mob,stcode,acno,partno,url,url1,isVerified,SharedKey,IP_HEADER;
    RadioButton rbverified,rbnotverified;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsesub);
        tvhome = (TextView) findViewById(R.id.tvhome);

        sauto = getIntent().getExtras().getString("sauto");
        Log.d("sauto1",sauto);
        prefs = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mob  = prefs.getString("mob","");
        stcode = prefs.getString("st_code","");
        acno = prefs.getString("AcNo","");
        partno = prefs.getString("PartNo","");
        SharedKey = prefs.getString("Key","");
        Log.d("SharedKey",SharedKey);

        Log.d("mobedf",mob);
        Log.d("st_code",stcode);
        Log.d("AcNo",acno);
        Log.d("PartNo",partno);
        IP_HEADER = Constants.IP_HEADER;

        final Dialog dialogg = new Dialog(DSESubActivity.this);

        url = IP_HEADER+"Get_DSERecordById?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno+"&dse_id="+sauto;
        Log.d("url",url);

        queue = Volley.newRequestQueue(this);
        pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getApplicationContext().getResources().getString(R.string.Please_Wait));
        pd.setCancelable(false);

        listView = (ListView) findViewById(R.id.dseSubListView);
        dataAL = new ArrayList<>();

        showProgressDialog();
        if(ConnectionStatus.isNetworkConnected(DSESubActivity.this)) {
            new RequestTask1().execute(url);
        } else {
            Intent intent=new Intent(getApplicationContext(),NoNetwork.class);
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //sauto = ((TextView) view.findViewById(R.id.tvautoid)).getText().toString();
                //Log.d("Auto ID",sauto);
                //Toast.makeText(getApplicationContext(),"Auto ID "+sauto,Toast.LENGTH_SHORT).show();
                 dseSubAutoID = ((TextView) view.findViewById(R.id.tvautoid)).getText().toString();
                String subac = ((TextView) view.findViewById(R.id.tvacno)).getText().toString();
                String subpart = ((TextView) view.findViewById(R.id.tvpartno)).getText().toString();
                Log.d("dseSubAutoID",dseSubAutoID);
                Log.d("subac",subac);
                Log.d("subpart",subpart);

                if((subac.equals(acno) || subac==acno) && (subpart.equals(partno) || subpart==partno)){

                    dialogg.setContentView(R.layout.custom_vrified_not_verified);
                    rbverified = (RadioButton) dialogg.findViewById(R.id.rbverified);
                    rbnotverified = (RadioButton) dialogg.findViewById(R.id.rbnotverified);
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

                            if(!(rbverified.isChecked() || rbnotverified.isChecked())){
                                Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Select_Verified_OR_Not_Verified),Toast.LENGTH_SHORT).show();
                                return ;
                            }
                            dialogg.dismiss();
                            //url1 ="http://eronet.ecinet.in/services/api/BLONet/Post_ASDStatusVerification?st_code=S02&ac_no=1&part_no=20&mobile_no=9823763919&id="+sauto+"&isVerified="+isVerified;
                            url1 = IP_HEADER+"Post_ASDStatusVerification?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno+"&mobile_no="+mob+"&id="+sauto+"&isVerified="+isVerified;
                            Log.d("url1",url1);
                            showProgressDialog();
                            new RequestTask2().execute(url1);

                        /*final Dialog dialog = new Dialog(DSESubActivity.this);
                        dialog.setContentView(R.layout.custom_blo_report_done_message);
                        Button btnok = (Button) dialog.findViewById(R.id.btnok);
                        dialog.setCanceledOnTouchOutside(false);

                        btnok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog.dismiss();

                            }
                        });
                        dialog.show();*/

                        /*JSONObject obj = new JSONObject();
                        Helper.getmHelper().v("OBJ",obj.toString());
                        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                                (Request.Method.GET, url, obj, new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d("On Response : ", response.toString());
                                        try {
                                            String IsSuccess = response.getString("IsSuccess");
                                            Log.e("IsSuccess",IsSuccess);

                                            if(IsSuccess.trim().equals("true")) {
                                                final Dialog dialog = new Dialog(MigrationVerifyActivity.this);
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
                                                Toast.makeText(getApplicationContext(), "No Response from Server. Enter correct User ID and Password", Toast.LENGTH_LONG).show();

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
*/


                        }
                    });

                    dialogg.show();


                } else Toast.makeText(getApplicationContext(),"NOT ALLOWED !!! BLO can verify within part only",Toast.LENGTH_LONG).show();



            }
        });

        tvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DSESubActivity.this, WelcomeBLONew.class);
                startActivity(intent);
                finish();
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


                    String NAME = jsreceipt.getString("NAME");
                    String EPIC_NO = jsreceipt.getString("EPIC_NO");
                    String GENDER = jsreceipt.getString("GENDER");
                    String SLNINPAART = jsreceipt.getString("SLNINPAART");
                    String RLN_TYPE = jsreceipt.getString("RLN_TYPE");
                    String RLN_NAME = jsreceipt.getString("RLN_NAME");
                    String AC_NO = jsreceipt.getString("AC_NO");
                    String PART_NO = jsreceipt.getString("PART_NO");
                    String AUTO_ID = jsreceipt.getString("AUTO_ID");

                    HashMap<String, String> data = new HashMap<>();
                    if(!NAME.equals("null") || NAME!="null"){
                        data.put("NAME", NAME);
                    } else data.put("NAME", "--");
                    //data.put("NAME", NAME);
                    if(!EPIC_NO.equals("null") || EPIC_NO!="null"){
                        data.put("EPIC_NO", EPIC_NO);
                    } else data.put("EPIC_NO", "--");
                    //data.put("EPIC_NO", EPIC_NO);
                    if(!GENDER.equals("null") || GENDER!="null"){
                        data.put("GENDER", GENDER);
                    } else data.put("GENDER", "--");
                    //data.put("GENDER", GENDER);
                    if(!SLNINPAART.equals("null") || SLNINPAART!="null"){
                        data.put("SLNINPAART", SLNINPAART);
                    } else data.put("SLNINPAART", "--");
                    //data.put("SLNINPAART", SLNINPAART);
                    if(!RLN_TYPE.equals("null") || RLN_TYPE!="null"){
                        data.put("RLN_TYPE", RLN_TYPE);
                    } else data.put("RLN_TYPE", "--");
                    //data.put("RLN_TYPE",RLN_TYPE);
                    if(!RLN_NAME.equals("null") || RLN_NAME!="null"){
                        data.put("RLN_NAME", RLN_NAME);
                    } else data.put("RLN_NAME", "--");
                    //data.put("RLN_NAME",RLN_NAME);
                    if(!AC_NO.equals("null") || AC_NO!="null"){
                        data.put("AC_NO", AC_NO);
                    } else data.put("AC_NO", "--");
                   // data.put("AC_NO",AC_NO);
                    if(!PART_NO.equals("null") || PART_NO!="null"){
                        data.put("PART_NO", PART_NO);
                    } else data.put("PART_NO", "--");
                   // data.put("PART_NO",PART_NO);
                    if(!GENDER.equals("null") || GENDER!="null"){
                        data.put("GENDER", GENDER);
                    } else data.put("GENDER", "--");
                    data.put("AUTO_ID",AUTO_ID);
                    //data.put("TAG_U", urgent);
                    dataAL.add(data);

                    ListAdapter la = new SimpleAdapter(getApplicationContext(), dataAL, R.layout.custom_sub_dse_listview_item,
                            new String[]{"NAME", "EPIC_NO", "GENDER", "SLNINPAART","RLN_TYPE","RLN_NAME","AC_NO","PART_NO","AUTO_ID"},
                            new int[]{R.id.tvname, R.id.tvepicno, R.id.tvgender, R.id.tvserialno, R.id.tvrlntype, R.id.tvrlnname, R.id.tvacno, R.id.tvpartno, R.id.tvautoid});
                    listView.setAdapter(la);
                }


            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("printStackTrace",e.getMessage());
                Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Something_Went_Wrong),Toast.LENGTH_LONG).show();
                Intent intent=new Intent(DSESubActivity.this,WelcomeBLO.class);
                startActivity(intent);
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

            Log.d("On Response : ", result.toString());
            try {
                JSONObject jsonObject = new JSONObject(result);
                Log.v("response jsonObject",jsonObject.toString());
                String IsSuccess = jsonObject.getString("IsSuccess");
                Log.d("IsSuccess",IsSuccess);
                if(IsSuccess.trim().equals("true")) {
                    final Dialog dialog = new Dialog(DSESubActivity.this);
                    dialog.setContentView(R.layout.custom_blo_report_done_message);
                    Button btnok = (Button) dialog.findViewById(R.id.btnok);
                    dialog.setCanceledOnTouchOutside(false);

                    btnok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.dismiss();
                            finish();

                        }
                    });
                    dialog.show();
                } else Toast.makeText(getApplicationContext(),"Resend the input",Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("printStackTrace",e.getMessage());
                Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Something_Went_Wrong),Toast.LENGTH_LONG).show();
            }
            finally {
                hideProgressDialog();
            }

        }
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
