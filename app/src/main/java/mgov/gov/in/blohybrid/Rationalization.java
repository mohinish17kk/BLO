package mgov.gov.in.blohybrid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Rationalization extends AppCompatActivity {

    TextView tvshow,tvhome,tvcapturstreetgps,tvshowstreetgps,tvcapturbuildinggps,tvshowbuildinggps,tvslno,tvcapturstreetgps2,tvcapturstreetgps3,tvshowstreetgps2,tvshowstreetgps3;
    DatabaseHelper myDb;
    EditText etstreet,etbuilding,ethouse,etdoor;
    String slatlong,slatlong2,slatlong3,slatlongbuilding;
    ProgressDialog pd;
    Button btn_submit;
    String sb1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rationalization);

        tvshow = (TextView) findViewById(R.id.tvshow);
        tvhome = (TextView) findViewById(R.id.tvhome);
        tvcapturstreetgps = (TextView) findViewById(R.id.tvcapturstreetgps);
        tvshowstreetgps = (TextView) findViewById(R.id.tvshowstreetgps);
        tvshowstreetgps2 = (TextView) findViewById(R.id.tvshowstreetgps2);
        tvshowstreetgps3 = (TextView) findViewById(R.id.tvshowstreetgps3);
        tvcapturbuildinggps = (TextView) findViewById(R.id.tvcapturbuildinggps);
        tvcapturstreetgps2 = (TextView) findViewById(R.id.tvcapturstreetgps2);
        tvcapturstreetgps3 = (TextView) findViewById(R.id.tvcapturstreetgps3);
        tvshowbuildinggps = (TextView) findViewById(R.id.tvshowbuildinggps);
        /*tvcapturhousegps = (TextView) findViewById(R.id.tvcapturhousegps);
        tvshowshousegps = (TextView) findViewById(R.id.tvshowshousegps);
        tvcapturdoorgps = (TextView) findViewById(R.id.tvcapturdoorgps);
        tvshowdoorgps = (TextView) findViewById(R.id.tvshowdoorgps);*/
        btn_submit = (Button) findViewById(R.id.btn_submit);
        tvslno = (TextView) findViewById(R.id.tvslno);
        etstreet = (EditText) findViewById(R.id.etstreet);
        etbuilding  = (EditText) findViewById(R.id.etbuilding);
        ethouse  = (EditText) findViewById(R.id.ethouse);
        etdoor  = (EditText) findViewById(R.id.etdoor);
        sb1 = getIntent().getExtras().getString("sb1");
        tvslno.setText(getApplicationContext().getResources().getString(R.string.For_Serial_Numbers)+" "+sb1);
        myDb = new DatabaseHelper(this);

        pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getApplicationContext().getResources().getString(R.string.Please_Wait));
        pd.setCancelable(false);

        tvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvcapturstreetgps.setOnClickListener(new View.OnClickListener() {
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
                        //Toast.makeText(getApplicationContext(),"Lat  "+lat+"  Lon  "+lon,Toast.LENGTH_LONG).show();
                        tvshowstreetgps.setText("  Lat  "+lat+"  Lon  "+lon);
                        Log.d("lat long","Lat  "+lat+"  Lon  "+lon);
                        Log.d("1","5");
                        slatlong = String.valueOf(lat)+","+String.valueOf(lon);
                        Log.d("slatlong",slatlong);
                    }
                } else {
                    Toast.makeText(Rationalization.this, getApplicationContext().getResources().getString(R.string.Please_Enable_GPS), Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvcapturstreetgps2.setOnClickListener(new View.OnClickListener() {
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
                        //Toast.makeText(getApplicationContext(),"Lat  "+lat+"  Lon  "+lon,Toast.LENGTH_LONG).show();
                        tvshowstreetgps2.setText("  Lat  "+lat+"  Lon  "+lon);
                        Log.d("lat long","Lat  "+lat+"  Lon  "+lon);
                        Log.d("1","5");
                        slatlong2 = String.valueOf(lat)+","+String.valueOf(lon);
                        Log.d("slatlong2",slatlong2);
                    }
                } else {
                    Toast.makeText(Rationalization.this, getApplicationContext().getResources().getString(R.string.Please_Enable_GPS), Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvcapturstreetgps3.setOnClickListener(new View.OnClickListener() {
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
                        //Toast.makeText(getApplicationContext(),"Lat  "+lat+"  Lon  "+lon,Toast.LENGTH_LONG).show();
                        tvshowstreetgps3.setText("  Lat  "+lat+"  Lon  "+lon);
                        Log.d("lat long","Lat  "+lat+"  Lon  "+lon);
                        Log.d("1","5");
                        slatlong3 = String.valueOf(lat)+","+String.valueOf(lon);
                        Log.d("slatlong3",slatlong3);
                    }
                } else {
                    Toast.makeText(Rationalization.this, getApplicationContext().getResources().getString(R.string.Please_Enable_GPS), Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvcapturbuildinggps.setOnClickListener(new View.OnClickListener() {
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
                        //Toast.makeText(getApplicationContext(),"Lat  "+lat+"  Lon  "+lon,Toast.LENGTH_LONG).show();
                        tvshowbuildinggps.setText("  Lat  "+lat+"  Lon  "+lon);
                        Log.d("lat long","Lat  "+lat+"  Lon  "+lon);
                        Log.d("1","5");
                        slatlongbuilding = String.valueOf(lat)+","+String.valueOf(lon);
                        Log.d("slatlongbuilding",slatlongbuilding);
                    }
                } else {
                    Toast.makeText(Rationalization.this, getApplicationContext().getResources().getString(R.string.Please_Enable_GPS), Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*tvcapturhousegps.setOnClickListener(new View.OnClickListener() {
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
                        //Toast.makeText(getApplicationContext(),"Lat  "+lat+"  Lon  "+lon,Toast.LENGTH_LONG).show();
                        tvshowshousegps.setText("  Lat  "+lat+"  Lon  "+lon);
                        Log.d("lat long","Lat  "+lat+"  Lon  "+lon);
                        Log.d("1","5");
                        slatlonghouse = String.valueOf(lat)+","+String.valueOf(lon);
                        Log.d("slatlonghouse",slatlonghouse);
                    }
                } else {
                    Toast.makeText(Rationalization.this, "Please Enable GPS", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvcapturdoorgps.setOnClickListener(new View.OnClickListener() {
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
                        //Toast.makeText(getApplicationContext(),"Lat  "+lat+"  Lon  "+lon,Toast.LENGTH_LONG).show();
                        tvshowdoorgps.setText("  Lat  "+lat+"  Lon  "+lon);
                        Log.d("lat long","Lat  "+lat+"  Lon  "+lon);
                        Log.d("1","5");
                        slatlongdoor = String.valueOf(lat)+","+String.valueOf(lon);
                        Log.d("slatlongdoor",slatlongdoor);
                    }
                } else {
                    Toast.makeText(Rationalization.this, "Please Enable GPS", Toast.LENGTH_SHORT).show();
                }
            }
        });
*/
        tvshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog();
                Cursor cursor = myDb.getAllDatafromTable14();
                if(cursor.getCount() == 0 ){
                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Nothing_to_show),Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while (cursor.moveToNext()){
                    stringBuffer.append("SerialNumbers : "+ cursor.getString(0)+"\n");
                    stringBuffer.append("StreetNumber : "+ cursor.getString(1)+"\n");
                    stringBuffer.append("StreetStartLocation :\n"+ cursor.getString(2)+"\n");
                    stringBuffer.append("StreetMidLocation :\n"+ cursor.getString(3)+"\n");
                    stringBuffer.append("StreetEndLocation :\n"+ cursor.getString(4)+"\n");
                    stringBuffer.append("BuildingDetail : "+ cursor.getString(5)+"\n");
                    stringBuffer.append("House : "+ cursor.getString(7)+"\n");
                    stringBuffer.append("Door : "+ cursor.getString(8)+"\n");
                    stringBuffer.append("BuildingGPSLocation :\n"+ cursor.getString(6)+"\n\n");
                }
                hideProgressDialog();
                showmessage("Data",stringBuffer.toString());

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

        if (!validateSSP()) {
            return;
        }
        if (!validateSMP()) {
            return;
        }
        if(!validateSEP()){
            return;
        }
        if(!validateBuildingGPS()){
            return;
        }/*if(!validateHouseGPS()){
            return;
        }if(!validateDoorGPS()) {
            return;
        }*/ if(!validateStreet()) {
            return;
        } if(!validateBuilding()) {
            return;
        } if(!validateHouse()) {
            return;
        } if(!validateDoor()) {
            return;
        }

        boolean isInserted = myDb.insertRationalizationData(sb1,etstreet.getText().toString(),tvshowstreetgps.getText().toString(),tvshowstreetgps2.getText().toString(),
                tvshowstreetgps3.getText().toString(),etbuilding.getText().toString(),ethouse.getText().toString(),etdoor.getText().toString(),tvshowbuildinggps.getText().toString(),"false");
        if(isInserted = true){
            final Dialog dialog = new Dialog(Rationalization.this);
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
            //Toast.makeText(getApplicationContext(),"Data locally saved successfully",Toast.LENGTH_SHORT).show();

        } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Data_not_saved_TRY_AGAIN),Toast.LENGTH_SHORT).show();
    }
    private boolean validateSSP() {
        if(tvshowstreetgps.getText().toString().trim().isEmpty())
        {
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.ERROR_Capture_Start_Point_of_Street),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean validateSMP() {
        if(tvshowstreetgps2.getText().toString().trim().isEmpty())
        {
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.ERROR_Capture_Mid_Point_of_Street),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean validateSEP() {
        if(tvshowstreetgps3.getText().toString().trim().isEmpty())
        {
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.ERROR_Capture_End_Point_of_Street),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean validateBuildingGPS() {
        if(tvshowbuildinggps.getText().toString().trim().isEmpty())
        {
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.ERROR_Capture_Building_GPS_Location),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    /*private boolean validateHouseGPS() {
        if(tvshowshousegps.getText().toString().trim().isEmpty())
        {
            Toast.makeText(getApplicationContext(),"ERROR: Capture House GPS Location",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean validateDoorGPS() {
        if(tvshowdoorgps.getText().toString().trim().isEmpty())
        {
            Toast.makeText(getApplicationContext(),"ERROR: Capture Door GPS Location",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }*/

    private boolean validateStreet() {
        if(etstreet.getText().toString().trim().isEmpty())
        {
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.ERROR_Mention_Street_Number),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean validateBuilding() {
        if(etbuilding.getText().toString().trim().isEmpty())
        {
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.ERROR_Mention_Building_Number),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean validateHouse() {
        if(ethouse.getText().toString().trim().isEmpty())
        {
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.ERROR_Mention_House_Number),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean validateDoor() {
        if(etdoor.getText().toString().trim().isEmpty())
        {
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.ERROR_Mention_Door_Number),Toast.LENGTH_SHORT).show();
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


}
