/**
 * <p>This class stores and allows the manipulation/retrieval of vehicle attributes.
 * This class was created as the original way to store and retrieve vehicle information in a java object.</p>
 * Ryan Bookram<br>
 * Software Development 1 (14877)<br>
 * November 15, 2025<br>
 * Vehicle.java<br>
 */
public class Vehicle {
    private int vinNumber;
    private String make;
    private String model;
    private double price;
    private String trans;
    private int miles;
    private String color;
    private String drivetrain;
    private String condition;

    /**
     * Vehicle Constructor
     * <p>This Vehicle constructor takes in all the necessary vehicle attributes to initialize a vehicle object.</p>
     * @param vinNumber The vehicle's VIN number as an INT
     * @param make The vehicle's make as a STRING
     * @param model The vehicle's model as a STRING
     * @param price The price of the vehicle as a DOUBLE
     * @param trans The transmission of the vehicle as a STRING
     * @param miles The miles on the vehicle as an INT
     * @param color The color of the vehicle as a STRING
     * @param drivetrain The drivetrain of the vehicle as a STRING
     */
    public Vehicle(int vinNumber, String make, String model, double price, String trans, int miles, String color, String drivetrain) {

        String tempValue = String.format("%.2f", price);

        this.vinNumber = vinNumber;
        this.make = make;
        this.model = model;
        this.price = Double.parseDouble(tempValue);
        this.trans = trans;
        this.miles = miles;
        this.color = color;
        this.drivetrain = drivetrain;
    }

    public void setVinNumber(int vinNumber) {
        this.vinNumber = vinNumber;
    }

    public int getVinNumber() {
        return vinNumber;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getMake() {
        return make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }

    public String getTrans() {
        return trans;
    }

    public void setMiles(int miles) {
        this.miles = miles;
    }

    public int getMiles() {
        return miles;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setDrivetrain(String drivetrain) {
        this.drivetrain = drivetrain;
    }

    public String getDrivetrain() {
        return drivetrain;
    }

    /**
     * <p>This method estimates this current Vehicle object's condition using the current mileage.</p>
     * @return This vehicle object's estimated condition as a STRING.
     */
    public String evaluate() {

        if(miles >= 0 && miles <= 10000) {
            condition = "New";
        }
        else if(miles > 10000 && miles <= 50000) {
            condition = "Maintained";
        }
        else if (miles > 50000 && miles <= 100000) {
            condition = "Used";
        }
        else if (miles > 100000 && miles <= 200000) {
            condition = "Worn";
        }
        else if (miles > 200000 && miles <= 300000) {
            condition = "Old";
        }
        else {
            condition = "Unavailable (Invalid Mileage)";
        }
        return condition;
    }

    /**
     * <p>Overrides the toString method to return the attributes of this vehicle.</p>
     * @return A STRING representation of this Vehicle object.
     */
    @Override
    public String toString() {
        return vinNumber + ", " + make + ", " + model + ", " + price + ", " + trans + ", " + miles + ", " + color + ", " + drivetrain;
    }
}
