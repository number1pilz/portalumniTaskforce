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

    public CompanyAdministrationController() {
        companyList = new ArrayList();
    }

    public ArrayList<Firma> getAllCompany() {
        //get all Companys
        Session s = HibernateUtil.getInstance().openSession();
        setCompanyList((ArrayList<Firma>) s.createQuery("FROM Firma").list());

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

}
