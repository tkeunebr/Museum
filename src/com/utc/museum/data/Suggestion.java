package com.utc.museum.data;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Classe representant une Suggestion dans l'application
 */
public class Suggestion {
	private String urlImage;
	private String nom;
	private String justification;
	
	public Suggestion() {
	}
	
	public Suggestion(String urlImage, String nom, String justification){
		this.urlImage = urlImage;
		this.nom = nom;
		this.justification = justification;
	}
	
	public String getUrlImage(){
		return urlImage;
	}
	public String getNom(){
		return nom;
	}
	public String getJustification(){
		return justification;
	}
	public Bitmap getBitmap(){
		Bitmap bm = null;
		try{
			URL aURL = new URL(urlImage);
			URLConnection conn = aURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			bm = BitmapFactory.decodeStream(bis);
			bis.close(); 
            is.close(); 
		}catch(IOException e){
			e.printStackTrace();
		}
		return bm;
	}
	public void setNom(String nom){
		this.nom = nom;
	}
	public void setUrlImage(String url){
		this.urlImage = url;
	}
	public void setJustification(String justification){
		this.justification = justification;
	}
	public String toString(){
		return "[nom="+nom+", urlImage="+urlImage+", justification="+justification+"]";
	}
}
