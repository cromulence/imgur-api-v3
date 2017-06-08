package net.cromulence.imgur.apiv3.endpoints.params;

import net.cromulence.imgur.apiv3.datamodel.constants.AlbumLayout;
import net.cromulence.imgur.apiv3.datamodel.constants.AlbumPrivacy;

public class AlbumCreationDetailsBuilder implements ParameterObjectBuilder<AlbumCreationDetails> {
    private String[] imageIds = new String[0];
    private String[] deleteHashes = new String[0];
    private String title = null;
    private String description = null;
    private AlbumPrivacy privacy = null;
    private AlbumLayout layout = null;
    private String coverId = null;

    public AlbumCreationDetailsBuilder imageIds(String[] imageIds) {
        this.imageIds = imageIds;
        return this;
    }

    public AlbumCreationDetailsBuilder deleteHashes(String[] deleteHashes) {
        this.deleteHashes = deleteHashes;
        return this;
    }

    public AlbumCreationDetailsBuilder title(String title) {
        this.title = title;
        return this;
    }

    public AlbumCreationDetailsBuilder description(String description) {
        this.description = description;
        return this;
    }

    public AlbumCreationDetailsBuilder privacy(AlbumPrivacy privacy) {
        this.privacy = privacy;
        return this;
    }

    public AlbumCreationDetailsBuilder layout(AlbumLayout layout) {
        this.layout = layout;
        return this;
    }

    public AlbumCreationDetailsBuilder coverId(String coverId) {
        this.coverId = coverId;
        return this;
    }

    @Override
    public AlbumCreationDetails build() {
        AlbumCreationDetails albumDetails = new AlbumCreationDetails();

        albumDetails.imageIds = imageIds;
        albumDetails.deleteHashes = deleteHashes;
        albumDetails.title = title;
        albumDetails.description = description;
        albumDetails.privacy = privacy;
        albumDetails.layout = layout;
        albumDetails.coverId = coverId;

        return albumDetails;
    }
}
