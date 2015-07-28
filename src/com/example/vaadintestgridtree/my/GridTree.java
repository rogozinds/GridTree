package com.example.vaadintestgridtree.my;

import java.util.Locale;

import me.everpro.everprotreegrid.EverproTreeButtonRenderer;
import me.everpro.everprotreegrid.container.EverproTreeGridHierarchicalIndexedContainer;

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
	public final static String EXPAND_COLUMN_ID = "COLUMN_ID";

	private void buildGridTreeContainer(Hierarchical hContainer) {
		container=new GridTreeContainer(hContainer);
	}
	public GridTree(HierarchicalContainer hContainer) {
		super();
		hContainer.addContainerProperty(GridTree.EXPAND_COLUMN_ID, String.class, "");
		buildGridTreeContainer(hContainer);
		super.setContainerDataSource(container);
		expandedColumn = getColumn(EXPAND_COLUMN_ID);
		setTreeRendererColumn(expandedColumn);
	}

	private void setTreeRendererColumn(Column col) {
		RendererClickListener listener = new ClickableRenderer.RendererClickListener() {
			public void click(RendererClickEvent event) {
				Indexed container = getContainerDataSource();
				Notification.show("Renderer clicked");
			}
		};
		col.setRenderer(new ButtonRenderer(listener));//, new GridTreeConverter()
	}
}
