package com.utc.museum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Classe abstraite representant une activite de type Details generique et regroupant les elements communs des Fragments de type Details
 * Toutes les Activity de type DetailsActivity de l'application heritent de cette classe (utilisee uniquement en mode telephone)
 */
public abstract class DetailsActivity extends Activity {
	
	/**
     * Constantes permettant l'identification de maniere unique des parametres qui seront passes entre les Activity
     */
	public static final String MENU_INDEX = "menuIndex";
	public static final String OEUVRE_ID = "idOeuvre";
	
	protected int mMenuIndex = -1;
	protected String mIdOeuvre = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 
		Intent intent = getIntent(); 
		if (intent != null) {
			mIdOeuvre = intent.getStringExtra(DetailsActivity.OEUVRE_ID);
			mMenuIndex = intent.getIntExtra(DetailsActivity.MENU_INDEX, -1);
		}
	}
	
	public int getMenuIndex() {
		return mMenuIndex;
	}
	
	public String getIdOeuvre() {
		return mIdOeuvre;
	}
}