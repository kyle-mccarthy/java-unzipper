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
public interface Notification {
    public void handlePercent(double percent, String file, String status);
}
