package RoomLogic;

public class Person {
    private double diseasedChance; // Chance that a person is diseased
    private int locationX;  // X coordinate
    private int locationY;  // Y coordinate
    private boolean masked; // Determines whether person is masked
    public static final int DISTANCE_BETWEEN_PEOPLE = 7; // scalar for distance between people
    public static final int TIME_FACTOR = 10; // scales transmission chances to represent how long people are in contact

    // Creates a person with a given diseasedChance and location
    public Person(double diseasedChance, int locationX, int locationY) {
        this.diseasedChance = diseasedChance;
        this.locationX = locationX;
        this.locationY = locationY;
        this.masked = false;
    }

    // Creates a person with a given diseasedChance, location, and masked state
    public Person(double diseasedChance, int locationX, int locationY, boolean masked) {
        this.diseasedChance = diseasedChance;
        this.locationX = locationX;
        this.locationY = locationY;
        this.masked = masked;
    }

    // Returns chance of being diseased
    public double isDiseasedChance() {
        return diseasedChance;
    }

    // Clones a person (creates a copy of this person)
    public Person clone() {
        return new Person(diseasedChance, locationX, locationY, masked);
    }

    // Returns the distance between this person and another person
    private double distance(Person other) {
        double dx = locationX - other.locationX;
        double dy = locationY - other.locationY;
        return DISTANCE_BETWEEN_PEOPLE*Math.sqrt(dx*dx + dy*dy);
    }

    // Returns the percent chance of transmission between two people
    public double transmissionChance(Person spreadingFrom, Person spreadingTo) {
        if (spreadingFrom.isDiseasedChance() == 0) {
            return 0.0;
        }
        double x = spreadingFrom.distance(spreadingTo); //proximity
        double chance = 0;
        // Neither wearing mask
        if (spreadingFrom.masked == false && spreadingTo.masked == false) {
            if (x <= 6) {
                chance = 100;
            }
            else if (x >= 24) {
                chance = 0;
            }
            else {
                chance = -(50/9)*x+(400/3);
            }
        }
        // Carrier is wearing a mask, other is not
        else if (spreadingFrom.masked == true && spreadingTo.masked == false) {
            if (x < 6) {
                chance = 30;
            }
            else if (x > 7) {
                chance = 0;
            }
            else {
                chance = -30*x+210;
            }
        }
        // Carrier is not wearing a mask, other is
        else if (spreadingFrom.masked == false && spreadingTo.masked == true) {
            if (x < 6) {
                chance = 50;
            }
            else if (x > 12) {
                chance = 0;
            }
            else {
                chance = -(25/2)*x+100;
            }
        }
        // Both wearing mask
        else {
            if (x < 6) {
                chance = 20;
            }
            else if (x == 6) {
                chance = 10;
            }
            else {
                chance = 0;
            }
        }
        return (chance/100 * spreadingFrom.diseasedChance) / TIME_FACTOR;
    }

}