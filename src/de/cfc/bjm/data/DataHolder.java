package de.cfc.bjm.data;

import java.util.ArrayList;
import java.util.HashMap;

import de.cfc.bjm.db.BJMDatabase;

public class DataHolder {
	public static BJMDatabase db;
	public static long usr1ID=0;
	public static long usr2ID=0;
	public static int volume=0;
	public static float volume_value=2.5f;
	public static int anzahl_value = 1;
	public static int liquid=0;
	public static int anzahl=0;
	public static long lid;
	public static long lid2nd;
	public static int indexEnsure=1;
	
	/**
	 * 0: id
	 * 1: name
	 * 2: avatar
	 * 3: verbindung
	 * 4: del
	 * 5: xp
	 * 6: inactiv
	 * 7: timestamp
	 * 8: level
	 */
	public static ArrayList<String[]> serverUsers;
	
	/**
	 * 0: id
	 * 1: name
	 * 2: del
	 */
	public static ArrayList<String[]> serverVerbindungen;
	
	/**
	 * 0: id
	 * 1: userid
	 * 2: opponentid
	 * 3: duration
	 * 4: time
	 * 5: volume
	 * 6: liquid
	 * 7: amount
	 * 8: del
	 * 9: xp
	 */
	public static ArrayList<String[]> serverList;
	public static String avatar = "bier";
	public static HashMap<Integer, Integer> rankIdMap;
    public static final HashMap<Integer, Integer> levelToXP = new HashMap<Integer, Integer>(){
		private static final long serialVersionUID = -8145810836394003731L;

		{
            put(1, 10);
            put(2, 25);
            put(3, 50);
            put(4, 90);
            put(5, 145);
            put(6, 200);
            put(7, 280);
            put(8, 360);
            put(9, 520);
        }
    };

}
