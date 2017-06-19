package net.cromulence.imgur.apiv3.endpoints;

import net.cromulence.imgur.apiv3.api.Imgur;
import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;
import net.cromulence.imgur.apiv3.datamodel.Conversation;

public class ConversationsEndpoint extends AbstractEndpoint {

    public ConversationsEndpoint(Imgur imgur) {
        super(imgur);
    }

    @Override
    protected String getEndpointName() {
        return "conversations";
    }

    public Conversation[] getConversations() throws ApiRequestException {
        return getImgur().http.typedGet(getEndpointUrl(), Conversation[].class, true);
    }

    public Conversation getConversation(int conversationId) throws ApiRequestException {
        final String conversationUrl = endpointUrlWithSinglePathParameter(conversationId);

        return getImgur().http.typedGet(conversationUrl, Conversation.class, true);
    }

    public Conversation getConversation(int conversationId, int page, int offset) throws ApiRequestException {
        final String conversationUrl = String.format("%s/%d/%d/%d", getEndpointUrl(), conversationId, page, offset);

        return getImgur().http.typedGet(conversationUrl, Conversation.class, true);
    }
}
