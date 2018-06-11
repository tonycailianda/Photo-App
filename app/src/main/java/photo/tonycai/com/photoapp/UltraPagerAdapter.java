package photo.tonycai.com.photoapp;

/*
 *
 *  MIT License
 *
 *  Copyright (c) 2017 Alibaba Group
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 *
 */


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;

/**
 * Created by mikeafc on 15/11/26.
 */
public class UltraPagerAdapter extends PagerAdapter {
    private OnClickListener mOnclickListener;

//    public abstract class Delegate {
//        public abstract void onClick(View v, int index);
//    }
//    private Delegate mDelegate;
    private boolean isMultiScr;
    private Bitmap mBitmap;
    private Resources mResources;
    private int mTimes;

    public UltraPagerAdapter(boolean isMultiScr, Bitmap bitmap , Resources resources, OnClickListener l ,int time) {
        this.isMultiScr = isMultiScr;
        mBitmap = bitmap;
        mResources = resources;
        mOnclickListener = l;
        mTimes = time;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.layout_child, null);
        //new LinearLayout(container.getContext());
       //T extView textView = (TextView) linearLayout.findViewById(R.id.pager_imageview);
       // textView.setText(position + "");
        linearLayout.setId(R.id.item_id);
        //Intent intent = getIntent();
        //textView.setOnClickListener(new OnClickListener);

        Bitmap comBitmap=mBitmap;
        ImageFilters filter = new ImageFilters();
        switch (position) {
            case 0:
                for (int i=1;i<=mTimes;i++)
                comBitmap = filter.applyEmbossEffect(comBitmap);
                linearLayout.setBackgroundColor(Color.parseColor("#2196F3"));
                linearLayout.setOnClickListener(mOnclickListener);
                ImageView img = linearLayout.findViewById(R.id.pager_imageview);
                Drawable d = new BitmapDrawable(mResources, comBitmap);
                img.setImageDrawable(d);
                break;
            case 1:
                for (int i=1;i<=mTimes;i++)
                comBitmap = filter.applyHighlightEffect(comBitmap);
                linearLayout.setBackgroundColor(Color.parseColor("#673AB7"));
                linearLayout.setOnClickListener(mOnclickListener);
                img = linearLayout.findViewById(R.id.pager_imageview);
                d = new BitmapDrawable(mResources, comBitmap);
                img.setImageDrawable(d);
                break;
            case 2:
                for (int i=1;i<=mTimes;i++)
                comBitmap = filter.applyMeanRemovalEffect(comBitmap);
                linearLayout.setBackgroundColor(Color.parseColor("#009688"));
                linearLayout.setOnClickListener(mOnclickListener);
                img = linearLayout.findViewById(R.id.pager_imageview);
                d = new BitmapDrawable(mResources, comBitmap);
                img.setImageDrawable(d);
                break;
            case 3:
                for (int i=1;i<=mTimes;i++)
                comBitmap = filter.applySnowEffect(comBitmap);
                linearLayout.setBackgroundColor(Color.parseColor("#607D8B"));
                linearLayout.setOnClickListener(mOnclickListener);
                img = linearLayout.findViewById(R.id.pager_imageview);
                d = new BitmapDrawable(mResources, comBitmap);
                img.setImageDrawable(d);
                break;
            case 4:
                for (int i=1;i<=mTimes;i++)
                    comBitmap = filter.applyFleaEffect(comBitmap);
                //comBitmap = filter.applyEmbossEffect(comBitmap);
                linearLayout.setBackgroundColor(Color.parseColor("#F44336"));
                linearLayout.setOnClickListener(mOnclickListener);
                img = linearLayout.findViewById(R.id.pager_imageview);
                d = new BitmapDrawable(mResources, comBitmap);
                img.setImageDrawable(d);
                //linearLayout.setTag((int) 5);
                //linearLayout.setT
                break;


        }
        container.addView(linearLayout);
//        linearLayout.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDelegate.onClick(v, position);
//            }
//        });
//        linearLayout.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, container.getContext().getResources().getDisplayMetrics());
//        linearLayout.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, container.getContext().getResources().getDisplayMetrics());
        return linearLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LinearLayout view = (LinearLayout) object;
        container.removeView(view);
    }
}