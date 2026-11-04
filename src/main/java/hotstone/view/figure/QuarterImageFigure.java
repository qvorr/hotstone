/*
 * Copyright (C) 2022 - 2026. Henrik Bærbak Christensen, Aarhus University.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hotstone.view.figure;

import hotstone.view.GfxConstants;
import minidraw.standard.AbstractFigure;
import minidraw.standard.ImageManager;

import java.awt.*;
import java.awt.image.BufferedImage;

/** A convenience class which is basically an ImageFigure with
 * the 'weird' feature that the read image file is reduced to
 * the half size in both width and height dimension.
 *
 * The reason is that MiniDraw has no scaling/viewport system,
 * so everything has to be exact pixels, and the downloaded
 * card gfx from the Blizzard community websites were much
 * too large to fit a laptop screen.
 */

public class QuarterImageFigure extends AbstractFigure {
  private boolean hasAssociatedImage;
  private Rectangle fDisplayBox;
  private Image fImage;

  private String imageName;

  public QuarterImageFigure(String imageName, Point position) {
    set(imageName, position);
  }

  private void set(String imageName, Point position) {
    boolean imageLoadSuccess;
    this.imageName = imageName;
    ImageManager im = ImageManager.getSingleton();
    // Try to fetch the image
    try {
      fImage = im.getImage(imageName);
      imageLoadSuccess = true;
    } catch (RuntimeException exception) {
      // thrown when no image loaded by the image manager
      if (exception.getMessage().contains("No image named") ||
              exception.getMessage().contains("Lazy load of image")) {
        // we have the right exception, so replace with a
        // default image
        fImage = im.getImage(GfxConstants.DEFAULT_EMBLEM_IMAGE_NAME);
        imageLoadSuccess = false;
      } else
        throw exception;
    }
    // Credits: https://www.baeldung.com/java-resize-image
    hasAssociatedImage = imageLoadSuccess;

    int targetWidth = fImage.getWidth(null) / 2;
    int targetHeight = fImage.getHeight(null) / 2;
    // Draw image in 1/4 size
    Image resultingImage = fImage.getScaledInstance(targetWidth, targetHeight,
            Image.SCALE_DEFAULT);
    BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight,
            BufferedImage.TYPE_4BYTE_ABGR);
    resizedImage.getGraphics().drawImage(resultingImage, 0, 0, null);
    // And override original image
    fImage = resizedImage;
    fDisplayBox = new Rectangle(position.x, position.y,
            targetWidth, targetHeight);
  }
  public void set(String imagename) {
    Point p = displayBox().getLocation();
    willChange();
    set(imagename, p);
    changed();
  }
  @Override
  protected void basicMoveBy(int dx, int dy) {
    fDisplayBox.translate(dx, dy);
  }

  @Override
  public void draw(Graphics g) {
    if (fImage != null) {
      g.drawImage(fImage, fDisplayBox.x, fDisplayBox.y,
              fDisplayBox.width, fDisplayBox.height, null);
    }
  }

  @Override
  public Rectangle displayBox() {
    return fDisplayBox;
  }

  public boolean hasAssociateImage() {
    return hasAssociatedImage;
  }
  /** Get the file name of the graphics that
   * this image figure shows.
   * @return the image name
   */
  public String getImageName() {
    return imageName;
  }

}
