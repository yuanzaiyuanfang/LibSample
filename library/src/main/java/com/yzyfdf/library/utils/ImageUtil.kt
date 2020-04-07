package com.yzyfdf.library.utils

import android.content.Context
import android.content.Intent
import android.media.MediaScannerConnection.scanFile
import android.net.Uri
import java.io.File

/**
 * @author sjj , 2019/5/23 16:55
 */
class ImageUtil private constructor() {

    //更新图库
    fun updatePhotoMedia1(file: File, context: Context) {
        val intent = Intent()
        intent.action = Intent.ACTION_MEDIA_SCANNER_SCAN_FILE
        intent.data = Uri.fromFile(file)
        context.sendBroadcast(intent)
    }

    fun updatePhotoMedia2(file: File, context: Context) {
        scanFile(context, arrayOf(file.absolutePath), arrayOf("image/jpeg")) { _, _ -> }
    }

    companion object {
        val instant: ImageUtil by lazy {
            ImageUtil()
        }
    }
}
