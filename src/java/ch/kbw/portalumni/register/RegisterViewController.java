/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.kbw.portalumni.register;

import ch.kbw.portalumni.event.EventCreatorViewController;
import ch.kbw.portalumni.hibernate.HibernateUtil;
import ch.kbw.portalumni.hibernatedata.Benutzer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    public void anfrageSenden() throws InterruptedException {
        setButton(false);
        if (vorname.isEmpty() || nachname.isEmpty() || email.isEmpty() || passwort1.isEmpty() || passwort2.isEmpty()) {
            setError("Füllen Sie das Formular ganz aus.");
        } else {
            if (checkPasswort() == true && checkEmail() == true) {

                Benutzer b = new Benutzer();
                b.setAdmin(false);
                b.setEmail(email);
                b.setEnabled(false);
                b.setFirma(null);
                b.setNachname(nachname);
                b.setNewsletter(false); //!!
                b.setPasswort(passwort1);
                b.setVorname(vorname);
                Session session = HibernateUtil.getInstance().openSession();
                Transaction t = session.beginTransaction();
                session.save(b);
                t.commit();
                session.close();

                Thread.sleep(5000);
            }
        }
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
        boolean result = true;
        try {
            InternetAddress emailAdr = new InternetAddress(email);
            emailAdr.validate();
        } catch (AddressException ex) {
            setError("Die angegebene E-Mail Adresse ist ungültig.");
            result = false;
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


}
