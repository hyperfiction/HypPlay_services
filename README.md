HypPlayService
=============================
A Google Play Services native extension for Haxe NME
-----------------------------

This NME native extension allows you to integrate the Google Play Services into your OpenFL application.

For now it's Android only.
iOS will be coming soom.

Requirement
------------
Require OpenFL 1.1+

Android
-------

Add the following line inside of your project file:
```xml
<!-- Google Play Services ID -->
<setenv name="GooglePlayID" value="REPLACE_ME_WITH_YOUR_GOOGLEID" />
```

This library is using Fragments, so you need to edit your OpenFL template class ( GameActivity.java )
Go into [haxelib folder]/lib/openfl-native/[version]/templates/android/template/src/org/haxe/nme.
And edtt the "GameActivity" file.

Replace the following line:
```java
public class GameActivity extends Activity implements SensorEventListener {
```

by:

```java
public class GameActivity extends android.support.v4.app.FragmentActivity implements SensorEventListener {
```

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
[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)
