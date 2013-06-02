package com.utc.museum.data;

/**
 * Classe representant un Auteur dans l'application
 * Cette classe herite de la classe abstraite Entry, qui permet de factoriser toutes les entrees gerees par l'application
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
