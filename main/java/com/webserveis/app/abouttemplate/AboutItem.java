package com.webserveis.app.abouttemplate;

public class AboutItem {
        private String text;
        private String text2;
        private int image;
        private String uri;

        public AboutItem() {}
        public AboutItem(String text, String text2, int image, String uri) {
            this.text = text;
            this.text2 = text2;
            this.image = image;
            this.uri = uri;
        }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "AboutItem{" +
                "text='" + text + '\'' +
                ", text2='" + text2 + '\'' +
                ", image=" + image +
                ", uri='" + uri + '\'' +
                '}';
    }
}