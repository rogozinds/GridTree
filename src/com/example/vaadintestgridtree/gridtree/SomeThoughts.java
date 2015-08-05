package com.example.vaadintestgridtree.gridtree;

public class SomeThoughts {

	// Clientside
	class TreeNodeExpandButtonRenderer extends ClickableRenderer<Boolean, HTML> {
	 
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
	 
	@Connect(server.TreeNodeExpandButtonRenderer.class)
	public class TNEBRendererConnector extends ClickableRendererConnector<Boolean> {
	 
	    @Override
	    public TNEBRenderer getRenderer() {
	        return (TNEBRenderer) super.getRenderer();
	    }
	 
	    @Override
	    protected HandlerRegistration addClickHandler(
	            RendererClickHandler<JsonObject> handler) {
	        return getRenderer().addClickHandler(handler);
	    }
	}
	 
	 
	// Server side
	class TreeNodeExpandButtonRenderer extends ClickableRenderer<Object> {
	 
	    @Override
	    public JsonValue encode(Object itemId) {
	        Hierarchical hc = ((GridTree)getParent()).getContainerDataSource();
	        return super.encode(hs.isExpanded(itemId), Boolean.class);
	    }
	}
}
