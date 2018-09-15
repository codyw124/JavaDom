package com.bcbsnc.cody;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

public class Element
{
	private String tagName_;

	// this is an arrayArrayList because we want to maintain that the elements that
	// are added are in the same order that the user added them
	protected ArrayList<Element> innerElements_;
	private String innerText_;

	// we decided to use tree implementation to maintain alphabetic order so
	// that the user does not have to keep up with when they add things to the
	// element
	private TreeSet<String> classes_;
	private TreeMap<String, String> attributes_;
	private TreeMap<String, String> dataAttributes_;
	private TreeMap<String, String> styles_;

	public Element(String tagName)
	{
		innerElements_ = new ArrayList<Element>();
		innerText_ = "";
		tagName_ = tagName;
		classes_ = new TreeSet<String>();
		attributes_ = new TreeMap<String, String>();
		dataAttributes_ = new TreeMap<String, String>();
		styles_ = new TreeMap<String, String>();
	}

	public String getTagName()
	{
		return tagName_;
	}
	
	public void setTagName(String newTagName)
	{
		tagName_ = newTagName;
	}

	public TreeMap<String, String> getAttributes()
	{
		return attributes_;
	}

	public ArrayList<Element> getInnerElements()
	{
		return innerElements_;
	}

	public Element getInnerElement(int index)
	{
		return innerElements_.get(index);
	}

	public Element addInnerElement(Element newInnerTag)
	{
		innerElements_.add(newInnerTag);

		return newInnerTag;
	}

	public Element addInnerElement(String tagName)
	{
		Element newInnerElement = new Element(tagName);
		
		return addInnerElement(newInnerElement);
	}

	public void removeInnerElement(int index)
	{
		innerElements_.remove(index);
	}

	public void setInnerText(String newText)
	{
		innerText_ = newText;
	}
	
	public String getInnerText()
	{
		return innerText_;
	}

	public void addAttribute(String attribute, String value)
	{
		char[] charsInValue = value.toCharArray();

		StringBuilder sb = new StringBuilder();

		for(char x : charsInValue)
		{
			if(Character.isLetterOrDigit(x))
			{
				sb.append(x);
			}
		}

		attributes_.put(attribute, sb.toString());
	}

	public void removeAttribute(String attribute)
	{
		attributes_.remove(attribute);
	}

	public void addClass(String classToAdd)
	{
		classes_.add(classToAdd);
	}

	public void removeClass(String classToRemove)
	{
		classes_.remove(classToRemove);
	}

	public void addDataAttribute(String key, String value)
	{
		dataAttributes_.put(key, value);
	}

	public void removeDataAttribute(String key)
	{
		dataAttributes_.remove(key);
	}

	public void addStyle(String key, String value)
	{
		styles_.put(key, value);
	}

	public void removeStyle(String key)
	{
		styles_.remove(key);
	}

	public String getHTML()
	{
		StringBuilder sb = new StringBuilder();

		getHTMLHelper(sb);

		return sb.toString();
	}

	private void getHTMLHelper(StringBuilder sb)
	{
		// opening tag
		sb.append("<" + tagName_);

		// attributes
		sb.append(getAttributesString());

		// close opening tag
		sb.append(">");

		// add the text that goes between the tags
		sb.append(innerText_);

		// add the elements that go between the tags
		for(Element innerTag : innerElements_)
		{
			innerTag.getHTMLHelper(sb);
		}

		// then ad the closing tag
		sb.append("</" + tagName_ + ">");
	}

	// builds the entire attributes ArrayList for this element as a string
	private String getAttributesString()
	{
		// make a builder
		StringBuilder sb = new StringBuilder();

		// add the class attribute
		sb.append(getClassString());

		// add the inline styles
		sb.append(getStyleString());

		// add the data attributes
		sb.append(getDataAttributeString());

		// add the basic attributes
		for(String x : attributes_.keySet())
		{
			sb.append(" " + x + "=\"" + attributes_.get(x) + "\"");
		}

		return sb.toString();
	}

	// gets the html equivalent for the classes as a string
	private String getClassString()
	{
		// make a string builder we can use
		StringBuilder sb = new StringBuilder();

		// if you have any
		if(!classes_.isEmpty())
		{
			// append initial class attribute definition
			sb.append(" class=\"");

			boolean firstClass = true;

			// for all classes
			for(String x : classes_)
			{
				if(firstClass)
				{
					firstClass = false;
				}
				else
				{
					sb.append(" ");
				}

				sb.append(x);
			}

			// end with the last quote mark
			sb.append("\"");
		}

		return sb.toString();
	}

	// gets the html equivalent for the ArrayList of data attributes as a string
	private String getDataAttributeString()
	{
		StringBuilder sb = new StringBuilder();

		for(String x : dataAttributes_.keySet())
		{
			sb.append(" data-" + x + "=\"" + dataAttributes_.get(x) + "\"");
		}

		return sb.toString();
	}

	// gets the html equivalent for the ArrayList of styles as a string
	private String getStyleString()
	{
		StringBuilder sb = new StringBuilder();

		if(!styles_.isEmpty())
		{
			sb.append(" style=\"");

			boolean firstStyle = true;

			for(String x : styles_.keySet())
			{
				if(firstStyle == true)
				{
					firstStyle = false;
				}
				else
				{
					sb.append(" ");
				}

				sb.append(x + ":" + styles_.get(x) + ";");

			}

			sb.append("\"");
		}

		return sb.toString();
	}
}
