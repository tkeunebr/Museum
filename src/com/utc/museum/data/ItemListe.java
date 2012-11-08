package com.utc.museum.data;

/**
 * Classe repr�sentant un ItemListe dans l'application
 * Un ItemListe est un �l�ment de la liste des oeuvres � proximit� de l'utilisateur
 */
public class ItemListe {
	private String id;
	private String titre;
	
	public ItemListe() {
	}
	
	public ItemListe(String id, String titre){
		this.id = id;
		this.titre = titre;
	}
	
	public String getId(){
		return id;
	}
	public String getTitre(){
		return titre;
	}

	public void setId(String id){
		this.id = id;
	}
	public void setNom(String titre){
		this.titre = titre;
	}
	public String toString(){
		return "[id="+id+", titre="+titre+"]";
	}
}
