package com.fest.project;

import android.content.pm.PackageInfo;

public class App
{

   private String title;
   private String packageName;
   private String versionName;
   private int versionCode;
   private String description;
   private long  size;
   PackageInfo pck;
   String[] reqPermission;

   
   public PackageInfo getPackageInfo() 
   {
	      return pck;
   }
   
   public void set_PackageInfo(PackageInfo apck) 
   {
	   pck = apck;
   }
   
   public String[] getPermissionInfo() 
   {
	      return reqPermission;
   }
   
   public void set_Permission_Info(String[] areqPermission) 
   {
	   reqPermission = areqPermission;
   }
   
   
   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getPackageName() {
      return packageName;
   }

   public void setPackageName(String packageName) {
      this.packageName = packageName;
   }

   public String getVersionName() {
      return versionName;
   }

   public void setVersionName(String versionName) {
      this.versionName = versionName;
   }

   public int getVersionCode() {
      return versionCode;
   }
   
   public long getSize() {
	      return size;
   }
   
   public void setSize(long asize) {
	      size = asize ;
   }
   
   public void setVersionCode(int versionCode) {
      this.versionCode = versionCode;
   }
   
   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

}
