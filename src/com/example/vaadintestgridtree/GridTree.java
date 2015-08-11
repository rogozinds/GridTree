package com.example.vaadintestgridtree;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.example.vaadintestgridtree.gridtree.treenoderenderer.TreeNodeExpandButtonRenderer;
import com.example.vaadintestgridtree.widgetset.client.CellWrapper;
import com.vaadin.ui.Grid;

public class GridTree extends Grid {

	Column expandedColumn;
	GridTreeContainer container;
	private Hashtable<Object, CellWrapper> idToWrappers=new Hashtable<Object, CellWrapper>();
	private  String expandColumnId ;
	/**
	 * 
	 * @param container
	 * @param columnId - propertyId of the container which will have an expand button
	 */
	public GridTree(GridTreeContainer container,String columnId) {
		super();
		this.expandColumnId=columnId;
		this.container=container;
		fillContainerWithCellWrappers();
		setContainerDataSource(this.container);
		expandedColumn=getColumn(columnId);
		addExpandColumnRenderer(expandedColumn);
		reorderColumns();
	}
	private void fillContainerWithCellWrappers() {
		container.getItemIds().forEach(id->{
			String value = id.toString();
			Boolean hasChildren=container.hasChildren(id);
			Boolean isExpanded=container.isItemExpanded(id);
			Integer level=container.getLevel(id);
			CellWrapper cw=new CellWrapper(value, id, hasChildren, isExpanded,level);
			idToWrappers.put(id, cw);
		});	
		container.removeContainerProperty(expandColumnId);
		CellWrapper defValue=new CellWrapper("bar", "0", false, false,0);
		this.container.addContainerProperty(expandColumnId, CellWrapper.class, defValue);
		idToWrappers.forEach((k,v)->{
			container.getItem(k).getItemProperty(expandColumnId).setValue(v);
		});
		
	}

	private void reorderColumns() {
		List<Object> propertyIds=new ArrayList<Object>();
		propertyIds.add(expandColumnId);
		container.getContainerPropertyIds().forEach(propId->{
			if(!propId.equals(expandColumnId)) {
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
				CellWrapper cw=(CellWrapper) container.getItem(it).getItemProperty(expandColumnId).getValue();
				cw.setIsExpanded(container.isItemExpanded(it));
			});
		});
		column.setRenderer(renderer);
	}


}
