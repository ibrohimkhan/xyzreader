package com.udacity.xyzreader.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.udacity.xyzreader.R;
import com.udacity.xyzreader.ui.events.ItemSelectionEventListener;
import com.udacity.xyzreader.ui.readerdetails.ReaderDetailsFragment;
import com.udacity.xyzreader.ui.readerlist.ReaderListFragment;

public class MainActivity extends AppCompatActivity implements ItemSelectionEventListener<Integer>  {

    private Fragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showReaderListFragment();
    }

    @Override
    public void onItemSelectedEvent(Integer item) {
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
