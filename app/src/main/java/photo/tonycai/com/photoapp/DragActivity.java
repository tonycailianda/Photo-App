package photo.tonycai.com.photoapp;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Map;
import java.util.Set;

public class DragActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);
//        findViewById(R.id.myimage1).setOnTouchListener(new MyTouchListener());
//        findViewById(R.id.myimage2).setOnTouchListener(new MyTouchListener());
//        findViewById(R.id.myimage3).setOnTouchListener(new MyTouchListener());
//        findViewById(R.id.myimage4).setOnTouchListener(new MyTouchListener());
//        findViewById(R.id.topleft).setOnDragListener(new MyDragListener());
//        findViewById(R.id.topright).setOnDragListener(new MyDragListener());
//        findViewById(R.id.bottomleft).setOnDragListener(new MyDragListener());
//        findViewById(R.id.bottomright).setOnDragListener(new MyDragListener());

        MyTouchListener touchListener = new MyTouchListener();
        MyDragListener dragListener = new MyDragListener();

        int[] dragIds = {R.id.topleft, R.id.topright, R.id.bottom};
        for (int id : dragIds) {
            LinearLayout l = findViewById(id);
            l.setOnDragListener(dragListener);
            for (int i = 0; i < l.getChildCount(); i ++) {
                l.getChildAt(i).setOnTouchListener(touchListener);
            }
        }
        Button button = (Button) findViewById(R.id.next_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout bottom = findViewById(R.id.bottom);
                //Map<String, int> map
                for (int i = 0; i < bottom.getChildCount(); i ++) {
                    String tag = (String) bottom.getChildAt(i).getTag();

                }

                Intent myIntent = new Intent(DragActivity.this,ShowResultActivity.class);
                startActivity(myIntent);
            }
        });
        //Intent myIntent;
    }

    private final class MyTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    class MyDragListener implements OnDragListener {
        Drawable enterShape = getResources().getDrawable(
                R.drawable.shape_droptarget);
        Drawable normalShape = getResources().getDrawable(R.drawable.shape);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    LinearLayout container = (LinearLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }
            return true;
        }
    }
}