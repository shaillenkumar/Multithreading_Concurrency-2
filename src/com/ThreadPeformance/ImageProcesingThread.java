package com.ThreadPeformance;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class ImageProcesingThread {
    public static final String Source_File="/Users/shailendrakumar/Documents/intellij/JavaConcurencyMultithreading/src/com/ThreadPeformance/resources/many-flowers.jpg";
    public static final String Destination_File="./out/many-flowers.jpg";

    public static void main(String[] args) throws Exception {
        BufferedImage orginalImg = ImageIO.read(new File(Source_File));
        BufferedImage resultImg = new BufferedImage(orginalImg.getWidth(), orginalImg.getHeight(), BufferedImage.TYPE_INT_RGB);

        long starttime = System.currentTimeMillis();
        // recolorSingleThreaded(orginalImg, resultImg);  // compare performance with Single Thread
        int noOfThread = 4;
        recolorMultiThreaded(orginalImg, resultImg, noOfThread);
        File outputFile = new File(Destination_File);
        ImageIO.write(resultImg, "jpg",outputFile);
        long endtime = System.currentTimeMillis();
        System.out.println("Time taken "+ (endtime-starttime));
    }
    public static void recolorMultiThreaded(BufferedImage originalImage, BufferedImage resultImage, int numberOfThreads) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        int width = originalImage.getWidth();
        int height = originalImage.getHeight()/numberOfThreads;
        for(int i=0;i < numberOfThreads; i++){
            final int threadMultiplier = i;
            Thread thread = new Thread(()->{
                int leftCorner = 0;
                int topCorner = height + threadMultiplier;
                reColorImage(originalImage,resultImage,leftCorner, topCorner,width, height);
            });
            threads.add(thread);
        }
        for(int i=0; i<threads.size();i++){
            Thread t = threads.get(i);
            t.start();
        }
        for(int i=0; i<threads.size();i++){
            Thread t = threads.get(i);
            t.join();
        }

    }
    public static void recolorSingleThreaded(BufferedImage originalImage, BufferedImage resultImage){
        reColorImage(originalImage, resultImage,0,0,originalImage.getWidth(), originalImage.getHeight() );
    }
    public static void reColorImage(BufferedImage originalImg, BufferedImage resultImage, int leftCorner, int topCorner, int width, int height){
        for(int x=leftCorner; x< leftCorner+width; x++  ){
            for(int y=topCorner; y<topCorner+height; y++){
                recolourPixel(originalImg, resultImage, x, y);
            }
        }
    }
    public static void recolourPixel(BufferedImage originalImage, BufferedImage resultImage, int x, int y){
        int rgb = originalImage.getRGB(x,y);

        int red = getRed(rgb);
        int green = getBlue(rgb);
        int blue = getBlue(rgb);

        int newRed;
        int newGreen;
        int newBlue;

        // If pixel is shade of grey
        if(isShadeOfGrey(red, blue, green)){
            newRed = Math.min(255, red+10);
            newGreen = Math.max(0, green-80);
            newBlue = Math.max(0,blue-20);
        }else{
            newRed=red; newBlue=blue; newGreen=green;
        }

        int newRGB = createRGBFromColors(newRed, newGreen, newBlue);
        setRGB(resultImage, x,y, newRGB);
    }



    public static void setRGB(BufferedImage image, int x, int y, int rgb){
        image.getRaster().setDataElements(x,y,image.getColorModel().getDataElements(rgb, null));
    }

    public static int getRed(int rgb){
        return (rgb & 0x00FF0000) >> 16; //get Red Component of Image
    }
    public static int getGreen(int rgb){
        return (rgb & 0x0000FF00) >> 16; //get Green Component of Image
    }
    public static int getBlue(int rgb){
        return (rgb & 0x000000FF) >> 16; //get Blue Component of Image
    }
    // Find if this is shade of grey
    public static boolean isShadeOfGrey(int red, int green, int blue){
        //Check if all colors have equal intensity - emphrical formula
        return Math.abs(red-green) <30 &&  Math.abs(red-blue) <30 &&  Math.abs(green-blue) <30;
    }
    // Create RGB pixel from given rgb value
    public static int createRGBFromColors(int red, int green, int blue){
        int rgb=0;
        rgb |= red;
        rgb |= green<<8;
        rgb |= blue<<16;
        rgb |= rgb & 0xFF000000;
        return rgb;
    }


}
