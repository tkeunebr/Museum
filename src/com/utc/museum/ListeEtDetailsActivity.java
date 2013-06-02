package com.utc.museum;

import android.app.Activity;
import android.os.Bundle;

/**
 * Classe representant l'Activite initialisant le Fragment menu et le Fragment Details de l'application (utilisee uniquement en format tablette)
 * Elle est appelee au demarrage par MainActivity, si celle ci a detecte que l'utilisateur utilise une tablette
 */
public class ListeEtDetailsActivity extends Activity {
	
	private DetailsFragment detailsFragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.liste_et_details);
	}
	
	/**
     * Methode appelee lorsque l'utilisateur appuie sur la touche retour et donc met l'appli en teche de fond (systeme Android)
     * Cette methode est necessaire pour ne pas laisser tourner d'eventuelles teches asynchrones qui entraeneraient des plantages de l'appli en arriere-plan
     */	
	@Override
	public void onBackPressed() {
	    detailsFragment = (DetailsFragment) getFragmentManager().findFragmentById(R.id.fragment_details);
	    if (detailsFragment != null) {
	    	detailsFragment.closeAllTasks();
	    }
	    super.onBackPressed();
	}
}
