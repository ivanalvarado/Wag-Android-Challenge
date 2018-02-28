# Wag-Android-Challenge

Android coding challenge for Wag!

## Third Party Libraries

The third party libraries I chose for consuming the API and displaying images are OkHttp, Retrofit, and Picasso. 

I specifically chose these libraries because they've become commonplace among Android apps and the accepted industry standard. Also, they've all been created by the same company, Square, so they complement each other nicely and compatibility issues are uncommon.

## Confirming images are being cached for offline use

To test/confirm the images being downloaded are being stored for offline use, I've added a DEBUG boolean, ```PICASSO_DEBUG```, in MainActivity.java that can be set to ```true``` in order to visually see that an image has been stored in cache/memory. I accomplish this by using Picasso's OkHttp3Downloader library which stores an image in memory once it has been downloaded. You can follow Picasso's debug indicator key displayed [here](http://square.github.io/picasso/).
