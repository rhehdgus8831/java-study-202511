package chap2_8.stream;

public enum DishType {
    MEAT("육류"),
    FISH("어류"),
    OTHER("기타")
    ;

    private String typeName;

    DishType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}

