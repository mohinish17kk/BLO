package mgov.gov.in.blohybrid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class BloRegisterActivity extends AppCompatActivity {


    TextView option10fill,tvhome,textView0,tv_format1,tv_format2,tv_format3,tv_format4,tv_format5,tv_format6,tv_format7,tv_format8,tv_format4A,tv_format10,tv_format11,tv_format12;
    public static final String MyPREFERENCES = "MyPrefs" ;
    String bloname,StatementType;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blo_register);
        tvhome = (TextView) findViewById(R.id.tvhome);

        textView0 = (TextView) findViewById(R.id.textView0);
        tv_format1 = (TextView) findViewById(R.id.option1);
        tv_format2 = (TextView) findViewById(R.id.option2);
        tv_format3 = (TextView) findViewById(R.id.option3);
        tv_format4 = (TextView) findViewById(R.id.option4);
        tv_format5 = (TextView) findViewById(R.id.option5);
        tv_format6 = (TextView) findViewById(R.id.option6);
        tv_format7 = (TextView) findViewById(R.id.option7);
        tv_format8 = (TextView) findViewById(R.id.option8);
        tv_format4A = (TextView) findViewById(R.id.option4a);
        tv_format10 = (TextView) findViewById(R.id.option10);
        tv_format11 = (TextView) findViewById(R.id.option11);
        tv_format12 = (TextView) findViewById(R.id.option12);
        option10fill = (TextView) findViewById(R.id.option10fill);

        prefs = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        bloname = prefs.getString("BLO_NAME","");
        textView0.setText(getApplicationContext().getResources().getString(R.string.Welcome)+" "+bloname);



        tvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BloRegisterActivity.this, WelcomeBLONew.class);
                startActivity(intent);
                finish();
            }
        });

        tv_format1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatementType = "Get_StatementOne";
                Intent intent = new Intent(getApplicationContext(), BLOStatements.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("StatementType", StatementType);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });
        tv_format2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatementType = "Get_StatementTwo";
                Intent intent = new Intent(getApplicationContext(), BLOStatements.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("StatementType", StatementType);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });
        tv_format3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatementType = "Get_StatementThree";
                Intent intent = new Intent(getApplicationContext(), BLOStatements.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("StatementType", StatementType);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });
        tv_format4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatementType = "Get_StatementFour";
                Intent intent = new Intent(getApplicationContext(), BLOStatements.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("StatementType", StatementType);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });
        tv_format5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatementType = "Get_StatementFive";
                Intent intent = new Intent(getApplicationContext(), BLOStatements.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("StatementType", StatementType);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });
        tv_format6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatementType = "Get_StatementSix";
                Intent intent = new Intent(getApplicationContext(), BLOStatements.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("StatementType", StatementType);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });
        tv_format7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatementType = "Get_StatementSeven";
                Intent intent = new Intent(getApplicationContext(), BLOStatements.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("StatementType", StatementType);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });
        tv_format8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatementType = "Get_StatementEight";
                Intent intent = new Intent(getApplicationContext(), BLOStatements.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("StatementType", StatementType);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });
        tv_format4A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatementType = "Get_StatementFourA";
                Intent intent = new Intent(getApplicationContext(), BLOStatements.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("StatementType", StatementType);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });
        tv_format10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatementType = "Get_StatementTen";
                Intent intent = new Intent(getApplicationContext(), BLOStatements.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("StatementType", StatementType);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });
        tv_format11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatementType = "Get_StatementEleven";
                Intent intent = new Intent(getApplicationContext(), BLOStatements.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("StatementType", StatementType);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });
        tv_format12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatementType = "Get_StatementTwelve";
                Intent intent = new Intent(getApplicationContext(), BLOStatements.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("StatementType", StatementType);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });

        option10fill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Statement10FillDetail.class);
                startActivity(intent);
            }
        });

    }

}
