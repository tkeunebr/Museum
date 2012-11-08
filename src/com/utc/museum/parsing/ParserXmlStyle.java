package com.utc.museum.parsing;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.utc.museum.data.Style;
import com.utc.museum.data.Suggestion;

/**
 * Cette classe permet le parsing du xml correspondant � un style
 * Elle h�rite de la classe abstraite ParserXmlGeneral
 */
public class ParserXmlStyle extends ParserXmlGeneral {
	
	/**
	 * D�finition des constantes correspondant aux balises XML
	 */
	public static final String STYLE = "style";
	public static final String EPOQUE = "epoque";
	public static final String ARTISTES = "artistes";
	public static final String DESCRIPTION_VISUEL = "descriptionvisuel";
	
	/**
	 * La liste des auteurs que l'on va peupler avec les auteurs cr��s par le parser
	 */
	private ArrayList<Style> entries;
	
	/**
	 * L'auteur courant que l'on est en train de cr�er � partir du XML
	 */
	private Style currentFeed;

    public ParserXmlStyle() {
        super();
    }
    
	/**
	 * M�thode lanc�e � l'ouverture du fichier XML
	 */
    @Override
	public void startDocument() throws SAXException{
		super.startDocument();
		entries = new ArrayList<Style>();
	}
    
	/**
	 * M�thode lanc�e � l'ouverture d'une balise : on cr�e l'�l�ment (un auteur ou une suggestion)
	 */
	@Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		buffer = new StringBuffer();
		if(name.equalsIgnoreCase(STYLE)){
			this.currentFeed = new Style();
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
	 * - si l'�l�ment est un attribut d'auteur ou de suggestion, on set la valeur de l'attribut au contenu de la balise (situ�e dans le buffer)
	 * - si l'�l�ment est un auteur ou une suggestion, alors il ou elle est termin�e, on peut la push dans la bonne liste
	 */
    @Override
	public void endElement(String uri, String localName, String name){
    	if(name.equalsIgnoreCase(NOM)){
    		this.currentFeed.setNom(buffer.toString());
    	}
    	if(name.equalsIgnoreCase(EPOQUE)){
    		this.currentFeed.setEpoque(buffer.toString());
    	}
    	if(name.equalsIgnoreCase(ARTISTES)) {
    		this.currentFeed.setArtistes(buffer.toString());
    	}
    	if(name.equalsIgnoreCase(DESCRIPTION_VISUEL)) {
    		this.currentFeed.setDescriptionVisuel(buffer.toString());
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
    	if(name.equalsIgnoreCase(STYLE)){
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
	public ArrayList<Style> getData() {
		return entries;
	}

}
