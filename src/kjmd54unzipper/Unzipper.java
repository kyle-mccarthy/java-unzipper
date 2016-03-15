/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kjmd54unzipper;

import java.util.ArrayList;
import java.util.List;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.progress.ProgressMonitor;

/**
 *
 * @author kylemccarthy
 */
public class Unzipper extends Thread {
    private ZipFile zipper;
    private String source;
    private String destination;
    private ProgressMonitor pg;
    private Notification notification;
    private Status status;
    
    public Unzipper() {
        this.source = null;
        this.destination = null;
        this.status = Status.NOTSTARTED;
    }
    
    public void setSource(String source) {
        this.source = source;
    }
    
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public String getSource() {
        return this.source;
    }
    
    public String getDestination() {
        return this.destination;
    }
    
    public void loadFile() throws Exception {
        // set the source
        this.zipper = new ZipFile(this.source);
        // set the progress manager
        this.pg = zipper.getProgressMonitor();
    }
    
    
    /**
     * Handle the extraction process.  Update the UI with information about the file
     * being extracted, the progress as a percentage, and the status of the application.
     */
    @Override
    public void run() {
        // try to get the list of file headers so we can notify the client of which
        // files are being extracted
        List files = null;
        try {
            files = this.zipper.getFileHeaders();
        } catch (Exception ex) {
            System.out.println("Error unpacking the zip file");
            System.out.println(ex);
        }
        if (files != null) {
            this.status = Status.RUNNING;
            for (Object file : files) {
                FileHeader fh  = (FileHeader)(file);
                System.out.println(fh.getFileName());
            }
            this.status = Status.FINISHED;
        }
    }
    
    private void onInterrupted() {
        status = Status.STOPPED;
    }
    
    private void onFinished() {
        status = Status.FINISHED;
    }
    
    public void setOnNotification(Notification notification) {
        this.notification = notification;
    }
    
    /**
     * Get the percentage of the file that has been extracted
     * 
     * @return int 
     */
    public int getPercentage() {
        if (this.pg != null) {
            return this.pg.getPercentDone();
        }
        return 0;
    }
    
    public String getStatus() {
        if (null != this.status) switch (this.status) {
            case NOTSTARTED:
                return "Not Started";
            case RUNNING:
                return "Running";
            case STOPPED:
                return "Interrupted";
            case FINISHED:
                return "Finished";
            default:
                return "Unkown status";
        }
        return "Unkown status";
    }
}
