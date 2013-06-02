package com.utc.museum.data;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Classe abstraite representant une Entry generique et les elements communs des Entry de l'application
 * Une Entry est une entree, par exemple une oeuvre, un auteur...
 * Toutes les Entry de l'application heritent de cette classe
 */
public abstract class Entry {
	protected String nom;
	protected String urlImage;
	protected String description;
	protected ArrayList<Suggestion> suggestionsType1 = null;
	protected ArrayList<Suggestion> suggestionsType2 = null;

	public String getNom(){
		return nom;
	}
	public String getUrlImage(){
		return urlImage;
	}
	public String getDescription(){
		return description;
	}
	public ArrayList<Suggestion> getSuggestionsType1(){
		return suggestionsType1;
	}
	public ArrayList<Suggestion> getSuggestionsType2(){
		return suggestionsType2;
	}
	public Bitmap getBitmap() {
		Bitmap bm = null;
		try {
			URL aURL = new URL(urlImage); 
			URLConnection conn = aURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			bm = BitmapFactory.decodeStream(bis);
			bis.close(); 
            is.close(); 
		} catch(IOException e){
		}
		return bm;
	}
	
	public void setNom(String nom){
		this.nom = nom;
	}
	
	public void setUrlImage(String url){
		this.urlImage = url;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public void addSuggestionType2(Suggestion s){
		if (suggestionsType2==null) {
			suggestionsType2 = new ArrayList<Suggestion>();
		}
		suggestionsType2.add(s);
	}
	
	public void addSuggestionType1(Suggestion s) {
		if (suggestionsType1==null) {
			suggestionsType1 = new ArrayList<Suggestion>();
		}
		suggestionsType1.add(s);
	}
}
