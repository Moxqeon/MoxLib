package org.moxqeon.module.websocket;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.PingFrame;
import org.java_websocket.framing.PongFrame;
import org.java_websocket.handshake.ServerHandshake;
import org.moxqeon.MoxUtil;

import java.net.URI;

/**
 * 简单 WebSocket 客户端抽象类。
 * <p>
 * 处理连接打开与 Ping 帧的 Pong 响应。
 */
public abstract class SimpleWebSocketClient extends WebSocketClient {
    public SimpleWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakeData) {
        MoxUtil.info("WebSocket open");
        MoxUtil.info("Address: " + uri);
    }


    @Override
    public void onWebsocketPing(WebSocket conn, Framedata f) {
        PingFrame pingFrame = (PingFrame) f;
        sendFrame(new PongFrame(pingFrame));
    }

}