package net.cromulence.imgur.apiv3.datamodel;

public class Topic {

    /**
     * ID of the topic.
     */
    int id;

    /**
     * Topic name
     */
    String name;

    /**
     * Description of the topic
     */
    String description;

    /**
     * CSS class used on website to style the ephemeral topic
     */
    String css;

    /**
     * Whether it is an ephemeral (e.g. current events) topic
     */
    boolean ephemeral;

    /**
     * The top image in this topic
     */
    GalleryEntry topPost;

    /**
     * The current 'hero' image chosen by the Imgur community staff
     */
    Image heroImage;

    /**
     * Whether the topic's heroImage should be used as the overall hero image
     */
    boolean isHero;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public boolean isEphemeral() {
        return ephemeral;
    }

    public void setEphemeral(boolean ephemeral) {
        this.ephemeral = ephemeral;
    }

    public GalleryEntry getTopPost() {
        return topPost;
    }

    public void setTopPost(GalleryEntry topPost) {
        this.topPost = topPost;
    }

    public Image getHeroImage() {
        return heroImage;
    }

    public void setHeroImage(Image heroImage) {
        this.heroImage = heroImage;
    }

    public boolean isHero() {
        return isHero;
    }

    public void setHero(boolean hero) {
        isHero = hero;
    }
}
