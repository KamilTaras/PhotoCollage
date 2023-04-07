package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static main.ProcessedImage.*;

public class GUI extends JFrame implements ActionListener {

    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 300;
    private Integer height = 1024;

    final private JButton openButton, saveButton, makingButton, colorButton;
    final private JLabel imageLabel, counterLabel, heightLabel;
    final private JTextField heightText;


    private JFileChooser fileChooser;
    private Color backgroundColor = Color.CYAN;

    private BufferedImage basicImage;


    private ArrayList<BufferedImage> images = new ArrayList<>();

    public GUI() {
        setTitle("CollageMaker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        //button section

        colorButton = new JButton("Choose color of background");
        colorButton.addActionListener(e -> useColorButton());

        openButton = new JButton("Open");
        openButton.addActionListener(e -> useOpenButton());
        counterLabel = new JLabel("You have uploaded: " + images.size() + " photos");

        makingButton = new JButton("Make an collage");
        makingButton.addActionListener(e -> useMakingButton());

        saveButton = new JButton("Save");


        heightLabel = new JLabel("Enter height of collage:");
        heightText = new JTextField(10);

        saveButton.addActionListener(e -> useSaveButton());




        //panel section

        JPanel buttonPanelTop = new JPanel();
        JPanel buttonPanelMiddle = new JPanel();
        JPanel buttonPanelBottom = new JPanel();

        buttonPanelTop.add(heightLabel);
        buttonPanelTop.add(heightText);
        buttonPanelTop.add(colorButton);

        buttonPanelMiddle.add(openButton);
        buttonPanelMiddle.add(counterLabel);

        buttonPanelBottom.add(makingButton);
        buttonPanelBottom.add(saveButton);

        add(buttonPanelTop, BorderLayout.NORTH);
        add(buttonPanelMiddle, BorderLayout.CENTER);
        add(buttonPanelBottom, BorderLayout.SOUTH);

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
        try {
            height = Integer.valueOf(heightText.getText());
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, "Provide an Integer value (1024 by default)");
            height = 1024;
        }
        basicImage = new ProcessedImage(2*height,height, backgroundColor).createABackground();
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

    private void useSaveButton() {
        if (basicImage != null) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Collage As");
            fileChooser.setFileFilter(new FileNameExtensionFilter("PNG images (*.png)", "png"));
            fileChooser.setSelectedFile(new File("collage.png"));
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();

                try {
                    ImageIO.write(basicImage, "png", fileToSave);
                    JOptionPane.showMessageDialog(this, "Collage saved successfully!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error saving collage: " + ex.getMessage());
                }
            }
        }
    }


    public void useColorButton(){
        Color selectedColor = JColorChooser.showDialog(this, "Choose Color", Color.WHITE);
        if (selectedColor != null) {
            setBackground(selectedColor);
            backgroundColor = selectedColor;
            }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}