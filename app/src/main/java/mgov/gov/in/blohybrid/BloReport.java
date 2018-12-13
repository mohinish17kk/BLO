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

public class BloReport extends AppCompatActivity {
    EditText etoption1,etoption2,etoption3,etoption4,etoption5,etoption6,etoption7,etoption8,etoption9;
    Button btnsubmit;
    TextView tvhome;
    ProgressDialog pd;
    JSONObject jsonBody = new JSONObject();
    RequestQueue queue;
    String url,IP_HEADER;//="http://117.239.180.198/BLONet_API_Test/api/blo/Post_BLOReport?st_code=S13&ac_no=1&part_no=1";
    Map<String, String> keyVal = new LinkedHashMap<String, String>();
    String mob,stcode,acno,partno,SharedKey;
    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blo_report);
        etoption1 = (EditText) findViewById(R.id.etoption1);
        etoption2 = (EditText) findViewById(R.id.etoption2);
        etoption3 = (EditText) findViewById(R.id.etoption3);
        etoption4 = (EditText) findViewById(R.id.etoption4);
        etoption5 = (EditText) findViewById(R.id.etoption5);
        etoption6 = (EditText) findViewById(R.id.etoption6);
        etoption7 = (EditText) findViewById(R.id.etoption7);
        etoption8 = (EditText) findViewById(R.id.etoption8);
        etoption9 = (EditText) findViewById(R.id.etoption9);

        pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getApplicationContext().getResources().getString(R.string.Please_Wait));
        pd.setCancelable(false);
        queue = Volley.newRequestQueue(this);

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

       // url = "http://eronet.ecinet.in/services/api/blonet/Post_BLOReport?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno;
        url = IP_HEADER+"Post_BLOReport?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno;

        // keyVal.put("RoleId", ROLE_ID);
        //keyVal.put("RoleId","7");

        tvhome = (TextView) findViewById(R.id.tvhome);

        tvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BloReport.this,WelcomeBLONew.class);
                startActivity(intent);
                finish();
            }
        });

        btnsubmit =  (Button) findViewById(R.id.btn_submit);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ConnectionStatus.isNetworkConnected(BloReport.this))
                {
                    submit();

                }
                else
                {
                    Intent intent=new Intent(getApplicationContext(),NoNetwork.class);
                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });

    }

    private void submit() {

        if (!validateTP()) {
            return;
        }
        if (!validateMP()) {
            return;
        }
        if(!validateFP()){
            return;
        }
        if(!validateOP()){
            return;
        }if(!validate5()){
            return;
        }if(!validate6()){
            return;
        }if(!validate7()){
            return;
        }if(!validate8()){
            return;
        }if(!validate9()){
            return;
        }


        keyVal.put("TotalVoters_Population_in_BLOArea",etoption1.getText().toString());
        keyVal.put("TotalVerificationDone",etoption5.getText().toString());
        keyVal.put("NewFormCollectedOrSubmited",etoption6.getText().toString());
        keyVal.put("FamilyDetailsCollected",etoption7.getText().toString());
        keyVal.put("DeadOrShiftedElector",etoption8.getText().toString());
        keyVal.put("FutureVoter",etoption9.getText().toString());
        keyVal.put("MaleVoters",etoption2.getText().toString());
        keyVal.put("FemaleVoters",etoption3.getText().toString());
        keyVal.put("ThirdGender",etoption4.getText().toString());


        try {
            jsonBody.put("TotalVoters_Population_in_BLOArea",etoption1.getText().toString());
            jsonBody.put("TotalVerificationDone",etoption5.getText().toString());
            jsonBody.put("NewFormCollectedOrSubmited",etoption6.getText().toString());
            jsonBody.put("FamilyDetailsCollected",etoption7.getText().toString());
            jsonBody.put("DeadOrShiftedElector",etoption8.getText().toString());
            jsonBody.put("FutureVoter",etoption9.getText().toString());
            jsonBody.put("MaleVoters",etoption2.getText().toString());
            jsonBody.put("FemaleVoters",etoption3.getText().toString());
            jsonBody.put("ThirdGender",etoption4.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("json formed",jsonBody.toString());



        Log.d("option156789234",etoption1.getText().toString()+" "+etoption5.getText().toString()+" "+etoption6.getText().toString()
                +" "+etoption7.getText().toString()+" "+etoption8.getText().toString()+" "+etoption9.getText().toString()
                +" "+etoption2.getText().toString()+" "+etoption3.getText().toString()+" "+etoption4.getText().toString());

        vollyRequestForPost(url, keyVal);

        /*showProgressDialog();
        JSONObject obj = new JSONObject();
        Helper.getmHelper().v("OBJ",obj.toString());*/






       /* Intent intent=new Intent(ChangePassword.this,LoginActivity.class);
        startActivity(intent);
        finish();*/

    }
    private boolean validateTP() {
        if(etoption1.getText().toString().trim().isEmpty())
        {
            etoption1.setError(getApplicationContext().getResources().getString(R.string.Empty_Entry));
            Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateMP() {
        if(etoption2.getText().toString().trim().isEmpty())
        {
            etoption2.setError(getApplicationContext().getResources().getString(R.string.Empty_Entry));
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.ERROR_Enter_Total_Population_Count),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateFP() {
        if(etoption3.getText().toString().trim().isEmpty())
        {
            etoption3.setError(getApplicationContext().getResources().getString(R.string.Empty_Entry));
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.ERROR_Enter_Female_Population_Count),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateOP() {
        if(etoption4.getText().toString().trim().isEmpty())
        {
            etoption4.setError(getApplicationContext().getResources().getString(R.string.Empty_Entry));
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.ERROR_Enter_Other_Gender_Count),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean validate5() {
        if(etoption5.getText().toString().trim().isEmpty())
        {
            etoption5.setError(getApplicationContext().getResources().getString(R.string.Empty_Entry));
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.ERROR_Fill_all_detail),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean validate6() {
        if(etoption6.getText().toString().trim().isEmpty())
        {
            etoption6.setError(getApplicationContext().getResources().getString(R.string.Empty_Entry));
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.ERROR_Fill_all_detail),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean validate7() {
        if(etoption7.getText().toString().trim().isEmpty())
        {
            etoption7.setError(getApplicationContext().getResources().getString(R.string.Empty_Entry));
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.ERROR_Fill_all_detail),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    } private boolean validate8() {
        if(etoption8.getText().toString().trim().isEmpty())
        {
            etoption8.setError(getApplicationContext().getResources().getString(R.string.Empty_Entry));
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.ERROR_Fill_all_detail),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    } private boolean validate9() {
        if(etoption9.getText().toString().trim().isEmpty())
        {
            etoption9.setError(getApplicationContext().getResources().getString(R.string.Empty_Entry));
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.ERROR_Fill_all_detail),Toast.LENGTH_SHORT).show();
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
                               final Dialog dialog = new Dialog(BloReport.this);
                                dialog.setContentView(R.layout.custom_blo_report_done_message);
                                Button btnok = (Button) dialog.findViewById(R.id.btnok);
                                dialog.setCanceledOnTouchOutside(false);




                                btnok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                Intent intent=new Intent(BloReport.this,WelcomeBLONew.class);
                                startActivity(intent);
                                finish();
                                dialog.dismiss();

                                }
                                });
                                dialog.show();



                            } else
                                Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_Response_from_Server), Toast.LENGTH_LONG).show();



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
