package de.cfc.bjm;

import android.content.Context;
import android.util.Log;
import de.cfc.bjm.data.DataHolder;
import de.cfc.bjm.db.BJMMySQLDb;

public class XPCalculator {
	/**Calculates and commits experience to the database. XP Values are returned in double[]:
	 * 
	 * result[0] = Rank bonus
	 * result[1] = personal highscore
	 * result[2] = total xp
	 * result[3] = Level Up bool
	 */
	public static double[] calcXP(Context ctx, long userid,long lid, float volume, int anzahl, double duration ,boolean winner){
		
		double[] result = new double[4];
		result[0]=0;
		result[1]=0;
		result[2]=0;
		result[3]=0;
		float basexp;
		double multi;
		
		//calc base experience
		basexp =(volume*anzahl);
		Log.d("BJM", "BaseXP: "+basexp+" volume: "+volume+" anzahl: "+anzahl);
		
		//calc multiplier
		if(duration/1000<1.625){
			multi = 5 ;
		}else{
		//idea: -1.8^log(x-2)+4
//			multi=(1/(0.8*(((float)duration)/1000)-0.8))+1;
			multi = (1/((0.4*((float)duration))-0.3))+1;
//			multi = -(duration/2000) + 5;
			// possible: -(x/1000-1.8)^2 + 3 + (x/1000)
		}
		multi = multi < 1 ? 1 : multi;
		
		Log.d("BJM", "Multi after calc: "+multi);

		if(winner){
			multi=multi+0.5;
		}
		Log.d("BJM", "Multi after winner bonus "+multi);

		
		BJMMySQLDb db =  BJMMySQLDb.getInstance(ctx);
		int rank = db.getRank(lid, DataHolder.volume, DataHolder.liquid, anzahl);
		Log.d("BJM", "rank: "+rank);

		if(rank<=20&&rank!=-1){
			multi=multi+(1.05-0.05*rank);
			result[0]= rank;
		}
		if(duration< BJMMySQLDb.getInstance(ctx).getPersonalHighScore(userid, DataHolder.volume, DataHolder.liquid, anzahl)){
			multi=multi+0.5;
			result[1]=0.5;
		}
		
		int percentage = BJMMySQLDb.getInstance(ctx).getListEntryUserMerit(userid, lid, DataHolder.volume, DataHolder.liquid, DataHolder.anzahl);
		
		multi += (2.02 - (percentage*0.02));
		
		int finalxp = (int) Math.round(basexp*multi);
		Log.d("BJM", "Multi after pers. highscore and rank "+multi);

		db.updateXP(userid, finalxp);
		db.setListEntryXP(lid, finalxp);
		boolean levelUp = db.checkLevel(userid);
		result[2]= finalxp;
		result[3]= (levelUp)?1:0;
		return result;
    }
}
