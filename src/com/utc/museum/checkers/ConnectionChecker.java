package com.utc.museum.checkers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Classe abstraite permettant de vérifier à tout instant l'état de la connexion réseau de l'utilisateur
 */
public abstract class ConnectionChecker {

	 /**
     * Méthode statique de la classe qui permet de savoir si l'utilisateur est connecté à Internet ou non
     * @param menuIndex Context Le contexte dans lequel se trouve l'application
     * @return boolean Vrai si l'utilisateur est connecté à Internet, faux sinon
     */	
	public static boolean isOnline(Context appContext) {
		ConnectivityManager mCm = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetInfo = mCm.getActiveNetworkInfo();
        if (mNetInfo != null && mNetInfo.isConnected() && mNetInfo.isAvailable()) {
        	return true;
        }
        else {
        	return false;
        }
	}
}
