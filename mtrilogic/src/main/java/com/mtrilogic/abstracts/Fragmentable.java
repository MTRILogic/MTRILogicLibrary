package com.mtrilogic.abstracts;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mtrilogic.interfaces.FragmentListener;
import com.mtrilogic.interfaces.OnMakeToastListener;

@SuppressWarnings({"unused","WeakerAccess"})
public abstract class Fragmentable<P extends Paginable> extends Fragment implements OnMakeToastListener {
    private static final String PAGINABLE = "paginable", POSITION = "position";

    protected FragmentListener listener;
    protected int position;
    protected P page;

// ++++++++++++++++| PROTECTED ABSTRACTS METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++

    @NonNull
    protected abstract View onCreateViewFragment(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

// ++++++++++++++++| PUBLIC STATIC METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @NonNull
    public static Fragmentable<? extends Paginable> getInstance(@NonNull Fragmentable<? extends Paginable> fragmentable, @NonNull Paginable paginable, int position){
        Bundle args = new Bundle();
        args.putParcelable(PAGINABLE, paginable);
        args.putInt(POSITION, position);
        fragmentable.setArguments(args);
        return fragmentable;
    }

// ++++++++++++++++| PUBLIC OVERRIDE METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentListener){
            listener = (FragmentListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            page = savedInstanceState.getParcelable(PAGINABLE);
            position = savedInstanceState.getInt(POSITION);
        }else {
            Bundle args = getArguments();
            if (args != null){
                page = args.getParcelable(PAGINABLE);
                position = args.getInt(POSITION);
            }
        }
    }

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (page != null){
            return onCreateViewFragment(inflater, container, savedInstanceState);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        position = listener.getPaginableListable().getItemPosition(page);
        onNewPosition();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(PAGINABLE, page);
        outState.putInt(POSITION, position);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @Override
    public final void onMakeToast(String line) {
        if (listener != null){
            listener.onMakeToast(line);
        }
    }

    @Override
    public final void onMakeLog(String line) {
        if (listener != null){
            listener.onMakeLog(line);
        }
    }

// ++++++++++++++++| PUBLIC METHODS |+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public final void setPage(P page) {
        this.page = page;
        onNewPage();
    }

    public final Paginable getPaginable(){
        return page;
    }

    public final void setPosition(int position) {
        this.position = position;
        onNewPosition();
    }

    public final int getPosition() {
        return position;
    }

// ++++++++++++++++| PROTECTED METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    protected void onNewPosition(){

    }

    protected void onNewPage(){

    }

    protected final void autoDelete(){
        if (listener != null && listener.getPaginableListable().deleteItem(page)) {
            listener.getFragmentableAdapter().notifyDataSetChanged();
        }
    }
}
