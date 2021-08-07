package com.revature.p0.screens;

import com.revature.p0.documents.AppUser;
import com.revature.p0.services.UserService;
import com.revature.p0.util.ScreenRouter;
import com.revature.p0.util.UserSession;

public class UpdateScreen {
    private UserService service;
    private ScreenRouter router;
    private UserSession session;

    public UpdateScreen(UserService service, ScreenRouter router, UserSession session){
        this.service = service;
        this.router = router;
        this.session = session;
    }

    public void render(){
        AppUser user = session.getCurrentUser();
        String currentInfo = "First name: "+user.getFirstName()+"\t"
                +"Last name: "+user.getLastName()+"\n"
                +"ID: "+user.getId()+"\t"
                +"Education: "+user.getEdu()+"\n"
                +"Email: "+user.getEmail()+"\n"
                +"Username: "+user.getUsername()+"\t"
                +"Password: "+user.getPassword()+"\n"; //TODO Find way to encrypt

        String menu = "Please select the item you wish to update: \n"+
                "1) First Name\t  2)Last Name\t 3)"
    }
}
