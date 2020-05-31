
package com.example.services;

import com.example.PersistenceManager;
import com.example.models.Competitor;
import com.example.models.CompetitorDTO;
import com.example.models.Producto;
import com.example.models.ProductoDTO;
import com.google.gson.Gson;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;



@Path("/competitors")
@Produces(MediaType.APPLICATION_JSON)
public class CompetitorService {

    @PersistenceContext(unitName = "CompetitorsPU")
    EntityManager entityManager;
    
    @PostConstruct
    public void init() {
        try {
            entityManager
                    = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        
        Query q = entityManager.createQuery("select u from Competitor u order by u.surname ASC");
        List<Competitor> competitors= q.getResultList();
        
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(competitors).build();
    }
    
    @GET
    @Path("/getallProduc")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProduc() {
        
        Query q = entityManager.createQuery("select p from Producto p order by p.name ASC");
        List<Producto> pro= q.getResultList();
        
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(pro).build();
    }


    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCompetitor(CompetitorDTO competitor) {
       
        JSONObject respuesta= new JSONObject();
        
        Competitor competitorTmp= new Competitor(competitor.getName(), competitor.getSurname(), competitor.getAge(), competitor.getTelephone(), competitor.getCellphone(), competitor.getAddress(), competitor.getCity(), competitor.getCountry(), competitor.isWinner(), 
                competitor.getContra());
        Iterator it= competitor.getProductos().iterator();
        while(it.hasNext()){
            Producto obj = (Producto)it.next();
            Producto pr = new Producto(obj.getName(), obj.getPrice(), obj.getDescription());
            pr.setCompetitor(competitorTmp);
            competitorTmp.getProductos().add(pr);
        }        
        
        try {
        entityManager.getTransaction().begin();
        entityManager.persist(competitorTmp);
        entityManager.getTransaction().commit();
        entityManager.refresh(competitorTmp);
        respuesta.put("competitor_id", competitorTmp.getId());
        
        } catch (Throwable e) {
            e.printStackTrace();
            if(entityManager.getTransaction().isActive()){
                entityManager.getTransaction().rollback();
                competitorTmp=null;
            }
        }
        finally{
            entityManager.clear();
            entityManager.close();
        }
        
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(respuesta).build();
    }

    @POST
    @Path("/inProd")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProduct(ProductoDTO producto) {

        JSONObject respuesta = new JSONObject();
        Producto productoTmp= new Producto(producto.getName(), producto.getPrice(), producto.getDescription());    
        
     try{    
        entityManager.getTransaction().begin();
        entityManager.persist(productoTmp);
        entityManager.getTransaction().commit();
        entityManager.refresh(productoTmp);
        respuesta.put("producto_id", productoTmp.getId());
        
        }catch(Throwable t){
            t.printStackTrace();
            if(entityManager.getTransaction().isActive()){
                entityManager.getTransaction().rollback();
                productoTmp = null;
            }
        }finally{
         entityManager.clear();
         entityManager.close();
     }
     
      return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(respuesta).build();
    }
    
    @GET
    @Path("/Log")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logeo(CompetitorDTO competitor){
        
       String resp="",resp2;
       String email=competitor.getAddress();
       String pass=competitor.getContra();
       
       JSONObject respuesta = new JSONObject();
       Query q  = entityManager.createQuery("select u.address from Competitor u " );
       List<String> resul = q.getResultList();
       
       Query q2  = entityManager.createQuery("select u.contra from Competitor u ");
       List<String> resul2 = q2.getResultList();
       
        for (int i = 0; i < resul.size(); i++) {
            resp=resul.get(i);
            resp2=resul2.get(i);
            if(resp.equals(email)&&resp2.equals(pass)){
                resp="correcto";
                
            }else{
              resp="no esta";  
            }
            
        }
        
       respuesta.put("entro", resp);
       
       return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(respuesta).build();
    }
    
}
