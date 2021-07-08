package com.mtrilogic.abstracts;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("unused")
public abstract class ModelableCreator<M extends Modelable> implements Parcelable.ClassLoaderCreator<M>{

// ++++++++++++++++| PUBLIC ABSTRACT METHODS |++++++++++++++++++++++++++++++++++++++++++++++++++++++

    protected abstract M createFromData(Bundle data);

// ++++++++++++++++| PUBLIC CONSTRUCTORS |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    public final M createFromParcel(Parcel src, ClassLoader loader){
        Bundle data;
        if (src == null || loader == null || (data = src.readBundle(loader)) == null){
            data = new Bundle();
        }
        return createFromData(data);
    }

    @Override
    public final M createFromParcel(Parcel src){
        return createFromParcel(src, null);
    }
}
