package mgov.gov.in.blohybrid;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static mgov.gov.in.blohybrid.Constants.buttonclr;

public class FieldVisitSearch extends AppCompatActivity {
    ProgressDialog pd;
    Button btnsearch;
    DatabaseHelper myDb;
    TextView tvhome,tvview,tvsearchByName;
    RadioButton rbmale,rbfemale,rbthirdgender;
    EditText etHeadOftheFamily,etwife,ethusbend,etsuncount,etMdaughtercount,etUdaughtercount;
    LinearLayout llmale,llfemale,llsun,lldaughterunmarried,lldaughtermaried,llsonstatic,llmdstatic,lludstatic;
    CheckBox cbsuncount,cbmalelate,cbfemalelate,cbdaughtermarriedcount,cbdaughterunmarriedcount;
    int intsuncount,intdaughterUnmarriedcount,intdaughterMarriedcount;
    String HOF_GENDER,HOF_SLNO,HOF_RLN_GENDER,HOF_RLN_SLNO,S_SLNO,DM_SLNO,DU_SLNO;

    EditText et;
    List<EditText> allEds = new ArrayList<EditText>();
    List<EditText> allEdsUD = new ArrayList<EditText>();
    List<EditText> allEdsMD = new ArrayList<EditText>();

    ArrayList<String> mylistson ;
    ArrayList<String> mylistud ;
    ArrayList<String> mylistmd ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_visit_search);


        btnsearch= (Button) findViewById(R.id.btsearch);
        tvhome = (TextView) findViewById(R.id.tvhome);
        tvview= (TextView) findViewById(R.id.tvview);
        tvsearchByName = (TextView) findViewById(R.id.tvsearchByName);
        rbmale = (RadioButton) findViewById(R.id.rbmale);
        rbfemale = (RadioButton) findViewById(R.id.rbfemale);
        rbthirdgender = (RadioButton) findViewById(R.id.rbthirdgender);
        cbsuncount = (CheckBox) findViewById(R.id.cbsuncount);
        cbdaughterunmarriedcount = (CheckBox) findViewById(R.id.cbdaughterunmarriedcount);
        cbdaughtermarriedcount = (CheckBox) findViewById(R.id.cbdaughterMarriedcount);
        etHeadOftheFamily = (EditText) findViewById(R.id.ethof);
        etwife = (EditText) findViewById(R.id.etfemaleserialnumber);
        ethusbend = (EditText) findViewById(R.id.etmaleserialnumber);
        etsuncount = (EditText) findViewById(R.id.etsuncount);
        etUdaughtercount =(EditText) findViewById(R.id.etdaughtercountU);
        etMdaughtercount = (EditText) findViewById(R.id.etdaughtercountM);
        llmale = (LinearLayout) findViewById(R.id.layoutmaleid);
        llfemale = (LinearLayout) findViewById(R.id.layoutfemaleid);
        llsonstatic = (LinearLayout) findViewById(R.id.llson);
        lludstatic = (LinearLayout) findViewById(R.id.llud);
        llmdstatic = (LinearLayout) findViewById(R.id.llmd);
        cbfemalelate = (CheckBox) findViewById(R.id.cbfemalelate);
        cbmalelate = (CheckBox) findViewById(R.id.cbmalelate);
        pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getApplicationContext().getResources().getString(R.string.Please_Wait));
        pd.setCancelable(false);
        myDb = new DatabaseHelper(this);
        //llsun = (LinearLayout) findViewById(R.id.llsun);
        buttonclr="enable";

        tvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FieldVisitSearch.this,WelcomeBLONew.class);
                startActivity(intent);
                finish();
            }
        });
        tvview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog();
                Cursor cursor = myDb.getAllDatafromTable1();
                if(cursor.getCount() == 0 ){
                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Nothing_to_show),Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while (cursor.moveToNext()){
                    stringBuffer.append("SLNOINPART : "+ cursor.getString(0)+"\n");
                    stringBuffer.append("EPIC_NO : "+ cursor.getString(1)+"\n");
                    stringBuffer.append("Name : "+ cursor.getString(2)+"\n");
                    stringBuffer.append("RLN_Name : "+ cursor.getString(3)+"\n");
                    stringBuffer.append("GENDER : "+ cursor.getString(4)+"\t \t AGE : "+ cursor.getString(5)+"\n");
                    stringBuffer.append("RLN_TYPE : "+ cursor.getString(8)+"\n");
                    stringBuffer.append("ADDRESS : "+ cursor.getString(6)+"\n\n");

                }
                hideProgressDialog();
                showmessage(getApplicationContext().getResources().getString(R.string.Data),stringBuffer.toString());
            }
        });

        tvsearchByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(FieldVisitSearch.this);
                dialog.setContentView(R.layout.custom_search_by_name);
                Button btnsearchbyname = (Button) dialog.findViewById(R.id.btnsearchbyname);
                final EditText etsearch = (EditText)dialog.findViewById(R.id.etsearch);
                dialog.setCanceledOnTouchOutside(false);

                btnsearchbyname.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = etsearch.getText().toString();
                        //name.append("%");
                        // name.append(etsearch.getText().toString());
                        //name.append("%");
                        Log.d("name ",name.toString());
                        showProgressDialog();
                        Cursor cursor = myDb.getAllSearchedDatafromTable1(name);
                        if(cursor.getCount() == 0 ){
                            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Nothing_to_show),Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            hideProgressDialog();
                            return;
                        }
                        StringBuffer stringBuffer = new StringBuffer();
                        while (cursor.moveToNext()){
                            stringBuffer.append("SLNOINPART : "+ cursor.getString(0)+"\n");
                            stringBuffer.append("EPIC_NO : "+ cursor.getString(1)+"\n");
                            stringBuffer.append("Name : "+ cursor.getString(2)+"\n");
                            stringBuffer.append("RLN_Name : "+ cursor.getString(3)+"\n");
                            stringBuffer.append("GENDER : "+ cursor.getString(4)+"\t \t AGE : "+ cursor.getString(5)+"\n");
                            stringBuffer.append("RLN_TYPE : "+ cursor.getString(6)+"\n");
                            stringBuffer.append("ADDRESS : "+ cursor.getString(7)+"\n\n");;

                        }
                        hideProgressDialog();
                        showmessage(getApplicationContext().getResources().getString(R.string.Data),stringBuffer.toString());

                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });

        rbmale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"male cheked",Toast.LENGTH_LONG).show();
                ethusbend.setText("");
                llsonstatic.setVisibility(View.VISIBLE);
                lludstatic.setVisibility(View.VISIBLE);
                llmdstatic.setVisibility(View.VISIBLE);
                if(etHeadOftheFamily.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Please_enter_Serial_number_first),Toast.LENGTH_LONG).show();
                    rbmale.setChecked(false);
                    rbthirdgender.setChecked(false);
                    llfemale.setVisibility(View.GONE);
                    llmale.setVisibility(View.GONE);
                }else{
                    rbfemale.setChecked(false);
                    rbthirdgender.setChecked(false);
                    llfemale.setVisibility(View.VISIBLE);
                    llmale.setVisibility(View.GONE);
                    HOF_GENDER = "M";
                    HOF_RLN_GENDER = "F";
                }

            }
        });


        rbfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"female cheked",Toast.LENGTH_LONG).show();
                etwife.setText("");
                llsonstatic.setVisibility(View.VISIBLE);
                lludstatic.setVisibility(View.VISIBLE);
                llmdstatic.setVisibility(View.VISIBLE);
                if(etHeadOftheFamily.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Please_enter_Serial_number_first),Toast.LENGTH_LONG).show();
                    rbfemale.setChecked(false);
                    rbthirdgender.setChecked(false);
                    llmale.setVisibility(View.GONE);
                    llfemale.setVisibility(View.GONE);
                } else{
                    rbmale.setChecked(false);
                    rbthirdgender.setChecked(false);
                    llfemale.setVisibility(View.GONE);
                    llmale.setVisibility(View.VISIBLE);
                    HOF_GENDER = "F";
                    HOF_RLN_GENDER = "M";
                }

            }
        });

        rbthirdgender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"female cheked",Toast.LENGTH_LONG).show();
                etwife.setText("");
                ethusbend.setText("");
                llsonstatic.setVisibility(View.GONE);
                lludstatic.setVisibility(View.GONE);
                llmdstatic.setVisibility(View.GONE);
                cbfemalelate.setChecked(false);
                cbmalelate.setChecked(false);
                cbsuncount.setChecked(false);
                cbdaughtermarriedcount.setChecked(false);
                cbdaughterunmarriedcount.setChecked(false);
                etsuncount.setText("");
                etUdaughtercount.setText("");
                etMdaughtercount.setText("");

                if(etHeadOftheFamily.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Please_enter_Serial_number_first),Toast.LENGTH_LONG).show();
                    rbfemale.setChecked(false);
                    rbmale.setChecked(false);
                    rbthirdgender.setChecked(false);
                    llmale.setVisibility(View.GONE);
                    llfemale.setVisibility(View.GONE);
                } else{
                    rbmale.setChecked(false);
                    rbfemale.setChecked(false);
                    llfemale.setVisibility(View.GONE);
                    llmale.setVisibility(View.GONE);

                    HOF_GENDER = "T";
                    HOF_RLN_GENDER = "";
                }

            }
        });

        cbmalelate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cbfemalelate.setChecked(false);
                if(cbmalelate.isChecked()){
                    etwife.setText("");
                    ethusbend.setText("");
                }

            }
        });
        cbfemalelate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cbmalelate.setChecked(false);
                if(cbfemalelate.isChecked()){
                    ethusbend.setText("");
                    etwife.setText("");
                }

            }
        });

        cbsuncount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout l = new LinearLayout(getApplicationContext());
                l.setOrientation(LinearLayout.VERTICAL);

                if(cbsuncount.isChecked())
                {
                    if(etsuncount.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.Enter_Son_Count), Toast.LENGTH_SHORT).show();
                        cbsuncount.setChecked(false);

                    } else {

                        /*LinearLayout l = new LinearLayout(getApplicationContext());
                        l.setOrientation(LinearLayout.VERTICAL);*/

                        intsuncount = Integer.parseInt(etsuncount.getText().toString().trim());
                        Log.d("intsuncount", String.valueOf(intsuncount));
                        //sonarray = new long[intsuncount];
                        //mylistson = new ArrayList<String>();
                        //setContentView(R.layout.activity_field_visit_search);
                        Log.d("1", "1");
                        llsun = (LinearLayout)findViewById(R.id.llsun);
                        Log.d("1", "2");
                        Display display = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
                        Log.d("1", "3");
                        int width = display.getWidth()/3;
                        Log.d("1", "4");
                        /*LinearLayout l = new LinearLayout(getApplicationContext());
                        l.setOrientation(LinearLayout.VERTICAL);*/
                        for(int i=0;i<intsuncount;i++) {
                            //LinearLayout l = new LinearLayout(getApplicationContext());
                            Log.d("1", "5");
                            //l.setOrientation(LinearLayout.VERTICAL);
                            Log.d("1", "6");
                            //EditText et = new EditText(getApplicationContext());
                            et = new EditText(getApplicationContext());
                            et.setHint("Serial Number"+(i+1));
                            et.setTextColor(Color.BLACK);
                            et.setHintTextColor(getResources().getColor(R.color.hint));
                            et.setTextSize(15);
                            //et.setId(R.id.s+i);
                            et.setInputType(InputType.TYPE_CLASS_NUMBER);
                            et.setTextColor(getResources().getColor(R.color.black));
                            allEds.add(et);
                            //int s= et.getId();
                            //mylistson.add(String.valueOf(s));
                            //Log.d("sir123", String.valueOf(s));
                            Log.d("1", "7");
                            //et.getText();
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(width, TableRow.LayoutParams.WRAP_CONTENT);
                            Log.d("1", "8");
                            lp.setMargins(20,2,2,2);
                            l.addView(et, lp);
                            Log.d("1", "9");
                        }
                        l.setVisibility(View.VISIBLE);
                        llsun.addView(l);
                        llsun.setVisibility(View.VISIBLE);
                        Log.d("1", "10");
                    }
                }
                else if(cbsuncount.isChecked()==false){
                    //Toast.makeText(getApplicationContext(),"sdf",Toast.LENGTH_LONG).show();
                    int a =llsun.getChildCount();
                    Log.d("a", String.valueOf(a));
                    llsun.removeAllViews();
                    //mylistson.clear();
                    allEds.clear();
                    //Toast.makeText(getApplicationContext(),"uncheked",Toast.LENGTH_LONG).show();
                }
            }
        });


        cbdaughterunmarriedcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout ld = new LinearLayout(getApplicationContext());
                ld.setOrientation(LinearLayout.VERTICAL);
                if(cbdaughterunmarriedcount.isChecked())
                {
                    if(etUdaughtercount.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.Enter_Dependent_Daughter_Count), Toast.LENGTH_SHORT).show();
                        cbdaughterunmarriedcount.setChecked(false);
                    } else {

                        /*LinearLayout l = new LinearLayout(getApplicationContext());
                        l.setOrientation(LinearLayout.VERTICAL);*/

                        intdaughterUnmarriedcount = Integer.parseInt(etUdaughtercount.getText().toString().trim());
                        Log.d("intUDcount", String.valueOf(intdaughterUnmarriedcount));
                        //setContentView(R.layout.activity_field_visit_search);
                        Log.d("1", "1");
                        lldaughterunmarried = (LinearLayout)findViewById(R.id.lldaughterUN);
                        Log.d("1", "2");
                        Display display = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
                        Log.d("1", "3");
                        int width = display.getWidth()/3;
                        Log.d("1", "4");
                        /*LinearLayout l = new LinearLayout(getApplicationContext());
                        l.setOrientation(LinearLayout.VERTICAL);*/
                        for(int i=0;i<intdaughterUnmarriedcount;i++) {
                            //LinearLayout l = new LinearLayout(getApplicationContext());
                            Log.d("1", "5");
                            //l.setOrientation(LinearLayout.VERTICAL);
                            Log.d("1", "6");
                            EditText et = new EditText(getApplicationContext());
                            et.setHint("Serial Number"+(i+1));
                            et.setTextColor(Color.BLACK);
                            et.setHintTextColor(getResources().getColor(R.color.hint));
                            et.setTextSize(15);
                            et.setInputType(InputType.TYPE_CLASS_NUMBER);
                            et.setTextColor(getResources().getColor(R.color.black));
                            allEdsUD.add(et);
                            Log.d("1", "7");
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(width, TableRow.LayoutParams.WRAP_CONTENT);
                            Log.d("1", "8");
                            lp.setMargins(20,2,2,2);
                            ld.addView(et, lp);
                            Log.d("1", "9");
                        }
                        ld.setVisibility(View.VISIBLE);
                        lldaughterunmarried.addView(ld);
                        lldaughterunmarried.setVisibility(View.VISIBLE);
                        Log.d("1", "10");
                    }
                }
                else if(cbdaughterunmarriedcount.isChecked()==false){
                    //Toast.makeText(getApplicationContext(),"sdf",Toast.LENGTH_LONG).show();
                    int a =lldaughterunmarried.getChildCount();
                    //Log.d("a", String.valueOf(a));
                    lldaughterunmarried.removeAllViews();
                    allEdsUD.clear();
                    //Toast.makeText(getApplicationContext(),"uncheked",Toast.LENGTH_LONG).show();
                }
            }
        });


        //=========================

        cbdaughtermarriedcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout ldm = new LinearLayout(getApplicationContext());
                ldm.setOrientation(LinearLayout.VERTICAL);
                if(cbdaughtermarriedcount.isChecked())
                {
                    if(etMdaughtercount.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.Enter_Married_Daughter_Count), Toast.LENGTH_SHORT).show();
                        cbdaughtermarriedcount.setChecked(false);
                    } else {

                        /*LinearLayout l = new LinearLayout(getApplicationContext());
                        l.setOrientation(LinearLayout.VERTICAL);*/

                        intdaughterMarriedcount = Integer.parseInt(etMdaughtercount.getText().toString().trim());
                        Log.d("intMDcount", String.valueOf(intdaughterMarriedcount));
                        //setContentView(R.layout.activity_field_visit_search);
                        Log.d("1", "1");
                        lldaughtermaried = (LinearLayout)findViewById(R.id.lldaughterM);
                        Log.d("1", "2");
                        Display display = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
                        Log.d("1", "3");
                        int width = display.getWidth()/3;
                        Log.d("1", "4");
                        /*LinearLayout l = new LinearLayout(getApplicationContext());
                        l.setOrientation(LinearLayout.VERTICAL);*/
                        for(int i=0;i<intdaughterMarriedcount;i++) {
                            //LinearLayout l = new LinearLayout(getApplicationContext());
                            Log.d("1", "5");
                            //l.setOrientation(LinearLayout.VERTICAL);
                            Log.d("1", "6");
                            EditText et = new EditText(getApplicationContext());
                            et.setHint("Serial Number"+(i+1));
                            et.setTextColor(Color.BLACK);
                            et.setHintTextColor(getResources().getColor(R.color.hint));
                            et.setTextSize(15);
                            et.setInputType(InputType.TYPE_CLASS_NUMBER);
                            et.setTextColor(getResources().getColor(R.color.black));
                            allEdsMD.add(et);
                            Log.d("1", "7");
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(width, TableRow.LayoutParams.WRAP_CONTENT);
                            Log.d("1", "8");
                            lp.setMargins(20,2,2,2);
                            ldm.addView(et, lp);
                            Log.d("1", "9");
                        }
                        ldm.setVisibility(View.VISIBLE);
                        lldaughtermaried.addView(ldm);
                        lldaughtermaried.setVisibility(View.VISIBLE);
                        Log.d("1", "10");
                    }
                }
                else if(cbdaughtermarriedcount.isChecked()==false){
                    //Toast.makeText(getApplicationContext(),"sdf",Toast.LENGTH_LONG).show();
                    int a =lldaughtermaried.getChildCount();
                    Log.d("a", String.valueOf(a));
                    lldaughtermaried.removeAllViews();
                    allEdsMD.clear();
                    //Toast.makeText(getApplicationContext(),"uncheked",Toast.LENGTH_LONG).show();
                }
            }
        });


        //=========================



        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( rbmale.isChecked() && etwife.getText().toString().isEmpty() && !(cbfemalelate.isChecked())){
                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Click_on_LATE_NA_in_case_wife_serial_number_is_deleted_or_not_available),Toast.LENGTH_LONG).show();
                    return;
                }

                if(rbfemale.isChecked() && ethusbend.getText().toString().isEmpty() && !(cbmalelate.isChecked())){
                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Click_on_LATE_NA_in_case_husband_serial_number_is_deleted_or_not_available),Toast.LENGTH_LONG).show();
                    return;
                }
                if(!(ethusbend.getText().toString().isEmpty()) || !(etwife.getText().toString().isEmpty())){
                    cbmalelate.setChecked(false);
                    cbfemalelate.setChecked(false);
                }

                StringBuffer sb = new StringBuffer();
                StringBuffer sbud = new StringBuffer();
                StringBuffer sbmd = new StringBuffer();

                StringBuffer sb1 = new StringBuffer();
                StringBuffer sbud1 = new StringBuffer();
                StringBuffer sbmd1 = new StringBuffer();

                StringBuffer sbcom = new StringBuffer(); // will contain string with comma seperated.


                if(!(etsuncount.getText().toString().isEmpty()) ) {
                    mylistson = new ArrayList<String>();
                    for (int i = 0; i < allEds.size(); i++) {
                        mylistson.add(String.valueOf(allEds.get(i).getText().toString()));
                    }

                    for (int i = 0; i < mylistson.size(); i++) {
                        if (!(mylistson.get(i).isEmpty()))
                            sb.append(" OR SLNOINPART = " + mylistson.get(i));
                        if(!(mylistson.get(i).isEmpty())){
                            sb1.append(mylistson.get(i)+",");
                        }


                    }
                    if (sb1.length() != 0 ){//&& sbud1.length() !=0) {
                        sb1.setLength(sb1.length() - 1);
                        Log.d("sb1", sb1.toString());
                    } //else Toast.makeText(getApplicationContext(),"Serial Number is empty",Toast.LENGTH_SHORT).show();
                    if (sb1.length() == 0) {
                        Log.d("StringBufferlengthzero", sb.toString());
                    }
                }

                if(!(etUdaughtercount.getText().toString().isEmpty())) {
                    mylistud = new ArrayList<String>();
                    for (int i = 0; i < allEdsUD.size(); i++) {
                        mylistud.add(String.valueOf(allEdsUD.get(i).getText().toString()));
                    }

                    for (int i = 0; i < mylistud.size(); i++) {
                        if (!(mylistud.get(i).isEmpty()))
                            sbud.append(" OR SLNOINPART = " + mylistud.get(i));
                        if(!(mylistud.get(i).isEmpty())){
                            sbud1.append(mylistud.get(i)+",");
                        }


                    }
                    if (sbud1.length() != 0){ //&& sbmd1.length() !=0) {
                        sbud1.setLength(sbud1.length() - 1);
                        Log.d("sbud1", sbud1.toString());
                    } //else Toast.makeText(getApplicationContext(),"Serial Number is empty",Toast.LENGTH_SHORT).show();

                }

                if(!(etMdaughtercount.getText().toString().isEmpty()))
                {
                    mylistmd = new ArrayList<String>();
                    for(int i=0; i < allEdsMD.size(); i++){
                        mylistmd.add(String.valueOf(allEdsMD.get(i).getText().toString()));
                    }

                    for(int i = 0; i< mylistmd.size(); i++){
                        if(!(mylistmd.get(i).isEmpty()))
                            sbmd.append(" OR SLNOINPART = " + mylistmd.get(i));
                        if(!(mylistmd.get(i).isEmpty())){
                            sbmd1.append(mylistmd.get(i)+",");
                        }


                    }
                    if(sbmd1.length()!=0){
                        sbmd1.setLength(sbmd1.length() - 1);
                        Log.d("sbmd1",sbmd1.toString());
                    } //else Toast.makeText(getApplicationContext(),"Serial Number is empty",Toast.LENGTH_SHORT).show();


                }

                StringBuffer totalStringBuffer = new StringBuffer();
                totalStringBuffer.append(etHeadOftheFamily.getText().toString());

                sbcom.append(etHeadOftheFamily.getText().toString());  //string with comma seperated
                sbcom.append(","+HOF_GENDER);

                if(!(ethusbend.getText().toString().isEmpty())){
                    totalStringBuffer.append(" OR SLNOINPART = "+ethusbend.getText().toString());
                    sbcom.append(","+ethusbend.getText().toString());
                    sbcom.append(","+HOF_RLN_GENDER);

                } if(!(etwife.getText().toString().isEmpty())){
                    totalStringBuffer.append(" OR SLNOINPART = "+etwife.getText().toString());
                    sbcom.append(","+etwife.getText().toString());
                    sbcom.append(","+HOF_RLN_GENDER);
                }
                //if(sb.length()!=0)
                if(sb !=null)
                {
                    totalStringBuffer.append(sb);
                    sbcom.append(sb1);
                }
                //if(sbud.length()!=0)
                if(sbud !=null)
                {
                    totalStringBuffer.append(sbud);
                    sbcom.append(sbud1);
                }
                //if(sbmd.length()!=0)
                if(sbmd !=null)
                {
                    totalStringBuffer.append(sbmd);
                    sbcom.append(sbmd1);
                }
                String s = String.valueOf(totalStringBuffer);
                Log.d("totalStringBuffer",totalStringBuffer.toString());
                Log.d("sbcom",sbcom.toString());
                Log.d("s",s);
                if(!(etHeadOftheFamily.getText().toString().isEmpty()))
                {
                    if(rbthirdgender.isChecked()){
                        Log.d("HOF_GENDER",HOF_GENDER);
                        HOF_SLNO = etHeadOftheFamily.getText().toString();
                        Log.d("HOF_SLNO",HOF_SLNO);
                        HOF_RLN_SLNO = "";
                        HOF_RLN_GENDER = "";
                        Log.d("HOF_RLN_SLNO",HOF_RLN_SLNO);
                        Log.d("HOF_RLN_GENDER",HOF_RLN_GENDER);
                        S_SLNO = "";
                        Log.d("S_SLNO",S_SLNO);
                        DU_SLNO = "";
                        Log.d("DU_SLNO",DU_SLNO);
                        DM_SLNO = "";
                        Log.d("DM_SLNO",DM_SLNO);
                        getterSetter.gs.setHOF_GENDER(HOF_GENDER);
                        getterSetter.gs.setHOF_SLNO(HOF_SLNO);
                        getterSetter.gs.setHOF_RLN_GENDER(HOF_RLN_GENDER);
                        getterSetter.gs.setHOF_RLN_SLNO(HOF_RLN_SLNO);
                        getterSetter.gs.setS_SLNO(S_SLNO);
                        getterSetter.gs.setDM_SLNO(DM_SLNO);
                        getterSetter.gs.setDU_SLNO(DU_SLNO);
                        getterSetter.gs.setSTRINGBUFFER(totalStringBuffer.toString());

                        getterSetter.gs.setSTRINGBUFFERCOM(sbcom.toString());


                        String HOF_GENDER1 = getterSetter.gs.getHOF_GENDER();
                        String HOF_SLNO1 = getterSetter.gs.getHOF_SLNO();
                        String HOF_RLN_GENDER1 = getterSetter.gs.getHOF_RLN_GENDER();
                        String HOF_RLN_SLNO1 = getterSetter.gs.getHOF_RLN_SLNO();
                        String S_SLNO1 = getterSetter.gs.getS_SLNO();
                        String DM_SLNO1 = getterSetter.gs.getDM_SLNO();
                        String DU_SLNO1 = getterSetter.gs.getDU_SLNO();
                        String TOTAl_STRING_BUFFER = getterSetter.gs.getSTRINGBUFFER();
                        if(sb1.length() == 0){
                            sb1.append("");
                        }
                        if(sbud1.length() == 0){
                            sbud1.append("");
                        }
                        if(sbmd1.length() == 0){
                            sbmd1.append("");
                        }
                        Log.d("getterSetter1",HOF_GENDER1+" "+HOF_SLNO1+" "+HOF_RLN_GENDER1+" "+HOF_RLN_SLNO1+" "+S_SLNO1+" "+DM_SLNO1+" "+DU_SLNO1+" "+TOTAl_STRING_BUFFER);
                        Log.e("type3 ",HOF_GENDER1+" "+HOF_SLNO1+" "+HOF_RLN_GENDER1+" "+HOF_RLN_SLNO1+" "+sb1.toString()+" "+sbud1.toString()+" "+sbmd1.toString() );

                        Intent intent = new Intent(getApplicationContext(), VoterListFamily.class);
                                    Bundle mBundle = new Bundle();
                                    mBundle.putString("HOF_GENDER", HOF_GENDER);
                                    mBundle.putString("HOF_SLNO", HOF_SLNO);
                                    mBundle.putString("HOF_RLN_GENDER", HOF_RLN_GENDER);
                                    mBundle.putString("HOF_RLN_SLNO", HOF_RLN_SLNO);
                                    mBundle.putString("S_SLNO", sb1.toString());
                                    mBundle.putString("DM_SLNO", sbmd1.toString());
                                    mBundle.putString("DU_SLNO", sbud1.toString());
                                    intent.putExtras(mBundle);
                        startActivity(intent);
                        finish();
                    }
                    if(rbmale.isChecked() || rbfemale.isChecked() || rbthirdgender.isChecked()){
                        if(rbmale.isChecked()){
                            if((!(etwife.getText().toString().isEmpty()) && !(cbfemalelate.isChecked())) ||
                                    (etwife.getText().toString().isEmpty() && cbfemalelate.isChecked()))
                            {
                                Log.d("HOF_GENDER",HOF_GENDER);
                                HOF_SLNO = etHeadOftheFamily.getText().toString();
                                Log.d("HOF_SLNO",HOF_SLNO);
                                if(ethusbend.getText().toString().isEmpty() && etwife.getText().toString().isEmpty()){
                                    HOF_RLN_SLNO = "";
                                    HOF_RLN_GENDER = "";
                                    Log.d("HOF_RLN_SLNO",HOF_RLN_SLNO);
                                    Log.d("HOF_RLN_GENDER",HOF_RLN_GENDER);
                                } else if(!(ethusbend.getText().toString().isEmpty())){
                                    HOF_RLN_SLNO = ethusbend.getText().toString();
                                    Log.d("HOF_RLN_GENDER",HOF_RLN_GENDER);
                                    Log.d("HOF_RLN_SLNO",HOF_RLN_SLNO);
                                } else  if(!(etwife.getText().toString().isEmpty()))
                                {
                                    HOF_RLN_SLNO = etwife.getText().toString();
                                    Log.d("HOF_RLN_GENDER",HOF_RLN_GENDER);
                                    Log.d("HOF_RLN_SLNO",HOF_RLN_SLNO);
                                }
                                // if(ethusbend.getText().toString().isEmpty())
                                if(sb.length()!=0){
                                    S_SLNO = sb.toString();
                                    Log.d("S_SLNO",S_SLNO);
                                } else {S_SLNO = ""; Log.d("S_SLNO",S_SLNO);}

                                if(sbud.length()!=0) { DU_SLNO = sbud.toString(); Log.d("DU_SLNO",DU_SLNO);}
                                else { DU_SLNO = ""; Log.d("DU_SLNO",DU_SLNO);}

                                if(sbmd.length()!=0) { DM_SLNO = sbmd.toString(); Log.d("DM_SLNO",DM_SLNO);}
                                else { DM_SLNO = ""; Log.d("DM_SLNO",DM_SLNO);}

                                getterSetter.gs.setHOF_GENDER(HOF_GENDER);
                                getterSetter.gs.setHOF_SLNO(HOF_SLNO);
                                getterSetter.gs.setHOF_RLN_GENDER(HOF_RLN_GENDER);
                                getterSetter.gs.setHOF_RLN_SLNO(HOF_RLN_SLNO);
                                getterSetter.gs.setS_SLNO(S_SLNO);
                                getterSetter.gs.setDM_SLNO(DM_SLNO);
                                getterSetter.gs.setDU_SLNO(DU_SLNO);
                                getterSetter.gs.setSTRINGBUFFER(totalStringBuffer.toString());
                                getterSetter.gs.setSTRINGBUFFERCOM(sbcom.toString());


                                String HOF_GENDER1 = getterSetter.gs.getHOF_GENDER();
                                String HOF_SLNO1 = getterSetter.gs.getHOF_SLNO();
                                String HOF_RLN_GENDER1 = getterSetter.gs.getHOF_RLN_GENDER();
                                String HOF_RLN_SLNO1 = getterSetter.gs.getHOF_RLN_SLNO();
                                String S_SLNO1 = getterSetter.gs.getS_SLNO();
                                String DM_SLNO1 = getterSetter.gs.getDM_SLNO();
                                String DU_SLNO1 = getterSetter.gs.getDU_SLNO();
                                String TOTAL_STRING_BUFFER = getterSetter.gs.getSTRINGBUFFER();
                                if(sb1.length() == 0){
                                    sb1.append("");
                                }
                                if(sbud1.length() == 0){
                                    sbud1.append("");
                                }
                                if(sbmd1.length() == 0){
                                    sbmd1.append("");
                                }
                                Log.d("getterSetter1",HOF_GENDER1+" "+HOF_SLNO1+" "+HOF_RLN_GENDER1+" "+HOF_RLN_SLNO1+" "+S_SLNO1+" "+DM_SLNO1+" "+DU_SLNO1+" "+totalStringBuffer);
                                Log.e("type1 ",HOF_GENDER1+" "+HOF_SLNO1+" "+HOF_RLN_GENDER1+" "+HOF_RLN_SLNO1+" "+sb1.toString()+" "+sbud1.toString()+" "+sbmd1.toString() );

                                Intent intent = new Intent(getApplicationContext(), VoterListFamily.class);
                                Bundle mBundle = new Bundle();
                                mBundle.putString("HOF_GENDER", HOF_GENDER);
                                mBundle.putString("HOF_SLNO", HOF_SLNO);
                                mBundle.putString("HOF_RLN_GENDER", HOF_RLN_GENDER);
                                mBundle.putString("HOF_RLN_SLNO", HOF_RLN_SLNO);
                                mBundle.putString("S_SLNO", sb1.toString());
                                mBundle.putString("DM_SLNO", sbmd1.toString());
                                mBundle.putString("DU_SLNO", sbud1.toString());
                                intent.putExtras(mBundle);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Either_Click_on_LATE_NA_or_mention_Wife_Serial_number),Toast.LENGTH_LONG).show();
                            }
                        }
                        if(rbfemale.isChecked()){
                            if((!(ethusbend.getText().toString().isEmpty()) && !(cbmalelate.isChecked())) ||
                                    (ethusbend.getText().toString().isEmpty() && cbmalelate.isChecked())){
                                Log.d("HOF_GENDER",HOF_GENDER);
                                HOF_SLNO = etHeadOftheFamily.getText().toString();
                                Log.d("HOF_SLNO",HOF_SLNO);
                                if(etwife.getText().toString().isEmpty() && ethusbend.getText().toString().isEmpty()){
                                    HOF_RLN_SLNO = "";
                                    HOF_RLN_GENDER = "";
                                    Log.d("HOF_RLN_SLNO",HOF_RLN_SLNO);
                                    Log.d("HOF_RLN_GENDER",HOF_RLN_GENDER);
                                    ;                         } else if(!(etwife.getText().toString().isEmpty())){
                                    HOF_RLN_SLNO = etwife.getText().toString();
                                    Log.d("HOF_RLN_GENDER",HOF_RLN_GENDER);
                                    Log.d("HOF_RLN_SLNO",HOF_RLN_SLNO);
                                } else  if(!(ethusbend.getText().toString().isEmpty()))
                                {
                                    HOF_RLN_SLNO = ethusbend.getText().toString();
                                    Log.d("HOF_RLN_GENDER",HOF_RLN_GENDER);
                                    Log.d("HOF_RLN_SLNO",HOF_RLN_SLNO);
                                }
                                //if(etwife.getText().toString().isEmpty())      //=========
                                if(sb.length()!=0){
                                    S_SLNO = sb.toString();
                                    Log.d("S_SLNO",S_SLNO);
                                } else {S_SLNO = ""; Log.d("S_SLNO",S_SLNO);}

                                if(sbud.length()!=0) { DU_SLNO = sbud.toString(); Log.d("DU_SLNO",DU_SLNO);}
                                else { DU_SLNO = ""; Log.d("DU_SLNO",DU_SLNO);}

                                if(sbmd.length()!=0) { DM_SLNO = sbmd.toString(); Log.d("DM_SLNO",DM_SLNO);}
                                else { DM_SLNO = ""; Log.d("DM_SLNO",DM_SLNO);}

                                getterSetter.gs.setHOF_GENDER(HOF_GENDER);
                                getterSetter.gs.setHOF_SLNO(HOF_SLNO);
                                getterSetter.gs.setHOF_RLN_GENDER(HOF_RLN_GENDER);
                                getterSetter.gs.setHOF_RLN_SLNO(HOF_RLN_SLNO);
                                getterSetter.gs.setS_SLNO(S_SLNO);
                                getterSetter.gs.setDM_SLNO(DM_SLNO);
                                getterSetter.gs.setDU_SLNO(DU_SLNO);
                                getterSetter.gs.setSTRINGBUFFER(totalStringBuffer.toString());
                                getterSetter.gs.setSTRINGBUFFERCOM(sbcom.toString());

                                String HOF_GENDER1 = getterSetter.gs.getHOF_GENDER();
                                String HOF_SLNO1 = getterSetter.gs.getHOF_SLNO();
                                String HOF_RLN_GENDER1 = getterSetter.gs.getHOF_RLN_GENDER();
                                String HOF_RLN_SLNO1 = getterSetter.gs.getHOF_RLN_SLNO();
                                String S_SLNO1 = getterSetter.gs.getS_SLNO();
                                String DM_SLNO1 = getterSetter.gs.getDM_SLNO();
                                String DU_SLNO1 = getterSetter.gs.getDU_SLNO();
                                String TOTAL_STRING_BUFFER = getterSetter.gs.getSTRINGBUFFER();
                                if(sb1.length() == 0){
                                    sb1.append("");
                                }
                                if(sbud1.length() == 0){
                                    sbud1.append("");
                                }
                                if(sbmd1.length() == 0){
                                    sbmd1.append("");
                                }

                                    /*getterSetter obj1 = new getterSetter();
                                    obj1.setHOF_GENDER(HOF_GENDER);
                                    obj1.setHOF_SLNO(HOF_SLNO);
                                    obj1.setHOF_RLN_GENDER(HOF_RLN_GENDER);
                                    obj1.setHOF_RLN_SLNO(HOF_RLN_SLNO);
                                    obj1.setS_SLNO(S_SLNO);
                                    obj1.setDM_SLNO(DM_SLNO);
                                    obj1.setDU_SLNO(DU_SLNO);*/
                                Log.d("SetterSetter1",HOF_GENDER1+" "+HOF_SLNO1+" "+HOF_RLN_GENDER1+" "+HOF_RLN_SLNO1+" "+S_SLNO1+" "+DM_SLNO1+" "+DU_SLNO1+" "+totalStringBuffer);
                                Log.d("Setter1",HOF_GENDER+" "+HOF_SLNO+" "+HOF_RLN_GENDER+" "+HOF_RLN_SLNO+" "+S_SLNO+" "+DM_SLNO+" "+DU_SLNO+" "+totalStringBuffer);
                                Log.e("type2 ",HOF_GENDER1+" "+HOF_SLNO1+" "+HOF_RLN_GENDER1+" "+HOF_RLN_SLNO1+" "+sb1.toString()+" "+sbud1.toString()+" "+sbmd1.toString() );
                                Intent intent = new Intent(getApplicationContext(), VoterListFamily.class);
                                Bundle mBundle = new Bundle();
                                mBundle.putString("HOF_GENDER", HOF_GENDER);
                                mBundle.putString("HOF_SLNO", HOF_SLNO);
                                mBundle.putString("HOF_RLN_GENDER", HOF_RLN_GENDER);
                                mBundle.putString("HOF_RLN_SLNO", HOF_RLN_SLNO);
                                mBundle.putString("S_SLNO", sb1.toString());
                                mBundle.putString("DM_SLNO", sbmd1.toString());
                                mBundle.putString("DU_SLNO", sbud1.toString());
                                intent.putExtras(mBundle);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Either_Click_on_LATE_NA_or_mention_Husband_Serial_number),Toast.LENGTH_LONG).show();
                            }
                        }
                    } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Mention_Head_of_the_family_gender),Toast.LENGTH_SHORT).show();

                } else Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Head_of_the_family_is_mandatory),Toast.LENGTH_SHORT).show();


                    /*if(sb.length()!=0 && sbud.length()!=0 && sbmd.length()!=0)
                    {

                        totalStringBuffer.append(sb);
                        totalStringBuffer.append(","+sbud);
                        totalStringBuffer.append(","+sbmd);
                        String s = String.valueOf(totalStringBuffer);
                        Log.d("totalStringBuffer",totalStringBuffer.toString());
                        Log.d("s",s);
                        Intent intent = new Intent(getApplicationContext(),VoterListFamily.class);
                        startActivity(intent);
                        finish();


                    }*/ /*if(sb.length()==0 || sbud.length()==0 || sbmd.length()==0){
                    Toast.makeText(getApplicationContext(),"Fill all detail correctly",Toast.LENGTH_SHORT).show();*/
                //}



            }
        });

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

}
