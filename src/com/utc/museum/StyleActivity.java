package com.utc.museum;

import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * Classe repr�sentant un style sous forme d'Activity (utilis�e uniquement en format t�l�phone)
 * Cette classe h�rite de la classe abstraite DetailsActivity, qui permet de factoriser tous les �l�ments commun des Activity de type D�tails de l'application
 */
public class StyleActivity extends DetailsActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// On affiche le fragment en plein �cran
		StyleFragment f = (StyleFragment) StyleFragment.newInstance(mMenuIndex, mIdOeuvre);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.replace(R.id.fragment_details, f).commit();
	}
}
