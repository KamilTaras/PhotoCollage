package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static main.ProcessedImage.*;

public class UI extends JFrame implements ActionListener {


    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 300;

    private int counter = 0;
    private JButton openButton, saveButton, makingButton;
    private JLabel imageLabel;

    private JFileChooser fileChooser;

//    private ImageIcon imageIcon;

    private BufferedImage basicImage = new ProcessedImage().createABackground();
    private int height = basicImage.getHeight();

    private ArrayList<BufferedImage> images = new ArrayList<>();

    public UI() {
        setTitle("CollageMaker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        openButton = new JButton("Open");
        openButton.addActionListener(this);

        makingButton = new JButton("Make an collage");
        makingButton.addActionListener(this);

        saveButton = new JButton("Save");
        saveButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        JPanel buttonPanel2 = new JPanel();

        buttonPanel.add(openButton);
        buttonPanel2.add(makingButton);
        buttonPanel2.add(saveButton);
        add(buttonPanel, BorderLayout.NORTH);
        add(buttonPanel2, BorderLayout.SOUTH);

        imageLabel = new JLabel();
        JScrollPane scrollPane = new JScrollPane(imageLabel);
        add(scrollPane, BorderLayout.EAST);

        fileChooser = new JFileChooser();

        setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(UI::new);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openButton) {
            int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    images.add(ImageIO.read(file));
                    counter++;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error opening file.");
                }
            }
        }else if (e.getSource() == makingButton){
            int xShift = height / 8;
            try{
                for (int i = 0; i < images.size(); i++) {
                    overlayImage(basicImage, images.get(i), xShift);
                    xShift += (5 * height) / 8;
                }
            }catch (Exception ex){
                JOptionPane.showMessageDialog(this, "Error creating collage.");
            }

        }else if (e.getSource() == saveButton){
            try {
                savingImages(basicImage, "UItest");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving collage.");
            }

        }

    }
}