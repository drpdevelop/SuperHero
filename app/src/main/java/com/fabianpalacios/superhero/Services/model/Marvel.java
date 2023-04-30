package com.fabianpalacios.superhero.Services.model;

public class Marvel {
        private String name, url;
        private int code;

        public String getName() {
            return name;
        }

        public int getCode() {
            return code;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getNumber() {
            String [] camposUrl = url.split("/");
            return Integer.parseInt(camposUrl[camposUrl.length-1]);
        }

        public void setNumber(int number) {
            this.code = code;
        }

}
