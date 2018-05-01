/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.kbw.portalumni.admin;

import ch.kbw.portalumni.hibernate.HibernateUtil;
import ch.kbw.portalumni.hibernatedata.Benutzer;
import ch.kbw.portalumni.hibernatedata.Event;
import ch.kbw.portalumni.hibernatedata.Firma;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Silvan Baach
 */
@ManagedBean
@SessionScoped
public class CompanyAdministrationController {

    private ArrayList<Firma> companyList;
    private String companyStr;
    private String message;

    private String companyStr2;
    private String message2;

    public CompanyAdministrationController() {
        companyList = new ArrayList();
    }

    public ArrayList<Firma> getAllCompany() {
        //get all Companys
        Session s = HibernateUtil.getInstance().openSession();
        setCompanyList((ArrayList<Firma>) s.createQuery("FROM Firma").list());
        /*message="";
        message2="";
        companyStr="";
        companyStr2="";*/
        return companyList;
    }

    public void addCompany() {
        if (!companyStr.equals("")) {
            Session s = HibernateUtil.getInstance().openSession();
            Firma f = new Firma();
            f.setName(companyStr);
            Transaction t = s.beginTransaction();
            s.save(f);
            t.commit();
            message = "Die Firma " + companyStr + " wurde erfolgreich erstellt!";
            s.close();
        } else {
            message = "Das Feld 'Firmenname' darf nicht leer sein!";
        }
    }

    public void deleteCompany() {
        if (!companyStr2.equals("")) {
            Session s = HibernateUtil.getInstance().openSession();
            for (Firma f : companyList) {
                if (f.getName().equalsIgnoreCase(companyStr2)) {
                    s.beginTransaction();
                    int id = f.getIdFirma();
                    /*Firma comp =(Firma) s.get(Firma.class, id);
                    s.delete(comp);
                    s.getTransaction().commit();*/
                    String hql = "delete from Firma where idFirma= :id";
                    s.createQuery(hql).setString(id, "id").executeUpdate();
                    s.getTransaction().commit();
                    message2 = "Die Firma " + companyStr2 + " wurde erfolgreich gelöscht!";
                }
            }
            s.close();
        } else {
            message2 = "Das Feld 'Firmenname' darf nicht leer sein";
        }

    }

    /**
     * @return the companyList
     */
    public ArrayList<Firma> getCompanyList() {
        return companyList;
    }

    /**
     * @param companyList the companyList to set
     */
    public void setCompanyList(ArrayList<Firma> companyList) {
        this.companyList = companyList;
    }

    /**
     * @return the companyStr
     */
    public String getCompanyStr() {
        return companyStr;
    }

    /**
     * @param companyStr the companyStr to set
     */
    public void setCompanyStr(String companyStr) {
        this.companyStr = companyStr;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the companyStr2
     */
    public String getCompanyStr2() {
        return companyStr2;
    }

    /**
     * @param companyStr2 the companyStr2 to set
     */
    public void setCompanyStr2(String companyStr2) {
        this.companyStr2 = companyStr2;
    }

    /**
     * @return the message2
     */
    public String getMessage2() {
        return message2;
    }

    /**
     * @param message2 the message2 to set
     */
    public void setMessage2(String message2) {
        this.message2 = message2;
    }

}
