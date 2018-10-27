package de.cfc.bjm;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import de.cfc.bjm.data.DataHolder;
import de.cfc.bjm.data.Sounds;
import de.cfc.bjm.db.BJMMySQLDb;
import de.cfc.bjm.db.PrefManager;
import de.cfc.bjm.model.User;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

import static android.view.MotionEvent.ACTION_HOVER_MOVE;

public class Start extends Activity {
    public static final int INTERVAL_SOUND = 10000;
    public static final int SOUND_MINIMUM_TIME = 30000;
    LinearLayout usr1;
    LinearLayout usr2;
    TextView user1text;
    TextView user2text;
    ImageView user1img;
    ImageView user2img;
    Spinner gemaessspinner;
    Spinner modusspinner;
    Spinner getraenkspinner;
    Bundle instance;
    BJMMySQLDb db;
    PrefManager prefs;
    Handler soundHandler;
    boolean playedSound;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.modescreen);
        instance = savedInstanceState;

        prefs = PrefManager.getInstance(this);
        db = BJMMySQLDb.getInstance(this);
        initStart();
        soundHandler = new Handler();

        if (!playedSound) {

            final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.intro);
            mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

                public void onCompletion(MediaPlayer mp) {
                    soundHandler.removeCallbacks(soundRunner);
                    soundHandler.postDelayed(soundRunner, generateNextSoundInterval());
                }


            });

            mediaPlayer.start();
        } else {
            soundHandler.postDelayed(soundRunner, generateNextSoundInterval());
        }
    }

    private long generateNextSoundInterval() {
        return (long) (SOUND_MINIMUM_TIME + (Math.random() * INTERVAL_SOUND));
    }

    public void initStart() {
        String[] anzahl_array = getResources().getStringArray(R.array.anzahl_bier);
        String[] gemaess_array = getResources().getStringArray(R.array.gemaess_array);
        String[] getraenk_array = getResources().getStringArray(R.array.getraenk_array);

        final WheelView anzahl = findViewById(R.id.anzahl_wheel);
        ArrayWheelAdapter<String> aadapter = new ArrayWheelAdapter<String>(this, anzahl_array);
        aadapter.setTextSize(20);
        anzahl.setViewAdapter(aadapter);
        anzahl.setCurrentItem(0);
        anzahl.addChangingListener(wl);

        final WheelView gemaess = findViewById(R.id.gemaess_wheel);
        ArrayWheelAdapter<String> gadapter = new ArrayWheelAdapter<String>(this, gemaess_array);
        gadapter.setTextSize(20);
        gemaess.setViewAdapter(gadapter);
        gemaess.setCurrentItem(0);
        gemaess.addChangingListener(wl);

        final WheelView getraenk = findViewById(R.id.getraenk_wheel);
        ArrayWheelAdapter<String> dadapter = new ArrayWheelAdapter<String>(this, getraenk_array);
        dadapter.setTextSize(20);
        getraenk.setViewAdapter(dadapter);
        getraenk.setCurrentItem(0);
        getraenk.addChangingListener(wl);

        Button startbtn = findViewById(R.id.startbtn);
        startbtn.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                if (DataHolder.usr1ID != 0 && DataHolder.usr2ID != 0) {
                    final Intent i = new Intent(v.getContext(), Saufschirm.class);
                    startActivityForResult(i, 0);
                } else {
                    showAlertDialog();
                }

            }
        });

        usr1 = findViewById(R.id.Usr1);
        usr2 = findViewById(R.id.Usr2);

        usr1.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                final Intent i = new Intent(v.getContext(), SelectUserPanel.class);
                i.putExtra("position", 0);
                startActivityForResult(i, 0);
            }
        });

        usr2.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                final Intent i = new Intent(v.getContext(), SelectUserPanel.class);
                i.putExtra("position", 1);
                startActivityForResult(i, 0);

            }
        });


        if (DataHolder.usr1ID != 0) {
            user1text = (TextView) usr1.findViewById(R.id.user1name);
            user1img = (ImageView) usr1.findViewById(R.id.user1img);
            TextView level = (TextView) usr1.findViewById(R.id.start_levelUsr1);
            User user = db.getUser(DataHolder.usr1ID);
            user1text.setText(user.name);
            level.setText(user.level + "");
            user.setAvatarToImageView(user1img, this);
        }

        if (DataHolder.usr2ID != 0) {
            user2text = (TextView) usr2.findViewById(R.id.user2name);
            user2img = (ImageView) usr2.findViewById(R.id.user2img);
            TextView level = (TextView) usr2.findViewById(R.id.start_levelUsr2);


            User user = db.getUser(DataHolder.usr2ID);

            user2text.setText(user.name);
            level.setText("" + user.level);
            user.setAvatarToImageView(user2img, this);
        }

    }


    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent ev) {
        boolean consumed = false;
        if (ev.getActionMasked() == ACTION_HOVER_MOVE) {
            consumed = true;
        }

        return consumed || super.dispatchGenericMotionEvent(ev);
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        if (event.getKeyCode() == 61 && event.getAction() == KeyEvent.ACTION_DOWN) {
            usr1.setBackgroundColor(Color.GREEN);
        } else if (event.getKeyCode() == 61 && event.getAction() == KeyEvent.ACTION_UP) {
            usr1.setBackgroundColor(Color.WHITE);
        }

        return super.dispatchKeyShortcutEvent(event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            FullscreenMode.hideSystemUI(getWindow());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newuser:
                final Intent intent = new Intent(this, NewUserPanel.class);
                startActivity(intent);
                break;
            case R.id.bestlist:
                final Intent i = new Intent(this, Bestenliste.class);
                i.putExtra("mode", "normal");
                i.putExtra("volume", DataHolder.volume);
                i.putExtra("liquid", DataHolder.liquid);
                i.putExtra("anzahl", DataHolder.anzahl);
                startActivity(i);
                break;
            case R.id.newverbindung:
                final Intent verb = new Intent(this, NewVerbPanel.class);
                startActivity(verb);
                break;
            case R.id.ranklist:
                final Intent ranks = new Intent(this, Rangliste.class);
                startActivity(ranks);
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return true;
    }

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Zu doof 2 Nutzer einzugeben?")
                .setCancelable(false)
                .setPositiveButton("Sorry!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    OnWheelChangedListener wl = new OnWheelChangedListener() {

        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            switch (wheel.getId()) {
                case R.id.gemaess_wheel:
                    int[] gemaess_array = getResources().getIntArray(R.array.gemaess_array_values);
                    DataHolder.volume_value = gemaess_array[newValue];
                    DataHolder.volume = newValue;
                    break;
                case R.id.getraenk_wheel:
                    DataHolder.liquid = newValue;
                    break;
                case R.id.anzahl_wheel:
                    int[] anzahl_array = getResources().getIntArray(R.array.anzahl_bier_values);
                    DataHolder.anzahl = newValue;
                    DataHolder.anzahl_value = anzahl_array[newValue];
                    break;

            }

        }

    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        playedSound = true;
        onCreate(instance);
    }

    public void showNoServerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Server nicht verfügbar!")
                .setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    Runnable soundRunner = new Runnable() {

        public void run() {
            int sound = (int) (Math.random() * Sounds.SOUNDS.length);
            final MediaPlayer saufMedia = MediaPlayer.create(Start.this, Sounds.SOUNDS[sound]);
            saufMedia.setOnCompletionListener(new OnCompletionListener() {

                public void onCompletion(MediaPlayer mp) {
                    soundHandler.postDelayed(soundRunner, generateNextSoundInterval());
                }
            });
            saufMedia.start();

        }
    };

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        playedSound = true;
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        soundHandler.removeCallbacks(soundRunner);
        super.onDestroy();
    }

    protected void onPause() {
        soundHandler.removeCallbacks(soundRunner);
        super.onPause();
    }
}