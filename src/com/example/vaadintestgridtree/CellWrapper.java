package com.example.vaadintestgridtree;

public class CellWrapper {
//put all the stuff about the cell
//and add this property to container, with a TreeGridRenderer
//Then on the client side you can get the info from it about how it should be rendered
	String value;
	Object itemId;
	Boolean hasChildren;
	Boolean isExpanded;
	
	
	
	public CellWrapper(String value,Object itemId, Boolean hasChildren, Boolean isExpanded) {
		super();
		this.value = value;
		this.itemId=itemId;
		this.hasChildren = hasChildren;
		this.isExpanded = isExpanded;
	}

	public Object getItemId() {
		return itemId;
	}

	public String getValue() {
		return value;
	}
	
	public Boolean hasChildren() {
		return hasChildren;
	}
	
	public Boolean isExpanded() {
		return isExpanded;
	}
}
