package net.cromulence.imgur.apiv3.endpoints;

import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;

public class Paginated<T> {

    private final PageLoader<T> loader;

    private int page = 0;

    Paginated(PageLoader<T> loader) {
        this(loader, 0);
    }

    Paginated(PageLoader<T> loader, int intialPage) {
        this.loader = loader;
        this.page = intialPage;
    }

    public T next() throws ApiRequestException {
        return loader.get(page++);
    }

    public int getPage() {
        return page;
    }

    @FunctionalInterface
    public interface PageLoader<T> {
        T get(int page) throws ApiRequestException;
    }
}
