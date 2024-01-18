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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class XMLUtils {

    public static void saveToFile(List<GameStateSerializable> gameStates, String filePath) {
        String xmlString = serializeToXml(gameStates);
        try (Writer writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(xmlString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<GameStateSerializable> loadFromFile(String filePath) {
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

    private static String serializeToXml(List<GameStateSerializable> gameStates) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // Root element
            Element rootElement = document.createElement("GameStates");
            document.appendChild(rootElement);

            for (GameStateSerializable gameState : gameStates) {
                Element gameStateElement = document.createElement("GameStateSerializable");
                rootElement.appendChild(gameStateElement);

                // Players
                Element playersElement = document.createElement("Players");
                gameStateElement.appendChild(playersElement);

                for (Player player : gameState.players) {
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
                currentPlayerIndexElement.appendChild(document.createTextNode(Integer.toString(gameState.currentPlayerIndex)));
                gameStateElement.appendChild(currentPlayerIndexElement);

                // Logs
                Element logsElement = document.createElement("Logs");
                gameStateElement.appendChild(logsElement);

                for (String log : gameState.logs) {
                    Element logElement = document.createElement("Log");
                    logElement.appendChild(document.createTextNode(log));
                    logsElement.appendChild(logElement);
                }
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

    private static List<GameStateSerializable> deserializeFromXml(String xml) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputSource inputSource = new InputSource(new StringReader(xml));
            Document document = documentBuilder.parse(inputSource);

            List<GameStateSerializable> gameStates = new ArrayList<>();

            NodeList gameStateNodes = document.getElementsByTagName("GameStateSerializable");
            for (int i = 0; i < gameStateNodes.getLength(); i++) {
                Element gameStateElement = (Element) gameStateNodes.item(i);
                GameStateSerializable gameState = new GameStateSerializable();
                gameState.players = new ArrayList<>();

                // Players
                NodeList playerNodes = gameStateElement.getElementsByTagName("Player");

                for (int j = 0; j < playerNodes.getLength(); j++) {
                    Element playerElement = (Element) playerNodes.item(j);
                    Player player = new Player(
                            Integer.parseInt(playerElement.getAttribute("id")),
                            Integer.parseInt(playerElement.getAttribute("money"))
                    );

                    player.setPosition(Integer.parseInt(playerElement.getAttribute("position")));
                    player.playing = Boolean.parseBoolean(playerElement.getAttribute("playing"));

                    // Deserialize deedIndices
                    NodeList deedIndicesNodes = playerElement.getElementsByTagName("DeedIndex");
                    player.deedIndices = new CopyOnWriteArrayList<>();
                    for (int k = 0; k < deedIndicesNodes.getLength(); k++) {
                        Element deedIndexElement = (Element) deedIndicesNodes.item(k);
                        player.deedIndices.add(Integer.parseInt(deedIndexElement.getTextContent()));
                    }

                    gameState.players.add(player);
                }

                // CurrentPlayerIndex
                NodeList currentPlayerIndexNodes = gameStateElement.getElementsByTagName("CurrentPlayerIndex");
                Element currentPlayerIndexElement = (Element) currentPlayerIndexNodes.item(0);
                gameState.currentPlayerIndex = Integer.parseInt(currentPlayerIndexElement.getTextContent());

                // Logs
                NodeList logNodes = gameStateElement.getElementsByTagName("Log");
                gameState.logs = new ArrayList<>();

                for (int j = 0; j < logNodes.getLength(); j++) {
                    Element logElement = (Element) logNodes.item(j);
                    gameState.logs.add(logElement.getTextContent());
                }

                gameStates.add(gameState);
            }

            return gameStates;

        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e) {
            e.printStackTrace();
            return null;
        }
    }
}