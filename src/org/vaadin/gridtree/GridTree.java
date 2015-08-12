package org.vaadin.gridtree;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.vaadin.gridtree.treenoderenderer.TreeNodeExpandButtonRenderer;
import org.vaadin.gridtree.widgetset.client.CellWrapper;

import com.vaadin.data.Container.Indexed;
import com.vaadin.ui.Grid;

public class GridTree extends Grid {

	Column expandedColumn;
	GridTreeContainer container;
	private Hashtable<Object, CellWrapper> itemIdToWrappers=new Hashtable<Object, CellWrapper>();
	private  String expandColumnPropertyId ;
	/**
	 * 
	 * @param container
	 * @param expandColumnPropertyId - propertyId of the container which will have an expand button
	 */
	public GridTree(GridTreeContainer container,String expandColumnPropertyId) {
		super();
		this.expandColumnPropertyId=expandColumnPropertyId;
		this.container=container;
		fillContainerWithCellWrappers();
		super.setContainerDataSource(this.container);
		expandedColumn=getColumn(expandColumnPropertyId);
		addExpandColumnRenderer(expandedColumn);
		reorderColumns();
	}
	
	private void fillContainerWithCellWrappers() {
		itemIdToWrappers.clear();
		container.getItemIds().forEach(id->{
			String value = id.toString();
			Boolean hasChildren=container.hasChildren(id);
			Boolean isExpanded=container.isItemExpanded(id);
			Integer level=container.getLevel(id);
			CellWrapper cw=new CellWrapper(value, id, hasChildren, isExpanded,level);
			itemIdToWrappers.put(id, cw);
		});	
		container.removeContainerProperty(expandColumnPropertyId);
		CellWrapper defValue=new CellWrapper("", "0", false, false,0);
		this.container.addContainerProperty(expandColumnPropertyId, CellWrapper.class, defValue);
		itemIdToWrappers.forEach((k,v)->{
			container.getItem(k).getItemProperty(expandColumnPropertyId).setValue(v);
		});
		
	}

	@Override
	/**
	 * container has to contain expandColumnPropertyId
	 * which was specified in the constructor
	 */
	public void setContainerDataSource(Indexed container) {
		this.container=(GridTreeContainer)container;
		fillContainerWithCellWrappers();
		super.setContainerDataSource(container);
	}
	
	private void reorderColumns() {
		List<Object> propertyIds=new ArrayList<Object>();
		propertyIds.add(expandColumnPropertyId);
		container.getContainerPropertyIds().forEach(propId->{
			if(!propId.equals(expandColumnPropertyId)) {
				propertyIds.add(propId);
			}
		});
		setColumnOrder(propertyIds.toArray());
	}
	
	private void addExpandColumnRenderer(Column column) {
		TreeNodeExpandButtonRenderer renderer=new TreeNodeExpandButtonRenderer(CellWrapper.class);
		renderer.addClickListener(e->{
			Object itemId=e.getItemId();
			List<Object>changedItems=container.toogleCollapse(itemId);
			changedItems.forEach(it->{
				CellWrapper cw=(CellWrapper) container.getItem(it).getItemProperty(expandColumnPropertyId).getValue();
				cw.setIsExpanded(container.isItemExpanded(it));
			});
		});
		column.setRenderer(renderer);
	}


}
