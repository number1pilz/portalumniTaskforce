/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.kbw.portalumni.news;

import ch.kbw.portalumni.event.EventCreatorViewController;
import ch.kbw.portalumni.hibernate.HibernateUtil;
import ch.kbw.portalumni.hibernatedata.Event;
import ch.kbw.portalumni.hibernatedata.News;
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
public class NewsViewController {
    
    
    private ArrayList<News> newsList;
    private String prewStr;
    private News rightNews = null;

    public NewsViewController() {
        newsList = new ArrayList();
    }

    public ArrayList<News> getNews() {
        //get all Events
        Session s = HibernateUtil.getInstance().openSession();
        setNewsList((ArrayList<News>) s.createQuery("FROM News").list());

        return getNewsList();
    }

    public String prewText(String text) {
        for (News n : newsList) {
            if (n.getText().equals(text)) {
                if (text.length() > 50) {
                    prewStr = text.substring(0, 50);
                    prewStr = prewStr + "...";
                } else {
                    prewStr = text;
                }
            }
        }

        return prewStr;
    }

    public void redirectToFullPost(String title) {
        for (News n : newsList) {
            if (n.getTitel().equalsIgnoreCase(title)) {
                rightNews = n;
                //REDIRECT
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("fullNews.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(EventCreatorViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
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

    /**
     * @return the prewStr
     */
    public String getPrewStr() {
        return prewStr;
    }

    /**
     * @param prewStr the prewStr to set
     */
    public void setPrewStr(String prewStr) {
        this.prewStr = prewStr;
    }

    /**
     * @return the rightNews
     */
    public News getRightNews() {
        return rightNews;
    }

    /**
     * @param rightNews the rightNews to set
     */
    public void setRightNews(News rightNews) {
        this.rightNews = rightNews;
    }
    
}
