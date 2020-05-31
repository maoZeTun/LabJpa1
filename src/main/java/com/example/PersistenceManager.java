
package com.example;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class PersistenceManager {
    public static final boolean DEBUG=true;
    private static final PersistenceManager singleton= new PersistenceManager();
    protected EntityManagerFactory emf;
    
    public static PersistenceManager getInstance(){
        return singleton;
    }
    
    private PersistenceManager (){}
    
    public EntityManagerFactory getEntityManagerFactory(){
        if(emf==null){
            createEntityManagerFactory();
        }
        return emf;
    }
    
    public void createEntityManagerFactory(){
        this.emf= Persistence.createEntityManagerFactory("CompetiitorsPU",System.getProperties());
        if (DEBUG){
        System.out.println("Persistence started at " + new java.util.Date());
        }
    }
    
    public void closeEntityManagerFactory(){
        if(emf!=null){
            emf.close();
            emf=null;
        }if(DEBUG){
            System.out.println("Persistence fin at " + new java.util.Date());
        }
        
    }
    
    
}