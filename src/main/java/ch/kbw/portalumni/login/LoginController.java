package ch.kbw.portalumni.login;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author Fabian Ulrich
 */
@RequestScoped
@ManagedBean
public class LoginController implements Serializable {

    private String email;
    private String password;
    private String errorMessage;
    private String loginName;
    
    
    LoginSession loginSession = LoginSession.getInstance();

    public LoginController() {
        email = "";
        password = "";
        errorMessage = "";
        if(loginSession.getUser() == null){
            loginName = "None";
        }else{
            loginName = loginSession.getUser().getEmail();
        }
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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
        loginSession.setUser(null);
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
