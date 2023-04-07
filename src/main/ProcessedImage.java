package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProcessedImage {
    private int width;
    private int height;
    private Color color;


    public ProcessedImage(int width, int height, Color color) {
        this.width = width;
        this.height = height;
        this.color = color;
    }
    public ProcessedImage() {
        height = 1024;
        width = 2 * height;
        color = Color.cyan;
    }

    public BufferedImage createABackground(){
// create a BufferedImage object
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D imageGraphics = image.createGraphics();

// draw a filled rectangle on the image with the color
        imageGraphics.setColor(this.color);
        imageGraphics.fillRect(0, 0, width, height);

        imageGraphics.dispose();

        return image;
    }

    public static BufferedImage overlayImage(BufferedImage overlappedImage, int xShift) throws IOException {
        BufferedImage basicImage = new ProcessedImage().createABackground();
        return overlayImage(basicImage, overlappedImage, xShift);
    }

    public static BufferedImage overlayImage (BufferedImage basicImage, BufferedImage overlappedImage,int xShift) throws IOException {

        Graphics2D g2d = basicImage.createGraphics();
        int backgroundHeight = basicImage.getHeight();
        int backgroundWidth = basicImage.getWidth();

        BufferedImage resizedImage =  new BufferedImage(backgroundHeight/2,backgroundHeight/2, Image.SCALE_DEFAULT);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(overlappedImage,0,0, backgroundHeight/2,backgroundHeight/2, null);
        graphics2D.dispose();

        g2d.drawImage(resizedImage,xShift,(backgroundHeight-resizedImage.getHeight())/2, null);

        g2d.dispose();
        return basicImage;
    }

    public static ArrayList<BufferedImage> readingImages(File[] files){
        ArrayList<BufferedImage> images = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()) {
                try {
                    BufferedImage image = ImageIO.read(file);
                    images.add(image);
                } catch (IOException ex) {
                    System.err.println("Error reading image file: " + ex.getMessage());
                }
            }
        }
        return images;
    }

    public static void savingImages(BufferedImage imageToSave){
        savingImages(imageToSave, "image");
    }

    public static void savingImages(BufferedImage imageToSave, String nameOfFile){
                File outputfile = new File(nameOfFile + ".png");
        try {
            ImageIO.write(imageToSave, "png", outputfile);
        } catch (IOException ex) {
            System.err.println("Error writing image file: " +ex.getMessage());
        }

    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

