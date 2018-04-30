package ch.kbw.portalumni.event;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ch.kbw.portalumni.hibernate.HibernateUtil;
import ch.kbw.portalumni.hibernatedata.Event;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.Transaction;

@SessionScoped
@ManagedBean
public class EventCreatorViewController implements Serializable {

    private String text;
    private String title;
    private String imgPath1="";
    private String imgPath2="";
    private String imgPath3="";
    private boolean complete = true;
    private String errorMsg1 = "";
    private String errorMsg2 = "";
    private String errorMsg3 = "";

    public void createEvent() {
        errorMsg1 = "";
        errorMsg2 = "";
        errorMsg3 = "";
        //Valdiate
        if (text.length()
                < 20) {
            complete = false;
            setErrorMsg1("Die Textlänge muss mindestens 20 Zeichen betragen!");
        }

        if (imgPath1.equals(
                "") && imgPath2.equals("") && imgPath3.equals("")) {
            complete = false;
            setErrorMsg2("Mindestens ein Feld 'Fotoname' muss ausgefüllt sein!");
        }

        if (getTitle()
                .equals("")) {
            complete = false;
            setErrorMsg3("Das Feld 'Titel' darf nicht leer sein!");
        }

        if (complete
                == true) {
            //Add Event

            Event e = new Event();
            e.setTitel(getTitle());
            e.setText(text);
            e.setImg1(imgPath1);
            e.setImg2(imgPath2);
            e.setImg3(imgPath3);
            Session session = HibernateUtil.getInstance().openSession();
            Transaction t = session.beginTransaction();
            session.save(e);
            t.commit();
            session.close();

            //REDIRECT
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Alumni/faces/events.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(EventCreatorViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the imgPath1
     */
    public String getImgPath1() {
        return imgPath1;
    }

    /**
     * @param imgPath1 the imgPath1 to set
     */
    public void setImgPath1(String imgPath1) {
        this.imgPath1 = imgPath1;
    }

    /**
     * @return the imgPath2
     */
    public String getImgPath2() {
        return imgPath2;
    }

    /**
     * @param imgPath2 the imgPath2 to set
     */
    public void setImgPath2(String imgPath2) {
        this.imgPath2 = imgPath2;
    }

    /**
     * @return the imgPath3
     */
    public String getImgPath3() {
        return imgPath3;
    }

    /**
     * @param imgPath3 the imgPath3 to set
     */
    public void setImgPath3(String imgPath3) {
        this.imgPath3 = imgPath3;
    }

    /**
     * @return the complete
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * @param complete the complete to set
     */
    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    /**
     * @return the errorMsg1
     */
    public String getErrorMsg1() {
        return errorMsg1;
    }

    /**
     * @param errorMsg1 the errorMsg1 to set
     */
    public void setErrorMsg1(String errorMsg1) {
        this.errorMsg1 = errorMsg1;
    }

    /**
     * @return the errorMsg2
     */
    public String getErrorMsg2() {
        return errorMsg2;
    }

    /**
     * @param errorMsg2 the errorMsg2 to set
     */
    public void setErrorMsg2(String errorMsg2) {
        this.errorMsg2 = errorMsg2;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the errorMsg3
     */
    public String getErrorMsg3() {
        return errorMsg3;
    }

    /**
     * @param errorMsg3 the errorMsg3 to set
     */
    public void setErrorMsg3(String errorMsg3) {
        this.errorMsg3 = errorMsg3;
    }

}
