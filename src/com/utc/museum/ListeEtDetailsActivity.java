package com.utc.museum;

import android.app.Activity;
import android.os.Bundle;

/**
 * Classe repr�sentant l'Activit� initialisant le Fragment menu et le Fragment D�tails de l'application (utilis�e uniquement en format tablette)
 * Elle est appel�e au d�marrage par MainActivity, si celle ci a d�tect� que l'utilisateur utilise une tablette
 */
public class ListeEtDetailsActivity extends Activity {
	
	private DetailsFragment detailsFragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.liste_et_details);
	}
	
	/**
     * M�thode appel�e lorsque l'utilisateur appuie sur la touche retour et donc met l'appli en t�che de fond (syst�me Android)
     * Cette m�thode est n�cessaire pour ne pas laisser tourner d'�ventuelles t�ches asynchrones qui entra�neraient des plantages de l'appli en arri�re-plan
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
