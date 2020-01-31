const express = require('express');
const app = express();
const PORT = process.env.PORT || 9100;
const eurekaHelper = require('./eureka-helper');

app.listen(PORT, () => {
  console.log("NodeMS BoilerPlate running on Port " + PORT);
})

app.get('/', (req, res) => {
 	res.json("Hello World!! From Node MS");
})
app.get('/testNode', (req, res) => {
	res.json("Test Node MS Succesfull");
})
eurekaHelper.registerWithEureka('node-ms', PORT);
