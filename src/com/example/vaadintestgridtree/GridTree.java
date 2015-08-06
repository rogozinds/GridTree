package com.example.vaadintestgridtree;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import me.everpro.everprotreegrid.EverproTreeButtonRenderer;
import me.everpro.everprotreegrid.container.EverproTreeGridHierarchicalIndexedContainer;

import com.example.vaadintestgridtree.gridtree.treenoderenderer.TreeNodeExpandButtonRenderer;
import com.vaadin.data.Container.Indexed;
import com.vaadin.data.Property;
import com.vaadin.data.Container.Hierarchical;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Notification;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.ClickableRenderer;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickEvent;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickListener;

public class GridTree extends Grid {

	Column expandedColumn;
	GridTreeContainer container;
	public static final String EXPAND_COLUMN_ID = "COLUMN_ID";
	private void buildGridTreeContainer(Hierarchical hContainer) {
		container=new GridTreeContainer(hContainer);
	}
	public GridTree(HierarchicalContainer hContainer) {
		super();
		hContainer.addContainerProperty(GridTree.EXPAND_COLUMN_ID, String.class, "");
		buildGridTreeContainer(hContainer);
		super.setContainerDataSource(container);
		saveItemIdsInGrid();
		expandedColumn=getColumn(EXPAND_COLUMN_ID);
		expandedColumn.setConverter(new GridTreeConverter());
//		createCellGenerator();
		
//		addItemClickListener(event->{
//			if(event.getPropertyId().equals(EXPAND_COLUMN_ID)) {
//				Object itemId=event.getItemId();
//				container.toogleCollapse(itemId);
//			}
//		});
	}
	private void addExpandColumnRenderer(Column column) {
		TreeNodeExpandButtonRenderer renderer=new TreeNodeExpandButtonRenderer(Object.class);
		column.setRenderer(renderer);
	}
	private void saveItemIdsInGrid(){
		container.getItemIds().forEach(id->{
			container.getItem(id).getItemProperty(EXPAND_COLUMN_ID).setValue(id);
		});	
	}
//	private void createCellGenerator () {
//	    this.setCellStyleGenerator(generator -> {
//	    	if(EXPAND_COLUMN_ID.equals(generator.getPropertyId())) {
//	    		Object propertyId=GridTree.EXPAND_COLUMN_ID;
//	            Object itemId = (Object)generator.getItem().getItemProperty(propertyId).getValue();
//	            boolean hasChildren=container.hasChildren(itemId);
//	            boolean isExpanded=container.isItemExpanded(itemId);
//	            if(hasChildren) {
//		            if(isExpanded) {
//		                return "v-tree-grid-node "+"expanded";
//		            } else {
//		                return "v-tree-grid-node "+"collapsed";
//		            }
//	            }
//	            else {
//	            	return null;
//	            }
//	        }else {
//	            return null;
//	        }
//	    });
//	}
	@Override
	public GridTreeContainer getContainerDataSource() {
		return container;
	}
}