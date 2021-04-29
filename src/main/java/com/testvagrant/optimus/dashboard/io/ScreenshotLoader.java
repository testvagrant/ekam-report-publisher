package com.testvagrant.optimus.dashboard.io;

import com.testvagrant.optimus.dashboard.models.dashboard.Screenshot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class ScreenshotLoader {

    private List<Screenshot> screenshots;
    private byte[] failedOnScreen;
    public ScreenshotLoader() {
        screenshots = new ArrayList<>();
        failedOnScreen = new byte[]{0};
    }

    public List<Screenshot> loadScreenshots(String feature, String test) {
        String path = Paths.get(System.getProperty("user.dir"),
                "build",
                "optimus-execution-timeline",
                feature,
                test,
                "screenshots").toString();
        File screenshotFolder = new File(path);
        File[] files = screenshotFolder.listFiles() == null
                ? new File[] {}
                : screenshotFolder.listFiles();
        Arrays.stream(files).forEach(file -> {
            try {
                if (!file.getName().contains("failedOnScreen")) {
                    BufferedImage originalImage = ImageIO.read(file);
                    Screenshot screenshot = Screenshot.builder().fileName(file.getName())
                            .data(getImageBytes(originalImage)).build();
                    screenshots.add(screenshot);
                } else {
                    InputStream is;
                    BufferedImage originalImage = ImageIO.read(file);
                    failedOnScreen = getImageBytes(originalImage);
                }
            } catch (IOException e) {
            }
        });
        screenshots.sort(Comparator.comparing(Screenshot::getFileName));
        return screenshots;
    }

    public byte[] getFailedOnScreen() {
        return failedOnScreen;
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
