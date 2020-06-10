package chapter.android.aweme.ss.com.homework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        Intent intent = getIntent();
        String order = intent.getStringExtra("order");
        TextView view = findViewById(R.id.tv_content_info);
        view.setText("我是列表中的第"+order+"个item");
    }
}