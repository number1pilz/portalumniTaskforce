/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.kbw.portalumni.admin;

import ch.kbw.portalumni.hibernate.HibernateUtil;
import ch.kbw.portalumni.hibernatedata.Benutzer;
import ch.kbw.portalumni.hibernatedata.Event;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Session;

/**
 *
 * @author Silvan Baach
 */
@ManagedBean
@SessionScoped
public class UserAdministrationViewController {
    private ArrayList<Benutzer> userList;

    public UserAdministrationViewController() {
        userList = new ArrayList();
    }
    
    
    public ArrayList getAllUsers(){
        //get all User
        Session s = HibernateUtil.getInstance().openSession();
        setUserList((ArrayList<Benutzer>) s.createQuery("FROM Benutzer").list());
        
        return userList;
    }

    /**
     * @return the userList
     */
    public ArrayList<Benutzer> getUserList() {
        return userList;
    }

    /**
     * @param userList the userList to set
     */
    public void setUserList(ArrayList<Benutzer> userList) {
        this.userList = userList;
    }
    
    
}
