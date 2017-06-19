package net.cromulence.imgur.apiv3.endpoints.params;

import com.google.common.base.Strings;

import net.cromulence.imgur.apiv3.Utils;
import net.cromulence.imgur.apiv3.datamodel.constants.AlbumLayout;
import net.cromulence.imgur.apiv3.datamodel.constants.AlbumPrivacy;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class AlbumCreationDetails implements ParameterObject<AlbumCreationDetailsBuilder> {

    String[] imageIds = new String[0];
    String[] deleteHashes = new String[0];
    String title = null;
    String description = null;
    AlbumPrivacy privacy = null;
    AlbumLayout layout = null;
    String coverId = null;

    AlbumCreationDetails() { }

    public List<NameValuePair> getAsParams() {
        List<NameValuePair> params = new ArrayList<>();

        if (imageIds != null && imageIds.length > 0) {
            params.add(new BasicNameValuePair("ids", Utils.asCommaSeparatedList(imageIds)));
        }

        if (deleteHashes != null && deleteHashes.length > 0) {
            params.add(new BasicNameValuePair("deletehashes", Utils.asCommaSeparatedList(deleteHashes)));
        }

        if (!Strings.isNullOrEmpty(title)) {
            params.add(new BasicNameValuePair("title", title));
        }

        if (!Strings.isNullOrEmpty(description)) {
            params.add(new BasicNameValuePair("description", description));
        }

        if (privacy != null) {
            params.add(new BasicNameValuePair("privacy", privacy.toString()));
        }

        if (layout != null) {
            params.add(new BasicNameValuePair("layout", layout.toString()));
        }

        if (!Strings.isNullOrEmpty(coverId)) {
            params.add(new BasicNameValuePair("cover", coverId));
        }

        return params;
    }
}
