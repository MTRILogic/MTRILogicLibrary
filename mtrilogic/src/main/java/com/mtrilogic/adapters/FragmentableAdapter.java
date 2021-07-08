package com.mtrilogic.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mtrilogic.abstracts.Fragmentable;
import com.mtrilogic.abstracts.Paginable;
import com.mtrilogic.classes.Base;
import com.mtrilogic.classes.Listable;
import com.mtrilogic.interfaces.FragmentableListener;

@SuppressWarnings({"unused","deprecation"})
public final class FragmentableAdapter extends FragmentPagerAdapter{
    private final FragmentableListener listener;

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public FragmentableAdapter(@NonNull FragmentManager manager, @NonNull FragmentableListener listener){
        super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.listener = listener;
    }

// ++++++++++++++++| PUBLIC OVERRIDE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @NonNull
    @Override
    public final Fragmentable<? extends Paginable> getItem(int position){
        return listener.getFragmentable(getPaginable(position), position);
    }

    @Override
    public final int getCount(){
        return getPaginableListable().getItemCount();
    }

    @Override
    public final int getItemPosition(@NonNull Object object){
        Fragmentable<? extends Paginable> fragmentable = (Fragmentable<? extends Paginable>)object;
        Paginable paginable = fragmentable.getPaginable();
        if (listener.getPaginableListable().containsItem(paginable)){
            int position = getPaginableListable().getItemPosition(paginable);
            if (fragmentable.getPosition() != position){
                fragmentable.setPosition(position);
                listener.onPositionChanged(position);
                return position;
            }
            return POSITION_UNCHANGED;
        }
        if (getCount() == 0){
            listener.onPositionChanged(Base.INVALID_POSITION);
        }
        return POSITION_NONE;
    }

    @Nullable
    @Override
    public final CharSequence getPageTitle(int position){
        return getPaginable(position).getPageTitle();
    }

    @Override
    public final long getItemId(int position){
        return getPaginable(position).getItemId();
    }

// ++++++++++++++++| PRIVATE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private Listable<Paginable> getPaginableListable(){
        return listener.getPaginableListable();
    }

    private Paginable getPaginable(int position){
        return getPaginableListable().getItem(position);
    }
}
