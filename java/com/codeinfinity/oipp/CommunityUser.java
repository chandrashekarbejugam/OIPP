package com.codeinfinity.oipp;

public class CommunityUser {

    String name, profilePic;

    public CommunityUser(String name, String profilePic) {
        this.name = name;
        this.profilePic = profilePic;
    }

    public CommunityUser() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
