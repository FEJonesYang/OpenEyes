package com.jonesyong.library_base.application

import android.annotation.SuppressLint
import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri

class ApplicationProvider : ContentProvider() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var ctx: Context? = null

        fun getContext(): Context = ctx!!

        fun ensureContext(context: Context?) {
            if (ctx != null || context == null) {
                return
            }
            ctx = context.applicationContext
        }
    }


    override fun onCreate(): Boolean {
        ensureContext(context)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? = null

    override fun getType(uri: Uri): String? = ""
    override fun insert(uri: Uri, values: ContentValues?): Uri? = null
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0
    override fun update(
        uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?
    ): Int = 0
}