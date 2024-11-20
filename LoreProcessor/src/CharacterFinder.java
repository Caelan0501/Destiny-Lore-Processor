import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;


public class CharacterFinder
{
    final String CharacterXmlDestination = System.getProperty("user.dir") + "\\LoreRefs\\Characters.xml";
    Document doc = null;

    public CharacterFinder()
    {
        try
        {
            File CharacterFile = new File(CharacterXmlDestination);
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(CharacterFile);

            // Normalize the document
            doc.getDocumentElement().normalize();
            removeWhitespaceNodes(doc.getDocumentElement());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void Update()
    {
        try
        {
            // Write the updated XML back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "1"); // Set indentation amount
            transformer.transform(new DOMSource(doc), new StreamResult(new File(CharacterXmlDestination)));
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private static void removeWhitespaceNodes(Node node) {
        NodeList children = node.getChildNodes();
        for (int i = children.getLength() - 1; i >= 0; i--) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.TEXT_NODE && child.getNodeValue().trim().isEmpty())
            {
                node.removeChild(child);
            }
            else if (child.hasChildNodes())
            {
                removeWhitespaceNodes(child);
            }
        }
    }

    public void AddCharacter(Character character)
    {
        Element newCharacter = doc.createElement("Character");
        newCharacter.setAttribute("Name", character.getName());

        // Add <OtherNames>
        Element otherNames = doc.createElement("OtherNames");

        int i = 1;
        for(String name : character.getNames())
        {
            if(name.compareTo(character.getName()) == 0) continue;
            Element element = doc.createElement("Name");
            element.setTextContent(name);
            otherNames.appendChild(element);
        }
        newCharacter.appendChild(otherNames);

        // Add <Characteristics>
        Element characteristicsElem = doc.createElement("Characteristics");
        Characteristics characteristics = character.getCharacteristics();
        characteristicsElem.setAttribute("Species", characteristics.Species());
        characteristicsElem.setAttribute("Race", characteristics.Race());
        characteristicsElem.setAttribute("Gender", characteristics.Gender());
        characteristicsElem.setAttribute("HairColor", characteristics.HairColor());
        characteristicsElem.setAttribute("EyeColor", characteristics.EyeType());
        newCharacter.appendChild(characteristicsElem);

        // Add <Affiliations>
        Element affiliations = doc.createElement("Affiliations");
        for(String aff : character.getAffiliation())
        {
            Element affiliation = doc.createElement("Affiliation");
            affiliation.setTextContent(aff);
            affiliations.appendChild(affiliation);
        }
        newCharacter.appendChild(affiliations);

        // Add <Powers>// Work in progress
        Element powers = doc.createElement("Powers");
        Element paracausal = doc.createElement("Paracausal");
        Element light = doc.createElement("Light");
        Element guardianSubclass = doc.createElement("GuardianSubclass");
        guardianSubclass.setTextContent("Voidwalker");
        light.appendChild(guardianSubclass);
        paracausal.appendChild(light);
        powers.appendChild(paracausal);
        newCharacter.appendChild(powers);

        // Append the new character to <Characters>
        Node root = doc.getElementsByTagName("Characters").item(0);
        root.appendChild(newCharacter);
    }

    public Character LoadCharacter(String Name) {
        try
        {
            File CharacterFile = new File(CharacterXmlDestination);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(CharacterFile);

            doc.getDocumentElement().normalize();
            Element root = doc.getDocumentElement();
            NodeList nodes = root.getChildNodes();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void UpdateCharacter(Character character) {

    }


}
