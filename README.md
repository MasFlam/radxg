# rest-api-doc-xml-gen
**RADXG** - Write REST API documentation/specification in XML, and generate HTML from it!

## Usage
`java -jar rest-api-doc-xml-gen-{VERSION}.jar <xml-file> <css-file>`  
Where `{VERSION}` should be replaced with the version of RADXG. This will use `<xml-file>` to generate an HTML page with CSS from the given `<css-file>` inside a `<style>` tag.

## Example
See [`example.xml`](/example.xml) for an example specification XML.

## Building the project
In the project root directory, use: `mvn clean package` to build the executable JAR located at `target/rest-api-doc-xml-gen-{VERSION}.jar`, where `{VERSION}` will become the RADXG version.