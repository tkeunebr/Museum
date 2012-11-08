package com.utc.museum.parsing;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.utc.museum.data.ItemListe;

/**
 * Cette classe permet le parsing du xml correspondant à la liste des oeuvres à proximité
 * Elle hérite de la classe DefaultHandler du système de parsing XML SAX
 */
public class ParserXmlListeItems extends ParserXmlGeneral {

	/**
	 * Définition des constantes correspondant aux balises XML
	 */
	private final String ITEM = "item";
	private final String ID = "id";
	private final String TITRE = "titre";

	/**
	 * La liste des items que l'on va peupler avec les items créés par le parser
	 */
	private ArrayList<ItemListe> liste;
	
	/**
	 * Un booléen permettant de savoir si le parser se situe entre des balises de type item
	 */
	private boolean inItem;
	
	/**
	 * L'item courant que l'on est en train de créer à partir du XML
	 */
	private ItemListe currentItem;

    public ParserXmlListeItems() {
        super();
    }
    
	/**
	 * Méthode lancée à l'ouverture du fichier XML
	 */
    @Override
	public void startDocument() throws SAXException{

		super.startDocument();
		liste = new ArrayList<ItemListe>();
	}
	/**
	 * Méthode lancée à l'ouverture d'une balise : on crée l'item
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
	 * Méthode lancée à la fermeture d'une balise :
	 * - si l'élément est un attribut d'item, on set la valeur de l'attribut au contenu de la balise (située dans le buffer)
	 * - si l'élément est un item, alors il est terminé, on peut la push dans la bonne liste
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
	 * Méthode publique permettant de récupérer la liste d'items (dans la pratique toujours un seul)
	 */
	public ArrayList<ItemListe> getData() {
		return liste;
	}

}
