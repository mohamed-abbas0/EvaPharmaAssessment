# 🎯 Eva Pharma Assessment – Playwright Java Automation

This project is built using **Playwright for Java** (JDK 23) to automate an end-to-end user flow on [https://opensource-demo.orangehrmlive.com/]. It’s part of the **Eva Pharma Automation Assessment**.

---

## 🧪 What the Script Does

The test covers the following:

1. Navigate to "https://opensource-demo.orangehrmlive.com/"
2. Enter "Admin" as username
3. Enter "admin123" as password
4. Click on the login button
5. Click on Admin tab on the left side menu
6. Get the number of records found
7. Click on add button
8. Fill the required data
9. Click on save button
10. Verify that the number of records increased by 1
11. Search with the username for the new user
12. Delete the new user
13. Verify that the number of records decreased by 1.

---

## 🛠️ Tech Stack

- ☕ **Java 23**
- ⚡ **Playwright for Java**
- 🧪 **TestNG**
- 📦 **Maven**
- 📊 **ExtentReports** (HTML reporting)
- 🪵 **Log4j2** (logging)
- 🧪 **Cucumber**

---

✅ Supported browsers:

- Chrome
- Firefox
- Edge

---


## 🚀 How to Run the Tests

1- Clone the repository:
  git clone https://github.com/mohamed-abbas0/ItidaChallenge.git

2- Navigate to the project directory:
  cd ItidaChallenge

3- Use the following command to execute the test suite:
```bash

Run on Chrome:
mvn -P itidaChallenge test -DxmlFile=testSuites/itidaChallengeTestSuite.xml

Run on Firefox:
mvn -P itidaChallenge -DwebBrowser=Firefox test -DxmlFile=testSuites/itidaChallengeTestSuite.xml

Run on Edge:
mvn -P itidaChallenge -DwebBrowser=Edge test -DxmlFile=testSuites/itidaChallengeTestSuite.xml


📊 Test Reports
After test execution, a report is generated at:
/Reports/<DATE>/index.html


📌 Notes
- Ensure that the required browser executables are installed via Playwright.
- Make sure Java 23 and Maven are correctly configured in your system’s environment variables.


👤 Author
Mohamed Abbas
📧 mohamed.abbas74@outlook.com

