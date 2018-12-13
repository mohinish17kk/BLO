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

public class Statement10FillDetail extends AppCompatActivity {
    Button btn_submit;
    EditText et1,et2,et3,etone,ettwo,etthree;
    TextView tvhome;
    ProgressDialog pd;
    JSONObject jsonBody = new JSONObject();
    RequestQueue queue;
    String url,IP_HEADER;//="http://117.239.180.198/BLONet_API_Test/api/blo/Post_BLOReport?st_code=S13&ac_no=1&part_no=1";
    Map<String, String> keyVal = new LinkedHashMap<String, String>();
    String mob,stcode,acno,partno;
    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement10_fill_detail);
        tvhome = (TextView) findViewById(R.id.tvhome);
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
        etone = (EditText) findViewById(R.id.etone);
        ettwo = (EditText) findViewById(R.id.ettwo);
        etthree = (EditText) findViewById(R.id.etthree);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getApplicationContext().getResources().getString(R.string.Please_Wait));
        pd.setCancelable(false);
        queue = Volley.newRequestQueue(this);

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

        url = IP_HEADER+"Post_DeviationReasons?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno+"&mobile_no="+mob;
        //url = "http://117.239.180.198/BLONet_API_Dev/api/BLONet/Post_DeviationReasons?st_code=S02&ac_no=1&part_no=20&mobile_no=9823763919";
        tvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Statement10FillDetail.this,WelcomeBLONew.class);
                startActivity(intent);
                finish();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ConnectionStatus.isNetworkConnected(Statement10FillDetail.this))
                {
                    submit();

                }
                else
                {
                    Intent intent=new Intent(getApplicationContext(),NoNetwork.class);
                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

                /*if (!validate1()) {
                    return;
                }

                final Dialog dialog = new Dialog(Statement10FillDetail.this);
                dialog.setContentView(R.layout.custom_blo_report_done_message);
                Button btnok = (Button) dialog.findViewById(R.id.btnok);
                dialog.setCanceledOnTouchOutside(false);
                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                        dialog.dismiss();

                    }
                });
                dialog.show();

*/
            }
        });

    }

    private void submit() {

        if (!validate1()) {
            return;
        }

        keyVal.put("DistrictGenderRatio",etone.getText().toString());
        keyVal.put("DistrictAgeCohort",ettwo.getText().toString());
        keyVal.put("DistrictElectorPopulation",etthree.getText().toString());
        keyVal.put("ReasonForDeviationInGendorRatio",et1.getText().toString());
        keyVal.put("ReasonForDeviationInAgeCohort",et2.getText().toString());
        keyVal.put("ReasonForDeviationInElectorPopulationRatio",et3.getText().toString());

        try {
            jsonBody.put("DistrictGenderRatio",etone.getText().toString());
            jsonBody.put("DistrictAgeCohort",ettwo.getText().toString());
            jsonBody.put("DistrictElectorPopulation",etthree.getText().toString());
            jsonBody.put("ReasonForDeviationInGendorRatio",et1.getText().toString());
            jsonBody.put("ReasonForDeviationInAgeCohort",et2.getText().toString());
            jsonBody.put("ReasonForDeviationInElectorPopulationRatio",et3.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("json formed",jsonBody.toString());

        vollyRequestForPost(url, keyVal);
    }

    private boolean validate1() {
        if(et1.getText().toString().trim().isEmpty() || et2.getText().toString().isEmpty() ||et3.getText().toString().isEmpty()
                || etone.getText().toString().isEmpty() || ettwo.getText().toString().isEmpty() || etthree.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.ERROR_Fill_all_detail),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public void vollyRequestForPost(String URL, Map<String, String> keyVal) {
        Log.e("URL",URL);
        showProgressDialog();
        JSONObject obj = new JSONObject();
        Helper.getmHelper().v("OBJ",obj.toString());
        //JSONObject obj = new JSONObject(keyVal);

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
                                final Dialog dialog = new Dialog(Statement10FillDetail.this);
                                dialog.setContentView(R.layout.custom_blo_report_done_message);
                                Button btnok = (Button) dialog.findViewById(R.id.btnok);
                                dialog.setCanceledOnTouchOutside(false);
                                btnok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
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
        };
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
