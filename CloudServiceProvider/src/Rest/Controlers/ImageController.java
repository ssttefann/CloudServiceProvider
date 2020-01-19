package Rest.Controlers;

import com.google.gson.Gson;
import spark.Route;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ImageController {

    private static Gson gson = new Gson();

    public static Route uploadImage =  (request, response) -> {

        String tupan = request.params("fileName");
        String ext = tupan.split("\\.")[1];
        byte[] data = request.bodyAsBytes();

        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        BufferedImage image = ImageIO.read(bis);
        ImageIO.write(image, ext, new File("CloudServiceProvider/WebContent/app/dist/images/" + tupan));
        ImageIO.write(image, ext, new File("CloudServiceProvider/WebContent/app/public/images/" + tupan));

//        try (FileOutputStream fos = new FileOutputStream("CloudServiceProvider/WebContent/app/dist/images/" + tupan)) {
//            fos.write(data);
//        }
//
//        try (FileOutputStream fos = new FileOutputStream("CloudServiceProvider/WebContent/app/public/images/" + tupan)) {
//            fos.write(data);
//        }

//        ByteArrayInputStream bis = new ByteArrayInputStream(data);
//        BufferedImage originalImage = ImageIO.read(bis);
//        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
//        BufferedImage resizedImage = resizeImage(originalImage, type, 35, 35);
//
//        ImageIO.write(resizedImage, "jpg", new File("CloudServiceProvider/WebContent/app/dist/images/" + tupan));

        return "";
    };

    private static BufferedImage resizeImage(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT) {
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();

        return resizedImage;
    }
}
