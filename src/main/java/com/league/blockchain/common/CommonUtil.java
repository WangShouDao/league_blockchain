package com.league.blockchain.common;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.UUID;


public class CommonUtil {
    public static Long getNow(){
        return System.currentTimeMillis();
    }

    public static void main(String[] args){
        InetAddress inetAddress = getLocalHostLANAddress();
        System.out.append(inetAddress.getHostName());
    }

    public static String getLocalIp(){
        InetAddress inetAddress = getLocalHostLANAddress();
        if(inetAddress != null){
            return inetAddress.getHostAddress();
        }
        return null;
    }

    public static String generateUuid(){
        return UUID.randomUUID().toString();
    }

    /**
     * 获取本机ip地址
     * @return
     */
    private static InetAddress getLocalHostLANAddress(){
        try{
            InetAddress candidateAddress = null;
            //遍历所有的网络接口
            for(Enumeration ifaces = NetworkInterface.getNetworkInterfaces();ifaces.hasMoreElements();){
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下遍历IP
                for(Enumeration inetAddrs=iface.getInetAddresses();inetAddrs.hasMoreElements();){
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    // 排除loopback类型地址
                    if(!inetAddr.isLoopbackAddress()){
                        if(inetAddr.isSiteLocalAddress()){
                            // 如果是site-local地址，就是它了
                            return inetAddr;
                        }else if{
                            // site-local类型的地址未发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if(candidateAddress!=null){
                return candidateAddress;
            }
            // 如果没有发现non-loopback地址,只能用最次选方案
            return InetAddress.getLocalHost();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

