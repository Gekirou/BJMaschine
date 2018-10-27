package de.cfc.bjm.model;

import java.io.Serializable;

/**
 * Created by Can on 02/07/16.
 */

public class XpResult implements Serializable{

    float baseExperience = 0.0f;

    float drinkVolume;
    int drinkCount;
    private int finalExperience;
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

    public float getBaseExperience() {
        return baseExperience;
    }

    public void setBaseExperience(final float baseExperience) {
        this.baseExperience = baseExperience;
    }

    public void setFinalExperience(final int finalExperience) {
        this.finalExperience = finalExperience;
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
