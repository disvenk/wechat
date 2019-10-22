package com.wechat.wechat.picTextEntity;

/**
 * Created by disvenk.dai on 2018-06-15 20:48
 */
public class ArticlesItem {


    private String Title;

     private String Description;

     private String PicUrl;

     private String Url;

      public String getTitle() {
          return Title;
      }

       public void setTitle(String title) {
             Title = title;
         }
         public String getDescription() {
             return Description;
         }

       public void setDescription(String description) {
           Description = description;
       }

      public String getPicUrl() {
            return PicUrl;
      }

         public void setPicUrl(String picUrl) {
             PicUrl = picUrl;
         }

        public String getUrl() {
           return Url;
       }

       public void setUrl(String url) {
            Url = url;
       }
}
