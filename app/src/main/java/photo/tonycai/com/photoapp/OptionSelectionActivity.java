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
import android.widget.Switch;

public class OptionSelectionActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button button = (Button) findViewById(R.id.import_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Switch sa = (Switch) findViewById(R.id.switch_filter_a);
            Switch sb = (Switch) findViewById(R.id.switch_filter_b);
            Switch sc = (Switch) findViewById(R.id.switch_function_a);
            Switch sd = (Switch) findViewById(R.id.switch_function_b);

            Intent myIntent = new Intent(OptionSelectionActivity.this, ShowResultActivity.class);
            myIntent.putExtra("is_filter_a", sa.isChecked()); //Optional parameters
            myIntent.putExtra("is_filter_b", sb.isChecked()); //Optional parameters
            myIntent.putExtra("is_function_a", sc.isChecked()); //Optional parameters
            myIntent.putExtra("is_function_b", sd.isChecked()); //Optional parameters
            startActivity(myIntent);
//            Bitmap imageSource = BitmapFactory.decodeFile(picturePath);
//            ImageFilters filters = new ImageFilters();
//            Bitmap afterFilter = filters.applyInvertEffect(imageSource);
//            ImageView imageView = (ImageView) findViewById(R.id.imageView);
//            imageView.setImageBitmap(afterFilter);
        }
    }
}
