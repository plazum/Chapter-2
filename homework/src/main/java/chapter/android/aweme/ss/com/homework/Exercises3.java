package chapter.android.aweme.ss.com.homework;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

import chapter.android.aweme.ss.com.homework.model.Message;
import chapter.android.aweme.ss.com.homework.model.PullParser;

/**
 * 大作业:实现一个抖音消息页面,
 * 1、所需的data数据放在assets下面的data.xml这里，使用PullParser这个工具类进行xml解析即可
 * <p>如何读取assets目录下的资源，可以参考如下代码</p>
 * <pre class="prettyprint">
 *
 *         @Override
 *     protected void onCreate(@Nullable Bundle savedInstanceState) {
 *         super.onCreate(savedInstanceState);
 *         setContentView(R.layout.activity_xml);
 *         //load data from assets/data.xml
 *         try {
 *             InputStream assetInput = getAssets().open("data.xml");
 *             List<Message> messages = PullParser.pull2xml(assetInput);
 *             for (Message message : messages) {
 *
 *             }
 *         } catch (Exception exception) {
 *             exception.printStackTrace();
 *         }
 *     }
 * </pre>
 * 2、所需UI资源已放在res/drawable-xxhdpi下面
 *
 * 3、作业中的会用到圆形的ImageView,可以参考 widget/CircleImageView.java
 */
public class Exercises3 extends AppCompatActivity implements MyAdapter.ListItemClickListener{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<Message> messages;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        recyclerView = findViewById(R.id.rv_list);
//        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        try {
            InputStream assetInput = getAssets().open("data.xml");
            messages = PullParser.pull2xml(assetInput);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        MyAdapter mAdapter = new MyAdapter(messages.size(), this);
        mAdapter.setMessages(messages);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(this, ChatActivity.class);
        Bundle budle = new Bundle();
        budle.putString("order",String.valueOf(clickedItemIndex+1));
        intent.putExtras(budle);
        startActivity(intent);
    }
}

class MyAdapter extends RecyclerView.Adapter<MyAdapter.NumberViewHolder> {

    private Message[] messages;

    private static int viewHolderCount;

    private int numberItems;

    private final ListItemClickListener onClickListener;


    public MyAdapter(int numListItems, ListItemClickListener listener) {
        this.numberItems = numListItems;
        this.onClickListener = listener;
        viewHolderCount = 0;
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.im_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        Message message = messages[viewHolderCount];
        viewHolder.userTitle.setText(message.getTitle());
        viewHolder.lastChatTime.setText(message.getTime());
        viewHolder.userDescription.setText(message.getDescription());

        viewHolderCount++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return numberItems;
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView userTitle;
        private TextView userDescription;
        private TextView lastChatTime;

        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);
            userTitle = itemView.findViewById(R.id.tv_title);
            userDescription = itemView.findViewById(R.id.tv_description);
            lastChatTime = itemView.findViewById(R.id.tv_time);

            itemView.setOnClickListener(this);
        }

        public void bind(int position){
            if (messages.length != 0){
                Message message = messages[position];
                userTitle.setText(message.getTitle());
                lastChatTime.setText(message.getTime());
                userDescription.setText(message.getDescription());
            }
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            if (onClickListener != null) {
                onClickListener.onListItemClick(clickedPosition);
            }
        }
    }

    public void setMessages(final List<Message> messages) {
        this.messages = messages.toArray(new Message[messages.size()]);
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
}