package com.stone.activities;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.stone.model.Stone;

public class Condition {
    public int index;
    public AdapterView.OnItemSelectedListener listener;

    public void setCondition(int index, String condition) {
        this.index = index;
        this.condition = condition;
    }

    private String condition;
    private Validator validator;

    public Condition(Spinner spinner, Validator validator) {
        listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setCondition(position, (String) parent.getAdapter().getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        spinner.setOnItemSelectedListener(listener);
        this.validator = validator;
    }

    public void setSpinner(Spinner spinner) {
        spinner.setSelection(index);
        spinner.setOnItemSelectedListener(listener);
    }

    public boolean validate(Stone stone) {
        return validator.validate(stone, condition);
    }
}
