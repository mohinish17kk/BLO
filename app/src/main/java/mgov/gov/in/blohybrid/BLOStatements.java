package mgov.gov.in.blohybrid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import mgov.gov.in.blohybrid.Constants;

public class BLOStatements extends AppCompatActivity {
    TextView tvstatemnts,tvhome,tvdetail;
    ListView listView;
    RequestQueue queue;
    ProgressDialog pd;//
    ArrayList<HashMap<String, String>> dataAL;
    SharedPreferences prefs;
    String mob,stcode,acno,partno,url,StatementType,SharedKey,IP_HEADER;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blostatements);

        tvhome = (TextView) findViewById(R.id.tvhome);
        tvstatemnts = (TextView) findViewById(R.id.tvstatemnts);
        tvdetail = (TextView) findViewById(R.id.tvdetail);
        prefs = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mob  = prefs.getString("mob","");
        stcode = prefs.getString("st_code","");
        acno = prefs.getString("AcNo","");
        partno = prefs.getString("PartNo","");
        SharedKey = prefs.getString("Key","");
        Log.d("SharedKey",SharedKey);
        IP_HEADER = Constants.IP_HEADER;

        StatementType = getIntent().getExtras().getString("StatementType");
        Log.d("StatementType",StatementType);

        if(StatementType.equals("Get_StatementOne") || StatementType == "Get_StatementOne"){
            tvstatemnts.setText(getApplicationContext().getResources().getString(R.string.Statement_1));
            tvdetail.setText(getApplicationContext().getResources().getString(R.string.Statement_detail1));
        }
        else if(StatementType.equals("Get_StatementTwo") || StatementType == "Get_StatementTwo"){
            tvstatemnts.setText(getApplicationContext().getResources().getString(R.string.Statement_2));
            tvdetail.setText(getApplicationContext().getResources().getString(R.string.Statement_detail2));
        }
        else if(StatementType.equals("Get_StatementThree") || StatementType == "Get_StatementThree"){
            tvstatemnts.setText(getApplicationContext().getResources().getString(R.string.Statement_3));
            tvdetail.setText(getApplicationContext().getResources().getString(R.string.Statement_detail3));
        }
        else if(StatementType.equals("Get_StatementFour") || StatementType == "Get_StatementFour"){
            tvstatemnts.setText(getApplicationContext().getResources().getString(R.string.Statement_4));
            tvdetail.setText(getApplicationContext().getResources().getString(R.string.Statement_detail4));
        }
        else if(StatementType.equals("Get_StatementFourA") || StatementType == "Get_StatementFourA"){
            tvstatemnts.setText(getApplicationContext().getResources().getString(R.string.Statement_4A));
            tvdetail.setText(getApplicationContext().getResources().getString(R.string.Statement_detail4A));
        }
        else if(StatementType.equals("Get_StatementFive") || StatementType == "Get_StatementFive"){
            tvstatemnts.setText(getApplicationContext().getResources().getString(R.string.Statement_5));
            tvdetail.setText(getApplicationContext().getResources().getString(R.string.Statement_detail5));
        }
        else if(StatementType.equals("Get_StatementSix") || StatementType == "Get_StatementSix"){
            tvstatemnts.setText(getApplicationContext().getResources().getString(R.string.Statement_6));
            tvdetail.setText(getApplicationContext().getResources().getString(R.string.Statement_detail6));
        }
        else if(StatementType.equals("Get_StatementSeven") || StatementType == "Get_StatementSeven"){
            tvstatemnts.setText(getApplicationContext().getResources().getString(R.string.Statement_7));
            tvdetail.setText(getApplicationContext().getResources().getString(R.string.Statement_detail7));
        }
        else if(StatementType.equals("Get_StatementEight") || StatementType == "Get_StatementEight"){
            tvstatemnts.setText(getApplicationContext().getResources().getString(R.string.Statement_8));
            tvdetail.setText(getApplicationContext().getResources().getString(R.string.Statement_detail8));
        }
        else if(StatementType.equals("Get_StatementTen") || StatementType == "Get_StatementTen"){
            tvstatemnts.setText(getApplicationContext().getResources().getString(R.string.Statement_10));
            tvdetail.setText(getApplicationContext().getResources().getString(R.string.Statement_detail10));
        }
        else if(StatementType.equals("Get_StatementEleven") || StatementType == "Get_StatementEleven"){
            tvstatemnts.setText(getApplicationContext().getResources().getString(R.string.Statement_11));
            tvdetail.setText(getApplicationContext().getResources().getString(R.string.Statement_detail11));
        }
        else if(StatementType.equals("Get_StatementTwelve") || StatementType == "Get_StatementTwelve"){
            tvstatemnts.setText(getApplicationContext().getResources().getString(R.string.Statement_12));
            tvdetail.setText(getApplicationContext().getResources().getString(R.string.Statement_detail12));
        }

        //url = "http://eronet.ecinet.in/services/api/BLONet/"+StatementType+"?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno+"&mobile_no="+mob;
        url = IP_HEADER+StatementType+"?st_code="+stcode+"&ac_no="+acno+"&part_no="+partno+"&mobile_no="+mob;

        Log.d("mobedf",mob);
        Log.d("st_code",stcode);
        Log.d("AcNo",acno);
        Log.d("PartNo",partno);
        Log.d("url",url);
        queue = Volley.newRequestQueue(this);
        pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getApplicationContext().getResources().getString(R.string.Please_Wait));
        pd.setCancelable(false);

        listView = (ListView) findViewById(R.id.StatementListView);
        dataAL = new ArrayList<>();

        tvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),WelcomeBLONew.class);
                startActivity(intent);
                finish();
            }
        });

        showProgressDialog();
        if(ConnectionStatus.isNetworkConnected(BLOStatements.this)) {
            new RequestTask1().execute(url);
        } else {
            Intent intent=new Intent(getApplicationContext(),NoNetwork.class);
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

    }

    class RequestTask1 extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            try {
                HttpGet httpGet = new HttpGet(url);

                httpGet.addHeader("Content-Type" , "application/json");
                httpGet.addHeader("access_token" , SharedKey);
                Log.e("access_token" , SharedKey);
                response = httpclient.execute(httpGet);
                //response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    responseString = out.toString();
                    out.close();
                } else {
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {

            Log.d("On Response : ", result.toString());
            try {
                JSONArray jArray = new JSONArray(result);
                Log.v("response",result.toString());
                Log.v("jArray.length()", String.valueOf(jArray.length()));
                if(StatementType.equals("Get_StatementOne") || StatementType == "Get_StatementOne"){

                    for(int i=0; i < jArray.length(); i++) {

                        JSONObject jsreceipt = jArray.getJSONObject(i);

                        String LastRevisionFinalRollPublicationDate = jsreceipt.getString("LastRevisionFinalRollPublicationDate");
                        String TotalVotersInPart = jsreceipt.getString("TotalVotersInPart");
                        String TotalPopulationOfPart = jsreceipt.getString("TotalPopulationOfPart");
                        String TotalMaleVoters = jsreceipt.getString("TotalMaleVoters");
                        String TotalFemaleVoters = jsreceipt.getString("TotalFemaleVoters");
                        String GenderRatio = jsreceipt.getString("GenderRatio");
                        String GenderPopulationRatio = jsreceipt.getString("GenderPopulationRatio");
                        String AgeCohort_18to20 = jsreceipt.getString("AgeCohort_18to20");
                        String AgeCohort_21to29 = jsreceipt.getString("AgeCohort_21to29");
                        String AgeCohort_30to35 = jsreceipt.getString("AgeCohort_30to35");

                        HashMap<String, String> data = new HashMap<>();
                        if(!LastRevisionFinalRollPublicationDate.equals("null") || LastRevisionFinalRollPublicationDate!="null"){
                            data.put("LastRevisionFinalRollPublicationDate", LastRevisionFinalRollPublicationDate);
                        } else data.put("LastRevisionFinalRollPublicationDate", "--");

                        if(!TotalVotersInPart.equals("null") || TotalVotersInPart!="null"){
                            data.put("TotalVotersInPart", TotalVotersInPart);
                        } else data.put("TotalVotersInPart", "--");
                        //data.put("TotalVotersInPart", TotalVotersInPart);
                        if(!TotalPopulationOfPart.equals("null") || TotalPopulationOfPart!="null"){
                            data.put("TotalPopulationOfPart", TotalPopulationOfPart);
                        } else data.put("TotalPopulationOfPart", "--");
                        //data.put("TotalPopulationOfPart", TotalPopulationOfPart);
                        if(!TotalMaleVoters.equals("null") || TotalMaleVoters!="null"){
                            data.put("TotalMaleVoters", TotalMaleVoters);
                        } else data.put("TotalMaleVoters", "--");
                        //data.put("TotalMaleVoters", TotalMaleVoters);

                        if(!TotalFemaleVoters.equals("null") || TotalFemaleVoters!="null"){
                            data.put("TotalFemaleVoters", TotalFemaleVoters);
                        } else data.put("TotalFemaleVoters", "--");
                        //data.put("TotalFemaleVoters",TotalFemaleVoters);
                        if(!GenderRatio.equals("null") || GenderRatio!="null"){
                            data.put("GenderRatio", GenderRatio);
                        } else data.put("GenderRatio", "--");
                       // data.put("GenderRatio", GenderRatio);
                        if(!GenderPopulationRatio.equals("null") || GenderPopulationRatio!="null"){
                            data.put("GenderPopulationRatio", GenderPopulationRatio);
                        } else data.put("GenderPopulationRatio", "--");
                        //data.put("GenderPopulationRatio", GenderPopulationRatio);
                        if(!AgeCohort_18to20.equals("null") || AgeCohort_18to20!="null"){
                            data.put("AgeCohort_18to20", AgeCohort_18to20);
                        } else data.put("AgeCohort_18to20", "--");
                        //data.put("AgeCohort_18to20", AgeCohort_18to20);
                        if(!AgeCohort_21to29.equals("null") || AgeCohort_21to29!="null"){
                            data.put("AgeCohort_21to29", AgeCohort_21to29);
                        } else data.put("AgeCohort_21to29", "--");
                        //data.put("AgeCohort_21to29", AgeCohort_21to29);
                        if(!AgeCohort_30to35.equals("null") || AgeCohort_30to35!="null"){
                            data.put("AgeCohort_30to35", AgeCohort_30to35);
                        } else data.put("AgeCohort_30to35", "--");
                        //data.put("AgeCohort_30to35", AgeCohort_30to35);

                        //data.put("TAG_U", urgent);
                        dataAL.add(data);

                        ListAdapter la = new SimpleAdapter(getApplicationContext(), dataAL, R.layout.custom_statement_one_listview_item,
                                new String[]{"LastRevisionFinalRollPublicationDate", "TotalVotersInPart", "TotalPopulationOfPart", "TotalMaleVoters","TotalFemaleVoters",
                                "GenderRatio", "GenderPopulationRatio", "AgeCohort_18to20", "AgeCohort_21to29", "AgeCohort_30to35"},
                                new int[]{R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5, R.id.tv6, R.id.tv7, R.id.tv8, R.id.tv9,R.id.tv10});
                        listView.setAdapter(la);
                    }

                } else if(StatementType.equals("Get_StatementTwo") || StatementType == "Get_StatementTwo"){

                    for(int i=0; i < jArray.length(); i++) {

                        JSONObject jsreceipt = jArray.getJSONObject(i);

                        String HouseNo = jsreceipt.getString("HouseNo");
                        String VoterName = jsreceipt.getString("VoterName");
                        String HOFName = jsreceipt.getString("HOFName");
                        String HOFRelation = jsreceipt.getString("HOFRelation");
                        String ContactNo = jsreceipt.getString("ContactNo");
                        String IsRegisteredAsVoterInPart = jsreceipt.getString("IsRegisteredAsVoterInPart");
                        String PartWhereRegistered = jsreceipt.getString("PartWhereRegistered");
                        String IsAnyMemberEligibleForRegisterInForm6ForCurrentRevision = jsreceipt.getString("IsAnyMemberEligibleForRegisterInForm6ForCurrentRevision");
                        String IsAnyMemberEligibleForRegisterInForm6ForNextRevision = jsreceipt.getString("IsAnyMemberEligibleForRegisterInForm6ForNextRevision");
                        String IsEPICIssued = jsreceipt.getString("IsEPICIssued");
                        String IsEPICAvailable = jsreceipt.getString("IsEPICAvailable");
                        String EPICNo = jsreceipt.getString("EPICNo");
                        String IsAddressCorrect = jsreceipt.getString("IsAddressCorrect");
                        String CorrectAddress = jsreceipt.getString("CorrectAddress");
                        String DurationOfLivingInYears = jsreceipt.getString("DurationOfLivingInYears");
                        String IsAnyFamilyMemberMarriedOrShiftedSinceLastRevision = jsreceipt.getString("IsAnyFamilyMemberMarriedOrShiftedSinceLastRevision");
                        String IsAnyFamilyMemberDeadSinceLastRevision = jsreceipt.getString("IsAnyFamilyMemberDeadSinceLastRevision");
                        String IsAnyPossibilityOfTemporaryShiftingInNextOneYear = jsreceipt.getString("IsAnyPossibilityOfTemporaryShiftingInNextOneYear");
                        String IsPhotoAvailableInElectoralRoll = jsreceipt.getString("IsPhotoAvailableInElectoralRoll");
                        String IsAllVotersOfHouseHoldShifted = jsreceipt.getString("IsAllVotersOfHouseHoldShifted");

                        HashMap<String, String> data = new HashMap<>();
                        if(!HouseNo.equals("null") || HouseNo!="null"){
                            data.put("HouseNo", HouseNo);
                        } else data.put("HouseNo", "--");
                        //data.put("HouseNo", HouseNo);
                        if(!VoterName.equals("null") || VoterName!="null"){
                            data.put("VoterName", VoterName);
                        } else data.put("VoterName", "--");
                        //data.put("VoterName", VoterName);
                        if(!HOFName.equals("null") || HOFName!="null"){
                            data.put("HOFName", HOFName);
                        } else data.put("HOFName", "--");
                        //data.put("HOFName", HOFName);
                        if(!HOFRelation.equals("null") || HOFRelation!="null"){
                            data.put("HOFRelation", HOFRelation);
                        } else data.put("HOFRelation", "--");
                        //data.put("HOFRelation", HOFRelation);
                        if(!ContactNo.equals("null") || ContactNo!="null"){
                            data.put("ContactNo", ContactNo);
                        } else data.put("AgeCohort_30to35", "--");
                        //data.put("ContactNo",ContactNo);
                        if(!IsRegisteredAsVoterInPart.equals("null") || IsRegisteredAsVoterInPart!="null"){
                            data.put("IsRegisteredAsVoterInPart", IsRegisteredAsVoterInPart);
                        } else data.put("IsRegisteredAsVoterInPart", "--");
                       // data.put("IsRegisteredAsVoterInPart", IsRegisteredAsVoterInPart);
                        if(!PartWhereRegistered.equals("null") || PartWhereRegistered!="null"){
                            data.put("PartWhereRegistered", PartWhereRegistered);
                        } else data.put("PartWhereRegistered", "--");
                        //data.put("PartWhereRegistered", PartWhereRegistered);
                        if(!IsAnyMemberEligibleForRegisterInForm6ForCurrentRevision.equals("null") || IsAnyMemberEligibleForRegisterInForm6ForCurrentRevision!="null"){
                            data.put("IsAnyMemberEligibleForRegisterInForm6ForCurrentRevision", IsAnyMemberEligibleForRegisterInForm6ForCurrentRevision);
                        } else data.put("IsAnyMemberEligibleForRegisterInForm6ForCurrentRevision", "--");
                        //data.put("IsAnyMemberEligibleForRegisterInForm6ForCurrentRevision", IsAnyMemberEligibleForRegisterInForm6ForCurrentRevision);
                        if(!IsAnyMemberEligibleForRegisterInForm6ForNextRevision.equals("null") || IsAnyMemberEligibleForRegisterInForm6ForNextRevision!="null"){
                            data.put("IsAnyMemberEligibleForRegisterInForm6ForNextRevision", IsAnyMemberEligibleForRegisterInForm6ForNextRevision);
                        } else data.put("IsAnyMemberEligibleForRegisterInForm6ForNextRevision", "--");
                       // data.put("IsAnyMemberEligibleForRegisterInForm6ForNextRevision", IsAnyMemberEligibleForRegisterInForm6ForNextRevision);
                        if(!IsEPICIssued.equals("null") || IsEPICIssued!="null"){
                            data.put("IsEPICIssued", IsEPICIssued);
                        } else data.put("IsEPICIssued", "--");
                        //data.put("IsEPICIssued", IsEPICIssued);
                        if(!IsEPICAvailable.equals("null") || IsEPICAvailable!="null"){
                            data.put("IsEPICAvailable", IsEPICAvailable);
                        } else data.put("IsEPICAvailable", "--");
                        //data.put("IsEPICAvailable", IsEPICAvailable);
                        if(!EPICNo.equals("null") || EPICNo!="null"){
                            data.put("EPICNo", EPICNo);
                        } else data.put("EPICNo", "--");
                       // data.put("EPICNo", EPICNo);
                        if(!IsAddressCorrect.equals("null") || IsAddressCorrect!="null"){
                            data.put("IsAddressCorrect", IsAddressCorrect);
                        } else data.put("IsAddressCorrect", "--");
                        //data.put("IsAddressCorrect", IsAddressCorrect);
                        if(!CorrectAddress.equals("null") || CorrectAddress!="null"){
                            data.put("CorrectAddress", CorrectAddress);
                        } else data.put("CorrectAddress", "--");
                        //data.put("CorrectAddress", CorrectAddress);
                        if(!DurationOfLivingInYears.equals("null") || DurationOfLivingInYears!="null"){
                            data.put("DurationOfLivingInYears", DurationOfLivingInYears);
                        } else data.put("DurationOfLivingInYears", "--");
                        //data.put("DurationOfLivingInYears",DurationOfLivingInYears);
                        if(!IsAnyFamilyMemberMarriedOrShiftedSinceLastRevision.equals("null") || IsAnyFamilyMemberMarriedOrShiftedSinceLastRevision!="null"){
                            data.put("IsAnyFamilyMemberMarriedOrShiftedSinceLastRevision", IsAnyFamilyMemberMarriedOrShiftedSinceLastRevision);
                        } else data.put("IsAnyFamilyMemberMarriedOrShiftedSinceLastRevision", "--");
                       // data.put("IsAnyFamilyMemberMarriedOrShiftedSinceLastRevision", IsAnyFamilyMemberMarriedOrShiftedSinceLastRevision);
                        if(!IsAnyFamilyMemberDeadSinceLastRevision.equals("null") || IsAnyFamilyMemberDeadSinceLastRevision!="null"){
                            data.put("IsAnyFamilyMemberDeadSinceLastRevision", IsAnyFamilyMemberDeadSinceLastRevision);
                        } else data.put("IsAnyFamilyMemberDeadSinceLastRevision", "--");
                       // data.put("IsAnyFamilyMemberDeadSinceLastRevision", IsAnyFamilyMemberDeadSinceLastRevision);
                        if(!IsAnyPossibilityOfTemporaryShiftingInNextOneYear.equals("null") || IsAnyPossibilityOfTemporaryShiftingInNextOneYear!="null"){
                            data.put("IsAnyPossibilityOfTemporaryShiftingInNextOneYear", IsAnyPossibilityOfTemporaryShiftingInNextOneYear);
                        } else data.put("IsAnyPossibilityOfTemporaryShiftingInNextOneYear", "--");
                        //data.put("IsAnyPossibilityOfTemporaryShiftingInNextOneYear", IsAnyPossibilityOfTemporaryShiftingInNextOneYear);
                        if(!IsPhotoAvailableInElectoralRoll.equals("null") || IsPhotoAvailableInElectoralRoll!="null"){
                            data.put("IsPhotoAvailableInElectoralRoll", IsPhotoAvailableInElectoralRoll);
                        } else data.put("IsPhotoAvailableInElectoralRoll", "--");
                        //data.put("IsPhotoAvailableInElectoralRoll", IsPhotoAvailableInElectoralRoll);
                        if(!IsAllVotersOfHouseHoldShifted.equals("null") || IsAllVotersOfHouseHoldShifted!="null"){
                            data.put("IsAllVotersOfHouseHoldShifted", IsAllVotersOfHouseHoldShifted);
                        } else data.put("IsAllVotersOfHouseHoldShifted", "--");
                        //data.put("IsAllVotersOfHouseHoldShifted", IsAllVotersOfHouseHoldShifted);

                        //data.put("TAG_U", urgent);
                        dataAL.add(data);

                        ListAdapter la = new SimpleAdapter(getApplicationContext(), dataAL, R.layout.custom_statement_two_listview_item,
                                new String[]{"HouseNo", "VoterName", "HOFName", "HOFRelation","ContactNo","IsRegisteredAsVoterInPart",
                                        "PartWhereRegistered", "IsAnyMemberEligibleForRegisterInForm6ForCurrentRevision", "IsAnyMemberEligibleForRegisterInForm6ForNextRevision",
                                        "IsEPICIssued", "IsEPICAvailable","EPICNo","IsAddressCorrect","CorrectAddress","DurationOfLivingInYears",
                                "IsAnyFamilyMemberMarriedOrShiftedSinceLastRevision","IsAnyFamilyMemberDeadSinceLastRevision","IsAnyPossibilityOfTemporaryShiftingInNextOneYear",
                                "IsPhotoAvailableInElectoralRoll","IsAllVotersOfHouseHoldShifted"},
                                new int[]{R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5, R.id.tv6, R.id.tv7, R.id.tv8, R.id.tv9,R.id.tv10,R.id.tv11,R.id.tv12,R.id.tv13,R.id.tv14,R.id.tv15
                                        ,R.id.tv16,R.id.tv17,R.id.tv18,R.id.tv19,R.id.tv20});
                        listView.setAdapter(la);
                    }

                } else if(StatementType.equals("Get_StatementThree") || StatementType == "Get_StatementThree"){

                    for(int i=0; i < jArray.length(); i++) {

                        JSONObject jsreceipt = jArray.getJSONObject(i);

                        String HouseNo = jsreceipt.getString("HouseNo");
                        String VoterName = jsreceipt.getString("VoterName");
                        String HOFName = jsreceipt.getString("HOFName");
                        String HOFRelation = jsreceipt.getString("HOFRelation");
                        String IsEPICIssued = jsreceipt.getString("IsEPICIssued");
                        String EPICNo = jsreceipt.getString("EPICNo");
                        String TypeOfShifting = jsreceipt.getString("TypeOfShifting");
                        String PurposeOfShifting = jsreceipt.getString("PurposeOfShifting");
                        String PlaceOfShifting = jsreceipt.getString("PlaceOfShifting");
                        String ShiftedFromMonth = jsreceipt.getString("ShiftedFromMonth");
                        String ShiftedTillMonth = jsreceipt.getString("ShiftedTillMonth");
                        String ShiftedSinceMonth = jsreceipt.getString("ShiftedSinceMonth");
                        String ShiftedSinceYear = jsreceipt.getString("ShiftedSinceYear");

                        HashMap<String, String> data = new HashMap<>();
                        if(!HouseNo.equals("null") || HouseNo!="null"){
                            data.put("HouseNo", HouseNo);
                        } else data.put("HouseNo", "--");
                        //data.put("HouseNo", HouseNo);
                        if(!VoterName.equals("null") || VoterName!="null"){
                            data.put("VoterName", VoterName);
                        } else data.put("VoterName", "--");
                        //data.put("VoterName", VoterName);
                        if(!HOFName.equals("null") || HOFName!="null"){
                            data.put("HOFName", HOFName);
                        } else data.put("HOFName", "--");
                        //data.put("HOFName", HOFName);
                        if(!HOFRelation.equals("null") || HOFRelation!="null"){
                            data.put("HOFRelation", HOFRelation);
                        } else data.put("HOFRelation", "--");
                        //data.put("HOFRelation", HOFRelation);
                        if(!IsEPICIssued.equals("null") || IsEPICIssued!="null"){
                            data.put("IsEPICIssued", IsEPICIssued);
                        } else data.put("IsEPICIssued", "--");
                        //data.put("IsEPICIssued ",IsEPICIssued);
                        if(!EPICNo.equals("null") || EPICNo!="null"){
                            data.put("EPICNo", EPICNo);
                        } else data.put("EPICNo", "--");
                        //data.put("EPICNo", EPICNo);
                        if(!TypeOfShifting.equals("null") || TypeOfShifting!="null"){
                            data.put("TypeOfShifting", TypeOfShifting);
                        } else data.put("TypeOfShifting", "--");
                        //data.put("TypeOfShifting", TypeOfShifting);
                        if(!PurposeOfShifting.equals("null") || PurposeOfShifting!="null"){
                            data.put("PurposeOfShifting", PurposeOfShifting);
                        } else data.put("PurposeOfShifting", "--");
                        //data.put("PurposeOfShifting", PurposeOfShifting);
                        if(!PlaceOfShifting.equals("null") || PlaceOfShifting!="null"){
                            data.put("PlaceOfShifting", PlaceOfShifting);
                        } else data.put("PlaceOfShifting", "--");
                       // data.put("PlaceOfShifting", PlaceOfShifting);
                        if(!ShiftedFromMonth.equals("null") || ShiftedFromMonth!="null"){
                            data.put("ShiftedFromMonth", ShiftedFromMonth);
                        } else data.put("ShiftedFromMonth", "--");
                        //data.put("ShiftedFromMonth", ShiftedFromMonth);
                        if(!ShiftedTillMonth.equals("null") || ShiftedTillMonth!="null"){
                            data.put("ShiftedTillMonth", ShiftedTillMonth);
                        } else data.put("ShiftedTillMonth", "--");
                        //data.put("ShiftedTillMonth", ShiftedTillMonth);
                        if(!ShiftedSinceMonth.equals("null") || ShiftedSinceMonth!="null"){
                            data.put("ShiftedSinceMonth", ShiftedSinceMonth);
                        } else data.put("ShiftedSinceMonth", "--");
                        //data.put("ShiftedSinceMonth", ShiftedSinceMonth);
                        if(!ShiftedSinceYear.equals("null") || ShiftedSinceYear!="null"){
                            data.put("ShiftedSinceYear", ShiftedSinceYear);
                        } else data.put("ShiftedSinceYear", "--");
                        //data.put("ShiftedSinceYear", ShiftedSinceYear);


                        //data.put("TAG_U", urgent);
                        dataAL.add(data);

                        ListAdapter la = new SimpleAdapter(getApplicationContext(), dataAL, R.layout.custom_statement_three_listview_item,
                                new String[]{"HouseNo", "VoterName", "HOFName", "HOFRelation","IsEPICIssued","EPICNo","TypeOfShifting",
                                        "PurposeOfShifting", "PlaceOfShifting", "ShiftedFromMonth", "ShiftedTillMonth", "ShiftedSinceMonth","ShiftedSinceYear"},
                                new int[]{R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5, R.id.tv6, R.id.tv7, R.id.tv8, R.id.tv9,R.id.tv10,R.id.tv11,R.id.tv12,R.id.tv13});
                        listView.setAdapter(la);
                    }

                } else if(StatementType.equals("Get_StatementFour") || StatementType == "Get_StatementFour"){

                    for(int i=0; i < jArray.length(); i++) {

                        JSONObject jsreceipt = jArray.getJSONObject(i);

                        String HouseNo = jsreceipt.getString("HouseNo");
                        String VoterName = jsreceipt.getString("VoterName");
                        String HOFName = jsreceipt.getString("HOFName");
                        String HOFRelation = jsreceipt.getString("HOFRelation");
                        String IsEPICIssued = jsreceipt.getString("IsEPICIssued");
                        String EPICNo = jsreceipt.getString("EPICNo");
                        String IsShiftedWithinSamePart = jsreceipt.getString("IsShiftedWithinSamePart");
                        String IsShiftedFromAnotherPart = jsreceipt.getString("IsShiftedFromAnotherPart");
                        String FromPartNo = jsreceipt.getString("FromPartNo");
                        String ShiftedSinceMonth = jsreceipt.getString("ShiftedSinceMonth");
                        String ShiftedSinceYear = jsreceipt.getString("ShiftedSinceYear");
                        String IsFreshVoter = jsreceipt.getString("IsFreshVoter");
                        String IsCompletedFormNo6Received = jsreceipt.getString("IsCompletedFormNo6Received");

                        HashMap<String, String> data = new HashMap<>();
                        if(!HouseNo.equals("null") || HouseNo!="null"){
                            data.put("HouseNo", HouseNo);
                        } else data.put("HouseNo", "--");
                        //data.put("HouseNo", HouseNo);
                        if(!VoterName.equals("null") || VoterName!="null"){
                            data.put("VoterName", VoterName);
                        } else data.put("VoterName", "--");
                        //data.put("VoterName", VoterName);
                        if(!HOFName.equals("null") || HOFName!="null"){
                            data.put("HOFName", HOFName);
                        } else data.put("HOFName", "--");
                        //data.put("HOFName", HOFName);
                        if(!HOFRelation.equals("null") || HOFRelation!="null"){
                            data.put("HOFRelation", HOFRelation);
                        } else data.put("HOFRelation", "--");
                        //data.put("HOFRelation", HOFRelation);
                        if(!IsEPICIssued.equals("null") || IsEPICIssued!="null"){
                            data.put("IsEPICIssued", IsEPICIssued);
                        } else data.put("IsEPICIssued", "--");
                        //data.put("IsEPICIssued ",IsEPICIssued);
                        if(!EPICNo.equals("null") || EPICNo!="null"){
                            data.put("EPICNo", EPICNo);
                        } else data.put("EPICNo", "--");
                       // data.put("EPICNo", EPICNo);
                        if(!IsShiftedWithinSamePart.equals("null") || IsShiftedWithinSamePart!="null"){
                            data.put("IsShiftedWithinSamePart", IsShiftedWithinSamePart);
                        } else data.put("IsShiftedWithinSamePart", "--");
                       // data.put("IsShiftedWithinSamePart", IsShiftedWithinSamePart);
                        if(!IsShiftedFromAnotherPart.equals("null") || IsShiftedFromAnotherPart!="null"){
                            data.put("IsShiftedFromAnotherPart", IsShiftedFromAnotherPart);
                        } else data.put("IsShiftedFromAnotherPart", "--");
                        //data.put("IsShiftedFromAnotherPart", IsShiftedFromAnotherPart);
                        if(!FromPartNo.equals("null") || FromPartNo!="null"){
                            data.put("FromPartNo", FromPartNo);
                        } else data.put("FromPartNo", "--");
                        //data.put("FromPartNo", FromPartNo);
                        if(!ShiftedSinceMonth.equals("null") || ShiftedSinceMonth!="null"){
                            data.put("ShiftedSinceMonth", ShiftedSinceMonth);
                        } else data.put("ShiftedSinceMonth", "--");
                        //data.put("ShiftedSinceMonth", ShiftedSinceMonth);
                        if(!ShiftedSinceYear.equals("null") || ShiftedSinceYear!="null"){
                            data.put("ShiftedSinceYear", ShiftedSinceYear);
                        } else data.put("ShiftedSinceYear", "--");
                        //data.put("ShiftedSinceYear", ShiftedSinceYear);
                        if(!IsFreshVoter.equals("null") || IsFreshVoter!="null"){
                            data.put("IsFreshVoter", IsFreshVoter);
                        } else data.put("IsFreshVoter", "--");
                       // data.put("IsFreshVoter", IsFreshVoter);
                        if(!IsCompletedFormNo6Received.equals("null") || IsCompletedFormNo6Received!="null"){
                            data.put("IsCompletedFormNo6Received", IsCompletedFormNo6Received);
                        } else data.put("IsCompletedFormNo6Received", "--");
                       // data.put("IsCompletedFormNo6Received", IsCompletedFormNo6Received);


                        //data.put("TAG_U", urgent);
                        dataAL.add(data);

                        ListAdapter la = new SimpleAdapter(getApplicationContext(), dataAL, R.layout.custom_statement_four_listview_item,
                                new String[]{"HouseNo", "VoterName", "HOFName", "HOFRelation","IsEPICIssued","EPICNo","IsShiftedWithinSamePart",
                                        "IsShiftedFromAnotherPart", "FromPartNo", "ShiftedSinceMonth", "ShiftedSinceYear", "IsFreshVoter","IsCompletedFormNo6Received"},
                                new int[]{R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5, R.id.tv6, R.id.tv7, R.id.tv8, R.id.tv9,R.id.tv10,R.id.tv11,R.id.tv12,R.id.tv13});
                        listView.setAdapter(la);
                    }

                } else if(StatementType.equals("Get_StatementFive") || StatementType == "Get_StatementFive"){

                    for(int i=0; i < jArray.length(); i++) {

                        JSONObject jsreceipt = jArray.getJSONObject(i);

                        String HouseNo = jsreceipt.getString("HouseNo");
                        String TotalPersonsLivingInHouse = jsreceipt.getString("TotalPersonsLivingInHouse");

                        HashMap<String, String> data = new HashMap<>();
                        if(!HouseNo.equals("null") || HouseNo!="null"){
                            data.put("HouseNo", HouseNo);
                        } else data.put("HouseNo", "--");
                        //data.put("HouseNo", HouseNo);
                        if(!TotalPersonsLivingInHouse.equals("null") || TotalPersonsLivingInHouse!="null"){
                            data.put("TotalPersonsLivingInHouse", TotalPersonsLivingInHouse);
                        } else data.put("TotalPersonsLivingInHouse", "--");
                        //data.put("TotalPersonsLivingInHouse", TotalPersonsLivingInHouse);

                        //data.put("TAG_U", urgent);
                        dataAL.add(data);

                        ListAdapter la = new SimpleAdapter(getApplicationContext(), dataAL, R.layout.custom_statement_five_listview_item,
                                new String[]{"HouseNo", "TotalPersonsLivingInHouse"},
                                new int[]{R.id.tv1, R.id.tv2});
                        listView.setAdapter(la);
                    }

                } else if(StatementType.equals("Get_StatementSix") || StatementType == "Get_StatementSix"){

                    for(int i=0; i < jArray.length(); i++) {

                        JSONObject jsreceipt = jArray.getJSONObject(i);

                        String HouseNo = jsreceipt.getString("HouseNo");
                        String VoterName = jsreceipt.getString("VoterName");
                        String HOFName = jsreceipt.getString("HOFName");
                        String EPICNo = jsreceipt.getString("EPICNo");
                        String DateOfDeath = jsreceipt.getString("DateOfDeath");
                        String IsDeathCertificateAvailable = jsreceipt.getString("IsDeathCertificateAvailable");
                        String IsListedInDeathRegisterOfMunicipalCorporation = jsreceipt.getString("IsListedInDeathRegisterOfMunicipalCorporation");
                        String DeathCertificateNumber = jsreceipt.getString("DeathCertificateNumber");
                        String DateofIssueofDeathCert = jsreceipt.getString("DateofIssueofDeathCert");

                        HashMap<String, String> data = new HashMap<>();
                        if(!HouseNo.equals("null") || HouseNo!="null"){
                            data.put("HouseNo", HouseNo);
                        } else data.put("HouseNo", "--");
                        //data.put("HouseNo", HouseNo);
                        if(!VoterName.equals("null") || VoterName!="null"){
                            data.put("VoterName", VoterName);
                        } else data.put("VoterName", "--");
                        //data.put("VoterName", VoterName);
                        if(!HOFName.equals("null") || HOFName!="null"){
                            data.put("HOFName", HOFName);
                        } else data.put("HOFName", "--");
                       // data.put("HOFName", HOFName);
                        if(!EPICNo.equals("null") || EPICNo!="null"){
                            data.put("EPICNo", EPICNo);
                        } else data.put("EPICNo", "--");
                        //data.put("EPICNo", EPICNo);
                        if(!DateOfDeath.equals("null") || DateOfDeath!="null"){
                            data.put("DateOfDeath", DateOfDeath);
                        } else data.put("DateOfDeath", "--");
                       // data.put("DateOfDeath",DateOfDeath);
                        if(!IsDeathCertificateAvailable.equals("null") || IsDeathCertificateAvailable!="null"){
                            data.put("IsDeathCertificateAvailable", IsDeathCertificateAvailable);
                        } else data.put("IsDeathCertificateAvailable", "--");
                        //data.put("IsDeathCertificateAvailable", IsDeathCertificateAvailable);
                        if(!IsListedInDeathRegisterOfMunicipalCorporation.equals("null") || IsListedInDeathRegisterOfMunicipalCorporation!="null"){
                            data.put("IsListedInDeathRegisterOfMunicipalCorporation", IsListedInDeathRegisterOfMunicipalCorporation);
                        } else data.put("IsListedInDeathRegisterOfMunicipalCorporation", "--");
                        //data.put("IsListedInDeathRegisterOfMunicipalCorporation",IsListedInDeathRegisterOfMunicipalCorporation);
                        if(!DeathCertificateNumber.equals("null") || DeathCertificateNumber!="null"){
                            data.put("DeathCertificateNumber", DeathCertificateNumber);
                        } else data.put("DeathCertificateNumber", "--");
                        //data.put("DeathCertificateNumber", DeathCertificateNumber);
                        if(!DateofIssueofDeathCert.equals("null") || DateofIssueofDeathCert!="null"){
                            data.put("DateofIssueofDeathCert", DateofIssueofDeathCert);
                        } else data.put("DateofIssueofDeathCert", "--");
                        //data.put("DateofIssueofDeathCert",DateofIssueofDeathCert);


                        //data.put("TAG_U", urgent);
                        dataAL.add(data);

                        ListAdapter la = new SimpleAdapter(getApplicationContext(), dataAL, R.layout.custom_statement_six_listview_item,
                                new String[]{"HouseNo", "VoterName", "HOFName", "EPICNo","DateOfDeath","IsDeathCertificateAvailable","IsListedInDeathRegisterOfMunicipalCorporation",
                                "DeathCertificateNumber","DateofIssueofDeathCert"},
                                new int[]{R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5, R.id.tv6, R.id.tv7, R.id.tv8, R.id.tv9});
                        listView.setAdapter(la);
                    }

                } else if(StatementType.equals("Get_StatementSeven") || StatementType == "Get_StatementSeven"){

                    for(int i=0; i < jArray.length(); i++) {

                        JSONObject jsreceipt = jArray.getJSONObject(i);

                        String HouseNo = jsreceipt.getString("HouseNo");
                        String VoterName = jsreceipt.getString("VoterName");
                        String HOFName = jsreceipt.getString("HOFName");
                        String IsEPICIssued = jsreceipt.getString("IsEPICIssued");
                        String EPICNo = jsreceipt.getString("EPICNo");
                        String IsPhotographObtained = jsreceipt.getString("IsPhotographObtained");
                        String SignatureOfVoter = jsreceipt.getString("SignatureOfVoter");


                        HashMap<String, String> data = new HashMap<>();
                        if(!HouseNo.equals("null") || HouseNo!="null"){
                            data.put("HouseNo", HouseNo);
                        } else data.put("HouseNo", "--");
                        //data.put("HouseNo", HouseNo);
                        if(!VoterName.equals("null") || VoterName!="null"){
                            data.put("VoterName", VoterName);
                        } else data.put("VoterName", "--");
                        //data.put("VoterName", VoterName);
                        if(!HOFName.equals("null") || HOFName!="null"){
                            data.put("HOFName", HOFName);
                        } else data.put("HOFName", "--");
                        //data.put("HOFName", HOFName);
                        if(!IsEPICIssued.equals("null") || IsEPICIssued!="null"){
                            data.put("IsEPICIssued", IsEPICIssued);
                        } else data.put("IsEPICIssued", "--");
                        //data.put("IsEPICIssued", IsEPICIssued);
                        if(!EPICNo.equals("null") || EPICNo!="null"){
                            data.put("EPICNo", EPICNo);
                        } else data.put("EPICNo", "--");
                        //data.put("EPICNo", EPICNo);
                        if(!IsPhotographObtained.equals("null") || IsPhotographObtained!="null"){
                            data.put("IsPhotographObtained", IsPhotographObtained);
                        } else data.put("IsPhotographObtained", "--");
                        //data.put("IsPhotographObtained", IsPhotographObtained);
                        if(!SignatureOfVoter.equals("null") || SignatureOfVoter!="null"){
                            data.put("SignatureOfVoter", SignatureOfVoter);
                        } else data.put("SignatureOfVoter", "--");
                        //data.put("SignatureOfVoter", SignatureOfVoter);


                        //data.put("TAG_U", urgent);
                        dataAL.add(data);

                        ListAdapter la = new SimpleAdapter(getApplicationContext(), dataAL, R.layout.custom_statement_seven_listview_item,
                                new String[]{"HouseNo", "VoterName", "HOFName", "IsEPICIssued", "EPICNo", "IsPhotographObtained", "SignatureOfVoter"},
                                new int[]{R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5, R.id.tv6, R.id.tv7});
                        listView.setAdapter(la);
                    }

                } else if(StatementType.equals("Get_StatementEight") || StatementType == "Get_StatementEight"){

                    for(int i=0; i < jArray.length(); i++) {

                        JSONObject jsreceipt = jArray.getJSONObject(i);

                        String NoOfNewlyConstructedHouseSinceLastRevision = jsreceipt.getString("NoOfNewlyConstructedHouseSinceLastRevision");
                        String NewlyConstructedHouseDetails = jsreceipt.getString("NewlyConstructedHouseDetails");
                        String NoOfNewlyConstructedBuildingSinceLastRevision = jsreceipt.getString("NoOfNewlyConstructedBuildingSinceLastRevision");
                        String NewlyConstructedBuildingDetails = jsreceipt.getString("NewlyConstructedBuildingDetails");
                        String NoOfNewlyConstructedSocitiesSinceLastRevision = jsreceipt.getString("NoOfNewlyConstructedSocitiesSinceLastRevision");
                        String NewlyConstructedSocitiesDetails = jsreceipt.getString("NewlyConstructedSocitiesDetails");


                        HashMap<String, String> data = new HashMap<>();
                        if(!NoOfNewlyConstructedHouseSinceLastRevision.equals("null") || NoOfNewlyConstructedHouseSinceLastRevision!="null"){
                            data.put("NoOfNewlyConstructedHouseSinceLastRevision", NoOfNewlyConstructedHouseSinceLastRevision);
                        } else data.put("NoOfNewlyConstructedHouseSinceLastRevision", "--");
                        //data.put("NoOfNewlyConstructedHouseSinceLastRevision", NoOfNewlyConstructedHouseSinceLastRevision);
                        if(!NewlyConstructedHouseDetails.equals("null") || NewlyConstructedHouseDetails!="null"){
                            data.put("NewlyConstructedHouseDetails", NewlyConstructedHouseDetails);
                        } else data.put("NewlyConstructedHouseDetails", "--");
                        //data.put("NewlyConstructedHouseDetails", NewlyConstructedHouseDetails);
                        if(!NoOfNewlyConstructedBuildingSinceLastRevision.equals("null") || NoOfNewlyConstructedBuildingSinceLastRevision!="null"){
                            data.put("NoOfNewlyConstructedBuildingSinceLastRevision", NoOfNewlyConstructedBuildingSinceLastRevision);
                        } else data.put("NoOfNewlyConstructedBuildingSinceLastRevision", "--");
                       // data.put("NoOfNewlyConstructedBuildingSinceLastRevision", NoOfNewlyConstructedBuildingSinceLastRevision);
                        if(!NewlyConstructedBuildingDetails.equals("null") || NewlyConstructedBuildingDetails!="null"){
                            data.put("NewlyConstructedBuildingDetails", NewlyConstructedBuildingDetails);
                        } else data.put("NewlyConstructedBuildingDetails", "--");
                        //data.put("NewlyConstructedBuildingDetails", NewlyConstructedBuildingDetails);
                        if(!NoOfNewlyConstructedSocitiesSinceLastRevision.equals("null") || NoOfNewlyConstructedSocitiesSinceLastRevision!="null"){
                            data.put("NoOfNewlyConstructedSocitiesSinceLastRevision", NoOfNewlyConstructedSocitiesSinceLastRevision);
                        } else data.put("NoOfNewlyConstructedSocitiesSinceLastRevision", "--");
                        //data.put("NoOfNewlyConstructedSocitiesSinceLastRevision", NoOfNewlyConstructedSocitiesSinceLastRevision);
                        if(!NewlyConstructedSocitiesDetails.equals("null") || NewlyConstructedSocitiesDetails!="null"){
                            data.put("NewlyConstructedSocitiesDetails", NewlyConstructedSocitiesDetails);
                        } else data.put("NewlyConstructedSocitiesDetails", "--");
                        //data.put("NewlyConstructedSocitiesDetails", NewlyConstructedSocitiesDetails);


                        //data.put("TAG_U", urgent);
                        dataAL.add(data);

                        ListAdapter la = new SimpleAdapter(getApplicationContext(), dataAL, R.layout.custom_statement_eight_listview_item,
                                new String[]{"NoOfNewlyConstructedHouseSinceLastRevision", "NewlyConstructedHouseDetails", "NoOfNewlyConstructedBuildingSinceLastRevision",
                                        "NewlyConstructedBuildingDetails", "NoOfNewlyConstructedSocitiesSinceLastRevision", "NewlyConstructedSocitiesDetails"},
                                new int[]{R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5, R.id.tv6});
                        listView.setAdapter(la);
                    }


                } else if(StatementType.equals("Get_StatementFourA") || StatementType == "Get_StatementFourA"){
                    for(int i=0; i < jArray.length(); i++) {

                        JSONObject jsreceipt = jArray.getJSONObject(i);

                        String HouseNo = jsreceipt.getString("HouseNo");
                        String VoterName = jsreceipt.getString("VoterName");
                        String HOFName = jsreceipt.getString("HOFName");
                        String DOB = jsreceipt.getString("DOB");
                        String InWhichRevisionYearVoterWillBecomeEligible = jsreceipt.getString("InWhichRevisionYearVoterWillBecomeEligible");


                        HashMap<String, String> data = new HashMap<>();
                        if(!HouseNo.equals("null") || HouseNo!="null"){
                            data.put("HouseNo", HouseNo);
                        } else data.put("HouseNo", "--");
                        //data.put("HouseNo", HouseNo);
                        if(!VoterName.equals("null") || VoterName!="null"){
                            data.put("VoterName", VoterName);
                        } else data.put("VoterName", "--");
                        //data.put("VoterName", VoterName);
                        if(!HOFName.equals("null") || HOFName!="null"){
                            data.put("HOFName", HOFName);
                        } else data.put("HOFName", "--");
                        //data.put("HOFName", HOFName);
                        if(!DOB.equals("null") || DOB!="null"){
                            data.put("DOB", DOB);
                        } else data.put("DOB", "--");
                        //data.put("DOB", DOB);
                        if(!InWhichRevisionYearVoterWillBecomeEligible.equals("null") || InWhichRevisionYearVoterWillBecomeEligible!="null"){
                            data.put("InWhichRevisionYearVoterWillBecomeEligible", InWhichRevisionYearVoterWillBecomeEligible);
                        } else data.put("InWhichRevisionYearVoterWillBecomeEligible", "--");
                        //data.put("InWhichRevisionYearVoterWillBecomeEligible",InWhichRevisionYearVoterWillBecomeEligible);


                        //data.put("TAG_U", urgent);
                        dataAL.add(data);

                        ListAdapter la = new SimpleAdapter(getApplicationContext(), dataAL, R.layout.custom_statement_foura_listview_item,
                                new String[]{"HouseNo", "VoterName", "HOFName", "DOB","InWhichRevisionYearVoterWillBecomeEligible"},
                                new int[]{R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5});
                        listView.setAdapter(la);
                    }

                } else if(StatementType.equals("Get_StatementTen") || StatementType == "Get_StatementTen"){

                    for(int i=0; i < jArray.length(); i++) {

                        JSONObject jsreceipt = jArray.getJSONObject(i);

                        String DistrictGenderRatio = jsreceipt.getString("DistrictGenderRatio");
                        String DistrictAgeCohort = jsreceipt.getString("DistrictAgeCohort");
                        String DistrictElectorPopulation = jsreceipt.getString("DistrictElectorPopulation");


                        HashMap<String, String> data = new HashMap<>();
                        if(!DistrictGenderRatio.equals("null") || DistrictGenderRatio!="null"){
                            data.put("DistrictGenderRatio", DistrictGenderRatio);
                        } else data.put("DistrictGenderRatio", "--");
                       // data.put("DistrictGenderRatio", DistrictGenderRatio);
                        if(!DistrictAgeCohort.equals("null") || DistrictAgeCohort!="null"){
                            data.put("DistrictAgeCohort", DistrictAgeCohort);
                        } else data.put("DistrictAgeCohort", "--");
                        //data.put("DistrictAgeCohort", DistrictAgeCohort);
                        if(!DistrictElectorPopulation.equals("null") || DistrictElectorPopulation!="null"){
                            data.put("DistrictElectorPopulation", DistrictElectorPopulation);
                        } else data.put("DistrictElectorPopulation", "--");
                        //data.put("DistrictElectorPopulation", DistrictElectorPopulation);


                        //data.put("TAG_U", urgent);
                        dataAL.add(data);

                        ListAdapter la = new SimpleAdapter(getApplicationContext(), dataAL, R.layout.custom_statement_ten_listview_item,
                                new String[]{"DistrictGenderRatio", "DistrictAgeCohort", "DistrictElectorPopulation"},
                                new int[]{R.id.tv1, R.id.tv2, R.id.tv3});
                        listView.setAdapter(la);
                    }


                } else if(StatementType.equals("Get_StatementEleven") || StatementType == "Get_StatementEleven"){

                    for(int i=0; i < jArray.length(); i++) {

                        JSONObject jsreceipt = jArray.getJSONObject(i);

                        String CurrentRevisionFinalRollPublicationDate = jsreceipt.getString("CurrentRevisionFinalRollPublicationDate");
                        String TotalVotersInPart = jsreceipt.getString("TotalVotersInPart");
                        String TotalPopulationOfPart = jsreceipt.getString("TotalPopulationOfPart");
                        String TotalMaleVoters = jsreceipt.getString("TotalMaleVoters");
                        String TotalFemaleVoters = jsreceipt.getString("TotalFemaleVoters");
                        String GenderRatio = jsreceipt.getString("GenderRatio");
                        String GenderPopulationRatio = jsreceipt.getString("GenderPopulationRatio");
                        String AgeCohort_18to20 = jsreceipt.getString("AgeCohort_18to20");
                        String AgeCohort_21to29 = jsreceipt.getString("AgeCohort_21to29");
                        String AgeCohort_30to35 = jsreceipt.getString("AgeCohort_30to35");

                        HashMap<String, String> data = new HashMap<>();
                        if(!CurrentRevisionFinalRollPublicationDate.equals("null") || CurrentRevisionFinalRollPublicationDate!="null"){
                            data.put("CurrentRevisionFinalRollPublicationDate", CurrentRevisionFinalRollPublicationDate);
                        } else data.put("CurrentRevisionFinalRollPublicationDate", "--");
                       // data.put("CurrentRevisionFinalRollPublicationDate", CurrentRevisionFinalRollPublicationDate);
                        if(!TotalVotersInPart.equals("null") || TotalVotersInPart!="null"){
                            data.put("TotalVotersInPart", TotalVotersInPart);
                        } else data.put("TotalVotersInPart", "--");
                        //data.put("TotalVotersInPart", TotalVotersInPart);
                        if(!TotalPopulationOfPart.equals("null") || TotalPopulationOfPart!="null"){
                            data.put("TotalPopulationOfPart", TotalPopulationOfPart);
                        } else data.put("TotalPopulationOfPart", "--");
                        //data.put("TotalPopulationOfPart", TotalPopulationOfPart);
                        if(!TotalMaleVoters.equals("null") || TotalMaleVoters!="null"){
                            data.put("TotalMaleVoters", TotalMaleVoters);
                        } else data.put("TotalMaleVoters", "--");
                        //data.put("TotalMaleVoters", TotalMaleVoters);
                        if(!TotalFemaleVoters.equals("null") || TotalFemaleVoters!="null"){
                            data.put("TotalFemaleVoters", TotalFemaleVoters);
                        } else data.put("TotalFemaleVoters", "--");
                        //data.put("TotalFemaleVoters",TotalFemaleVoters);
                        if(!GenderRatio.equals("null") || GenderRatio!="null"){
                            data.put("GenderRatio", GenderRatio);
                        } else data.put("GenderRatio", "--");
                        //data.put("GenderRatio", GenderRatio);
                        if(!GenderPopulationRatio.equals("null") || GenderPopulationRatio!="null"){
                            data.put("GenderPopulationRatio", GenderPopulationRatio);
                        } else data.put("GenderPopulationRatio", "--");
                        //data.put("GenderPopulationRatio", GenderPopulationRatio);
                        if(!AgeCohort_18to20.equals("null") || AgeCohort_18to20!="null"){
                            data.put("AgeCohort_18to20", AgeCohort_18to20);
                        } else data.put("AgeCohort_18to20", "--");
                        //data.put("AgeCohort_18to20", AgeCohort_18to20);
                        if(!AgeCohort_21to29.equals("null") || AgeCohort_21to29!="null"){
                            data.put("AgeCohort_21to29", AgeCohort_21to29);
                        } else data.put("AgeCohort_21to29", "--");
                        //data.put("AgeCohort_21to29", AgeCohort_21to29);
                        if(!AgeCohort_30to35.equals("null") || AgeCohort_30to35!="null"){
                            data.put("AgeCohort_30to35", AgeCohort_30to35);
                        } else data.put("AgeCohort_30to35", "--");
                        //data.put("AgeCohort_30to35", AgeCohort_30to35);

                        //data.put("TAG_U", urgent);
                        dataAL.add(data);

                        ListAdapter la = new SimpleAdapter(getApplicationContext(), dataAL, R.layout.custom_statement_eleven_listview_item,
                                new String[]{"CurrentRevisionFinalRollPublicationDate", "TotalVotersInPart", "TotalPopulationOfPart", "TotalMaleVoters","TotalFemaleVoters",
                                        "GenderRatio", "GenderPopulationRatio", "AgeCohort_18to20", "AgeCohort_21to29", "AgeCohort_30to35"},
                                new int[]{R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5, R.id.tv6, R.id.tv7, R.id.tv8, R.id.tv9,R.id.tv10});
                        listView.setAdapter(la);
                    }

                } else if(StatementType.equals("Get_StatementTwelve") || StatementType == "Get_StatementTwelve"){

                    for(int i=0; i < jArray.length(); i++) {

                        JSONObject jsreceipt = jArray.getJSONObject(i);

                        String HouseNo = jsreceipt.getString("HouseNo");
                        String VoterName = jsreceipt.getString("VoterName");
                        String HOFName = jsreceipt.getString("HOFName");
                        String EPICNo = jsreceipt.getString("EPICNo");
                        String IsVoterInSamePart = jsreceipt.getString("IsVoterInSamePart");
                        String PartWhereRegisteredAsVoter = jsreceipt.getString("PartWhereRegisteredAsVoter");
                        String DesignationInGovernment = jsreceipt.getString("DesignationInGovernment");
                        String IsRetired = jsreceipt.getString("IsRetired");
                        String IsPermanent = jsreceipt.getString("IsPermanent");
                        String Class = jsreceipt.getString("Class");
                        String ResidingInPartSince = jsreceipt.getString("ResidingInPartSince");

                        HashMap<String, String> data = new HashMap<>();
                        if(!HouseNo.equals("null") || HouseNo!="null"){
                            data.put("HouseNo", HouseNo);
                        } else data.put("HouseNo", "--");
                        //data.put("HouseNo", HouseNo);
                        if(!VoterName.equals("null") || VoterName!="null"){
                            data.put("VoterName", VoterName);
                        } else data.put("VoterName", "--");
                        //data.put("VoterName", VoterName);
                        if(!HOFName.equals("null") || HOFName!="null"){
                            data.put("HOFName", HOFName);
                        } else data.put("HOFName", "--");
                        //data.put("HOFName", HOFName);
                        if(!EPICNo.equals("null") || EPICNo!="null"){
                            data.put("EPICNo", EPICNo);
                        } else data.put("EPICNo", "--");
                        //data.put("EPICNo", EPICNo);
                        if(!IsVoterInSamePart.equals("null") || IsVoterInSamePart!="null"){
                            data.put("IsVoterInSamePart", IsVoterInSamePart);
                        } else data.put("IsVoterInSamePart", "--");
                       // data.put("IsVoterInSamePart",IsVoterInSamePart);
                        if(!PartWhereRegisteredAsVoter.equals("null") || PartWhereRegisteredAsVoter!="null"){
                            data.put("PartWhereRegisteredAsVoter", PartWhereRegisteredAsVoter);
                        } else data.put("PartWhereRegisteredAsVoter", "--");
                        //data.put("PartWhereRegisteredAsVoter", PartWhereRegisteredAsVoter);
                        if(!DesignationInGovernment.equals("null") || DesignationInGovernment!="null"){
                            data.put("DesignationInGovernment", DesignationInGovernment);
                        } else data.put("DesignationInGovernment", "--");
                        //data.put("DesignationInGovernment", DesignationInGovernment);
                        if(!IsRetired.equals("null") || IsRetired!="null"){
                            data.put("IsRetired", IsRetired);
                        } else data.put("IsRetired", "--");
                        //data.put("IsRetired", IsRetired);
                        if(!IsPermanent.equals("null") || IsPermanent!="null"){
                            data.put("IsPermanent", IsPermanent);
                        } else data.put("IsPermanent", "--");
                        //data.put("IsPermanent", IsPermanent);
                        if(!Class.equals("null") || Class!="null"){
                            data.put("Class", Class);
                        } else data.put("Class", "--");
                        //data.put("Class", Class);
                        if(!ResidingInPartSince.equals("null") || ResidingInPartSince!="null"){
                            data.put("ResidingInPartSince", ResidingInPartSince);
                        } else data.put("ResidingInPartSince", "--");
                        //data.put("ResidingInPartSince", ResidingInPartSince);

                        //data.put("TAG_U", urgent);
                        dataAL.add(data);

                        ListAdapter la = new SimpleAdapter(getApplicationContext(), dataAL, R.layout.custom_statement_twelve_listview_item,
                                new String[]{"HouseNo", "VoterName", "HOFName", "EPICNo","IsVoterInSamePart","PartWhereRegisteredAsVoter",
                                        "DesignationInGovernment", "IsRetired", "IsPermanent", "Class", "ResidingInPartSince"},
                                new int[]{R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5, R.id.tv6, R.id.tv7, R.id.tv8, R.id.tv9,R.id.tv10, R.id.tv11});
                        listView.setAdapter(la);
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("printStackTrace",e.getMessage());
                Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Something_Went_Wrong),Toast.LENGTH_LONG).show();
                finish();
            }
            finally {
                hideProgressDialog();
            }

        }
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
