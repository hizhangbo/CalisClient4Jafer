package calis;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.jafer.zclient.ZClient;

import util.xml.XmlHelper;

public class CalisClient {
	
	private String host;
	private Integer port;
	private String user;
	private String password;
	private String database;
	private String syntax;
	
	public ZClient zClient;
	
	public CalisClient(){
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config/CalisServer.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        
        host = p.getProperty("calis.host");
        port = Integer.parseInt(p.getProperty("calis.port"));
        user = p.getProperty("calis.user");
        password = p.getProperty("calis.password");
        database = p.getProperty("calis.database");
        syntax = p.getProperty("calis.syntax");
        
        System.out.println(host);
        
        setClient();
	}
	
	private void setClient(){

		zClient = new ZClient();

		zClient.setHost(host);
		zClient.setPort(port);
		zClient.setDatabases(database);

		String syntaxName = setSyntax();
		if(syntaxName.equals("")){
			syntaxName = "http://www.openarchives.org/OAI/oai_marc";
		}
		zClient.setRecordSchema(syntaxName);
		
		if(!user.equals("")){
			zClient.setUsername(user);
		}
		
		if(!password.equals("")){
			zClient.setPassword(password);
		}
	}
	
	private String setSyntax() {
		XmlHelper xmlHelper = new XmlHelper();
		Document doc = null;
		try {
			doc = xmlHelper.parse(this.getClass().getResource("/config/recordDescriptor.xml").getPath());
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Element> childs = xmlHelper.getChildNodesByName(doc, syntax.toUpperCase(), "transform");
		return "http://www.openarchives.org/OAI/oai_marc";//childs.get(0).attributeValue("sourceSchema");
	}
}
