package com.stone.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import com.stone.Data;
import com.stone.R;
import com.stone.adapters.StoneListAdapter;

import static android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

public class CollectActivity extends AppCompatActivity {
    private StoneListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

        Window window = this.getWindow();
        window.setStatusBarColor(0xffffffff);
        window.getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
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
