package de.cfc.bjm

import android.content.Context

import de.cfc.bjm.data.DataHolder
import de.cfc.bjm.db.BJMMySQLDb
import de.cfc.bjm.model.XpResult

object XPCalculator {
    /**
     * Calculates and commits experience to the database. XP Values are returned in double[]:
     *
     *
     * result[0] = Rank bonus
     * result[1] = personal highscore
     * result[2] = total xp
     * result[3] = Level Up bool
     */
    fun calcXP(ctx: Context, userid: Long, lid: Long, volume: Float, anzahl: Int, duration: Double, winner: Boolean): XpResult {

        val xpResult = XpResult()
        val liquidSquared = (lid * lid).toFloat()
        val finalXp = (liquidSquared / duration * 100).toFloat()
        xpResult.isWinner = winner

        val db = BJMMySQLDb.getInstance(ctx)

        val finalxp = Math.round(finalXp)

        xpResult.finalExperience = finalxp

        db.updateXP(userid, finalxp)

        db.setListEntryXP(lid, finalxp)
        val levelUp = db.checkLevel(userid)

        xpResult.setLevelUp(levelUp)

        return xpResult
    }
}
