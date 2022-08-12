package org.javaproteam27.socialnetwork.service;

import org.javaproteam27.socialnetwork.model.dto.request.LoginRq;
import org.javaproteam27.socialnetwork.model.dto.response.LoginRs;
import org.javaproteam27.socialnetwork.model.dto.response.LogoutRs;

public interface LoginService {
    LoginRs login(LoginRq loginRq);
    LogoutRs logout();
}
