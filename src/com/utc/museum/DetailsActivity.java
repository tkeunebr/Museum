package com.utc.museum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Classe abstraite représentant une activité de type Détails générique et regroupant les éléments communs des Fragments de type Détails
 * Toutes les Activity de type DetailsActivity de l'application héritent de cette classe (utilisée uniquement en mode téléphone)
 */
public abstract class DetailsActivity extends Activity {
	
	/**
     * Constantes permettant l'identification de manière unique des paramètres qui seront passés entre les Activity
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