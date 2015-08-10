	package com.example.vaadintestgridtree.gridtree.treenoderenderer;

import com.example.vaadintestgridtree.widgetset.client.CellWrapper;
import com.vaadin.ui.renderers.ClickableRenderer;

import elemental.json.JsonValue;

public class TreeNodeExpandButtonRenderer extends ClickableRenderer<CellWrapper> {


    public TreeNodeExpandButtonRenderer(Class<CellWrapper> presentationType) {
		super(presentationType);
	}

	@Override
    public JsonValue encode(CellWrapper value) {
        return super.encode(value, CellWrapper.class);
    }
}
