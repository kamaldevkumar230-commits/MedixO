let localStream;
let peerConnection = new RTCPeerConnection({
    iceServers: [
        { urls: "stun:stun.l.google.com:19302" }
    ]
});

// 🚑 START CALL
async function startCall(){

    try {

        // 1. Camera ON
        localStream = await navigator.mediaDevices.getUserMedia({
            video:true,
            audio:true
        });

        document.getElementById("localVideo").srcObject = localStream;

        // 2. Track add karo
        localStream.getTracks().forEach(track=>{
            peerConnection.addTrack(track, localStream);
        });

        // 3. Remote video handle
        peerConnection.ontrack = event=>{
            document.getElementById("remoteVideo").srcObject = event.streams[0];
        };

        // 4. Offer create karo
        let offer = await peerConnection.createOffer();
        await peerConnection.setLocalDescription(offer);

        // 5. Backend pe bhejo
        await fetch("/video/send-offer",{
            method:"POST",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify(offer)
        });

        // 6. Answer wait karo
        window.answerInterval = setInterval(async ()=>{
            let res = await fetch("/video/get-answer");
            let answer = await res.json();

            if(answer && !peerConnection.currentRemoteDescription){
                await peerConnection.setRemoteDescription(answer);
            }
        },2000);

    } catch (err) {
        alert("Camera access error ❌");
        console.error(err);
    }
}


// ❌ END CALL FUNCTION
function endCall(){

    // 1. Stop camera
    if(localStream){
        localStream.getTracks().forEach(track => track.stop());
    }

    // 2. Close connection
    if(peerConnection){
        peerConnection.close();
    }

    // 3. Clear videos
    document.getElementById("localVideo").srcObject = null;
    document.getElementById("remoteVideo").srcObject = null;

    // 4. Interval stop
    if(window.answerInterval){
        clearInterval(window.answerInterval);
    }

    // 5. Reset connection (important for next call)
    peerConnection = new RTCPeerConnection({
        iceServers: [
            { urls: "stun:stun.l.google.com:19302" }
        ]
    });

    alert("Call Ended ❌");
}