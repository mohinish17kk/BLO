package mgov.gov.in.blohybrid;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectSubmission extends AppCompatActivity {
    EditText etform6,etepic;
    Button btnsubmitform6;
    TextView tvform6error,tvhome,tvrandamnumber,tvuploadphoto,tvnotice;
    ImageView ivimage;
    DatabaseHelper myDb;
    SharedPreferences prefs;
    String mob,stcode,acno,partno,encImage;
    public static final String MyPREFERENCES = "MyPrefs";
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath="";
    public static final int MY_PERMISSIONS = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_submission);

        etform6 = (EditText)findViewById(R.id.etform6);
        etepic = (EditText) findViewById(R.id.etepic);
        btnsubmitform6 = (Button) findViewById(R.id.btn_submitform6);
        tvform6error =  (TextView) findViewById(R.id.tvform6error);
        tvhome = (TextView) findViewById(R.id.tvhome);
        tvuploadphoto = (TextView) findViewById(R.id.tvuploadphoto);
        ivimage = (ImageView) findViewById(R.id.ivimage);
        tvrandamnumber = (TextView) findViewById(R.id.tvrandamnumber);
        tvnotice = (TextView) findViewById(R.id.tvnotice);

        prefs = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mob  = prefs.getString("mob","");
        stcode = prefs.getString("st_code","");
        acno = prefs.getString("AcNo","");
        partno = prefs.getString("PartNo","");

        Log.d("mobedf",mob);
        Log.d("st_code",stcode);
        Log.d("AcNo",acno);
        Log.d("PartNo",partno);

        tvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DirectSubmission.this,WelcomeBLONew.class);
                startActivity(intent);
                finish();
            }
        });

        tvuploadphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentapiVersion = Build.VERSION.SDK_INT;

                String sc=Integer.toString(currentapiVersion);
                Log.e("API version",sc);
                int Version= Build.VERSION_CODES.LOLLIPOP_MR1;
                String sv=Integer.toString(Version);
                Log.e("API v2",sv);

                Intent i = new Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, SELECT_PICTURE);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyhhmmss");
                String format = simpleDateFormat.format(new Date());
                tvrandamnumber.setText(getApplicationContext().getResources().getString(R.string.Random_number_for_this_form_is)+" "+ format);
                tvnotice.setVisibility(View.GONE);
                tvrandamnumber.setVisibility(View.VISIBLE);
                System.out.println("date : "+ format);

            }
        });

        Log.d("selectedImagePath123",selectedImagePath);
        if(!selectedImagePath.isEmpty())
        {
            File imagefile = new File(selectedImagePath);
            FileInputStream fis = null;
            try{
                fis = new FileInputStream(imagefile);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }
            Bitmap bm = BitmapFactory.decodeStream(fis);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG,30,baos);
            byte[] b = baos.toByteArray();
            encImage = Base64.encodeToString(b, Base64.DEFAULT);
            Log.d("encImage",encImage);

        } else if(selectedImagePath.isEmpty()){ encImage = "";}


        btnsubmitform6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    tvform6error.setText("");
                    tvform6error.setVisibility(View.GONE);
                if(!isphotouploaded()){
                    return;
                }
                    if(etform6.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.ERROR_Please_enter_Form_Type), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (etepic.getText().toString().trim().isEmpty()){
                        Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.ERROR_Please_Mention_Voter_Epic_number), Toast.LENGTH_SHORT).show();
                        return;
                    }
                        final Dialog dialog = new Dialog(DirectSubmission.this);
                        dialog.setContentView(R.layout.custom_blo_report_done_message);
                        Button btnok = (Button) dialog.findViewById(R.id.btnok);
                        dialog.setCanceledOnTouchOutside(false);


                        btnok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(DirectSubmission.this,WelcomeBLONew.class);
                                startActivity(intent);
                                finish();
                                dialog.dismiss();

                            }
                        });
                        dialog.show();
                    }
            //}
        });

    }

    private boolean isphotouploaded() {
        if(selectedImagePath.isEmpty())
        {
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.Form_is_not_uploaded), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImageUri,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();


                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                File file = new File(selectedImagePath);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(picturePath, options);
                int width = options.outWidth;
                int height = options.outHeight;
                String swidth = Integer.toString(width);
                String sheight = Integer.toString(height);
                Log.e("file width ", swidth + "file Height " + sheight);

                long length = file.length();

                String slength = Long.toString(length);
                // if ((selectedImagePath.toLowerCase().endsWith(".jpg") || selectedImagePath.toLowerCase().endsWith(".jpeg")) && (length <= 1048576) && (width == 250 && height == 270)) {
                ivimage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                //tvnofilechoosen.setText(selectedImagePath);
                Log.e("Correct Format", selectedImagePath);
                //tverror.setVisibility(View.GONE);
                // }
                /*else {
                    ivimage.setImageBitmap(null);
                    tvnofilechoosen.setText("No file choosen");
                    final Dialog dialog = new Dialog(UploadPhoto.this);
                    dialog.setContentView(R.layout.custominvalidimage);
                    Button bOK = (Button) dialog.findViewById(R.id.bok);
                    dialog.setCanceledOnTouchOutside(false);


                    bOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                *//*Intent intent = new Intent(getApplicationContext(), MakePayment.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);*//*
                            dialog.dismiss();


                        }
                    });
                    dialog.show();
                    Toast.makeText(UploadPhoto.this, "Invalid Image", Toast.LENGTH_SHORT).show();
                    Log.e("Incorrect format", selectedImagePath);*/
            }
            //Log.e("file size", slength);
        }
    }


    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public boolean hasPermissions(Context context) {
        int permissionCamera = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        int permissionContacts = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS);
        int permissionStorage = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionPhone = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (permissionContacts != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);
        }
        if (permissionStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionPhone != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MY_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("TAG", "Permission callback called-------");
        switch (requestCode) {
            case MY_PERMISSIONS: {

                final Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_CONTACTS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d("TAG", "camera & contacts & storage & state services permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d("TAG", "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)||ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                            showDialogOK("CAMERA,CONTACTS,STORAGE and PHONE STATE Services Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    hasPermissions(DirectSubmission.this);
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }


    }
    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

}
