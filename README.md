JBeret is an implementation of [JSR 352 (Batch Applications for the Java Platform)](https://java.net/projects/jbatch). It is also included in [WildFly, the new and improved JBoss Application Server](http://wildfly.org/) to provide portable batch processing support in Java EE environment. 

####Sub-modules:
* [jberet-core](https://github.com/jberet/jsr352/tree/master/jberet-core): core batch runtime engine
* [jberet-se](https://github.com/jberet/jsr352/tree/master/jberet-se): impl classes specific to Java SE runtime environment
* [jberet-support](https://github.com/jberet/jsr352/tree/master/jberet-support): a collection of reusable batch readers and writers (e.g., CSV, fixed length, Excel, Json, XML, Mongo, etc) for batch applications, and JNDI support
* [jberet-distribution](https://github.com/jberet/jsr352/tree/master/jberet-distribution): produces a zip distribution for Java SE
* [jberet-jpa-repository](https://github.com/jberet/jsr352/tree/master/jberet-jpa-repository): batch job repository implemented with JPA (incomplete)
* [test-apps](https://github.com/jberet/jsr352/tree/master/test-apps): test applications
* [tck-porting-impl](https://github.com/jberet/jsr352/tree/master/test-apps): support running [JSR 352 TCK](https://java.net/projects/jbatch/downloads) with JBeret in Java SE

####Project Resources:
* [JBeret Issues & Bugs](https://issues.jboss.org/browse/JBERET-55?jql=project%20%3D%20JBERET)
* [WildFly Forum](https://community.jboss.org/en/wildfly?view=discussions)
* [JBeret Forum](https://community.jboss.org/en/jberet/)
* [JSR 352 Expert Group Discussion](https://java.net/projects/jbatch/lists/public/archive)
* [JBoss Batch API project](https://github.com/jboss/jboss-batch-api_spec)
* Download JBeret jars and distro zip from [JBoss.org nexus](https://repository.jboss.org/nexus/index.html#nexus-search;quick~jberet)
* Additional JBeret project info on [ohloh.net](https://www.ohloh.net/p/jberet)

####Batch sample & test applications:
  - <https://github.com/chengfang/wildfly-samples/tree/master/jberet>, web apps that demonstrate the following:
    + JsonItemReader, JsonItemWriter
    + CsvItemReader, CsvItemWriter
    + XmlItemReader, XmlItemWriter
    + MongoItemReader, MongoItemWriter
    + JNDI lookup of Jackson JsonFactory, MappingJsonFactory & XmlFactory in WildFly for batch reader and writer
    + JNDI lookup of MongoClient in WildFly
    + job xml files showing the use of various reader/writer configuration properties
    + jberet-support module can be installed in WildFly and referenced by multiple apps via either MANIFEST.MF or jboss-deployment-structure.xml
  - <https://github.com/jberet/jsr352/tree/master/jberet-support/src/test>
    + comprehensive tests for implemented batch readers and writers
  - <https://github.com/jberet/jsr352/tree/master/test-apps>
    + test apps running in Java SE environment to verify core batch requirements
    + test apps running in Java SE environment to verify additional JBeret features (inheritance, scripting support, etc)

####How to reference org.jberet artifacts in maven pom.xml
    <repositories>
        <repository>
            <id>jboss-public-repository-group</id>
            <name>JBoss Public Repository Group</name>
            <url>http://repository.jboss.org/nexus/content/groups/public/</url>
        </repository>
    </repositories>
    ...
    <dependencies>
        <dependency>
            <groupId>org.jboss.spec.javax.batch</groupId>
            <artifactId>jboss-batch-api_1.0_spec</artifactId>
            <version>1.0.0.Final</version>
        </dependency>
        <dependency>
            <groupId>org.jberet</groupId>
            <artifactId>jberet-core</artifactId>
            <version>1.0.2.Final</version> <!-- replace it with the desired version -->
        </dependency>
        <dependency>
            <groupId>org.jberet</groupId>
            <artifactId>jberet-support</artifactId>
            <version>1.0.2.Final</version> <!-- replace it with the desired version -->
        </dependency>

####Batch application dependencies
#####Minimal application dependencies:
        <dependency>
            <groupId>org.jboss.spec.javax.batch</groupId>
            <artifactId>jboss-batch-api_1.0_spec</artifactId>
        </dependency>
        <dependency>
            <groupId>javax.inject</groupId>
            <artifactId>javax.inject</artifactId>
        </dependency>
        <dependency>
            <groupId>javax.enterprise</groupId>
            <artifactId>cdi-api</artifactId>
        </dependency>
        <dependency>
            <groupId>org.jboss.spec.javax.transaction</groupId>
            <artifactId>jboss-transaction-api_1.2_spec</artifactId>
        </dependency>
        <dependency>
            <groupId>org.jberet</groupId>
            <artifactId>jberet-core</artifactId>
        </dependency>
        <dependency>
            <groupId>org.jboss.marshalling</groupId>
            <artifactId>jboss-marshalling</artifactId>
        </dependency>
        <dependency>
            <groupId>org.jboss.logging</groupId>
            <artifactId>jboss-logging</artifactId>
        </dependency>
        <dependency>
            <groupId>org.jboss.weld</groupId>
            <artifactId>weld-core</artifactId>
        </dependency>
        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
        </dependency>
        
#####The following is also required for Java SE batch applications (h2 can be omitted when using in-memory batch job repository):
        <dependency>
            <groupId>org.jberet</groupId>
            <artifactId>jberet-se</artifactId>
        </dependency>
        <dependency>
            <groupId>org.jboss.weld.se</groupId>
            <artifactId>weld-se</artifactId>
        </dependency>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
        </dependency>
        
#####Optional application dependencies:
        <!-- any JDBC driver jars, e.g., h2, when using jdbc batch job repository -->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
        </dependency>
        
        <!-- replace Java built-in StAX provider with aalto-xml or woodstox -->
        <dependency>
            <groupId>com.fasterxml</groupId>
            <artifactId>aalto-xml</artifactId>
        </dependency>
        <dependency>
            <groupId>org.codehaus.woodstox</groupId>
            <artifactId>stax2-api</artifactId>
        </dependency>
        
        <!-- jberet-support includes common reusable batch ItemReader & ItemWriter classes for
        various data types such as CSV, XML, JSON, Fixed length, Excel, MongoDB, etc.
        The application should provide appropriate transitive dependencies from jberet-support,
        depending on the usage.-->
        <dependency>
            <groupId>org.jberet</groupId>
            <artifactId>jberet-support</artifactId>
        </dependency>
