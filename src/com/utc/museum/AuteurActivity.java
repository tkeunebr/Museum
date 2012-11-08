package com.utc.museum;

import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * Classe représentant un auteur sous forme d'Activity (utilisée uniquement en format téléphone)
 * Cette classe hérite de la classe abstraite DetailsActivity, qui permet de factoriser tous les éléments commun des Activity de type Détails de l'application
 */
public class AuteurActivity extends DetailsActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// On affiche le fragment en plein écran
		AuteurFragment f = (AuteurFragment) AuteurFragment.newInstance(mMenuIndex, mIdOeuvre);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.replace(R.id.fragment_details, f).commit();
	}
}
