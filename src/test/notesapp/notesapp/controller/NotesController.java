package notesapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

/**
 * Controller class for NotesApp.
 */
public class NotesController {

    private boolean useModern = false;

    @FXML private TextArea textArea;
    @FXML private Label statusLabel;

    private File currentFile;  // Tracks the currently open/saved file

    /** Called when user clicks "New". */
    @FXML
    private void handleNew() {
        textArea.clear();
        currentFile = null;
        statusLabel.setText("New note");
    }

    /** Called when user clicks "Open". */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file == null) return;
        currentFile = file;

        // --- Training area #1 ---
        try {
            if (useModern) {
                // OPTION B: Using java.nio.file.Files (modern approach)
                String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
                textArea.setText(content);
            } else {
                // OPTION A: Using BufferedReader / FileReader (classic I/O)
                StringBuilder sb = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append(System.lineSeparator());
                    }
                }
                textArea.setText(sb.toString());
            }
            statusLabel.setText("Opened: " + file.getName());

        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Error opening file: " + e.getMessage());
            textArea.clear();
        }
    }

    /** Called when user clicks "Save". */
    @FXML
    private void handleSave() {
        if (currentFile == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save File");
            currentFile = fileChooser.showSaveDialog(new Stage());
        }
        if (currentFile == null) return;

        String content = textArea.getText();

        // --- Training area #2 ---
        try {
            if (useModern) {
                // OPTION B: Using java.nio.file.Files (modern approach)
                Files.writeString(currentFile.toPath(), content, StandardCharsets.UTF_8);
            } else {
                // OPTION A: Using BufferedWriter / FileWriter (classic I/O)
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile, StandardCharsets.UTF_8))) {
                    writer.write(content);
                }
            }
            statusLabel.setText("Saved: " + currentFile.getName());

        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Error saving file: " + e.getMessage());
        }
    }
}