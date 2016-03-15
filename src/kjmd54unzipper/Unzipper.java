/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kjmd54unzipper;

import net.lingala.zip4j.core.ZipFile;

/**
 *
 * @author kylemccarthy
 */
public class Unzipper {
    private ZipFile zipper;
    private String source;
    private String destination;
    
    public Unzipper(String source, String destination) {
        this.source = source;
        this.destination = destination;
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
        this.zipper = new ZipFile(this.source);
        this.zipper.extractAll(this.destination);
    }
}
