package mgov.gov.in.blohybrid;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import mgov.gov.in.blohybrid.Constants;

public class ExistingPollingStation extends AppCompatActivity {

    EditText etpollingaddress,etpollingaddress2,etpollingaddres3,etpollingaddress4;
    String[] buildingtype = {"Select Building Type", "Government Building", "Public Sector Building", "Non Government Building", "Building Without Religious Places Nearby", "Other"};
    String[] buildingpriority = {"Priority Choice", "1", "2", "3", "4"};
    String buildingtypespinner,buildingpriorityspinner,slatlong,IP_HEADER;
    Button btn_submit;
    CheckBox cbamf;
    DatabaseHelper myDb;
    ProgressDialog pd;
    RequestQueue queue;
    JSONObject jsonBody = new JSONObject();
    //String url="http://117.239.180.198/BLONet_API_Test/api/blo/Post_PollingStationLocation?st_code=S24&ac_no=1&part_no=1";
    String url;//="http://117.239.180.198/BLONet_API_Test/api/blo/Post_PollingStationLocation?st_code=S24&ac_no=1&part_no=1";
    Map<String, String> keyVal = new LinkedHashMap<String, String>();
    String mob,stcode,acno,partno,AddressPin;
    RadioButton rb1y,rb1n,rb2y,rb2n,rb3y,rb3n,rb4y,rb4n,rb5y,rb5n,rb6y,rb6n,rb7y,rb7n,rb8y,rb8n,rb9y,rb9n,rb10y,rb10n,rb11y,rb11n,
            rb12y,rb12n,rb13y,rb13n,rb14y,rb14n,rb15y,rb15n,rb16y,rb16n,rb17y,rb17n,rb18y,rb18n,rb19y,rb19n,rb20y,rb20n,rb21y,
            rb21n,rb22y,rb22n,rb23y,rb23n,rb24y,rb24n,rb25y,rb25n;
    LinearLayout llamfsurvey;
    String q1,q2,q3,q4,q5,q6,q7,q8,q9,q10,q11,q12,q13,q14,q15,q16,q17,q18,q19,q20,q21,q22,q23,q24,q25,encImage;
    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";
    TextView tvpollinggps, tvfetched,tvfetched1,tvuploadphoto,tvhome,tvshow;
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath="";
    ImageView ivimage;
    public static final int MY_PERMISSIONS = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_polling_station);

        etpollingaddress4 = (EditText) findViewById(R.id.etpollingaddress4);
        etpollingaddres3 = (EditText) findViewById(R.id.etpollingaddres3);
        etpollingaddress2 = (EditText) findViewById(R.id.etpollingaddress2);
        tvpollinggps = (TextView) findViewById(R.id.pollinggps);
        btn_submit = (Button) findViewById(R.id.btn_submitform6);
        etpollingaddress = (EditText) findViewById(R.id.etpollingaddress);
        tvfetched = (TextView) findViewById(R.id.tvfetchedgpslocation);
        tvfetched1 = (TextView) findViewById(R.id.tvfetchedgpslocation1);
        tvshow = (TextView) findViewById(R.id.tvshow);
        tvuploadphoto = (TextView) findViewById(R.id.tvuploadphoto);
        ivimage = (ImageView) findViewById(R.id.ivimage);
        cbamf = (CheckBox) findViewById(R.id.cbamf);
        rb1y= (RadioButton) findViewById(R.id.rb1y);
        rb1n = (RadioButton) findViewById(R.id.rb1n);
        rb2y= (RadioButton) findViewById(R.id.rb2y);
        rb2n = (RadioButton) findViewById(R.id.rb2n);
        rb3y= (RadioButton) findViewById(R.id.rb3y);
        rb3n = (RadioButton) findViewById(R.id.rb3n);
        rb4y= (RadioButton) findViewById(R.id.rb4y);
        rb4n = (RadioButton) findViewById(R.id.rb4n);
        rb5y= (RadioButton) findViewById(R.id.rb5y);
        rb5n = (RadioButton) findViewById(R.id.rb5n);
        rb6y= (RadioButton) findViewById(R.id.rb6y);
        rb6n = (RadioButton) findViewById(R.id.rb6n);
        rb7y= (RadioButton) findViewById(R.id.rb7y);
        rb7n = (RadioButton) findViewById(R.id.rb7n);
        rb8y= (RadioButton) findViewById(R.id.rb8y);
        rb8n = (RadioButton) findViewById(R.id.rb8n);
        rb9y= (RadioButton) findViewById(R.id.rb9y);
        rb9n = (RadioButton) findViewById(R.id.rb9n);
        rb10y= (RadioButton) findViewById(R.id.rb10y);
        rb10n = (RadioButton) findViewById(R.id.rb10n);
        rb11y= (RadioButton) findViewById(R.id.rb11y);
        rb11n = (RadioButton) findViewById(R.id.rb11n);
        rb12y= (RadioButton) findViewById(R.id.rb12y);
        rb12n = (RadioButton) findViewById(R.id.rb12n);
        rb13y= (RadioButton) findViewById(R.id.rb13y);
        rb13n = (RadioButton) findViewById(R.id.rb13n);
        rb14y= (RadioButton) findViewById(R.id.rb14y);
        rb14n = (RadioButton) findViewById(R.id.rb14n);
        rb15y= (RadioButton) findViewById(R.id.rb15y);
        rb15n = (RadioButton) findViewById(R.id.rb15n);
        rb16y= (RadioButton) findViewById(R.id.rb16y);
        rb16n = (RadioButton) findViewById(R.id.rb16n);
        rb17y= (RadioButton) findViewById(R.id.rb17y);
        rb17n = (RadioButton) findViewById(R.id.rb17n);
        rb18y= (RadioButton) findViewById(R.id.rb18y);
        rb18n = (RadioButton) findViewById(R.id.rb18n);
        rb19y= (RadioButton) findViewById(R.id.rb19y);
        rb19n = (RadioButton) findViewById(R.id.rb19n);
        rb20y= (RadioButton) findViewById(R.id.rb20y);
        rb20n = (RadioButton) findViewById(R.id.rb20n);
        rb21y= (RadioButton) findViewById(R.id.rb21y);
        rb21n = (RadioButton) findViewById(R.id.rb21n);
        rb22y= (RadioButton) findViewById(R.id.rb22y);
        rb22n = (RadioButton) findViewById(R.id.rb22n);
        rb23y= (RadioButton) findViewById(R.id.rb23y);
        rb23n = (RadioButton) findViewById(R.id.rb23n);
        rb24y= (RadioButton) findViewById(R.id.rb24y);
        rb24n = (RadioButton) findViewById(R.id.rb24n);
        rb25y= (RadioButton) findViewById(R.id.rb25y);
        rb25n = (RadioButton) findViewById(R.id.rb25n);
        myDb = new DatabaseHelper(this);
        llamfsurvey = (LinearLayout) findViewById(R.id.llamfsurvey);
        final Spinner spinbuilding = (Spinner) findViewById(R.id.spinnerBuildingtype);
        final Spinner spinbuildingpriority = (Spinner) findViewById(R.id.spinnerBuildingPriority);
        cbamf.setChecked(false);

        llamfsurvey.setVisibility(View.GONE);
        tvhome = (TextView) findViewById(R.id.tvhome);
        prefs = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mob  = prefs.getString("mob","");
        stcode = prefs.getString("st_code","");
        acno = prefs.getString("AcNo","");
        partno = prefs.getString("PartNo","");
        IP_HEADER = Constants.IP_HEADER;
        url = IP_HEADER+"Post_Existing_PollingStationDetails?st_code="+stcode+"&mobile_no="+mob+"&ac_no="+acno+"&part_no="+partno;

        Log.d("mobedf",mob);
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
                Intent intent=new Intent(ExistingPollingStation.this,WelcomeBLONew.class);
                startActivity(intent);
                finish();
            }
        });

        ArrayAdapter<String> sourceofmobAdapter = new ArrayAdapter<>(ExistingPollingStation.this, android.R.layout.simple_list_item_checked, buildingtype);
        spinbuilding.setAdapter(sourceofmobAdapter);

        ArrayAdapter<String> sourceofpriorityAdapter = new ArrayAdapter<>(ExistingPollingStation.this, android.R.layout.simple_list_item_checked, buildingpriority);
        spinbuildingpriority.setAdapter(sourceofpriorityAdapter);


        rb1y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb1n.setChecked(false);
            }
        });
        rb1n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb1y.setChecked(false);
            }
        });

        rb2y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb2n.setChecked(false);
            }
        });
        rb2n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb2y.setChecked(false);
            }
        });

        rb3y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb3n.setChecked(false);
            }
        });
        rb3n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb3y.setChecked(false);
            }
        });

        rb4y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb4n.setChecked(false);
            }
        });
        rb4n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb4y.setChecked(false);
            }
        });

        rb5y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb5n.setChecked(false);
            }
        });
        rb5n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb5y.setChecked(false);
            }
        });

        rb6y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb6n.setChecked(false);
            }
        });
        rb6n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb6y.setChecked(false);
            }
        });

        rb7y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb7n.setChecked(false);
            }
        });
        rb7n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb7y.setChecked(false);
            }
        });

        rb8y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb8n.setChecked(false);
            }
        });
        rb8n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb8y.setChecked(false);
            }
        });

        rb9y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb9n.setChecked(false);
            }
        });
        rb9n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb9y.setChecked(false);
            }
        });

        rb10y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb10n.setChecked(false);
            }
        });
        rb10n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb10y.setChecked(false);
            }
        });

        rb11y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb11n.setChecked(false);
            }
        });
        rb11n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb11y.setChecked(false);
            }
        });

        rb12y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb12n.setChecked(false);
            }
        });
        rb12n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb12y.setChecked(false);
            }
        });

        rb13y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb13n.setChecked(false);
            }
        });
        rb13n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb13y.setChecked(false);
            }
        });

        rb14y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb14n.setChecked(false);
            }
        });
        rb14n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb14y.setChecked(false);
            }
        });

        rb15y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb15n.setChecked(false);
            }
        });
        rb15n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb15y.setChecked(false);
            }
        });

        rb16y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb16n.setChecked(false);
            }
        });
        rb16n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb16y.setChecked(false);
            }
        });

        rb17y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb17n.setChecked(false);
            }
        });
        rb17n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb17y.setChecked(false);
            }
        });

        rb18y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb18n.setChecked(false);
            }
        });
        rb18n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb18y.setChecked(false);
            }
        });

        rb19y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb19n.setChecked(false);
            }
        });
        rb19n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb19y.setChecked(false);
            }
        });

        rb20y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb20n.setChecked(false);
            }
        });
        rb20n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb20y.setChecked(false);
            }
        });

        rb21y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb21n.setChecked(false);
            }
        });
        rb21n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb21y.setChecked(false);
            }
        });

        rb22y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb22n.setChecked(false);
            }
        });
        rb22n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb22y.setChecked(false);
            }
        });

        rb23y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb23n.setChecked(false);
            }
        });
        rb23n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb23y.setChecked(false);
            }
        });

        rb24y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb24n.setChecked(false);
            }
        });
        rb24n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb24y.setChecked(false);
            }
        });

        rb25y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb25n.setChecked(false);
            }
        });
        rb25n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb25y.setChecked(false);
            }
        });



        cbamf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbamf.isChecked())
                    llamfsurvey.setVisibility(View.VISIBLE);
                if(!(cbamf.isChecked()))
                    llamfsurvey.setVisibility(View.GONE);
            }
        });

        tvshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog();
                Cursor cursor = myDb.getAllDatafromTable7();
                if(cursor.getCount() == 0 ){
                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Nothing_to_show),Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while (cursor.moveToNext()){
                    stringBuffer.append("AddressLine1 : "+ cursor.getString(0)+"\n");
                    stringBuffer.append("AddressLine2 : "+ cursor.getString(1)+"\n");
                    stringBuffer.append("AddressLine3 : "+ cursor.getString(2)+"\n");
                    stringBuffer.append("PS_BUILDING_TYPE_CODE : "+ cursor.getString(3)+"\n");
                    stringBuffer.append("PRIORITY_CHOICE : "+ cursor.getString(4)+"\n");
                    stringBuffer.append("PS_BULDING_LAT_LONG : "+ cursor.getString(5)+"\n\n");

                }
                hideProgressDialog();
                showmessage(getApplicationContext().getResources().getString(R.string.Data),stringBuffer.toString());

            }
        });


        spinbuilding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String  input = (String) spinbuilding.getItemAtPosition(position);
                Log.d("input", input);
                String  abc = input.toString();
                buildingtypespinner = abc.replace(" ", "");
                Log.d("buildingtypespinner",buildingtypespinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinbuildingpriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                buildingpriorityspinner = (String) spinbuildingpriority.getItemAtPosition(i);
                Log.d("buildingpriorityspinner", buildingpriorityspinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tvuploadphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentapiVersion = Build.VERSION.SDK_INT;

                String sc=Integer.toString(currentapiVersion);
                Log.e("API version",sc);
                int Version= Build.VERSION_CODES.LOLLIPOP_MR1;
                String sv=Integer.toString(Version);
                Log.e("API v2",sv);

                Intent i = new Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, SELECT_PICTURE);

            }
        });


        ActivityCompat.requestPermissions(ExistingPollingStation.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);
        tvpollinggps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("1","1");
                GPSTracker g = new GPSTracker(getApplicationContext());
                if (g.isGPSEnabled){
                    Log.d("1","2");
                    Location l = g.getLocation();
                    Log.d("1","3");
                    if(l!=null)
                    {
                        Log.d("1","4");
                        double lat = l.getLatitude();
                        double lon = l.getLongitude();
                        tvfetched.setText("Lat  "+lat+"  Lon  "+lon);
                        Log.d("latlong","Lat  "+lat+"  Lon  "+lon);
                        slatlong = String.valueOf(lat)+","+String.valueOf(lon);
                        Log.d("slatlong",slatlong);
                        Log.d("lat long","Lat  "+lat+"  Lon  "+lon);
                        Log.d("1","5");
                    }
                } else {
                    Toast.makeText(ExistingPollingStation.this,getApplicationContext().getResources().getString(R.string.Please_Enable_GPS), Toast.LENGTH_SHORT).show();
                }
                 /*if(l.equals(null)){
                Toast.makeText(getApplicationContext(),"null",Toast.LENGTH_SHORT).show();
            } Log.d("l", l.toString());*/
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    submit();

            }
        });


    }

    private void submit() {
        /*if (!isphotouploaded()) {
            return;
        }*/
        if (!address1()) {
            return;
        }
        if (!address2()) {
            return;
        }
        if (!address3()) {
            return;
        }
        if (!pincode()) {
            return;
        }
        if (!Buildingtypespinner()) {
            return;
        }
        if (!priorityChoicespinner()) {
            return;
        }
        if (!GPS()) {
            return;
        }

        if(rb1y.isChecked()){
            q1="true";
        }else if(rb1n.isChecked()){
            q1="false";
        } else q1="";
        if(rb2y.isChecked()){
            q2="true";
        }else if(rb2n.isChecked()){
            q2="false";
        }else q2="";
        if(rb3y.isChecked()){
            q3="true";
        }else if(rb3n.isChecked()){
            q3="false";
        }else q3="";
        if(rb4y.isChecked()){
            q4="true";
        }else if(rb4n.isChecked()){
            q4="false";
        }else q4="";
        if(rb5y.isChecked()){
            q5="true";
        }else if(rb5n.isChecked()){
            q5="false";
        }else q5="";
        if(rb6y.isChecked()){
            q6="true";
        }else if(rb6n.isChecked()){
            q6="false";
        }else q6="";
        if(rb7y.isChecked()){
            q7="true";
        }else if(rb7n.isChecked()){
            q7="false";
        }else q7="";
        if(rb8y.isChecked()){
            q8="true";
        }else if(rb8n.isChecked()){
            q8="false";
        }else q8="";
        if(rb9y.isChecked()){
            q9="true";
        }else if(rb9n.isChecked()){
            q9="false";
        }else q9="";
        if(rb10y.isChecked()){
            q10="true";
        }else if(rb10n.isChecked()){
            q10="false";
        }else q10="";
        if(rb11y.isChecked()){
            q11="true";
        }else if(rb11n.isChecked()){
            q11="false";
        }else q11="";
        if(rb12y.isChecked()){
            q12="true";
        }else if(rb12n.isChecked()){
            q12="false";
        }else q12="";
        if(rb13y.isChecked()){
            q13="true";
        }else if(rb13n.isChecked()){
            q13="false";
        }else q13="";
        if(rb14y.isChecked()){
            q14="true";
        }else if(rb14n.isChecked()){
            q14="false";
        }else q14="";
        if(rb15y.isChecked()){
            q15="true";
        }else if(rb15n.isChecked()){
            q15="false";
        }else q15="";
        if(rb16y.isChecked()){
            q16="true";
        }else if(rb16n.isChecked()){
            q16="false";
        }else q16="";
        if(rb17y.isChecked()){
            q17="true";
        }else if(rb17n.isChecked()){
            q17="false";
        }else q17="";
        if(rb18y.isChecked()){
            q18="true";
        }else if(rb18n.isChecked()){
            q18="false";
        }else q18="";
        if(rb19y.isChecked()){
            q19="true";
        }else if(rb19n.isChecked()){
            q19="false";
        }else q19="";
        if(rb20y.isChecked()){
            q20="true";
        }else if(rb20n.isChecked()){
            q20="false";
        }else q20="";
        if(rb21y.isChecked()){
            q21="true";
        }else if(rb21n.isChecked()){
            q21="false";
        }else q21="";
        if(rb22y.isChecked()){
            q22="true";
        }else if(rb22n.isChecked()){
            q22="false";
        }else q22="";
        if(rb23y.isChecked()){
            q23="true";
        }else if(rb23n.isChecked()){
            q23="false";
        }else q23="";
        if(rb24y.isChecked()){
            q24="true";
        }else if(rb24n.isChecked()){
            q24="false";
        }else q24="";
        if(rb25y.isChecked()){
            q25="true";
        }else if(rb25n.isChecked()){
            q25="false";
        }else q25="";

        AddressPin = etpollingaddres3.getText().toString()+","+etpollingaddress4.getText().toString();
        Log.d("AddressPin",AddressPin);

        Log.d("selectedImagePath123",selectedImagePath);
        if(!selectedImagePath.isEmpty())
        {
            File imagefile = new File(selectedImagePath);
            FileInputStream fis = null;
            try{
                fis = new FileInputStream(imagefile);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }
            Bitmap bm = BitmapFactory.decodeStream(fis);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG,30,baos);
            byte[] b = baos.toByteArray();
            encImage = Base64.encodeToString(b, Base64.DEFAULT);
            Log.d("encImage",encImage);

        } else if(selectedImagePath.isEmpty()){ encImage = "";}




        /*keyVal.put("AddressLine1",etpollingaddress.getText().toString());
        keyVal.put("AddressLine2",etpollingaddress2.getText().toString());
        keyVal.put("AddressLine3",AddressPin.toString());
        keyVal.put("BuildingType",buildingtypespinner.toString());
        keyVal.put("PS_BUILDING_TYPE_CODE",buildingtypespinner.toString());
        keyVal.put("PRIORITY_CHOICE",buildingtypespinner.toString());
        keyVal.put("PS_BULDING_LAT_LONG",slatlong.toString());
        keyVal.put("BUILDING_PHOTOGRAPH",encImage);
        keyVal.put("IS_POLLINGBOOTH_AREA_IS_GT_20SQM",q1);
        keyVal.put("IS_BUILDING_DILAPIDATED_OR_DANGEROUS",q2);
        keyVal.put("IS_PS_IN_GOV_BUILDING_OR_PREMISIS",q3);
        keyVal.put("IS_PS_LOCATERD_IN_INSTITUTION_OR_RELIGIOUS_PLACE",q4);
        keyVal.put("IS_PS_IN_SCHOOL_OR_COLLEGE_BUILDING",q5);
        keyVal.put("IS_PS_ON_GROUND_FLOOR",q6);
        keyVal.put("IS_SEPARATE_DOOR_FOR_ENTRY_N_EXIT",q7);
        keyVal.put("IS_ANY_POLITICAL_PARTY_WITHIN_200MTR_PS_PREMISES",q8);
        keyVal.put("IS_DRINKING_WATER_FACILITY_AVAILABLE",q9);
        keyVal.put("IS_ELECTRICITY_SUPPLY_AVAILABLE_IN_BUILDING",q10);
        keyVal.put("IS_PROPER_LIGHTING_AVAILABLE_IN_BUILDING",q11);
        keyVal.put("IS_TOILET_FACILITY_AVAILABLE_IN_BUILDING",q12);
        keyVal.put("IS_RAMP_PROVIDED",q13);
        keyVal.put("IS_ADEQUATE_FURNITURE_AVAILABLE_IN_BUILDING",q14);
        keyVal.put("IS_SHADE_OR_SHELTER_AVAILABLE",q15);
        keyVal.put("IS_PROPER_ROAD_CONNECTIVITY_AVAILABLE",q16);
        keyVal.put("IS_ANY_RIVER_OR_VALLY_TO_CROSS_TO_REACH_PS",q17);
        keyVal.put("IS_PS_HAS_LANDLINE_TELEPHONE_OR_FAX",q18);
        keyVal.put("IS_PS_HAS_MOBILE_CONNECTIVITY",q19);
        keyVal.put("IS_PS_HAS_INTERNET_FACILITY",q20);
        keyVal.put("IS_PS_HAS_PROPER_SIGNAGE_OF_BUILDING_NAME_N_ADDRESS",q21);
        keyVal.put("IS_PS_WITHIN_INSURGENCY_AFFECTED_AREA",q22);
        keyVal.put("IS_PS_WITHIN_FOREST_OR_SEMIFOREST_AREA",q23);
        keyVal.put("IS_PS_IN_VULNERABLE_CRITICAL_LOCATION",q24);
        keyVal.put("IS_PS_SENSITIVE_OR_HYPER_SENSITIVE",q25);*/

        try {
            jsonBody.put("AddressLine1",etpollingaddress.getText().toString());
            jsonBody.put("AddressLine2",etpollingaddress2.getText().toString());
            jsonBody.put("AddressLine3",AddressPin.toString());
            //jsonBody.put("BuildingType",buildingtypespinner.toString());
            jsonBody.put("PS_BUILDING_TYPE_CODE",buildingtypespinner.toString());
            jsonBody.put("PRIORITY_CHOICE",buildingpriorityspinner.toString());
            jsonBody.put("PS_BULDING_LAT_LONG",slatlong.toString());
            jsonBody.put("BUILDING_PHOTOGRAPH",encImage);
            jsonBody.put("IS_POLLINGBOOTH_AREA_IS_GT_20SQM",q1);
            jsonBody.put("IS_BUILDING_DILAPIDATED_OR_DANGEROUS",q2);
            jsonBody.put("IS_PS_IN_GOV_BUILDING_OR_PREMISIS",q3);
            jsonBody.put("IS_PS_LOCATERD_IN_INSTITUTION_OR_RELIGIOUS_PLACE",q4);
            jsonBody.put("IS_PS_IN_SCHOOL_OR_COLLEGE_BUILDING",q5);
            jsonBody.put("IS_PS_ON_GROUND_FLOOR",q6);
            jsonBody.put("IS_SEPARATE_DOOR_FOR_ENTRY_N_EXIT",q7);
            jsonBody.put("IS_ANY_POLITICAL_PARTY_WITHIN_200MTR_PS_PREMISES",q8);
            jsonBody.put("IS_DRINKING_WATER_FACILITY_AVAILABLE",q9);
            jsonBody.put("IS_ELECTRICITY_SUPPLY_AVAILABLE_IN_BUILDING",q10);
            jsonBody.put("IS_PROPER_LIGHTING_AVAILABLE_IN_BUILDING",q11);
            jsonBody.put("IS_TOILET_FACILITY_AVAILABLE_IN_BUILDING",q12);
            jsonBody.put("IS_RAMP_PROVIDED",q13);
            jsonBody.put("IS_ADEQUATE_FURNITURE_AVAILABLE_IN_BUILDING",q14);
            jsonBody.put("IS_SHADE_OR_SHELTER_AVAILABLE",q15);
            jsonBody.put("IS_PROPER_ROAD_CONNECTIVITY_AVAILABLE",q16);
            jsonBody.put("IS_ANY_RIVER_OR_VALLY_TO_CROSS_TO_REACH_PS",q17);
            jsonBody.put("IS_PS_HAS_LANDLINE_TELEPHONE_OR_FAX",q18);
            jsonBody.put("IS_PS_HAS_MOBILE_CONNECTIVITY",q19);
            jsonBody.put("IS_PS_HAS_INTERNET_FACILITY",q20);
            jsonBody.put("IS_PS_HAS_PROPER_SIGNAGE_OF_BUILDING_NAME_N_ADDRESS",q21);
            jsonBody.put("IS_PS_WITHIN_INSURGENCY_AFFECTED_AREA",q22);
            jsonBody.put("IS_PS_WITHIN_FOREST_OR_SEMIFOREST_AREA",q23);
            jsonBody.put("IS_PS_IN_VULNERABLE_CRITICAL_LOCATION",q24);
            jsonBody.put("IS_PS_SENSITIVE_OR_HYPER_SENSITIVE",q25);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("json formed",jsonBody.toString());

        Log.d("keyVal ",etpollingaddress.getText().toString()+" "+etpollingaddress2.getText().toString()+" "+etpollingaddres3.getText().toString()
                +" "+etpollingaddress4.getText().toString()+" "+buildingtypespinner.toString()+" "+slatlong.toString()+" "+encImage);

        Log.d("q123",q1+","+q25);

        boolean isInserted = myDb.insertExistingPSData(etpollingaddress.getText().toString(),etpollingaddress2.getText().toString(),AddressPin.toString(),
                buildingtypespinner.toString(),buildingpriorityspinner.toString(),slatlong.toString(),encImage,"false");
        if(isInserted = true){
            final Dialog dialog = new Dialog(ExistingPollingStation.this);
            dialog.setContentView(R.layout.custom_blo_report_done_message);
            Button btnok = (Button) dialog.findViewById(R.id.btnok);
            dialog.setCanceledOnTouchOutside(false);

            btnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(ExistingPollingStation.this,WelcomeBLONew.class);
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
    private boolean isphotouploaded() {
        if(selectedImagePath.isEmpty())
        {
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Polling_station_image_is_not_uploaded), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean address1() {
        if(etpollingaddress.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Empty_Flat_HouseNo_Floor_Building_is_not_allowed), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private boolean address2() {
        if(etpollingaddress2.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Empty_Colony_Street_Locality_is_not_allowed), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean address3() {
        if(etpollingaddres3.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Empty_City_State_is_not_allowed), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean pincode() {
        if(etpollingaddress4.getText().toString().isEmpty() || etpollingaddress4.length()!=6)
        {
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Invalid_Pincode), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private boolean Buildingtypespinner() {
        if(buildingtypespinner.toString().equals("SelectBuildingType"))
        {
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Select_Building_Type), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean priorityChoicespinner() {
        if(buildingpriorityspinner.toString().equals("Priority Choice"))
        {
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Select_Priority_Choice) , Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean GPS() {
        if(tvfetched.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Capture_GPS_location),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImageUri,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();


                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                File file = new File(selectedImagePath);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(picturePath, options);
                int width = options.outWidth;
                int height = options.outHeight;
                String swidth = Integer.toString(width);
                String sheight = Integer.toString(height);
                Log.e("file width ", swidth + "file Height " + sheight);

                long length = file.length();

                String slength = Long.toString(length);
                // if ((selectedImagePath.toLowerCase().endsWith(".jpg") || selectedImagePath.toLowerCase().endsWith(".jpeg")) && (length <= 1048576) && (width == 250 && height == 270)) {
                ivimage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                //tvnofilechoosen.setText(selectedImagePath);
                Log.e("Correct Format", selectedImagePath);
                //tverror.setVisibility(View.GONE);
                // }
                /*else {
                    ivimage.setImageBitmap(null);
                    tvnofilechoosen.setText("No file choosen");
                    final Dialog dialog = new Dialog(UploadPhoto.this);
                    dialog.setContentView(R.layout.custominvalidimage);
                    Button bOK = (Button) dialog.findViewById(R.id.bok);
                    dialog.setCanceledOnTouchOutside(false);


                    bOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                *//*Intent intent = new Intent(getApplicationContext(), MakePayment.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);*//*
                            dialog.dismiss();


                        }
                    });
                    dialog.show();
                    Toast.makeText(UploadPhoto.this, "Invalid Image", Toast.LENGTH_SHORT).show();
                    Log.e("Incorrect format", selectedImagePath);*/
            }
            //Log.e("file size", slength);
        }
    }


    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public boolean hasPermissions(Context context) {
        int permissionCamera = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        int permissionContacts = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS);
        int permissionStorage = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionPhone = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (permissionContacts != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);
        }
        if (permissionStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionPhone != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MY_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("TAG", "Permission callback called-------");
        switch (requestCode) {
            case MY_PERMISSIONS: {

                final Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_CONTACTS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d("TAG", "camera & contacts & storage & state services permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d("TAG", "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)||ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                            showDialogOK("CAMERA,CONTACTS,STORAGE and PHONE STATE Services Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    hasPermissions(ExistingPollingStation.this);
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(this, getApplicationContext().getResources().getString(R.string.Go_to_settings_and_enable_permissions), Toast.LENGTH_LONG)
                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }


    }
    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
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
                                final Dialog dialog = new Dialog(ExistingPollingStation.this);
                                dialog.setContentView(R.layout.custom_blo_report_done_message);
                                Button btnok = (Button) dialog.findViewById(R.id.btnok);
                                dialog.setCanceledOnTouchOutside(false);
                                btnok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent=new Intent(ExistingPollingStation.this,WelcomeBLONew.class);
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
        };
        queue.add(jsObjRequest);
    }

}
