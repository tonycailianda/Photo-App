package photo.tonycai.com.photoapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanzhenjie.album.Album;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class LandingActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
  //  ImageView mImageView = findViewById(R.id.mImageView);
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private int ACTIVITY_REQUEST_SELECT_PHOTO =1;
    private String TAG;
    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    private void testAlertPermission() {
        WindowManager mWindowManager = (WindowManager) getSystemService(
                Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        mWindowManager.addView(new TextView(this), params);
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    @TargetApi(23)
    private static boolean checkSelfPermissionWrapper(Object cxt, String permission) {
        if (cxt instanceof Activity) {
            Activity activity = (Activity) cxt;
            return ActivityCompat.checkSelfPermission(activity,
                    permission) == PackageManager.PERMISSION_GRANTED;
        } else if (cxt instanceof Fragment) {
            Fragment fragment = (Fragment) cxt;
            return fragment.getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        } else {
            throw new RuntimeException("cxt is net a activity or fragment");
        }
    }

    private static String[] checkSelfPermissionArray(Object cxt, String[] permission) {
        ArrayList<String> permiList = new ArrayList<>();
        for (String p : permission) {
            if (!checkSelfPermissionWrapper(cxt, p)) {
                permiList.add(p);
            }
        }

        return permiList.toArray(new String[permiList.size()]);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);setSupportActionBar(toolbar);


        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                onBackPressed();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out_right);
            }
        });
       // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent i = new Intent(
//////                        Intent.ACTION_PICK,
//////                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
////
////                startActivityForResult(i, RESULT_LOAD_IMAGE);
//                Intent myIntent = new Intent(LandingActivity.this, OptionSelectionActivity.class);
//                //myIntent.putExtra("key", value); //Optional parameters
//                startActivity(myIntent);
//
//            }
//        });
        Button button = (Button) findViewById(R.id.goto_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LandingActivity.this, PagerActivity.class);
                //myIntent.putExtra("key", value); //Optional parameters
                myIntent.putExtra("from",1);
                startActivity(myIntent);
                //dispatchTakePictureIntent();
            }
        });

        Button button2 = (Button) findViewById(R.id.goto_button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LandingActivity.this, PagerActivity.class);
                myIntent.putExtra("from",2);
                startActivity(myIntent);
            }
        });

        Button button3 = (Button) findViewById(R.id.goto_button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LandingActivity.this, DragActivity.class);
                //myIntent.putExtra("from",2);
                startActivity(myIntent);
            }
        });

        //Button dButton = (Button) findViewById(R.id.button_drag);
//        dButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent myIntent = new Intent (LandingActivity.this, PagerActivity.class);
//                startActivity(myIntent);
//            }
//        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        }
    }

