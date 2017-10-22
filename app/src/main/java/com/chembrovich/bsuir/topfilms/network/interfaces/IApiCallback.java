package com.chembrovich.bsuir.topfilms.network.interfaces;

import retrofit2.Response;

public interface IApiCallback<T> {
    void onResponse(Response<T> response);

    void onFailure();
}
