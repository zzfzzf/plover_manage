<<<<<<< HEAD
package com.zzy.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/3/26.
 */
public class ImageUtil {

    /***
     * 功能 :调整图片大小 开发：wuyechun 2011-7-22
     * @param distImgPath  转换大小后图片路径
     * @param width   转换后图片宽度
     * @param height  转换后图片高度
     */
    public static void resizeImage(InputStream inputStream, String distImgPath,
                                   int width, int height) throws IOException {

        Image srcImg = ImageIO.read(inputStream);
        BufferedImage buffImg = null;
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        buffImg.getGraphics().drawImage(
                srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0,
                0, null);
        ImageIO.write(buffImg, "PNG", new File(distImgPath));

    }

=======
package com.zzy.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/3/26.
 */
public class ImageUtil {

    /***
     * 功能 :调整图片大小 开发：wuyechun 2011-7-22
     * @param distImgPath  转换大小后图片路径
     * @param width   转换后图片宽度
     * @param height  转换后图片高度
     */
    public static void resizeImage(InputStream inputStream, String distImgPath,
                                   int width, int height) throws IOException {

        Image srcImg = ImageIO.read(inputStream);
        BufferedImage buffImg = null;
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        buffImg.getGraphics().drawImage(
                srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0,
                0, null);
        ImageIO.write(buffImg, "PNG", new File(distImgPath));

    }

>>>>>>> 87862a9414168c7958e3f27eded4d0e8c1646de5
}