package com.mtrilogic.interfaces;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.mtrilogic.adapters.FragmentableAdapter;

@SuppressWarnings("unused")
public interface FragmentablePageListener extends OnMakeToastListener{
    @NonNull FragmentableAdapter getFragmentableAdapter();
    @NonNull ViewPager getViewPager();
}
