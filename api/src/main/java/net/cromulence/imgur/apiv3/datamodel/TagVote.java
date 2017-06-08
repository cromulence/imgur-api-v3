package net.cromulence.imgur.apiv3.datamodel;

public class TagVote {

    private int ups;// 	integer 	Number of upvotes.
    private int downs;// 	integer 	Number of downvotes.
    private String name;// 	string 	Name of the tag.
    private String author;// 	string 	Author of the tag.

    public int getUps() {
        return ups;
    }

    public void setUps(int ups) {
        this.ups = ups;
    }

    public int getDowns() {
        return downs;
    }

    public void setDowns(int downs) {
        this.downs = downs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
