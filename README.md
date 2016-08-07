# FunJSON

FunJSON is a JSON Helper for Java. You can use this project to parse or create
JSON architecture in your application.

## Functionality

- Parse JSON format (file (not tested yet) and string)
- JSON writer
- Properties selection

## How to use

- Parse a json format
```
//str = a json file or string
JSON json = new JSON(str);
```

- Create a json file
```
//obj = new JSONObject with properties
JSON json = new JSON(obj);
json.write(new File(myPath));
```

- Select a property
```
JSON json = new JSON("{\"foo\":42}");
json.getProperty("foo").get() //will return 42.0
```

## Following project

- [FunParser](https://github.com/YannDub/FunParser) : A functional Parser wrote in Java

## ToDo List

- Enhance the parser to ensure the parsing of all json files
