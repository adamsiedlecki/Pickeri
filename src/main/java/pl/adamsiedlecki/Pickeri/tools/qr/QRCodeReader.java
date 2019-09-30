package pl.adamsiedlecki.Pickeri.tools.qr;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

public class QRCodeReader {

    private static final Logger log = LoggerFactory.getLogger(QRCodeReader.class);

    public static String decodeQRCode(File qrCodeimage) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(qrCodeimage);
        } catch (IOException e) {
            log.error("QRCodeReader - IOException");
        }

        // https://stackoverflow.com/questions/3433275/adjust-brightness-and-contrast-of-bufferedimage-in-java
        RescaleOp rescaleOp = new RescaleOp(0.4f, 1, null); //new RescaleOp(1.8f, 15, null);
        rescaleOp.filter(bufferedImage, bufferedImage);

        File f = new File("image.jpg");
        try {
            ImageIO.write(bufferedImage, "jpg", f);
        } catch (IOException e) {
            qrCodeimage.delete();
            f.delete();
            log.error("IOException");
        }

        ////////////
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        try {
            Result result = new MultiFormatReader().decode(bitmap);
            qrCodeimage.delete();
            f.delete();
            return result.getText();
        } catch (NotFoundException e) {
            log.error("There is no QR code in the image");
            qrCodeimage.delete();
            f.delete();
            return null;
        }
    }


}

