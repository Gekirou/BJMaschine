package de.cfc.bjm.model;

import java.io.Serializable;

/**
 * Created by Can on 02/07/16.
 */

public class XpResult implements Serializable{

    float baseExperience = 0.0f;

    float drinkVolume;
    int drinkCount;

    float speedMultiplicator = 1.0f;

    boolean isWinner;
    float winnerMultiplicator = 1.0f;

    int rank;
    float rankMultiplicator = 1.0f;

    boolean isPersonalHighscore;
    float personalHighscoreMultiplicator = 1.0f;

    int personalDrinksRankPercentage;
    float personalDrinksPercentageMultiplicator = 1.0f;

    private int finalExperience;
    private boolean levelUp;
    private double finalMultiplicator;

    public float getDrinkVolume() {
        return drinkVolume;
    }

    public void setDrinkVolume(final float drinkVolume) {
        this.drinkVolume = drinkVolume;
    }

    public int getDrinkCount() {
        return drinkCount;
    }

    public void setDrinkCount(final int drinkCount) {
        this.drinkCount = drinkCount;
    }

    public float getSpeedMultiplicator() {
        return speedMultiplicator;
    }

    public void setSpeedMultiplicator(final float speedMultiplicator) {
        this.speedMultiplicator = speedMultiplicator;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(final boolean winner) {
        isWinner = winner;
    }

    public float getWinnerMultiplicator() {
        return winnerMultiplicator;
    }

    public void setWinnerMultiplicator(final float winnerMultiplicator) {
        this.winnerMultiplicator = winnerMultiplicator;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(final int rank) {
        this.rank = rank;
    }

    public float getRankMultiplicator() {
        return rankMultiplicator;
    }

    public void setRankMultiplicator(final float rankMultiplicator) {
        this.rankMultiplicator = rankMultiplicator;
    }

    public boolean isPersonalHighscore() {
        return isPersonalHighscore;
    }

    public void setPersonalHighscore(final boolean personalHighscore) {
        isPersonalHighscore = personalHighscore;
    }

    public float getPersonalHighscoreMultiplicator() {
        return personalHighscoreMultiplicator;
    }

    public void setPersonalHighscoreMultiplicator(final float personalHighscoreMultiplicator) {
        this.personalHighscoreMultiplicator = personalHighscoreMultiplicator;
    }

    public int getPersonalDrinksRankPercentage() {
        return personalDrinksRankPercentage;
    }

    public void setPersonalDrinksRankPercentage(final int personalDrinksRankPercentage) {
        this.personalDrinksRankPercentage = personalDrinksRankPercentage;
    }

    public float getPersonalDrinksPercentageMultiplicator() {
        return personalDrinksPercentageMultiplicator;
    }

    public float getBaseExperience() {
        return baseExperience;
    }

    public void setBaseExperience(final float baseExperience) {
        this.baseExperience = baseExperience;
    }

    public void setPersonalDrinksPercentageMultiplicator(final float personalDrinksPercentageMultiplicator) {
        this.personalDrinksPercentageMultiplicator = personalDrinksPercentageMultiplicator;
    }

    public void setFinalExperience(final int finalExperience) {
        this.finalExperience = finalExperience;
    }

    public void setLevelUp(final boolean levelUp) {
        this.levelUp = levelUp;
    }

    public int getFinalExperience() {
        return finalExperience;
    }

    public void setFinalMultiplicator(final double finalMultiplicator) {
        this.finalMultiplicator = finalMultiplicator;
    }

    public double getFinalMultiplicator() {
        return finalMultiplicator;
    }
}
