package io.recommendation.common.bean;

public class ActionType {
    private Integer id;
    private Type type;
    private Integer value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public enum Type{
        watch,
        favor,
        comment,
        click
    }
}
