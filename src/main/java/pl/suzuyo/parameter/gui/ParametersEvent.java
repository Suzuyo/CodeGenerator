package pl.suzuyo.parameter.gui;

public interface ParametersEvent {
    void performAfterAdd(String parameter);
    void performAfterRemove(String parameter);
}
