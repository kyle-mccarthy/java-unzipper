/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kjmd54unzipper;

import java.util.List;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.FileHeader;
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
    
    public void setOnNotification(Notification notification) {
        this.notification = notification;
    }
    
    public String getSource() {
        return this.source;
    }
    
    public String getDestination() {
        return this.destination;
    }
    
    /**
     * Initialize the ZipFile that we want to decompress 
     * @throws Exception 
     */
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
        // make sure that there wasn't and error extracting the file headers
        if (files != null) {
            this.numFiles = files.size();
            this.onStart();
            // interate through the list of files and extract them one by one and notify
            // the UI of the progress of the unzipping
            for (Object file : files) {
                FileHeader fh  = (FileHeader)(file);
                this.onExtractFile(fh.getFileName());
                try {
                    this.zipper.extractFile(fh, this.destination);
                } catch (ZipException ex) {
                    System.out.println("could not extract " + fh.getFileName());
                }
                this.numCurrFile++;
                // sleep or the thread is executed too quickly?
                try {
                    Thread.sleep(10);
                } catch(InterruptedException ex) {
                    this.onInterrupted();
                    return;
                }
            }
            this.onFinished();
        }
    }
    
    /**
     * Set the status to started and notify the UI
     */
    private void onStart() {
        this.status = Status.RUNNING;
        this.doNotify();
    }
    
    /**
     * Set the status to interrupted and notify the UI
     */
    private void onInterrupted() {
        this.status = Status.STOPPED;
        this.doNotify();
    }
    
    /**
     * Set the status to finished and notify the UI
     */
    private void onFinished() {
        this.status = Status.FINISHED;
        this.currFile = null;
        this.doNotify();
    }
    
    /**
     * Notify the UI of the file that is currently being extracted from the archive
     * 
     * @param file 
     */
    private void onExtractFile(String file) {
        this.currFile = file;
        this.doNotify();
    }
    
    /**
     * Get the filename of the current file being extracted
     * 
     * @return 
     */
    private String getCurrentFile() {
        return this.currFile;
    }
    
    /**
     * Notify the UI of the status of the unzipping, the progress, and the file that is
     * currently being unzipped.
     */
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
    
    /**
     * Convert the status enums to Strings for the UI
     * 
     * @return 
     */
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
