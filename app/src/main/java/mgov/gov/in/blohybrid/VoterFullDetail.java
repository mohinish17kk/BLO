package mgov.gov.in.blohybrid;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import mgov.gov.in.blohybrid.Constants;

public class VoterFullDetail extends AppCompatActivity {

    TextView tvname,tvepic,tvserial,tvvoterrlnname,tvgender,tvage,tvaddress,tvhome,etform7and8;
    java.util.Date Date;
    EditText etform8,etshifted,etdeath,etmob,etform8ref;
    String[] asd = { "Verified", "Permanently Shifted", "Dead", "Multiple Entries" };
    String[] mob = {"Select Platform", "IOS" , "Android" , "Windows" , "Google" , "Other"};
    private Spinner spinnerasd,spinnermobile;
    String asdselectedspinner,mobiletypespinner,Svip,Sdivyangjan,Sprnpr,Sdvoter,Spwd,Sform7,IP_HEADER;
    RadioButton rbsmart,rbbasic,rbself,rbhof,rbregistered,rbreported,rbvip,rbselectedvoter,rbretired;
    CheckBox cbdivyangjan,cbdvoter,cbprnpr,cbpwdmarked,cbform7and8;
    LinearLayout llmobstatus,llmobtype,llmob,lldeath;
    Button btnsubmit,btnrectification;
    ProgressDialog pd;
    RequestQueue queue;
    JSONObject jsonBody = new JSONObject();
    DatabaseHelper myDb;
    String url;//="http://117.239.180.198/BLONet_API_Test/api/blo/Post_BLOReport?st_code=S13&ac_no=1&part_no=1";
    Map<String, String> keyVal = new LinkedHashMap<String, String>();
    String Sreason,Smobowner,Smobtype,Sasd,SregisteredOrReported;
    String mobnumber,stcode,acno,partno;
    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_full_detail);

        final Spinner spin = (Spinner) findViewById(R.id.spinnerASD);
        final Spinner spinmob = (Spinner) findViewById(R.id.spinnerPhonetype);
        tvname= (TextView) findViewById(R.id.tvname);
        tvepic= (TextView) findViewById(R.id.tvvoterepicnumber);
        tvserial= (TextView) findViewById(R.id.tvvoterserialnumber);
        tvvoterrlnname= (TextView) findViewById(R.id.tvvoterrlnname);
        tvgender= (TextView) findViewById(R.id.tvvotergender);
        tvage= (TextView) findViewById(R.id.tvvoterage);
        tvaddress= (TextView) findViewById(R.id.tvvoteraddress);
        tvhome = (TextView) findViewById(R.id.tvhome);
        cbdivyangjan = (CheckBox) findViewById(R.id.cbdivyangjan);
        cbdvoter = (CheckBox) findViewById(R.id.cbdvoter);
        cbprnpr = (CheckBox) findViewById(R.id.cbprnpr);
        cbpwdmarked = (CheckBox) findViewById(R.id.cbpwdmarked);
        cbform7and8 = (CheckBox) findViewById(R.id.cbform7and8);
        rbsmart = (RadioButton) findViewById(R.id.rbsmart);
        rbbasic = (RadioButton) findViewById(R.id.rbbasic);
        rbself = (RadioButton) findViewById(R.id.rbself);
        rbhof = (RadioButton) findViewById(R.id.rbhof);
        rbselectedvoter = (RadioButton) findViewById(R.id.rbselectedvoter);
        rbreported = (RadioButton) findViewById(R.id.rbreported);
        rbvip = (RadioButton) findViewById(R.id.rbvip);
        rbretired = (RadioButton) findViewById(R.id.rbretired);
        rbregistered = (RadioButton) findViewById(R.id.rbregistered);
        etmob = (EditText) findViewById(R.id.etmobile);
        llmobstatus = (LinearLayout) findViewById(R.id.llmobstatus);
        llmob = (LinearLayout) findViewById(R.id.llmob);
        llmobtype = (LinearLayout) findViewById(R.id.llmobtype);
        lldeath = (LinearLayout) findViewById(R.id.lldeath);
        btnsubmit = (Button) findViewById(R.id.btnsubmit);
        btnrectification = (Button) findViewById(R.id.btnrectification);
        etshifted = (EditText) findViewById(R.id.etshifted);
        etdeath = (EditText) findViewById(R.id.etdeath);
        etform8ref = (EditText) findViewById(R.id.etform8ref);
        etform7and8 =(EditText) findViewById(R.id.etform7and8);
        IP_HEADER = Constants.IP_HEADER;
       // etdeathauthority = (EditText) findViewById(R.id.etdeathauthority);
        myDb = new DatabaseHelper(this);

        String sname = getIntent().getExtras().getString("sname");
        String sepic = getIntent().getExtras().getString("sepic");
        String sgender = getIntent().getExtras().getString("sgender");
        String sserial = getIntent().getExtras().getString("sserial");
        String sage = getIntent().getExtras().getString("sage");
        String saddress = getIntent().getExtras().getString("address");
        String svoterrlnname = getIntent().getExtras().getString("srlnname");

        Log.d("voterfulldetail","sname- "+sname+" sepic- "+sepic+" sgender- "+sgender+" sserial- "+sserial+" sage- "+sage+" saddress- "+saddress+" svoterrlnname-"+svoterrlnname);

        tvname.setText(sname);
        tvepic.setText(sepic);
        tvgender.setText(sgender);
        tvserial.setText(sserial);
        tvage.setText(sage);
        if(!saddress.equals("null")){
            tvaddress.setText(saddress);
        } else tvaddress.setText("--");

        tvvoterrlnname.setText(svoterrlnname);

        prefs = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mobnumber  = prefs.getString("mob","");
        stcode = prefs.getString("st_code","");
        acno = prefs.getString("AcNo","");
        partno = prefs.getString("PartNo","");
        url = IP_HEADER+"Post_ASD_Status?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno;
               //http://117.239.180.198/BLONet_API_Dev/api/blo/Post_ASD_Status?st_code=S13&ac_no=1&part_no=1

        Log.d("mobedf",mobnumber);
        Log.d("st_code",stcode);
        Log.d("AcNo",acno);
        Log.d("PartNo",partno);

        //J&K
        if(stcode.equals("S09")|| stcode == "S09"){
            cbprnpr.setVisibility(View.VISIBLE);
        }
        //ASSAM
        if(stcode.equals("S03")|| stcode == "S03"){
            cbdvoter.setVisibility(View.VISIBLE);
        }

        pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getApplicationContext().getResources().getString(R.string.Please_Wait));
        pd.setCancelable(false);
        queue = Volley.newRequestQueue(this);

        /*etmob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        etmob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                llmobstatus.setVisibility(View.VISIBLE);
                return false;
            }
        });
        tvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(VoterFullDetail.this,WelcomeBLONew.class);
                startActivity(intent);
                finish();
            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(VoterFullDetail.this);
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

        rbhof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbself.setChecked(false);
            }
        });

        rbself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbhof.setChecked(false);
            }
        });
        rbbasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"male cheked",Toast.LENGTH_LONG).show();
                rbsmart.setChecked(false);
                spinmob.setVisibility(View.GONE);

            }
        });



        rbsmart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"male cheked",Toast.LENGTH_LONG).show();
                rbbasic.setChecked(false);
                spinmob.setVisibility(View.VISIBLE);

            }
        });

        rbregistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"male cheked",Toast.LENGTH_LONG).show();
                rbreported.setChecked(false);
                SregisteredOrReported = "Registered Death";

            }
        });

        rbreported.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"male cheked",Toast.LENGTH_LONG).show();
                rbregistered.setChecked(false);
                SregisteredOrReported = "Reported Death";
            }
        });

        rbvip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbselectedvoter.setChecked(false);
                rbretired.setChecked(false);
            }
        });

        rbselectedvoter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbvip.setChecked(false);
                rbretired.setChecked(false);
            }
        });
        rbretired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbvip.setChecked(false);
                rbselectedvoter.setChecked(false);
            }
        });

        btnrectification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(VoterFullDetail.this);
                dialog.setContentView(R.layout.custom_rectification_popup_menu);
                TextView tvchangeName = (TextView) dialog.findViewById(R.id.changeName);
                TextView tvchangeRLNName = (TextView) dialog.findViewById(R.id.changeRLNName);
                TextView tvchangeAddress = (TextView) dialog.findViewById(R.id.changeAddress);
                dialog.setCanceledOnTouchOutside(false);

                tvchangeName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        final Dialog dialogg = new Dialog(VoterFullDetail.this);
                        dialogg.setContentView(R.layout.custom_form6_popup);
                        TextView tvonline = (TextView) dialogg.findViewById(R.id.tvonline);
                        TextView tvdirect = (TextView) dialogg.findViewById(R.id.tvdirect);
                        dialogg.setCanceledOnTouchOutside(false);

                        tvonline.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogg.dismiss();
                                Intent intent = new Intent();
                                intent.setComponent(new ComponentName("com.ecip.msdp.ecitest1",
                                        "com.ecip.msdp.ecitest1.Home"));
                                PackageManager manager = getApplicationContext().getPackageManager();
                                List<ResolveInfo> infos = manager.queryIntentActivities(intent, 0);
                                if (infos.size() > 0) {
                                    startActivity(intent);
                                    //Then there is an Application(s) can handle your intent
                                } else {
                                    //Toast.makeText(getApplicationContext(),"Please install voter list application",Toast.LENGTH_SHORT).show(); //No Application can handle your intent
                                    final Dialog dialog = new Dialog(VoterFullDetail.this);
                                    dialog.setContentView(R.layout.custom_voter_service_app_link);
                                    Button btnok = (Button) dialog.findViewById(R.id.btnok);
                                    Button bcancel = (Button) dialog.findViewById(R.id.bcancel);
                                    dialog.setCanceledOnTouchOutside(false);

                                    btnok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String url = "https://apps.mgov.gov.in/descp.do?param=0&appid=1457&fb=true";
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
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
                            }
                        });
                        tvdirect.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(VoterFullDetail.this,DirectSubmission.class);
                                startActivity(intent);
                                dialogg.dismiss();
                            }
                        });
                        dialogg.show();
                    }
                });

                tvchangeRLNName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                        final Dialog dialogg = new Dialog(VoterFullDetail.this);
                        dialogg.setContentView(R.layout.custom_form6_popup);
                        TextView tvonline = (TextView) dialogg.findViewById(R.id.tvonline);
                        TextView tvdirect = (TextView) dialogg.findViewById(R.id.tvdirect);
                        dialogg.setCanceledOnTouchOutside(false);

                        tvonline.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogg.dismiss();
                                Intent intent = new Intent();
                                intent.setComponent(new ComponentName("com.ecip.msdp.ecitest1",
                                        "com.ecip.msdp.ecitest1.Home"));
                                PackageManager manager = getApplicationContext().getPackageManager();
                                List<ResolveInfo> infos = manager.queryIntentActivities(intent, 0);
                                if (infos.size() > 0) {
                                    startActivity(intent);
                                    //Then there is an Application(s) can handle your intent
                                } else {
                                    //Toast.makeText(getApplicationContext(),"Please install voter list application",Toast.LENGTH_SHORT).show(); //No Application can handle your intent
                                    final Dialog dialog = new Dialog(VoterFullDetail.this);
                                    dialog.setContentView(R.layout.custom_voter_service_app_link);
                                    Button btnok = (Button) dialog.findViewById(R.id.btnok);
                                    Button bcancel = (Button) dialog.findViewById(R.id.bcancel);
                                    dialog.setCanceledOnTouchOutside(false);

                                    btnok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String url = "https://apps.mgov.gov.in/descp.do?param=0&appid=1457&fb=true";
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
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

                            }
                        });
                        tvdirect.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(VoterFullDetail.this,DirectSubmission.class);
                                startActivity(intent);
                                dialogg.dismiss();
                            }
                        });
                        dialogg.show();

                    }
                });

                tvchangeAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                        final Dialog dialogg = new Dialog(VoterFullDetail.this);
                        dialogg.setContentView(R.layout.custom_form6_popup);
                        TextView tvonline = (TextView) dialogg.findViewById(R.id.tvonline);
                        TextView tvdirect = (TextView) dialogg.findViewById(R.id.tvdirect);
                        dialogg.setCanceledOnTouchOutside(false);

                        tvonline.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogg.dismiss();
                                Intent intent = new Intent();
                                intent.setComponent(new ComponentName("com.ecip.msdp.ecitest1",
                                        "com.ecip.msdp.ecitest1.Home"));
                                PackageManager manager = getApplicationContext().getPackageManager();
                                List<ResolveInfo> infos = manager.queryIntentActivities(intent, 0);
                                if (infos.size() > 0) {
                                    startActivity(intent);
                                    //Then there is an Application(s) can handle your intent
                                } else {
                                    //Toast.makeText(getApplicationContext(),"Please install voter list application",Toast.LENGTH_SHORT).show(); //No Application can handle your intent
                                    final Dialog dialog = new Dialog(VoterFullDetail.this);
                                    dialog.setContentView(R.layout.custom_voter_service_app_link);
                                    Button btnok = (Button) dialog.findViewById(R.id.btnok);
                                    Button bcancel = (Button) dialog.findViewById(R.id.bcancel);
                                    dialog.setCanceledOnTouchOutside(false);

                                    btnok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String url = "https://apps.mgov.gov.in/descp.do?param=0&appid=1457&fb=true";
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
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
                            }
                        });
                        tvdirect.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(VoterFullDetail.this,DirectSubmission.class);
                                startActivity(intent);
                                dialogg.dismiss();
                            }
                        });
                        dialogg.show();


                    }
                });

                dialog.show();
            }
        });

        ArrayAdapter<String> sourceofdateAdapter= new ArrayAdapter<>(VoterFullDetail.this,android.R.layout.simple_list_item_checked,asd);
        spin.setAdapter(sourceofdateAdapter);

        ArrayAdapter<String> sourceofmobAdapter= new ArrayAdapter<>(VoterFullDetail.this,android.R.layout.simple_list_item_checked,mob);
        spinmob.setAdapter(sourceofmobAdapter);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                asdselectedspinner = (String) spin.getItemAtPosition(position);
                if(asdselectedspinner.toString().equals("Permanently Shifted")){
                    etshifted.setVisibility(View.VISIBLE);
                    etdeath.setVisibility(View.GONE);
                    llmob.setVisibility(View.GONE);
                    llmobtype.setVisibility(View.GONE);
                    spinmob.setVisibility(View.GONE);
                    llmobstatus.setVisibility(View.GONE);
                    lldeath.setVisibility(View.GONE);
                    //etdeathauthority.setVisibility(View.GONE);
                } else  if(asdselectedspinner.toString().equals("Dead")){
                    etshifted.setVisibility(View.GONE);
                    etdeath.setVisibility(View.VISIBLE);
                    llmob.setVisibility(View.GONE);
                    llmobtype.setVisibility(View.GONE);
                    spinmob.setVisibility(View.GONE);
                    llmobstatus.setVisibility(View.GONE);
                    lldeath.setVisibility(View.VISIBLE);
                    //etdeathauthority.setVisibility(View.VISIBLE);
                } else  if(asdselectedspinner.toString().equals("Verified")){
                    etshifted.setVisibility(View.GONE);
                    etdeath.setVisibility(View.GONE);
                    llmob.setVisibility(View.VISIBLE);
                    llmobtype.setVisibility(View.VISIBLE);
                    lldeath.setVisibility(View.GONE);
                    //spinmob.setVisibility(View.VISIBLE);
                    //llmobstatus.setVisibility(View.VISIBLE);
                } else  if(asdselectedspinner.toString().equals("Multiple Entries")){
                    etshifted.setVisibility(View.GONE);
                    etdeath.setVisibility(View.GONE);
                    llmob.setVisibility(View.VISIBLE);
                    llmobtype.setVisibility(View.VISIBLE);
                    lldeath.setVisibility(View.GONE);
                    //spinmob.setVisibility(View.VISIBLE);
                    //llmobstatus.setVisibility(View.VISIBLE);
                }
                //Toast.makeText(getApplicationContext(),asdselectedspinner.toString(),Toast.LENGTH_LONG).show();
                Log.e("date", asdselectedspinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

        if(!((asdselectedspinner.toString().equals("Verified")) || (asdselectedspinner.toString().equals("Multiple Entries")))){
            etmob.setText("");
            Smobowner = "";
            Smobtype = "";
            Log.d("test123","mob"+etmob.getText().toString()+" ownr"+Smobtype+" type"+Smobtype);
        } else if(!(etmob.getText().toString().isEmpty())){
            if (!validmobile()) {
                return;
            }

            if(!validMbileowner())
            {
                return;
            }
            if(!validmobiletype())
            {
                return;
            } if(!validplatform()){
                return;
            }
        }



        keyVal.put("SerialNo",tvserial.getText().toString());
        if(asdselectedspinner.toString().equals("Permanently Shifted")){
            if(!(etshifted.getText().toString().isEmpty())){
                Sreason = etshifted.getText().toString();
            }else Sreason = "NA";

        }
        if(asdselectedspinner.toString().equals("Dead")){
            if(!(rbregistered.isChecked() || rbreported.isChecked()))
            {
                Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Click_on_either_Registered_or_Reported_Death_option),Toast.LENGTH_SHORT).show();
                return;
            }
            if(!(etdeath.getText().toString().isEmpty())){
                Sreason = SregisteredOrReported + " , " + etdeath.getText().toString();
            }else Sreason = SregisteredOrReported +"NA";
        } if(asdselectedspinner.toString().equals("Verified") || asdselectedspinner.toString().equals("Multiple Entries")){
            Sreason = "NA";
        }
        /*if((asdselectedspinner.toString().equals("Multiple Entries"))){
            if(!(etmob.getText().toString().isEmpty())){
                if (!validmobile()) {
                    return;
                }

                if(!validMbileowner())
                {
                    return;
                }
                if(!validmobiletype())
                {
                    return;
                } if(!validplatform()){
                    return;
                }
            } else {
                Smobowner = "";
                Smobtype = "";
                Sreason = "NA";
                etmob.setText("");
            }
        }*/
        keyVal.put("Reason",Sreason.toString());
        keyVal.put("UserMobileNo",etmob.getText().toString());
        if(rbhof.isChecked()){
            Smobowner = "HOF";
        } else  if(rbself.isChecked()){
            Smobowner = "Self";
        } else {Smobowner = "";}
        keyVal.put("MobileOwnedType",Smobowner.toString());
        if(rbsmart.isChecked()){
            Smobtype = "SmartPhone";
        } else  if(rbbasic.isChecked()){
            Smobtype = "Feature";
        } else {Smobtype = "";}
        keyVal.put("MobileType",Smobtype.toString());
        if(asdselectedspinner.toString().equals("Verified")){
            Sasd = "Verified";
        } else  if(asdselectedspinner.toString().equals("Permanently Shifted")){
                  Sasd = "PermanentShifted";
        } else  if(asdselectedspinner.toString().equals("Dead")){
                  Sasd = "Dead";
        } else if (asdselectedspinner.toString().equals("Multiple Entries")){
                  Sasd = "MultipleEntry";
        }

        if(rbvip.isChecked()){
            Svip= "true";
        } else Svip= "fasle";
        if(cbdivyangjan.isChecked()){
            Sdivyangjan = "true";
        } else  Sdivyangjan = "false";
        if(cbdvoter.isChecked()){
            Sdvoter = "true";
        }else Sdvoter = "false";
        if(cbprnpr.isChecked()){
            Sprnpr = "true";
        } else Sprnpr = "false";
        //Verified", "Permanently Shifted", "Dead
        if(cbpwdmarked.isChecked())
        {
            Spwd = "true";
        } else Spwd = "false";
        if(cbform7and8.isChecked()){
            Sform7 = "true";
        } else Sform7 = "false";
        keyVal.put("ASDstatus",Sasd);

        try {
            jsonBody.put("SerialNo",tvserial.getText().toString());
            jsonBody.put("Reason",Sreason.toString());
            jsonBody.put("UserMobileNo",etmob.getText().toString());
            jsonBody.put("MobileOwnedType",Smobowner.toString());
            jsonBody.put("MobileType",Smobtype.toString());
            jsonBody.put("ASDstatus",Sasd);
            jsonBody.put("RLN_Name",tvvoterrlnname.getText().toString());
            jsonBody.put("PRNPR",Sprnpr);
            jsonBody.put("Dvoter",Sdvoter);
            jsonBody.put("IS_PWD",Spwd);
            jsonBody.put("FORM7_REF_NO",Sform7);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("json formed",jsonBody.toString());

        Log.d("ASD",tvserial.getText().toString()+" "+Sreason+" "+etmob.getText().toString()
                +" "+Smobowner+" "+Smobtype+" "+asdselectedspinner);
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);
        Log.d("thisDate", thisDate);
        int serial = Integer.parseInt((String) tvserial.getText());

       // vollyRequestForPost(url, keyVal);
        boolean isInserted = myDb.insertASDData(serial,Sreason.toString(),etmob.getText().toString(),
                Smobowner.toString(),Smobtype.toString(),Sasd,thisDate,tvvoterrlnname.getText().toString(),Sdivyangjan,Sprnpr,Sdvoter,Svip,"false",Spwd,Sform7,etform7and8.getText().toString(),etform8ref.getText().toString());

        boolean isSaved = myDb.updateVoterList(tvserial.getText().toString().trim(),"true");
        if(isSaved == true){
            Log.d("updateVoterList","saved");
        } else  Log.d("updateVoterList","not saved");
        if(isInserted = true){
            final Dialog dialog = new Dialog(VoterFullDetail.this);
            dialog.setContentView(R.layout.custom_blo_report_done_message);
            Button btnok = (Button) dialog.findViewById(R.id.btnok);
            dialog.setCanceledOnTouchOutside(false);

            btnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(VoterFullDetail.this,VoterListFamily.class);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();

                }
            });
            dialog.show();
            //Toast.makeText(getApplicationContext(),"Data locally saved successfully",Toast.LENGTH_SHORT).show();

        } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Data_not_saved_TRY_AGAIN),Toast.LENGTH_SHORT).show();

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


    private boolean validmobiletype() {
        if(!(rbsmart.isChecked()) && !(rbbasic.isChecked())){
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Select_Phone_Type),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean validMbileowner() {
        if(!(rbhof.isChecked()) && !(rbself.isChecked()))
        {
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Select_mobile_owner),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean validmobile() {
        if(etmob.getText().toString().isEmpty()|| etmob.length()!=10){
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Enter_Valid_Contact_Number),Toast.LENGTH_LONG).show();
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

    /*public void vollyRequestForPost(String URL, Map<String, String> keyVal) {
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
                                final Dialog dialog = new Dialog(VoterFullDetail.this);
                                dialog.setContentView(R.layout.custom_blo_report_done_message);
                                Button btnok = (Button) dialog.findViewById(R.id.btnok);
                                dialog.setCanceledOnTouchOutside(false);




                                btnok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent=new Intent(VoterFullDetail.this,VoterListFamily.class);
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
                params.put("SerialNo",tvserial.getText().toString());
                params.put("Reason",Sreason.toString());
                params.put("UserMobileNo",etmob.getText().toString());
                params.put("MobileOwnedType",Smobowner.toString());
                params.put("MobileType",Smobtype.toString());
                params.put("ASDstatus",Sasd);
                Log.d("ASDD",tvserial.getText().toString()+" "+Sreason+" "+etmob.getText().toString()
                        +" "+Smobowner+" "+Smobtype+" "+asdselectedspinner);

                return params;
            }*//*
        };
        queue.add(jsObjRequest);
    }*/

}
