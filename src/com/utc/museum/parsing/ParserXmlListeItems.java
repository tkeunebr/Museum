package com.utc.museum.parsing;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.utc.museum.data.ItemListe;

/**
 * Cette classe permet le parsing du xml correspondant e la liste des oeuvres e proximite
 * Elle herite de la classe DefaultHandler du systeme de parsing XML SAX
 */
public class ParserXmlListeItems extends ParserXmlGeneral {

	/**
	 * Definition des constantes correspondant aux balises XML
	 */
	private final String ITEM = "item";
	private final String ID = "id";
	private final String TITRE = "titre";

	/**
	 * La liste des items que l'on va peupler avec les items crees par le parser
	 */
	private ArrayList<ItemListe> liste;
	
	/**
	 * Un booleen permettant de savoir si le parser se situe entre des balises de type item
	 */
	private boolean inItem;
	
	/**
	 * L'item courant que l'on est en train de creer e partir du XML
	 */
	private ItemListe currentItem;

    public ParserXmlListeItems() {
        super();
    }
    
	/**
	 * Methode lancee e l'ouverture du fichier XML
	 */
    @Override
	public void startDocument() throws SAXException{

		super.startDocument();
		liste = new ArrayList<ItemListe>();
	}
	/**
	 * Methode lancee e l'ouverture d'une balise : on cree l'item
	 */
	@Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {

		buffer = new StringBuffer();
		if(name.equalsIgnoreCase(ITEM)){
			inItem = true;
			this.currentItem = new ItemListe();
		}
    }
	/**
	 * Methode lancee e la fermeture d'une balise :
	 * - si l'element est un attribut d'item, on set la valeur de l'attribut au contenu de la balise (situee dans le buffer)
	 * - si l'element est un item, alors il est termine, on peut la push dans la bonne liste
	 */
    @Override
	public void endElement(String uri, String localName, String name){
    	if(inItem && name.equalsIgnoreCase(ID)){
    		currentItem.setId(buffer.toString());
    	}
    	if(inItem && name.equalsIgnoreCase(TITRE)){
    		currentItem.setNom(buffer.toString());
    	}
    	if(name.equalsIgnoreCase(ITEM)){
    		liste.add(currentItem);
    	}
    }

	/**
	 * Methode publique permettant de recuperer la liste d'items (dans la pratique toujours un seul)
	 */
	public ArrayList<ItemListe> getData() {
		return liste;
	}

}
