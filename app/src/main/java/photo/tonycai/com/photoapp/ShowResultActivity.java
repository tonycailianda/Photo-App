package photo.tonycai.com.photoapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ShowResultActivity extends AppCompatActivity {
    private int OptionSelectionActivity = 1;
    private int AfterSaveActivity = 1;
    private int SYMMETRY_ADJUSTMENT_ACTIVITY = 1;
    private Bitmap mAfterFilter;
    private String mAxis = "";

    public static Bitmap mergeBitmap_LR(Bitmap leftBitmap, Bitmap rightBitmap, boolean isBaseMax) {

        if (leftBitmap == null || leftBitmap.isRecycled()
                || rightBitmap == null || rightBitmap.isRecycled()) {
            return null;
        }
        int height = 0; // 拼接后的高度，按照参数取大或取小
        if (isBaseMax) {
            height = leftBitmap.getHeight() > rightBitmap.getHeight() ? leftBitmap.getHeight() : rightBitmap.getHeight();
        } else {
            height = leftBitmap.getHeight() < rightBitmap.getHeight() ? leftBitmap.getHeight() : rightBitmap.getHeight();
        }

        // 缩放之后的bitmap
        Bitmap tempBitmapL = leftBitmap;
        Bitmap tempBitmapR = rightBitmap;

        if (leftBitmap.getHeight() != height) {
            tempBitmapL = Bitmap.createScaledBitmap(leftBitmap, (int)(leftBitmap.getWidth()*1f/leftBitmap.getHeight()*height), height, false);
        } else if (rightBitmap.getHeight() != height) {
            tempBitmapR = Bitmap.createScaledBitmap(rightBitmap, (int)(rightBitmap.getWidth()*1f/rightBitmap.getHeight()*height), height, false);
        }

        // 拼接后的宽度
        int width = tempBitmapL.getWidth() + tempBitmapR.getWidth();

        // 定义输出的bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // 缩放后两个bitmap需要绘制的参数
        Rect leftRect = new Rect(0, 0, tempBitmapL.getWidth(), tempBitmapL.getHeight());
        Rect rightRect  = new Rect(0, 0, tempBitmapR.getWidth(), tempBitmapR.getHeight());

        // 右边图需要绘制的位置，往右边偏移左边图的宽度，高度是相同的
        Rect rightRectT  = new Rect(tempBitmapL.getWidth(), 0, width, height);

        canvas.drawBitmap(tempBitmapL, leftRect, leftRect, null);
        canvas.drawBitmap(tempBitmapR, rightRect, rightRectT, null);
        return bitmap;
    }


    /**
     * 把两个位图覆盖合成为一个位图，上下拼接
     * @param leftBitmap
     * @param rightBitmap
     * @param isBaseMax 是否以高度大的位图为准，true则小图等比拉伸，false则大图等比压缩
     * @return
     */
    public static Bitmap mergeBitmap_TB(Bitmap topBitmap, Bitmap bottomBitmap, boolean isBaseMax) {

        if (topBitmap == null || topBitmap.isRecycled()
                || bottomBitmap == null || bottomBitmap.isRecycled()) {
            return null;
        }
        int width = 0;
        if (isBaseMax) {
            width = topBitmap.getWidth() > bottomBitmap.getWidth() ? topBitmap.getWidth() : bottomBitmap.getWidth();
        } else {
            width = topBitmap.getWidth() < bottomBitmap.getWidth() ? topBitmap.getWidth() : bottomBitmap.getWidth();
        }
        Bitmap tempBitmapT = topBitmap;
        Bitmap tempBitmapB = bottomBitmap;

        if (topBitmap.getWidth() != width) {
            tempBitmapT = Bitmap.createScaledBitmap(topBitmap, width, (int)(topBitmap.getHeight()*1f/topBitmap.getWidth()*width), false);
        } else if (bottomBitmap.getWidth() != width) {
            tempBitmapB = Bitmap.createScaledBitmap(bottomBitmap, width, (int)(bottomBitmap.getHeight()*1f/bottomBitmap.getWidth()*width), false);
        }

        int height = tempBitmapT.getHeight() + tempBitmapB.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Rect topRect = new Rect(0, 0, tempBitmapT.getWidth(), tempBitmapT.getHeight());
        Rect bottomRect  = new Rect(0, 0, tempBitmapB.getWidth(), tempBitmapB.getHeight());

        Rect bottomRectT  = new Rect(0, tempBitmapT.getHeight(), width, height);

        canvas.drawBitmap(tempBitmapT, topRect, topRect, null);
        canvas.drawBitmap(tempBitmapB, bottomRect, bottomRectT, null);
        return bitmap;
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap) {
        Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth(), firstBitmap.getHeight(),
                firstBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(firstBitmap, new Matrix(), null);
        canvas.drawBitmap(secondBitmap, 0, 0, null);
        return bitmap;
    }
    public static Bitmap resizeBitmap(Bitmap bm, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
    }

    /**
     * 图片缩放
     *
     * @param bm
     * @param w
     *            缩小或放大成的宽
     * @param h
     *            缩小或放大成的高
     * @return
     */
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

    /**
     * 图片反转
     *
     * @param bm
     * @param flag
     *            0为水平反转，1为垂直反转
     * @return
     */
    public static Bitmap reverseBitmap(Bitmap bmp, int flag) {
        float[] floats = null;
        switch (flag) {
            case 0: // 水平反转
                floats = new float[] { -1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f };
                break;
            case 1: // 垂直反转
                floats = new float[] { 1f, 0f, 0f, 0f, -1f, 0f, 0f, 0f, 1f };
                break;
        }

        if (floats != null) {
            Matrix matrix = new Matrix();
            matrix.setValues(floats);
            return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        }

        return null;
    }

    Bitmap flip(Bitmap d, int a, int b) {
        Matrix m = new Matrix();
        m.preScale(a, b);
        Bitmap dst = Bitmap.createBitmap(d, 0, 0, d.getWidth(), d.getHeight(), m, false);
        dst.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        return dst;
    }

    private void showImage() {
        Intent intent = getIntent();
        byte[] pictureByte = intent.getByteArrayExtra("picByte");
                //intent.getStringExtra("picPath");
        Boolean filter_a_info = intent.getBooleanExtra("is_filter_a", true);
        Boolean filter_b_info = intent.getBooleanExtra("is_filter_b", true);
        Boolean function_a_info = intent.getBooleanExtra("is_function_a", true);
        Boolean function_b_info = intent.getBooleanExtra("is_function_b", true);
        int rotation_angle = intent.getIntExtra("rotation_angle", 6);
        //Bitmap imageSource = BitmapFactory.decodeFile(picturePath);
        Bitmap imageSource = BitmapFactory.decodeByteArray(pictureByte, 0, pictureByte.length);
        ImageFilters filters = new ImageFilters();
        mAfterFilter = imageSource;
        Bitmap afterSymmetry = imageSource;
        if (filter_a_info == true)
            mAfterFilter = filters.applyBlackFilter(mAfterFilter);
        if (filter_b_info == true)
            mAfterFilter = filters.applyHighlightEffect(mAfterFilter);

        if (mAxis.equals("UP")) {
            //mAfterFilter = reverseBitmap((RotateBitmap(mAfterFilter,180)),1);
            mAfterFilter = mergeBitmap_TB(reverseBitmap(mAfterFilter,1),mAfterFilter,false);
            //mAfterFilter = mergeBitmap_TB(mAfterFilter,flip(mAfterFilter, -1, 1),false);
            //mAfterFilter = mergeBitmap_TB(mAfterFilter,reverseBitmap(mAfterFilter,1),false);
        }
        if (mAxis.equals("DOWN")) {
            //mAfterFilter = flip(mAfterFilter, 1, -1);
            //mAfterFilter = mergeBitmap_TB(mAfterFilter,flip(mAfterFilter, 1, -1),false);
            mAfterFilter = mergeBitmap_TB(mAfterFilter,reverseBitmap(mAfterFilter,1),false);
        }
        if (mAxis.equals("LEFT")) {
            mAfterFilter = RotateBitmap(mAfterFilter, 180);
            mAfterFilter = mAfterFilter = mergeBitmap_LR(mAfterFilter,reverseBitmap(mAfterFilter,0),false);
        }
        if (mAxis.equals("RIGHT")) {
            mAfterFilter = mergeBitmap_LR(reverseBitmap(mAfterFilter,0),mAfterFilter,false);
        }
        mAfterFilter = RotateBitmap(mAfterFilter, (rotation_angle) * 90);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(mAfterFilter);

        Button yes = (Button) findViewById(R.id.yes_button);
        Button no = (Button) findViewById(R.id.no_button);
        final Bitmap finalAfterFilter = mAfterFilter;
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
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().getBooleanExtra("is_function_a", false)) {
            Intent myIntent = new Intent(ShowResultActivity.this, SymmetryAdjustmentActivity.class);
            myIntent.putExtra("bitmapURL",getIntent().getStringExtra("picPath"));
            startActivityForResult(myIntent, SYMMETRY_ADJUSTMENT_ACTIVITY);
            //showImage();
        } else {
            showImage();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SYMMETRY_ADJUSTMENT_ACTIVITY && resultCode == RESULT_OK && null != data) {
            mAxis = data.getStringExtra(SymmetryAdjustmentActivity.SYMMETRY_AXIS);
            showImage();
        }

    }
}