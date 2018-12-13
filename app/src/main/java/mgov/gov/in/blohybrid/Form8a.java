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
import android.widget.LinearLayout;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mgov.gov.in.blohybrid.Constants;

public class Form8a extends AppCompatActivity {

    TextView tvhome,tvname,tvepic,tvslno,tvpart,tvrefno,tvemail,tvmobile,tverollname,tverollgender,tverollage,tverollrlntype,tverolldob,
            tverollrlnname,tvhsno2,tvstreet2,tvvillage2,tvpo2,tvpincode2,tvdistrict2;
    CheckBox cbaddress,cbdob,cbdetailverified,cbdataentryerror;
    RadioButton rbabsent,rbshifted,rbdead,rbnosuchperson;
    EditText etidduplicatecount,etname,etlname,etgender,etdobage,etrlnname,etrlnlname,etrlntype,ethsno,etstreet,etvillage,etpo,etdistrict,etpincode,etremark;;
    Button btn_submit,btdobage;;
    LinearLayout lldataentryerrorifany;
    int year,month,day,yearFinal,monthFinal,dayFinal;

    RequestQueue queue;
    ProgressDialog pd;
    SharedPreferences prefs;
    String mob,stcode,acno,partno,url,url1,srefno,SapplicantASDNSP,SapplicantVerified,SaddressConfirmed,Sremark,SdobConfirmed,SduplicateCount,SharedKey;
    String SFname,SLname,Sgender,Sdob,SrlnFname,SrlnLname,SrlnType,Shsno,Sstreet,SPO,SDist,Spincode,Is_Spellings_Correct,bloname,IP_HEADER;
    public static final String MyPREFERENCES = "MyPrefs";
    JSONObject jsonBody = new JSONObject();
    Map<String, String> keyVal = new LinkedHashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form8a);

        tvhome = (TextView) findViewById(R.id.tvhome);
        tvname  = (TextView) findViewById(R.id.tvname);
        tvepic  = (TextView) findViewById(R.id.tvepic);
        tvslno  = (TextView) findViewById(R.id.tvslno);
        tvpart  = (TextView) findViewById(R.id.tvpart);
        tvrefno  = (TextView) findViewById(R.id.tvrefno);
        tvemail  = (TextView) findViewById(R.id.tvemail);
        tvmobile  = (TextView) findViewById(R.id.tvmobile);
        tverollname  = (TextView) findViewById(R.id.tverollname);
        tverollgender  = (TextView) findViewById(R.id.tverollgender);
        tverollage  = (TextView) findViewById(R.id.tverollage);
        tverollrlntype  = (TextView) findViewById(R.id.tverollrlntype);
        tverolldob  = (TextView) findViewById(R.id.tverolldob);
        tverollrlnname  = (TextView) findViewById(R.id.tverollrlnname);
        tvhsno2  = (TextView) findViewById(R.id.tvhsno2);
        tvstreet2  = (TextView) findViewById(R.id.tvstreet2);
        tvvillage2  = (TextView) findViewById(R.id.tvvillage2);
        tvpo2  = (TextView) findViewById(R.id.tvpo2);
        tvpincode2  = (TextView) findViewById(R.id.tvpincode2);
        tvdistrict2 = (TextView) findViewById(R.id.tvdistrict2);

        cbaddress = (CheckBox) findViewById(R.id.cbaddress);
        cbdob = (CheckBox) findViewById(R.id.cbdob);
        cbdetailverified = (CheckBox) findViewById(R.id.cbdetailverified);
        cbdataentryerror = (CheckBox) findViewById(R.id.cbdataentryerror);

        rbabsent = (RadioButton) findViewById(R.id.rbabsent);
        rbshifted = (RadioButton) findViewById(R.id.rbshifted);
        rbdead = (RadioButton) findViewById(R.id.rbdead);
        rbnosuchperson = (RadioButton) findViewById(R.id.rbnosuchperson);

        etidduplicatecount = (EditText) findViewById(R.id.etidduplicatecount);
        etremark = (EditText) findViewById(R.id.etremark);
        etname =(EditText) findViewById(R.id.etname);
        etlname =(EditText) findViewById(R.id.etlname);
        etgender =(EditText) findViewById(R.id.etgender);
        //etdobage =(EditText) findViewById(R.id.etdobage);
        etrlnname =(EditText) findViewById(R.id.etrlnname);
        etrlnlname =(EditText) findViewById(R.id.etrlnlname);
        etrlntype =(EditText) findViewById(R.id.etrlntype);
        ethsno =(EditText) findViewById(R.id.ethsno);
        etstreet =(EditText) findViewById(R.id.etstreet);
        etvillage =(EditText) findViewById(R.id.etvillage);
        etpo =(EditText) findViewById(R.id.etpo);
        etdistrict =(EditText) findViewById(R.id.etdistrict);
        etpincode =(EditText) findViewById(R.id.etpincode);

        lldataentryerrorifany = (LinearLayout) findViewById(R.id.lldataentryerrorifany);
        lldataentryerrorifany.setVisibility(View.GONE);

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
        Log.d("SharedKey",SharedKey);
        bloname = prefs.getString("BLO_NAME","");
        Log.d("SharedKey",SharedKey);
        srefno = getIntent().getExtras().getString("srefno");
        IP_HEADER = Constants.IP_HEADER;
        //url = "http://117.239.180.198/BLONet_API_Dev/api/BLONet/GetFormChecklistData?refNo="+srefno+"&formType=form8a&st_code="+stcode;
        url = IP_HEADER+"GetFormChecklistData?refNo="+srefno+"&formType=form8a&st_code="+stcode+"&ac_no="+acno+"&part_no="+partno;
        url1 = IP_HEADER+"PostForm8AChecklistDetails?formType=form8a&st_code="+stcode+"&ac_no="+acno+"&part_no="+partno;
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
                if(ConnectionStatus.isNetworkConnected(Form8a.this)) {
                    submit();
                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_Internet_connection),Toast.LENGTH_SHORT).show();

            }
        });
        tvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Form8a.this, WelcomeBLONew.class);
                startActivity(intent);
                finish();
            }
        });

        cbdataentryerror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbdataentryerror.isChecked()){
                    lldataentryerrorifany.setVisibility(View.VISIBLE);
                    if((!tvname.getText().toString().equals("null") || tvname.getText().toString()!="null") && (!tvname.getText().toString().equals("--") || tvname.getText().toString()!="--")){
                        String[] result1 = tvname.getText().toString().split("\\s+");
                        StringBuffer sb = new StringBuffer();
                        int Count = result1.length;
                        Log.d("Count", String.valueOf(Count));
                        if(Count==1){
                            etname.setText(tvname.getText().toString().split("\\s+")[0]);
                        } else if(Count>1){
                            // etname.setText(tvname.getText().toString().split("\\s+")[0]);
                            etname.setText(result1[0]);
                            for(int i = 1; i<Count ; i++){
                                sb.append(result1[i]+" ");
                            }
                            etlname.setText(sb);
                        }

                    } else {
                        etname.setText("--");
                        etlname.setText("--");
                    }

                    /*if(!tvgender.getText().toString().equals("--") || tvgender.getText().toString()!="--"){
                        etgender.setText(tvgender2.getText().toString());
                    }*/
                    //btdobage.setText("Confirm DOB");
                    /*if(!tvrlnname2.getText().toString().equals("--") || tvrlnname2.getText().toString()!="--"){
                        String[] result1 = tvrlnname2.getText().toString().split("\\s+");
                        StringBuffer sb = new StringBuffer();
                        int Count = result1.length;
                        Log.d("Count", String.valueOf(Count));
                        if(Count==1){
                            etrlnname.setText(tvrlnname2.getText().toString().split("\\s+")[0]);
                        } else if(Count>1){
                            // etname.setText(tvname.getText().toString().split("\\s+")[0]);
                            etrlnname.setText(result1[0]);
                            for(int i = 1; i<Count ; i++){
                                sb.append(result1[i]+" ");
                            }
                            etrlnlname.setText(sb);
                        }

                    } else {
                        etrlnname.setText("--");
                        etrlnlname.setText("--");
                    }*/
                    //etrlntype.setText(tvrlntype2.getText().toString());
                    ethsno.setText(tvhsno2.getText().toString());
                    etstreet.setText(tvstreet2.getText().toString());
                    etpo.setText(tvpo2.getText().toString());
                    etdistrict.setText(tvdistrict2.getText().toString());
                    etpincode.setText(tvpincode2.getText().toString());


                } else {
                    lldataentryerrorifany.setVisibility(View.GONE);
                    etrlntype.setText("");
                    ethsno.setText("");
                    etstreet.setText("");
                    etpo.setText("");
                    etdistrict.setText("");
                    etpincode.setText("");
                    etrlnname.setText("");
                    etrlnlname.setText("");
                    etgender.setText("");
                    etname.setText("");
                    etlname.setText("");
                    //btdobage.setText("");
                }
            }
        });





        if(ConnectionStatus.isNetworkConnected(Form8a.this)) {
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
                                //tvpart.setText(ApplicantPartNo);
                                String ApplicantSrNoInPart = response.getString("ApplicantSrNoInPart");
                                if(!ApplicantSrNoInPart.equals("null") || ApplicantSrNoInPart!="null"){
                                    tvslno.setText(ApplicantSrNoInPart);
                                } else tvslno.setText("--");
                                //tvslno.setText(ApplicantSrNoInPart);
                                String ApplicantEmailID = response.getString("ApplicantEmailID");
                                if(!ApplicantEmailID.equals("null") || ApplicantEmailID!="null"){
                                    tvemail.setText(ApplicantEmailID);
                                } else tvemail.setText("--");
                                //tvemail.setText(ApplicantEmailID);
                                String ApplicantMobile = response.getString("ApplicantMobile");
                                if(!ApplicantMobile.equals("null") || ApplicantMobile!="null"){
                                    tvmobile.setText(ApplicantMobile);
                                } else tvmobile.setText("--");
                                //tvmobile.setText(ApplicantMobile);

                                String Applicant_ERollName = response.getString("Applicant_ERollName");
                                if(!Applicant_ERollName.equals("null") || Applicant_ERollName!="null"){
                                    tverollname.setText(Applicant_ERollName);
                                } else tverollname.setText("--");
                                //tverollname.setText(Applicant_ERollName);
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
                                String Applicant_ERollDOB = response.getString("Applicant_ERollDOB");
                                //tverolldob.setText(Applicant_ERollDOB);
                                if((!Applicant_ERollDOB.equals("null") || Applicant_ERollDOB!="null") && (!Applicant_ERollDOB.equals("--") || Applicant_ERollDOB!="--")){
                                    String result1 = Applicant_ERollDOB.split("T")[0];
                                    tverolldob.setText(result1);
                                } else tverolldob.setText("--");

                                String ApplicantHouseNo = response.getString("ApplicantHouseNo");
                                if(!ApplicantHouseNo.equals("null") || ApplicantHouseNo!="null"){
                                    tvhsno2.setText(ApplicantHouseNo);
                                } else tvhsno2.setText("--");
                               // tvhsno2.setText(ApplicantHouseNo);
                                String ApplicantStreetArea = response.getString("ApplicantStreetArea");
                                if(!ApplicantStreetArea.equals("null") || ApplicantStreetArea!="null"){
                                    tvstreet2.setText(ApplicantStreetArea);
                                } else tvstreet2.setText("--");
                                //tvstreet2.setText(ApplicantStreetArea);
                                String ApplicantVillage = response.getString("ApplicantVillage");
                                if(!ApplicantVillage.equals("null") || ApplicantVillage!="null"){
                                    tvvillage2.setText(ApplicantVillage);
                                } else tvvillage2.setText("--");
                                //tvvillage2.setText(ApplicantVillage);
                                String ApplicantPost = response.getString("ApplicantPost");
                                if(!ApplicantPost.equals("null") || ApplicantPost!="null"){
                                    tvpo2.setText(ApplicantPost);
                                } else tvpo2.setText("--");
                                //tvpo2.setText(ApplicantPost);

                                String ApplicantDistrict = response.getString("ApplicantDistrict");
                                if(!ApplicantDistrict.equals("null") || ApplicantDistrict!="null"){
                                    tvdistrict2.setText(ApplicantDistrict);
                                } else tvpo2.setText("--");
                                String ApplicantPinCode = response.getString("ApplicantPinCode");
                                if(!ApplicantPinCode.equals("null") || ApplicantPinCode!="null"){
                                    tvpincode2.setText(ApplicantPinCode);
                                } else tvpincode2.setText("--");
                                //tvpincode2.setText(ApplicantPinCode);

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

        if(!(etpincode.getText().toString().isEmpty()) && etpincode.length()!=6){
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Invalid_Pincode),Toast.LENGTH_SHORT).show();
            return;
        }

        if(cbdataentryerror.isChecked()){
            Pattern pattern = Pattern.compile("[a-zA-Z0-9\\s+]*");
            //Pattern pattern= Pattern.compile("[^a-z A-Z0-9 \\s+]*");

            String str = etname.getText().toString();
            String strLastname  =etlname.getText().toString();
            Matcher matcher = pattern.matcher(str);

            if (!matcher.matches()) {
                Toast.makeText(getApplicationContext(),"First Name contains special character",Toast.LENGTH_LONG).show();
                Log.d("string "+str , " FirstNAme contains special character");
                return;
            }
            Matcher matcher1 = pattern.matcher(strLastname);
            if (!matcher1.matches()) {
                Toast.makeText(getApplicationContext(),"Last Name contains special character",Toast.LENGTH_LONG).show();
                Log.d("string "+str , " Last name contains special character");
                return;
            }

        }

        if(cbaddress.isChecked()){
            SaddressConfirmed = "true";
        }else {SaddressConfirmed = "false";}

        if(cbdob.isChecked()){
            SdobConfirmed = "true";
        }else {SdobConfirmed = "false";}
        if(rbabsent.isChecked()){
            SapplicantASDNSP = "A";
        } else if(rbshifted.isChecked()){
            SapplicantASDNSP = "S";
        } else  if(rbdead.isChecked()){
            SapplicantASDNSP = "D";
        } else if(rbnosuchperson.isChecked()){
            SapplicantASDNSP = "NP";
        } else {SapplicantASDNSP = "";}
        if(!(etidduplicatecount.getText().toString().isEmpty())){
            SduplicateCount = etidduplicatecount.getText().toString();
        } else { SduplicateCount = "";}
        if(!(etremark.getText().toString().isEmpty())){
            Sremark = etremark.getText().toString();
        } else { Sremark = "";}

        if(cbdetailverified.isChecked()){
            SapplicantVerified = "true";
        } else { SapplicantVerified = "false";}

        if(cbdataentryerror.isChecked()){
            Is_Spellings_Correct = "false";
        }else Is_Spellings_Correct= "true";

        if(!(etname.getText().toString().isEmpty())){
            SFname = etname.getText().toString();
        } else { SFname = "";}

        if(!(etlname.getText().toString().isEmpty())){
            SLname = etlname.getText().toString();
        } else { SLname = "";}

        if(!(ethsno.getText().toString().isEmpty())){
            Shsno = ethsno.getText().toString();
        } else { Shsno = "";}

        if(!(etstreet.getText().toString().isEmpty())){
            Sstreet = etstreet.getText().toString();
        } else { Shsno = "";}

        if(!(etpo.getText().toString().isEmpty())){
            SPO = etpo.getText().toString();
        } else { SPO = "";}

        if(!(etdistrict.getText().toString().isEmpty())){
            SDist = etdistrict.getText().toString();
        } else { SDist = "";}

        if(!(etpincode.getText().toString().isEmpty())){
            Spincode = etpincode.getText().toString();
        } else { Spincode = "";}


        keyVal.put("RefNO",srefno);
        keyVal.put("FormType","form8a");
        keyVal.put("Is_Address_Confirmed",SaddressConfirmed);
        keyVal.put("Is_Spellings_Correct",Is_Spellings_Correct);
        keyVal.put("BLO_Name",bloname);
        keyVal.put("Is_Duplicate_Found","false");
        keyVal.put("FirstName",SFname);
        keyVal.put("LastName",SLname);
        keyVal.put("HouseNo",Shsno);
        keyVal.put("StreetArea",Sstreet);
        keyVal.put("PostOffice",SPO);
        keyVal.put("District",SDist);
        keyVal.put("PinCode",Spincode);
        keyVal.put("Is_Age_Proof_Ok",SdobConfirmed);
        keyVal.put("BLO_Report",SapplicantASDNSP);
        keyVal.put("No_Of_Duplicate_Applications",SduplicateCount);
        keyVal.put("BLO_Remarks",Sremark);

        try {
            jsonBody.put("RefNO",srefno);
            jsonBody.put("FormType","form8a");
            jsonBody.put("Is_Address_Confirmed",SaddressConfirmed);
            jsonBody.put("Is_Spellings_Correct",Is_Spellings_Correct);
            jsonBody.put("BLO_Name",bloname);
            jsonBody.put("Is_Duplicate_Found","false");
            jsonBody.put("FirstName",SFname);
            jsonBody.put("LastName",SLname);
            jsonBody.put("HouseNo",Shsno);
            jsonBody.put("StreetArea",Sstreet);
            jsonBody.put("PostOffice",SPO);
            jsonBody.put("District",SDist);
            jsonBody.put("PinCode",Spincode);
            jsonBody.put("Is_Age_Proof_Ok",SdobConfirmed);
            jsonBody.put("BLO_Report",SapplicantASDNSP);
            jsonBody.put("No_Of_Duplicate_Applications",SduplicateCount);
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
                                final Dialog dialog = new Dialog(Form8a.this);
                                dialog.setContentView(R.layout.custom_blo_report_done_message);
                                Button btnok = (Button) dialog.findViewById(R.id.btnok);
                                dialog.setCanceledOnTouchOutside(false);

                                btnok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        Intent intent=new Intent(Form8a.this,CheckListVerificationActivity.class);
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
        Intent intent=new Intent(Form8a.this,CheckListVerificationActivity.class);
        startActivity(intent);
        finish();
    }

}
