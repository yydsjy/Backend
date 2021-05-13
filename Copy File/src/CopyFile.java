
import java.io.*;

public class CopyFile {
    public static void main(String[] args) {
        File file = new File
        File newFile = new File

        InputStream input = null;
        OutputStream output = null;

        try {
            input = new FileInputStream(file);
            output = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            int byteRead;
            while ((byteRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, byteRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
