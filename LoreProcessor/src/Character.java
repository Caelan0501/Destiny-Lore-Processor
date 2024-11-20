import java.util.ArrayList;
import java.util.List;

public class Character
{
    String Name;
    List<String> OtherNames = new ArrayList<>();
    Characteristics Characteristics;
    List<String> Affiliation = new ArrayList<>();
    List<String> Powers = new ArrayList<>();
    List<String> LoreReferences = new ArrayList<>();

    public Character(String Name) { this.Name = Name; }
    public Character(String Name, Characteristics Characteristics) {
        this(Name);
        this.Characteristics = Characteristics;
    }
    public Character(String Name, String[] OtherNames, Characteristics Characteristics) {
        this(Name, Characteristics);
        this.OtherNames = new ArrayList<>(List.of(OtherNames));
    }

    public String getName() { return Name; }
    public String[] getNames() {
        String[] result = new String[OtherNames.size() + 1];
        result[0] = Name;
        for(int i = 0; i < OtherNames.size(); i++) result[i+1] = OtherNames.get(i);
        return result;
    }
    public Characteristics getCharacteristics() { return Characteristics; }
    public String[] getAffiliation() {
        String[] result = new String[Affiliation.size()];
        for(int i = 0; i < Affiliation.size(); i++) result[i] = Affiliation.get(i);
        return result;
    }
    public String[] getPowers() {
        String[] result = new String[Powers.size()];
        for(int i = 0; i < Powers.size(); i++) result[i] = Powers.get(i);
        return result;
    }
    public String[] getLoreReferences() {
        String[] result = new String[LoreReferences.size()];
        for(int i = 0; i < LoreReferences.size(); i++) result[i] = LoreReferences.get(i);
        return result;
    }

    public void AddName(String name) { OtherNames.add(name); }
    public void RemoveName(String name) { OtherNames.remove(name); }
    public void AddLoreReference(String ref) { LoreReferences.add(ref); }
    public void RemoveLoreReference(String ref) { LoreReferences.remove(ref); }
    public void ModifyCharacteristics(Characteristics Characteristics) { this.Characteristics = Characteristics; }
    public void AddAffiliation(String aff) { Affiliation.add(aff); }
    public void RemoveAffiliation(String aff) { Affiliation.remove(aff); }
    public void AddPower(String power) { Powers.add(power); }
    public void RemovePower(String power) { Powers.remove(power); }

}