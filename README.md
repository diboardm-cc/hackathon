Quickstart: Restate + Kafka Example

1. install restate server and cli

```shell
brew install restatedev/tap/restate-server restatedev/tap/restate
```

2. Start Restate and Kafka

```shell
cd ./restate
restate-server --config-file restate.toml
docker compose up
````

3. Let the Restate Server know about the Greeter service by registering it:

```shell
restate deployments register localhost:9080
```

4. Execute the following curl command to create a subscription, and invoke the handler for each event:

```shell
curl localhost:9070/subscriptions --json '{
"source": "kafka://my-cluster/greetings",
"sink": "service://Greeter/greet",
"options": {"auto.offset.reset": "earliest"}
}'
```

5. Publish an Event to Kafka

```shell
docker exec -it broker kafka-console-producer --bootstrap-server broker:29092 --topic greetings
```

Type a message and press enter (the greeter takes a String name as input):

```json 
{
  "name": "Sarah"
}
```
