package com.example.happyhomes.Model;

public class Use {
    private int toolsId;
    private byte[] toolsPic;

    public int getToolsId() {
        return toolsId;
    }

    public void setToolsId(int toolsId) {
        this.toolsId = toolsId;
    }

    public byte[] getToolsPic() {
        return toolsPic;
    }

    public void setToolsPic(byte[] toolsPic) {
        this.toolsPic = toolsPic;
    }

    public Use(int toolsId, byte[] toolsPic) {
        this.toolsId = toolsId;
        this.toolsPic = toolsPic;
    }
}
