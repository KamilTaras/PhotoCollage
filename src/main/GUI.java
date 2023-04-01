package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import static main.ProcessedImage.*;

public class GUI extends JFrame implements ActionListener {


    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 300;

    private JButton openButton, saveButton, makingButton, colorButton;
    private JLabel imageLabel, counterLabel;

    private JFileChooser fileChooser;

    private JPanel colorPanel;


    private BufferedImage basicImage = new ProcessedImage().createABackground();
    private int height = basicImage.getHeight();

    private ArrayList<BufferedImage> images = new ArrayList<>();

    public GUI() {
        setTitle("CollageMaker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        openButton = new JButton("Open");
        openButton.addActionListener(e -> useOpenButton());
        counterLabel = new JLabel("You have uploaded: " + images.size() + " photos");

        makingButton = new JButton("Make an collage");
        makingButton.addActionListener(e -> useMakingButton());

        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> useSaveButton());

        colorButton = new JButton("Choose color of background");
        colorButton.addActionListener(e -> useColorButton());

        JPanel buttonPanel = new JPanel();
        JPanel buttonPanel2 = new JPanel();

        buttonPanel.add(openButton);
        buttonPanel.add(counterLabel);
        buttonPanel.add(colorButton);


        buttonPanel2.add(makingButton);
        buttonPanel2.add(saveButton);
//        add(counterLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.NORTH);
        add(buttonPanel2, BorderLayout.SOUTH);

        imageLabel = new JLabel();

        JScrollPane scrollPane = new JScrollPane(imageLabel);
        add(scrollPane, BorderLayout.EAST);

        fileChooser = new JFileChooser();

        setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }

    public void useOpenButton() {
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                images.add(ImageIO.read(file));
                counterLabel.setText("You have uploaded: " + images.size() + " photos");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error opening file.");
            }
        }
    }

    public void useMakingButton() {
        int xShift = height / 8;
        try {
            for (int i = 0; i < images.size(); i++) {
                overlayImage(basicImage, images.get(i), xShift);
                xShift += (5 * height) / 8;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error creating collage.");
        }
    }

    public void useSaveButton() {
        try {
            savingImages(basicImage, "UItest");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving collage.");
        }

    }

    public void useColorButton(){
        Color selectedColor = JColorChooser.showDialog(this, "Choose Color", Color.WHITE);
        if (selectedColor != null) {
            colorPanel.setBackground(selectedColor);
            }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}