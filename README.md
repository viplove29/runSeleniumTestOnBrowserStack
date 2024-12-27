# testng-browserstack

[TestNG](http://testng.org) Integration with BrowserStack.

![BrowserStack Logo](https://d98b8t1nnulk5.cloudfront.net/production/images/layout/logo-header.png?1469004780)

## Using Maven

### Run sample build

- Clone the repository
- Replace YOUR_USERNAME and YOUR_ACCESS_KEY with your BrowserStack access credentials in browserstack.yml.
- Install dependencies `mvn compile`
- To run the test suite having cross-platform with parallelization, run `mvn test -P sample-test`
- To run local tests, run `mvn test -P sample-local-test`

Understand how many parallel sessions you need by using our [Parallel Test Calculator](https://www.browserstack.com/automate/parallel-calculator?ref=github)

### Integrate your test suite

This repository uses the BrowserStack SDK to run tests on BrowserStack. Follow the steps below to install the SDK in your test suite and run tests on BrowserStack:

* Create sample browserstack.yml file with the browserstack related capabilities with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings) and place it in your root folder.
* Add maven dependency of browserstack-java-sdk in your pom.xml file
```sh
<dependency>
    <groupId>com.browserstack</groupId>
    <artifactId>browserstack-java-sdk</artifactId>
    <version>LATEST</version>
    <scope>compile</scope>
</dependency>
```
* Modify your build plugin to run tests by adding argLine `-javaagent:${com.browserstack:browserstack-java-sdk:jar}` and `maven-dependency-plugin` for resolving dependencies in the profiles `sample-test` and `sample-local-test`.
```
            <plugin>
               <artifactId>maven-dependency-plugin</artifactId>
                 <executions>
                   <execution>
                     <id>getClasspathFilenames</id>
                       <goals>
                         <goal>properties</goal>
                       </goals>
                   </execution>
                 </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.0.0-M5</version>
                <configuration>
                    <suiteXmlFiles>
                        <suiteXmlFile>config/sample-local-test.testng.xml</suiteXmlFile>
                    </suiteXmlFiles>
                    <argLine>
                        -javaagent:${com.browserstack:browserstack-java-sdk:jar}
                    </argLine>
                </configuration>
            </plugin>
```
* Install dependencies `mvn compile`

## Using Gradle

### Prerequisites
- If using Gradle, Java v9+ is required.

### Run sample build

- Clone the repository
- Install dependencies `gradle build`
- To run the test suite having cross-platform with parallelization, run `gradle sampleTest`
- To run local tests, run `gradle sampleLocalTest`

Understand how many parallel sessions you need by using our [Parallel Test Calculator](https://www.browserstack.com/automate/parallel-calculator?ref=github)

### Integrate your test suite

This repository uses the BrowserStack SDK to run tests on BrowserStack. Follow the steps below to install the SDK in your test suite and run tests on BrowserStack:

* Following are the changes required in `gradle.build` -
    * Add `compileOnly 'com.browserstack:browserstack-java-sdk:latest.release'` in dependencies
    * Fetch Artifact Information and add `jvmArgs` property in tasks *SampleTest* and *SampleLocalTest* :
  ```
  def browserstackSDKArtifact = configurations.compileClasspath.resolvedConfiguration.resolvedArtifacts.find { it.name == 'browserstack-java-sdk' }
  
  task sampleTest(type: Test) {
    useTestNG() {
      dependsOn cleanTest
      useDefaultListeners = true
      suites "config/sample-test.testng.xml"
      jvmArgs "-javaagent:${browserstackSDKArtifact.file}"
    }
  }
  ```

* Install dependencies `gradle build`

##Folder Structure :

## Detailed Component Description  

### `src/main/java/org/example/config`
- **`ConfigReader.java`**: Utility class for reading configuration values from `config.properties`.

### `src/main/java/org/example/pages`
- **`SpanishNewsWebsitePage.java`**: Page Object Model (POM) class encapsulating methods to interact with elements on the Spanish news website.

### `src/main/java/org/example/utils`
- **`TranslationService.java`**: Utility for translating text using the Google Translation API.
- **`FileUtilsHelper.java`**: Helper utility for file-related operations like saving images or handling directories (if required).

### `src/main/java/org/example/models`
- **`TranslationResponse.java`**: (Optional) Model class for deserializing translation API responses into Java objects.

### `src/main/resources`
- **`config.properties`**: Centralized configuration file containing API keys, base URIs, and other constants.
- **`test-data`**: Directory to store test data files (if needed for testing purposes).

### `src/test/java/org/example/tests`
- **`BaseTest.java`**: Abstract base class defining shared setup and teardown logic for test cases.
- **`BStackDemoTest.java`**: Test class containing test methods for verifying functionality.

### `src/test/resources`
- Directory to store resources specific to testing, such as mock data or other auxiliary files.

---

This structure allows for better code organization, scalability, and reusability. Each module serves a specific purpose and ensures that the code adheres to the principles of clean architecture.

## Notes
* You can view your test results on the [BrowserStack Automate dashboard](https://www.browserstack.com/automate)

* Problem Statement :
* Visit the website El País, a Spanish news outlet.
    - Ensure that the website's text is displayed in Spanish.
    - Scrape Articles from the Opinion Section:
    - Navigate to the Opinion section of the website.
    - Fetch the first five articles in this section.
    - Print the title and content of each article in Spanish.
    - If available, download and save the cover image of each article to your local machine.
    - Translate Article Headers:
    - Use a translation API of your choice, such as:
    - Google Translate API
    - Rapid Translate Multi Traduction API
    - Translate the title of each article to English.
    - Print the translated headers.
    - Analyze Translated Headers:
    - From the translated headers, identify any words that are repeated more than twice across all headers combined.
    - Print each repeated word along with the count of its occurrences.
    - Cross-Browser Testing:
    - Run the solution locally to verify functionality.
    - Once validated, execute the solution on BrowserStack across 5 parallel threads, testing across a combination of desktop and mobile browsers.
    - DEmo Video link : [demoVideo](https://github.com/viplove29/runSeleniumTestOnBrowserStack/tree/master/demoVideo)
