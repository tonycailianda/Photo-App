package photo.tonycai.com.photoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class SymmetryAdjustmentActivity extends AppCompatActivity{
    public static String SYMMETRY_AXIS = "Symmetry Axis";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symmetry_adjustment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

    }
}
