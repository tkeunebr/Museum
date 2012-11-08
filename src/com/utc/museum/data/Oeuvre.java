package com.utc.museum.data;

/**
 * Classe représentant une Oeuvre dans l'application
 * Cette classe hérite de la classe abstraite Entry, qui permet de factoriser toutes les entrées gérées par l'application
 */
public class Oeuvre extends Entry {
	protected String id;
	private String auteur;
	private String date;
	
	public String getId(){
		return id;
	}

	public String getAuteur(){
		return auteur;
	}
	public String getDate() {
		return date;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public void setAuteur(String auteur){
		this.auteur = auteur;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String toString(){
		String s = "Oeuvre [nom="+nom+", date="+date+", auteur="+auteur+", urlImage="+urlImage+", description="+description;
		if (suggestionsType1 != null && !suggestionsType1.isEmpty()) {
			s+=", ";
			for (Suggestion i : suggestionsType1){
				s+=i.toString();
			}
		}
		s+="]";
		return s;
	}

}
