package com.example.vaadintestgridtree.widgetset.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.vaadin.client.renderers.ClickableRenderer;
import com.vaadin.client.widget.grid.RendererCellReference;

import elemental.json.JsonObject;

public class TreeNodeExpandButtonRenderer extends ClickableRenderer<Boolean, HTML> {

	@Override
	public HTML createWidget() {
	       HTML html = GWT.create(HTML.class);
	       html.addClickHandler(this);
	       return html;
	}

	@Override
	public void render(RendererCellReference cell, Boolean expanded, HTML widget) {
		// get row and col from client side
       // String rowKey = getRowKey((JsonObject) cell.getRow());
       // String columnId = getColumnId(cell.getColumn());
	   //	int int=cell.getRowIndex();
		widget.setHTML(expanded ? "v" : ">");
	}

}
