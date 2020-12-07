package RoomLogic;

public class Person {
    private double diseasedChance;
    private int locationX; // matrix model
    private int locationY;
    private boolean masked; // Maybe use this in the transmission model, but probably for now we'll just assume no masks
    public static final int DISTANCE_BETWEEN_PEOPLE = 7;
    public static final int TIME_FACTOR = 10; // The TIME_FACTOR scales transmission chances and represents how long people are in contact.
    // Higher TIME_FACTOR means less transmission chance.

    public Person(double diseasedChance, int locationX, int locationY) {
        this.diseasedChance = diseasedChance;
        this.locationX = locationX;
        this.locationY = locationY;
        this.masked = false;
    }

    // better
    public Person(double diseasedChance, int locationX, int locationY, boolean masked) {
        this.diseasedChance = diseasedChance;
        this.locationX = locationX;
        this.locationY = locationY;
        this.masked = masked;
    }

    public double isDiseasedChance() {
        return diseasedChance;
    }

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
        // neither wearing mask
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
        // carrier is wearing a mask, other is not
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
        // carrier is not wearing a mask, other is
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
        // both wearing mask
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
