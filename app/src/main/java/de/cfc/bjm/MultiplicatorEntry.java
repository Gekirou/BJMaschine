package de.cfc.bjm;

/**
 * Created by Can on 02/07/16.
 */
public class MultiplicatorEntry {
    String multiplicatorName;
    String multiplicator;

    public MultiplicatorEntry(final String multiplicatorName, final String multiplicator) {
        this.multiplicatorName = multiplicatorName;
        this.multiplicator = multiplicator;
    }

    public String getMultiplicatorName() {
        return multiplicatorName;
    }

    public void setMultiplicatorName(final String multiplicatorName) {
        this.multiplicatorName = multiplicatorName;
    }

    public String getMultiplicator() {
        return multiplicator;
    }

    public void setMultiplicator(final String multiplicator) {
        this.multiplicator = multiplicator;
    }
}
