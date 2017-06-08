package net.cromulence.imgur.apiv3.datamodel.meme;


import com.google.gson.annotations.SerializedName;

public class MemeMetadata {
    @SerializedName("meme_name")
    private String memeName;//	The name of the meme used.
    @SerializedName("top_text")
    private String topText;//	The top text of the meme.
    @SerializedName("bottom_text")
    private String bottomText;//The bottom text of the meme.
    @SerializedName("bg_image")
    private String backgroundImage;//g 	The image id of the background image of the meme.

    public String getMemeName() {
        return memeName;
    }

    public void setMemeName(String memeName) {
        this.memeName = memeName;
    }

    public String getTopText() {
        return topText;
    }

    public void setTopText(String topText) {
        this.topText = topText;
    }

    public String getBottomText() {
        return bottomText;
    }

    public void setBottomText(String bottomText) {
        this.bottomText = bottomText;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
}
