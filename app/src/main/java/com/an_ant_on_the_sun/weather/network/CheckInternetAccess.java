package com.an_ant_on_the_sun.weather.network;

import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class CheckInternetAccess {
    private static final String TAG = CheckInternetAccess.class.getSimpleName();
    private static final String SERVER_HOSTNAME = "8.8.8.8";
    private static final int DNS_PORT = 53;

    // TCP/HTTP/DNS (depending on the port, 53=DNS, 80=HTTP, etc.)
    public static boolean isOnline(){
        try{
            int timeoutMs = 1500;
            Socket socket = new Socket();
            SocketAddress socketAddress = new InetSocketAddress(SERVER_HOSTNAME, DNS_PORT);
            socket.connect(socketAddress, timeoutMs);
            socket.close();
            Log.i(TAG, "Internet is available");
            return true;
        } catch (IOException e){
            Log.i(TAG, "Internet is not available, IOException: ", e);
            return false;
        }
    }

}
