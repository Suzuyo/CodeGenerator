package pl.suzuyo.common.gui;

public interface OperatedListEvent<T> {
    void performAfterCreate(T element);
    void performAfterRemove(T element);
    void performAfterRemoveSelection();
    void performBeforeSelection(T element);
    void performAfterSelection(T element);
}
