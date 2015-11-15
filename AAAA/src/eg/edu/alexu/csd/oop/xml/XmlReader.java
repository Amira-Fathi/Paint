package eg.edu.alexu.csd.oop.xml;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
public class XmlReader{
	private File data;
	private String attr;
	private String[][]entries;
	public XmlReader (String path){
		data = new File (path);
		if (!(data.exists()&&!data.isDirectory())){
			throw new RuntimeException("No Such Table");
		}
		else {
			read();
		}
	}
	public String getAtrr(){
		return attr;
	}
	public String[][] getEntries(){
		return entries;
	}
	private void read(){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(data);
			Element root = doc.getDocumentElement();		
			attr=root.getAttribute(root.getTagName());
			int r,c;
			String[]cols = attr.split("\\,");
			c = cols.length;
			for (int i=0;i<c;i++){
				String[]p=cols[i].split("\\;");
				cols[i]=p[0];
			}
			NodeList rows = root.getElementsByTagName("entry");
			r = rows.getLength();
			entries = new String[r][c];
			for (int i=0;i<r;i++){
				for (int j=0;j<c;j++){
					Element col = (Element)((Element)rows.item(i)).getElementsByTagName(cols[j]).item(0);
					String content = col.getTextContent();
					if (content.equals("null")){
						entries[i][j]=null;
					}
					else entries[i][j]=content;
				}
			}
			// close file 
		}catch(ParserConfigurationException ex){
			throw new RuntimeException("ParserConfigurationException");
		}
		catch(SAXException ex){
			throw new RuntimeException("SAXException");
		}
		catch(IOException ex){
			throw new RuntimeException("IOException");
		}
	
	}
}
