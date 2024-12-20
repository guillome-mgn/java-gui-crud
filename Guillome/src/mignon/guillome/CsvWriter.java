package mignon.guillome;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CsvWriter {
    private String fileName = "./computers.csv";
    private String separation = ";";
    private File file = new File(fileName);

    public CsvWriter() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void saveComputer(Computer computer) {
        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write(computer.getNom() + separation + computer.getType() + separation + computer.getMarque() + separation + computer.getProcesseur() + separation + computer.getCarteGraphique() + separation + computer.getStockage() + separation + computer.getRam() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Computer> loadComputers() {
        ArrayList<Computer> computers = new ArrayList<>();
        try {
            java.util.Scanner scanner = new java.util.Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(separation);
                Computer computer = new Computer(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
                computers.add(computer);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return computers;
    }

    public void deleteComputer(int index) {
        ArrayList<Computer> computers = this.loadComputers();
        computers.remove(index);
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            for (Computer computer : computers) {
                fileWriter.write(computer.getNom() + separation + computer.getType() + separation + computer.getMarque() + separation + computer.getProcesseur() + separation + computer.getCarteGraphique() + separation + computer.getStockage() + separation + computer.getRam() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}