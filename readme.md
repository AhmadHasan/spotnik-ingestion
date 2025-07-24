# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.0/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.0/gradle-plugin/packaging-oci-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.5.0/reference/web/servlet.html)
* [Spring Data MongoDB](https://docs.spring.io/spring-boot/3.5.0/reference/data/nosql.html#data.nosql.mongodb)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)

### Additional Links

These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

### Retrieving Wikidata Subtypes

You can go to https://query.wikidata.org/ and run the query below.

Running the query with all types might cause an error, in that case run it in batches with a couple of types each time.

Some types have so many subtypes that you have to split within the same top type. Remove the star in the query to disable recursivity and retrieve the first level subtypes and repeat on this list. Please note that the topType in this case would be the direct parent of the subtype. You need to replace it (probably manually in the resulting data export) with the root type where it originates from.

```
SELECT ?topType ?subtype ?subtypeLabel WHERE {
VALUES ?topType {
wd:Q5           # Human
wd:Q43229       # Organization
wd:Q783794      # Company
wd:Q163740      # Educational institution
wd:Q7278        # Political party
wd:Q6256        # Country
wd:Q515         # City
wd:Q3957        # Town
wd:Q56061       # Administrative territorial entity
wd:Q1182803     # Province
wd:Q82794       # Region
wd:Q2221906     # Geographic location
wd:Q23442       # Island
wd:Q8502        # River
wd:Q8504        # Mountain
wd:Q8436        # Building
wd:Q9174        # Book
wd:Q28108       # Film
wd:Q1322638     # Television show
wd:Q1248784     # Magazine
wd:Q62447       # Language
wd:Q811979      # Architectural structure
wd:Q2424752     # Fictional character
wd:Q11446       # Software
wd:Q7397        # Website
wd:Q1047215     # Musical work
wd:Q1190554     # Album
wd:Q180684      # Song
wd:Q198         # Television series
wd:Q1656682     # Musical group
wd:Q838948      # Work of art
wd:Q1359396     # Video game
wd:Q7725634     # Literary work
wd:Q386724      # Political organization
wd:Q820655      # Newspaper
wd:Q2334719     # News agency
wd:Q34770       # Sports team
}
?subtype wdt:P279* ?topType .
SERVICE wikibase:label { bd:serviceParam wikibase:language "en". }
}
```


You can clean the resulted data with this command:

```
grep -v '^topType' all.tsv | awk -F'\t' '{
split($1, a, "/");
split($2, b, "/");
print a[length(a)] "\t" b[length(b)] "\t" $3
}' > all_clean.tsv
```
