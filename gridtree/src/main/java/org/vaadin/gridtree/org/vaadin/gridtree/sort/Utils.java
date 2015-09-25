package org.vaadin.gridtree.org.vaadin.gridtree.sort;

import com.vaadin.data.Container;
import com.vaadin.data.Item;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by dmitrii on 14.9.2015.
 */
public class Utils {

    private Container.Hierarchical hierachical;
    public Utils(Container.Hierarchical container) {
        this.hierachical=container;
    }
    public void sortItems(List<Object> itemIds, final SortBy sortBy) {
        Collections.sort(itemIds, new Comparator<Object>() {
            @Override
            public int compare(Object id1, Object id2) {
                for (int i = 0; i < sortBy.getProperties().length; i++) {
                    boolean asc = sortBy.getAsc()[i];
                    int result = compareByProperty(sortBy.getProperties()[i], id1, id2, asc);
                    //if equal for one property compare for next one
                    if (result != 0) {
                        return result;
                    }
                }
                return 0;
            }
        });
    }
    public Item getItem(Object itemId) {
        return hierachical.getItem(itemId);
    }
    private int compareByProperty(Object propertyId,Object id1,Object id2,boolean asc) {
        if((getItem(id1).getItemProperty(propertyId).getValue() instanceof  Comparable)) {
            Comparable obj1 = (Comparable) getItem(id1).getItemProperty(propertyId).getValue();
            Comparable obj2 = (Comparable) getItem(id2).getItemProperty(propertyId).getValue();
            if (asc) {
                return obj1.compareTo(obj2);
            } else {
                return obj2.compareTo(obj1);
            }
        } else {
            return 0;
        }
    }
}
