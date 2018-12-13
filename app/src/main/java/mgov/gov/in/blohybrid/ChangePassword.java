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

public class ChangePassword extends AppCompatActivity {
    DatabaseHelper myDb;
    private TextView tverror,tverroroldpassword,tverrornewpassword;
    private EditText etoldpassword,etnewpassword,etconfirmpassword;
    private Button btnsubmit;
    ProgressDialog pd;
    RequestQueue queue;
    String url,mob,oldpass,newpass,SharedKey,IP_HEADER;
    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs" ;
    String stcode,acno,partno;
    JSONObject jsonBody = new JSONObject();
    Map<String, String> keyVal = new LinkedHashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);



        tverroroldpassword= (TextView) findViewById(R.id.tverroroldpassword);
        tverrornewpassword= (TextView) findViewById(R.id.tverrornewpassword);
        tverror= (TextView) findViewById(R.id.tverrorchangepassword);
        etoldpassword= (EditText) findViewById(R.id.etoldpassword);
        etnewpassword= (EditText) findViewById(R.id.etnewpassword);
        etconfirmpassword= (EditText) findViewById(R.id.etconfirmpassword);
        btnsubmit= (Button) findViewById(R.id.btn_submitcp);
        //mob=BLO_MOBILE;
        myDb = new DatabaseHelper(this);

        prefs = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mob  = prefs.getString("mob","");
        stcode = prefs.getString("st_code","");
        acno = prefs.getString("AcNo","");
        partno = prefs.getString("PartNo","");
        SharedKey = prefs.getString("Key","");
        Log.d("SharedKey",SharedKey);
        IP_HEADER = Constants.IP_HEADER;

        Log.d("mobedf",mob);
        Log.d("st_code",stcode);
        Log.d("AcNo",acno);
        Log.d("PartNo",partno);

        pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getApplicationContext().getResources().getString(R.string.Please_Wait));
        pd.setCancelable(false);
        queue = Volley.newRequestQueue(this);

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ConnectionStatus.isNetworkConnected(ChangePassword.this))
                {
                    tverror.setText("");
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

        if (!validateOldPassword()) {
            return;
        }
        if (!validateNewPassword()) {
            return;
        }
        if(!validateReEnterPassword()){
            return;
        }


        oldpass=etoldpassword.getText().toString();
        newpass=etnewpassword.getText().toString();
        Log.d("mob123",mob);
        //url ="http://117.239.180.198/BLONet_API_Test/api/blo/ChangePassword?st_code=S13&UserId="+mob+"&oldpassword="+oldpass+"&newpassword="+newpass+"&ac_no=1&part_no=1";
        //url ="http://117.239.180.198/BLONet_API_Dev/api/blo/ChangePassword?st_code="+stcode+"&UserId="+mob+"&oldpassword="+oldpass+"&newpassword="+newpass+"&ac_no="+acno+"&part_no="+partno;
        //url ="http://eronet.ecinet.in/services/api/blonet/ChangePassword?st_code="+stcode+"&UserId="+mob+"&oldpassword="+oldpass+"&newpassword="+newpass+"&ac_no="+acno+"&part_no="+partno;
        //url = "http://117.239.180.198/BLONet_API_Dev/api/blo/ChangePassword?st_code=S13&UserId=9823763919&oldpassword=1234567&newpassword=123456&ac_no=01&part_no=01";
        url = IP_HEADER+"ChangePassword?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno;
        Log.d("url",url);
        Log.d("oldpass",oldpass);
        Log.d("newpass",newpass);
        Log.d("url",url);
        keyVal.put("user_id",mob);
        keyVal.put("password",oldpass);
        keyVal.put("new_password",newpass);
        try {
            jsonBody.put("user_id",mob);
            jsonBody.put("password",oldpass);
            jsonBody.put("new_password",newpass);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("jsonBody formed",jsonBody.toString());
        vollyRequestForPost(url, keyVal);
        /*JSONObject obj = new JSONObject();
        Helper.getmHelper().v("OBJ",obj.toString());
        showProgressDialog();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, obj, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("On Response : ", response.toString());
                        try {
                            String IsSuccess = response.getString("IsSuccess");
                            String Message = response.getString("Message");
                            Log.d("IsSuccess",IsSuccess);
                            Log.d("Message",Message);

                            Log.v("Response : ", "IsSuccess : " + IsSuccess);

                            if(IsSuccess.trim().equals("true")) {

                                boolean isUpdate = myDb.updatePassword(mob,newpass);
                                if(isUpdate == true){
                                    final Dialog dialog = new Dialog(ChangePassword.this);
                                    dialog.setContentView(R.layout.custom_password_change_message);
                                    Button btnok = (Button) dialog.findViewById(R.id.bok);
                                    dialog.setCanceledOnTouchOutside(false);


                                    btnok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent=new Intent(ChangePassword.this,LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                            dialog.dismiss();

                                        }
                                    });
                                    dialog.show();


                                }else {
                                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Password_Updated_on_main_server_but_not_on_local_server),Toast.LENGTH_SHORT).show();
                                }



                            } else
                                Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.Invalid_UserID_or_Password), Toast.LENGTH_LONG).show();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });*/

// Access the RequestQueue through your singleton class.
        //queue.add(jsObjRequest);
        //hideProgressDialog();




       /* Intent intent=new Intent(ChangePassword.this,LoginActivity.class);
        startActivity(intent);
        finish();*/

    }

    private boolean validateOldPassword() {
        tverroroldpassword.setText("");
        tverrornewpassword.setText("");
        tverror.setText("");
        tverroroldpassword.setVisibility(View.GONE);
        tverrornewpassword.setVisibility(View.GONE);
        tverror.setVisibility(View.GONE);
        if(etoldpassword.getText().toString().trim().isEmpty())
        {
            tverroroldpassword.setText(getApplicationContext().getResources().getString(R.string.Please_Enter_Old_Password));
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.ERROR_Please_Enter_Old_Password),Toast.LENGTH_SHORT).show();
            tverroroldpassword.setVisibility(View.VISIBLE);
            return false;
        }
        tverror.setText("");
        tverroroldpassword.setText("");
        tverrornewpassword.setText("");
        tverror.setVisibility(View.GONE);
        tverroroldpassword.setVisibility(View.GONE);
        tverrornewpassword.setVisibility(View.GONE);
        return true;
    }

    private boolean validateNewPassword() {
        tverror.setText("");
        tverroroldpassword.setText("");
        tverrornewpassword.setText("");
        tverror.setVisibility(View.GONE);
        tverroroldpassword.setVisibility(View.GONE);
        tverrornewpassword.setVisibility(View.GONE);
        if(etnewpassword.getText().toString().trim().isEmpty())
        {
            tverrornewpassword.setText(getApplicationContext().getResources().getString(R.string.Please_enter_your_new_password));
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.ERROR_Please_enter_your_new_password),Toast.LENGTH_SHORT).show();
            tverrornewpassword.setVisibility(View.VISIBLE);
            return false;
        }else  if((etnewpassword.getText().toString().length()<6)||(etnewpassword.getText().toString().length()>500))
        {
            tverrornewpassword.setText(getApplicationContext().getResources().getString(R.string.Password_must_be_Min6_and_Max500_Characters));
            Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.ERROR_Password_must_be_Min6and_Max500_Characters),Toast.LENGTH_SHORT).show();
            tverrornewpassword.setVisibility(View.VISIBLE);
            return false;
        }
        tverror.setText("");
        tverroroldpassword.setText("");
        tverrornewpassword.setText("");
        tverror.setVisibility(View.GONE);
        tverroroldpassword.setVisibility(View.GONE);
        tverrornewpassword.setVisibility(View.GONE);
        return true;
    }


    private boolean validateReEnterPassword(){
        tverror.setText("");
        tverroroldpassword.setText("");
        tverrornewpassword.setText("");
        tverror.setVisibility(View.GONE);
        tverroroldpassword.setVisibility(View.GONE);
        tverrornewpassword.setVisibility(View.GONE);
        if(etconfirmpassword.getText().toString().trim().isEmpty())
        {
            tverror.setText(getApplicationContext().getResources().getString(R.string.Please_re_enter_your_new_password));
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.ERROR_Please_re_enter_your_new_password),Toast.LENGTH_SHORT).show();
            tverror.setVisibility(View.VISIBLE);
            return false;
        }else  if((etconfirmpassword.getText().toString().length()<6)||(etconfirmpassword.getText().toString().length()>500))
        {
            tverror.setText(getApplicationContext().getResources().getString(R.string.Password_must_be_Min6_and_Max500_Characters));
            Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.ERROR_Password_must_be_Min6and_Max500_Characters),Toast.LENGTH_SHORT).show();
            tverror.setVisibility(View.VISIBLE);
            return false;
        } else if(!((etconfirmpassword.getText().toString().equals(etnewpassword.getText().toString())) || (etconfirmpassword.getText().toString()== etnewpassword.getText().toString())))
        {
            tverror.setText(getApplicationContext().getResources().getString(R.string.Passwords_does_not_match));
            Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.ERROR_Passwords_does_not_match),Toast.LENGTH_SHORT).show();
            etnewpassword.setText("");
            etconfirmpassword.setText("");

            tverror.setVisibility(View.VISIBLE);
            return false;
        }
        tverror.setText("");
        tverroroldpassword.setText("");
        tverrornewpassword.setText("");
        tverror.setVisibility(View.GONE);
        tverroroldpassword.setVisibility(View.GONE);
        tverrornewpassword.setVisibility(View.GONE);
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
       // showProgressDialog();

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

                            if(IsSuccess.trim().equals("true")) {

                                boolean isUpdate = myDb.updatePassword(mob,newpass);
                                if(isUpdate == true){
                                    final Dialog dialog = new Dialog(ChangePassword.this);
                                    dialog.setContentView(R.layout.custom_password_change_message);
                                    Button btnok = (Button) dialog.findViewById(R.id.bok);
                                    dialog.setCanceledOnTouchOutside(false);


                                    btnok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent=new Intent(ChangePassword.this,LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                            dialog.dismiss();

                                        }
                                    });
                                    dialog.show();


                                }else {
                                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Password_Updated_on_main_server_but_not_on_local_server),Toast.LENGTH_SHORT).show();
                                }



                            } else
                                Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.Invalid_UserID_or_Password), Toast.LENGTH_LONG).show();



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
                headers.put("Content-Type", "application/json; charset=utf-8");
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
