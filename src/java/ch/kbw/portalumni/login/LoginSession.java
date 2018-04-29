/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.kbw.portalumni.login;

import ch.kbw.portalumni.hibernate.HibernateUtil;
import ch.kbw.portalumni.hibernatedata.*;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.hibernate.Session;

/**
 *
 * @author Fabian Ulrich
 */
@Named
@SessionScoped
public class LoginSession implements Serializable {

    Benutzer user;

    public Benutzer getUser() {
        return user;
    }

    public void setUser(Benutzer user) {
        this.user = user;
    }
    
    public String validate(String email, String clearTextPassword) { //TODO: HASHEN
        Session session = HibernateUtil.getInstance().openSession();
        List<Benutzer> users = session.createQuery("FROM Benutzer").list();

        for (Benutzer user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                if (user.getPasswort().equals(clearTextPassword)) {
                    if (user.isEnabled()) {
                        this.user = user;
                        return "success";
                    } else {
                        return "notEnabled";
                    }
                } else {
                    return "wrongPassword";
                }
            }
        }
        return "unknownEmail";
    }

    public String clearTextToHash(String text) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            byte[] digest = md.digest();
            String hashCode = "";
            for (byte d : digest) {
                String nextByte = Integer.toHexString(d & 0xFF).toUpperCase();
                if (nextByte.length() < 2) {
                    nextByte = "0" + nextByte;
                }
                hashCode += nextByte;
            }
            System.out.println(hashCode);
            return hashCode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
