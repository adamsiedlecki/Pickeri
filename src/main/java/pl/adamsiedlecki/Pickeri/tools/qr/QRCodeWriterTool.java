package pl.adamsiedlecki.Pickeri.tools.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.IOException;

public class QRCodeWriterTool {

    public static File encode(Long id, String name, String lastName, String path) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(id + "," + name + " " + lastName, BarcodeFormat.QR_CODE, 190, 190);
            File f = new File(path + "\\" + id + " " + name + " " + lastName + ".jpg");
            MatrixToImageWriter.writeToFile(bitMatrix, "jpg", f);
            return f;
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
