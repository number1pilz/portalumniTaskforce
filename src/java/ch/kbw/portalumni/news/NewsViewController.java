/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.kbw.portalumni.news;

import ch.kbw.portalumni.hibernate.HibernateUtil;
import ch.kbw.portalumni.hibernatedata.Event;
import ch.kbw.portalumni.hibernatedata.News;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Session;

/**
 *
 * @author Silvan Baach
 */
@SessionScoped
@ManagedBean
public class NewsViewController {

    private ArrayList<News> newsList;

    public NewsViewController() {
        newsList = new ArrayList();
    }

    public ArrayList<News> getNews() {
        //get all Events
        Session s = HibernateUtil.getInstance().openSession();
        setNewsList((ArrayList<News>) s.createQuery("FROM News").list());

        return getNewsList();
    }

    /**
     * @return the newsList
     */
    public ArrayList<News> getNewsList() {
        return newsList;
    }

    /**
     * @param newsList the newsList to set
     */
    public void setNewsList(ArrayList<News> newsList) {
        this.newsList = newsList;
    }

 


    
    
}
