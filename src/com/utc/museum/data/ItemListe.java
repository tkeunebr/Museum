package com.utc.museum.data;

/**
 * Classe représentant un ItemListe dans l'application
 * Un ItemListe est un élément de la liste des oeuvres à proximité de l'utilisateur
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
