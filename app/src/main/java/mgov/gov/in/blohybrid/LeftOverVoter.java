package mgov.gov.in.blohybrid;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LeftOverVoter extends AppCompatActivity {
    TextView tvhome;
    EditText etform6;
    Button btnsubmitform6;
    TextView tvform6error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_over_voter);

        tvhome = (TextView) findViewById(R.id.tvhome);
        etform6 = (EditText)findViewById(R.id.etform6);
        btnsubmitform6 = (Button) findViewById(R.id.btn_submitform6);
        tvform6error =  (TextView) findViewById(R.id.tvform6error);

        tvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LeftOverVoter.this,WelcomeBLONew.class);
                startActivity(intent);
                finish();
            }
        });

        btnsubmitform6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ConnectionStatus.isNetworkConnected(LeftOverVoter.this))
                {
                    tvform6error.setText("");
                    tvform6error.setVisibility(View.GONE);
                    if(etform6.getText().toString().trim().isEmpty()) {
                        tvform6error.setText(getApplicationContext().getResources().getString(R.string.Please_enter_Form_Reference_number));
                        Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.ERROR_Please_enter_Form_Reference_number), Toast.LENGTH_SHORT).show();
                        tvform6error.setVisibility(View.VISIBLE);
                    }else{
                        tvform6error.setText("");
                        tvform6error.setVisibility(View.GONE);
                        final Dialog dialog = new Dialog(LeftOverVoter.this);
                        dialog.setContentView(R.layout.custom_blo_report_done_message);
                        Button btnok = (Button) dialog.findViewById(R.id.btnok);
                        dialog.setCanceledOnTouchOutside(false);


                        btnok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(LeftOverVoter.this,WelcomeBLO.class);
                                startActivity(intent);
                                finish();
                                dialog.dismiss();

                            }
                        });
                        dialog.show();
                    }

                }
                else
                {
                    Intent intent=new Intent(getApplicationContext(),NoNetwork.class);
                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });


    }

}
