package photo.tonycai.com.photoapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AutomaticAdjustment extends AppCompatActivity {

    final int REQUEST_IMAGE_CAPTURE = 1;
    public static Bitmap resizeBitmap(Bitmap bm, int w, int h) {
        Bitmap BitmapOrg = bm;

        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();

        float scaleWidth = ((float) w) / width;
        float scaleHeight = ((float) h) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(BitmapOrg, 0, 0, width, height, matrix, true);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automatic_adjustment);
        dispatchTakePictureIntent();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            ImageView a1 = findViewById(R.id.Auto1);
            ImageView a2 = findViewById(R.id.Auto2);
            ImageView a3 = findViewById(R.id.Auto3);
            Bundle extras = data.getExtras();
            final Bitmap imageBitmap = (Bitmap) extras.get("data");
            a1.setImageBitmap(resizeBitmap(imageBitmap,256,256));
            a2.setImageBitmap(resizeBitmap(imageBitmap,256,256));
            a3.setImageBitmap(resizeBitmap(imageBitmap,256,256));
            a1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaStore.Images.Media.insertImage(getContentResolver(), imageBitmap, "title", "description");
                    Intent myIntent = new Intent(AutomaticAdjustment.this, AfterSaveActivity.class);
                    startActivityForResult(myIntent,RESULT_OK);
                }
            });
            a2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaStore.Images.Media.insertImage(getContentResolver(), imageBitmap, "title", "description");
                    Intent myIntent = new Intent(AutomaticAdjustment.this, AfterSaveActivity.class);
                    startActivityForResult(myIntent,RESULT_OK);
                }
            });
            a3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaStore.Images.Media.insertImage(getContentResolver(), imageBitmap, "title", "description");
                    Intent myIntent = new Intent(AutomaticAdjustment.this, AfterSaveActivity.class);
                    startActivityForResult(myIntent,RESULT_OK);
                }
            });
            Button button = findViewById(R.id.not_satisfied);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(AutomaticAdjustment.this, OptionSelectionActivity.class);
                    startActivityForResult(myIntent,RESULT_OK);
                }
            });
        }
    }

}
