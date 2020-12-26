package com.heeexy.example.util.constants;

public enum DeleteStateEnum {
    NORMAL("1","正常"),
    DELETE("2","删除")

    ;
    private String code;
    private String name;

    DeleteStateEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
