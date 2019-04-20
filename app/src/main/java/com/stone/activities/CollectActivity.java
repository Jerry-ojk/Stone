package com.stone.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.stone.Data;
import com.stone.R;
import com.stone.adapters.StoneListAdapter;

public class CollectActivity extends AppCompatActivity {
    private StoneListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());
        RecyclerView recyclerView = findViewById(R.id.rec_col);
        adapter = new StoneListAdapter(this, Data.COLLECT_LIST);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }
}
