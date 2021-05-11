
/**
 * Escreva a descrição da classe Item aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class Item
{
    // variáveis de instância - substitua o exemplo abaixo pelo seu próprio
    private String name;
    private String description;
    private String detailedDescription;
    private Double weight;
    private boolean hidden;

    /**
     * Constructor for the item class, if the is a hidden item.
     * @param name, description, detailedDescription and weight Represents the item name, description, detailed description and weight,
     * respectively.
     * @param hidden is the setting if the item will be hidden on the room or not.
     */
    public Item(String name, String description, String detailedDescription, Double weight, Boolean hidden)
    {
        this.name = name;
        this.description = description;
        this.detailedDescription = detailedDescription;
        this.weight = weight;
        this.hidden = hidden;
    }

    /**
     * @return Item name
     */
    public String getName()
    {
        return name;   
    }

    /**
     * @return Item description
     */
    public String getDescription()
    {
        return description;   
    }

    /**
     * @return Item weight
     */
    public Double getWeight()
    {
        return weight;   
    }

    /**
     * @return Item detailed description
     */
    public String getDetailedDescription() {
        return detailedDescription;   
    }

    /**
     * @returns true if item is hidden
     */
    public Boolean isHidden() {
        return hidden;   
    }

    /**
     * Set method to set if a item is hidden.
     */
    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * Set method to set the item detailed description.
     * @param detailedDescription The detailed description of said item.
     */
    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    /**
     * @return the item name, description and weight for the player console, already formatted
     */
    public String toString()
    {
        return name + description + " It weights about " + weight + " KG.";
    }
}
