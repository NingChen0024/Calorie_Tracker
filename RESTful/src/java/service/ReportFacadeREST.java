/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import A1.Report;
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
@Path("a1.report")
public class ReportFacadeREST extends AbstractFacade<Report> {

    @PersistenceContext(unitName = "Assignment1PU")
    private EntityManager em;

    public ReportFacadeREST() {
        super(Report.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Report entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Report entity) {
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
    public Report find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("findByReportDate/{reportDate}")
    @Produces({"application/json"})
    public List<Report> findByReportDate(@PathParam("reportDate") String reportDate) throws ParseException{
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       Date dateObj = sdf.parse(reportDate);
       Query query = em.createNamedQuery("Report.findByReportDate");
       query.setParameter("reportDate", dateObj);
       return query.getResultList();
               
    }
 
    @GET
    @Path("findByTotalConsumed/{totalConsumed}")
    @Produces({"application/json"})
    public List<Report> findByTotalConsumed (@PathParam("totalConsumed") Integer totalConsumed){
        Query query = em.createNamedQuery("Report.findByTotalConsumed");
        query.setParameter("totalConsumed", totalConsumed);
        return query.getResultList();
    }
    
    @GET
    @Path("findByTotalBurned/{totalBurned}")
    @Produces({"application/json"})
    public List<Report> findByTotalBurned (@PathParam("totalBurned") Integer totalBurned){
        Query query = em.createNamedQuery("Report.findByTotalBurned");
        query.setParameter("totalBurned", totalBurned);
        return query.getResultList();
    }
    
    @GET
    @Path("findByTotalSteps/{totalSteps}")
    @Produces({"application/json"})
    public List<Report> findByTotalSteps (@PathParam("totalSteps") Integer totalSteps){
        Query query = em.createNamedQuery("Report.findByTotalSteps");
        query.setParameter("totalSteps", totalSteps);
        return query.getResultList();
    }
    
    @GET
    @Path("findByUserId/{userId}")
    @Produces({"application/json"})
    public List<Report> findByUserId (@PathParam("userId") Integer userId){
        Query query = em.createNamedQuery("Report.findByUserId");
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    
    
    @GET
    @Path("findByCalorieGoal/{calorieGoal}")
    @Produces({"application/json"})
    public List<Report> findByCalorieGoal (@PathParam("calorieGoal") Integer calorieGoal){
        Query query = em.createNamedQuery("Report.findByCalorieGoal");
        query.setParameter("calorieGoal", calorieGoal);
        return query.getResultList();
    }
    
    @GET
    @Path("findCaloriesConsumedBurnedandRemained/{userId}/{reportDate}")
    @Produces({"text/plain"})
    public String findCaloriesConsumedBurnedandRemained (@PathParam("userId") Integer userId, 
                                                         @PathParam("reportDate") String reportDate) throws ParseException {
        
        TypedQuery<Report> q = em.createQuery("SELECT r FROM Report r WHERE r.userId.userId = :userId "
                                            + "AND r.reportDate = :reportDate", Report.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObj = sdf.parse(reportDate);
        q.setParameter("userId", userId);
        q.setParameter("reportDate", dateObj);
        
        List<Report> resultList = q.getResultList(); 
        
        int totalConsumed = 0;
        int totalBurned = 0;
        int totalRemained = 0;
        
        totalConsumed = resultList.get(0).getTotalConsumed();
        totalBurned = resultList.get(0).getTotalBurned();
        totalRemained = totalConsumed - totalBurned - resultList.get(0).getCalorieGoal();
       
        String finalString = totalConsumed + "," + totalBurned + "," + totalRemained;
                
        return finalString;
    }
    
    @GET
    @Path("findCaloriesConsumedBurnedandStepsInAPeriod/{userId}/{startingDate}/{endingDate}")
    @Produces({"text/plain"})
    public String findCaloriesConsumedBurnedandStepsInAPeriod (@PathParam("userId") Integer userId, 
                                                               @PathParam("startingDate") String startingDate,
                                                               @PathParam("endingDate") String endingDate) throws ParseException {
        
        TypedQuery<Report> q = em.createQuery("SELECT r FROM Report r WHERE r.userId.userId = :userId "
                                            + "AND r.reportDate >= :startingDate AND r.reportDate <= :endingDate", Report.class);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Date startingDateObj = sdf.parse(startingDate);
        Date endingDateObj = sdf.parse(endingDate);
        
        q.setParameter("userId", userId);
        q.setParameter("startingDate", startingDateObj);
        q.setParameter("endingDate", endingDateObj);
        
        List<Report> resultList = q.getResultList(); 
        
        int totalConsumed = 0;
        int totalBurned = 0;
        int totalSteps = 0;
        for (int index = 0; index < resultList.size(); index++){
            totalConsumed = totalConsumed + resultList.get(index).getTotalConsumed();
            totalBurned = totalBurned + resultList.get(index).getTotalBurned();
            totalSteps = totalSteps + resultList.get(index).getTotalSteps();
        }
        

        String finalString = totalConsumed + "," + totalBurned + "," + totalSteps;
                
        return finalString;
    }
    
    
}
