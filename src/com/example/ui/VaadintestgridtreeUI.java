package com.example.ui;

import java.util.Random;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import org.vaadin.gridtree.GridTree;
import org.vaadin.gridtree.GridTreeContainer;

@SuppressWarnings("serial")
@Theme("gridtree")
public class VaadintestgridtreeUI extends UI {
	Grid grid;
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = VaadintestgridtreeUI.class, widgetset = "org.vaadin.gridtree.widgetset.VaadintestgridtreeWidgetset")
	public static class Servlet extends VaadinServlet {
	}

	private final int DEFAULT_ITEMS=100;
	private Button addButton;
	private TextField textField;
	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		final HorizontalLayout buttonsLayout=new HorizontalLayout();
		buttonsLayout.setSpacing(true);
		layout.setMargin(true);
		layout.setSpacing(true);
		setContent(layout);
		buttonsLayout.setHeight("50px");
		buttonsLayout.setWidth("600px");
		final Label info= new Label();
		info.setValue("Specify number of items");
		textField=new TextField();
		textField.setValue(""+DEFAULT_ITEMS);

		addButton=new Button("Generate",e->{
			addItemsClick();
		});

		initObjects(DEFAULT_ITEMS);
		layout.addComponent(buttonsLayout);
		buttonsLayout.addComponent(info);
		buttonsLayout.addComponent(textField);
		buttonsLayout.addComponent(addButton);
		grid.setSizeFull();
		layout.addComponent(grid);

	}
	private void addItemsClick() {
		final String value=textField.getValue();
		int nItems=DEFAULT_ITEMS;
		try {
			nItems=Integer.parseInt(value);
		} catch (final NumberFormatException exc) {
			Notification.show("Wrong number", Notification.Type.ERROR_MESSAGE);
		}
		changeContainer(nItems);
	}

	@SuppressWarnings("unchecked")
	private void initObjects(int nItems) {
		final HierarchicalContainer container=createContainer(nItems);
		grid= new GridTree(new GridTreeContainer(container),"id");
		grid.setColumnReorderingAllowed(true);
		grid.getColumns().get(0).setWidth(200.5);
	}
	private void changeContainer(int nItems) {
		final HierarchicalContainer container=createContainer(nItems);
		final GridTreeContainer ct=new GridTreeContainer(container);
		grid.setContainerDataSource(ct);
	}
	private HierarchicalContainer createContainer(int nItems) {
		if(nItems<30) {
			nItems=30;
		}
		final String[] names={"Billy", "Willy","Timmy","Bob","Mog","Rilley", "Ville","Bobby", "Moby", "Ben"};
		final String[] lastName={"Black","White","Anaya","Anders","Andersen","Anderson","Andrade","Andre","Andres","Andrew","Andrews"};
		final int  minIncome=1500;
		final int maxIncome=4000;

		final HierarchicalContainer container=new HierarchicalContainer();
		container.addContainerProperty("id", Integer.class, 0);
		container.addContainerProperty("name", String.class, "");
		container.addContainerProperty("lastName", String.class, "");
		container.addContainerProperty("income", Integer.class, 0);

		for(int i=0;i<nItems;i++) {
			final Object itemId=""+i;
			final Item item=container.addItem(itemId);
			item.getItemProperty("id").setValue(i);
			item.getItemProperty("name").setValue(getValueFromArray(names));
			item.getItemProperty("lastName").setValue(getValueFromArray(lastName));
			item.getItemProperty("income").setValue(generateIncome(minIncome,maxIncome));
			container.addItem(itemId);
		}
		createHierarcy(container,nItems);
		Notification.show(nItems+ " created" );
		return container;
	}
	private void addParent(HierarchicalContainer container,String item,String parent) {
		if(container.getItem(item)!=null) {
			if(container.getItem(parent)!=null) {
				container.setParent(item,parent);
			}
		}
	}
	private void createHierarcy(HierarchicalContainer container,int nItems) {
		final int nLevels=5;
		for(int i=0;i<nItems;i++) {
			final String itemId=""+i;
			if((i%nLevels)==0) {

			}
			else {
				final String parent=(i-1)+"";
				addParent(container,itemId,(i-1)+"");
			}
		}
	}
	private int generateIncome(int min,int max) {
		final Random rand=new Random();
		final int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
	private String getValueFromArray(String[] list) {
		final int size=list.length;
		final Random rand=new Random();
		final int index=rand.nextInt(size);
		return list[index];
	}
}