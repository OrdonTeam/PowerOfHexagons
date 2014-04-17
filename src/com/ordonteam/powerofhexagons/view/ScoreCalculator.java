package com.ordonteam.powerofhexagons.view;

import java.util.Collection;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.Toast;

import com.ordonteam.powerofhexagons.Main;
import com.ordonteam.powerofhexagons.board.Field;

public class ScoreCalculator {

	private Activity main;
	private int maxScore;
	private int score;
	private int maxLocalValue;

	public ScoreCalculator(Activity main) {
		this.main = main;
	}

	public void calculateScore(Collection<Field> values) {
		score = 0;
		maxLocalValue = 0;
		for (Field field : values) {
			score += field.getValue();
			maxLocalValue = Math.max(maxLocalValue, field.getValue());
		}
		maxScore = calculateMaxScore();
		updateMaxValue();
	}

	private int calculateMaxScore() {
		SharedPreferences preferences = main.getPreferences(Main.MODE_PRIVATE);
		int storedScore = preferences.getInt("score", 0);
		if(score > storedScore){
			preferences.edit().putInt("score", score).commit();
			return score;
		}
		return storedScore;
	}
	
	private void updateMaxValue() {
		SharedPreferences preferences = main.getPreferences(Context.MODE_PRIVATE);
		int maxValue = preferences.getInt("value", 0);
		if(maxLocalValue > maxValue){
			Editor edit = preferences.edit();
			edit.putInt("value", maxLocalValue);
			edit.commit();
			showCongrats(maxLocalValue);
		}else{
			maxLocalValue = maxValue;
		}
	}
	
	private void showCongrats(int maxLocalValue) {
		switch (maxLocalValue) {
		case 8:
			Toast.makeText(main, "New achivement: "+maxLocalValue+"\nGO GO GO!", Toast.LENGTH_LONG).show();
			break;
		case 64:
			Toast.makeText(main, "New achivement: "+maxLocalValue+"\nFeeling save?", Toast.LENGTH_LONG).show();
			break;
		case 1024:
			Toast.makeText(main, "New achivement: "+maxLocalValue+"\nOne's game would be over now.", Toast.LENGTH_LONG).show();
			break;
		case 2048:
			Toast.makeText(main, "New achivement: "+maxLocalValue+"\nYou can try harder.", Toast.LENGTH_LONG).show();
			break;
		case 4096:
			Toast.makeText(main, "New achivement: "+maxLocalValue+"\n4096 is not that big", Toast.LENGTH_LONG).show();
			break;
		case 8192:
			Toast.makeText(main, "New achivement: "+maxLocalValue+"\nIt's not over yet.", Toast.LENGTH_LONG).show();
			break;
		case 16384:
			Toast.makeText(main, "New achivement: "+maxLocalValue+"\nYou can be proud now.", Toast.LENGTH_LONG).show();
			break;
		case 32768:
			Toast.makeText(main, "New achivement: "+maxLocalValue+"\nWow, that is something!", Toast.LENGTH_LONG).show();
			break;
		case 65536:
			Toast.makeText(main, "New achivement: "+maxLocalValue+"\nI cannot believe it...", Toast.LENGTH_LONG).show();
			break;
		case 131072:
			Toast.makeText(main, "New achivement: "+maxLocalValue+"\nYou are so nerdy :)", Toast.LENGTH_LONG).show();
			break;
		}
	}

	public int getMaxScore() {
		return maxScore;
	}

	public int getScore() {
		return score;
	}

	public int getMaxLocalValue() {
		return maxLocalValue;
	}

}
