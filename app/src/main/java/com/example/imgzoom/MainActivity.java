package com.example.imgzoom;

import android.Manifest;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Arrays;
import java.util.List;

import static android.os.Build.*;

public class MainActivity extends AppCompatActivity {

    private DragScaleView dera;
    private View mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dera = findViewById(R.id.drrr);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_LOGS, Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
//        Intent intent = new Intent(this, Main2Activity.class);
//        startActivity(intent);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            init();
        }
    }

    private void init() {
        Log.e("aaaa", "是否离开大翻领短款了考虑就是大翻领设计连放假都是垃圾分类的世界里");
        ImageView imageView = new ImageView(this);
        IconBean tag = (IconBean) imageView.getTag();
        final String able = getResources().getConfiguration().locale.getCountry();
        initRe();
        Button viewById = findViewById(R.id.qie);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (able.equals("CN")) {
                    LanguageUtil.set(true, MainActivity.this);

                } else {
                    LanguageUtil.set(false, MainActivity.this);
                }
            }
        });


    }

    private void initRe() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.re);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<String> strings = Arrays.asList(getResources().getStringArray(R.array.transport_list));
        BenAdpute benAdpute = new BenAdpute(strings, this);

//        BaseQuickAdapter<String, BaseViewHolder> adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_transport,
//                Arrays.asList(getResources().getStringArray(R.array.transport_list))) {
//            @Override
//            protected void convert(BaseViewHolder helper, String item) {
//                TextView tvDescribe = helper.getView(R.id.tv_describe);
//                TextView tvTime = helper.getView(R.id.tv_time);
//                tvDescribe.setText(item);
//                tvTime.setText("2018-06-01 12:00");
//                int position = helper.getAdapterPosition();
//                tvDescribe.setTextColor(position < 2 ? 0xff4caf65 : 0xff999999);
//                tvTime.setTextColor(position < 2 ? 0xff4caf65 : 0xff999999);
//
//            }
//        };
//
//        recyclerView.addItemDecoration(new TimelineDecoration(getDimen(R.dimen.time_line_width),
//                getDimen(R.dimen.time_line_top),
//                ContextCompat.getDrawable(this, R.drawable.ic_check_circle),
//                getDimen(R.dimen.time_going_size),
//                6,MainActivity.this));
        recyclerView.setAdapter(benAdpute);
        recyclerView.addItemDecoration(new RecycleViewPartingLineDivider(this, LinearLayoutManager.HORIZONTAL, 5, R.color.colorAccent));
        recyclerView.addItemDecoration(new TimeLineItemDecoration());


    }

    private int getDimen(int dimeRes) {
        return (int) getResources().getDimension(dimeRes);
    }

    public static class IconBean {
        public int id;//标签的顺序，从0开始
        public float sx;//左边距比例
        public float sy;//上边距比例
        public Drawable drawable;//图标

        public IconBean(int id, float sx, float sy, Drawable drawable) {
            this.id = id;
            this.sx = sx;
            this.sy = sy;
            this.drawable = drawable;
        }
    }
}
