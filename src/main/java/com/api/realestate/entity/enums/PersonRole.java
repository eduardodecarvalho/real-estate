package com.api.realestate.entity.enums;

public enum PersonRole {

    ADMIN(0, "ROLE_ADMIN"),
    CLIENT(1, "ROLE_CLIENT"),
    MASTER(99, "ROLE_MASTER");

    private Integer cod;
    private String description;

    PersonRole(Integer cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public Integer getCod() {
        return cod;
    }

    public String getDescription() {
        return description;
    }

    public static PersonRole toEnum(Integer cod) {
        for (PersonRole pr : PersonRole.values()) {
            if (pr.getCod().equals(cod)) {
                return pr;
            }
        }
        return null;
    }
}
