package com.utc.museum;

import android.app.Activity;
import android.os.Bundle;

/**
 * Classe représentant l'Activity lancée par la MainActivity lorsque l'on est en mode téléphone. Seul le menu est alors affiché, il n'y a pas de Fragment Détails
 */
public class ListeSeuleActivity extends Activity {
	
	//private MenuFragment listFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.liste_seule);
		//listFragment = (MenuFragment) getFragmentManager().findFragmentById(R.id.fragment_liste);
	}
}
