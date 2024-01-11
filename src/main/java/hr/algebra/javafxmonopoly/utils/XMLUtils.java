package hr.algebra.javafxmonopoly.utils;

import hr.algebra.javafxmonopoly.GameStateSerializable;
import hr.algebra.javafxmonopoly.models.Player;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import java.io.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class XMLUtils {

    public static void saveToFile(GameStateSerializable gameStateSerializable, String filePath) {
        String xmlString = serializeToXml(gameStateSerializable);
        try (Writer writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(xmlString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameStateSerializable loadFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder xmlStringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                xmlStringBuilder.append(line).append("\n");
            }
            String xmlString = xmlStringBuilder.toString();
            return deserializeFromXml(xmlString);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String serializeToXml(GameStateSerializable gameStateSerializable) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // Root element
            Element rootElement = document.createElement("GameStateSerializable");
            document.appendChild(rootElement);

            // Players
            Element playersElement = document.createElement("Players");
            rootElement.appendChild(playersElement);

            for (Player player : gameStateSerializable.players) {
                Element playerElement = document.createElement("Player");
                playerElement.setAttribute("id", Integer.toString(player.getId()));
                playerElement.setAttribute("money", Integer.toString(player.getMoney()));
                playerElement.setAttribute("position", Integer.toString(player.getPosition()));
                playerElement.setAttribute("playing", Boolean.toString(player.playing));

                // Serialize deedIndices
                Element deedIndicesElement = document.createElement("DeedIndices");
                for (Integer deedIndex : player.deedIndices) {
                    Element deedIndexElement = document.createElement("DeedIndex");
                    deedIndexElement.appendChild(document.createTextNode(deedIndex.toString()));
                    deedIndicesElement.appendChild(deedIndexElement);
                }
                playerElement.appendChild(deedIndicesElement);

                playersElement.appendChild(playerElement);
            }

            // CurrentPlayerIndex
            Element currentPlayerIndexElement = document.createElement("CurrentPlayerIndex");
            currentPlayerIndexElement.appendChild(document.createTextNode(Integer.toString(gameStateSerializable.currentPlayerIndex)));
            rootElement.appendChild(currentPlayerIndexElement);

            // Logs
            Element logsElement = document.createElement("Logs");
            rootElement.appendChild(logsElement);

            for (String log : gameStateSerializable.logs) {
                Element logElement = document.createElement("Log");
                logElement.appendChild(document.createTextNode(log));
                logsElement.appendChild(logElement);
            }

            // Transform the document to a string
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            StringWriter stringWriter = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(stringWriter));

            return stringWriter.toString();

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static GameStateSerializable deserializeFromXml(String xml) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputSource inputSource = new InputSource(new StringReader(xml));
            Document document = documentBuilder.parse(inputSource);

            GameStateSerializable gameStateSerializable = new GameStateSerializable();
            gameStateSerializable.players = new java.util.ArrayList<>();

            // Players
            NodeList playerNodes = document.getElementsByTagName("Player");

            for (int i = 0; i < playerNodes.getLength(); i++) {
                Element playerElement = (Element) playerNodes.item(i);
                Player player = new Player(
                        Integer.parseInt(playerElement.getAttribute("id")),
                        Integer.parseInt(playerElement.getAttribute("money"))
                );

                player.setPosition(Integer.parseInt(playerElement.getAttribute("position")));
                player.playing = Boolean.parseBoolean(playerElement.getAttribute("playing"));

                // Deserialize deedIndices
                NodeList deedIndicesNodes = playerElement.getElementsByTagName("DeedIndex");
                player.deedIndices = new CopyOnWriteArrayList<>();
                for (int j = 0; j < deedIndicesNodes.getLength(); j++) {
                    Element deedIndexElement = (Element) deedIndicesNodes.item(j);
                    player.deedIndices.add(Integer.parseInt(deedIndexElement.getTextContent()));
                }

                gameStateSerializable.players.add(player);
            }

            // CurrentPlayerIndex
            NodeList currentPlayerIndexNodes = document.getElementsByTagName("CurrentPlayerIndex");
            Element currentPlayerIndexElement = (Element) currentPlayerIndexNodes.item(0);
            gameStateSerializable.currentPlayerIndex = Integer.parseInt(currentPlayerIndexElement.getTextContent());

            // Logs
            NodeList logNodes = document.getElementsByTagName("Log");
            gameStateSerializable.logs = new java.util.ArrayList<>();

            for (int i = 0; i < logNodes.getLength(); i++) {
                Element logElement = (Element) logNodes.item(i);
                gameStateSerializable.logs.add(logElement.getTextContent());
            }

            return gameStateSerializable;

        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e) {
            e.printStackTrace();
            return null;
        }
    }
}