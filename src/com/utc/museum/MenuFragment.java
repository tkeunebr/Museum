package com.utc.museum;

import com.utc.museum.anim.DropDownAnim;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Classe representant le menu de navigation
 * C'est cette classe qui permet de faire le lien entre les differents Fragments/Activity de type Details de l'application
 */
public class MenuFragment extends ListFragment {
	private int mItemSelectionne = -1;
	private boolean mTablette = false;
	private String mIdOeuvre = "";
	private Fragment mFragmentDetails = null;
	private Fragment mFragmentCache = null;
	private LinearLayout mLayout;
	private boolean menuCache = false;
	private String mLoginId = null;
	
	/**
     * Constantes permettant l'identification de maniere unique des Fragments dans la pile des Fragments
     */
	public final static String HOMEFRAGMENT = "class com.utc.museum.HomeFragment";
	public final static String OEUVREFRAGMENT = "class com.utc.museum.OeuvreFragment";
	public final static String AUTEURFRAGMENT = "class com.utc.museum.AuteurFragment";
	public final static String STYLEFRAGMENT = "class com.utc.museum.StyleFragment";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
		if (!this.isInLayout() && container == null) return null;
		View v = inflater.inflate(R.layout.menu_nav_fragment, container);
		v.setDrawingCacheEnabled(false);
		mLayout = (LinearLayout) v.findViewById(R.id.layout_menu_liste);
	    setListAdapter(ArrayAdapter.createFromResource(getActivity(), R.array.menu, R.layout.item_liste));
	    
	    return v;
	}
	
	public void onActivityCreated (Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	    // C'est une tablette si cette vue existe
	    View fragmentDetails = getActivity().findViewById(R.id.fragment_details);
	    mTablette = fragmentDetails != null && fragmentDetails.getVisibility() == View.VISIBLE;	    
	    
        if (mTablette) {
                getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        } else {
                getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);
        }
        
        // On met e jour le fragment details
        if (mFragmentDetails == null) {
        	showDetails(-1, "");
        }  
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
        showDetails(position, mIdOeuvre);
	}
	
	public String getLogin() {
		return mLoginId;
	}
	
	public void setLogin(String login) {
		mLoginId = login;
	}
	
	public int getItemSelectionne() {
		return mItemSelectionne;
	}
	
	public void setItemSelectionne(int item) {
		if (item >= 0) {
			mItemSelectionne = item;
		}
	}
	
	public void setIndexOeuvre(String oeuvre) {
		mIdOeuvre = oeuvre;
	}
	
	public boolean isTablet() {
		return mTablette;
	}
	
	/**
     * Methode permettant d'instancier un fragment de type Details selon la demande de l'utilisateur. Cette methode est appelee suite e un clique sur element du menu
     * Le fragment est affiche sur la partie droite en mode tablette, et une nouvelle activity est lancee en mode telephone
     * @param menuIndex L'item clique dans le menu
     * @param idOeuvre L'id de l'oeuvre que l'utilisateur est actuellement en train de consulter
     * @return void
     */	
	public void showDetails(int menuIndex, String idOeuvre) {
        if (mTablette) {
            // C'est une tablette, on affiche les details dans le fragment details
	        if (idOeuvre == "") {
	        	// Premier demarrage, pas d'oeuvre selectionnee, on affiche la liste des oeuvres (avec demande du login utilisateur)
	        	mFragmentDetails = (ListeOeuvresFragment) ListeOeuvresFragment.newInstance();
    			FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_details, mFragmentDetails);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
                if (!menuCache) {
                	hideMenu();
                }
	        }
	        else {
	        	if (menuCache) {
	        		showMenu();
	        	}
	        	getListView().setItemChecked(menuIndex, true);
	        	if (mItemSelectionne != menuIndex || !mIdOeuvre.equalsIgnoreCase(idOeuvre)) {
	        		// On ne va pas recharger 2 fois la meme chose
		        	FragmentManager fm = getFragmentManager();
		        	FragmentTransaction ft = fm.beginTransaction();
		        	mFragmentCache = null;
	        		mItemSelectionne = menuIndex;
	        		switch(mItemSelectionne) {
		        	case 0:
		        		try {
		        			mFragmentCache = fm.findFragmentByTag(OEUVREFRAGMENT + idOeuvre);
		        			ft.hide(mFragmentDetails);
		        			if (mFragmentCache == null || !mIdOeuvre.equalsIgnoreCase(idOeuvre)) {
		        				if (!mIdOeuvre.equalsIgnoreCase(idOeuvre)) {
		        					verifierTachesFond(mFragmentDetails);
		        					ft.remove(mFragmentDetails);
		        				}
		        				mFragmentDetails = OeuvreFragment.newInstance(mItemSelectionne, idOeuvre);
		    	                ft.add(R.id.fragment_details, mFragmentDetails, OEUVREFRAGMENT + idOeuvre);
		    	            }
		    	            else {
		    	            	mFragmentDetails = mFragmentCache;
		    	            	ft.show(mFragmentDetails);
		    	            }
		        			
		        			mIdOeuvre = idOeuvre;    

		    	            ft.commit();
		        		} catch (IllegalStateException err) {
		        			err.printStackTrace();
		        	    }
		        		break;
		        	case 1:
		        		try {
		        			mFragmentCache = fm.findFragmentByTag(AUTEURFRAGMENT + mIdOeuvre);
		        			ft.hide(mFragmentDetails);
		    	            if (mFragmentCache == null) {
		    	            	mFragmentDetails = AuteurFragment.newInstance(mItemSelectionne, idOeuvre);
		    	                ft.add(R.id.fragment_details, mFragmentDetails, AUTEURFRAGMENT + mIdOeuvre);
		    	            }
		    	            else {
		    	            	mFragmentDetails = mFragmentCache;
		    	            	ft.show(mFragmentDetails);
		    	            }    	            

		    	            ft.commit();
		        		} catch (IllegalStateException err) {
		        			err.printStackTrace();
		        	    }
		        		break;
		        	case 2:
		        		try {
		        			mFragmentCache = fm.findFragmentByTag(STYLEFRAGMENT + mIdOeuvre);
		        			ft.hide(mFragmentDetails);
		    	            if (mFragmentCache == null) {
		    	            	mFragmentDetails = StyleFragment.newInstance(mItemSelectionne, idOeuvre);
		    	                ft.add(R.id.fragment_details, mFragmentDetails, STYLEFRAGMENT + mIdOeuvre);
		    	            }
		    	            else {
		    	            	mFragmentDetails = mFragmentCache;
		    	            	ft.show(mFragmentDetails);
		    	            }    	            

		    	            ft.commit();
		        		} catch (IllegalStateException err) {
		        			err.printStackTrace();
		        	    }
		        		break;
		        	default:
		        		ft.hide(mFragmentDetails);
		        		break;
	        	}
	        	
		        }
	        }
        }
        else {
            // Ce n'est pas une tablette, on lance une nouvelle Activity
        	Intent intent = null;
        	if (idOeuvre == "") {
    	       	// Premier demarrage, pas d'oeuvre selectionnee, on affiche la liste des oeuvres (avec demande du login utilisateur)
        		intent = new Intent(getActivity(), ListeOeuvresActivity.class);
    	    }
        	else {
        		mItemSelectionne = menuIndex;
        		mIdOeuvre = idOeuvre;
	        	switch(mItemSelectionne) {
	        	case 0:
	        		intent = new Intent(getActivity(), OeuvreActivity.class);
	        		break;
	        	case 1:
	        		intent =  new Intent(getActivity(), AuteurActivity.class);
	        		break;
	        	default:
	        		intent = new Intent(getActivity(), AuteurActivity.class);
	        		break;
	        	}
        	}
        	intent.putExtra(DetailsActivity.MENU_INDEX, mItemSelectionne);
        	intent.putExtra(DetailsActivity.OEUVRE_ID, mIdOeuvre);
            startActivity(intent);
        }
	}
	
	/**
     * Methode permettant de cacher le menu (MenuFragment) avec une animation
     */	
	public void hideMenu() {
		ObjectAnimator fadeOut = ObjectAnimator.ofFloat(mLayout, "alpha", 0f);
		fadeOut.setDuration(300);
		fadeOut.addListener(new AnimatorListenerAdapter() {
	        @Override
	        public void onAnimationEnd(Animator animation) {
	            mLayout.setAlpha(0f);
	        }
	    });
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(fadeOut);
        animSet.start();
        mLayout.startAnimation(new DropDownAnim(mLayout, 400, false));
        menuCache = true;
	}
	
	/**
     * Methode permettant d'afficher le menu (MenuFragment) avec une animation
     */	
	public void showMenu() {
		ObjectAnimator fadeIn = ObjectAnimator.ofFloat(mLayout, "alpha", 1f);
		fadeIn.setDuration(300);
		fadeIn.addListener(new AnimatorListenerAdapter() {
	        @Override
	        public void onAnimationEnd(Animator animation) {
	            mLayout.setAlpha(1f);
	        }
	    });
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(fadeIn);
        animSet.start();
        mLayout.startAnimation(new DropDownAnim(mLayout, 400, true));
		menuCache = false;
	}
	
	public boolean getIsToggled() {
		return !menuCache;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    Log.d("savedins", "savedins");
	    outState.putInt("itemCourant", mItemSelectionne);
	}
	
	/**
     * Methode permettant de verifier si des teches de fond tournent encore dans le fragment Details
     * Si c'est le cas, ces teches sont annulees. Methode e appeler quand l'utilisateur change de vue avant la fin du chargement pour eviter tout probleme
     * @param Le fragment dont on veut verifier et annuler les teches
     * @return void
     */	
	public void verifierTachesFond(Fragment f) {
		if (f != null && ((DetailsFragment) f).hasTasks()) {
			((DetailsFragment) f).closeAllTasks();
		}
	}
}
