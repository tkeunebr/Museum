package com.utc.museum.anim;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout.LayoutParams;

/**
 * Classe représentant l'animation utilisée pour afficher et cacher le menu (MenuFragment) avec un effet de slide
 * Cette classe hérite de la classe Animation
 */
public class DropDownAnim extends Animation {
	
	private LayoutParams mViewLayoutParams;
    private View mAnimatedView;
    private boolean mIsVisibleAfter = false;
    private boolean mWasEndedAlready = false;
    private boolean mExpand;

    public DropDownAnim(View view, int duration, boolean expand) {
    	setDuration(duration);
        mAnimatedView = view;
        mExpand = expand;
        mViewLayoutParams = (LayoutParams) view.getLayoutParams();
        if (expand) {
        	mIsVisibleAfter = (mViewLayoutParams.weight == 15);
        }
        else {
        	mIsVisibleAfter = (mViewLayoutParams.weight == 0);
        }
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
    	super.applyTransformation(interpolatedTime, t);
        if (interpolatedTime < 1.0f) {
        	if (mExpand && mViewLayoutParams.weight < 15) {
        		mViewLayoutParams.weight += 3f;
        	}
        	else if (!mExpand && mViewLayoutParams.weight > 0){
        		mViewLayoutParams.weight -= 3f;
        	}
        	mAnimatedView.requestLayout();
        } else if (!mWasEndedAlready) {
        	if (mExpand) {
        		mViewLayoutParams.weight = 15;
        	}
        	else {
        		mViewLayoutParams.weight = 0;
        	}
            mAnimatedView.requestLayout();

            if (mIsVisibleAfter && mExpand) {
                mAnimatedView.setVisibility(View.VISIBLE);
            }
            else if (mIsVisibleAfter && !mExpand) {
            	mAnimatedView.setVisibility(View.GONE);
            }
            mWasEndedAlready = true;
        }
    }
}