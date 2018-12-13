package mgov.gov.in.blohybrid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import mgov.gov.in.blohybrid.Constants;

public class LogicalErrorActivity extends AppCompatActivity {

    TextView tvhome;
    ProgressDialog pd;
    RequestQueue queue;
    String url,mob,stcode,acno,partno,bloname,ErrorType,SharedKey,IP_HEADER;
    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs" ;
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12,btn13,btn14,btn15,btn16,btn17,btn18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logical_error);
        tvhome = (TextView) findViewById(R.id.tvhome);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btn10 = (Button) findViewById(R.id.btn10);
        btn11 = (Button) findViewById(R.id.btn11);
        btn12 = (Button) findViewById(R.id.btn12);
        btn13 = (Button) findViewById(R.id.btn13);
        btn14 = (Button) findViewById(R.id.btn14);
        btn15 = (Button) findViewById(R.id.btn15);
        btn16 = (Button) findViewById(R.id.btn16);
        btn17 = (Button) findViewById(R.id.btn17);
        btn18 = (Button) findViewById(R.id.btn18);

        prefs = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mob  = prefs.getString("mob","");
        stcode = prefs.getString("st_code","");
        acno = prefs.getString("AcNo","");
        partno = prefs.getString("PartNo","");
        bloname = prefs.getString("BLO_NAME","");
        SharedKey = prefs.getString("Key","");
        Log.d("SharedKey",SharedKey);
        IP_HEADER = Constants.IP_HEADER;


        pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getApplicationContext().getResources().getString(R.string.Please_Wait));
        pd.setCancelable(false);
        queue = Volley.newRequestQueue(this);

        if(ConnectionStatus.isNetworkConnected(LogicalErrorActivity.this)){
            Log.d("mob123",mob);
            //url ="http://eronet.ecinet.in/services/api/blonet/GetBloDetails?st_code="+stcode+"&mobile_no="+mob;
            url= IP_HEADER+"Get_LogicalErrorReport?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno+"&mobile_no="+mob;
            Log.e("url",url);
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
                                if(response.length()!=0){
                                    //String StateCode = response.getString("StateCode");
                                    String ET01 = response.getString("ET01");
                                    String ET02 = response.getString("ET02");
                                    String ET03 = response.getString("ET03");
                                    String ET04 = response.getString("ET04");
                                    String ET05 = response.getString("ET05");
                                    String ET06 = response.getString("ET06");
                                    String ET07 = response.getString("ET07");
                                    String ET08 = response.getString("ET08");
                                    String ET09 = response.getString("ET09");
                                    String ET10 = response.getString("ET10");
                                    String ET11 = response.getString("ET11");
                                    String ET12 = response.getString("ET12");
                                    String ET13 = response.getString("ET13");
                                    String ET14 = response.getString("ET14");
                                    String ET15 = response.getString("ET15");
                                    String ET16 = response.getString("ET16");
                                    String ET17 = response.getString("ET17");
                                    String ET18 = response.getString("ET18");

                                    btn1.setText(ET01);
                                    btn2.setText(ET02);
                                    btn3.setText(ET03);
                                    btn4.setText(ET04);
                                    btn5.setText(ET05);
                                    btn6.setText(ET06);
                                    btn7.setText(ET07);
                                    btn8.setText(ET08);
                                    btn9.setText(ET09);
                                    btn10.setText(ET10);
                                    btn11.setText(ET11);
                                    btn12.setText(ET12);
                                    btn13.setText(ET13);
                                    btn14.setText(ET14);
                                    btn15.setText(ET15);
                                    btn16.setText(ET16);
                                    btn17.setText(ET17);
                                    btn18.setText(ET18);


                                }


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

// Access the RequestQueue through your singleton class.
            queue.add(jsObjRequest);



        } else {
            Intent intent=new Intent(getApplicationContext(),NoNetwork.class);
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.No_Internet_connection), Toast.LENGTH_LONG).show();
        }



        tvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogicalErrorActivity.this, WelcomeBLONew.class);
                startActivity(intent);
                finish();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Logical_Error_Type_1),Toast.LENGTH_SHORT).show();
                if((Integer.parseInt(btn1.getText().toString())>0)){
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("error_type", "1");
                    editor.commit();
                    Log.d("error_type", "1");
                    Intent intent = new Intent(LogicalErrorActivity.this, LogicalErrorListFamily.class);
                    startActivity(intent);
                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_Data_To_Display),Toast.LENGTH_SHORT).show();

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Logical_Error_Type_2),Toast.LENGTH_SHORT).show();
                if((Integer.parseInt(btn2.getText().toString())>0)){
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("error_type", "2");
                    editor.commit();
                    Log.d("error_type", "2");
                    Intent intent = new Intent(LogicalErrorActivity.this, LogicalErrorListFamily.class);
                    startActivity(intent);
                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_Data_To_Display),Toast.LENGTH_SHORT).show();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Logical_Error_Type_3),Toast.LENGTH_SHORT).show();
                if((Integer.parseInt(btn3.getText().toString())>0)){
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("error_type", "3");
                    editor.commit();
                    Log.d("error_type", "3");
                    Intent intent = new Intent(LogicalErrorActivity.this, LogicalErrorListFamily.class);
                    startActivity(intent);
                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_Data_To_Display),Toast.LENGTH_SHORT).show();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Logical_Error_Type_4),Toast.LENGTH_SHORT).show();
                if((Integer.parseInt(btn4.getText().toString())>0)){
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("error_type", "4");
                    editor.commit();
                    Log.d("error_type", "4");
                    Intent intent = new Intent(LogicalErrorActivity.this, LogicalErrorListFamily.class);
                    startActivity(intent);
                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_Data_To_Display),Toast.LENGTH_SHORT).show();
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Logical_Error_Type_5),Toast.LENGTH_SHORT).show();
                if((Integer.parseInt(btn5.getText().toString())>0)){
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("error_type", "5");
                    editor.commit();
                    Log.d("error_type", "5");
                    Intent intent = new Intent(LogicalErrorActivity.this, LogicalErrorListFamily.class);
                    startActivity(intent);
                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_Data_To_Display),Toast.LENGTH_SHORT).show();
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Logical_Error_Type_6),Toast.LENGTH_SHORT).show();
                if((Integer.parseInt(btn6.getText().toString())>0)){
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("error_type", "6");
                    editor.commit();
                    Log.d("error_type", "6");
                    Intent intent = new Intent(LogicalErrorActivity.this, LogicalErrorListFamily.class);
                    startActivity(intent);
                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_Data_To_Display),Toast.LENGTH_SHORT).show();
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Logical_Error_Type_7),Toast.LENGTH_SHORT).show();
                if((Integer.parseInt(btn7.getText().toString())>0)){
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("error_type", "7");
                    editor.commit();
                    Log.d("error_type", "7");
                    Intent intent = new Intent(LogicalErrorActivity.this, LogicalErrorListFamily.class);
                    startActivity(intent);
                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_Data_To_Display),Toast.LENGTH_SHORT).show();
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Logical_Error_Type_8),Toast.LENGTH_SHORT).show();
                if((Integer.parseInt(btn8.getText().toString())>0)){
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("error_type", "8");
                    editor.commit();
                    Log.d("error_type", "8");
                    Intent intent = new Intent(LogicalErrorActivity.this, LogicalErrorListFamily.class);
                    startActivity(intent);
                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_Data_To_Display),Toast.LENGTH_SHORT).show();
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Logical_Error_Type_9),Toast.LENGTH_SHORT).show();
                if((Integer.parseInt(btn9.getText().toString())>0)){
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("error_type", "9");
                    editor.commit();
                    Log.d("error_type", "9");
                    Intent intent = new Intent(LogicalErrorActivity.this, LogicalErrorListFamily.class);
                    startActivity(intent);
                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_Data_To_Display),Toast.LENGTH_SHORT).show();
            }
        });

        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Logical_Error_Type_10),Toast.LENGTH_SHORT).show();
                if((Integer.parseInt(btn10.getText().toString())>0)){
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("error_type", "10");
                    editor.commit();
                    Log.d("error_type", "10");
                    Intent intent = new Intent(LogicalErrorActivity.this, LogicalErrorListFamily.class);
                    startActivity(intent);
                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_Data_To_Display),Toast.LENGTH_SHORT).show();
            }
        });

        btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Logical_Error_Type_11),Toast.LENGTH_SHORT).show();
                if((Integer.parseInt(btn11.getText().toString())>0)){
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("error_type", "11");
                    editor.commit();
                    Log.d("error_type", "11");
                    Intent intent = new Intent(LogicalErrorActivity.this, LogicalErrorListFamily.class);
                    startActivity(intent);
                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_Data_To_Display),Toast.LENGTH_SHORT).show();
            }
        });

        btn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Logical_Error_Type_12),Toast.LENGTH_SHORT).show();
                if((Integer.parseInt(btn12.getText().toString())>0)){
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("error_type", "12");
                    editor.commit();
                    Log.d("error_type", "12");
                    Intent intent = new Intent(LogicalErrorActivity.this, LogicalErrorListFamily.class);
                    startActivity(intent);
                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_Data_To_Display),Toast.LENGTH_SHORT).show();
            }
        });

        btn13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Logical_Error_Type_13),Toast.LENGTH_SHORT).show();
                if((Integer.parseInt(btn13.getText().toString())>0)){
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("error_type", "13");
                    editor.commit();
                    Log.d("error_type", "13");
                    Intent intent = new Intent(LogicalErrorActivity.this, LogicalErrorListFamily.class);
                    startActivity(intent);
                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_Data_To_Display),Toast.LENGTH_SHORT).show();
            }
        });

        btn14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Logical_Error_Type_14),Toast.LENGTH_SHORT).show();
                if((Integer.parseInt(btn14.getText().toString())>0)){
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("error_type", "14");
                    editor.commit();
                    Log.d("error_type", "14");
                    Intent intent = new Intent(LogicalErrorActivity.this, LogicalErrorListFamily.class);
                    startActivity(intent);
                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_Data_To_Display),Toast.LENGTH_SHORT).show();
            }
        });

        btn15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Logical_Error_Type_15),Toast.LENGTH_SHORT).show();
                if((Integer.parseInt(btn15.getText().toString())>0)){
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("error_type", "15");
                    editor.commit();
                    Log.d("error_type","15");
                    Intent intent = new Intent(LogicalErrorActivity.this, LogicalErrorListFamily.class);
                    startActivity(intent);
                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_Data_To_Display),Toast.LENGTH_SHORT).show();
            }
        });

        btn16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Logical_Error_Type_16),Toast.LENGTH_SHORT).show();
                if((Integer.parseInt(btn16.getText().toString())>0)){
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("error_type", "16");
                    editor.commit();
                    Log.d("error_type", "16");
                    Intent intent = new Intent(LogicalErrorActivity.this, LogicalErrorListFamily.class);
                    startActivity(intent);
                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_Data_To_Display),Toast.LENGTH_SHORT).show();
            }
        });

        btn17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Logical_Error_Type_17),Toast.LENGTH_SHORT).show();
                if((Integer.parseInt(btn17.getText().toString())>0)){
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("error_type", "17");
                    editor.commit();
                    Log.d("error_type", "17");
                    Intent intent = new Intent(LogicalErrorActivity.this, LogicalErrorListFamily.class);
                    startActivity(intent);
                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_Data_To_Display),Toast.LENGTH_SHORT).show();
            }
        });

        btn18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Logical_Error_Type_18),Toast.LENGTH_SHORT).show();
                if((Integer.parseInt(btn18.getText().toString())>0)){
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("error_type", "18");
                    editor.commit();
                    Log.d("error_type", "18");
                    Intent intent = new Intent(LogicalErrorActivity.this, LogicalErrorListFamily.class);
                    startActivity(intent);
                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.No_Data_To_Display),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgressDialog() {
        if (!pd.isShowing())
            pd.show();
    }

    private void hideProgressDialog() {
        if (pd.isShowing())
            pd.dismiss();
    }

}
