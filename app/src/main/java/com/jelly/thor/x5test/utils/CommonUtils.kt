package com.jelly.thor.x5test.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File


object CommonUtils {
    fun openPDFInFile(context: Context, file: File) {
        val intent = Intent(Intent.ACTION_VIEW)
        val uri: Uri
        //intent.addCategory(Intent.CATEGORY_DEFAULT);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            //Android 7.0之后
            val sb = StringBuilder("com.jelly.thor.x5test")
            /*if (BuildConfig.DEBUG) {
                sb.append(".debug")
            }*/
            sb.append(".fileprovider")
            uri = FileProvider.getUriForFile(context, sb.toString(), file)
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION) //给目标文件临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) //给目标文件临时授权
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION) //给目标文件临时授权
        } else {
            uri = Uri.fromFile(file)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setDataAndType(uri, "application/pdf")
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            //Log.w("123===", "Activity was not found for intent, $intent")
        }
    }


}
