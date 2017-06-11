package com.mint.fiestapp.services.facebook;

import com.mint.fiestapp.services.IService;

public interface IFacebookService extends IService {
    void getDisplayName(String facebookId);
}
