package com.testvagrant.optimus.dashboard.io;

import com.testvagrant.optimus.dashboard.OptimusExecutionTimelinePaths;
import com.testvagrant.optimus.dashboard.models.TestCase;
import com.testvagrant.optimus.dashboard.models.dashboard.Screenshot;

import javax.imageio.ImageIO;
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

    public List<Screenshot> loadScreenshots(TestCase testCase) {
        String path = OptimusExecutionTimelinePaths.getScreenshotsPath(testCase);
        File screenshotFolder = new File(path);
        File[] files = screenshotFolder.listFiles() == null
                ? new File[] {}
                : screenshotFolder.listFiles();
        Arrays.stream(files).forEach(file -> {
            try {
                BufferedImage originalImage = ImageIO.read(file);
                Screenshot screenshot =
                    Screenshot.builder()
                        .fileName(file.getName())
                        .data(getImageBytes(originalImage))
                        .build();
                screenshots.add(screenshot);
            } catch (IOException e) {
            }
        });
        screenshots.sort(Comparator.comparing(Screenshot::getFileName));
        updateFailedOnScreen();
        return screenshots;
    }

    private void updateFailedOnScreen() {
        if(screenshots.size()==0) {
            failedOnScreen = new byte[]{0};
        } else {
            Screenshot screenshot = screenshots.get(screenshots.size() - 1);
            failedOnScreen = screenshot.getData();
        }
    }

    public byte[] getFailedOnScreen() {
        return failedOnScreen;
    }

  public byte[] getLastScreen(String feature, String test) {
    String path = Paths.get("execution_timeline", feature, test).toString();
    File screenshotFolder = new File(path);
    File[] files =
        screenshotFolder.listFiles() == null ? new File[] {} : screenshotFolder.listFiles();
    Optional<File> last_screen =
        Arrays.stream(Objects.requireNonNull(files))
            .filter(file -> file.getName().contains("last_screen"))
            .findFirst();
    if (last_screen.isPresent()) {
      BufferedImage originalImage;
      try {
        originalImage = ImageIO.read(last_screen.get());
        return getImageBytes(originalImage);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return new byte[] {0};
  }

  public static byte[] getImageBytes(BufferedImage image) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(image, "png", baos);
    baos.flush();
    byte[] imageInBytes = baos.toByteArray();
    baos.close();
    return imageInBytes;
  }
}
