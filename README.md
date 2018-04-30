# Schema registry

## Run
### SBT 
Run the project using command: `sbt "project registry" run` 

### Docker

### Kubernetes

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