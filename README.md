GreedyGame Android Native Integration Guide
===================

This is a complete guide to integrate GreedyGame plugin within your native android game. 

You can download Android library project named, [greedy-game-agent](https://github.com/GreedyGame/android-native-plugin/releases).

| Original       | Dynamic Theme      |
| ------------- | ----------- |
| ![Sun Original Theme](screen-shots/sun.png?raw=true "Sun Original Theme" ) | ![Moon Dynamically changed Theme](screen-shots/moon.png?raw=true "Moon Dynamically changed Theme" ) |

#### Steps

* Link android library project to build path
* Put your gameprofile id in Android Xml values as string
    ```xml
    <resources>
	    <string name="greedy_game_profile">11111111</string>
    </resources>
    ```
### Documentations
#### GreedyGameAgent
**SDK Overview**

Install the GreedygameAgent in the Application

##### `public GreedyGameAgent install(Activity activity, IAgentListener agentListener);`

---------

**Methods**

##### `public void init(String []Units, FETCH_TYPE)`
Lookup for new native campaign from server. 

* GameId - Unique game profile id from panel.greedygame.com
* Units - List of relative path of assets used in games. 
    Also register unit id can be used
* FETCH_TYPE - it can be FETCH_TYPE.DOWNLOAD_BY_PATH or FETCH_TYPE.DOWNLOAD_BY_ID, to fetch units by relative path or u_id
    
##### `public String activeTheme()`
Return Theme id of currently active and running theme

##### `public String getActivePath()`
 Return path of folder, where assets of activeTheme is stored.

----

#### FloatAdLayout - For Floating ads
**Class Overview**

Extended FrameLayout used to display FloatAd creatives

**Public Constructors**
##### `FloatAdLayout(Context context)`

Constructs a new instance of FloatAdLayout.

----------

**Methods**

##### `public void fetchHeadAd(String unit_id) throws AgentInitNotCalledException`
Fetch floating AdHead unit and add view to current context. 

* unit_id - Float unit id from panel.greedygame.com (e.g 'float-123')
* AgentInitNotCalledException - throws exception if called before calling GreedyGameAgent's init callback.
    

##### `public void fetchHeadAd(String unit_id, int diX, int diY) throws AgentInitNotCalledException`
Fetch floating AdHead unit and add view to current context. 

* unit_id - Float unit id from panel.greedygame.com (e.g 'float-123')
* diX, diY - Adjust dip cordinates in screen. 0,0 stands for top left.
* AgentInitNotCalledException - throws exception if called before calling GreedyGameAgent's init callback.
 
##### `public void removeAllHeadAd()`
Hide floating AdHead with unit-id
```java
/*** Fetching Float Ad unit ***/
floatAdlayout = new FloatAdLayout(context);
try {
    floatAdlayout.fetchHeadAd("float-363");
} catch (AgentInitNotCalledException e) {
    e.printStackTrace();
}
```

----

**Other Utilities Methods**

##### `public String get_verison()`
Return sdk version
    
##### `public void setDebug(boolean b)`
Set sdk into debug mode

----

#### interface IAgentListner
**Class Overview**
It is used as a callback listener argument for GreedyGameAgent class

**Methods**
 
##### `void onInit(OnInitEvent response)`
response value to indicate
     * CAMPAIGN_NOT_AVAILABLE = using no campaign
     * CAMPAIGN_AVAILABLE = Campaign is active 

##### `void onDownload()`
Called when new branded contents are downloaded so that new scene can fetch assets from getActivePath().

#### `void unAvailablePermissions(ArrayList permissions)`

This method needs to be used only if your game is targetting SDK version 23 or higher. This callback gives a list of permissions that are not available at runtime and is invoked after GreedyGameAgent initialization.

NB : Only performs check for 4 dangerous permissions that are required by GreedyGameSDK.

Permissions that are checked :

Manifest.permission.ACCESS_COARSE_LOCATION
Manifest.permission.WRITE_EXTERNAL_STORAGE
Manifest.permission.GET_ACCOUNTS
Manifest.permission.READ_PHONE_STATE
NB : The above strings itself are returned in the argument if they are not available.

void onError()

Called when there is a problem with downloading the units.

For example

```java
class GGListner implements IAgentListner{
    @Override
    public void onProgress(float progress) {
        //Use this for showing progress bar
        Log.i("GreedyGame Sample", "Downloaded = "+progress+"%");
    }

    @Override
    public void onDownload() {
        if(success){
            isBranded = true;
        }
    }

    @Override
    public void onInit(OnInitEvent response) {
        if(    response == OnInitEvent.CAMPAIGN_CACHED || 
            response == OnInitEvent.CAMPAIGN_FOUND){
            isBranded = true;
        }else{
            isBranded = false;
        }
    }

    @override
    public void unAvailablePermissions(ArrayList<String> permissions) {


    }
}

#### Manifest Requirement
```xml
<application>

    <activity
        android:name="com.greedygame.android.adhead.AdHeadActivity"
        android:configChanges="keyboardHidden|orientation|screenSize|screenLayout|layoutDirection"
        android:launchMode="singleTask"
        android:theme="@style/Theme.Transparent">
    </activity>

    <!-- GreedyGame SDK's requirements start -->

    <receiver
        android:name=".agent.GreedyRefReceiver"
        android:enabled="true"
        android:exported="true">
        <intent-filter>
            <action android:name="com.android.vending.INSTALL_REFERRER"/>
            <action android:name="android.net.conn.CONNECTIVITY_CHANGE"/>
            <action android:name="com.greedygame.broadcast"/>
        </intent-filter>
    </receiver>

</application>

<!-- GreedyGame SDK's requirements end -->
```

---
### Some helper functions
To fetch drawable from android res

```java
public static Bitmap getBitmapByResid(GreedyGameAgent ggAgent, int resid){ 
    if(GreedyGameAgent.gameActivity == null){
        return null;
    }

    String resName = GreedyGameAgent.gameActivity.getApplicationContext().getResources().getResourceEntryName(resid);
    File file = new File(ggAgent.getActivePath() + "/" + resName+".png");
    if(file.exists()){
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    return null;
}
```

---

### External Jars
GreedyGame SDK uses Volley from Google and PriorityJobQueue from path as external jars. In case of conflicts you can remove it from libs folder of the wrapper. 

### For Hello Tutorial, goto [andorid-native-sample](andorid-native-sample)  
### For more help please see [FAQ] (https://github.com/GreedyGame/unity-plugin/wiki/FAQs)

