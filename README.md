HypPlayService
=============================
A Google Play Services native extension for Haxe NME
-----------------------------

This NME native extension allows you to integrate the Google Play Services into your NME application.

For now it's Android only.
iOS will be coming soom.

[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

Installation
------------

Android
-------
Add the following metadata in to your application inside the <application/> node.

```xml
<meta-data android:name="com.google.android.gms.games.APP_ID" android:value="@string/app_id" />
````

After your first compilation you need to edit the "ids.xml" file ( [YOUR_BIN_FOLDER]/android/bin/res/values/ids.xml)

```xml
<string name="app_id">PUT YOUR PLAY SERVICES ID HERE</string>
````
Recompiling
-----------
For recompiling the native extensions just use the sh files contained in the project folder

Usage
-----
TBD

Quick reference
---------------
TBD

Made at [Hyperfiction](http://hyperfiction.fr)
--------------------
Developed by :
- [Johann Martinache](https://github.com/shoebox) [@shoe_box](https://twitter.com/shoe_box)

License
-------
This work is under BSD simplified License.
