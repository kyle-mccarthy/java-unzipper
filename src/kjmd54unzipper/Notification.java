/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kjmd54unzipper;

/**
 *
 * @author kylemccarthy
 */
@FunctionalInterface
public interface Notification {
    public void handle(double progress, String status, String file);
}
