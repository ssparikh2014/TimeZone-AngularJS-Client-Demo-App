/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nsuniexample.timezonewebclient;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.PendingResult;
import com.google.maps.TimeZoneApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import static java.util.Date.UTC;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author CSE42
 */
public class TimeZoneServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TimeZoneServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Here is your Geocoding Info !!!!!</h1>");
            // Following statement reads the Http Request parameter named "address"
            String address = request.getParameter("address");
            // following statement sets the API key and creates the GeoApiContext object
            GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyBauCuJFY9WVdRfbQXIbx74x53DZBbHu5c");
            // Following code calls Google Geocoding Web Service            
           GeocodingResult[] results;
            try {                
                results = GeocodingApi.geocode(context,address).await();
                out.println("<h2>Given Formatted Address: "+results[0].formattedAddress + "</h2>");
                LatLng latlng = results[0].geometry.location;
                out.println("<h2>Latitude :" +latlng.lat + "<h2>");
                out.println("<h2>Longitude :" +latlng.lng + "<h2>");
                // Following code calls Google TimeZone Web Service synchronously
                PendingResult<java.util.TimeZone> pr = (PendingResult) TimeZoneApi.getTimeZone(context, latlng);
              TimeZone tz = pr.await();
              
              // following code get the running millisecs from for date 2/19/2016 
             Calendar c = Calendar.getInstance();
             c.set(2016,2,19);
             long date = c.getTimeInMillis();
             out.println("<h3>Raw Time Offset :" + tz.getOffset(date) + "<h3>");
              
              out.println("<h3>DaySavingTime Offset" + tz.getDSTSavings()+ "<h3>");
              out.println("<h3>TimeZone Name :" + tz.getDisplayName() + "<h3>");
              
             
                     
              
            } catch (Exception ex) {
                Logger.getLogger(TimeZoneServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
             out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
