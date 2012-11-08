package com.utc.museum.parsing;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.utc.museum.data.ItemListe;

/**
 * Cette classe permet le parsing du xml correspondant � la liste des oeuvres � proximit�
 * Elle h�rite de la classe DefaultHandler du syst�me de parsing XML SAX
 */
public class ParserXmlListeItems extends ParserXmlGeneral {

	/**
	 * D�finition des constantes correspondant aux balises XML
	 */
	private final String ITEM = "item";
	private final String ID = "id";
	private final String TITRE = "titre";

	/**
	 * La liste des items que l'on va peupler avec les items cr��s par le parser
	 */
	private ArrayList<ItemListe> liste;
	
	/**
	 * Un bool�en permettant de savoir si le parser se situe entre des balises de type item
	 */
	private boolean inItem;
	
	/**
	 * L'item courant que l'on est en train de cr�er � partir du XML
	 */
	private ItemListe currentItem;

    public ParserXmlListeItems() {
        super();
    }
    
	/**
	 * M�thode lanc�e � l'ouverture du fichier XML
	 */
    @Override
	public void startDocument() throws SAXException{

		super.startDocument();
		liste = new ArrayList<ItemListe>();
	}
	/**
	 * M�thode lanc�e � l'ouverture d'une balise : on cr�e l'item
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
	 * M�thode lanc�e � la fermeture d'une balise :
	 * - si l'�l�ment est un attribut d'item, on set la valeur de l'attribut au contenu de la balise (situ�e dans le buffer)
	 * - si l'�l�ment est un item, alors il est termin�, on peut la push dans la bonne liste
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
	 * M�thode publique permettant de r�cup�rer la liste d'items (dans la pratique toujours un seul)
	 */
	public ArrayList<ItemListe> getData() {
		return liste;
	}

}
