package com.example.gameactivity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends StandardActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		TextView vunnet = (TextView) findViewById(R.id.textView2);
		vunnet.setText(getString(R.string.vinner) +"\n" + getIntent().getStringExtra("Vinner"));
		TextView vunnet1 = (TextView) findViewById(R.id.textView1);
		vunnet1.setText(getString(R.string.rundervunnet) +" "+ getIntent().getStringExtra("Spiller1")+ ":\n" + getIntent().getIntExtra("Vunnet1", 0) + "");
		TextView vunnet2 = (TextView) findViewById(R.id.textView3);
		vunnet2.setText(getString(R.string.rundervunnet)+ " " + getIntent().getStringExtra("Spiller2")+ ":\n" + getIntent().getIntExtra("Vunnet2", 0) + "");
		TextView antRunder = (TextView) findViewById(R.id.textView4);
		String antTrekkRunder = "";
		ArrayList<Integer> antTrekk = getIntent().getIntegerArrayListExtra(
				"antTrekk");
		for (int i = 0; i < antTrekk.size(); i++) {
			int j = i+1;
			antTrekkRunder += getString(R.string.runder) + " " + j + ": \n" + antTrekk.get(i) + "\n";
		}
		antRunder.setText(getString(R.string.runderSpilt) + "\n" + antTrekkRunder);
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startNew();
			}
		});
	}

	private void startNew() {
		GameActivity.a.clear();
		GameActivity.atRound = 1;
		GameActivity.player1won = 0;
		GameActivity.player2won = 0;
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}

}
