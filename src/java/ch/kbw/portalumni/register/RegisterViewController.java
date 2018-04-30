/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.kbw.portalumni.register;

import ch.kbw.portalumni.event.EventCreatorViewController;
import ch.kbw.portalumni.hibernate.HibernateUtil;
import ch.kbw.portalumni.hibernatedata.Benutzer;
import ch.kbw.portalumni.hibernatedata.Firma;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Silvan Baach
 */
@SessionScoped
@ManagedBean
public class RegisterViewController {

    private String vorname = "";
    private String nachname = "";
    private String email = "";
    private String passwort1 = "";
    private String passwort2 = "";
    private String error = "";
    private String firma = "";
    private boolean button = false;
    private Map<String, String> companyMap;
    private String companyString;
    private List<Firma> comp;

    @PostConstruct
    public void init() {
        companyMap = new LinkedHashMap<String, String>();
        Session session = HibernateUtil.getInstance().openSession();
        comp = session.createQuery("FROM Firma").list();

        for (Firma f : comp) {
            companyMap.put(f.getName(), f.getName());
        }
    }

    public void show() {
        System.out.println("akljfhajksdfhasjhlsdhjfsa" + companyString);
    }

    public void anfrageSenden() throws InterruptedException {
        setButton(false);
        boolean check = false;
        Session session = HibernateUtil.getInstance().openSession();
        if (vorname.isEmpty() || nachname.isEmpty() || email.isEmpty() || passwort1.isEmpty() || passwort2.isEmpty() || companyString.equals("")) {
            setError("Füllen Sie das Formular ganz aus.");
        } else {
            if (checkPasswort() == true && checkEmail() == true) {

                Benutzer b = new Benutzer();
                b.setAdmin(false);
                b.setEmail(email);
                b.setEnabled(false);

                for (Firma f : comp) {
                    if (f.getName().equalsIgnoreCase(companyString)) {
                        b.setFirma(f);
                        check = true;
                    }
                }
                if (check == false) {
                    b.setFirma(null);
                }
                b.setNachname(nachname);
                b.setNewsletter(false); //!!
                b.setPasswort(passwort1);
                b.setVorname(vorname);

                Transaction t = session.beginTransaction();;
                session.save(b);
                t.commit();

                //REDIRECT
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("confirmation.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(EventCreatorViewController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        session.close();

    }

    public boolean checkPasswort() {

        if (passwort1.equals(passwort2)) {
            if (passwort1.length() < 8) {

                setError("Die Passwortanforderungen wurden nicht erfüllt.");
                return false;
            }

            String grossbuchstaben = "[A-Z]";
            Pattern uppCasePattern = Pattern.compile(grossbuchstaben);
            Matcher m = uppCasePattern.matcher(passwort1);
            if (!m.find()) {

                setError("Die Passwortanforderungen wurden nicht erfüllt.");
                return false;
            }

            String kleinbuchstaben = "[a-z]";
            Pattern lowerCasePattern = Pattern.compile(kleinbuchstaben);
            Matcher m2 = lowerCasePattern.matcher(passwort1);
            if (!m2.find()) {

                setError("Die Passwortanforderungen wurden nicht erfüllt.");
                return false;
            }

            String zahlen = "[0-9]";
            Pattern numberCasePattern = Pattern.compile(zahlen);
            Matcher m3 = numberCasePattern.matcher(passwort1);
            if (!m3.find()) {

                setError("Die Passwortanforderungen wurden nicht erfüllt.");
                return false;
            }

            String symbole = "[+\"*ç%&/()=?^$£<>¦@#°§¬|¢´~]";
            Pattern symbolCasePattern = Pattern.compile(symbole);
            Matcher m4 = symbolCasePattern.matcher(passwort1);
            if (!m4.find()) {

                setError("Die Passwortanforderungen wurden nicht erfüllt.");
                return false;
            }
        } else {
            setError("Die Passwörter stimmen nicht überein.");
            return false;
        }

        return true;
    }

    public boolean checkEmail() {
        ArrayList<String> mailList = new ArrayList();
        boolean result = true;

        Session session = HibernateUtil.getInstance().openSession();
        List<Benutzer> members = session.createQuery("FROM Benutzer").list();

        try {
            InternetAddress emailAdr = new InternetAddress(email);
            emailAdr.validate();
        } catch (AddressException ex) {
            setError("Die angegebene E-Mail Adresse ist ungültig.");
            result = false;
        }

        for (Benutzer b : members) {
            if (b.getEmail().equalsIgnoreCase(email)) {
                setError("Es besteht schon ein Account mit dieser Mailadresse.");
                result = false;
            }
        }

        return result;
    }

    public void resetForm() {
        vorname = "";
        nachname = "";
        email = "";
        passwort1 = "";
        passwort2 = "";
        setError("");
    }

    /**
     * @return the vorname
     */
    public String getVorname() {
        return vorname;
    }

    /**
     * @param vorname the vorname to set
     */
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    /**
     * @return the nachname
     */
    public String getNachname() {
        return nachname;
    }

    /**
     * @param nachname the nachname to set
     */
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the passwort1
     */
    public String getPasswort1() {
        return passwort1;
    }

    /**
     * @param passwort1 the passwort1 to set
     */
    public void setPasswort1(String passwort1) {
        this.passwort1 = passwort1;
    }

    /**
     * @return the passwort2
     */
    public String getPasswort2() {
        return passwort2;
    }

    /**
     * @param passwort2 the passwort2 to set
     */
    public void setPasswort2(String passwort2) {
        this.passwort2 = passwort2;
    }

    /**
     * @return the button
     */
    public boolean isButton() {
        return button;
    }

    /**
     * @param button the button to set
     */
    public void setButton(boolean button) {
        this.button = button;
    }

    /**
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * @return the firma
     */
    public String getFirma() {
        return firma;
    }

    /**
     * @param firma the firma to set
     */
    public void setFirma(String firma) {
        this.firma = firma;
    }

    /**
     * @return the companyMap
     */
    public Map<String, String> getCompanyMap() {
        return companyMap;
    }

    /**
     * @param companyMap the companyMap to set
     */
    public void setCompanyMap(Map<String, String> companyMap) {
        this.companyMap = companyMap;
    }

    /**
     * @return the companyString
     */
    public String getCompanyString() {
        return companyString;
    }

    /**
     * @param companyString the companyString to set
     */
    public void setCompanyString(String companyString) {
        this.companyString = companyString;
    }

}
