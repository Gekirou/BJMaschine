package de.cfc.bjm.model;

public class Record {

	 public long id;
	 public String username;
	 public long duration;
	 public long opponentid;
	 public long time;
	 public int volume;
	 public int liquid;
	 public int amount;
	 
	 public Record(long id, String username, long duration, long opponentid, long time, int volume, int liquid, int amount){
		 this.id = id;
		 this.username = username;
		 this.duration = duration;
		 this.opponentid = opponentid;
		 this.time = time;
		 this.volume = volume;
		 this.liquid = liquid;
		 this.amount = amount;
	 }
	 
	 public Record(long id, String username, long duration){
		 this.id = id;
		 this.username = username;
		 this.duration = duration;
	 }
}
