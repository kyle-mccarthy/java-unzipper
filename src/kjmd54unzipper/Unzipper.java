/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kjmd54unzipper;

import java.util.List;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.progress.ProgressMonitor;
import javafx.application.Platform;
import net.lingala.zip4j.exception.ZipException;

/**
 *
 * @author kylemccarthy
 */
public class Unzipper extends Thread {
    private ZipFile zipper;
    private String source;
    private String destination;
    private Notification notification;
    private Status status;
    private String currFile;
    private int numFiles;
    private int numCurrFile;
    
    public Unzipper() {
        this.source = null;
        this.destination = null;
        this.status = Status.NOTSTARTED;
        this.numCurrFile = 0;
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
            this.numFiles = files.size();
            this.onStart();
            for (Object file : files) {
                FileHeader fh  = (FileHeader)(file);
                this.onExtractFile(fh.getFileName());
                try {
                    this.zipper.extractFile(fh, this.destination);
                } catch (ZipException ex) {
                    System.out.println("could not extract " + fh.getFileName());
                }
                this.numCurrFile++;
                try {
                    Thread.sleep(10);
                } catch(InterruptedException ex) {
                    onInterrupted();
                    return;
                }
            }
            this.onFinished();
        }
    }
    
    private void onStart() {
        this.status = Status.RUNNING;
        this.doNotify();
    }
    
    private void onInterrupted() {
        this.status = Status.STOPPED;
        this.doNotify();
    }
    
    private void onFinished() {
        this.status = Status.FINISHED;
        this.currFile = null;
        this.doNotify();
    }
    
    private void onExtractFile(String file) {
        this.currFile = file;
        this.doNotify();
    }
    
    private String getCurrentFile() {
        return this.currFile;
    }
    
    public void setOnNotification(Notification notification) {
        this.notification = notification;
    }
    
    private void doNotify() {
        if (notification != null) {
            Platform.runLater(() -> {
                notification.handle(this.getProgress(), this.getStatus(), this.getCurrentFile());
            });
        }
    }
    
    /**
     * Get the percentage of the file that has been extracted
     * 
     * @return double
     */
    public double getProgress() {
        if (this.numFiles > 0) {
            return this.numCurrFile/(double)this.numFiles;
        }
        return 0.0;
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
