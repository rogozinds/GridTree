package org.vaadin.gridtree;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import com.vaadin.data.Container.Indexed;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickEvent;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickListener;

import org.vaadin.gridtree.client.CellWrapper;
import org.vaadin.gridtree.treenoderenderer.TreeNodeExpandButtonRenderer;

public class GridTree extends Grid {

	Column expandedColumn;
	GridTreeContainer container;
	private final Hashtable<Object, CellWrapper> itemIdToWrappers=new Hashtable<Object, CellWrapper>();
	private final  String expandColumnPropertyId ;
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
		for(final Object id:container.getItemIds()) {
			final String value = id.toString();
			final Boolean hasChildren=container.hasChildren(id);
			final Boolean isExpanded=container.isItemExpanded(id);
			final Integer level=container.getLevel(id);
			final CellWrapper cw=new CellWrapper(value, id, hasChildren, isExpanded,level);
			itemIdToWrappers.put(id, cw);
		};
		container.removeContainerProperty(expandColumnPropertyId);
		final CellWrapper defValue=new CellWrapper("", "0", false, false,0);
		this.container.addContainerProperty(expandColumnPropertyId, CellWrapper.class, defValue);
		for(final Entry<Object, CellWrapper> pair:itemIdToWrappers.entrySet()){
			container.getItem(pair.getKey()).getItemProperty(expandColumnPropertyId).setValue(pair.getValue());
		};
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
		final List<Object> propertyIds=new ArrayList<Object>();
		propertyIds.add(expandColumnPropertyId);
		for(final Object propId:container.getContainerPropertyIds()) {
			if(!propId.equals(expandColumnPropertyId)) {
				propertyIds.add(propId);
			}
		};
		setColumnOrder(propertyIds.toArray());
	}

	private void addExpandColumnRenderer(Column column) {
		final TreeNodeExpandButtonRenderer renderer=new TreeNodeExpandButtonRenderer(CellWrapper.class);
		renderer.addClickListener(new RendererClickListener() {
			@Override
			public void click(RendererClickEvent event) {
				final Object itemId=event.getItemId();
				final List<Object>changedItems=container.toogleCollapse(itemId);
				for(final Object it:changedItems) {
					final CellWrapper cw=(CellWrapper) container.getItem(it).getItemProperty(expandColumnPropertyId).getValue();
					cw.setIsExpanded(container.isItemExpanded(it));
				};
			}
		});
		column.setRenderer(renderer);
	}


}
