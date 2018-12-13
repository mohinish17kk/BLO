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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WelcomeBLO extends AppCompatActivity {
    TextView tvfieldvisit,tvlogout,tvgps,tvbloreport,tvstateid,tvacid,tvpartid,tvbloname,tvoverseas,tvoption5,tvoption6,tvoption7,tvoptionexistingPS,option8AuxiPS,option9PoliceStation,option10HealthCare,option11adjacent;
    ProgressDialog pd;
    RequestQueue queue;
    String url,mob,stcode,acno,partno,bloname,SharedKey;
    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_blo);

        tvfieldvisit= (TextView) findViewById(R.id.option2);
        tvlogout = (TextView) findViewById(R.id.logout);
        tvgps = (TextView) findViewById(R.id.option3);
        tvbloreport = (TextView) findViewById(R.id.option4);
        tvstateid = (TextView) findViewById(R.id.tvstateid);
        tvacid = (TextView) findViewById(R.id.tvacid);
        tvpartid = (TextView) findViewById(R.id.tvpartid);
        tvbloname = (TextView) findViewById(R.id.textView);
        tvoverseas = (TextView) findViewById(R.id.etoverseas);
        tvoption5 = (TextView) findViewById(R.id.option5);
        tvoption6 = (TextView) findViewById(R.id.option6);
        tvoption7 = (TextView) findViewById(R.id.option7);
        option8AuxiPS = (TextView) findViewById(R.id.option8);
        option9PoliceStation = (TextView) findViewById(R.id.option9);
        option10HealthCare = (TextView) findViewById(R.id.option10);


        tvoptionexistingPS = (TextView) findViewById(R.id.optionexistingPS);

        prefs = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mob  = prefs.getString("mob","");
        stcode = prefs.getString("st_code","");
        acno = prefs.getString("AcNo","");
        partno = prefs.getString("PartNo","");
        bloname = prefs.getString("BLO_NAME","");
        SharedKey = prefs.getString("Key","");
        Log.d("SharedKey",SharedKey);
        tvstateid.setText("State "+stcode);
        tvacid.setText("Ac "+acno);
        tvpartid.setText("Part "+partno);
        tvbloname.setText("Welcome "+bloname);

        //prefs = this.getSharedPreferences (MyPREFERENCES, Context.MODE_PRIVATE);
        //

        Log.d("mobedf",mob);
        Log.d("st_code",stcode);
        Log.d("AcNo",acno);
        Log.d("PartNo",partno);
        Log.d("bloname",bloname);

        pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getApplicationContext().getResources().getString(R.string.Please_Wait));
        pd.setCancelable(false);
        queue = Volley.newRequestQueue(this);

        /*if(ConnectionStatus.isNetworkConnected(WelcomeBLO.this))
        {
            Log.d("mob123",mob);
            //url ="http://117.239.180.198/BLONet_API_Dev/api/blo/GetBloDetails?st_code="+stcode+"&mobile_no="+mob+"&ac_no="+acno+"&part_no="+partno;
            url ="http://eronet.ecinet.in/services/api/blonet/GetBloDetails?st_code="+stcode+"&mobile_no="+mob+"&ac_no="+acno+"&part_no="+partno;
            Log.d("Welcomeblourl",url);
            showProgressDialog();
            JSONObject obj = new JSONObject();
            Helper.getmHelper().v("OBJ",obj.toString());


            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, obj, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("On Response : ", response.toString());
                            try {
                                String IsSuccess = response.getString("IsSuccess");
                                Log.d("IsSuccess",IsSuccess);
                                String StateCode = response.getString("StateCode");
                                //Log.v("Response : ", "IsSuccess : " + IsSuccess);

                                if(IsSuccess.trim().equals("true") || IsSuccess.trim().equals("true")) {
                                    //String StateCode = response.getString("StateCode");
                                    String AcNo = response.getString("AcNo");
                                    String PartNo = response.getString("PartNo");
                                    String BLOName = response.getString("BLOName");

                                    tvstateid.setText("State "+StateCode);
                                    tvacid.setText("Ac "+AcNo);
                                    tvpartid.setText("Part "+PartNo);
                                    tvbloname.setText("Welcome "+BLOName);



                                } else
                                    Toast.makeText(getApplicationContext(), "No Response from Server.", Toast.LENGTH_LONG).show();
                                finish();



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
                    });

// Access the RequestQueue through your singleton class.
            queue.add(jsObjRequest);



        }
        else
        {
            Intent intent=new Intent(getApplicationContext(),NoNetwork.class);
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }*/


        tvoptionexistingPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WelcomeBLO.this,ExistingPollingStation.class);
                startActivity(intent);
            }
        });

        tvgps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Under Development",Toast.LENGTH_LONG).show();
            }
        });

        tvbloreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WelcomeBLO.this,BloReport.class);
                startActivity(intent);
            }
        });

        tvoverseas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WelcomeBLO.this,OverseasVoter.class);
                startActivity(intent);
            }
        });

        tvoption5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WelcomeBLO.this,PossiblePoolingStation.class);
                startActivity(intent);
            }
        });

        tvoption6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WelcomeBLO.this,PostOfficeGPSLocation.class);
                startActivity(intent);
            }
        });

        option8AuxiPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WelcomeBLO.this,AuxiliaryPollingStation.class);
                startActivity(intent);
            }
        });

        option9PoliceStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WelcomeBLO.this,PoliceGPSLocation.class);
                startActivity(intent);
            }
        });

        option10HealthCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WelcomeBLO.this,HealthCareCentre.class);
                startActivity(intent);
            }
        });



        tvoption7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(WelcomeBLO.this);
                dialog.setContentView(R.layout.customaccountdeactivate);
                Button btnok = (Button) dialog.findViewById(R.id.bok);
                Button bcancel = (Button) dialog.findViewById(R.id.bcancel);
                dialog.setCanceledOnTouchOutside(false);

                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        final Dialog dialogg = new Dialog(WelcomeBLO.this);
                        dialogg.setContentView(R.layout.customaccountdeactivatewithreason);
                        Button btnok = (Button) dialogg.findViewById(R.id.bok);
                        final EditText etidreason = (EditText) dialogg.findViewById(R.id.etidreason);
                        dialogg.setCanceledOnTouchOutside(false);

                        btnok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(etidreason.length()>5){
                                    if(ConnectionStatus.isNetworkConnected(WelcomeBLO.this))
                                    {
                                        submit();
                                        //Toast.makeText(getApplicationContext(),"Submitted",Toast.LENGTH_SHORT).show();
                                        dialogg.dismiss();

                                    }
                                    else
                                    {
                                        Intent intent=new Intent(getApplicationContext(),NoNetwork.class);
                                        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }

                                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Mention_reason),Toast.LENGTH_SHORT).show();


                        /*Toast.makeText(getApplicationContext(),"Your account is deactivated now",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(WelcomeBLO.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();*/

                            }
                        });

                        dialogg.show();
                        /*Toast.makeText(getApplicationContext(),"Your account is deactivated now",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(WelcomeBLO.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();*/

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

        tvlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WelcomeBLO.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvfieldvisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(WelcomeBLO.this);
                dialog.setContentView(R.layout.custom_field_visit_popup_menu);
                TextView tvasd = (TextView) dialog.findViewById(R.id.asdstatus);
                TextView tvlov = (TextView) dialog.findViewById(R.id.leftovervoter);
                TextView tvnewvoter = (TextView) dialog.findViewById(R.id.newvoter);
                TextView tvq = (TextView) dialog.findViewById(R.id.ifany);
                dialog.setCanceledOnTouchOutside(false);

                tvnewvoter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent=new Intent(WelcomeBLO.this,UnenrolledActivity.class);
                        startActivity(intent);
                        /*Intent intent = new Intent();
                        intent.setComponent(new ComponentName("com.ecip.msdp.ecitest1",
                                "com.ecip.msdp.ecitest1.Home"));
                        PackageManager manager = getApplicationContext().getPackageManager();
                        List<ResolveInfo> infos = manager.queryIntentActivities(intent, 0);
                        if (infos.size() > 0) {
                            startActivity(intent);
                            //Then there is an Application(s) can handle your intent
                        } else {
                            //Toast.makeText(getApplicationContext(),"Please install voter list application",Toast.LENGTH_SHORT).show(); //No Application can handle your intent
                            final Dialog dialog = new Dialog(WelcomeBLO.this);
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
*/
                    /*    dialog.dismiss();

                        final Dialog dialogg = new Dialog(WelcomeBLO.this);
                        dialogg.setContentView(R.layout.custom_form6_popup);
                        TextView tvonline = (TextView) dialogg.findViewById(R.id.tvonline);
                        TextView tvdirect = (TextView) dialogg.findViewById(R.id.tvdirect);
                        dialogg.setCanceledOnTouchOutside(false);

                        tvonline.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                *//*Intent intent=new Intent(WelcomeBLO.this,NewVoter.class);
                                startActivity(intent);*//*
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
                                    final Dialog dialog = new Dialog(WelcomeBLO.this);
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
                               // startActivity(intent);
                                //com.ecip.msdp.ecitest1
                                //Toast.makeText(getApplicationContext(),"Under Development",Toast.LENGTH_SHORT).show();
                                dialogg.dismiss();
                            }
                        });

                        tvdirect.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(WelcomeBLO.this,NewVoter.class);
                                startActivity(intent);
                                dialogg.dismiss();
                            }
                        });
                        dialogg.show();
                    */
                        dialog.dismiss();}

                });

                tvq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(WelcomeBLO.this,ToBeQualified.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });


                tvasd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                                Intent intent=new Intent(WelcomeBLO.this,FieldVisitDashboard.class);
                                startActivity(intent);
                        dialog.dismiss();

                    }
                });

                tvlov.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
                            final Dialog dialog = new Dialog(WelcomeBLO.this);
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

                       /* dialog.dismiss();

                        final Dialog dialogg = new Dialog(WelcomeBLO.this);
                        dialogg.setContentView(R.layout.custom_form6_popup);
                        TextView tvonline = (TextView) dialogg.findViewById(R.id.tvonline);
                        TextView tvdirect = (TextView) dialogg.findViewById(R.id.tvdirect);
                        dialogg.setCanceledOnTouchOutside(false);

                        tvonline.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                *//*Intent intent=new Intent(WelcomeBLO.this,NewVoter.class);
                                startActivity(intent);*//*
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
                                    final Dialog dialog = new Dialog(WelcomeBLO.this);
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


                                    //startActivity(intent);
                                //Toast.makeText(getApplicationContext(),"Under Development",Toast.LENGTH_SHORT).show();
                                dialogg.dismiss();
                            }
                        });

                        tvdirect.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(WelcomeBLO.this,LeftOverVoter.class);
                                startActivity(intent);
                                dialogg.dismiss();
                            }
                        });
                        dialogg.show();*/
                   dialog.dismiss(); }
                });


                dialog.show();

            }
        });


    }

    private void submit() {
        //url ="http://eronet.ecinet.in/services/api/BLONet/DisableUser?UserId="+mob+"&st_code="+stcode+"&ac_no="+acno+"&part_no="+partno;
          url ="http://eronet.ecinet.in/services/api/BLONet/DisableUser?UserId="+mob+"&st_code="+stcode+"&ac_no="+acno+"&part_no="+partno;
        //url ="http://117.239.180.198/BLONet_API_Dev/api/BLONet/DisableUser?UserId=9823763919&st_code=S21&ac_no=1&part_no=1";
        Log.e("url",url);
        showProgressDialog();
        JSONObject obj = new JSONObject();
        Helper.getmHelper().v("OBJ",obj.toString());
        Log.e("abc","abc");


        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, obj, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("On Response : ", response.toString());
                        try {
                            String IsSuccess = response.getString("IsSuccess");
                            String Message = response.getString("Message");
                            String IsDisable = response.getString("IsDisable");
                            Log.e("IsSuccess",IsSuccess);
                            Log.e("Message",Message);
                            Log.e("IsDisable",IsDisable);

                            Log.e("Response : ", "IsSuccess : " + IsSuccess);

                            if(IsSuccess.trim().equals("true") || IsDisable.trim().equals("false")) {

                                final Dialog dialog = new Dialog(WelcomeBLO.this);
                                dialog.setContentView(R.layout.custom_accountdeactivated_message);
                                Button btnok = (Button) dialog.findViewById(R.id.bok);
                                dialog.setCanceledOnTouchOutside(false);


                                btnok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent=new Intent(WelcomeBLO.this,LoginActivity.class);
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
                            Log.d("printStackTrace",e.getMessage());
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

// Access the RequestQueue through your singleton class.
        queue.add(jsObjRequest);





       /* Intent intent=new Intent(ChangePassword.this,LoginActivity.class);
        startActivity(intent);
        finish();*/

    }



    /*@Override
    public void onBackPressed() {
        backButtonHandler();
    }
    private void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                WelcomeBLO.this);
        // Setting Dialog Title
        alertDialog.setTitle("BLO Registration");
        // Setting Dialog Message
        alertDialog.setMessage("Do you want to EXIT from this App ?");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.mipmap.ic_launcher);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);


                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }*/

    private void showProgressDialog() {
        if (!pd.isShowing())
            pd.show();
    }

    private void hideProgressDialog() {
        if (pd.isShowing())
            pd.dismiss();
    }

}

