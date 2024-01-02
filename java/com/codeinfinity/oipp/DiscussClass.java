package com.codeinfinity.oipp;

public class DiscussClass {
    String userUid, contentText, timeStamp, postID;

    public DiscussClass() {
    }

    public DiscussClass(String userUid, String contentText, String timeStamp, String postID) {
        this.userUid = userUid;
        this.postID = postID;
        this.contentText = contentText;
        this.timeStamp = timeStamp;
    }

    public String getUserUid() {
        return userUid;
    }


    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
