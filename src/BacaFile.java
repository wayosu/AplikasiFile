import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BacaFile {
    public static void main(String[] args) {
        String fileName = "src/puisi.txt";
        
        try {
            // membaca file
            File myFile = new File(fileName);
            Scanner fileReader = new Scanner(myFile);
            
            // cetak isi file
            while(fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                System.out.println(data);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Terjadi Kesalahan: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
