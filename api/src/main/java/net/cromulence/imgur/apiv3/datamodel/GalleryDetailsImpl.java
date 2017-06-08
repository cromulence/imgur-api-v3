package net.cromulence.imgur.apiv3.datamodel;


import com.google.gson.annotations.SerializedName;

public class GalleryDetailsImpl implements GalleryDetails {

    @SerializedName("comment_count")
    private int commentCount;

    private int downs;

    @SerializedName("is_album")
    private boolean isAlbum;

    private int score;

    private String topic;

    @SerializedName("topic_id")
    private int topicId;

    private int ups;
    private String vote;
    private int points;

    @SerializedName("account_id")
    private Long accountId;

    @SerializedName("account_url")
    private String accountUrl;

    @Override
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public String getAccountUrl() {
        return accountUrl;
    }

    public void setAccountUrl(String accountUrl) {
        this.accountUrl = accountUrl;
    }

    @Override
    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    @Override
    public int getDowns() {
        return 0;
    }

    public void setDowns(int downs) {
        this.downs = downs;
    }

    @Override
    public boolean isAlbum() {
        return isAlbum;
    }

    public void setAlbum(boolean album) {
        isAlbum = album;
    }

    @Override
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    @Override
    public int getUps() {
        return ups;
    }

    public void setUps(int ups) {
        this.ups = ups;
    }

    @Override
    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }
}
