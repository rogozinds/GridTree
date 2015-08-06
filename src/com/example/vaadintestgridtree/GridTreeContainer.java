package com.example.vaadintestgridtree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.vaadin.data.Container.Hierarchical;
import com.vaadin.data.Container.Indexed;
import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.data.util.AbstractContainer;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Container.*;
public class GridTreeContainer extends AbstractContainer implements Indexed, ItemSetChangeNotifier {

	
	private List<Object> visibleItems;
	private Set<Object> expandedItems;//all items are collapsed by default
	private Hierarchical hierachical;

	public GridTreeContainer(Hierarchical hierachical){
		this.hierachical = hierachical;
		visibleItems = new ArrayList<Object>();
		expandedItems= new HashSet<Object>();
		init();
	}


	// This should be package visibility - for now public, not to loose time for solving visibility problems.
	public void toogleCollapse(Object itemId) {
		if(hierachical.hasChildren(itemId)) {
			if (isItemExpanded(itemId)) {
				collapse(itemId);
			}
			else {
				expand(itemId);
			}
		}
	}
	
	public boolean hasChildren(Object itemId) {
		return hierachical.hasChildren(itemId);
	}
	/**
	 * 
	 * @param itemId
	 * @return true if item is expanded, false otherwise. Returns false also
	 * if item doesn't have children.
	 */
	public boolean isItemExpanded(Object itemId) {
		return expandedItems.contains(itemId);
	}
	//Below this line internal stuff :)
	//***************************************************************************
	private void expand(Object itemId) {
		List<Object> tmpItems = new ArrayList<Object>();
		visibleItems.forEach(it -> {
			tmpItems.add(it);
			// expand item
				if (it.equals(itemId)) {
					expandedItems.add(it);
					hierachical.getChildren(itemId).forEach(child -> {
						tmpItems.add(child);
					});
				}
			});
		visibleItems = tmpItems;
		fireItemSetChange();
	}
	
	private void collapse(Object itemId) {
		List<Object> tmpItems = new ArrayList<Object>();
		visibleItems.forEach(it -> {
			Collection<?> children = hierachical.getChildren(itemId);
			// collapse item
				if (it.equals(itemId)) {
					expandedItems.remove(it);
				}
				if (!children.contains(it)) {
					tmpItems.add(it);
				}
			});
		visibleItems = tmpItems;
		fireItemSetChange();
	}
	
	private void init() {
		//store only items of the 0 level (those which don't have parents)
		hierachical.getItemIds().forEach(it->{
			if(hierachical.getParent(it)==null) {
				visibleItems.add(it);
			}
		});
	}
	
	public void addItemSetChangeListener(Container.ItemSetChangeListener listener) {
		super.addItemSetChangeListener(listener);
	}
	
    public void removeItemSetChangeListener(Container.ItemSetChangeListener listener) {
    	super.removeItemSetChangeListener(listener);
    }
    @Deprecated
    public void removeListener(Container.ItemSetChangeListener listener) {
        removeItemSetChangeListener(listener);
    }
    @Deprecated
    public void addListener(Container.ItemSetChangeListener listener) {
        addItemSetChangeListener(listener);
    }
	
	
	@Override
	public Object nextItemId(Object itemId) {
		int index=visibleItems.indexOf(itemId);
		if(visibleItems.size()<=index) {
			return null;
		}
		return visibleItems.get(index+1);
	}

	@Override
	public Object prevItemId(Object itemId) {
		int index=visibleItems.indexOf(itemId);
		if(index<=0) {
			return null;
		}
		return visibleItems.get(index-1);
	}

	@Override
	public Object firstItemId() {
		if(visibleItems.size()>0) {
			return visibleItems.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Object lastItemId() {
		if(visibleItems.size()>0) {
			return visibleItems.get(visibleItems.size()-1);
		} else {
			return null;
		}
	}

	@Override
	public boolean isFirstId(Object itemId) {
		return firstItemId().equals(itemId);
	}

	@Override
	public boolean isLastId(Object itemId) {
		return lastItemId().equals(itemId);
	}

	@Override
	public Object addItemAfter(Object previousItemId)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		 throw new UnsupportedOperationException();
	}

	@Override
	public Item addItemAfter(Object previousItemId, Object newItemId)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		 throw new UnsupportedOperationException();
	}

	@Override
	public Item getItem(Object itemId) {
		return hierachical.getItem(itemId);
	}

	@Override
	public Collection<?> getContainerPropertyIds() {
		return hierachical.getContainerPropertyIds();
	}

	@Override
	public Collection<?> getItemIds() {
		return hierachical.getItemIds();
	}

	@Override
	public Property getContainerProperty(Object itemId, Object propertyId) {
		return hierachical.getContainerProperty(itemId, propertyId);
	}

	@Override
	public Class<?> getType(Object propertyId) {
		return hierachical.getType(propertyId);
	}

	@Override
	public int size() {
		return visibleItems.size();
	}

	@Override
	public boolean containsId(Object itemId) {
        if (itemId == null) {
            return false;
        } else {
            return visibleItems.contains(itemId);
        }
	}

	@Override
	public Item addItem(Object itemId) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Object addItem() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeItem(Object itemId)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addContainerProperty(Object propertyId, Class<?> type,
			Object defaultValue) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeContainerProperty(Object propertyId)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAllItems() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public int indexOfId(Object itemId) {
		return visibleItems.indexOf(itemId);
	}

	@Override
	public Object getIdByIndex(int index) {
		if(index>=0 && index<visibleItems.size()) {
			return visibleItems.get(index);
		}
		else {
			return null;
		}
	}

	@Override
	public List<?> getItemIds(int startIndex, int numberOfItems) {
		return visibleItems;
	}

	@Override
	public Object addItemAt(int index) throws UnsupportedOperationException {
		return new UnsupportedOperationException();
	}

	@Override
	public Item addItemAt(int index, Object newItemId)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
