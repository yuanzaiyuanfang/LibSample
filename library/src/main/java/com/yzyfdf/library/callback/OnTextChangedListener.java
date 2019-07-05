package com.yzyfdf.library.callback;

import android.text.TextWatcher;

/**
 * @author sjj , 2019/4/25 10:01
 *
 * 文字改变后监听
 */
public abstract class OnTextChangedListener implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
}
