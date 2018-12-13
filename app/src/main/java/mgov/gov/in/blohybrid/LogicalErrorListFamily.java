package mgov.gov.in.blohybrid;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import java.util.List;
import mgov.gov.in.blohybrid.Constants;

public class LogicalErrorListFamily extends AppCompatActivity {
    ListView listView;
    RequestQueue queue;
    ProgressDialog pd;
    TextView tvhome;
    ArrayList<HashMap<String, String>> dataAL;
    SharedPreferences prefs;
    String mob,stcode,acno,partno,url,error_type,SharedKey,IP_HEADER;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logical_error_list_family);

        tvhome = (TextView) findViewById(R.id.tvhome);

        queue = Volley.newRequestQueue(this);
        pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getApplicationContext().getResources().getString(R.string.Please_Wait));
        pd.setCancelable(false);

        prefs = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mob  = prefs.getString("mob","");
        stcode = prefs.getString("st_code","");
        acno = prefs.getString("AcNo","");
        partno = prefs.getString("PartNo","");
        error_type = prefs.getString("error_type","");
        Log.d("PartNo",partno);
        Log.d("error_typee",error_type);
        SharedKey = prefs.getString("Key","");
        Log.d("SharedKey",SharedKey);
        IP_HEADER = Constants.IP_HEADER;


        url = IP_HEADER+"Get_LogicalErrorRecords?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno+"&mobile_no="+mob+"&error_type="+error_type;
        //showProgressDialog();
        Log.d("url",url);

        dataAL = new ArrayList<>();
        listView = (ListView) findViewById(R.id.logicalErrorListView);


        tvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogicalErrorListFamily.this, WelcomeBLONew.class);
                startActivity(intent);
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String sserial = ((TextView) view.findViewById(R.id.tvserialno)).getText().toString();
                Log.d("Serial Number",sserial);
                final Dialog dialogg = new Dialog(LogicalErrorListFamily.this);
                dialogg.setContentView(R.layout.custom_form6_popup);
                TextView tvonline = (TextView) dialogg.findViewById(R.id.tvonline);
                TextView tvdirect = (TextView) dialogg.findViewById(R.id.tvdirect);
                dialogg.setCanceledOnTouchOutside(false);

                tvonline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogg.dismiss();
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
                            final Dialog dialog = new Dialog(LogicalErrorListFamily.this);
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
                    }
                });
                tvdirect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(LogicalErrorListFamily.this,DirectSubmission.class);
                        startActivity(intent);

                        /*Intent intent = new Intent(getApplicationContext(), DirectSubmission.class);
                        Bundle mBundle = new Bundle();

                        mBundle.putString("sserial", sserial);

                        intent.putExtras(mBundle);
                        startActivity(intent);*/
                        dialogg.dismiss();
                    }
                });
                dialogg.show();

            }
        });

        /*dataAL = new ArrayList<>();
        for(int i=0; i < 5; i++) {

            HashMap<String, String> data = new HashMap<>();
            data.put("Name", "Ujjwal");
            data.put("Epic", "EGR123456778");
            data.put("Gender", "M");
            data.put("Age","25");
            data.put("Serial","AB123");
            data.put("RLN_Name","Prakash");
            data.put("RLN_Type", "M");
            data.put("HouseNo", "26AB");
            data.put("ErrorType", "error Type");

            //data.put("TAG_U", urgent);
            dataAL.add(data);
            //serialArray.add(SLNOINPART);

            ListAdapter la = new SimpleAdapter(getApplicationContext(), dataAL, R.layout.custom_logical_error_listview_item,
                    new String[]{"Name", "Epic", "Gender", "Age", "Serial", "RLN_Name","RLN_Type","HouseNo","ErrorType"},
                    new int[]{R.id.tvname, R.id.tvepicno, R.id.tvgender, R.id.tvage, R.id.tvserialno, R.id.tvrlnname
                    , R.id.tvrlntypee,R.id.tvhouseNumber,R.id.tverrortype});
            listView.setAdapter(la);
        }*/

        showProgressDialog();
        if(ConnectionStatus.isNetworkConnected(LogicalErrorListFamily.this)) {
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


                    String Name = jsreceipt.getString("Name");
                    String EpicNo = jsreceipt.getString("EpicNo");
                    String Gender = jsreceipt.getString("Gender");
                    String Age = jsreceipt.getString("Age");
                    String SlnoInPart = jsreceipt.getString("SlnoInPart");
                    String RlnName = jsreceipt.getString("RlnName");
                    String RlnType = jsreceipt.getString("RlnType");
                    String HouseNo = jsreceipt.getString("HouseNo");
                    String ErrorType = jsreceipt.getString("ErrorType");

                    HashMap<String, String> data = new HashMap<>();
                    if(!Name.equals("null") || Name!="null"){
                        data.put("Name", Name);
                    } else data.put("RLN_TYPE", "--");
                    //data.put("Name", Name);
                    if(!EpicNo.equals("null") || EpicNo!="null"){
                        data.put("EpicNo", EpicNo);
                    } else data.put("EpicNo", "--");
                    //data.put("EpicNo", EpicNo);
                    if(!Gender.equals("null") || Gender!="null"){
                        data.put("Gender", Gender);
                    } else data.put("Gender", "--");
                    //data.put("Gender", Gender);
                    if(!Age.equals("null") || Age!="null"){
                        data.put("Age", Age);
                    } else data.put("Age", "--");
                    //data.put("Age", Age);
                    if(!SlnoInPart.equals("null") || SlnoInPart!="null"){
                        data.put("SlnoInPart", SlnoInPart);
                    } else data.put("SlnoInPart", "--");
                    //data.put("SlnoInPart", SlnoInPart);
                    if(!RlnName.equals("null") || RlnName!="null"){
                        data.put("RlnName", RlnName);
                    } else data.put("RlnName", "--");
                    //data.put("RlnName", RlnName);
                    if(!RlnType.equals("null") || RlnType!="null"){
                        data.put("RlnType", RlnType);
                    } else data.put("RlnType", "--");
                   // data.put("RlnType",RlnType);
                    if(!HouseNo.equals("null") || HouseNo!="null"){
                        data.put("HouseNo", HouseNo);
                    } else data.put("HouseNo", "--");
                    //data.put("HouseNo", HouseNo);
                    if(!ErrorType.equals("null") || ErrorType!="null"){
                        data.put("ErrorType", ErrorType);
                    } else data.put("ErrorType", "--");
                   // data.put("ErrorType", ErrorType);

                    //data.put("TAG_U", urgent);
                    dataAL.add(data);

                    ListAdapter la = new SimpleAdapter(getApplicationContext(), dataAL, R.layout.custom_logical_error_listview_item,
                            new String[]{"Name", "EpicNo", "Gender", "Age", "SlnoInPart", "RlnName","RlnType","HouseNo","ErrorType"},
                            new int[]{R.id.tvname, R.id.tvepicno, R.id.tvgender, R.id.tvage, R.id.tvserialno, R.id.tvrlnname
                                    , R.id.tvrlntypee,R.id.tvhouseNumber,R.id.tverrortype});
                    listView.setAdapter(la);
                }


            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("printStackTrace",e.getMessage());
                Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Something_Went_Wrong),Toast.LENGTH_LONG).show();
                Intent intent=new Intent(LogicalErrorListFamily.this,WelcomeBLO.class);
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
