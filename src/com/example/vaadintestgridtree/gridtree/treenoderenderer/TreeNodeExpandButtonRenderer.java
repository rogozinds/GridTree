	package com.example.vaadintestgridtree.gridtree.treenoderenderer;

import com.example.vaadintestgridtree.widgetset.shared.CellWrapper;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.renderers.ClickableRenderer;

import elemental.json.JsonValue;

public class TreeNodeExpandButtonRenderer extends ClickableRenderer<CellWrapper> {


    public TreeNodeExpandButtonRenderer(Class<CellWrapper> presentationType) {
		super(presentationType);
	}

	@Override
    public JsonValue encode(CellWrapper value) {
    	String cellValue=value.getValue();
    	String caretDown=FontAwesome.CARET_DOWN.getHtml();
    	String caretRight=FontAwesome.CARET_RIGHT.getHtml();
    	if(value.hasChildren()) {
    		if(value.isExpanded()){
    			cellValue=caretDown+" "+value.getValue();
    		}
    		else {
    			cellValue=caretRight+" "+value.getValue();
    		}
    	}
    	final int INTEND = 19;
		cellValue=cellValue+"DELIM;1"+INTEND*value.getLevel();
        return super.encode(cellValue, String.class);
    }
}
