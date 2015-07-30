package com.example.vaadintestgridtree.my;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import me.everpro.everprotreegrid.container.EverproTreeGridHierarchicalIndexedContainer;

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

	public class GridItem {

		Object itemId;
		private boolean isExpanded=false;
		
		private List<GridItem> children=new ArrayList<GridItem>();
		public GridItem(Object itemId) {
			this.itemId=itemId;
		}
		public Object getId() {
			return this.itemId;
		}
		public void setExpanded(boolean isExpanded) {
			this.isExpanded=isExpanded;
		}
		public boolean isExpanded() {
			return isExpanded;
		}
		public boolean hasChildren() {
			return !getChildren().isEmpty();
		}
		public List<GridItem> getChildren() {
			return children;
		}
		
		public void expand() {
			hierachical.getChildren(this.getId()).forEach(id->{
				children.add(new GridItem(id));
			});
		}
		public void collapse() {
			children.clear();
		}
		private GridTreeContainer getOuterType() {
			return GridTreeContainer.this;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((itemId == null) ? 0 : itemId.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			GridItem other = (GridItem) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (itemId == null) {
				if (other.itemId != null)
					return false;
			} else if (!itemId.equals(other.itemId))
				return false;
			return true;
		}
		
	}
	private List<GridItem> allItems;
	private List<GridItem> visibleItems;
	private Hierarchical hierachical;

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
	public GridTreeContainer(Hierarchical hierachical){
		this.hierachical = hierachical;
		allItems = new ArrayList<GridItem>();
		visibleItems = new ArrayList<GridItem>();
		init();
	}


	
	private void expand(Object itemId) {
		List<GridItem> tmpItems = new ArrayList<GridItem>();
		visibleItems.forEach(it -> {
			tmpItems.add(it);
			// expand item
				if (it.equals(new GridItem(itemId))) {
					it.setExpanded(true);
					hierachical.getChildren(itemId).forEach(child -> {
						tmpItems.add(new GridItem(child));
					});
				}
			});
		visibleItems = tmpItems;
		
		fireItemSetChange();
	}

	private void collapse(Object itemId) {
		List<GridItem> tmpItems = new ArrayList<GridItem>();
		visibleItems.forEach(it -> {
			Collection<?> children = hierachical.getChildren(itemId);
			// collapse item
				if (it.equals(new GridItem(itemId))) {
					it.setExpanded(false);
				}
				if (!children.contains(it.getId())) {
					tmpItems.add(it);
				}
			});
		visibleItems = tmpItems;
		fireItemSetChange();
	}
	public void toogleCollapse(Object itemId) {
		
		int index=visibleItems.indexOf(new GridItem(itemId));
		GridItem item=visibleItems.get(index);
		if(hierachical.hasChildren(itemId)) {
			if (item.isExpanded()) {
				collapse(itemId);
			}
			else {
				expand(itemId);
			}
		}
	}
	private void init() {
		//store only items of the 0 level (those which don't have parents)
		hierachical.getItemIds().forEach(it->{
			if(hierachical.getParent(it)==null) {
				allItems.add(new GridItem(it));
				visibleItems.add(new GridItem(it));
			}
		});
	}
	
	@Override
	public Object nextItemId(Object itemId) {
		int index=visibleItems.indexOf(itemId);
		if(visibleItems.size()<=index) {
			return null;
		}
		return visibleItems.get(index+1).getId();
	}

	@Override
	public Object prevItemId(Object itemId) {
		int index=visibleItems.indexOf(itemId);
		if(index<=0) {
			return null;
		}
		return visibleItems.get(index-1).getId();
	}

	@Override
	public Object firstItemId() {
		if(visibleItems.size()>0) {
			return visibleItems.get(0).getId();
		} else {
			return null;
		}
	}

	@Override
	public Object lastItemId() {
		if(visibleItems.size()>0) {
			return visibleItems.get(visibleItems.size()-1).getId();
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
            return visibleItems.contains( new GridItem(itemId));
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
		// TODO Auto-generated method stub
		return visibleItems.indexOf(new GridItem(itemId));
	}

	@Override
	public Object getIdByIndex(int index) {
		if(index>=0 && index<visibleItems.size()) {
			return visibleItems.get(index).getId();
		}
		else {
			return null;
		}
	}

	@Override
	public List<?> getItemIds(int startIndex, int numberOfItems) {
		return visibleItems.stream().map(it->it.getId()).collect(Collectors.toList());
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
