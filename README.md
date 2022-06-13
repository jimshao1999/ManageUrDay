# ManageUrDay
The java project is to record how you spend your daily time, and you can analyze the time distribution of your daily activities.

## Environmental Settings
1. Run in Java SE 11 or Java SE 17.
2. Download `JavaFx` and `mysql-connector` and link to reference library.

***Dependencies:***

![Dependencies](https://user-images.githubusercontent.com/46078933/173243517-75d19841-daf8-4efd-bd9f-cf24f6f019d8.png)


## How to Run the code
1. Clone the project.
2. Go to `\src\sql\DBConnection` and `\src\sql\DBCreation` and change the ***username*** and ***password*** to fit your **mysql** account.
3. Run `\src\sql\TestConnection` to build the database.
4. if you meet errors in `module-info.java`, just right click and auto-debug.
5. Run `\src\Application\Main`

## Quetion
If fail many time, you can try to open the application by our jar file below.

[Jar](https://gntuedutw-my.sharepoint.com/personal/b08505023_g_ntu_edu_tw/_layouts/15/onedrive.aspx?id=%2Fpersonal%2Fb08505023%5Fg%5Fntu%5Fedu%5Ftw%2FDocuments%2FOOP%5Foptional&ga=1)

Put the jar file in your favorite folder and run the command below or seeing the launch.bat

```
java --module-path "{Your javafx lib folder}\javafx-sdk-18.0.1\lib" --add-modules=javafx.controls,javafx.fxml -jar OOP_optional.jar
```

Example:

```
java --module-path "C:\Users\user\Downloads\openjfx\openjfx-18.0.1_windows-x64_bin-sdk (1)\javafx-sdk-18.0.1\lib" --add-modules=javafx.controls,javafx.fxml -jar OOP_optional.jar
```

## Authors
- [jimshao1999](https://github.com/jimshao1999)
- [Gting6](https://github.com/Gting6)
- [Ming0609](https://github.com/Ming0609)
- [yu02200059](https://github.com/yu02200059)


