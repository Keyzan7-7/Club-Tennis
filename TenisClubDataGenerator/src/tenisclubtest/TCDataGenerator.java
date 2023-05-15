/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tenisclubtest;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import model.Booking;
import model.Member;
import model.Club;
import model.ClubDAOException;
import model.Court;

/**
 *
 * @author jsoler
 */
public class TCDataGenerator {

    /**
     * This program clean de file club.db and generate ramdom bookings 
     * in the next 5 days
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClubDAOException, IOException {

        Club club= Club.getInstance(); 
        //==================================
        //Clean the file club.db
        club.setInitialData();
        
        //===================================
        // club data:
        System.out.println("Club name: "+ club.getName());
        for (Court court : club.getCourts()) {
            System.out.println("court:" + court.getName());
        }
        //===================================
        // add simple data:
        club.addSimpleData();
        
        //===================================
        // users        
        for (Member member : club.getMembers()) {
            System.out.println("member:" + member.getName()+ ", "+ member.getNickName());
        }
        
        //===================================
        // bookings now + 2 days
        System.out.println("Bookings after 2 days");
        List<Booking> forDayBookings = club.getForDayBookings(LocalDate.now().plusDays(2));
        for (Booking booking : forDayBookings) {
              System.out.println("booking:" + booking.getMember().getNickName()+
                      ", " + booking.getCourt().getName()+ ", "+
                      booking.getMadeForDay().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)) +
                      ", "+booking.getFromTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
        }   

    }

}
