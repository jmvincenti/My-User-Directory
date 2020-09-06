# My-User-Directory
Sample project using [randomuser.me](http://randomuser.me/) api to test the following project structure:

### Clean architecture
The application follows the [Clean architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) with: 
- Business entities under `feature.*.model.*` packages
- Datasource contracts and usecases under `feature.*.domain.*` packages
- State machines, also on the business layer, are located under `feature.*.domain.*` packages
- Datasource implementation are present under `feature.*.data.*` packages

### State machines 
A custom redux pattern is used to control features state. 
State machines are written only using business layer entities and datasources contracts. 
They can be fully unit tested with mocked implementations. 

### Library used

* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency injection. 
* [SQDelight](https://github.com/cashapp/sqldelight) for SQLite database storage
* Kotlin coroutines to handle asynchronous calls
* [Retrofit](https://square.github.io/retrofit/) for HTTP requests
* [Moshi](https://github.com/square/moshi) for Json parsing
* [AndroidX components](https://developer.android.com/jetpack/androidx) and [Material library](https://material.io/develop/android/) for UI design
* [Glide](https://github.com/bumptech/glide) to fetch and display images
