package org.askforai.message;

public enum Sender {
    User(0), AI(1);
    
    private final int value;

    Sender(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
