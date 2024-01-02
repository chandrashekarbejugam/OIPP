package com.codeinfinity.oipp;

// Message.java
public class Message {
    private String text;
    private String senderUID;

   // private String userUid;
    private long timestamp;

    // Constructors, getters, and setters


    public Message() {
    }

    public Message(String text, String senderUID, long timestamp) {
        this.text = text;
        this.senderUID = senderUID;
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSenderUID() {
        return senderUID;
    }

    public void setSenderUID(String senderUID) {
        this.senderUID = senderUID;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

