package com.example.vaadintestgridtree;

import java.util.ArrayList;
import java.util.List;

import com.example.vaadintestgridtree.gridtree.treenoderenderer.TreeNodeExpandButtonRenderer;
import com.example.vaadintestgridtree.widgetset.client.CellWrapper;
import com.vaadin.data.Container.Hierarchical;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.Grid;

public class GridTree extends Grid {

	Column expandedColumn;
	GridTreeContainer container;
	public static final String EXPAND_COLUMN_ID = "COLUMN_ID";
	private void buildGridTreeContainer(Hierarchical hContainer) {
		container=new GridTreeContainer(hContainer);
	}

	public GridTree(HierarchicalContainer hContainer) {
		super();
		CellWrapper defValue=new CellWrapper("bar", "0", false, false,0);
		hContainer.addContainerProperty(GridTree.EXPAND_COLUMN_ID, CellWrapper.class, defValue);
		buildGridTreeContainer(hContainer);
		super.setContainerDataSource(container);
		saveItemIdsInGrid();
		expandedColumn=getColumn(EXPAND_COLUMN_ID);
		expandedColumn.setHeaderCaption("");
		addExpandColumnRenderer(expandedColumn);
		reorderColumns();
	}
	private void reorderColumns() {
		List<Object> propertyIds=new ArrayList<Object>();
		propertyIds.add(EXPAND_COLUMN_ID);
		container.getContainerPropertyIds().forEach(propId->{
			if(!propId.equals(EXPAND_COLUMN_ID)) {
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
				CellWrapper cw=(CellWrapper) container.getItem(it).getItemProperty(EXPAND_COLUMN_ID).getValue();
				cw.setIsExpanded(container.isItemExpanded(it));
			});
		});
		column.setRenderer(renderer);
	}
	private void saveItemIdsInGrid(){
		container.getItemIds().forEach(id->{
			
			String value = id.toString();
			Boolean hasChildren=container.hasChildren(id);
			Boolean isExpanded=container.isItemExpanded(id);
			Integer level=container.getLevel(id);
			CellWrapper cw=new CellWrapper(value, id, hasChildren, isExpanded,level);
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
