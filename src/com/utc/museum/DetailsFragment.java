package com.utc.museum;

import java.util.ArrayList;

import org.restlet.resource.ClientResource;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.utc.museum.checkers.ConnectionChecker;
import com.utc.museum.data.ContainerData;
import com.utc.museum.data.Entry;
import com.utc.museum.data.ItemListe;

/**
 * Classe abstraite représentant un fragment générique et les éléments communs des Fragments de type Détails
 * Tous les Fragments de type DetailsFragment de l'application héritent de cette classe
 */
public abstract class DetailsFragment extends Fragment {
	/**
     * Constantes permettant de faire référence de manière sûre à une erreur donnée et d'afficher le message correspond à l'utilisateur
     */
	public static final int DIALOG_LISTE_OEUVRES_ID = 0;
	public static final int DIALOG_ABOUT_ID = 1;
	public static final int DIALOG_NO_CONNEXION_ID = 2;
	
	/**
	 * Constante permettant de récupérer l'adresse IP du serveur
	 */
	public static final String ipServ = "http://192.168.1.32:8182/calm/";
	
	/**
     * Constante contenant le chemin d'accès pour réaliser un POST de l'utilisateur courant
     */
	public static final String URL_POST_USER = ipServ + "?user=";
	
	/**
     * Constante contenant le chemin d'accès à une ressource de type Liste Oeuvres sur le réseau
    */ 
	public static final String URL_LISTE_OEUVRES = ipServ + "artworkList?userName=";
	
	/**
	 * Constante permettant de faire référence de manière unique à la clé utilisée pour stockée l'id de l'oeuvre et l'id du menu
	 */
	public static final String idOeuvre = "idOeuvre";
	public static final String idMenu = "idMenu";
	
	protected Boolean mTablette;
	protected static MenuFragment f;
	protected Boolean mConnexion = false;
	protected AsyncTask<String, Void, ArrayList<? extends Entry>> mDataTask = null;
	protected AsyncTask<Void, Void, Bitmap> mImageVisuelTask = null;
	protected AsyncTask<Void, Void, ArrayList<Bitmap>> mSuggType1Task = null;
	protected AsyncTask<Void, Void, ArrayList<Bitmap>> mSuggType2Task = null;
	protected AsyncTask<String, Void, Void> mPostUserLoginTask = null;
	
	protected static ProgressBar mProgressBarListeOeuvres = null;
	protected static ListView mListViewListeOeuvres = null;
	
	private AsyncTask<String, Void, ArrayList<ItemListe>> mItemsTask = null;
	private ArrayList<ItemListe> mItems = null;
	private static ArrayAdapter<String> mAdapter;
	private static ArrayList<String> mAl;
	private MonDialogFragment mDialogListeOeuvres;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		f = (MenuFragment) getFragmentManager().findFragmentById(R.id.fragment_liste);
		// Le booléen mTablette vaut vrai si l'utilisateur utilise une tablette, faux sinon
		mTablette = f == null ? false : true;

		if (!mTablette) {
			ActionBar bar = getActivity().getActionBar();
			bar.setDisplayHomeAsUpEnabled(true);	
		}
		setHasOptionsMenu(true);
	}
	
	private void startLoadingListe() {
		mConnexion = ConnectionChecker.isOnline(getActivity());
		if (!mConnexion) {
			MonDialogFragment.newInstance(DIALOG_NO_CONNEXION_ID).show(getFragmentManager(), "DialogNoConnexion");
		}
		else {
			if (mItemsTask == null) {
    			mItemsTask = new DownloadListItemsTask();
    			mItemsTask.execute(URL_LISTE_OEUVRES + f.getLogin());
    		}
		}
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
	    inflater.inflate(R.menu.menu, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	        switch(item.getItemId()) {
	        	case R.id.menu_liste:
	        		startLoadingListe();
	        		mDialogListeOeuvres = MonDialogFragment.newInstance(DIALOG_LISTE_OEUVRES_ID);
	        		mDialogListeOeuvres.show(getFragmentManager(), "DialogListItems");
	                return true;
	        	case R.id.about:
	        		MonDialogFragment.newInstance(DIALOG_ABOUT_ID).show(getFragmentManager(), "DialogAbout");
	                return true;
		        case android.R.id.home:
		        		if (!mTablette) {
		        			Intent intent = new Intent(getActivity(), MainActivity.class);
				            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				            startActivity(intent);
		        		}
		                return true;
		        default:                
		                return super.onOptionsItemSelected(item);
	        }
	}
	
	/**
     * Méthode permettant de déterminer si des tâches asynchrones tournent en arrière plan
     * @return Vrai s'il reste des tâches asynchrones, faux sinon
     */	
	public boolean hasTasks() {
		return (mDataTask != null || mImageVisuelTask != null || mSuggType1Task != null || mSuggType2Task != null || mItemsTask != null);
	}
	
	/**
     * Méthode permettant de fermer la tâche concernant la liste des oeuvres à proximité
     */
	public void closeTasksGeneral() {
		if (mItemsTask != null) {
			mItemsTask.cancel(true);
			mItemsTask = null;
		}
	}
	
	/**
     * Méthode permettant de fermer les tâches concernant la partie centrale du fragment Détails (Oeuvre, Auteur, ...)
     * Cette méthode ferme également les tâches concernant les suggestions
     */
	public void closeTasks() {
		if (mDataTask != null) {
			mDataTask.cancel(true);
			mDataTask = null;
		}
		if (mImageVisuelTask != null) {
			mImageVisuelTask.cancel(true);
			mImageVisuelTask = null;
		}
		if (mSuggType1Task != null) {
			mSuggType1Task.cancel(true);
			mSuggType1Task = null;
		}
		if (mSuggType2Task != null) {
			mSuggType2Task.cancel(true);
			mSuggType2Task = null;
		}
	}
	
	/**
     * Méthode permettant de fermer toutes les tâches asynchrones du fragment
     */
	public void closeAllTasks() {
		closeTasksGeneral();
		closeTasks();
	}
	
	protected void postUserLogin() {
		if (mPostUserLoginTask == null) {
			mPostUserLoginTask = new PostUserLoginTask();
			mPostUserLoginTask.execute(URL_POST_USER);
		}
	}
	
	public int getShownIndex() {
		return getArguments().getInt(DetailsFragment.idMenu, 1);
	}
	
	public String getShownOeuvre() {
		return getArguments().getString(DetailsFragment.idOeuvre);
	}
	
	/**
     * Méthode permettant de remplir la liste des oeuvres à proximité à partir des données récupérées de la tâche asynchrone
     */
	private void updateUiListe() {
		if (mProgressBarListeOeuvres != null && mListViewListeOeuvres != null && mItems != null) {
     		for (ItemListe i : mItems) {
     			mAl.add(i.getTitre());
    	 	}
    		mListViewListeOeuvres.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					if (!mTablette) {
//						if (((DetailsActivity) getActivity()).getMenuIndex() == 0) {
//							if (((OeuvreFragment) getFragmentManager().findFragmentById(android.R.id.content)).getShownOeuvre() == arg2) {
//								mDialogListeOeuvres.getDialog().dismiss();
//								return;
//							}
//						}
						Intent intent = new Intent(getActivity(), OeuvreActivity.class);
						intent.putExtra(idOeuvre, arg2);
		                startActivity(intent);
					}
					else {
						f.showDetails(0, mItems.get(arg2).getId());
						mDialogListeOeuvres.getDialog().dismiss();
					}
				}
    		});
    		mProgressBarListeOeuvres.setVisibility(View.GONE);
    		mListViewListeOeuvres.setVisibility(View.VISIBLE);
    	}
		else {
			closeTasksGeneral();
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage(getString(R.string.erreur_chargement_liste_oeuvres))
			       .setPositiveButton(getString(R.string.reessayer), new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                startLoadingListe();
			                dialog.dismiss();
			           }
			       });
			builder.create().show();
		}
     }
	
	/**
     * Tâche asynchrone pour le téléchargement de la liste des oeuvres à proximité de l'utilisateur
     */	
	private class DownloadListItemsTask extends AsyncTask<String, Void, ArrayList<ItemListe>> {
		protected void onPreExecute() {
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
	
	/**
     * Tâche asynchrone pour le téléchargement de la liste des oeuvres à proximité de l'utilisateur
     */	
	private class PostUserLoginTask extends AsyncTask<String, Void, Void> {
		protected void onPreExecute() {
		}
		
		protected Void doInBackground(String... urls) {
			try {
				new ClientResource(urls[0]).post(f.getLogin());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
	    protected void onPostExecute(Void ret) {
	    	 if (mPostUserLoginTask != null) {
	    		 mPostUserLoginTask.cancel(true);
	    		 mPostUserLoginTask = null;
	    	 }
	    }
	}
	
	/**
     * Classe générique héritant de DialogFragment (boîte de dialogue)
     */	
	public static class MonDialogFragment extends DialogFragment {
		/**
	     * Méthode statique de la classe qui permet d'instancier un nouvel objet de la classe en utilisant le pattern Factory, avec comme propriété l'id du message que l'on veut afficher
	     * @param id L'id du message que l'on veut afficher, par exemple DetailsFragment.DIALOG_NO_CONNEXION_ID
	     * @return MonDialogFragment Une instance de la classe MonDialogFragment
	     */		
        public static MonDialogFragment newInstance(int id) {
        	MonDialogFragment frag = new MonDialogFragment();
            Bundle args = new Bundle();
            args.putInt("id", id);
            frag.setArguments(args);
            return frag;
        }
        
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int id = getArguments().getInt("id");
            Dialog dialog = null;
            AlertDialog.Builder builder = null;
            switch (id) {
	            case DetailsFragment.DIALOG_LISTE_OEUVRES_ID:
	            	View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_liste, null);
	            	mProgressBarListeOeuvres = (ProgressBar) v.findViewById(R.id.progressbar_dialog);
	            	mListViewListeOeuvres = (ListView) v.findViewById(R.id.liste_oeuvres);
	            	mAl = new ArrayList<String>();
	            	mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item_liste, mAl);
		    		mListViewListeOeuvres.setAdapter(mAdapter);
	            	builder = new AlertDialog.Builder(getActivity())
	            	.setView(v)
                    .setTitle(Html.fromHtml("<b>" + getString(R.string.liste_oeuvre) + "</b>"))
                    .setNegativeButton(R.string.annuler,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                    );
	            	dialog = builder.create(); 
	            	break;
	            case DetailsFragment.DIALOG_ABOUT_ID:
	            	String msg;
	            	msg = "<b>Login</b><br/> " + f.getLogin() + "<br/><br/><b>Développeurs</b><br/>- Thomas Keunebroek<br/>- Alexandre Masciulli<br/><br/><b>Projet</b><br/>TX00 : Aide à la visite d'un musée<br/><br>Projet CALM (Contextualized Annotation for Learning through Mobility)<br><br>- Pierre-Yves Gicquel<br>- Dominique Lenne";
	            	builder = new AlertDialog.Builder(getActivity())
                    .setTitle(getString(R.string.a_propos))
                    .setMessage(Html.fromHtml(msg))
                    .setPositiveButton(R.string.OK,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                    );
	            	dialog = builder.create(); 
	            	break;
	            case DetailsFragment.DIALOG_NO_CONNEXION_ID:
	            	builder = new AlertDialog.Builder(getActivity());
	    			builder.setMessage(getString(R.string.erreur_connexion))
	    			   .setCancelable(false)
	    			   .setTitle(getString(R.string.erreur_connexion_titre))
	    			   .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
	    				   public void onClick(DialogInterface dialog, int which) {
	    					   startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
	    				   }
	    			   	})
	    			   .setNegativeButton(getString(R.string.annuler), new DialogInterface.OnClickListener() {
	    			       public void onClick(DialogInterface dialog, int id) {
	    			            dialog.dismiss();
	    			       }
	    			    });				
	    			dialog = builder.create();
	    			break;
	            default:
	            	break;
            }
            return dialog;
        }
    }
} 
