package org.example.view;

public enum Difficulty {
    EASY(40),    // Przykładowa liczba pól do usunięcia
    MEDIUM(50),
    HARD(60);

    private final int fieldsToRemove;

    Difficulty(int fieldsToRemove) {
        this.fieldsToRemove = fieldsToRemove;
    }

    public int getFieldsToRemove() {
        return fieldsToRemove;
    }
}