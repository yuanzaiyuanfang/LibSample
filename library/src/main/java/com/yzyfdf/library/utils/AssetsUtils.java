package com.yzyfdf.library.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class AssetsUtils {

    public static String readAssetsTxt(Context context, String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
