package com.core.UI;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.Buffer;

/**
 * Created by siefker on 10.11.2015.
 */
public class WebcamEvaluation {

    public Boolean checkIfMotionDetectedForGivenTest(String currentTest, BufferedImage image1) throws IOException {

        Webcam webcam = Webcam.getDefault();

        ImageIO.write(webcam.getImage(), "PNG", new File("img/2.png"));
        File file2 = new File("img/2.png");
        FileInputStream fis2 = new FileInputStream(file2);
        BufferedImage image2 = ImageIO.read(fis2);

        if (currentTest.equals("CIRCLE_TEST_CIRCLE_TOP_LEFT")) {

            Boolean motionInTopLeftCornerDetected = compareTopLeftCorners(image1, image2);
            return motionInTopLeftCornerDetected;
        }

        if (currentTest.equals("CIRCLE_TEST_CIRCLE_TOP_RIGHT")) {

            Boolean motionInTopRightCornerDetected = compareTopRightCorners(image1, image2);
            return motionInTopRightCornerDetected;
        }

        if (currentTest.equals("CIRCLE_TEST_CIRCLE_BOTTOM_LEFT")) {

            Boolean motionInBottomLeftCornerDetected = compareBottomLeftCorners(image1, image2);
            return motionInBottomLeftCornerDetected;
        }

        if (currentTest.equals("CIRCLE_TEST_CIRCLE_BOTTOM_RIGHT")) {

            Boolean motionInBottomRightCornerDetected = compareBottomRightCorners(image1, image2);
            return motionInBottomRightCornerDetected;
        }

        /* PICTURES TEST */

        if (currentTest.equals("PICTURES_TEST")) {

            Boolean motionInTopLeftAndTopRightCornerDetected = compareTopLeftCorners(image1, image2) && compareTopRightCorners(image1, image2);
            return motionInTopLeftAndTopRightCornerDetected;
        }

         /* BALANCE TEST */
        if (currentTest.equals("BALANCE_TEST_RIGHT")) {

            Boolean motionRightSideDetected = (compareRightSide(image1, image2)/* compareLeftSide(image1, image2)*/) ;
            return motionRightSideDetected;
        }

        if (currentTest.equals("BALANCE_TEST_LEFT")) {

            Boolean motionLeftSideDetected = compareLeftSide(image1, image2) /*&& !compareTopRightCorners(image1, image2) && !compareBottomLeftCorners(image1, image2) && !compareBottomRightCorners(image1, image2)*/;
            return motionLeftSideDetected;
        }

        if (currentTest.equals("WHOLE_PIC")) {

            Boolean motionPicDetected = compareWholePic(image1, image2) /*&& !compareTopRightCorners(image1, image2) && !compareBottomLeftCorners(image1, image2) && !compareBottomRightCorners(image1, image2)*/;
            return motionPicDetected;
        }


        return false;
    }

    private boolean compareTopLeftCorners(BufferedImage image1, BufferedImage image2) throws IOException {

        int identicalPixels = 0, differentPixels = 0;

        int width = 640;
        int height = 75;

        for(int a=width-75;a<width;a++)
        {
            for(int b=0;b<height;b++)
            {

                int clr1=  image1.getRGB(a,b);
                int  red1   = (clr1 & 0x00ff0000) >> 16;
                int  green1 = (clr1 & 0x0000ff00) >> 8;
                int  blue1  =  clr1 & 0x000000ff;

                int clr2=  image2.getRGB(a,b);
                int  red2   = (clr2 & 0x00ff0000) >> 16;
                int  green2 = (clr2 & 0x0000ff00) >> 8;
                int  blue2  =  clr2 & 0x000000ff;

                if(((red1 - red2) + (green1 - green2) + (blue1 - blue2))<50)  // Summanden werden quadriert um gegenseitiges Ausgleichen zu vermeiden. 250 = Schwellwert ab wann sich Pixel unterscheiden
                {
                    identicalPixels=identicalPixels+1;
                }
                else
                    differentPixels=differentPixels+1;
            }
        }

        float allPixels = 75*75;
        float percentageIdenticalPixels =(100*identicalPixels)/allPixels;

        return percentageIdenticalPixels<=90;
    }

    private boolean compareTopRightCorners(BufferedImage image1, BufferedImage image2) throws IOException {

        int identicalPixels = 0, differentPixels = 0;

        int width = 75;
        int height = 75;

        for(int a=0;a<width;a++)
        {
            for(int b=0;b<height;b++)
            {

                int clr1=  image1.getRGB(a,b);
                int  red1   = (clr1 & 0x00ff0000) >> 16;
                int  green1 = (clr1 & 0x0000ff00) >> 8;
                int  blue1  =  clr1 & 0x000000ff;

                int clr2=  image2.getRGB(a,b);
                int  red2   = (clr2 & 0x00ff0000) >> 16;
                int  green2 = (clr2 & 0x0000ff00) >> 8;
                int  blue2  =  clr2 & 0x000000ff;

                if(((red1 - red2) + (green1 - green2) + (blue1 - blue2))<50)
                {
                    identicalPixels=identicalPixels+1;
                }
                else
                    differentPixels=differentPixels+1;
            }
        }

        float allPixels = 75*75;
        float percentageIdenticalPixels =(100*identicalPixels)/allPixels;

        return percentageIdenticalPixels<=90;
    }

    private boolean compareBottomLeftCorners(BufferedImage image1, BufferedImage image2) throws IOException {

        int identicalPixels = 0, differentPixels = 0;

        int width = 640;
        int height = 480;

        for(int a=width-75;a<width;a++)
        {
            for(int b=height-75;b<height;b++)
            {

                int clr1=  image1.getRGB(a,b);
                int  red1   = (clr1 & 0x00ff0000) >> 16;
                int  green1 = (clr1 & 0x0000ff00) >> 8;
                int  blue1  =  clr1 & 0x000000ff;

                int clr2=  image2.getRGB(a,b);
                int  red2   = (clr2 & 0x00ff0000) >> 16;
                int  green2 = (clr2 & 0x0000ff00) >> 8;
                int  blue2  =  clr2 & 0x000000ff;

                if(((red1 - red2) + (green1 - green2) + (blue1 - blue2))<50)
                {
                    identicalPixels=identicalPixels+1;
                }
                else
                    differentPixels=differentPixels+1;
            }
        }

        float allPixels = 75*75;
        float percentageIdenticalPixels =(100*identicalPixels)/allPixels;

        return percentageIdenticalPixels<=90;
    }

    private boolean compareBottomRightCorners(BufferedImage image1, BufferedImage image2) throws IOException {

        int identicalPixels = 0, differentPixels = 0;

        int width = 75;
        int height = 480;

        for(int a=0;a<width;a++)
        {
            for(int b=height-75;b<height;b++)
            {

                int clr1=  image1.getRGB(a,b);
                int  red1   = (clr1 & 0x00ff0000) >> 16;
                int  green1 = (clr1 & 0x0000ff00) >> 8;
                int  blue1  =  clr1 & 0x000000ff;

                int clr2=  image2.getRGB(a,b);
                int  red2   = (clr2 & 0x00ff0000) >> 16;
                int  green2 = (clr2 & 0x0000ff00) >> 8;
                int  blue2  =  clr2 & 0x000000ff;

                if(((red1 - red2) + (green1 - green2) + (blue1 - blue2))<50)
                {
                    identicalPixels=identicalPixels+1;
                }
                else
                    differentPixels=differentPixels+1;
            }
        }

        float allPixels = 75*75;
        float percentageIdenticalPixels =(100*identicalPixels)/allPixels;

        return percentageIdenticalPixels<=90;
    }

    private boolean compareRightSide(BufferedImage image1, BufferedImage image2) throws IOException {

        int identicalPixels = 0, differentPixels = 0;

        int width = 200;
        int height = 480;

        for(int a=0;a<width;a++)
        {
            for(int b=0;b<height;b++)
            {

                int clr1=  image1.getRGB(a,b);
                int  red1   = (clr1 & 0x00ff0000) >> 16;
                int  green1 = (clr1 & 0x0000ff00) >> 8;
                int  blue1  =  clr1 & 0x000000ff;

                int clr2=  image2.getRGB(a,b);
                int  red2   = (clr2 & 0x00ff0000) >> 16;
                int  green2 = (clr2 & 0x0000ff00) >> 8;
                int  blue2  =  clr2 & 0x000000ff;

                if(((red1 - red2)*(red1 - red2) + (green1 - green2)*(green1 - green2) + (blue1 - blue2)*(blue1 - blue2))<2500)
                {
                    identicalPixels=identicalPixels+1;
                }
                else
                    differentPixels=differentPixels+1;
            }
        }

        float allPixels = (height*width);
        float percentageIdenticalPixels =(100*identicalPixels)/allPixels;

        return percentageIdenticalPixels<=97;
    }

    private boolean compareLeftSide(BufferedImage image1, BufferedImage image2) throws IOException {

        int identicalPixels = 0, differentPixels = 0;

        int width = 640;
        int height = 480;

        for(int a=width-200;a<width;a++)
        {
            for(int b=0;b<height;b++)
            {

                int clr1=  image1.getRGB(a,b);
                int  red1   = (clr1 & 0x00ff0000) >> 16;
                int  green1 = (clr1 & 0x0000ff00) >> 8;
                int  blue1  =  clr1 & 0x000000ff;

                int clr2=  image2.getRGB(a,b);
                int  red2   = (clr2 & 0x00ff0000) >> 16;
                int  green2 = (clr2 & 0x0000ff00) >> 8;
                int  blue2  =  clr2 & 0x000000ff;

                if(((red1 - red2)*(red1 - red2) + (green1 - green2)*(green1 - green2) + (blue1 - blue2)*(blue1 - blue2))<2500)
                {
                    identicalPixels=identicalPixels+1;
                }
                else
                    differentPixels=differentPixels+1;
            }
        }

        float allPixels = (height*width);
        float percentageIdenticalPixels =(100*identicalPixels)/allPixels;

        return percentageIdenticalPixels<=97;
    }

    private boolean compareWholePic(BufferedImage image1, BufferedImage image2) throws IOException {

        int identicalPixels = 0, differentPixels = 0;

        int width = 640;
        int height = 480;

        for(int a=0;a<width;a++)
        {
            for(int b=0;b<height;b++)
            {

                int clr1=  image1.getRGB(a,b);
                int  red1   = (clr1 & 0x00ff0000) >> 16;
                int  green1 = (clr1 & 0x0000ff00) >> 8;
                int  blue1  =  clr1 & 0x000000ff;

                int clr2=  image2.getRGB(a,b);
                int  red2   = (clr2 & 0x00ff0000) >> 16;
                int  green2 = (clr2 & 0x0000ff00) >> 8;
                int  blue2  =  clr2 & 0x000000ff;

                if(((red1 - red2)*(red1 - red2) + (green1 - green2)*(green1 - green2) + (blue1 - blue2)*(blue1 - blue2))<2500)
                {
                    identicalPixels=identicalPixels+1;
                }
                else
                    differentPixels=differentPixels+1;
            }
        }

        float allPixels = (height*width);
        float percentageIdenticalPixels =(100*identicalPixels)/allPixels;

        return percentageIdenticalPixels<=95;
    }
}