package de.cfc.bjm;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import de.cfc.bjm.model.XpResult;

/**
 * Created by Can on 02/07/16.
 */

public class XpResultActivity extends Activity {


    public static final String XP_RESULT = "XP_RESULT";
    private XpResult xpResult;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xp_result_screen);

        xpResult = (XpResult) getIntent().getSerializableExtra(XP_RESULT);
        List<MultiplicatorEntry> entries = getMultiplicatorEntries();

        ListView xpMultiplactorList = (ListView) findViewById(R.id.xpMultiplicatorList);
        xpMultiplactorList.setAdapter(new MultiplicatorEntryAdapter(this, entries));

        TextView baseExperience = (TextView) findViewById(R.id.baseExperience);
        baseExperience.setText(floatToString(xpResult.getBaseExperience()));

        TextView multiplicator = (TextView) findViewById(R.id.finalMultiplicator);
        multiplicator.setText(floatToString(xpResult.getFinalMultiplicator()));

        TextView finalExperience = (TextView) findViewById(R.id.finalXp);
        finalExperience.setText(xpResult.getFinalExperience()+"");
    }

    List<MultiplicatorEntry> getMultiplicatorEntries() {
        List<MultiplicatorEntry> entries = new ArrayList<MultiplicatorEntry>();

        entries.add(new MultiplicatorEntry("Zeit", floatToString(xpResult.getSpeedMultiplicator())));
        entries.add(new MultiplicatorEntry("Gewinner", "+ " +  floatToString(xpResult.getWinnerMultiplicator())));
        entries.add(new MultiplicatorEntry("Rang "+xpResult.getRank(), "+ " +  floatToString(xpResult.getRankMultiplicator())));
        entries.add(new MultiplicatorEntry("Persönlicher Highscore ", "+ " +  floatToString(xpResult.getPersonalHighscoreMultiplicator())));
        entries.add(new MultiplicatorEntry("Persönlicher Rang in %: " + xpResult.getPersonalDrinksRankPercentage(), "+ " +  floatToString(xpResult.getPersonalDrinksPercentageMultiplicator())));

        return entries;
    }

    public static String floatToString(double d)
    {
        return new DecimalFormat("#.##").format(d);
    }
}
