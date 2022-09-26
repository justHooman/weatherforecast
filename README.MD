# Weather forecast 
App use open weather api to get daily forecast 
## A. Brief explanation for the software development principles
    - Apply MVVM with Clean Architecture: 
        - with Model and data object act as Entity Layer
        - Usecase as Usecase Layer
        - ViewModel as Presenter in Interface Adapters Layer
        - UI, Network call are Framework and Devices Layer
    - SOLID: 
        - class is as simple as posible, single repository, 
        - multiple implements of Repo interface but don't break app logic, 
        - split Interface into smaller interface and use composite when posible
        - use DI and outer object only keep reference/ contact via interface
    - Keep all the serects like keystores, appid out of git, and load from project var or env var to avoid leak info when commit to public git Repo
    - Centralization libraries version in gradle scripts, order code by feature releated to help easier to split mono project to multiple module project when code grow bigger later.

## B. Brief explanation for the code
    1. Code Structure:
    - App code is group by features, like all related to `daily forecast` group under package `dailyforecast`
    - feature folder will devide into smaller group:  
        - api: for network related code, like Retrofit Service
        - api.dto: class map data from network call to object 
        - data: app domain model and repository to manage the data model
        - di: dependency injection related code
        - ui: all UI releated code, like Fragments, Adapters, Views if any
        - usecases: the small action flow of app, which combine into bigger logic controller like viewmodel
        - viewmodels: viewmodel related code
    2. Libraries use:
    - Retrofit + Gson + Kotlin Coroutine: for Network call, parse response and handle thread
    - Lifecycle libs: for LiveData and app lifecyle observe
    - Hilt for Dependencies Injection
    - other UI libs like: constraint layout, material, ...
## C. Build
set environment/ or project variables for these keys below:
    
  1. Keystore: setup for app keystore, path file set in env should be a absolute path or a relative path from `rootDir/app`

      1.1. for debug, 
        - by default, use the `~/.android/debug.keystore`
        - see [debug/keys-pass](./app/.keys/debug/keys-pass) to see the format of keys pass file:
          ```
            storePassword 
            keyAlias
            keyPassword
          ```
        - if use another keystore for debug build, 
          set the path to that keystore and pass-file by env variables:
          ```
            WEATHER_APP_ANDROID_DEBUG_KEYSTORE
            WEATHER_APP_ANDROID_DEBUG_KEY_PASS
          ```
            
      1.2. for local build without RELEASE keys
        - set environment `CI` to 1 to use the debug keystore as the keystore for release
          ```
          CI=1
          ```

      1.3. for release Android apk
        - set the path to keystore and pass-file by env variables:
          ```
          WEATHER_APP_ANDROID_RELEASE_KEY_STORE
          WEATHER_APP_ANDROID_RELEASE_PASS_FILE
          ```

  2. Open Weather Map
  - set env for appID of openweathermap api
    ```
    OPEN_WEATHER_MAP_APPID
    ```

## D. Checklist
1. MVP Functionalities

   [x] 1. The application is a simple Android application which is written by Java/Kotlin.

   [x] 2. The application is able to retrieve the weather information from OpenWeatherMaps API.

   [x] 3. The application is able to allow user to input the searching term.

   [x] 4. The application is able to proceed searching with a condition of the search term length must be from 3 characters or above.

   [x] 5. The application is able to render the searched results as a list of weather items.

   [x] 6. The application is able to support caching mechanism so as to prevent the app from generating a bunch of API requests.

   [x] 7. The application is able to manage caching mechanism & lifecycle.
   
   [x] 8. The application is able to handle failures.
   
   [x] 9. The application is able to support the disability to scale large text for who can't see the text clearly.
   
   [ ] 10. The application is able to support the disability to read out the text using VoiceOver controls.
   
2. Expected Outputs

   [x] 1. Programming language: Kotlin is required, Java is optional.
   
   [x] 2. Design app's architecture (suggest MVVM)
   
   [x] 3. Apply LiveData mechanism
   
   [x] 4. UI should be looks like in attachment.
   
   [x] 5. Write UnitTests
   
   [ ] 6. Acceptance Tests
   
   [x] 7. Exception handling
   
   [x] 8. Caching handling
   
   [ ] 9. Secure Android app from:
   
   [x] 	a. Decompile APK
   
   [ ] 	b. Rooted device
   
   [ ] 	c. Data transmission via network
   
   [ ] 	d. Encryption for sensitive information
   
   [ ] 10. Accessibility for Disability Supports:
   
   [ ] 	a. Talkback: Use a screen reader.
   
   [x] 	b. Scaling Text: Display size and font size: To change the size of items on your screen, adjust the display size or font size.
   
   [ ] 11. Entity relationship diagram for the database and solution diagrams for the components, infrastructure design if any
   
   [x] 12. Readme file includes:
   
   [x] 	a. Brief explanation for the software development principles, patterns & practices being applied
   
   [x] 	b. Brief explanation for the code folder structure and the key Java/Kotlin libraries and frameworks being used
   
   [x] 	c. All the required steps in order to get the application run on local computer
   
   [x] 	d. Checklist of items the candidate has done.
   