package com.example.cat.sqliteport;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class PortDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "local.db"; // название бд
	private static final int SCHEMA = 1; // версия базы данных
	static final String TBL_ZAJAV = "ZAJAV"; // название таблиц в бд
	static final String TBL_SPEC = "ZAJAVSPEC";
	static final String TBL_RES = "RESOURCES";
    // названия столбцов
    public static final String Z_ID = "_id";
    public static final String Z_STRCODE = "STRCODE";
    public static final String Z_DATE = "ZDATE";
    public static final String Z_NOTE = "NOTE";
    public static final String S_ID = "_id";
    public static final String S_ZID = "Z_ID";
	public static final String S_RES = "RES_ID";
    public static final String S_QUANTITY = "QUANTITY";
    public static final String S_COST = "RESCOST";
	public static final String R_ID =  "_id";
	public static final String R_PARENT =  "pid";
	public static final String R_NAME =  "RNAME";
	public static final String R_COST =  "RCOST";

	private final Context mCtx;
	public static SQLiteDatabase mDB;
	public static PortDBHelper sqlHelper;

    public PortDBHelper(Context context) {

		super(context, DATABASE_NAME, null, SCHEMA);
		mCtx = context;
		mDB = getWritableDatabase();
		sqlHelper=this;

	}
 
    @Override
    public void onCreate(SQLiteDatabase db) {

		mDB=db;

		db.execSQL("CREATE TABLE DUAL (_id INTEGER PRIMARY KEY AUTOINCREMENT);");
		db.execSQL("CREATE TABLE "+TBL_ZAJAV+" ("
				+ Z_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ Z_STRCODE + " TEXT,"
				+ Z_DATE + " DATE,"
				+ Z_NOTE + " TEXT"
				+");");
        db.execSQL("CREATE TABLE "+TBL_SPEC+" ("
				+ S_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + S_ZID + " integer,"
                + S_RES + " integer,"
                + S_QUANTITY + " real,"
                + S_COST + " real"
                + ");");
		db.execSQL("CREATE TABLE "+TBL_RES+" ("
				+ R_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ R_PARENT + " INTEGER,"
				+ R_NAME + " text,"
				+ R_COST + " real"
				+ ");");

		// добавление начальных данных */
		db.execSQL("INSERT INTO DUAL(_id) VALUES (1);");

		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", "+ R_NAME + ") VALUES (11, 'Детали');");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", " + R_PARENT +", "+ R_NAME +", "+ R_COST + ") VALUES (101, 11, 'Ячейка ВЧ 845', 4500);");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", " + R_PARENT +", "+ R_NAME +", "+ R_COST + ") VALUES (102, 11, 'Корпус защищенный', 1370);");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", " + R_PARENT +", "+ R_NAME +", "+ R_COST + ") VALUES (103, 11, 'Пульт сопряжения ПРМИ 1298', 7800);");

		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", "+ R_NAME + ") VALUES (12, 'Заготовки');");

		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", " + R_PARENT +", "+ R_NAME + ") VALUES (21, 12, 'Болванки');");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", " + R_PARENT +", "+ R_NAME +", "+ R_COST + ") VALUES (104, 21, 'Болванка 7001',120);");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", " + R_PARENT +", "+ R_NAME +", "+ R_COST + ") VALUES (105, 21, 'Болванка 5-500', 23.70);");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", " + R_PARENT +", "+ R_NAME +", "+ R_COST + ") VALUES (106, 21, 'Болванка 4', 780);");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", " + R_PARENT +", "+ R_NAME +", "+ R_COST + ") VALUES (107, 21, 'Болванка 4а', 780);");

		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", " + R_PARENT +", "+ R_NAME + ") VALUES (22, 12, 'Пруток');");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", " + R_PARENT +", "+ R_NAME +", "+ R_COST + ") VALUES (108, 22, 'Пруток М6', 13);");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", " + R_PARENT +", "+ R_NAME +", "+ R_COST + ") VALUES (109, 22, 'Пруток ГП4Х', 4);");

		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", " + R_PARENT +", "+ R_NAME + ") VALUES (23, 12, 'Штамповка');");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", " + R_PARENT +", "+ R_NAME +", "+ R_COST + ") VALUES (110, 23, 'Штамповка ГП4-08', 65);");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", " + R_PARENT +", "+ R_NAME +", "+ R_COST + ") VALUES (111, 23, 'Штамповка ГП4-92', 18);");

		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", "+ R_NAME + ") VALUES (13, 'Комплектующие изделия');");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", " + R_PARENT +", "+ R_NAME +", "+ R_COST + ") VALUES (112, 13, 'Насос', 160);");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", " + R_PARENT +", "+ R_NAME +", "+ R_COST + ") VALUES (113, 13, 'Аптечка', 270);");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", " + R_PARENT +", "+ R_NAME +", "+ R_COST + ") VALUES (114, 13, 'Набор инструментов', 1400);");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", " + R_PARENT +", "+ R_NAME +", "+ R_COST + ") VALUES (115, 13, 'Огнетушитель', 2400);");


		db.execSQL("INSERT INTO "+ TBL_ZAJAV +" (" + Z_ID +", "+ Z_DATE + ", " + Z_STRCODE + ", " + Z_NOTE +") VALUES (1, '22.08.2016', '1', 'Комментарий');");
		db.execSQL("INSERT INTO "+ TBL_ZAJAV +" (" + Z_ID +", "+ Z_DATE + ", " + Z_STRCODE + ", " + Z_NOTE +") VALUES (2, '08.08.2016', '2', '');");
		db.execSQL("INSERT INTO "+ TBL_ZAJAV +" (" + Z_ID +", "+ Z_DATE + ", " + Z_STRCODE + ", " + Z_NOTE +") VALUES (3, '24.08.2016', '33', '');");

		db.execSQL("INSERT INTO "+ TBL_SPEC +" (" + S_ID +","+S_ZID+","+ S_RES+","+S_QUANTITY+","+S_COST+") VALUES (11 ,1, 101, 1, 88.34);");
		db.execSQL("INSERT INTO "+ TBL_SPEC +" (" + S_ID +","+S_ZID+","+ S_RES+","+S_QUANTITY+","+S_COST+") VALUES (12 ,1, 102, 10, 19.48);");
		db.execSQL("INSERT INTO "+ TBL_SPEC +" (" + S_ID +","+S_ZID+","+ S_RES+","+S_QUANTITY+","+S_COST+") VALUES (13 ,1, 103, 100, 87.95);");

		db.execSQL("INSERT INTO "+ TBL_SPEC +" (" + S_ID +","+S_ZID+","+ S_RES+","+S_QUANTITY+","+S_COST+") VALUES (21 ,2, 104, 25, 22.55);");
		db.execSQL("INSERT INTO "+ TBL_SPEC +" (" + S_ID +","+S_ZID+","+ S_RES+","+S_QUANTITY+","+S_COST+") VALUES (22 ,2, 105, 121, 41);");

		db.execSQL("INSERT INTO "+ TBL_SPEC +" (" + S_ID +","+S_ZID+","+ S_RES+","+S_QUANTITY+","+S_COST+") VALUES (31 ,3, 106, 100000, 0.34);");
		db.execSQL("INSERT INTO "+ TBL_SPEC +" (" + S_ID +","+S_ZID+","+ S_RES+","+S_QUANTITY+","+S_COST+") VALUES (32 ,3, 107, 150000, 1.4);");
		db.execSQL("INSERT INTO "+ TBL_SPEC +" (" + S_ID +","+S_ZID+","+ S_RES+","+S_QUANTITY+","+S_COST+") VALUES (33 ,3, 108, 180000, 0.004);");
		db.execSQL("INSERT INTO "+ TBL_SPEC +" (" + S_ID +","+S_ZID+","+ S_RES+","+S_QUANTITY+","+S_COST+") VALUES (34 ,3, 109, 1000000, 1.001);");

	}
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS DUAL");
		db.execSQL("DROP TABLE IF EXISTS "+TBL_SPEC);
		db.execSQL("DROP TABLE IF EXISTS "+TBL_ZAJAV);
		db.execSQL("DROP TABLE IF EXISTS "+TBL_RES);
        onCreate(db);
    }


	public static Cursor fetchAllZajavs() {

		Cursor mCursor = mDB.query(TBL_ZAJAV, new String[] { Z_ID,
						Z_STRCODE, Z_DATE, Z_NOTE},
				null, null, null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public static void saveZajav(int id, String strcode, Date date, String note) {
		Locale local = new Locale("ru","RU");
		DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, local);
		mDB.execSQL("update "+TBL_ZAJAV+" set "+Z_STRCODE+"='"+strcode+"', "+Z_DATE+"='"+df.format(date)+"', "+
				Z_NOTE+"='"+note+"' where "+Z_ID+"="+Integer.toString(id)+";");

	}

	public static void deleteZajav(String id) {
		mDB.execSQL("delete from "+TBL_ZAJAV+" where "+Z_ID+"="+id+";");

	}

	public static int addZajav() {
		Cursor c=mDB.query(TBL_ZAJAV, new String[] {"max(_id) m"}, null,null,null,null,null);
		if (c != null) {
			c.moveToFirst();
		}
		int id=c.getInt(0)+1;
		mDB.execSQL("insert into "+TBL_ZAJAV+"(_id) values("+id+");");
		return id;
	}

	public static void deleteSpec(String specID) {
		mDB.execSQL("delete from "+TBL_SPEC+" where "+S_ZID+"="+specID+";");
	}

	public static Cursor fetchAllSpec(String zajavID) {
//		Cursor mCursor = mDB.rawQuery("select zs._id, zs.RES_ID, r.NAME RES_NAME, zs.QUANTITY, zs.COST from ZAJAVSPEC zs, RESOURCES r where zs.Z_ID = ?s and r._id=zs.RES_ID", new String[]{zajavID});
		Cursor mCursor = mDB.rawQuery("select zs._id, zs.RES_ID, r.NAME RES_NAME, zs.QUANTITY, zs.RESCOST from "+TBL_SPEC+" zs, RESOURCES r where zs.Z_ID = "+zajavID+" and r._id=zs.RES_ID", null);

//				(TBL_ZAJAV, new String[] { Z_ID,
//						Z_STRCODE, Z_DATE, Z_NOTE},
//				null, null, null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public static int addSpec(String zid, int ResID) {
		Cursor c=mDB.query(TBL_SPEC, new String[] {"max(_id) m"}, null,null,null,null,null);
		if (c != null) {
			c.moveToFirst();
		}
		int id=c.getInt(0)+1;
		mDB.execSQL("insert into "+TBL_SPEC+"(_id, Z_ID, RES_ID) values("+id+", "+zid+", "+ResID+");");
		return id;
	}

}
