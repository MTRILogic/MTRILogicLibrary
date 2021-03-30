package com.mtrilogic.abstracts;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mtrilogic.adapters.FragmentableAdapter;
import com.mtrilogic.interfaces.FragmentablePageListener;
import com.mtrilogic.interfaces.OnMakeToastListener;

@SuppressWarnings({"unused","WeakerAccess"})
public abstract class Fragmentable<P extends Paginable> extends Fragment implements OnMakeToastListener {
    private static final String PAGINABLE = "paginable", STATE = "state";

    protected abstract View onCreateViewFragment(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);
    protected abstract void onNewPosition();

    protected FragmentablePageListener listener;
    protected Bundle args;
    protected int position;
    protected P page;

// ++++++++++++++++| PUBLIC STATIC METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static Fragmentable<? extends Paginable> getInstance(Paginable paginable, Fragmentable<? extends Paginable> fragmentable){
        Bundle args = new Bundle();
        args.putParcelable(PAGINABLE, paginable);
        args.putBundle(STATE, new Bundle());
        fragmentable.setArguments(args);
        return fragmentable;
    }

// ++++++++++++++++| PUBLIC OVERRIDE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentablePageListener){
            listener = (FragmentablePageListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getArguments();
        if (args != null){
            page = args.getParcelable(PAGINABLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (page != null){
            position = listener.getFragmentableAdapter().getPaginablePosition(page);
            View view = onCreateViewFragment(inflater, container, savedInstanceState);
            onNewPosition();
            return view;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @Override
    public void onMakeToast(String line){
        if (listener != null) {
            listener.onMakeToast(line);
        }
    }

    @Override
    public void onMakeLog(String line) {
        if (listener != null){
            listener.onMakeLog(line);
        }
    }

    // ++++++++++++++++| PUBLIC METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public Paginable getPaginable(){
        return page;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
        onNewPosition();
        String s = super.getTag();
    }

// ++++++++++++++++| PROTECTED METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    protected void autoDelete(){
        if (listener != null) {
            FragmentableAdapter adapter = listener.getFragmentableAdapter();
            if (adapter != null) {
                if (adapter.removePaginable(page)) {
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}
