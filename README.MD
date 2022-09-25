# Weather forecast 
App use open weather api to get daily forecast 

## Build
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

