package com.example.codemac.retropost;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class ViewDetail extends AppCompatActivity {

    RecyclerView recyclerView;
    private View parentView;
    private LinearLayoutManager layoutManager;
    private ArrayList<login> loginArrayList;
    private DocumentDetails adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);

        recyclerView=(RecyclerView)findViewById(R.id.rview);
        parentView=findViewById(R.id.rview);

        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



    }
}
