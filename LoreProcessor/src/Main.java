import java.io.IOException;
import java.sql.*;
import java.util.Arrays;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class Main
{
    public static void CharacterFinderSandbox() {
        CharacterFinder cf = new CharacterFinder();
        cf.Update();
        Character zavala = cf.LoadCharacter("Zavala");
        System.out.println(zavala.toString());
        String[] nicknames = {"IKO-006", "Ikorakel", "Bad Name"};
        Characteristics characteristics = new Characteristics("Human", "Awoken", "Female", "Brown", "Brown");
        Character ikora = new Character("Ikora Rey", nicknames, characteristics);
        ikora.preset("Guardian");

        ikora.AddName("Ikora");
        ikora.RemoveName("Bad Name");

        ikora.AddLoreReference("None");
        ikora.RemoveLoreReference("None");
        Characteristics newCharacteristics = new Characteristics("Human", "Human", "Female", "Brown", "Brown");
        ikora.ModifyCharacteristics(newCharacteristics);

        ikora.AddAffiliation("Affiliation");
        ikora.RemoveAffiliation("Affiliation");

        ikora.AddPower("Something");
        String[] powers = ikora.getPowers();
        String[] lore = ikora.getLoreReferences();
        ikora.RemovePower("Something");
        System.out.println(Arrays.toString(powers));
        System.out.println(Arrays.toString(lore));

        cf.AddCharacter(ikora);
    }
    public static void SQLSandbox() {
        Lore lore = null;
        String jdbcUrl = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\world_sql_content_10-8.sqlite3";
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(jdbcUrl);
        try
        {
            Connection connection = DriverManager.getConnection(jdbcUrl);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM DestinyLoreDefinition");

            while(resultSet.next())
            {
                int id = resultSet.getInt("id");

                JsonFactory factory = new JsonFactory();
                String json = resultSet.getString("json");
                try (JsonParser parser = factory.createParser(json))
                {
                    String name = "", subtitle = "", content = "", iconAddress = "";
                    boolean hasIcon = false;
                    long hash = 0;
                    while (!parser.isClosed())
                    {
                        JsonToken token = parser.nextToken();
                        if (token == null) break;
                        if (token == JsonToken.FIELD_NAME)
                        {
                            switch (parser.getValueAsString())
                            {
                                case "name":
                                    parser.nextToken();
                                    name = parser.getValueAsString();
                                    break;
                                case "subtitle":
                                    parser.nextToken();
                                    subtitle = parser.getValueAsString();
                                    break;
                                case "description":
                                    parser.nextToken();
                                    content = parser.getValueAsString();
                                    break;
                                case "hash":
                                    parser.nextToken();
                                    hash = parser.getValueAsLong();
                                case "hasIcon":
                                    parser.nextToken();
                                    hasIcon = parser.getValueAsBoolean();
                                    break;
                                case "iconAddress":
                                    parser.nextToken();
                                    iconAddress = parser.getValueAsString();
                                    break;
                            }
                        }
                    }
                    if (hasIcon)
                        lore = new Lore(name, subtitle, content, hash, id, iconAddress);
                    else
                        lore = new Lore(name, subtitle, content, hash, id);

                    System.out.println(lore);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            System.out.println("ERROR");
            e.printStackTrace();
        }
        if (lore != null)
        {
            System.out.println(lore);
        }
    }
    public static void main(String[] args)
    {
        CharacterFinderSandbox();
        //SQLSandbox();
    }
}