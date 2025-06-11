# FOT News

## Created By
Danushka Lakshan  
Student ID: 2022T01548  
Undergraduate Student, Faculty of Technology, University of Colombo

## Overview
FOT News is a news management application designed to provide real-time updates and manage news articles through a simple admin interface. The application is connected to Firebase Realtime Database for seamless data storage and retrieval.

**Please Note:**  
Some updates are coming soon, and there may be occasional errors in the app during the update process.

## Features
- Admin interface for managing news articles.
- Real-time syncing of news data with Firebase Realtime Database.
- Image support for articles.
- Simple and easy-to-use interface for users to view news.

## Project Structure
![image](https://github.com/user-attachments/assets/b6b16e35-2199-46dd-8a0f-0aeeacf56a63)


## Installation

### Prerequisites
- Java (>=8)
- Firebase account and Firebase SDK for Java
- Android Studio (for developing and testing the Android app)
- Gradle (for managing dependencies in the Android project)

### Setup
1. Clone the repository:
    ```bash
    git clone https://github.com/danutech-uoc/fotnewsjava.git
    cd fotnewsjava
    ```

2. Install the dependencies (for Android Studio projects, dependencies are managed via Gradle):
    - Open the project in Android Studio, and Gradle will automatically resolve dependencies.

3. Configure Firebase:
    - Go to [Firebase Console](https://console.firebase.google.com/).
    - Create a new project.
    - Add Firebase SDK for Android to the project.
    - Set up Firebase Realtime Database.
    - Download the Firebase configuration file (`google-services.json`) and add it to the `/app` directory of your project.

4. Run the app:
    - Open the project in Android Studio.
    - Build and run the app using the Android Emulator or a physical device.

## Admin App Setup
For the admin interface, you can use the following repository:
- [FOT News Admin App](https://github.com/danutech-uoc/fotadmin.git)

1. Clone the repository:
    ```bash
    git clone https://github.com/danutech-uoc/fotadmin.git
    cd fotadmin
    ```

2. Install the dependencies (for Android Studio projects, dependencies are managed via Gradle):
    - Open the project in Android Studio, and Gradle will automatically resolve dependencies.

3. Follow the same Firebase setup steps mentioned earlier to configure the Firebase Realtime Database.

4. Run the app:
    - Open the project in Android Studio.
    - Build and run the app using the Android Emulator or a physical device.

## Usage
- **Admin App**: Admin users can add, update, and delete news articles.
- **User App**: Users can view the latest news articles in real-time.

## Firebase Setup
Make sure Firebase is correctly configured with the necessary Realtime Database rules:

### Realtime Database Rules:
```json
{
  "rules": {
    ".read": "auth != null",
    ".write": "auth != null"
  }
}
Contributing
Fork the repository.

Create a feature branch (git checkout -b feature-branch).

Commit your changes (git commit -am 'Add new feature').

Push to the branch (git push origin feature-branch).

Create a new Pull Request.

License
This project is licensed under the MIT License - see the LICENSE.md file for details.

Acknowledgements
Firebase for real-time database management.

The FOT News team for their contributions.
