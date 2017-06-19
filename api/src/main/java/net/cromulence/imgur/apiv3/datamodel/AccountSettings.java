package net.cromulence.imgur.apiv3.datamodel;

import com.google.gson.annotations.SerializedName;

import net.cromulence.imgur.apiv3.datamodel.constants.AlbumPrivacy;

public class AccountSettings {

    // account_url
    /** The account username */
    @SerializedName("account_url")
    private String accountUrl;

    /** The users email address */
    private String email;

    //high_quality
    /** The users ability to upload higher quality images, there will be less compression */
    @SerializedName("high_quality")
    private boolean highQuality;

    // public_images
    /** Automatically allow all images to be publicly accessible */
    @SerializedName("public_images")
    private boolean publicImages;

    // album_privacy
    /** Set the album privacy to this privacy setting on creation */
    @SerializedName("album_privacy")
    private AlbumPrivacy albumPrivacy;

    // pro_expiration
    /** False if not a pro user, their expiration date if they are. */
    @SerializedName("pro_expiration")
    private boolean proExpiration;

    // accepted_gallery_terms
    /** True if the user has accepted the terms of uploading to the Imgur gallery. */
    @SerializedName("accepted_gallery_terms")
    private boolean acceptedGalleryTerms;

    // active_emails
    /** The email addresses that have been activated to allow uploading */
    @SerializedName("active_emails")
    private String[] activeEmails;

    // messaging_enabled
    /** If the user is accepting incoming messages or not */
    @SerializedName("messaging_enabled")
    private boolean messagingEnabled;

    // blocked_users
    /** An array of users that have been blocked from messaging, the object is blocked_id and blocked_url. */
    @SerializedName("blocked_users")
    private BlockedUser[] blockedUsers;

    // show_mature
    /** True if the user has opted to have mature images displayed in gallery list endpoints. */
    @SerializedName("show_mature")
    private boolean showMature;

    // first_party
    /** True unless the user created their account via a third party service such as Google Plus */
    @SerializedName("first_party")
    private boolean firstParty;

    // newsletter_subscribed
    @SerializedName("newsletter_subscribed")
    private boolean newsletterSubscribed;

    public String getAccountUrl() {
        return accountUrl;
    }

    public void setAccountUrl(String accountUrl) {
        this.accountUrl = accountUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isHighQuality() {
        return highQuality;
    }

    public void setHighQuality(boolean highQuality) {
        this.highQuality = highQuality;
    }

    public boolean isPublicImages() {
        return publicImages;
    }

    public void setPublicImages(boolean publicImages) {
        this.publicImages = publicImages;
    }

    public AlbumPrivacy getAlbumPrivacy() {
        return albumPrivacy;
    }

    public void setAlbumPrivacy(AlbumPrivacy albumPrivacy) {
        this.albumPrivacy = albumPrivacy;
    }

    public boolean isProExpiration() {
        return proExpiration;
    }

    public void setProExpiration(boolean proExpiration) {
        this.proExpiration = proExpiration;
    }

    public boolean isAcceptedGalleryTerms() {
        return acceptedGalleryTerms;
    }

    public void setAcceptedGalleryTerms(boolean acceptedGalleryTerms) {
        this.acceptedGalleryTerms = acceptedGalleryTerms;
    }

    public String[] getActiveEmails() {
        return activeEmails;
    }

    public void setActiveEmails(String[] activeEmails) {
        this.activeEmails = activeEmails;
    }

    public boolean isMessagingEnabled() {
        return messagingEnabled;
    }

    public void setMessagingEnabled(boolean messagingEnabled) {
        this.messagingEnabled = messagingEnabled;
    }

    public BlockedUser[] getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(BlockedUser[] blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public boolean isShowMature() {
        return showMature;
    }

    public void setShowMature(boolean showMature) {
        this.showMature = showMature;
    }

    public boolean isFirstParty() {
        return firstParty;
    }

    public void setFirstParty(boolean firstParty) {
        this.firstParty = firstParty;
    }

    static class BlockedUser {

        @SerializedName("blocked_id")
        String blockedId;

        @SerializedName("blocked_url")
        String blockedUrl;

        public String getBlockedId() {
            return blockedId;
        }

        public void setBlockedId(String blockedId) {
            this.blockedId = blockedId;
        }

        public String getBlockedUrl() {
            return blockedUrl;
        }

        public void setBlockedUrl(String blockedUrl) {
            this.blockedUrl = blockedUrl;
        }
    }

    public boolean isNewsletterSubscribed() {
        return newsletterSubscribed;
    }

    public void setNewsletterSubscribed(boolean newsletterSubscribed) {
        this.newsletterSubscribed = newsletterSubscribed;
    }
}
