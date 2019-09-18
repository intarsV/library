package com.accenture.library.service.userSrv;

import com.accenture.library.domain.User;

public interface userSrv {

    Long addUSer(String userName, String password);

    void disableUser(User user);
}
