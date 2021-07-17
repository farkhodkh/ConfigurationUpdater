# ConfigurationUpdater

Application is made for demonstrating android (Kotlin) skill

Spec:
The application is designed to read the configuration file from the local storage. Files are read at a specified interval in the background. The readed data is saved to the local database

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
1. core - scheduling worker
2. persistence-api - database repository
3. persistence-room - database room implementation
4. remote config - configuration file read
