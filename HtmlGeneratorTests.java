package com.bcbsnc.cody;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.TreeMap;

import org.junit.Test;

public class HtmlGeneratorTests
{
	@Test
	public void test1ElementConstructor()
	{
		Element test = new Element("test");

		assertEquals("<test></test>", test.getHTML());
	}

	@Test
	public void test2AddSomeAttribute()
	{
		Element test = new Element("test");

		test.addAttribute("testAttribute", "this is my value");

		assertEquals("<test testAttribute=\"thisismyvalue\"></test>", test
				.getHTML());
	}

	@Test
	public void test3RemoveSomeAttribute()
	{
		Element test = new Element("test");

		test.addAttribute("testAttribute", "this is my value");

		test.removeAttribute("testAttribute");

		assertEquals("<test></test>", test.getHTML());
	}

	@Test
	public void test4GetAttributeHashMap()
	{
		Element test = new Element("test");

		test.addAttribute("testAttribute", "this is my value");

		test.addAttribute("testAttribute2", "this is my 2nd value");

		test.addAttribute("testAttribute3", "this is my 3rd value");

		test.removeAttribute("testAttribute2");

		TreeMap<String, String> attributes = test.getAttributes();

		assertEquals(2, attributes.size());

		assertEquals("<test testAttribute=\"thisismyvalue\" "
				+ "testAttribute3=\"thisismy3rdvalue\"></test>", test.getHTML());
	}

	@Test
	public void test5AddInnerElement()
	{
		Element containerElement = new Element("container");

		containerElement.addInnerElement(new Element("inner"));

		assertEquals("<container><inner></inner></container>", containerElement
				.getHTML());
	}

	@Test
	public void test6GetInnerTagByIndex()
	{
		Element containerElement = new Element("container");

		containerElement.addInnerElement(new Element("inner"));
		containerElement.addInnerElement(new Element("inner2"));
		containerElement.addInnerElement(new Element("inner3"));

		assertEquals("<container><inner></inner><inner2></inner2><inner3>"
				+ "</inner3></container>", containerElement.getHTML());

		assertEquals("inner", containerElement.getInnerElement(0).getTagName());
		assertEquals("inner2", containerElement.getInnerElement(1).getTagName());
		assertEquals("inner3", containerElement.getInnerElement(2).getTagName());
	}

	@Test
	public void test7GetInnerTags()
	{
		// make a container
		Element containerElement = new Element("container");

		// make an array that will contain the elements we are testing for
		ArrayList<Element> expected = new ArrayList<Element>();

		// make the elements
		Element inner = new Element("inner");
		Element inner2 = new Element("inner2");
		Element inner3 = new Element("inner3");

		// stick them in our expected array
		expected.add(inner);
		expected.add(inner2);
		expected.add(inner3);

		// actually add them to our container element
		containerElement.addInnerElement(inner);
		containerElement.addInnerElement(inner2);
		containerElement.addInnerElement(inner3);

		// grab the actual values
		ArrayList<Element> actual = containerElement.getInnerElements();

		assertEquals(expected, actual);
	}

	@Test
	public void test8RemoveInnerTag()
	{
		// make a container
		Element containerElement = new Element("container");

		// make an array that will contain the elements we are testing for
		ArrayList<Element> expected = new ArrayList<Element>();

		// make the elements
		Element inner = new Element("inner");
		Element inner2 = new Element("inner2");
		Element inner3 = new Element("inner3");

		// stick them in our expected array
		expected.add(inner);
		expected.add(inner3);

		// actually add them to our container element
		containerElement.addInnerElement(inner);
		containerElement.addInnerElement(inner2);
		containerElement.addInnerElement(inner3);

		// now remove the second inner
		containerElement.removeInnerElement(1);

		// grab the actual values
		ArrayList<Element> actual = containerElement.getInnerElements();

		assertEquals(expected, actual);
	}

	@Test
	public void test9SetInnerText()
	{
		Element div = new Element("div");

		div.setInnerText("this is a div");

		assertEquals("<div>this is a div</div>", div.getHTML());
	}

	// opted to force alphabetical order when there are multiple classes because
	// that way the user doesnt have to keep up with when they added a certain
	// class they will just look up the list and then find its index
	@Test
	public void test10TestClassStuff()
	{
		Element div = new Element("div");

		div.addClass("class1");
		div.addClass("class3");
		div.addClass("class2");

		// this one should not get added because no duplicates
		div.addClass("class1");

		assertEquals("<div class=\"class1 class2 class3\"></div>", div
				.getHTML());

		div.removeClass("class2");

		assertEquals("<div class=\"class1 class3\"></div>", div.getHTML());
	}

	// @Test
	// public void test11TestIdStuff()
	// {
	// fail("not yet implemented");
	// }

	@Test
	public void test12AddDataAttribute()
	{
		Element div = new Element("div");

		div.addDataAttribute("test", "value");

		assertEquals("<div data-test=\"value\"></div>", div.getHTML());
	}

	@Test
	public void test13RemoveDataAttributes()
	{
		Element div = new Element("div");

		div.addDataAttribute("test1", "value1");

		div.addDataAttribute("test2", "value2");

		div.addDataAttribute("test3", "value3");

		div.removeDataAttribute("test2");

		assertEquals("<div data-test1=\"value1\" data-test3=\"value3\"></div>",
				div.getHTML());
	}

	@Test
	public void test14AddStyle()
	{
		Element div = new Element("div");

		div.addStyle("test1", "value1");

		assertEquals("<div style=\"test1:value1;\"></div>", div.getHTML());
	}

	@Test
	public void test15RemoveStyle()
	{
		Element div = new Element("div");

		div.addStyle("test1", "value1");

		div.addStyle("test2", "value2");

		div.addStyle("test3", "value3");

		div.removeStyle("test2");

		assertEquals("<div style=\"test1:value1; test3:value3;\"></div>", div
				.getHTML());
	}

	// NOTE inner notes TL;DR the only things that arent in alphabetical order
	// are the inner elements because it makes more sense for them to be in the
	// order that the user specifies
	@Test
	public void test16EnsurePrintOrderUsingAllAttributeTypes()
	{
		// make a div
		Element div = new Element("div");

		// add a couple of spans to it noting that the spans should appear in
		// the html in the order we are specifying here
		Element span = new Element("span");
		span.setInnerText("span 1");
		div.addInnerElement(span);

		Element span2 = new Element("span");
		span2.setInnerText("span 2");
		div.addInnerElement(span2);

		// add some innerText to it
		div.setInnerText("sample inner text");

		// add a couple of classes to it noting that order should come out
		// alphabetical
		div.addClass("ZZFirstClassAdded");
		div.addClass("AASecondClassAdded");

		// attributes note that these should appear in alphabetical order
		div.addAttribute("ZZotherAttribute1", "value");
		div.addAttribute("AAotherAttribute2", "value");

		// data attributes note these should be alphabetical
		div.addDataAttribute("ZZdataAttribute1", "value");
		div.addDataAttribute("AAdataAttribute2", "value");

		// styles note these should be alphabetical
		div.addStyle("ZZstyle1", "value");
		div.addStyle("AAstyle2", "value");

		assertEquals("<div class=\"AASecondClassAdded ZZFirstClassAdded\" "
				+ "style=\"AAstyle2:value; ZZstyle1:value;\" "
				+ "data-AAdataAttribute2=\"value\" "
				+ "data-ZZdataAttribute1=\"value\" "
				+ "AAotherAttribute2=\"value\" ZZotherAttribute1=\"value\">"
				+ "sample inner text<span>span 1</span><span>span 2</span>"
				+ "</div>", div.getHTML());
	}

	@Test
	public void test17TableClassConstructor()
	{
		TableElement test = new TableElement();

		assertEquals("<table></table>", test.getHTML());
	}

	@Test
	public void test18AddRowsToTableClass()
	{
		TableElement test = new TableElement();

		Element row1 = test.addRow();
		test.addRow();
		row1.addInnerElement("td");

		assertEquals("<table><tr><td></td></tr><tr></tr></table>", test
				.getHTML());
	}

	@Test
	public void test19MakeTableFrom2DArrayListOfData()
	{
		ArrayList<ArrayList<String>> testData = new ArrayList<ArrayList<String>>();

		for(int i = 0; i < 3; i++)
		{
			ArrayList<String> testRow = new ArrayList<String>();

			for(int j = 0; j < 3; j++)
			{
				testRow.add(new Integer(j).toString());
			}

			testData.add(testRow);
		}

		TableElement table = new TableElement(testData);

		assertEquals(
				"<table><tr><td>0</td><td>1</td><td>2</td></tr><tr><td>0</td>"
						+ "<td>1</td><td>2</td></tr><tr><td>0</td><td>1</td>"
						+ "<td>2</td></tr></table>", table.getHTML());
	}

	@Test
	public void test20AlterColumnOfTags()
	{
		ArrayList<ArrayList<String>> testData = new ArrayList<ArrayList<String>>();

		for(int i = 0; i < 3; i++)
		{
			ArrayList<String> testRow = new ArrayList<String>();

			for(int j = 0; j < 3; j++)
			{
				testRow.add(new Integer(j).toString());
			}

			testData.add(testRow);
		}

		TableElement table = new TableElement(testData);

		assertEquals(
				"<table><tr><td>0</td><td>1</td><td>2</td></tr><tr><td>0</td>"
						+ "<td>1</td><td>2</td></tr><tr><td>0</td><td>1</td>"
						+ "<td>2</td></tr></table>", table.getHTML());
		
		ArrayList<Element> columnOfElements = table.getColumn(0);
		
		for(Element x : columnOfElements)
		{
			x.setTagName("th");
		}
		
		assertEquals(
				"<table><tr><th>0</th><td>1</td><td>2</td></tr><tr><th>0</th>"
						+ "<td>1</td><td>2</td></tr><tr><th>0</th><td>1</td>"
						+ "<td>2</td></tr></table>", table.getHTML());
	}

	@Test
	public void test21AlterRowOfTags()
	{
		ArrayList<ArrayList<String>> testData = new ArrayList<ArrayList<String>>();

		for(int i = 0; i < 3; i++)
		{
			ArrayList<String> testRow = new ArrayList<String>();

			for(int j = 0; j < 3; j++)
			{
				testRow.add(new Integer(j).toString());
			}

			testData.add(testRow);
		}

		TableElement table = new TableElement(testData);

		assertEquals(
				"<table><tr><td>0</td><td>1</td><td>2</td></tr><tr><td>0</td>"
						+ "<td>1</td><td>2</td></tr><tr><td>0</td><td>1</td>"
						+ "<td>2</td></tr></table>", table.getHTML());
		
		Element row = table.getRow(0);
		
		for(Element x : row.getInnerElements())
		{
			x.setTagName("th");
		}
		
		assertEquals(
				"<table><tr><th>0</th><th>1</th><th>2</th></tr><tr><td>0</td>"
						+ "<td>1</td><td>2</td></tr><tr><td>0</td><td>1</td>"
						+ "<td>2</td></tr></table>", table.getHTML());
	}
	
	@Test
	public void test22SetCaptionOnTable()
	{
		TableElement table = new TableElement();
		
		table.setCaption("testCaption");
		
		assertEquals(
				"<table><caption>testCaption</caption></table>", 
				table.getHTML());
		
		table.setCaption("secondCaption");
		
		assertEquals(
				"<table><caption>secondCaption</caption></table>", 
				table.getHTML());
		
	}
	
	@Test
	public void test23SetCaptionOnTableWithData()
	{
		ArrayList<ArrayList<String>> testData = new ArrayList<ArrayList<String>>();

		for(int i = 0; i < 3; i++)
		{
			ArrayList<String> testRow = new ArrayList<String>();

			for(int j = 0; j < 3; j++)
			{
				testRow.add(new Integer(j).toString());
			}

			testData.add(testRow);
		}

		TableElement table = new TableElement(testData);

		assertEquals(
				"<table><tr><td>0</td><td>1</td><td>2</td></tr><tr><td>0</td>"
						+ "<td>1</td><td>2</td></tr><tr><td>0</td><td>1</td>"
						+ "<td>2</td></tr></table>", table.getHTML());
		

		
		table.setCaption("testCaption");
		
		assertEquals(
				"<table><tr><td>0</td><td>1</td><td>2</td></tr><tr><td>0</td>"
						+ "<td>1</td><td>2</td></tr><tr><td>0</td><td>1</td>"
						+ "<td>2</td></tr><caption>testCaption</caption>"
						+ "</table>", table.getHTML());
		
		table.setCaption("secondCaption");
		
		assertEquals(
				"<table><tr><td>0</td><td>1</td><td>2</td></tr><tr><td>0</td>"
						+ "<td>1</td><td>2</td></tr><tr><td>0</td><td>1</td>"
						+ "<td>2</td></tr><caption>secondCaption</caption>"
						+ "</table>", table.getHTML());
	}
}
