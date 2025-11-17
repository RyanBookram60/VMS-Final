import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Ryan Bookram
 * Software Development 1 (14877)
 * October 18, 2025
 * VehicleManagerTest.java
 * This class serves the purpose of providing unit tests for the CRUD methods of VehicleManager.java
 */
class VehicleManagerTest {

    VehicleManager vm = null;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        vm = new VehicleManager();
        vm.vehicleList.add(new Vehicle(53920, "BMW", "M3", 195000.107, "Sequential", 5000, "Silver", "RWD"));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Add Vehicle Test")
    void addVehicle() {
        int origLength = vm.vehicleList.size();
        //vm.addVehicle("28495-Toyota-Supra-96000.50-Manual-15000-Red-RWD");
        assertEquals(origLength+1, vm.vehicleList.size());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Add From Text File Test")
    void addFromText() {
        int origLength = vm.vehicleList.size();
        //vm.addFromText("\\C:\\Users\\Ryan Bookram\\IdeaProjects\\Vehicle Managment System\\res\\30_Vehicle_Samples.txt\\");
        assertTrue(vm.vehicleList.size() > origLength);
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Remove Vehicle Test")
    void removeVehicle() {
        int origLength = vm.vehicleList.size();
        //vm.removeVehicle(53920);
        assertEquals(origLength-1, vm.vehicleList.size());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Update Vehicle Test")
    void updateVehicle() {
        Vehicle origVehicle = vm.vehicleList.get(0);
        //vm.updateVehicle("53920-BMW-M3-45000.00-Manual-5000-Silver-RWD");
        assertNotEquals(origVehicle.toString(), vm.vehicleList.get(0).toString());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Evaluate Vehicle Test")
    void evaluateVehicle() {
        int miles = vm.vehicleList.get(0).getMiles();
        String condition = "";

        if(miles > 0 && miles <= 10000) {
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
        //assertTrue(vm.evaluateVehicle(53920).contains(condition));
    }
}