let localStream;
let peerConnection = new RTCPeerConnection();

async function startCamera() {
    localStream = await navigator.mediaDevices.getUserMedia({
        video: true,
        audio: true
    });

    document.getElementById("localVideo").srcObject = localStream;

    localStream.getTracks().forEach(track => {
        peerConnection.addTrack(track, localStream);
    });

    peerConnection.ontrack = event => {
        document.getElementById("remoteVideo").srcObject = event.streams[0];
    };
}

// offer receive karo
setInterval(async () => {
    let res = await fetch("/video/get-offer");
    let offer = await res.json();

    if (offer && !peerConnection.currentRemoteDescription) {

        await peerConnection.setRemoteDescription(offer);

        let answer = await peerConnection.createAnswer();
        await peerConnection.setLocalDescription(answer);

        await fetch("/video/send-answer", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(answer)
        });
    }
}, 2000);