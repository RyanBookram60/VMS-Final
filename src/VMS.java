import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 * <p>This class operates as a Vehicle Managment System, allowing users to store and manipulate vehicle attributes in a SQLite database.
 * This class uses a custom GUI built out of Java swing to allow users to interact with the database.
 * This class is dependent on both the VehicleManager and Vehicle classes.</p>
 * Ryan Bookram<br>
 * Software Development (14877)<br>
 * November 15, 2025<br>
 * VMS.java<br>
 */
public class VMS {
    public static String jdbcURL;
    public static Connection con;
    public static String selectAll = "SELECT * FROM vehicles";
    public static Statement stmt;
    public static ResultSet rs;

    /**
     * <p>Runs the GUI and starts the program.</p>
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                constructGUI();
            }
        });
    }

    /**
     * <p>Creates the design and logic of the GUI.</p>
     */
    public static void constructGUI() {

        VehicleManager vm = new VehicleManager();

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame mainGUI = new JFrame("Vehicle Management System");
        mainGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainGUI.setResizable(false);

        JFrame connectGUI = new JFrame("Vehicle Management System: Connect");
        connectGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        connectGUI.setResizable(false);

        BoxLayout layout1 = new BoxLayout(mainGUI.getContentPane(), BoxLayout.Y_AXIS);
        BoxLayout layout2 = new BoxLayout(connectGUI.getContentPane(), BoxLayout.Y_AXIS);
        GridLayout buttonLayout = new GridLayout(1, 10);
        GridLayout singleLayout = new GridLayout(1, 1);
        mainGUI.setLayout(layout1);
        mainGUI.setMinimumSize(new Dimension(1366, 768));
        mainGUI.setMaximumSize(new Dimension(1366, 768));
        connectGUI.setMinimumSize(new Dimension(192, 144));
        connectGUI.setLayout(layout2);

        JPanel listPanel = new JPanel();
        JPanel infoPanel = new JPanel();
        JPanel buttonPanel = new JPanel(buttonLayout);
        JPanel inputPanel = new JPanel();
        JPanel aftPanel = new JPanel(singleLayout);
        JPanel outputPanel = new JPanel(singleLayout);
        JPanel exitPanel = new JPanel();

        DefaultListModel<String> listModel = new DefaultListModel<String>();
        JList<String> vehicleList = new JList<String>(listModel);
        vehicleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ScrollPane vScroll = new ScrollPane();
        vScroll.add(vehicleList);
        vScroll.setSize(1200, 500);

        JLabel infoLabel = new JLabel("Enter text below. All fields need to have a valid value for ADD/UPDATE to work. Use the bottom text box for AFT (Add from text, requires valid file path). Select a list entry for REMOVE and EVALUATE.");
        JLabel outputLabel = new JLabel("");

        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
        JButton updateButton = new JButton("Update");
        JButton evaluateButton = new JButton("Evaluate");
        JButton addTextButton = new JButton("AFT");
        JButton mainExitButton = new JButton("Exit");
        JButton disconnectButton = new JButton("Disconnect");
        JButton connectExitButton = new JButton("Exit");

        JTextField vinBox = new JTextField();
        JTextField makeBox = new JTextField();
        JTextField modelBox = new JTextField();
        JTextField priceBox = new JTextField();
        JTextField transBox = new JTextField();
        JTextField milesBox = new JTextField();
        JTextField colorBox = new JTextField();
        JTextField drivetrainBox = new JTextField();
        JTextField aftBox = new JTextField();

        vinBox.setPreferredSize(new Dimension(120, 20));
        makeBox.setPreferredSize(new Dimension(120, 20));
        modelBox.setPreferredSize(new Dimension(120, 20));
        priceBox.setPreferredSize(new Dimension(120, 20));
        transBox.setPreferredSize(new Dimension(120, 20));
        milesBox.setPreferredSize(new Dimension(120, 20));
        colorBox.setPreferredSize(new Dimension(120, 20));
        drivetrainBox.setPreferredSize(new Dimension(120, 20));

        JLabel vinLabel = new JLabel("VIN: ");
        JLabel makeLabel = new JLabel("Make: ");
        JLabel modelLabel = new JLabel("Model: ");
        JLabel priceLabel = new JLabel("Price: ");
        JLabel transLabel = new JLabel("Trans: ");
        JLabel milesLabel = new JLabel("Miles: ");
        JLabel colorLabel = new JLabel("Color: ");
        JLabel drivetrainLabel = new JLabel("DT: ");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!vinBox.getText().isEmpty() && !makeBox.getText().isEmpty() && !modelBox.getText().isEmpty() && !priceBox.getText().isEmpty() && !transBox.getText().isEmpty() && !milesBox.getText().isEmpty() && !colorBox.getText().isEmpty() && !drivetrainBox.getText().isEmpty()) {

                    outputLabel.setText(vm.addVehicle(vinBox.getText(), makeBox.getText(), modelBox.getText(), priceBox.getText(), transBox.getText(), milesBox.getText(), colorBox.getText(), drivetrainBox.getText(), stmt));
                    vinBox.setText("");
                    makeBox.setText("");
                    modelBox.setText("");
                    priceBox.setText("");
                    transBox.setText("");
                    milesBox.setText("");
                    colorBox.setText("");
                    drivetrainBox.setText("");

                    listModel.clear();

                    try{
                        rs = stmt.executeQuery(selectAll);
                        while(rs.next()) {
                            listModel.addElement(rs.getString(1) + ", " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(4) +  ", " + rs.getString(5) +  ", " + rs.getString(6) + ", " + rs.getString(7) +  ", " + rs.getString(8));
                        }
                    }
                    catch(Exception x) {
                        outputLabel.setText("Failed to add vehicle.");
                    }
                }
            }
        });

        addTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!aftBox.getText().isEmpty()){
                    outputLabel.setText(vm.addFromText(aftBox.getText(), stmt));
                    aftBox.setText("");
                    listModel.clear();

                    try{
                        rs = stmt.executeQuery(selectAll);
                        while(rs.next()) {
                            listModel.addElement(rs.getString(1) + ", " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(4) +  ", " + rs.getString(5) +  ", " + rs.getString(6) + ", " + rs.getString(7) +  ", " + rs.getString(8));
                        }
                    }
                    catch(Exception x) {
                        outputLabel.setText("Failed to add vehicles.");
                    }
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!vehicleList.isSelectionEmpty()) {
                    String[] splitInfo = vehicleList.getSelectedValue().split(",");
                    String vin = splitInfo[0];
                    outputLabel.setText(vm.removeVehicle(vin, stmt));
                    listModel.clear();

                    try{
                        rs = stmt.executeQuery(selectAll);
                        while(rs.next()) {
                            listModel.addElement(rs.getString(1) + ", " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(4) +  ", " + rs.getString(5) +  ", " + rs.getString(6) + ", " + rs.getString(7) +  ", " + rs.getString(8));
                        }
                    }
                    catch(Exception x) {
                        outputLabel.setText("Failed to remove vehicle.");
                    }

                    vehicleList.clearSelection();
                }
            }
        });

        vehicleList.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_DELETE) {
                    if(!vehicleList.isSelectionEmpty()) {
                        String[] splitInfo = vehicleList.getSelectedValue().split(",");
                        String vin = splitInfo[0];
                        outputLabel.setText(vm.removeVehicle(vin, stmt));
                        listModel.clear();

                        try{
                            rs = stmt.executeQuery(selectAll);
                            while(rs.next()) {
                                listModel.addElement(rs.getString(1) + ", " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(4) +  ", " + rs.getString(5) +  ", " + rs.getString(6) + ", " + rs.getString(7) +  ", " + rs.getString(8));
                            }
                        }
                        catch(Exception x) {
                            outputLabel.setText("Failed to remove vehicle.");
                        }

                        vehicleList.clearSelection();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!vinBox.getText().isEmpty() && !makeBox.getText().isEmpty() && !modelBox.getText().isEmpty() && !priceBox.getText().isEmpty() && !transBox.getText().isEmpty() && !milesBox.getText().isEmpty() && !colorBox.getText().isEmpty() && !drivetrainBox.getText().isEmpty()){
                    boolean exists = false;
                    String[] listInfo;
                    String inputVin;
                    String listVin;

                    inputVin = vinBox.getText();
                    for(int i = 0; i < listModel.size(); i++) {
                        listInfo = listModel.getElementAt(i).split(",");
                        listVin = listInfo[0];

                        if(listVin.equals(inputVin)){
                                exists = true;
                        }
                    }

                    outputLabel.setText("Vehicle entry not found. Ensure the VIN number is correct.");

                    if(exists){
                        outputLabel.setText(vm.updateVehicle(vinBox.getText(), makeBox.getText(), modelBox.getText(), priceBox.getText(), transBox.getText(), milesBox.getText(), colorBox.getText(), drivetrainBox.getText(), stmt));
                    }

                    vinBox.setText("");
                    makeBox.setText("");
                    modelBox.setText("");
                    priceBox.setText("");
                    transBox.setText("");
                    milesBox.setText("");
                    colorBox.setText("");
                    drivetrainBox.setText("");

                    listModel.clear();

                    try{
                        rs = stmt.executeQuery(selectAll);
                        while(rs.next()) {
                            listModel.addElement(rs.getString(1) + ", " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(4) +  ", " + rs.getString(5) +  ", " + rs.getString(6) + ", " + rs.getString(7) +  ", " + rs.getString(8));
                        }
                    }
                    catch(Exception x) {
                        outputLabel.setText("Failed to update vehicle.");
                    }
                }
            }
        });

        evaluateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!vehicleList.isSelectionEmpty()) {
                    outputLabel.setText(vm.evaluateVehicle(vehicleList.getSelectedValue(), stmt));
                }
            }
        });

        disconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    con.close();
                    listModel.clear();
                    mainGUI.setVisible(false);
                    connectGUI.setVisible(true);
                }
                catch(Exception x) {
                    outputLabel.setText("Failed to disconnect database. Try again.");
                }
            }
        });

        mainExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGUI.dispose();
                connectGUI.dispose();
            }
        });

        connectExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGUI.dispose();
                connectGUI.dispose();
            }
        });

        mainGUI.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() != vehicleList && !vehicleList.getBounds().contains(e.getPoint())) {
                    vehicleList.clearSelection();
                }
            }
        });

        listPanel.add(vScroll);
        infoPanel.add(infoLabel);
        buttonPanel.add(addButton);
        buttonPanel.add(addTextButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(evaluateButton);
        buttonPanel.add(vinBox);

        inputPanel.add(vinLabel);
        inputPanel.add(vinBox);
        inputPanel.add(makeLabel);
        inputPanel.add(makeBox);
        inputPanel.add(modelLabel);
        inputPanel.add(modelBox);
        inputPanel.add(priceLabel);
        inputPanel.add(priceBox);
        inputPanel.add(transLabel);
        inputPanel.add(transBox);
        inputPanel.add(milesLabel);
        inputPanel.add(milesBox);
        inputPanel.add(colorLabel);
        inputPanel.add(colorBox);
        inputPanel.add(drivetrainLabel);
        inputPanel.add(drivetrainBox);

        aftPanel.add(aftBox);
        outputPanel.add(outputLabel);
        exitPanel.add(disconnectButton);
        exitPanel.add(mainExitButton);

        mainGUI.add(listPanel);
        mainGUI.add(infoPanel);
        mainGUI.add(buttonPanel);
        mainGUI.add(inputPanel);
        mainGUI.add(aftPanel);
        mainGUI.add(outputPanel);
        mainGUI.add(exitPanel);

        JLabel startUp = new JLabel("Welcome to Vehicle Management System! Enter the file path to your SQLite database file to get started.");
        JLabel connectOutput = new JLabel(" ");
        JTextField connectBox = new JTextField();
        JButton connectButton = new JButton("Connect");
        
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!connectBox.getText().isEmpty()) {
                    jdbcURL = "jdbc:sqlite:\\" + connectBox.getText() + "\\";
                    try {
                        con = DriverManager.getConnection(jdbcURL);
                        stmt = con.createStatement();
                        connectOutput.setText("");
                        connectGUI.setVisible(false);
                        mainGUI.setVisible(true);

                        try{
                            rs = stmt.executeQuery(selectAll);
                            while(rs.next()) {
                                listModel.addElement(rs.getString(1) + ", " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(4) +  ", " + rs.getString(5) +  ", " + rs.getString(6) + ", " + rs.getString(7) +  ", " + rs.getString(8));
                            }
                        }
                        catch(Exception x) {
                            outputLabel.setText("Failed to load database entries. Try to disconnect and reconnect the database.");
                        }
                    } catch (Exception x) {
                        connectOutput.setText("Failed to connect to database. Ensure the file path is correct.");
                    }
                }
            }
        });

        JPanel connectP1 = new JPanel(singleLayout);
        JPanel connectP2 = new JPanel(singleLayout);
        JPanel connectP3 = new JPanel();
        JPanel connectP4 = new JPanel(singleLayout);

        connectP1.add(startUp);
        connectP2.add(connectBox);
        connectP3.add(connectButton);
        connectP3.add(connectExitButton);
        connectP4.add(connectOutput);

        connectGUI.add(connectP1);
        connectGUI.add(connectP2);
        connectGUI.add(connectP3);
        connectGUI.add(connectP4);

        connectGUI.pack();
        mainGUI.pack();
        mainGUI.setVisible(false);
        connectGUI.setVisible(true);

        mainGUI.setBounds(0, 0, 1366, 768);
    }
}