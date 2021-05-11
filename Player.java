import java.util.ArrayList;

/**
 * Escreva a descrição da classe Player aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class Player
{
    // variáveis de instância - substitua o exemplo abaixo pelo seu próprio
    private ArrayList<Item> inventory;
    private Double maxCarryWeight;
    private Double currentWeight = 0.0;
    private Room currentRoom;
    private String currentRoomName;

    /**
     * Creates the player and his inventory.
     * @param maxCarryWeight Is the maximum amount the player is able to carry
     */
    public Player(Double maxCarryWeight) {
        inventory = new ArrayList<>();
        this.maxCarryWeight = maxCarryWeight;
        currentRoom = null;
    }

    /**
     * Gives access to the player current room
     * @return The player current room in the game
     */
    public Room getCurrentRoom() {
        return currentRoom;   
    }

    /**
     * Gives access to the name of the room the player is currently in
     * @return Is the name of the current room
     */
    public String getCurrentRoomName(){
        return currentRoomName;
    }

    /**
     * Set's the current room value.
     * @param currentRoom is the room the player is currently in
     */
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    /**
     * Set's the current room name.
     * @param currentRoomName is the name of the room in which the player is in.
     */
    public void setCurrentRoomName(String currentRoomName){
        this.currentRoomName = currentRoomName;
    }

    /**
     * Gives access to the player inventory
     * return The list of items in the player current inventory
     */
    public ArrayList<Item> getInventory() {
        return inventory;   
    }

    /**
     * Take an item from the room.
     * @param itemName is the second word the player has to type, in the format of the name of the item, identifying which item to take.
     */
    public boolean takeItem(String itemName) {
        ArrayList<Item> item = currentRoom.getItens();
        Item itemToRemove = null;
        boolean verify = false; //serves to keep track if there is said item in the room
        boolean verifyWeight = false;
        int aux = 0;
        for (Item i : item) {
            //gets the item weight
            double itemWeight = item.get(aux).getWeight();
            if (itemName.equals(i.getName())) {
                verify = true;
                //checks if the item weights less or equal to what you can carry
                if (itemWeight <= (maxCarryWeight - currentWeight)){
                    if (i.isHidden() == true) {
                        System.out.println("There is no such item in the room");
                    } else {
                        verifyWeight = true;
                        currentWeight += itemWeight;
                        inventory.add(i);
                        itemToRemove = i;
                    }
                }
                //prints a error message if the item is too heavy for the player
                else{
                    System.out.println("The "+itemName+" is too heavy for you to carry! Drop something, if you want to pick it up.");
                }
            }
            aux++;
        }
        item.remove(itemToRemove);
        //prints a error message if verify is false
        if (verify == false) {
            System.out.println("There is no such item in the room.");   
        }
        if (verifyWeight == false){
            verify = false;
        }
        currentRoom.setItens(item);
        return verify;
    }

    /**
     * Drop a item from the player inventory.
     * @param itemName is the second word the player has to type, in the format of the name of the item, indetifying which item to drop. 
     */
    public boolean dropItem(String itemName) {
        boolean verify = false;
        Item itemToRemove = null;
        for (Item i : inventory) {
            if (itemName.equals(i.getName())) {
                currentWeight -= i.getWeight(); //removes the item weight from the player current weight
                currentRoom.getItens().add(i);
                itemToRemove = i;
                verify = true;
            }
        }
        inventory.remove(itemToRemove);
        //prints a error if the player does not have the item in their inventory
        if (verify == false) {
            System.out.println("You have no such item in your inventory");   
        }
        return verify;
    }

    /**
     * Return in the console the player inventory, so the player can see what items he has
     * Additionally shows his current weight and max weight.
     */
    public void checkInventory() {
        System.out.println("Checking your inventory, you find that you have:");
        short counter = 0;
        for (Item i : inventory) {
            System.out.print("a " + i.getName()+",");
            if (counter >= 1) {
                System.out.print(" a " + i.getName()+",");
            }
        }
        System.out.print(" and your current weight is: "+currentWeight+"/"+maxCarryWeight);
        System.out.println();
    }

}