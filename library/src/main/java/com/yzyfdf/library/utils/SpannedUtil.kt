package com.yzyfdf.library.utils

import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorInt
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY

object SpannedUtil {
    /**
     * @param name
     * @param key
     * @param color 格式#D91515 不支持透明
     * @return
     */
    fun getTitle(name: String, key: String, color: String): Spanned? {
        var color = color
        if (TextUtils.isEmpty(name)) {
            return null
        }
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(color)) {
            return SpannableString(name)
        }
        if (color.startsWith("#") && color.length == 9) {
            color = "#" + color.substring(3)
        }
        if (name.contains(key)) {
            val i = name.indexOf(key)
            val sb = name.substring(0,
                    i) + "<font color=\"" + color + "\">" + key + "</font>" + name.substring(
                    i + key.length)
            return HtmlCompat.fromHtml(sb, FROM_HTML_MODE_LEGACY)
        }
        return SpannableString(name)
    }

    fun getTitle(name: String, key: String, @ColorInt color: Int): Spanned? {
        if (TextUtils.isEmpty(name)) {
            return null
        }

        val spannableString = SpannableString(name)
        if (TextUtils.isEmpty(key)) {
            return spannableString
        }

        if (name.contains(key)) {
            val i = name.indexOf(key)
            val foregroundColorSpan = ForegroundColorSpan(color)
            spannableString.setSpan(foregroundColorSpan, i, i + key.length,
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        }
        return spannableString
    }

}
