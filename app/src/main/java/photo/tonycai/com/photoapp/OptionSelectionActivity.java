package photo.tonycai.com.photoapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;

public class OptionSelectionActivity extends AppCompatActivity{
    private static int RESULT_LOAD_IMAGE = 1;
    private int ShowResultActivity = 1;
    int rotation_data= 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_selection);
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
        String fromWhere = getIntent().getStringExtra("where");
        final Button button = (Button) findViewById(R.id.import_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //button.setText("rale");
                String picturePath = "";
                Switch sa = (Switch) findViewById(R.id.switch_filter_a);
                Switch sb = (Switch) findViewById(R.id.switch_filter_b);
                Switch sc = (Switch) findViewById(R.id.switch_function_a);
                Switch sd = (Switch) findViewById(R.id.switch_function_b);
                Switch se = (Switch) findViewById(R.id.switch_function_c);
                Switch sf = (Switch) findViewById(R.id.switch_filter_c);

                SeekBar seekBar = findViewById(R.id.rotation_seekbar);

                seekBar.setOnSeekBarChangeListener(SeekBarListener);
                Intent myIntent = new Intent(OptionSelectionActivity.this, ShowResultActivity.class);
                myIntent.putExtra("is_filter_a",sa.isChecked()); //Optional parameters
                myIntent.putExtra("is_filter_b", sb.isChecked()); //Optional parameters
                myIntent.putExtra("is_function_a", sc.isChecked()); //Optional parameters
                myIntent.putExtra("is_function_b", sd.isChecked()); //Optional parameters
                myIntent.putExtra("is_function_c", se.isChecked()); //Optional parameters
                myIntent.putExtra("is_filter_c", sf.isChecked()); //Optional parameters
                myIntent.putExtra("picByte", getIntent().getByteArrayExtra("bitmap")); //Optional parameters
                myIntent.putExtra("rotation_angle", rotation_data); //Optional parameters

                //Intent myIntent2 = new Intent(OptionSelectionActivity.this, SymmetryAdjustmentActivity.class);


                startActivityForResult(myIntent,ShowResultActivity);
            }
        });

    }
    SeekBar.OnSeekBarChangeListener SeekBarListener = new SeekBar.OnSeekBarChangeListener()
    {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // TODO Auto-generated method stub
            rotation_data=progress;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub
        }
    };
//    @Override
//    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
////            Uri selectedImage = data.getData();
////            String[] filePathColumn = { MediaStore.Images.Media.DATA };
////
////            Cursor cursor = getContentResolver().query(selectedImage,
////                    filePathColumn, null, null, null);
////            cursor.moveToFirst();
////
////            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
////            String picturePath = cursor.getString(columnIndex);
//            String picturePath = "";
////            cursor.close();
//
//            Switch sa = (Switch) findViewById(R.id.switch_filter_a);
//            Switch sb = (Switch) findViewById(R.id.switch_filter_b);
//            Switch sc = (Switch) findViewById(R.id.switch_function_a);
//            Switch sd = (Switch) findViewById(R.id.switch_function_b);
//
//            SeekBar seekBar = findViewById(R.id.rotation_seekbar);
//
//
//            seekBar.setOnSeekBarChangeListener(SeekBarListener);
//            Intent myIntent = new Intent(OptionSelectionActivity.this, ShowResultActivity.class);
//            myIntent.putExtra("is_filter_a",sa.isChecked()); //Optional parameters
//            myIntent.putExtra("is_filter_b", sb.isChecked()); //Optional parameters
//            myIntent.putExtra("is_function_a", sc.isChecked()); //Optional parameters
//            myIntent.putExtra("is_function_b", sd.isChecked()); //Optional parameters
//            myIntent.putExtra("picPath", picturePath); //Optional parameters
//            myIntent.putExtra("rotation_angle", rotation_data); //Optional parameters
//            startActivityForResult(myIntent,ShowResultActivity);
////            Bitmap imageSource = BitmapFactory.decodeFile(picturePath);
////            ImageFilters filters = new ImageFilters();
////            Bitmap mAfterFilter = filters.applyInvertEffect(imageSource);
////            ImageView imageView = (ImageView) findViewById(R.id.imageView);
////            imageView.setImageBitmap(mAfterFilter);
//        }

    }



