package com.example.vaadintestgridtree;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import me.everpro.everprotreegrid.EverproTreeGrid;

import com.example.vaadintestgridtree.models.DataObject;
import com.example.vaadintestgridtree.my.GridTree;
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
	GridTree grid;
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = VaadintestgridtreeUI.class, widgetset = "com.example.vaadintestgridtree.widgetset.VaadintestgridtreeWidgetset")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		Button button = new Button("Click Me");
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				layout.addComponent(new Label("Thank you for clicking"));
			}
		});
		layout.addComponent(button);
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

		Item item0=container.addItem("1");
		item0.getItemProperty("id").setValue("id1");
		item0.getItemProperty("name").setValue("Name1");
		
		Item item1=container.addItem("2");
		item1.getItemProperty("id").setValue("id2");
		item1.getItemProperty("name").setValue("Name2");

		Item item2=container.addItem("3");
		item2.getItemProperty("id").setValue("id3");
		item2.getItemProperty("name").setValue("Name3");
		
	//	container.setParent("2", "1");
	//	container.setParent("3", "1");
		
		grid= new GridTree(container);
	}
}