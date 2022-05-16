package ru.otus.cachehw;

public enum Action {
    PUT("put"),
    GET("get"),
    DELETE("delete");

    private String actionType;

    public String getActionType() {
        return actionType;
    }

    Action(String actionType){
        this.actionType = actionType;
    }
}
