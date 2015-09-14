package org.vaadin.gridtree.org.vaadin.gridtree.sort;

/**
 * Created by erogdmi on 9/14/2015.
 */
public class SortBy {
    Object[] properties;
    boolean[] asc;

    public SortBy(Object[] properties,boolean[] asc) {
        this.properties = properties;
        this.asc=asc;
    }

    public Object[] getProperties() {
        return properties;
    }

    public boolean[] getAsc() {
        return asc;
    }
}
