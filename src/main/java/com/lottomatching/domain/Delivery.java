package com.lottomatching.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "delivery")
public class Delivery {

    @Id
    private ObjectId _id;

    @DBRef
    private User user;
    private String round;
    private int lottoList;
    private int matched;
    private int notMatched;
    private String status;
    private boolean enabled;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public int getLottoList() {
        return lottoList;
    }

    public void setLottoList(int lottoList) {
        this.lottoList = lottoList;
    }

    public int getMatched() {
        return matched;
    }

    public void setMatched(int matched) {
        this.matched = matched;
    }

    public int getNotMatched() {
        return notMatched;
    }

    public void setNotMatched(int notMatched) {
        this.notMatched = notMatched;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "_id=" + _id +
                ", user=" + user +
                ", round='" + round + '\'' +
                ", lottoList=" + lottoList +
                ", matched=" + matched +
                ", notMatched=" + notMatched +
                ", status='" + status + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
