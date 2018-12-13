package mgov.gov.in.blohybrid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class NoNetwork extends AppCompatActivity {
    Button retryButton,settingsbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_network);


        retryButton=(Button)findViewById(R.id.retryButton);
        settingsbutton=(Button)findViewById(R.id.settingButton);

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnectionStatus.isNetworkConnected(NoNetwork.this)) {
                    finish();
                }
            }

        });


        settingsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(dialogIntent);
            }
        });

    }
    public void onBackPressed() {

    }


}
