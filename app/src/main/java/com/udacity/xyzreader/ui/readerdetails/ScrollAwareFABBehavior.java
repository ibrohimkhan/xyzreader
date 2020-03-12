package com.udacity.xyzreader.ui.readerdetails;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ScrollAwareFABBehavior extends FloatingActionButton.Behavior {

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull FloatingActionButton child, @NonNull View dependency) {
        return dependency instanceof AppBarLayout || super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, @NonNull FloatingActionButton child, View dependency) {
        if (dependency instanceof AppBarLayout) {

            if (dependency.getBottom() < 450 && child.getVisibility() == View.VISIBLE) {
                child.hide();
                return true;

            } else if (dependency.getBottom() > 500 && child.getVisibility() != View.VISIBLE) {
                child.show();
                return true;
            }

            return false;

        } else {
            return super.onDependentViewChanged(parent, child, dependency);
        }
    }
}
