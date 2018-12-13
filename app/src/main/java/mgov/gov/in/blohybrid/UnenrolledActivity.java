package mgov.gov.in.blohybrid;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnenrolledActivity extends AppCompatActivity {

    TextView tvshow,tvhome;
    EditText etname,etrlnname,etrelslno,etreltype,etmobile,etemail,etform6ref,etformref;
    RadioButton rbsmart,rbbasic;
    Spinner spinnerPhonetype;
    CheckBox cb1;
    Button btsubmit;
    String[] mob = {"Select Platform", "IOS" , "Android" , "Windows" , "Google" , "Other"};
    ProgressDialog pd;
    String mobiletypespinner,Semail;
    RequestQueue queue;
    JSONObject jsonBody = new JSONObject();
    DatabaseHelper myDb;
    String url,Srbmobile,FORM6_REF_NO,Scb;
    Map<String, String> keyVal = new LinkedHashMap<String, String>();
    String mobnumber,stcode,acno,partno;
    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unenrolled);

        final Spinner spinmob = (Spinner) findViewById(R.id.spinnerPhonetype);
        etname = (EditText) findViewById(R.id.etname);
        etrlnname = (EditText) findViewById(R.id.etrlnname);
        etrelslno = (EditText) findViewById(R.id.etrelslno);
        etreltype = (EditText) findViewById(R.id.etreltype);
        etmobile = (EditText) findViewById(R.id.etmobile);
        etemail = (EditText) findViewById(R.id.etemail);
        etform6ref = (EditText) findViewById(R.id.etform6ref);
        etformref = (EditText) findViewById(R.id.etformref);
        rbsmart = (RadioButton) findViewById(R.id.rbsmart);
        rbbasic = (RadioButton) findViewById(R.id.rbbasic);
        tvhome = (TextView) findViewById(R.id.tvhome);
        btsubmit = (Button) findViewById(R.id.btsubmit);
        cb1 = (CheckBox) findViewById(R.id.cb1);
        tvshow = (TextView) findViewById(R.id.tvshow);
        myDb = new DatabaseHelper(this);

        prefs = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mobnumber  = prefs.getString("mob","");
        stcode = prefs.getString("st_code","");
        acno = prefs.getString("AcNo","");
        partno = prefs.getString("PartNo","");

        Log.d("mobedf",mobnumber);
        Log.d("st_code",stcode);
        Log.d("AcNo",acno);
        Log.d("PartNo",partno);

        pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getApplicationContext().getResources().getString(R.string.Please_Wait));
        pd.setCancelable(false);
        queue = Volley.newRequestQueue(this);

        tvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UnenrolledActivity.this, WelcomeBLONew.class);
                startActivity(intent);
                finish();
            }
        });

        rbbasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"male cheked",Toast.LENGTH_LONG).show();
                rbsmart.setChecked(false);
                spinmob.setVisibility(View.GONE);
                Srbmobile = "Feature";

            }
        });



        rbsmart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"male cheked",Toast.LENGTH_LONG).show();
                rbbasic.setChecked(false);
                spinmob.setVisibility(View.VISIBLE);
                Srbmobile = "SmartPhone";

            }
        });

    cb1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(cb1.isChecked()){
                etformref.setVisibility(View.VISIBLE);
            } if(!cb1.isChecked()){
                etformref.setVisibility(View.GONE);
            }
        }
    });

        tvshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog();
                Cursor cursor = myDb.getAllDatafromTable15();
                if(cursor.getCount() == 0 ){
                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Nothing_to_show),Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while (cursor.moveToNext()){
                    stringBuffer.append("NAME : "+ cursor.getString(0)+"\n");
                    stringBuffer.append("RLN_NAME : "+ cursor.getString(1)+"\n");
                    stringBuffer.append("RLN_TYPE : "+ cursor.getString(3)+"\n");
                    stringBuffer.append("MOBILE_NO : "+ cursor.getString(4)+"\n\n");

                }
                hideProgressDialog();
                showmessage(getApplicationContext().getResources().getString(R.string.Data),stringBuffer.toString());

            }
        });

        btsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(UnenrolledActivity.this);
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

        ArrayAdapter<String> sourceofmobAdapter= new ArrayAdapter<>(UnenrolledActivity.this,android.R.layout.simple_list_item_checked,mob);
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

    }
    private void submit() {
        if (!validateTP()) {
            return;
        }

        Pattern pattern = Pattern.compile("[a-zA-Z0-9\\s+]*");
        //Pattern pattern= Pattern.compile("[^a-z A-Z0-9 \\s+]*");

        String str = etname.getText().toString();
        String strLastname  =etrlnname.getText().toString();
        Matcher matcher = pattern.matcher(str);

        if (!matcher.matches()) {
            Toast.makeText(getApplicationContext(),"Name contains special character",Toast.LENGTH_LONG).show();
            Log.d("string "+str , " Name contains special character");
            return;
        }
        Matcher matcher1 = pattern.matcher(strLastname);
        if (!matcher1.matches()) {
            Toast.makeText(getApplicationContext(),"REL Name contains special character",Toast.LENGTH_LONG).show();
            Log.d("string "+str , " Last name contains special character");
            return;
        }

        if(!etreltype.getText().toString().trim().equals("M")){
            if(!etreltype.getText().toString().trim().equals("F")){
                if(!etreltype.getText().toString().trim().equals("H")){
                    if(!etreltype.getText().toString().trim().equals("O")){
                        Toast.makeText(getApplicationContext(),"REL TYPE must be either of M/F/H/O",Toast.LENGTH_LONG).show();
                        Log.d("rlntype",etreltype.getText().toString().trim());
                        return;

                    }
                }
            }

        }
        if(!validmobiletype())
        {
            return;
        }
        if(!validplatform()){
            return;
        }
        if(etemail.getText().toString().isEmpty()){
            Semail = "";
        } else  Semail = etemail.getText().toString();

        if(etform6ref.getText().toString().isEmpty()){
            FORM6_REF_NO = "";
        } else FORM6_REF_NO = etform6ref.getText().toString();
        if(cb1.isChecked()){
            Scb = "true";
        } else Scb = "false";

        boolean isInserted = myDb.insertUNENROLLEDData(etname.getText().toString(),etrlnname.getText().toString(),etrelslno.getText().toString(),
                etreltype.getText().toString(),etmobile.getText().toString(),Srbmobile,Semail,FORM6_REF_NO,Scb,"false");
        if(isInserted = true){
            final Dialog dialog = new Dialog(UnenrolledActivity.this);
            dialog.setContentView(R.layout.custom_blo_report_done_message);
            Button btnok = (Button) dialog.findViewById(R.id.btnok);
            dialog.setCanceledOnTouchOutside(false);

            btnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(UnenrolledActivity.this,WelcomeBLONew.class);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();

                }
            });
            dialog.show();
            //Toast.makeText(getApplicationContext(),"Data locally saved successfully",Toast.LENGTH_SHORT).show();

        } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Data_not_saved_TRY_AGAIN),Toast.LENGTH_SHORT).show();

    }
    private boolean validateTP() {
        if((etname.getText().toString().isEmpty() || etrlnname.getText().toString().isEmpty() || etrelslno.getText().toString().isEmpty()
                || etreltype.getText().toString().isEmpty() || etmobile.getText().toString().isEmpty() || etform6ref.getText().toString().isEmpty()))
        {
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Fill_all_detail_correctly),Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean validmobiletype() {
        if(!(rbsmart.isChecked()) && !(rbbasic.isChecked())){
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Select_Phone_Type),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean validplatform() {
        if(rbsmart.isChecked()){
            if(mobiletypespinner.toString().equals("Select Platform")){
                Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Select_Platform),Toast.LENGTH_LONG).show();
                return false;
            }
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


}
