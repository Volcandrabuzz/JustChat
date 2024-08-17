# One-to-One Communication App

## Overview

This Android app enables seamless one-to-one communication, built using Android Studio and Firebase. It allows users to send and receive messages in real-time, making it ideal for personal chat applications.

## Features

- **Real-Time Messaging**: Instant message delivery with Firebase Realtime Database.
- **User Authentication**: Secure sign-in with Firebase Authentication.
- **User Profiles**: Manage and display user profiles.
- **Message History**: Persistent storage of chat history using Firestore.

## Getting Started

### Prerequisites

- Android Studio
- Firebase account
- Intermediate knowledge of Android development

### Setup

1. **Clone the Repository**

   ```bash
   git clone https://github.com/Volcandrabuzz/JustChat
   ```

2. **Open the Project**

   Open Android Studio and import the project.

3. **Configure Firebase**

   - Go to the [Firebase Console](https://console.firebase.google.com/).
   - Create a new project and add your Android app.
   - Download the `google-services.json` file and place it in the `app` directory of your project.
   - Enable Firebase Authentication and Realtime Database in the Firebase Console.

4. **Sync Gradle**

   Make sure to sync your project with Gradle files by clicking on `Sync Now` in Android Studio.

5. **Run the App**

   Build and run the app on an emulator or physical device.

## Usage

- **Sign Up/Sign In**: Use Firebase Authentication to create an account or log in.
- **Start Chatting**: Select a user and start sending messages.
- **View Messages**: Access your message history in real-time.

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -am 'Add new feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Create a new Pull Request.

Feel free to adjust the details according to your project specifics!
