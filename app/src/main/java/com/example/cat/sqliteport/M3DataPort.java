package com.example.cat.sqliteport;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by akoshkin on 30.08.16.
 */
public class M3DataPort extends ContentProvider {
    static final Uri ZAJAV_URI = Uri.parse("content://com.example.cat.sqliteport/zajav");
    static final Uri ZAJAVSPEC_URI = Uri.parse("content://com.example.cat.sqliteport/zajavspec");
    static final Uri RESOURCES_URI = Uri.parse("content://com.example.cat.sqliteport/resources");

    private SQLiteDatabase mDB;

    @Override
    public boolean onCreate() {
        mDB= new PortDBHelper(getContext()).getWritableDatabase();
        return (mDB==null) ? false : true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] columns, String selection, String[] selectionArgs, String sort) {
        String path = uri.getEncodedPath().substring(1);

        Cursor c = mDB.query(path, columns, selection, selectionArgs, null, null, sort);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
