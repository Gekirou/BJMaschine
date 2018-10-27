package de.cfc.bjm;

import android.content.Context;

import de.cfc.bjm.data.DataHolder;
import de.cfc.bjm.db.BJMMySQLDb;
import de.cfc.bjm.model.XpResult;

class XPCalculator {
    /**
     * Calculates and commits experience to the database. XP Values are returned in double[]:
     * <p>
     * result[0] = Rank bonus
     * result[1] = personal highscore
     * result[2] = total xp
     * result[3] = Level Up bool
     */
    static XpResult calcXP(Context ctx, long userid, long lid, float volume, int anzahl, double duration, boolean winner) {

        XpResult xpResult = new XpResult();
        float liquidSquared = volume * volume;
        float finalXp = (float) (liquidSquared / ((float) (duration)) * 100) * anzahl;

        BJMMySQLDb db = BJMMySQLDb.getInstance(ctx);

        int finalxp = (int) Math.round(finalXp);

        xpResult.setFinalExperience(finalxp);
        db.updateXP(userid, finalxp);
        db.setListEntryXP(lid, finalxp);
        return xpResult;
    }
}
