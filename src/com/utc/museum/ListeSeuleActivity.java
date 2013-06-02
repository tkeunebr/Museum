package com.utc.museum;

import android.app.Activity;
import android.os.Bundle;

/**
 * Classe representant l'Activity lancee par la MainActivity lorsque l'on est en mode telephone. Seul le menu est alors affiche, il n'y a pas de Fragment Details
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
