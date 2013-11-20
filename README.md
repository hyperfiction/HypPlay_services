HypPlayService
=============================
A Google Play Services native extension for Haxe NME
-----------------------------

This NME native extension allows you to integrate the Google Play Services into your OpenFL application.

For now it's Android only.
iOS will be coming soom.

[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

Requirement
------------
Require OpenFL 1.1+

Android
-------

You must copy the folder named "templateAndroid" into your workspace.

Inside the subfolder "android/templates/res/value/" edit the "ids.xml" file and fill your Google Play Services id.

Then add the following to your OpenFL project file:
```xml
<templatePath name="templates/android/template-path/"/>
````

Recompiling
-----------
For recompiling the native extensions just use the sh files contained in the project folder

Usage
-----
Take a look at the [wiki](https://github.com/hyperfiction/HypPlay_services/wiki)

Made at [Hyperfiction](http://hyperfiction.fr)
--------------------
Developed by :
- [Johann Martinache](https://github.com/shoebox) [@shoe_box](https://twitter.com/shoe_box)

License
-------
This work is under BSD simplified License.
