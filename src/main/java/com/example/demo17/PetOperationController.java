package com.example.demo17;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomManager {

    private final Map<String, List<String>> roomSlots = new HashMap<>();

    public RoomManager() {
        initializeRoomSlots();
    }

    private void initializeRoomSlots() {
        roomSlots.put("Room 1", FXCollections.observableArrayList("Slot 1", "Slot 2", "Slot 3"));
        roomSlots.put("Room 2", FXCollections.observableArrayList("Slot 4", "Slot 5", "Slot 6"));
        roomSlots.put("Room 3", FXCollections.observableArrayList("Slot 7", "Slot 8", "Slot 9"));
    }

    public List<String> getAvailableSlots(String selectedRoom) {
        return roomSlots.getOrDefault(selectedRoom, (List<String>) FXCollections.emptyObservableMap());
    }
}

public class PetOperationController {

    private final RoomManager roomManager = new RoomManager();

    private final TextField petIdField;
    private final ChoiceBox<String> operationRoomChoiceBox;
    private final ChoiceBox<String> slotChoiceBox;
    private final Label invalidIdLabel;

    public PetOperationController(TextField petIdField, ChoiceBox<String> operationRoomChoiceBox,
                                  ChoiceBox<String> slotChoiceBox, Label invalidIdLabel) {
        this.petIdField = petIdField;
        this.operationRoomChoiceBox = operationRoomChoiceBox;
        this.slotChoiceBox = slotChoiceBox;
        this.invalidIdLabel = invalidIdLabel;
        initialize();
    }

    private void initialize() {
        operationRoomChoiceBox.setOnAction(event -> handleRoomSelection());
    }

    private void handleRoomSelection() {
        String selectedRoom = operationRoomChoiceBox.getValue();

        if (selectedRoom != null) {
            List<String> availableSlots = roomManager.getAvailableSlots(selectedRoom);
            slotChoiceBox.setItems(FXCollections.observableArrayList(availableSlots));
            slotChoiceBox.setDisable(false);
        }
    }

    public void handleSubmit() {
        String selectedPetId = petIdField.getText();
        String selectedRoom = operationRoomChoiceBox.getValue();
        String selectedSlot = slotChoiceBox.getValue();
        int parsedPetId;
        try {
            parsedPetId = Integer.parseInt(selectedPetId);
        } catch (NumberFormatException e) {
            invalidIdLabel.setText("Please enter a valid Pet ID.");
            return;
        }

        boolean isValidId = isValidId(parsedPetId);
        if (!isValidId) {
            invalidIdLabel.setText("Invalid Pet ID. Please enter a valid ID.");
            return;
        }

        if (selectedSlot != null && !selectedSlot.isEmpty()) {
            List<String> availableSlots = roomManager.getAvailableSlots(selectedRoom);

            if (availableSlots.contains(selectedSlot)) {
                availableSlots.remove(selectedSlot);
                System.out.println("Pet ID: " + parsedPetId);
                System.out.println("Selected Room: " + selectedRoom);
                System.out.println("Selected Slot: " + selectedSlot);
                clearFields();
            } else {
                System.out.println("The selected slot is not available. Please choose another slot.");
            }
        }
    }

    private void clearFields() {
        petIdField.clear();
        operationRoomChoiceBox.getSelectionModel().clearSelection();
        slotChoiceBox.getItems().clear();
        slotChoiceBox.setDisable(true);
        invalidIdLabel.setText("");
    }

    private boolean isValidId(int id) {
        return id >= 1 && id <= 10;
    }
}
