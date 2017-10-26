package com.mmarchand.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 Exemple d'implémentation du patron Singleton.
 */
public class Connexion {
    
    private static Connection cnx;
    private static String url, user = "", password = "";
        
    private Connexion(){}
    
    //GET INSTANCE
    public static Connection getInstance(){
	if (cnx == null)
            try {
		if (user.equals(""))
                    cnx = DriverManager.getConnection(url);
		else
                    cnx = DriverManager.getConnection(url,user,password);
		} 
            catch (SQLException e) {
		e.printStackTrace();
            }
	return cnx;
    }
    
    //RE-INIT
    public static void reinit(){
	cnx = null;
    }
    
    //CLOSE
    public static void close(){
	try {
            if (cnx!=null){
		cnx.close();
                cnx = null;
            }
	} 
        catch (SQLException e){
            e.printStackTrace();
	}
    }
    
    //GET URL
    public static String getUrl(){
	return url;
    }
    
    //SET URL
    public static void setUrl(String url){
	Connexion.url = url;
    }
    
    //GET USER
    public static String getUser(){
	return user;
    }
    
    //SET USER
    public static void setUser(String user){
	Connexion.user = user;
    }
    
    //SET PASSWORD
    public static void setPassword(String password){
	Connexion.password = password;
    }
}
