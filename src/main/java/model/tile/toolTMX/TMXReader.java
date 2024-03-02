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

    private ArrayList<Rectangle2D.Double> collisionObjects;
    private ArrayList<String> listaMatrici = new ArrayList<>();
    private Set<Integer> insiemeCodici = new TreeSet<>();
    private List<Set<Integer>> listaInsiemi = new ArrayList<>();
    private List<Integer> intervalli = new ArrayList<>();
    private List<String> listaPercorsiTilesets = new ArrayList<>();
    private ArrayList<Integer> firstGidList = new ArrayList<>();
    private ArrayList<Integer> tileCounterList = new ArrayList<>();
    private ArrayList<Integer> columnsTilesetList = new ArrayList<>();
    private HashMap<Integer, BufferedImage> mappaSprite = new HashMap<>();
    private int tilesetSize;
    private int numLayer;
    private int mapWidth;
    private int mapHeigth;


    public TMXReader(String filePathTMX) {
        this.collisionObjects = new ArrayList<Rectangle2D.Double>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(filePathTMX);

            // Opzionale, ma consigliato: normalizza il documento
            doc.getDocumentElement().normalize();

            // Estrai le informazioni generali sulla mappa
            Element mapElement = doc.getDocumentElement();
            mapWidth = Integer.parseInt(mapElement.getAttribute("width"));
            mapHeigth = Integer.parseInt(mapElement.getAttribute("height"));

            // Estrai le informazioni sui tileset
            NodeList tilesetList = doc.getElementsByTagName("tileset");
            tilesetSize = tilesetList.getLength();
            for (int i = 0; i < tilesetList.getLength(); i++) {
                Element tilesetElement = (Element) tilesetList.item(i);
                int firstGid = Integer.parseInt(tilesetElement.getAttribute("firstgid"));
                firstGidList.add(firstGid); //importante
                int tileCount = Integer.parseInt(tilesetElement.getAttribute("tilecount"));
                tileCounterList.add(tileCount); //importante
                int columns = Integer.parseInt(tilesetElement.getAttribute("columns"));
                columnsTilesetList.add(columns); //importante

                Element imageElement = (Element) tilesetElement.getElementsByTagName("image").item(0);
                String imageSource = imageElement.getAttribute("source");

                intervalli.add(firstGid);
                intervalli.add(firstGid + tileCount - 1);
                listaPercorsiTilesets.add(imageSource);
            }

            // Estrai le informazioni sui layer
            NodeList layerList = doc.getElementsByTagName("layer");
            numLayer = layerList.getLength();
            for (int i = 0; i < numLayer; i++) {
                Set<Integer> codici = new HashSet<>();
                Element layerElement = (Element) layerList.item(i);

                // Estrai i dati del layer
                Element dataElement = (Element) layerElement.getElementsByTagName("data").item(0);
                String data = dataElement.getTextContent().trim();

                listaMatrici.add(data);

                // Dividi il contenuto CSV e aggiungi gli elementi all'insieme
                String[] elements = data.split(",");
                for (String element : elements) {
                    codici.add(Integer.parseInt(element.trim()));
                }


                listaInsiemi.add(codici);
                insiemeCodici.addAll(codici);
            }
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

        /*
        System.out.println("Lista di insiemi con i codici:");
        for (Set<Integer> set : listaInsiemi) System.out.println(set);
        System.out.println("\nInsieme con tutti i " + insiemeCodici.size() + " codici del file TMX:\n" + insiemeCodici);
        System.out.println("\nIntervalli di ciascun tileset:\n" + intervalli);
        System.out.println("\nLista di percorsi: ");
        for (String percorso : listaPercorsiTilesets) System.out.println(percorso);
        for (String matrice : listaMatrici) {
            System.out.println("\n" + matrice);
        }

         */



        int codiciPassati = 0;

        for (int i = 0; i < tilesetSize; i++) {
            try {
                //Nel file tmx i percorsi iniziano con 1 o più "../" quindi bisogna cancellarli e concatenargli "src/main/resources/"
                String imageSource = listaPercorsiTilesets.get(i);
                String patternToRemove = "\\.\\./";
                String imagePath = "src/main/resources/" + imageSource.replaceAll(patternToRemove, "");
                //System.out.println("\n" + imagePath);

                // Grandezza desiderata dello sprite (16x16)
                int subimageWidth = 16;
                int subimageHeight = 16;

                // Carica l'immagine del tileset PNG
                BufferedImage originalImage = ImageIO.read(new File(imagePath));

                insiemeCodici.remove(0);

                List<Integer> listaCodici = new ArrayList<>(insiemeCodici);

                //System.out.println("Colonne tilesets: "+ columnsTilesetList);
                //System.out.println("Codici passati: "+ codiciPassati);

                // Cicla attraverso l'insieme di codici
                // Per ogni codice mi trova lo sprite corrispondente e me lo salva
                for (int j = codiciPassati; j < listaCodici.size(); j++) {
                    //System.out.println("Codice corrente: "+listaCodici.get(j));
                    if (listaCodici.get(j) >= intervalli.get(2 * i) && listaCodici.get(j) <= intervalli.get(2 * i + 1)) {
                        codiciPassati++;

                        //System.out.println("Passato");
                        // Calcola le coordinate della sottoregione in base al codice
                        int row = (listaCodici.get(j) - firstGidList.get(i)) / columnsTilesetList.get(i);
                        int col = (listaCodici.get(j) - firstGidList.get(i)) % columnsTilesetList.get(i);

                        // Calcola le coordinate di inizio della sottoregione
                        int startX = col * subimageWidth;
                        int startY = row * subimageHeight;

                        // Ottieni lo sprite
                        BufferedImage subImage = originalImage.getSubimage(startX, startY, subimageWidth, subimageHeight);


                        mappaSprite.put(listaCodici.get(j), subImage);

                        /*
                        // Salva la sottoregione in un nuovo file PNG
                        String outputImagePath = "src/main/resources/Sprite/sprite_" + listaCodici.get(j) + ".png";


                        //Creazione dell'oggetto File per rappresentare la cartella
                        File outputFolder = new File("src/main/resources/Sprite/");
                        // Verifica se la cartella esiste, altrimenti la crea
                        if (!outputFolder.exists()) {
                            try {
                                outputFolder.mkdirs(); // mkdirs() crea anche le sottocartelle se necessario
                                System.out.println("Cartella creata con successo.");
                            } catch (SecurityException se) {
                                System.out.println("Impossibile creare la cartella");
                                se.printStackTrace();
                            }
                        }
                        try {
                            // Salva l'immagine
                            ImageIO.write(subImage, "png", new File(outputImagePath));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                         */


                    } else {
                        break;
                    }
                }

                //System.out.println("Sprite estratti e salvati con successo.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> getListaMatrici() {
        return listaMatrici;
    }

    public HashMap<Integer, BufferedImage> getMappaSprite() {
        return mappaSprite;
    }

    public int getNumLayer() {
        return numLayer;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeigth() {
        return mapHeigth;
    }

    public ArrayList<Rectangle2D.Double> getCollisionObjects() {
        return collisionObjects;
    }
}
