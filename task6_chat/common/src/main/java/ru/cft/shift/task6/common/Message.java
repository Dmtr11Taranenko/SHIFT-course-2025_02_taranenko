package ru.cft.shift.task6.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * @author Dmitrii Taranenko
 */
@Data
public class Message {

    private MessageType type;
    private String userName;
    private String sendTime;
    private String text;

    @JsonCreator
    public Message(@JsonProperty("userName") String userName,
                   @JsonProperty("text") String text,
                   @JsonProperty("sendTime") String sendTime,
                   @JsonProperty("type") MessageType type) {
        this.userName = userName;
        this.text = text;
        this.sendTime = sendTime != null
                ? sendTime
                : LocalDateTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
        this.type = type;
    }

    public Message(String userName, String text, MessageType type) {
        this(userName, text, LocalDateTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)), type);
    }
}
