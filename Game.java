/**
 *  This class is the main class of the "Detective Pink Panther" application. 
 *  "Detective Pink Panther" is a very simple, text based adventure and investigation game.
 *  Users can walk around some scenery, interact with objects and try to solve puzzles.
 *  
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Guilherme Matos and Igor Lucas
 * @version 2008.03.30
 */

public class Game 
{
    private Parser parser;
    private Player player;
    private String endRoom;
    public static boolean conditionLight;
    private boolean conditionKey;
    private boolean endgame = false;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        player = new Player(15.0);
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together. Additionaly, set's the player initial room and creates the items linked to the
     * rooms.
     */
    private void createRooms()
    {
        Room outside, theatre, pub, lab, office, cellar, bathroom, classroomA, classroomB, southCorridor, northCorridor;

        // create the rooms
        outside = new Room("outside the main entrance of the university", "outside", true);
        theatre = new Room("in a lecture theatre", "theatre", false, true, "pair-of-keys");
        pub = new Room("in the campus pub", "pub", true);
        lab = new Room("in a computing lab", "lab", true);
        office = new Room("in the computing admin office", "office", true, true, "pair-of-keys");
        cellar = new Room("in the cellar of the theatre", "cellar", true, true, "bolt-cutter");
        bathroom = new Room("in the stinky fossaw", "bathroom", true);
        classroomA = new Room("in a classroom for students in their first year on the course, with many tables and a desk lying around",
            "classroomA", true);
        classroomB = new Room("in a classroom for students in their second year on the course.\nIt's a mess, with tables and chairs scattered"+
            " all over because of the previous investigation", "classroomB", true);
        southCorridor = new Room("at a corridor connecting the outside to the main building.\nSigns on the left and right indicate the"+
            " classrooms for first and second year, respectively.\nThe corridor continues north",
            "southCorridor", true);
        northCorridor = new Room("at a corridor connecting the south corridor to the rest of the first floor.\nSigns on the left and"
            +" right indicate the computer lab and theatre, respectively.\nTo the north, a sign says 'Main Office'", "northCorridor", true);

        // initialise room exits
        outside.setExit("north", southCorridor);
        outside.setExit("west", pub);

        theatre.setExit("west", northCorridor);
        theatre.setExit("down", cellar);

        pub.setExit("east", outside);

        lab.setExit("east", northCorridor);

        office.setExit("south", northCorridor);
        office.setExit("east", bathroom);

        cellar.setExit("up", theatre);

        bathroom.setExit("west", office);

        classroomA.setExit("east", southCorridor);

        classroomB.setExit("west", southCorridor);

        southCorridor.setExit("south", outside);
        southCorridor.setExit("west", classroomA);
        southCorridor.setExit("east", classroomB);
        southCorridor.setExit("north", northCorridor);

        northCorridor.setExit("south", southCorridor);
        northCorridor.setExit("west", lab);
        northCorridor.setExit("east", theatre);
        northCorridor.setExit("north", office);

        //set itens in rooms

        office.createItem("flashlight", " with around half it's battery left.\nCan be used to flash light at things. Duh.",
            "A flashlight that can be used to illuminate dark places", 1.0, true);
        office.createItem("note", "a note, presumably left by the admin.\nMay contain useful information", "March 24th, 2018\nMiles lost"+
            " the keys to the cellar again. While we try to arrange some new ones,\nwe're keeping the door open with some chains and a padlock"+
            " to lock it up.\n\nMarch 25th, 2018\nGoddammit, Steve lost damn keys to the padlock! Now I need to ask ol' Frank at the pub\nfor"+
            " some bolt cutters if I wanna get in the cellar. Just great.", 0.1, true); 
        theatre.createItem("gameboy", ", thrown away recently.",
            "It looks recently discarded. Might be a clue.", 2.0, true);
        lab.createItem("table",", heavy and probably useless right now.",
            "It's made of sturdy wood and has many drawers. Maybe there's something in them?", 15.0, false);
        lab.createItem("pair-of-keys", ", two of them, old and slightly rusty.",
            "The tags on the keys say 'Main Office' and 'Theatre'.", 0.5, true);
        classroomA.createItem("diary", " with a 'Hello Kitty' logo.\nMaybe there's something interesting in it?",
            "March 23th, 2018\nBy God, that Greg kid is a pain in the butt! Always sticking his nose where it doesn't belong.\nHe dared to"+
            " tell me that one 'shouldn't smoke weed' on school grounds and that he was going to 'report me to the principal'.\nAs if he's"+
            " got anything to do with this.\nIn times like this I'm glad I have Brad. I'm gonna ask him to deal with this. I'm sure this"+
            " idiot will not bother me again.\n\nMarch 24th, 2018\nThe idiot beat the kid! I wanted Brad to scare him a little, not punch"+
            " the boy!\nI screamed at him and after a fight, I broke up with him. I love the guy, but I don't want to be together with a brute!\n\n"+
            "March 29th, 2018\nGreg has gone missing. And Brad is acting weird. Oh God... please tell he did not do what I think he did...",
            2.0, true);
        classroomB.createItem("notebook", " that seemingly belongs to one of the students.\nMaybe there's some sort of clue in it?",
            "March 23th, 2018\nJess has been complaining about a little bitch called Greg. Told me he said he was gonna snitch on her.\nShe said she wanted me to 'talk to him', but I"+
            " know what she really wanted. I would like to him snitch with two teeth missing, haha.\n\nMarch 24th, 2018\nThat hoe broke up "+
            "with me! With ME! All because I punched a nerd? She asked for it!\nThat damn little snitch... that's all his fault! Oh, but he's"+
            " gonna pay. I called Adam, and we're gonna teach him a lesson!\n\nMarch 26th, 2018\nShit, shit, shit... the little shit"+
            " came to me today. Said he was going to the cops since we broke his arm. Goddammit, I can't go to jail!\nAdam said he stole the"+
            " padlock keys from Steve's office.\nHe says we can grab the little snitch, knock him out and trap him in cellar, then toss the"+
            " keys out.\nHe will never get out and we'll never get in trouble.\nI'm not a killer, but... I still won't be one if he dies"+
            " from natural causes, right?", 2.0, true);
        pub.createItem("bolt-cutter", ", heavy and rusted, but still very much functional",
            "A small label on the handle says 'Property"+
            " of Frank, do not touch'.",8.0, true);

        player.setCurrentRoom(outside);
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        if (finished == true){
            System.out.println("Thank you for playing Detective Pink Panther!");
        }
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Detective Pink Panther Standard Edition! For the full experience, please buy the DLC NOW for R$200.");
        System.out.println("Detective Pink Panther is a new investigation game,"+
            "\nWhere you are trying to uncover the disappearence of a student in the IFMG"+
            " university campus.");
        System.out.println("You are restricted to investigate only the first"+
            " floor, but you can feel that the student - Greg - is there.\nSearch"+
            " around the floor, try to find some clues, and find your way to rescue"+
            " the student!");
        System.out.println("\nType 'help' if you need help.");
        System.out.println();
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;
        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord().toLowerCase();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        } else if (commandWord.equals("search")) {
            searchRoom();   
        }
        else if (commandWord.equals("look")) {
            look();   
        } else if (commandWord.equals("take")) {
            take(command);   
        } else if (commandWord.equals("examine")){
            examine(command); 
        } else if (commandWord.equals("drop")) {
            drop(command);
        } else if (commandWord.equals("inventory")) {
            inventory();
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Prints the description of the room and visible items to the player.
     */
    private void look()
    {
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    /**
     * Searches the room, removing any hidden attributes from occult items in said room.
     */
    private void searchRoom() {
        short counter = 0;
        for (Item i : player.getCurrentRoom().getItens()) {
            if (i.isHidden() == true) {
                i.setHidden(false);
                counter++;
                if (counter == 1) {
                    System.out.println("Searching the room, you found:");
                    System.out.println("A " + i.getName());
                } else if (counter > 1) {
                    System.out.println("A " + i.getName());   
                }
            }
        }
        //if therer is no hidden item
        if (counter == 0) {
            System.out.println("After searching the room, you found no hidden"
                + " objects.");
        }
    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are the great detective Pink Panther!");
        System.out.println("You are at the IFMG university campus to try to find the missing student, who has gone missing 5 days ago.");
        System.out.println("Try to interact with objects and find clues to solve the mystery.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.listCommands());
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * @param command The command given to take this action, using it's second word as a parameter.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            if (nextRoom.getLock() == true) {
                for (Item i : player.getInventory()) {
                    if (i.getName().equals(nextRoom.getKeyName())) {
                        conditionKey = true;
                    }
                }
            }
            if (conditionKey == false && nextRoom.getLock() == true) {
                System.out.println("The door is locked! Try to find a key or another way in.");
            }
            else {
                for (Item i : player.getInventory()) {
                    if (i.getName().equals("flashlight")) {
                        conditionLight = true;
                    }
                }
                if (nextRoom.getRoomName().equals("cellar")) {
                    endgame = true;
                    endgame();
                }
                player.setCurrentRoom(nextRoom);
                player.setCurrentRoomName(nextRoom.getRoomName());
                System.out.println(player.getCurrentRoom().getLongDescription());
                conditionLight = false;
            }
        }
    }

    /**
     * Prints a item long, detailed description
     * @param command Is the command given to the action, taking it's second word as a parameter
     */
    private void examine(Command command) {
        //checks if you informed the item
        if (!command.hasSecondWord()) {
            System.out.println("What item do you want to examine?");   
        } else {
            String name = command.getSecondWord();
            boolean isInRoom = false;
            boolean isInInventory = false;
            //checks if the item is in the room
            for (Item i : player.getCurrentRoom().getItens()) {
                if (i.getName().equals(name)) {
                    System.out.println(i.getDetailedDescription());
                    isInRoom = true;
                }
            }
            //if the item is not in the room, checks if it is in the player inventory
            if (isInRoom == false) {
                for (Item i : player.getInventory()) {
                    if (i.getName().equals(name)) {
                        System.out.println(i.getDetailedDescription());
                        isInInventory = true;
                    }
                }
            }
            //prints a message saying that the item does not exist if it's not in the player inventory or room
            if (isInRoom == false && isInInventory == false) {
                System.out.println("There is no such item.");   
            }
        }
    }

    /**
     * Takes a item from the ground and adds it to the player inventory
     * @param command Is the command given to take said action, taking it's second word as a parameter.
     */
    private void take (Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("What item do you want to take?");   
        } else {

            String item = command.getSecondWord();
            /* tries to take the item. If it can be taken, prints a message saying it was taken. If not, prints a error message from the
            player takeItem method */

            if (player.takeItem(item) == true) {
                System.out.println("You took the " + item + " and added it to the"
                    +" inventory");
            }
        }
    }

    /**
     * Drops a item from the player inventory to the ground.
     * @param command Is the command given to take said action, using it's second word to determine which item to drop.
     */
    private void drop (Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("What item do you want to drop?");
        } else {

            String item = command.getSecondWord();
            /* attempts to drop the item. Prints a confirmation message if it could be dropped or a error message from the player dropItem
            method if it couldn't */
            if (player.dropItem(item) == true) {
                System.out.println("You dropped the " + item + " to the ground.");   
            }
        }
    }

    /**
     * Prints a list of all the items in the player inventory.
     */
    private void inventory () {
        player.checkInventory();   
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * Prints the endgame message if the player reached the final room.
     */
    private void endgame() {
        System.out.println("As you break the chains keeping the cellar closed and enter, the soft cries and sobs of a young boy start"+
            " to echo:\n'Please... please, don't hurt me.'\nYou have rescued the boy, Greg! He is hungry and thirsty, but otherwise fine."+
            "\nEvidence was also found linking two students, Adam and Brad, to his kidnapping. They shall pay for their crime.\nAs the young"+
            " boys are taken out, you shake your head in dismay. 'So young, to fall into the claws of crime'.\n\nIt's a sad thing to see a"+
            "crime like that, but at least you can take some joy in knowing that the criminals will always pay.\nBecause you"+
            " are here.\nAnd you are DETECTIVE PINK PANTHER!\n\n");
        System.out.println("Congratulations, you won the game! You found the student, being kept at the cellar of the theatre.\n"+
            "Feel free to look around a little more if you want. Maybe find the diaries or check all the rooms.\nPlease, type 'quit'"+
            " when you want to quit the game! Hope you had fun!\n\n");
    }
}
