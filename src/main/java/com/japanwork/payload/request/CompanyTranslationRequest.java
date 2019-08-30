package com.japanwork.payload.request;

import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CompanyTranslationRequest {
    @NotNull(message = "language_required")
    private UUID languageId;

    @NotBlank(message = "name_company_required")
    @Size(max = 128)
    private String name;

    @NotBlank(message = "address_required")
    @Size(max = 256)
    private String address;

    private String introduction;

    public UUID getLanguageId() {
        return languageId;
    }

    public void setLanguageId(UUID languageId) {
        this.languageId = languageId;
    }

    public String getName() {
		return name;
	}

    public void setName(String name) {
		this.name = name;
	}

    public String getAddress() {
		return address;
	}

    public void setAddress(String address) {
		this.address = address;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
}
