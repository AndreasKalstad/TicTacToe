package com.example.gameactivity;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GameActivity extends Activity{
	public static int rounds;
	private TextView mRoundView;
	public static int atRound = 1;
	public static int player1won;
	public static int player2won;
	public static Activity ga;
	private int antTrekk;
	private static String vinner;
	private String player1;
	private String player2;
	public static ArrayList<Integer> a = new ArrayList<Integer>();
	private boolean noughtsTurn = false;
	private char board[][] = new char[3][3];
	private boolean checkWin;
	private Button newGame;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lib_game);
		ga = this;
		setTitle(R.string.title_activity_game);
		setupOnClickListeners();
		resetButtons();
		rounds = getIntent().getIntExtra("Rounds", 0);
		mRoundView = (TextView) findViewById(R.id.rounds);
		mRoundView.setText(getString(R.string.runder) + ":" + atRound + " / "
				+ rounds);
		player1 = getIntent().getStringExtra("Player1");
		player2 = getIntent().getStringExtra("Player2");
		newGame = (Button) findViewById(R.id.button8);
		newGame.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				atRound++;
				restartActivity();
			}
		});
		newGame.setEnabled(false);
	}

	private void resetButtons() {
		TableLayout TL = (TableLayout) findViewById(R.id.tableLayout);
		for (int x = 0; x < TL.getChildCount(); x++) {
			if (TL.getChildAt(x) instanceof TableRow) {
				TableRow R = (TableRow) TL.getChildAt(x);
				for (int y = 0; y < R.getChildCount(); y++) {
					if (R.getChildAt(y) instanceof Button) {
						Button knapp = (Button) R.getChildAt(y);
						knapp.setText("");
						knapp.setEnabled(true);
					}
				}
			}
		}
	}

	private void checkWin() {
		char winner = 'U';
		if (checkWinner(board, 'X')) {
			winner = 'X';
		} else if (checkWinner(board, 'O')) {
			winner = 'O';
		}
		if (winner == 'U' && antTrekk != 9) {
			checkWin = false;
		} else {
			TextView T = (TextView) findViewById(R.id.info_turn);
			if (winner == 'X') {
				T.setText(player1 + " " + getString(R.string.vinn));
				player1won++;
			} else if (winner == 'O') {
				T.setText(player2 + " " + getString(R.string.vinn));
				player2won++;
			} else if (winner == 'U') {
				T.setText(getString(R.string.uavgjort));
			}
			checkWin = true;
		}
		if (checkWin == true) {
			newGame.setEnabled(true);
			if (antTrekk == 9) {
				a.add(9);
			} else
				a.add(antTrekk);
			if (atRound == rounds) {
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setCancelable(false);
				newGame.setEnabled(false);
				if (player1won > player2won) {
					alert.setMessage(player1 + " har vunnet flest kamper!");
					vinner = player1;
				} else if (player1won < player2won) {
					alert.setMessage(player2 + " har vunnet flest kamper!");
					vinner = player2;
				} else if (player1won == player2won) {
					alert.setMessage("Det ble uavgjort!");
					vinner = "Uavgjort";
				}

				alert.setPositiveButton(R.string.resultater,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								Intent i = new Intent(GameActivity.this,
										ResultActivity.class);
								i.putExtra("Vinner", vinner);
								i.putExtra("Vunnet1", player1won);
								i.putExtra("Vunnet2", player2won);
								i.putExtra("Spiller1", player1);
								i.putExtra("Spiller2", player2);
								i.putIntegerArrayListExtra("antTrekk", a);
								startActivity(i);
								GameActivity.this.finish();
								MainActivity.ma.finish();
							}
						});

				alert.show();
			}
		}
	}

	private boolean checkWinner(char[][] board, char player) {
		int size = 3;
		for (int x = 0; x < size; x++) {
			int total = 0;
			for (int y = 0; y < size; y++) {
				if (board[x][y] == player) {
					total++;
				}
			}
			if (total >= size) {
				return true;
			}
		}

		for (int x = 0; x < size; x++) {
			int total = 0;
			for (int y = 0; y < size; y++) {
				if (board[y][x] == player) {
					total++;
				}
			}
			if (total >= size) {
				return true;
			}
		}

		int total = 0;
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (x == y && board[x][y] == player) {
					total++;
				}
			}
		}
		if (total >= size) {
			return true;
		}

		total = 0;
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (x + y == size - 1 && board[x][y] == player) {
					total++;
				}
			}
		}
		
		if (total >= size) {
			return true;
		}
		return false;
	}

	private void disableButtons() {
		TableLayout T = (TableLayout) findViewById(R.id.tableLayout);
		for (int y = 0; y < T.getChildCount(); y++) {
			if (T.getChildAt(y) instanceof TableRow) {
				TableRow R = (TableRow) T.getChildAt(y);
				for (int x = 0; x < R.getChildCount(); x++) {
					if (R.getChildAt(x) instanceof Button) {
						Button b = (Button) R.getChildAt(x);
						b.setEnabled(false);
					}
				}
			}
		}
	}

	private void setupOnClickListeners() {
		TableLayout T = (TableLayout) findViewById(R.id.tableLayout);
		for (int y = 0; y < T.getChildCount(); y++) {
			if (T.getChildAt(y) instanceof TableRow) {
				TableRow R = (TableRow) T.getChildAt(y);
				for (int x = 0; x < R.getChildCount(); x++) {
					View v = R.getChildAt(x);
					v.setOnClickListener(new PlayOnClick(x, y));
				}
			}
		}
	}

	private class PlayOnClick implements View.OnClickListener {
		private int x = 0;
		private int y = 0;

		public PlayOnClick(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public void onClick(View view) {
			if (view instanceof Button) {
				Button B = (Button) view;
				board[x][y] = noughtsTurn ? 'O' : 'X';
				B.setText(noughtsTurn ? "O" : "X");
				B.setEnabled(false);
				noughtsTurn = !noughtsTurn;
				antTrekk++;
				checkWin();
				if (checkWin) {
					disableButtons();
				}
			}
		}
	}
	
	public void restartActivity() {
		Intent intent = getIntent();
		finish();
		startActivity(intent);
	}
}
