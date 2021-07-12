# ConfigurationUpdater

Application is made for dimonstating android (Kotlin) skill
Spec:
The application is designed to read the configuration file from the local storage. Files are read at a specified interval in the background. The read data is saved to the local database

Useb libraries:
1. Material design, Dialog
2. Fragments
3. Lifecycle. ViewModel
4. Room
5. Dagger-Hilt
6. Logging by slf4j
7. Coroutines
8. Coroutines Worker

It has multi model architecture and contains models:
core - scheduling worker
persistence-api - database repository
persistence-room - database room implementation
remote config - configuration file read