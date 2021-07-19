package com.mtrilogic.pages;

import android.os.Bundle;

import com.mtrilogic.abstracts.Paginable;
import com.mtrilogic.abstracts.PaginableCreator;

@SuppressWarnings("unused")
public class Page extends Paginable {
    public static final Creator<Page> CREATOR = new PaginableCreator<Page>() {
        @Override
        protected Page createFromData(Bundle data) {
            return new Page(data);
        }

        @Override
        public Page[] newArray(int size) {
            return new Page[size];
        }
    };

    public Page() {
        super();
    }

    public Page(String pageTitle, String tagName, long itemId, int viewType) {
        super(pageTitle, tagName, itemId, viewType);
    }

    protected Page(Bundle data) {
        super(data);
    }
}
