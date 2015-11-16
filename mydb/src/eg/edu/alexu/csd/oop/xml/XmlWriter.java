package eg.edu.alexu.csd.oop.xml;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
public class XmlWriter {
	private String attrib,tableName;
    private String[][]table;
    private File data;
    //PersonID;int,LastName;varchar,FirstName;varchar,Address;varchar,City;varchar
    //http://codeshare.io/78we0
    //XMLWriter
	public XmlWriter(File data , String[][] table,String attrib , String tableName) {
		this.data=data;
		this.attrib = attrib;
		this.table = table;
		this.tableName = tableName;
		this.create();
	}

	public String getAttrib() {
		return attrib;
	}

	private void create() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = docBuilder.newDocument();
			org.w3c.dom.Element root = doc.createElement(this.tableName);
			Attr attribute = doc.createAttribute(root.getTagName());
			attribute.setValue(this.attrib);
			root.setAttributeNode(attribute);
			System.out.println(table.length);
            for(int i = 0 ; i < this.table.length ; i++)
            {
            	String[] colAttribute = this.attrib.split(",");
            	org.w3c.dom.Element child = doc.createElement("entry");
            	for(int j = 0 ; j <this.table[0].length ; j++)
            	{
            		String[]col = colAttribute[j].split(";");
            		System.out.println(col[0] + " " + this.table[i][j]);
            		org.w3c.dom.Element columnName = doc.createElement(col[0]);
            		columnName.appendChild(doc.createTextNode(String.valueOf(this.table[i][j])));
            		child.appendChild(columnName);
            	}
            	root.appendChild(child);
            }
            doc.appendChild(root);
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			DOMSource source = new DOMSource(doc);
			
			StreamResult result = new StreamResult(data);
			
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
			// close file
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
}

