package com.ordonteam.powerofhexagons;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ordonteam.powerofhexagons.input.InputHandler;
import com.ordonteam.powerofhexagons.util.Util;
import com.ordonteam.powerofhexagons.view.Board;

public class Main extends Activity {

	Board board;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		create();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (board == null)
			create();
		board.load(this);
	}

	private void create() {
		Util.setFontSize(getResources().getDimensionPixelOffset(
				R.dimen.fontSize));

		board = new Board(this);
		board.setOnTouchListener(new InputHandler(board));
		setContentView(board);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_try_again) {
			board.clear();
		} else if (item.getItemId() == R.id.menu_popit) {
			String url = "https://play.google.com/store/apps/details?id=com.ordonteam.popit";
			startActivityBasedOnUrl(url);
		} else if (item.getItemId() == R.id.menu_about) {
			String url = "https://github.com/OrdonTeam/PowerOfHexagons";
			startActivityBasedOnUrl(url);
		}

		return true;
	}

	private void startActivityBasedOnUrl(String url) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
	}

	@Override
	protected void onPause() {
		super.onPause();
		board.save(this);
	}
}
