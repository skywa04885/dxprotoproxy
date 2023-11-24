package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.createApi;

public interface ICreateApiCallbacks {
    /**
     * Gets called when the create instance window is about to submit and needs to validate the values.
     * @param name the name.
     * @param httpVersion the http version.
     * @return null if the values are valid, otherwise an error message.
     */
    String validate(String name, String httpVersion);

    /**
     * Gets called when the create instance windows submits.
     * @param name the name.
     * @param httpVersion the http version.
     */
    void submit(String name, String httpVersion);
}
