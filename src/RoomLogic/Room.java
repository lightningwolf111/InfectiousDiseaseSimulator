package RoomLogic;
import java.util.*;
import RoomLogic.Person;

public class Room {
    private int dimension;
    private Person[][] room;
    private String input;

    public Room(int dimension, String input){
        try{
            this.dimension = dimension;
            Random random = new Random();
            room = new Person[dimension][dimension];
            if(input.equalsIgnoreCase("autofill")){
                input = "";
                for(int i = 0; i < dimension * dimension; i++){
                    input += i + ",";
                }
            }
            this.input = input;

            Set<String> peopleSet = new HashSet<>();
            for(String person : Arrays.asList(input.split(","))){
                peopleSet.add(person);
            }

            for(int i = 0; i < dimension; i++){
                for(int j = 0; j < dimension; j++){
                    String currentLocation = new Integer((dimension * i) + j).toString();
                    if(peopleSet.contains(currentLocation)){
                        if (random.nextBoolean()) {
                            room[i][j] = new Person(1, i, j);
                        }
                        else {
                            room[i][j] = new Person(0, i, j);
                        }
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("Invalid Input!!!");
            System.out.println("Make sure the dimension is an integer value, and the room input is valid");
        }
    }

    public Room(int dimension, Person[][] room) {
        this.dimension = dimension;
        this.room = room;
    }

    public Room(Room toCopy) {
        this.dimension = toCopy.getDimension();
        room = new Person[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                room[i][j] = toCopy.getPerson(i, j).clone();
            }
        }
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                if(room[i][j] != null){
                    double chance = room[i][j].isDiseasedChance();
                    if ((chance * 100) % 10 == 0) {
                        sb.append(room[i][j].isDiseasedChance() + "  ");
                    } else {
                        sb.append(room[i][j].isDiseasedChance() + " ");
                    }
                }
                else{
                    sb.append("---- ");
                }
            }
            sb.append("<br/>");
        }
        return sb.toString();
    }

//    public void printRoom(){
//        for(int i = 0; i < dimension; i++){
//            for(int j = 0; j < dimension; j++){
//                if(room[i][j] != null){
//                    double chance = room[i][j].isDiseasedChance();
//                    if ((chance * 100) % 10 == 0) {
//                        System.out.print(room[i][j].isDiseasedChance() + "  ");
//                    } else {
//                        System.out.print(room[i][j].isDiseasedChance() + " ");
//                    }
//                }
//                else{
//                    System.out.print("-- ");
//                }
//            }
//            System.out.println();
//        }
//    }

    // Returns the chance of a specific person catching the infectious disease.
    // The person with the given x and y.
    public double chance(int x, int y){
        double chance = -1.0;
        if(room[x][y] != null){
            if (room[x][y].isDiseasedChance() == 1) {
                return 1;
            }
            chance = room[x][y].isDiseasedChance();
            Person temp = room[x][y];
            for(int i = 0; i < dimension; i++){
                for(int j = 0; j < dimension; j++){
                    if (!(x == i && y == j) && (room[i][j] != null)) {
                        double addedChance = (1 - chance) * temp.transmissionChance(room[i][j], temp);
                        //System.out.print(" chance: " + round(addedChance) + " i: " + i + " j: " + j + " ");
                        chance += addedChance;
                    }
                }
                //System.out.println();
            }
        }
        return round(chance);
    }

    // Returns a 2D array of each person in the room's chance of getting infected
    // with the infectious disease.
    public Room chanceMatrix(){
        Room toReturn = new Room(dimension, input);
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                double chance = chance(i,j);
                if (chance != -1) {
                    toReturn.setPerson(i, j, new Person(chance, i, j));
                }
            }
        }
        return toReturn;
    }

    // Returns a 2D array of each person in the room's chance of getting infected
    // with the infectious disease. The input parameter represents the time for which
    // they are in contact
    public Room chanceMatrix(int time) {
        if (time < 0) {
            throw new IllegalArgumentException("Invalid time");
        }
        if (time == 0) {
            return new Room(this);
        }
        return (chanceMatrix().chanceMatrix(time-1));
    }

    // Returns the person at the given coordinates
    // Not sure what people are supposed to be a part of this set yet, but we can figure it out.
    // I think all of the people in the room.
    public Person getPerson(int x, int y){
        return room[x][y];
    }

    public void setPerson(int x, int y, Person person) {
        room[x][y] = person;
    }

    public int getDimension() {
        return dimension;
    }

    // Rounds the val parameter to two decimal places. Can cause overflow problems for large doubles.
    public static double round(double val) {
        val = val*100;
        val = Math.round(val);
        return val /100;
    }

}