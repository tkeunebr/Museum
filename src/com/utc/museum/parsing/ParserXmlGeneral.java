package com.utc.museum.parsing;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.utc.museum.data.Suggestion;

/**
 * Classe abstraite representant un parser generique et regroupant les elements communs aux parsers de l'application
 * Tous les parsers de l'application heritent de cette classe
 */
public abstract class ParserXmlGeneral extends DefaultHandler {
	
	public static final String NOM = "nom";
	public static final String URL = "url";
	public static final String SUGGESTIONTYPE1 = "suggestionType1";
	public static final String SUGGESTIONTYPE2 = "suggestionType2";
	public static final String NOMSUGG = "nomsugg";
	public static final String URLSUGG = "urlsugg";
	public static final String JUSTIFSUGG = "justifsugg";
	public static final String DESCRIPTION = "description";
	
	/**
	 * Des boolees permettant de savoir si le parser se situe entre des balises de type suggestionType1 ou suggestionType2
	 */
	protected boolean inSuggestionType1;
	protected boolean inSuggestionType2;
	
	/**
	 * La suggestion courante que l'on est en train de creer e partir du XML
	 */
	protected Suggestion currentSuggestion;
	
	protected StringBuffer buffer;
	
	@Override
    public void processingInstruction(String target, String data) throws SAXException {
    }
	
	public ParserXmlGeneral() {
		super();
	}
	
	/**
	 * Methode parcourant le XML
	 */
    @Override
    public void characters(char[] ch,int start, int length) throws SAXException{
    	String lecture = new String(ch,start,length);
    	if(buffer != null) buffer.append(lecture);
    }

}
