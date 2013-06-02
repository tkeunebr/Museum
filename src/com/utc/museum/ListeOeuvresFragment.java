package com.utc.museum;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.utc.museum.checkers.ConnectionChecker;
import com.utc.museum.data.ContainerData;
import com.utc.museum.data.ItemListe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Classe representant le fragment contenant la liste des oeuvres e proximite
 * Cette classe herite de la classe abstraite DetailsFragment, qui permet de factoriser tous les elements commun des Fragments de type Details de l'application
 * Elle est appelee apres chargement de l'Activity principale, elle permet e l'utilisateur de choisir une oeuvre de demarrage parmi la liste
 * Elle se charge egalement de demander son login e l'utilisateur en premier lieu
 */
public class ListeOeuvresFragment extends DetailsFragment {
	
	/**
     * Constante contenant le chemin d'acces e une ressource de type Liste Oeuvres sur le reseau
     */ 
	public final String URL = ipServ + "artworkList?userName=";
	
	private final static String mRegex = "^[a-z0-9_-]{3,15}$";
	private AsyncTask<String, Void, ArrayList<ItemListe>> mItemsTask = null;
	private ArrayList<ItemListe> mItems = null;
	private static ArrayAdapter<String> mAdapter;
	private static ArrayList<String> mAl;
	private ListView mListView;
	private ProgressBar mProgressBar;
	private View mBordure;
	private TextView mTextView;
	private TextView mTextChargement;
	private AlertDialog mDialog;
	private TextView mTextLogin;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (!this.isInLayout() && container == null) return null;
		View v = inflater.inflate(R.layout.liste_oeuvres_demarrage, container, false);
		
		f = (MenuFragment) getFragmentManager().findFragmentById(R.id.fragment_liste);

				
		if (f != null && (f.getLogin() == null || f.getLogin().isEmpty())) {
				showDialog();
	
				final Button valider = mDialog.getButton(AlertDialog.BUTTON_POSITIVE);
	            if (mTextLogin != null && valider != null) {
	            	valider.setEnabled(false);
	            	mTextLogin.addTextChangedListener(new TextWatcher() {
	    		        public void onTextChanged(CharSequence s, int start, int before, int count) {
	    		        }
	    		 
	    		        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	    		        }
	    		 
	    		        public void afterTextChanged(Editable s) {
	    		        	valider.setEnabled(isMatch(mTextLogin.getText().toString(), mRegex));
	    		        }
	    		    });
	        	}
		}
		
		mListView = (ListView) v.findViewById(R.id.liste_oeuvres);
		mProgressBar = (ProgressBar) v.findViewById(R.id.progressbar_liste_oeuvres);
		mTextChargement = (TextView) v.findViewById(R.id.textview_chargement);
		mBordure = v.findViewById(R.id.bordure);
		mTextView = (TextView) v.findViewById(R.id.textview_liste_oeuvres);
    	mAl = new ArrayList<String>();
    	mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item_liste_oeuvres_demarrage, mAl);
		mListView.setAdapter(mAdapter);
		getActivity().getActionBar().setTitle(getString(R.string.aucune_oeuvre));
		return v;
	}
	
	private boolean isMatch(String s, String pattern) {
        try {
            Pattern patt = Pattern.compile(pattern);
            Matcher matcher = patt.matcher(s);
            return matcher.matches();
        } catch (RuntimeException e) {
        	e.printStackTrace();
        	return false;
        }  
	}
	
	/**
     * Methode permettant l'affichage de la boete de dialogue demander son login e l'utilisateur
     */	
	private void showDialog() {
		 mDialog = null;
         AlertDialog.Builder builder = null;
         View v = getActivity().getLayoutInflater().inflate(R.layout.login_demarrage, null);
         if (v != null) {
        	 mTextLogin = (TextView) v.findViewById(R.id.input_login);
	            builder = new AlertDialog.Builder(getActivity())
	                    .setTitle(getString(R.string.demande_login))
	                    .setView(v)
	                    .setPositiveButton(getString(R.string.valider),
	                        new DialogInterface.OnClickListener() {
	                    		public void onClick(DialogInterface dialog, int whichButton) {
	                            	if (mTextLogin != null && !mTextLogin.getText().toString().isEmpty()) {
	                            		if (f != null) {
	                            			f.setLogin(mTextLogin.getText().toString());
	                            			startLoading();
	                            			dialog.dismiss();
	                            		}
	                            	}
	                            }
	                        }
	                    );

	     mDialog = builder.create();
	     mDialog.setCancelable(false);
	     mDialog.show();
         }
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	    inflater.inflate(R.menu.menu_demarrage, menu);
	}
	
	/**
     * Methode statique de la classe qui permet d'instancier un nouvel objet de la classe en utilisant le pattern Factory
     * @return ListeOeuvresFragment Une instance de la classe ListeOeuvresFragment
     */	
	public static ListeOeuvresFragment newInstance() {
		// Instanciation d'un nouveau fragment
		return new ListeOeuvresFragment();
	}
	
	private void updateUiListe() {
		if (mProgressBar != null && mListView != null && mItems != null) {
     		for (ItemListe i : mItems) {
     			mAl.add(i.getTitre());
    	 	}
    		mListView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					f.setIndexOeuvre(mItems.get(arg2).getId());
					f.showDetails(0, mItems.get(arg2).getId());
				}
    		});
    		mTextChargement.setVisibility(View.GONE);
    		mProgressBar.setVisibility(View.GONE);
    		mTextView.setVisibility(View.VISIBLE);
    		mBordure.setVisibility(View.VISIBLE);
    		mListView.setVisibility(View.VISIBLE);
    	}
		else {
			closeTask();
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage(getString(R.string.erreur_chargement_liste_oeuvres))
			       .setPositiveButton(getString(R.string.reessayer), new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                startLoading();
			                dialog.cancel();
			           }
			       });
			builder.create().show();
		}
     }
	
	/**
     * Methode permettant de demarrer le telechargement de la liste des oeuvres e proximite avec une teche asynchrone, et le remplissage de la vue avec cette liste
     */	
	private void startLoading() {
		mConnexion = ConnectionChecker.isOnline(getActivity());
		if (!mConnexion) {
			MonDialogFragment.newInstance(DIALOG_NO_CONNEXION_ID).show(getFragmentManager(), "DialogNoConnexion");
		}
		else {
			if (mItemsTask == null) {
    			mItemsTask = new DownloadListItemsTask();
    			mItemsTask.execute(URL + f.getLogin());
    		}
		}
	}
	
	private void closeTask() {
		if (mItemsTask != null) {
			mItemsTask.cancel(true);
			mItemsTask = null;
		}
	}
	
	/**
     * Teche asynchrone pour le telechargement de la liste des oeuvres e proximite
     */	
	private class DownloadListItemsTask extends AsyncTask<String, Void, ArrayList<ItemListe>> {
		protected void onPreExecute () {
			mProgressBar.setVisibility(View.VISIBLE);
			mTextChargement.setVisibility(View.VISIBLE);
		}
		
		protected ArrayList<ItemListe> doInBackground(String... urls) {
			mItems = ContainerData.getListe(urls[0]);
	        return mItems;
		}
		
	    protected void onPostExecute(ArrayList<ItemListe> b) {
	    	updateUiListe();
	    	if (mItemsTask != null) {
	    		mItemsTask.cancel(true);
		    	mItemsTask = null;
	    	}
	    }
	}
}