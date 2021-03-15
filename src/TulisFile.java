import java.io.FileWriter;
import java.io.IOException;

public class TulisFile {
    public static void main(String[] args) {
        String fileName = "src/puisi.txt";
        String fileContent = "Belajar membaca dan menulis file di java2!";
        
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(fileContent);
            fileWriter.close();
            
            System.out.println("File sudah ditulis ulang!");
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan karena: " + e.getMessage());
        }
    }
}
