package photo.tonycai.com.photoapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.transformer.UltraDepthScaleTransformer;

import java.io.ByteArrayOutputStream;

public class PagerActivity extends AppCompatActivity implements View.OnClickListener{

   final int REQUEST_IMAGE_CAPTURE = 1;

   Bitmap bit;
    public static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imgBytes = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    void updatePager (Bitmap b)
    {
        PagerAdapter adapter = new UltraPagerAdapter(false, b, getResources(), this);
        UltraViewPager ultraViewPager = (UltraViewPager)findViewById(R.id.ultra_viewpager);
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
//UltraPagerAdapter 绑定子view到UltraViewPager

        ultraViewPager.setAdapter(adapter);

//内置indicator初始化
        ultraViewPager.initIndicator();
//设置indicator样式
        ultraViewPager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(Color.GREEN)
                .setNormalColor(Color.WHITE)
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
//设置indicator对齐方式
        ultraViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
//构造indicator,绑定到UltraViewPager
        ultraViewPager.getIndicator().build();
//设定页面循环播放
        ultraViewPager.setInfiniteLoop(true);
//设定页面自动切换  间隔2秒
        ultraViewPager.setAutoScroll(2000);

        ultraViewPager.setMultiScreen(0.5f);
        ultraViewPager.setItemRatio(1.0f);
        ultraViewPager.setAutoMeasureHeight(true);
        ultraViewPager.setPageTransformer(false, new UltraDepthScaleTransformer());
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
            dispatchTakePictureIntent();

//        Intent intent = getIntent();
//        String originStrBit = intent.getStringExtra("originBitmap");
//        mBit = originStrBit;
//        Bitmap originBitmap = StringToBitMap(originStrBit);
//        Drawable originDrawable = new BitmapDrawable(getResources(), originBitmap);
//        Intent myIntent = new Intent(PagerActivity.this, UltraPagerAdapter.class);
//        myIntent.putExtra("StrBit",originStrBit);
        //Bitmap origin = intent.getExtras("originBitmap");


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //ImageView a1 = findViewById(R.id.Auto1);
            //ImageView a2 = findViewById(R.id.Auto2);
            updatePager((Bitmap) data.getExtras().get("data"));
            Button button = (Button) findViewById(R.id.no_satisfied);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Intent myIntent = new Intent(PagerActivity.this, OptionSelectionActivity.class);
                        myIntent.putExtra("where","Pager");
                        myIntent.putExtra("bitmap","");
                        startActivity(myIntent);
                }
            });

//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent myIntent = new Intent(PagerActivity.this, ShowResultActivity.class);
//                    myIntent.putExtra("From Pager",true);
//                    startActivity(myIntent);
//                }
//            });
        }
    }

    @Override
    public void onClick(View v) {
        ImageView img = v.findViewById(R.id.pager_imageview);
        //int position = Integer.parseInt(String.valueOf(v.getTag()));
        //Log.v(String, position + "");
//        if (position!=4)
//        {
            //Intent myIntent = new Intent(PagerActivity.this, ExtraAdding.class);
            //startActivityForResult(myIntent,RESULT_OK);
//        }
//        else
//        {
            BitmapDrawable d = (BitmapDrawable) img.getDrawable();
            MediaStore.Images.Media.insertImage(getContentResolver(), d.getBitmap(), "title", "description");
            Intent myIntent = new Intent(PagerActivity.this, AfterSaveActivity.class);
            startActivityForResult(myIntent,RESULT_OK);
//        }
    }

}
