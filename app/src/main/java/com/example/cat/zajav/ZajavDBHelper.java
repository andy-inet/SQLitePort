package com.example.cat.zajav;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class ZajavDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "local.db"; // название бд
	private static final int SCHEMA = 2; // версия базы данных
	static final String TBL_ZAJAV = "ZAJAV"; // название таблиц в бд
	static final String TBL_SPEC = "ZAJZVSPEC";
	static final String TBL_RES = "RESOURCES";
    // названия столбцов
    public static final String Z_ID = "_id";
    public static final String Z_STRCODE = "STRCODE";
    public static final String Z_DATE = "ZDATE";
    public static final String Z_NOTE = "NOTE";
    public static final String S_ID = "_id";
    public static final String S_ZID = "Z_ID";
	public static final String S_RES = "RES_ID";
	public static final String S_RESNAME = "RES_NAME";
    public static final String S_QUANTITY = "QUANTITY";
    public static final String S_COST = "RESCOST";
    public static final String R_ID =  "_id";
    public static final String R_NAME =  "NAME";

	private final Context mCtx;
	public static SQLiteDatabase mDB;
	public static ZajavDBHelper sqlHelper;

    public ZajavDBHelper(Context context) {

		super(context, DATABASE_NAME, null, SCHEMA);
		mCtx = context;
		mDB = getWritableDatabase();
		sqlHelper=this;

	}
 
    @Override
    public void onCreate(SQLiteDatabase db) {

		mDB=db;
 
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
				+ R_NAME + " text"
				+ ");");

		// добавление начальных данных */
/*
        db.execSQL("INSERT INTO "+ semaphores +" (" + C_NAME +", "+ C_ID + ") VALUES ('��� ����',1);");
        db.execSQL("INSERT INTO "+ smph_specification +" (" + C_ID +","+
        		C_MSTASK_ID+","+ C_SMPH_ID+","+C_POSITION_NUM+","+ C_NAME+","+C_COLOR+"," 
                + C_POINTENTER+","+ C_SUMMA+","+ C_PERCENT+","+ C_PERCENT_REL+","+C_FULLNAME 
        		+") VALUES (" + C_ID +","+
        		C_MSTASK_ID+","+ C_SMPH_ID+","+C_POSITION_NUM+","+ C_NAME+","+C_COLOR+"," 
                + C_POINTENTER+","+ C_SUMMA+","+ C_PERCENT+","+ C_PERCENT_REL+","+C_FULLNAME 
        		+");");
 
      select 'db.execSQL("INSERT INTO "+ semaphores +" (" + C_NAME +", "+ C_ID + ") VALUES ('''||name||''','||id||');");' from m2_all.semaphores#F s
      select
        'db.execSQL("INSERT INTO "+ smph_specification +" (" + C_ID +","+
                C_MSTASK_ID+","+ C_SMPH_ID+","+C_POSITION_NUM+","+ C_NAME+","+C_COLOR+"," 
                + C_POINTENTER+","+ C_SUMMA+","+ C_PERCENT+","+ C_PERCENT_REL+","+C_FULLNAME 
                +") VALUES ('||ID||' ,'||nvl(to_char(MSTASK_ID),null)||', '||nvl(to_char(SMPH_ID),null)||', '||
                nvl(to_char(POSITION_NUM),null)||', '''||NAME||''','||nvl(to_char(COLOR),null)||', '
                ||nvl(to_char(POINTENTER),null)||','||nvl(to_char(SUMMA),null)||','||nvl(to_char(PERCENT),null)||','||
                nvl(to_char(PERCENT_REL),null)||','''||FULLNAME||''');");'        
 from m2_all.smph_specification#f where smph_id in (select id from m2_all.semaphores#F s)  
     order by smph_id,position_num    

 */

		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", "+ R_NAME + ") VALUES (11, 'Детали');");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", "+ R_NAME + ") VALUES (101, 'Ячейка ВЧ 845');");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", "+ R_NAME + ") VALUES (102, 'Корпус защищенный');");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", "+ R_NAME + ") VALUES (103, 'Пульт сопряжения ПРМИ 1298');");

		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", "+ R_NAME + ") VALUES (12, 'Заготовки');");

		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", "+ R_NAME + ") VALUES (21, 'Болванки');");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", "+ R_NAME + ") VALUES (104, 'Болванка 7001');");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", "+ R_NAME + ") VALUES (105, 'Болванка 5-500');");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", "+ R_NAME + ") VALUES (106, 'Болванка 4');");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", "+ R_NAME + ") VALUES (107, 'Болванка 4а');");

		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", "+ R_NAME + ") VALUES (22, 'Пруток');");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", "+ R_NAME + ") VALUES (108, 'Пруток М6');");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", "+ R_NAME + ") VALUES (109, 'Пруток ГП4Х');");

		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", "+ R_NAME + ") VALUES (23, 'Штамповка');");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", "+ R_NAME + ") VALUES (110, 'Штамповка ГП4-08');");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", "+ R_NAME + ") VALUES (111, 'Штамповка ГП4-92');");

		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", "+ R_NAME + ") VALUES (13, 'Комплектующие изделия');");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", "+ R_NAME + ") VALUES (112, 'Насос');");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", "+ R_NAME + ") VALUES (113, 'Аптечка');");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", "+ R_NAME + ") VALUES (114, 'Набор инструментов');");
		db.execSQL("INSERT INTO "+ TBL_RES +" (" + R_ID +", "+ R_NAME + ") VALUES (115, 'Огнетушитель');");


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
