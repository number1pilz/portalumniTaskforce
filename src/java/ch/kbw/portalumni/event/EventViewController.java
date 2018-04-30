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
    private ArrayList<String> imagesList;

    public EventViewController() {
        eventList = new ArrayList<>();
        imagesList = new ArrayList<>();
    }

    public ArrayList<Event> getEvents() {
        //get all Events
        Session s = HibernateUtil.getInstance().openSession();
        setEventList((ArrayList<Event>) s.createQuery("FROM Event").list());

        return eventList;
    }

    public ArrayList<String> getImages(Event e) {
        imagesList.clear();
        
        if (!e.getImg1().isEmpty()) {
            imagesList.add(e.getImg1());
        }
        if (!e.getImg2().isEmpty()) {
            imagesList.add(e.getImg2());
        }
        if (!e.getImg3().isEmpty()) {
            imagesList.add(e.getImg3());
        }

        return imagesList;
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

    /**
     * @return the imagesList
     */
    public ArrayList<String> getImagesList() {
        return imagesList;
    }

    /**
     * @param imagesList the imagesList to set
     */
    public void setImagesList(ArrayList<String> imagesList) {
        this.imagesList = imagesList;
    }
}
