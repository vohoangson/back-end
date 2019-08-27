package com.japanwork.payload.response;

import java.util.UUID;

public class CompanyTranslationResponse {
    private UUID id;
    private String name;
    private String introduction;
    private String address;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CompanyTranslationResponse(UUID id, String name, String introduction, String address) {
        this.id = id;
        this.name = name;
        this.introduction = introduction;
        this.address = address;
    }
}
