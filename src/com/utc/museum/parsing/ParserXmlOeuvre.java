package com.utc.museum.parsing;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.utc.museum.data.Oeuvre;
import com.utc.museum.data.Suggestion;

/**
 * Cette classe permet le parsing du xml correspondant à une oeuvre
 * Elle hérite de la classe abstraite ParserXmlGeneral
 */
public class ParserXmlOeuvre extends ParserXmlGeneral {

	/**
	 * Définition des constantes correspondant aux balises XML
	 */
	public final String OEUVRE = "oeuvre";
	public final String AUTEUR = "auteur";
	public final String ID = "id";	
	
	/**
	 * La liste des oeuvres que l'on va peupler avec les oeuvres créées par le parser
	 */
	private ArrayList<Oeuvre> entries;
	
	/**
	 * L'oeuvre courante que l'on est en train de créer à partir du XML
	 */
	private Oeuvre currentFeed;
	
    public ParserXmlOeuvre() {
        super();
    }
    
    @Override
	public void startDocument() throws SAXException{
    	/**
    	 * Méthode lancée à l'ouverture du fichier XML
    	 */
		super.startDocument();
		entries = new ArrayList<Oeuvre>();
	}
    
	/**
	 * Méthode lancée à l'ouverture d'une balise : on crée l'élément (une oeuvre ou une suggestion)
	 */
	@Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		buffer = new StringBuffer();
		if(name.equalsIgnoreCase(OEUVRE)){
			this.currentFeed = new Oeuvre();
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
	 * - si l'élément est un attribut d'oeuvre ou de suggestion, on set la valeur de l'attribut au contenu de la balise (située dans le buffer)
	 * - si l'élément est une oeuvre ou une suggestion, alors elle est terminée, on peut la push dans la bonne liste
	 */
    @Override
	public void endElement(String uri, String localName, String name){
    	if(name.equalsIgnoreCase(ID)){
    		this.currentFeed.setId(buffer.toString());
    	}
    	if(name.equalsIgnoreCase(NOM)){
    		this.currentFeed.setNom(buffer.toString());
    	}
    	if(name.equalsIgnoreCase(AUTEUR)){
    		this.currentFeed.setAuteur(buffer.toString());
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
    	if(name.equalsIgnoreCase(OEUVRE)){
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
	public ArrayList<Oeuvre> getData() {
		return entries;
	}

}
