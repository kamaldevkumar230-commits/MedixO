let localStream;
let peerConnection = new RTCPeerConnection();

async function startCall() {
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

// Offer create
async function createOffer() {
    let offer = await peerConnection.createOffer();
    await peerConnection.setLocalDescription(offer);

    await fetch("/video/send-offer", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(offer)
    });

    // answer receive karo continuously
    setInterval(async () => {
        let res = await fetch("/video/get-answer");
        let answer = await res.json();

        if (answer && !peerConnection.currentRemoteDescription) {
            await peerConnection.setRemoteDescription(answer);
        }
    }, 2000);
}


// 🔴 END CALL FUNCTION
function endCall() {

    // 1. Camera band karo
    if (localStream) {
        localStream.getTracks().forEach(track => track.stop());
    }

    // 2. Peer connection close karo
    if (peerConnection) {
        peerConnection.close();
    }

    // 3. Video screen clear karo
    document.getElementById("localVideo").srcObject = null;
    document.getElementById("remoteVideo").srcObject = null;

    alert("Call Ended ❌");
}