package com.utc.museum.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.restlet.data.MediaType;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.xml.sax.SAXException;

import com.utc.museum.parsing.ParserXmlAuteur;
import com.utc.museum.parsing.ParserXmlListeItems;
import com.utc.museum.parsing.ParserXmlOeuvre;
import com.utc.museum.parsing.ParserXmlStyle;


/**
 * Une classe abstraite permettant de lancer le parsing et récupérer les données à l'aide d'une méthode statique
 */
public abstract class ContainerData {

	/**
	 * Méthode statique permettant de lancer le parsing. Elle retourne une liste contenant les données Oeuvres.
	 */
	public static ArrayList<Oeuvre> getOeuvres(String urlData){
		SAXParserFactory fabrique = SAXParserFactory.newInstance();
		SAXParser parser = null;
		ArrayList<Oeuvre> entries = null;
		
		try {
			parser = fabrique.newSAXParser();
		} catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
		
		// Récupération de la représentation (type XML) de la ressource		
		InputStream input = null;
		try {
			input = new ClientResource(urlData).get(MediaType.APPLICATION_XML).getStream();
		} catch (ResourceException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		if (input != null) {
			ParserXmlOeuvre handler = new ParserXmlOeuvre();
			try {
				parser.parse(input, handler);
				entries = handler.getData();
			} catch (SAXException e) {
				e.printStackTrace();
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
		}
		return entries;
	}

	
	/**
	 * Méthode statique permettant de lancer le parsing. Elle retourne une liste contenant les données Auteurs.
	 */
	public static ArrayList<Auteur> getAuteurs(String urlData){
		SAXParserFactory fabrique = SAXParserFactory.newInstance();
		SAXParser parser = null;
		ArrayList<Auteur> entries = null;
		
		try {
			parser = fabrique.newSAXParser();
		} catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

		// Récupération de la représentation (type XML) de la ressource		
		InputStream input = null;
		try {
			input = new ClientResource(urlData).get(MediaType.APPLICATION_XML).getStream();
		} catch (ResourceException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		if (input != null) {
			ParserXmlAuteur handler = new ParserXmlAuteur();
			try {
				parser.parse(input, handler);
				entries = handler.getData();
			} catch (SAXException e) {
				e.printStackTrace();
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
		}
		return entries;
	}
	/**
	 * Méthode statique permettant de lancer le parsing. Elle retourne une liste contenant les données de la liste d'Oeuvres.
	 */
	public static ArrayList<ItemListe> getListe(String urlData){
		SAXParserFactory fabrique = SAXParserFactory.newInstance();
		SAXParser parser = null;
		ArrayList<ItemListe> entries = null;
		
		try{
			parser = fabrique.newSAXParser();
		}catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

		// Récupération de la représentation (type XML) de la ressource		
		InputStream input = null;
		try {
			input = new ClientResource(urlData).get(MediaType.APPLICATION_XML).getStream();
		} catch (ResourceException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		if (input != null) {
			ParserXmlListeItems handler = new ParserXmlListeItems();
			try {
				parser.parse(input, handler);
				entries = handler.getData();
			} catch (SAXException e) {
				e.printStackTrace();
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
		}
		return entries;
	}
	/**
	 * Méthode statique permettant de lancer le parsing. Elle retourne une liste contenant les données Styles.
	 */
	public static ArrayList<Style> getStyles(String urlData){
		SAXParserFactory fabrique = SAXParserFactory.newInstance();
		SAXParser parser = null;
		ArrayList<Style> entries = null;
		
		try {
			parser = fabrique.newSAXParser();
		} catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

		// Récupération de la représentation (type XML) de la ressource		
		InputStream input = null;
		try {
			input = new ClientResource(urlData).get(MediaType.APPLICATION_XML).getStream();
		} catch (ResourceException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		if (input != null) {
			ParserXmlStyle handler = new ParserXmlStyle();
			try {
				parser.parse(input, handler);
				entries = handler.getData();
			} catch (SAXException e) {
				e.printStackTrace();
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
		}
		return entries;
	}
}
