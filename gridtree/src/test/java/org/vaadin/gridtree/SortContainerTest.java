package org.vaadin.gridtree;

import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class SortContainerTest {
	GridTreeContainer tc=null;

	/**
	 * Create container with such structure:
	 * -0 i1
	 * 	-01 id5
	 * 	 	-010 id7
	 * 		-011 id3
	 * 	-02 id2
	 */
	@Before
	public void createContainer() {
		final HierarchicalContainer container=new HierarchicalContainer();
		container.addContainerProperty("id", String.class, "");

		final Item item0=container.addItem("0");
		item0.getItemProperty("id").setValue("id1");

		final Item item01=container.addItem("01");
		item01.getItemProperty("id").setValue("id5");

		final Item item02=container.addItem("02");
		item02.getItemProperty("id").setValue("id2");

		final Item item010=container.addItem("010");
		item010.getItemProperty("id").setValue("id7");

		final Item item011=container.addItem("011");
		item011.getItemProperty("id").setValue("id3");

		container.setParent("01", "0");
		container.setParent("02", "0");
		container.setParent("010", "01");
		container.setParent("011", "01");

		tc= new GridTreeContainer(container);
		//By default all items are collapsed
		Assert.assertEquals(1, tc.size());
	}

	/**
	 * Sorted values should be in such order
	 * -0 i1
	 * 	-02 id2
	 * 	-01 id5
	 * 	 	-011 id3
	 * 	 	-010 id7
	 *
	 *
	 *
	 */
	@Test
	public void testSort() {
		boolean[] sortArray={true};
		Object[] propertyIds={"id"};
		tc.sort(propertyIds,sortArray);
		tc.toogleCollapse("0");
		tc.toogleCollapse("01");
		Assert.assertEquals(5,tc.size());
		Assert.assertEquals("0", tc.getIdByIndex(0));
		Assert.assertEquals("02", tc.getIdByIndex(1));
		Assert.assertEquals("01", tc.getIdByIndex(2));
		Assert.assertEquals("011", tc.getIdByIndex(3));
		Assert.assertEquals("010", tc.getIdByIndex(4));

	}
}
