/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import A1.Consumption;
import A1.Foods;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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
@Path("a1.consumption")
public class ConsumptionFacadeREST extends AbstractFacade<Consumption> {

    @PersistenceContext(unitName = "Assignment1PU")
    private EntityManager em;

    public ConsumptionFacadeREST() {
        super(Consumption.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Consumption entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Consumption entity) {
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
    public Consumption find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Consumption> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Consumption> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("findByConsumeDate/{consumeDate}")
    @Produces({"application/json"})
    public List<Consumption> findByConsumeDate(@PathParam("consumeDate") String consumeDate) throws ParseException{
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       Date dateObj = sdf.parse(consumeDate);
       Query query = em.createNamedQuery("Consumption.findByConsumeDate");
       query.setParameter("consumeDate", dateObj);
       return query.getResultList();
               
    }
    
    
    @GET
    @Path("findByQtyofserving/{qtyofserving}")
    @Produces({"application/json"})
    public List<Consumption> findByQtyofserving (@PathParam("qtyofserving") Integer qtyofserving){
        Query query = em.createNamedQuery("Consumption.findByQtyofserving");
        query.setParameter("qtyofserving", qtyofserving);
        return query.getResultList();
    }
    
    @GET
    @Path("findByUserId/{userId}")
    @Produces({"application/json"})
    public List<Consumption> findByUserId (@PathParam("userId") Integer userId){
        Query query = em.createNamedQuery("Consumption.findByUserId");
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    
    @GET
    @Path("findByFoodId/{foodId}")
    @Produces({"application/json"})
    public List<Consumption> findByFoodId (@PathParam("foodId") Integer foodId){
        Query query = em.createNamedQuery("Consumption.findByFoodId");
        query.setParameter("foodId", foodId);
        return query.getResultList();
    }
    
    @GET
    @Path("findByFoodNameAndUserName/{foodName}/{userName}")
    @Produces({"application/json"})
    public List<Consumption> findByFoodNameAndUserName(@PathParam("foodName") String foodName, @PathParam("userName") String userName) {
        TypedQuery<Consumption> q = em.createQuery("SELECT c FROM Consumption c WHERE c.foodId.foodName = :foodName "
                + "AND c.userId.userName = :userName", Consumption.class);
        q.setParameter("foodName", foodName);
        q.setParameter("userName", userName);
        return q.getResultList();
    }
    
    @GET
    @Path("findByFoodNameAndUserName2/{foodName}/{userName}")
    @Produces({"application/json"})
    public List<Consumption> findByFoodNameAndUserName2(@PathParam("foodName") String foodName, @PathParam("userName") String userName) {
        Query query = em.createNamedQuery("Consumption.findByFoodNameAndUserName2");
        query.setParameter("foodName", foodName);
        query.setParameter("userName", userName);
        return query.getResultList();
    }
    
    @GET
    @Path("findTotalCaloConsumed/{userId}/{consumeDate}")
    @Produces({"text/plain"})
    public Double findTotalCaloConsumed (@PathParam("userId") Integer userId, 
                                         @PathParam("consumeDate") String consumeDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObj = sdf.parse(consumeDate);
        TypedQuery<Consumption> q = em.createQuery("SELECT c FROM Consumption c WHERE c.userId.userId = :userId "
                + "AND c.consumeDate = :consumeDate", Consumption.class);
        q.setParameter("userId", userId);
        q.setParameter("consumeDate", dateObj);
        List<Consumption> resultList = q.getResultList();  
        ArrayList<Integer> items = new ArrayList<>();
        ArrayList<Integer> qty = new ArrayList<>();
        for (int index = 0; index < resultList.size(); index++) {
            Consumption newFood = resultList.get(index);
            int newFoodId = newFood.getFoodId().getFoodId();
            int flag = 0; 
            int position = 0;
            for (int i = 0; i < items.size(); i++){
                if (items.get(i) == newFoodId){
                    flag = 1;
                    position = i;
                }
            }   
            if (flag == 0){
               items.add(newFoodId);
               qty.add(resultList.get(index).getQtyofserving());   
            }
            else{
               Integer newQty = qty.get(position) + resultList.get(index).getQtyofserving();
               qty.set(position, newQty);
            }
        }
        ArrayList<Double> consume = new ArrayList<>();
        for (int index = 0; index < items.size(); index++){
            int foodId = items.get(index);
            TypedQuery<Foods> fq = em.createQuery("SELECT f FROM Foods f WHERE f.foodId = :foodId ", Foods.class);
            fq.setParameter("foodId", foodId);
            List<Foods> food = fq.getResultList();
            int amount = food.get(0).getCalorieAmount();
            double qtyForEach = qty.get(index);
            consume.add(amount * qtyForEach);
        }
        double totalAmount = 0;
        for (int index = 0; index < consume.size(); index++){
            totalAmount = totalAmount + consume.get(index);
        }
        return totalAmount;
    }
}
