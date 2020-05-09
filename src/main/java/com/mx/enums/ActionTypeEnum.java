package com.mx.enums;

public enum ActionTypeEnum {

    SELECT("select"), INSERT("insert"), UPDATE("update"), DELETE("delete");

    private String actionType;

    ActionTypeEnum(String actionType) {
        this.actionType = actionType;
    }

    public String getActionType() {
        return actionType;
    }

    public static ActionTypeEnum[] getAllTypes() {

        return new ActionTypeEnum[]{SELECT, INSERT, UPDATE, DELETE};
    }
}
