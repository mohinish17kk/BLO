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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mgov.gov.in.blohybrid.Constants;

public class ToBeQualified extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    EditText etname,etfathername,etdob,etforref,etstudentaddress;
    RadioButton rbmale,rbfemale,rbso,rbdo;
    TextView tvhome,tverror,tvshow;
    CheckBox cbssrform6,cbstudent;
    Button btnsubmit,btdobage;
    int year,month,day,yearFinal,monthFinal,dayFinal;
    ProgressDialog pd;
    JSONObject jsonBody = new JSONObject();
    RequestQueue queue;
    String url,fatername,relation,gender,IP_HEADER;
    String mob,stcode,acno,partno,mobiletype;
    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";
    Map<String, String> keyVal = new LinkedHashMap<String, String>();
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_be_qualified);

        etname =  (EditText) findViewById(R.id.etname);
        etstudentaddress = (EditText) findViewById(R.id.etstudentaddress);
        etforref = (EditText) findViewById(R.id.etforref);
        etfathername =  (EditText) findViewById(R.id.etfathername);
        //etdob =  (EditText) findViewById(R.id.etdob);
        btdobage = (Button) findViewById(R.id.btdobage);
        rbmale = (RadioButton) findViewById(R.id.rbmale);
        rbfemale = (RadioButton) findViewById(R.id.rbfemale);
        rbso = (RadioButton) findViewById(R.id.rbso);
        rbdo = (RadioButton) findViewById(R.id.rbdo);
        tvhome = (TextView) findViewById(R.id.tvhome);
        tverror = (TextView) findViewById(R.id.tverror);
        tvshow = (TextView) findViewById(R.id.tvshow);
        cbssrform6 = (CheckBox) findViewById(R.id.cbssrform6);
        cbstudent = (CheckBox) findViewById(R.id.cbstudent);
        btnsubmit = (Button) findViewById(R.id.btsearch);
        myDb = new DatabaseHelper(this);

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

        url = IP_HEADER+"Post_FutureVotersDetails?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno;
            // http://117.239.180.198/BLONet_API_Dev/api/blo/Post_FutureVotersDetails?st_code=S13&ac_no=1&part_no=1


        tvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToBeQualified.this, WelcomeBLONew.class);
                startActivity(intent);
                finish();
                   }
                });

                rbso.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rbdo.setChecked(false);
                    }
                });

                rbdo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rbso.setChecked(false);
                    }
                });

                rbmale.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rbfemale.setChecked(false);
                        gender ="M";
                    }
                });

                rbfemale.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rbmale.setChecked(false);
                        gender = "F";
                    }
                });

        tvshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog();
                Cursor cursor = myDb.getAllDatafromTable9();
                if(cursor.getCount() == 0 ){
                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Nothing_to_show),Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while (cursor.moveToNext()){
                    stringBuffer.append("NAME : "+ cursor.getString(0)+"\n");
                    stringBuffer.append("RLN_TYPE : "+ cursor.getString(1)+"\n");
                    stringBuffer.append("DOB : "+ cursor.getString(2)+"\n");
                    stringBuffer.append("GENDER : "+ cursor.getString(3)+"\n\n");

                }
                hideProgressDialog();
                showmessage(getApplicationContext().getResources().getString(R.string.Data),stringBuffer.toString());

            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(ToBeQualified.this);
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

        btdobage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ToBeQualified.this,ToBeQualified.this,year,month,day);
                datePickerDialog.show();

            }
        });

    }
    private void submit() {

        if (!validateTP()) {
            return;
        }

        Pattern pattern = Pattern.compile("[a-zA-Z0-9\\s+]*");
        //Pattern pattern= Pattern.compile("[^a-z A-Z0-9 \\s+]*");

        String str = etname.getText().toString();
        String strLastname  =etfathername.getText().toString();
        Matcher matcher = pattern.matcher(str);

        if (!matcher.matches()) {
            Toast.makeText(getApplicationContext(),"Name contains special character",Toast.LENGTH_LONG).show();
            Log.d("string "+str , " Name contains special character");
            return;
        }
        Matcher matcher1 = pattern.matcher(strLastname);
        if (!matcher1.matches()) {
            Toast.makeText(getApplicationContext(),"Father's Name contains special character",Toast.LENGTH_LONG).show();
            Log.d("string "+str , " Last name contains special character");
            return;
        }
        if(btdobage.getText().toString().equals("Select DOB"))
        {
            Toast.makeText(getApplicationContext(),"Select Date of Birth",Toast.LENGTH_LONG).show();
            Log.d("Select DOB",btdobage.getText().toString());
            return;
        }

        if(rbdo.isChecked()){
            relation = rbdo.getText().toString();
        } else  if(rbso.isChecked()){
            relation = rbso.getText().toString();
        }
        mobiletype = "SmartPhone";
        fatername = relation+etfathername.getText().toString();
        keyVal.put("NAME",etname.getText().toString());
        keyVal.put("RLN_NAME",fatername.toString());
        keyVal.put("DOB",btdobage.getText().toString());
        keyVal.put("MOBILE_TYPE",mobiletype);

        try {
            jsonBody.put("NAME",etname.getText().toString());
            jsonBody.put("RLN_NAME",fatername.toString());
            jsonBody.put("DOB",btdobage.getText().toString());
            jsonBody.put("MOBILE_TYPE",mobiletype);
            jsonBody.put("GENDER",gender);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("json formed",jsonBody.toString());



        Log.d("option",etname.getText().toString()+" "+fatername.toString()+" "+btdobage.getText().toString()+" "+gender.toString());
        Log.d("url",url);

        boolean isInserted = myDb.insertFutureVotersData(etname.getText().toString(),etfathername.getText().toString(),btdobage.getText().toString(),
                gender,"false");
        if(isInserted = true){
            final Dialog dialog = new Dialog(ToBeQualified.this);
            dialog.setContentView(R.layout.custom_blo_report_done_message);
            Button btnok = (Button) dialog.findViewById(R.id.btnok);
            dialog.setCanceledOnTouchOutside(false);

            btnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(ToBeQualified.this,WelcomeBLONew.class);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();

                }
            });
            dialog.show();
            //Toast.makeText(getApplicationContext(),"Data locally saved successfully",Toast.LENGTH_SHORT).show();

        } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Data_not_saved_TRY_AGAIN),Toast.LENGTH_SHORT).show();

        //vollyRequestForPost(url, keyVal);
    }

    private boolean validateTP() {
        if((etname.getText().toString().isEmpty() || etfathername.getText().toString().isEmpty() || (!(rbdo.isChecked()) && (!(rbso.isChecked()))) || (!(rbmale.isChecked())) && (!(rbfemale.isChecked()))))
        {
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Fill_all_detail_correctly),Toast.LENGTH_SHORT).show();
            tverror.setVisibility(View.VISIBLE);
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

    private void showmessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void vollyRequestForPost(String URL, Map<String, String> keyVal) {
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
                                final Dialog dialog = new Dialog(ToBeQualified.this);
                                dialog.setContentView(R.layout.custom_blo_report_done_message);
                                Button btnok = (Button) dialog.findViewById(R.id.btnok);
                                dialog.setCanceledOnTouchOutside(false);




                                btnok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent=new Intent(ToBeQualified.this,WelcomeBLONew.class);
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
        if(y-yearFinal >=17)
        btdobage.setText(day3+"/"+month3+"/"+yearFinal);
        else Toast.makeText(getApplicationContext(),"Applicant must be 17+ year",Toast.LENGTH_LONG).show();


    }
}

