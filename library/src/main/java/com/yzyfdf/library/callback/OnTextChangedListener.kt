package com.yzyfdf.library.callback

import android.text.TextWatcher

/**
 * @author sjj , 2019/4/25 10:01
 *
 * 文字改变后监听
 */
abstract class OnTextChangedListener : TextWatcher {
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

    }
}
