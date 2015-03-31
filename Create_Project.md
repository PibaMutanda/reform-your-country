# Introduction #




# Details #

  1. Make a checkout from the ryc project: http://reform-your-country.googlecode.com/svn/trunk
  1. Create your own Dynamic Web Project
  1. In **BuildPath->Libraries** add libraries (web app libraries) choose ReformYourCountry
  1. In **BuildPath->Source** Link sources from **WORKSPACE\_LOC/ReformYourCountry/src** and rename the folder (like RYC-src)
  1. Apply the changes
  1. Add a folder in your Project with advanced option "link to alternate location" and the adress must be **WORKSPACE\_LOC/ReformYourCountry/WebContent** (i name it RYC-WebContent)
  1. Go in your project properties again and in "Deployment Assembly", add the folders  RYC-src and  RYC-WebContent if they are not present.
    1. The Deploy-Path of RYC-src must be "WEB-INF/classes"
    1. The Deploy-Path of RYC-WebContent must be "/"
    1. You can change them by double-click on the text-zone
  1. Now your project is ready. Just verify the order and export  from your build path. RYC-src must be after your src
  1. If you want to overwrite a file (like home.jsp) Just create the file with the same path but in your WebContent or in your src folder depending of the overwrited file's path.