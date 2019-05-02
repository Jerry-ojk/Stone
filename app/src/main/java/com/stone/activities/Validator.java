package com.stone.activities;

import com.stone.model.Stone;

public interface Validator {
    boolean validate(Stone stone,String condition);
}
