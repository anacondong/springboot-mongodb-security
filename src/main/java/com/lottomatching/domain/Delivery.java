package com.lottomatching.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "delivery")
public class Delivery {

    @Id
    private ObjectId id;

    @DBRef
    private User user;
    private String round;
    private int lottoList;
    private int matched;
    private int notMatched;
    private int received;
    private String status;
    private boolean enabled;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    public int getReceived() {
        return received;
    }

    public void setReceived(int received) {
        this.received = received;
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
                "id=" + id +
                ", user=" + user +
                ", round='" + round + '\'' +
                ", lottoList=" + lottoList +
                ", matched=" + matched +
                ", notMatched=" + notMatched +
                ", received=" + received +
                ", status='" + status + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
