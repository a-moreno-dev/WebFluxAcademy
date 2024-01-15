package com.academy.enroll.configurations.Security;

import org.springframework.stereotype.Component;

@Component
public class TestAuthorize {

    public boolean hasAccess(){
        return true;
    }
}
