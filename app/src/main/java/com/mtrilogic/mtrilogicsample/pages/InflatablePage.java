package com.mtrilogic.mtrilogicsample.pages;

import com.mtrilogic.pages.ListablePage;

@SuppressWarnings("unused")
public class InflatablePage extends ListablePage {
    public InflatablePage() {
        super();
    }

    public InflatablePage(String pageTitle, String tagName, long itemId, int viewType) {
        super(pageTitle, tagName, itemId, viewType);
    }
}
