import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.sql.*;

/**
 * <p>This class allows the storage and manipulation of Vehicle information in a SQLite database.
 * This class is mainly used for the CRUD operations done to the SQLite database.
 * This class is dependent on the Vehicle class.</p>
 * Ryan Bookram<br>
 * Software Development 1 (14877)<br>
 * November 15, 2025<br>
 * VehicleManager.java<br>
 */
public class VehicleManager {
    public ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();

    /**
     * <p>Adds a singular vehicle to the SQLite database using the input attributes and statement object.</p>
     * @param inputVin Vehicle VIN number as an INT
     * @param inputMake Vehicle make as a STRING
     * @param inputModel Vehicle model as a STRING
     * @param inputPrice Vehicle price as a DOUBLE
     * @param inputTrans Vehicle Transmission as a STRING
     * @param inputMiles Vehicle miles as an INT
     * @param inputColor Vehicle color as a STRING
     * @param inputDrive Vehicle drivetrain as a STRING
     * @param stmt The SQL statement object to be executed
     * @return A STRING, indicating if the process was successful or not.
     */
    public String addVehicle(String inputVin, String inputMake, String inputModel, String inputPrice, String inputTrans, String inputMiles, String inputColor, String inputDrive, Statement stmt) {

        int vinNumber;
        String make;
        String model;
        double price;
        String trans;
        int miles;
        String color;
        String drivetrain;

        try {

            vinNumber = Integer.parseInt(inputVin);
            make = inputMake;
            model = inputModel;
            price = Double.parseDouble(inputPrice);
            trans = inputTrans;
            miles = Integer.parseInt(inputMiles);
            color = inputColor;
            drivetrain = inputDrive;

            stmt.executeUpdate("INSERT INTO vehicles VALUES (" + vinNumber + ", '" + make + "', '" + model + "', " + price + ", '" + trans + "', " + miles + ", '" + color + "', '" + drivetrain + "')");

            return "Vehicle added successfully!";
        }
        catch(Exception e) {
            return "Failed to add vehicle. Ensure values are entered correctly.";
        }
    }

    /**
     * <p>An overloaded method that adds a singular vehicle to the SQLite database using the input string sequence and statement object.</p>
     * @param info A STRING, containing a vehicle's attributes in the format: VIN-Make-Model-Price-Trans-Miles-Color-Drivetrain
     * @param stmt The SQL statement object to be executed
     * @return A STRING, indicating if the process was successful or not.
     */
    public String addVehicle(String info, Statement stmt) {

        int vinNumber;
        String make;
        String model;
        double price;
        String trans;
        int miles;
        String color;
        String drivetrain;
        String[] splitInfo  = info.split("-");

        try {
            if (splitInfo.length != 8) {
                return "Invalid format. Ensure 8 attributes are entered and are separated by dashes with no spaces. Format: VIN-Make-Model-Price-Trans-Miles-Color-Drivetrain";
            }
            else {

                vinNumber = Integer.parseInt(splitInfo[0]);
                make = splitInfo[1];
                model = splitInfo[2];
                price = Double.parseDouble(splitInfo[3]);
                trans = splitInfo[4];
                miles = Integer.parseInt(splitInfo[5]);
                color = splitInfo[6];
                drivetrain = splitInfo[7];

                stmt.executeUpdate("INSERT INTO vehicles VALUES (" + vinNumber + ", '" + make + "', '" + model + "', " + price + ", '" + trans + "', " + miles + ", '" + color + "', '" + drivetrain + "')");

                return "Vehicle added successfully!";
            }
        }
        catch(Exception e) {
            return "Failed to add vehicle.";
        }
    }

    /**
     * <p>Adds multiple vehicles to the SQLite database using a text file as reference.
     * This method relies on the overloaded addVehicle method and for each text file line to have the format VIN-Make-Model-Price-Trans-Miles-Color-Drivetrain.</p>
     * @param fileName The text file to be opened
     * @param stmt The SQL statement object to be executed
     * @return A STRING, indicating if the process was successful or not.
     */
    public String addFromText(String fileName, Statement stmt) {

        File inputFile = null;
        BufferedReader br = null;
        String vehicle;

        try {

            inputFile = new File(fileName);
            br = new BufferedReader(new FileReader(inputFile));

            while((vehicle = br.readLine()) != null) {
                addVehicle(vehicle, stmt);
            }

            return "Vehicles added successfully!";
        }
        catch(Exception e){
            return "An error has occurred. Please ensure your file path is correct and your text file is formatted correctly.";
        }
    }

    /**
     * <p>Removes a vehicle from the SQLite database using the vehicle's VIN number as reference.</p>
     * @param vin The VIN number of the vehicle to be deleted
     * @param stmt The SQL statement object to be executed
     * @return A STRING, indicating if the process was successful or not.
     */
    public String removeVehicle(String vin, Statement stmt) {

        try{
            stmt.executeUpdate("DELETE FROM vehicles WHERE VIN = '" + vin + "'");
        }
        catch(Exception e){
            return e.getMessage();
        }
        return "Vehicle removed successfully!";
    }

    /**
     * <p>Updates a singular vehicle in the SQLite database using the input attributes and statement object.</p>
     * @param inputVin Vehicle VIN number as a STRING
     * @param inputMake Vehicle make as a STRING
     * @param inputModel Vehicle model as a STRING
     * @param inputPrice Vehicle price as a STRING
     * @param inputTrans Vehicle Transmission as a STRING
     * @param inputMiles Vehicle miles as a STRING
     * @param inputColor Vehicle color as a STRING
     * @param inputDrive Vehicle drivetrain as a STRING
     * @param stmt The SQL statement object to be executed
     * @return A STRING, indicating if the process was successful or not.
     */
    public String updateVehicle(String inputVin, String inputMake, String inputModel, String inputPrice, String inputTrans, String inputMiles, String inputColor, String inputDrive, Statement stmt) {

        String vinNumber;
        String make;
        String model;
        String price;
        String trans;
        String miles;
        String color;
        String drivetrain;
        String output = "";

        try {

            vinNumber = inputVin;
            make = inputMake;
            model = inputModel;
            price = inputPrice;
            trans = inputTrans;
            miles = inputMiles;
            color = inputColor;
            drivetrain = inputDrive;

            stmt.executeUpdate("UPDATE vehicles SET vin = " + vinNumber + ", make = '" + make + "', model = '" + model + "', price = " + price + ", trans = '" + trans + "', miles = " + miles + ", color = '" + color + "', drive = '" + drivetrain + "' WHERE vin = " + vinNumber + ";");

            output = "Vehicle updated successfully!";

        }
        catch (Exception e) {
            output = "Error updating vehicle.";
        }
        return output;
    }

    /**
     * <p>Evaluates a given vehicle from the SQLite database based on its mileage.
     * This method is dependent on the Vehicle object.</p>
     * @param vehicle The information of the vehicle to be evaluated
     * @param stmt The SQL statement object to be executed
     * @return The evaluated condition of the vehicle as a STRING
     */
    public String evaluateVehicle(String vehicle, Statement stmt) {

        String eval;
        int vinNumber;
        String make;
        String model;
        double price;
        String trans;
        int miles;
        String color;
        String drivetrain;
        String[] splitInfo  = vehicle.split(", ");

        try{
            vinNumber = Integer.parseInt(splitInfo[0]);
            make = splitInfo[1];
            model = splitInfo[2];
            price = Double.parseDouble(splitInfo[3]);
            trans = splitInfo[4];
            miles = Integer.parseInt(splitInfo[5]);
            color = splitInfo[6];
            drivetrain = splitInfo[7];

            Vehicle tempV = new Vehicle(vinNumber, make, model, price, trans, miles, color, drivetrain);
            eval =  "The condition of the " + tempV.getMake() + " " + tempV.getModel() + " (" + vinNumber + ") is: " + tempV.evaluate();
        }
        catch(Exception e){
            eval = "Error evaluating vehicle.";
        }
        return eval;
    }
}
