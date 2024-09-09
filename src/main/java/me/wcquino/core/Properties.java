package me.wcquino.core;

import org.aeonbits.owner.Config;

@Config.Sources("file:src/main/resources/application.properties")
public interface Properties extends Config {
    @Key("base.uri")
    String baseURI();

    @Key("email.login")
    String email();

    @Key("password.login")
    String password();
}
