package de.cfc.bjm;

import java.io.File;
import java.util.Calendar;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import de.cfc.bjm.data.DataHolder;
import de.cfc.bjm.db.BJMMySQLDb;
import de.cfc.bjm.model.Record;
import de.cfc.bjm.model.User;


public class SaufDetail extends Activity {
    private long id;
    private ImageView avatar;
    private TextView username;
    private TextView listid;
    private ImageView opponentavatar;
    private TextView opponentname;
    private TextView duration;
    private TextView gemaess;
    private TextView getraenk;
    private TextView uhrzeit;
    private TextView datum;
    private TextView anzahl;
    Calendar cal;
    BJMMySQLDb db;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saufdetail);
        Bundle extras = getIntent().getExtras();
        db = BJMMySQLDb.getInstance(this);
        id = extras.getLong("id");

        avatar = (ImageView) findViewById(R.id.sd_mainavatar);
        username = (TextView) findViewById(R.id.sd_mainname);
        listid = (TextView) findViewById(R.id.sd_bj);
        duration = (TextView) findViewById(R.id.sd_maintime);
        opponentavatar = (ImageView) findViewById(R.id.sd_opavatar);
        opponentname = (TextView) findViewById(R.id.sd_opname);
        gemaess = (TextView) findViewById(R.id.sd_gemaess);
        getraenk = (TextView) findViewById(R.id.sd_getraenk);
        uhrzeit = (TextView) findViewById(R.id.sd_uhrzeit);
        datum = (TextView) findViewById(R.id.sd_datum);
        anzahl = (TextView) findViewById(R.id.sd_anzahl);


        listid.setText("Bierjunge Nr. " + id);


        Record cur = db.getRecordInformation(id);
        User user = db.getUser(cur.id);

        username.setText(cur.username);
        user.setAvatarToImageView(avatar, this);


        long durationInt = cur.duration;

        String seconds, mseconds;
        int sec = (int) ((durationInt) / 1000);
        int msec = (int) ((durationInt) - (sec * 1000));
        if (msec < 10) {
            mseconds = "00" + msec;
        } else {
            if (msec < 100) {
                mseconds = "0" + msec;
            } else {
                mseconds = "" + msec;
            }
        }
        seconds = "" + sec;

        duration.setText(seconds + "." + mseconds);

        cal = Calendar.getInstance();
        cal.setTimeInMillis(cur.time);
        String minute;
        if (cal.get(Calendar.MINUTE) < 10) {
            minute = "0" + cal.get(Calendar.MINUTE);
        } else {
            minute = "" + cal.get(Calendar.MINUTE);
        }
        uhrzeit.setText(cal.get(Calendar.HOUR_OF_DAY) + "." + minute + " Uhr");
        datum.setText(cal.get(Calendar.DAY_OF_MONTH) + "." + (cal.get(Calendar.MONTH) + 1) + "." + cal.get(Calendar.YEAR));
        User opCursor = db.getUser(cur.opponentid);
        opCursor.setAvatarToImageView(opponentavatar, this);

        opponentname.setText(opCursor.name);
        String[] gemaesse = getResources().getStringArray(R.array.gemaess_array);
        String[] getraenke = getResources().getStringArray(R.array.getraenk_array);
        String[] anzahlbier = getResources().getStringArray(R.array.anzahl_bier);

        gemaess.setText(gemaesse[cur.volume]);
        getraenk.setText(getraenke[cur.liquid]);
        anzahl.setText(anzahlbier[cur.amount]);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(1, getIntent());

    }


}
