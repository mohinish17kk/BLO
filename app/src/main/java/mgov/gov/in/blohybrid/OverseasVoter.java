package mgov.gov.in.blohybrid;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
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
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import mgov.gov.in.blohybrid.Constants;

public class OverseasVoter extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    RadioButton rbyes,rbno,rbeoe,rbuoe,rbsmart,rbfeature,rblandline;
    EditText etpassport,etmob,etserial,etareacode,etdob,etemail;
    Button btnsub,btdobage;
    int year,month,day,yearFinal,monthFinal,dayFinal;
    String[] mob = {"Select Platform", "IOS" , "Android" , "Windows" , "Google" , "Other"};
    String mobiletypespinner,IP_HEADER;
    LinearLayout llpassport,llcontact,llphonetype,llserial,lldob,llemail;
    TextView tvos,tvphonetype,tvhome,tvhint,tvshow;
    String mobnumber,stcode,acno,partno;
    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs" ;
    ProgressDialog pd;
    JSONObject jsonBody = new JSONObject();
    RequestQueue queue;
    String contact;
    DatabaseHelper myDb;
    String sphonetype ="";
    String splatform = "";
    //String url1 = "http://117.239.180.198/BLONet_API_Dev/api/BLO/UnEnrolledOverseasVoter?st_code=S02&mobile_no=8763578927&ac_no=1&part_no=20";
    //String url="http://117.239.180.198/BLONet_API_Dev/api/BLO/EnrolledOverseasVoter?st_code=S02&mobile_no=8763578927&ac_no=1&part_no=20";
    String urlnew;
    Map<String, String> keyVal = new LinkedHashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overseas_voter);

        final Spinner spinmob = (Spinner) findViewById(R.id.spinnerPhonetype);
        llpassport = (LinearLayout) findViewById(R.id.llpassport);
        llcontact = (LinearLayout) findViewById(R.id.llcontact);
        llphonetype = (LinearLayout) findViewById(R.id.llphonetype);
        llserial = (LinearLayout) findViewById(R.id.llserial);
        lldob = (LinearLayout) findViewById(R.id.lldob);
        llemail = (LinearLayout) findViewById(R.id.llemail);
        tvos = (TextView) findViewById(R.id.tvos);
        tvhome = (TextView) findViewById(R.id.tvhome);
        tvphonetype = (TextView) findViewById(R.id.tvphonetype);
        tvhint = (TextView) findViewById(R.id.tvhint);
        tvshow = (TextView) findViewById(R.id.tvshow);
        rbyes= (RadioButton) findViewById(R.id.rbyes);
        rbno = (RadioButton) findViewById(R.id.rbno);
        rbeoe = (RadioButton) findViewById(R.id.rbenrolled);
        rbuoe =(RadioButton) findViewById(R.id.rbunenrolled);
        rbsmart = (RadioButton) findViewById(R.id.rbsmart);
        rbfeature = (RadioButton) findViewById(R.id.rbfeature);
        rblandline = (RadioButton) findViewById(R.id.rblandline);
        etpassport = (EditText) findViewById(R.id.etpassport);
        etserial = (EditText) findViewById(R.id.etserial);
        etmob = (EditText) findViewById(R.id.etmobile);
        etareacode= (EditText) findViewById(R.id.etareacode);
        etemail = (EditText) findViewById(R.id.etemail);
        //etdob = (EditText) findViewById(R.id.etdob);
        btdobage = (Button) findViewById(R.id.btdobage);
        btnsub = (Button) findViewById(R.id.btnsubmit);
        myDb = new DatabaseHelper(this);




        rbeoe.setVisibility(View.GONE);
        rbuoe.setVisibility(View.GONE);
        llpassport.setVisibility(View.GONE);
        llcontact.setVisibility(View.GONE);
        llphonetype.setVisibility(View.GONE);
        llserial.setVisibility(View.GONE);
        lldob.setVisibility(View.GONE);
        llemail.setVisibility(View.GONE);
        tvphonetype.setVisibility(View.GONE);
        spinmob.setVisibility(View.GONE);

        pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getApplicationContext().getResources().getString(R.string.Please_Wait));
        pd.setCancelable(false);
        queue = Volley.newRequestQueue(this);
        prefs = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mobnumber  = prefs.getString("mob","");
        stcode = prefs.getString("st_code","");
        acno = prefs.getString("AcNo","");
        partno = prefs.getString("PartNo","");
        IP_HEADER = Constants.IP_HEADER;

        urlnew = IP_HEADER+"Post_OverseasVoterDetails?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno;

        tvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(OverseasVoter.this,WelcomeBLONew.class);
                startActivity(intent);
                finish();
            }
        });

        btdobage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(OverseasVoter.this,OverseasVoter.this,year,month,day);
                datePickerDialog.show();

            }
        });

        tvshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog();
                Cursor cursor = myDb.getAllDatafromTable4();
                if(cursor.getCount() == 0 ){
                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Nothing_to_show),Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while (cursor.moveToNext()){
                    stringBuffer.append("SLNO_INPART : "+ cursor.getString(0)+"\n");
                    stringBuffer.append("PASSPORT_NO : "+ cursor.getString(1)+"\n");
                    stringBuffer.append("DOB : "+ cursor.getString(2)+"\n");
                    stringBuffer.append("EMAIL_ID : "+ cursor.getString(3)+"\n");
                    stringBuffer.append("MOBILE_NO : "+ cursor.getString(4)+"\n");
                    stringBuffer.append("MOBILE_TYPE : "+ cursor.getString(5)+"\n");
                    stringBuffer.append("PLATFORM_TYPE : "+ cursor.getString(6)+"\n\n");

                }
                hideProgressDialog();
                showmessage("Data",stringBuffer.toString());

            }
        });

        rbyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rbno.setChecked(false);
                rbeoe.setChecked(false);
                rbuoe.setChecked(false);
                rbsmart.setChecked(false);
                rbfeature.setChecked(false);
                rblandline.setChecked(false);
                etpassport.setText("");
                etmob.setText("");
                etserial.setText("");
                tvphonetype.setVisibility(View.VISIBLE);
                tvhint.setVisibility(View.VISIBLE);
                rbeoe.setVisibility(View.VISIBLE);
                rbuoe.setVisibility(View.VISIBLE);
                llpassport.setVisibility(View.VISIBLE);
                llcontact.setVisibility(View.VISIBLE);
                llphonetype.setVisibility(View.VISIBLE);
                llemail.setVisibility(View.VISIBLE);
                lldob.setVisibility(View.VISIBLE);
            }
        });

        rbno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbyes.setChecked(false);
                rbeoe.setChecked(false);
                rbuoe.setChecked(false);
                rbsmart.setChecked(false);
                rbfeature.setChecked(false);
                rblandline.setChecked(false);
                etpassport.setText("");
                etmob.setText("");
                etserial.setText("");
                tvphonetype.setVisibility(View.GONE);
                tvhint.setVisibility(View.GONE);
                rbeoe.setVisibility(View.GONE);
                rbuoe.setVisibility(View.GONE);
                llpassport.setVisibility(View.GONE);
                llcontact.setVisibility(View.GONE);
                llphonetype.setVisibility(View.GONE);
                llemail.setVisibility(View.GONE);
                lldob.setVisibility(View.GONE);
                llserial.setVisibility(View.GONE);
                spinmob.setVisibility(View.GONE);
            }
        });

        rbeoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rbuoe.setChecked(false);
                rbsmart.setChecked(false);
                rbfeature.setChecked(false);
                rblandline.setChecked(false);
                etpassport.setText("");
                etmob.setText("");
                etemail.setText("");
                etareacode.setText("");
                //etdob.setText("");
                etserial.setText("");
                llserial.setVisibility(View.VISIBLE);
            }
        });

        rbuoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbsmart.setChecked(false);
                rbfeature.setChecked(false);
                rblandline.setChecked(false);
                etpassport.setText("");
                etmob.setText("");
                etserial.setText("");
                etemail.setText("");
                etareacode.setText("");
                //etdob.setText("");
                rbeoe.setChecked(false);
                llserial.setVisibility(View.GONE);
            }
        });

        rbsmart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etmob.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Enter_Contact_detail),Toast.LENGTH_SHORT).show();
                    rbsmart.setChecked(false);
                    spinmob.setVisibility(View.GONE);
                } else if(!(etmob.getText().toString().isEmpty())){
                    spinmob.setVisibility(View.VISIBLE);
                    rbfeature.setChecked(false);
                    rblandline.setChecked(false);
                }


            }
        });

        rbfeature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etmob.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.Enter_Contact_detail), Toast.LENGTH_SHORT).show();
                    rbfeature.setChecked(false);
                } else if(!(etmob.getText().toString().isEmpty())){
                    spinmob.setVisibility(View.GONE);
                    rbsmart.setChecked(false);
                    rblandline.setChecked(false);
                }

            }
        });

        rblandline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etmob.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.Enter_Contact_detail), Toast.LENGTH_SHORT).show();
                    rblandline.setChecked(false);
                } else if(!(etmob.getText().toString().isEmpty())){
                    spinmob.setVisibility(View.GONE);
                    rbsmart.setChecked(false);
                    rbfeature.setChecked(false);
                }

            }
        });


        ArrayAdapter<String> sourceofmobAdapter= new ArrayAdapter<>(OverseasVoter.this,android.R.layout.simple_list_item_checked,mob);
        spinmob.setAdapter(sourceofmobAdapter);

        spinmob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mobiletypespinner = (String) spinmob.getItemAtPosition(position);
                //Toast.makeText(getApplicationContext(),mobiletypespinner.toString(),Toast.LENGTH_LONG).show();
                Log.e("date", mobiletypespinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(OverseasVoter.this);
                dialog.setContentView(R.layout.custom_push_warning2);
                TextView tvdialogue_important_message = (TextView) dialog.findViewById(R.id.dialogue_important_message);
                Button bcancel = (Button) dialog.findViewById(R.id.bcancel);
                Button btnok = (Button) dialog.findViewById(R.id.bok);
                btnok.setText("Yes");
                bcancel.setText("No");
                tvdialogue_important_message.setText("Do you want to save data ?");
                dialog.setCanceledOnTouchOutside(false);

                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        submit();
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
        });
    }
    private void submit(){
        if(!rbnoOrrbyes()){
            return;
        }

        if(rbno.isChecked()){
            Intent intent=new Intent(getApplicationContext(),WelcomeBLONew.class);
            startActivity(intent);
            finish();
            return;
        }

        if(!rbenrolledOrUnenrolled()){
            return;
        }
        if(!serial()){
            return;
        }
        if(!passport()){
            return;
        }
        if(!dob()){
            return;
        }
        /*if(!areacode()){
            return;
        }*/
        /*if(!mobile()){
            return;
        }*/
        if((!(etmob.getText().toString().isEmpty())) || !(etareacode.getText().toString().isEmpty())){

            if(!areacode()) {
                return;
            }
            if(!mobile()){
                return;
            }
            if(!rbphonetype()){
                return;
            }
            if(!rbplatform()){
                return;
            }

        }


        contact = etareacode.getText().toString()+""+etmob.getText().toString();
        Log.d("Contact",contact);
        if(rbsmart.isChecked()) {sphonetype = rbsmart.getText().toString(); splatform = mobiletypespinner.toString();}
        else if(rbfeature.isChecked()) {sphonetype = rbfeature.getText().toString(); splatform = "NA";}
        else if(rblandline.isChecked()) {sphonetype = rblandline.getText().toString(); splatform = "NA";}
       // Log.d("sphonetype",sphonetype);
        //Log.d("splatform",splatform);
        //Toast.makeText(getApplicationContext(),"Form Submitted",Toast.LENGTH_LONG).show();
        if(rbeoe.isChecked()){
            keyVal.put("SLNO_INPART",etserial.getText().toString());
            keyVal.put("PASSPORT_NO",etpassport.getText().toString());
            keyVal.put("DOB",btdobage.getText().toString());
            keyVal.put("EMAIL_ID",etemail.getText().toString());
            keyVal.put("MOBILE_NO",contact.toString());
            keyVal.put("MOBILE_TYPE",sphonetype);
            keyVal.put("PLATFORM_TYPE",splatform);

            try {
                jsonBody.put("SLNO_INPART",etserial.getText().toString());
                jsonBody.put("PASSPORT_NO",etpassport.getText().toString());
                jsonBody.put("DOB",btdobage.getText().toString());
                jsonBody.put("EMAIL_ID",etemail.getText().toString());
                jsonBody.put("MOBILE_NO",contact.toString());
                jsonBody.put("MOBILE_TYPE",sphonetype);
                jsonBody.put("PLATFORM_TYPE",splatform);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.e("json formed",jsonBody.toString());
            //keyVal.put("FemaleVoters",etoption3.getText().toString());
            //keyVal.put("ThirdGender",etoption4.getText().toString());

            Log.d("option",etserial.getText().toString()+" "+etpassport.getText().toString()+" "+btdobage.getText().toString()
                    +" "+etemail.getText().toString()+" "+contact.toString()+" "+sphonetype
                    +" "+splatform);
           // vollyRequestForPost(url, keyVal);
            //vollyRequestForPost(urlnew, keyVal);
            boolean isInserted = myDb.insertOVERSEASData(etserial.getText().toString(),etpassport.getText().toString(),btdobage.getText().toString(),
                    etemail.getText().toString(),contact.toString(),sphonetype,splatform,"false");
            if(isInserted = true){
                final Dialog dialog = new Dialog(OverseasVoter.this);
                dialog.setContentView(R.layout.custom_blo_report_done_message);
                Button btnok = (Button) dialog.findViewById(R.id.btnok);
                dialog.setCanceledOnTouchOutside(false);

                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(OverseasVoter.this,WelcomeBLONew.class);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();

                    }
                });
                dialog.show();
                //Toast.makeText(getApplicationContext(),"Data locally saved successfully",Toast.LENGTH_SHORT).show();

            } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Data_not_saved_TRY_AGAIN),Toast.LENGTH_SHORT).show();

        } else  if(rbuoe.isChecked()){
            keyVal.put("PASSPORT_NO",etpassport.getText().toString());
            keyVal.put("DOB",btdobage.getText().toString());
            keyVal.put("EMAIL_ID",etemail.getText().toString());
            keyVal.put("MOBILE_NO",contact.toString());
            keyVal.put("MOBILE_TYPE",sphonetype);
            keyVal.put("PLATFORM_TYPE",splatform);

            try {
                jsonBody.put("PASSPORT_NO",etpassport.getText().toString());
                jsonBody.put("DOB",btdobage.getText().toString());
                jsonBody.put("EMAIL_ID",etemail.getText().toString());
                jsonBody.put("MOBILE_NO",contact.toString());
                jsonBody.put("MOBILE_TYPE",sphonetype);
                jsonBody.put("PLATFORM_TYPE",splatform);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.e("json formed",jsonBody.toString());

            Log.d("option",etpassport.getText().toString()+" "+btdobage.getText().toString()
                    +" "+etemail.getText().toString()+" "+contact.toString()+" "+sphonetype
                    +" "+splatform);
           // vollyRequestForPost1(url1, keyVal);
            //vollyRequestForPost(urlnew, keyVal);
            boolean isInserted = myDb.insertOVERSEASData(" ",etpassport.getText().toString(),btdobage.getText().toString(),
                    etemail.getText().toString(),contact.toString(),sphonetype,splatform,"false");
            if(isInserted = true){
                final Dialog dialog = new Dialog(OverseasVoter.this);
                dialog.setContentView(R.layout.custom_blo_report_done_message);
                Button btnok = (Button) dialog.findViewById(R.id.btnok);
                dialog.setCanceledOnTouchOutside(false);

                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(OverseasVoter.this,WelcomeBLONew.class);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();

                    }
                });
                dialog.show();
                //Toast.makeText(getApplicationContext(),"Data locally saved successfully",Toast.LENGTH_SHORT).show();

            } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Data_not_saved_TRY_AGAIN),Toast.LENGTH_SHORT).show();

        }
    }



    private boolean rbplatform() {
        if(rbsmart.isChecked() && mobiletypespinner.toString().equals("Select Platform")){
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Select_Platform),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean rbphonetype() {
        if(!(rbsmart.isChecked()) && !(rbfeature.isChecked()) && !(rblandline.isChecked()))
        {
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Select_Phone_Type),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean mobile() {
        if(etmob.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Enter_Contact_Number),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean areacode() {
        if(etareacode.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Enter_Country_Code),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean dob() {
        /*if(etdob.getText().toString().isEmpty() || etdob.length()!=10)
        {
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Invalid_Date_of_birth_yyyy_mm_dd),Toast.LENGTH_LONG).show();
            return false;
        }*/
        if(btdobage.getText().toString().equals("Select DOB"))
        {
            Toast.makeText(getApplicationContext(),"Select Date of Birth",Toast.LENGTH_LONG).show();
            Log.d("Select DOB",btdobage.getText().toString());
            return false;
        }
        return true;
    }

    private boolean passport() {
        if(etpassport.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Enter_Passport_number),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean serial() {
        if(rbeoe.isChecked() && etserial.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Enter_Serial_Number),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean rbenrolledOrUnenrolled() {
        if(!(rbeoe.isChecked()) && !(rbuoe.isChecked())){
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Select_Overseas_Elector_type_Enrolled_Unenrolled),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean rbnoOrrbyes() {
        if(!(rbyes.isChecked()) && !(rbno.isChecked())){
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Select_any_option),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void showmessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
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
                                final Dialog dialog = new Dialog(OverseasVoter.this);
                                dialog.setContentView(R.layout.custom_blo_report_done_message);
                                Button btnok = (Button) dialog.findViewById(R.id.btnok);
                                dialog.setCanceledOnTouchOutside(false);




                                btnok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent=new Intent(OverseasVoter.this,WelcomeBLONew.class);
                                        startActivity(intent);
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
            //@Override
            /*protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                //params.put("RoleId", ROLE_ID);
                params.put("SLNO_INPART",etserial.getText().toString());
                params.put("PASSPORT_NO",etpassport.getText().toString());
                params.put("DOB",etdob.getText().toString());
                params.put("EMAIL_ID",etemail.getText().toString());
                params.put("MOBILE_NO",contact.toString());
                params.put("MOBILE_TYPE",sphonetype);
                params.put("PLATFORM_TYPE",splatform);
                //params.put("ThirdGender",etoption4.getText().toString());

                Log.d("option",etserial.getText().toString()+" "+etpassport.getText().toString()+" "+etdob.getText().toString()
                        +" "+etemail.getText().toString()+" "+contact.toString()+" "+sphonetype
                        +" "+splatform);

                return params;
            }*/
        };
        queue.add(jsObjRequest);
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


    /*public void vollyRequestForPost1(String URL, Map<String, String> keyVal) {
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
                                final Dialog dialog = new Dialog(OverseasVoter.this);
                                dialog.setContentView(R.layout.custom_blo_report_done_message);
                                Button btnok = (Button) dialog.findViewById(R.id.btnok);
                                dialog.setCanceledOnTouchOutside(false);




                                btnok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent=new Intent(OverseasVoter.this,WelcomeBLONew.class);
                                        startActivity(intent);
                                        finish();
                                        dialog.dismiss();

                                    }
                                });
                                dialog.show();



                            } else
                                Toast.makeText(getApplicationContext(), "No Response from Server.", Toast.LENGTH_LONG).show();



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
            *//*@Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                //params.put("RoleId", ROLE_ID);
                //params.put("SLNo",etserial.getText().toString());
                params.put("PASSPORT_NO",etpassport.getText().toString());
                params.put("DOB",etdob.getText().toString());
                params.put("EMAIL_ID",etemail.getText().toString());
                params.put("MOBILE_NO",contact.toString());
                params.put("MOBILE_TYPE",sphonetype);
                params.put("PLATFORM_TYPE",splatform);
                //params.put("ThirdGender",etoption4.getText().toString());

                Log.d("option",etpassport.getText().toString()+" "+etdob.getText().toString()
                        +" "+etemail.getText().toString()+" "+contact.toString()+" "+sphonetype
                        +" "+splatform);

                return params;
            }*//*
        };
        queue.add(jsObjRequest);
    }*/

}
