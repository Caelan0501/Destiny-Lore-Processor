import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

public class CharacterFinder
{
    final String CharacterXmlDestination = System.getProperty("user.dir") + "\\LoreRefs\\Characters.xml";

    record Entry(String Character, String[] LoreRef){}

    public void StoreCharacterLoreRef(String Character, String LoreRef)
    {
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


    }
}
