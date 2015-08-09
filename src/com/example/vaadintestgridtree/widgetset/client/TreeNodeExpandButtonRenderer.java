package com.example.vaadintestgridtree.widgetset.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.vaadin.client.renderers.ClickableRenderer;
import com.vaadin.client.widget.grid.RendererCellReference;

public class TreeNodeExpandButtonRenderer extends ClickableRenderer<String, HTML> {

	@Override
	public HTML createWidget() {
	       HTML html = GWT.create(HTML.class);
	       html.addClickHandler(this);
	       return html;
	}

	@Override
	public void render(RendererCellReference cell, String cellValue, HTML widget) {
		String[] values = cellValue.split("DELIM;1");
		String html=values[0];
		String intend=values[1];
		widget.setHTML(html);
		widget.getElement().getStyle().setProperty("padding-left", intend+"px");
	}

}
