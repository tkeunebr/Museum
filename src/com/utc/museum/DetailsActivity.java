package com.utc.museum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Classe abstraite repr�sentant une activit� de type D�tails g�n�rique et regroupant les �l�ments communs des Fragments de type D�tails
 * Toutes les Activity de type DetailsActivity de l'application h�ritent de cette classe (utilis�e uniquement en mode t�l�phone)
 */
public abstract class DetailsActivity extends Activity {
	
	/**
     * Constantes permettant l'identification de mani�re unique des param�tres qui seront pass�s entre les Activity
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