package com.mtrilogic.interfaces;

import androidx.annotation.NonNull;

import com.mtrilogic.abstracts.Fragmentable;
import com.mtrilogic.abstracts.Paginable;

@SuppressWarnings("unused")
public interface FragmentableListener extends OnMakeToastListener{
    Fragmentable<? extends Paginable> getFragmentable(@NonNull Paginable paginable, int position);
    void onPositionChanged(int position);
}
