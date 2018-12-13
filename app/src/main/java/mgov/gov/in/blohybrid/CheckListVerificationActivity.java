package mgov.gov.in.blohybrid;

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
import mgov.gov.in.blohybrid.Constants;

public class CheckListVerificationActivity extends AppCompatActivity {

    ListView listView;
    TextView tvhome;
    ArrayList<HashMap<String, String>> dataAL;
    RequestQueue queue;
    ProgressDialog pd;
    String url,mob,stcode,acno,partno,SharedKey,IP_HEADER;
    Map<String, String> keyVal = new LinkedHashMap<String, String>();
    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list_verification);
        tvhome = (TextView) findViewById(R.id.tvhome);

        prefs = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mob  = prefs.getString("mob","");
        stcode = prefs.getString("st_code","");
        acno = prefs.getString("AcNo","");
        partno = prefs.getString("PartNo","");
        SharedKey = prefs.getString("Key","");
        Log.d("mobedf",mob);
        Log.d("st_code",stcode);
        Log.d("AcNo",acno);
        Log.d("PartNo",partno);
        IP_HEADER = Constants.IP_HEADER;
        //url = "http://eronet.ecinet.in/services/api/BLONET/GetBLOAppointedForms?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno;
        url = IP_HEADER+"GetBLOAppointedForms?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno;
        Log.d("url",url);

        queue = Volley.newRequestQueue(this);
        pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getApplicationContext().getResources().getString(R.string.Please_Wait));
        pd.setCancelable(false);
        showProgressDialog();

        dataAL = new ArrayList<>();
        listView = (ListView) findViewById(R.id.checklist_ListView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String sformtype = ((TextView) view.findViewById(R.id.tvformtype)).getText().toString();
                String srefno = ((TextView) view.findViewById(R.id.tvrefno)).getText().toString();
                Log.d("srefnoclick",srefno);
                if(sformtype=="form6" || sformtype.equals("form6")){
                    Toast.makeText(getApplicationContext(),"Form Tpye : FORM 6",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Form6.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("sformtype", sformtype);
                    mBundle.putString("srefno",srefno);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                    finish();
                } else if(sformtype=="form6a" || sformtype.equals("form6a")){
                    Toast.makeText(getApplicationContext(),"Form Tpye : FORM 6A",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Form6a.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("sformtype", sformtype);
                    mBundle.putString("srefno",srefno);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                    finish();
                } else if(sformtype=="form7" || sformtype.equals("form7")){
                    Toast.makeText(getApplicationContext(),"Form Tpye : FORM 7",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Form7.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("sformtype", sformtype);
                    mBundle.putString("srefno",srefno);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                    finish();
                } else if(sformtype=="form8" || sformtype.equals("form8")){
                    Toast.makeText(getApplicationContext(),"Form Tpye : FORM 8",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Form8.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("sformtype", sformtype);
                    mBundle.putString("srefno",srefno);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                    finish();
                } else if(sformtype=="form8a" || sformtype.equals("form8a")){
                    Toast.makeText(getApplicationContext(),"Form Tpye : FORM 8A",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Form8a.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("sformtype", sformtype);
                    mBundle.putString("srefno",srefno);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                    finish();
                }


            }
        });

        if(ConnectionStatus.isNetworkConnected(CheckListVerificationActivity.this)) {
            new RequestTask1().execute(url);
        } else {
            Intent intent=new Intent(getApplicationContext(),NoNetwork.class);
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }



        tvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckListVerificationActivity.this, WelcomeBLONew.class);
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


                    String ApplicantName = jsreceipt.getString("ApplicantName");
                    String FormReferenceNo = jsreceipt.getString("FormReferenceNo");
                    String Gender = jsreceipt.getString("Gender");
                    String RLN_Name = jsreceipt.getString("RLN_Name");
                    String FORM_SUBMISSION_DATE = jsreceipt.getString("FORM_SUBMISSION_DATE");
                    String FORM_STATUS = jsreceipt.getString("FORM_STATUS");
                    String formType = jsreceipt.getString("formType");


                    HashMap<String, String> data = new HashMap<>();
                    if(!ApplicantName.equals("null") || ApplicantName!="null"){
                        data.put("ApplicantName", ApplicantName);
                    } else data.put("ApplicantName", "--");
                   // data.put("ApplicantName", ApplicantName);
                    if(!FormReferenceNo.equals("null") || FormReferenceNo!="null"){
                        data.put("FormReferenceNo", FormReferenceNo);
                    } else data.put("FormReferenceNo", "--");
                   // data.put("FormReferenceNo", FormReferenceNo);
                    if(!Gender.equals("null") || Gender!="null"){
                        data.put("Gender", Gender);
                    } else data.put("Gender", "--");
                    //data.put("Gender", Gender);
                    if(!RLN_Name.equals("null") || RLN_Name!="null"){
                        data.put("RLN_Name", RLN_Name);
                    } else data.put("RLN_Name", "--");
                    //data.put("RLN_Name", RLN_Name);
                    if(!FORM_SUBMISSION_DATE.equals("null") || FORM_SUBMISSION_DATE!="null"){
                        data.put("FORM_SUBMISSION_DATE", FORM_SUBMISSION_DATE);
                    } else data.put("FORM_SUBMISSION_DATE", "--");
                    //data.put("FORM_SUBMISSION_DATE", FORM_SUBMISSION_DATE);
                    if(!FORM_STATUS.equals("null") || FORM_STATUS!="null"){
                        data.put("FORM_STATUS", FORM_STATUS);
                    } else data.put("FORM_STATUS", "--");
                    //data.put("FORM_STATUS", FORM_STATUS);
                    if(!formType.equals("null") || formType!="null"){
                        data.put("formType", formType);
                    } else data.put("formType", "--");
                    //data.put("formType",formType);

                    //data.put("TAG_U", urgent);
                    dataAL.add(data);

                    ListAdapter la = new SimpleAdapter(getApplicationContext(), dataAL, R.layout.custom_checklist_listview_item,
                            new String[]{"ApplicantName", "FormReferenceNo", "Gender", "RLN_Name", "FORM_SUBMISSION_DATE", "FORM_STATUS", "formType"},
                            new int[]{R.id.tvname, R.id.tvrefno, R.id.tvgender, R.id.tvrlnName, R.id.tvsubDate, R.id.tvstatus, R.id.tvformtype});
                    listView.setAdapter(la);
                }



            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("printStackTrace",e.getMessage());
                Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Something_Went_Wrong), Toast.LENGTH_LONG).show();
                Intent intent=new Intent(CheckListVerificationActivity.this,RevisionActivity.class);
                startActivity(intent);
                finish();
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
