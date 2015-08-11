package com.example.ui;

import java.util.Random;

import javax.servlet.annotation.WebServlet;

import com.example.vaadintestgridtree.GridTree;
import com.example.vaadintestgridtree.GridTreeContainer;
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

@SuppressWarnings("serial")
@Theme("vaadintestgridtree")
public class VaadintestgridtreeUI extends UI {
	Grid grid;
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = VaadintestgridtreeUI.class, widgetset = "com.example.vaadintestgridtree.widgetset.VaadintestgridtreeWidgetset")
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
		Label info= new Label();
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
		String value=textField.getValue();
		int nItems=DEFAULT_ITEMS;
		try {
			nItems=Integer.parseInt(value);
		} catch (NumberFormatException exc) {
			Notification.show("Wrong number", Notification.Type.ERROR_MESSAGE);
		}
		initObjects(nItems);
	}
	
	@SuppressWarnings("unchecked")
	private void initObjects(int nItems) {
//		HierarchicalContainer container=createContainer(nItems);
		HierarchicalContainer container=oldContainer();
		grid= new GridTree(new GridTreeContainer(container),"id");
		grid.setColumnReorderingAllowed(true);
		grid.getColumns().get(0).setWidth(200.5);
	}
	
	private HierarchicalContainer createContainer(int nItems) {
		final String[] names={"Billy", "Willy","Timmy","Bob","Mog","Rilley", "Ville","Bobby", "Moby", "Ben"};
		final String[] lastName={"Black","White","Anaya","Anders","Andersen","Anderson","Andrade","Andre","Andres","Andrew","Andrews"};
		final int  minIncome=1500;
		final int maxIncome=4000;
		final int nLevels=5;
		HierarchicalContainer container=new HierarchicalContainer();
		container.addContainerProperty("id", Integer.class, 0);
		container.addContainerProperty("name", String.class, "");
		container.addContainerProperty("lastName", String.class, "");
		container.addContainerProperty("income", Integer.class, 0);
		
		for(int i=0;i<nItems;i++) {
			Object itemId=""+i;
			Item item=container.addItem(itemId);
			item.getItemProperty("id").setValue(i);
			item.getItemProperty("name").setValue(getValueFromArray(names));
			item.getItemProperty("lastName").setValue(getValueFromArray(lastName));
			item.getItemProperty("income").setValue(generateIncome(minIncome,maxIncome));
			container.addItem(itemId);
		}
		Notification.show(nItems+ " created" );
		return container;
	}
	private void createHierarcy() {
		
	}
	private int generateIncome(int min,int max) {
		Random rand=new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
	private String getValueFromArray(String[] list) {
		int size=list.length;
		Random rand=new Random();
		int index=rand.nextInt(size);
		return list[index];
	}
	
	private HierarchicalContainer oldContainer() {
		HierarchicalContainer container=new HierarchicalContainer();
		container.addContainerProperty("id", String.class, "");
		container.addContainerProperty("name", String.class, "");

		Item item0=container.addItem("0");
		item0.getItemProperty("id").setValue("0");
		item0.getItemProperty("name").setValue("Name1");
		
		Item item01=container.addItem("01");
		item01.getItemProperty("id").setValue("01");
		item01.getItemProperty("name").setValue("Name2");

		Item item02=container.addItem("02");
		item02.getItemProperty("id").setValue("02");
		item02.getItemProperty("name").setValue("Name3");
		
		Item item010=container.addItem("010");
		item010.getItemProperty("id").setValue("010");
		item010.getItemProperty("name").setValue("Name4");
		
		Item item011=container.addItem("011");
		item011.getItemProperty("id").setValue("011");
		item011.getItemProperty("name").setValue("Name5");
		Item item0100 = container.addItem("0100");
		item0100.getItemProperty("id").setValue("0100");
		item0100.getItemProperty("name").setValue("Name6");
		container.setParent("01", "0");
		container.setParent("02", "0");
		container.setParent("010", "01");
		container.setParent("011", "01");
		container.setParent("0100", "010");
		return container;
	}
}