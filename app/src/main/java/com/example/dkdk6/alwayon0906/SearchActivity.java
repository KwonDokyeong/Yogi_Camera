package com.example.dkdk6.alwayon0906;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SearchActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }
    public void onClick_sch(View v){
        EditText text_location = (EditText) findViewById(R.id.editText_location);
        String location= text_location.getText().toString();
        Intent intent_01 = new Intent(getApplicationContext(),SearchActivity_sub.class);
        intent_01.putExtra("장소",location);
        startActivity(intent_01);
    }
    public void onClick_logo(View v){
        finish();
    }
}
