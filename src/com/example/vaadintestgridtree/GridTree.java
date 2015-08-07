package com.example.vaadintestgridtree;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;


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
		CellWrapper defValue=new CellWrapper("bar", "0", false, false);
		hContainer.addContainerProperty(GridTree.EXPAND_COLUMN_ID, CellWrapper.class, defValue);
		buildGridTreeContainer(hContainer);
		super.setContainerDataSource(container);
		saveItemIdsInGrid();
		expandedColumn=getColumn(EXPAND_COLUMN_ID);
		expandedColumn.setConverter(new GridTreeConverter());
		addExpandColumnRenderer(expandedColumn);

	}
	private void addExpandColumnRenderer(Column column) {
		TreeNodeExpandButtonRenderer renderer=new TreeNodeExpandButtonRenderer(CellWrapper.class);
		renderer.addClickListener(e->{
			Object itemId=e.getItemId();
			container.toogleCollapse(itemId);
		});
		column.setRenderer(renderer);
	}
	private void saveItemIdsInGrid(){
		container.getItemIds().forEach(id->{
			
			String value = "foo";
			Boolean hasChildren=container.hasChildren(id);
			Boolean isExpanded=container.isItemExpanded(id);
			CellWrapper cw=new CellWrapper(value, id, hasChildren, isExpanded);
			container.getItem(id).getItemProperty(EXPAND_COLUMN_ID).setValue(cw);
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

}
