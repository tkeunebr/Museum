package com.utc.museum;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * Classe representant la premiere Activity lancee au demarrage
 * Cette classe se charge de detecter la configuration telephone ou tablette et d'appeler la bonne Activity selon le cas
 */
public class MainActivity extends Activity {
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent startActivity = null;
		if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE) {
			startActivity = new Intent(this, ListeEtDetailsActivity.class);
		} else {
			startActivity = new Intent(this, ListeSeuleActivity.class);
		}
		startActivity(startActivity);
		finish();
    }   
}