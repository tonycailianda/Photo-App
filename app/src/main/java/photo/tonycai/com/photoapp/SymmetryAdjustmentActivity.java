package photo.tonycai.com.photoapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SymmetryAdjustmentActivity extends AppCompatActivity{
    public static String SYMMETRY_AXIS = "Symmetry Axis";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symmetry_adjustment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Drawable bitmapDraw = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteBit"), 0, getIntent().getByteArrayExtra("byteBit").length));
        ImageView imageView = (ImageView) findViewById(R.id.imageViewSymmetry);
        imageView.setImageDrawable(bitmapDraw);
        final Intent myIntent = new Intent(SymmetryAdjustmentActivity.this, ShowResultActivity.class);
        Button up = (Button) findViewById(R.id.up_button);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                myIntent.putExtra("axis", 1);
                setResult(Activity.RESULT_OK, new Intent().putExtra(SYMMETRY_AXIS,"UP"));
                finish();
            }
        });

        Button down = (Button) findViewById(R.id.down_button);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK, new Intent().putExtra(SYMMETRY_AXIS,"DOWN"));
                finish();
            }
        });

        Button left = (Button) findViewById(R.id.left_button);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK, new Intent().putExtra(SYMMETRY_AXIS,"LEFT"));
                finish();
            }
        });

        Button right = (Button) findViewById(R.id.right_button);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK, new Intent().putExtra(SYMMETRY_AXIS,"RIGHT"));
                finish();
            }
        });
        //ImageView imageView = (ImageView) findViewById(R.id.imageViewSymmetry);
        //imageView.setImageDrawable(new BitmapDrawable(getResources(), getIntent().getStringExtra("bitmapURL")));
    }
}
