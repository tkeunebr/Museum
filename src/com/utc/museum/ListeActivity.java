package com.utc.museum;

import android.app.Activity;
import android.os.Bundle;

/**
 * Classe repr�sentant l'Activit� initialisant le Fragment menu de l'application (utilis�e uniquement en format t�l�phone)
 * Elle est appel�e au d�marrage par MainActivity, si celle ci a d�tect� que l'utilisateur utilise un t�l�phone
 */
public class ListeActivity extends Activity {
	
	//private MenuFragment listFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.liste_et_details);
		//listFragment = (MenuFragment) getFragmentManager().findFragmentById(R.id.fragment_liste);
	}
}
