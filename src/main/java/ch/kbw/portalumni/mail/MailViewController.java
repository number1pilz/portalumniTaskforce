/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.kbw.portalumni.mail;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Silvan Baach
 */
@ManagedBean
@SessionScoped
public class MailViewController {
    private String to;
    private static final String FROM ="info@portalumni.ch";
    
    
}
