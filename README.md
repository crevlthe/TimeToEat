# TimeToEat App 

## QuickStart with TimeToEat App 

  TimeToEat is a recipe recommender that allows users to save recipes, manipulate ingredient lists and fetch to-buy-lists. The application also includes some additional functions, such as search of nearby supermarket, sending notifications and uploading personal recipes. TimeToEat was built in Android Studio IDE using Java. 



### Screenshots

![search](https://github.com/crevlthe/TimeToEat/blob/master/search.png)



### Clonning from GitHub 

* Clone via SSH 

```
git clone git@github.com:crevlthe/TimeToEat.git 
```

  Open Android Studio &rarr; Check out the project from Version Control &rarr; Git &rarr; Use the obtained SSH to clone the repository to your device and open it in your Android Studio 



* Clone via HTTPS 

```
git clone https://github.com/crevlthe/TimeToEat.git 
```

  Use yout password to clone through HTTPS. It may alternatively ask you to use your [Personal Access Token](https://docs.gitlab.com/ee/user/profile/personal_access_tokens.html)

### Running a sample 

* Run on Android Studio emulator 

  After you cloned the repository and opened the project on Android Studio, you may use the emulator to run it.
  Go to: 

  Tools &rarr; AVD Manager &rarr; + Create Virtual Device 

  and add the new emulator. We were testing the app mostly on Pixel 3a XL API R. After, use the build/run option in Android Studio. 

* Run on your device 

  Alternatively, run the app on your physical device. To do this, plug your phone into your computer, and enable USB Debugging under Developer Options in Settings.

  If Developer Options is not shown in Settings, go to About Phone instead and tap Build Number 7 times to enable the Developer Options menu.

  The Google USB Driver is required for Windows for Google devices and this can be obtained at https://developer.android.com/studio/run/win-usb.
  For other manufacturers, you may need to find device-specific USB drivers.

  For the purposes of testing this app, a Nexus 6P was used.

### Libraries Used 

#### Libraries 

* [Jsoup](https://jsoup.org/) - Java library for working with real-world HTML, used for retrieving recipes from [allrecipes](https://www.allrecipes.com/)

* [Material Design](https://material.io/design) - used for creating UI components

* [Glide](https://github.com/bumptech/glide) - efficient image loading framework for Android 

* [Firebase](https://firebase.google.com/) - provided real-time database and storage 

#### Widgets  

* [CardView](https://developer.android.com/guide/topics/ui/layout/cardview) - for card based layout 

* [RecyclerView](https://developer.android.com/guide/topics/ui/layout/recyclerview) - for creating a scrolling list of elements 



