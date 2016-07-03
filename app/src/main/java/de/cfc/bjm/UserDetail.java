package de.cfc.bjm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import de.cfc.bjm.data.DataHolder;
import de.cfc.bjm.db.BJMMySQLDb;
import de.cfc.bjm.model.User;
import de.cfc.bjm.model.Verbindung;

public class UserDetail extends Activity{
	private long id;
	BJMMySQLDb db;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userdetail);
		db = BJMMySQLDb.getInstance(this);
		Bundle extras=getIntent().getExtras();
		id=extras.getLong("id");
		
		getData();
	}
	public void getData(){
		TextView name = (TextView)findViewById(R.id.ud_name);
		ImageView avatar = (ImageView)findViewById(R.id.ud_avatar);
		TextView totalbj=(TextView) findViewById(R.id.ud_totalbj);
		TextView exp = (TextView) findViewById(R.id.ud_xp);
		TextView verbindung = (TextView) findViewById(R.id.ud_verbindung);
		LinearLayout opLinLay = (LinearLayout) findViewById(R.id.ud_oplinlay);
		ImageView op1img = (ImageView) findViewById(R.id.ud_opavatar1);
		TextView op1name = (TextView) findViewById(R.id.ud_opname1);
		TextView level = (TextView) findViewById(R.id.ud_level);
		TextView toNextLevel = (TextView) findViewById(R.id.ud_xpToNextLevel);
		ProgressBar xpProgress = (ProgressBar) findViewById(R.id.ud_xpProgress);
		TextView mean025 = (TextView) findViewById(R.id.mean025);
		TextView mean03 = (TextView) findViewById(R.id.mean03);
		
		User user = db.getUser(id);
		name.setText(user.name);
		user.setAvatarToImageView(avatar, this);
		
		totalbj.setText(""+db.getTotalBJ(id));
		long XP = db.getXP(id);
		int levelInt = db.getLevel(id);
		level.setText(""+levelInt);
		exp.setText(""+XP);
		long xpDif = DataHolder.levelToXP.get(levelInt)-XP;
		toNextLevel.setText(""+xpDif);
		Verbindung bund = db.getVerbindung(user.verbindungid);
		verbindung.setText(bund.name);

		mean025.setText(String.format("%.3f", (db.getUserMeanTime(id, 0, 0, 0)/1000)));
		mean03.setText(String.format("%.3f", (db.getUserMeanTime(id, 1, 0, 0)/1000)));
		
		if(levelInt>1){
			xpProgress.setMax(DataHolder.levelToXP.get(levelInt)-DataHolder.levelToXP.get(levelInt-1));
			xpProgress.setProgress((int) (XP-DataHolder.levelToXP.get(levelInt-1)));
		}else{
			xpProgress.setMax(DataHolder.levelToXP.get(levelInt));
			xpProgress.setProgress((int) XP);
		}
		long oid = db.getFavOpponent(id);
		
		if(oid!=0){
			User opponent = db.getUser(oid);
			opLinLay.setVisibility(View.VISIBLE);
			op1name.setText(opponent.name);
			opponent.setAvatarToImageView(op1img, this);
		}
	}

}
