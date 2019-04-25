package com.stone.activities;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.stone.model.Stone;

public class Condition {
    public void setCondition(String condition) {
        this.condition = condition;
    }

    private String condition;
    private Validator validator;

    public Condition(Spinner spinner, Validator validator) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setCondition((String) parent.getAdapter().getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        this.validator = validator;
    }

    public boolean validate(Stone stone) {
        return validator.validate(stone, condition);
    }
}
