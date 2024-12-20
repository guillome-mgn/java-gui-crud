package mignon.guillome;

public class Computer {
    private String nom;
    private String type;
    private String marque;
    private String processeur;
    private String carteGraphique;
    private String stockage;
    private String ram;

    // Constructeur
    public Computer (String nom, String type, String marque, String processeur, String carteGraphique, String stockage, String ram) {
        this.nom = nom;
        this.type = type;
        this.marque = marque;
        this.processeur = processeur;
        this.carteGraphique = carteGraphique;
        this.stockage = stockage;
        this.ram = ram;
    }

    // Getters
    public String getNom() {
        return this.nom;
    }
    public String getType() {
        return this.type;
    }
    public String getMarque() {
        return this.marque;
    }
    public String getProcesseur() {
        return this.processeur;
    }
    public String getCarteGraphique() {
        return this.carteGraphique;
    }
    public String getStockage() {
        return this.stockage;
    }
    public String getRam() {
        return this.ram;
    }
    public String getAllFormatted() {
        return "Nom: " + this.nom + "\nType: " + this.type + "\nMarque: " + this.marque + "\nProcesseur: " + this.processeur + "\nCarte graphique: " + this.carteGraphique + "\nStockage: " + this.stockage + "\nRAM: " + this.ram;
    }

    // Setters
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setMarque(String marque) {
        this.marque = marque;
    }
    public void setProcesseur(String processeur) {
        this.processeur = processeur;
    }
    public void setCarteGraphique(String carteGraphique) {
        this.carteGraphique = carteGraphique;
    }
    public void setStockage(String stockage) {
        this.stockage = stockage;
    }
    public void setRam(String ram) {
        this.ram = ram;
    }
}
