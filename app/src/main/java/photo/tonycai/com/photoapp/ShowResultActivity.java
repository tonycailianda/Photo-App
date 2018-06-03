package photo.tonycai.com.photoapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

public class ShowResultActivity extends AppCompatActivity {
    private int OptionSelectionActivity = 1;
    private int AfterSaveActivity = 1;
    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String picturePath = intent.getStringExtra("picPath");
        Boolean filter_a_info = intent.getBooleanExtra("is_filter_a", true);
        Boolean filter_b_info = intent.getBooleanExtra("is_filter_b", true);
        Boolean function_a_info = intent.getBooleanExtra("is_function_a", true);
        Boolean function_b_info = intent.getBooleanExtra("is_function_b", true);
        int rotation_angle = intent.getIntExtra("rotation_angle", 0);
        Bitmap imageSource = BitmapFactory.decodeFile(picturePath);
        ImageFilters filters = new ImageFilters();
        Bitmap afterFilter = imageSource;
        if (filter_a_info == true)
            afterFilter = filters.applyBlackFilter(afterFilter);
        if (filter_b_info == true)
            afterFilter = filters.applyHighlightEffect(afterFilter);
        afterFilter = RotateBitmap(afterFilter, (rotation_angle) * 90);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(afterFilter);
        Button yes = (Button) findViewById(R.id.yes_button);
        Button no = (Button) findViewById(R.id.no_button);
        final Bitmap finalAfterFilter = afterFilter;
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaStore.Images.Media.insertImage(getContentResolver(), finalAfterFilter, "title", "description");
                Intent myIntent = new Intent(ShowResultActivity.this, AfterSaveActivity.class);
                startActivityForResult(myIntent, AfterSaveActivity);
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ShowResultActivity.this, OptionSelectionActivity.class);
                startActivityForResult(myIntent, OptionSelectionActivity);
            }
        });
        //CheckBox checkBox_yes = (CheckBox) findViewById(R.id.yes_checkbox);
        //final CheckBox checkBox_no = (CheckBox) findViewById(R.id.no_checkbox);
//        if (checkBox_yes.isChecked() == true && checkBox_no.isChecked() != true)
//            MediaStore.Images.Media.insertImage(getContentResolver(), afterFilter, "title", "description");
//        else {
//            if (checkBox_no.isChecked() == true && checkBox_yes.isChecked() != true) {
//                Intent myIntent = new Intent(ShowResultActivity.this, OptionSelectionActivity.class);
//                startActivityForResult(myIntent, OptionSelectionActivity);
//            }

}
}

