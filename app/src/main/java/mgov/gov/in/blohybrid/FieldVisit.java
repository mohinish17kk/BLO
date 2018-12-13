package mgov.gov.in.blohybrid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class FieldVisit extends AppCompatActivity {
    ListView listView;
    ArrayList<HashMap<String, String>> dataAL;
    String[] name ={ "Mohinish Uzzwal", "Manish Uzzwal", "Sweta Bhilare", "Ritu Chauhan", "Vijay Singh", "Laxman Kumar", "Vibha Singh", "Ratnesh Yadav" };
    String[] epic ={ "KSJD764887", "ASDS799345", "HFND580963", "JSEN895974", "BDHT649807", "NVQL923785", "MNGH074254", "NDFR976845" };
    String[] gender ={ "Male", "Male", "Female", "Female", "Male", "Other", "Female", "Male" };
    String[] serial ={ "239847", "237684", "89269", "238764", "2389894", "2378423", "2378423", "2783423" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_visit);

        dataAL = new ArrayList<>();
        listView = (ListView) findViewById(R.id.voterListView);

        //===========TO DO ==============================



    }

}
