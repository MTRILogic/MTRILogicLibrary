package com.mtrilogic.mtrilogicsample.pages;

import com.mtrilogic.pages.ListablePage;

@SuppressWarnings("unused")
public class RecyclablePage extends ListablePage {
    public RecyclablePage() {
        super();
    }

    public RecyclablePage(String pageTitle, String tagName, long itemId, int viewType) {
        super(pageTitle, tagName, itemId, viewType);
    }
}
