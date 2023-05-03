/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Services;

import Entity.User;
 
public class UserSession 
{    
  public static String userEmail;
  private final ServiceUser ServiceUser = new ServiceUser();

    public String getUserEmail() {return userEmail;}

    public void setUserEmail(String userEmail)
    {UserSession.userEmail = userEmail;}
    
    public User getUser()
    {return ServiceUser.GetUserByMailSession(userEmail);}
}

