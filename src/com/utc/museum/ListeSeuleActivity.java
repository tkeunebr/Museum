package com.utc.museum;

import android.app.Activity;
import android.os.Bundle;

/**
 * Classe repr�sentant l'Activity lanc�e par la MainActivity lorsque l'on est en mode t�l�phone. Seul le menu est alors affich�, il n'y a pas de Fragment D�tails
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
