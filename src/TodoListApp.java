import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TodoListApp {
    // deklarasi variabel global
    static String fileName;
    static ArrayList<String> todoLists;
    static boolean isEditing = false;
    static Scanner input;
    
    public static void main(String[] args) {
        // initialize
        todoLists = new ArrayList<>();
        input = new Scanner(System.in);
        
        String filePath = System.console() == null ? "/src/todolist.txt" : "/todolist.txt";
        fileName = System.getProperty("user.dir") + filePath;
        
        System.out.println("FILE: " + fileName);
        
        while(true) {
            showMenu();
        }
    }
    
    static void clearScreen() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                // clear screen untuk windows
                new ProcessBuilder("cmd", "/c", "cls")
                        .inheritIO()
                        .start()
                        .waitFor();
            } else {
                // clear screen untuk linux, unix, mac
                Runtime.getRuntime().exec("clear");
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            System.out.println("Error karena: " + e.getMessage());
        }
    }
    
    static void showMenu() {
        System.out.println("=== TODO LIST APP ===");
        System.out.println("[1] Lihat Todo List");
        System.out.println("[2] Tambah Todo List");
        System.out.println("[3] Edit Todo List");
        System.out.println("[4] Hapus Todo List");
        System.out.println("[0] Keluar");
        System.out.println("---------------------");
        System.out.print("Pilih menu > ");
        
        String selectedMenu = input.nextLine();
        
        if (selectedMenu.equals("1")) {
            showTodoList();
        } else if (selectedMenu.equals("2")) {
            addTodoList();
        } else if (selectedMenu.equals("3")) {
            editTodoList();
        } else if (selectedMenu.equals("4")) {
            deleteTodoList();
        } else if (selectedMenu.equals("0")) {
            System.exit(0);
        } else {
            System.out.println("Kamu salah pilih menu!");
            backToMenu();
        }
    }
    
    static void backToMenu() {
        System.out.println("");
        System.out.print("Tekan [Enter] untuk kembali..");
        input.nextLine();
        clearScreen();
    }

    static void readTodoList() {
        try {
            File file = new File(fileName);
            Scanner fileReader = new Scanner(file);
            
            // load isi file ke dalam array todoLists
            todoLists.clear();
            while(fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                todoLists.add(data);
            }
        } catch (Exception e) {
            System.out.println("Error karena: " + e.getMessage());
        }
    }

    static void showTodoList() {
        clearScreen();
        readTodoList();
        if (todoLists.size() > 0) {
            System.out.println("TODO LIST:");
            int index = 0;
            for (String data : todoLists) {
                System.out.println(String.format("[%d] %s", index, data));
                index++;
            }
        } else {
            System.out.println("Tidak ada data!");
        }
        
        if (!isEditing) {
            backToMenu();
        }
    }

    static void addTodoList() {
        clearScreen();
        
        System.out.println("Apa yang ingin kamu kerjakan?");
        System.out.print("Jawab: ");
        String newTodoList = input.nextLine();
        
        try {
            // tulis file
            FileWriter fileWriter = new FileWriter(fileName, true);
            fileWriter.append(String.format("%s%n", newTodoList));
            fileWriter.close();
            System.err.println("Berhasil ditambahkan!");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan karena: " + e.getMessage());
        }
        
        backToMenu();
    }

    static void editTodoList() {
        isEditing = true;
        showTodoList();
        
        try {
            System.out.println("-----------------");
            System.out.print("Pilih Index > ");
            int index = Integer.parseInt(input.nextLine());
            
            if (index > todoLists.size()) {
                throw new IndexOutOfBoundsException("Kamu  memasukan data yang salah!");
            } else {
                System.out.print("Data baru: ");
                String newData = input.nextLine();
                
                // update data
                todoLists.set(index, newData);
                
                System.out.println(todoLists.toString());
                
                try {
                    FileWriter fileWriter = new FileWriter(fileName, false);
                    
                    // write new data
                    for (String data : todoLists) {
                        fileWriter.append(String.format("%s%n", data));
                    }
                    fileWriter.close();
                    
                    System.out.println("Berhasil diubah!");
                } catch (IOException e) {
                    System.out.println("Terjadi kesalahan karena: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        isEditing = false;
        backToMenu();
    }

    static void deleteTodoList() {
        isEditing = true;
        showTodoList();
        
        System.out.println("-----------------");
        System.out.print("Piilih Index > ");
        int index = Integer.parseInt(input.nextLine());
        
        try {
            if (index > todoLists.size()) {
                throw new IndexOutOfBoundsException("Kamu memasukan data yang salah!");
            } else {
                System.out.println("Kamu akan menghapus:");
                System.out.println(String.format("[%d] %s", index, todoLists.get(index)));
                System.out.print("Jawab (y/t): ");
                String jawab = input.nextLine();
                
                if (jawab.equalsIgnoreCase("y")) {
                    // hapus data
                    todoLists.remove(index);
                    
                    // tulis ulang file
                    try {
                        FileWriter fileWriter = new FileWriter(fileName, false);
                        
                        // write new data
                        for (String data : todoLists) {
                            fileWriter.append(String.format("%s%n", data));
                        }
                        fileWriter.close();
                        
                        System.out.println("Berhasil dihapus!");
                    } catch (IOException e) {
                        System.out.println("Terjadi kesalahan karena: " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        isEditing = false;
        backToMenu();
    }
}
