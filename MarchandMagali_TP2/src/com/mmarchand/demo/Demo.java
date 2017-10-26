package com.mmarchand.demo;

import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import com.mmarchand.implementation.AnnonceDAO;
import com.mmarchand.modele.Annonce;
import com.mmarchand.util.Connexion;
import java.sql.Connection;


public class Demo extends JFrame implements ActionListener{
    
    private JPanel jp1 = new JPanel();
    private JToolBar tbNavig = new JToolBar();
    private JButton btnPremier = new JButton("Premier"),
                  btnPrecedent = new JButton("Précédent"),
                    btnSuivant = new JButton("Suivant"),
                    btnDernier = new JButton("Dernier"),
                        btnAdd = new JButton("Ajouter"),
                       btnSupp = new JButton("Supprimer");
            
    private JLabel lbTitre = new JLabel("Titre: "),
                     lbNom = new JLabel("Nom Propriétaire: "),
                    lbDesc = new JLabel("Description: "),
                    lbPrix = new JLabel("Prix: "), lb1, lb2;
    private JTextArea jtDescription = new JTextArea(100,70);
    private static int index = 0;
    private List<Annonce> listAnnonces;
    private NouvelleAnnonce nouvelleAnnonce;
    private Annonce annonce;
    private AnnonceDAO dao;
    
    //CONSTRUCTEUR DEMO
    public Demo() throws Exception{
        
        super("VOTRE LISTE D'ANNONCES");
        super.setResizable(false);
        
        //CONNECTION A LA BD
        Class.forName("com.mysql.jdbc.Driver");
	Connexion.setUrl("jdbc:mysql://localhost/ads?user=root&password=root");
	Connection cnx = Connexion.getInstance();
        dao = new AnnonceDAO(cnx);   
        annonce = dao.read("IPhone 5");  
			

        //REMPLISSAGE DE LA LISTE D'ANNONCES AVEC LA BD
	listAnnonces = dao.findAll();
        
        //DEFINITION DES COMPOSANTS VUE
        btnPremier.addActionListener(this);
        btnPrecedent.addActionListener(this);
        btnSuivant.addActionListener(this);
        btnDernier.addActionListener(this);
        btnAdd.addActionListener(this);
        btnSupp.addActionListener(this);

        jtDescription.setEditable(false);
        jtDescription.setLineWrap(true);
        jtDescription.setWrapStyleWord(true);
        JScrollPane jsp = new JScrollPane(jtDescription);
        lb1 = new JLabel("         ");
        lb2 = new JLabel("         ");
        
        tbNavig.setFloatable(false);
        tbNavig.setRollover(true);
        tbNavig.setAlignmentY(CENTER_ALIGNMENT);
        tbNavig.addSeparator(); tbNavig.addSeparator();
        tbNavig.add(btnPremier); tbNavig.addSeparator();
        tbNavig.add(btnPrecedent); tbNavig.addSeparator();
        tbNavig.add(btnSuivant); tbNavig.addSeparator();
        tbNavig.add(btnDernier); tbNavig.addSeparator();
        tbNavig.add(Box.createHorizontalGlue());
        tbNavig.add(btnAdd); tbNavig.addSeparator();
        tbNavig.add(btnSupp); tbNavig.addSeparator(); tbNavig.addSeparator();
        
        jp1.setLayout(new GridLayout(4,1));
        jp1.add(lbTitre);
        jp1.add(jsp);
        jp1.add(lbPrix);
        jp1.add(lbNom);
        
        //AFFICHAGE DE L'ANNONCE
        afficheAnnonce();
        
        //DEFINITION DU PANNEAU D'ANNONCES
        add(tbNavig,BorderLayout.NORTH); 
        add(jp1,BorderLayout.CENTER); 
        add(lb1,BorderLayout.EAST);
        add(lb2,BorderLayout.WEST);
        setSize(500,350);
        setLocation(50,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    //AFFICHER ANNONCE
    public void afficheAnnonce(){
        
        lbTitre.setText(listAnnonces.get(index).getTitre());
        jtDescription.setText(listAnnonces.get(index).getDescription());
        lbPrix.setText(String.valueOf(listAnnonces.get(index).getPrix()));
        lbNom.setText(listAnnonces.get(index).getNomProprio());
    }

    //DEFINITION DES ACTIONS DES BOUTONS DE CONTROLE DU PANNEAU ANNONCES
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnPremier){
	    index = 0;
            afficheAnnonce();
	}    
        if (e.getSource() == btnPrecedent){
	    if(index > 0)
                index--;
            else
                index = 0;
            afficheAnnonce();
	} 
        if (e.getSource() == btnSuivant){
	    if(index < listAnnonces.size()-1)
                index++;
            else
                index = listAnnonces.size()-1;
            afficheAnnonce();
	} 
        if (e.getSource() == btnDernier){
	    index = listAnnonces.size()-1;
            afficheAnnonce();
	}
        if (e.getSource() == btnAdd){
	    nouvelleAnnonce = new NouvelleAnnonce(this);
            if(!nouvelleAnnonce.getTitre().trim().equals(" ")){
                Annonce a1 = new Annonce(nouvelleAnnonce.getTitre(), "Pierre Laporte",nouvelleAnnonce.getDescription(),
                                 nouvelleAnnonce.getPrix(), nouvelleAnnonce.getLienPhoto());
                listAnnonces.add(a1);
                afficheAnnonce();
                dao.create(a1);
            }
	} 
      
        if (e.getSource() == btnSupp){
	    int r = JOptionPane.showConfirmDialog(this,"Voulez-vous vraiment supprimer l'annonce?",
                   "Confirmation",JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
            if (r == JOptionPane.YES_OPTION){
                JOptionPane.showMessageDialog(this,"L'annonce a été supprimée.");
                listAnnonces.remove(index);
                afficheAnnonce();
                dao.delete(listAnnonces.get(index));
           }
	}
        
    }
    
    //CLASSE D'UNE NOUVELLE ANNONCE (BOITE DIALOGUE)
    public class NouvelleAnnonce extends JDialog implements ActionListener{
    
    private JPanel jp1;
    private JButton btnOk, btnAnnuler;
    private JLabel lbTitre, lbDescription, lbPrix;
    private JTextField jtTitre, jtPrix;
    private JTextArea jtDescription;
    private String titre, description;
    private double prix;
    
    
    public NouvelleAnnonce(Frame parent){
        
        super(parent,"NOUVELLE ANNONCE",true);
        super.setResizable(false);
          
        jp1 = new JPanel();
        jp1.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5,5,5,5);
        
        lbTitre = new JLabel("Titre: ");
        gbc.gridy = 0;
        jp1.add(lbTitre, gbc);
        
        jtTitre = new JTextField(" ");
        gbc.gridy = 1;
        jp1.add(jtTitre, gbc);
        
        lbDescription = new JLabel("Description: ");
        gbc.gridy = 2;
        jp1.add(lbDescription, gbc);
        
        jtDescription = new JTextArea(" ");
        JScrollPane sp = new JScrollPane(jtDescription); 
        gbc.gridy = 3;
        gbc.ipadx = 250;
        gbc.ipady = 70; 
        jp1.add(sp, gbc);
        
        lbPrix = new JLabel("Prix: ");
        gbc.gridy = 4;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        jp1.add(lbPrix, gbc);
        
        jtPrix = new JTextField(" ");
        gbc.gridy = 5;
        jp1.add(jtPrix, gbc);
        
        gbc.fill = GridBagConstraints.SOUTHEAST;
        btnOk = new JButton("OK");
        gbc.gridx = 2;
        gbc.gridy = 6;
        jp1.add(btnOk, gbc);
        
        btnAnnuler = new JButton("Annuler");
        gbc.gridx = 3;
        gbc.gridy = 6;
        jp1.add(btnAnnuler, gbc);
        
        btnOk.addActionListener(this);
        btnAnnuler.addActionListener(this);
        
        add(jp1);
        setSize(500,350);
        setLocation(650,200);
        setVisible(true);
    }

    //GETTERS & SETTERS NOUVELLE ANNONCE (BOITE DIALOGUE)
    public String getTitre() {return titre;}
    public String getDescription() {return description;}
    public double getPrix() {return prix;}
    public String getLienPhoto() {return null;}
    public void setTitre(String titre) {this.titre = titre;}
    public void setDescription(String description) {this.description = description;}
    public void setPrix(double prix) {this.prix = prix;}
    public void setLienPhoto(String lienPhoto) {}

    //ACTION PERFORMED NOUVELLE ANNONCE (BOITE DIALOGUE)
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == btnOk){
	    int r = JOptionPane.showConfirmDialog(this,"Voulez-vous enregistrer l'annonce?",
                   "Confirmation",JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
	    if (r == JOptionPane.YES_OPTION){
                //Enregistrement des données de l'annonce à ajouter:
                titre = jtTitre.getText();
                description = jtDescription.getText();
                try{
                    prix = Double.parseDouble(jtPrix.getText());
                    JOptionPane.showMessageDialog(this,"L'annonce a été enregistrée.");
                    this.dispose();
                }
                catch(NumberFormatException exc){
                    JOptionPane.showMessageDialog(this,"Le prix entré n'est pas au bon format.");
                }
                /*****AJOUT*****/
                
           }
	}    
    
        if (e.getSource() == btnAnnuler){
            int r = JOptionPane.showConfirmDialog(this,"Voulez-vous vraiment quitter?\nVotre annonce ne sera pas enregistrée.",
                         "Confirmation",JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
	    if (r == JOptionPane.YES_OPTION)
                this.dispose();
        }   
    }    
}


    //MAIN
    public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		new Demo();
    }
}
