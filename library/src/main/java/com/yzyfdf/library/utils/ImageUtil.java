package com.yzyfdf.library.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * @author sjj , 2019/5/23 16:55
 */
public class ImageUtil {
    private static volatile ImageUtil sInstance;

    private ImageUtil() {
    }

    public static ImageUtil getInstance() {

        if (sInstance == null) {
            synchronized (ImageUtil.class) {
                if (sInstance == null) {
                    sInstance = new ImageUtil();
                }
            }
        }
        return sInstance;
    }

    //更新图库
    public void updatePhotoMedia(File file, Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        context.sendBroadcast(intent);
    }
}
