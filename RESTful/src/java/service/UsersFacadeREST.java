/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import A1.Users;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
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
@Path("a1.users")
public class UsersFacadeREST extends AbstractFacade<Users> {

    @PersistenceContext(unitName = "Assignment1PU")
    private EntityManager em;

    public UsersFacadeREST() {
        super(Users.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Users entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Users entity) {
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
    public Users find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Users> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Users> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("findByUserName/{userName}")
    @Produces({"application/json"})
    public List<Users> findByUserName (@PathParam("userName") String userName){
        Query query = em.createNamedQuery("Users.findByUserName");
        query.setParameter("userName", userName);
        return query.getResultList();
    }
    @GET
    @Path("findByUserSurname/{userSurname}")
    @Produces({"application/json"})
    public List<Users> findByUserSurname (@PathParam("userSurname") String userSurname){
        Query query = em.createNamedQuery("Users.findByUserSurname");
        query.setParameter("userSurname", userSurname);
        return query.getResultList();
    }
    @GET
    @Path("findByEmail/{email}")
    @Produces({"application/json"})
    public List<Users> findByEmail (@PathParam("email") String email){
        Query query = em.createNamedQuery("Users.findByEmail");
        query.setParameter("email", email);
        return query.getResultList();
    }
    @GET
    @Path("findByDob/{dob}")
    @Produces({"application/json"})
    public List<Users> findByDob(@PathParam("dob") String dob) throws ParseException{
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       Date dateObj = sdf.parse(dob);
       Query query = em.createNamedQuery("Users.findByDob");
       query.setParameter("dob", dateObj);
       return query.getResultList();
    }
    
    @GET
    @Path("findByHeight/{height}")
    @Produces({"application/json"})
    public List<Users> findByHeight (@PathParam("height") Integer height){
        Query query = em.createNamedQuery("Users.findByHeight");
        query.setParameter("height", height);
        return query.getResultList();
    }
    @GET
    @Path("findByWeight/{weight}")
    @Produces({"application/json"})
    public List<Users> findByWeight (@PathParam("weight") Integer weight){
        Query query = em.createNamedQuery("Users.findByWeight");
        query.setParameter("weight", weight);
        return query.getResultList();
    }   
    @GET
    @Path("findByGender/{gender}")
    @Produces({"application/json"})
    public List<Users> findByGender (@PathParam("gender") String gender){
        Query query = em.createNamedQuery("Users.findByGender");
        query.setParameter("gender", gender);
        return query.getResultList();
    }

    @GET
    @Path("findByAddress/{address}")
    @Produces({"application/json"})
    public List<Users> findByAddress (@PathParam("address") String address){
        Query query = em.createNamedQuery("Users.findByAddress");
        query.setParameter("address", address);
        return query.getResultList();
    }
    
    @GET
    @Path("findByPostcode/{postcode}")
    @Produces({"application/json"})
    public List<Users> findByPostcode (@PathParam("postcode") String postcode){
        Query query = em.createNamedQuery("Users.findByPostcode");
        query.setParameter("postcode", postcode);
        return query.getResultList();
    }
    
    @GET
    @Path("findByLoact/{loact}")
    @Produces({"application/json"})
    public List<Users> findByLoact (@PathParam("loact") Integer loact){
        Query query = em.createNamedQuery("Users.findByLoact");
        query.setParameter("loact", loact);
        return query.getResultList();
    }
    
    @GET
    @Path("findBySpmile/{spmile}")
    @Produces({"application/json"})
    public List<Users> findBySpmile (@PathParam("spmile") Integer spmile){
        Query query = em.createNamedQuery("Users.findBySpmile");
        query.setParameter("spmile", spmile);
        return query.getResultList();
    }
    
    @GET
    @Path("findByNameAndSurname/{userName}/{userSurname}")
    @Produces({"application/json"})
    public List<Users> findByNameAndSurname (@PathParam("userName") String userName, @PathParam("userSurname") String userSurname) {
        TypedQuery<Users> q = em.createQuery("SELECT u FROM Users u WHERE u.userName = :userName AND u.userSurname = :userSurname", Users.class);
        q.setParameter("userName", userName);
        q.setParameter("userSurname", userSurname);
        return q.getResultList();
    } 
    
    @GET
    @Path("findCaloriesBurnedPerStep/{userId}")
    @Produces({"text/plain"})
    public Double findCaloriesBurnedPerStep (@PathParam("userId") Integer userId) {
        TypedQuery<Users> q = em.createQuery("SELECT u FROM Users u WHERE u.userId = :userId ", Users.class);
        q.setParameter("userId", userId);
        List<Users> resultList = q.getResultList();
        double weight = resultList.get(0).getWeight();
        double SpMile = resultList.get(0).getSpmile();
        weight = weight * 2.20462;//convert kg to pound
        double bpm = weight * 0.49;
        double result = bpm/SpMile;
        return result;
    } 
    
    @GET
    @Path("calculateBMR/{userId}")
    @Produces({"text/plain"})
    public double calculateBMR (@PathParam("userId") Integer userId) {
        TypedQuery<Users> q = em.createQuery("SELECT u FROM Users u WHERE u.userId = :userId ", Users.class);
        q.setParameter("userId", userId);
        List<Users> resultList = q.getResultList();
        double weight = resultList.get(0).getWeight();
        double height = resultList.get(0).getHeight();
        String gender = resultList.get(0).getGender();
        
        //calculate age based on current date and birthday
        Date dob = resultList.get(0).getDob();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dob);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        LocalDate pdate = LocalDate.of(year, month, day);
        LocalDate now = LocalDate.now();
        Period diff = Period.between(pdate, now);
        int age = diff.getYears();
        
        
        double BMR = 0.0;
        if (gender.equals("M")){
            
            BMR = (13.75 * weight) + (5.003 * height) + (6.755 * age) + 66.5;
        }
        else if(gender.equals("F")){
            
            BMR = (9.563 * weight) + (1.85 * height) + (4.676 * age) + 655.1;
        }

        return BMR;
    } 
    
    @GET
    @Path("calculateTotalCaloBurnedAtRest/{userId}")
    @Produces({"text/plain"})
    public Double calculateTotalCaloBurnedAtRest (@PathParam("userId") Integer userId) {
        TypedQuery<Users> q = em.createQuery("SELECT u FROM Users u WHERE u.userId = :userId ", Users.class);
        q.setParameter("userId", userId);
        List<Users> resultList = q.getResultList();
        int loA = resultList.get(0).getLoact();
        double BMR = calculateBMR(userId);//call calculateBMR method
        double totalCalo = 0.0;
        
        switch (loA){
            case 1:
                totalCalo = BMR * 1.2;
                break;
                
            case 2:
                totalCalo = BMR * 1.375;
                break;
                
            case 3:
                totalCalo = BMR * 1.55;
                break;
                
            case 4:
                totalCalo = BMR * 1.725;
                break;
                
            default:
                totalCalo = BMR * 1.9;
        }
        
        return totalCalo;
    } 
    
    
}
