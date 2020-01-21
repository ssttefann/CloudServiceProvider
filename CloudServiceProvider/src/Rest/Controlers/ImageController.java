package Rest.Controlers;

import Model.Entities.User;
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

    public static Route uploadOrgImage =  (request, response) -> {
        String fileName = request.params("fileName");
        String ext = fileName.split("\\.")[1];

        byte[] data = request.bodyAsBytes();
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        BufferedImage image = ImageIO.read(bis);
        ImageIO.write(image, ext, new File("CloudServiceProvider/WebContent/app/dist/images/orgs/" + fileName));
        ImageIO.write(image, ext, new File("CloudServiceProvider/WebContent/app/public/images/orgs/" + fileName));

        return "";
    };

    // TODO dodati funkc na frontu za poziv ove metode
    public static Route uploadProfilPicture =  (request, response) -> {

        User user = request.session().attribute("user");
        String naziv = request.params("fileName");
        String tokens[] = naziv.split("\\.");
        String ext = tokens[1];
        naziv = user.getEmail();
        byte[] data = request.bodyAsBytes();

        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        BufferedImage image = ImageIO.read(bis);
        ImageIO.write(image, ext, new File("CloudServiceProvider/WebContent/app/dist/images/" + naziv));
        ImageIO.write(image, ext, new File("CloudServiceProvider/WebContent/app/public/images/" + naziv));

        return "";
    };

    public static Route getProfilePic = (request, response) -> {

        User user = request.session().attribute("user");

        if(user == null){
            response.redirect("/images/users/stock.png", 302 );
            return "";
        }

        File f = new File("CloudServiceProvider/WebContent/app/public/images/users/" + user.getImage());
        if (f.isFile()){
            response.redirect("/images/users/" + user.getImage(), 302 );
            return "";
        }else{
            response.redirect("/images/users/stock.png", 302 );
            return "";
        }

    };

    private static BufferedImage resizeImage(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT) {
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();

        return resizedImage;
    }
}
