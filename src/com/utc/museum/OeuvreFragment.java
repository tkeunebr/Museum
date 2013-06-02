package com.utc.museum;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.utc.museum.checkers.ConnectionChecker;
import com.utc.museum.data.ContainerData;
import com.utc.museum.data.Entry;
import com.utc.museum.data.Oeuvre;
import com.utc.museum.data.Suggestion;

/**
 * Classe representant le fragment d'une oeuvre
 * Cette classe herite de la classe abstraite DetailsFragment, qui permet de factoriser tous les elements commun des Fragments de type Details de l'application
 */
public class OeuvreFragment extends DetailsFragment {
	
	/**
     * Constante contenant le chemin d'acces e une ressource de type Oeuvre sur le reseau
     */
	public static final String URL = ipServ + "artworks?artwork=";
	
	private TextView mTitreImage;
	private TextView mAuteur;
	private TextView mDescription;
	private ImageView mImage;
	private ProgressBar mProgressBarVisuel;
	private LinearLayout mProgressBarPrincipale;
	private ProgressBar mProgressBarSuggestionsType1;
	private ProgressBar mProgressBarSuggestionsType2;
	private LinearLayout mLayoutSuggType1;
	private LinearLayout mLayoutSuggType2;
	private View v;
	private ArrayList<Oeuvre> mAlData;
	private Bitmap mOeuvreBitmap;
	private ArrayList<Bitmap> mSuggVisuelsType1;
	private ArrayList<Bitmap> mSuggVisuelsType2;
	private ArrayList<Suggestion> mSuggestionsType1;
	private ArrayList<Suggestion> mSuggestionsType2;
	private int mCompteurType1 = 0;
	private int mCompteurType2 = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (!this.isInLayout() && container == null) return null;
		v = inflater.inflate(R.layout.oeuvre_fragment, container, false);
		v.setDrawingCacheEnabled(false);
		mTitreImage = (TextView) v.findViewById(R.id.titre_image);
		mAuteur = (TextView) v.findViewById(R.id.auteur);
		mDescription = (TextView) v.findViewById(R.id.description);
		mImage = (ImageView) v.findViewById(R.id.visuel);
		mProgressBarPrincipale = (LinearLayout) v.findViewById(R.id.progressbar_main);
		mProgressBarVisuel = (ProgressBar) v.findViewById(R.id.progressBar_visuel);
		mProgressBarSuggestionsType1 = (ProgressBar) v.findViewById(R.id.progressBar_suggestions_type1);
		mProgressBarSuggestionsType2 = (ProgressBar) v.findViewById(R.id.progressBar_suggestions_type2);
		mLayoutSuggType1 = (LinearLayout) v.findViewById(R.id.layout_suggestions_type1);
		mLayoutSuggType2 = (LinearLayout) v.findViewById(R.id.layout_suggestions_type2);
		
		startLoading();
		
		return v;
	}
	
	 /**
     * Methode statique de la classe qui permet d'instancier un nouvel objet de la classe en utilisant le pattern Factory, avec comme propriete l'id de l'oeuvre et la position du menu
     * @param menuIndex L'item clique dans le menu
     * @param oeuvre L'id de l'oeuvre que l'utilisateur veut consulter
     * @return OeuvreFragment Une instance de la classe OeuvreFragment
     */	
	public static OeuvreFragment newInstance(int menuIndex, String oeuvre) {
		// Instanciation d'un nouveau fragment
		OeuvreFragment detail = new OeuvreFragment();
		Bundle args = new Bundle();
		args.putInt(DetailsFragment.idMenu, menuIndex);
		args.putString(DetailsFragment.idOeuvre, oeuvre);
	    detail.setArguments(args);
	    return detail;
	}
	
	 /**
     * Methode permettant de demarrer le telechargement des donnees ainsi que le remplissage des vues avec des teches asynchrones
     */	
	protected void startLoading() {
		mConnexion = ConnectionChecker.isOnline(getActivity());
		if (!mConnexion) {
			MonDialogFragment.newInstance(DIALOG_NO_CONNEXION_ID).show(getFragmentManager(), "DialogNoConnexion");
		}
		else {
				mDataTask = new DownloadDataTask();
				mDataTask.execute(URL + getShownOeuvre());
		}
	}
	
	/**
     * Methode permettant la mise e jour du texte dans les vues
     */		
	private void updateUiTexte() {
		if (mAlData != null) {
			getActivity().getActionBar().setTitle(Html.fromHtml("<b>"+mAlData.get(0).getNom()+"</b>"));
    		mTitreImage.setText(mAlData.get(0).getNom());
 			mAuteur.setText(mAlData.get(0).getAuteur());
 			mDescription.setText(mAlData.get(0).getDescription());
 			mProgressBarPrincipale.setVisibility(View.GONE);
 			v.findViewById(R.id.layout_oeuvre).setVisibility(View.VISIBLE);
 			v.findViewById(R.id.separateur).setVisibility(View.VISIBLE);
 			((LinearLayout)v.findViewById(R.id.layout_suggestions)).setVisibility(View.VISIBLE);
 		}
		else {
			closeTasks();
			getActivity().getActionBar().setTitle(R.string.aucune_oeuvre);
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage(getString(R.string.erreur_chargement_oeuvre))
			       .setPositiveButton(getString(R.string.reessayer), new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                startLoading();
			                dialog.dismiss();
			           }
			       });
			builder.create().show();
		}
     }
	
	/**
     * Methode permettant la mise e jour des images dans les vues
     */	
	private void updateUiVisuel() {
		if (mAlData != null && mOeuvreBitmap != null) {
			mImage.setImageBitmap(mOeuvreBitmap);
			mProgressBarVisuel.setVisibility(View.GONE);
			mImage.setVisibility(View.VISIBLE);
		}
	}
	
	/**
     * Methode permettant la mise e jour des suggestions "A proximite" dans les vues
     */	
	private void updateUiSuggType1() {
			LinearLayout layoutSuggType1 = (LinearLayout) v.findViewById(R.id.layout_suggestions_visuels_type1);
			mCompteurType1 = 0;

			for (final Suggestion i : mSuggestionsType1) {
				LinearLayout l = new LinearLayout(getActivity());
				l.setOrientation(LinearLayout.VERTICAL);
				l.setGravity(Gravity.CENTER);
				l.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
				l.setPadding(10, 0, 10, 5);
				
				ImageView iv = new ImageView(getActivity());
				iv.setImageBitmap(mSuggVisuelsType1.get(mCompteurType1));
				iv.setLayoutParams(new LayoutParams(100, 100));
				iv.setOnTouchListener(new OnTouchListener(){
					public boolean onTouch(View view, MotionEvent arg1) {
						postUserLogin();
						AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
						String s = "<b>" + mAlData.get(0).getNom() + "</b> et <b>" + i.getNom() + "</b><br/><br/>" + i.getJustification();
						dialog.setMessage(Html.fromHtml(s))
						   .setTitle(getString(R.string.justification))
						   .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
							   public void onClick(DialogInterface dialog, int which) {
								   dialog.dismiss();
							   }
						   	});				
						final AlertDialog alert = dialog.create();
						alert.show();
						return false;
					};
				});
				
				TextView tv = new TextView(getActivity());
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				lp.setMargins(0, 10, 0, 0);
				tv.setLayoutParams(lp);
				tv.setText(i.getNom());
				tv.setGravity(Gravity.CENTER);
				
				l.addView(iv);
				l.addView(tv);

				layoutSuggType1.addView(l);
				mCompteurType1++;
			}
			
			if (mCompteurType1 == 0) {
				TextView tv = new TextView(getActivity());
				tv.setText(getString(R.string.erreur_suggestionsType1));
				mLayoutSuggType1.addView(tv);
			}				
			
			mProgressBarSuggestionsType1.setVisibility(View.GONE);
	}
	
	/**
     * Methode permettant la mise e jour des suggestions "Dans l'historique" dans les vues
     */	
	private void updateUiSuggType2() {
		LinearLayout layoutSuggType2 = (LinearLayout) v.findViewById(R.id.layout_suggestions_visuels_type2);
		mCompteurType2 = 0;
		
		for (final Suggestion i : mSuggestionsType2) {
			LinearLayout l = new LinearLayout(getActivity());
			l.setOrientation(LinearLayout.VERTICAL);
			l.setGravity(Gravity.CENTER);
			l.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
			l.setPadding(10, 0, 10, 5);
			
			ImageView iv = new ImageView(getActivity());
			iv.setImageBitmap(mSuggVisuelsType2.get(mCompteurType2));
			iv.setLayoutParams(new LayoutParams(100, 100));
			iv.setOnTouchListener(new OnTouchListener(){
				public boolean onTouch(View view, MotionEvent arg1) {
					postUserLogin();
					AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
					String s = "<b>" + mAlData.get(0).getNom() + "</b> et <b>" + i.getNom() + "</b><br/><br/>" + i.getJustification();
					dialog.setMessage(Html.fromHtml(s))
					   .setTitle(getString(R.string.justification))
					   .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
						   public void onClick(DialogInterface dialog, int which) {
							   dialog.dismiss();
						   }
					   	});				
					final AlertDialog alert = dialog.create();
					alert.show();
					return false;
				};
			});
			
			TextView tv = new TextView(getActivity());
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			lp.setMargins(0, 10, 0, 0);
			tv.setLayoutParams(lp);
			tv.setText(i.getNom());
			tv.setGravity(Gravity.CENTER);
			
			l.addView(iv);
			l.addView(tv);

			layoutSuggType2.addView(l);
			mCompteurType2++;
		}
		
		if (mCompteurType2 == 0) {
			TextView tv = new TextView(getActivity());
			tv.setText(getString(R.string.erreur_suggestionsType2));
			mLayoutSuggType2.addView(tv);
		}
		
		mProgressBarSuggestionsType2.setVisibility(View.GONE);
	}
	
	/**
     * Teche asynchrone pour le telechargement des donnees de type texte pour la partie centrale du Fragment (informations concernant l'oeuvre)
     */	
	private class DownloadDataTask extends AsyncTask<String, Void, ArrayList<? extends Entry>> {
		protected void onPreExecute () {
			mProgressBarPrincipale.setVisibility(View.VISIBLE);
		}
	     protected ArrayList<? extends Entry> doInBackground(String... urls) {
			mAlData = ContainerData.getOeuvres(urls[0]);
	        return mAlData;
	     }

	     protected void onPostExecute(ArrayList<? extends Entry> al) {
	    	if (mImageVisuelTask == null && mAlData != null) {
	    		mImageVisuelTask = new DownloadImageVisuelTask();
	    		mSuggType1Task = new DownloadSuggType1Task();
	    		mSuggType2Task = new DownloadSuggType2Task();
	    		mImageVisuelTask.execute();
	    		mSuggType1Task.execute();
	    		mSuggType2Task.execute();
	    	}
	        updateUiTexte();
	        if (mDataTask != null) {
		    	mDataTask.cancel(true);
		    	mDataTask = null;
	    	}
	     }
	 }
	
	/**
     * Teche asynchrone pour le telechargement du visuel de l'oeuvre
     */	
	private class DownloadImageVisuelTask extends AsyncTask<Void, Void, Bitmap> {
		protected void onPreExecute () {
			mProgressBarVisuel.setVisibility(View.VISIBLE);
		}
		
		protected Bitmap doInBackground(Void... params) {
			mOeuvreBitmap = mAlData.get(0).getBitmap();
			return mOeuvreBitmap;
		}
		
	     protected void onPostExecute(Bitmap b) {
	        updateUiVisuel();
	        if (mImageVisuelTask != null) {
		        mImageVisuelTask.cancel(true);
		        mImageVisuelTask = null;
	        }
	     }
	}
	
	/**
     * Teche asynchrone pour le telechargement des donnees des suggestions de type "A proximite"
     */	
	private class DownloadSuggType1Task extends AsyncTask<Void, Void, ArrayList<Bitmap>> {
		protected void onPreExecute () {
			mProgressBarSuggestionsType1.setVisibility(View.VISIBLE);
		}
		
		protected ArrayList<Bitmap> doInBackground(Void... params) {
			mSuggVisuelsType1 = new ArrayList<Bitmap>();
			mSuggestionsType1 = mAlData.get(0).getSuggestionsType1();
			for (final Suggestion i : mSuggestionsType1) {
				mSuggVisuelsType1.add(i.getBitmap());
			}
	        return mSuggVisuelsType1;
		}
		
	     protected void onPostExecute(ArrayList<Bitmap> b) {
	        updateUiSuggType1();
	        if (mSuggType1Task != null) {
		        mSuggType1Task.cancel(true);
		        mSuggType1Task = null;
	        }
	     }
	}
	
	/**
     * Teche asynchrone pour le telechargement des donnees des suggestions de type "Dans l'historique"
     */	
	private class DownloadSuggType2Task extends AsyncTask<Void, Void, ArrayList<Bitmap>> {
		protected void onPreExecute () {
			mProgressBarSuggestionsType2.setVisibility(View.VISIBLE);
		}
		
		protected ArrayList<Bitmap> doInBackground(Void... params) {
			mSuggVisuelsType2 = new ArrayList<Bitmap>();
			mSuggestionsType2 = mAlData.get(0).getSuggestionsType2();
			for (final Suggestion i : mSuggestionsType2) {
				mSuggVisuelsType2.add(i.getBitmap());
			}
	        return mSuggVisuelsType2;
		}
		
	     protected void onPostExecute(ArrayList<Bitmap> b) {
	    	updateUiSuggType2();
	    	if (mSuggType2Task != null) {
		    	mSuggType2Task.cancel(true);
		        mSuggType2Task = null;
	    	}
	     }
	}
}