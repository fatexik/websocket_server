var ws = new WebSocket("ws://localhost:9999/example/hello");
var text;
var btn;
window.onload = function(){
    text = document.getElementById("text");
    btn = document.getElementById("btn");
    btn.addEventListener("click",sendMessage)
};
function sendMessage() {
    ws.send(text.value);
}
ws.onmessage = function (ev) {
    console.log(ev.data);
};