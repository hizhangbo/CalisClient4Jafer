package web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;

import org.jafer.exception.JaferException;
import org.jafer.query.QueryBuilder;
import org.jafer.query.QueryException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Node;

import calis.CalisClient;
import util.xml.XmlHelper;

@RestController
public class CalisController {

    @RequestMapping(value = "/getByISBN/{isbn}", method = RequestMethod.GET)
    public DOMSource getByISBN(@PathVariable("isbn") String isbn) {
    	DOMSource result = null;
        QueryBuilder builder = new QueryBuilder();
        try {
			Node a = builder.getNode("isbn", isbn);
			
			System.out.println("实例化calisclient. isbn:"+isbn);
			
			CalisClient calisClient = new CalisClient();
			int count = calisClient.zClient.submitQuery(a);
			System.out.println("提交查询");
			count = (count > 5) ? 5 : count;
			
//			XmlHelper xmlHelper = new XmlHelper();
//			String content = "";
//			
//			for(int i=1; i <= count; i++){
//				calisClient.zClient.setRecordCursor(i);
//		        org.jafer.record.Field field = calisClient.zClient.getCurrentRecord();
//		        
//
//		        DOMSource domSource = new DOMSource(field.getXML());
//		        content = xmlHelper.doc2String(domSource);
//		        result.add(content);
//			}
			
			if(count > 0){
				calisClient.zClient.setRecordCursor(1);
		        org.jafer.record.Field field = calisClient.zClient.getCurrentRecord();
		        result = new DOMSource(field.getXML());
			}
			
			calisClient.zClient.close();
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JaferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return result;
    	
    }
    


    @RequestMapping(value = "/getByTitle/{title}", method = RequestMethod.GET)
    public DOMSource getByTitle(@PathVariable("title") String title) {
    	DOMSource result = null;
        QueryBuilder builder = new QueryBuilder();
        try {
			Node a = builder.getNode("title", title);
			CalisClient calisClient = new CalisClient();
			int count = calisClient.zClient.submitQuery(a);
			count = (count > 5) ? 5 : count;
			
//			XmlHelper xmlHelper = new XmlHelper();
//			String content = "";
//			
//			for(int i=1; i <= count; i++){
//				calisClient.zClient.setRecordCursor(i);
//		        org.jafer.record.Field field = calisClient.zClient.getCurrentRecord();
//		        
//
//		        DOMSource domSource = new DOMSource(field.getXML());
//		        content = xmlHelper.doc2String(domSource);
//		        result.add(content);
//			}
			
			if(count > 0){
				calisClient.zClient.setRecordCursor(1);
		        org.jafer.record.Field field = calisClient.zClient.getCurrentRecord();
		        result = new DOMSource(field.getXML());
			}
			
			calisClient.zClient.close();
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JaferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return result;
    }
}
