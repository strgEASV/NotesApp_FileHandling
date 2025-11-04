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
        // OPTION A: Using BufferedReader / FileReader (classic I/O)
        //   1. Create a BufferedReader from the chosen file.
        //   2. Read line by line and append to a StringBuilder.
        //   3. Update textArea with the file content.
        //   4. Close the reader safely (try-with-resources).
        //
        // OPTION B: Using java.nio.file.Files (modern approach)
        //   1. Use Files.readString(file.toPath(), StandardCharsets.UTF_8)
        //      to read the entire content in one call.
        //   2. Set the result into textArea.

        statusLabel.setText("Opened: " + file.getName());
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
        // OPTION A: Using BufferedWriter / FileWriter (classic I/O)
        //   1. Create a BufferedWriter for currentFile.
        //   2. Write the content from textArea.
        //   3. Close the writer with try-with-resources.
        //
        // OPTION B: Using java.nio.file.Files (modern approach)
        //   1. Use Files.writeString(currentFile.toPath(), content, StandardCharsets.UTF_8)
        //      to save in a single line.

        statusLabel.setText("Saved: " + currentFile.getName());
    }
}