package model.tile.toolTMX;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class TMXReader {

    private final ArrayList<Rectangle2D.Double> collisionObjects;
    public TMXReader(String filePathTMX) {
        collisionObjects = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(filePathTMX);

            // Opzionale, ma consigliato: normalizza il documento
            doc.getDocumentElement().normalize();

            // COLLISIONI Estrae le informazioni sugli oggetti
            NodeList objectGroupList = doc.getElementsByTagName("objectgroup");
            for (int i = 0; i < objectGroupList.getLength(); i++) {
                Element objectGroupElement = (Element) objectGroupList.item(i);

                // Estrae gli oggetti all'interno del gruppo
                NodeList objectList = objectGroupElement.getElementsByTagName("object");
                for (int j = 0; j < objectList.getLength(); j++) {
                    Element objectElement = (Element) objectList.item(j);
                    double objectX = Double.parseDouble(objectElement.getAttribute("x"));
                    double objectY = Double.parseDouble(objectElement.getAttribute("y"));
                    double objectWidth = Double.parseDouble(objectElement.getAttribute("width"));
                    double objectHeight = Double.parseDouble(objectElement.getAttribute("height"));

                    collisionObjects.add(new Rectangle2D.Double(objectX, objectY, objectWidth, objectHeight));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Rectangle2D.Double> getCollisionObjects() {
        return collisionObjects;
    }
}
