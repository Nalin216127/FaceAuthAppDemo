package com.example.roomapp.util

import android.content.Context
import android.content.Intent
import android.os.Environment
import androidx.core.content.FileProvider
import com.example.roomapp.fragments.list.ListFragment
import java.io.File

fun generateFile(context: Context, fileName: String): File? {
    val csvFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)
    csvFile.createNewFile()

    return if (csvFile.exists()) {
        csvFile
    } else {
        null
    }

}

fun goToFileIntent(context: Context,file: File):Intent{
    val intent = Intent(Intent.ACTION_VIEW)
    val contentUri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    val mimeType = context.contentResolver.getType(contentUri)
    intent.setDataAndType(contentUri, mimeType)
    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION

    return intent
}