package com.ucast.taxiscreen.events;

/**
 * Created by pj on 2019/5/31.
 */
public class ERPMountEvent {
    String mount;

    public ERPMountEvent(String mount) {
        this.mount = mount;
    }

    public String getMount() {
        return mount;
    }

    public void setMount(String mount) {
        this.mount = mount;
    }
}
