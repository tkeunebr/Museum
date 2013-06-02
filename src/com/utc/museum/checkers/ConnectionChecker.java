package com.utc.museum.checkers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Classe abstraite permettant de verifier e tout instant l'etat de la connexion reseau de l'utilisateur
 */
public abstract class ConnectionChecker {

	 /**
     * Methode statique de la classe qui permet de savoir si l'utilisateur est connecte e Internet ou non
     * @param menuIndex Context Le contexte dans lequel se trouve l'application
     * @return boolean Vrai si l'utilisateur est connecte e Internet, faux sinon
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
