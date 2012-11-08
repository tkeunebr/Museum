package com.utc.museum.data;

/**
 * Classe représentant un Auteur dans l'application
 * Cette classe hérite de la classe abstraite Entry, qui permet de factoriser toutes les entrées gérées par l'application
 */
public class Auteur extends Entry {
	private String dates;
	
	public String getDates() {
		return dates;
	}
		
	public void setDates(String dates) {
		this.dates = dates;
	}
		
	public String toString(){
		String s = "Oeuvre [nom="+nom+", dates="+dates+", urlImage="+urlImage+", description="+description;
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
