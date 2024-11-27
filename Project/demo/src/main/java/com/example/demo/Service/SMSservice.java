package com.example.demo.Service;


import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import org.springframework.stereotype.Service;

@Service
public class SMSservice {
    private VonageClient vonageClient;

    public SMSservice(){
        this.vonageClient = VonageClient.builder()
                .apiKey("89945a2f")
                .apiSecret("Nk0LxG6zDCBLR58m")
                .build();
    }

    public String sendSms(String toPhoneNumber, String messageText) {
        TextMessage message = new TextMessage("Vonage APIs", toPhoneNumber, messageText);

        SmsSubmissionResponse response = vonageClient.getSmsClient().submitMessage(message);

        if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
            return "Message sent successfully.";
        } else {
            return "Message failed with error: " + response.getMessages().get(0).getErrorText();
        }
    }

}
