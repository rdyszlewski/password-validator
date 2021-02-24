package com.farfocle.password_validator.message_creator;

import com.farfocle.password_validator.InfoType;
import com.farfocle.password_validator.PasswordRuleResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class FileValidationMessageCreator implements ValidationMessageCreator {

    private final Properties properties;

    public FileValidationMessageCreator(File file) throws IOException {
        properties = new Properties();
        // TODO: spróbować tutaj użyć try with resources
        FileInputStream stream = new FileInputStream(file);
        properties.load(stream);
        stream.close();
    }

    @Override
    public String getMessage(PasswordRuleResult result) {
        String message = (String) properties.get(result.getErrorType().name());
        assert result.getErrorInfo() != null;
        for (Map.Entry<InfoType, String> entry : result.getErrorInfo().entrySet()) {
            message = message.replaceAll(entry.getKey().name(), entry.getValue());
        }
        return message;
    }

    @Override
    public boolean validate(List<MessageValidationRule> rules) throws MessageCreatorValidationException {
        for (MessageValidationRule rule : rules) {
            String message = properties.getProperty(rule.getError().name());
            for (InfoType infoType : rule.getAvailableInfo()) {
                message = message.replaceAll(infoType.name(), "");
            }
            for (InfoType infoType : InfoType.values()) {
                if (message.contains(infoType.name())) {
                    throw new MessageCreatorValidationException(rule.getError(), infoType);
                }
            }
        }
        return true;
    }
}
