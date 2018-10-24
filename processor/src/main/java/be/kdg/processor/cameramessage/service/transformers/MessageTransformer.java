package be.kdg.processor.cameramessage.service.transformers;

import java.io.IOException;

public interface MessageTransformer {

    Object transformMessage(String msg) throws IOException;

}
