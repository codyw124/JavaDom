//TODO idea to create a way to add tables to tables

package com.bcbsnc.cody;

import java.util.ArrayList;
import java.util.ArrayList;

public class TableElement extends Element
{
	public TableElement()
	{
		super("table");
	}

	public TableElement(ArrayList<ArrayList<String>> data)
	{
		super("table");

		for(ArrayList<String> row : data)
		{
			Element newRow = addRow();

			for(String pieceOfData : row)
			{
				Element columnInRow = newRow.addInnerElement("td");
				if(pieceOfData.equals(""))
				{
					columnInRow.setInnerText("&nbsp");
				}
				else
				{
					columnInRow.setInnerText(pieceOfData);	
				}
			}
		}
	}

	public Element addRow()
	{
		return this.addInnerElement("tr");
	}

	public ArrayList<Element> getRows()
	{
		ArrayList<Element> rows = new ArrayList<Element>();

		for(Element x : this.getInnerElements())
		{
			if(x.getTagName().equals("tr"))
			{
				rows.add(x);
			}
		}

		return rows;
	}

	public Element getRow(int index)
	{
		ArrayList<Element> rows = getRows();

		return rows.get(index);
	}

	public ArrayList<Element> getColumn(int index)
	{
		ArrayList<Element> column = new ArrayList<Element>();

		ArrayList<Element> rows = (ArrayList<Element>) getRows();

		for(Element row : rows)
		{
			column.add(row.getInnerElement(index));
		}

		return column;
	}

	public Element getCaption()
	{
		Element caption = null;

		int captionIndex = -1;

		ArrayList<Element> elements = (ArrayList<Element>) this.getInnerElements();

		for(int i = 0; i < elements.size() && captionIndex == -1; i++)
		{
			if(elements.get(i).getTagName().equals("caption"))
			{
				captionIndex = i;
			}
		}

		// if there is no caption
		if(captionIndex != -1)
		{
			caption = elements.get(captionIndex);
		}
		
		return caption;
	}
	
	public Element setCaption(String newCaption)
	{
		Element caption = getCaption();
		
		if(caption == null)
		{
			caption = new Element("caption");
			caption.setInnerText(newCaption);
			this.addInnerElement(caption);
		}
		else
		{
			caption.setInnerText(newCaption);
		}
		
		return caption;
	}
}
