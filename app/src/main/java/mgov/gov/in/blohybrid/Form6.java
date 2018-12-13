package mgov.gov.in.blohybrid;

import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
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

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mgov.gov.in.blohybrid.Constants;

public class Form6 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    TextView tvhome,tvname,tvrefno,tvslno,tvgender,tvage,tvrlntype,tvrlnname,tvepic,tvpart,tvemail,tvdob,tvhsno,tvstreet,tvdistrict,tvvillage,tvpo,tvpincode;
    CheckBox cbaddress,cbdob,cbdeclaration,cbdetailverified,cbdataentryerror;
    RadioButton rbabsent,rbshifted,rbdead,rbnosuchperson;
    EditText etidduplicatecount,etname,etlname,etgender,etrlnname,etrlnlname,etrlntype,ethsno,etstreet,etpo,etdistrict,etpincode,etremark;
    LinearLayout lldataentryerrorifany;
    Button btn_submit,btdobage;
    RequestQueue queue;
    ProgressDialog pd;
    SharedPreferences prefs;
    int year,month,day,yearFinal,monthFinal,dayFinal;
    String mob,stcode,acno,partno,bloname,url,url1,srefno,SaddressConfirmed,SdobConfirmed,Is_Duplicate_Found ,isAgeabove22Declaration,SapplicantASDNSP,SduplicateCount,SapplicantVeridied,Is_Spellings_Correct,
    SFname,SLname,Sgender,Sdob,SrlnFname,SrlnLname,SrlnType,Shsno,Sstreet,SPO,SDist,Spincode,Sremark,SharedKey,IP_HEADER;
    public static final String MyPREFERENCES = "MyPrefs";
    JSONObject jsonBody = new JSONObject();
    Map<String, String> keyVal = new LinkedHashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form6);

        tvhome = (TextView) findViewById(R.id.tvhome);
        tvname = (TextView) findViewById(R.id.tvname);
        tvrefno = (TextView) findViewById(R.id.tvrefno);
        tvslno = (TextView) findViewById(R.id.tvslno);
        tvgender = (TextView) findViewById(R.id.tvgender);
        tvage = (TextView) findViewById(R.id.tvage);
        tvrlntype = (TextView) findViewById(R.id.tvrlntype);
        tvrlnname = (TextView) findViewById(R.id.tvrlnname);
        tvepic = (TextView) findViewById(R.id.tvepic);
        tvpart = (TextView) findViewById(R.id.tvpart);
        tvemail = (TextView) findViewById(R.id.tvemail);
        tvdob = (TextView) findViewById(R.id.tvdob);
        tvstreet = (TextView) findViewById(R.id.tvstreet);
        tvvillage = (TextView) findViewById(R.id.tvvillage);
        tvhsno = (TextView) findViewById(R.id.tvhsno);
        tvdistrict = (TextView) findViewById(R.id.tvdistrict);
        tvpo = (TextView) findViewById(R.id.tvpo);
        tvpincode = (TextView) findViewById(R.id.tvpincode);
        cbaddress = (CheckBox) findViewById(R.id.cbaddress);
        cbdob = (CheckBox) findViewById(R.id.cbdob);
        cbdeclaration = (CheckBox) findViewById(R.id.cbdeclaration);
        cbdetailverified = (CheckBox) findViewById(R.id.cbdetailverified);
        cbdataentryerror = (CheckBox) findViewById(R.id.cbdataentryerror);
        rbabsent = (RadioButton) findViewById(R.id.rbabsent);
        rbshifted = (RadioButton) findViewById(R.id.rbshifted);
        rbdead = (RadioButton) findViewById(R.id.rbdead);
        rbnosuchperson = (RadioButton) findViewById(R.id.rbnosuchperson);
        etidduplicatecount = (EditText) findViewById(R.id.etidduplicatecount);
        etname = (EditText) findViewById(R.id.etname);
        etlname = (EditText) findViewById(R.id.etlname);
        etgender = (EditText) findViewById(R.id.etgender);
        btdobage = (Button) findViewById(R.id.btdobage);
        etrlnname = (EditText) findViewById(R.id.etrlnname);
        etrlnlname = (EditText) findViewById(R.id.etrlnlname);
        etrlntype = (EditText) findViewById(R.id.etrlntype);
        ethsno = (EditText) findViewById(R.id.ethsno);
        etstreet = (EditText) findViewById(R.id.etstreet);
        etpo = (EditText) findViewById(R.id.etpo);
        etdistrict = (EditText) findViewById(R.id.etdistrict);
        etpincode = (EditText) findViewById(R.id.etpincode);
        etremark = (EditText) findViewById(R.id.etremark);
        lldataentryerrorifany = (LinearLayout) findViewById(R.id.lldataentryerrorifany);
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
        bloname = prefs.getString("BLO_NAME","");
        SharedKey = prefs.getString("Key","");
        Log.d("SharedKey",SharedKey);
        srefno = getIntent().getExtras().getString("srefno");
        IP_HEADER = Constants.IP_HEADER;
        //Main//url = "http://eronet.ecinet.in/services/api/BLONet/GetFormChecklistData?refNo="+srefno+"&formType=form6&st_code="+stcode;
        url = IP_HEADER+"GetFormChecklistData?refNo="+srefno+"&formType=form6&st_code="+stcode+"&ac_no="+acno+"&part_no="+partno;
        //Main//url1 = "http://eronet.ecinet.in/services/api/BLONET/PostForm6ChecklistDetails?formType=form6&st_code="+stcode;
        url1 = IP_HEADER+"PostForm6ChecklistDetails?formType=form6&st_code="+stcode+"&ac_no="+acno+"&part_no="+partno;
        Log.d("url",url);

        Log.d("mob",mob);Log.d("stcode",stcode);Log.d("acno",acno);Log.d("partno",partno);

        lldataentryerrorifany.setVisibility(View.GONE);
        if(ConnectionStatus.isNetworkConnected(Form6.this)) {
            showProgressDialog();
            new RequestTask3().execute(url);

        } else {
            Intent intent=new Intent(getApplicationContext(),NoNetwork.class);
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
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

            if(!tvgender.getText().toString().equals("--") || tvgender.getText().toString()!="--"){
                etgender.setText(tvgender.getText().toString());
            }

            if((!tvdob.getText().toString().equals("null") || tvdob.getText().toString()!="null") && (!tvdob.getText().toString().equals("--") || tvdob.getText().toString()!="--")){

                String[] result1 = tvdob.getText().toString().split("-");
                StringBuffer sb = new StringBuffer();
                int Count = result1[0].length();
                Log.d("CountDB", String.valueOf(Count));
                if(Count==4){
                   String SyearFinal = result1[0];
                   String SmonthFinal= result1[1];
                   String SdayFinal = result1[2];
                   // int CountYear = result1[0].length();
                    int CountMonth = result1[1].length();
                    int CountDay = result1[2].length();
                    Log.e("SyearFinal",result1[0]);
                    Log.e("SmonthFinal",result1[1]);
                    Log.e("SdayFinal",result1[2]);
                    String month3,day3;
                    if(CountMonth==1 ){
                        Log.d("Sqwertymon", String.valueOf(CountMonth));
                        month3 = "0"+SmonthFinal;
                    } else month3 = SmonthFinal;
                    if(CountDay==1){
                        Log.d("Sqwertyday", String.valueOf(CountDay));
                        day3 = "0"+SdayFinal;
                    } else day3 = SdayFinal;
                    btdobage.setText(day3+"/"+month3+"/"+SyearFinal);
                } else if(Count==2){
                     btdobage.setText(tvdob.getText().toString());

                } else btdobage.setText("Confirm DOB");
            } else btdobage.setText("Confirm DOB");






            if(!tvrlnname.getText().toString().equals("--") || tvrlnname.getText().toString()!="--"){
                String[] result1 = tvrlnname.getText().toString().split("\\s+");
                StringBuffer sb = new StringBuffer();
                int Count = result1.length;
                Log.d("Count", String.valueOf(Count));
                if(Count==1){
                    etrlnname.setText(tvrlnname.getText().toString().split("\\s+")[0]);
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
            }
            etrlntype.setText(tvrlntype.getText().toString());
            ethsno.setText(tvhsno.getText().toString());
            etstreet.setText(tvstreet.getText().toString());
            etpo.setText(tvpo.getText().toString());
            etdistrict.setText(tvdistrict.getText().toString());
            etpincode.setText(tvpincode.getText().toString());


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
            btdobage.setText("");
        }
       }
      });

        btdobage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Form6.this,Form6.this,year,month,day);
                datePickerDialog.show();

            }
        });

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
                if(ConnectionStatus.isNetworkConnected(Form6.this)) {
                    submit();
                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_Internet_connection),Toast.LENGTH_SHORT).show();

            }
        });

        tvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Form6.this, WelcomeBLONew.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void submit() {
      if(!(etpincode.getText().toString().isEmpty()) && etpincode.length()!=6){
          Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Invalid_Pincode),Toast.LENGTH_SHORT).show();
          return;
      }
      /*if(!etrlntype.getText().toString().equals("M") && !etrlntype.getText().toString().equals("F") && !etrlntype.getText().toString().equals("H") &&
        !etrlntype.getText().toString().equals("O") && etrlntype.getText().toString()!=("M") && etrlntype.getText().toString()!=("F") &&
        etrlntype.getText().toString()!=("H") && etrlntype.getText().toString()!=("O")){
          if(!etrlntype.getText().toString().isEmpty()){
              Toast.makeText(getApplicationContext(),"RLN TYPE must be either of M/F/H/O",Toast.LENGTH_LONG).show();
              return;
          }
        }*/
        if(cbdataentryerror.isChecked()){
            if(!etrlntype.getText().toString().trim().equals("M")){
                if(!etrlntype.getText().toString().trim().equals("F")){
                    if(!etrlntype.getText().toString().trim().equals("H")){
                        if(!etrlntype.getText().toString().trim().equals("O")){
                            Toast.makeText(getApplicationContext(),"RLN TYPE must be either of M/F/H/O",Toast.LENGTH_LONG).show();
                            Log.d("rlntype",etrlntype.getText().toString().trim());
                            return;

                        }
                    }
                }

            }
            if(!etgender.getText().toString().trim().equals("M")){
                if(!etgender.getText().toString().trim().equals("F")){
                    if(!etgender.getText().toString().trim().equals("T")){
                            Toast.makeText(getApplicationContext(),"Gender must be either of M/F/T",Toast.LENGTH_LONG).show();
                            Log.d("Gender",etgender.getText().toString().trim());
                            return;
                    }
                }

            }

            if(btdobage.getText().toString().equals("Confirm DOB"))
            {
                Toast.makeText(getApplicationContext(),"Confirm Date of Birth",Toast.LENGTH_LONG).show();
                Log.d("Confirm DOB",btdobage.getText().toString());
                return;
            }

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


        /*if(!etgender.getText().toString().trim().equals("M") && !etgender.getText().toString().trim().equals("F") && !etgender.getText().toString().trim().equals("T") &&
                etgender.getText().toString().trim()!=("M") && etgender.getText().toString().trim()!=("F") &&
                etgender.getText().toString().trim()!=("T") ){
            if(!etgender.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Gender must be either of M/F/T",Toast.LENGTH_LONG).show();
                return;
            }
        }*/



      if(cbaddress.isChecked()){
          SaddressConfirmed = "true";
      }else {SaddressConfirmed = "false";}

        if(cbdob.isChecked()){
            SdobConfirmed = "true";
        }else {SdobConfirmed = "false";}

        if(cbdeclaration.isChecked()){
            isAgeabove22Declaration = "null";
        } else { isAgeabove22Declaration = "null";}

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

        if(cbdetailverified.isChecked()){
            SapplicantVeridied = "true";
        } else {SapplicantVeridied = "false";}

        if(cbdataentryerror.isChecked()){
            Is_Spellings_Correct = "false";
        }else Is_Spellings_Correct= "true";

        if(!(etname.getText().toString().isEmpty())){
            SFname = etname.getText().toString();
        } else { SFname = "";}

        if(!(etname.getText().toString().isEmpty())){
            SFname = etname.getText().toString();
        } else { SFname = "";}

        if(!(etlname.getText().toString().isEmpty())){
            SLname = etlname.getText().toString();
        } else { SLname = "";}
        if(!(etgender.getText().toString().isEmpty())){
            Sgender = etgender.getText().toString();
        } else { Sgender = "";}

        if(!(btdobage.getText().toString().isEmpty())){
            Sdob = btdobage.getText().toString();
        } else { Sdob = "";}

        if(!(etrlnname.getText().toString().isEmpty())){
            SrlnFname = etrlnname.getText().toString();
        } else { SrlnFname = "";}

        if(!(etrlnlname.getText().toString().isEmpty())){
            SrlnLname = etrlnlname.getText().toString();
        } else { SrlnLname = "";}

        if(!(etrlntype.getText().toString().isEmpty())){
            SrlnType = etrlntype.getText().toString();
        } else { SrlnType = "";}

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

        if(!(etremark.getText().toString().isEmpty())){
            Sremark = etremark.getText().toString();
        } else { Sremark = "";}

        keyVal.put("RefNO",srefno);
        keyVal.put("FormType","form6");
        keyVal.put("Is_Address_Confirmed",SaddressConfirmed);
        keyVal.put("Is_Age_Proof_Ok",SdobConfirmed);
        keyVal.put("Is_Spellings_Correct",Is_Spellings_Correct);
        keyVal.put("BLO_Name",bloname);
        keyVal.put("AGE_PROOF",isAgeabove22Declaration);
        keyVal.put("BLO_Report",SapplicantASDNSP);
        keyVal.put("No_Of_Duplicate_Applications",SduplicateCount);
        keyVal.put("Is_Applicant_Deatails_Correct",SapplicantVeridied);
        keyVal.put("FirstName",SFname);
        keyVal.put("LastName",SLname);
        keyVal.put("Gender",Sgender);
        keyVal.put("Is_Duplicate_Found","false");
        //keyVal.put("Age",Sdob);
        keyVal.put("DOB",Sdob);
        keyVal.put("RLN_FirstName",SrlnFname);
        keyVal.put("RLN_LastName",SrlnLname);
        keyVal.put("RLN_Type",SrlnType);
        keyVal.put("HouseNo",Shsno);
        keyVal.put("StreetArea",Sstreet);
        keyVal.put("PostOffice",SPO);
        keyVal.put("District",SDist);
        keyVal.put("PinCode",Spincode);
        keyVal.put("BLO_Remarks",Sremark);

        try {
            jsonBody.put("RefNO",srefno);
            jsonBody.put("FormType","form6");
            jsonBody.put("Is_Address_Confirmed",SaddressConfirmed);
            jsonBody.put("Is_Age_Proof_Ok",SdobConfirmed);
            jsonBody.put("AGE_PROOF",isAgeabove22Declaration);
            jsonBody.put("BLO_Report",SapplicantASDNSP);
            jsonBody.put("No_Of_Duplicate_Applications",SduplicateCount);
            jsonBody.put("Is_Applicant_Deatails_Correct",SapplicantVeridied);
            jsonBody.put("Is_Spellings_Correct",Is_Spellings_Correct);
            jsonBody.put("Is_Duplicate_Found","false");
            jsonBody.put("BLO_Name",bloname);
            jsonBody.put("FirstName",SFname);
            jsonBody.put("LastName",SLname);
            jsonBody.put("Gender",Sgender);
            //jsonBody.put("Age",Sdob);
            jsonBody.put("DOB",Sdob);
            jsonBody.put("RLN_FirstName",SrlnFname);
            jsonBody.put("RLN_LastName",SrlnLname);
            jsonBody.put("RLN_Type",SrlnType);
            jsonBody.put("HouseNo",Shsno);
            jsonBody.put("StreetArea",Sstreet);
            jsonBody.put("PostOffice",SPO);
            jsonBody.put("District",SDist);
            jsonBody.put("PinCode",Spincode);
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
                                final Dialog dialog = new Dialog(Form6.this);
                                dialog.setContentView(R.layout.custom_blo_report_done_message);
                                Button btnok = (Button) dialog.findViewById(R.id.btnok);
                                dialog.setCanceledOnTouchOutside(false);

                                btnok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        Intent intent=new Intent(Form6.this,CheckListVerificationActivity.class);
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
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        yearFinal = i;
        monthFinal= i1 +1;
        dayFinal = i2;
        String day3="",month3="";
        Log.e("monthFinal", String.valueOf(monthFinal));
        Log.e("dayFinal", String.valueOf(dayFinal));
        Log.e("ML", String.valueOf(String.valueOf(monthFinal).length()));
        Log.e("DL", String.valueOf(String.valueOf(dayFinal).length()));
        String month2 = String.valueOf(String.valueOf(monthFinal).length());
        String day2 = String.valueOf(String.valueOf(dayFinal).length());
        Log.d("month2",month2);
        Log.d("day2",day2);
        if(month2=="1" || month2.equals("1")){
            Log.d("qwertymon", month2);
           month3 = "0"+monthFinal;
        } else month3 = String.valueOf(monthFinal);
        if(day2=="1" || day2.equals("1")){
            Log.d("qwertyday", day2);
            day3 = "0"+dayFinal;
        } else day3 = String.valueOf(dayFinal);
        int y = Calendar.getInstance().get(Calendar.YEAR);
        if(y-yearFinal >=18)
        btdobage.setText(day3+"/"+month3+"/"+yearFinal);
        else Toast.makeText(getApplicationContext(),"Applicant must be 18+ year",Toast.LENGTH_LONG).show();

    }

    class RequestTask3 extends AsyncTask<String, String, String> {

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
                JSONObject jsonObject = new JSONObject(result);
                Log.v("response jsonObject",jsonObject.toString());

                String RefNo = jsonObject.getString("RefNo");
                Log.d("RefNo",RefNo);
                tvrefno.setText(RefNo);
                String ApplicantName = jsonObject.getString("ApplicantName");
                if(!ApplicantName.equals("null") || ApplicantName!="null"){
                    tvname.setText(ApplicantName);
                } else tvname.setText("--");

                String ApplicantGender = jsonObject.getString("ApplicantGender");
                if(!ApplicantGender.equals("null") || ApplicantGender!="null"){
                    tvgender.setText(ApplicantGender);
                } else tvgender.setText("--");
                //tvgender.setText(ApplicantGender);
                String ApplicantAge = jsonObject.getString("ApplicantAge");
                if(!ApplicantAge.equals("null") || ApplicantAge!="null"){
                    tvage.setText(ApplicantAge);
                } else tvage.setText("--");
                //tvage.setText(ApplicantAge);
                String ApplicantRelativeName = jsonObject.getString("ApplicantRelativeName");
                if(!ApplicantRelativeName.equals("null") || ApplicantRelativeName!="null"){
                    tvrlnname.setText(ApplicantRelativeName);
                } else tvrlnname.setText("--");
                //tvrlnname.setText(ApplicantRelativeName);
                String ApplicantRelativeType = jsonObject.getString("ApplicantRelativeType");
                if(!ApplicantRelativeType.equals("null") || ApplicantRelativeType!="null"){
                    tvrlntype.setText(ApplicantRelativeType);
                } else tvrlntype.setText("--");
                //tvrlntype.setText(ApplicantRelativeType);
                String ApplicantEPIC  = jsonObject.getString("ApplicantEPIC");
                if(!ApplicantEPIC.equals("null") || ApplicantEPIC!="null"){
                    tvepic.setText(ApplicantEPIC);
                } else tvepic.setText("--");
                //tvepic.setText(ApplicantEPIC);
                String ApplicantPartNo = jsonObject.getString("ApplicantPartNo");
                if(!ApplicantPartNo.equals("null") || ApplicantPartNo!="null"){
                    tvpart.setText(ApplicantPartNo);
                } else tvpart.setText("--");
                //tvpart.setText(ApplicantPartNo);
                String ApplicantSrNoInPart = jsonObject.getString("ApplicantSrNoInPart");
                if(!ApplicantSrNoInPart.equals("null") || ApplicantSrNoInPart!="null"){
                    tvslno.setText(ApplicantSrNoInPart);
                } else tvslno.setText("--");
                //tvslno.setText(ApplicantSrNoInPart);
                String ApplicantEmailID = jsonObject.getString("ApplicantEmailID");
                if(!ApplicantEmailID.equals("null") || ApplicantEmailID!="null"){
                    tvemail.setText(ApplicantEmailID);
                } else tvemail.setText("--");
                //tvemail.setText(ApplicantEmailID);
                String ApplicantDOB = jsonObject.getString("ApplicantDOB");
                if((!ApplicantDOB.equals("null") || ApplicantDOB!="null") && (!ApplicantDOB.equals("--") || ApplicantDOB!="--")){
                    String result1 = ApplicantDOB.split("T")[0];
                    tvdob.setText(result1);
                } else tvdob.setText("--");
                String ApplicantHouseNo = jsonObject.getString("ApplicantHouseNo");
                if(!ApplicantHouseNo.equals("null") || ApplicantHouseNo!="null"){
                    tvhsno.setText(ApplicantHouseNo);
                } else tvhsno.setText("--");
                String ApplicantStreetArea = jsonObject.getString("ApplicantStreetArea");
                if(!ApplicantStreetArea.equals("null") || ApplicantStreetArea!="null"){
                    tvstreet.setText(ApplicantStreetArea);
                } else tvstreet.setText("--");
                //tvstreet.setText(ApplicantStreetArea);
                String ApplicantVillage = jsonObject.getString("ApplicantVillage");
                if(!ApplicantVillage.equals("null") || ApplicantVillage!="null"){
                    tvvillage.setText(ApplicantVillage);
                } else tvvillage.setText("--");
                //tvvillage.setText(ApplicantVillage);
                String ApplicantPost = jsonObject.getString("ApplicantPost");
                if(!ApplicantPost.equals("null") || ApplicantPost!="null"){
                    tvpo.setText(ApplicantPost);
                } else tvpo.setText("--");
                //tvpo.setText(ApplicantPost);
                String ApplicantPinCode = jsonObject.getString("ApplicantPinCode");
                if(!ApplicantPinCode.equals("null") || ApplicantPinCode!="null"){
                    tvpincode.setText(ApplicantPinCode);
                } else tvpincode.setText("--");
                String ApplicantDistrict = jsonObject.getString("ApplicantDistrict");
                if(!ApplicantDistrict.equals("null") || ApplicantDistrict!="null"){
                    tvdistrict.setText(ApplicantDistrict);
                } else tvdistrict.setText("--");
               // tvpincode.setText(ApplicantPinCode);

                                    /*String PartNo = response.getString("PartNo");
                                    String BLOName = response.getString("BLOName");
                                    String BLO_ID = response.getString("BLO_ID");
                                    String Password = response.getString("Password");*/

                //}


            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                hideProgressDialog();
            }

        }
    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Form6.this,CheckListVerificationActivity.class);
        startActivity(intent);
        finish();
    }

}
