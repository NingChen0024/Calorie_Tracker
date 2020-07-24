/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import A1.Foods;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ning
 */
@Stateless
@Path("a1.foods")
public class FoodsFacadeREST extends AbstractFacade<Foods> {

    @PersistenceContext(unitName = "Assignment1PU")
    private EntityManager em;

    public FoodsFacadeREST() {
        super(Foods.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Foods entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Foods entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Foods find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Foods> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Foods> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @GET
    @Path("findByFoodName/{foodName}")
    @Produces({"application/json"})
    public List<Foods> findByFoodName (@PathParam("foodName") String foodName){
        Query query = em.createNamedQuery("Foods.findByFoodName");
        query.setParameter("foodName", foodName);
        return query.getResultList();
    }
    
    @GET
    @Path("findByCategory/{category}")
    @Produces({"application/json"})
    public List<Foods> findByCategory (@PathParam("category") String category){
        Query query = em.createNamedQuery("Foods.findByCategory");
        query.setParameter("category", category);
        return query.getResultList();
    } 
    
    @GET
    @Path("findByCalorieAmount/{calorieAmount}")
    @Produces({"application/json"})
    public List<Foods> findByCalorieAmount (@PathParam("calorieAmount") Integer calorieAmount){
        Query query = em.createNamedQuery("Foods.findByCalorieAmount");
        query.setParameter("calorieAmount", calorieAmount);
        return query.getResultList();
    }  
    
    @GET
    @Path("findByServingUnit/{servingUnit}")
    @Produces({"application/json"})
    public List<Foods> findByServingUnit (@PathParam("servingUnit") String servingUnit){
        Query query = em.createNamedQuery("Foods.findByServingUnit");
        query.setParameter("servingUnit", servingUnit);
        return query.getResultList();
    }
    
    @GET
    @Path("findByServingAmount/{servingAmount}")
    @Produces({"application/json"})
    public List<Foods> findByServingAmount (@PathParam("servingAmount") double servingAmount){
        Query query = em.createNamedQuery("Foods.findByServingAmount");
        query.setParameter("servingAmount", servingAmount);
        return query.getResultList();
    }
    
    @GET
    @Path("findByFat/{fat}")
    @Produces({"application/json"})
    public List<Foods> findByFat (@PathParam("fat") Integer fat){
        Query query = em.createNamedQuery("Foods.findByFat");
        query.setParameter("fat", fat);
        return query.getResultList();
    }  
    
    
    
}
