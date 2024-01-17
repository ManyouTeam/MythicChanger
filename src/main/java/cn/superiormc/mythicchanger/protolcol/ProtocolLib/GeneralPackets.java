package cn.superiormc.mythicchanger.protolcol.ProtocolLib;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;

public class GeneralPackets {

    protected PacketAdapter packetAdapter;

    public GeneralPackets() {
        initPacketAdapter();
        registerListener();
    }
    protected void registerListener() {
        ProtocolLibrary.getProtocolManager().addPacketListener(packetAdapter);

    }
    protected void initPacketAdapter(){
        return;
    }
}
