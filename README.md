# Cyber Security Simulator - Advanced Java Application

This Java-based GUI application exemplifies the integration of essential cybersecurity functionalities while leveraging advanced Object-Oriented Programming (OOP) principles. The application includes three primary modules:

1. **🔒 Password Strength Checker**
2. **🔐 Encryption/Decryption Utility (Caesar Cipher)**
3. **🔑 Password Generator**

Each module reflects modular design and ensures adaptability, maintainability, and user-centric functionality.

```
CyberSecuritySimulator/
│
├── SimulatorApp.java                 // Entry point (Abstraction)
├── PasswordStrengthChecker.java      // Password Strength Checker (Encapsulation, Event-Driven Programming)
├── CipherSimulator.java              // Encryption/Decryption Utility (Encapsulation, Polymorphism)
└── PasswordGenerator.java            // Password Generator (Encapsulation, Reusability, Event-Driven Programming)

```

---

## Application Modules and Their Functionalities

### 1. **🔒 Password Strength Checker**

- **Objective**: Assesses the strength of a user-input password, classifying it as Weak, Moderate, or Strong based on predefined criteria.
- **Key OOP Principles Utilized**:
  - **Encapsulation**: Password validation logic resides in a private `checkStrength` method, abstracting the implementation details.
  - **Event-Driven Programming**: Action events are handled via `ActionListener`, ensuring user interaction drives the functionality.

### 2. **🔐 Encryption/Decryption Utility**

- **Objective**: Provides text encryption and decryption using the Caesar Cipher algorithm with a fixed shift value of 3.
- **Key OOP Principles Utilized**:
  - **Encapsulation**: The cipher logic is encapsulated within the `caesarCipher` method, promoting modularity.
  - **Polymorphism**: The same `caesarCipher` method accommodates both encryption and decryption by parameterizing the shift value.
  - **Event-Driven Programming**: Implements dynamic responses to user inputs via `ActionListener` mechanisms.

### 3. **🔑 Password Generator**

- **Objective**: Generates random, secure passwords of a specified length and displays them within the GUI.
- **Key OOP Principles Utilized**:
  - **Encapsulation**: The password generation logic is encapsulated within a private `generatePassword` method.
  - **Reusability**: The `showGUI` method ensures the GUI framework can be deployed independently across various modules.
  - **Event-Driven Programming**: Dynamically updates the generated password based on user actions.

---

## Core Object-Oriented Programming Concepts

### 1. **🔒 Encapsulation**

- Core methods, including `generatePassword` and `caesarCipher`, are private, safeguarding internal implementation and enhancing modularity.
- GUI components (e.g., `JTextField`, `JButton`) are confined within respective modules to ensure cohesive design.

### 2. **📦 Abstraction**

- Complex functionalities are abstracted into high-level modules, such as `PasswordStrengthChecker`, `CipherSimulator`, and `PasswordGenerator`, fostering code clarity and scalability.

### 3. **🔀 Polymorphism**

- The `caesarCipher` method demonstrates polymorphism by accommodating both encryption and decryption functionalities through parameterized operations.

### 4. **🔄 Event-Driven Programming**

- Each module incorporates `ActionListener` to facilitate dynamic responses to user interactions, creating a seamless user experience.

### 5. **🔁 Reusability**

- GUI construction is modularized through reusable `showGUI` methods, enabling consistent implementation across various components.

---

## Execution Instructions

1. 🖥️ Compile and execute the `SimulatorApp` class.
2. The main interface provides buttons for:
   - **🔒 Password Strength Checker**: Launches the password analysis tool.
   - **🔐 Encrypt/Decrypt**: Opens the Caesar Cipher utility.
   - **🔑 Password Generator**: Activates the password creation module.
3. Select a button to access the desired module.

---

## System Requirements

- **💻 Java Development Kit (JDK)**: Version 8 or later.
- **📚 Swing Library**: Required for GUI implementation (included in standard Java SE distributions).

---

## Practical Examples

![image](https://github.com/user-attachments/assets/160e51e0-a630-4740-9a78-0d07c1225eec)


### 🔒 Password Strength Checker

- **Input**: `P@ssw0rd123`
- **Output**: `Password Strength: Strong`

![image](https://github.com/user-attachments/assets/73bfbe9b-aedc-4d9b-b563-6f5e08a490c2)


### 🔐 Encryption/Decryption Utility

- **Input**: `hello`
  - **Encrypted Output**: `khoor`

![image](https://github.com/user-attachments/assets/d3f61dbb-24b8-4d4c-a723-142039f171a7)

  - **Decrypted Output**: `hello`


![image](https://github.com/user-attachments/assets/3519525d-ae8c-4059-8a0d-45aca0a6ea21)


### 🔑 Password Generator

- **Generated Output Example**: `aZ3!bX7@`


![image](https://github.com/user-attachments/assets/4e627492-fb3d-44cc-a65e-8e565c2f33fd)


---

## Future Development Prospects

- 🗂️ Incorporate file-based storage for managing generated passwords.
- 🛡️ Extend cryptographic capabilities to include modern algorithms (e.g., AES, RSA).
- 📊 Implement advanced password strength validation using dictionary-based and heuristic analysis.

---

## Project Overview

This application was developed as an academic project to demonstrate Java GUI development and OOP principles. It simulates real-world cybersecurity challenges, offering a user-friendly yet robust interface for educational and practical purposes.

