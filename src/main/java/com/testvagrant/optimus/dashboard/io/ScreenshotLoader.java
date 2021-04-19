package com.testvagrant.optimus.dashboard.io;

import com.testvagrant.optimus.dashboard.models.dashboard.Screenshot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ScreenshotLoader {

    public List<Screenshot> loadScreenshots(String feature, String test) {
        String path = Paths.get("execution_timeline", feature, test).toString();
        List<Screenshot> screenshots = new ArrayList<>();
        File screenshotFolder = new File(path);
        File[] files = screenshotFolder.listFiles() == null
                ? new File[] {}
                : screenshotFolder.listFiles();
        Arrays.stream(files).forEach(file -> {
            try {
                BufferedImage originalImage = ImageIO.read(file);
                Screenshot screenshot = Screenshot.builder().fileName(file.getName())
                        .data(getImageBytes(originalImage)).build();
                screenshots.add(screenshot);
            } catch (IOException e) {
            }
        });
        return screenshots;
    }

    public byte[] getLastScreen(String feature, String test) {
        String path = Paths.get("execution_timeline", feature, test).toString();
        File screenshotFolder = new File(path);
        File[] files = screenshotFolder.listFiles() == null
                ? new File[] {}
                : screenshotFolder.listFiles();
        Optional<File> last_screen = Arrays.stream(files).filter(file -> file.getName().contains("last_screen")).findFirst();
        if(last_screen.isPresent()) {
            BufferedImage originalImage = null;
            Screenshot screenshot = null;
            try {
                originalImage = ImageIO.read(last_screen.get());
               return getImageBytes(originalImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new byte[]{0};
    }

    public static byte[] getImageBytes(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( image, "png", baos );
        baos.flush();
        byte[] imageInBytes = baos.toByteArray();
        baos.close();
        return imageInBytes;
    }
}
