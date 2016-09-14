package com.example.cat.sqliteport;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by akoshkin on 30.08.16.
 */
public class M3DataPort extends ContentProvider {
    static final String PROVIDER_NAME = "com.example.cat.sqliteport";
    static final String TBL_META = "meta";
    static final String TBL_ZAJAV = "zajav";
    static final String TBL_ZAJAVSPEC = "zajavspec";
    static final String TBL_RESOURCES = "resources";

    static final Uri ZAJAV_URI = Uri.parse("content://" + PROVIDER_NAME + "/" + TBL_ZAJAV);
    static final Uri ZAJAVSPEC_URI = Uri.parse("content://" + PROVIDER_NAME + "/" + TBL_ZAJAVSPEC);
    static final Uri RESOURCES_URI = Uri.parse("content://" + PROVIDER_NAME + "/" + TBL_RESOURCES);

    private static final int UM_METADATA_ALL = 10;
    private static final int UM_RESOURCES_ALL = 101;
    private static final int UM_RESOURCES_ONE = 102;
    private static final int UM_ZAJAV_ALL = 111;
    private static final int UM_ZAJAV_ONE = 112;
    private static final int UM_ZAJAVSPEC_ALL = 121;
    private static final int UM_ZAJAVSPEC_ONE = 122;

    private static UriMatcher sUriMatcher = null;

    private PortDBHelper helper = null;
    private SQLiteDatabase mDB = null;

    @Override
    public boolean onCreate() {
        String s = generateUriMatcher(true);
//        if (mDB == null)
        checkDB();
        return (mDB == null) ? false : true;
    }

    private void checkDB() {
        if (helper == null)
            helper = new PortDBHelper(getContext());
        if (mDB == null)
            mDB = helper.getWritableDatabase();
    }

    private String generateUriMatcher(boolean generate) {
        if (sUriMatcher == null)
            sUriMatcher = new UriMatcher(1);
        StringBuffer sb = new StringBuffer("");
        addUriIntoMather(generate, sb, TBL_META, UM_METADATA_ALL);
        addUriIntoMather(generate, sb, TBL_RESOURCES, UM_RESOURCES_ALL);
        addUriIntoMather(generate, sb, TBL_RESOURCES + "/#", UM_RESOURCES_ONE);
        addUriIntoMather(generate, sb, TBL_ZAJAV, UM_ZAJAV_ALL);
        addUriIntoMather(generate, sb, TBL_ZAJAV + "/#", UM_ZAJAV_ONE);
        addUriIntoMather(generate, sb, TBL_ZAJAVSPEC, UM_ZAJAVSPEC_ALL);
        addUriIntoMather(generate, sb, TBL_ZAJAVSPEC + "/#", UM_ZAJAVSPEC_ONE);
        return sb.append(";").toString();
    }

    private void addUriIntoMather(boolean generate, StringBuffer sb, String tbl, int um) {
        if (generate) {
            sUriMatcher.addURI(PROVIDER_NAME, tbl, um);
        } else {
            if ((!tbl.equals(TBL_META)) && (!tbl.contains("/#"))) {
                if (sb.length() > 0)
                    sb.append(" union all ");
                sb.append("select " + um + " _id, '" + tbl + "' tbl from dual");
            }
        }
    }


    @Nullable
    @Override
    public Cursor query(Uri uri, String[] columns, String selection, String[] selectionArgs, String sort) {
        checkDB();
        Cursor c;
        String sql;
        try {
            switch (sUriMatcher.match(uri)) {
                case UM_METADATA_ALL:
                    sql = generateUriMatcher(false);
                    c = mDB.rawQuery(sql.toString(), null);
                    break;
                case UM_RESOURCES_ALL:
                    if (TextUtils.isEmpty(sort))
                        sort = "_ID ASC";
                    sql = new String("select r._id, r.PID, r.RNAME NAME, r.rcost COST, (select count(*) from RESOURCES rr where rr.pid=r._id) CHILDCNT from RESOURCES r order by r.rname");
                    c = mDB.rawQuery(sql, null);
                    break;
                case UM_RESOURCES_ONE:
                    sql = new String("select r._id, r.PID, r.RNAME NAME, r.rcost COST, (select count(*) from RESOURCES rr where rr.pid=r._id) CHILDCNT from RESOURCES r where _id=" + uri.getLastPathSegment());
                    c = mDB.rawQuery(sql, null);
                    break;
                default:
                    String path = uri.getEncodedPath().substring(1);
                    if (path.contains("/")) {
                        selection = new String("_id=?");
                        selectionArgs = new String[]{path.substring(path.indexOf('/') + 1)};
                        path = path.substring(0, path.indexOf('/'));
                    }
                    c = mDB.query(path, columns, selection, selectionArgs, null, null, sort);
            }

            c.setNotificationUri(getContext().getContentResolver(), uri);
        } catch (Exception e) {
            e.printStackTrace();
            sql = new String("select '" + e.getMessage() + "' mess from dual");
            c = mDB.rawQuery(sql, null);
        }
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
        String sql;
        Uri res = null;
        checkDB();
//        try {
            switch (sUriMatcher.match(uri)) {
                case UM_RESOURCES_ALL:
                    long id = mDB.insert(TBL_RESOURCES, "rname", contentValues);
                    if (id == -1)
                        res = null;
                    else
                        res = ContentUris.withAppendedId(RESOURCES_URI, id);
                    break;
                default:
            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            res=null;
//        }
        return res;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int cnt=0;
        checkDB();
//        try {
            String path = uri.getEncodedPath().substring(1);
            if (path.contains("/")) {
                selection = new String("_id=?");
                selectionArgs = new String[]{path.substring(path.indexOf('/') + 1)};
                path = path.substring(0, path.indexOf('/'));
            }
            cnt=mDB.delete(path, selection,selectionArgs);
//        } catch (Exception e) {
//            e.printStackTrace();
//            cnt=0;
//        }
        return cnt;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        int cnt=0;
        checkDB();
//        try {
        String path = uri.getEncodedPath().substring(1);
        if (path.contains("/")) {
            selection = new String("_id=?");
            selectionArgs = new String[]{path.substring(path.indexOf('/') + 1)};
            path = path.substring(0, path.indexOf('/'));
        }
        cnt=mDB.update(path, contentValues, selection,selectionArgs);
//        } catch (Exception e) {
//            e.printStackTrace();
//            cnt=0;
//        }
        return cnt;
    }
}
