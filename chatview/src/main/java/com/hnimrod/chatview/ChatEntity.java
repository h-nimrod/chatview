package com.hnimrod.chatview;

import androidx.annotation.NonNull;

public class ChatEntity {
    public static enum TYPE {
        RIGHT,
        LEFT,
        HEADER
    }
    
    public String utt;
    public String image;
    public String name;
    public long unixtime;
    public boolean readdone;
    public TYPE type;


    public void setUtt(String utt) {
        this.utt = utt;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setUnixtime(long unixtime) {
        this.unixtime = unixtime;
    }
    public void setReadDone() {
        this.readdone = true;
    }

    public void setRight() {
        this.type = TYPE.RIGHT;
    }

    public void setLeft() {
        this.type = TYPE.LEFT;
    }



    public static class Builder {
        private String utt = "";
        private String image = "";
        private String name = "";
        private long unixtime = Long.MIN_VALUE;
        private boolean readdone = false;
        private TYPE type = TYPE.RIGHT;
        
        public ChatEntity create() {
            ChatEntity item = new ChatEntity();
            item.utt = this.utt;
            item.image = this.image;
            item.name = this.name;
            item.unixtime = (this.unixtime == Long.MIN_VALUE) ? System.currentTimeMillis() / 1000L : this.unixtime;
            item.readdone = this.readdone;
            item.type = this.type;
            return item;
        }

        public Builder setMessage(@NonNull String message) {
            this.utt = message;
            return this;
        }

        public Builder setImage(@NonNull String image) {
            this.image = image;
            return this;
        }
        public Builder setName(@NonNull String name) {
            this.name = name;
            return this;
        }
        public Builder setUnixtime(long unixtime) {
            this.unixtime = unixtime;
            return this;
        }
        public Builder setReadDone(boolean readdone) {
            this.readdone = readdone;
            return this;
        }
        public Builder setType(@NonNull TYPE type) {
            this.type = type;
            return this;
        }

        public Builder setTypeRight(){
            this.type = TYPE.RIGHT;
            return this;
        }

        public Builder setTypeLeft(){
            this.type = TYPE.LEFT;
            return this;
        }

        public Builder setTypeHeader(){
            this.type = TYPE.HEADER;
            return this;
        }
    }
    
    

}
