package com.utc.museum.checkers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Classe abstraite permettant de v�rifier � tout instant l'�tat de la connexion r�seau de l'utilisateur
 */
public abstract class ConnectionChecker {

	 /**
     * M�thode statique de la classe qui permet de savoir si l'utilisateur est connect� � Internet ou non
     * @param menuIndex Context Le contexte dans lequel se trouve l'application
     * @return boolean Vrai si l'utilisateur est connect� � Internet, faux sinon
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
