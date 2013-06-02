package com.utc.museum;

import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * Classe representant un auteur sous forme d'Activity (utilisee uniquement en format telephone)
 * Cette classe herite de la classe abstraite DetailsActivity, qui permet de factoriser tous les elements commun des Activity de type Details de l'application
 */
public class AuteurActivity extends DetailsActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// On affiche le fragment en plein ecran
		AuteurFragment f = (AuteurFragment) AuteurFragment.newInstance(mMenuIndex, mIdOeuvre);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.replace(R.id.fragment_details, f).commit();
	}
}
