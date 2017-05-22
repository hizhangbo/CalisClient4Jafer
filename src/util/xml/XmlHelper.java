package util.xml;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlHelper {
	
	public Document parse(String file_path) throws DocumentException{
		File file = new File(file_path);
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        return document;
	}
	
	public List<Element> getNodesByName(Document doc, String name){
        Element root = doc.getRootElement();

        List<Element> elements = new ArrayList<>();
        for(Iterator<?> i = root.elementIterator(name); i.hasNext();){
            elements.add((Element) i.next());
        }
        return elements;
	}
	
	public List<Element> getChildNodesByName(Document doc, String parentName, String childName){
		Element root = doc.getRootElement();
		
		List<Element> parents = new ArrayList<>();
        for(Iterator<?> i = root.elementIterator(parentName); i.hasNext();){
        	parents.add((Element) i.next());
        }
        
        List<Element> childs = new ArrayList<>();
        for(Element parent : parents){
            for(Iterator<?> i = parent.elementIterator(childName); i.hasNext();){
            	childs.add((Element) i.next());
            }
        }        
        return childs;
	}

    public String doc2String(DOMSource doc){  
        try {  
            Source source = doc;  
            StringWriter stringWriter = new StringWriter();  
            Result result = new StreamResult(stringWriter);  
            TransformerFactory factory = TransformerFactory.newInstance();  
            Transformer transformer = factory.newTransformer();  
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");  
            transformer.transform(source, result);  
            return stringWriter.getBuffer().toString();  
        } catch (Exception e) {  
            return null;  
        }  
    }
}
