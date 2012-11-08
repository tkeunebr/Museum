package com.utc.museum.parsing;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.utc.museum.data.Auteur;
import com.utc.museum.data.Suggestion;

/**
 * Cette classe permet le parsing du xml correspondant à un auteur
 * Elle hérite de la classe abstraite ParserXmlGeneral
 */
public class ParserXmlAuteur extends ParserXmlGeneral {
	
	/**
	 * Définition des constantes correspondant aux balises XML
	 */
	public static final String AUTEUR = "auteur";
	public static final String DATES = "dates";
	
	/**
	 * La liste des auteurs que l'on va peupler avec les auteurs créés par le parser
	 */
	private ArrayList<Auteur> entries;

	
	/**
	 * L'auteur courant que l'on est en train de créer à partir du XML
	 */
	private Auteur currentFeed;

    public ParserXmlAuteur() {
        super();
    }
    
	/**
	 * Méthode lancée à l'ouverture du fichier XML
	 */
    @Override
	public void startDocument() throws SAXException{
		super.startDocument();
		entries = new ArrayList<Auteur>();
	}
    
	/**
	 * Méthode lancée à l'ouverture d'une balise : on crée l'élément (un auteur ou une suggestion)
	 */
	@Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		buffer = new StringBuffer();
		if(name.equalsIgnoreCase(AUTEUR)){
			this.currentFeed = new Auteur();
		}
		if(name.equalsIgnoreCase(SUGGESTIONTYPE1)){
			this.currentSuggestion = new Suggestion();
			inSuggestionType1 = true;
		}
		if(name.equalsIgnoreCase(SUGGESTIONTYPE2)){
			this.currentSuggestion = new Suggestion();
			inSuggestionType2 = true;
		}
    }
	
	/**
	 * Méthode lancée à la fermeture d'une balise :
	 * - si l'élément est un attribut d'auteur ou de suggestion, on set la valeur de l'attribut au contenu de la balise (située dans le buffer)
	 * - si l'élément est un auteur ou une suggestion, alors il ou elle est terminée, on peut la push dans la bonne liste
	 */
    @Override
	public void endElement(String uri, String localName, String name){
    	if(name.equalsIgnoreCase(NOM)){
    		this.currentFeed.setNom(buffer.toString());
    	}
    	if(name.equalsIgnoreCase(DATES)){
    		this.currentFeed.setDates(buffer.toString());
    	}
    	if(name.equalsIgnoreCase(URL)){
    		this.currentFeed.setUrlImage(buffer.toString());
    	}
    	if(name.equalsIgnoreCase(DESCRIPTION)){
    		this.currentFeed.setDescription(buffer.toString());
    	}
    	if(name.equalsIgnoreCase(NOMSUGG) && (inSuggestionType1 || inSuggestionType2)){
    		this.currentSuggestion.setNom(buffer.toString());
    	}
    	if(name.equalsIgnoreCase(URLSUGG) && (inSuggestionType1 || inSuggestionType2)){
    		this.currentSuggestion.setUrlImage(buffer.toString());
    	}
    	if(name.equalsIgnoreCase(JUSTIFSUGG) && (inSuggestionType1 || inSuggestionType2)){
    		this.currentSuggestion.setJustification(buffer.toString());
    	}
    	if(name.equalsIgnoreCase(AUTEUR)){
    		entries.add(currentFeed);
    	}
    	if(name.equalsIgnoreCase(SUGGESTIONTYPE1)){
    		currentFeed.addSuggestionType1(currentSuggestion);
    		inSuggestionType1 = false;
    	}
    	if(name.equalsIgnoreCase(SUGGESTIONTYPE2)){
    		currentFeed.addSuggestionType2(currentSuggestion);
    		inSuggestionType2 = false;
    	}
    }
    
	/**
	 * Méthode publique permettant de récupérer la liste d'auteurs (dans la pratique toujours un seul)
	 */
	public ArrayList<Auteur> getData() {
		return entries;
	}

}
