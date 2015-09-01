package com.example.vaadintestgridtree;

import java.util.Arrays;
import java.util.List;

import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.vaadin.gridtree.GridTreeContainer;

public class ContainerTest {
	GridTreeContainer tc=null;

	/**
	 * Create container with such structure:
	 * -0
	 * 	-01
	 * 	 	-010
	 * 		-011
	 * 	-02
	 */
	@Before
	public void createContainer() {
		final HierarchicalContainer container=new HierarchicalContainer();
		container.addContainerProperty("id", String.class, "");

		final Item item0=container.addItem("0");
		item0.getItemProperty("id").setValue("id1");

		final Item item01=container.addItem("01");
		item01.getItemProperty("id").setValue("id2");

		final Item item02=container.addItem("02");
		item02.getItemProperty("id").setValue("id3");

		final Item item010=container.addItem("010");
		item010.getItemProperty("id").setValue("id4");

		final Item item011=container.addItem("011");
		item011.getItemProperty("id").setValue("id5");

		container.setParent("01", "0");
		container.setParent("02", "0");
		container.setParent("010", "01");
		container.setParent("011", "01");

		tc= new GridTreeContainer(container);
		//By default all items are collapsed
		Assert.assertEquals(1, tc.size());
	}

	@Test
	public void testExpand() {
		tc.toogleCollapse("0");
		Assert.assertEquals(3,tc.size());
	}
	@Test
	public void testToogleCollapse() {

		tc.toogleCollapse("0");
		Assert.assertEquals(3, tc.size());

		tc.toogleCollapse("01");
		//All 5 items are shown
		Assert.assertEquals(5, tc.size());

		tc.toogleCollapse("0");
		//collapseing top parent row should have collapse all its children
		Assert.assertEquals(1, tc.size());
	}

	@Test
	public void testToogleCollapseTwoTimes() {

		tc.toogleCollapse("0");
		tc.toogleCollapse("01");
		tc.toogleCollapse("0");
		tc.toogleCollapse("0");
		tc.toogleCollapse("01");
		//collapsing top parent row should have collapse all its children
		Assert.assertEquals(5, tc.size());
	}
	//Test toogleCollapse method return changed item ids
	@Test
	public void testToogleCollapseReturnValue() {
		tc.toogleCollapse("0");
		tc.toogleCollapse("01");
		final List<Object> changedItems=tc.toogleCollapse("0");
		final List<Object> expectedItemsChanged=(List<Object>)(List<?>) Arrays.asList("0","01");
		Assert.assertEquals("Items changed",expectedItemsChanged,changedItems);
	}
	@Test
	public void testGetLevel() {
		Assert.assertEquals(0,tc.getLevel("0"));
		Assert.assertEquals(1,tc.getLevel("01"));
		Assert.assertEquals(2,tc.getLevel("010"));
	}
}
