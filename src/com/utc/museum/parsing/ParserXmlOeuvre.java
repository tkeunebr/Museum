package com.utc.museum.parsing;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.utc.museum.data.Oeuvre;
import com.utc.museum.data.Suggestion;

/**
 * Cette classe permet le parsing du xml correspondant � une oeuvre
 * Elle h�rite de la classe abstraite ParserXmlGeneral
 */
public class ParserXmlOeuvre extends ParserXmlGeneral {

	/**
	 * D�finition des constantes correspondant aux balises XML
	 */
	public final String OEUVRE = "oeuvre";
	public final String AUTEUR = "auteur";
	public final String ID = "id";	
	
	/**
	 * La liste des oeuvres que l'on va peupler avec les oeuvres cr��es par le parser
	 */
	private ArrayList<Oeuvre> entries;
	
	/**
	 * L'oeuvre courante que l'on est en train de cr�er � partir du XML
	 */
	private Oeuvre currentFeed;
	
    public ParserXmlOeuvre() {
        super();
    }
    
    @Override
	public void startDocument() throws SAXException{
    	/**
    	 * M�thode lanc�e � l'ouverture du fichier XML
    	 */
		super.startDocument();
		entries = new ArrayList<Oeuvre>();
	}
    
	/**
	 * M�thode lanc�e � l'ouverture d'une balise : on cr�e l'�l�ment (une oeuvre ou une suggestion)
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
	 * M�thode lanc�e � la fermeture d'une balise :
	 * - si l'�l�ment est un attribut d'oeuvre ou de suggestion, on set la valeur de l'attribut au contenu de la balise (situ�e dans le buffer)
	 * - si l'�l�ment est une oeuvre ou une suggestion, alors elle est termin�e, on peut la push dans la bonne liste
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
	 * M�thode publique permettant de r�cup�rer la liste d'auteurs (dans la pratique toujours un seul)
	 */
	public ArrayList<Oeuvre> getData() {
		return entries;
	}

}
