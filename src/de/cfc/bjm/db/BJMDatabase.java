package de.cfc.bjm.db;

import de.cfc.bjm.data.DataHolder;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BJMDatabase{
//	
//	public static final String KEY_USERID = "_id";
//	public static final String KEY_USERNAME = "name";
//	public static final String KEY_AVATAR = "avatar";
//	public static final String KEY_VERBINDUNG = "verbindung";
//	public static final String KEY_XP = "xp";
//	public static final String KEY_STATUS = "inactiv";
//	public static final String KEY_TIMESTAMP = "timestamp";
//	public static final String KEY_LEVEL = "level";
//
//	
//	public static final String KEY_LISTID = "_id";
//	public static final String KEY_UID = "userid";
//	public static final String KEY_OID = "opponentid";
//	public static final String KEY_TIME = "time";
//	public static final String KEY_DURATION = "duration";
//	public static final String KEY_VOLUME = "volume";
//	public static final String KEY_LIQUID = "liquid";
//	public static final String KEY_AMOUNT = "amount";
//	public static final String KEY_LISTXP = "xp";
//	
//	public static final String KEY_VERBINDUNGSID = "_id";
//	public static final String KEY_VERBINDUNGSNAME = "name";
//	public static final String KEY_DELETE ="del";
//	
//	private static final String DB_NAME = "bjmdb.db";
//	private static final String DB_USER_TABLE = "user";
//	private static final String DB_LIST_TABLE = "list";
//	private static final String DB_VERBINDUNG_TABLE = "verbindungen";
//	
//	private static final int DB_VERSION = 25;
//	
//	private static final String DB_USER_CREATE =
//		"CREATE TABLE user(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(16) NOT NULL, avatar VARCHAR(16) NOT NULL, verbindung INTEGER NOT NULL, del INTEGER NOT NULL, xp INTEGER, inactiv INTEGER, timestamp INTEGER, level INTEGER)";
//	private static final String DB_LIST_CREATE =
//		"CREATE TABLE list(_id INTEGER PRIMARY KEY AUTOINCREMENT, userid INTEGER NOT NULL, opponentid INTEGER NOT NULL, duration INTEGER NOT NULL, time INTEGER NOT NULL, volume INTEGER NOT NULL, liquid INTEGER NOT NULL, amount INTEGER NOT NULL, del INTEGER NOT NULL, xp INTEGER)";
//	
//	private static final String DB_VERBINDUNGEN_CREATE =
//		"CREATE TABLE verbindungen(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(32) NOT NULL, del INTEGER NOT NULL)";
//	
//	private final Context context;
//	
//	
//	private DatabaseHelper DBHelper;
//	private SQLiteDatabase db;
//	
//	public BJMDatabase(Context ctx){
//		this.context = ctx;
//		DBHelper = new DatabaseHelper(context);
//	}
//	
//	private static class DatabaseHelper extends SQLiteOpenHelper{
//		DatabaseHelper(Context context){
//			super(context, DB_NAME, null, DB_VERSION);
//		}
//		
//		@Override
//		public void onCreate(SQLiteDatabase db){
//			db.execSQL(DB_USER_CREATE);
//			db.execSQL(DB_LIST_CREATE);
//			db.execSQL(DB_VERBINDUNGEN_CREATE);
//			
//			ContentValues initialValues = new ContentValues();
//			initialValues.put(KEY_USERNAME, "SaufBuxe1");
//			initialValues.put(KEY_AVATAR, "corpsstudent");
//			initialValues.put(KEY_VERBINDUNG, 0);
//			initialValues.put(KEY_DELETE, 0);
//			initialValues.put(KEY_XP, 0);
//			initialValues.put(KEY_LEVEL, 1);
//			db.insert(DB_USER_TABLE, null, initialValues);
//			
//			initialValues = new ContentValues();
//			initialValues.put(KEY_USERNAME, "SaufBuxe2");
//			initialValues.put(KEY_AVATAR, "spongebob");
//			initialValues.put(KEY_VERBINDUNG, 0);
//			initialValues.put(KEY_DELETE, 0);
//			initialValues.put(KEY_XP, 0);
//			initialValues.put(KEY_LEVEL, 1);
//			db.insert(DB_USER_TABLE, null, initialValues);
//			
//			initialValues = new ContentValues();
//			initialValues.put(KEY_VERBINDUNGSNAME, "Ohne");
//			initialValues.put(KEY_DELETE, 0);
//			db.insert(DB_VERBINDUNG_TABLE, null, initialValues);
//		}
//		
//		@Override
//		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
//			Log.d("onUpgrade", "wiping DB");
//			db.execSQL("DROP TABLE IF EXISTS list");
//			db.execSQL("DROP TABLE IF EXISTS user");
//			db.execSQL("DROP TABLE IF EXISTS verbindungen");
//			onCreate(db);
//		}
//	}
//	
//	public BJMDatabase open() throws SQLException{
//		db = DBHelper.getWritableDatabase();
//		return this;
//	}
//	
//	public void close(){
//		DBHelper.close();
//	}
//	
//	public long insertUser(String username, String avatar, int verbindung){
//		
//		ContentValues initialValues = new ContentValues();
//		initialValues.put(KEY_USERNAME, username);
//		initialValues.put(KEY_AVATAR, avatar);
//		initialValues.put(KEY_VERBINDUNG, verbindung);
//		initialValues.put(KEY_DELETE, 0);
//		initialValues.put(KEY_XP, 0);
//		initialValues.put(KEY_LEVEL, 1);
//		initialValues.put(KEY_STATUS, 0);
//		return db.insert(DB_USER_TABLE, null, initialValues);
//	}
//	
//	public long insertListEntry(int userid, int opponentid, int duration,  long time, int volume, int liquid, int amount){
//		ContentValues initialValues = new ContentValues();
//		initialValues.put(KEY_UID, userid);
//		initialValues.put(KEY_OID, opponentid);
//		initialValues.put(KEY_DURATION, duration);
//		initialValues.put(KEY_TIME, time);
//		initialValues.put(KEY_VOLUME, volume);
//		initialValues.put(KEY_LIQUID, liquid);
//		initialValues.put(KEY_AMOUNT, amount);
//		initialValues.put(KEY_DELETE, 0);
//		initialValues.put(KEY_LISTXP, 0);
//		
//		return db.insert(DB_LIST_TABLE, null, initialValues);
//	}
//	
//	public boolean updateUser(long userid, String username, String avatar, int verbindung){
//		ContentValues values = new ContentValues();
//		values.put(KEY_USERNAME, username);
//		values.put(KEY_AVATAR, avatar);
//		values.put(KEY_VERBINDUNG, verbindung);
//		values.put(KEY_DELETE, 0);
//		return db.update(DB_USER_TABLE, values, KEY_USERID + "=" + userid, null) > 0;
//	}
//	
//	public void deleteUser(long userid){
//		ContentValues values = new ContentValues();
//		values.put(KEY_DELETE, 1);
//		db.update(DB_USER_TABLE, values, KEY_USERID + "=" + userid, null);
//	}
//	
//	public void deleteVerbindung(long id){
//		ContentValues values = new ContentValues();
//		values.put(KEY_DELETE, 1);
//		db.update(DB_VERBINDUNG_TABLE, values, KEY_VERBINDUNGSID + "=" + id, null);
//	}
//	
//	public void deleteListEntry(long id){
//		ContentValues values = new ContentValues();
//		values.put(KEY_DELETE, 1);
//		db.update(DB_LIST_TABLE, values, KEY_LISTID + "=" + id, null);
//	}
//	
//	public Cursor getUser(long userid) throws SQLException{
//		Cursor mCursor = db.query(DB_USER_TABLE, new String[]{
//				KEY_USERID, KEY_USERNAME, KEY_AVATAR, KEY_VERBINDUNG, KEY_XP, KEY_LEVEL},KEY_USERID + "=" + userid + " AND "+KEY_DELETE+" = "+ 0,null,null,null,null);
//		if(mCursor != null){
//			mCursor.moveToFirst();
//		}
//		return mCursor;
//	}
//	
//	public Cursor getVerbindung(int id) throws SQLException{
//		Cursor mCursor = db.query(DB_VERBINDUNG_TABLE, new String[]{
//				KEY_VERBINDUNGSNAME},KEY_DELETE+" = "+ 0,null,null,null,null);
//		if(mCursor != null){
//			mCursor.moveToFirst();
//		}
//		return mCursor;
//	}
//	
//	public Cursor getAllUsers(){
//		return db.query(DB_USER_TABLE, new String[]{
//				KEY_USERID, KEY_USERNAME, KEY_AVATAR, KEY_VERBINDUNG, KEY_XP, KEY_LEVEL},KEY_DELETE+" = "+ 0,null,null,null,null);
//	}
//	
//	public Cursor getAllUsersFromVerbindung(long id){
//		return db.query(DB_USER_TABLE, new String[]{
//				KEY_USERID, KEY_USERNAME, KEY_AVATAR, KEY_VERBINDUNG, KEY_XP, KEY_LEVEL},KEY_VERBINDUNG + " = " + id + " AND "+KEY_DELETE+" = "+ 0,null,null,null,null);
//	}
//	
//	public Cursor getLastDrinkFromUser(long userid){
//		Cursor mCursor =  db.query(DB_LIST_TABLE, new String[]{"MAX("+KEY_LISTID+")"}, userid + "=" + KEY_UID+ " AND "+KEY_DELETE+" = "+ 0, null, null, null, null);
//		if(mCursor != null){
//			mCursor.moveToFirst();
//		}
//		return mCursor;
//	}
//
//	public Cursor getRecord(int volume, int liquid, int amount){
//		return db.rawQuery("SELECT l._id, u.name, l.duration FROM list AS l, user AS u WHERE l.volume = " + volume + " AND l.amount = "+amount + " AND l.liquid = " + liquid + " AND u._id = l.userid AND l.del = 0 ORDER BY l.duration", null);
//	}
//	
//	public Cursor getRecord(int position, int limit, int volume,  int liquid, int amount){
//		int startIndex = position - (limit);
//		int endIndex = position + (limit);
//		if(startIndex<0){
//			startIndex=0;
//		}
//		return db.rawQuery("SELECT l._id, u.name, l.duration FROM list AS l, user AS u WHERE l.volume = " + volume + " AND l.amount = " + amount +" AND l.liquid = " + liquid + " AND u._id = l.userid AND l.del = 0 ORDER BY l.duration LIMIT "+startIndex+","+endIndex, null);
//	}
//	
//	public Cursor getRecordInformation(long listid){
//		Cursor mCursor = db.rawQuery("SELECT u._id, u.name, l.duration, o._id, l.time, l.volume, l.liquid, l.amount FROM list AS l, user AS u, user AS o WHERE l._id = " + listid + " AND u._id = userid AND o._id = opponentid", null);
//		if(mCursor != null){
//			mCursor.moveToFirst();
//		}
//		return mCursor;
//	
//	}
//	public double getPersonalHighScore(int id,int volume,  int liquid, int amount){
//		Cursor mCursor = db.rawQuery("SELECT duration FROM list WHERE volume = " + volume + " AND amount = "+amount + " AND liquid = " + liquid + " AND userid = " + id +" AND del = 0 ORDER BY duration", null);
//		if(mCursor.getCount()<1){
//			return -1;
//		}
//		mCursor.moveToFirst();
//		return mCursor.getDouble(0);
//	}
//	
//	public int getTotalBJ(int id){
//		Cursor mCursor = db.rawQuery("SELECT _id FROM list WHERE userid =? AND del = 0", new String[]{id+""});
//		if(mCursor == null){
//			return 0;
//		}
//		int result = mCursor.getCount();
//		return result;
//		
//	}
//	
//	public Cursor getVerbindungen(){
//		Cursor mCursor = db.rawQuery("SELECT _id, name FROM verbindungen WHERE del = 0", null);
//		if(mCursor != null){
//			mCursor.moveToFirst();
//		}
//		return mCursor;
//	}
//	
//	public long insertVerbindung(String name){
//		ContentValues initialValues = new ContentValues();
//		initialValues.put(KEY_VERBINDUNGSNAME, name);
//		initialValues.put(KEY_DELETE, 0);
//		return db.insert(DB_VERBINDUNG_TABLE, null, initialValues);
//	}
//	
//	public int getRank(int id, int volume, int liquid, int amount){
//		Cursor r = getRecord(volume, liquid, amount);
//		if(r == null){
//			return -1;
//		}
//		int i=1;
//		while(r.moveToNext()){
//			if(r.getInt(0)==id){
//				r.close();
//				return i;
//			}
//			i++;
//		}
//		r.close();
//		return -1;
//	}
//	public int updateXP(int id, int xp){
//		Cursor mCursor = db.rawQuery("SELECT xp FROM user WHERE _id=?", new String[]{""+id});
//		mCursor.moveToFirst();
//		int oldxp=mCursor.getInt(0);
//		xp=xp+oldxp;
//		mCursor.close();
//		ContentValues update = new ContentValues();
//		update.put("xp", xp);
//		db.update(DB_USER_TABLE, update, "_id=?", new String[]{""+id});
//		return xp;
//	}
//	
//	public boolean checkLevel(int id){
//		int lvl = getLevel(id);
//		int XP = getXP(id);
//		if(XP > DataHolder.levelToXP.get(lvl)){
//			setLevel(id, lvl+1);
//			return true;
//		}else{
//			return false;
//		}
//	}
//	
//	public void setListEntryXP(int lid, int xp){
//		ContentValues update = new ContentValues();
//		update.put("xp", xp);
//		db.update(DB_LIST_TABLE, update, "_id=?", new String[]{""+lid});
//	}
//	
//	public int getLevel(int id){
//		Cursor mCursor = db.rawQuery("SELECT level FROM user WHERE _id=?", new String[]{""+id});
//		if(mCursor.getCount()<1){
//			return -1;
//		}
//		mCursor.moveToFirst();
//		int result = mCursor.getInt(0);
//		mCursor.close();
//		return result;
//	}
//	
//	
//	public void setLevel(int id, int level){
//		ContentValues update = new ContentValues();
//		update.put("level", level);
//		db.update(DB_USER_TABLE, update, "_id=?", new String[]{""+id});
//	}
//	
//	public int getXP(int id){
//		Cursor mCursor = db.rawQuery("SELECT xp FROM user WHERE _id=?", new String[]{""+id});
//		if(mCursor.getCount()<1){
//			return -1;
//		}
//		mCursor.moveToFirst();
//		int result = mCursor.getInt(0);
//		mCursor.close();
//		return result;
//	}
//	
//	public int getFavOpponent(int id){
//		Cursor cur = db.rawQuery("SELECT opponentid FROM list WHERE userid = "+id+" GROUP BY opponentid ORDER BY count(*)", null);
//		if(cur.getCount()<1){
//			return 0;
//		}
//		cur.moveToFirst();
//		int result = cur.getInt(0);
//		cur.close();
//		return result;
//	}
//	public Cursor getXPRecord(){
//		Cursor cur = db.rawQuery("SELECT _id, name , xp FROM user ORDER BY xp DESC ", null);
//		return cur;
//	}
//	
//	
}
