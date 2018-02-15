package com.zuZuz.XusuGateBot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class XusuMessage implements Serializable {
    @JsonProperty("uid")
    private String uid;
    @JsonProperty("bot")
    private String bot;
    @JsonProperty("text")
    private String text;
    @JsonProperty("ok")
    private Boolean ok;

    public XusuMessage() {
        super();
    }

    public XusuMessage(String uid, String bot, String text) {
        this.uid = uid;
        this.bot = bot;
        this.text = text;
    }

    public String getUid() {
        return uid;
    }

    public String getBot() {
        return bot;
    }

    public String getText() {
        return text;
    }

    public Boolean isOk() {
        return ok;
    }

    @Override
    public String toString() {
        return "XusuMessage{" +
                "uid=" + uid +
                ", bot=" + bot +
                ", text=" + text +
                ", ok=" + ok +
                '}';
    }
}
