package com.hnimrod.chatviewapp.model;

import java.util.List;

/**
 * see also: https://a3rt.recruit-tech.co.jp/product/talkAPI/
 */
public class DialogueReplyEntity {
    public int status;
    public String message;
    public List<Results> results;

    public class Results {
        public float perplexity;
        public String reply;
    }
}
