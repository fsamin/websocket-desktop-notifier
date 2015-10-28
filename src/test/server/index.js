var ws = require("nodejs-websocket")

var Mockaroo = require('mockaroo'); // npm install mockaroo

var client = new Mockaroo.Client({
    apiKey: '0fbca640'
})

var send;
var sleep = require('sleep');



var server = ws.createServer(function (conn) {
    console.log("New connection")
    conn.on("text", function (str) {
        console.log("Received "+str)
        var cmd = JSON.parse(str)
        console.log(cmd)
        if (cmd['type'] === 'LOGIN') {
            cmd['result'] = 'SUCCESS'
        }

        conn.sendText(JSON.stringify(cmd))

        send = function(title, message) {
            var v = {
                type: 'NOTIFICATION',
                data : {
                    title: title,
                    message: message
                },
                result: 'SUCCESS'
            }
            conn.sendText(JSON.stringify(v))
        }

        client.generate({
            count: 10,
            fields: [{
                name: 'title',
                type: 'Words',
                min: 3,
                max : 10
            }, {
                name: 'message',
                type: 'Words',
                min: 10,
                max : 50
            }]
        }).then(function(records) {
            for (var i=0; i<records.length; i++) {
                sleep.sleep(15);
                var record = records[i];
                console.log('record ' + i, 'title:' + record.title + ', message:' + record.message + ', image: http://placehold.it/50x50');
                send(record.title, record.message)
            }
        });



    })
    conn.on("close", function (code, reason) {
        console.log("Connection closed")
    })
}).listen(8001)