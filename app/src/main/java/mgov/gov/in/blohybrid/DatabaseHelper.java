package mgov.gov.in.blohybrid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

/**
 * Created by kgaurav on 14-Mar-18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "BLO.db";
    public static final String TABLE_NAME1_VOTER_LIST = "Voter_List_Table";
    //public static final String TABLE_1_COL_SLNO = "SLNO";
    public static final String TABLE_1_COL_SLNOINPART  = "SLNOINPART";
    public static final String TABLE_1_COL_EPIC_NO = "EPIC_NO";
    public static final String TABLE_1_COL_Name = "Name";
    public static final String TABLE_1_COL_RLN_Name = "RLN_Name";
    public static final String TABLE_1_COL_GENDER = "GENDER";
    public static final String TABLE_1_COL_AGE = "AGE";
    public static final String TABLE_1_COL_ADDRESS = "ADDRESS";
    public static final String TABLE_1_COL_HOUSE_NO = "HOUSE_NO";
    public static final String TABLE_1_COL_RLN_TYPE = "RLN_TYPE";
    public static final String TABLE_1_COL_isWORKDONE = "isWORKDONE";

    public static final String TABLE_NAME2_ASD_SAVED_DATA= "ASD_Saved_Data";
    public static final String TABLE_2_COL_SerialNo = "SerialNo";
    public static final String TABLE_2_COL_Reason = "Reason";
    public static final String TABLE_2_COL_UserMobileNo = "UserMobileNo";
    public static final String TABLE_2_COL_MobileOwnedType = "MobileOwnedType";
    public static final String TABLE_2_COL_MobileType = "MobileType";
    public static final String TABLE_2_COL_ASDstatus = "ASDstatus";
    public static final String TABLE_2_COL_Date = "Date";
    public static final String TABLE_2_COL_PushedFlag = "PushedFlag";
    public static final String TABLE_2_COL_RLN_Name = "RLN_Name";
    public static final String TABLE_2_COL_isDivyangjan = "isDivyangjan";
    public static final String TABLE_2_COL_isPRVoter = "isPRVoter";
    public static final String TABLE_2_COL_isDVoter = "isDVoter";
    public static final String TABLE_2_COL_isImportantElector = "isImportantElector";
    public static final String TABLE_2_COL_IS_PWD = "IS_PWD";
    public static final String TABLE_2_COL_IS_FILLED_FORM_COLLECTED = "IS_FILLED_FORM_COLLECTED";
    public static final String TABLE_2_COL_FORM7_REF_NO = "FORM7_REF_NO";
    public static final String TABLE_2_COL_FORM8_REF_NO = "FORM8_REF_NO";

    public static final String TABLE_NAME3_BLO_CREDENTIALS = "BLO_CREDENTIALS";
    public static final String TABLE_3_COL_AcNo = "AcNo";
    public static final String TABLE_3_COL_PartNo = "PartNo";
    public static final String TABLE_3_COL_BLOName = "BLOName";
    public static final String TABLE_3_COL_BLOMobileNo = "BLOMobileNo";
    public static final String TABLE_3_COL_StateCode = "StateCode";
    public static final String TABLE_3_COL_Password = "Password";
    public static final String TABLE_3_COL_IsDisabledFlag = "IsDisabledFlag";
    public static final String TABLE_3_COL_Key = "Key";

    public static final String TABLE_NAME4_OVERSEAS_VOTER = "OVERSEAS_VOTER";
    public static final String TABLE_4_COL_SLNO_INPART = "SLNO_INPART";
    public static final String TABLE_4_COL_PASSPORT_NO = "PASSPORT_NO";
    public static final String TABLE_4_COL_DOB = "DOB";
    public static final String TABLE_4_COL_EMAIL_ID = "EMAIL_ID";
    public static final String TABLE_4_COL_MOBILE_NO = "MOBILE_NO";
    public static final String TABLE_4_COL_MOBILE_TYPE = "MOBILE_TYPE";
    public static final String TABLE_4_COL_PLATFORM_TYPE = "PLATFORM_TYPE";
    public static final String TABLE_4_COL_isPushed = "isPushed";

    public static final String TABLE_NAME5_PROBABLE_POLLING_STATION = "PROBABLE_POLLING_STATION";
    public static final String TABLE_5_COL_AddressLine1 = "AddressLine1";
    public static final String TABLE_5_COL_AddressLine2 = "AddressLine2";
    public static final String TABLE_5_COL_AddressLine3 = "AddressLine3";
    public static final String TABLE_5_COL_PS_BUILDING_TYPE_CODE = "PS_BUILDING_TYPE_CODE";
    public static final String TABLE_5_COL_PRIORITY_CHOICE = "PRIORITY_CHOICE";
    public static final String TABLE_5_COL_PS_BULDING_LAT_LONG = "PS_BULDING_LAT_LONG";
    public static final String TABLE_5_COL_BUILDING_PHOTOGRAPH = "BUILDING_PHOTOGRAPH";
    public static final String TABLE_5_COL_IS_POLLINGBOOTH_AREA_IS_GT_20SQM = "IS_POLLINGBOOTH_AREA_IS_GT_20SQM";
    public static final String TABLE_5_COL_IS_BUILDING_DILAPIDATED_OR_DANGEROUS = "IS_BUILDING_DILAPIDATED_OR_DANGEROUS";
    public static final String TABLE_5_COL_IS_PS_IN_GOV_BUILDING_OR_PREMISIS = "IS_PS_IN_GOV_BUILDING_OR_PREMISIS";
    public static final String TABLE_5_COL_IS_PS_LOCATERD_IN_INSTITUTION_OR_RELIGIOUS_PLACE = "IS_PS_LOCATERD_IN_INSTITUTION_OR_RELIGIOUS_PLACE";
    public static final String TABLE_5_COL_IS_PS_IN_SCHOOL_OR_COLLEGE_BUILDING = "IS_PS_IN_SCHOOL_OR_COLLEGE_BUILDING";
    public static final String TABLE_5_COL_IS_PS_ON_GROUND_FLOOR = "IS_PS_ON_GROUND_FLOOR";
    public static final String TABLE_5_COL_IS_SEPARATE_DOOR_FOR_ENTRY_N_EXIT = "IS_SEPARATE_DOOR_FOR_ENTRY_N_EXIT";
    public static final String TABLE_5_COL_IS_ANY_POLITICAL_PARTY_WITHIN_200MTR_PS_PREMISES = "IS_ANY_POLITICAL_PARTY_WITHIN_200MTR_PS_PREMISES";
    public static final String TABLE_5_COL_IS_DRINKING_WATER_FACILITY_AVAILABLE = "IS_DRINKING_WATER_FACILITY_AVAILABLE";
    public static final String TABLE_5_COL_IS_ELECTRICITY_SUPPLY_AVAILABLE_IN_BUILDING = "IS_ELECTRICITY_SUPPLY_AVAILABLE_IN_BUILDING";
    public static final String TABLE_5_COL_IS_PROPER_LIGHTING_AVAILABLE_IN_BUILDING = "IS_PROPER_LIGHTING_AVAILABLE_IN_BUILDING";
    public static final String TABLE_5_COL_IS_TOILET_FACILITY_AVAILABLE_IN_BUILDING = "IS_TOILET_FACILITY_AVAILABLE_IN_BUILDING";
    public static final String TABLE_5_COL_IS_RAMP_PROVIDED = "IS_RAMP_PROVIDED";
    public static final String TABLE_5_COL_IS_ADEQUATE_FURNITURE_AVAILABLE_IN_BUILDING = "IS_ADEQUATE_FURNITURE_AVAILABLE_IN_BUILDING";
    public static final String TABLE_5_COL_IS_SHADE_OR_SHELTER_AVAILABLE = "IS_SHADE_OR_SHELTER_AVAILABLE";
    public static final String TABLE_5_COL_IS_PROPER_ROAD_CONNECTIVITY_AVAILABLE = "IS_PROPER_ROAD_CONNECTIVITY_AVAILABLE";
    public static final String TABLE_5_COL_IS_ANY_RIVER_OR_VALLY_TO_CROSS_TO_REACH_PS = "IS_ANY_RIVER_OR_VALLY_TO_CROSS_TO_REACH_PS";
    public static final String TABLE_5_COL_IS_PS_HAS_LANDLINE_TELEPHONE_OR_FAX = "IS_PS_HAS_LANDLINE_TELEPHONE_OR_FAX";
    public static final String TABLE_5_COL_IS_PS_HAS_MOBILE_CONNECTIVITY = "IS_PS_HAS_MOBILE_CONNECTIVITY";
    public static final String TABLE_5_COL_IS_PS_HAS_INTERNET_FACILITY = "IS_PS_HAS_INTERNET_FACILITY";
    public static final String TABLE_5_COL_IS_PS_HAS_PROPER_SIGNAGE_OF_BUILDING_NAME_N_ADDRESS = "IS_PS_HAS_PROPER_SIGNAGE_OF_BUILDING_NAME_N_ADDRESS";
    public static final String TABLE_5_COL_IS_PS_WITHIN_INSURGENCY_AFFECTED_AREA = "IS_PS_WITHIN_INSURGENCY_AFFECTED_AREA";
    public static final String TABLE_5_COL_IS_PS_WITHIN_FOREST_OR_SEMIFOREST_AREA = "IS_PS_WITHIN_FOREST_OR_SEMIFOREST_AREA";
    public static final String TABLE_5_COL_IS_PS_IN_VULNERABLE_CRITICAL_LOCATION = "IS_PS_IN_VULNERABLE_CRITICAL_LOCATION";
    public static final String TABLE_5_COL_IS_PS_SENSITIVE_OR_HYPER_SENSITIVE = "IS_PS_SENSITIVE_OR_HYPER_SENSITIVE";
    public static final String TABLE_5_COL_isPushed = "isPushed";

    public static final String TABLE_NAME6_FAMILY_GPS_LOCATION = "FAMILY_GPS_LOCATION";
    public static final String TABLE_6_COL_HOF_GENDER = "HOF_GENDER";
    public static final String TABLE_6_COL_HOF_SLNO = "HOF_SLNO";
    public static final String TABLE_6_COL_HOF_RLN_GENDER = "HOF_RLN_GENDER";
    public static final String TABLE_6_COL_HOF_RLN_SLNO = "HOF_RLN_SLNO";
    public static final String TABLE_6_COL_S_SLNO = "S_SLNO";
    public static final String TABLE_6_COL_DM_SLNO = "DM_SLNO";
    public static final String TABLE_6_COL_DU_SLNO = "DU_SLNO";
    public static final String TABLE_6_COL_LagLong = "LagLong";
    public static final String TABLE_6_COL_isPushed = "isPushed";

    public static final String TABLE_NAME7_EXISTING_POLLING_STATION = "EXISTING_POLLING_STATION";
    public static final String TABLE_7_COL_AddressLine1 = "AddressLine1";
    public static final String TABLE_7_COL_AddressLine2 = "AddressLine2";
    public static final String TABLE_7_COL_AddressLine3 = "AddressLine3";
    public static final String TABLE_7_COL_PS_BUILDING_TYPE_CODE = "PS_BUILDING_TYPE_CODE";
    public static final String TABLE_7_COL_PRIORITY_CHOICE = "PRIORITY_CHOICE";
    public static final String TABLE_7_COL_PS_BULDING_LAT_LONG = "PS_BULDING_LAT_LONG";
    public static final String TABLE_7_COL_BUILDING_PHOTOGRAPH = "BUILDING_PHOTOGRAPH";
    public static final String TABLE_7_COL_isPushed = "isPushed";

    public static final String TABLE_NAME_8_PostOfficeAddressLocations = "PostOfficeAddressLocations";
    public static final String TABLE_8_COL_AddressLine1 = "AddressLine1";
    public static final String TABLE_8_COL_AddressLine2 = "AddressLine2";
    public static final String TABLE_8_COL_AddressLine3 = "AddressLine3";
    public static final String TABLE_8_COL_PS_BULDING_LAT_LONG = "PS_BULDING_LAT_LONG";
    public static final String TABLE_8_COL_PO_AREA_PINCODE = "PO_AREA_PINCODE";
    public static final String TABLE_8_COL_isPushed = "isPushed";

    public static final String TABLE_NAME_9_FutureVotersList = "FutureVotersList";
    public static final String TABLE_9_COL_NAME = "NAME";
    public static final String TABLE_9_COL_RLN_NAME = "RLN_NAME";
    public static final String TABLE_9_COL_DOB = "DOB";
    public static final String TABLE_9_COL_GENDER = "GENDER";
    public static final String TABLE_9_COL_isPushed = "isPushed";

    public static final String TABLE_NAME_10_SHOW_DASHBOARD = "SHOW_DASHBOARD";
    public static final String TABLE_10_COL_Date = "Date";
    /*public static final String TABLE_10_Work_Done = "Work_Done";
    public static final String TABLE_10_Pendig_Work = "Pending_Work";*/
    public static final String TABLE_10_Pushed_Record = "Pushed_Record";
    public static final String TABLE_10_Pushed_TimeStamp = "Pushed_TimeStamp";

    public static final String TABLE_NAME11_AUXILIARY_POLLING_STATION = "AUXILIARY_POLLING_STATION";
    public static final String TABLE_11_COL_AddressLine1 = "AddressLine1";
    public static final String TABLE_11_COL_AddressLine2 = "AddressLine2";
    public static final String TABLE_11_COL_AddressLine3 = "AddressLine3";
    public static final String TABLE_11_COL_PS_BUILDING_TYPE_CODE = "PS_BUILDING_TYPE_CODE";
    public static final String TABLE_11_COL_PRIORITY_CHOICE = "PRIORITY_CHOICE";
    public static final String TABLE_11_COL_PS_BULDING_LAT_LONG = "PS_BULDING_LAT_LONG";
    public static final String TABLE_11_COL_BUILDING_PHOTOGRAPH = "BUILDING_PHOTOGRAPH";
    public static final String TABLE_11_COL_isPushed = "isPushed";

    public static final String TABLE_NAME_12_PoliceStationLocations = "PoliceStationLocations";
    public static final String TABLE_12_COL_AddressLine1 = "AddressLine1";
    public static final String TABLE_12_COL_AddressLine2 = "AddressLine2";
    public static final String TABLE_12_COL_AddressLine3 = "AddressLine3";
    public static final String TABLE_12_COL_PS_BULDING_LAT_LONG = "POLICE_STN_BULDING_LAT_LONG";
    public static final String TABLE_12_COL_PS_Mobile_Number = "POLICE_STN_CONTACT_NO";
    public static final String TABLE_12_COL_isPushed = "isPushed";

    public static final String TABLE_NAME_13_HealthCareCentre = "HealthCareCentre";
    public static final String TABLE_13_COL_AddressLine1 = "AddressLine1";
    public static final String TABLE_13_COL_AddressLine2 = "AddressLine2";
    public static final String TABLE_13_COL_AddressLine3 = "AddressLine3";
    public static final String TABLE_13_COL_HC_BULDING_LAT_LONG = "PS_BULDING_LAT_LONG";
    public static final String TABLE_13_COL_HC_Mobile_Number = "HEALTHCARE_CENTER_CONTACT_NO";
    public static final String TABLE_13_COL_isPushed = "isPushed";

    public static final String TABLE_NAME_14_Rationalization = "Rationalization";
    public static final String TABLE_14_COL_SerialNumbers = "SerialNumbers";
    public static final String TABLE_14_COL_StreetNumber = "StreetNumber";
    public static final String TABLE_14_COL_StreetStartLocation = "StreetStartLocation";
    public static final String TABLE_14_COL_StreetMidLocation = "StreetMidLocation";
    public static final String TABLE_14_COL_StreetEndLocation = "StreetEndLocation";
    public static final String TABLE_14_COL_BuildingNumber = "BuildingDetail";
    public static final String TABLE_14_COL_HouseNumber = "HouseNumber";
    public static final String TABLE_14_COL_DoorNumber = "DoorNumber";
    public static final String TABLE_14_COL_BuildingGPSLocation = "BuildingGPSLocation";
   /* public static final String TABLE_14_COL_HouseGPSLOcation = "HouseGPSLOcation";
    public static final String TABLE_14_COL_DoorGPSLocation = "DoorGPSLocation";*/
    public static final String TABLE_14_COL_isPushed = "isPushed";

    public static final String TABLE_NAME_15_Unenrolled = "Unenrolled";
    public static final String TABLE_15_COL_NAME = "NAME";
    public static final String TABLE_15_COL_RLN_NAME = "RLN_NAME";
    public static final String TABLE_15_COL_SLNO_OF_RLN = "SLNO_OF_RLN";
    public static final String TABLE_15_COL_RLN_TYPE = "RLN_TYPE";
    public static final String TABLE_15_COL_MOBILE_NO = "MOBILE_NO";
    public static final String TABLE_15_COL_MOBILE_TYPE = "MOBILE_TYPE";
    public static final String TABLE_15_COL_EMAIL_ID = "EMAIL_ID";
    public static final String TABLE_15_COL_FORM6_REF_NO = "FORM6_REF_NO";
    public static final String TABLE_15_COL_IS_FILLED_FORM_COLLECTED = "IS_FILLED_FORM_COLLECTED";
   // public static final String TABLE_15_COL_FORM_REF_NO = "FORM_REF_NO";
    public static final String TABLE_15_COL_isPushed = "isPushed";






    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*db.execSQL("create table " + TABLE_NAME1_VOTER_LIST +"( " + TABLE_1_COL_SLNO + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ TABLE_1_COL_SLNOINPART  + " INTEGER, " + TABLE_1_COL_EPIC_NO + " TEXT, " + TABLE_1_COL_Name + " TEXT, "
                    + TABLE_1_COL_RLN_Name + " TEXT, "+ TABLE_1_COL_GENDER + " TEXT, " + TABLE_1_COL_AGE + " INTEGER, " + TABLE_1_COL_ADDRESS + " TEXT, "
                    + TABLE_1_COL_HOUSE_NO + " TEXT, "+ TABLE_1_COL_RLN_TYPE + " TEXT, " + TABLE_1_COL_isWORKDONE + " TEXT)");*/

        db.execSQL("create table " + TABLE_NAME1_VOTER_LIST +"( " + TABLE_1_COL_SLNOINPART  + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TABLE_1_COL_EPIC_NO + " TEXT, " + TABLE_1_COL_Name + " TEXT, "
                + TABLE_1_COL_RLN_Name + " TEXT, "+ TABLE_1_COL_GENDER + " TEXT, " + TABLE_1_COL_AGE + " INTEGER, " + TABLE_1_COL_ADDRESS + " TEXT, "
                + TABLE_1_COL_HOUSE_NO + " TEXT, "+ TABLE_1_COL_RLN_TYPE + " TEXT, " + TABLE_1_COL_isWORKDONE + " TEXT)");

        db.execSQL("create table " + TABLE_NAME2_ASD_SAVED_DATA +"( " + TABLE_2_COL_SerialNo  + " INTEGER PRIMARY KEY, " + TABLE_2_COL_Reason + " TEXT, " + TABLE_2_COL_UserMobileNo + " TEXT, "
                + TABLE_2_COL_MobileOwnedType + " TEXT, "+ TABLE_2_COL_MobileType + " TEXT, " + TABLE_2_COL_ASDstatus + " TEXT, " + TABLE_2_COL_Date + " LONG, "
                + TABLE_2_COL_RLN_Name + " TEXT, "+ TABLE_2_COL_isDivyangjan + " TEXT, "+ TABLE_2_COL_isPRVoter + " TEXT, "+ TABLE_2_COL_isDVoter + " TEXT, "
                + TABLE_2_COL_isImportantElector + " TEXT, "  +TABLE_2_COL_PushedFlag + " TEXT, "+ TABLE_2_COL_IS_PWD + " TEXT, "+ TABLE_2_COL_IS_FILLED_FORM_COLLECTED + " TEXT, "
                + TABLE_2_COL_FORM7_REF_NO + " TEXT, " + TABLE_2_COL_FORM8_REF_NO + " TEXT)");

        db.execSQL("create table " + TABLE_NAME3_BLO_CREDENTIALS +"( " + TABLE_3_COL_AcNo  + " INTEGER, " + TABLE_3_COL_PartNo + " INTEGER, " + TABLE_3_COL_BLOName + " TEXT, "
                + TABLE_3_COL_BLOMobileNo + " TEXT PRIMARY KEY, "+ TABLE_3_COL_StateCode + " TEXT, " + TABLE_3_COL_Password + " TEXT, " + TABLE_3_COL_IsDisabledFlag + " TEXT, " + TABLE_3_COL_Key + " TEXT)");



        db.execSQL("create table " + TABLE_NAME4_OVERSEAS_VOTER +"( " + TABLE_4_COL_SLNO_INPART + " TEXT, " + TABLE_4_COL_PASSPORT_NO + " TEXT, "
                + TABLE_4_COL_DOB + " TEXT, "+ TABLE_4_COL_EMAIL_ID + " TEXT, " + TABLE_4_COL_MOBILE_NO + " TEXT, " + TABLE_4_COL_MOBILE_TYPE + " TEXT, "
                + TABLE_4_COL_PLATFORM_TYPE + " TEXT, "  +TABLE_4_COL_isPushed + " TEXT)");

        db.execSQL("create table " + TABLE_NAME5_PROBABLE_POLLING_STATION +"( " + TABLE_5_COL_AddressLine1  + " TEXT, " + TABLE_5_COL_AddressLine2 + " TEXT, " + TABLE_5_COL_AddressLine3 + " TEXT, "
                + TABLE_5_COL_PS_BUILDING_TYPE_CODE + " TEXT, "+ TABLE_5_COL_PRIORITY_CHOICE + " TEXT, " + TABLE_5_COL_PS_BULDING_LAT_LONG + " TEXT, " + TABLE_5_COL_BUILDING_PHOTOGRAPH + " BLOB, "
                + TABLE_5_COL_IS_POLLINGBOOTH_AREA_IS_GT_20SQM + " TEXT, "  +TABLE_5_COL_IS_BUILDING_DILAPIDATED_OR_DANGEROUS + " TEXT, "+TABLE_5_COL_IS_PS_IN_GOV_BUILDING_OR_PREMISIS + " TEXT, "
                + TABLE_5_COL_IS_PS_LOCATERD_IN_INSTITUTION_OR_RELIGIOUS_PLACE + " TEXT, "  +TABLE_5_COL_IS_PS_IN_SCHOOL_OR_COLLEGE_BUILDING + " TEXT, "+TABLE_5_COL_IS_PS_ON_GROUND_FLOOR + " TEXT, "
                + TABLE_5_COL_IS_SEPARATE_DOOR_FOR_ENTRY_N_EXIT + " TEXT, "  +TABLE_5_COL_IS_ANY_POLITICAL_PARTY_WITHIN_200MTR_PS_PREMISES + " TEXT, "+TABLE_5_COL_IS_DRINKING_WATER_FACILITY_AVAILABLE + " TEXT, "
                + TABLE_5_COL_IS_ELECTRICITY_SUPPLY_AVAILABLE_IN_BUILDING + " TEXT, "  +TABLE_5_COL_IS_PROPER_LIGHTING_AVAILABLE_IN_BUILDING + " TEXT, "+TABLE_5_COL_IS_TOILET_FACILITY_AVAILABLE_IN_BUILDING + " TEXT, "
                + TABLE_5_COL_IS_RAMP_PROVIDED + " TEXT, "  +TABLE_5_COL_IS_ADEQUATE_FURNITURE_AVAILABLE_IN_BUILDING + " TEXT, "+TABLE_5_COL_IS_SHADE_OR_SHELTER_AVAILABLE + " TEXT, "
                + TABLE_5_COL_IS_PROPER_ROAD_CONNECTIVITY_AVAILABLE + " TEXT, "  +TABLE_5_COL_IS_ANY_RIVER_OR_VALLY_TO_CROSS_TO_REACH_PS + " TEXT, "+TABLE_5_COL_IS_PS_HAS_LANDLINE_TELEPHONE_OR_FAX + " TEXT, "
                + TABLE_5_COL_IS_PS_HAS_MOBILE_CONNECTIVITY + " TEXT, "  +TABLE_5_COL_IS_PS_HAS_INTERNET_FACILITY + " TEXT, "+TABLE_5_COL_IS_PS_HAS_PROPER_SIGNAGE_OF_BUILDING_NAME_N_ADDRESS + " TEXT, "
                + TABLE_5_COL_IS_PS_WITHIN_INSURGENCY_AFFECTED_AREA + " TEXT, "  +TABLE_5_COL_IS_PS_WITHIN_FOREST_OR_SEMIFOREST_AREA + " TEXT, "+TABLE_5_COL_IS_PS_IN_VULNERABLE_CRITICAL_LOCATION + " TEXT, "
                + TABLE_5_COL_IS_PS_SENSITIVE_OR_HYPER_SENSITIVE + " TEXT, "  +TABLE_5_COL_isPushed + " TEXT)");

        db.execSQL("create table " + TABLE_NAME6_FAMILY_GPS_LOCATION +"( " + TABLE_6_COL_HOF_SLNO  + " TEXT, " + TABLE_6_COL_HOF_GENDER + " TEXT, " + TABLE_6_COL_HOF_RLN_GENDER + " TEXT, "
                + TABLE_6_COL_HOF_RLN_SLNO + " TEXT, " + TABLE_6_COL_S_SLNO + " TEXT, " + TABLE_6_COL_DM_SLNO + " TEXT, " + TABLE_6_COL_DU_SLNO + " TEXT, "+ TABLE_6_COL_LagLong + " TEXT, "  +TABLE_6_COL_isPushed + " TEXT)");

        db.execSQL("create table " + TABLE_NAME7_EXISTING_POLLING_STATION +"( " + TABLE_7_COL_AddressLine1  + " TEXT, " + TABLE_7_COL_AddressLine2 + " TEXT, " + TABLE_7_COL_AddressLine3 + " TEXT, "
                + TABLE_7_COL_PS_BUILDING_TYPE_CODE + " TEXT, "+ TABLE_7_COL_PRIORITY_CHOICE + " TEXT, " + TABLE_7_COL_PS_BULDING_LAT_LONG + " TEXT, " + TABLE_7_COL_BUILDING_PHOTOGRAPH + " BLOB, "
                +TABLE_7_COL_isPushed + " TEXT)");

        db.execSQL("create table " + TABLE_NAME_8_PostOfficeAddressLocations +"( " + TABLE_8_COL_AddressLine1  + " TEXT, " + TABLE_8_COL_AddressLine2 + " TEXT, " + TABLE_8_COL_AddressLine3 + " TEXT, "
                + TABLE_8_COL_PS_BULDING_LAT_LONG + " TEXT, " + TABLE_8_COL_PO_AREA_PINCODE + " TEXT, " + TABLE_8_COL_isPushed + " TEXT)");


        db.execSQL("create table " + TABLE_NAME_9_FutureVotersList +"( " + TABLE_9_COL_NAME  + " TEXT, " + TABLE_9_COL_RLN_NAME + " TEXT, "
                + TABLE_9_COL_DOB + " TEXT, "+ TABLE_9_COL_GENDER + " TEXT, " +TABLE_9_COL_isPushed + " TEXT)");

        db.execSQL("create table " + TABLE_NAME_10_SHOW_DASHBOARD +"( " + TABLE_10_COL_Date  + " LONG PRIMARY KEY, " + TABLE_10_Pushed_Record + " INTEGER, "+ TABLE_10_Pushed_TimeStamp + " LONG)");

        db.execSQL("create table " + TABLE_NAME11_AUXILIARY_POLLING_STATION +"( " + TABLE_11_COL_AddressLine1  + " TEXT, " + TABLE_11_COL_AddressLine2 + " TEXT, " + TABLE_11_COL_AddressLine3 + " TEXT, "
                + TABLE_11_COL_PS_BUILDING_TYPE_CODE + " TEXT, "+ TABLE_11_COL_PRIORITY_CHOICE + " TEXT, " + TABLE_11_COL_PS_BULDING_LAT_LONG + " TEXT, " + TABLE_11_COL_BUILDING_PHOTOGRAPH + " BLOB, "
                +TABLE_11_COL_isPushed + " TEXT)");

        db.execSQL("create table " + TABLE_NAME_12_PoliceStationLocations +"( " + TABLE_12_COL_AddressLine1  + " TEXT, " + TABLE_12_COL_AddressLine2 + " TEXT, " + TABLE_12_COL_AddressLine3 + " TEXT, "
                + TABLE_12_COL_PS_BULDING_LAT_LONG + " TEXT, " + TABLE_12_COL_PS_Mobile_Number + " TEXT, " + TABLE_12_COL_isPushed + " TEXT)");

        db.execSQL("create table " + TABLE_NAME_13_HealthCareCentre +"( " + TABLE_13_COL_AddressLine1  + " TEXT, " + TABLE_13_COL_AddressLine2 + " TEXT, " + TABLE_13_COL_AddressLine3 + " TEXT, "
                + TABLE_13_COL_HC_BULDING_LAT_LONG + " TEXT, " + TABLE_13_COL_HC_Mobile_Number + " TEXT, " + TABLE_13_COL_isPushed + " TEXT)");

        db.execSQL("create table " + TABLE_NAME_14_Rationalization +"( " + TABLE_14_COL_SerialNumbers  + " TEXT, " + TABLE_14_COL_StreetNumber + " TEXT, " + TABLE_14_COL_StreetStartLocation + " TEXT, "
                + TABLE_14_COL_StreetMidLocation + " TEXT, "+ TABLE_14_COL_StreetEndLocation + " TEXT, " + TABLE_14_COL_BuildingNumber + " TEXT, " + TABLE_14_COL_BuildingGPSLocation + " TEXT, "+ TABLE_14_COL_HouseNumber + " TEXT, "+ TABLE_14_COL_DoorNumber + " TEXT, "
                + TABLE_14_COL_isPushed + " TEXT)");


        db.execSQL("create table " + TABLE_NAME_15_Unenrolled +"( " + TABLE_15_COL_NAME  + " TEXT, " + TABLE_15_COL_RLN_NAME + " TEXT, " + TABLE_15_COL_SLNO_OF_RLN + " TEXT, "
                + TABLE_15_COL_RLN_TYPE + " TEXT, "+ TABLE_15_COL_MOBILE_NO + " TEXT, " + TABLE_15_COL_MOBILE_TYPE + " TEXT, " + TABLE_15_COL_EMAIL_ID + " TEXT, "+ TABLE_15_COL_FORM6_REF_NO + " TEXT, "+ TABLE_15_COL_IS_FILLED_FORM_COLLECTED + " TEXT, "
                + TABLE_15_COL_isPushed + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME1_VOTER_LIST +" ; ");
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME2_ASD_SAVED_DATA +" ; ");
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME3_BLO_CREDENTIALS +" ; ");
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME4_OVERSEAS_VOTER + " ; ");
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME5_PROBABLE_POLLING_STATION + " ; ");
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME6_FAMILY_GPS_LOCATION + " ; ");
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME7_EXISTING_POLLING_STATION + " ; ");
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_8_PostOfficeAddressLocations + " ; ");
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_9_FutureVotersList + " ; ");
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_10_SHOW_DASHBOARD + " ; ");
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME11_AUXILIARY_POLLING_STATION + " ; ");
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_12_PoliceStationLocations + " ; ");
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_13_HealthCareCentre + " ; ");
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_14_Rationalization + " ; ");
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_15_Unenrolled + " ; ");
        onCreate( db);

    }

    public int count(String tablename){
        int count;
        SQLiteDatabase db = this.getWritableDatabase();
        count = (int) DatabaseUtils.queryNumEntries(db, tablename);
        db.close();

        return count;
    }

    public boolean insertData(String SLNOINPART, String EPIC_NO, String Name, String RLN_Name, String GENDER, int AGE, String ADDRESS,
                              String HOUSE_NO, String RLN_TYPE, String isWorkDone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_1_COL_SLNOINPART, SLNOINPART);
        contentValues.put(TABLE_1_COL_EPIC_NO, EPIC_NO);
        contentValues.put(TABLE_1_COL_Name, Name);
        contentValues.put(TABLE_1_COL_RLN_Name, RLN_Name);
        contentValues.put(TABLE_1_COL_GENDER, GENDER);
        contentValues.put(TABLE_1_COL_AGE, AGE);
        contentValues.put(TABLE_1_COL_ADDRESS, ADDRESS);
        contentValues.put(TABLE_1_COL_HOUSE_NO, HOUSE_NO);
        contentValues.put(TABLE_1_COL_RLN_TYPE, RLN_TYPE);
        contentValues.put(TABLE_1_COL_isWORKDONE,isWorkDone);
        long result = db.insertWithOnConflict(TABLE_NAME1_VOTER_LIST,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        if(result == -1){
            return false;
        }else {
            return true;
        }

    }

    public boolean insertFamilyGPSLocationData(String HOF_SLNO, String HOF_GENDER, String HOF_RLN_SLNO, String HOF_RLN_GENDER, String S_SLNO, String DM_SLNO,String DU_SLNO,String lantlong, String PushedFlag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_6_COL_HOF_SLNO, HOF_SLNO);
        contentValues.put(TABLE_6_COL_HOF_GENDER, HOF_GENDER);
        contentValues.put(TABLE_6_COL_HOF_RLN_SLNO, HOF_RLN_SLNO);
        contentValues.put(TABLE_6_COL_HOF_RLN_GENDER, HOF_RLN_GENDER);
        contentValues.put(TABLE_6_COL_S_SLNO, S_SLNO);
        contentValues.put(TABLE_6_COL_DM_SLNO, DM_SLNO);
        contentValues.put(TABLE_6_COL_DU_SLNO, DU_SLNO);
        contentValues.put(TABLE_6_COL_LagLong, lantlong);
        contentValues.put(TABLE_6_COL_isPushed, PushedFlag);
        // long result = db.insert(TABLE_NAME2_ASD_SAVED_DATA,null,contentValues);
        long result = db.insertWithOnConflict(TABLE_NAME6_FAMILY_GPS_LOCATION,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        if(result == -1){
            return false;
        }else {
            return true;
        }

    }

    public boolean insertASDData(int SerialNo, String Reason, String UserMobileNo, String MobileOwnedType, String MobileType, String ASDstatus, String Date,
                              String rlnname ,String isDivyangjan, String isPrNpr, String isDvoter, String isImportantElector, String PushedFlag, String pwd, String form7, String Sform7ref, String Sform8ref) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_2_COL_SerialNo, SerialNo);
        contentValues.put(TABLE_2_COL_Reason, Reason);
        contentValues.put(TABLE_2_COL_UserMobileNo, UserMobileNo);
        contentValues.put(TABLE_2_COL_MobileOwnedType, MobileOwnedType);
        contentValues.put(TABLE_2_COL_MobileType, MobileType);
        contentValues.put(TABLE_2_COL_ASDstatus, ASDstatus);
        contentValues.put(TABLE_2_COL_Date, Date);
        contentValues.put(TABLE_2_COL_RLN_Name,rlnname);
        contentValues.put(TABLE_2_COL_isDivyangjan,isDivyangjan);
        contentValues.put(TABLE_2_COL_isPRVoter,isPrNpr);
        contentValues.put(TABLE_2_COL_isDVoter,isDvoter);
        contentValues.put(TABLE_2_COL_isImportantElector,isImportantElector);
        contentValues.put(TABLE_2_COL_PushedFlag, PushedFlag);
        contentValues.put(TABLE_2_COL_IS_PWD, pwd);
        contentValues.put(TABLE_2_COL_IS_FILLED_FORM_COLLECTED,form7);
        contentValues.put(TABLE_2_COL_FORM7_REF_NO,Sform7ref);
        contentValues.put(TABLE_2_COL_FORM8_REF_NO,Sform8ref);
       // long result = db.insert(TABLE_NAME2_ASD_SAVED_DATA,null,contentValues);
        long result = db.insertWithOnConflict(TABLE_NAME2_ASD_SAVED_DATA,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        if(result == -1){
            return false;
        }else {
            return true;
        }

    }

    public boolean insertOVERSEASData(String SLNO_INPART, String PASSPORT_NO, String DOB, String EMAIL_ID, String MOBILE_NO, String MOBILE_TYPE, String PLATFORM_TYPE,
                                 String PushedFlag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_4_COL_SLNO_INPART, SLNO_INPART);
        contentValues.put(TABLE_4_COL_PASSPORT_NO, PASSPORT_NO);
        contentValues.put(TABLE_4_COL_DOB, DOB);
        contentValues.put(TABLE_4_COL_EMAIL_ID, EMAIL_ID);
        contentValues.put(TABLE_4_COL_MOBILE_NO, MOBILE_NO);
        contentValues.put(TABLE_4_COL_MOBILE_TYPE, MOBILE_TYPE);
        contentValues.put(TABLE_4_COL_PLATFORM_TYPE, PLATFORM_TYPE);
        contentValues.put(TABLE_4_COL_isPushed, PushedFlag);
        // long result = db.insert(TABLE_NAME2_ASD_SAVED_DATA,null,contentValues);
        long result = db.insertWithOnConflict(TABLE_NAME4_OVERSEAS_VOTER,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        if(result == -1){
            return false;
        }else {
            return true;
        }

    }

    public boolean insertUNENROLLEDData(String NAME, String RLN_NAME, String SLNO_OF_RLN, String RLN_TYPE, String MOBILE_NO, String MOBILE_TYPE, String EMAIL_ID,
                                        String FORM6_REF_NO, String IS_FILLED_FORM_COLLECTED,String isPushed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_15_COL_NAME, NAME);
        contentValues.put(TABLE_15_COL_RLN_NAME, RLN_NAME);
        contentValues.put(TABLE_15_COL_SLNO_OF_RLN, SLNO_OF_RLN);
        contentValues.put(TABLE_15_COL_RLN_TYPE, RLN_TYPE);
        contentValues.put(TABLE_15_COL_MOBILE_NO, MOBILE_NO);
        contentValues.put(TABLE_15_COL_MOBILE_TYPE, MOBILE_TYPE);
        contentValues.put(TABLE_15_COL_EMAIL_ID, EMAIL_ID);
        contentValues.put(TABLE_15_COL_FORM6_REF_NO, FORM6_REF_NO);
        contentValues.put(TABLE_15_COL_IS_FILLED_FORM_COLLECTED, IS_FILLED_FORM_COLLECTED);
        contentValues.put(TABLE_15_COL_isPushed, isPushed);
        // long result = db.insert(TABLE_NAME2_ASD_SAVED_DATA,null,contentValues);
        long result = db.insertWithOnConflict(TABLE_NAME_15_Unenrolled,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        if(result == -1){
            return false;
        }else {
            return true;
        }

    }


    public boolean insertExistingPSData(String AddressLine1, String AddressLine2, String AddressLine3, String BTCode, String PRIORITY_CHOICE, String BULDING_LAT_LONG, String PHOTOGRAPH,
                                      String PushedFlag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_7_COL_AddressLine1, AddressLine1);
        contentValues.put(TABLE_7_COL_AddressLine2, AddressLine2);
        contentValues.put(TABLE_7_COL_AddressLine3, AddressLine3);
        contentValues.put(TABLE_7_COL_PS_BUILDING_TYPE_CODE, BTCode);
        contentValues.put(TABLE_7_COL_PRIORITY_CHOICE, PRIORITY_CHOICE);
        contentValues.put(TABLE_7_COL_PS_BULDING_LAT_LONG, BULDING_LAT_LONG);
        contentValues.put(TABLE_7_COL_BUILDING_PHOTOGRAPH, PHOTOGRAPH);
        contentValues.put(TABLE_7_COL_isPushed, PushedFlag);
        // long result = db.insert(TABLE_NAME2_ASD_SAVED_DATA,null,contentValues);
        long result = db.insertWithOnConflict(TABLE_NAME7_EXISTING_POLLING_STATION,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        if(result == -1){
            return false;
        }else {
            return true;
        }

    }

    public boolean insertAuxiliaryPSData(String AddressLine1, String AddressLine2, String AddressLine3, String BTCode, String PRIORITY_CHOICE, String BULDING_LAT_LONG, String PHOTOGRAPH,
                                        String PushedFlag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_11_COL_AddressLine1, AddressLine1);
        contentValues.put(TABLE_11_COL_AddressLine2, AddressLine2);
        contentValues.put(TABLE_11_COL_AddressLine3, AddressLine3);
        contentValues.put(TABLE_11_COL_PS_BUILDING_TYPE_CODE, BTCode);
        contentValues.put(TABLE_11_COL_PRIORITY_CHOICE, PRIORITY_CHOICE);
        contentValues.put(TABLE_11_COL_PS_BULDING_LAT_LONG, BULDING_LAT_LONG);
        contentValues.put(TABLE_11_COL_BUILDING_PHOTOGRAPH, PHOTOGRAPH);
        contentValues.put(TABLE_11_COL_isPushed, PushedFlag);
        // long result = db.insert(TABLE_NAME2_ASD_SAVED_DATA,null,contentValues);
        long result = db.insertWithOnConflict(TABLE_NAME11_AUXILIARY_POLLING_STATION,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        if(result == -1){
            return false;
        }else {
            return true;
        }

    }

    public boolean insertPOGPSLocationData(String AddressLine1, String AddressLine2, String AddressLine3, String BULDING_LAT_LONG, String Areapincode,
                                        String PushedFlag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_8_COL_AddressLine1, AddressLine1);
        contentValues.put(TABLE_8_COL_AddressLine2, AddressLine2);
        contentValues.put(TABLE_8_COL_AddressLine3, AddressLine3);
        contentValues.put(TABLE_8_COL_PS_BULDING_LAT_LONG, BULDING_LAT_LONG);
        contentValues.put(TABLE_8_COL_PO_AREA_PINCODE, Areapincode);
        contentValues.put(TABLE_8_COL_isPushed, PushedFlag);
        // long result = db.insert(TABLE_NAME2_ASD_SAVED_DATA,null,contentValues);
        long result = db.insertWithOnConflict(TABLE_NAME_8_PostOfficeAddressLocations,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        if(result == -1){
            return false;
        }else {
            return true;
        }

    }

    public boolean insertPOLICELocationData(String AddressLine1, String AddressLine2, String AddressLine3, String BULDING_LAT_LONG, String mob,
                                           String PushedFlag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_12_COL_AddressLine1, AddressLine1);
        contentValues.put(TABLE_12_COL_AddressLine2, AddressLine2);
        contentValues.put(TABLE_12_COL_AddressLine3, AddressLine3);
        contentValues.put(TABLE_12_COL_PS_BULDING_LAT_LONG, BULDING_LAT_LONG);
        contentValues.put(TABLE_12_COL_PS_Mobile_Number, mob);
        contentValues.put(TABLE_12_COL_isPushed, PushedFlag);
        // long result = db.insert(TABLE_NAME2_ASD_SAVED_DATA,null,contentValues);
        long result = db.insertWithOnConflict(TABLE_NAME_12_PoliceStationLocations,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        if(result == -1){
            return false;
        }else {
            return true;
        }

    }

    public boolean insertHealthCareCentreData(String AddressLine1, String AddressLine2, String AddressLine3, String BULDING_LAT_LONG,
                                            String mob ,String PushedFlag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_13_COL_AddressLine1, AddressLine1);
        contentValues.put(TABLE_13_COL_AddressLine2, AddressLine2);
        contentValues.put(TABLE_13_COL_AddressLine3, AddressLine3);
        contentValues.put(TABLE_13_COL_HC_BULDING_LAT_LONG, BULDING_LAT_LONG);
        contentValues.put(TABLE_13_COL_HC_Mobile_Number, mob);
        contentValues.put(TABLE_13_COL_isPushed, PushedFlag);
        // long result = db.insert(TABLE_NAME2_ASD_SAVED_DATA,null,contentValues);
        long result = db.insertWithOnConflict(TABLE_NAME_13_HealthCareCentre,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        if(result == -1){
            return false;
        }else {
            return true;
        }

    }

    public boolean insertProbablePSData(String AddressLine1, String AddressLine2, String AddressLine3, String BTCode, String PRIORITY_CHOICE, String BULDING_LAT_LONG, String PHOTOGRAPH,
                                        String q1, String q2, String q3, String q4, String q5, String q6, String q7, String q8, String q9, String q10, String q11, String q12, String q13,
                                        String q14, String q15, String q16, String q17, String q18, String q19, String q20, String q21, String q22, String q23, String q24, String q25, String PushedFlag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_5_COL_AddressLine1, AddressLine1);
        contentValues.put(TABLE_5_COL_AddressLine2, AddressLine2);
        contentValues.put(TABLE_5_COL_AddressLine3, AddressLine3);
        contentValues.put(TABLE_5_COL_PS_BUILDING_TYPE_CODE, BTCode);
        contentValues.put(TABLE_5_COL_PRIORITY_CHOICE, PRIORITY_CHOICE);
        contentValues.put(TABLE_5_COL_PS_BULDING_LAT_LONG, BULDING_LAT_LONG);
        contentValues.put(TABLE_5_COL_BUILDING_PHOTOGRAPH, PHOTOGRAPH);
        contentValues.put(TABLE_5_COL_IS_POLLINGBOOTH_AREA_IS_GT_20SQM,q1);
        contentValues.put(TABLE_5_COL_IS_BUILDING_DILAPIDATED_OR_DANGEROUS,q2);
        contentValues.put(TABLE_5_COL_IS_PS_IN_GOV_BUILDING_OR_PREMISIS,q3);
        contentValues.put(TABLE_5_COL_IS_PS_LOCATERD_IN_INSTITUTION_OR_RELIGIOUS_PLACE,q4);
        contentValues.put(TABLE_5_COL_IS_PS_IN_SCHOOL_OR_COLLEGE_BUILDING,q5);
        contentValues.put(TABLE_5_COL_IS_PS_ON_GROUND_FLOOR,q6);
        contentValues.put(TABLE_5_COL_IS_SEPARATE_DOOR_FOR_ENTRY_N_EXIT,q7);
        contentValues.put(TABLE_5_COL_IS_ANY_POLITICAL_PARTY_WITHIN_200MTR_PS_PREMISES,q8);
        contentValues.put(TABLE_5_COL_IS_DRINKING_WATER_FACILITY_AVAILABLE,q9);
        contentValues.put(TABLE_5_COL_IS_ELECTRICITY_SUPPLY_AVAILABLE_IN_BUILDING,q10);
        contentValues.put(TABLE_5_COL_IS_PROPER_LIGHTING_AVAILABLE_IN_BUILDING,q11);
        contentValues.put(TABLE_5_COL_IS_TOILET_FACILITY_AVAILABLE_IN_BUILDING,q12);
        contentValues.put(TABLE_5_COL_IS_RAMP_PROVIDED,q13);
        contentValues.put(TABLE_5_COL_IS_ADEQUATE_FURNITURE_AVAILABLE_IN_BUILDING,q14);
        contentValues.put(TABLE_5_COL_IS_SHADE_OR_SHELTER_AVAILABLE,q15);
        contentValues.put(TABLE_5_COL_IS_PROPER_ROAD_CONNECTIVITY_AVAILABLE,q16);
        contentValues.put(TABLE_5_COL_IS_ANY_RIVER_OR_VALLY_TO_CROSS_TO_REACH_PS,q17);
        contentValues.put(TABLE_5_COL_IS_PS_HAS_LANDLINE_TELEPHONE_OR_FAX,q18);
        contentValues.put(TABLE_5_COL_IS_PS_HAS_MOBILE_CONNECTIVITY,q19);
        contentValues.put(TABLE_5_COL_IS_PS_HAS_INTERNET_FACILITY,q20);
        contentValues.put(TABLE_5_COL_IS_PS_HAS_PROPER_SIGNAGE_OF_BUILDING_NAME_N_ADDRESS,q21);
        contentValues.put(TABLE_5_COL_IS_PS_WITHIN_INSURGENCY_AFFECTED_AREA,q22);
        contentValues.put(TABLE_5_COL_IS_PS_WITHIN_FOREST_OR_SEMIFOREST_AREA,q23);
        contentValues.put(TABLE_5_COL_IS_PS_IN_VULNERABLE_CRITICAL_LOCATION,q24);
        contentValues.put(TABLE_5_COL_IS_PS_SENSITIVE_OR_HYPER_SENSITIVE,q25);
        contentValues.put(TABLE_5_COL_isPushed, PushedFlag);
        // long result = db.insert(TABLE_NAME2_ASD_SAVED_DATA,null,contentValues);
        long result = db.insertWithOnConflict(TABLE_NAME5_PROBABLE_POLLING_STATION,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        if(result == -1){
            return false;
        }else {
            return true;
        }

    }

    public boolean insertBLOCredentials(int AcNo, int PartNo, String BLOName, String BLOMobileNo, String StateCode, String Password, String isdisabled, String key) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_3_COL_AcNo, AcNo);
        contentValues.put(TABLE_3_COL_PartNo, PartNo);
        contentValues.put(TABLE_3_COL_BLOName, BLOName);
        contentValues.put(TABLE_3_COL_BLOMobileNo, BLOMobileNo);
        contentValues.put(TABLE_3_COL_StateCode, StateCode);
        contentValues.put(TABLE_3_COL_Password, Password);
        contentValues.put(TABLE_3_COL_IsDisabledFlag, isdisabled);
        contentValues.put(TABLE_3_COL_Key,key);
        long result = db.insertWithOnConflict(TABLE_NAME3_BLO_CREDENTIALS,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        if(result == -1){
            return false;
        }else {
            return true;
        }

    }

    public boolean insertFutureVotersData(String Name, String RLN_Name, String DOB, String gender, String isdisabled) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_9_COL_NAME, Name);
        contentValues.put(TABLE_9_COL_RLN_NAME, RLN_Name);
        contentValues.put(TABLE_9_COL_DOB, DOB);
        contentValues.put(TABLE_9_COL_GENDER, gender);
        contentValues.put(TABLE_9_COL_isPushed, isdisabled);
        long result = db.insertWithOnConflict(TABLE_NAME_9_FutureVotersList,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        if(result == -1){
            return false;
        }else {
            return true;
        }

    }

    public boolean insertRationalizationData(String slno, String StreetNumber, String StreetStartPoint, String StreetMidPoint, String StreetStreetEndPoint, String BuildingNo,String HOUSE_NO,String DoorNo, String BuildingGPS, String isPushed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_14_COL_SerialNumbers, slno);
        contentValues.put(TABLE_14_COL_StreetNumber, StreetNumber);
        contentValues.put(TABLE_14_COL_StreetStartLocation, StreetStartPoint);
        contentValues.put(TABLE_14_COL_StreetMidLocation, StreetMidPoint);
        contentValues.put(TABLE_14_COL_StreetEndLocation, StreetStreetEndPoint);
        contentValues.put(TABLE_14_COL_BuildingNumber, BuildingNo);
        contentValues.put(TABLE_14_COL_HouseNumber, HOUSE_NO);
        contentValues.put(TABLE_14_COL_DoorNumber, DoorNo);
        contentValues.put(TABLE_14_COL_BuildingGPSLocation, BuildingGPS);
        contentValues.put(TABLE_14_COL_isPushed,isPushed);
        long result = db.insertWithOnConflict(TABLE_NAME_14_Rationalization,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        if(result == -1){
            return false;
        }else {
            return true;
        }

    }


    public JSONArray SelectFamilyGPSData(String whereCondition){
        JSONArray jsonArray= new JSONArray();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+ TABLE_NAME6_FAMILY_GPS_LOCATION + " where " + TABLE_6_COL_isPushed + "=" + whereCondition + ";" , null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                {
                    try
                    {
                        switch (cursor.getColumnName(i)){
                            case TABLE_6_COL_HOF_SLNO:{
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_6_COL_HOF_SLNO, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_6_COL_HOF_GENDER:{
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_6_COL_HOF_GENDER, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_6_COL_HOF_RLN_SLNO:{
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_6_COL_HOF_RLN_SLNO, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_6_COL_HOF_RLN_GENDER:{
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_6_COL_HOF_RLN_GENDER, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_6_COL_S_SLNO:{
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_6_COL_S_SLNO, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_6_COL_DM_SLNO:{
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_6_COL_DM_SLNO, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_6_COL_DU_SLNO:{
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_6_COL_DU_SLNO, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_6_COL_LagLong : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_6_COL_LagLong, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }

                        }

                    }
                    catch( Exception e )
                    {
                        Log.d("TAG_NAME", e.getMessage()  );
                    }
                }
            }
            jsonArray.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("DBHelper_jsonArray", jsonArray.toString() );
        return jsonArray;
    }

    public JSONArray SelectSearchedData(String whereCondition){
        JSONArray jsonArray= new JSONArray();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+ TABLE_NAME1_VOTER_LIST + " where " + TABLE_1_COL_SLNOINPART + "=" + whereCondition + ";" , null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                {
                    try
                    {
                        switch (cursor.getColumnName(i)){
                            case TABLE_1_COL_Name:{
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_1_COL_Name, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                            }
                            case TABLE_1_COL_EPIC_NO : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_1_COL_EPIC_NO, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                            }
                            case TABLE_1_COL_GENDER : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_1_COL_GENDER, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                            }
                            case TABLE_1_COL_SLNOINPART : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_1_COL_SLNOINPART, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                            }
                            case TABLE_1_COL_AGE : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_1_COL_AGE, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                            }
                            case TABLE_1_COL_ADDRESS : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_1_COL_ADDRESS, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                            }
                            case TABLE_1_COL_RLN_Name : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_1_COL_RLN_Name, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                            }

                        }

                    }
                    catch( Exception e )
                    {
                        Log.d("TAG_NAME", e.getMessage()  );
                    }
                }
            }
            jsonArray.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("DBHelper_jsonArray", jsonArray.toString() );
        return jsonArray;
    }


    public JSONArray SelectASDData(String whereCondition){
        JSONArray jsonArray= new JSONArray();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+ TABLE_NAME2_ASD_SAVED_DATA + " where " + TABLE_2_COL_PushedFlag + "=" + whereCondition + ";" , null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                {
                    try
                    {
                        switch (cursor.getColumnName(i)){
                            case TABLE_2_COL_SerialNo:{
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_2_COL_SerialNo, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_2_COL_Reason : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_2_COL_Reason, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_2_COL_UserMobileNo : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_2_COL_UserMobileNo, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_2_COL_MobileOwnedType : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_2_COL_MobileOwnedType, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_2_COL_MobileType : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_2_COL_MobileType, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_2_COL_ASDstatus : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_2_COL_ASDstatus, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_2_COL_isDivyangjan : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_2_COL_isDivyangjan, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_2_COL_isPRVoter : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_2_COL_isPRVoter, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_2_COL_isDVoter : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_2_COL_isDVoter, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_2_COL_isImportantElector : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_2_COL_isImportantElector, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_2_COL_IS_PWD : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_2_COL_IS_PWD, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_2_COL_IS_FILLED_FORM_COLLECTED : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_2_COL_IS_FILLED_FORM_COLLECTED, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_2_COL_FORM7_REF_NO : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_2_COL_FORM7_REF_NO, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_2_COL_FORM8_REF_NO : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_2_COL_FORM8_REF_NO, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }

                        }

                    }
                    catch( Exception e )
                    {
                        Log.d("TAG_NAME", e.getMessage()  );
                    }
                }
            }
            jsonArray.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("DBHelper_jsonArrayASD", jsonArray.toString() );
        return jsonArray;
    }

    public JSONArray SelectOverseasData(String whereCondition){
        JSONArray jsonArray= new JSONArray();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+ TABLE_NAME4_OVERSEAS_VOTER + " where " + TABLE_4_COL_isPushed + "=" + whereCondition + ";" , null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                {
                    try
                    {
                        switch (cursor.getColumnName(i)){
                            case TABLE_4_COL_SLNO_INPART:{
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_4_COL_SLNO_INPART, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_4_COL_PASSPORT_NO : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_4_COL_PASSPORT_NO, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_4_COL_DOB : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_4_COL_DOB, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_4_COL_EMAIL_ID : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_4_COL_EMAIL_ID, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_4_COL_MOBILE_NO : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_4_COL_MOBILE_NO, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_4_COL_MOBILE_TYPE : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_4_COL_MOBILE_TYPE, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_4_COL_PLATFORM_TYPE : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_4_COL_PLATFORM_TYPE, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }

                        }

                    }
                    catch( Exception e )
                    {
                        Log.d("TAG_NAME", e.getMessage()  );
                    }
                }
            }
            jsonArray.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("DBHelper_jsonArrayOverseas", jsonArray.toString() );
        return jsonArray;
    }

    public JSONArray SelectFutureVoterData(String whereCondition){
        JSONArray jsonArray= new JSONArray();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+ TABLE_NAME_9_FutureVotersList + " where " + TABLE_9_COL_isPushed + "=" + whereCondition + ";" , null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                {
                    try
                    {
                        switch (cursor.getColumnName(i)){
                            case TABLE_9_COL_NAME:{
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_9_COL_NAME, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_9_COL_RLN_NAME : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_9_COL_RLN_NAME, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_9_COL_DOB : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_9_COL_DOB, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_9_COL_GENDER : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_9_COL_GENDER, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }

                        }

                    }
                    catch( Exception e )
                    {
                        Log.d("TAG_NAME", e.getMessage()  );
                    }
                }
            }
            jsonArray.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("DBHelper_jsonArrayASD", jsonArray.toString() );
        return jsonArray;
    }

    public JSONArray SelectPOData(String whereCondition){
        JSONArray jsonArray= new JSONArray();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+ TABLE_NAME_8_PostOfficeAddressLocations + " where " + TABLE_8_COL_isPushed + "=" + whereCondition + ";" , null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                {
                    try
                    {
                        switch (cursor.getColumnName(i)){
                            case TABLE_8_COL_AddressLine1:{
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_8_COL_AddressLine1, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_8_COL_AddressLine2 : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_8_COL_AddressLine2, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_8_COL_AddressLine3 : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_8_COL_AddressLine3, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_8_COL_PS_BULDING_LAT_LONG : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_8_COL_PS_BULDING_LAT_LONG, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_8_COL_PO_AREA_PINCODE : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_8_COL_PO_AREA_PINCODE, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }

                        }

                    }
                    catch( Exception e )
                    {
                        Log.d("TAG_NAME", e.getMessage()  );
                    }
                }
            }
            jsonArray.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("DBHelper_jsonArrayASD", jsonArray.toString() );
        return jsonArray;
    }

    public JSONArray SelectPOLICEData(String whereCondition){
        JSONArray jsonArray= new JSONArray();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+ TABLE_NAME_12_PoliceStationLocations + " where " + TABLE_12_COL_isPushed + "=" + whereCondition + ";" , null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                {
                    try
                    {
                        switch (cursor.getColumnName(i)){
                            case TABLE_12_COL_AddressLine1:{
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_12_COL_AddressLine1, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_12_COL_AddressLine2 : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_12_COL_AddressLine2, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_12_COL_AddressLine3 : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_12_COL_AddressLine3, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_12_COL_PS_BULDING_LAT_LONG : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_12_COL_PS_BULDING_LAT_LONG, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_12_COL_PS_Mobile_Number : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_12_COL_PS_Mobile_Number, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                        }

                    }
                    catch( Exception e )
                    {
                        Log.d("TAG_NAME", e.getMessage()  );
                    }
                }
            }
            jsonArray.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("DBHelper_jsonArrayASD", jsonArray.toString() );
        return jsonArray;
    }

    public JSONArray SelectHealthCareData(String whereCondition){
        JSONArray jsonArray= new JSONArray();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+ TABLE_NAME_13_HealthCareCentre + " where " + TABLE_13_COL_isPushed + "=" + whereCondition + ";" , null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                {
                    try
                    {
                        switch (cursor.getColumnName(i)){
                            case TABLE_13_COL_AddressLine1:{
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_13_COL_AddressLine1, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_13_COL_AddressLine2 : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_13_COL_AddressLine2, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_13_COL_AddressLine3 : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_13_COL_AddressLine3, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_13_COL_HC_BULDING_LAT_LONG : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_13_COL_HC_BULDING_LAT_LONG, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_13_COL_HC_Mobile_Number : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_13_COL_HC_Mobile_Number, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }

                        }

                    }
                    catch( Exception e )
                    {
                        Log.d("TAG_NAME", e.getMessage()  );
                    }
                }
            }
            jsonArray.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("DBHelper_jsonArrayHCC", jsonArray.toString() );
        return jsonArray;
    }

    public JSONArray SelectUnenrolledData(String whereCondition){
        JSONArray jsonArray= new JSONArray();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+ TABLE_NAME_15_Unenrolled + " where " + TABLE_15_COL_isPushed + "=" + whereCondition + ";" , null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                {
                    try
                    {
                        switch (cursor.getColumnName(i)){
                            case TABLE_15_COL_NAME:{
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_15_COL_NAME, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_15_COL_RLN_NAME : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_15_COL_RLN_NAME, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_15_COL_SLNO_OF_RLN : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_15_COL_SLNO_OF_RLN, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_15_COL_RLN_TYPE : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_15_COL_RLN_TYPE, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_15_COL_MOBILE_NO : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_15_COL_MOBILE_NO, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_15_COL_MOBILE_TYPE : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_15_COL_MOBILE_TYPE, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_15_COL_EMAIL_ID : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_15_COL_EMAIL_ID, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_15_COL_FORM6_REF_NO : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_15_COL_FORM6_REF_NO, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_15_COL_IS_FILLED_FORM_COLLECTED : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_15_COL_IS_FILLED_FORM_COLLECTED, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }

                        }

                    }
                    catch( Exception e )
                    {
                        Log.d("TAG_NAME", e.getMessage()  );
                    }
                }
            }
            jsonArray.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("DBHelper_jsonArrayHCC", jsonArray.toString() );
        return jsonArray;
    }

    public JSONArray SelectExistingData(String whereCondition){
        JSONArray jsonArray= new JSONArray();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+ TABLE_NAME7_EXISTING_POLLING_STATION + " where " + TABLE_7_COL_isPushed + "=" + whereCondition + ";" , null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                {
                    try
                    {
                        switch (cursor.getColumnName(i)){
                            case TABLE_7_COL_AddressLine1:{
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_7_COL_AddressLine1, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_7_COL_AddressLine2 : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_7_COL_AddressLine2, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_7_COL_AddressLine3 : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_7_COL_AddressLine3, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_7_COL_PS_BUILDING_TYPE_CODE : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_7_COL_PS_BUILDING_TYPE_CODE, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_7_COL_PRIORITY_CHOICE : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_7_COL_PRIORITY_CHOICE, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_7_COL_PS_BULDING_LAT_LONG : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_7_COL_PS_BULDING_LAT_LONG, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_7_COL_BUILDING_PHOTOGRAPH : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_7_COL_BUILDING_PHOTOGRAPH, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }

                        }

                    }
                    catch( Exception e )
                    {
                        Log.d("TAG_NAME", e.getMessage()  );
                    }
                }
            }
            jsonArray.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("DBHelper_jsonArrayASD", jsonArray.toString() );
        return jsonArray;
    }

    public JSONArray SelectAuxiliaryData(String whereCondition){
        JSONArray jsonArray= new JSONArray();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+ TABLE_NAME11_AUXILIARY_POLLING_STATION + " where " + TABLE_11_COL_isPushed + "=" + whereCondition + ";" , null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                {
                    try
                    {
                        switch (cursor.getColumnName(i)){
                            case TABLE_11_COL_AddressLine1:{
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_11_COL_AddressLine1, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_11_COL_AddressLine2 : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_11_COL_AddressLine2, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_11_COL_AddressLine3 : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_11_COL_AddressLine3, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_11_COL_PS_BUILDING_TYPE_CODE : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_11_COL_PS_BUILDING_TYPE_CODE, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_11_COL_PRIORITY_CHOICE : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_11_COL_PRIORITY_CHOICE, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_11_COL_PS_BULDING_LAT_LONG : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_11_COL_PS_BULDING_LAT_LONG, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_11_COL_BUILDING_PHOTOGRAPH : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_11_COL_BUILDING_PHOTOGRAPH, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }

                        }

                    }
                    catch( Exception e )
                    {
                        Log.d("TAG_NAME", e.getMessage()  );
                    }
                }
            }
            jsonArray.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("DBHelper_jsonArrayAuxiPS", jsonArray.toString() );
        return jsonArray;
    }

    public JSONArray SelectPrabableData(String whereCondition){
        JSONArray jsonArray= new JSONArray();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+ TABLE_NAME5_PROBABLE_POLLING_STATION + " where " + TABLE_5_COL_isPushed + "=" + whereCondition + ";" , null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                {
                    try
                    {
                        switch (cursor.getColumnName(i)){
                            case TABLE_5_COL_AddressLine1:{
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_AddressLine1, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_AddressLine2 : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_AddressLine2, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_AddressLine3 : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_AddressLine3, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_PS_BUILDING_TYPE_CODE : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_PS_BUILDING_TYPE_CODE, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_PRIORITY_CHOICE : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_PRIORITY_CHOICE, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_PS_BULDING_LAT_LONG : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_PS_BULDING_LAT_LONG, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_BUILDING_PHOTOGRAPH : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_BUILDING_PHOTOGRAPH, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_IS_POLLINGBOOTH_AREA_IS_GT_20SQM : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_IS_POLLINGBOOTH_AREA_IS_GT_20SQM, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_IS_BUILDING_DILAPIDATED_OR_DANGEROUS : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_IS_BUILDING_DILAPIDATED_OR_DANGEROUS, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_IS_PS_IN_GOV_BUILDING_OR_PREMISIS : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_IS_PS_IN_GOV_BUILDING_OR_PREMISIS, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_IS_PS_LOCATERD_IN_INSTITUTION_OR_RELIGIOUS_PLACE : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_IS_PS_LOCATERD_IN_INSTITUTION_OR_RELIGIOUS_PLACE, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_IS_PS_IN_SCHOOL_OR_COLLEGE_BUILDING : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_IS_PS_IN_SCHOOL_OR_COLLEGE_BUILDING, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_IS_PS_ON_GROUND_FLOOR : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_IS_PS_ON_GROUND_FLOOR, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_IS_SEPARATE_DOOR_FOR_ENTRY_N_EXIT : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_IS_SEPARATE_DOOR_FOR_ENTRY_N_EXIT, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_IS_ANY_POLITICAL_PARTY_WITHIN_200MTR_PS_PREMISES : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_IS_ANY_POLITICAL_PARTY_WITHIN_200MTR_PS_PREMISES, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_IS_DRINKING_WATER_FACILITY_AVAILABLE : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_IS_DRINKING_WATER_FACILITY_AVAILABLE, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_IS_ELECTRICITY_SUPPLY_AVAILABLE_IN_BUILDING : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_IS_ELECTRICITY_SUPPLY_AVAILABLE_IN_BUILDING, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_IS_PROPER_LIGHTING_AVAILABLE_IN_BUILDING : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_IS_PROPER_LIGHTING_AVAILABLE_IN_BUILDING, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_IS_TOILET_FACILITY_AVAILABLE_IN_BUILDING : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_IS_TOILET_FACILITY_AVAILABLE_IN_BUILDING, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_IS_RAMP_PROVIDED : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_IS_RAMP_PROVIDED, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }

                            case TABLE_5_COL_IS_ADEQUATE_FURNITURE_AVAILABLE_IN_BUILDING : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_IS_ADEQUATE_FURNITURE_AVAILABLE_IN_BUILDING, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_IS_SHADE_OR_SHELTER_AVAILABLE : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_IS_SHADE_OR_SHELTER_AVAILABLE, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_IS_PROPER_ROAD_CONNECTIVITY_AVAILABLE : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_IS_PROPER_ROAD_CONNECTIVITY_AVAILABLE, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_IS_ANY_RIVER_OR_VALLY_TO_CROSS_TO_REACH_PS : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_IS_ANY_RIVER_OR_VALLY_TO_CROSS_TO_REACH_PS, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_IS_PS_HAS_LANDLINE_TELEPHONE_OR_FAX : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_IS_PS_HAS_LANDLINE_TELEPHONE_OR_FAX, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_IS_PS_HAS_MOBILE_CONNECTIVITY : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_IS_PS_HAS_MOBILE_CONNECTIVITY, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_IS_PS_HAS_INTERNET_FACILITY : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_IS_PS_HAS_INTERNET_FACILITY, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_IS_PS_HAS_PROPER_SIGNAGE_OF_BUILDING_NAME_N_ADDRESS : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_IS_PS_HAS_PROPER_SIGNAGE_OF_BUILDING_NAME_N_ADDRESS, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_IS_PS_WITHIN_INSURGENCY_AFFECTED_AREA : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_IS_PS_WITHIN_INSURGENCY_AFFECTED_AREA, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_IS_PS_WITHIN_FOREST_OR_SEMIFOREST_AREA : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_IS_PS_WITHIN_FOREST_OR_SEMIFOREST_AREA, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_IS_PS_IN_VULNERABLE_CRITICAL_LOCATION : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_IS_PS_IN_VULNERABLE_CRITICAL_LOCATION, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                            case TABLE_5_COL_IS_PS_SENSITIVE_OR_HYPER_SENSITIVE : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_5_COL_IS_PS_SENSITIVE_OR_HYPER_SENSITIVE, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                                break;
                            }
                        }

                    }
                    catch( Exception e )
                    {
                        Log.d("TAG_NAME", e.getMessage()  );
                    }
                }
            }
            jsonArray.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("DBHelper_jsonArrayASD", jsonArray.toString() );
        return jsonArray;
    }

    public JSONArray selectBLOCredentials(String whereCondition){
        JSONArray jsonArray= new JSONArray();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+ TABLE_NAME3_BLO_CREDENTIALS + " where " + TABLE_3_COL_BLOMobileNo + "=" + whereCondition + ";" , null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                {
                    try
                    {
                        switch (cursor.getColumnName(i)){
                            case TABLE_3_COL_BLOMobileNo:{
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_3_COL_BLOMobileNo, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                            }
                            case TABLE_3_COL_Password : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_3_COL_Password, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                            }
                            case TABLE_3_COL_IsDisabledFlag : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_3_COL_IsDisabledFlag, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                            }
                            case TABLE_3_COL_StateCode : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_3_COL_StateCode, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                            }
                            case TABLE_3_COL_AcNo : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_3_COL_AcNo, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                            }
                            case TABLE_3_COL_PartNo : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_3_COL_PartNo, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                            }
                            case TABLE_3_COL_BLOName : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_3_COL_BLOName, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                            }
                            case TABLE_3_COL_Key : {
                                if( cursor.getString(i) != null )
                                {
                                    Log.d(TABLE_3_COL_Key, cursor.getString(i) );
                                    rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                                }
                                else
                                {
                                    rowObject.put( cursor.getColumnName(i) ,  "" );
                                }
                            }


                        }

                    }
                    catch( Exception e )
                    {
                        Log.d("TAG_NAME", e.getMessage()  );
                    }
                }
            }
            jsonArray.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("DBHelper_jsonArrayBLOCREDENTIALS", jsonArray.toString() );
        return jsonArray;
    }





 public boolean updatePassword(String whereClause, String newPassword){
     SQLiteDatabase db = this.getWritableDatabase();
     ContentValues contentValues = new ContentValues();
     contentValues.put(TABLE_3_COL_Password, newPassword);
     int result = db.update(TABLE_NAME3_BLO_CREDENTIALS,contentValues,TABLE_3_COL_Password + " = ? ", new String[]{whereClause});
     if(result == -1){
         return false;
     }else {
         return true;
     }
 }

    public boolean updateVoterList(String whereClause, String change){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_1_COL_isWORKDONE, change);
        int result = db.update(TABLE_NAME1_VOTER_LIST,contentValues,TABLE_1_COL_SLNOINPART + " = ? ", new String[]{whereClause});
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean updateASD(String whereClause, String change){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_2_COL_PushedFlag, change);
        int result = db.update(TABLE_NAME2_ASD_SAVED_DATA,contentValues,null , null);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Integer deletePPSdata(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME5_PROBABLE_POLLING_STATION,"isPushed = ?",new String[] {s});
    }

    public Integer deleteGPSdata(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME6_FAMILY_GPS_LOCATION,"isPushed = ?",new String[] {s});
    }

    public Integer deleteEXISTINGdata(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME7_EXISTING_POLLING_STATION,"isPushed = ?",new String[] {s});
    }

    public Integer deletePOdata(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_8_PostOfficeAddressLocations,"isPushed = ?",new String[] {s});
    }

    public Integer deleteFUTUREVOTERdata(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_9_FutureVotersList,"isPushed = ?",new String[] {s});
    }

    public Integer deleteOVERSEASdata(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME4_OVERSEAS_VOTER,"isPushed = ?",new String[] {s});
    }

    public Integer deleteHCdata(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_13_HealthCareCentre,"isPushed = ?",new String[] {s});
    }

    public Integer deletePOLICEdata(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_12_PoliceStationLocations,"isPushed = ?",new String[] {s});
    }

    public Integer deleteAUXILIARYdata(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME11_AUXILIARY_POLLING_STATION,"isPushed = ?",new String[] {s});
    }

    public Integer deleteUnenrolleddata(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_15_Unenrolled,"isPushed = ?",new String[] {s});
    }

 public Cursor getAllDatafromTable1(){
     SQLiteDatabase db = this.getWritableDatabase();
     Cursor cursor = db.rawQuery(" select * from "+ TABLE_NAME1_VOTER_LIST, null) ;
     return cursor;
 }

    public Cursor getVoterListPendingdata(String whereCondition){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME1_VOTER_LIST + " WHERE " + TABLE_1_COL_isWORKDONE + " = ? ", new String[]{ whereCondition });
        Log.d("count getVoterListPendingdata", String.valueOf(cursor.getCount()));
        return cursor;
    }

    public Cursor getAllSearchedDatafromTable1(String whereCondition){
        SQLiteDatabase db = this.getWritableDatabase();
        String [] column = {TABLE_1_COL_SLNOINPART,TABLE_1_COL_EPIC_NO,TABLE_1_COL_Name,TABLE_1_COL_RLN_Name,TABLE_1_COL_GENDER,TABLE_1_COL_AGE,TABLE_1_COL_RLN_TYPE,TABLE_1_COL_ADDRESS};
        String selection = TABLE_1_COL_Name+" LIKE ?";
        Cursor cursor= db.query(true, TABLE_NAME1_VOTER_LIST, new String[] { TABLE_1_COL_SLNOINPART,TABLE_1_COL_EPIC_NO,TABLE_1_COL_Name,TABLE_1_COL_RLN_Name,TABLE_1_COL_GENDER,TABLE_1_COL_AGE,TABLE_1_COL_RLN_TYPE,TABLE_1_COL_ADDRESS }, TABLE_1_COL_Name + " LIKE ?",
                new String[] {"%"+ whereCondition + "%" }, null, null, null,
                null);
        Log.d("count", String.valueOf(cursor.getCount()));
        return cursor;
    }

    public Cursor getASDtobePusheddata(String whereCondition){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME2_ASD_SAVED_DATA + " WHERE " + TABLE_2_COL_PushedFlag + " = ? ", new String[]{ whereCondition });
        Log.d("count", String.valueOf(cursor.getCount()));
        return cursor;
    }

    public Cursor getAllDatafromTable4(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(" select * from "+ TABLE_NAME4_OVERSEAS_VOTER, null) ;
        return cursor;
    }

    public Cursor getAllDatafromTable7(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(" select * from "+ TABLE_NAME7_EXISTING_POLLING_STATION, null) ;
        return cursor;
    }

    public Cursor getAllDatafromTable11(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(" select * from "+ TABLE_NAME11_AUXILIARY_POLLING_STATION, null) ;
        return cursor;
    }

    public Cursor getAllDatafromTable5(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(" select * from "+ TABLE_NAME5_PROBABLE_POLLING_STATION, null) ;
        return cursor;
    }

    public Cursor getAllDatafromTable8(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(" select * from "+ TABLE_NAME_8_PostOfficeAddressLocations, null) ;
        return cursor;
    }
    public Cursor getAllDatafromTable9(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(" select * from "+ TABLE_NAME_9_FutureVotersList, null) ;
        return cursor;
    }

    public Cursor getAllDatafromTable12(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(" select * from "+ TABLE_NAME_12_PoliceStationLocations, null) ;
        return cursor;
    }

    public Cursor getAllDatafromTable13(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(" select * from "+ TABLE_NAME_13_HealthCareCentre, null) ;
        return cursor;
    }

    public Cursor getAllDatafromTable14(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(" select * from "+ TABLE_NAME_14_Rationalization, null) ;
        return cursor;
    }

    public Cursor getAllDatafromTable15(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(" select * from "+ TABLE_NAME_15_Unenrolled, null) ;
        return cursor;
    }


    /*database implementation by Kishore Kumar */

    public void update_Asd_pushed_timestamp(int asd_count){
        SQLiteDatabase db = this.getWritableDatabase();
        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        Cursor c =db.rawQuery("SELECT * FROM "+TABLE_NAME_10_SHOW_DASHBOARD+" WHERE "+TABLE_10_COL_Date+"= ?", new String[] {date});
        Log.i("Number of Records"," :: "+c.getCount());
        if(c.getCount()==0){
            ContentValues cv=new ContentValues();
            cv.put(TABLE_10_COL_Date,date);
            cv.put(TABLE_10_Pushed_Record,asd_count);
            cv.put(TABLE_10_Pushed_TimeStamp,System.currentTimeMillis());
            db.insert(TABLE_NAME_10_SHOW_DASHBOARD,null,cv);
        }
        if(c.getCount()==1){
            db.execSQL("UPDATE "+TABLE_NAME_10_SHOW_DASHBOARD+
                            " SET "+TABLE_10_Pushed_Record+" = "+TABLE_10_Pushed_Record+" + "+asd_count+ " , "
                            +TABLE_10_Pushed_TimeStamp+" = "+System.currentTimeMillis()+
                            " WHERE "+TABLE_10_COL_Date+" = ?;",
                    new String[]{ date });
        }
        c.close();
        db.close();
    }

    public java.util.ArrayList<DashboardClass> update_show_Dashboard(){

        ArrayList<String> date_list=new ArrayList<String>();
        ArrayList<DashboardClass> dashboard_list=new ArrayList<DashboardClass>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(" SELECT DISTINCT "+TABLE_2_COL_Date+" FROM "+ TABLE_NAME2_ASD_SAVED_DATA+" ORDER BY "+TABLE_2_COL_Date+" DESC", null) ;
        if (cursor.moveToFirst() ){
            String[] columnNames = cursor.getColumnNames();
            do {
                for (String name: columnNames) {
                    Log.e("TAG value",String.format("%s: %s\n", name,
                            cursor.getString(cursor.getColumnIndex(name))));
                    date_list.add(cursor.getString(cursor.getColumnIndex(name)));
                }
                //tableString += "\n";

            } while (cursor.moveToNext());
        }

        for(int i=0;i<date_list.size();i++){
            DashboardClass dbc=get_pushed_info(date_list.get(i));
            dbc.setDate(date_list.get(i));
            int countunique=getcount_date(date_list.get(i));
            dbc.setWorkdone(countunique);
            dashboard_list.add(dbc);
        }

        SQLiteDatabase db1 = this.getWritableDatabase();
        int totalwork = (int) DatabaseUtils.queryNumEntries(db1, TABLE_NAME1_VOTER_LIST);
        Collections.sort(dashboard_list,DashboardClass.DateComparator);

        for(int i=dashboard_list.size()-1;i>=0;i--){
            dashboard_list.get(i).setPending_work(totalwork=totalwork-dashboard_list.get(i).getWorkdone());
        }

        cursor.close();

        return dashboard_list;


    }

    private int getcount_date(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        long countunique=DatabaseUtils.queryNumEntries(db, TABLE_NAME2_ASD_SAVED_DATA,
                TABLE_2_COL_Date+"=?", new String[] {date});
        db.close();
        return (int)countunique;
    }

    private DashboardClass get_pushed_info(String date){
        DashboardClass dbc=new DashboardClass();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(" SELECT "+TABLE_10_Pushed_Record+","+TABLE_10_Pushed_TimeStamp+" FROM "+ TABLE_NAME_10_SHOW_DASHBOARD+" WHERE "+TABLE_10_COL_Date+" ='"+date+"'", null) ;
        if (cursor.moveToFirst() ){
            String[] columnNames = cursor.getColumnNames();
            do {
                dbc.setPushed_records(cursor.getInt(0));
                Date date1 = new Date(cursor.getLong(1));
                Format format = new SimpleDateFormat("HH:mm:ss");
                dbc.setPused_timestamp(format.format(date1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return dbc;
    }

    private boolean insert_data(){
        /*SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME_10_SHOW_DASHBOARD,null,)
        long countunique=DatabaseUtils.queryNumEntries(db, TABLE_NAME2_ASD_SAVED_DATA,
                TABLE_2_COL_Date+"=?", new String[] {date});
        db.close();
        return (int)countunique;*/
        return true;
    };


    /* end of implemention */


}
