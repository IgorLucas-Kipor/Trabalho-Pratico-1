import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */

import java.util.ArrayList;
import java.util.List;

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;// stores exits of this room.
    private ArrayList<Item> itens;
    private int itemID = 0;
    private boolean lock;
    private String key;
    private boolean light;
    private boolean lantern;
    private String roomName;

    /**
     * Create a room described in the game, initially with no set exits or itens
     * @param description The room's description
     * @param roomName The room's name
     * @param light Set's if the room is illuminated or not
     * @param lock Set's if the room is locked or not
     * @param keyName Set's the name of the key to unlock the room
     */
    public Room(String description, String roomName, Boolean light, Boolean lock, String keyName) 
    {
        this.description = description;
        this.roomName = roomName;
        this.light = light;
        this.lock = lock;
        if (keyName != null && lock == true) {
            key = keyName;
        }
        else if (keyName == null && lock == true) {
            System.out.println("Error in the room creation! The room "+roomName+" is locked but there isn't any key defined!");
            key = null;
        }
        else if (keyName != null && lock == false) {
            System.out.println("Error in the room creation! The room "+roomName+" isn' t locked but has a key defined!");
            key = keyName;
        }
        exits = new HashMap<String, Room>();
        itens = new ArrayList<>();
    }

    /**
     * Creates a room with only description, name and light
     * @param description The room's description
     * @param roomName The room's name
     * @param light Set's if the room is illuminated or not
     */
    public Room(String description, String roomName, Boolean light) 
    {
        this.description = description;
        this.roomName = roomName;
        this.light = light;
        lock = false;
        exits = new HashMap<String, Room>();
        itens = new ArrayList<>();
    }

    /**
     * Gives access to the room name
     * @return The room's name
     */
    public String getRoomName(){
        return roomName;
    }

    /**
     * @return true if the room is illuminated
     */
    public boolean getLight(){
        return light;
    }

    /**
     * @returns the name of the item that acts as the room's key
     */
    public String getKeyName() {
        return key;
    }

    /**
     * @return true if the room is locked
     */
    public boolean getLock() {
        return lock;
    }

    /**
     * @return the list of itens in the room
     */
    public ArrayList<Item> getItens() {
        return itens;   
    }

    /**
     * @param The position of a item in the room's list
     * @return The item in said position
     */
    public Item getItem(int index) {
        return itens.get(index);
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {

        if (light == true || Game.conditionLight == true) {
            return "You are " + description + ".\n" + printItens() + getExitString();
        }
        else {
            return "The room is too dark to see something! Try to find a source of light." + "\n" + getExitString();
        }
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * @param lock Set's the status of the room's lock
     */
    public void setLock(boolean lock) {
        this.lock = lock;
    }

    /**
     * Set's the list of itens in the room
     * @param itens The new list of itens in the room
     */
    public void setItens(ArrayList<Item> itens) {
        this.itens = itens;
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
        this.light = light;
    }

    /**
     * @param light Set's the status of the room's light
     */
    public void setLight(boolean light) {
        this.light = light;
    }

    /**
     * Creates a item linked to the room
     * @param name The item name
     * @param description The item description
     * @param detailedDescription A expanded, detailed description of the item, with hints to the player
     * @param weight The item weight
     * @param hidden Set's if the item is visible or not
     */
    public void createItem(String name, String description, String detailedDescription, Double weight, Boolean hidden) {
        itens.add(new Item(name, description, detailedDescription, weight, hidden));
    }

    /**
     * Prints all the visible items in the room alongside the room description
     * @return a String listing all the items in the room
     */
    public String printItens() {
        int i = 0;
        String itensInside = "";
        for (Item item : itens) {
            if (item.isHidden() == true) {

            } else {
                if (i == 0) {
                    itensInside += "Inside the room you can see a " + item + "\n";
                } else if (i > 0) {
                    itensInside += "You can also see a " + item + "\n";
                }
                i++;
            }
        }

        if (itensInside == null) {
            itensInside = "There are no itens inside this room.";   
        }
        return itensInside;
    }
}

