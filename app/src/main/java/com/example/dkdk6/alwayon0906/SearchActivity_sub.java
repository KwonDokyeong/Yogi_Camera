package com.example.dkdk6.alwayon0906;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by dkdk6 on 2016-09-07.
 */
public class SearchActivity_sub extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_sub_activity);
        TextView textView_location = (TextView) findViewById(R.id.textView_location);
        Intent intent_01 = getIntent();
        String location = intent_01.getStringExtra("장소");
        textView_location.setText(String.valueOf(location));
        GridView gridView=(GridView)findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(this));
    }

    public class ImageAdapter extends BaseAdapter {
        private Context context;
        private Integer[] images={R.drawable.images1,R.drawable.images2};
        public ImageAdapter(Context con){
            this.context=con;
        }
        public int getCount(){
            return images.length;
        }
        public Object getItem(int pos){
            return null;
        }
        public long getItemId(int pos){
            return 0;
        }

        public View getView(final int pos, View convertView, ViewGroup parent){
            final ImageView imageView_sub;
            if(convertView==null){
                imageView_sub=new ImageView(context);
                imageView_sub.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v) {
                        Intent intent = new Intent(SearchActivity_sub.this, ImageClick.class);
                        intent.putExtra("image",images[pos]);
                        startActivity(intent);
                    }
                });
                imageView_sub.setLayoutParams(new GridView.LayoutParams(300,300));
                imageView_sub.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView_sub.setPadding(10,10,10,10);
            }
            else{
                imageView_sub=(ImageView)convertView;
            }
            imageView_sub.setImageResource(images[pos]);
            return imageView_sub;
        }

    }
}
