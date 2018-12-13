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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class Form7 extends AppCompatActivity {
    TextView tvhome,tvname,tvepic,tvslno,tvpart,tvrefno,tverollname,tverollgender,tverollage,tverollrlntype,tverollrlnname,
            tverollreasonforObjectionDeletion,tvname2,tvepic2,tvslno2,tvpart2,tvmobno2,tvemail2,tverollname2,tverollgender2,
            tverollage2,tverollrlntype2,tverollrlnname2;
    RadioButton rbabsent,rbshifted,rbdead,rbnosuchperson;
    CheckBox cbdetailverified,cbobjections;
    EditText etremark;
    Button btn_submit;

    RequestQueue queue;
    ProgressDialog pd;
    SharedPreferences prefs;
    String mob,stcode,acno,partno,url,url1,srefno,SapplicantASDNSP,SapplicantVerified,SObjection,Sremark,SharedKey,bloname,IP_HEADER;
    public static final String MyPREFERENCES = "MyPrefs";
    JSONObject jsonBody = new JSONObject();
    Map<String, String> keyVal = new LinkedHashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form7);

        tvhome = (TextView) findViewById(R.id.tvhome);
        tvname = (TextView) findViewById(R.id.tvname);
        tvepic = (TextView) findViewById(R.id.tvepic);
        tvslno = (TextView) findViewById(R.id.tvslno);
        tvpart = (TextView) findViewById(R.id.tvpart);
        tvrefno = (TextView) findViewById(R.id.tvrefno);
        tverollname = (TextView) findViewById(R.id.tverollname);
        tverollgender = (TextView) findViewById(R.id.tverollgender);
        tverollage = (TextView) findViewById(R.id.tverollage);
        tverollrlntype = (TextView) findViewById(R.id.tverollrlntype);
        tverollrlnname = (TextView) findViewById(R.id.tverollrlnname);
        tverollreasonforObjectionDeletion = (TextView) findViewById(R.id.tverollreasonforObjectionDeletion);
        tvname2 = (TextView) findViewById(R.id.tvname2);
        tvepic2 = (TextView) findViewById(R.id.tvepic2);
        tvslno2 = (TextView) findViewById(R.id.tvslno2);
        tvpart2 = (TextView) findViewById(R.id.tvpart2);
        tvmobno2 = (TextView) findViewById(R.id.tvmobno2);
        tvemail2 = (TextView) findViewById(R.id.tvemail2);
        tverollname2 = (TextView) findViewById(R.id.tverollname2);
        tverollgender2 = (TextView) findViewById(R.id.tverollgender2);
        tverollage2 = (TextView) findViewById(R.id.tverollage2);
        tverollrlntype2 = (TextView) findViewById(R.id.tverollrlntype2);
        tverollrlnname2 = (TextView) findViewById(R.id.tverollrlnname2);

        rbabsent = (RadioButton) findViewById(R.id.rbabsent);
        rbshifted = (RadioButton) findViewById(R.id.rbshifted);
        rbdead = (RadioButton) findViewById(R.id.rbdead);
        rbnosuchperson = (RadioButton) findViewById(R.id.rbnosuchperson);

        cbdetailverified = (CheckBox) findViewById(R.id.cbdetailverified);
        cbobjections = (CheckBox) findViewById(R.id.cbobjections);

        etremark = (EditText) findViewById(R.id.etremark);

        btn_submit = (Button) findViewById(R.id.btn_submit);

        queue = Volley.newRequestQueue(this);
        pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getApplicationContext().getResources().getString(R.string.Please_Wait));
        pd.setCancelable(false);

        prefs = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mob  = prefs.getString("mob","");
        stcode = prefs.getString("st_code","");
        acno = prefs.getString("AcNo","");
        partno = prefs.getString("PartNo","");
        SharedKey = prefs.getString("Key","");
        bloname = prefs.getString("BLO_NAME","");
        Log.d("SharedKey",SharedKey);
        srefno = getIntent().getExtras().getString("srefno");
        IP_HEADER = Constants.IP_HEADER;
        url = IP_HEADER+"GetFormChecklistData?refNo="+srefno+"&formType=form7&st_code="+stcode+"&ac_no="+acno+"&part_no="+partno;
        //url = "http://eronet.ecinet.in/services/api/BLONET/GetFormChecklistData?refNo="+srefno+"&formType=form7&st_code="+stcode;
        //url1 = "http://eronet.ecinet.in/services/api/BLONET/PostForm7ChecklistDetails?formType=form7&st_code="+stcode;
        url1 = IP_HEADER+"PostForm7ChecklistDetails?formType=form7&st_code="+stcode+"&ac_no="+acno+"&part_no="+partno;
        Log.d("url",url);

        Log.d("mob",mob);Log.d("stcode",stcode);Log.d("acno",acno);Log.d("partno",partno);

        rbabsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbshifted.setChecked(false);
                rbdead.setChecked(false);
                rbnosuchperson.setChecked(false);
            }
        });
        rbshifted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbabsent.setChecked(false);
                rbdead.setChecked(false);
                rbnosuchperson.setChecked(false);
            }
        });
        rbdead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbabsent.setChecked(false);
                rbshifted.setChecked(false);
                rbnosuchperson.setChecked(false);
            }
        });
        rbnosuchperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbabsent.setChecked(false);
                rbshifted.setChecked(false);
                rbdead.setChecked(false);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ConnectionStatus.isNetworkConnected(Form7.this)) {
                    submit();
                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_Internet_connection),Toast.LENGTH_SHORT).show();

            }
        });
        tvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Form7.this, WelcomeBLONew.class);
                startActivity(intent);
                finish();
            }
        });

        if(ConnectionStatus.isNetworkConnected(Form7.this)) {
            showProgressDialog();
            JSONObject obj = new JSONObject();
            Helper.getmHelper().v("OBJ",obj.toString());


            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, obj, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("On Response : ", response.toString());
                            try {
                                /*String IsSuccess = response.getString("IsSuccess");
                                Log.d("IsSuccess",IsSuccess);
                                String StateCode = response.getString("StateCode");*/
                                //Log.v("Response : ", "IsSuccess : " + IsSuccess);

                                //if(IsSuccess.trim().equals("true") || IsSuccess.trim().equals("true")) {
                                //String StateCode = response.getString("StateCode");
                                String RefNo = response.getString("RefNo");
                                Log.d("RefNo",RefNo);
                                tvrefno.setText(RefNo);
                                String ApplicantName = response.getString("ApplicantName");
                                if(!ApplicantName.equals("null") || ApplicantName!="null"){
                                    tvname.setText(ApplicantName);
                                } else tvname.setText("--");
                                //tvname.setText(ApplicantName);
                                String ApplicantEPIC  = response.getString("ApplicantEPIC");
                                if(!ApplicantEPIC.equals("null") || ApplicantEPIC!="null"){
                                    tvepic.setText(ApplicantEPIC);
                                } else tvepic.setText("--");
                                //tvepic.setText(ApplicantEPIC);
                                String ApplicantPartNo = response.getString("ApplicantPartNo");
                                if(!ApplicantPartNo.equals("null") || ApplicantPartNo!="null"){
                                    tvpart.setText(ApplicantPartNo);
                                } else tvpart.setText("--");
                               // tvpart.setText(ApplicantPartNo);
                                String ApplicantSrNoInPart = response.getString("ApplicantSrNoInPart");
                                if(!ApplicantSrNoInPart.equals("null") || ApplicantSrNoInPart!="null"){
                                    tvslno.setText(ApplicantSrNoInPart);
                                } else tvslno.setText("--");
                                //tvslno.setText(ApplicantSrNoInPart);

                                String Applicant_ERollName = response.getString("Applicant_ERollName");
                                if(!Applicant_ERollName.equals("null") || Applicant_ERollName!="null"){
                                    tverollname.setText(Applicant_ERollName);
                                } else tverollname.setText("--");
                               // tverollname.setText(Applicant_ERollName);
                                String Applicant_ERollGender = response.getString("Applicant_ERollGender");
                                if(!Applicant_ERollGender.equals("null") || Applicant_ERollGender!="null"){
                                    tverollgender.setText(Applicant_ERollGender);
                                } else tverollgender.setText("--");
                                //tverollgender.setText(Applicant_ERollGender);
                                String Applicant_ERollAge = response.getString("Applicant_ERollAge");
                                if(!Applicant_ERollAge.equals("null") || Applicant_ERollAge!="null"){
                                    tverollage.setText(Applicant_ERollAge);
                                } else tverollage.setText("--");
                                //tverollage.setText(Applicant_ERollAge);
                                String Applicant_ERollRLNType = response.getString("Applicant_ERollRLNType");
                                if(!Applicant_ERollRLNType.equals("null") || Applicant_ERollRLNType!="null"){
                                    tverollrlntype.setText(Applicant_ERollRLNType);
                                } else tverollrlntype.setText("--");
                                //tverollrlntype.setText(Applicant_ERollRLNType);
                                String Applicant_ERollRLNName = response.getString("Applicant_ERollRLNName");
                                if(!Applicant_ERollRLNName.equals("null") || Applicant_ERollRLNName!="null"){
                                    tverollrlnname.setText(Applicant_ERollRLNName);
                                } else tverollrlnname.setText("--");
                                //tverollrlnname.setText(Applicant_ERollRLNName);
                                String ObjectionReason = response.getString("ObjectionReason");
                                if(!ObjectionReason.equals("null") || ObjectionReason!="null"){
                                    tverollreasonforObjectionDeletion.setText(ObjectionReason);
                                } else tverollreasonforObjectionDeletion.setText("--");
                                //tverollreasonforObjectionDeletion.setText(ObjectionReason);

                                String ObjectedName = response.getString("ObjectedName");
                                if(!ObjectedName.equals("null") || ObjectedName!="null"){
                                    tvname2.setText(ObjectedName);
                                } else tvname2.setText("--");
                                //tvname2.setText(ObjectedName);
                                String ObjectedEPIC = response.getString("ObjectedEPIC");
                                if(!ObjectedEPIC.equals("null") || ObjectedEPIC!="null"){
                                    tvepic2.setText(ObjectedEPIC);
                                } else tvepic2.setText("--");
                                //tvepic2.setText(ObjectedEPIC);
                                String ObjectedSlNoInPart = response.getString("ObjectedSlNoInPart");
                                if(!ObjectedSlNoInPart.equals("null") || ObjectedSlNoInPart!="null"){
                                    tvslno2.setText(ObjectedSlNoInPart);
                                } else tvslno2.setText("--");
                                //tvslno2.setText(ObjectedSlNoInPart);
                                String ObjectedPart = response.getString("ObjectedPart");
                                if(!ObjectedPart.equals("null") || ObjectedPart!="null"){
                                    tvpart2.setText(ObjectedPart);
                                } else tvpart2.setText("--");
                                //tvpart2.setText(ObjectedPart);
                                String ApplicantMobile = response.getString("ApplicantMobile");
                                if(!ApplicantMobile.equals("null") || ApplicantMobile!="null"){
                                    tvmobno2.setText(ApplicantMobile);
                                } else tvmobno2.setText("--");
                                //tvmobno2.setText(ApplicantMobile);
                                String ApplicantEmailID = response.getString("ApplicantEmailID");
                                if(!ApplicantEmailID.equals("null") || ApplicantEmailID!="null"){
                                    tvemail2.setText(ApplicantEmailID);
                                } else tvemail2.setText("--");
                                //tvemail2.setText(ApplicantEmailID);

                                String Objected_ERollName = response.getString("Objected_ERollName");
                                if(!Objected_ERollName.equals("null") || Objected_ERollName!="null"){
                                    tverollname2.setText(Objected_ERollName);
                                } else tverollname2.setText("--");
                                //tverollname2.setText(Objected_ERollName);
                                String ObjectedGender = response.getString("ObjectedGender");
                                if(!ObjectedGender.equals("null") || ObjectedGender!="null"){
                                    tverollgender2.setText(ObjectedGender);
                                } else tverollgender2.setText("--");
                                //tverollgender2.setText(ObjectedGender);
                                String Objected_ERollAge = response.getString("Objected_ERollAge");
                                if(!Objected_ERollAge.equals("null") || Objected_ERollAge!="null"){
                                    tverollage2.setText(Objected_ERollAge);
                                } else tverollage2.setText("--");
                                //tverollage2.setText(Objected_ERollAge);
                                String ObjectedRLNType = response.getString("ObjectedRLNType");
                                if(!ObjectedRLNType.equals("null") || ObjectedRLNType!="null"){
                                    tverollrlntype2.setText(ObjectedRLNType);
                                } else tverollrlntype2.setText("--");
                                //tverollrlntype2.setText(ObjectedRLNType);
                                String Objected_ERollRLNName = response.getString("Objected_ERollRLNName");
                                if(!Objected_ERollRLNName.equals("null") || Objected_ERollRLNName!="null"){
                                    tverollrlnname2.setText(Objected_ERollRLNName);
                                } else tverollrlnname2.setText("--");
                                //tverollrlnname2.setText(Objected_ERollRLNName);

                                    /*String PartNo = response.getString("PartNo");
                                    String BLOName = response.getString("BLOName");
                                    String BLO_ID = response.getString("BLO_ID");
                                    String Password = response.getString("Password");*/

                                //}


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            hideProgressDialog();

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub

                        }
                    }){
                /** Passing some request headers* */
                @Override
                public Map getHeaders() throws AuthFailureError {
                    HashMap headers = new HashMap();
                    headers.put("Content-Type", "application/json");
                    headers.put("access_token", SharedKey);
                    return headers;
                }
            };
            queue.add(jsObjRequest);
        } else {
            Intent intent=new Intent(getApplicationContext(),NoNetwork.class);
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

    }

    private void submit() {
        if(rbabsent.isChecked()){
            SapplicantASDNSP = "A";
        } else if(rbshifted.isChecked()){
            SapplicantASDNSP = "S";
        } else  if(rbdead.isChecked()){
            SapplicantASDNSP = "D";
        } else if(rbnosuchperson.isChecked()){
            SapplicantASDNSP = "NP";
        } else {SapplicantASDNSP = "";}
        if(!(etremark.getText().toString().isEmpty())){
            Sremark = etremark.getText().toString();
        } else { Sremark = "";}
        if(cbobjections.isChecked()){
            SObjection = "true";
        } else {SObjection = "false";}

        if(cbdetailverified.isChecked()){
            SapplicantVerified = "true";
        } else { SapplicantVerified = "false";}

        keyVal.put("RefNO",srefno);
        keyVal.put("FormType","form7");
        keyVal.put("BLO_Name",bloname);
        keyVal.put("Is_Applicant_Deatails_Correct",SapplicantVerified);
        keyVal.put("BLO_Report",SapplicantASDNSP);
        keyVal.put("Is_Objected",SObjection);
        keyVal.put("BLO_Remarks",Sremark);

        try {
            jsonBody.put("RefNO",srefno);
            jsonBody.put("FormType","form7");
            jsonBody.put("BLO_Name",bloname);
            jsonBody.put("BLO_Report",SapplicantASDNSP);
            jsonBody.put("Is_Applicant_Deatails_Correct",SapplicantVerified);
            jsonBody.put("Is_Objected",SObjection);
            jsonBody.put("BLO_Remarks",Sremark);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("json formed",jsonBody.toString());
        vollyRequestForPost(url1, keyVal);

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
                                final Dialog dialog = new Dialog(Form7.this);
                                dialog.setContentView(R.layout.custom_blo_report_done_message);
                                Button btnok = (Button) dialog.findViewById(R.id.btnok);
                                dialog.setCanceledOnTouchOutside(false);

                                btnok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        Intent intent=new Intent(Form7.this,CheckListVerificationActivity.class);
                                        startActivity(intent);
                                        finish();


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
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Form7.this,CheckListVerificationActivity.class);
        startActivity(intent);
        finish();
    }

}
