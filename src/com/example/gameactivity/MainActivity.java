package com.example.gameactivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends StandardActivity {
	private static int runder;
	public static Button start;
	public static Activity ma;
	private static String player1;
	private static String player2;
	private TextView view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) { 
	        finish(); 
	        return; 
	    } 
		setContentView(R.layout.main);
		setTitle(R.string.tittel);
		ma = this;
		start = (Button) findViewById(R.id.start_player);
		start.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setRound();
			}
		});
		view = (TextView) findViewById(R.id.textView1);
		view.setText(R.string.regler);
	}

	private void changeListener() {
		start.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startGame();
			}
		});
	}

	private void startGame() {
		Intent i = new Intent(this, GameActivity.class);
		i.putExtra("Rounds", runder);
		i.putExtra("Player1", player1);
		i.putExtra("Player2", player2);
		startActivity(i);
	}

	private void setRound() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setCancelable(false);
		alert.setTitle(R.string.antRunder);

		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				boolean inputOk = true;
				try {
					runder = Integer.parseInt(input.getText().toString().trim());
					if(runder == 0){
						setRound();
						inputOk = false;
						Context context = getApplicationContext();
						CharSequence text = context.getString(R.string.feilmelding);
						int duration = Toast.LENGTH_SHORT;
						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
						Toast.makeText(context, text, duration).show();
					}
				} catch (NumberFormatException e) {
					setRound();
					inputOk = false;
					Context context = getApplicationContext();
					CharSequence text = context.getString(R.string.feilmelding);
					int duration = Toast.LENGTH_SHORT;
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
					Toast.makeText(context, text, duration).show();
				}
				if(inputOk == true){
					setNames();
				}
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

					}
				});

		alert.show();
	}

	private void setNames() {
		LayoutInflater factory = LayoutInflater.from(this);
		final View textEntryView = factory.inflate(R.layout.text_entry, null);
		final EditText input1 = (EditText) textEntryView
				.findViewById(R.id.editText1);
		final EditText input2 = (EditText) textEntryView
				.findViewById(R.id.editText2);
		
		input1.setText("", TextView.BufferType.EDITABLE);
		input2.setText("", TextView.BufferType.EDITABLE);
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setCancelable(false); 
		alert.setTitle(R.string.kallenavn);

		alert.setView(textEntryView);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				player1 = input1.getText().toString();
				player2 = input2.getText().toString();
				changeListener();
				startGame();
			} 
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() { 
					public void onClick(DialogInterface dialog, int whichButton) {

					}
				});

		alert.show();
	}
}
