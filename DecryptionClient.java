package cop2805;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class DecryptionClient extends JFrame {

    // GUI components
    private JTextArea inputTextArea, outputTextArea;
    private JButton loadButton, decryptButton;

    public DecryptionClient() {
        super("TOP Secret Agent Communication Terminal");

        // Initialize text areas
        inputTextArea = new JTextArea(10, 40);
        outputTextArea = new JTextArea(10, 40);
        outputTextArea.setEditable(false); // Make output area read-only

        // Initialize buttons
        loadButton = new JButton("Select File");
        decryptButton = new JButton("Translate");

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loadButton);
        buttonPanel.add(decryptButton);

        // Layout setup
        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(inputTextArea), BorderLayout.CENTER);  // Encrypted input
        add(new JScrollPane(outputTextArea), BorderLayout.SOUTH);  // Decrypted output

        // Action listeners
        loadButton.addActionListener(e -> loadEncryptionFile());
        decryptButton.addActionListener(e -> decipherMessage());

        // JFrame settings (fix typo here)
        setDefaultCloseOperation(EXIT_ON_CLOSE);  
        pack();
        setLocationRelativeTo(null);              
        setVisible(true);
    }

    // Load encrypted text file into input area
    private void loadEncryptionFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                inputTextArea.setText(""); // Clear previous input
                String line;
                while ((line = reader.readLine()) != null) {
                    inputTextArea.append(line + "\n");
                }

            } catch (IOException e) {
                showError("Error reading the file: " + e.getMessage());
            }
        }
    }

    // Connect to server and decrypt message
    private void decipherMessage() {
        String message = inputTextArea.getText().trim();

        if (message.isEmpty()) {
            showError("Input is empty. Load a file.");
            return;
        }

        // Networking code to send message to server
        try (
            Socket socket = new Socket("127.0.0.1", 1236); 
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            // Send message to server
            out.println(message);

            // Receive decrypted response
            String decrypted = in.readLine();
            outputTextArea.setText(decrypted); 

        } catch (IOException e) {
            showError("Failed to connect to server: " + e.getMessage()); 
        }
    }

    // Display error messages
    private void showError(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Main method to launch GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(DecryptionClient::new);
    }
}
