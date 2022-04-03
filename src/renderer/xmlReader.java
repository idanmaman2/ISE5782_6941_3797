package renderer;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.*;
import geometries.*;
import lightning.AmbientLight;
import primitives.*;
import renderer.*;
import Scene.*;

public class xmlReader {
    String fileName ; 
    Element root ; 

    public Color getBG (){
        return new Color(xmlAtDouble3("background-color",root));
    }
    public AmbientLight getAmbient(){
        Node sonAB = root.getElementsByTagName("ambient-light").item(0);
        return new AmbientLight(new Color(xmlAtDouble3("color", sonAB)),xmlAtDouble3("dx",sonAB) );
    }
    public Geometries getGeometries(){
        Geometries g = new Geometries();
        Node geo = root.getElementsByTagName("geometries").item(0);
        for(int i =0  ; i <  geo.getChildNodes().getLength() ; i++ ){
            Node x = geo.getChildNodes().item(i);
            if(x.getNodeName() == "triangle"){
                    g.add(new Triangle(new Point(xmlAtDouble3("p0",x)), new Point(xmlAtDouble3("p1",x)) , new Point(xmlAtDouble3("p2",x))));
            }
            if(x.getNodeName() == "sphere"){
                g.add(new Sphere( new Point(xmlAtDouble3("center",x)) , Double.parseDouble(x.getAttributes().getNamedItem("radius").getTextContent())));
            }
        } 
        return g ;
    }


    public xmlReader(String name){
        this.fileName  = name ;
        root =getRoot();
    }

	Element getRoot(){
	
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try { 
		DocumentBuilder builder = factory.newDocumentBuilder();
		final String FOLDER_PATH = System.getProperty("user.dir") + "/xmlDB";
		File file = new File(FOLDER_PATH + '/' + fileName + ".xml");
		try{
		Document document = builder.parse(file);
		document.getDocumentElement().normalize();
		return document.getDocumentElement();
		}
		catch(IOException e){
			throw new IllegalStateException("I/O error - may be missing directory " + FOLDER_PATH, e);
		}
		catch(SAXException e){
			throw new IllegalStateException("I/O error - may be missing directory " + FOLDER_PATH, e);
		}
		} 
		catch (ParserConfigurationException e) {
			System.out.println("I dont realy care");
		}
		return null ;
	
	}

	Double3 xmlAtDouble3(String name,Element root){
		String color  = root.getAttribute(name);
		String  [] colorarr= color.split(" ");
		return  new Double3(Double.parseDouble(colorarr[0]),Double.parseDouble(colorarr[1]),Double.parseDouble(colorarr[2]));
	}
    
	Double3 xmlAtDouble3(String name,Node root){
		String color  = root.getAttributes().getNamedItem(name).getTextContent();
		String  [] colorarr= color.split(" ");
		return  new Double3(Double.parseDouble(colorarr[0]),Double.parseDouble(colorarr[1]),Double.parseDouble(colorarr[2]));
	}






}
