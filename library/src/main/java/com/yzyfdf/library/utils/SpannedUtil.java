package com.yzyfdf.library.utils;

import androidx.annotation.ColorInt;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

public class SpannedUtil {
    /**
     * @param name
     * @param key
     * @param color 格式#D91515 不支持透明
     * @return
     */
    public static Spanned getTitle(String name, String key, String color) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(color)) {
            return new SpannableString(name);
        }
        if (color.startsWith("#") && color.length() == 9) {
            color = "#" + color.substring(3);
        }
        if (name.contains(key)) {
            int i = name.indexOf(key);
            String sb = name.substring(0, i) +
                    "<font color=\"" + color + "\">" +
                    key +
                    "</font>" +
                    name.substring(i + key.length());
            return Html.fromHtml(sb);
        }
        return new SpannableString(name);
    }

    public static Spanned getTitle(String name, String key, @ColorInt int color) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }

        SpannableString spannableString = new SpannableString(name);
        if (TextUtils.isEmpty(key)) {
            return spannableString;
        }

        if (name.contains(key)) {
            int i = name.indexOf(key);
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);
            spannableString.setSpan(foregroundColorSpan, i, i + key.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        return spannableString;
    }

}
