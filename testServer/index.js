var ws = require("nodejs-websocket")

// Scream server example: "hi" -> "HI!!!"
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
    })
    conn.on("close", function (code, reason) {
        console.log("Connection closed")
    })
}).listen(8001)