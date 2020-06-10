package chapter.android.aweme.ss.com.chapter2.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import chapter.android.aweme.ss.com.chapter2.R;

public class RelativeLayoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relativelayout);

        TextView tv_center = findViewById(R.id.tv_center);
        int n = ((RelativeLayout) tv_center.getParent()).getChildCount();
        tv_center.setText(String.format("%d", n));
    }
}
