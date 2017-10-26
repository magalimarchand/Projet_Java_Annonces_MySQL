package com.mmarchand.implementation;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.mmarchand.dao.DAO;
import com.mmarchand.modele.Annonce;

public class AnnonceDAO extends DAO<Annonce> {

    //CONSTRUCTEUR
    public AnnonceDAO(Connection c){
	super(c);
    }
    
    //CREATE ANNONCE
    @Override
    public boolean create(Annonce x) {
	String req = "INSERT INTO annonce (`TITRE` , `NOM_PROPRIO` , `DESCRIPTION` , `PRIX` , `LIEN_PHOTO`) "+
		     "VALUES ('"+x.getTitre()+"','"+x.getNomProprio()+"','"+x.getDescription()+"','"+x.getPrix()+"','"+x.getLienPhoto()+"')";
	Statement stm = null;
	try {
            stm = cnx.createStatement(); 
            int n= stm.executeUpdate(req);
            if (n>0){
		stm.close();
		return true;
            }
	}catch (SQLException exp){}
	finally{
            if (stm!=null)
		try {
                    stm.close();
		}catch (SQLException e) {
                    e.printStackTrace();
		}			
	}
	return false;
    }

    //DELETE ANNONCE
    @Override
    public boolean delete(Annonce x) {
	Statement stm = null;
	try{
            stm = cnx.createStatement(); 
            int n= stm.executeUpdate("DELETE FROM annonce WHERE titre='"+x.getTitre()+"'");
            if (n>0){
		stm.close();
                return true;
            }
	}catch (SQLException exp){}
	finally{
            if (stm!=null)
            try {
		stm.close();
            }catch (SQLException e) {
		e.printStackTrace();
            }			
	}
	return false;
    }

    //READ ANNONCE
    @Override
    public Annonce read(String titre) {
        Statement stm = null;
        ResultSet r = null;
	try{
            stm = cnx.createStatement(); 
            r = stm.executeQuery("SELECT * FROM annonce WHERE titre = '"+titre+"'");
            if (r.next()){
		Annonce c = new Annonce(r.getString("TITRE"),
					r.getString("NOM_PROPRIO"),
					r.getString("DESCRIPTION"),
                                        r.getDouble("PRIX"),
					r.getString("LIEN_PHOTO"));
		r.close();
		stm.close();
		return c;
            }
	}catch (SQLException exp){}
	finally{
            if (stm!=null)
		try {
                    r.close();
                    stm.close();
		}catch (SQLException e) {
                    e.printStackTrace();
		}			
	}
	return null;
    }

    //FIND ALL ANNONCE
    @Override
    public List<Annonce> findAll() {
	List<Annonce> liste = new LinkedList<Annonce>();
	try{
            Statement stm = cnx.createStatement(); 
            ResultSet r = stm.executeQuery("SELECT * FROM annonce");
            while (r.next()){
		Annonce c = new Annonce(r.getString("TITRE"),
					r.getString("NOM_PROPRIO"),
                                        r.getString("DESCRIPTION"),
					r.getDouble("PRIX"),
					r.getString("LIEN_PHOTO"));
		liste.add(c);
            }
            r.close();
            stm.close();
	}catch (SQLException exp){}
	return liste;
    }
}
