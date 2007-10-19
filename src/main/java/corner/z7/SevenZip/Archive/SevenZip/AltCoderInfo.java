package corner.z7.SevenZip.Archive.SevenZip;

import corner.z7.Common.ByteBuffer;


class AltCoderInfo {
    public MethodID MethodID;
    public ByteBuffer Properties;

    public AltCoderInfo() {
        MethodID = new MethodID();
        Properties = new ByteBuffer();
    }
}
