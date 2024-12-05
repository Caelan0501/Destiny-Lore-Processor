public class Lore
{
    public String name;
    public String subtitle;
    public String content;
    public boolean hasIcon = false;
    public String iconAddress = "";
    public long hash;
    public int id;

    public Lore(String name, String subtitle, String content, long hash, int id)
    {
        this.name = name;
        this.subtitle = subtitle;
        this.content = content;
        this.hash = hash;
        this.id = id;
    }

    public Lore(String name, String subtitle, String content, long hash, int id, String iconAddress)
    {
        this(name, subtitle, content, hash, id);
        this.hasIcon = true;
        this.iconAddress = iconAddress;
    }

    @Override
    public String toString() {
        return "Name " + name + " Subtitle " + subtitle + " Content " + content + " Hash " + hash + " ID " + id;
    }
}
