package be.kdg.processor.transformers;

@Deprecated
public interface MessageTransformer {
    Object transformToObject(String msg);
}
