package de.cfc.bjm.db;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.datatype.Duration;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import de.cfc.bjm.R;
import de.cfc.bjm.data.DataHolder;
import de.cfc.bjm.model.Entry;
import de.cfc.bjm.model.Record;
import de.cfc.bjm.model.User;
import de.cfc.bjm.model.Verbindung;


public class BJMMySQLDb{
    private static final int STATEMENT_TIMEOUT=0;
    
    PrefManager prefmanager;
    
    //Info for the local crm db
    private static String LOCAL_DB_PATH = PrefManager.BASE_FOLDER+ ".databases/";
    private static String LOCAL_DB_NAME = "bjm_db.db";
    private static String LOCAL_DEFAULT_DB_NAME = "default_database.db";
    
    public static final int MODE_REMOTE = 0;
    public static final int MODE_LOCAL = 1;
    public static final int MODE_NO_CONNECTION = 2;
    
    int mode;
  
    private String DB_NAME;
    Context context;
    
	private Connection connection = null;
     
	private static BJMMySQLDb instance;
	
	public static synchronized BJMMySQLDb getInstance(Context ctx) {
		if (instance == null || instance.connection == null) {
			instance = new BJMMySQLDb(ctx);
		}
		return instance;
	}
	
	private BJMMySQLDb(Context ctx){
		this(ctx, false);
	}
	
	private BJMMySQLDb(Context ctx, boolean forceLocal){
		prefmanager = PrefManager.getInstance(ctx);
		context = ctx;
	    DB_NAME = prefmanager.getDBNamespace()+".";
	    if(!forceLocal)
	    	setupRemoteConnection();
	    else
	    	setupLocalConnection();
		
	}
//	
	private void setupLocalConnection(){
		if(!new File(LOCAL_DB_PATH+LOCAL_DB_NAME).exists()){
			Log.d("BJMQLDDB",("local DB not found! Copying default!"));
			try{
				copyDefaultDB();	
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		try {
			DB_NAME="";
			Class.forName("android.database.sqlite");
			connection = DriverManager.getConnection("jdbc:sqlite:"+LOCAL_DB_PATH+LOCAL_DB_NAME);
			mode=MODE_LOCAL;
			Log.d("BJM SQL", "local db successfully loaded!");

		} catch (ClassNotFoundException e) {
			Log.d("BJM SQL","sqlite driver not found.");
			mode=MODE_NO_CONNECTION;
		} catch (SQLException e) {
			Log.d("BJM SQL","Couldn't connect to local or remote db");
			mode=MODE_NO_CONNECTION;
		}
		
	}
	
	private void copyDefaultDB() throws IOException{
		new File(LOCAL_DB_PATH).mkdir();
		
//		File inputFile = new File(LOCAL_DB_PATH+LOCAL_DEFAULT_DB_NAME);
	    File outputFile = new File(LOCAL_DB_PATH+LOCAL_DB_NAME);
	    
	    BufferedInputStream is = new BufferedInputStream(context.getResources().openRawResource(R.raw.bjm_db));
	            int currentByte;
	            int BUFFER = 2048;
	            // establish buffer for writing file
	            byte data[] = new byte[BUFFER];

	            // write the current file to disk
	            FileOutputStream fos = new FileOutputStream(outputFile);
	            BufferedOutputStream dest = new BufferedOutputStream(fos,
	            BUFFER);

	            // read and write until last byte is encountered
	            while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
	                dest.write(data, 0, currentByte);
	            }
	            dest.flush();
	            dest.close();
	            is.close();				
	}

	
	private void setupRemoteConnection(){
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Log.d("SQL Data", "User: "+prefmanager.getDBUser());
				connection = DriverManager.getConnection(prefmanager.getDBURL(),prefmanager.getDBUser(),prefmanager.getDBPassword());
//				connection = DriverManager.getConnection("jdbc:mysql://tri-kon.de:3306/bjm?user=bjm_u&password=6&&9Hpp?78");
			} catch (java.sql.SQLException e) {
				Log.e("BJM", "Setup Remote Connection", e);
				setupLocalConnection();
			} catch (ClassNotFoundException e) {
				Log.e("BJM", "Setup Remote Connection", e);
				setupLocalConnection();

			}
	}
	
	
	public static synchronized BJMMySQLDb getLocalInstance(Context ctx) {
		return new BJMMySQLDb(ctx, true);
	}
	
//	public boolean isConnected(){
//		if(connection == null) return false;
//		boolean result = false;
//		try {
//			Statement stmt = this.getStatement();
//			ResultSet df;
//		
//			df = stmt.executeQuery("SELECT setting FROM "+DB_NAME+"settings");
//			result = df.next();
//		    df.close();
//		    stmt.close();
//		} catch (SQLException e) {
//			result =false;
//			e.printStackTrace();
//		}
//	    
//	    return result;
//	    
//	}
	
	
	public Statement getStatement() throws SQLException{
		if(connection==null) return null;
		Statement statement = connection.createStatement();
	    statement.setQueryTimeout(STATEMENT_TIMEOUT);
	    return statement;
	}
	
	
//    public void insertPosition(Position position) throws SQLException, IOException{
//
//        PreparedStatement preparedStatement = connection.prepareStatement("insert into "+DB_NAME+"position (positionNumber,parent,name,data,preview,date_lastModified,date_created,configuration) values (?, ?, ?, ?, ?, ?, ?, ?)");
//
//        preparedStatement.setInt(1, position.getPositionNumber());
//        preparedStatement.setString(2, position.getParent());
//        preparedStatement.setString(3, position.getName());
//        preparedStatement.setBlob(4, new FileInputStream(new File(position.getDataFilePath())));
//        preparedStatement.setBlob(5, new FileInputStream(new File(position.getPreviewFilePath())));
//       
//
//        preparedStatement.setLong(6, position.getLastModifiedTime());
//        preparedStatement.setLong(7, position.getCreationTime());
//        preparedStatement.setString(8, "");
//
//        preparedStatement.executeUpdate();
//      
//        preparedStatement.close();
//    
//    }
	
//	public Cursor getRecord(int volume, int liquid, int amount){
//		Statement stmt = getStatement();
//	return db.rawQuery("SELECT l._id, u.name, l.duration FROM list AS l, user AS u WHERE l.volume = " + volume + " AND l.amount = "+amount + " AND l.liquid = " + liquid + " AND u._id = l.userid AND l.del = 0 ORDER BY l.duration", null);
//	}
	
	
	public User getUser(long id){
		ResultSet df = null;
		try {
			Statement stmt = getStatement();
			df = stmt.executeQuery("SELECT id, name, verbindungid, xp, timestamp, level FROM "+DB_NAME+"user WHERE id="+id);
			
			if(df.first()){
				if(!(new File(prefmanager.getAvatarFolder()+id+".png").exists())){
						loadAvatar(id);
				}
				
				return new User(df.getLong(1), df.getString(2), df.getLong(3), df.getLong(4), df.getLong(5), df.getInt(6));
			}
			
			df.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<User> getUsersFromVerbindung(long id){
		ResultSet df = null;
		List<User> users = new LinkedList<User>();

		try {
			Statement stmt = getStatement();
			df = stmt.executeQuery("SELECT id, name, verbindungid, xp, timestamp, level FROM "+DB_NAME+"user WHERE verbindungid="+id);
			
			while(df.next()){
//				if(!(new File(prefmanager.getAvatarFolder()+id).exists()))
//						loadAvatar(id);
				
				users.add(new User(df.getLong(1), df.getString(2), df.getLong(3), df.getLong(4), df.getLong(5), df.getInt(6)));
			}
			
			df.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public List<Entry> getEntries(){
		ResultSet df = null;
		List<Entry> users = new LinkedList<Entry>();

		try {
			Statement stmt = getStatement();
			df = stmt.executeQuery("SELECT id, userid, opponentid, duration, time, volume, liquid, amount, del, xp FROM "+DB_NAME+"entries ORDER BY id ASC");
			
			while(df.next()){
				users.add(new Entry(df.getLong(1), df.getLong(2), df.getLong(3), df.getLong(4), df.getLong(5), df.getInt(6), df.getInt(7), df.getInt(8), df.getInt(9)));
			}
			
			df.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public Verbindung getVerbindung(long id){
		ResultSet df = null;
		try {
			Statement stmt = getStatement();
			df = stmt.executeQuery("SELECT id, name FROM "+DB_NAME+"verbindungen WHERE id="+id);
			
			if(df.first()){
				return new Verbindung(df.getLong(1), df.getString(2));
			}
			
			df.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Verbindung> getVerbindungList(){
		ResultSet df = null;
		List<Verbindung> list = new ArrayList<Verbindung>();
		try {
			Statement stmt = getStatement();
			df = stmt.executeQuery("SELECT id, name FROM "+DB_NAME+"verbindungen");
			
			while(df.next()){
				list.add(new Verbindung(df.getLong(1), df.getString(2)));
			}
			
			
			df.close();
			stmt.close();
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
    public boolean loadAvatar(long id){
        try{
        	String tempdir = prefmanager.getAvatarFolder();
        	
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT avatar FROM "+DB_NAME+"user WHERE id=?");
            preparedStatement.setLong(1, id);
            
            ResultSet df = preparedStatement.executeQuery();
            
            if(df.next()){
            	File file = new File(prefmanager.getAvatarFolder()+id+".png");
            	Log.d("BJM", "Writing file to: "+file.getAbsolutePath());
            	FileOutputStream fos = new FileOutputStream(file);
            	fos.write(df.getBytes(1));
            	fos.close();
            }	
            
            df.close();
            preparedStatement.close();
        }catch(Exception e){
        	e.printStackTrace();
        	return false;
        }
        return true;
    }
	
	public double getPersonalHighScore(long id,int volume,  int liquid, int amount){
		try {

		Statement stmt = getStatement();
		ResultSet df;
		df = stmt.executeQuery("SELECT duration FROM "+DB_NAME+"entries WHERE volume = " + volume + " AND amount = "+amount + " AND liquid = " + liquid + " AND userid = " + id +" AND del = 0 ORDER BY duration");
		
			if(df.next()){
				return df.getDouble(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public int getRank(long id, int volume, int liquid, int amount){
//		List<Record> entry = getRecord(volume, liquid, amount);
//		if(entry.size() == 0){
//			return -1;
//		}
//		
//		int i=1;
//		for(Record e : entry){
//			if(e.id==id){
//				return i;
//			}
//			i++;
//		}
//		return -1;	
		try{
			Statement stmt = getStatement();
//			ResultSet df = stmt.executeQuery("SELECT l.id, u.name, l.duration FROM "+DB_NAME+"entries AS l, "+DB_NAME+"user AS u WHERE l.volume = " + volume + " AND l.amount = "+amount + " AND l.liquid = " + liquid + " AND u.id = l.userid AND l.del = 0 ORDER BY l.duration");
			ResultSet df = stmt.executeQuery("SELECT (SELECT COUNT(*) FROM "+DB_NAME+"entries x WHERE x.duration <= t.duration) AS position FROM "+DB_NAME+"entries t WHERE t.id="+id);

//			while(df.next())
//				entry.add(new Record(df.getLong(1), df.getString(2), df.getLong(3)));
			int rank = 0;
			if(df.next())
				rank = df.getInt(1);
			stmt.close();
			df.close();
			return rank;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 9999;
	}
		
	public List<Record> getRecord(int volume, int liquid, int amount){
		List<Record> entry = new ArrayList<Record>();
		try{
			Statement stmt = getStatement();
			ResultSet df = stmt.executeQuery("SELECT l.id, u.name, l.duration FROM "+DB_NAME+"entries AS l, "+DB_NAME+"user AS u WHERE l.volume = " + volume + " AND l.amount = "+amount + " AND l.liquid = " + liquid + " AND u.id = l.userid AND l.del = 0 ORDER BY l.duration");
				
			while(df.next())
				entry.add(new Record(df.getLong(1), df.getString(2), df.getLong(3)));
				
			stmt.close();
			df.close();
			return entry;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return entry;
	}
	
	public List<Record> getRecordWithLimit(int volume, int liquid, int amount, int position, int limit){
		int startindex = position-limit;
		int endindex = position + limit;
		if(startindex<0){
			startindex=0;
			endindex = limit;
		}
		List<Record> entry = new ArrayList<Record>();
		try{
			Statement stmt = getStatement();
			ResultSet df = stmt.executeQuery("SELECT l.id, u.name, l.duration FROM "+DB_NAME+"entries AS l, "+DB_NAME+"user AS u WHERE l.volume = " + volume + " AND l.amount = "+amount + " AND l.liquid = " + liquid + " AND u.id = l.userid AND l.del = 0 ORDER BY l.duration LIMIT "+startindex+","+endindex+"");
				
			while(df.next())
				entry.add(new Record(df.getLong(1), df.getString(2), df.getLong(3)));
				
			stmt.close();
			df.close();
			return entry;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return entry;
	}


	public Long updateXP(long id, int xp){
		try{
			Statement stmt = getStatement();
			ResultSet df = stmt.executeQuery("SELECT xp FROM "+DB_NAME+"user WHERE id="+id);
			
			if(df.next()){
				Long oldXP = df.getLong(1);
			
				PreparedStatement pstmt = connection.prepareStatement("UPDATE "+DB_NAME+"user SET xp = ? WHERE id=?");
				pstmt.setLong(1, xp+oldXP);
				pstmt.setLong(2, id);
				pstmt.executeUpdate();
				
				return xp+oldXP;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return (long) xp;
	}
	
	public void setListEntryXP(long lid, int xp){
		try{
			PreparedStatement pstmt = connection.prepareStatement("UPDATE "+DB_NAME+"entries SET xp=? WHERE id=?");
			pstmt.setLong(1, xp);
			pstmt.setLong(2, lid);
			pstmt.executeUpdate();
		}catch(SQLException e){
			Log.e("BJM", "Error setting ListXP", e);
		}
	}
	
	public boolean checkLevel(long id){
		int lvl = getLevel(id);
		long XP = getXP(id);
		if(XP > DataHolder.levelToXP.get(lvl)){
			setLevel(id, lvl+1);
			return true;
		}else{
			return false;
		}
	}
	
	public int getLevel(long id){
		Statement stmt;
		try {
			stmt = getStatement();

			ResultSet df = stmt.executeQuery("SELECT level FROM "+DB_NAME+"user WHERE id="+id);
			if(df.next()){
				return df.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public void setLevel(long id, int lvl){
		try {
			PreparedStatement pstmt = connection.prepareStatement("UPDATE "+DB_NAME+"user SET level=? WHERE id=?");
			
			pstmt.setInt(1, lvl);
			pstmt.setLong(2, id);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public long getXP(long id){
		Statement stmt;
		try {
			stmt = getStatement();
	
		ResultSet df = stmt.executeQuery("SELECT xp FROM "+DB_NAME+"user WHERE id="+id);
		if(df.next()){
			return df.getLong(1);
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (long) -1;
	}
	
	public long insertListEntry(long userid, long opponentid, int duration,  long time, int volume, int liquid, int amount){
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("insert into "+DB_NAME+"entries (userid, opponentid, duration, time, volume, liquid, amount, del, xp)" +
					" values (?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, userid);
			preparedStatement.setLong(2, opponentid);
			preparedStatement.setLong(3, duration);
			preparedStatement.setLong(4, time);
			preparedStatement.setInt(5, volume);
			preparedStatement.setInt(6, liquid);
			preparedStatement.setInt(7, amount);
			preparedStatement.setInt(8, 0);
			preparedStatement.setInt(9, 0);
			
			preparedStatement.executeUpdate();
			
			ResultSet df = preparedStatement.getGeneratedKeys();
			df.next();
			return df.getLong(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public long insertEntry(Entry entry){
		return insertListEntry(entry.userid, entry.opponentid, (int) entry.duration, entry.time, entry.volume, entry.liquid, entry.amount);
	}
	
	public long getLastDrinkFromUser(long userid){
		Statement stmt;
		try {
			stmt = getStatement();
		
			ResultSet df = stmt.executeQuery("SELECT MAX(id) FROM "+DB_NAME+"entries WHERE userid="+userid);
			if(df.next())
				return df.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public long insertUser(String username, Bitmap avatar, long verbindung){
		PreparedStatement pstmt;
		try {
			pstmt = connection.prepareStatement("INSERT INTO "+DB_NAME+"user (name, avatar, verbindungid, del, xp, inactive, level, timestamp) values (?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, username);
		
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			avatar.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] byteArray = stream.toByteArray();
			
			pstmt.setBytes(2, byteArray);
			pstmt.setLong(3, verbindung);
			pstmt.setInt(4, 0);
			pstmt.setLong(5, 0);
			pstmt.setInt(6, 0);
			pstmt.setInt(7, 1);
			pstmt.setLong(8, System.currentTimeMillis());

			pstmt.executeUpdate();
			
			ResultSet df = pstmt.getGeneratedKeys();
			df.next();
			return df.getLong(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public int getTotalBJ(long id){
		try {
			Statement stmt = getStatement();
			ResultSet df = stmt.executeQuery("SELECT COUNT(id) FROM "+DB_NAME+"entries WHERE userid="+id+" AND del = 0");
			
			if(df.next())
				return df.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public long getFavOpponent(long id){
		Statement stmt;
		try {
			stmt = getStatement();
		
		ResultSet df = stmt.executeQuery("SELECT opponentid FROM "+DB_NAME+"entries WHERE userid = "+id+" ORDER BY count(*)");
		if(df.next())
			return df.getLong(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public Record getRecordInformation(long listid){
		Statement stmt;
		try {
			stmt = getStatement();
		
			ResultSet df = stmt.executeQuery("SELECT u.id, u.name, l.duration, o.id, l.time, l.volume, l.liquid, l.amount FROM "+DB_NAME+"entries AS l, "+DB_NAME+"user AS u, "+DB_NAME+"user AS o WHERE l.id = " + listid + " AND u.id = userid AND o.id = opponentid");
		if(df.next())
			return new Record(df.getLong(1), df.getString(2), df.getLong(3), df.getLong(4), df.getLong(5), df.getInt(6), df.getInt(7), df.getInt(8));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<User> getXPRecord(){
		List<User> users = new ArrayList<User>();
		try {
		Statement stmt;
		stmt = getStatement();
		
		ResultSet rs = stmt.executeQuery("SELECT id, name , xp FROM "+DB_NAME+"user ORDER BY xp DESC ");
		while(rs.next())
			users.add(new User(rs.getLong(1), rs.getString(2), rs.getLong(3)));
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public long insertVerbindung(String name){
		PreparedStatement pstmt;
		try {
			pstmt = connection.prepareStatement("INSERT INTO "+DB_NAME+"verbindungen (name) values (?)", Statement.RETURN_GENERATED_KEYS);
	
		pstmt.setString(1, name);
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		
		rs.next();
		return rs.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int getMode() {
		return mode;
	}
	
	 public int getRecordCount(String tableName){
	    	PreparedStatement preparedStatement;
			try {
				preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS total FROM "+DB_NAME+tableName);
			
	        ResultSet df= preparedStatement.executeQuery();
	        int result = 0;
	        if(df.next())
	        	result = df.getInt("total");
	        df.close();
	        preparedStatement.close();
	        return result;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return -1;
			
	    }
	    public int getAllRecordCounts(){
	    	int result = 0;
	    	result += getRecordCount("entries");
	    	result += getRecordCount("user");
	    	result += getRecordCount("verbindungen");
	    	return result;
	    }
	    
	    public void clear(String tableName) throws SQLException{
	      	PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM "+DB_NAME+tableName);
	        preparedStatement.executeUpdate(); 
	        preparedStatement.close();  
	    }
	    public void clearAll() throws SQLException{
	    	clear("entries");
	    	clear("user");
	    	clear("verbindungen");
	    }
	    
	    public float getUserMeanTime(long userid, int volume, int liquid, int amount){
	    	PreparedStatement preparedStatement;
			try {
				preparedStatement = connection.prepareStatement("SELECT avg(duration) FROM "+DB_NAME+"entries WHERE userid="+userid+" AND volume="+volume+" AND liquid="+liquid+" AND amount="+amount);
			
	        ResultSet df= preparedStatement.executeQuery();
	        float result = 0;
	        if(df.next())
	        	result = df.getFloat(1);
	        df.close();
	        preparedStatement.close();
	        return result;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return -1;
	    }
	    
	    public int getListEntryUserMerit(long userid, long lid, int volume, int liquid, int amount){

			try {
		        Statement stmt0 = getStatement();
			ResultSet df = stmt0.executeQuery("SELECT (SELECT COUNT(*) FROM "+DB_NAME+"entries x WHERE x.duration <= t.duration AND userid="+userid+") AS position FROM "+DB_NAME+"entries t WHERE t.id="+lid);
				
	        int result = 0;
	        if(df.next())
	        	result = df.getInt(1);
	        df.close();
	        stmt0.close();
	        
	        Statement stmt = getStatement();
			ResultSet drinkCount = stmt.executeQuery("SELECT COUNT(id) FROM "+DB_NAME+"entries WHERE userid="+userid+" AND del = 0 AND volume="+volume+" AND liquid="+liquid+" AND amount="+amount);
	        
			int drinks = 0;
			if(drinkCount.next())
				drinks = drinkCount.getInt(1);
			
			int percentage = (int) (100*(((float)result)/((float)drinks)));
//			Log.d("BJMMySQLDb", "Drink position: "+result+" total drinks: "+drinks+" percentage: "+percentage);
			return percentage;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return -1;
	    }
}