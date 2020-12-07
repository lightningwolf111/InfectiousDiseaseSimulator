package RoomLogic;

import java.util.Arrays;
import java.util.Random;

public class Room {
    private int dimension;
    private Person[][] room;

    // Constructs a new room of the specified dimension, full of random people
    public Room(int dimension){
        this.dimension = dimension;
        Random random = new Random();
        room = new Person[dimension][dimension];
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                if (random.nextBoolean()) {
                    room[i][j] = new Person(1, i, j);
                } else {
                    room[i][j] = new Person(0, i, j);
                }

            }
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

    

    public void printRoom(){
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                double chance = room[i][j].isDiseasedChance();
                if ((chance * 100) % 10 == 0) {
                    System.out.print(room[i][j].isDiseasedChance() + "  ");
                } else {
                    System.out.print(room[i][j].isDiseasedChance() + " ");
                }
            }
            System.out.println();
        }
    }

    // Returns the chance of a specific person catching the infectious disease.
    // The person with the given x and y.
    public double chance(int x, int y){
        if (room[x][y].isDiseasedChance() == 1) {
            return 1;
        }
        double chance = room[x][y].isDiseasedChance();
        Person temp = room[x][y];
        for(int i = 0; i < dimension; i++){ // I think indices are better since it guarantees logical order
            for(int j = 0; j < dimension; j++){
                if (!(x==i && y == j)) {
                    double addedChance = (1 - chance) * temp.transmissionChance(room[i][j], temp);
                    //System.out.print(" chance: " + round(addedChance) + " i: " + i + " j: " + j + " ");
                    chance += addedChance;
                }
            }
            //System.out.println();
        }
        return round(chance);
    }

    // Returns a 2D array of each person in the room's chance of getting infected
    // with the infectious disease.
    public Room chanceMatrix(){
        Room toReturn = new Room(dimension);
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                toReturn.setPerson(i, j, new Person(chance(i, j), i, j));
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
