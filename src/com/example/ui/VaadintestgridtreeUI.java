package com.example.ui;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.example.vaadintestgridtree.GridTree;
import com.example.vaadintestgridtree.models.DataObject;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Container.Hierarchical;
import com.vaadin.data.Container.Indexed;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("vaadintestgridtree")
public class VaadintestgridtreeUI extends UI {
	List<DataObject> objects=new ArrayList<DataObject>();
	Grid grid;
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = VaadintestgridtreeUI.class, widgetset = "com.example.vaadintestgridtree.widgetset.VaadintestgridtreeWidgetset")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		initObjects();
		layout.addComponent(grid);
		
	}
	@SuppressWarnings("unchecked")
	private void initObjects() {
		objects.add(new DataObject("1", "Name", "LastName"));
		objects.add(new DataObject("2", "Name2", "LastName2"));
		objects.add(new DataObject("3", "Name3", "LastName3"));
		
		HierarchicalContainer container=new HierarchicalContainer();
		container.addContainerProperty("id", String.class, "");
		container.addContainerProperty("name", String.class, "");

		Item item0=container.addItem("0");
		item0.getItemProperty("id").setValue("id1");
		item0.getItemProperty("name").setValue("Name1");
		
		Item item01=container.addItem("01");
		item01.getItemProperty("id").setValue("id2");
		item01.getItemProperty("name").setValue("Name2");

		Item item02=container.addItem("02");
		item02.getItemProperty("id").setValue("id3");
		item02.getItemProperty("name").setValue("Name3");
		
		Item item010=container.addItem("010");
		item010.getItemProperty("id").setValue("id4");
		item010.getItemProperty("name").setValue("Name4");
		
		Item item011=container.addItem("011");
		item011.getItemProperty("id").setValue("id5");
		item011.getItemProperty("name").setValue("Name5");
		
		container.setParent("01", "0");
		container.setParent("02", "0");
		container.setParent("010", "01");
		container.setParent("011", "01");
		grid= new GridTree(container);
		grid.setColumnReorderingAllowed(true);
	}
}