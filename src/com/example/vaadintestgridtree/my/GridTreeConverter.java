package com.example.vaadintestgridtree.my;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

public class GridTreeConverter implements Converter<String, Object> {

	static public final String CSS_EXPANDED_STYLE_NAME="v-gridtree-node-expanded";
	static public final String CSS_COLLAPSED_STYLE_NAME="v-gridtree-node-collapsed";
	@Override
	public Object convertToModel(String value,
			Class<? extends Object> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		return "";
	}
	@Override
	public String convertToPresentation(Object value,
			Class<? extends String> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		return "";
	}
	@Override
	public Class<Object> getModelType() {
		// TODO Auto-generated method stub
		return Object.class;
	}
	@Override
	public Class<String> getPresentationType() {
		// TODO Auto-generated method stub
		return String.class;
	}




}
