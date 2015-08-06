package com.example.vaadintestgridtree.gridtree.treenoderenderer;

import com.example.vaadintestgridtree.GridTree;
import com.example.vaadintestgridtree.GridTreeContainer;
import com.vaadin.data.Container.Hierarchical;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.renderers.ClickableRenderer;

import elemental.json.JsonValue;

public class TreeNodeExpandButtonRenderer extends ClickableRenderer<Object> {


    public TreeNodeExpandButtonRenderer(Class<Object> presentationType) {
		super(presentationType);
	}

	@Override
    public JsonValue encode(Object itemId) {
    	GridTree tree=(GridTree)getParent();
    	GridTreeContainer container = (GridTreeContainer)tree.getContainerDataSource();
        Boolean isExpanded=container.isItemExpanded(itemId);
        return super.encode(isExpanded, Boolean.class);
    }
}
