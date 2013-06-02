package com.utc.museum;

import android.app.Activity;
import android.os.Bundle;

/**
 * Classe representant l'Activite initialisant le Fragment menu de l'application (utilisee uniquement en format telephone)
 * Elle est appelee au demarrage par MainActivity, si celle ci a detecte que l'utilisateur utilise un telephone
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
