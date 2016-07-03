package de.cfc.bjm.model;

public class Entry {
	public long id;
	public long userid;
	public long opponentid;
	public long duration;
	public long time;
	public int volume;
	public int liquid;
	public int amount;
	public int xp;
	
	public Entry(long id, long userid, long opponentid, long duration, long time, int volume, int liquid, int amount, int xp){
		this.id = id;
		this.userid = userid;
		this.opponentid = opponentid;
		this.duration = duration;
		this.time = time;
		this.volume = volume;
		this.liquid = liquid;
		this.amount = amount;
		this.xp = xp;
	}
	
	
}
