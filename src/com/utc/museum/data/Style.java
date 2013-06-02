package com.utc.museum.data;

/**
 * Classe representant un Style dans l'application
 * Cette classe herite de la classe abstraite Entry, qui permet de factoriser toutes les entrees gerees par l'application
 */
public class Style extends Entry {
	private String epoque;
	private String descriptionVisuel;
	private String artistes;
	
	public String getEpoque() {
		return epoque;
	}
	
	public String getDescriptionVisuel() {
		return descriptionVisuel;
	}
	
	public String getArtistes() {
		return artistes;
	}
		
	public void setEpoque(String epoque) {
		this.epoque = epoque;
	}
		
	public void setDescriptionVisuel(String descriptionVisuel) {
		this.descriptionVisuel = descriptionVisuel;
	}
	
	public void setArtistes(String artistes) {
		this.artistes = artistes;
	}
	
	public String toString(){
		String s = "Oeuvre [nom="+nom+", epoque="+epoque+", urlImage="+urlImage+", description="+description;
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