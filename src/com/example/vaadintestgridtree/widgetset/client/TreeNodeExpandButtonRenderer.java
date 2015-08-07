package com.example.vaadintestgridtree.widgetset.client;

import com.example.vaadintestgridtree.CellWrapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.vaadin.client.renderers.ClickableRenderer;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.ui.Grid;

import elemental.json.JsonObject;

public class TreeNodeExpandButtonRenderer extends ClickableRenderer<CellWrapper, HTML> {

	@Override
	public HTML createWidget() {
	       HTML html = GWT.create(HTML.class);
	       html.addClickHandler(this);
	       return html;
	}

	@Override
	public void render(RendererCellReference cell, CellWrapper cellValue, HTML widget) {
		String html="+"+cellValue.getValue();
		if(cellValue.hasChildren()) {
			if(cellValue.isExpanded()) {
				html+="v";
			} else {
				html+=">";
			}
		}
		widget.setHTML(html);
	}

}
