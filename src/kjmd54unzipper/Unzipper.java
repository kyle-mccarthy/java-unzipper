/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kjmd54unzipper;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.progress.ProgressMonitor;

/**
 *
 * @author kylemccarthy
 */
public class Unzipper {
    private ZipFile zipper;
    private String source;
    private String destination;
    private ProgressMonitor pg;
    
    public Unzipper() {
        this.source = null;
        this.destination = null;
    }
    
    public Unzipper(String source, String destination) {
        this.source = source;
        this.destination = destination;
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
    
    /**
     * Extract the files from the specified ZIP file to the specified folder.
     * This method can throw a net.lingala.zip4j.exception.ZipException Exception
     * indicating the the chosen zip file was invalid.  If the destination does
     * not exist it will be created.
     * 
     * @throws Exception 
     */
    public void extract() throws Exception {
        // set the source
        this.zipper = new ZipFile(this.source);
        
        // make it run in its own thread
        this.zipper.setRunInThread(true);
        
        // set the progress manager and start the extraction
        this.pg = zipper.getProgressMonitor();
        this.zipper.extractAll(this.destination);
    }
    
    /**
     * Get the percentage of the file that has been extracted
     * 
     * @return int 
     */
    public int getPercentage() {
        return this.pg.getPercentDone();
    }
    
    /**
     * Get the progress of the current file and the extraction process
     * 
     * @return 
     */
    public String getStatus() {
        if (this.pg.getCurrentOperation() == ProgressMonitor.OPERATION_EXTRACT) {
            return "Extracting";
        } else {
            return "None";
        }
    }
}
