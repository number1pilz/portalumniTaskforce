/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.kbw.portalumni.event;

import ch.kbw.portalumni.hibernate.HibernateUtil;
import ch.kbw.portalumni.hibernatedata.Event;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;

/**
 *
 * @author Silvan Baach
 */
@SessionScoped
@ManagedBean
public class EventViewController {

    private ArrayList<Event> eventList;

    public EventViewController() {
        eventList = new ArrayList();
    }

    public ArrayList<Event> getEvents() {
        //get all Events
        Session s = HibernateUtil.getInstance().openSession();
        setEventList((ArrayList<Event>) s.createQuery("FROM Event").list());

        return eventList;
    }

    /**
     * @return the eventList
     */
    public ArrayList<Event> getEventList() {
        return eventList;
    }

    /**
     * @param eventList the eventList to set
     */
    public void setEventList(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }
    
    
}
