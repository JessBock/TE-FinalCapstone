package com.techelevator.model;

public class Collection {

    private long collectionId;
    private String collectionName;
    private long count;
    private boolean share = false;
    private String collectionComments = "";
    private long userId;

    public long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(long collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public boolean isShare() {
        return share;
    }

    public void setShare(boolean share) {
        this.share = share;
    }

    public String getCollectionComments() {
        return collectionComments;
    }

    public void setCollectionComments(String collectionComments) {
        this.collectionComments = collectionComments;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
