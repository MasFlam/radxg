# rest-api-doc-xml-gen
**RADXG** - Write REST API documentation/specification in XML, and generate HTML from it!

## Usage
`java -jar rest-api-doc-xml-gen-{VERSION}.jar <xml-basename>...`  
Where `{VERSION}` should be replaced with the version of RADXG, and `<xml-basename>` is the base name of your REST API specification XML file, that is: if your file is `rest-api.xml`, pass `rest-api` as an argument for RADXG to convert it.

## Example
See [`example.xml`](/example.xml) for an example specification XML.

## Building the project
In the project root directory, use: `mvn clean package` to build the executable JAR located at `target/rest-api-doc-xml-gen-{VERSION}.jar`, where `{VERSION}` will become the latest RADXG version.