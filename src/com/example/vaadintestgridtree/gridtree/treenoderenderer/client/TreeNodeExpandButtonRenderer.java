package com.example.vaadintestgridtree.gridtree.treenoderenderer.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.vaadin.client.renderers.ClickableRenderer;
import com.vaadin.client.widget.grid.RendererCellReference;

public class TreeNodeExpandButtonRenderer extends ClickableRenderer<Boolean, HTML> {

	@Override
	public HTML createWidget() {
	       HTML html = GWT.create(HTML.class);
	       html.addClickHandler(this);
	       return html;
	}

	@Override
	public void render(RendererCellReference cell, Boolean expanded, HTML widget) {
		widget.setHTML(expanded ? "v" : ">");
	}

}
