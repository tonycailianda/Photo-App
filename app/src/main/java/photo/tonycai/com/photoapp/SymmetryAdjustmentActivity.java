package photo.tonycai.com.photoapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SymmetryAdjustmentActivity extends AppCompatActivity{
    public static String SYMMETRY_AXIS = "Symmetry Axis";


    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symmetry_adjustment);
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
        try {
            String s = getIntent().getStringExtra("byteBit");
            Bitmap imageSource = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(getIntent().getStringExtra("byteBit")));
            Drawable bitmapDraw = new BitmapDrawable(getResources(), imageSource);
            //Drawable bitmapDraw = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteBit"), 0, getIntent().getByteArrayExtra("byteBit").length));
            ImageView imageView = (ImageView) findViewById(R.id.imageViewSymmetry);
            imageView.setImageDrawable(bitmapDraw);
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        }

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
