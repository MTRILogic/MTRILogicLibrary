package com.mtrilogic.mtrilogicsample;

import com.getbase.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

import com.mtrilogic.abstracts.Fragmentable;
import com.mtrilogic.abstracts.Paginable;
import com.mtrilogic.adapters.FragmentableAdapter;
import com.mtrilogic.classes.Base;
import com.mtrilogic.classes.Listable;
import com.mtrilogic.interfaces.FragmentListener;
import com.mtrilogic.interfaces.FragmentableListener;
import com.mtrilogic.mtrilogicsample.databinding.ActivityMainBinding;
import com.mtrilogic.mtrilogicsample.fragments.ExpandableFragment;
import com.mtrilogic.mtrilogicsample.fragments.InflatableFragment;
import com.mtrilogic.mtrilogicsample.fragments.RecyclableFragment;
import com.mtrilogic.mtrilogicsample.pages.ExpandablePage;
import com.mtrilogic.mtrilogicsample.pages.InflatablePage;
import com.mtrilogic.mtrilogicsample.pages.RecyclablePage;
import com.mtrilogic.mtrilogicsample.types.PageType;

@SuppressWarnings("unused")
public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, FragmentableListener, FragmentListener{
    private static final int[] TITLES = {
            R.string.inflatable,
            R.string.recyclable,
            R.string.expandable
    };
    private static final int[] TYPES = {
            PageType.INFLATABLE,
            PageType.RECYCLABLE,
            PageType.EXPANDABLE
    };
    private static final String TAG = "MainActivityTAG";
    private ActivityMainBinding binding;
    private ActionBar actionBar;
    private FragmentableAdapter adapter;
    private Listable<Paginable> paginableListable;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            paginableListable = new Listable<>(savedInstanceState);
        }else {
            int count = 0;
            paginableListable = new Listable<>();
            for (int i = 0; i < 3; i++){
                Paginable paginable = getNewPaginable(i);
                if (paginable != null && paginableListable.appendItem(paginable)){
                    count++; // no used
                }
            }
        }

        adapter = new FragmentableAdapter(getSupportFragmentManager(),this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        binding.pager.setAdapter(adapter);
        binding.pager.addOnPageChangeListener(this);
        binding.tabs.setupWithViewPager(binding.pager);

        if(binding.tabs.getTabCount() > 0){
            adapter.notifyDataSetChanged();
        }
        if(binding.tabs.getSelectedTabPosition() == 0){
            pageSelected(0);
        }

        FloatingActionButton fabInflatable = findViewById(R.id.fabInflatable);
        fabInflatable.setOnClickListener(v -> addNewPaginable(getNewPaginable(0)));

        FloatingActionButton fabRecyclable = findViewById(R.id.fabRecyclable);
        fabRecyclable.setOnClickListener(v -> addNewPaginable(getNewPaginable(1)));

        FloatingActionButton fabExpandable = findViewById(R.id.fabExpandable);
        fabExpandable.setOnClickListener(v -> addNewPaginable(getNewPaginable(2)));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        paginableListable.saveToData(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1){}

    @Override
    public void onPageSelected(int position){
        pageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int i){}

    @Override
    public Fragmentable<? extends Paginable> getFragmentable(@NonNull Paginable paginable, int position){
        switch(paginable.getViewType()){
            case PageType.INFLATABLE:
                return Fragmentable.getInstance(new InflatableFragment(), paginable, position);
            case PageType.RECYCLABLE:
                return Fragmentable.getInstance(new RecyclableFragment(), paginable, position);
            case PageType.EXPANDABLE:
                return Fragmentable.getInstance(new ExpandableFragment(), paginable, position);
        }
        return null;
    }

    @Override
    public void onPositionChanged(int position){
        if (position == Base.INVALID_POSITION){
            if (adapter.getCount() == 0) {
                actionBar.setTitle(R.string.app_name);
            }
        }else {
            pageSelected(position);
        }
    }

    @NonNull
    @Override
    public FragmentableAdapter getFragmentableAdapter(){
        return adapter;
    }

    @NonNull
    @Override
    public ViewPager getViewPager() {
        return binding.pager;
    }

    @NonNull
    @Override
    public Listable<Paginable> getPaginableListable() {
        return paginableListable;
    }

    @Override
    public void onMakeToast(String line){
        makeToast(line);
    }

    @Override
    public void onMakeLog(String line) {
        makeLog(line);
    }

    private void pageSelected(int position){
        actionBar.setTitle(adapter.getPageTitle(position));
    }

    private void addNewPaginable(Paginable paginable){
        if (paginable != null){
            if (paginableListable.appendItem(paginable)){
                adapter.notifyDataSetChanged();
                int index = paginableListable.getItemCount() - 1;
                if (index == 0){
                    pageSelected(0);
                }else {
                    binding.pager.setCurrentItem(index);
                }
            }
        }
        binding.fam.collapse();
    }

    private Paginable getNewPaginable(int index){
        String pageTitle = getString(TITLES[index]);
        String tagName = pageTitle.toLowerCase();
        int viewType = TYPES[index];
        switch (viewType){
            case PageType.INFLATABLE:
                return new InflatablePage(pageTitle, tagName, 0, viewType);
            case PageType.RECYCLABLE:
                return new RecyclablePage(pageTitle, tagName, 0, viewType);
            case PageType.EXPANDABLE:
                return new ExpandablePage(pageTitle, tagName, 0, viewType);
        }
        return null;
    }

    private void makeToast(String line){
        Toast.makeText(this,line,Toast.LENGTH_LONG).show();
    }

    private void makeLog(String line){
        Log.d(TAG, "MTRI: " + line);
    }
}
