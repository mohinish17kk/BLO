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
import mgov.gov.in.blohybrid.Constants;

public class DSEActivity extends AppCompatActivity {

    ListView listView;
    RequestQueue queue;
    ProgressDialog pd;
    TextView tvhome;
    ArrayList<HashMap<String, String>> dataAL;
    SharedPreferences prefs;
    String mob,stcode,acno,partno,url,SharedKey,IP_HEADER;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dse);
        tvhome = (TextView) findViewById(R.id.tvhome);
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

        url = IP_HEADER+"Get_DSERecords?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno+"&mobile_no="+mob;
        Log.d("url",url);

        queue = Volley.newRequestQueue(this);
        pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getApplicationContext().getResources().getString(R.string.Please_Wait));
        pd.setCancelable(false);


        listView = (ListView) findViewById(R.id.dseListView);
        dataAL = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String sauto = ((TextView) view.findViewById(R.id.tvAUTO_ID)).getText().toString();
                Log.d("sauto",sauto);
                Intent intent = new Intent(getApplicationContext(), DSESubActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("sauto", sauto);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });

        tvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DSEActivity.this, WelcomeBLONew.class);
                startActivity(intent);
                finish();
            }
        });

        showProgressDialog();
        if(ConnectionStatus.isNetworkConnected(DSEActivity.this)) {
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


                    String NAME = jsreceipt.getString("NAME");
                    String EPIC_NO = jsreceipt.getString("EPIC_NO");
                    String GENDER = jsreceipt.getString("GENDER");
                    String SLNINPAART = jsreceipt.getString("SLNINPAART");
                    String AUTO_ID = jsreceipt.getString("DSE_ID");
                    String AC_NO = jsreceipt.getString("AC_NO");
                    String PART_NO = jsreceipt.getString("PART_NO");

                    HashMap<String, String> data = new HashMap<>();
                    if(!NAME.equals("null") || NAME!="null"){
                        data.put("NAME", NAME);
                    } else data.put("NAME", "--");
                    //data.put("NAME", NAME);
                    if(!EPIC_NO.equals("null") || EPIC_NO!="null"){
                        data.put("EPIC_NO", EPIC_NO);
                    } else data.put("EPIC_NO", "--");
                   // data.put("EPIC_NO", EPIC_NO);
                    if(!GENDER.equals("null") || GENDER!="null"){
                        data.put("GENDER", GENDER);
                    } else data.put("GENDER", "--");
                    //data.put("GENDER", GENDER);
                    if(!SLNINPAART.equals("null") || SLNINPAART!="null"){
                        data.put("SLNINPAART", SLNINPAART);
                    } else data.put("SLNINPAART", "--");
                    //data.put("SLNINPAART", SLNINPAART);
                    if(!AUTO_ID.equals("null") || AUTO_ID!="null"){
                        data.put("AUTO_ID", AUTO_ID);
                    } else data.put("AUTO_ID", "--");
                    //data.put("AUTO_ID",AUTO_ID);
                    if(!AC_NO.equals("null") || AC_NO!="null"){
                        data.put("AC_NO", AC_NO);
                    } else data.put("AC_NO", "--");
                    //data.put("AC_NO",AC_NO);
                    if(!PART_NO.equals("null") || PART_NO!="null"){
                        data.put("PART_NO", PART_NO);
                    } else data.put("PART_NO", "--");
                    //data.put("PART_NO",PART_NO);

                    //data.put("TAG_U", urgent);
                    dataAL.add(data);

                    ListAdapter la = new SimpleAdapter(getApplicationContext(), dataAL, R.layout.custom_dse_listview_item,
                            new String[]{"NAME", "EPIC_NO", "GENDER", "SLNINPAART","AUTO_ID","AC_NO","PART_NO"},
                            new int[]{R.id.tvname, R.id.tvepicno, R.id.tvgender, R.id.tvserialno, R.id.tvAUTO_ID, R.id.tvacno, R.id.tvpartno});
                    listView.setAdapter(la);
                }


            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("printStackTrace",e.getMessage());
                Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Something_Went_Wrong),Toast.LENGTH_LONG).show();
                Intent intent=new Intent(DSEActivity.this,WelcomeBLO.class);
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
