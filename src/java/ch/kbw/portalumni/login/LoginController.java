/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.kbw.portalumni.login;

import ch.kbw.portalumni.event.EventCreatorViewController;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Fabian Ulrich
 */
@Named
@SessionScoped
public class LoginController implements Serializable {

    private String email;
    private String password;
    private String errorMessage;

    @Inject
    private LoginSession loginSession;

    public LoginController() {
        email = "";
        password = "";
        errorMessage = "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void logout(){
        loginSession.user = null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    
    public String checkUser() {
        String returnVal = loginSession.validate(email, password);
        
        switch(returnVal){
            case "success":
                this.errorMessage = "";
                return "index.xhtml?faces-redirect=true";
            case "notEnabled":
                this.errorMessage = "Fehler: Dieser Benutzer ist nicht freigeschalten.";
                return "login.xhtml?faces-redirect=true";
            case "wrongPassword":
                this.errorMessage = "Ungültiges Passwort.";
                return "login.xhtml?faces-redirect=true";
            case "unknownEmail":
                this.errorMessage = "Ein Benutzer mit dieser E-Mail-Adresse existiert nicht.";
                return "login.xhtml?faces-redirect=true";
            default:
                return "index.xhtml?faces-redirect=true";
        }
    }

}
