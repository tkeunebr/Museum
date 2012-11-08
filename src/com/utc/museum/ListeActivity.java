package com.utc.museum;

import android.app.Activity;
import android.os.Bundle;

/**
 * Classe représentant l'Activité initialisant le Fragment menu de l'application (utilisée uniquement en format téléphone)
 * Elle est appelée au démarrage par MainActivity, si celle ci a détecté que l'utilisateur utilise un téléphone
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
