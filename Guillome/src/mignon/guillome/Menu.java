package mignon.guillome;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Menu {

	int jframeSize = 700;
	ArrayList<Computer> computers = new ArrayList<>();
	CsvWriter csvWriter = new CsvWriter();
	
	public void displayMenu() {
		// Main menu
		JFrame frame = new JFrame("Menu");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(jframeSize, jframeSize);
		frame.setLayout(new GridLayout(3, 1));
		
		JButton addComputerButton = new JButton("Ajouter un ordinateur");
		JButton viewComputersButton = new JButton("Voir tous les ordinateurs");
		JButton exitButton = new JButton("Quitter");
		
		frame.add(addComputerButton);
		frame.add(viewComputersButton);
		frame.add(exitButton);
		
		frame.setVisible(true);

		addComputerButton.addActionListener(e -> {
			this.handleMenuChange(frame, "add");
		});

		viewComputersButton.addActionListener(e -> {
			this.handleMenuChange(frame, "view");
		});

		exitButton.addActionListener(e -> {
			System.exit(0);
		});
	}

	public void displayAddMenu() {
		// Computer add menu
		JFrame frame = new JFrame("Ajouter un ordinateur");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(jframeSize, jframeSize);
		frame.setLayout(new GridLayout(8, 2));
		
		JLabel nomLabel = new JLabel("Nom: ");
		JTextField nomField = new JTextField();
		JLabel typeLabel = new JLabel("Type: ");
		String[] types = { "Laptop", "Desktop", "Serveur" };
		JComboBox<String> typeComboBox = new JComboBox<>(types);
		JLabel marqueLabel = new JLabel("Marque: ");
		JTextField marqueField = new JTextField();
		JLabel processeurLabel = new JLabel("Processeur: ");
		JTextField processeurField = new JTextField();
		JLabel carteGraphiqueLabel = new JLabel("Carte graphique: ");
		JTextField carteGraphiqueField = new JTextField();
		JLabel stockageLabel = new JLabel("Stockage: ");
		JTextField stockageField = new JTextField();
		JLabel ramLabel = new JLabel("RAM: ");
		JTextField ramField = new JTextField();
		
		JButton submitButton = new JButton("Ajouter");
		JButton backButton = new JButton("Retour");
		
		frame.add(nomLabel);
		frame.add(nomField);
		frame.add(typeLabel);
		frame.add(typeComboBox);
		frame.add(marqueLabel);
		frame.add(marqueField);
		frame.add(processeurLabel);
		frame.add(processeurField);
		frame.add(carteGraphiqueLabel);
		frame.add(carteGraphiqueField);
		frame.add(stockageLabel);
		frame.add(stockageField);
		frame.add(ramLabel);
		frame.add(ramField);
		frame.add(backButton);
		frame.add(submitButton);
		
		frame.setVisible(true);

		submitButton.addActionListener(e -> {
			// Add a new computer to the list
			ArrayList<String> caracteristiques = new ArrayList<>();
			caracteristiques.add(nomField.getText());
			caracteristiques.add(typeComboBox.getSelectedItem().toString());
			caracteristiques.add(marqueField.getText());
			caracteristiques.add(processeurField.getText());
			caracteristiques.add(carteGraphiqueField.getText());
			caracteristiques.add(stockageField.getText());
			caracteristiques.add(ramField.getText());
			for (String caracteristique : caracteristiques) {
				if (caracteristique.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Veuillez remplir tous les champs.");
					return;
				}
			}

			// Create a new computer object
			Computer computer = new Computer(
				nomField.getText(),
				typeComboBox.getSelectedItem().toString(),
				marqueField.getText(),
				processeurField.getText(),
				carteGraphiqueField.getText(),
				stockageField.getText(),
				ramField.getText()
			);

			// Write it to the file
			csvWriter.saveComputer(computer);

			// Display a success message
			JOptionPane.showMessageDialog(frame, "Ordinateur ajouté avec succès !");

			// Reset the fields
			nomField.setText("");
			marqueField.setText("");
			processeurField.setText("");
			carteGraphiqueField.setText("");
			stockageField.setText("");
			ramField.setText("");
		});

		backButton.addActionListener(e -> {
			this.handleMenuChange(frame, "main");
		});
	}

	public void displayViewMenu() {
		// Create the computers array and load the computers
		computers = csvWriter.loadComputers();
		// Create a new frame with a dropdown menu to select a computer among the list
		JFrame frame = new JFrame("Voir les ordinateurs");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(jframeSize, jframeSize);
		frame.setLayout(new GridLayout(2, 1));

		frame.setVisible(true);
		
		if (computers.size() == 0) {
			JOptionPane.showMessageDialog(frame, "Aucun ordinateur n'a été trouvé.");
			this.handleMenuChange(frame, "main");
			return;
		} else {
			String[] columnNames = {"Nom", "Type", "Marque", "Processeur", "Carte Graphique", "Stockage", "RAM"};
			DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
			for (Computer computer : computers) {
				if (computer != null) {
					Object[] rowData = {
						computer.getNom(),
						computer.getType(),
						computer.getMarque(),
						computer.getProcesseur(),
						computer.getCarteGraphique(),
						computer.getStockage(),
						computer.getRam()
					};
					tableModel.addRow(rowData);
				}
			}
			JTable table = new JTable(tableModel);
			JScrollPane scrollPane = new JScrollPane(table);
			frame.add(scrollPane, BorderLayout.CENTER);

			JButton viewButton = new JButton("Voir");
			JButton deleteButton = new JButton("Supprimer");
			JButton backButton = new JButton("Retour");
			JPanel buttonPanel = new JPanel();
			buttonPanel.add(viewButton);
			buttonPanel.add(deleteButton);
			buttonPanel.add(backButton);
			frame.add(buttonPanel);

			// If the user clicks on the view button, display the computer's details in a popup
			viewButton.addActionListener(e -> {
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un ordinateur.");
					return;
				}
				Computer selectedComputer = computers.get(selectedRow);
				JOptionPane.showMessageDialog(frame, selectedComputer.getAllFormatted());
			});

			deleteButton.addActionListener(e -> {
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un ordinateur.");
					return;
				}

				int option = JOptionPane.showConfirmDialog(
					frame, 
					"Êtes-vous sûr de vouloir supprimer cet ordinateur ?", 
					"Confirmation", 
					JOptionPane.YES_NO_OPTION
				);
				if (option == JOptionPane.YES_OPTION) {
					csvWriter.deleteComputer(selectedRow);
					JOptionPane.showMessageDialog(frame, "Ordinateur supprimé avec succès !");
					this.handleMenuChange(frame, "view");
				}
			});
	
			backButton.addActionListener(e -> {
				this.handleMenuChange(frame, "main");
			});
		}
	}

	public void handleMenuChange(Frame frame, String menu) {
			// Centralized menu switching
		// First, dispose of the current frame
		frame.dispose();
		// Then, open the new one
		switch (menu) {
			case "add":
				this.displayAddMenu();
				break;
			case "view":
				this.displayViewMenu();
				break;
			default:
				this.displayMenu();
				break;
		}
	}
}
