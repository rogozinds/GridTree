package com.example.vaadintestgridtree.my;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

public class GridTreeConverter implements Converter<String, String> {

	static public final String CSS_EXPANDED_STYLE_NAME="v-gridtree-node-expanded";
	static public final String CSS_COLLAPSED_STYLE_NAME="v-gridtree-node-collapsed";
	GridTreeContainer container;
	Object itemId;
	public GridTreeConverter(GridTreeContainer container,Object itemId) {
		this.container=container;
		this.itemId=itemId;
	}
	@Override
	public String convertToModel(String value,
			Class<? extends String> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		//We won't convert to model;
		return "";
	}

	@Override
	public String convertToPresentation(String value,
			Class<? extends String> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {

		String styleName;
		if(container.hasChildren(itemId)){
			if(container.isItemExpanded(itemId)){
				styleName = CSS_EXPANDED_STYLE_NAME;
			}else{
				styleName = CSS_COLLAPSED_STYLE_NAME;
			}
		}
		return "";
	}

	@Override
	public Class<String> getModelType() {
		return String.class;
	}

	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}

}
