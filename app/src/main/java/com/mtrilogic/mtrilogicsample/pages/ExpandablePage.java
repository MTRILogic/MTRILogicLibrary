package com.mtrilogic.mtrilogicsample.pages;

import com.mtrilogic.pages.MapablePage;

@SuppressWarnings("unused")
public class ExpandablePage extends MapablePage {
    public ExpandablePage() {
        super();
    }

    public ExpandablePage(String pageTitle, String tagName, long itemId, int viewType) {
        super(pageTitle, tagName, itemId, viewType);
    }
}
