[![Build Status](https://travis-ci.org/maciej-adamiak/avro-schema-registry.svg?branch=master)](https://travis-ci.org/maciej-adamiak/avro-schema-registry)
[![codecov](https://codecov.io/gh/maciej-adamiak/avro-schema-registry/branch/master/graph/badge.svg)](https://codecov.io/gh/maciej-adamiak/avro-schema-registry)
[![CodeFactor](https://www.codefactor.io/repository/github/maciej-adamiak/avro-schema-registry/badge)](https://www.codefactor.io/repository/github/maciej-adamiak/avro-schema-registry)

# Schema registry

## Run
### SBT 
Run the project using command: `sbt run` 

### Docker

#### Build local container

```bash
sbt docker:publishLocal 
```

#### Build and push continer

```bash
sbt docker:publish 
```

### Kubernetes

```bash
helm install avro-schema-registry -n registry-app --set=database.password=VvWIFgkxLH,database.host=registry-datasource-postgresql,database.port=5432,database.name=postgres
```

## API

Create a schema registry enrollment: 
```bash
curl -X PUT -H "Content-Type: application/json" -H "Cache-Control: no-cache" -d '{
	"schema": {
        "namespace": "generic",
        "type": "record",
        "doc": "test platform event",
        "name": "event",
        "fields":[
             {  "name": "amount", "type": "int"},
             {  "name": "message",  "type": "string"}
        ]   
    }
}' "http://localhost:8000/enrollment/test-event/2.0.1"
```

Find all schema registry enrollments:
```bash
curl -X GET -H "Cache-Control: no-cache" "http://localhost:8000/enrollment"
```

Find specific schema enrollment with all possible versions:
```bash
curl -X GET -H "Cache-Control: no-cache" "http://localhost:8000/enrollment/test-event"
```

Find specific schema registry enrollment:
```bash
curl -X GET -H "Cache-Control: no-cache" "http://localhost:8000/enrollment/test-event/2.0.1"
```

Delete specific schema registry enrollment
```bash
curl -X DELETE -H "Cache-Control: no-cache" "http://localhost:8000/enrollment/test-event/2.0.1"
```
