	package com.example.vaadintestgridtree.gridtree.treenoderenderer;

import com.example.vaadintestgridtree.CellWrapper;
import com.example.vaadintestgridtree.GridTree;
import com.example.vaadintestgridtree.GridTreeContainer;
import com.vaadin.data.Container.Hierarchical;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.renderers.ClickableRenderer;

import elemental.json.JsonValue;

public class TreeNodeExpandButtonRenderer extends ClickableRenderer<CellWrapper> {


    public TreeNodeExpandButtonRenderer(Class<CellWrapper> presentationType) {
		super(presentationType);
	}

	@Override
    public JsonValue encode(CellWrapper value) {
    	GridTree tree=(GridTree)getParent();
    	GridTreeContainer container = (GridTreeContainer)tree.getContainerDataSource();
        Boolean isExpanded=value.isExpanded();
        return super.encode(value, CellWrapper.class);
    }
}
