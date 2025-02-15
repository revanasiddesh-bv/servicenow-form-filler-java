package com.servicenow.config;

import java.util.List;

public class NotificationConfig {
    private EmailConfig email;
    private SlackConfig slack;

    public EmailConfig getEmail() { return email; }
    public void setEmail(EmailConfig email) { this.email = email; }
    
    public SlackConfig getSlack() { return slack; }
    public void setSlack(SlackConfig slack) { this.slack = slack; }

    public static class EmailConfig {
        private boolean enabled;
        private List<String> recipients;
        private boolean onError;
        private boolean onSuccess;

        public boolean isEnabled() { return enabled; }
        public void setEnabled(boolean enabled) { this.enabled = enabled; }
        
        public List<String> getRecipients() { return recipients; }
        public void setRecipients(List<String> recipients) { this.recipients = recipients; }
        
        public boolean isOnError() { return onError; }
        public void setOnError(boolean onError) { this.onError = onError; }
        
        public boolean isOnSuccess() { return onSuccess; }
        public void setOnSuccess(boolean onSuccess) { this.onSuccess = onSuccess; }
    }

    public static class SlackConfig {
        private boolean enabled;
        private String webhook;
        private String channel;

        public boolean isEnabled() { return enabled; }
        public void setEnabled(boolean enabled) { this.enabled = enabled; }
        
        public String getWebhook() { return webhook; }
        public void setWebhook(String webhook) { this.webhook = webhook; }
        
        public String getChannel() { return channel; }
        public void setChannel(String channel) { this.channel = channel; }
    }
}
