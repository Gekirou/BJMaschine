package de.cfc.bjm;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.NumberFormat;

import de.cfc.bjm.data.DataHolder;
import de.cfc.bjm.db.BJMMySQLDb;
import de.cfc.bjm.db.PrefManager;
import de.cfc.bjm.model.User;
import de.cfc.bjm.model.XpResult;

import static android.view.MotionEvent.ACTION_HOVER_MOVE;

public class Saufschirm extends Activity {
    private Handler uihandler;
    private TextView dauer1;
    private TextView dauer2;
    private int dauerms1 = 0;
    private int dauerms2 = 0;
    public boolean isActive1 = true;
    public boolean isActive2 = true;
    private long startTime;
    private TextView stopBtn1;
    private TextView stopBtn2;
    LinearLayout usr1LinLay;
    LinearLayout usr2LinLay;
    LinearLayout winnerLinLay;
    TextView saufnutzer1;
    TextView saufnutzer2;
    boolean alternate;
    boolean showActive = true;
    ImageView ava1;
    ImageView ava2;
    MediaPlayer media;
    MediaPlayer saufMedia;
    ProgressDialog progDialog;
    Context myContext;
    boolean postSauf = false;
    BJMMySQLDb db;
    PrefManager prefs;
    private XpResult user1XpResult;
    private XpResult user2XpResult;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.saufschirm);
        myContext = this;
        db = BJMMySQLDb.getInstance(this);
        prefs = PrefManager.getInstance(this);

        saufnutzer1 = findViewById(R.id.saufschirmusr1);
        saufnutzer2 = findViewById(R.id.saufschirmusr2);
        ava1 = findViewById(R.id.user1avatar);
        ava2 = findViewById(R.id.user2avatar);
        usr1LinLay = findViewById(R.id.user1linlay);
        usr2LinLay = findViewById(R.id.user2linlay);

        String nutzername1, nutzername2;
        User user1 = db.getUser(DataHolder.usr1ID);

        nutzername1 = user1.name;
        user1.setAvatarToImageView(ava1, this);


        User user2 = db.getUser(DataHolder.usr2ID);
        nutzername2 = user2.name;
        user2.setAvatarToImageView(ava2, this);

        saufnutzer1.setText(nutzername1);
        saufnutzer2.setText(nutzername2);

        dauer1 = findViewById(R.id.DauerUsr1);
        dauer2 = findViewById(R.id.DauerUsr2);

        stopBtn1 = findViewById(R.id.StopUsr1);
        stopBtn2 = findViewById(R.id.StopUsr2);

        uihandler = new Handler();


        progDialog = new ProgressDialog(this);
        progDialog.setIndeterminate(true);
        progDialog.setMessage("Gut hinhorchen!");
        progDialog.setCancelable(false);
        progDialog.show();


        new Thread(new Runnable() {

            public void run() {
                media = MediaPlayer.create(myContext, R.raw.hochbitte);
                media.start();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.d("BJM", "Could not pause Thread");
                }

                MediaPlayer fertig = MediaPlayer.create(myContext, R.raw.fertig);
                fertig.setOnCompletionListener(new OnCompletionListener() {

                    public void onCompletion(MediaPlayer mp) {
                        postHochBitte();
                    }
                });
                fertig.start();
            }
        }).start();
    }

    public void postHochBitte() {
        runOnUiThread(new Runnable() {

            public void run() {
                int zeitabstand = (int) (1000 * Math.random() + 3000);
                try {
                    Thread.sleep(zeitabstand);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                saufMedia = MediaPlayer.create(myContext, R.raw.saufts);
                saufMedia.start();
                startTime = System.currentTimeMillis();
                progDialog.dismiss();
                uihandler.removeCallbacks(updateui);
                uihandler.postDelayed(updateui, 100);
            }
        });
    }


    public void postSaufSound() {
        progDialog.dismiss();
        startTime = System.currentTimeMillis();
        uihandler.removeCallbacks(updateui);
        uihandler.postDelayed(updateui, 100);
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent ev) {
        boolean consumed = false;
        if (ev.getActionMasked() == ACTION_HOVER_MOVE) {
            isActive2 = false;
            consumed = true;
        }

        return consumed || super.dispatchGenericMotionEvent(ev);
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        Log.d("Input", "Got Key Shortcut Event");
        boolean consumed = false;
        if (event.getKeyCode() == 61) {
            isActive1 = false;
            consumed = true;
        }
        return consumed || super.dispatchKeyShortcutEvent(event);
    }

    protected void onDestroy() {
        super.onDestroy();
        showActive = false;
    }

    private Runnable updateui = new Runnable() {
        public void run() {
            String seconds, mseconds;
            long currentTime = System.currentTimeMillis();
            int sec = (int) ((currentTime - startTime) / 1000);
            int msec = (int) ((currentTime - startTime) - (sec * 1000));
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

            if (isActive1) {
                dauer1.setText(seconds + "." + mseconds);
                dauerms1 = (int) (currentTime - startTime);
            }
            if (isActive2) {
                dauer2.setText(seconds + "." + mseconds);
                dauerms2 = (int) (currentTime - startTime);
            }

            if (!isActive1 && !isActive2) {
                boolean atempo = Math.abs(dauerms1 - dauerms2) < 40;

                if (atempo) {
                    dauerms1 = dauerms2;
                    dauer1.setText(dauer2.getText());
                }

                if (!atempo)
                    MediaPlayer.create(myContext, R.raw.ersterbiersieger).start();
                else
                    MediaPlayer.create(myContext, R.raw.atempo).start();

                uihandler.removeCallbacks(updateui);
                showAlertDialogSaveData();

            } else {
                uihandler.postDelayed(this, 5);
            }
        }
    };

    public void postSauf() {
        postSauf = true;

        final long listid1 = db.insertListEntry(DataHolder.usr1ID, DataHolder.usr2ID, dauerms1, System.currentTimeMillis(), DataHolder.volume, DataHolder.liquid, DataHolder.anzahl);
        final long listid2 = db.insertListEntry(DataHolder.usr2ID, DataHolder.usr1ID, dauerms2, System.currentTimeMillis(), DataHolder.volume, DataHolder.liquid, DataHolder.anzahl);


        if (dauerms1 < dauerms2) {
            winnerLinLay = usr1LinLay;
            calculateAndShowUsr1XP(listid1, true);
            calculateAndShowUsr2XP(listid2, false);
        } else {
            calculateAndShowUsr1XP(listid1, false);
            calculateAndShowUsr2XP(listid2, true);
            winnerLinLay = usr2LinLay;
        }

        uihandler.removeCallbacks(showWinner);
        uihandler.postDelayed(showWinner, 500);

//	        Button zurueck = (Button) findViewById(R.id.returnButton);
//
//	        zurueck.setVisibility(Button.VISIBLE);
//	        zurueck.setOnClickListener(new OnClickListener() {
//				
//				public void onClick(View v) {
//					final Intent i = new Intent(v.getContext(), Start.class);
//			 		startActivity(i);
//
//				}
//			});
//
        usr1LinLay.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                if (postSauf) {
                    Intent intent = new Intent(Saufschirm.this, XpResultActivity.class);
                    intent.putExtra(XpResultActivity.XP_RESULT, user1XpResult);
                    startActivity(intent);
                } else {
                    isActive1 = false;
                }
            }
        });

        usr2LinLay.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                if (postSauf) {
                    Intent intent = new Intent(Saufschirm.this, XpResultActivity.class);
                    intent.putExtra(XpResultActivity.XP_RESULT, user2XpResult);
                    startActivity(intent);
                } else {
                    isActive2 = false;
                }
            }
        });

//	        usr1LinLay.setOnClickListener(new OnClickListener() {
//
//				public void onClick(View v) {
//					if(postSauf){
//						final Intent i = new Intent(v.getContext(), Bestenliste.class);
//						DataHolder.lid = listid1;
//						DataHolder.lid2nd = listid2;
//						showActive=false;
//						i.putExtra("mode", "saufmode");
//						startActivity(i);
//					}
//
//				}
//			});
//
//
//
//	        usr2LinLay.setOnClickListener(new OnClickListener() {
//
//				public void onClick(View v) {
//					if(postSauf){
//
//						final Intent i = new Intent(v.getContext(), Bestenliste.class);
//						DataHolder.lid2nd = listid1;
//				 		DataHolder.lid = listid2;
//						showActive=false;
//						i.putExtra("mode", "saufmode");
//						startActivity(i);
//					}
//				}
//			});

    }

    public void calculateAndShowUsr1XP(long id, boolean winner) {

        user1XpResult = XPCalculator.calcXP(this, DataHolder.usr1ID, id, DataHolder.volume_value, DataHolder.anzahl_value, dauerms1, winner);
        LinearLayout rankBLinLay = (LinearLayout) findViewById(R.id.ss_rmLinLay1);
        LinearLayout persBLinLay = (LinearLayout) findViewById(R.id.ss_pmLinLay1);
        LinearLayout levelUp = (LinearLayout) findViewById(R.id.ss_levelUp1);
        LinearLayout totalXPLinLay = (LinearLayout) findViewById(R.id.ss_txpLinLay1);


        TextView rankBTV = (TextView) findViewById(R.id.ss_rmText1);
        TextView persBTV = (TextView) findViewById(R.id.ss_pmText1);
        TextView totalXPTV = (TextView) findViewById(R.id.ss_txpText1);


        NumberFormat format = NumberFormat.getInstance();
        format.setMinimumFractionDigits(0);
        format.setMaximumFractionDigits(2);

        totalXPTV.setText(format.format(user1XpResult.getFinalExperience()));
        totalXPLinLay.setVisibility(View.VISIBLE);

    }

    public void calculateAndShowUsr2XP(long id, boolean winner) {

        user2XpResult = XPCalculator.calcXP(this, DataHolder.usr2ID, id, DataHolder.volume_value, DataHolder.anzahl_value, dauerms2, winner);
        LinearLayout rankBLinLay = (LinearLayout) findViewById(R.id.ss_rmLinLay2);
        LinearLayout persBLinLay = (LinearLayout) findViewById(R.id.ss_pmLinLay2);
        LinearLayout levelUp = (LinearLayout) findViewById(R.id.ss_levelUp2);
        LinearLayout totalXPLinLay = (LinearLayout) findViewById(R.id.ss_txpLinLay2);


        TextView rankBTV = (TextView) findViewById(R.id.ss_rmText2);
        TextView persBTV = (TextView) findViewById(R.id.ss_pmText2);
        TextView totalXPTV = (TextView) findViewById(R.id.ss_txpText2);


        NumberFormat format = NumberFormat.getInstance();
        format.setMinimumFractionDigits(0);
        format.setMaximumFractionDigits(2);

        totalXPTV.setText(format.format(user2XpResult.getFinalExperience()));

        totalXPLinLay.setVisibility(View.VISIBLE);
    }

    private Runnable showWinner = new Runnable() {
        public void run() {
            if (alternate) {
                winnerLinLay.setBackgroundResource(R.color.white);
                alternate = !alternate;
            } else {
                winnerLinLay.setBackgroundResource(R.color.green);
                alternate = !alternate;
            }
            if (showActive) {
                uihandler.postDelayed(showWinner, 500);
            } else {
                winnerLinLay.setBackgroundResource(R.color.green);
                uihandler.removeCallbacks(showWinner);
            }
        }
    };

    public void showAlertDialogSaveData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Soll der Datensatz gespeichert werden?")
                .setCancelable(false)
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        postSauf();
                    }
                });
        builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                finish();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


}
