package com.tweekerz.sharecam;

import android.support.annotation.NonNull;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

public class ResizeAnimation extends Animation {
    final int mFinalLength;
    final boolean mIsPortrait;
    final int mStartLength;
    final LinearLayout mView;

    public ResizeAnimation(@NonNull LinearLayout view, ImageParameters imageParameters) {
        this.mIsPortrait = imageParameters.isPortrait();
        this.mView = view;
        this.mStartLength = this.mIsPortrait ? this.mView.getHeight() : this.mView.getWidth();
        this.mFinalLength = imageParameters.getAnimationParameter();
    }

    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int newLength = (int) (((float) this.mStartLength) + (((float) (this.mFinalLength - this.mStartLength)) * interpolatedTime));
        if (this.mIsPortrait) {
            this.mView.getLayoutParams().height = newLength;
        } else {
            this.mView.getLayoutParams().width = newLength;
        }
        this.mView.requestLayout();
    }

    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    public boolean willChangeBounds() {
        return true;
    }
}
