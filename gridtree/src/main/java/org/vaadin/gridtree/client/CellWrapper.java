package org.vaadin.gridtree.client;

import java.io.Serializable;

public class CellWrapper implements Serializable {
public Boolean getHasChildren() {
		return hasChildren;
	}

	public Boolean getIsExpanded() {
		return isExpanded;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setItemId(Object itemId) {
		this.itemId = itemId;
	}

public void setHasChildren(Boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public void setIsExpanded(Boolean isExpanded) {
		this.isExpanded = isExpanded;
	}

	//put all the stuff about the cell
//and add this property to container, with a TreeGridRenderer
//Then on the client side you can get the info from it about how it should be rendered
	String value;
	Object itemId;
	Boolean hasChildren;
	Boolean isExpanded;
	Integer level;

	public CellWrapper() {

	}
	public CellWrapper(String value,Object itemId, Boolean hasChildren, Boolean isExpanded,Integer level) {
		super();
		this.value = value;
		this.itemId=itemId;
		this.hasChildren = hasChildren;
		this.isExpanded = isExpanded;
		this.level=level;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
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
