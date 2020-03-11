package com.udacity.xyzreader.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.udacity.xyzreader.R;
import com.udacity.xyzreader.ui.events.ItemSelectionEventListener;
import com.udacity.xyzreader.ui.readerdetails.ReaderDetailsFragment;
import com.udacity.xyzreader.ui.readerlist.ReaderListFragment;

public class MainActivity extends AppCompatActivity implements ItemSelectionEventListener<Integer>  {

    private static final String ACTIVE_FRAGMENT = "active_fragment";
    private static final String SELECTED_ITEM = "selected_item";

    private Fragment activeFragment;
    private int selectedItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            showReaderListFragment();

        } else {
            int itemPosition = savedInstanceState.getInt(SELECTED_ITEM);

            String tag = savedInstanceState.getString(ACTIVE_FRAGMENT);
            activeFragment = getSupportFragmentManager().findFragmentByTag(tag);

            if (activeFragment instanceof ReaderListFragment) showReaderListFragment();
            else showReaderDetailsFragment(itemPosition);
        }
    }

    @Override
    public void onItemSelectedEvent(Integer item) {
        selectedItemPosition = item;
        showReaderDetailsFragment(item);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();

            int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
            String tag = getSupportFragmentManager().getBackStackEntryAt(index).getName();

            activeFragment = getSupportFragmentManager().findFragmentByTag(tag);

        } else {
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ACTIVE_FRAGMENT, activeFragment.getTag());
        outState.putInt(SELECTED_ITEM, selectedItemPosition);
    }

    private void showReaderDetailsFragment(Integer position) {
        if (activeFragment instanceof ReaderDetailsFragment) return;

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(ReaderDetailsFragment.TAG);

        if (fragment == null) {
            fragment = ReaderDetailsFragment.newInstance(position);
            addNewFragment(fragment, ReaderDetailsFragment.TAG);

        } else {
            showFragment(fragment);
        }
    }

    private void showReaderListFragment() {
        if (activeFragment instanceof ReaderListFragment) return;

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(ReaderListFragment.TAG);

        if (fragment == null) {
            fragment = ReaderListFragment.newInstance();
            addNewFragment(fragment, ReaderListFragment.TAG);

        } else {
            showFragment(fragment);
        }
    }

    private void addNewFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (activeFragment != null) transaction.hide(activeFragment);

        transaction.add(R.id.fragment_container, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();

        activeFragment = fragment;
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (activeFragment != null)
            transaction.hide(activeFragment);

        transaction.show(fragment);
        transaction.commitNow();

        activeFragment = fragment;
    }
}
