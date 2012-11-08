package com.utc.museum;

import android.app.Activity;
import android.os.Bundle;

/**
 * Classe représentant l'Activité initialisant le Fragment menu et le Fragment Détails de l'application (utilisée uniquement en format tablette)
 * Elle est appelée au démarrage par MainActivity, si celle ci a détecté que l'utilisateur utilise une tablette
 */
public class ListeEtDetailsActivity extends Activity {
	
	private DetailsFragment detailsFragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.liste_et_details);
	}
	
	/**
     * Méthode appelée lorsque l'utilisateur appuie sur la touche retour et donc met l'appli en tâche de fond (système Android)
     * Cette méthode est nécessaire pour ne pas laisser tourner d'éventuelles tâches asynchrones qui entraîneraient des plantages de l'appli en arrière-plan
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
