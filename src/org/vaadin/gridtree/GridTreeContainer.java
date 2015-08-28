package org.vaadin.gridtree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vaadin.data.Container;
import com.vaadin.data.Container.Indexed;
import com.vaadin.data.Container.ItemSetChangeNotifier;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.AbstractContainer;
import com.vaadin.data.util.HierarchicalContainer;
public class GridTreeContainer extends AbstractContainer implements Indexed, ItemSetChangeNotifier,
Container.Sortable{

	
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
	/**
	 * Toogle the expand/collapse for item
	 * @param itemId
	 * @return list of changed item ids
	 */
	public List<Object> toogleCollapse(Object itemId) {
		List<Object> changedItems=new ArrayList<Object>();
		if(hierachical.hasChildren(itemId)) {
			if (isItemExpanded(itemId)) {
				changedItems=collapse(itemId);
			}
			else {
				expand(itemId);
				changedItems.add(itemId);
			}
		}
		return changedItems;
	}
	
	public boolean hasChildren(Object itemId) {
		return hierachical.hasChildren(itemId);
	}
	/**
	 * Returns level of the item starting from 0.
	 * @param itemId
	 * @return level of the item starting from 0.
	 */
	public int getLevel(Object itemId) {
		return getLevel(itemId,0);
	}
	private int getLevel(Object itemId,int levelIter) {
		Object parent=hierachical.getParent(itemId);
		if(parent==null) {
			return levelIter;
		}
		else {
			return getLevel(parent,++levelIter);
		}
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
	
	private void collapseSelfAndChildren(Object itemId,boolean removeSelf,List<Object> changedItems) {
		if (removeSelf) {
			visibleItems.remove(itemId);
		}
		if(expandedItems.remove(itemId)) {
			changedItems.add(itemId);
		}
		if(hierachical.hasChildren(itemId)) {
			hierachical.getChildren(itemId).forEach(child->{
				collapseSelfAndChildren(child,true,changedItems);
			});
		}
		
	}
	private List<Object> collapse(Object itemId) {
		List<Object> changedItems=new ArrayList<Object>();
		collapseSelfAndChildren(itemId,false,changedItems);
		fireItemSetChange();
		return changedItems;
	}
	
	private void init() {
		//store only items of the 0 level (those which don't have parents)
		hierachical.getItemIds().forEach(it->{
			if(hierachical.getParent(it)==null) {
				visibleItems.add(it);
			}
		});
	}
	
	@Override
	public void addItemSetChangeListener(Container.ItemSetChangeListener listener) {
		super.addItemSetChangeListener(listener);
	}
	
    @Override
	public void removeItemSetChangeListener(Container.ItemSetChangeListener listener) {
    	super.removeItemSetChangeListener(listener);
    }
    @Override
	@Deprecated
    public void removeListener(Container.ItemSetChangeListener listener) {
        removeItemSetChangeListener(listener);
    }
    @Override
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
		return hierachical.addContainerProperty(propertyId, type, defaultValue);
	}

	@Override
	public boolean removeContainerProperty(Object propertyId)
			throws UnsupportedOperationException {
		return hierachical.removeContainerProperty(propertyId);
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
	       if (startIndex < 0) {
	            throw new IndexOutOfBoundsException(
	                    "Start index cannot be negative! startIndex=" + startIndex);
	        }

	        if (startIndex > visibleItems.size()) {
	            throw new IndexOutOfBoundsException(
	                    "Start index exceeds container size! startIndex="
	                            + startIndex + " containerLastItemIndex="
	                            + (visibleItems.size() - 1));
	        }

	        if (numberOfItems < 1) {
	            if (numberOfItems == 0) {
	                return Collections.emptyList();
	            }

	            throw new IllegalArgumentException(
	                    "Cannot get negative amount of items! numberOfItems="
	                            + numberOfItems);
	        }

	        int endIndex = startIndex + numberOfItems;

	        if (endIndex > visibleItems.size()) {
	            endIndex = visibleItems.size();
	        }

	        return Collections.unmodifiableList(visibleItems.subList(
	                startIndex, endIndex));

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

	private void copyItem(Object itemId,HierarchicalContainer iter) {
		//implement
		//add item to container
	}
	private void depthSort(Object parentId,HierarchicalContainer iter) {
		copyItem(parentId,iter);
		Collection<?> childrenId = iter.getChildren(parentId);
//		// TODO Auto-generated method stub
//		_items[] depthSort (_parent){
//			  add _parent to _items[];
//
//			  get _children of _parent sorted by desired attribute;
//
//			  for every _item in _children
//			      depthSort(_item);
//			}	
	}
	@Override
	public void sort(Object[] propertyId, boolean[] ascending) {
		HierarchicalContainer tmp=new HierarchicalContainer();
		hierachical.getItemIds()
		List<Object> parentItemIds=null;//items of level 0
		parentItems.forEach(par->{
			depthSort(par);
		});

	}


	@Override
	public Collection<?> getSortableContainerPropertyIds() {
		//Should exclude expandProperty id
		return hierachical.getContainerPropertyIds();
	}

}
