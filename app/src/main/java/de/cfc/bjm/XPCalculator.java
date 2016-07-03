package de.cfc.bjm;

import android.content.Context;

import de.cfc.bjm.data.DataHolder;
import de.cfc.bjm.db.BJMMySQLDb;
import de.cfc.bjm.model.XpResult;

public class XPCalculator {
	/**Calculates and commits experience to the database. XP Values are returned in double[]:
	 * 
	 * result[0] = Rank bonus
	 * result[1] = personal highscore
	 * result[2] = total xp
	 * result[3] = Level Up bool
	 */
	public static XpResult calcXP(Context ctx, long userid,long lid, float volume, int anzahl, double duration ,boolean winner){
        XpResult xpResult = new XpResult();

		float basexp;

		xpResult.setDrinkVolume(volume);
        xpResult.setDrinkCount(anzahl);

		//calc base experience
		basexp =(volume*anzahl);
        xpResult.setBaseExperience(basexp);

		//calc multiplier
        float speedMultiplicator = 0.0f;
		if(duration/1000<1.625){
            speedMultiplicator = 5 ;
		}else{
            speedMultiplicator = (float) ((1/((0.4*((float)duration))-0.3))+1);

            //0.4
            // ((1/((0.2*x-0.4))+1.0))

            // 0.5
            //

            // 0.3 = gemäß
            //((1/((0.2*x-0.3))+1.0))
		}

        double multi = speedMultiplicator > 1 ? speedMultiplicator : 1;

        xpResult.setSpeedMultiplicator(speedMultiplicator);

        float winnerMultiplicator = 0.0f;

        if(winner){
            winnerMultiplicator = 0.5f;
        }

        multi += winnerMultiplicator;

        xpResult.setWinner(winner);
        xpResult.setWinnerMultiplicator(winnerMultiplicator);

		BJMMySQLDb db =  BJMMySQLDb.getInstance(ctx);
		int rank = db.getRank(lid, DataHolder.volume, DataHolder.liquid, anzahl);


        float rankMultiplicator = 0.0f;
		if(rank<=20&&rank!=-1){
            rankMultiplicator = (float) (1.05f-0.05f*rank);
		}

        multi += rankMultiplicator;
        xpResult.setRank(rank);
        xpResult.setRankMultiplicator(rankMultiplicator);

        // vielleicht weg
        float personalHighscoreMultiplicator = 0.0f;

		if(duration < BJMMySQLDb.getInstance(ctx).getPersonalHighScore(userid, DataHolder.volume, DataHolder.liquid, anzahl)){
            xpResult.setPersonalHighscore(true);
            personalHighscoreMultiplicator = 0.5f;
		}

        multi += personalHighscoreMultiplicator;
        xpResult.setPersonalHighscoreMultiplicator(personalHighscoreMultiplicator);

		int percentage = BJMMySQLDb.getInstance(ctx).getListEntryUserMerit(userid, lid, DataHolder.volume, DataHolder.liquid, DataHolder.anzahl);

        float percentageMultiplicator = (float) (2.02 - (percentage*0.02));

        if(percentageMultiplicator < 0) {
            percentageMultiplicator = 0;
        }

        xpResult.setPersonalDrinksRankPercentage(percentage);
        xpResult.setPersonalDrinksPercentageMultiplicator(percentageMultiplicator);

        multi += percentageMultiplicator;
		
		int finalxp = (int) Math.round(basexp*multi);

        xpResult.setFinalExperience(finalxp);

		db.updateXP(userid, finalxp);
		db.setListEntryXP(lid, finalxp);
		boolean levelUp = db.checkLevel(userid);

        xpResult.setFinalMultiplicator(multi);
        xpResult.setLevelUp(levelUp);

		return xpResult;
    }
}
