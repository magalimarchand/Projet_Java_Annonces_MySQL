package com.mmarchand.modele;

public class Annonce {
    
    private String titre, nomProprio, description, lienPhoto;
    private double prix;

    public Annonce(String titre, String nomProprio, String description, double prix, String lienPhoto) {
        this.titre = titre;
        this.nomProprio = nomProprio;
        this.description = description;
        this.prix = prix;
        this.lienPhoto = lienPhoto;
    }

    public String getTitre() {
        return titre;
    }
    public String getDescription() {
        return description;
    }
    public String getNomProprio() {
        return nomProprio;
    }
    public double getPrix() {
        return prix;
    }
    public String getLienPhoto() {
        return lienPhoto;
    }
    

    public void setTitre(String titre) {
        this.titre = titre;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setNomProprio(String nomProprio) {
        this.nomProprio = nomProprio;
    }
    public void setPrix(double prix) {
        this.prix = prix;
    }
    public void setLienPhoto(String lienPhoto) {
        this.lienPhoto = lienPhoto;
    }
    
    @Override
	public String toString() {
		return ""+this.titre+","+this.nomProprio+","+this.description+","+this.prix+","+this.lienPhoto;
	}
     
}
