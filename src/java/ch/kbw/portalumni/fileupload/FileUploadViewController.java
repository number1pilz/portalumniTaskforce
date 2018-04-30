/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.kbw.portalumni.fileupload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

/**
 *
 * @author Silvan Baach
 *
 *
 */
@ManagedBean
@SessionScoped
public class FileUploadViewController {

    private Part filePart;

    public Part getFilePart() {
        return filePart;
    }

    public void setFilePart(Part FilePart) {
        this.filePart = FilePart;
    }

    public void save() {
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, new File("C:\\Users\\Fabian\\Desktop\\PortalumniTaskforce_Repo\\uploads", filePart.getSubmittedFileName()).toPath());
        } catch (IOException e) {
            // Show faces message?
        }
    }

}
